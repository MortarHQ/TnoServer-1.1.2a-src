# Mythika: Creatures of Greek Myth（`mythika`）

> jar：`[希腊生物]mythika.jar` ｜ 版本：`1.2.1` ｜ 作者：Thrasos
> 规模：**57 个类 / 11 个包 / 6071 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 9

**依赖**：`neoforge`、`minecraft`、`geckolib`、`cloth_config`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.pixeldreamstudios.mythika.entity` | 21 | `SatyrEntity`、`CyclopsEntity`、`MinotaurEntity` |
| `net.pixeldreamstudios.mythika.worldgen` | 10 | `LabyrinthLayout`、`CyclopsCaveLayout`、`CyclopsCavePiece` |
| `net.pixeldreamstudios.mythika.registry` | 8 | `ItemRegistry`、`EntityRegistry`、`StructureRegistry` |
| `net.pixeldreamstudios.mythika.client` | 7 | `ScreenShake`、`CyclopsRenderer`、`MinotaurRenderer` |
| `net.pixeldreamstudios.mythika` | 2 | `MythikaConfig`、`Mythika` |
| `net.pixeldreamstudios.mythika.neoforge` | 2 | `MythikaNeoForge`、`ConfigSpawnBiomeModifier` |
| `net.pixeldreamstudios.mythika.neoforge.platform` | 2 | `NeoForgeRegistryHelper`、`NeoForgePlatformHelper` |
| `net.pixeldreamstudios.mythika.platform.services` | 2 | `IRegistryHelper`、`IPlatformHelper` |
| `net.pixeldreamstudios.mythika.mixin.client` | 1 | `CameraMixin` |
| `net.pixeldreamstudios.mythika.neoforge.client` | 1 | `MythikaNeoForgeClient` |
| `net.pixeldreamstudios.mythika.platform` | 1 | `Services` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 57）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MythikaConfig` | class | ConfigData | Config | 2 | 94 | [MythikaConfig.java](net/pixeldreamstudios/mythika/MythikaConfig.java) |
| `SatyrEntity` | class | PathfinderMob | — | 50 | 543 | [SatyrEntity.java](net/pixeldreamstudios/mythika/entity/SatyrEntity.java) |
| `MythikaNeoForge` | class | — | Mod | 3 | 59 | [MythikaNeoForge.java](net/pixeldreamstudios/mythika/neoforge/MythikaNeoForge.java) |
| `CyclopsEntity` | class | Monster | — | 32 | 347 | [CyclopsEntity.java](net/pixeldreamstudios/mythika/entity/CyclopsEntity.java) |
| `MinotaurEntity` | class | Monster | — | 46 | 386 | [MinotaurEntity.java](net/pixeldreamstudios/mythika/entity/MinotaurEntity.java) |
| `LabyrinthLayout` | class | — | — | 33 | 404 | [LabyrinthLayout.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthLayout.java) |
| `ItemRegistry` | class | — | — | 2 | 32 | [ItemRegistry.java](net/pixeldreamstudios/mythika/registry/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 3 | 70 | [EntityRegistry.java](net/pixeldreamstudios/mythika/registry/EntityRegistry.java) |
| `NeoForgeRegistryHelper` | class | IRegistryHelper | — | 3 | 44 | [NeoForgeRegistryHelper.java](net/pixeldreamstudios/mythika/neoforge/platform/NeoForgeRegistryHelper.java) |
| `CyclopsCaveLayout` | class | — | — | 41 | 304 | [CyclopsCaveLayout.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCaveLayout.java) |
| `IRegistryHelper` | interface | — | — | 0 | 15 | [IRegistryHelper.java](net/pixeldreamstudios/mythika/platform/services/IRegistryHelper.java) |
| `CyclopsCavePiece` | class | StructurePiece | — | 23 | 471 | [CyclopsCavePiece.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCavePiece.java) |
| `CameraMixin` | class | — | Mixin | 1 | 33 | [CameraMixin.java](net/pixeldreamstudios/mythika/mixin/client/CameraMixin.java) |
| `StructureRegistry` | class | — | — | 1 | 33 | [StructureRegistry.java](net/pixeldreamstudios/mythika/registry/StructureRegistry.java) |
| `TabRegistry` | class | — | — | 1 | 29 | [TabRegistry.java](net/pixeldreamstudios/mythika/registry/TabRegistry.java) |
| `TagRegistry` | class | — | — | 0 | 16 | [TagRegistry.java](net/pixeldreamstudios/mythika/registry/TagRegistry.java) |
| `RegistrySupplier` | class | Supplier<T> | — | 4 | 37 | [RegistrySupplier.java](net/pixeldreamstudios/mythika/registry/RegistrySupplier.java) |
| `SpawnRegistry` | class | — | — | 1 | 34 | [SpawnRegistry.java](net/pixeldreamstudios/mythika/registry/SpawnRegistry.java) |
| `MinotaurChargeGoal` | class | Goal | — | 19 | 300 | [MinotaurChargeGoal.java](net/pixeldreamstudios/mythika/entity/MinotaurChargeGoal.java) |
| `LightCampfireGoal` | class | Goal | — | 17 | 287 | [LightCampfireGoal.java](net/pixeldreamstudios/mythika/entity/LightCampfireGoal.java) |
| `ConfigSpawnBiomeModifier` | record | BiomeModifier | — | 2 | 29 | [ConfigSpawnBiomeModifier.java](net/pixeldreamstudios/mythika/neoforge/ConfigSpawnBiomeModifier.java) |
| `PipeAtCampfireGoal` | class | Goal | — | 17 | 195 | [PipeAtCampfireGoal.java](net/pixeldreamstudios/mythika/entity/PipeAtCampfireGoal.java) |
| `LabyrinthEntrancePiece` | class | StructurePiece | — | 11 | 260 | [LabyrinthEntrancePiece.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthEntrancePiece.java) |
| `SatyrTunes` | class | — | — | 9 | 224 | [SatyrTunes.java](net/pixeldreamstudios/mythika/entity/SatyrTunes.java) |
| `MythikaNeoForgeClient` | class | — | — | 2 | 32 | [MythikaNeoForgeClient.java](net/pixeldreamstudios/mythika/neoforge/client/MythikaNeoForgeClient.java) |
| `CaveBlocks` | class | — | — | 0 | 72 | [CaveBlocks.java](net/pixeldreamstudios/mythika/worldgen/CaveBlocks.java) |
| `LabyrinthPiece` | class | StructurePiece | — | 7 | 245 | [LabyrinthPiece.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthPiece.java) |
| `LabyrinthBlocks` | class | — | — | 0 | 57 | [LabyrinthBlocks.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthBlocks.java) |
| `ScreenShake` | class | — | — | 2 | 46 | [ScreenShake.java](net/pixeldreamstudios/mythika/client/ScreenShake.java) |
| `CyclopsRenderer` | class | GeoEntityRenderer | — | 0 | 13 | [CyclopsRenderer.java](net/pixeldreamstudios/mythika/client/CyclopsRenderer.java) |
| `MinotaurRenderer` | class | GeoEntityRenderer | — | 0 | 13 | [MinotaurRenderer.java](net/pixeldreamstudios/mythika/client/MinotaurRenderer.java) |
| `SatyrRenderer` | class | GeoEntityRenderer | — | 1 | 51 | [SatyrRenderer.java](net/pixeldreamstudios/mythika/client/SatyrRenderer.java) |
| `ReturnToLairGoal` | class | Goal | — | 6 | 75 | [ReturnToLairGoal.java](net/pixeldreamstudios/mythika/entity/ReturnToLairGoal.java) |
| `MenacingChorusGoal` | class | Goal | — | 8 | 77 | [MenacingChorusGoal.java](net/pixeldreamstudios/mythika/entity/MenacingChorusGoal.java) |
| `RetreatIntoLabyrinthGoal` | class | Goal | — | 8 | 99 | [RetreatIntoLabyrinthGoal.java](net/pixeldreamstudios/mythika/entity/RetreatIntoLabyrinthGoal.java) |
| `FollowFireBuilderGoal` | class | Goal | — | 5 | 81 | [FollowFireBuilderGoal.java](net/pixeldreamstudios/mythika/entity/FollowFireBuilderGoal.java) |
| `ReturnToCampfireGoal` | class | Goal | — | 6 | 76 | [ReturnToCampfireGoal.java](net/pixeldreamstudios/mythika/entity/ReturnToCampfireGoal.java) |
| `AttackTimeline` | class | — | — | 7 | 62 | [AttackTimeline.java](net/pixeldreamstudios/mythika/entity/AttackTimeline.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 4 | 30 | [NeoForgePlatformHelper.java](net/pixeldreamstudios/mythika/neoforge/platform/NeoForgePlatformHelper.java) |
| `LabyrinthStructure` | class | Structure | — | 6 | 130 | [LabyrinthStructure.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthStructure.java) |
| `CyclopsCaveStructure` | class | Structure | — | 5 | 135 | [CyclopsCaveStructure.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCaveStructure.java) |
| `IPlatformHelper` | interface | — | — | 0 | 14 | [IPlatformHelper.java](net/pixeldreamstudios/mythika/platform/services/IPlatformHelper.java) |
| `DeferredRegister` | class | — | — | 6 | 56 | [DeferredRegister.java](net/pixeldreamstudios/mythika/registry/DeferredRegister.java) |
| `Mythika` | class | — | — | 2 | 42 | [Mythika.java](net/pixeldreamstudios/mythika/Mythika.java) |
| `MinotaurNavigation` | class | GroundPathNavigation | — | 3 | 31 | [MinotaurNavigation.java](net/pixeldreamstudios/mythika/entity/MinotaurNavigation.java) |
| `TimedMeleeAttackGoal` | class | MeleeAttackGoal | — | 2 | 37 | [TimedMeleeAttackGoal.java](net/pixeldreamstudios/mythika/entity/TimedMeleeAttackGoal.java) |
| `TimedAttacker` | interface | — | — | 0 | 12 | [TimedAttacker.java](net/pixeldreamstudios/mythika/entity/TimedAttacker.java) |
| `CyclopsNavigation` | class | GroundPathNavigation | — | 2 | 35 | [CyclopsNavigation.java](net/pixeldreamstudios/mythika/entity/CyclopsNavigation.java) |
| `GaitTracker` | class | — | — | 4 | 46 | [GaitTracker.java](net/pixeldreamstudios/mythika/entity/GaitTracker.java) |
| `ChargeInTurnGoal` | class | TimedMeleeAttackGoal | — | 2 | 20 | [ChargeInTurnGoal.java](net/pixeldreamstudios/mythika/entity/ChargeInTurnGoal.java) |
| `Footfalls` | class | — | — | 2 | 45 | [Footfalls.java](net/pixeldreamstudios/mythika/entity/Footfalls.java) |
| `GaitAnimationController` | class | AnimationController | — | 1 | 35 | [GaitAnimationController.java](net/pixeldreamstudios/mythika/entity/GaitAnimationController.java) |
| `Variance` | class | — | — | 0 | 15 | [Variance.java](net/pixeldreamstudios/mythika/worldgen/Variance.java) |
| `Services` | class | — | — | 1 | 18 | [Services.java](net/pixeldreamstudios/mythika/platform/Services.java) |
| `MinotaurModel` | class | GeoModel | — | 4 | 40 | [MinotaurModel.java](net/pixeldreamstudios/mythika/client/MinotaurModel.java) |
| `SatyrModel` | class | GeoModel | — | 4 | 45 | [SatyrModel.java](net/pixeldreamstudios/mythika/client/SatyrModel.java) |
| `CyclopsModel` | class | GeoModel | — | 4 | 40 | [CyclopsModel.java](net/pixeldreamstudios/mythika/client/CyclopsModel.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `CameraMixin` | `Camera` | [CameraMixin.java](net/pixeldreamstudios/mythika/mixin/client/CameraMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `MythikaConfig` | Config | [MythikaConfig.java](net/pixeldreamstudios/mythika/MythikaConfig.java) |
| `ConfigSpawnBiomeModifier` | — | [ConfigSpawnBiomeModifier.java](net/pixeldreamstudios/mythika/neoforge/ConfigSpawnBiomeModifier.java) |

## 全部类（57）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MythikaConfig` | class | ConfigData | Config | 2 | 94 | [MythikaConfig.java](net/pixeldreamstudios/mythika/MythikaConfig.java) |
| `SatyrEntity` | class | PathfinderMob | — | 50 | 543 | [SatyrEntity.java](net/pixeldreamstudios/mythika/entity/SatyrEntity.java) |
| `MythikaNeoForge` | class | — | Mod | 3 | 59 | [MythikaNeoForge.java](net/pixeldreamstudios/mythika/neoforge/MythikaNeoForge.java) |
| `CyclopsEntity` | class | Monster | — | 32 | 347 | [CyclopsEntity.java](net/pixeldreamstudios/mythika/entity/CyclopsEntity.java) |
| `MinotaurEntity` | class | Monster | — | 46 | 386 | [MinotaurEntity.java](net/pixeldreamstudios/mythika/entity/MinotaurEntity.java) |
| `LabyrinthLayout` | class | — | — | 33 | 404 | [LabyrinthLayout.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthLayout.java) |
| `ItemRegistry` | class | — | — | 2 | 32 | [ItemRegistry.java](net/pixeldreamstudios/mythika/registry/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 3 | 70 | [EntityRegistry.java](net/pixeldreamstudios/mythika/registry/EntityRegistry.java) |
| `NeoForgeRegistryHelper` | class | IRegistryHelper | — | 3 | 44 | [NeoForgeRegistryHelper.java](net/pixeldreamstudios/mythika/neoforge/platform/NeoForgeRegistryHelper.java) |
| `CyclopsCaveLayout` | class | — | — | 41 | 304 | [CyclopsCaveLayout.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCaveLayout.java) |
| `IRegistryHelper` | interface | — | — | 0 | 15 | [IRegistryHelper.java](net/pixeldreamstudios/mythika/platform/services/IRegistryHelper.java) |
| `CyclopsCavePiece` | class | StructurePiece | — | 23 | 471 | [CyclopsCavePiece.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCavePiece.java) |
| `CameraMixin` | class | — | Mixin | 1 | 33 | [CameraMixin.java](net/pixeldreamstudios/mythika/mixin/client/CameraMixin.java) |
| `StructureRegistry` | class | — | — | 1 | 33 | [StructureRegistry.java](net/pixeldreamstudios/mythika/registry/StructureRegistry.java) |
| `TabRegistry` | class | — | — | 1 | 29 | [TabRegistry.java](net/pixeldreamstudios/mythika/registry/TabRegistry.java) |
| `TagRegistry` | class | — | — | 0 | 16 | [TagRegistry.java](net/pixeldreamstudios/mythika/registry/TagRegistry.java) |
| `RegistrySupplier` | class | Supplier<T> | — | 4 | 37 | [RegistrySupplier.java](net/pixeldreamstudios/mythika/registry/RegistrySupplier.java) |
| `SpawnRegistry` | class | — | — | 1 | 34 | [SpawnRegistry.java](net/pixeldreamstudios/mythika/registry/SpawnRegistry.java) |
| `MinotaurChargeGoal` | class | Goal | — | 19 | 300 | [MinotaurChargeGoal.java](net/pixeldreamstudios/mythika/entity/MinotaurChargeGoal.java) |
| `LightCampfireGoal` | class | Goal | — | 17 | 287 | [LightCampfireGoal.java](net/pixeldreamstudios/mythika/entity/LightCampfireGoal.java) |
| `ConfigSpawnBiomeModifier` | record | BiomeModifier | — | 2 | 29 | [ConfigSpawnBiomeModifier.java](net/pixeldreamstudios/mythika/neoforge/ConfigSpawnBiomeModifier.java) |
| `PipeAtCampfireGoal` | class | Goal | — | 17 | 195 | [PipeAtCampfireGoal.java](net/pixeldreamstudios/mythika/entity/PipeAtCampfireGoal.java) |
| `LabyrinthEntrancePiece` | class | StructurePiece | — | 11 | 260 | [LabyrinthEntrancePiece.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthEntrancePiece.java) |
| `SatyrTunes` | class | — | — | 9 | 224 | [SatyrTunes.java](net/pixeldreamstudios/mythika/entity/SatyrTunes.java) |
| `MythikaNeoForgeClient` | class | — | — | 2 | 32 | [MythikaNeoForgeClient.java](net/pixeldreamstudios/mythika/neoforge/client/MythikaNeoForgeClient.java) |
| `CaveBlocks` | class | — | — | 0 | 72 | [CaveBlocks.java](net/pixeldreamstudios/mythika/worldgen/CaveBlocks.java) |
| `LabyrinthPiece` | class | StructurePiece | — | 7 | 245 | [LabyrinthPiece.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthPiece.java) |
| `LabyrinthBlocks` | class | — | — | 0 | 57 | [LabyrinthBlocks.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthBlocks.java) |
| `ScreenShake` | class | — | — | 2 | 46 | [ScreenShake.java](net/pixeldreamstudios/mythika/client/ScreenShake.java) |
| `CyclopsRenderer` | class | GeoEntityRenderer | — | 0 | 13 | [CyclopsRenderer.java](net/pixeldreamstudios/mythika/client/CyclopsRenderer.java) |
| `MinotaurRenderer` | class | GeoEntityRenderer | — | 0 | 13 | [MinotaurRenderer.java](net/pixeldreamstudios/mythika/client/MinotaurRenderer.java) |
| `SatyrRenderer` | class | GeoEntityRenderer | — | 1 | 51 | [SatyrRenderer.java](net/pixeldreamstudios/mythika/client/SatyrRenderer.java) |
| `ReturnToLairGoal` | class | Goal | — | 6 | 75 | [ReturnToLairGoal.java](net/pixeldreamstudios/mythika/entity/ReturnToLairGoal.java) |
| `MenacingChorusGoal` | class | Goal | — | 8 | 77 | [MenacingChorusGoal.java](net/pixeldreamstudios/mythika/entity/MenacingChorusGoal.java) |
| `RetreatIntoLabyrinthGoal` | class | Goal | — | 8 | 99 | [RetreatIntoLabyrinthGoal.java](net/pixeldreamstudios/mythika/entity/RetreatIntoLabyrinthGoal.java) |
| `FollowFireBuilderGoal` | class | Goal | — | 5 | 81 | [FollowFireBuilderGoal.java](net/pixeldreamstudios/mythika/entity/FollowFireBuilderGoal.java) |
| `ReturnToCampfireGoal` | class | Goal | — | 6 | 76 | [ReturnToCampfireGoal.java](net/pixeldreamstudios/mythika/entity/ReturnToCampfireGoal.java) |
| `AttackTimeline` | class | — | — | 7 | 62 | [AttackTimeline.java](net/pixeldreamstudios/mythika/entity/AttackTimeline.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 4 | 30 | [NeoForgePlatformHelper.java](net/pixeldreamstudios/mythika/neoforge/platform/NeoForgePlatformHelper.java) |
| `LabyrinthStructure` | class | Structure | — | 6 | 130 | [LabyrinthStructure.java](net/pixeldreamstudios/mythika/worldgen/LabyrinthStructure.java) |
| `CyclopsCaveStructure` | class | Structure | — | 5 | 135 | [CyclopsCaveStructure.java](net/pixeldreamstudios/mythika/worldgen/CyclopsCaveStructure.java) |
| `IPlatformHelper` | interface | — | — | 0 | 14 | [IPlatformHelper.java](net/pixeldreamstudios/mythika/platform/services/IPlatformHelper.java) |
| `DeferredRegister` | class | — | — | 6 | 56 | [DeferredRegister.java](net/pixeldreamstudios/mythika/registry/DeferredRegister.java) |
| `Mythika` | class | — | — | 2 | 42 | [Mythika.java](net/pixeldreamstudios/mythika/Mythika.java) |
| `MinotaurNavigation` | class | GroundPathNavigation | — | 3 | 31 | [MinotaurNavigation.java](net/pixeldreamstudios/mythika/entity/MinotaurNavigation.java) |
| `TimedMeleeAttackGoal` | class | MeleeAttackGoal | — | 2 | 37 | [TimedMeleeAttackGoal.java](net/pixeldreamstudios/mythika/entity/TimedMeleeAttackGoal.java) |
| `TimedAttacker` | interface | — | — | 0 | 12 | [TimedAttacker.java](net/pixeldreamstudios/mythika/entity/TimedAttacker.java) |
| `CyclopsNavigation` | class | GroundPathNavigation | — | 2 | 35 | [CyclopsNavigation.java](net/pixeldreamstudios/mythika/entity/CyclopsNavigation.java) |
| `GaitTracker` | class | — | — | 4 | 46 | [GaitTracker.java](net/pixeldreamstudios/mythika/entity/GaitTracker.java) |
| `ChargeInTurnGoal` | class | TimedMeleeAttackGoal | — | 2 | 20 | [ChargeInTurnGoal.java](net/pixeldreamstudios/mythika/entity/ChargeInTurnGoal.java) |
| `Footfalls` | class | — | — | 2 | 45 | [Footfalls.java](net/pixeldreamstudios/mythika/entity/Footfalls.java) |
| `GaitAnimationController` | class | AnimationController | — | 1 | 35 | [GaitAnimationController.java](net/pixeldreamstudios/mythika/entity/GaitAnimationController.java) |
| `Variance` | class | — | — | 0 | 15 | [Variance.java](net/pixeldreamstudios/mythika/worldgen/Variance.java) |
| `Services` | class | — | — | 1 | 18 | [Services.java](net/pixeldreamstudios/mythika/platform/Services.java) |
| `MinotaurModel` | class | GeoModel | — | 4 | 40 | [MinotaurModel.java](net/pixeldreamstudios/mythika/client/MinotaurModel.java) |
| `SatyrModel` | class | GeoModel | — | 4 | 45 | [SatyrModel.java](net/pixeldreamstudios/mythika/client/SatyrModel.java) |
| `CyclopsModel` | class | GeoModel | — | 4 | 40 | [CyclopsModel.java](net/pixeldreamstudios/mythika/client/CyclopsModel.java) |
