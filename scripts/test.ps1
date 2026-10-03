# Run in PowerShell 7 with JDK 21 installed.
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
Push-Location $projectRoot
try {
    New-Item -ItemType Directory -Force artifacts | Out-Null
    & ./scripts/validate-resources.ps1
    & .\gradlew.bat build runGameTestServer --console=plain
    if ($LASTEXITCODE -ne 0) { throw 'Build or server GameTest failed.' }
    $testWorld = Join-Path $projectRoot 'run/saves/RedundantGuns-SmokeWorld'
    if (-not (Test-Path -LiteralPath $testWorld)) {
        New-Item -ItemType Directory -Force (Split-Path -Parent $testWorld) | Out-Null
        Copy-Item -LiteralPath (Join-Path $projectRoot 'run/world') -Destination $testWorld -Recurse
    }
    & .\gradlew.bat runClientSmoke --console=plain
    if ($LASTEXITCODE -ne 0) { throw 'Client smoke test failed.' }
} finally {
    Pop-Location
}
