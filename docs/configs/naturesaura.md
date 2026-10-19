---
页面: configs/naturesaura
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `naturesaura` 配置项

> **1 个文件 / 31 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/naturesaura](../../mods/naturesaura/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `naturesaura-common.toml` | `general.additionalBotanistPickaxeConversions` | `[]` | list | Additional conversion recipes for the Botanist's Pickaxe rig | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.auraTypeOverrides` | `[]` | list | Additional dimensions that map to Aura types that should be  | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.additionalOres` | `[]` | list | Additional blocks that are recognized as generatable ores fo | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.oreExceptions` | `[]` | list | Blocks that are exempt from being recognized as generatable  | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.plantBoostExceptions` | `[]` | list | Blocks that are exept from being fertilized by the plant boo | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.additionalProjectiles` | `[]` | list | Additional projectile types that are allowed to be consumed  | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.fieldCreatorRange` | `24` | int | The amount of blocks that can be between two Aura Field Crea | `naturesaura:BlockEntityFieldCreator`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.auraToRFRatio` | `0.05` | float | The Aura to RF ratio used by the RF converter, read as aura* | `naturesaura:BlockEntityRFConverter`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.maxAnimalsAroundPowder` | `200` | int | The maximum amount of animals that can be around the powder  | `naturesaura:AnimalEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `general.maxAuraSpreadRange` | `150` | int | The maximum amount of blocks that aura can spread from an in | `naturesaura:AuraChunk`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.rfConverter` | `True` | bool | If the RF converter block should be enabled | `naturesaura:BlockEntityRFConverter`、`naturesaura:BlockRFConverter`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.chunkLoader` | `True` | bool | If the chunk loader block should be enabled | `naturesaura:BlockChunkLoader`、`naturesaura:BlockEntityChunkLoader`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.grassDieEffect` | `True` | bool | If the Aura Imbalance effect of grass and trees dying in the | `naturesaura:GrassDieEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.netherDecayEffect` | `True` | bool | If the Aura Imbalance effect of nether blocks degrading in t | `naturesaura:ModConfig`、`naturesaura:NetherDecayEffect` |
| `naturesaura-common.toml` | `features.plantBoostEffect` | `True` | bool | If the Aura Imbalance effect of plant growth being boosted i | `naturesaura:ModConfig`、`naturesaura:PlantBoostEffect` |
| `naturesaura-common.toml` | `features.cacheRechargeEffect` | `True` | bool | If the Aura Imbalance effect of aura containers in players'  | `naturesaura:CacheRechargeEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.explosionEffect` | `True` | bool | If the Aura Imbalance effect of explosions happening randoml | `naturesaura:ExplosionEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.breathlessEffect` | `True` | bool | If the Aura Imbalance effect of breathlessness if Aura level | `naturesaura:BreathlessEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.angerEffect` | `True` | bool | If the Aura Imbalance effect of passive mobs being angered i | `naturesaura:AngerEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.animalEffect` | `True` | bool | If the Aura Imbalance effect of farm animals being affected  | `naturesaura:AnimalEffect`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.oreEffect` | `True` | bool | If the Aura Imbalance effect of ores spawning in the area if | `naturesaura:ModConfig`、`naturesaura:OreSpawnEffect` |
| `naturesaura-common.toml` | `features.auraBlooms` | `True` | bool | If Aura Blooms and Aura Cacti should generate in the level | `naturesaura:ModConfig` |
| `naturesaura-common.toml` | `features.netherGrassEffect` | `True` | bool | If the Aura Imbalance effect of grass growing on netherrack  | `naturesaura:ModConfig`、`naturesaura:NetherGrassEffect` |
| `naturesaura-common.toml` | `client.particleAmount` | `1.0` | float | Range: 0.0 ~ 1.0 | `bosses_of_mass_destruction:VoidLilyBlockEntity`、`friendsandfoes:WildfireShockwaveAttackTask`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `client.respectVanillaParticleSettings` | `False` | bool | If particle spawning should respect the particle setting in  | `naturesaura:ModConfig`、`naturesaura:ParticleHandler` |
| `naturesaura-common.toml` | `client.excessParticleAmount` | `1.0` | float | The percentage of particles that should spawn when there is  | `naturesaura:ClientEvents`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `client.auraBarLocation` | `1` | int | Range: 0 ~ 3 | `naturesaura:ClientEvents`、`naturesaura:ModConfig`、`tensura_naturesaura:ClientEventsMixin` |
| `naturesaura-common.toml` | `client.cacheBarLocation` | `0` | int | Range: 0 ~ 1 | `naturesaura:ClientEvents`、`naturesaura:ModConfig`、`tensura_naturesaura:ClientEventsMixin` |
| `naturesaura-common.toml` | `client.debugText` | `True` | bool | If debug information about Aura around the player should be  | `naturesaura:ClientEvents`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `client.debugLevel` | `False` | bool | If, when the F3 debug menu is open and the player is in crea | `naturesaura:ClientEvents`、`naturesaura:ModConfig` |
| `naturesaura-common.toml` | `client.renderItemsOnPlayer` | `True` | bool | If certain equippable items, like the Environmental Eye, sho | `naturesaura:ModConfig`、`naturesaura:PlayerLayerTrinkets` |
