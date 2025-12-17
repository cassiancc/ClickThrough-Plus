# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

## [3.6.0]

### Changed
- Mod configuration is now handled via Kaleido Config and McQoy. Current targets are 1.21.10 and 1.21.11.

## [3.5.3] - 2025-10-18

### Fixed
- Crash from leftover debugging mixin.

## [3.5.2] - 2025-10-14

### Fixed
- Keybind category sorting

## [3.5.1] - 2025-06-26

### Fixed
- Crash on startup in Fabric 1.21+

## [3.5.0] - 2025-06-26

### Added
- Config option to add additional containers to be clicked to.

### Changed
- Empty Fast Item Frames can now be clicked through.
- Fabric no longer uses Architectury API for keybinds.

## [3.4.1] - 2025-04-16

### Fixed
- Crash on Forge.

## [3.4] -2025-04-11

### Changed
- Ported buildscript to Stonecutter. Mod functionality should now be more consistent across versions.

### Fixed
- Configs are now saved when the game is closed, fixing an issue where the config wouldn't be saved when changed via the keybind.

## [3.3] - 2025-03-09

## Added
- Compatibility with Fast Item Frames.

## [3.2] - 2025-03-09

## Added
- Improved compatibility with modded chests.

## Changed
- Removed dependency on Architectury API on Fabric 1.21

## [3.2] - 2025-03-09

## Added
- Improved compatibility with modded chests.

## Fixed
- Broken Architectury API dependency on Fabric.

## [3.1] - 2025-11-02

## Added
- Compatibility with 1.21.4

## Changed
- Removed mandatory dependency on Architectury API. It is now only required for keybinds.

## [3.0] - 2025-08-15

Initial release.