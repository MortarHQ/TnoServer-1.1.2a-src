# TensuraMoreSkills（`tensuramoreskills`）

> jar：`[转生附属]tensuramoreskills.jar` ｜ 版本：`0.0.4.9.2` ｜ 作者：wal_bos
> 规模：**568 个类 / 62 个包 / 213645 行** ｜ 事件处理类 28 ｜ Mixin 2 ｜ 注册/管理类 91

**依赖**：`neoforge`、`minecraft`、`tensura`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.wal_bos.moreskills.net.msg` | 85 | `GreedCasinoGamblePayload`、`ElfariaIcemaidenVfxPayload`、`ArthurAvalonVfxPacketSender` |
| `com.github.wal_bos.moreskills.client.fx` | 84 | `MichaelJerezVfxClient`、`AsmodayBlackHoleClient`、`MichaelHalberdShotVfxClient` |
| `com.github.wal_bos.moreskills.handler` | 42 | `BlackBloodConquestHandler`、`BlackBloodDomainHandler`、`BlackBloodEcologyHandler` |
| `com.github.wal_bos.moreskills.world.entity` | 26 | `MichaelManasEntity`、`ArsWeissCloneEntity`、`FeldwayAngelEntity` |
| `com.github.wal_bos.moreskills.handler.avalon` | 23 | `AvalonKingdomHandler`、`AvalonSovereignHeavensFallHandler`、`AvalonSovereignJudgementHandler` |
| `com.github.wal_bos.moreskills.ability.skill.unique` | 21 | `AdaptabilityHooks`、`VoidArchiveForgeData`、`VoidArchiveSkill` |
| `com.github.wal_bos.moreskills.client.fx.avalon` | 18 | `AvalonDivineDisintegrationVfxClient`、`AvalonSovereignJudgementVfxClient`、`AvalonEternalVictoryVfxClient` |
| `com.github.wal_bos.moreskills.handler.asmoday` | 17 | `AsmodayPassives`、`AsmodaySpatialAbsorption`、`AsmodayPrisonRealmManager` |
| `com.github.wal_bos.moreskills.client.screen` | 16 | `FeldwayRewardScreen`、`NirvanaArchiveScreen`、`VoidArchiveForgeScreen` |
| `com.github.wal_bos.moreskills.ability.skill.ultimate` | 16 | `CaedrosGodOfConquestSkill`、`PainLordOfSixPathsSkill`、`DivineGeneralMahoragaSkill` |
| `com.github.wal_bos.moreskills.handler.phainon` | 12 | `PhainonHandler`、`PhainonCommand`、`PhainonProgress` |
| `com.github.wal_bos.moreskills.world.block` | 12 | `PathsPortalBlock`、`InfectedWoodBlock`、`BloodFlowerRootBlock` |
| `com.github.wal_bos.moreskills.client.renderer` | 11 | `ArthurAfkAnimationRenderer`、`MichaelAfkAnimationRenderer`、`ModEntityRenderers` |
| `com.github.wal_bos.moreskills.handler.arthur` | 10 | `ArthurRuntimeHandler`、`ArthurLightDominationHandler`、`ArthurExcaliburPromisedVictoryHandler` |
| `com.github.wal_bos.moreskills.menu` | 10 | `VoidArchiveForgeMenu`、`VoidArchiveLibraryMenu`、`GodlyWorkMenu` |
| `com.github.wal_bos.moreskills.handler.michael` | 10 | `MichaelActions`、`MichaelSegvaSealing`、`MichaelShifuruRelease` |
| `com.github.wal_bos.moreskills.world.entity.feldway` | 9 | `AwakenedFeldwayDefense`、`AwakenedFeldwayCombat`、`AxisJudgmentAttack` |
| `com.github.wal_bos.moreskills.command` | 8 | `ClientVfxCommand`、`ParasiteCommand`、`RaphaelCommand` |
| `com.github.wal_bos.moreskills.ability.skill.shades` | 8 | `NaberiusCombatStanceHandler`、`NaberiusSkill`、`NaberiusFusionCombatController` |
| `com.github.wal_bos.moreskills.client.fx.arthur` | 8 | `ArthurHolyDisintegrationVfxClient`、`ArthurPromisedVictoryVfxClient`、`ArthurLightDominationVfxClient` |
| `com.github.wal_bos.moreskills.handler.Istaroth` | 8 | `IstarothActions`、`IstarothTimeHandler`、`IstarothPassives` |
| `com.github.wal_bos.moreskills.handler.ronova` | 8 | `RonovaPlayerHandler`、`RonovaDeathHandler`、`RonovaSoulHandler` |
| `com.github.wal_bos.moreskills.world.entity.avalon` | 7 | `AvalonNovariMagic`、`AvalonNovariPhaseData`、`NovariBlackWhiteAttacks` |
| `com.github.wal_bos.moreskills.item` | 6 | `FeldwayAngelSummonerItem`、`GeneticSyringeItem`、`AsmodayPrisonCubeItem` |
| `com.github.wal_bos.moreskills.client` | 6 | `IstarothClientDoomTornado`、`ClientEventHandler`、`ClientPayloadHandler` |
| `com.github.wal_bos.moreskills.race.bloodfiend` | 6 | `AbstractBloodfiendRace`、`CrimsonProgenitorRace`、`BloodfiendRace` |
| `com.github.wal_bos.moreskills.client.particle` | 6 | `JudgementSmokeParticle`、`SunParticle`、`DeathEnergyLightningParticle` |
| `com.github.wal_bos.moreskills.ai` | 5 | `TensuraWikiKnowledge`、`TensuraAiKnowledge`、`TensuraAiConfig` |
| `com.github.wal_bos.moreskills.effect` | 5 | `LifeSeizureEffect`、`NaberiusWillEffect`、`OrganDecayEffect` |
| `com.github.wal_bos.moreskills.configs` | 4 | `MoreskillsRaceConfig`、`EntityConfigs`、`MoreSkillsConfigs` |
| `com.github.wal_bos.moreskills.nirvana` | 4 | `NirvanaData`、`NirvanaPlayerResetUtil`、`NirvanaMenu` |
| `com.github.wal_bos.moreskills.limbo` | 4 | `IWantLimboData`、`IWantLimboMenu`、`IWantLimboRoom` |
| `com.github.wal_bos.moreskills.net` | 4 | `PathsEffectNetwork`、`ModPayloads`、`CaedrosMenuActionPayload` |
| `com.github.wal_bos.moreskills.ability.skill.intrinsic` | 4 | `BloodArtsSkill`、`BlackCommunionSkill`、`AntiSymbioteSkill` |
| `com.github.wal_bos.moreskills.world.entity.beretta` | 3 | `BerettaCombat`、`BerettaDefense`、`BerettaArenaHandler` |
| `com.github.wal_bos.moreskills.world` | 3 | `PathsWorldEvents`、`PathsSingleEntitySpawner`、`PathsBossSavedData` |
| `com.github.wal_bos.moreskills.client.model` | 3 | `TheCoordinateModelGeometry`、`BerettaModel`、`TheCoordinateModel` |
| `com.github.wal_bos.moreskills.client.vfx` | 2 | `PhainonAcquirementVfxClient`、`PhainonVfxRenderTypes` |
| `com.github.wal_bos.moreskills.event` | 2 | `ModEffectBlockEvents`、`ModCaedrosHandler` |
| `com.github.wal_bos.moreskills.gene` | 2 | `GeneEditorMenu`、`GeneEditorProvider` |
| … | | 其余 22 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BlackBloodConquestHandler` | class | — | EventBusSubscriber | 95 | 2544 | [BlackBloodConquestHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodConquestHandler.java) |
| `BlackBloodDomainHandler` | class | — | EventBusSubscriber | 84 | 2164 | [BlackBloodDomainHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodDomainHandler.java) |
| `BlackBloodEcologyHandler` | class | SavedData | EventBusSubscriber | 198 | 4196 | [BlackBloodEcologyHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodEcologyHandler.java) |
| `BlackCommunionHandler` | class | — | EventBusSubscriber | 41 | 840 | [BlackCommunionHandler.java](com/github/wal_bos/moreskills/handler/BlackCommunionHandler.java) |
| `BloodfiendHandler` | class | — | EventBusSubscriber | 36 | 876 | [BloodfiendHandler.java](com/github/wal_bos/moreskills/handler/BloodfiendHandler.java) |
| `ArthurRuntimeHandler` | class | — | EventBusSubscriber | 33 | 459 | [ArthurRuntimeHandler.java](com/github/wal_bos/moreskills/handler/arthur/ArthurRuntimeHandler.java) |
| `PhainonHandler` | class | — | EventBusSubscriber | 27 | 495 | [PhainonHandler.java](com/github/wal_bos/moreskills/handler/phainon/PhainonHandler.java) |
| `FeldwayRewardHandler` | class | — | EventBusSubscriber | 23 | 369 | [FeldwayRewardHandler.java](com/github/wal_bos/moreskills/handler/FeldwayRewardHandler.java) |
| `AsmodayBlackHoleHandler` | class | — | EventBusSubscriber | 14 | 370 | [AsmodayBlackHoleHandler.java](com/github/wal_bos/moreskills/handler/AsmodayBlackHoleHandler.java) |
| `AbhothPlagueHandler` | class | — | EventBusSubscriber | 14 | 388 | [AbhothPlagueHandler.java](com/github/wal_bos/moreskills/handler/AbhothPlagueHandler.java) |
| `ClientVfxCommand` | class | — | EventBusSubscriber | 10 | 138 | [ClientVfxCommand.java](com/github/wal_bos/moreskills/command/ClientVfxCommand.java) |
| `FungusInfectionDeathHandler` | class | — | EventBusSubscriber | 2 | 27 | [FungusInfectionDeathHandler.java](com/github/wal_bos/moreskills/handler/FungusInfectionDeathHandler.java) |
| `ParasiteCommand` | class | — | EventBusSubscriber | 10 | 168 | [ParasiteCommand.java](com/github/wal_bos/moreskills/command/ParasiteCommand.java) |
| `RaphaelCommand` | class | — | EventBusSubscriber | 6 | 207 | [RaphaelCommand.java](com/github/wal_bos/moreskills/command/RaphaelCommand.java) |
| `PhainonCommand` | class | — | EventBusSubscriber | 3 | 128 | [PhainonCommand.java](com/github/wal_bos/moreskills/handler/phainon/PhainonCommand.java) |
| `AsmodayPassives` | class | — | EventBusSubscriber | 14 | 325 | [AsmodayPassives.java](com/github/wal_bos/moreskills/handler/asmoday/AsmodayPassives.java) |
| `CaedrosAspectCommand` | class | — | EventBusSubscriber | 3 | 76 | [CaedrosAspectCommand.java](com/github/wal_bos/moreskills/command/CaedrosAspectCommand.java) |
| `AsmodayBlackHoleCommand` | class | — | EventBusSubscriber | 2 | 55 | [AsmodayBlackHoleCommand.java](com/github/wal_bos/moreskills/command/AsmodayBlackHoleCommand.java) |
| `GearshiftTrailCommand` | class | — | EventBusSubscriber | 3 | 115 | [GearshiftTrailCommand.java](com/github/wal_bos/moreskills/command/GearshiftTrailCommand.java) |
| `ParasiteHandler` | class | — | — | 94 | 1803 | [ParasiteHandler.java](com/github/wal_bos/moreskills/handler/ParasiteHandler.java) |
| `ElfariaTheHallowedIcemaidenHandler` | class | — | — | 56 | 1273 | [ElfariaTheHallowedIcemaidenHandler.java](com/github/wal_bos/moreskills/handler/ElfariaTheHallowedIcemaidenHandler.java) |
| `NaberiusCombatStanceHandler` | class | — | — | 45 | 1597 | [NaberiusCombatStanceHandler.java](com/github/wal_bos/moreskills/ability/skill/shades/NaberiusCombatStanceHandler.java) |
| `PainPlanetaryDevastationHandler` | class | — | — | 40 | 981 | [PainPlanetaryDevastationHandler.java](com/github/wal_bos/moreskills/handler/PainPlanetaryDevastationHandler.java) |
| `GeneEditorMobHandler` | class | — | — | 55 | 989 | [GeneEditorMobHandler.java](com/github/wal_bos/moreskills/handler/GeneEditorMobHandler.java) |
| `NirvanaHandler` | class | — | — | 49 | 906 | [NirvanaHandler.java](com/github/wal_bos/moreskills/handler/NirvanaHandler.java) |
| `IWantToBeJustLikeYouHandler` | class | — | — | 23 | 512 | [IWantToBeJustLikeYouHandler.java](com/github/wal_bos/moreskills/handler/IWantToBeJustLikeYouHandler.java) |
| `PathsRitualHandler` | class | — | — | 22 | 408 | [PathsRitualHandler.java](com/github/wal_bos/moreskills/handler/PathsRitualHandler.java) |
| `AvalonDivineDisintegrationVfxClient` | class | — | — | 48 | 1144 | [AvalonDivineDisintegrationVfxClient.java](com/github/wal_bos/moreskills/client/fx/avalon/AvalonDivineDisintegrationVfxClient.java) |
| `WitchOfEnvySatellaHandler` | class | — | — | 30 | 754 | [WitchOfEnvySatellaHandler.java](com/github/wal_bos/moreskills/handler/WitchOfEnvySatellaHandler.java) |
| `MoreskillsRaceConfig` | class | MoreskillsRaceConfig.BloodfiendStats | — | 29 | 1391 | [MoreskillsRaceConfig.java](com/github/wal_bos/moreskills/configs/MoreskillsRaceConfig.java) |
| `AdaptabilityHooks` | class | — | EventBusSubscriber | 2 | 43 | [AdaptabilityHooks.java](com/github/wal_bos/moreskills/ability/skill/unique/AdaptabilityHooks.java) |
| `ArthurHolyDisintegrationVfxClient` | class | — | — | 33 | 819 | [ArthurHolyDisintegrationVfxClient.java](com/github/wal_bos/moreskills/client/fx/arthur/ArthurHolyDisintegrationVfxClient.java) |
| `GodlyScienceSmitheryHandler` | class | — | — | 31 | 517 | [GodlyScienceSmitheryHandler.java](com/github/wal_bos/moreskills/handler/GodlyScienceSmitheryHandler.java) |
| `VoidArchiveForgeMenu` | class | AbstractContainerMenu | — | 70 | 1126 | [VoidArchiveForgeMenu.java](com/github/wal_bos/moreskills/menu/VoidArchiveForgeMenu.java) |
| `MichaelManasEntity` | class | PathfinderMob | — | 49 | 1462 | [MichaelManasEntity.java](com/github/wal_bos/moreskills/world/entity/MichaelManasEntity.java) |
| `ArsWeissCloneEntity` | class | Mob | — | 233 | 5280 | [ArsWeissCloneEntity.java](com/github/wal_bos/moreskills/world/entity/ArsWeissCloneEntity.java) |
| `FeldwayAngelEntity` | class | PathfinderMob | — | 64 | 1250 | [FeldwayAngelEntity.java](com/github/wal_bos/moreskills/world/entity/FeldwayAngelEntity.java) |
| `FeldwayEntity` | class | PathfinderMob | — | 95 | 2873 | [FeldwayEntity.java](com/github/wal_bos/moreskills/world/entity/FeldwayEntity.java) |
| `BerettaEntity` | class | PathfinderMob | — | 71 | 1031 | [BerettaEntity.java](com/github/wal_bos/moreskills/world/entity/BerettaEntity.java) |
| `PhainonAcquirementVfxClient` | class | — | — | 127 | 3591 | [PhainonAcquirementVfxClient.java](com/github/wal_bos/moreskills/client/vfx/PhainonAcquirementVfxClient.java) |
| `MichaelJerezVfxClient` | class | — | — | 247 | 6097 | [MichaelJerezVfxClient.java](com/github/wal_bos/moreskills/client/fx/MichaelJerezVfxClient.java) |
| `AsmodayBlackHoleClient` | class | — | — | 46 | 1051 | [AsmodayBlackHoleClient.java](com/github/wal_bos/moreskills/client/fx/AsmodayBlackHoleClient.java) |
| `MichaelHalberdShotVfxClient` | class | — | — | 251 | 6692 | [MichaelHalberdShotVfxClient.java](com/github/wal_bos/moreskills/client/fx/MichaelHalberdShotVfxClient.java) |
| `PathsAuroraRenderer` | class | — | — | 33 | 1254 | [PathsAuroraRenderer.java](com/github/wal_bos/moreskills/client/fx/PathsAuroraRenderer.java) |
| `MichaelAfkStarsVfxClient` | class | — | — | 44 | 1250 | [MichaelAfkStarsVfxClient.java](com/github/wal_bos/moreskills/client/fx/MichaelAfkStarsVfxClient.java) |
| `AlbisVinaVfxClient` | class | — | — | 54 | 1234 | [AlbisVinaVfxClient.java](com/github/wal_bos/moreskills/client/fx/AlbisVinaVfxClient.java) |
| `MitamaAtomicEdictVfxClient` | class | — | — | 419 | 3903 | [MitamaAtomicEdictVfxClient.java](com/github/wal_bos/moreskills/client/fx/MitamaAtomicEdictVfxClient.java) |
| `MichaelKeterVfxClient` | class | — | — | 814 | 6342 | [MichaelKeterVfxClient.java](com/github/wal_bos/moreskills/client/fx/MichaelKeterVfxClient.java) |
| `ElfariaFruzelCardeneiaVfxClient` | class | — | — | 48 | 1284 | [ElfariaFruzelCardeneiaVfxClient.java](com/github/wal_bos/moreskills/client/fx/ElfariaFruzelCardeneiaVfxClient.java) |
| `MichaelShifuruMeteorVfxClient` | class | — | — | 41 | 1003 | [MichaelShifuruMeteorVfxClient.java](com/github/wal_bos/moreskills/client/fx/MichaelShifuruMeteorVfxClient.java) |
| `RonovaFinalityVfxClient` | class | — | — | 45 | 1214 | [RonovaFinalityVfxClient.java](com/github/wal_bos/moreskills/client/fx/RonovaFinalityVfxClient.java) |
| `AvalonSovereignJudgementVfxClient` | class | — | — | 39 | 1041 | [AvalonSovereignJudgementVfxClient.java](com/github/wal_bos/moreskills/client/fx/avalon/AvalonSovereignJudgementVfxClient.java) |
| `AvalonEternalVictoryVfxClient` | class | — | — | 59 | 2395 | [AvalonEternalVictoryVfxClient.java](com/github/wal_bos/moreskills/client/fx/avalon/AvalonEternalVictoryVfxClient.java) |
| `ArthurPromisedVictoryVfxClient` | class | — | — | 68 | 2962 | [ArthurPromisedVictoryVfxClient.java](com/github/wal_bos/moreskills/client/fx/arthur/ArthurPromisedVictoryVfxClient.java) |
| `UniqueOwnerSkillHandler` | class | — | — | 27 | 499 | [UniqueOwnerSkillHandler.java](com/github/wal_bos/moreskills/handler/UniqueOwnerSkillHandler.java) |
| `OverlayHandlerMixin` | class | — | Mixin | 2 | 251 | [OverlayHandlerMixin.java](com/github/wal_bos/moreskills/mixin/client/OverlayHandlerMixin.java) |
| `VoidArchiveForgeData` | class | — | — | 109 | 2902 | [VoidArchiveForgeData.java](com/github/wal_bos/moreskills/ability/skill/unique/VoidArchiveForgeData.java) |
| `WitchOfEnvySatellaVfxClient` | class | — | — | 43 | 894 | [WitchOfEnvySatellaVfxClient.java](com/github/wal_bos/moreskills/client/fx/WitchOfEnvySatellaVfxClient.java) |
| `GearshiftBlueTrailClient` | class | — | — | 34 | 943 | [GearshiftBlueTrailClient.java](com/github/wal_bos/moreskills/client/fx/GearshiftBlueTrailClient.java) |
| `ArthurLightDominationVfxClient` | class | — | — | 35 | 929 | [ArthurLightDominationVfxClient.java](com/github/wal_bos/moreskills/client/fx/arthur/ArthurLightDominationVfxClient.java) |

## 事件处理（`@SubscribeEvent`，28 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `BlackBloodEcologyHandler` | 5 | `com.github.wal_bos.moreskills.handler` | [BlackBloodEcologyHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodEcologyHandler.java) |
| `BloodfiendHandler` | 5 | `com.github.wal_bos.moreskills.handler` | [BloodfiendHandler.java](com/github/wal_bos/moreskills/handler/BloodfiendHandler.java) |
| `PhainonHandler` | 5 | `com.github.wal_bos.moreskills.handler.phainon` | [PhainonHandler.java](com/github/wal_bos/moreskills/handler/phainon/PhainonHandler.java) |
| `BlackBloodConquestHandler` | 4 | `com.github.wal_bos.moreskills.handler` | [BlackBloodConquestHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodConquestHandler.java) |
| `FeldwayRewardHandler` | 4 | `com.github.wal_bos.moreskills.handler` | [FeldwayRewardHandler.java](com/github/wal_bos/moreskills/handler/FeldwayRewardHandler.java) |
| `IWantToBeJustLikeYouHandler` | 4 | `com.github.wal_bos.moreskills.handler` | [IWantToBeJustLikeYouHandler.java](com/github/wal_bos/moreskills/handler/IWantToBeJustLikeYouHandler.java) |
| `AsmodayTimeHandler` | 4 | `com.github.wal_bos.moreskills.handler.asmoday` | [AsmodayTimeHandler.java](com/github/wal_bos/moreskills/handler/asmoday/AsmodayTimeHandler.java) |
| `BlackBloodDomainHandler` | 3 | `com.github.wal_bos.moreskills.handler` | [BlackBloodDomainHandler.java](com/github/wal_bos/moreskills/handler/BlackBloodDomainHandler.java) |
| `PathsRitualHandler` | 3 | `com.github.wal_bos.moreskills.handler` | [PathsRitualHandler.java](com/github/wal_bos/moreskills/handler/PathsRitualHandler.java) |
| `BlackCommunionHandler` | 2 | `com.github.wal_bos.moreskills.handler` | [BlackCommunionHandler.java](com/github/wal_bos/moreskills/handler/BlackCommunionHandler.java) |
| `MorphSyncHandler` | 2 | `com.github.wal_bos.moreskills.handler` | [MorphSyncHandler.java](com/github/wal_bos/moreskills/handler/MorphSyncHandler.java) |
| `MichaelActions` | 2 | `com.github.wal_bos.moreskills.handler.michael` | [MichaelActions.java](com/github/wal_bos/moreskills/handler/michael/MichaelActions.java) |
| `ArthurRuntimeHandler` | 1 | `com.github.wal_bos.moreskills.handler.arthur` | [ArthurRuntimeHandler.java](com/github/wal_bos/moreskills/handler/arthur/ArthurRuntimeHandler.java) |
| `AsmodayBlackHoleHandler` | 1 | `com.github.wal_bos.moreskills.handler` | [AsmodayBlackHoleHandler.java](com/github/wal_bos/moreskills/handler/AsmodayBlackHoleHandler.java) |
| `AbhothPlagueHandler` | 1 | `com.github.wal_bos.moreskills.handler` | [AbhothPlagueHandler.java](com/github/wal_bos/moreskills/handler/AbhothPlagueHandler.java) |
| `ClientVfxCommand` | 1 | `com.github.wal_bos.moreskills.command` | [ClientVfxCommand.java](com/github/wal_bos/moreskills/command/ClientVfxCommand.java) |
| `FungusInfectionDeathHandler` | 1 | `com.github.wal_bos.moreskills.handler` | [FungusInfectionDeathHandler.java](com/github/wal_bos/moreskills/handler/FungusInfectionDeathHandler.java) |
| `ParasiteCommand` | 1 | `com.github.wal_bos.moreskills.command` | [ParasiteCommand.java](com/github/wal_bos/moreskills/command/ParasiteCommand.java) |
| `RaphaelCommand` | 1 | `com.github.wal_bos.moreskills.command` | [RaphaelCommand.java](com/github/wal_bos/moreskills/command/RaphaelCommand.java) |
| `PhainonCommand` | 1 | `com.github.wal_bos.moreskills.handler.phainon` | [PhainonCommand.java](com/github/wal_bos/moreskills/handler/phainon/PhainonCommand.java) |
| `AsmodayPassives` | 1 | `com.github.wal_bos.moreskills.handler.asmoday` | [AsmodayPassives.java](com/github/wal_bos/moreskills/handler/asmoday/AsmodayPassives.java) |
| `CaedrosAspectCommand` | 1 | `com.github.wal_bos.moreskills.command` | [CaedrosAspectCommand.java](com/github/wal_bos/moreskills/command/CaedrosAspectCommand.java) |
| `AsmodayBlackHoleCommand` | 1 | `com.github.wal_bos.moreskills.command` | [AsmodayBlackHoleCommand.java](com/github/wal_bos/moreskills/command/AsmodayBlackHoleCommand.java) |
| `GearshiftTrailCommand` | 1 | `com.github.wal_bos.moreskills.command` | [GearshiftTrailCommand.java](com/github/wal_bos/moreskills/command/GearshiftTrailCommand.java) |
| `AdaptabilityHooks` | 1 | `com.github.wal_bos.moreskills.ability.skill.unique` | [AdaptabilityHooks.java](com/github/wal_bos/moreskills/ability/skill/unique/AdaptabilityHooks.java) |
| `AsmodaySpatialAbsorption` | 1 | `com.github.wal_bos.moreskills.handler.asmoday` | [AsmodaySpatialAbsorption.java](com/github/wal_bos/moreskills/handler/asmoday/AsmodaySpatialAbsorption.java) |
| `AsmodayChatTeleportHandler` | 1 | `com.github.wal_bos.moreskills.handler.asmoday` | [AsmodayChatTeleportHandler.java](com/github/wal_bos/moreskills/handler/asmoday/AsmodayChatTeleportHandler.java) |
| `DomainOfNegationSkill` | 1 | `com.github.wal_bos.moreskills.ability.skill.unique` | [DomainOfNegationSkill.java](com/github/wal_bos/moreskills/ability/skill/unique/DomainOfNegationSkill.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `OverlayHandlerMixin` | `OverlayHandler` | [OverlayHandlerMixin.java](com/github/wal_bos/moreskills/mixin/client/OverlayHandlerMixin.java) |
| `NightmareTitleFeldwayMixin` | `NightmareTitleFeldway` | [NightmareTitleFeldwayMixin.java](com/github/wal_bos/moreskills/mixin/compat/NightmareTitleFeldwayMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `MoreskillsRaceConfig` | — | [MoreskillsRaceConfig.java](com/github/wal_bos/moreskills/configs/MoreskillsRaceConfig.java) |
| `EntityConfigs` | — | [EntityConfigs.java](com/github/wal_bos/moreskills/configs/EntityConfigs.java) |
| `MoreSkillsConfigs` | — | [MoreSkillsConfigs.java](com/github/wal_bos/moreskills/configs/MoreSkillsConfigs.java) |
| `ClientVfxConfigs` | — | [ClientVfxConfigs.java](com/github/wal_bos/moreskills/configs/ClientVfxConfigs.java) |
| `TensuraAiConfig` | — | [TensuraAiConfig.java](com/github/wal_bos/moreskills/ai/TensuraAiConfig.java) |
| `RonovaConfigAccess` | — | [RonovaConfigAccess.java](com/github/wal_bos/moreskills/ability/skill/shades/ronova/RonovaConfigAccess.java) |

## 按包浏览

- `com.github.wal_bos.moreskills`（1 类）
- `com.github.wal_bos.moreskills.ability.skill.extra`（2 类）
- `com.github.wal_bos.moreskills.ability.skill.intrinsic`（4 类）
- `com.github.wal_bos.moreskills.ability.skill.shades`（8 类）
- `com.github.wal_bos.moreskills.ability.skill.shades.ronova`（1 类）
- `com.github.wal_bos.moreskills.ability.skill.ultimate`（16 类）
- `com.github.wal_bos.moreskills.ability.skill.unique`（21 类）
- `com.github.wal_bos.moreskills.ai`（5 类）
- `com.github.wal_bos.moreskills.casino`（2 类）
- `com.github.wal_bos.moreskills.client`（6 类）
- `com.github.wal_bos.moreskills.client.fx`（84 类）
- `com.github.wal_bos.moreskills.client.fx.arthur`（8 类）
- `com.github.wal_bos.moreskills.client.fx.avalon`（18 类）
- `com.github.wal_bos.moreskills.client.model`（3 类）
- `com.github.wal_bos.moreskills.client.model.item`（2 类）
- `com.github.wal_bos.moreskills.client.particle`（6 类）
- `com.github.wal_bos.moreskills.client.renderer`（11 类）
- `com.github.wal_bos.moreskills.client.renderer.item`（2 类）
- `com.github.wal_bos.moreskills.client.screen`（16 类）
- `com.github.wal_bos.moreskills.client.vfx`（2 类）
- `com.github.wal_bos.moreskills.command`（8 类）
- `com.github.wal_bos.moreskills.configs`（4 类）
- `com.github.wal_bos.moreskills.effect`（5 类）
- `com.github.wal_bos.moreskills.entity.michael`（1 类）
- `com.github.wal_bos.moreskills.event`（2 类）
- `com.github.wal_bos.moreskills.fungus`（2 类）
- `com.github.wal_bos.moreskills.gene`（2 类）
- `com.github.wal_bos.moreskills.handler`（42 类）
- `com.github.wal_bos.moreskills.handler.Istaroth`（8 类）
- `com.github.wal_bos.moreskills.handler.arthur`（10 类）
- `com.github.wal_bos.moreskills.handler.asmoday`（17 类）
- `com.github.wal_bos.moreskills.handler.avalon`（23 类）
- `com.github.wal_bos.moreskills.handler.michael`（10 类）
- `com.github.wal_bos.moreskills.handler.phainon`（12 类）
- `com.github.wal_bos.moreskills.handler.ronova`（8 类）
- `com.github.wal_bos.moreskills.item`（6 类）
- `com.github.wal_bos.moreskills.limbo`（4 类）
- `com.github.wal_bos.moreskills.menu`（10 类）
- `com.github.wal_bos.moreskills.mixin.client`（1 类）
- `com.github.wal_bos.moreskills.mixin.compat`（1 类）
- `com.github.wal_bos.moreskills.net`（4 类）
- `com.github.wal_bos.moreskills.net.client`（1 类）
- `com.github.wal_bos.moreskills.net.msg`（85 类）
- `com.github.wal_bos.moreskills.nirvana`（4 类）
- `com.github.wal_bos.moreskills.race.bloodfiend`（6 类）
- `com.github.wal_bos.moreskills.race.parasite`（1 类）
- `com.github.wal_bos.moreskills.registry`（2 类）
- `com.github.wal_bos.moreskills.registry.block`（1 类）
- `com.github.wal_bos.moreskills.registry.effect`（1 类）
- `com.github.wal_bos.moreskills.registry.entity`（2 类）
- `com.github.wal_bos.moreskills.registry.menu`（1 类）
- `com.github.wal_bos.moreskills.registry.particle`（1 类）
- `com.github.wal_bos.moreskills.registry.race`（1 类）
- `com.github.wal_bos.moreskills.registry.skill`（2 类）
- `com.github.wal_bos.moreskills.support`（1 类）
- `com.github.wal_bos.moreskills.util`（1 类）
- `com.github.wal_bos.moreskills.world`（3 类）
- `com.github.wal_bos.moreskills.world.block`（12 类）
- `com.github.wal_bos.moreskills.world.entity`（26 类）
- `com.github.wal_bos.moreskills.world.entity.avalon`（7 类）
- `com.github.wal_bos.moreskills.world.entity.beretta`（3 类）
- `com.github.wal_bos.moreskills.world.entity.feldway`（9 类）
