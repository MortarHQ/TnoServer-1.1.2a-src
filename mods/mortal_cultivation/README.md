# Mortal Cultivation（`mortal_cultivation`）

> jar：`[转生附属]mortal_cultivation.jar` ｜ 版本：`1.0.5` ｜ 作者：AblazeAQZL
> 规模：**348 个类 / 60 个包 / 44037 行** ｜ 事件处理类 51 ｜ Mixin 14 ｜ 注册/管理类 46

**依赖**：`neoforge`、`minecraft`、`terrablender`、`tensura`、`manascore_race`、`manascore_skill`、`curios`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.mortal_cultivation.item` | 33 | `TenThousandSoulBannerItem`、`RuneTableItem`、`NourishingQiTalismanItem` |
| `com.mortal_cultivation.client.renderer` | 28 | `SpiritGlassesItemRenderer`、`RuneTableItemRenderer`、`AncientScholarTableItemRenderer` |
| `com.mortal_cultivation.event` | 22 | `CommandRegistrationHandler`、`PuppetTalismanEventHandler`、`SpiritRootClientEvents` |
| `com.mortal_cultivation.worldgen` | 22 | `AncientCherryLayout`、`SpiritSkyIslandLayout`、`SteleGrottoPiece` |
| `com.mortal_cultivation.client.model` | 17 | `ZhaoYingZhuItemGeoModel`、`AzureMossQilinModel`、`PlayerMonkeyTail` |
| `com.mortal_cultivation.client` | 15 | `MillenniumGinsengClientEvents`、`AncientScholarTableClientEvents`、`ZhaoYingZhuClientEvents` |
| `com.mortal_cultivation.entity` | 15 | `QilinEffectNetwork`、`AzureMossQilinEntity`、`SpiritMonkeyEntity` |
| `com.mortal_cultivation.mixin` | 14 | `PlayerModelMixin`、`PlayerRendererMixin`、`MainScreenMixin` |
| `com.mortal_cultivation.block` | 13 | `AlchemyFurnaceBlock`、`AncientScholarTableBlock`、`RuneTableBlock` |
| `com.mortal_cultivation.race.monkey` | 13 | `SunWukongRace`、`ImmortalApeRace`、`MonkeyRace` |
| `com.mortal_cultivation.race.right_way` | 11 | `FoundationEstablishmentRace`、`DaoBreakingRace`、`GoldenCoreRace` |
| `com.mortal_cultivation.race.demonic_cultivator` | 10 | `DemonicGoldenCoreRace`、`DemonicTrueImmortalRace`、`DemonicUnityRace` |
| `com.mortal_cultivation.registry` | 8 | `RecipeRegistry`、`EntityRegistry`、`BlockRegistry` |
| `com.mortal_cultivation.command` | 8 | `ReloadFuelConfigCommand`、`SpiritRootCommand`、`DebugGreatSageEvolutionCommand` |
| `com.mortal_cultivation.block.entity` | 7 | `MillenniumGinsengBlockEntity`、`AncientScholarTableBlockEntity`、`RuneTableBlockEntity` |
| `com.mortal_cultivation.ability.skill.unique` | 6 | `GreatSageSkill`、`MischiefSkill`、`GreatSageEvolutionTracker` |
| `com.mortal_cultivation.flight` | 6 | `FlightAnimationServerHandler`、`FlightAnimationNetwork`、`FlyingSwordDeploymentData` |
| `com.mortal_cultivation.spiritroot` | 6 | `SpiritRootData`、`SpiritRootRaceHandler`、`SpiritRootType` |
| `com.mortal_cultivation.race.literal_translation` | 5 | `TalismanMasterRace`、`SigilImmortalRealmRace`、`SigilSaintRace` |
| `com.mortal_cultivation.config.race` | 5 | `MonkeyRaceConfig`、`LiteralTranslationRaceConfig`、`SpecialRaceConfig` |
| `com.mortal_cultivation.client.vfx` | 4 | `MyriadSwordsClientEffects`、`QilinClientEffects`、`SwordFlightClientEffects` |
| `com.mortal_cultivation.myriadswords` | 4 | `MyriadSwordsServerState`、`MyriadSwordsNetwork`、`MyriadSwordsTiming` |
| `com.mortal_cultivation.init` | 4 | `MortalCultivationModScreens`、`BlockEntityRegistry`、`MortalCultivationCapabilities` |
| `com.mortal_cultivation.gui.menu` | 4 | `AncientScholarTableMenu`、`TalismanMakingMenu`、`AlchemyFurnaceMenu` |
| `com.mortal_cultivation.gui.screen` | 4 | `AncientScholarTableScreen`、`TalismanMakingScreen`、`AlchemyFurnaceScreen` |
| `com.mortal_cultivation.ability.skill.extra` | 4 | `CultivatingTheMysticDaoSkill`、`HundredGhostsSurgeSkill`、`SwordFlightSkill` |
| `com.mortal_cultivation.compat.emi` | 4 | `AlchemyFurnaceEmiRecipe`、`TalismanMakingEmiRecipe`、`MortalCultivationEmiPlugin` |
| `com.mortal_cultivation.client.particle` | 4 | `JiuyouMistParticle`、`JiuyouWispParticle`、`LightningWoodArcParticle` |
| `com.github.manasmods.tensura.registry.skill` | 4 | `UniqueSkills`、`IntrinsicSkills`、`CommonSkills` |
| `com.mortal_cultivation.race.requirement` | 4 | `WanEPRequirement`、`SoulPointRequirement`、`QilinKillRequirement` |
| `com.mortal_cultivation.client.animation` | 3 | `FlightAnimationClientState`、`FlightAnimationClientSetup`、`FlightAnimationLibrary` |
| `com.mortal_cultivation.ability.skill.intrinsic` | 3 | `EarlyCultivationSkill`、`SizeTransformationSkill`、`AlchemistFireSkill` |
| `com.mortal_cultivation.entity.ai` | 3 | `SpiritMonkeyTreeGoal`、`SpiritMonkeyNavigation`、`SpiritMonkeyCanopyClimb` |
| `com.mortal_cultivation.compat.jei` | 3 | `AlchemyFurnaceCategory`、`JEIPlugin`、`TalismanMakingCategory` |
| `com.mortal_cultivation` | 2 | `MortalCultivation`、`MortalCultivationAddon` |
| `com.mortal_cultivation.data.recipe` | 2 | `TalismanRecipe`、`AncientScholarTableRecipe` |
| `com.mortal_cultivation.loot` | 2 | `AddItemLootModifier`、`CultivationLootModifiers` |
| `com.mortal_cultivation.config` | 2 | `AlchemyFurnaceConfig`、`MortalCultivationGeneralConfig` |
| `com.mortal_cultivation.compat.curios.client` | 2 | `SpiritGlassesCuriosClientCompat`、`SpiritGlassesCurioRenderer` |
| `com.mortal_cultivation.registry.item` | 2 | `CultivationItems`、`CultivationCreativeTabs` |
| … | | 其余 20 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CommandRegistrationHandler` | class | — | EventBusSubscriber | 1 | 30 | [CommandRegistrationHandler.java](com/mortal_cultivation/event/CommandRegistrationHandler.java) |
| `GreatSageSkill` | class | LegacySkill | EventBusSubscriber | 65 | 1396 | [GreatSageSkill.java](com/mortal_cultivation/ability/skill/unique/GreatSageSkill.java) |
| `PuppetTalismanEventHandler` | class | — | EventBusSubscriber | 11 | 218 | [PuppetTalismanEventHandler.java](com/mortal_cultivation/event/PuppetTalismanEventHandler.java) |
| `FlightAnimationClientState` | class | — | EventBusSubscriber | 45 | 706 | [FlightAnimationClientState.java](com/mortal_cultivation/client/animation/FlightAnimationClientState.java) |
| `SpiritRootClientEvents` | class | — | EventBusSubscriber | 1 | 20 | [SpiritRootClientEvents.java](com/mortal_cultivation/event/SpiritRootClientEvents.java) |
| `DoubleJumpEventHandler` | class | — | EventBusSubscriber | 9 | 388 | [DoubleJumpEventHandler.java](com/mortal_cultivation/event/DoubleJumpEventHandler.java) |
| `MillenniumGinsengClientEvents` | class | — | EventBusSubscriber | 1 | 22 | [MillenniumGinsengClientEvents.java](com/mortal_cultivation/client/MillenniumGinsengClientEvents.java) |
| `AncientScholarTableClientEvents` | class | — | EventBusSubscriber | 1 | 22 | [AncientScholarTableClientEvents.java](com/mortal_cultivation/client/AncientScholarTableClientEvents.java) |
| `ZhaoYingZhuClientEvents` | class | — | EventBusSubscriber | 1 | 21 | [ZhaoYingZhuClientEvents.java](com/mortal_cultivation/client/ZhaoYingZhuClientEvents.java) |
| `SpiritGlassesClientEvents` | class | — | EventBusSubscriber | 1 | 20 | [SpiritGlassesClientEvents.java](com/mortal_cultivation/client/SpiritGlassesClientEvents.java) |
| `LightningWoodLingzhiClientEvents` | class | — | EventBusSubscriber | 1 | 22 | [LightningWoodLingzhiClientEvents.java](com/mortal_cultivation/client/LightningWoodLingzhiClientEvents.java) |
| `SpiritCompassClientEvents` | class | — | EventBusSubscriber | 1 | 36 | [SpiritCompassClientEvents.java](com/mortal_cultivation/client/SpiritCompassClientEvents.java) |
| `JiuyouColdPoolClientEvents` | class | — | EventBusSubscriber | 4 | 145 | [JiuyouColdPoolClientEvents.java](com/mortal_cultivation/client/JiuyouColdPoolClientEvents.java) |
| `AncientSteleClientEvents` | class | — | EventBusSubscriber | 1 | 22 | [AncientSteleClientEvents.java](com/mortal_cultivation/client/AncientSteleClientEvents.java) |
| `SpiritMonkeyClientEvents` | class | — | EventBusSubscriber | 2 | 35 | [SpiritMonkeyClientEvents.java](com/mortal_cultivation/client/SpiritMonkeyClientEvents.java) |
| `AzureMossQilinClientEvents` | class | — | EventBusSubscriber | 1 | 22 | [AzureMossQilinClientEvents.java](com/mortal_cultivation/client/AzureMossQilinClientEvents.java) |
| `RuneTableClientEvents` | class | — | EventBusSubscriber | 3 | 43 | [RuneTableClientEvents.java](com/mortal_cultivation/client/RuneTableClientEvents.java) |
| `MyriadSwordsClientEffects` | class | — | EventBusSubscriber | 28 | 713 | [MyriadSwordsClientEffects.java](com/mortal_cultivation/client/vfx/MyriadSwordsClientEffects.java) |
| `FastingTalismanEventHandler` | class | — | EventBusSubscriber | 5 | 104 | [FastingTalismanEventHandler.java](com/mortal_cultivation/event/FastingTalismanEventHandler.java) |
| `MindscapeChallengeManager` | class | — | EventBusSubscriber | 18 | 303 | [MindscapeChallengeManager.java](com/mortal_cultivation/event/MindscapeChallengeManager.java) |
| `SpiritForestAtmosphereEvents` | class | — | EventBusSubscriber | 7 | 165 | [SpiritForestAtmosphereEvents.java](com/mortal_cultivation/client/SpiritForestAtmosphereEvents.java) |
| `MischiefSkill` | class | LegacySkill | EventBusSubscriber | 33 | 476 | [MischiefSkill.java](com/mortal_cultivation/ability/skill/unique/MischiefSkill.java) |
| `BiguEventHandler` | class | — | EventBusSubscriber | 4 | 70 | [BiguEventHandler.java](com/mortal_cultivation/event/BiguEventHandler.java) |
| `PlayerEvents` | class | — | EventBusSubscriber | 4 | 95 | [PlayerEvents.java](com/mortal_cultivation/event/PlayerEvents.java) |
| `VillagerTradeEventHandler` | class | — | EventBusSubscriber | 1 | 30 | [VillagerTradeEventHandler.java](com/mortal_cultivation/event/VillagerTradeEventHandler.java) |
| `HeavenCleansingElixirEventHandler` | class | — | EventBusSubscriber | 2 | 69 | [HeavenCleansingElixirEventHandler.java](com/mortal_cultivation/event/HeavenCleansingElixirEventHandler.java) |
| `WhiteLieEventHandler` | class | — | EventBusSubscriber | 1 | 29 | [WhiteLieEventHandler.java](com/mortal_cultivation/event/WhiteLieEventHandler.java) |
| `MillenniumGinsengEvents` | class | — | EventBusSubscriber | 3 | 68 | [MillenniumGinsengEvents.java](com/mortal_cultivation/event/MillenniumGinsengEvents.java) |
| `SpiritForestMusicEvents` | class | — | EventBusSubscriber | 1 | 52 | [SpiritForestMusicEvents.java](com/mortal_cultivation/client/SpiritForestMusicEvents.java) |
| `QilinClientEffects` | class | — | EventBusSubscriber | 23 | 482 | [QilinClientEffects.java](com/mortal_cultivation/client/vfx/QilinClientEffects.java) |
| `RaceFlightAbilityHandler` | class | — | EventBusSubscriber | 12 | 199 | [RaceFlightAbilityHandler.java](com/mortal_cultivation/event/RaceFlightAbilityHandler.java) |
| `MindscapeSleepHandler` | class | — | EventBusSubscriber | 8 | 201 | [MindscapeSleepHandler.java](com/mortal_cultivation/event/MindscapeSleepHandler.java) |
| `SwordFlightClientEffects` | class | — | EventBusSubscriber | 18 | 410 | [SwordFlightClientEffects.java](com/mortal_cultivation/client/vfx/SwordFlightClientEffects.java) |
| `CultivationAnimationHandler` | class | — | EventBusSubscriber | 8 | 85 | [CultivationAnimationHandler.java](com/mortal_cultivation/event/CultivationAnimationHandler.java) |
| `SpiritRootPassiveHandler` | class | — | EventBusSubscriber | 5 | 114 | [SpiritRootPassiveHandler.java](com/mortal_cultivation/event/SpiritRootPassiveHandler.java) |
| `WastelanderBookHandler` | class | — | EventBusSubscriber | 9 | 140 | [WastelanderBookHandler.java](com/mortal_cultivation/event/WastelanderBookHandler.java) |
| `MeditationRestrictionHandler` | class | — | EventBusSubscriber、OnlyIn | 5 | 98 | [MeditationRestrictionHandler.java](com/mortal_cultivation/event/MeditationRestrictionHandler.java) |
| `SpiritForestSpawnHandler` | class | — | EventBusSubscriber | 5 | 117 | [SpiritForestSpawnHandler.java](com/mortal_cultivation/event/SpiritForestSpawnHandler.java) |
| `FlightAnimationServerHandler` | class | — | EventBusSubscriber | 5 | 110 | [FlightAnimationServerHandler.java](com/mortal_cultivation/flight/FlightAnimationServerHandler.java) |
| `QiFlightCleanupHandler` | class | — | EventBusSubscriber | 2 | 26 | [QiFlightCleanupHandler.java](com/mortal_cultivation/event/QiFlightCleanupHandler.java) |
| `MyriadSwordsServerState` | class | — | EventBusSubscriber | 16 | 248 | [MyriadSwordsServerState.java](com/mortal_cultivation/myriadswords/MyriadSwordsServerState.java) |
| `MillenniumGinsengBlockEntity` | class | BlockEntity | EventBusSubscriber | 2 | 79 | [MillenniumGinsengBlockEntity.java](com/mortal_cultivation/block/entity/MillenniumGinsengBlockEntity.java) |
| `QilinEffectNetwork` | class | — | EventBusSubscriber | 3 | 38 | [QilinEffectNetwork.java](com/mortal_cultivation/entity/QilinEffectNetwork.java) |
| `GreatSageEvolutionTracker` | class | — | EventBusSubscriber | 13 | 240 | [GreatSageEvolutionTracker.java](com/mortal_cultivation/ability/skill/unique/GreatSageEvolutionTracker.java) |
| `MindscapeFogRenderer` | class | — | EventBusSubscriber、OnlyIn | 2 | 40 | [MindscapeFogRenderer.java](com/mortal_cultivation/event/MindscapeFogRenderer.java) |
| `FlyingSwordClientControl` | class | — | EventBusSubscriber | 1 | 28 | [FlyingSwordClientControl.java](com/mortal_cultivation/client/FlyingSwordClientControl.java) |
| `FlightAnimationClientSetup` | class | — | EventBusSubscriber | 1 | 19 | [FlightAnimationClientSetup.java](com/mortal_cultivation/client/animation/FlightAnimationClientSetup.java) |
| `MortalCultivationModScreens` | class | — | EventBusSubscriber | 1 | 26 | [MortalCultivationModScreens.java](com/mortal_cultivation/init/MortalCultivationModScreens.java) |
| `AncientScholarTableBlockEntity` | class | BlockEntity | — | 61 | 1019 | [AncientScholarTableBlockEntity.java](com/mortal_cultivation/block/entity/AncientScholarTableBlockEntity.java) |
| `EarlyCultivationSkill` | class | LegacySkill | EventBusSubscriber | 7 | 184 | [EarlyCultivationSkill.java](com/mortal_cultivation/ability/skill/intrinsic/EarlyCultivationSkill.java) |
| `MortalCultivation` | class | — | Mod | 4 | 146 | [MortalCultivation.java](com/mortal_cultivation/MortalCultivation.java) |
| `AzureMossQilinEntity` | class | PathfinderMob | — | 53 | 823 | [AzureMossQilinEntity.java](com/mortal_cultivation/entity/AzureMossQilinEntity.java) |
| `SpiritMonkeyEntity` | class | Animal | — | 61 | 720 | [SpiritMonkeyEntity.java](com/mortal_cultivation/entity/SpiritMonkeyEntity.java) |
| `MillenniumGinsengEntity` | class | PathfinderMob | — | 50 | 748 | [MillenniumGinsengEntity.java](com/mortal_cultivation/entity/MillenniumGinsengEntity.java) |
| `FlyingSwordEntity` | class | Entity | — | 52 | 615 | [FlyingSwordEntity.java](com/mortal_cultivation/entity/FlyingSwordEntity.java) |
| `AncientScholarTableMenu` | class | AbstractContainerMenu | — | 64 | 610 | [AncientScholarTableMenu.java](com/mortal_cultivation/gui/menu/AncientScholarTableMenu.java) |
| `RuneTableBlockEntity` | class | BlockEntity | — | 35 | 342 | [RuneTableBlockEntity.java](com/mortal_cultivation/block/entity/RuneTableBlockEntity.java) |
| `AncientScholarTableScreen` | class | AbstractContainerScreen | — | 29 | 600 | [AncientScholarTableScreen.java](com/mortal_cultivation/gui/screen/AncientScholarTableScreen.java) |
| `RecipeRegistry` | class | — | — | 1 | 37 | [RecipeRegistry.java](com/mortal_cultivation/registry/RecipeRegistry.java) |
| `BlockEntityRegistry` | class | — | — | 1 | 48 | [BlockEntityRegistry.java](com/mortal_cultivation/init/BlockEntityRegistry.java) |

## 事件处理（`@SubscribeEvent`，51 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `MeditationRestrictionHandler` | 5 | `com.mortal_cultivation.event` | [MeditationRestrictionHandler.java](com/mortal_cultivation/event/MeditationRestrictionHandler.java) |
| `MyriadSwordsServerState` | 5 | `com.mortal_cultivation.myriadswords` | [MyriadSwordsServerState.java](com/mortal_cultivation/myriadswords/MyriadSwordsServerState.java) |
| `PuppetTalismanEventHandler` | 4 | `com.mortal_cultivation.event` | [PuppetTalismanEventHandler.java](com/mortal_cultivation/event/PuppetTalismanEventHandler.java) |
| `FlightAnimationClientState` | 4 | `com.mortal_cultivation.client.animation` | [FlightAnimationClientState.java](com/mortal_cultivation/client/animation/FlightAnimationClientState.java) |
| `JiuyouColdPoolClientEvents` | 4 | `com.mortal_cultivation.client` | [JiuyouColdPoolClientEvents.java](com/mortal_cultivation/client/JiuyouColdPoolClientEvents.java) |
| `MindscapeChallengeManager` | 4 | `com.mortal_cultivation.event` | [MindscapeChallengeManager.java](com/mortal_cultivation/event/MindscapeChallengeManager.java) |
| `SwordFlightClientEffects` | 4 | `com.mortal_cultivation.client.vfx` | [SwordFlightClientEffects.java](com/mortal_cultivation/client/vfx/SwordFlightClientEffects.java) |
| `FlightAnimationServerHandler` | 4 | `com.mortal_cultivation.flight` | [FlightAnimationServerHandler.java](com/mortal_cultivation/flight/FlightAnimationServerHandler.java) |
| `GreatSageEvolutionTracker` | 4 | `com.mortal_cultivation.ability.skill.unique` | [GreatSageEvolutionTracker.java](com/mortal_cultivation/ability/skill/unique/GreatSageEvolutionTracker.java) |
| `SizeTransformationSkill` | 4 | `com.mortal_cultivation.ability.skill.intrinsic` | [SizeTransformationSkill.java](com/mortal_cultivation/ability/skill/intrinsic/SizeTransformationSkill.java) |
| `MyriadSwordsClientEffects` | 3 | `com.mortal_cultivation.client.vfx` | [MyriadSwordsClientEffects.java](com/mortal_cultivation/client/vfx/MyriadSwordsClientEffects.java) |
| `FastingTalismanEventHandler` | 3 | `com.mortal_cultivation.event` | [FastingTalismanEventHandler.java](com/mortal_cultivation/event/FastingTalismanEventHandler.java) |
| `QilinClientEffects` | 3 | `com.mortal_cultivation.client.vfx` | [QilinClientEffects.java](com/mortal_cultivation/client/vfx/QilinClientEffects.java) |
| `EarlyCultivationSkill` | 3 | `com.mortal_cultivation.ability.skill.intrinsic` | [EarlyCultivationSkill.java](com/mortal_cultivation/ability/skill/intrinsic/EarlyCultivationSkill.java) |
| `MortalCultivation` | 3 | `com.mortal_cultivation` | [MortalCultivation.java](com/mortal_cultivation/MortalCultivation.java) |
| `DoubleJumpEventHandler` | 2 | `com.mortal_cultivation.event` | [DoubleJumpEventHandler.java](com/mortal_cultivation/event/DoubleJumpEventHandler.java) |
| `SpiritMonkeyClientEvents` | 2 | `com.mortal_cultivation.client` | [SpiritMonkeyClientEvents.java](com/mortal_cultivation/client/SpiritMonkeyClientEvents.java) |
| `RuneTableClientEvents` | 2 | `com.mortal_cultivation.client` | [RuneTableClientEvents.java](com/mortal_cultivation/client/RuneTableClientEvents.java) |
| `SpiritForestAtmosphereEvents` | 2 | `com.mortal_cultivation.client` | [SpiritForestAtmosphereEvents.java](com/mortal_cultivation/client/SpiritForestAtmosphereEvents.java) |
| `PlayerEvents` | 2 | `com.mortal_cultivation.event` | [PlayerEvents.java](com/mortal_cultivation/event/PlayerEvents.java) |
| `MillenniumGinsengEvents` | 2 | `com.mortal_cultivation.event` | [MillenniumGinsengEvents.java](com/mortal_cultivation/event/MillenniumGinsengEvents.java) |
| `MindscapeSleepHandler` | 2 | `com.mortal_cultivation.event` | [MindscapeSleepHandler.java](com/mortal_cultivation/event/MindscapeSleepHandler.java) |
| `WastelanderBookHandler` | 2 | `com.mortal_cultivation.event` | [WastelanderBookHandler.java](com/mortal_cultivation/event/WastelanderBookHandler.java) |
| `QiFlightCleanupHandler` | 2 | `com.mortal_cultivation.event` | [QiFlightCleanupHandler.java](com/mortal_cultivation/event/QiFlightCleanupHandler.java) |
| `MindscapeFogRenderer` | 2 | `com.mortal_cultivation.event` | [MindscapeFogRenderer.java](com/mortal_cultivation/event/MindscapeFogRenderer.java) |
| `CommandRegistrationHandler` | 1 | `com.mortal_cultivation.event` | [CommandRegistrationHandler.java](com/mortal_cultivation/event/CommandRegistrationHandler.java) |
| `GreatSageSkill` | 1 | `com.mortal_cultivation.ability.skill.unique` | [GreatSageSkill.java](com/mortal_cultivation/ability/skill/unique/GreatSageSkill.java) |
| `SpiritRootClientEvents` | 1 | `com.mortal_cultivation.event` | [SpiritRootClientEvents.java](com/mortal_cultivation/event/SpiritRootClientEvents.java) |
| `MillenniumGinsengClientEvents` | 1 | `com.mortal_cultivation.client` | [MillenniumGinsengClientEvents.java](com/mortal_cultivation/client/MillenniumGinsengClientEvents.java) |
| `AncientScholarTableClientEvents` | 1 | `com.mortal_cultivation.client` | [AncientScholarTableClientEvents.java](com/mortal_cultivation/client/AncientScholarTableClientEvents.java) |
| `ZhaoYingZhuClientEvents` | 1 | `com.mortal_cultivation.client` | [ZhaoYingZhuClientEvents.java](com/mortal_cultivation/client/ZhaoYingZhuClientEvents.java) |
| `SpiritGlassesClientEvents` | 1 | `com.mortal_cultivation.client` | [SpiritGlassesClientEvents.java](com/mortal_cultivation/client/SpiritGlassesClientEvents.java) |
| `LightningWoodLingzhiClientEvents` | 1 | `com.mortal_cultivation.client` | [LightningWoodLingzhiClientEvents.java](com/mortal_cultivation/client/LightningWoodLingzhiClientEvents.java) |
| `SpiritCompassClientEvents` | 1 | `com.mortal_cultivation.client` | [SpiritCompassClientEvents.java](com/mortal_cultivation/client/SpiritCompassClientEvents.java) |
| `AncientSteleClientEvents` | 1 | `com.mortal_cultivation.client` | [AncientSteleClientEvents.java](com/mortal_cultivation/client/AncientSteleClientEvents.java) |
| `AzureMossQilinClientEvents` | 1 | `com.mortal_cultivation.client` | [AzureMossQilinClientEvents.java](com/mortal_cultivation/client/AzureMossQilinClientEvents.java) |
| `MischiefSkill` | 1 | `com.mortal_cultivation.ability.skill.unique` | [MischiefSkill.java](com/mortal_cultivation/ability/skill/unique/MischiefSkill.java) |
| `BiguEventHandler` | 1 | `com.mortal_cultivation.event` | [BiguEventHandler.java](com/mortal_cultivation/event/BiguEventHandler.java) |
| `VillagerTradeEventHandler` | 1 | `com.mortal_cultivation.event` | [VillagerTradeEventHandler.java](com/mortal_cultivation/event/VillagerTradeEventHandler.java) |
| `HeavenCleansingElixirEventHandler` | 1 | `com.mortal_cultivation.event` | [HeavenCleansingElixirEventHandler.java](com/mortal_cultivation/event/HeavenCleansingElixirEventHandler.java) |

## Mixin（14 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PlayerModelMixin` | `PlayerModel` | [PlayerModelMixin.java](com/mortal_cultivation/mixin/PlayerModelMixin.java) |
| `PlayerRendererMixin` | `PlayerRenderer` | [PlayerRendererMixin.java](com/mortal_cultivation/mixin/PlayerRendererMixin.java) |
| `MainScreenMixin` | `MainScreen` | [MainScreenMixin.java](com/mortal_cultivation/mixin/MainScreenMixin.java) |
| `QilinDisplacedBlockMixin` | `QilinDisplacedBlock` | [QilinDisplacedBlockMixin.java](com/mortal_cultivation/mixin/QilinDisplacedBlockMixin.java) |
| `MixinResetScrollItem` | `ResetScrollItem` | [MixinResetScrollItem.java](com/mortal_cultivation/mixin/MixinResetScrollItem.java) |
| `ReincarnationScreenMixin` | `ReincarnationScreen` | [ReincarnationScreenMixin.java](com/mortal_cultivation/mixin/ReincarnationScreenMixin.java) |
| `QilinCompileTaskContextMixin` | `QilinCompileTaskContext` | [QilinCompileTaskContextMixin.java](com/mortal_cultivation/mixin/QilinCompileTaskContextMixin.java) |
| `SpiritMonkeyMagiculePoisonMixin` | `SpiritMonkeyMagiculePoison` | [SpiritMonkeyMagiculePoisonMixin.java](com/mortal_cultivation/mixin/SpiritMonkeyMagiculePoisonMixin.java) |
| `QilinDisplacedNeighborMixin` | `QilinDisplacedNeighbor` | [QilinDisplacedNeighborMixin.java](com/mortal_cultivation/mixin/QilinDisplacedNeighborMixin.java) |
| `HumanRaceMixin` | `HumanRace` | [HumanRaceMixin.java](com/mortal_cultivation/mixin/HumanRaceMixin.java) |
| `QilinCompileTaskAccessor` | `QilinCompileTaskAccessor` | [QilinCompileTaskAccessor.java](com/mortal_cultivation/mixin/QilinCompileTaskAccessor.java) |
| `QilinChunkUploadMixin` | `QilinChunkUpload` | [QilinChunkUploadMixin.java](com/mortal_cultivation/mixin/QilinChunkUploadMixin.java) |
| `QilinTerrainRefreshMixin` | `QilinTerrainRefresh` | [QilinTerrainRefreshMixin.java](com/mortal_cultivation/mixin/QilinTerrainRefreshMixin.java) |
| `SpiritForestMusicMixin` | `SpiritForestMusic` | [SpiritForestMusicMixin.java](com/mortal_cultivation/mixin/SpiritForestMusicMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ReloadFuelConfigCommand` | — | [ReloadFuelConfigCommand.java](com/mortal_cultivation/command/ReloadFuelConfigCommand.java) |
| `AlchemyFurnaceConfig` | — | [AlchemyFurnaceConfig.java](com/mortal_cultivation/config/AlchemyFurnaceConfig.java) |
| `MortalCultivationGeneralConfig` | — | [MortalCultivationGeneralConfig.java](com/mortal_cultivation/config/MortalCultivationGeneralConfig.java) |
| `MonkeyRaceConfig` | — | [MonkeyRaceConfig.java](com/mortal_cultivation/config/race/MonkeyRaceConfig.java) |
| `LiteralTranslationRaceConfig` | — | [LiteralTranslationRaceConfig.java](com/mortal_cultivation/config/race/LiteralTranslationRaceConfig.java) |
| `SpecialRaceConfig` | — | [SpecialRaceConfig.java](com/mortal_cultivation/config/race/SpecialRaceConfig.java) |
| `DemonicRaceConfig` | — | [DemonicRaceConfig.java](com/mortal_cultivation/config/race/DemonicRaceConfig.java) |
| `RightWayRaceConfig` | — | [RightWayRaceConfig.java](com/mortal_cultivation/config/race/RightWayRaceConfig.java) |

## 按包浏览

- `com.github.manasmods.tensura.capability.ep`（1 类）
- `com.github.manasmods.tensura.capability.race`（1 类）
- `com.github.manasmods.tensura.race`（1 类）
- `com.github.manasmods.tensura.registry`（1 类）
- `com.github.manasmods.tensura.registry.race`（1 类）
- `com.github.manasmods.tensura.registry.skill`（4 类）
- `com.github.manasmods.tensura.util`（1 类）
- `com.mortal_cultivation`（2 类）
- `com.mortal_cultivation.ability.skill.base`（1 类）
- `com.mortal_cultivation.ability.skill.extra`（4 类）
- `com.mortal_cultivation.ability.skill.intrinsic`（3 类）
- `com.mortal_cultivation.ability.skill.unique`（6 类）
- `com.mortal_cultivation.block`（13 类）
- `com.mortal_cultivation.block.entity`（7 类）
- `com.mortal_cultivation.client`（15 类）
- `com.mortal_cultivation.client.animation`（3 类）
- `com.mortal_cultivation.client.gui`（1 类）
- `com.mortal_cultivation.client.model`（17 类）
- `com.mortal_cultivation.client.particle`（4 类）
- `com.mortal_cultivation.client.renderer`（28 类）
- `com.mortal_cultivation.client.vfx`（4 类）
- `com.mortal_cultivation.command`（8 类）
- `com.mortal_cultivation.compat.curios`（1 类）
- `com.mortal_cultivation.compat.curios.client`（2 类）
- `com.mortal_cultivation.compat.emi`（4 类）
- `com.mortal_cultivation.compat.jei`（3 类）
- `com.mortal_cultivation.config`（2 类）
- `com.mortal_cultivation.config.race`（5 类）
- `com.mortal_cultivation.data`（1 类）
- `com.mortal_cultivation.data.recipe`（2 类）
- `com.mortal_cultivation.dimension`（1 类）
- `com.mortal_cultivation.effect`（1 类）
- `com.mortal_cultivation.entity`（15 类）
- `com.mortal_cultivation.entity.ai`（3 类）
- `com.mortal_cultivation.entity.breath`（1 类）
- `com.mortal_cultivation.event`（22 类）
- `com.mortal_cultivation.event.client`（1 类）
- `com.mortal_cultivation.flight`（6 类）
- `com.mortal_cultivation.gui.menu`（4 类）
- `com.mortal_cultivation.gui.screen`（4 类）
- `com.mortal_cultivation.init`（4 类）
- `com.mortal_cultivation.item`（33 类）
- `com.mortal_cultivation.item.tier`（1 类）
- `com.mortal_cultivation.loot`（2 类）
- `com.mortal_cultivation.mixin`（14 类）
- `com.mortal_cultivation.myriadswords`（4 类）
- `com.mortal_cultivation.race.demonic_cultivator`（10 类）
- `com.mortal_cultivation.race.literal_translation`（5 类）
- `com.mortal_cultivation.race.monkey`（13 类）
- `com.mortal_cultivation.race.requirement`（4 类）
- `com.mortal_cultivation.race.right_way`（11 类）
- `com.mortal_cultivation.race.special`（1 类）
- `com.mortal_cultivation.recipe`（1 类）
- `com.mortal_cultivation.registry`（8 类）
- `com.mortal_cultivation.registry.item`（2 类）
- `com.mortal_cultivation.registry.race`（1 类）
- `com.mortal_cultivation.registry.skill`（1 类）
- `com.mortal_cultivation.spiritroot`（6 类）
- `com.mortal_cultivation.util`（1 类）
- `com.mortal_cultivation.worldgen`（22 类）
