---
页面: configs/chronodawn
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `chronodawn` 配置项

> **6 个文件 / 34 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/chronodawn](../../mods/chronodawn/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `chronodawn.toml` | `schema_version` | `1` | int | field, delete the line; this file is overwritten only when m | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.structures.ancient_ruins.enabled` | `True` | bool | Whether Clockstone Ore generates in the Chrono dimension. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `chronodawn.toml` | `world.structures.ancient_ruins.spacing` | `56` | int | Lower = denser, higher = rarer. Must be > separation. Vanill | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn.toml` | `world.structures.ancient_ruins.separation` | `20` | int | Must be < spacing. | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn.toml` | `world.structures.ancient_ruins.salt` | `20005897` | int | Random seed offset for placement; usually no reason to chang | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn.toml` | `world.ores.time_crystal.enabled` | `True` | bool | Whether Clockstone Ore generates in the Chrono dimension. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `chronodawn.toml` | `world.ores.time_crystal.count` | `3` | int | Each attempt generates one cluster (cluster size is fixed at | `architectury:ItemStackHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CampsiteCountPlacement` |
| `chronodawn.toml` | `world.ores.time_crystal.y_min` | `0` | int | Distribution between min and max is biased toward the middle | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.time_crystal.y_max` | `48` | int |  | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.entropy_crystal.enabled` | `True` | bool | Whether Clockstone Ore generates in the Chrono dimension. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `chronodawn.toml` | `world.ores.entropy_crystal.count` | `4` | int | Each attempt generates one cluster (cluster size is fixed at | `architectury:ItemStackHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CampsiteCountPlacement` |
| `chronodawn.toml` | `world.ores.entropy_crystal.y_min` | `40` | int | Distribution between min and max is biased toward the middle | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.entropy_crystal.y_max` | `100` | int |  | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.temporal_amber.enabled` | `True` | bool | Whether Clockstone Ore generates in the Chrono dimension. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `chronodawn.toml` | `world.ores.temporal_amber.count` | `4` | int | Each attempt generates one cluster (cluster size is fixed at | `architectury:ItemStackHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CampsiteCountPlacement` |
| `chronodawn.toml` | `world.ores.temporal_amber.y_min` | `-30` | int | Distribution between min and max is biased toward the middle | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.temporal_amber.y_max` | `20` | int |  | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.clockstone.enabled` | `True` | bool | Whether Clockstone Ore generates in the Chrono dimension. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `chronodawn.toml` | `world.ores.clockstone.count` | `8` | int | Each attempt generates one cluster (cluster size is fixed at | `architectury:ItemStackHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CampsiteCountPlacement` |
| `chronodawn.toml` | `world.ores.clockstone.y_min` | `-16` | int | Distribution between min and max is biased toward the middle | `chronodawn:ConfigLoader` |
| `chronodawn.toml` | `world.ores.clockstone.y_max` | `80` | int |  | `chronodawn:ConfigLoader` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/structur` | `structures` | `[{"structure": "chronodawn:ancient_ruins` | list |  | `artifacts:ItemConfigs`、`artifacts:Language`、`block_factorys_bosses:BossesRise` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/structur` | `placement.type` | `minecraft:random_spread` | str |  | `ancient_artifacts:ArtifactCurioItem`、`ancient_artifacts:ArtifactEffect`、`ancient_artifacts:PlatformEquipmentImpl` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/structur` | `placement.salt` | `20005897` | int |  | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/structur` | `placement.spacing` | `56` | int |  | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/structur` | `placement.separation` | `20` | int |  | `betterdeserttemples:BetterDesertTemplePlacement`、`betterjungletemples:BetterJungleTemplePlacement`、`betterstrongholds:BetterStrongholdsPlacement` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `feature` | `chronodawn:ore_temporal_amber` | str |  | `architectury:BiomeModificationsImpl`、`artifacts:CampsiteFeature`、`artifacts:CampsiteFeatureConfiguration` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `placement` | `[{"type": "minecraft:count", "count": 4}` | list |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:GenerationProperties` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `feature` | `chronodawn:ore_clockstone` | str |  | `architectury:BiomeModificationsImpl`、`artifacts:CampsiteFeature`、`artifacts:CampsiteFeatureConfiguration` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `placement` | `[{"type": "minecraft:count", "count": 8}` | list |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:GenerationProperties` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `feature` | `chronodawn:ore_time_crystal` | str |  | `architectury:BiomeModificationsImpl`、`artifacts:CampsiteFeature`、`artifacts:CampsiteFeatureConfiguration` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `placement` | `[{"type": "minecraft:count", "count": 3}` | list |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:GenerationProperties` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `feature` | `chronodawn:ore_entropy_crystal` | str |  | `architectury:BiomeModificationsImpl`、`artifacts:CampsiteFeature`、`artifacts:CampsiteFeatureConfiguration` |
| `chronodawn-runtime-overlay/data/chronodawn/worldgen/placed_f` | `placement` | `[{"type": "minecraft:count", "count": 4}` | list |  | `architectury:BiomeHooks`、`architectury:BiomeModificationsImpl`、`architectury:GenerationProperties` |
