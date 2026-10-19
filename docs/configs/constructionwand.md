---
页面: configs/constructionwand
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `constructionwand` 配置项

> **2 个文件 / 34 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/constructionwand](../../mods/constructionwand/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `constructionwand-server.toml` | `stone_wand.durability` | `131` | int | Range: > 1 | `block_factorys_bosses:BossesRiseItems`、`block_factorys_bosses:DragonBonesItem`、`block_factorys_bosses:DragonGuardShieldItem` |
| `constructionwand-server.toml` | `stone_wand.limit` | `9` | int | Range: > 1 | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `constructionwand-server.toml` | `stone_wand.angel` | `0` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`mysticism:AngelConfig` |
| `constructionwand-server.toml` | `stone_wand.destruction` | `0` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`lootr:ConfigManager` |
| `constructionwand-server.toml` | `stone_wand.upgradeable` | `False` | bool | Allow wand upgrading by putting the wand together with a wan | `constructionwand:ConfigServer`、`relics:RelicExperienceOrbEntity` |
| `constructionwand-server.toml` | `iron_wand.durability` | `250` | int | Range: > 1 | `block_factorys_bosses:BossesRiseItems`、`block_factorys_bosses:DragonBonesItem`、`block_factorys_bosses:DragonGuardShieldItem` |
| `constructionwand-server.toml` | `iron_wand.limit` | `27` | int | Range: > 1 | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `constructionwand-server.toml` | `iron_wand.angel` | `2` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`mysticism:AngelConfig` |
| `constructionwand-server.toml` | `iron_wand.destruction` | `9` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`lootr:ConfigManager` |
| `constructionwand-server.toml` | `iron_wand.upgradeable` | `True` | bool | Allow wand upgrading by putting the wand together with a wan | `constructionwand:ConfigServer`、`relics:RelicExperienceOrbEntity` |
| `constructionwand-server.toml` | `diamond_wand.durability` | `1561` | int | Range: > 1 | `block_factorys_bosses:BossesRiseItems`、`block_factorys_bosses:DragonBonesItem`、`block_factorys_bosses:DragonGuardShieldItem` |
| `constructionwand-server.toml` | `diamond_wand.limit` | `128` | int | Range: > 1 | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `constructionwand-server.toml` | `diamond_wand.angel` | `8` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`mysticism:AngelConfig` |
| `constructionwand-server.toml` | `diamond_wand.destruction` | `25` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`lootr:ConfigManager` |
| `constructionwand-server.toml` | `diamond_wand.upgradeable` | `True` | bool | Allow wand upgrading by putting the wand together with a wan | `constructionwand:ConfigServer`、`relics:RelicExperienceOrbEntity` |
| `constructionwand-server.toml` | `netherite_wand.durability` | `2031` | int | Range: > 1 | `block_factorys_bosses:BossesRiseItems`、`block_factorys_bosses:DragonBonesItem`、`block_factorys_bosses:DragonGuardShieldItem` |
| `constructionwand-server.toml` | `netherite_wand.limit` | `512` | int | Range: > 1 | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `constructionwand-server.toml` | `netherite_wand.angel` | `15` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`mysticism:AngelConfig` |
| `constructionwand-server.toml` | `netherite_wand.destruction` | `75` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`lootr:ConfigManager` |
| `constructionwand-server.toml` | `netherite_wand.upgradeable` | `True` | bool | Allow wand upgrading by putting the wand together with a wan | `constructionwand:ConfigServer`、`relics:RelicExperienceOrbEntity` |
| `constructionwand-server.toml` | `infinity_wand.limit` | `1024` | int | Range: > 1 | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `constructionwand-server.toml` | `infinity_wand.angel` | `16` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`mysticism:AngelConfig` |
| `constructionwand-server.toml` | `infinity_wand.destruction` | `81` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:LanguageGenerator`、`lootr:ConfigManager` |
| `constructionwand-server.toml` | `infinity_wand.upgradeable` | `True` | bool | Allow wand upgrading by putting the wand together with a wan | `constructionwand:ConfigServer`、`relics:RelicExperienceOrbEntity` |
| `constructionwand-server.toml` | `misc.InfinityWandCreative` | `2048` | int | Range: > 1 | `constructionwand:ConfigServer` |
| `constructionwand-server.toml` | `misc.MaxRange` | `100` | int | Range: > 0 | `constructionwand:ConfigServer` |
| `constructionwand-server.toml` | `misc.UndoHistory` | `3` | int | Range: > 0 | `constructionwand:ConfigServer`、`constructionwand:ConstructionWand`、`constructionwand:UndoHistory` |
| `constructionwand-server.toml` | `misc.AngelFalling` | `False` | bool | Place blocks below you while falling > 10 blocks with angel  | `constructionwand:ConfigServer` |
| `constructionwand-server.toml` | `misc.SimilarBlocks` | `["minecraft:dirt;minecraft:grass_block;m` | list | Blocks to treat equally when in Similar mode. Enter block ID | `constructionwand:ConfigServer` |
| `constructionwand-server.toml` | `tileentity.TEList` | `["chiselsandbits"]` | list | You can either add block ids like minecraft:chest or mod ids | `constructionwand:ConfigServer` |
| `constructionwand-server.toml` | `tileentity.TEWhitelist` | `False` | bool | If set to TRUE, treat TEList as a whitelist, otherwise black | `constructionwand:ConfigServer` |
| `constructionwand-client.toml` | `keys.OptKey` | `341` | int | Range: 0 ~ 350 | `constructionwand:ConfigClient` |
| `constructionwand-client.toml` | `keys.ShiftOpt` | `False` | bool | Press SNEAK+OPTKEY instead of SNEAK for changing wand mode/d | `constructionwand:ConfigClient` |
| `constructionwand-client.toml` | `keys.ShiftOptGUI` | `True` | bool | Press SNEAK+OPTKEY instead of SNEAK for opening wand GUI | `constructionwand:ConfigClient` |
