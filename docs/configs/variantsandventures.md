---
页面: configs/variantsandventures
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `variantsandventures` 配置项

> **3 个文件 / 78 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/variantsandventures](../../mods/variantsandventures/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `variantsandventures.json` | `enableGelid` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:GelidEntity`、`variantsandventures:GelidOnEntitySpawn` |
| `variantsandventures.json` | `enableGelidSpawns` | `True` | bool |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `gelidSpawnChance` | `80.0` | float |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `gelidMinimumYLevel` | `-64` | int |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableGelidSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `gelidSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableGelidSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures.json` | `enableMurk` | `True` | bool |  | `variantsandventures:MurkEntity`、`variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesEntityTypes` |
| `variantsandventures.json` | `enableMurkSpawns` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesEntityTypes` |
| `variantsandventures.json` | `enableMurkSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures.json` | `enableThicket` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:ThicketEntity`、`variantsandventures:ThicketOnEntitySpawn` |
| `variantsandventures.json` | `enableThicketSpawns` | `True` | bool |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `thicketSpawnChance` | `80.0` | float |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `thicketMinimumYLevel` | `-64` | int |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableThicketSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `thicketSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableThicketSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures.json` | `enableVerdant` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantEntity` |
| `variantsandventures.json` | `enableVerdantSpawns` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures.json` | `verdantSpawnChance` | `80.0` | float |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures.json` | `verdantMinimumYLevel` | `-64` | int |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures.json` | `enableVerdantSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `verdantSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableVerdantSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures.json` | `enableBetterStraySpawns` | `True` | bool |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `straySpawnChance` | `80.0` | float |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `strayMinimumYLevel` | `-64` | int |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableStraySpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `straySpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableBetterHuskSpawns` | `True` | bool |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `huskSpawnChance` | `80.0` | float |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `huskMinimumYLevel` | `-64` | int |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableHuskSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `huskSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableBetterBoggedSpawns` | `True` | bool |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `boggedSpawnChance` | `80.0` | float |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `boggedMinimumYLevel` | `-64` | int |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `enableBoggedSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures.json` | `boggedSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableGelid` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:GelidEntity`、`variantsandventures:GelidOnEntitySpawn` |
| `variantsandventures/mod_mobs.json` | `enableGelidSpawns` | `True` | bool |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `gelidSpawnChance` | `80.0` | float |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `gelidMinimumYLevel` | `-64` | int |  | `variantsandventures:GelidOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableGelidSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `gelidSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableGelidSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures/mod_mobs.json` | `enableMurk` | `True` | bool |  | `variantsandventures:MurkEntity`、`variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesEntityTypes` |
| `variantsandventures/mod_mobs.json` | `enableMurkSpawns` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesEntityTypes` |
| `variantsandventures/mod_mobs.json` | `enableMurkSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures/mod_mobs.json` | `enableThicket` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:ThicketEntity`、`variantsandventures:ThicketOnEntitySpawn` |
| `variantsandventures/mod_mobs.json` | `enableThicketSpawns` | `True` | bool |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `thicketSpawnChance` | `80.0` | float |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `thicketMinimumYLevel` | `-64` | int |  | `variantsandventures:ThicketOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableThicketSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `thicketSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableThicketSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures/mod_mobs.json` | `enableVerdant` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantEntity` |
| `variantsandventures/mod_mobs.json` | `enableVerdantSpawns` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures/mod_mobs.json` | `verdantSpawnChance` | `80.0` | float |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures/mod_mobs.json` | `verdantMinimumYLevel` | `-64` | int |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VerdantOnEntitySpawn` |
| `variantsandventures/mod_mobs.json` | `enableVerdantSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `verdantSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/mod_mobs.json` | `enableVerdantSpawnersInTrialChambers` | `True` | bool |  | `variantsandventures:VariantsAndVenturesConfig`、`variantsandventures:VariantsAndVenturesStructurePoolAliases` |
| `variantsandventures/vanilla_mobs.json` | `enableBetterStraySpawns` | `True` | bool |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `straySpawnChance` | `80.0` | float |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `strayMinimumYLevel` | `-64` | int |  | `variantsandventures:StrayOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `enableStraySpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `straySpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `enableBetterHuskSpawns` | `True` | bool |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `huskSpawnChance` | `80.0` | float |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `huskMinimumYLevel` | `-64` | int |  | `variantsandventures:HuskOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `enableHuskSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `huskSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `enableBetterBoggedSpawns` | `True` | bool |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `boggedSpawnChance` | `80.0` | float |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `boggedMinimumYLevel` | `-64` | int |  | `variantsandventures:BoggedOnEntitySpawn`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `enableBoggedSpawners` | `True` | bool |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
| `variantsandventures/vanilla_mobs.json` | `boggedSpawnerChance` | `80.0` | float |  | `variantsandventures:DungeonFeatureMixin`、`variantsandventures:VariantsAndVenturesConfig` |
