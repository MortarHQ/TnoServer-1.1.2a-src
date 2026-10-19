---
页面: configs/emi_loot
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `emi_loot` 配置项

> **1 个文件 / 31 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/emi_loot](../../mods/emi_loot/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `emi_loot_config.toml` | `version` | `1` | int | Don't change this! Version used to track needed updates. | `balm:NeoForgeBalmNetworking`、`betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge` |
| `emi_loot_config.toml` | `debugMode` | `False` | bool |  | `emi_loot:EMILoot`、`jei:DebugConfig` |
| `emi_loot_config.toml` | `parseChestLoot` | `True` | bool |  | `emi_loot:EMILoot`、`emi_loot:LootTableParser` |
| `emi_loot_config.toml` | `parseBlockLoot` | `True` | bool |  | `emi_loot:EMILoot`、`emi_loot:LootTableParser` |
| `emi_loot_config.toml` | `parseMobLoot` | `True` | bool |  | `emi_loot:EMILoot`、`emi_loot:LootTableParser` |
| `emi_loot_config.toml` | `parseGameplayLoot` | `True` | bool |  | `emi_loot:EMILoot`、`emi_loot:LootTableParser` |
| `emi_loot_config.toml` | `parseArchaeologyLoot` | `True` | bool |  | `emi_loot:EMILoot`、`emi_loot:LootTableParser` |
| `emi_loot_config.toml` | `skippedKeys` | `["emi_loot.function.limit_count", "emi_l` | list |  | `emi_loot:AbstractTextKeyParsingClientLootTable`、`emi_loot:EMILoot`、`emi_loot:TextKey` |
| `emi_loot_config.toml` | `chestLootAlwaysStackSame` | `False` | bool |  | `emi_loot:ArchaeologyLootRecipe`、`emi_loot:ChestLootRecipe`、`emi_loot:EMILoot` |
| `emi_loot_config.toml` | `splitChestLootByTrial` | `False` | bool |  | `emi_loot:ChestLootRecipe`、`emi_loot:EMILoot` |
| `emi_loot_config.toml` | `chanceDecimalPlaces` | `1` | int |  | `emi_loot:ArchaeologyLootRecipe`、`emi_loot:BlockLootRecipe`、`emi_loot:ChestLootRecipe` |
| `emi_loot_config.toml` | `conditionStyle` | `default` | str |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `linkFormatting` | `["UNDERLINE"]` | list |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `debugModes.block` | `False` | bool |  | `apple_cows:AppleCowEntity`、`architectury:ArchitecturyFlowingFluid`、`architectury:ArchitecturyFluidAttributes` |
| `emi_loot_config.toml` | `debugModes.chest` | `False` | bool |  | `ancient_artifacts:ArtifactSlot`、`artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
| `emi_loot_config.toml` | `debugModes.mob` | `False` | bool |  | — |
| `emi_loot_config.toml` | `debugModes.gameplay` | `False` | bool |  | `bookshelf:DebugCommands`、`emi_loot:AbstractTextKeyParsingClientLootTable`、`emi_loot:ClientLootTables` |
| `emi_loot_config.toml` | `debugModes.archaeology` | `False` | bool |  | `artifacts:ConfigValueChance`、`artifacts:LootModifiers`、`betterdeserttemples:OrangeStainedGlassProcessor` |
| `emi_loot_config.toml` | `compactLoot.block` | `True` | bool |  | `apple_cows:AppleCowEntity`、`architectury:ArchitecturyFlowingFluid`、`architectury:ArchitecturyFluidAttributes` |
| `emi_loot_config.toml` | `compactLoot.chest` | `True` | bool |  | `ancient_artifacts:ArtifactSlot`、`artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
| `emi_loot_config.toml` | `compactLoot.mob` | `True` | bool |  | — |
| `emi_loot_config.toml` | `compactLoot.gameplay` | `True` | bool |  | `bookshelf:DebugCommands`、`emi_loot:AbstractTextKeyParsingClientLootTable`、`emi_loot:ClientLootTables` |
| `emi_loot_config.toml` | `compactLoot.archaeology` | `True` | bool |  | `artifacts:ConfigValueChance`、`artifacts:LootModifiers`、`betterdeserttemples:OrangeStainedGlassProcessor` |
| `emi_loot_config.toml` | `logUntranslatedTables.chest` | `False` | bool |  | `ancient_artifacts:ArtifactSlot`、`artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
| `emi_loot_config.toml` | `logUntranslatedTables.gameplay` | `False` | bool |  | `bookshelf:DebugCommands`、`emi_loot:AbstractTextKeyParsingClientLootTable`、`emi_loot:ClientLootTables` |
| `emi_loot_config.toml` | `logUntranslatedTables.archaeology` | `False` | bool |  | `artifacts:ConfigValueChance`、`artifacts:LootModifiers`、`betterdeserttemples:OrangeStainedGlassProcessor` |
| `emi_loot_config.toml` | `directDrops.chestLoot` | `True` | bool |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `directDrops.blockLoot` | `True` | bool |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `directDrops.mobLoot` | `True` | bool |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `directDrops.gameplayLoot` | `True` | bool |  | `emi_loot:EMILoot` |
| `emi_loot_config.toml` | `directDrops.archaeologyLoot` | `True` | bool |  | `emi_loot:EMILoot` |
