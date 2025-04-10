# ClickThrough+

<a href='https://modrinth.com/mod/clickthrough+/versions?l=fabric'><img alt="fabric" height="56" src="https://raw.githubusercontent.com/intergrav/devins-badges/refs/heads/v3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/clickthrough+/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/NeoForge.svg"></a>

This mod helps you access containers (Chests, Shulker Boxes, Barrels, etc.) that are marked with a Sign or Item Frame. Right-clicking a sign or item frame that's attached to a container will open the container instead.

ClickThrough Plus works clientside, and does not need to be installed on the server.

You can still rotate items in item frames or edit signs by sneaking and clicking them.

## Installation

ClickThrough Plus is a completely clientside mod for Fabric and NeoForge, as well as Forge (1.18-1.20.6) and Quilt (where QSL is available)
- [Architectury API](https://modrinth.com/mod/architectury-api) is required.
  - On snapshots, or when Architectury API is not installed, the mod will work without it, but keybinds will not be available. Fabric API is still required.
- Mod settings are available with [Mod Menu](https://modrinth.com/mod/mod-menu) (only required on Fabric) and [Cloth Config](https://modrinth.com/mod/cloth-config).

## Mod Compatibility + Config Options

- By default, ClickThrough Plus will only click through to valid containers, including all mods with properly tagged chests, shulker boxes, and similar containers. To disable this, change `onlyContainers` to false.
- ClickThrough Plus has optional compatibility with the [Fast Item Frames](https://modrinth.com/mod/fast-item-frames) mod. Note that with ClickThrough active, you will not be able to rotate items, as shift-right clicking a Fast Item Frame [does not rotate it](https://github.com/Fuzss/fastitemframes/issues/6), unlike a vanilla Item Frame. 

## Credits

This is a port of Giselbaer's ClickThrough to Architectury 1.21 and above, allowing for the mod to be used on both Fabric and NeoForge, as well as Quilt and Forge on older versions. ClickThrough is available under the [MIT License](https://github.com/gbl/ClickThrough/blob/fabric_1_20/LICENSE).

This mod will be kept up to date with the latest versions of Minecraft, currently focusing on 1.20 and above.

Mod Menu/Cloth Config integration is based on the integration present in [idwtialsimmoedm](https://modrinth.com/mod/idwtialsimmoedm) under its [MIT License](https://github.com/gliscowo/idwtialsimmoedm/blob/1.21/LICENSE).
