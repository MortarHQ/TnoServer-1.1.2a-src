---
页面: configs/naturescompass
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `naturescompass` 配置项

> **2 个文件 / 14 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/naturescompass](../../mods/naturescompass/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `naturescompass-client.toml` | `Client.displayWithChatOpen` | `True` | bool | Displays Nature's Compass information even while chat is ope | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassOverlay`、`naturescompass:ConfigHandler` |
| `naturescompass-client.toml` | `Client.fixBiomeNames` | `True` | bool | Fixes biome names by adding missing spaces. Ex: ForestHills  | `naturescompass:BiomeUtils`、`naturescompass:ConfigHandler` |
| `naturescompass-client.toml` | `Client.overlayLineOffset` | `1` | int | Range: 0 ~ 50 | `explorerscompass:ConfigHandler`、`explorerscompass:RenderUtils`、`naturescompass:ConfigHandler` |
| `naturescompass-client.toml` | `Client.overlaySide` | `LEFT` | str | Allowed Values: LEFT, RIGHT | `explorerscompass:ConfigHandler`、`explorerscompass:RenderUtils`、`naturescompass:ConfigHandler` |
| `naturescompass-common.toml` | `General.allowTeleport` | `True` | bool | Allows a player to teleport to a located biome when in creat | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`explorerscompass:TeleportPacket` |
| `naturescompass-common.toml` | `General.maxNextSearches` | `25` | int | Range: 0 ~ 10000 | `explorerscompass:ConcentricRingsSearchWorker`、`explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompass` |
| `naturescompass-common.toml` | `General.displayCoordinates` | `True` | bool | Allows players to view the precise coordinates and distance  | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`explorerscompass:StructureSearchWorker` |
| `naturescompass-common.toml` | `General.radiusModifier` | `2500` | int | Range: 0 ~ 1000000 | `naturescompass:BiomeSearchWorker`、`naturescompass:ConfigHandler` |
| `naturescompass-common.toml` | `General.sampleSpaceModifier` | `16` | int | Range: 0 ~ 1000000 | `naturescompass:BiomeSearchWorker`、`naturescompass:ConfigHandler` |
| `naturescompass-common.toml` | `General.biomeBlacklist` | `[]` | list | A list of biomes that the compass will not be able to search | `mowziesmobs:BiomeChecker`、`mowziesmobs:ConfigHandler`、`naturescompass:BiomeUtils` |
| `naturescompass-common.toml` | `General.defaultXpLevels` | `0` | int | Range: 0 ~ 3 | `explorerscompass:ConfigHandler`、`explorerscompass:StructureUtils`、`naturescompass:BiomeUtils` |
| `naturescompass-common.toml` | `General.perBiomeXpLevels` | `[]` | list | A list of per-biome XP level costs that override defaultXpLe | `naturescompass:BiomeUtils`、`naturescompass:ConfigHandler` |
| `naturescompass-common.toml` | `General.maxSamples` | `50000` | int | Range: 0 ~ 1000000 | `explorerscompass:ConcentricRingsSearchWorker`、`explorerscompass:ConfigHandler`、`explorerscompass:StructureSearchWorker` |
| `naturescompass-common.toml` | `General.compassDurability` | `0` | int | Range: 0 ~ 10000 | `explorerscompass:ConfigHandler`、`explorerscompass:ExplorersCompassItem`、`naturescompass:ConfigHandler` |
