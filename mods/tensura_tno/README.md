# Tensura TNO（`tensura_tno`）

> jar：`[转生附属]tensura_tno.jar` ｜ 版本：`1.0.5` ｜ 作者：?
> 规模：**379 个类 / 39 个包 / 32281 行** ｜ 事件处理类 37 ｜ Mixin 161 ｜ 注册/管理类 46

**依赖**：`minecraft`、`architectury`、`manascore_storage`、`manascore_skill`、`manascore_race`、`tensura`、`iceandfire`、`kaleidoscope_cookery`、`sophisticatedcore`、`sophisticatedbackpacks`、`stextras`、`tensuramoreskills`、`jade`、`regions_unexplored`、`eldergrove`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.tensura_tno.mixin.client` | 161 | `QuestFormatterLocalizationMixin`、`MissionsScreenLocalizationMixin`、`VanillaBackportSpecialMobRendererCompatMixin` |
| `com.tensura_tno.client.race` | 27 | `OrcRacePlayerRenderHandler`、`SlimeRacePlayerRenderHandler`、`FoxSpiritRacePlayerRenderHandler` |
| `com.tensura_tno.race.fox_spirit` | 25 | `SpiritSummonRidingEventHandler`、`SpiritSummonBreedingEventHandler`、`SpiritSummonTameBehaviorHandler` |
| `com.tensura_tno.event` | 16 | `SpiritSummonProtectionEventHandler`、`RimuruPlushInteractionEvents`、`ShizuFinalKillAdvancementHandler` |
| `com.tensura_tno.registry` | 13 | `TensuraTNOItemTabEvents`、`TensuraTNOBackpackEvents`、`TensuraTNOFoodTabEvents` |
| `com.tensura_tno.compat` | 13 | `CuriosSoulBoundDeathHandler`、`YigdCompat`、`IceAndFireEntityCompat` |
| `com.tensura_tno.client` | 11 | `TensuraTNOClientEvents`、`TensuraTNOBackpackClientEvents`、`LanP2PBridgeClient` |
| `com.tensura_tno.world.spawn` | 9 | `SpawnRelocator`、`BiomeBlacklistConfig`、`SafeSpawnFinder` |
| `com.tensura_tno.client.compat` | 8 | `MoreSkillsElfariaVfxOptimizer`、`MoreSkillsElfariaIceMeshRenderer`、`STExtrasKeenNoseNeoForgeCompat` |
| `com.tensura_tno.ability.skill` | 7 | `SpiritSummonSkill`、`EdoTenseiSkill`、`HumanFormSkill` |
| `com.tensura_tno.network` | 7 | `ContractLittleFoxPackets`、`ReincarnationRulesPackets`、`EdoTenseiPackets` |
| `com.tensura_tno.compat.stextras` | 6 | `FoxBossQuestCompat`、`STExtrasConsumptionCompat`、`SkillUnlockEpCost` |
| `com.tensura_tno.util` | 6 | `PondererScreenIds`、`AdminPrestigeBypass`、`PrestigeGuard` |
| `com.tensura_tno.ftb.tasks` | 5 | `TaskTableItemTask`、`SoulGradeTask`、`ReincarnationEssenceTask` |
| `com.tensura_tno.food` | 5 | `FoodEPManager`、`FoodEPHandler`、`FoodEPConfig` |
| `com.tensura_tno.block` | 5 | `TnoStoveBlock`、`RimuruPlushBlock`、`LuminousPlushBlock` |
| `com.tensura_tno.ftb` | 5 | `STExtrasHelper`、`MasterySeedHelper`、`FTBQuestsResetHelper` |
| `com.tensura_tno.client.renderer` | 5 | `RimuruPlushItemRenderer`、`RimuruPlushShaderCompat`、`FoxSpiritRenderer` |
| `com.tensura_tno.item` | 5 | `RequiredQuestResetScrollItem`、`ForbiddenSummonTomeItem`、`TnoBowlFoodItem` |
| `com.tensura_tno.ai` | 4 | `SubordinateSkillAiEventHandler`、`SubordinateCloneGuard`、`SubordinateSkillAiController` |
| `com.tensura_tno.client.screen` | 4 | `SpiritSummonScreen`、`ContractLittleFoxScreen`、`EdoTenseiScreen` |
| `com.tensura_tno` | 3 | `TensuraTNOMod`、`TensuraTNOCompatConfig`、`QuestNameCache` |
| `com.tensura_tno.compat.jade` | 3 | `TensuraEntitySkillProvider`、`JadeCompatBootstrap`、`TensuraTNOJadePlugin` |
| `com.tensura_tno.command` | 3 | `TNOAdminCommands`、`SummonEPCommand`、`FoodEPCommand` |
| `com.tensura_tno.ftb.rewards` | 3 | `SoulEnergyReward`、`ReincarnationEssenceReward`、`SoulGradeReward` |
| `com.tensura_tno.compat.oneenough` | 2 | `OneEnoughBlockCompat`、`OneEnoughBlockCache` |
| `com.tensura_tno.config` | 2 | `TNOConfigInjector`、`TNOGeneralConfig` |
| `com.tensura_tno.loot` | 2 | `AddForbiddenTomeLootModifier`、`ReplaceIceAndFireSilverLootModifier` |
| `com.tensura_tno.race` | 2 | `SlimeHumanFormState`、`SlimeRaceHelper` |
| `com.tensura_tno.client.model` | 2 | `RimuruPlushItemModel`、`RimuruPlushModel` |
| `com.tensura_tno.recipe` | 2 | `NetheriteToMagisteel`、`NetheriteToMagisteelSerializer` |
| `com.tensura_tno.entity.spirit` | 1 | `FoxSpiritEntity` |
| `com.tensura_tno.config.race` | 1 | `FoxSpiritRaceConfig` |
| `com.tensura_tno.compat.vanillabackport` | 1 | `VanillaBackportSurfaceRuleCompat` |
| `com.tensura_tno.block.entity` | 1 | `RimuruPlushBlockEntity` |
| `com.tensura_tno.client.localization` | 1 | `MoreSkillsHardcodedClientLocalization` |
| `com.tensura_tno.compat.ponder_jei` | 1 | `PonderJeiEmiSwitch` |
| `com.tensura_tno.compat.moreskills` | 1 | `MoreSkillsHardcodedLocalization` |
| `com.tensura_tno.world` | 1 | `TensuraTNOGameRules` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TensuraTNOClientEvents` | class | — | EventBusSubscriber | 5 | 73 | [TensuraTNOClientEvents.java](com/tensura_tno/client/TensuraTNOClientEvents.java) |
| `TensuraTNOItemTabEvents` | class | — | EventBusSubscriber | 1 | 27 | [TensuraTNOItemTabEvents.java](com/tensura_tno/registry/TensuraTNOItemTabEvents.java) |
| `TensuraTNOBackpackClientEvents` | class | — | EventBusSubscriber | 4 | 94 | [TensuraTNOBackpackClientEvents.java](com/tensura_tno/client/TensuraTNOBackpackClientEvents.java) |
| `SpiritSummonRidingEventHandler` | class | — | EventBusSubscriber | 7 | 176 | [SpiritSummonRidingEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonRidingEventHandler.java) |
| `SpiritSummonBreedingEventHandler` | class | — | EventBusSubscriber | 5 | 51 | [SpiritSummonBreedingEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonBreedingEventHandler.java) |
| `SpiritSummonProtectionEventHandler` | class | — | EventBusSubscriber | 5 | 69 | [SpiritSummonProtectionEventHandler.java](com/tensura_tno/event/SpiritSummonProtectionEventHandler.java) |
| `SpiritSummonTameBehaviorHandler` | class | — | EventBusSubscriber | 10 | 239 | [SpiritSummonTameBehaviorHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonTameBehaviorHandler.java) |
| `SummonBonusEventHandler` | class | — | EventBusSubscriber | 4 | 86 | [SummonBonusEventHandler.java](com/tensura_tno/race/fox_spirit/SummonBonusEventHandler.java) |
| `SummonAffinityEventHandler` | class | — | EventBusSubscriber | 4 | 84 | [SummonAffinityEventHandler.java](com/tensura_tno/race/fox_spirit/SummonAffinityEventHandler.java) |
| `FoxKillEventHandler` | class | — | EventBusSubscriber | 3 | 59 | [FoxKillEventHandler.java](com/tensura_tno/race/fox_spirit/FoxKillEventHandler.java) |
| `SpiritSummonRetaliationEventHandler` | class | — | EventBusSubscriber | 3 | 67 | [SpiritSummonRetaliationEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonRetaliationEventHandler.java) |
| `SpiritSummonEPBackflowEventHandler` | class | — | EventBusSubscriber | 4 | 121 | [SpiritSummonEPBackflowEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonEPBackflowEventHandler.java) |
| `SummonDamageRedirectEventHandler` | class | — | EventBusSubscriber | 3 | 88 | [SummonDamageRedirectEventHandler.java](com/tensura_tno/race/fox_spirit/SummonDamageRedirectEventHandler.java) |
| `SubordinateSkillAiEventHandler` | class | — | EventBusSubscriber | 3 | 56 | [SubordinateSkillAiEventHandler.java](com/tensura_tno/ai/SubordinateSkillAiEventHandler.java) |
| `RimuruPlushInteractionEvents` | class | — | EventBusSubscriber | 1 | 29 | [RimuruPlushInteractionEvents.java](com/tensura_tno/event/RimuruPlushInteractionEvents.java) |
| `ShizuFinalKillAdvancementHandler` | class | — | EventBusSubscriber | 14 | 250 | [ShizuFinalKillAdvancementHandler.java](com/tensura_tno/event/ShizuFinalKillAdvancementHandler.java) |
| `ContractLittleFoxEvents` | class | — | EventBusSubscriber | 3 | 93 | [ContractLittleFoxEvents.java](com/tensura_tno/event/ContractLittleFoxEvents.java) |
| `SpiritSummonDropsEventHandler` | class | — | EventBusSubscriber | 3 | 54 | [SpiritSummonDropsEventHandler.java](com/tensura_tno/event/SpiritSummonDropsEventHandler.java) |
| `TensuraTNOBackpackEvents` | class | — | EventBusSubscriber | 2 | 45 | [TensuraTNOBackpackEvents.java](com/tensura_tno/registry/TensuraTNOBackpackEvents.java) |
| `TensuraTNOFoodTabEvents` | class | — | EventBusSubscriber | 1 | 39 | [TensuraTNOFoodTabEvents.java](com/tensura_tno/registry/TensuraTNOFoodTabEvents.java) |
| `MysticismRestrictedSkillHandler` | class | — | EventBusSubscriber | 8 | 94 | [MysticismRestrictedSkillHandler.java](com/tensura_tno/event/MysticismRestrictedSkillHandler.java) |
| `IceAndFireDragonRemainsHandler` | class | — | EventBusSubscriber | 8 | 127 | [IceAndFireDragonRemainsHandler.java](com/tensura_tno/event/IceAndFireDragonRemainsHandler.java) |
| `FoxSpiritPlayerSizeHandler` | class | — | EventBusSubscriber | 1 | 25 | [FoxSpiritPlayerSizeHandler.java](com/tensura_tno/race/fox_spirit/FoxSpiritPlayerSizeHandler.java) |
| `OrcTrainingDummyGrabHandler` | class | — | EventBusSubscriber | 1 | 21 | [OrcTrainingDummyGrabHandler.java](com/tensura_tno/event/OrcTrainingDummyGrabHandler.java) |
| `LuminousPlushDropsHandler` | class | — | EventBusSubscriber | 1 | 38 | [LuminousPlushDropsHandler.java](com/tensura_tno/event/LuminousPlushDropsHandler.java) |
| `RimuruPlushDropsHandler` | class | — | EventBusSubscriber | 1 | 38 | [RimuruPlushDropsHandler.java](com/tensura_tno/event/RimuruPlushDropsHandler.java) |
| `MagicOreSenseAcquisitionHandler` | class | — | EventBusSubscriber | 3 | 58 | [MagicOreSenseAcquisitionHandler.java](com/tensura_tno/event/MagicOreSenseAcquisitionHandler.java) |
| `OneEnoughBlockCompat` | class | OneEnoughBlockCache.BlockRegistry<Bl | EventBusSubscriber | 5 | 61 | [OneEnoughBlockCompat.java](com/tensura_tno/compat/oneenough/OneEnoughBlockCompat.java) |
| `LanP2PBridgeClient` | class | — | EventBusSubscriber | 10 | 279 | [LanP2PBridgeClient.java](com/tensura_tno/client/LanP2PBridgeClient.java) |
| `OrcRacePlayerRenderHandler` | class | — | EventBusSubscriber | 2 | 45 | [OrcRacePlayerRenderHandler.java](com/tensura_tno/client/race/OrcRacePlayerRenderHandler.java) |
| `SlimeRacePlayerRenderHandler` | class | — | EventBusSubscriber | 2 | 45 | [SlimeRacePlayerRenderHandler.java](com/tensura_tno/client/race/SlimeRacePlayerRenderHandler.java) |
| `FoxSpiritRacePlayerRenderHandler` | class | — | EventBusSubscriber | 2 | 46 | [FoxSpiritRacePlayerRenderHandler.java](com/tensura_tno/client/race/FoxSpiritRacePlayerRenderHandler.java) |
| `LizardmanRacePlayerRenderHandler` | class | — | EventBusSubscriber | 2 | 45 | [LizardmanRacePlayerRenderHandler.java](com/tensura_tno/client/race/LizardmanRacePlayerRenderHandler.java) |
| `MagicSenseShaderNightVisionCompat` | class | — | EventBusSubscriber | 9 | 136 | [MagicSenseShaderNightVisionCompat.java](com/tensura_tno/client/MagicSenseShaderNightVisionCompat.java) |
| `QuestFormatterLocalizationMixin` | class | — | Mixin | 27 | 642 | [QuestFormatterLocalizationMixin.java](com/tensura_tno/mixin/client/QuestFormatterLocalizationMixin.java) |
| `SubordinateCloneGuard` | class | — | EventBusSubscriber | 2 | 41 | [SubordinateCloneGuard.java](com/tensura_tno/ai/SubordinateCloneGuard.java) |
| `BossesRiseCinematicWatchdog` | class | — | EventBusSubscriber | 3 | 101 | [BossesRiseCinematicWatchdog.java](com/tensura_tno/client/BossesRiseCinematicWatchdog.java) |
| `MissionsScreenLocalizationMixin` | class | — | Mixin、Pseudo | 22 | 371 | [MissionsScreenLocalizationMixin.java](com/tensura_tno/mixin/client/MissionsScreenLocalizationMixin.java) |
| `FoxSpiritEntity` | class | Fox | — | 49 | 653 | [FoxSpiritEntity.java](com/tensura_tno/entity/spirit/FoxSpiritEntity.java) |
| `SubordinateSkillAiController` | class | — | — | 59 | 1425 | [SubordinateSkillAiController.java](com/tensura_tno/ai/SubordinateSkillAiController.java) |
| `FoxSpiritRaceConfig` | class | Default | — | 60 | 321 | [FoxSpiritRaceConfig.java](com/tensura_tno/config/race/FoxSpiritRaceConfig.java) |
| `TensuraTNOMod` | class | — | Mod | 1 | 155 | [TensuraTNOMod.java](com/tensura_tno/TensuraTNOMod.java) |
| `VanillaBackportSpecialMobRendererCompatMixin` | class | — | Mixin、Pseudo | 0 | 45 | [VanillaBackportSpecialMobRendererCompatMixin.java](com/tensura_tno/mixin/client/VanillaBackportSpecialMobRendererCompatMixin.java) |
| `ReincarnationScreenKeepMenuForRulesMixin` | class | — | Mixin | 0 | 20 | [ReincarnationScreenKeepMenuForRulesMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenKeepMenuForRulesMixin.java) |
| `SpiritSummonSkill` | class | Skill | — | 38 | 875 | [SpiritSummonSkill.java](com/tensura_tno/ability/skill/SpiritSummonSkill.java) |
| `SlimePredationHumanFormHandler` | class | — | — | 5 | 108 | [SlimePredationHumanFormHandler.java](com/tensura_tno/event/SlimePredationHumanFormHandler.java) |
| `TaskTableItemTask` | class | Task | — | 40 | 543 | [TaskTableItemTask.java](com/tensura_tno/ftb/tasks/TaskTableItemTask.java) |
| `ContractLittleFoxPackets` | class | CustomPacketPayload | — | 23 | 519 | [ContractLittleFoxPackets.java](com/tensura_tno/network/ContractLittleFoxPackets.java) |
| `STExtrasLegacyConsumeEventMixin` | class | — | Mixin、Pseudo | 0 | 28 | [STExtrasLegacyConsumeEventMixin.java](com/tensura_tno/mixin/client/STExtrasLegacyConsumeEventMixin.java) |
| `CaptivatorSkillNetworkFixMixin` | class | — | Mixin | 0 | 28 | [CaptivatorSkillNetworkFixMixin.java](com/tensura_tno/mixin/client/CaptivatorSkillNetworkFixMixin.java) |
| `TwilightForestSpiritSummonLootMixin` | class | — | Mixin、Pseudo | 0 | 21 | [TwilightForestSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/TwilightForestSpiritSummonLootMixin.java) |
| `ObsidilithSpiritSummonLootMixin` | class | — | Mixin、Pseudo | 0 | 21 | [ObsidilithSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/ObsidilithSpiritSummonLootMixin.java) |
| `ScreenHelperOpenContainerFixMixin` | class | — | Mixin | 0 | 19 | [ScreenHelperOpenContainerFixMixin.java](com/tensura_tno/mixin/client/ScreenHelperOpenContainerFixMixin.java) |
| `AzazelSpiritSummonLootMixin` | class | — | Mixin、Pseudo | 0 | 39 | [AzazelSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/AzazelSpiritSummonLootMixin.java) |
| `PrestigeScreenBackgroundMixin` | class | — | Mixin | 5 | 54 | [PrestigeScreenBackgroundMixin.java](com/tensura_tno/mixin/client/PrestigeScreenBackgroundMixin.java) |
| `SpiritSummonScreen` | class | Screen | — | 29 | 407 | [SpiritSummonScreen.java](com/tensura_tno/client/screen/SpiritSummonScreen.java) |
| `ContractLittleFoxScreen` | class | Screen | — | 27 | 469 | [ContractLittleFoxScreen.java](com/tensura_tno/client/screen/ContractLittleFoxScreen.java) |
| `TensuraLivingEntitySenseMixin` | class | — | Mixin、Pseudo | 0 | 24 | [TensuraLivingEntitySenseMixin.java](com/tensura_tno/mixin/client/TensuraLivingEntitySenseMixin.java) |
| `FTBHiddenChapterGroupScreenMixin` | class | — | Mixin、Pseudo | 1 | 39 | [FTBHiddenChapterGroupScreenMixin.java](com/tensura_tno/mixin/client/FTBHiddenChapterGroupScreenMixin.java) |
| `ReincarnationScreenRulesButtonMixin` | class | — | Mixin | 0 | 104 | [ReincarnationScreenRulesButtonMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenRulesButtonMixin.java) |

## 事件处理（`@SubscribeEvent`，37 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SpiritSummonProtectionEventHandler` | 5 | `com.tensura_tno.event` | [SpiritSummonProtectionEventHandler.java](com/tensura_tno/event/SpiritSummonProtectionEventHandler.java) |
| `TensuraTNOClientEvents` | 3 | `com.tensura_tno.client` | [TensuraTNOClientEvents.java](com/tensura_tno/client/TensuraTNOClientEvents.java) |
| `TensuraTNOBackpackClientEvents` | 3 | `com.tensura_tno.client` | [TensuraTNOBackpackClientEvents.java](com/tensura_tno/client/TensuraTNOBackpackClientEvents.java) |
| `SpiritSummonRidingEventHandler` | 3 | `com.tensura_tno.race.fox_spirit` | [SpiritSummonRidingEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonRidingEventHandler.java) |
| `SpiritSummonBreedingEventHandler` | 3 | `com.tensura_tno.race.fox_spirit` | [SpiritSummonBreedingEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonBreedingEventHandler.java) |
| `SpiritSummonTameBehaviorHandler` | 3 | `com.tensura_tno.race.fox_spirit` | [SpiritSummonTameBehaviorHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonTameBehaviorHandler.java) |
| `SubordinateSkillAiEventHandler` | 3 | `com.tensura_tno.ai` | [SubordinateSkillAiEventHandler.java](com/tensura_tno/ai/SubordinateSkillAiEventHandler.java) |
| `SpiritSummonDropsEventHandler` | 3 | `com.tensura_tno.event` | [SpiritSummonDropsEventHandler.java](com/tensura_tno/event/SpiritSummonDropsEventHandler.java) |
| `MysticismRestrictedSkillHandler` | 3 | `com.tensura_tno.event` | [MysticismRestrictedSkillHandler.java](com/tensura_tno/event/MysticismRestrictedSkillHandler.java) |
| `IceAndFireDragonRemainsHandler` | 3 | `com.tensura_tno.event` | [IceAndFireDragonRemainsHandler.java](com/tensura_tno/event/IceAndFireDragonRemainsHandler.java) |
| `SummonAffinityEventHandler` | 2 | `com.tensura_tno.race.fox_spirit` | [SummonAffinityEventHandler.java](com/tensura_tno/race/fox_spirit/SummonAffinityEventHandler.java) |
| `ShizuFinalKillAdvancementHandler` | 2 | `com.tensura_tno.event` | [ShizuFinalKillAdvancementHandler.java](com/tensura_tno/event/ShizuFinalKillAdvancementHandler.java) |
| `TensuraTNOBackpackEvents` | 2 | `com.tensura_tno.registry` | [TensuraTNOBackpackEvents.java](com/tensura_tno/registry/TensuraTNOBackpackEvents.java) |
| `OneEnoughBlockCompat` | 2 | `com.tensura_tno.compat.oneenough` | [OneEnoughBlockCompat.java](com/tensura_tno/compat/oneenough/OneEnoughBlockCompat.java) |
| `LanP2PBridgeClient` | 2 | `com.tensura_tno.client` | [LanP2PBridgeClient.java](com/tensura_tno/client/LanP2PBridgeClient.java) |
| `OrcRacePlayerRenderHandler` | 2 | `com.tensura_tno.client.race` | [OrcRacePlayerRenderHandler.java](com/tensura_tno/client/race/OrcRacePlayerRenderHandler.java) |
| `SlimeRacePlayerRenderHandler` | 2 | `com.tensura_tno.client.race` | [SlimeRacePlayerRenderHandler.java](com/tensura_tno/client/race/SlimeRacePlayerRenderHandler.java) |
| `FoxSpiritRacePlayerRenderHandler` | 2 | `com.tensura_tno.client.race` | [FoxSpiritRacePlayerRenderHandler.java](com/tensura_tno/client/race/FoxSpiritRacePlayerRenderHandler.java) |
| `LizardmanRacePlayerRenderHandler` | 2 | `com.tensura_tno.client.race` | [LizardmanRacePlayerRenderHandler.java](com/tensura_tno/client/race/LizardmanRacePlayerRenderHandler.java) |
| `TensuraTNOItemTabEvents` | 1 | `com.tensura_tno.registry` | [TensuraTNOItemTabEvents.java](com/tensura_tno/registry/TensuraTNOItemTabEvents.java) |
| `SummonBonusEventHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [SummonBonusEventHandler.java](com/tensura_tno/race/fox_spirit/SummonBonusEventHandler.java) |
| `FoxKillEventHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [FoxKillEventHandler.java](com/tensura_tno/race/fox_spirit/FoxKillEventHandler.java) |
| `SpiritSummonRetaliationEventHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [SpiritSummonRetaliationEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonRetaliationEventHandler.java) |
| `SpiritSummonEPBackflowEventHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [SpiritSummonEPBackflowEventHandler.java](com/tensura_tno/race/fox_spirit/SpiritSummonEPBackflowEventHandler.java) |
| `SummonDamageRedirectEventHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [SummonDamageRedirectEventHandler.java](com/tensura_tno/race/fox_spirit/SummonDamageRedirectEventHandler.java) |
| `RimuruPlushInteractionEvents` | 1 | `com.tensura_tno.event` | [RimuruPlushInteractionEvents.java](com/tensura_tno/event/RimuruPlushInteractionEvents.java) |
| `ContractLittleFoxEvents` | 1 | `com.tensura_tno.event` | [ContractLittleFoxEvents.java](com/tensura_tno/event/ContractLittleFoxEvents.java) |
| `TensuraTNOFoodTabEvents` | 1 | `com.tensura_tno.registry` | [TensuraTNOFoodTabEvents.java](com/tensura_tno/registry/TensuraTNOFoodTabEvents.java) |
| `FoxSpiritPlayerSizeHandler` | 1 | `com.tensura_tno.race.fox_spirit` | [FoxSpiritPlayerSizeHandler.java](com/tensura_tno/race/fox_spirit/FoxSpiritPlayerSizeHandler.java) |
| `OrcTrainingDummyGrabHandler` | 1 | `com.tensura_tno.event` | [OrcTrainingDummyGrabHandler.java](com/tensura_tno/event/OrcTrainingDummyGrabHandler.java) |
| `LuminousPlushDropsHandler` | 1 | `com.tensura_tno.event` | [LuminousPlushDropsHandler.java](com/tensura_tno/event/LuminousPlushDropsHandler.java) |
| `RimuruPlushDropsHandler` | 1 | `com.tensura_tno.event` | [RimuruPlushDropsHandler.java](com/tensura_tno/event/RimuruPlushDropsHandler.java) |
| `MagicOreSenseAcquisitionHandler` | 1 | `com.tensura_tno.event` | [MagicOreSenseAcquisitionHandler.java](com/tensura_tno/event/MagicOreSenseAcquisitionHandler.java) |
| `MagicSenseShaderNightVisionCompat` | 1 | `com.tensura_tno.client` | [MagicSenseShaderNightVisionCompat.java](com/tensura_tno/client/MagicSenseShaderNightVisionCompat.java) |
| `SubordinateCloneGuard` | 1 | `com.tensura_tno.ai` | [SubordinateCloneGuard.java](com/tensura_tno/ai/SubordinateCloneGuard.java) |
| `BossesRiseCinematicWatchdog` | 1 | `com.tensura_tno.client` | [BossesRiseCinematicWatchdog.java](com/tensura_tno/client/BossesRiseCinematicWatchdog.java) |
| `SlimePredationHumanFormHandler` | 1 | `com.tensura_tno.event` | [SlimePredationHumanFormHandler.java](com/tensura_tno/event/SlimePredationHumanFormHandler.java) |

## Mixin（161 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `QuestFormatterLocalizationMixin` | `QuestFormatterLocalization` | [QuestFormatterLocalizationMixin.java](com/tensura_tno/mixin/client/QuestFormatterLocalizationMixin.java) |
| `MissionsScreenLocalizationMixin` | `MissionsScreenLocalization` | [MissionsScreenLocalizationMixin.java](com/tensura_tno/mixin/client/MissionsScreenLocalizationMixin.java) |
| `VanillaBackportSpecialMobRendererCompatMixin` | `VanillaBackportSpecialMobRendererCompat` | [VanillaBackportSpecialMobRendererCompatMixin.java](com/tensura_tno/mixin/client/VanillaBackportSpecialMobRendererCompatMixin.java) |
| `ReincarnationScreenKeepMenuForRulesMixin` | `ReincarnationScreenKeepMenuForRules` | [ReincarnationScreenKeepMenuForRulesMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenKeepMenuForRulesMixin.java) |
| `STExtrasLegacyConsumeEventMixin` | `STExtrasLegacyConsumeEvent` | [STExtrasLegacyConsumeEventMixin.java](com/tensura_tno/mixin/client/STExtrasLegacyConsumeEventMixin.java) |
| `CaptivatorSkillNetworkFixMixin` | `CaptivatorSkillNetworkFix` | [CaptivatorSkillNetworkFixMixin.java](com/tensura_tno/mixin/client/CaptivatorSkillNetworkFixMixin.java) |
| `TwilightForestSpiritSummonLootMixin` | `TwilightForestSpiritSummonLoot` | [TwilightForestSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/TwilightForestSpiritSummonLootMixin.java) |
| `ObsidilithSpiritSummonLootMixin` | `ObsidilithSpiritSummonLoot` | [ObsidilithSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/ObsidilithSpiritSummonLootMixin.java) |
| `ScreenHelperOpenContainerFixMixin` | `ScreenHelperOpenContainerFix` | [ScreenHelperOpenContainerFixMixin.java](com/tensura_tno/mixin/client/ScreenHelperOpenContainerFixMixin.java) |
| `AzazelSpiritSummonLootMixin` | `AzazelSpiritSummonLoot` | [AzazelSpiritSummonLootMixin.java](com/tensura_tno/mixin/client/AzazelSpiritSummonLootMixin.java) |
| `PrestigeScreenBackgroundMixin` | `PrestigeScreenBackground` | [PrestigeScreenBackgroundMixin.java](com/tensura_tno/mixin/client/PrestigeScreenBackgroundMixin.java) |
| `TensuraLivingEntitySenseMixin` | `TensuraLivingEntitySense` | [TensuraLivingEntitySenseMixin.java](com/tensura_tno/mixin/client/TensuraLivingEntitySenseMixin.java) |
| `FTBHiddenChapterGroupScreenMixin` | `FTBHiddenChapterGroupScreen` | [FTBHiddenChapterGroupScreenMixin.java](com/tensura_tno/mixin/client/FTBHiddenChapterGroupScreenMixin.java) |
| `ReincarnationScreenRulesButtonMixin` | `ReincarnationScreenRulesButton` | [ReincarnationScreenRulesButtonMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenRulesButtonMixin.java) |
| `ResetScrollSoulGradeBlockMixin` | `ResetScrollSoulGradeBlock` | [ResetScrollSoulGradeBlockMixin.java](com/tensura_tno/mixin/client/ResetScrollSoulGradeBlockMixin.java) |
| `HakiSkillLegacyCompatMixin` | `HakiSkillLegacyCompat` | [HakiSkillLegacyCompatMixin.java](com/tensura_tno/mixin/client/HakiSkillLegacyCompatMixin.java) |
| `SkillLockResistanceBlockMixin` | `SkillLockResistanceBlock` | [SkillLockResistanceBlockMixin.java](com/tensura_tno/mixin/client/SkillLockResistanceBlockMixin.java) |
| `BeyondItemRewardLocaleMixin` | `BeyondItemRewardLocale` | [BeyondItemRewardLocaleMixin.java](com/tensura_tno/mixin/client/BeyondItemRewardLocaleMixin.java) |
| `ReincarnationMenuAccessor` | `ReincarnationMenuAccessor` | [ReincarnationMenuAccessor.java](com/tensura_tno/mixin/client/ReincarnationMenuAccessor.java) |
| `ReincarnationScreenSlimePreviewSizeMixin` | `ReincarnationScreenSlimePreviewSize` | [ReincarnationScreenSlimePreviewSizeMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenSlimePreviewSizeMixin.java) |
| `OneEnoughBlockReplacementCacheMixin` | `OneEnoughBlockReplacementCache` | [OneEnoughBlockReplacementCacheMixin.java](com/tensura_tno/mixin/client/OneEnoughBlockReplacementCacheMixin.java) |
| `ReincarnationScreenRimuruCheckboxMixin` | `ReincarnationScreenRimuruCheckbox` | [ReincarnationScreenRimuruCheckboxMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenRimuruCheckboxMixin.java) |
| `RaceMessagesLocalizationMixin` | `RaceMessagesLocalization` | [RaceMessagesLocalizationMixin.java](com/tensura_tno/mixin/client/RaceMessagesLocalizationMixin.java) |
| `SkillLockScreenEpCostMixin` | `SkillLockScreenEpCost` | [SkillLockScreenEpCostMixin.java](com/tensura_tno/mixin/client/SkillLockScreenEpCostMixin.java) |
| `KumodesuFirstPersonItemMixin` | `KumodesuFirstPersonItem` | [KumodesuFirstPersonItemMixin.java](com/tensura_tno/mixin/client/KumodesuFirstPersonItemMixin.java) |
| `SpiritSummonNamingMenuMixin` | `SpiritSummonNamingMenu` | [SpiritSummonNamingMenuMixin.java](com/tensura_tno/mixin/client/SpiritSummonNamingMenuMixin.java) |
| `SkillLockScreenLocalizationMixin` | `SkillLockScreenLocalization` | [SkillLockScreenLocalizationMixin.java](com/tensura_tno/mixin/client/SkillLockScreenLocalizationMixin.java) |
| `MainScreenSlimeHumanFormCheckboxMixin` | `MainScreenSlimeHumanFormCheckbox` | [MainScreenSlimeHumanFormCheckboxMixin.java](com/tensura_tno/mixin/client/MainScreenSlimeHumanFormCheckboxMixin.java) |
| `SellingBinItemStackAdapterFixMixin` | `SellingBinItemStackAdapterFix` | [SellingBinItemStackAdapterFixMixin.java](com/tensura_tno/mixin/client/SellingBinItemStackAdapterFixMixin.java) |
| `ReincarnationScreenSlimeBgMixin` | `ReincarnationScreenSlimeBg` | [ReincarnationScreenSlimeBgMixin.java](com/tensura_tno/mixin/client/ReincarnationScreenSlimeBgMixin.java) |
| `SkillLockTooltipLocalizationMixin` | `SkillLockTooltipLocalization` | [SkillLockTooltipLocalizationMixin.java](com/tensura_tno/mixin/client/SkillLockTooltipLocalizationMixin.java) |
| `MoreSkillsBlackBloodFogOptimizationMixin` | `MoreSkillsBlackBloodFogOptimization` | [MoreSkillsBlackBloodFogOptimizationMixin.java](com/tensura_tno/mixin/client/MoreSkillsBlackBloodFogOptimizationMixin.java) |
| `BeyondCoinsRewardLocaleMixin` | `BeyondCoinsRewardLocale` | [BeyondCoinsRewardLocaleMixin.java](com/tensura_tno/mixin/client/BeyondCoinsRewardLocaleMixin.java) |
| `DragonRemainsSubordinateTargetMixin` | `DragonRemainsSubordinateTarget` | [DragonRemainsSubordinateTargetMixin.java](com/tensura_tno/mixin/client/DragonRemainsSubordinateTargetMixin.java) |
| `SkillStudyBookRandomMessageMixin` | `SkillStudyBookRandomMessage` | [SkillStudyBookRandomMessageMixin.java](com/tensura_tno/mixin/client/SkillStudyBookRandomMessageMixin.java) |
| `BeyondSkillRewardLocaleMixin` | `BeyondSkillRewardLocale` | [BeyondSkillRewardLocaleMixin.java](com/tensura_tno/mixin/client/BeyondSkillRewardLocaleMixin.java) |
| `SubordinateAbilityPresetMixin` | `SubordinateAbilityPreset` | [SubordinateAbilityPresetMixin.java](com/tensura_tno/mixin/client/SubordinateAbilityPresetMixin.java) |
| `MoreSkillsElfariaVfxFrameMixin` | `MoreSkillsElfariaVfxFrame` | [MoreSkillsElfariaVfxFrameMixin.java](com/tensura_tno/mixin/client/MoreSkillsElfariaVfxFrameMixin.java) |
| `MowzieAvoidGoalOptMixin` | `MowzieAvoidGoalOpt` | [MowzieAvoidGoalOptMixin.java](com/tensura_tno/mixin/client/MowzieAvoidGoalOptMixin.java) |
| `MoreSkillsSatellaMiasmaSurfaceOptimizationMixin` | `MoreSkillsSatellaMiasmaSurfaceOptimization` | [MoreSkillsSatellaMiasmaSurfaceOptimizationMixin.java](com/tensura_tno/mixin/client/MoreSkillsSatellaMiasmaSurfaceOptimizationMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FoxSpiritRaceConfig` | — | [FoxSpiritRaceConfig.java](com/tensura_tno/config/race/FoxSpiritRaceConfig.java) |
| `TensuraTNOCompatConfig` | — | [TensuraTNOCompatConfig.java](com/tensura_tno/TensuraTNOCompatConfig.java) |
| `BiomeBlacklistConfig` | — | [BiomeBlacklistConfig.java](com/tensura_tno/world/spawn/BiomeBlacklistConfig.java) |
| `FoodEPConfig` | — | [FoodEPConfig.java](com/tensura_tno/food/FoodEPConfig.java) |
| `TNOConfigInjector` | — | [TNOConfigInjector.java](com/tensura_tno/config/TNOConfigInjector.java) |
| `TNOGeneralConfig` | — | [TNOGeneralConfig.java](com/tensura_tno/config/TNOGeneralConfig.java) |

## 按包浏览

- `com.tensura_tno`（3 类）
- `com.tensura_tno.ability.skill`（7 类）
- `com.tensura_tno.ai`（4 类）
- `com.tensura_tno.block`（5 类）
- `com.tensura_tno.block.entity`（1 类）
- `com.tensura_tno.client`（11 类）
- `com.tensura_tno.client.compat`（8 类）
- `com.tensura_tno.client.localization`（1 类）
- `com.tensura_tno.client.model`（2 类）
- `com.tensura_tno.client.race`（27 类）
- `com.tensura_tno.client.renderer`（5 类）
- `com.tensura_tno.client.screen`（4 类）
- `com.tensura_tno.command`（3 类）
- `com.tensura_tno.compat`（13 类）
- `com.tensura_tno.compat.jade`（3 类）
- `com.tensura_tno.compat.moreskills`（1 类）
- `com.tensura_tno.compat.oneenough`（2 类）
- `com.tensura_tno.compat.ponder_jei`（1 类）
- `com.tensura_tno.compat.stextras`（6 类）
- `com.tensura_tno.compat.vanillabackport`（1 类）
- `com.tensura_tno.config`（2 类）
- `com.tensura_tno.config.race`（1 类）
- `com.tensura_tno.entity.spirit`（1 类）
- `com.tensura_tno.event`（16 类）
- `com.tensura_tno.food`（5 类）
- `com.tensura_tno.ftb`（5 类）
- `com.tensura_tno.ftb.rewards`（3 类）
- `com.tensura_tno.ftb.tasks`（5 类）
- `com.tensura_tno.item`（5 类）
- `com.tensura_tno.loot`（2 类）
- `com.tensura_tno.mixin.client`（161 类）
- `com.tensura_tno.network`（7 类）
- `com.tensura_tno.race`（2 类）
- `com.tensura_tno.race.fox_spirit`（25 类）
- `com.tensura_tno.recipe`（2 类）
- `com.tensura_tno.registry`（13 类）
- `com.tensura_tno.util`（6 类）
- `com.tensura_tno.world`（1 类）
- `com.tensura_tno.world.spawn`（9 类）
