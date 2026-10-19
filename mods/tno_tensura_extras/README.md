# Tensura Extras（`tno_tensura_extras`）

> jar：`[转生附属]tno_tensura_extras.jar` ｜ 版本：`1.0.2` ｜ 作者：?
> 规模：**79 个类 / 19 个包 / 12277 行** ｜ 事件处理类 0 ｜ Mixin 6 ｜ 注册/管理类 12

**依赖**：`neoforge`、`minecraft`、`tensura`、`geckolib`、`ftbchunks`、`ironchest`、`lootr`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tno_tensura_extras.client` | 23 | `LooterClientEvents`、`TimeTravelSceneRenderer`、`LooterScreen` |
| `com.tno_tensura_extras.entity` | 9 | `DiabloEntity`、`DiabloSkillController`、`AlignmentFieldEntity` |
| `com.tno_tensura_extras.registry` | 8 | `ExtrasBlocks`、`ExtrasItems`、`ExtrasEntityTypes` |
| `com.tno_tensura_extras.util` | 7 | `LooterManager`、`SeducerManager`、`TimeTravelManager` |
| `com.tno_tensura_extras.mixin` | 5 | `ServerboundMovePlayerPacketAccessor`、`ServerGamePacketListenerAlignmentFieldMixin`、`EntityAlignmentFieldMixin` |
| `com.tno_tensura_extras.event` | 5 | `DiabloSpawnEvents`、`AlignmentFieldEvents`、`DiabloCombatEvents` |
| `com.tno_tensura_extras.skill.unique` | 5 | `SeducerSkill`、`LooterSkill`、`TimeTravelerSkill` |
| `com.tno_tensura_extras.item` | 3 | `MagicCameraItem`、`HighQualityHoneyItem`、`TeleportMagicTubeItem` |
| `com.tno_tensura_extras.magic` | 3 | `DayNightInversionMagic`、`AlignmentFieldMagic`、`HolyDemonicInversionMagic` |
| `com.tno_tensura_extras.data` | 2 | `SeducerContractData`、`BoundCargo` |
| `com.tno_tensura_extras` | 1 | `TensuraExtras` |
| `com.tno_tensura_extras.network` | 1 | `LooterPackets` |
| `com.tno_tensura_extras.block.entity` | 1 | `CargoInTransitBlockEntity` |
| `com.tno_tensura_extras.mixin.client` | 1 | `MinecraftMixin` |
| `com.tno_tensura_extras.config` | 1 | `ExtrasConfig` |
| `com.tno_tensura_extras.block` | 1 | `CargoInTransitBlock` |
| `com.tno_tensura_extras.compat` | 1 | `ProtectionCompat` |
| `com.tno_tensura_extras.effect` | 1 | `DespairTimeEffect` |
| `com.tno_tensura_extras.compat.ftbchunks` | 1 | `FtbChunksProtection` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LooterManager` | class | — | — | 58 | 1076 | [LooterManager.java](com/tno_tensura_extras/util/LooterManager.java) |
| `SeducerManager` | class | Goal | — | 40 | 765 | [SeducerManager.java](com/tno_tensura_extras/util/SeducerManager.java) |
| `LooterClientEvents` | class | GeoRenderLayer | — | 7 | 139 | [LooterClientEvents.java](com/tno_tensura_extras/client/LooterClientEvents.java) |
| `TimeTravelManager` | class | RuntimeException | — | 28 | 487 | [TimeTravelManager.java](com/tno_tensura_extras/util/TimeTravelManager.java) |
| `TimeTravelSceneRenderer` | class | — | — | 44 | 888 | [TimeTravelSceneRenderer.java](com/tno_tensura_extras/client/TimeTravelSceneRenderer.java) |
| `LooterScreen` | class | Screen | — | 27 | 309 | [LooterScreen.java](com/tno_tensura_extras/client/LooterScreen.java) |
| `TensuraExtras` | class | — | Mod | 2 | 147 | [TensuraExtras.java](com/tno_tensura_extras/TensuraExtras.java) |
| `DiabloEntity` | class | ArchDaemonEntity | — | 36 | 534 | [DiabloEntity.java](com/tno_tensura_extras/entity/DiabloEntity.java) |
| `ExtrasClientEvents` | class | — | — | 1 | 17 | [ExtrasClientEvents.java](com/tno_tensura_extras/client/ExtrasClientEvents.java) |
| `TimeTravelClient` | class | — | — | 35 | 595 | [TimeTravelClient.java](com/tno_tensura_extras/client/TimeTravelClient.java) |
| `DiabloSkillController` | class | — | — | 29 | 837 | [DiabloSkillController.java](com/tno_tensura_extras/entity/DiabloSkillController.java) |
| `ServerboundMovePlayerPacketAccessor` | interface | — | Mixin | 0 | 22 | [ServerboundMovePlayerPacketAccessor.java](com/tno_tensura_extras/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerAlignmentFieldMixin` | class | — | Mixin | 0 | 41 | [ServerGamePacketListenerAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/ServerGamePacketListenerAlignmentFieldMixin.java) |
| `AlignmentFieldEntity` | class | Entity | — | 27 | 222 | [AlignmentFieldEntity.java](com/tno_tensura_extras/entity/AlignmentFieldEntity.java) |
| `LooterBlackHoleEntity` | class | Entity | — | 17 | 139 | [LooterBlackHoleEntity.java](com/tno_tensura_extras/entity/LooterBlackHoleEntity.java) |
| `EntityAlignmentFieldMixin` | class | — | Mixin | 0 | 21 | [EntityAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/EntityAlignmentFieldMixin.java) |
| `DiabloSpawnEvents` | class | — | — | 2 | 47 | [DiabloSpawnEvents.java](com/tno_tensura_extras/event/DiabloSpawnEvents.java) |
| `AlignmentFieldEvents` | class | — | — | 3 | 63 | [AlignmentFieldEvents.java](com/tno_tensura_extras/event/AlignmentFieldEvents.java) |
| `DiabloCombatEvents` | class | — | — | 2 | 62 | [DiabloCombatEvents.java](com/tno_tensura_extras/event/DiabloCombatEvents.java) |
| `DayNightInversionEvents` | class | — | — | 2 | 47 | [DayNightInversionEvents.java](com/tno_tensura_extras/event/DayNightInversionEvents.java) |
| `HighQualityHoneyEvents` | class | — | — | 1 | 45 | [HighQualityHoneyEvents.java](com/tno_tensura_extras/event/HighQualityHoneyEvents.java) |
| `CargoTeleportHandler` | class | — | — | 10 | 225 | [CargoTeleportHandler.java](com/tno_tensura_extras/util/CargoTeleportHandler.java) |
| `LooterPackets` | class | CustomPacketPayload | — | 9 | 116 | [LooterPackets.java](com/tno_tensura_extras/network/LooterPackets.java) |
| `AlignmentFieldManager` | class | — | — | 10 | 176 | [AlignmentFieldManager.java](com/tno_tensura_extras/util/AlignmentFieldManager.java) |
| `LooterAuraRenderer` | class | — | — | 8 | 258 | [LooterAuraRenderer.java](com/tno_tensura_extras/client/LooterAuraRenderer.java) |
| `LooterBlackHoleRenderer` | class | EntityRenderer | — | 7 | 229 | [LooterBlackHoleRenderer.java](com/tno_tensura_extras/client/LooterBlackHoleRenderer.java) |
| `MagicCameraView` | class | AbstractTexture | — | 25 | 453 | [MagicCameraView.java](com/tno_tensura_extras/client/MagicCameraView.java) |
| `DiabloCombatController` | class | — | — | 15 | 602 | [DiabloCombatController.java](com/tno_tensura_extras/entity/DiabloCombatController.java) |
| `CargoInTransitBlockEntity` | class | BlockEntity | — | 14 | 193 | [CargoInTransitBlockEntity.java](com/tno_tensura_extras/block/entity/CargoInTransitBlockEntity.java) |
| `DiabloProjectileTargetMixin` | class | — | Mixin | 0 | 22 | [DiabloProjectileTargetMixin.java](com/tno_tensura_extras/mixin/DiabloProjectileTargetMixin.java) |
| `DespairTimeMagicMixin` | class | — | Mixin | 0 | 51 | [DespairTimeMagicMixin.java](com/tno_tensura_extras/mixin/DespairTimeMagicMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 0 | 21 | [MinecraftMixin.java](com/tno_tensura_extras/mixin/client/MinecraftMixin.java) |
| `SeducerContractData` | class | SavedData | — | 15 | 216 | [SeducerContractData.java](com/tno_tensura_extras/data/SeducerContractData.java) |
| `TeleportPortalEntity` | class | Entity | — | 14 | 104 | [TeleportPortalEntity.java](com/tno_tensura_extras/entity/TeleportPortalEntity.java) |
| `DiabloDecoyEntity` | class | PathfinderMob | — | 10 | 125 | [DiabloDecoyEntity.java](com/tno_tensura_extras/entity/DiabloDecoyEntity.java) |
| `ExtrasConfig` | class | — | — | 4 | 72 | [ExtrasConfig.java](com/tno_tensura_extras/config/ExtrasConfig.java) |
| `SeducerSkill` | class | Skill | — | 17 | 344 | [SeducerSkill.java](com/tno_tensura_extras/skill/unique/SeducerSkill.java) |
| `CargoInTransitBlock` | class | BaseEntityBlock | — | 5 | 52 | [CargoInTransitBlock.java](com/tno_tensura_extras/block/CargoInTransitBlock.java) |
| `ProtectionCompat` | class | — | — | 5 | 82 | [ProtectionCompat.java](com/tno_tensura_extras/compat/ProtectionCompat.java) |
| `LooterSkill` | class | Skill | — | 3 | 32 | [LooterSkill.java](com/tno_tensura_extras/skill/unique/LooterSkill.java) |
| `MagicCameraHud` | class | — | — | 14 | 231 | [MagicCameraHud.java](com/tno_tensura_extras/client/MagicCameraHud.java) |
| `AlignmentFieldRenderer` | class | EntityRenderer | — | 8 | 133 | [AlignmentFieldRenderer.java](com/tno_tensura_extras/client/AlignmentFieldRenderer.java) |
| `MagicCameraItem` | class | Item | — | 4 | 42 | [MagicCameraItem.java](com/tno_tensura_extras/item/MagicCameraItem.java) |
| `HighQualityHoneyItem` | class | Item | — | 3 | 58 | [HighQualityHoneyItem.java](com/tno_tensura_extras/item/HighQualityHoneyItem.java) |
| `TeleportMagicTubeItem` | class | Item | — | 4 | 97 | [TeleportMagicTubeItem.java](com/tno_tensura_extras/item/TeleportMagicTubeItem.java) |
| `ExtrasBlocks` | class | — | — | 0 | 36 | [ExtrasBlocks.java](com/tno_tensura_extras/registry/ExtrasBlocks.java) |
| `ExtrasItems` | class | — | — | 0 | 33 | [ExtrasItems.java](com/tno_tensura_extras/registry/ExtrasItems.java) |
| `ExtrasEntityTypes` | class | — | — | 0 | 73 | [ExtrasEntityTypes.java](com/tno_tensura_extras/registry/ExtrasEntityTypes.java) |
| `MagicCameraScreenLayer` | class | GeoRenderLayer | — | 4 | 104 | [MagicCameraScreenLayer.java](com/tno_tensura_extras/client/MagicCameraScreenLayer.java) |
| `TeleportPortalRenderer` | class | GeoEntityRenderer | — | 3 | 29 | [TeleportPortalRenderer.java](com/tno_tensura_extras/client/TeleportPortalRenderer.java) |
| `DiabloDecoyRenderer` | class | GeoEntityRenderer | — | 1 | 45 | [DiabloDecoyRenderer.java](com/tno_tensura_extras/client/DiabloDecoyRenderer.java) |
| `MagicCameraRenderer` | class | GeoItemRenderer | — | 0 | 22 | [MagicCameraRenderer.java](com/tno_tensura_extras/client/MagicCameraRenderer.java) |
| `CargoInTransitRenderer` | class | BlockEntityRenderer<CargoInTransitBl | — | 1 | 40 | [CargoInTransitRenderer.java](com/tno_tensura_extras/client/CargoInTransitRenderer.java) |
| `MagicCameraClientExtensions` | class | — | — | 2 | 24 | [MagicCameraClientExtensions.java](com/tno_tensura_extras/client/MagicCameraClientExtensions.java) |
| `DiabloRenderer` | class | GeoEntityRenderer | — | 1 | 45 | [DiabloRenderer.java](com/tno_tensura_extras/client/DiabloRenderer.java) |
| `DayNightInversionMagic` | class | Magic | — | 13 | 110 | [DayNightInversionMagic.java](com/tno_tensura_extras/magic/DayNightInversionMagic.java) |
| `AlignmentFieldMagic` | class | AspectualMagic | — | 12 | 182 | [AlignmentFieldMagic.java](com/tno_tensura_extras/magic/AlignmentFieldMagic.java) |
| `DiabloTargeting` | class | — | — | 7 | 175 | [DiabloTargeting.java](com/tno_tensura_extras/entity/DiabloTargeting.java) |
| `ExtrasDataComponents` | class | — | — | 0 | 16 | [ExtrasDataComponents.java](com/tno_tensura_extras/registry/ExtrasDataComponents.java) |
| `SeducerContractOffers` | class | — | — | 7 | 75 | [SeducerContractOffers.java](com/tno_tensura_extras/util/SeducerContractOffers.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ServerboundMovePlayerPacketAccessor` | `ServerboundMovePlayerPacketAccessor` | [ServerboundMovePlayerPacketAccessor.java](com/tno_tensura_extras/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerAlignmentFieldMixin` | `ServerGamePacketListenerAlignmentField` | [ServerGamePacketListenerAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/ServerGamePacketListenerAlignmentFieldMixin.java) |
| `EntityAlignmentFieldMixin` | `EntityAlignmentField` | [EntityAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/EntityAlignmentFieldMixin.java) |
| `DiabloProjectileTargetMixin` | `DiabloProjectileTarget` | [DiabloProjectileTargetMixin.java](com/tno_tensura_extras/mixin/DiabloProjectileTargetMixin.java) |
| `DespairTimeMagicMixin` | `DespairTimeMagic` | [DespairTimeMagicMixin.java](com/tno_tensura_extras/mixin/DespairTimeMagicMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/tno_tensura_extras/mixin/client/MinecraftMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ExtrasConfig` | — | [ExtrasConfig.java](com/tno_tensura_extras/config/ExtrasConfig.java) |

## 全部类（79）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LooterManager` | class | — | — | 58 | 1076 | [LooterManager.java](com/tno_tensura_extras/util/LooterManager.java) |
| `SeducerManager` | class | Goal | — | 40 | 765 | [SeducerManager.java](com/tno_tensura_extras/util/SeducerManager.java) |
| `LooterClientEvents` | class | GeoRenderLayer | — | 7 | 139 | [LooterClientEvents.java](com/tno_tensura_extras/client/LooterClientEvents.java) |
| `TimeTravelManager` | class | RuntimeException | — | 28 | 487 | [TimeTravelManager.java](com/tno_tensura_extras/util/TimeTravelManager.java) |
| `TimeTravelSceneRenderer` | class | — | — | 44 | 888 | [TimeTravelSceneRenderer.java](com/tno_tensura_extras/client/TimeTravelSceneRenderer.java) |
| `LooterScreen` | class | Screen | — | 27 | 309 | [LooterScreen.java](com/tno_tensura_extras/client/LooterScreen.java) |
| `TensuraExtras` | class | — | Mod | 2 | 147 | [TensuraExtras.java](com/tno_tensura_extras/TensuraExtras.java) |
| `DiabloEntity` | class | ArchDaemonEntity | — | 36 | 534 | [DiabloEntity.java](com/tno_tensura_extras/entity/DiabloEntity.java) |
| `ExtrasClientEvents` | class | — | — | 1 | 17 | [ExtrasClientEvents.java](com/tno_tensura_extras/client/ExtrasClientEvents.java) |
| `TimeTravelClient` | class | — | — | 35 | 595 | [TimeTravelClient.java](com/tno_tensura_extras/client/TimeTravelClient.java) |
| `DiabloSkillController` | class | — | — | 29 | 837 | [DiabloSkillController.java](com/tno_tensura_extras/entity/DiabloSkillController.java) |
| `ServerboundMovePlayerPacketAccessor` | interface | — | Mixin | 0 | 22 | [ServerboundMovePlayerPacketAccessor.java](com/tno_tensura_extras/mixin/ServerboundMovePlayerPacketAccessor.java) |
| `ServerGamePacketListenerAlignmentFieldMixin` | class | — | Mixin | 0 | 41 | [ServerGamePacketListenerAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/ServerGamePacketListenerAlignmentFieldMixin.java) |
| `AlignmentFieldEntity` | class | Entity | — | 27 | 222 | [AlignmentFieldEntity.java](com/tno_tensura_extras/entity/AlignmentFieldEntity.java) |
| `LooterBlackHoleEntity` | class | Entity | — | 17 | 139 | [LooterBlackHoleEntity.java](com/tno_tensura_extras/entity/LooterBlackHoleEntity.java) |
| `EntityAlignmentFieldMixin` | class | — | Mixin | 0 | 21 | [EntityAlignmentFieldMixin.java](com/tno_tensura_extras/mixin/EntityAlignmentFieldMixin.java) |
| `DiabloSpawnEvents` | class | — | — | 2 | 47 | [DiabloSpawnEvents.java](com/tno_tensura_extras/event/DiabloSpawnEvents.java) |
| `AlignmentFieldEvents` | class | — | — | 3 | 63 | [AlignmentFieldEvents.java](com/tno_tensura_extras/event/AlignmentFieldEvents.java) |
| `DiabloCombatEvents` | class | — | — | 2 | 62 | [DiabloCombatEvents.java](com/tno_tensura_extras/event/DiabloCombatEvents.java) |
| `DayNightInversionEvents` | class | — | — | 2 | 47 | [DayNightInversionEvents.java](com/tno_tensura_extras/event/DayNightInversionEvents.java) |
| `HighQualityHoneyEvents` | class | — | — | 1 | 45 | [HighQualityHoneyEvents.java](com/tno_tensura_extras/event/HighQualityHoneyEvents.java) |
| `CargoTeleportHandler` | class | — | — | 10 | 225 | [CargoTeleportHandler.java](com/tno_tensura_extras/util/CargoTeleportHandler.java) |
| `LooterPackets` | class | CustomPacketPayload | — | 9 | 116 | [LooterPackets.java](com/tno_tensura_extras/network/LooterPackets.java) |
| `AlignmentFieldManager` | class | — | — | 10 | 176 | [AlignmentFieldManager.java](com/tno_tensura_extras/util/AlignmentFieldManager.java) |
| `LooterAuraRenderer` | class | — | — | 8 | 258 | [LooterAuraRenderer.java](com/tno_tensura_extras/client/LooterAuraRenderer.java) |
| `LooterBlackHoleRenderer` | class | EntityRenderer | — | 7 | 229 | [LooterBlackHoleRenderer.java](com/tno_tensura_extras/client/LooterBlackHoleRenderer.java) |
| `MagicCameraView` | class | AbstractTexture | — | 25 | 453 | [MagicCameraView.java](com/tno_tensura_extras/client/MagicCameraView.java) |
| `DiabloCombatController` | class | — | — | 15 | 602 | [DiabloCombatController.java](com/tno_tensura_extras/entity/DiabloCombatController.java) |
| `CargoInTransitBlockEntity` | class | BlockEntity | — | 14 | 193 | [CargoInTransitBlockEntity.java](com/tno_tensura_extras/block/entity/CargoInTransitBlockEntity.java) |
| `DiabloProjectileTargetMixin` | class | — | Mixin | 0 | 22 | [DiabloProjectileTargetMixin.java](com/tno_tensura_extras/mixin/DiabloProjectileTargetMixin.java) |
| `DespairTimeMagicMixin` | class | — | Mixin | 0 | 51 | [DespairTimeMagicMixin.java](com/tno_tensura_extras/mixin/DespairTimeMagicMixin.java) |
| `MinecraftMixin` | class | — | Mixin | 0 | 21 | [MinecraftMixin.java](com/tno_tensura_extras/mixin/client/MinecraftMixin.java) |
| `SeducerContractData` | class | SavedData | — | 15 | 216 | [SeducerContractData.java](com/tno_tensura_extras/data/SeducerContractData.java) |
| `TeleportPortalEntity` | class | Entity | — | 14 | 104 | [TeleportPortalEntity.java](com/tno_tensura_extras/entity/TeleportPortalEntity.java) |
| `DiabloDecoyEntity` | class | PathfinderMob | — | 10 | 125 | [DiabloDecoyEntity.java](com/tno_tensura_extras/entity/DiabloDecoyEntity.java) |
| `ExtrasConfig` | class | — | — | 4 | 72 | [ExtrasConfig.java](com/tno_tensura_extras/config/ExtrasConfig.java) |
| `SeducerSkill` | class | Skill | — | 17 | 344 | [SeducerSkill.java](com/tno_tensura_extras/skill/unique/SeducerSkill.java) |
| `CargoInTransitBlock` | class | BaseEntityBlock | — | 5 | 52 | [CargoInTransitBlock.java](com/tno_tensura_extras/block/CargoInTransitBlock.java) |
| `ProtectionCompat` | class | — | — | 5 | 82 | [ProtectionCompat.java](com/tno_tensura_extras/compat/ProtectionCompat.java) |
| `LooterSkill` | class | Skill | — | 3 | 32 | [LooterSkill.java](com/tno_tensura_extras/skill/unique/LooterSkill.java) |
| `MagicCameraHud` | class | — | — | 14 | 231 | [MagicCameraHud.java](com/tno_tensura_extras/client/MagicCameraHud.java) |
| `AlignmentFieldRenderer` | class | EntityRenderer | — | 8 | 133 | [AlignmentFieldRenderer.java](com/tno_tensura_extras/client/AlignmentFieldRenderer.java) |
| `MagicCameraItem` | class | Item | — | 4 | 42 | [MagicCameraItem.java](com/tno_tensura_extras/item/MagicCameraItem.java) |
| `HighQualityHoneyItem` | class | Item | — | 3 | 58 | [HighQualityHoneyItem.java](com/tno_tensura_extras/item/HighQualityHoneyItem.java) |
| `TeleportMagicTubeItem` | class | Item | — | 4 | 97 | [TeleportMagicTubeItem.java](com/tno_tensura_extras/item/TeleportMagicTubeItem.java) |
| `ExtrasBlocks` | class | — | — | 0 | 36 | [ExtrasBlocks.java](com/tno_tensura_extras/registry/ExtrasBlocks.java) |
| `ExtrasItems` | class | — | — | 0 | 33 | [ExtrasItems.java](com/tno_tensura_extras/registry/ExtrasItems.java) |
| `ExtrasEntityTypes` | class | — | — | 0 | 73 | [ExtrasEntityTypes.java](com/tno_tensura_extras/registry/ExtrasEntityTypes.java) |
| `MagicCameraScreenLayer` | class | GeoRenderLayer | — | 4 | 104 | [MagicCameraScreenLayer.java](com/tno_tensura_extras/client/MagicCameraScreenLayer.java) |
| `TeleportPortalRenderer` | class | GeoEntityRenderer | — | 3 | 29 | [TeleportPortalRenderer.java](com/tno_tensura_extras/client/TeleportPortalRenderer.java) |
| `DiabloDecoyRenderer` | class | GeoEntityRenderer | — | 1 | 45 | [DiabloDecoyRenderer.java](com/tno_tensura_extras/client/DiabloDecoyRenderer.java) |
| `MagicCameraRenderer` | class | GeoItemRenderer | — | 0 | 22 | [MagicCameraRenderer.java](com/tno_tensura_extras/client/MagicCameraRenderer.java) |
| `CargoInTransitRenderer` | class | BlockEntityRenderer<CargoInTransitBl | — | 1 | 40 | [CargoInTransitRenderer.java](com/tno_tensura_extras/client/CargoInTransitRenderer.java) |
| `MagicCameraClientExtensions` | class | — | — | 2 | 24 | [MagicCameraClientExtensions.java](com/tno_tensura_extras/client/MagicCameraClientExtensions.java) |
| `DiabloRenderer` | class | GeoEntityRenderer | — | 1 | 45 | [DiabloRenderer.java](com/tno_tensura_extras/client/DiabloRenderer.java) |
| `DayNightInversionMagic` | class | Magic | — | 13 | 110 | [DayNightInversionMagic.java](com/tno_tensura_extras/magic/DayNightInversionMagic.java) |
| `AlignmentFieldMagic` | class | AspectualMagic | — | 12 | 182 | [AlignmentFieldMagic.java](com/tno_tensura_extras/magic/AlignmentFieldMagic.java) |
| `DiabloTargeting` | class | — | — | 7 | 175 | [DiabloTargeting.java](com/tno_tensura_extras/entity/DiabloTargeting.java) |
| `ExtrasDataComponents` | class | — | — | 0 | 16 | [ExtrasDataComponents.java](com/tno_tensura_extras/registry/ExtrasDataComponents.java) |
| `SeducerContractOffers` | class | — | — | 7 | 75 | [SeducerContractOffers.java](com/tno_tensura_extras/util/SeducerContractOffers.java) |
| `TimeTravelerSkill` | class | Skill | — | 8 | 64 | [TimeTravelerSkill.java](com/tno_tensura_extras/skill/unique/TimeTravelerSkill.java) |
| `OptimistSkill` | class | Skill | — | 7 | 71 | [OptimistSkill.java](com/tno_tensura_extras/skill/unique/OptimistSkill.java) |
| `AllRounderSkill` | class | Skill | — | 9 | 168 | [AllRounderSkill.java](com/tno_tensura_extras/skill/unique/AllRounderSkill.java) |
| `DespairTimeEffect` | class | MobEffect | — | 5 | 58 | [DespairTimeEffect.java](com/tno_tensura_extras/effect/DespairTimeEffect.java) |
| `DiabloAttackAnimation` | enum | — | — | 0 | 34 | [DiabloAttackAnimation.java](com/tno_tensura_extras/entity/DiabloAttackAnimation.java) |
| `FtbChunksProtection` | class | — | — | 2 | 42 | [FtbChunksProtection.java](com/tno_tensura_extras/compat/ftbchunks/FtbChunksProtection.java) |
| `BoundCargo` | record | — | — | 0 | 33 | [BoundCargo.java](com/tno_tensura_extras/data/BoundCargo.java) |
| `ExtrasSkills` | class | — | — | 2 | 45 | [ExtrasSkills.java](com/tno_tensura_extras/registry/ExtrasSkills.java) |
| `ExtrasAttachments` | class | — | — | 0 | 18 | [ExtrasAttachments.java](com/tno_tensura_extras/registry/ExtrasAttachments.java) |
| `ExtrasMobEffects` | class | — | — | 0 | 16 | [ExtrasMobEffects.java](com/tno_tensura_extras/registry/ExtrasMobEffects.java) |
| `ExtrasSounds` | class | — | — | 0 | 18 | [ExtrasSounds.java](com/tno_tensura_extras/registry/ExtrasSounds.java) |
| `SeducerSoulHarvest` | class | — | — | 3 | 70 | [SeducerSoulHarvest.java](com/tno_tensura_extras/util/SeducerSoulHarvest.java) |
| `DiabloDecoyModel` | class | GeoModel | — | 4 | 30 | [DiabloDecoyModel.java](com/tno_tensura_extras/client/DiabloDecoyModel.java) |
| `MagicCameraArmPose` | class | — | — | 4 | 46 | [MagicCameraArmPose.java](com/tno_tensura_extras/client/MagicCameraArmPose.java) |
| `ExtrasRenderTypes` | class | RenderStateShard | — | 0 | 33 | [ExtrasRenderTypes.java](com/tno_tensura_extras/client/ExtrasRenderTypes.java) |
| `TeleportPortalModel` | class | DefaultedEntityGeoModel | — | 1 | 16 | [TeleportPortalModel.java](com/tno_tensura_extras/client/TeleportPortalModel.java) |
| `MagicCameraModel` | class | DefaultedItemGeoModel | — | 0 | 12 | [MagicCameraModel.java](com/tno_tensura_extras/client/MagicCameraModel.java) |
| `DiabloModel` | class | GeoModel | — | 4 | 30 | [DiabloModel.java](com/tno_tensura_extras/client/DiabloModel.java) |
| `HolyDemonicInversionMagic` | class | DayNightInversionMagic | — | 3 | 27 | [HolyDemonicInversionMagic.java](com/tno_tensura_extras/magic/HolyDemonicInversionMagic.java) |
