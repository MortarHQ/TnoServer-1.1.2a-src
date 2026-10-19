---
页面: configs/explorerscompass
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `explorerscompass` 配置项

> **2 个文件 / 13 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/explorerscompass](../../mods/explorerscompass/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `explorerscompass-common.toml` | `General.allowTeleport` | `True` | bool | Allows a player to teleport to a located structure when in c | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`explorerscompass:TeleportPacket` |
| `explorerscompass-common.toml` | `General.maxNextSearches` | `100` | int | Range: 0 ~ 10000 | `explorerscompass:ConcentricRingsSearchWorker`、`explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompass` |
| `explorerscompass-common.toml` | `General.displayCoordinates` | `True` | bool | Allows players to view the precise coordinates and distance  | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`explorerscompass:StructureSearchWorker` |
| `explorerscompass-common.toml` | `General.maxRadius` | `35000` | int | Range: 0 ~ 1000000 | `cerbons_api:ParticleUtils`、`eternal_starlight:OrbitalAshenSnowParticle`、`explorerscompass:ConfigHandler` |
| `explorerscompass-common.toml` | `General.structureBlacklist` | `[]` | list | A list of structures that the compass will not display in th | `explorerscompass:ConfigHandler`、`explorerscompass:StructureUtils` |
| `explorerscompass-common.toml` | `General.defaultXpLevels` | `0` | int | Range: 0 ~ 3 | `explorerscompass:ConfigHandler`、`explorerscompass:StructureUtils`、`naturescompass:BiomeUtils` |
| `explorerscompass-common.toml` | `General.xpLevelOverrides` | `[]` | list | A list of structure-specific XP level costs that override de | `explorerscompass:ConfigHandler`、`explorerscompass:StructureUtils`、`naturescompass:BiomeUtils` |
| `explorerscompass-common.toml` | `General.maxSamples` | `100000` | int | Range: 0 ~ 100000000 | `explorerscompass:ConcentricRingsSearchWorker`、`explorerscompass:ConfigHandler`、`explorerscompass:StructureSearchWorker` |
| `explorerscompass-common.toml` | `General.compassDurability` | `35` | int | Range: 0 ~ 10000 | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`naturescompass:ConfigHandler` |
| `explorerscompass-client.toml` | `Client.displayWithChatOpen` | `True` | bool | Displays Explorer's Compass information on the HUD even whil | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassOverlay`、`naturescompass:ConfigHandler` |
| `explorerscompass-client.toml` | `Client.translateStructureNames` | `True` | bool | Attempts to translate structure names before fixing the unlo | `explorerscompass:ConfigHandler`、`explorerscompass:StructureUtils` |
| `explorerscompass-client.toml` | `Client.overlayLineOffset` | `1` | int | Range: 0 ~ 50 | `explorerscompass:ConfigHandler`、`explorerscompass:RenderUtils`、`naturescompass:ConfigHandler` |
| `explorerscompass-client.toml` | `Client.overlaySide` | `LEFT` | str | Allowed Values: LEFT, RIGHT | `explorerscompass:ConfigHandler`、`explorerscompass:RenderUtils`、`naturescompass:ConfigHandler` |
