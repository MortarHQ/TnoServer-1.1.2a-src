---
页面: configs/betterstrongholds
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `betterstrongholds` 配置项

> **6 个文件 / 74 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/betterstrongholds](../../mods/betterstrongholds/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `betterstrongholds-neoforge-1_21.toml` | `YUNG's Better Strongholds.General.Enable Structure Ruin` | `False` | bool |  | — |
| `betterstrongholds-neoforge-1_21.toml` | `YUNG's Better Strongholds.General.Filled Portal Frame Chance` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:iron_sword` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:stone_axe` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:golden_axe` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:bow` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:shield` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:name_tag` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:arrow` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:iron_axe` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:golden_sword` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.entries.minecraft:stone_sword` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `armouryItems.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:flint` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:cake` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:slime_ball` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:compass` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:melon_seeds` | `0.025` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:beetroot_seeds` | `0.025` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:lead` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:paper` | `0.25` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:map` | `0.25` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:pumpkin_seeds` | `0.025` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:rabbit_foot` | `0.01` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.entries.minecraft:wheat_seeds` | `0.025` | float |  | — |
| `betterstrongholds/neoforge-1_21/itemframes.json` | `storageItems.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/README.txt` | `- entries` | `An object where each entry's key is a bl` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `- defaultBlock` | `The blockstate used for any leftover pro` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `Here's an example block selector` | `` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `"entries"` | `{` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `"minecraft` | `cobblestone": 0.25,` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `"minecraft` | `air": 0.2,` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `"minecraft` | `stone_bricks": 0.1` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `"defaultBlock"` | `"minecraft:oak_planks"` | string |  | — |
| `betterstrongholds/neoforge-1_21/README.txt` | `10% chance of choosing stone bricks, and a 100 - (25 + 20 + 10)` | `45% chance of choosing oak planks (since` | string |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonHelmets.entries.minecraft:leather_helmet` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonHelmets.entries.minecraft:iron_helmet` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonHelmets.entries.minecraft:carved_pumpkin` | `0.01` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonHelmets.entries.minecraft:chainmail_helmet` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonHelmets.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareHelmets.entries.minecraft:carved_pumpkin` | `0.2` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareHelmets.entries.minecraft:diamond_helmet` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareHelmets.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonChestplates.entries.minecraft:iron_chestplate` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonChestplates.entries.minecraft:chainmail_chestplate` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonChestplates.entries.minecraft:leather_chestplate` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonChestplates.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareChestplates.entries.minecraft:diamond_chestplate` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareChestplates.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonLeggings.entries.minecraft:chainmail_leggings` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonLeggings.entries.minecraft:leather_leggings` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonLeggings.entries.minecraft:iron_leggings` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonLeggings.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareLeggings.entries.minecraft:diamond_leggings` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareLeggings.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonBoots.entries.minecraft:leather_boots` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonBoots.entries.minecraft:iron_boots` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonBoots.entries.minecraft:chainmail_boots` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `commonBoots.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareBoots.entries.minecraft:diamond_boots` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/armorstands.json` | `rareBoots.defaultItem` | `minecraft:air` | str |  | `betterdeserttemples:ConfigModuleNeoForge`、`betterfortresses:ConfigModuleNeoForge`、`yet_another_config_lib_v3:ItemRegistryHelper` |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:lapis_ore` | `0.15` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:iron_ore` | `0.2` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:emerald_ore` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:redstone_ore[lit=false]` | `0.15` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:coal_ore` | `0.2` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:gold_ore` | `0.2` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.entries.minecraft:diamond_ore` | `0.05` | float |  | — |
| `betterstrongholds/neoforge-1_21/ores.json` | `oreChances.defaultBlock` | `minecraft:coal_ore` | str |  | `betterstrongholds:ConfigModuleNeoForge`、`eternal_starlight:ESChunkGenerator`、`lithostitched:RereferenceNoiseSettingsModifier` |
| `betterstrongholds/neoforge-1_21/rareblocks.json` | `blockChances.entries.minecraft:quartz_block` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/rareblocks.json` | `blockChances.entries.minecraft:gold_block` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/rareblocks.json` | `blockChances.entries.minecraft:iron_block` | `0.3` | float |  | — |
| `betterstrongholds/neoforge-1_21/rareblocks.json` | `blockChances.entries.minecraft:diamond_block` | `0.1` | float |  | — |
| `betterstrongholds/neoforge-1_21/rareblocks.json` | `blockChances.defaultBlock` | `minecraft:iron_block` | str |  | `betterstrongholds:ConfigModuleNeoForge`、`eternal_starlight:ESChunkGenerator`、`lithostitched:RereferenceNoiseSettingsModifier` |
