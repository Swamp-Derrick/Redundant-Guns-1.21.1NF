# Redundant Guns: NeoForge Port

Minecraft 1.21.1 / NeoForge port of Redundant Guns by zaeonNineZero.

**[Download 0.1.1](https://github.com/Swamp-Derrick/Redundant-Guns-1.21.1NF/releases/tag/v0.1.1)**

Adds 12 guns, 2 attachments, 13 creative weapon variants and 27 workbench recipes. The original `redundantguns` item IDs are preserved.

## Required Mods

- Minecraft 1.21.1, Java 21, NeoForge 21.1.228 or newer within 21.1.x.
- [createmeow's MrCrayfish's Gun Mod port](https://github.com/createmeow/MrCrayfishGunMod/tree/1.21.1), tested with `cgm-1.4.4.jar`.
- [NZGE-Unofficial](https://github.com/Swamp-Derrick/NineZero-Gun-Expansion-1.21.1NF), version 0.1.1, or its earlier NeoForge build `1.5.0-port.1+1.21.1`. Install only one. Supplies shared ammunition, models, sounds and the component-aware workbench serializer.
- Framework 0.13.11 for Minecraft 1.21.1 / NeoForge (CurseForge file 7530361).

Install all four mod JARs on both client and server. Do not also load the original 1.19.2 Forge versions.

This port uses ordinary CGM firing animations and reloading. The separate CGM Expanded animation engine and its detachable-magazine mechanics are not present in the requested CGM fork.

## Build and Test

Put `cgm-1.4.4.jar` and `NZGE-Unofficial-0.1.1.jar` in `libs/`, then run:

```powershell
.\gradlew.bat build
.\gradlew.bat runGameTestServer
.\gradlew.bat runClient
```

For the complete automated suite, run `pwsh -File scripts/test.ps1`. It uses the isolated `run/` development instance, creates a test world and closes the smoke-test client automatically. The client requires OpenGL and a desktop session. Test classes live in `src/gameTest` and are excluded from the release JAR.

## Source and License

- Original/fork starting commit: `ac7773bdc30146879b00720b1fa3ffef8cdd9bf5`.
- Target CGM branch verified at `00e6ec6de31f81fd46ec2476c03c8ec43d86c219`.
- Original author: zaeonNineZero. CGM: MrCrayfish. NeoForge port: Swamp-Derrick.
- GPL-3.0; see [LICENSE](LICENSE).

Version `0.1.1` includes the NeoForge 1.21.1 port and accepts both NZGE NeoForge version names listed above. The release contains only the mod JAR; required mods are installed separately.
