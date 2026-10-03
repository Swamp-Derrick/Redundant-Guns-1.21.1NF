# Requires PowerShell 7. Reads the exact dependency JARs without extracting them.
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$resources = Join-Path $projectRoot 'src/main/resources'
$archives = @(
    [System.IO.Compression.ZipFile]::OpenRead((Join-Path $projectRoot 'libs/cgm-1.4.4.jar')),
    [System.IO.Compression.ZipFile]::OpenRead((Join-Path $projectRoot 'libs/NZGE-Unofficial-0.1.1.jar'))
)
$problems = [System.Collections.Generic.List[string]]::new()
function Read-Resource([string]$name) {
    $local = Join-Path $resources $name
    if (Test-Path -LiteralPath $local -PathType Leaf) { return Get-Content -Raw -LiteralPath $local }
    foreach ($archive in $archives) {
        $entry = $archive.GetEntry($name)
        if ($entry) {
            $reader = [System.IO.StreamReader]::new($entry.Open())
            try { return $reader.ReadToEnd() } finally { $reader.Dispose() }
        }
    }
    return $null
}
function Test-Resource([string]$location, [string]$category, [string]$extension) {
    if ($location -notmatch ':') { $location = "minecraft:$location" }
    $namespace, $name = $location.Split(':', 2)
    if ($namespace -eq 'minecraft') { return $true } # Actual client bake validates vanilla assets.
    $relative = "assets/$namespace/$category/$name$extension"
    if (Test-Path -LiteralPath (Join-Path $resources $relative) -PathType Leaf) { return $true }
    foreach ($archive in $archives) { if ($archive.GetEntry($relative)) { return $true } }
    return $false
}
try {
    $soundDefinitions = @{}
    $language = @{}
    foreach ($namespace in @('cgm', 'nzgmaddon', 'redundantguns')) {
        $soundDefinitions[$namespace] = Read-Resource "assets/$namespace/sounds.json" | ConvertFrom-Json -AsHashtable
        $lang = Read-Resource "assets/$namespace/lang/en_us.json" | ConvertFrom-Json -AsHashtable
        foreach ($key in $lang.Keys) { $language[$key] = $lang[$key] }
    }
    $jsonFiles = Get-ChildItem -LiteralPath $resources -Recurse -File |
        Where-Object { $_.Extension -in @('.json', '.mcmeta', '.cgmmeta') }
    foreach ($file in $jsonFiles) {
        $data = Get-Content -Raw -LiteralPath $file.FullName | ConvertFrom-Json -AsHashtable
        if ($file.FullName -notmatch '[\\/]models[\\/]') { continue }
        foreach ($texture in $data.textures.Values) {
            if (-not $texture.StartsWith('#') -and -not (Test-Resource $texture 'textures' '.png')) {
                $problems.Add("$($file.Name): unresolved texture $texture")
            }
        }
        foreach ($override in $data.overrides) {
            if (-not (Test-Resource $override.model 'models' '.json')) { $problems.Add("$($file.Name): unresolved override $($override.model)") }
        }
        if ($data.parent -and -not (Test-Resource $data.parent 'models' '.json')) {
            $problems.Add("$($file.Name): unresolved parent $($data.parent)")
        }
        if (-not $data.parent) {
            foreach ($part in @($data.elements) + @($data.components)) {
                foreach ($face in $part.faces.Values) {
                    $texture = $face.texture
                    $seen = @{}
                    while ($texture -and $texture.StartsWith('#')) {
                        $key = $texture.Substring(1)
                        if ($seen.ContainsKey($key) -or -not $data.textures.ContainsKey($key)) {
                            $problems.Add("$($file.Name): undefined/cyclic texture $texture")
                            break
                        }
                        $seen[$key] = $true
                        $texture = $data.textures[$key]
                    }
                }
            }
        }
    }
    $modelSource = Get-Content -Raw (Join-Path $projectRoot 'src/main/java/zaeonninezero/redundantguns/client/RedundantSpecialModels.java')
    foreach ($match in [regex]::Matches($modelSource, '\("((?:gun|attachment)/[^"]+)"\)')) {
        $location = 'redundantguns:special/' + $match.Groups[1].Value
        if (-not (Test-Resource $location 'models' '.json')) { $problems.Add("Registered model does not exist: $location") }
    }
    $itemSource = Get-Content -Raw (Join-Path $projectRoot 'src/main/java/zaeonninezero/redundantguns/init/initItems.java')
    $items = [regex]::Matches($itemSource, 'ITEMS.register\("([^"]+)"') | ForEach-Object { $_.Groups[1].Value }
    if ($items.Count -ne 14) { $problems.Add("Expected 14 items, got $($items.Count)") }
    foreach ($item in $items) {
        if (-not $language.ContainsKey("item.redundantguns.$item")) { $problems.Add("Missing item translation: $item") }
    }
    $recipes = Get-ChildItem (Join-Path $resources 'data/redundantguns/recipe') -Filter *.json
    if ($recipes.Count -ne 27) { $problems.Add("Expected 27 recipes, got $($recipes.Count)") }
    foreach ($file in $recipes) {
        $recipe = Get-Content -Raw $file.FullName | ConvertFrom-Json -AsHashtable
        if ($recipe.type -ne 'nzgmaddon:workbench' -or $recipe.recipe_id -ne "redundantguns:$($file.BaseName)") {
            $problems.Add("Incorrect recipe identity: $($file.Name)")
        }
        if (($recipe.result.id -replace '^redundantguns:') -notin $items) { $problems.Add("Unknown recipe result: $($file.Name)") }
        if ($recipe.result.ContainsKey('nbt')) { $problems.Add("Legacy NBT result: $($file.Name)") }
        foreach ($material in $recipe.materials) {
            if ($material.tag -like 'forge:*') { $problems.Add("Legacy material tag: $($file.Name)") }
        }
    }
    $guns = Get-ChildItem (Join-Path $resources 'data/redundantguns/guns') -Filter *.json
    if ($guns.Count -ne 12) { $problems.Add("Expected 12 gun definitions, got $($guns.Count)") }
    foreach ($file in $guns) {
        $gun = Get-Content -Raw $file.FullName | ConvertFrom-Json -AsHashtable
        foreach ($sound in $gun.sounds.Values) {
            $ns, $key = $sound.Split(':', 2)
            if (-not $soundDefinitions[$ns].ContainsKey($key)) { $problems.Add("$($file.Name): missing sound event $sound") }
        }
    }
    $soundSource = Get-Content -Raw (Join-Path $projectRoot 'src/main/java/zaeonninezero/redundantguns/init/initSounds.java')
    foreach ($match in [regex]::Matches($soundSource, '= register\("([^"]+)"')) {
        $key = $match.Groups[1].Value
        if (-not $soundDefinitions.redundantguns.ContainsKey($key)) { $problems.Add("Registered sound has no definition: $key") }
    }
    foreach ($event in $soundDefinitions.redundantguns.GetEnumerator()) {
        if ($event.Value.subtitle -and -not $language.ContainsKey($event.Value.subtitle)) {
            $problems.Add("$($event.Key): unresolved subtitle $($event.Value.subtitle)")
        }
        foreach ($sound in $event.Value.sounds) {
            $location = if ($sound -is [string]) { $sound } else { $sound.name }
            if ($sound -is [System.Collections.IDictionary] -and $sound.type -eq 'event') { continue }
            if (-not (Test-Resource $location 'sounds' '.ogg')) { $problems.Add("$($event.Key): missing sound $location") }
        }
    }
    if ($problems.Count) { throw ($problems -join [Environment]::NewLine) }
    Write-Output "PASS: $($jsonFiles.Count) JSON/metadata files; 14 items; 12 guns; 27 recipes; model/texture/sound/translation references."
} finally {
    foreach ($archive in $archives) { $archive.Dispose() }
}
