# Fox Ablaze Ultimate（`foxablazeultimate`）

> jar：`[转生附属]foxablazeultimate.jar` ｜ 版本：`1.0.6.4` ｜ 作者：AblazeAQZL
> 规模：**164 个类 / 39 个包 / 30483 行** ｜ 事件处理类 29 ｜ Mixin 19 ｜ 注册/管理类 26

**依赖**：`neoforge`、`minecraft`、`architectury`、`manascore_storage`、`manascore_skill`、`tensura`、`geckolib`、`shouldersurfing`、`tno_tensura_extras`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.foxablazeultimate.event` | 15 | `VelgryndAcquisitionHandler`、`VeldoraAcquisitionHandler`、`SatanaelAcquisitionHandler` |
| `com.foxablazeultimate.config` | 14 | `AzathothConfig`、`RaphaelConfig`、`FortunaConfig` |
| `com.foxablazeultimate.network` | 14 | `OpenShubWarehousePayload`、`ShowWisdomCrystalEasterEggPayload`、`OpenBeelzebubStoragePayload` |
| `com.foxablazeultimate.client` | 11 | `CielTakeoverInputHandler`、`FoxAblazeUltimateModClient`、`VeldoraSlashViewLockClient` |
| `com.foxablazeultimate.mixin.client` | 9 | `MixinItemRendererVelgryndGlint`、`MixinItemInHandRendererAzathoth`、`MixinItemRendererAzathoth` |
| `com.foxablazeultimate.ability.skill.ciel` | 8 | `CielTakeoverTerrainHandler`、`CielEmergencyController`、`CielAwakeningHandler` |
| `com.foxablazeultimate.mixin` | 8 | `MixinTickRateManagerYogTimeStop`、`MixinEnergyHelperCielOptimization`、`MixinPredatorMistProjectile` |
| `com.foxablazeultimate.registry` | 8 | `FoxAblazeUltimateLootModifiers`、`FoxAblazeUltimateEntityTypes`、`FoxAblazeUltimateItems` |
| `com.foxablazeultimate.client.render` | 7 | `HellSniperBeamRenderer`、`CapturedEntityRenderer`、`YogTimeStopWaveRenderer` |
| `com.foxablazeultimate.ability.skill.azathoth` | 5 | `AzathothCombat`、`AzathothDragons`、`AzathothSkill` |
| `com.foxablazeultimate.ability.skill.beelzebub` | 5 | `BeelzebubCaptureHandler`、`BeelzebubFusionTickHandler`、`BeelzebubSkill` |
| `com.foxablazeultimate.effect` | 5 | `SatanaelRampageEffect`、`VelgryndExcitationEffect`、`WisdomBuffEffect` |
| `com.foxablazeultimate.ability.skill.yogsothoth` | 4 | `YogTimeStopManager`、`YogSothothSkill`、`YogSothothEvolution` |
| `com.foxablazeultimate.ability.skill.raphael` | 4 | `RaphaelFusionTickHandler`、`RaphaelSkill`、`RaphaelFusion` |
| `com.foxablazeultimate.entity` | 4 | `HellSniperCircleEntity`、`HellSniperBeamEntity`、`YogTimeStopWaveEntity` |
| `com.foxablazeultimate.ability.skill.shubniggurath` | 4 | `ShubNiggurathWarehouse`、`ShubNiggurathArchiveHandler`、`ShubNiggurathSkill` |
| `com.foxablazeultimate.client.screen` | 4 | `ShubWarehouseScreen`、`BeelzebubStorageScreen`、`PredationFilterOverlay` |
| `com.foxablazeultimate.ability.skill.velgrynd` | 3 | `VelgryndChargeHandler`、`VelgryndSkill`、`VelgryndChargeData` |
| `com.foxablazeultimate.ability.skill.veldora` | 3 | `VeldoraDomainTickHandler`、`VeldoraSkill`、`VeldoraSlashViewLock` |
| `com.foxablazeultimate.mixin.stextras` | 3 | `STExtrasCrystalLockServerMixin`、`STExtrasIsSkillValidUnlockRaphaelMixin`、`FoxAblazeStextrasMixinPlugin` |
| `com.foxablazeultimate.item` | 3 | `CrystalLockState`、`WisdomCrystalItem`、`TimeCrystalItem` |
| `com.foxablazeultimate.command` | 2 | `PredationFilterCommand`、`UltimateSkillRequirementDebugCommand` |
| `com.foxablazeultimate.menu` | 2 | `BeelzebubStorageMenu`、`RaphaelNamingMenu` |
| `com.foxablazeultimate.ability.skill.mammon` | 2 | `MammonSkill`、`MammonDelegateHelper` |
| `com.foxablazeultimate.item.beelzebub` | 2 | `CapturedEntityItem`、`CapturedEntityData` |
| `com.foxablazeultimate.world` | 2 | `SatanaelAcquisitionData`、`FoxAblazeGameRules` |
| `com.foxablazeultimate.client.ciel` | 1 | `CielTakeoverNavigator` |
| `com.foxablazeultimate` | 1 | `FoxAblazeUltimateMod` |
| `com.foxablazeultimate.ability.skill.satanael` | 1 | `SatanaelSkill` |
| `com.foxablazeultimate.ability.skill.fortuna` | 1 | `FortunaSkill` |
| `com.foxablazeultimate.ability.skill.uriel` | 1 | `UrielSkill` |
| `com.foxablazeultimate.ability.skill.hellsniper` | 1 | `HellSniperSkill` |
| `com.foxablazeultimate.compat.stextras` | 1 | `STExtrasCompat` |
| `com.foxablazeultimate.loot` | 1 | `AddWisdomCrystalLootModifier` |
| `com.foxablazeultimate.predation` | 1 | `PredationFilterHelper` |
| `com.foxablazeultimate.client.model` | 1 | `HellSniperCircleModel` |
| `com.foxablazeultimate.menu.slot` | 1 | `SharedBucketSlot` |
| `com.foxablazeultimate.compat.shouldersurfing` | 1 | `CielShoulderSurfingBridge` |
| `com.foxablazeultimate.compat.emi` | 1 | `FoxAblazeUltimateEmiPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CielTakeoverInputHandler` | class | — | EventBusSubscriber | 33 | 565 | [CielTakeoverInputHandler.java](com/foxablazeultimate/client/CielTakeoverInputHandler.java) |
| `YogTimeStopManager` | class | — | EventBusSubscriber | 26 | 470 | [YogTimeStopManager.java](com/foxablazeultimate/ability/skill/yogsothoth/YogTimeStopManager.java) |
| `CielTakeoverTerrainHandler` | class | — | EventBusSubscriber | 22 | 452 | [CielTakeoverTerrainHandler.java](com/foxablazeultimate/ability/skill/ciel/CielTakeoverTerrainHandler.java) |
| `CielEmergencyController` | class | — | EventBusSubscriber | 58 | 927 | [CielEmergencyController.java](com/foxablazeultimate/ability/skill/ciel/CielEmergencyController.java) |
| `AzathothCombat` | class | — | EventBusSubscriber | 25 | 578 | [AzathothCombat.java](com/foxablazeultimate/ability/skill/azathoth/AzathothCombat.java) |
| `VelgryndAcquisitionHandler` | class | — | EventBusSubscriber | 6 | 252 | [VelgryndAcquisitionHandler.java](com/foxablazeultimate/event/VelgryndAcquisitionHandler.java) |
| `VeldoraAcquisitionHandler` | class | — | EventBusSubscriber | 12 | 171 | [VeldoraAcquisitionHandler.java](com/foxablazeultimate/event/VeldoraAcquisitionHandler.java) |
| `CielAwakeningHandler` | class | — | EventBusSubscriber | 8 | 115 | [CielAwakeningHandler.java](com/foxablazeultimate/ability/skill/ciel/CielAwakeningHandler.java) |
| `BeelzebubCaptureHandler` | class | — | EventBusSubscriber | 6 | 195 | [BeelzebubCaptureHandler.java](com/foxablazeultimate/ability/skill/beelzebub/BeelzebubCaptureHandler.java) |
| `SatanaelAcquisitionHandler` | class | — | EventBusSubscriber | 9 | 149 | [SatanaelAcquisitionHandler.java](com/foxablazeultimate/event/SatanaelAcquisitionHandler.java) |
| `FortunaAcquisitionHandler` | class | — | EventBusSubscriber | 5 | 195 | [FortunaAcquisitionHandler.java](com/foxablazeultimate/event/FortunaAcquisitionHandler.java) |
| `VelgryndChargeHandler` | class | — | EventBusSubscriber | 1 | 36 | [VelgryndChargeHandler.java](com/foxablazeultimate/ability/skill/velgrynd/VelgryndChargeHandler.java) |
| `VeldoraDomainTickHandler` | class | — | EventBusSubscriber | 4 | 62 | [VeldoraDomainTickHandler.java](com/foxablazeultimate/ability/skill/veldora/VeldoraDomainTickHandler.java) |
| `BeelzebubFusionTickHandler` | class | — | EventBusSubscriber | 3 | 43 | [BeelzebubFusionTickHandler.java](com/foxablazeultimate/ability/skill/beelzebub/BeelzebubFusionTickHandler.java) |
| `RaphaelFusionTickHandler` | class | — | EventBusSubscriber | 3 | 44 | [RaphaelFusionTickHandler.java](com/foxablazeultimate/ability/skill/raphael/RaphaelFusionTickHandler.java) |
| `MammonAcquisitionHandler` | class | — | EventBusSubscriber | 3 | 172 | [MammonAcquisitionHandler.java](com/foxablazeultimate/event/MammonAcquisitionHandler.java) |
| `HarvestCrystalDropHandler` | class | — | EventBusSubscriber | 1 | 46 | [HarvestCrystalDropHandler.java](com/foxablazeultimate/event/HarvestCrystalDropHandler.java) |
| `LegacySkillIdMigrationHandler` | class | — | EventBusSubscriber | 2 | 36 | [LegacySkillIdMigrationHandler.java](com/foxablazeultimate/event/LegacySkillIdMigrationHandler.java) |
| `HellSniperAcquisitionHandler` | class | — | EventBusSubscriber | 3 | 122 | [HellSniperAcquisitionHandler.java](com/foxablazeultimate/event/HellSniperAcquisitionHandler.java) |
| `VeldoraDragonEssenceLockHandler` | class | — | EventBusSubscriber | 1 | 40 | [VeldoraDragonEssenceLockHandler.java](com/foxablazeultimate/event/VeldoraDragonEssenceLockHandler.java) |
| `WrathCrystalDropHandler` | class | — | EventBusSubscriber | 2 | 38 | [WrathCrystalDropHandler.java](com/foxablazeultimate/event/WrathCrystalDropHandler.java) |
| `WisdomCrystalSyncHandler` | class | — | EventBusSubscriber | 3 | 33 | [WisdomCrystalSyncHandler.java](com/foxablazeultimate/event/WisdomCrystalSyncHandler.java) |
| `AzathothAcquisitionHandler` | class | — | EventBusSubscriber | 4 | 113 | [AzathothAcquisitionHandler.java](com/foxablazeultimate/event/AzathothAcquisitionHandler.java) |
| `FoxAblazeCreativeTabHandler` | class | — | EventBusSubscriber | 1 | 30 | [FoxAblazeCreativeTabHandler.java](com/foxablazeultimate/event/FoxAblazeCreativeTabHandler.java) |
| `PredationFilterCommand` | class | — | EventBusSubscriber | 4 | 102 | [PredationFilterCommand.java](com/foxablazeultimate/command/PredationFilterCommand.java) |
| `FoxAblazeUltimateModClient` | class | — | EventBusSubscriber | 7 | 103 | [FoxAblazeUltimateModClient.java](com/foxablazeultimate/client/FoxAblazeUltimateModClient.java) |
| `AzathothDragons` | class | — | EventBusSubscriber | 7 | 307 | [AzathothDragons.java](com/foxablazeultimate/ability/skill/azathoth/AzathothDragons.java) |
| `VeldoraSlashViewLockClient` | class | — | EventBusSubscriber | 4 | 75 | [VeldoraSlashViewLockClient.java](com/foxablazeultimate/client/VeldoraSlashViewLockClient.java) |
| `UltimateSkillRequirementDebugCommand` | class | — | — | 39 | 842 | [UltimateSkillRequirementDebugCommand.java](com/foxablazeultimate/command/UltimateSkillRequirementDebugCommand.java) |
| `MixinTickRateManagerYogTimeStop` | class | — | Mixin | 0 | 25 | [MixinTickRateManagerYogTimeStop.java](com/foxablazeultimate/mixin/MixinTickRateManagerYogTimeStop.java) |
| `VelgryndSkill` | class | Skill | — | 74 | 1072 | [VelgryndSkill.java](com/foxablazeultimate/ability/skill/velgrynd/VelgryndSkill.java) |
| `VeldoraSkill` | class | Skill | — | 90 | 1178 | [VeldoraSkill.java](com/foxablazeultimate/ability/skill/veldora/VeldoraSkill.java) |
| `CielTakeoverNavigator` | class | — | — | 70 | 1626 | [CielTakeoverNavigator.java](com/foxablazeultimate/client/ciel/CielTakeoverNavigator.java) |
| `FoxAblazeUltimateMod` | class | — | Mod | 0 | 139 | [FoxAblazeUltimateMod.java](com/foxablazeultimate/FoxAblazeUltimateMod.java) |
| `HellSniperCircleEntity` | class | Entity | — | 50 | 521 | [HellSniperCircleEntity.java](com/foxablazeultimate/entity/HellSniperCircleEntity.java) |
| `MixinItemRendererVelgryndGlint` | class | — | Mixin | 0 | 23 | [MixinItemRendererVelgryndGlint.java](com/foxablazeultimate/mixin/client/MixinItemRendererVelgryndGlint.java) |
| `MixinItemInHandRendererAzathoth` | class | — | Mixin | 0 | 37 | [MixinItemInHandRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinItemInHandRendererAzathoth.java) |
| `MixinItemRendererAzathoth` | class | — | Mixin | 0 | 35 | [MixinItemRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinItemRendererAzathoth.java) |
| `RaphaelSkill` | class | Skill | — | 64 | 935 | [RaphaelSkill.java](com/foxablazeultimate/ability/skill/raphael/RaphaelSkill.java) |
| `ShubNiggurathWarehouse` | class | — | — | 55 | 816 | [ShubNiggurathWarehouse.java](com/foxablazeultimate/ability/skill/shubniggurath/ShubNiggurathWarehouse.java) |
| `BeelzebubStorageMenu` | class | RecipeBookMenu | — | 46 | 595 | [BeelzebubStorageMenu.java](com/foxablazeultimate/menu/BeelzebubStorageMenu.java) |
| `ShubWarehouseScreen` | class | Screen | — | 32 | 407 | [ShubWarehouseScreen.java](com/foxablazeultimate/client/screen/ShubWarehouseScreen.java) |
| `HellSniperBeamEntity` | class | BeamProjectile | — | 31 | 392 | [HellSniperBeamEntity.java](com/foxablazeultimate/entity/HellSniperBeamEntity.java) |
| `SatanaelSkill` | class | Skill | — | 56 | 690 | [SatanaelSkill.java](com/foxablazeultimate/ability/skill/satanael/SatanaelSkill.java) |
| `FortunaSkill` | class | Skill | — | 50 | 682 | [FortunaSkill.java](com/foxablazeultimate/ability/skill/fortuna/FortunaSkill.java) |
| `UltimateSkillProtector` | class | — | — | 38 | 675 | [UltimateSkillProtector.java](com/foxablazeultimate/event/UltimateSkillProtector.java) |
| `SatanaelCombatRecoveryHandler` | class | — | — | 1 | 38 | [SatanaelCombatRecoveryHandler.java](com/foxablazeultimate/event/SatanaelCombatRecoveryHandler.java) |
| `MixinPlayerRendererAzathoth` | class | — | Mixin | 0 | 30 | [MixinPlayerRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinPlayerRendererAzathoth.java) |
| `MixinItemStackVelgryndGlint` | class | — | Mixin | 0 | 19 | [MixinItemStackVelgryndGlint.java](com/foxablazeultimate/mixin/client/MixinItemStackVelgryndGlint.java) |
| `MixinSpatialStorageScreen` | class | AbstractContainerScreen | Mixin | 1 | 63 | [MixinSpatialStorageScreen.java](com/foxablazeultimate/mixin/client/MixinSpatialStorageScreen.java) |
| `MixinVeldoraSlashItemInHandLayer` | class | — | Mixin | 0 | 35 | [MixinVeldoraSlashItemInHandLayer.java](com/foxablazeultimate/mixin/client/MixinVeldoraSlashItemInHandLayer.java) |
| `STExtrasCrystalLockServerMixin` | class | — | Mixin | 12 | 151 | [STExtrasCrystalLockServerMixin.java](com/foxablazeultimate/mixin/stextras/STExtrasCrystalLockServerMixin.java) |
| `UrielSkill` | class | Skill | — | 27 | 638 | [UrielSkill.java](com/foxablazeultimate/ability/skill/uriel/UrielSkill.java) |
| `YogSothothSkill` | class | Skill | — | 45 | 492 | [YogSothothSkill.java](com/foxablazeultimate/ability/skill/yogsothoth/YogSothothSkill.java) |
| `CielAwakening` | class | — | — | 36 | 598 | [CielAwakening.java](com/foxablazeultimate/ability/skill/ciel/CielAwakening.java) |
| `HellSniperSkill` | class | Skill | — | 44 | 537 | [HellSniperSkill.java](com/foxablazeultimate/ability/skill/hellsniper/HellSniperSkill.java) |
| `BeelzebubSkill` | class | Skill | — | 34 | 512 | [BeelzebubSkill.java](com/foxablazeultimate/ability/skill/beelzebub/BeelzebubSkill.java) |
| `MammonSkill` | class | Skill | — | 42 | 553 | [MammonSkill.java](com/foxablazeultimate/ability/skill/mammon/MammonSkill.java) |
| `MixinEnergyHelperCielOptimization` | class | — | Mixin | 0 | 23 | [MixinEnergyHelperCielOptimization.java](com/foxablazeultimate/mixin/MixinEnergyHelperCielOptimization.java) |
| `MixinPredatorMistProjectile` | class | BeamProjectile | Mixin | 0 | 56 | [MixinPredatorMistProjectile.java](com/foxablazeultimate/mixin/MixinPredatorMistProjectile.java) |

## 事件处理（`@SubscribeEvent`，29 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CielEmergencyController` | 10 | `com.foxablazeultimate.ability.skill.ciel` | [CielEmergencyController.java](com/foxablazeultimate/ability/skill/ciel/CielEmergencyController.java) |
| `CielTakeoverInputHandler` | 7 | `com.foxablazeultimate.client` | [CielTakeoverInputHandler.java](com/foxablazeultimate/client/CielTakeoverInputHandler.java) |
| `CielAwakeningHandler` | 6 | `com.foxablazeultimate.ability.skill.ciel` | [CielAwakeningHandler.java](com/foxablazeultimate/ability/skill/ciel/CielAwakeningHandler.java) |
| `FoxAblazeUltimateModClient` | 6 | `com.foxablazeultimate.client` | [FoxAblazeUltimateModClient.java](com/foxablazeultimate/client/FoxAblazeUltimateModClient.java) |
| `YogTimeStopManager` | 4 | `com.foxablazeultimate.ability.skill.yogsothoth` | [YogTimeStopManager.java](com/foxablazeultimate/ability/skill/yogsothoth/YogTimeStopManager.java) |
| `AzathothCombat` | 4 | `com.foxablazeultimate.ability.skill.azathoth` | [AzathothCombat.java](com/foxablazeultimate/ability/skill/azathoth/AzathothCombat.java) |
| `CielTakeoverTerrainHandler` | 3 | `com.foxablazeultimate.ability.skill.ciel` | [CielTakeoverTerrainHandler.java](com/foxablazeultimate/ability/skill/ciel/CielTakeoverTerrainHandler.java) |
| `VeldoraAcquisitionHandler` | 3 | `com.foxablazeultimate.event` | [VeldoraAcquisitionHandler.java](com/foxablazeultimate/event/VeldoraAcquisitionHandler.java) |
| `VeldoraDomainTickHandler` | 3 | `com.foxablazeultimate.ability.skill.veldora` | [VeldoraDomainTickHandler.java](com/foxablazeultimate/ability/skill/veldora/VeldoraDomainTickHandler.java) |
| `BeelzebubFusionTickHandler` | 3 | `com.foxablazeultimate.ability.skill.beelzebub` | [BeelzebubFusionTickHandler.java](com/foxablazeultimate/ability/skill/beelzebub/BeelzebubFusionTickHandler.java) |
| `RaphaelFusionTickHandler` | 3 | `com.foxablazeultimate.ability.skill.raphael` | [RaphaelFusionTickHandler.java](com/foxablazeultimate/ability/skill/raphael/RaphaelFusionTickHandler.java) |
| `VeldoraSlashViewLockClient` | 3 | `com.foxablazeultimate.client` | [VeldoraSlashViewLockClient.java](com/foxablazeultimate/client/VeldoraSlashViewLockClient.java) |
| `SatanaelAcquisitionHandler` | 2 | `com.foxablazeultimate.event` | [SatanaelAcquisitionHandler.java](com/foxablazeultimate/event/SatanaelAcquisitionHandler.java) |
| `WisdomCrystalSyncHandler` | 2 | `com.foxablazeultimate.event` | [WisdomCrystalSyncHandler.java](com/foxablazeultimate/event/WisdomCrystalSyncHandler.java) |
| `VelgryndAcquisitionHandler` | 1 | `com.foxablazeultimate.event` | [VelgryndAcquisitionHandler.java](com/foxablazeultimate/event/VelgryndAcquisitionHandler.java) |
| `BeelzebubCaptureHandler` | 1 | `com.foxablazeultimate.ability.skill.beelzebub` | [BeelzebubCaptureHandler.java](com/foxablazeultimate/ability/skill/beelzebub/BeelzebubCaptureHandler.java) |
| `FortunaAcquisitionHandler` | 1 | `com.foxablazeultimate.event` | [FortunaAcquisitionHandler.java](com/foxablazeultimate/event/FortunaAcquisitionHandler.java) |
| `VelgryndChargeHandler` | 1 | `com.foxablazeultimate.ability.skill.velgrynd` | [VelgryndChargeHandler.java](com/foxablazeultimate/ability/skill/velgrynd/VelgryndChargeHandler.java) |
| `MammonAcquisitionHandler` | 1 | `com.foxablazeultimate.event` | [MammonAcquisitionHandler.java](com/foxablazeultimate/event/MammonAcquisitionHandler.java) |
| `HarvestCrystalDropHandler` | 1 | `com.foxablazeultimate.event` | [HarvestCrystalDropHandler.java](com/foxablazeultimate/event/HarvestCrystalDropHandler.java) |
| `LegacySkillIdMigrationHandler` | 1 | `com.foxablazeultimate.event` | [LegacySkillIdMigrationHandler.java](com/foxablazeultimate/event/LegacySkillIdMigrationHandler.java) |
| `HellSniperAcquisitionHandler` | 1 | `com.foxablazeultimate.event` | [HellSniperAcquisitionHandler.java](com/foxablazeultimate/event/HellSniperAcquisitionHandler.java) |
| `VeldoraDragonEssenceLockHandler` | 1 | `com.foxablazeultimate.event` | [VeldoraDragonEssenceLockHandler.java](com/foxablazeultimate/event/VeldoraDragonEssenceLockHandler.java) |
| `WrathCrystalDropHandler` | 1 | `com.foxablazeultimate.event` | [WrathCrystalDropHandler.java](com/foxablazeultimate/event/WrathCrystalDropHandler.java) |
| `AzathothAcquisitionHandler` | 1 | `com.foxablazeultimate.event` | [AzathothAcquisitionHandler.java](com/foxablazeultimate/event/AzathothAcquisitionHandler.java) |
| `FoxAblazeCreativeTabHandler` | 1 | `com.foxablazeultimate.event` | [FoxAblazeCreativeTabHandler.java](com/foxablazeultimate/event/FoxAblazeCreativeTabHandler.java) |
| `PredationFilterCommand` | 1 | `com.foxablazeultimate.command` | [PredationFilterCommand.java](com/foxablazeultimate/command/PredationFilterCommand.java) |
| `AzathothDragons` | 1 | `com.foxablazeultimate.ability.skill.azathoth` | [AzathothDragons.java](com/foxablazeultimate/ability/skill/azathoth/AzathothDragons.java) |
| `SatanaelCombatRecoveryHandler` | 1 | `com.foxablazeultimate.event` | [SatanaelCombatRecoveryHandler.java](com/foxablazeultimate/event/SatanaelCombatRecoveryHandler.java) |

## Mixin（19 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinTickRateManagerYogTimeStop` | `TickRateManagerYogTimeStop` | [MixinTickRateManagerYogTimeStop.java](com/foxablazeultimate/mixin/MixinTickRateManagerYogTimeStop.java) |
| `MixinItemRendererVelgryndGlint` | `ItemRendererVelgryndGlint` | [MixinItemRendererVelgryndGlint.java](com/foxablazeultimate/mixin/client/MixinItemRendererVelgryndGlint.java) |
| `MixinItemInHandRendererAzathoth` | `ItemInHandRendererAzathoth` | [MixinItemInHandRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinItemInHandRendererAzathoth.java) |
| `MixinItemRendererAzathoth` | `ItemRendererAzathoth` | [MixinItemRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinItemRendererAzathoth.java) |
| `MixinPlayerRendererAzathoth` | `PlayerRendererAzathoth` | [MixinPlayerRendererAzathoth.java](com/foxablazeultimate/mixin/client/MixinPlayerRendererAzathoth.java) |
| `MixinItemStackVelgryndGlint` | `ItemStackVelgryndGlint` | [MixinItemStackVelgryndGlint.java](com/foxablazeultimate/mixin/client/MixinItemStackVelgryndGlint.java) |
| `MixinSpatialStorageScreen` | `SpatialStorageScreen` | [MixinSpatialStorageScreen.java](com/foxablazeultimate/mixin/client/MixinSpatialStorageScreen.java) |
| `MixinVeldoraSlashItemInHandLayer` | `VeldoraSlashItemInHandLayer` | [MixinVeldoraSlashItemInHandLayer.java](com/foxablazeultimate/mixin/client/MixinVeldoraSlashItemInHandLayer.java) |
| `STExtrasCrystalLockServerMixin` | `STExtrasCrystalLockServer` | [STExtrasCrystalLockServerMixin.java](com/foxablazeultimate/mixin/stextras/STExtrasCrystalLockServerMixin.java) |
| `MixinEnergyHelperCielOptimization` | `EnergyHelperCielOptimization` | [MixinEnergyHelperCielOptimization.java](com/foxablazeultimate/mixin/MixinEnergyHelperCielOptimization.java) |
| `MixinPredatorMistProjectile` | `PredatorMistProjectile` | [MixinPredatorMistProjectile.java](com/foxablazeultimate/mixin/MixinPredatorMistProjectile.java) |
| `MixinMagic` | `Magic` | [MixinMagic.java](com/foxablazeultimate/mixin/MixinMagic.java) |
| `MixinTensuraFlyingProjectileFortuna` | `TensuraFlyingProjectileFortuna` | [MixinTensuraFlyingProjectileFortuna.java](com/foxablazeultimate/mixin/MixinTensuraFlyingProjectileFortuna.java) |
| `MixinResetScrollUniqueOwnership` | `ResetScrollUniqueOwnership` | [MixinResetScrollUniqueOwnership.java](com/foxablazeultimate/mixin/MixinResetScrollUniqueOwnership.java) |
| `MixinServerInventoryAzathothCharge` | `ServerInventoryAzathothCharge` | [MixinServerInventoryAzathothCharge.java](com/foxablazeultimate/mixin/MixinServerInventoryAzathothCharge.java) |
| `MixinSkillStorageYogTimeStopActivation` | `SkillStorageYogTimeStopActivation` | [MixinSkillStorageYogTimeStopActivation.java](com/foxablazeultimate/mixin/MixinSkillStorageYogTimeStopActivation.java) |
| `MixinBufferSourceVelgryndGlint` | `BufferSourceVelgryndGlint` | [MixinBufferSourceVelgryndGlint.java](com/foxablazeultimate/mixin/client/MixinBufferSourceVelgryndGlint.java) |
| `MixinVeldoraSlashPlayerAnimation` | `VeldoraSlashPlayerAnimation` | [MixinVeldoraSlashPlayerAnimation.java](com/foxablazeultimate/mixin/client/MixinVeldoraSlashPlayerAnimation.java) |
| `STExtrasIsSkillValidUnlockRaphaelMixin` | `STExtrasIsSkillValidUnlockRaphael` | [STExtrasIsSkillValidUnlockRaphaelMixin.java](com/foxablazeultimate/mixin/stextras/STExtrasIsSkillValidUnlockRaphaelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `AzathothConfig` | — | [AzathothConfig.java](com/foxablazeultimate/config/AzathothConfig.java) |
| `RaphaelConfig` | — | [RaphaelConfig.java](com/foxablazeultimate/config/RaphaelConfig.java) |
| `FortunaConfig` | — | [FortunaConfig.java](com/foxablazeultimate/config/FortunaConfig.java) |
| `BeelzebubConfig` | — | [BeelzebubConfig.java](com/foxablazeultimate/config/BeelzebubConfig.java) |
| `YogSothothConfig` | — | [YogSothothConfig.java](com/foxablazeultimate/config/YogSothothConfig.java) |
| `CielConfig` | — | [CielConfig.java](com/foxablazeultimate/config/CielConfig.java) |
| `HellSniperConfig` | — | [HellSniperConfig.java](com/foxablazeultimate/config/HellSniperConfig.java) |
| `SatanaelConfig` | — | [SatanaelConfig.java](com/foxablazeultimate/config/SatanaelConfig.java) |
| `ShubNiggurathConfig` | — | [ShubNiggurathConfig.java](com/foxablazeultimate/config/ShubNiggurathConfig.java) |
| `FoxAblazeUltimateConfig` | — | [FoxAblazeUltimateConfig.java](com/foxablazeultimate/config/FoxAblazeUltimateConfig.java) |
| `VelgryndConfig` | — | [VelgryndConfig.java](com/foxablazeultimate/config/VelgryndConfig.java) |
| `MammonConfig` | — | [MammonConfig.java](com/foxablazeultimate/config/MammonConfig.java) |
| `VeldoraConfig` | — | [VeldoraConfig.java](com/foxablazeultimate/config/VeldoraConfig.java) |
| `UrielConfig` | — | [UrielConfig.java](com/foxablazeultimate/config/UrielConfig.java) |

## 按包浏览

- `com.foxablazeultimate`（1 类）
- `com.foxablazeultimate.ability.skill.azathoth`（5 类）
- `com.foxablazeultimate.ability.skill.beelzebub`（5 类）
- `com.foxablazeultimate.ability.skill.ciel`（8 类）
- `com.foxablazeultimate.ability.skill.fortuna`（1 类）
- `com.foxablazeultimate.ability.skill.hellsniper`（1 类）
- `com.foxablazeultimate.ability.skill.mammon`（2 类）
- `com.foxablazeultimate.ability.skill.raphael`（4 类）
- `com.foxablazeultimate.ability.skill.satanael`（1 类）
- `com.foxablazeultimate.ability.skill.shubniggurath`（4 类）
- `com.foxablazeultimate.ability.skill.uriel`（1 类）
- `com.foxablazeultimate.ability.skill.veldora`（3 类）
- `com.foxablazeultimate.ability.skill.velgrynd`（3 类）
- `com.foxablazeultimate.ability.skill.yogsothoth`（4 类）
- `com.foxablazeultimate.client`（11 类）
- `com.foxablazeultimate.client.ciel`（1 类）
- `com.foxablazeultimate.client.model`（1 类）
- `com.foxablazeultimate.client.render`（7 类）
- `com.foxablazeultimate.client.screen`（4 类）
- `com.foxablazeultimate.command`（2 类）
- `com.foxablazeultimate.compat.emi`（1 类）
- `com.foxablazeultimate.compat.shouldersurfing`（1 类）
- `com.foxablazeultimate.compat.stextras`（1 类）
- `com.foxablazeultimate.config`（14 类）
- `com.foxablazeultimate.effect`（5 类）
- `com.foxablazeultimate.entity`（4 类）
- `com.foxablazeultimate.event`（15 类）
- `com.foxablazeultimate.item`（3 类）
- `com.foxablazeultimate.item.beelzebub`（2 类）
- `com.foxablazeultimate.loot`（1 类）
- `com.foxablazeultimate.menu`（2 类）
- `com.foxablazeultimate.menu.slot`（1 类）
- `com.foxablazeultimate.mixin`（8 类）
- `com.foxablazeultimate.mixin.client`（9 类）
- `com.foxablazeultimate.mixin.stextras`（3 类）
- `com.foxablazeultimate.network`（14 类）
- `com.foxablazeultimate.predation`（1 类）
- `com.foxablazeultimate.registry`（8 类）
- `com.foxablazeultimate.world`（2 类）
