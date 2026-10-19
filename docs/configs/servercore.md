---
页面: configs/servercore
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `servercore` 配置项

> **2 个文件 / 191 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/servercore](../../mods/servercore/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `servercore/config.yml` | `features.prevent-moving-into-unloaded-chunks` | `false` | string |  | — |
| `servercore/config.yml` | `features.autosave-interval-seconds` | `300` | string |  | — |
| `servercore/config.yml` | `features.xp-merge-fraction` | `40` | string |  | — |
| `servercore/config.yml` | `features.xp-merge-radius` | `0.5` | string |  | — |
| `servercore/config.yml` | `features.item-merge-radius` | `0.5` | string |  | — |
| `servercore/config.yml` | `features.lobotomize-villagers.enabled` | `false` | string |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `servercore/config.yml` | `features.lobotomize-villagers.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `dynamic.enabled` | `false` | string |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `servercore/config.yml` | `dynamic.target-mspt` | `35` | string |  | — |
| `servercore/config.yml` | `dynamic.default-values.CHUNK_TICK_DISTANCE` | `10` | string |  | `servercore:ChunkMapMixin`、`servercore:DynamicConfig`、`servercore:DynamicSetting` |
| `servercore/config.yml` | `dynamic.default-values.MOBCAP_PERCENTAGE` | `100` | string |  | `servercore:Config`、`servercore:DynamicConfig`、`servercore:DynamicSetting` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'CHUNK_TICK_DISTANCE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `10` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `6` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `1` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'MOBCAP_PERCENTAGE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `100` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `50` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `10` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'SIMULATION_DISTANCE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `10` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `6` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `1` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'CHUNK_TICK_DISTANCE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `6` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `2` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `1` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'MOBCAP_PERCENTAGE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `50` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `30` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `10` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'SIMULATION_DISTANCE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `6` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `5` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `1` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `15` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `dynamic.dynamic-settings.- setting` | `'VIEW_DISTANCE'` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.max` | `10` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.min` | `5` | string |  | — |
| `servercore/config.yml` | `dynamic.dynamic-settings.increment` | `1` | string |  | `byepregen:ServerChunkCacheTickChunksMixin`、`cloth_config:ScannerImpl`、`crafttweaker:JavaPrepareClassMethodVisitor` |
| `servercore/config.yml` | `dynamic.dynamic-settings.interval` | `150` | string |  | `artifacts:WarpDriveRenderer`、`cataclysm:AnimalAIRandomSwimming`、`cataclysm:SemiAquaticAIRandomSwimming` |
| `servercore/config.yml` | `breeding-cap.enabled` | `false` | string |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `servercore/config.yml` | `breeding-cap.villagers.limit` | `32` | string |  | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `servercore/config.yml` | `breeding-cap.villagers.range` | `64` | string |  | `ancient_artifacts:RoyalBeastSkillMixin`、`artifacts:MagnetismMobEffect`、`artifacts:MimicEntity` |
| `servercore/config.yml` | `breeding-cap.villagers.unlimited-height` | `false` | string |  | — |
| `servercore/config.yml` | `breeding-cap.animals.limit` | `32` | string |  | `balm:ContainerUtils`、`cataclysm:IABoss_monster`、`cataclysm:LLibrary_Boss_Monster` |
| `servercore/config.yml` | `breeding-cap.animals.range` | `64` | string |  | `ancient_artifacts:RoyalBeastSkillMixin`、`artifacts:MagnetismMobEffect`、`artifacts:MimicEntity` |
| `servercore/config.yml` | `breeding-cap.animals.unlimited-height` | `false` | string |  | — |
| `servercore/config.yml` | `mob-spawning.zombie-reinforcements.enforce-mobcap` | `false` | string |  | — |
| `servercore/config.yml` | `mob-spawning.zombie-reinforcements.additional-capacity` | `32` | string |  | — |
| `servercore/config.yml` | `mob-spawning.nether-portal-randomticks.enforce-mobcap` | `false` | string |  | — |
| `servercore/config.yml` | `mob-spawning.nether-portal-randomticks.additional-capacity` | `32` | string |  | — |
| `servercore/config.yml` | `mob-spawning.monster-spawners.enforce-mobcap` | `false` | string |  | — |
| `servercore/config.yml` | `mob-spawning.monster-spawners.additional-capacity` | `32` | string |  | — |
| `servercore/config.yml` | `mob-spawning.infested.enforce-mobcap` | `false` | string |  | — |
| `servercore/config.yml` | `mob-spawning.infested.additional-capacity` | `32` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'MONSTER'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `70` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'CREATURE'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `10` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `400` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'AMBIENT'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `15` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'AXOLOTLS'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `5` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'UNDERGROUND_WATER_CREATURE'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `5` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'WATER_CREATURE'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `5` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'WATER_AMBIENT'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `20` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `64` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'GLARES'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `15` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.- category` | `'RASCALS'` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.mobcap` | `1` | string |  | `servercore:EnforcedMobcap`、`servercore:MainConfig`、`servercore:MobSpawnConfig` |
| `servercore/config.yml` | `mob-spawning.categories.spawn-interval` | `1` | string |  | — |
| `servercore/config.yml` | `mob-spawning.categories.despawn-distance` | `128` | string |  | — |
| `servercore/config.yml` | `commands.status-enabled` | `true` | string |  | — |
| `servercore/config.yml` | `commands.mobcaps-enabled` | `true` | string |  | — |
| `servercore/config.yml` | `commands.colors.primary` | `'dark_aqua'` | string |  | `crafttweaker:ExpandBasicItemListing`、`foxablazeultimate:ShubNiggurathWarehouse`、`jade:ClientRegistrationSession` |
| `servercore/config.yml` | `commands.colors.secondary` | `'green'` | string |  | `cloth_config:Tag`、`crafttweaker:ExpandBasicItemListing`、`creativecore:GuiStyle` |
| `servercore/config.yml` | `commands.colors.tertiary` | `'aqua'` | string |  | `netherexp:JNEConfigImpl`、`servercore:CommandConfig`、`servercore:Formatter` |
| `servercore/config.yml` | `activation-range.enabled` | `false` | string |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `servercore/config.yml` | `activation-range.tick-new-entities` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.use-vertical-range` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.skip-non-immune` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.villager-tick-panic` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.villager-work-immunity-after` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.villager-work-immunity-for` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.excluded-entity-types.- 'minecraft` | `warden'` | string |  | — |
| `servercore/config.yml` | `activation-range.excluded-entity-types.- 'minecraft` | `hopper_minecart'` | string |  | — |
| `servercore/config.yml` | `activation-range.excluded-entity-types.- 'minecraft` | `ghast'` | string |  | — |
| `servercore/config.yml` | `activation-range.default-activation-type.activation-range` | `16` | string |  | — |
| `servercore/config.yml` | `activation-range.default-activation-type.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.default-activation-type.wakeup-interval` | `-1` | string |  | — |
| `servercore/config.yml` | `activation-range.default-activation-type.extra-height-up` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.default-activation-type.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'raider'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `48` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `raider'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'water'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `16` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `60` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `water_animal'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'villager'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `16` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `30` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `villager'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'zombie'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `16` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `zombie'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `husk'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'monster-below'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `32` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `creeper'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `slime'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `magma_cube'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `hoglin'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'flying-monster'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `48` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `ghast'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'minecraft` | `phantom'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'monster'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `32` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `true` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `monster'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'animal'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `16` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `60` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `animal'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `ambient'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.- name` | `'creature'` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.activation-range` | `24` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.tick-interval` | `20` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.wakeup-interval` | `30` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-up` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.extra-height-down` | `false` | string |  | — |
| `servercore/config.yml` | `activation-range.custom-activation-types.entity-matcher.- 'typeof` | `mob'` | string |  | — |
| `servercore/optimizations.yml` | `reduce-sync-loads` | `true` | string |  | — |
| `servercore/optimizations.yml` | `cache-ticking-chunks` | `true` | string |  | — |
| `servercore/optimizations.yml` | `optimize-command-blocks` | `false` | string |  | — |
| `servercore/optimizations.yml` | `fast-biome-lookups` | `false` | string |  | — |
| `servercore/optimizations.yml` | `cancel-duplicate-fluid-ticks` | `false` | string |  | — |
