# Eternal Nether（`eternalnether`）

> jar：`[永恒下界]EternalNether.jar` ｜ 版本：`21.1.3` ｜ 作者：Izofar, Fuzs
> 规模：**90 个类 / 35 个包 / 7615 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 3

**官方描述**：New structures, mobs, and many Nether adventures await! A continuation of Bygone Nether by Izofar.

**依赖**：`neoforge`、`minecraft`、`puzzleslib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fuzs.eternalnether.init` | 10 | `ModSoundEvents`、`ModRegistry`、`ModEntityTypes` |
| `fuzs.eternalnether.client.renderer.entity` | 8 | `PiglinPrisonerRenderer`、`WitherSkeletonKnightRenderer`、`PiglinHunterRenderer` |
| `fuzs.eternalnether.world.entity.monster` | 6 | `WitherSkeletonKnight`、`WarpedEnderMan`、`Wraither` |
| `fuzs.eternalnether.world.entity.ai.behavior` | 5 | `ModStopAdmiringIfItemTooFarAway`、`ModStopHoldingItemIfNoLongerAdmiring`、`ModStopAdmiringIfTiredOfTryingToReachItem` |
| `fuzs.eternalnether.client.model` | 5 | `WitherSkeletonKnightModel`、`PiglinHunterModel`、`CorporModel` |
| `fuzs.eternalnether.world.entity.monster.piglin` | 4 | `PiglinPrisonerAi`、`PiglinPrisoner`、`PiglinHunter` |
| `fuzs.eternalnether.world.item` | 4 | `CutlassItem`、`WarpedEnderpearlItem`、`UnrepairableShieldItem` |
| `fuzs.eternalnether.data.tags` | 4 | `ModBlockTagProvider`、`ModItemTagProvider`、`ModEntityTypeTagProvider` |
| `fuzs.eternalnether.util` | 4 | `DataFixer`、`HSV`、`ModStructureUtils` |
| `fuzs.eternalnether.data.loot` | 3 | `ModBlockLootProvider`、`ModEntityTypeLootProvider`、`ModChestLootProvider` |
| `fuzs.eternalnether.client.renderer.entity.layers` | 3 | `ModCarriedBlockLayer`、`ModEnderEyesLayer`、`WitherGlowLayer` |
| `fuzs.eternalnether.world.level.levelgen.structure` | 3 | `CitadelStructure`、`PiglinManorStructure`、`CatacombStructure` |
| `fuzs.eternalnether.mixin` | 2 | `PiglinAiMixin`、`PiglinBruteAiMixin` |
| `fuzs.eternalnether.data.client` | 2 | `ModLanguageProvider`、`ModModelProvider` |
| `fuzs.eternalnether.data` | 2 | `ModRecipeProvider`、`ModAdvancementProvider` |
| `fuzs.eternalnether.data.registries` | 2 | `ModDatapackRegistriesProvider`、`AbstractDatapackRegistriesProvider` |
| `fuzs.eternalnether.neoforge.data.client` | 2 | `ModSoundProvider`、`ModAtlasProvider` |
| `fuzs.eternalnether.world.entity.ai.sensing` | 2 | `PiglinPrisonerSpecificSensor`、`ModPiglinBruteSpecificSensor` |
| `fuzs.eternalnether.world.entity.projectile` | 2 | `ThrownWarpedEnderpearl`、`EnderPearlTeleportCallback` |
| `fuzs.eternalnether.world.level.levelgen.feature` | 2 | `MobFeature`、`MobPassengerFeature` |
| `fuzs.eternalnether.neoforge.client` | 1 | `EternalNetherNeoForgeClient` |
| `fuzs.eternalnether.neoforge` | 1 | `EternalNetherNeoForge` |
| `fuzs.eternalnether.world.level.block` | 1 | `NetheriteBellBlock` |
| `fuzs.eternalnether.neoforge.mixin` | 1 | `PlayerNeoForgeMixin` |
| `fuzs.eternalnether.world.level.block.entity` | 1 | `NetheriteBellBlockEntity` |
| `fuzs.eternalnether.client.handler` | 1 | `FirstPersonRenderingHandler` |
| `fuzs.eternalnether.client.renderer` | 1 | `ShieldItemRenderer` |
| `fuzs.eternalnether.client` | 1 | `EternalNetherClient` |
| `fuzs.eternalnether.client.renderer.blockentity` | 1 | `NetheriteBellRenderer` |
| `fuzs.eternalnether` | 1 | `EternalNether` |
| `fuzs.eternalnether.world.entity.ai.goal` | 1 | `ShieldDefenseGoal` |
| `fuzs.eternalnether.world.entity.animal.horse` | 1 | `WitherSkeletonHorse` |
| `fuzs.eternalnether.neoforge.services` | 1 | `NeoForgeAbstractions` |
| `fuzs.eternalnether.services` | 1 | `CommonAbstractions` |
| `architectury_inject_EternalNether1211_Common_2f8f2f7902eb4f658ac36a2b72da8964_ce56455b21237114b17bb445b85e294de3b75aa6e38a57539a0fa5d24658e6c0EternalNetherv21131211Commondevjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EternalNetherNeoForgeClient` | class | — | Mod | 0 | 23 | [EternalNetherNeoForgeClient.java](fuzs/eternalnether/neoforge/client/EternalNetherNeoForgeClient.java) |
| `EternalNetherNeoForge` | class | — | Mod | 1 | 67 | [EternalNetherNeoForge.java](fuzs/eternalnether/neoforge/EternalNetherNeoForge.java) |
| `PiglinPrisonerAi` | class | PiglinAi | — | 62 | 611 | [PiglinPrisonerAi.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinPrisonerAi.java) |
| `PiglinPrisoner` | class | AbstractPiglin | — | 46 | 427 | [PiglinPrisoner.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinPrisoner.java) |
| `ModSoundEvents` | class | — | — | 2 | 26 | [ModSoundEvents.java](fuzs/eternalnether/init/ModSoundEvents.java) |
| `NetheriteBellBlock` | class | BaseEntityBlock | — | 20 | 283 | [NetheriteBellBlock.java](fuzs/eternalnether/world/level/block/NetheriteBellBlock.java) |
| `PlayerNeoForgeMixin` | class | LivingEntity | Mixin | 1 | 26 | [PlayerNeoForgeMixin.java](fuzs/eternalnether/neoforge/mixin/PlayerNeoForgeMixin.java) |
| `PiglinAiMixin` | class | — | Mixin | 3 | 53 | [PiglinAiMixin.java](fuzs/eternalnether/mixin/PiglinAiMixin.java) |
| `PiglinBruteAiMixin` | class | — | Mixin | 2 | 32 | [PiglinBruteAiMixin.java](fuzs/eternalnether/mixin/PiglinBruteAiMixin.java) |
| `NetheriteBellBlockEntity` | class | BlockEntity | — | 14 | 190 | [NetheriteBellBlockEntity.java](fuzs/eternalnether/world/level/block/entity/NetheriteBellBlockEntity.java) |
| `ModRegistry` | class | — | — | 1 | 36 | [ModRegistry.java](fuzs/eternalnether/init/ModRegistry.java) |
| `ModBlockLootProvider` | class | Blocks | — | 2 | 49 | [ModBlockLootProvider.java](fuzs/eternalnether/data/loot/ModBlockLootProvider.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 2 | 177 | [ModEntityTypeLootProvider.java](fuzs/eternalnether/data/loot/ModEntityTypeLootProvider.java) |
| `ModLanguageProvider` | class | AbstractLanguageProvider | — | 21 | 245 | [ModLanguageProvider.java](fuzs/eternalnether/data/client/ModLanguageProvider.java) |
| `WitherSkeletonKnight` | class | WitherSkeleton | — | 24 | 209 | [WitherSkeletonKnight.java](fuzs/eternalnether/world/entity/monster/WitherSkeletonKnight.java) |
| `FirstPersonRenderingHandler` | class | — | — | 3 | 79 | [FirstPersonRenderingHandler.java](fuzs/eternalnether/client/handler/FirstPersonRenderingHandler.java) |
| `ModRecipeProvider` | class | AbstractRecipeProvider | — | 4 | 123 | [ModRecipeProvider.java](fuzs/eternalnether/data/ModRecipeProvider.java) |
| `ShieldItemRenderer` | class | ReloadingBuiltInItemRenderer | — | 2 | 70 | [ShieldItemRenderer.java](fuzs/eternalnether/client/renderer/ShieldItemRenderer.java) |
| `ModChestLootProvider` | class | Simple | — | 1 | 138 | [ModChestLootProvider.java](fuzs/eternalnether/data/loot/ModChestLootProvider.java) |
| `PiglinHunter` | class | Piglin | — | 17 | 155 | [PiglinHunter.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinHunter.java) |
| `CutlassItem` | class | UnrepairableShieldItem | — | 6 | 50 | [CutlassItem.java](fuzs/eternalnether/world/item/CutlassItem.java) |
| `EternalNetherClient` | class | ClientModConstructor | — | 9 | 85 | [EternalNetherClient.java](fuzs/eternalnether/client/EternalNetherClient.java) |
| `ModBlockTagProvider` | class | AbstractTagProvider | — | 1 | 53 | [ModBlockTagProvider.java](fuzs/eternalnether/data/tags/ModBlockTagProvider.java) |
| `ModItemTagProvider` | class | AbstractTagProvider | — | 1 | 28 | [ModItemTagProvider.java](fuzs/eternalnether/data/tags/ModItemTagProvider.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 29 | [ModEntityTypeTagProvider.java](fuzs/eternalnether/data/tags/ModEntityTypeTagProvider.java) |
| `ModModelProvider` | class | AbstractModelProvider | — | 5 | 213 | [ModModelProvider.java](fuzs/eternalnether/data/client/ModModelProvider.java) |
| `ModStopAdmiringIfItemTooFarAway` | class | Behavior | — | 2 | 33 | [ModStopAdmiringIfItemTooFarAway.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopAdmiringIfItemTooFarAway.java) |
| `ModStopHoldingItemIfNoLongerAdmiring` | class | Behavior | — | 2 | 25 | [ModStopHoldingItemIfNoLongerAdmiring.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopHoldingItemIfNoLongerAdmiring.java) |
| `ModStopAdmiringIfTiredOfTryingToReachItem` | class | Behavior | — | 2 | 54 | [ModStopAdmiringIfTiredOfTryingToReachItem.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopAdmiringIfTiredOfTryingToReachItem.java) |
| `ModStartAdmiringItemIfSeen` | class | Behavior | — | 2 | 40 | [ModStartAdmiringItemIfSeen.java](fuzs/eternalnether/world/entity/ai/behavior/ModStartAdmiringItemIfSeen.java) |
| `WarpedEnderMan` | class | EnderMan | — | 14 | 191 | [WarpedEnderMan.java](fuzs/eternalnether/world/entity/monster/WarpedEnderMan.java) |
| `WarpedEnderpearlItem` | class | Item | — | 1 | 45 | [WarpedEnderpearlItem.java](fuzs/eternalnether/world/item/WarpedEnderpearlItem.java) |
| `UnrepairableShieldItem` | class | ShieldItem | — | 1 | 16 | [UnrepairableShieldItem.java](fuzs/eternalnether/world/item/UnrepairableShieldItem.java) |
| `WitheredBoneMealItem` | class | Item | — | 3 | 78 | [WitheredBoneMealItem.java](fuzs/eternalnether/world/item/WitheredBoneMealItem.java) |
| `DataFixer` | class | DataFixer | — | 6 | 168 | [DataFixer.java](fuzs/eternalnether/util/DataFixer.java) |
| `HSV` | class | — | — | 14 | 85 | [HSV.java](fuzs/eternalnether/util/HSV.java) |
| `ModStructureUtils` | class | — | — | 5 | 66 | [ModStructureUtils.java](fuzs/eternalnether/util/ModStructureUtils.java) |
| `CreativeModeTabHelper` | class | — | — | 7 | 90 | [CreativeModeTabHelper.java](fuzs/eternalnether/util/CreativeModeTabHelper.java) |
| `PiglinPrisonerRenderer` | class | PiglinRenderer | — | 1 | 21 | [PiglinPrisonerRenderer.java](fuzs/eternalnether/client/renderer/entity/PiglinPrisonerRenderer.java) |
| `WitherSkeletonKnightRenderer` | class | HumanoidMobRenderer | — | 2 | 27 | [WitherSkeletonKnightRenderer.java](fuzs/eternalnether/client/renderer/entity/WitherSkeletonKnightRenderer.java) |
| `PiglinHunterRenderer` | class | HumanoidMobRenderer | — | 2 | 36 | [PiglinHunterRenderer.java](fuzs/eternalnether/client/renderer/entity/PiglinHunterRenderer.java) |
| `WraitherRenderer` | class | HumanoidMobRenderer | — | 2 | 30 | [WraitherRenderer.java](fuzs/eternalnether/client/renderer/entity/WraitherRenderer.java) |
| `WitherSkeletonHorseRenderer` | class | UndeadHorseRenderer | — | 1 | 21 | [WitherSkeletonHorseRenderer.java](fuzs/eternalnether/client/renderer/entity/WitherSkeletonHorseRenderer.java) |
| `CorporRenderer` | class | HumanoidMobRenderer | — | 2 | 26 | [CorporRenderer.java](fuzs/eternalnether/client/renderer/entity/CorporRenderer.java) |
| `WexRenderer` | class | MobRenderer | — | 3 | 32 | [WexRenderer.java](fuzs/eternalnether/client/renderer/entity/WexRenderer.java) |
| `WarpedEnderManRenderer` | class | MobRenderer | — | 3 | 57 | [WarpedEnderManRenderer.java](fuzs/eternalnether/client/renderer/entity/WarpedEnderManRenderer.java) |
| `ModCarriedBlockLayer` | class | — | — | 1 | 48 | [ModCarriedBlockLayer.java](fuzs/eternalnether/client/renderer/entity/layers/ModCarriedBlockLayer.java) |
| `NetheriteBellRenderer` | class | BlockEntityRenderer<NetheriteBellBlo | — | 1 | 51 | [NetheriteBellRenderer.java](fuzs/eternalnether/client/renderer/blockentity/NetheriteBellRenderer.java) |
| `ModEntityTypes` | class | — | — | 2 | 127 | [ModEntityTypes.java](fuzs/eternalnether/init/ModEntityTypes.java) |
| `ModBlocks` | class | — | — | 1 | 87 | [ModBlocks.java](fuzs/eternalnether/init/ModBlocks.java) |
| `ModBlockFamilies` | class | — | — | 1 | 33 | [ModBlockFamilies.java](fuzs/eternalnether/init/ModBlockFamilies.java) |
| `ModItems` | class | — | — | 2 | 97 | [ModItems.java](fuzs/eternalnether/init/ModItems.java) |
| `EternalNether` | class | ModConstructor | — | 9 | 145 | [EternalNether.java](fuzs/eternalnether/EternalNether.java) |
| `ModDatapackRegistriesProvider` | class | AbstractDatapackRegistriesProvider | — | 4 | 185 | [ModDatapackRegistriesProvider.java](fuzs/eternalnether/data/registries/ModDatapackRegistriesProvider.java) |
| `AbstractDatapackRegistriesProvider` | class | RegistriesDatapackGenerator | — | 3 | 57 | [AbstractDatapackRegistriesProvider.java](fuzs/eternalnether/data/registries/AbstractDatapackRegistriesProvider.java) |
| `ShieldDefenseGoal` | class | NearestAttackableTargetGoal | — | 6 | 63 | [ShieldDefenseGoal.java](fuzs/eternalnether/world/entity/ai/goal/ShieldDefenseGoal.java) |
| `ModFollowLeader` | class | Behavior | — | 5 | 65 | [ModFollowLeader.java](fuzs/eternalnether/world/entity/ai/behavior/ModFollowLeader.java) |
| `Wraither` | class | WitherSkeleton | — | 9 | 104 | [Wraither.java](fuzs/eternalnether/world/entity/monster/Wraither.java) |
| `ModPiglinBruteAi` | class | PiglinBruteAi | — | 5 | 71 | [ModPiglinBruteAi.java](fuzs/eternalnether/world/entity/monster/piglin/ModPiglinBruteAi.java) |
| `WitherSkeletonHorse` | class | SkeletonHorse | — | 9 | 103 | [WitherSkeletonHorse.java](fuzs/eternalnether/world/entity/animal/horse/WitherSkeletonHorse.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PlayerNeoForgeMixin` | `PlayerNeoForge` | [PlayerNeoForgeMixin.java](fuzs/eternalnether/neoforge/mixin/PlayerNeoForgeMixin.java) |
| `PiglinAiMixin` | `PiglinAi` | [PiglinAiMixin.java](fuzs/eternalnether/mixin/PiglinAiMixin.java) |
| `PiglinBruteAiMixin` | `PiglinBruteAi` | [PiglinBruteAiMixin.java](fuzs/eternalnether/mixin/PiglinBruteAiMixin.java) |

## 全部类（90）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EternalNetherNeoForgeClient` | class | — | Mod | 0 | 23 | [EternalNetherNeoForgeClient.java](fuzs/eternalnether/neoforge/client/EternalNetherNeoForgeClient.java) |
| `EternalNetherNeoForge` | class | — | Mod | 1 | 67 | [EternalNetherNeoForge.java](fuzs/eternalnether/neoforge/EternalNetherNeoForge.java) |
| `PiglinPrisonerAi` | class | PiglinAi | — | 62 | 611 | [PiglinPrisonerAi.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinPrisonerAi.java) |
| `PiglinPrisoner` | class | AbstractPiglin | — | 46 | 427 | [PiglinPrisoner.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinPrisoner.java) |
| `ModSoundEvents` | class | — | — | 2 | 26 | [ModSoundEvents.java](fuzs/eternalnether/init/ModSoundEvents.java) |
| `NetheriteBellBlock` | class | BaseEntityBlock | — | 20 | 283 | [NetheriteBellBlock.java](fuzs/eternalnether/world/level/block/NetheriteBellBlock.java) |
| `PlayerNeoForgeMixin` | class | LivingEntity | Mixin | 1 | 26 | [PlayerNeoForgeMixin.java](fuzs/eternalnether/neoforge/mixin/PlayerNeoForgeMixin.java) |
| `PiglinAiMixin` | class | — | Mixin | 3 | 53 | [PiglinAiMixin.java](fuzs/eternalnether/mixin/PiglinAiMixin.java) |
| `PiglinBruteAiMixin` | class | — | Mixin | 2 | 32 | [PiglinBruteAiMixin.java](fuzs/eternalnether/mixin/PiglinBruteAiMixin.java) |
| `NetheriteBellBlockEntity` | class | BlockEntity | — | 14 | 190 | [NetheriteBellBlockEntity.java](fuzs/eternalnether/world/level/block/entity/NetheriteBellBlockEntity.java) |
| `ModRegistry` | class | — | — | 1 | 36 | [ModRegistry.java](fuzs/eternalnether/init/ModRegistry.java) |
| `ModBlockLootProvider` | class | Blocks | — | 2 | 49 | [ModBlockLootProvider.java](fuzs/eternalnether/data/loot/ModBlockLootProvider.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 2 | 177 | [ModEntityTypeLootProvider.java](fuzs/eternalnether/data/loot/ModEntityTypeLootProvider.java) |
| `ModLanguageProvider` | class | AbstractLanguageProvider | — | 21 | 245 | [ModLanguageProvider.java](fuzs/eternalnether/data/client/ModLanguageProvider.java) |
| `WitherSkeletonKnight` | class | WitherSkeleton | — | 24 | 209 | [WitherSkeletonKnight.java](fuzs/eternalnether/world/entity/monster/WitherSkeletonKnight.java) |
| `FirstPersonRenderingHandler` | class | — | — | 3 | 79 | [FirstPersonRenderingHandler.java](fuzs/eternalnether/client/handler/FirstPersonRenderingHandler.java) |
| `ModRecipeProvider` | class | AbstractRecipeProvider | — | 4 | 123 | [ModRecipeProvider.java](fuzs/eternalnether/data/ModRecipeProvider.java) |
| `ShieldItemRenderer` | class | ReloadingBuiltInItemRenderer | — | 2 | 70 | [ShieldItemRenderer.java](fuzs/eternalnether/client/renderer/ShieldItemRenderer.java) |
| `ModChestLootProvider` | class | Simple | — | 1 | 138 | [ModChestLootProvider.java](fuzs/eternalnether/data/loot/ModChestLootProvider.java) |
| `PiglinHunter` | class | Piglin | — | 17 | 155 | [PiglinHunter.java](fuzs/eternalnether/world/entity/monster/piglin/PiglinHunter.java) |
| `CutlassItem` | class | UnrepairableShieldItem | — | 6 | 50 | [CutlassItem.java](fuzs/eternalnether/world/item/CutlassItem.java) |
| `EternalNetherClient` | class | ClientModConstructor | — | 9 | 85 | [EternalNetherClient.java](fuzs/eternalnether/client/EternalNetherClient.java) |
| `ModBlockTagProvider` | class | AbstractTagProvider | — | 1 | 53 | [ModBlockTagProvider.java](fuzs/eternalnether/data/tags/ModBlockTagProvider.java) |
| `ModItemTagProvider` | class | AbstractTagProvider | — | 1 | 28 | [ModItemTagProvider.java](fuzs/eternalnether/data/tags/ModItemTagProvider.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 29 | [ModEntityTypeTagProvider.java](fuzs/eternalnether/data/tags/ModEntityTypeTagProvider.java) |
| `ModModelProvider` | class | AbstractModelProvider | — | 5 | 213 | [ModModelProvider.java](fuzs/eternalnether/data/client/ModModelProvider.java) |
| `ModStopAdmiringIfItemTooFarAway` | class | Behavior | — | 2 | 33 | [ModStopAdmiringIfItemTooFarAway.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopAdmiringIfItemTooFarAway.java) |
| `ModStopHoldingItemIfNoLongerAdmiring` | class | Behavior | — | 2 | 25 | [ModStopHoldingItemIfNoLongerAdmiring.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopHoldingItemIfNoLongerAdmiring.java) |
| `ModStopAdmiringIfTiredOfTryingToReachItem` | class | Behavior | — | 2 | 54 | [ModStopAdmiringIfTiredOfTryingToReachItem.java](fuzs/eternalnether/world/entity/ai/behavior/ModStopAdmiringIfTiredOfTryingToReachItem.java) |
| `ModStartAdmiringItemIfSeen` | class | Behavior | — | 2 | 40 | [ModStartAdmiringItemIfSeen.java](fuzs/eternalnether/world/entity/ai/behavior/ModStartAdmiringItemIfSeen.java) |
| `WarpedEnderMan` | class | EnderMan | — | 14 | 191 | [WarpedEnderMan.java](fuzs/eternalnether/world/entity/monster/WarpedEnderMan.java) |
| `WarpedEnderpearlItem` | class | Item | — | 1 | 45 | [WarpedEnderpearlItem.java](fuzs/eternalnether/world/item/WarpedEnderpearlItem.java) |
| `UnrepairableShieldItem` | class | ShieldItem | — | 1 | 16 | [UnrepairableShieldItem.java](fuzs/eternalnether/world/item/UnrepairableShieldItem.java) |
| `WitheredBoneMealItem` | class | Item | — | 3 | 78 | [WitheredBoneMealItem.java](fuzs/eternalnether/world/item/WitheredBoneMealItem.java) |
| `DataFixer` | class | DataFixer | — | 6 | 168 | [DataFixer.java](fuzs/eternalnether/util/DataFixer.java) |
| `HSV` | class | — | — | 14 | 85 | [HSV.java](fuzs/eternalnether/util/HSV.java) |
| `ModStructureUtils` | class | — | — | 5 | 66 | [ModStructureUtils.java](fuzs/eternalnether/util/ModStructureUtils.java) |
| `CreativeModeTabHelper` | class | — | — | 7 | 90 | [CreativeModeTabHelper.java](fuzs/eternalnether/util/CreativeModeTabHelper.java) |
| `PiglinPrisonerRenderer` | class | PiglinRenderer | — | 1 | 21 | [PiglinPrisonerRenderer.java](fuzs/eternalnether/client/renderer/entity/PiglinPrisonerRenderer.java) |
| `WitherSkeletonKnightRenderer` | class | HumanoidMobRenderer | — | 2 | 27 | [WitherSkeletonKnightRenderer.java](fuzs/eternalnether/client/renderer/entity/WitherSkeletonKnightRenderer.java) |
| `PiglinHunterRenderer` | class | HumanoidMobRenderer | — | 2 | 36 | [PiglinHunterRenderer.java](fuzs/eternalnether/client/renderer/entity/PiglinHunterRenderer.java) |
| `WraitherRenderer` | class | HumanoidMobRenderer | — | 2 | 30 | [WraitherRenderer.java](fuzs/eternalnether/client/renderer/entity/WraitherRenderer.java) |
| `WitherSkeletonHorseRenderer` | class | UndeadHorseRenderer | — | 1 | 21 | [WitherSkeletonHorseRenderer.java](fuzs/eternalnether/client/renderer/entity/WitherSkeletonHorseRenderer.java) |
| `CorporRenderer` | class | HumanoidMobRenderer | — | 2 | 26 | [CorporRenderer.java](fuzs/eternalnether/client/renderer/entity/CorporRenderer.java) |
| `WexRenderer` | class | MobRenderer | — | 3 | 32 | [WexRenderer.java](fuzs/eternalnether/client/renderer/entity/WexRenderer.java) |
| `WarpedEnderManRenderer` | class | MobRenderer | — | 3 | 57 | [WarpedEnderManRenderer.java](fuzs/eternalnether/client/renderer/entity/WarpedEnderManRenderer.java) |
| `ModCarriedBlockLayer` | class | — | — | 1 | 48 | [ModCarriedBlockLayer.java](fuzs/eternalnether/client/renderer/entity/layers/ModCarriedBlockLayer.java) |
| `NetheriteBellRenderer` | class | BlockEntityRenderer<NetheriteBellBlo | — | 1 | 51 | [NetheriteBellRenderer.java](fuzs/eternalnether/client/renderer/blockentity/NetheriteBellRenderer.java) |
| `ModEntityTypes` | class | — | — | 2 | 127 | [ModEntityTypes.java](fuzs/eternalnether/init/ModEntityTypes.java) |
| `ModBlocks` | class | — | — | 1 | 87 | [ModBlocks.java](fuzs/eternalnether/init/ModBlocks.java) |
| `ModBlockFamilies` | class | — | — | 1 | 33 | [ModBlockFamilies.java](fuzs/eternalnether/init/ModBlockFamilies.java) |
| `ModItems` | class | — | — | 2 | 97 | [ModItems.java](fuzs/eternalnether/init/ModItems.java) |
| `EternalNether` | class | ModConstructor | — | 9 | 145 | [EternalNether.java](fuzs/eternalnether/EternalNether.java) |
| `ModDatapackRegistriesProvider` | class | AbstractDatapackRegistriesProvider | — | 4 | 185 | [ModDatapackRegistriesProvider.java](fuzs/eternalnether/data/registries/ModDatapackRegistriesProvider.java) |
| `AbstractDatapackRegistriesProvider` | class | RegistriesDatapackGenerator | — | 3 | 57 | [AbstractDatapackRegistriesProvider.java](fuzs/eternalnether/data/registries/AbstractDatapackRegistriesProvider.java) |
| `ShieldDefenseGoal` | class | NearestAttackableTargetGoal | — | 6 | 63 | [ShieldDefenseGoal.java](fuzs/eternalnether/world/entity/ai/goal/ShieldDefenseGoal.java) |
| `ModFollowLeader` | class | Behavior | — | 5 | 65 | [ModFollowLeader.java](fuzs/eternalnether/world/entity/ai/behavior/ModFollowLeader.java) |
| `Wraither` | class | WitherSkeleton | — | 9 | 104 | [Wraither.java](fuzs/eternalnether/world/entity/monster/Wraither.java) |
| `ModPiglinBruteAi` | class | PiglinBruteAi | — | 5 | 71 | [ModPiglinBruteAi.java](fuzs/eternalnether/world/entity/monster/piglin/ModPiglinBruteAi.java) |
| `WitherSkeletonHorse` | class | SkeletonHorse | — | 9 | 103 | [WitherSkeletonHorse.java](fuzs/eternalnether/world/entity/animal/horse/WitherSkeletonHorse.java) |
| `CitadelStructure` | class | Structure | — | 5 | 121 | [CitadelStructure.java](fuzs/eternalnether/world/level/levelgen/structure/CitadelStructure.java) |
| `PiglinManorStructure` | class | Structure | — | 5 | 117 | [PiglinManorStructure.java](fuzs/eternalnether/world/level/levelgen/structure/PiglinManorStructure.java) |
| `WitherSkeletonKnightModel` | class | HumanoidModel | — | 6 | 135 | [WitherSkeletonKnightModel.java](fuzs/eternalnether/client/model/WitherSkeletonKnightModel.java) |
| `PiglinHunterModel` | class | PiglinModel | — | 5 | 75 | [PiglinHunterModel.java](fuzs/eternalnether/client/model/PiglinHunterModel.java) |
| `ResourceKeyHelper` | class | — | — | 4 | 32 | [ResourceKeyHelper.java](fuzs/eternalnether/init/ResourceKeyHelper.java) |
| `ModSoundProvider` | class | AbstractSoundDefinitionProvider | — | 1 | 65 | [ModSoundProvider.java](fuzs/eternalnether/neoforge/data/client/ModSoundProvider.java) |
| `ModAtlasProvider` | class | AbstractSpriteSourceProvider | — | 2 | 28 | [ModAtlasProvider.java](fuzs/eternalnether/neoforge/data/client/ModAtlasProvider.java) |
| `NeoForgeAbstractions` | class | CommonAbstractions | — | 2 | 18 | [NeoForgeAbstractions.java](fuzs/eternalnether/neoforge/services/NeoForgeAbstractions.java) |
| `ModAdvancementProvider` | class | AbstractAdvancementProvider | — | 1 | 165 | [ModAdvancementProvider.java](fuzs/eternalnether/data/ModAdvancementProvider.java) |
| `ModBiomeTagProvider` | class | AbstractTagProvider | — | 1 | 28 | [ModBiomeTagProvider.java](fuzs/eternalnether/data/tags/ModBiomeTagProvider.java) |
| `PiglinPrisonerSpecificSensor` | class | Sensor | — | 4 | 106 | [PiglinPrisonerSpecificSensor.java](fuzs/eternalnether/world/entity/ai/sensing/PiglinPrisonerSpecificSensor.java) |
| `ModPiglinBruteSpecificSensor` | class | PiglinBruteSpecificSensor | — | 2 | 37 | [ModPiglinBruteSpecificSensor.java](fuzs/eternalnether/world/entity/ai/sensing/ModPiglinBruteSpecificSensor.java) |
| `ShieldedMob` | interface | — | — | 0 | 10 | [ShieldedMob.java](fuzs/eternalnether/world/entity/monster/ShieldedMob.java) |
| `Wex` | class | Vex | — | 3 | 44 | [Wex.java](fuzs/eternalnether/world/entity/monster/Wex.java) |
| `Corpor` | class | WitherSkeleton | — | 2 | 32 | [Corpor.java](fuzs/eternalnether/world/entity/monster/Corpor.java) |
| `ThrownWarpedEnderpearl` | class | ThrownEnderpearl | — | 2 | 54 | [ThrownWarpedEnderpearl.java](fuzs/eternalnether/world/entity/projectile/ThrownWarpedEnderpearl.java) |
| `EnderPearlTeleportCallback` | interface | — | — | 0 | 17 | [EnderPearlTeleportCallback.java](fuzs/eternalnether/world/entity/projectile/EnderPearlTeleportCallback.java) |
| `CatacombStructure` | class | Structure | — | 3 | 95 | [CatacombStructure.java](fuzs/eternalnether/world/level/levelgen/structure/CatacombStructure.java) |
| `MobFeature` | class | Feature | — | 1 | 46 | [MobFeature.java](fuzs/eternalnether/world/level/levelgen/feature/MobFeature.java) |
| `MobPassengerFeature` | class | Feature | — | 2 | 49 | [MobPassengerFeature.java](fuzs/eternalnether/world/level/levelgen/feature/MobPassengerFeature.java) |
| `ModEnderEyesLayer` | class | — | — | 1 | 21 | [ModEnderEyesLayer.java](fuzs/eternalnether/client/renderer/entity/layers/ModEnderEyesLayer.java) |
| `WitherGlowLayer` | class | — | — | 2 | 41 | [WitherGlowLayer.java](fuzs/eternalnether/client/renderer/entity/layers/WitherGlowLayer.java) |
| `CorporModel` | class | HumanoidModel | — | 4 | 98 | [CorporModel.java](fuzs/eternalnether/client/model/CorporModel.java) |
| `WexModel` | class | HumanoidModel | — | 3 | 73 | [WexModel.java](fuzs/eternalnether/client/model/WexModel.java) |
| `WarpedEndermanModel` | class | EndermanModel | — | 2 | 66 | [WarpedEndermanModel.java](fuzs/eternalnether/client/model/WarpedEndermanModel.java) |
| `CommonAbstractions` | interface | — | — | 0 | 14 | [CommonAbstractions.java](fuzs/eternalnether/services/CommonAbstractions.java) |
| `ModSensorTypes` | class | — | — | 1 | 18 | [ModSensorTypes.java](fuzs/eternalnether/init/ModSensorTypes.java) |
| `ModFeatures` | class | — | — | 3 | 111 | [ModFeatures.java](fuzs/eternalnether/init/ModFeatures.java) |
| `ModStructures` | class | — | — | 1 | 37 | [ModStructures.java](fuzs/eternalnether/init/ModStructures.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_EternalNether1211_Common_2f8f2f7902eb4f658ac36a2b72da8964_ce56455b21237114b17bb445b85e294de3b75aa6e38a57539a0fa5d24658e6c0EternalNetherv21131211Commondevjar/PlatformMethods.java) |
