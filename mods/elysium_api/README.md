# Elysium-API（`elysium_api`）

> jar：`[前置]ElysiumAPI.jar` ｜ 版本：`2.0.1` ｜ 作者：ThatJadenXgamer
> 规模：**107 个类 / 45 个包 / 6596 行** ｜ 事件处理类 2 ｜ Mixin 34 ｜ 注册/管理类 10

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.jadenxgamer.elysium_api.impl.mixin.block` | 10 | `NetherSproutsBlockMixin`、`BlockBehaviorMixin`、`BlockMixin` |
| `net.jadenxgamer.elysium_api.impl.mixin.client` | 5 | `FallbackResourceManagerMixin`、`ReloadableResourceManagerMixin`、`GameRendererMixin` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.block.use_behaviors` | 5 | `AfterUseItemEnum`、`UseBehaviorImpl`、`UseBehavior` |
| `net.jadenxgamer.elysium_api.api.util` | 4 | `LookupRegistryHelper`、`RegistryAccessHelper`、`ClientItemData` |
| `net.jadenxgamer.elysium_api.impl.mixin.entity` | 4 | `LivingEntityMixin`、`AbstractClientPlayerMixin`、`PlayerSkinAccessor` |
| `net.jadenxgamer.elysium_api.impl.mixin.enchantment` | 4 | `ItemEnchantmentsMutableMixin`、`EnchantmentHelperMixin`、`EnchantRandomlyFunctionMixin` |
| `net.jadenxgamer.elysium_api.impl.compat` | 4 | `ElysiumEmiBrewingRecipe`、`ElysiumTerrablenderHelper`、`ElysiumEmiPlugin` |
| `net.jadenxgamer.elysium_api.impl.util` | 4 | `BlockStatePropertiesCondition`、`StructureHelper`、`ConditionalBoolean` |
| `net.jadenxgamer.elysium_api.impl.registry` | 4 | `ElysiumItems`、`ElysiumBlocks`、`ElysiumRegistries` |
| `net.jadenxgamer.elysium_api.impl.mixin.brewing` | 3 | `BrewingStandBlockEntityMixin`、`PotionBrewingMixin`、`PotionSlotMixin` |
| `net.jadenxgamer.elysium_api.impl.mixin.item` | 3 | `IItemExtensionMixin`、`ItemStackMixin`、`ItemMixin` |
| `net.jadenxgamer.elysium_api.impl.mixin.misc` | 3 | `DecoratedPotPatternsMixin`、`JigsawReplacementProcessorMixin`、`StructureTemplateMixin` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer` | 3 | `BlockPropertiesTransformerHelper`、`BlockPropertiesTransformer`、`PushReactionEnum` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.item.properties_transformer` | 3 | `ItemPropertiesTransformerHelper`、`ItemPropertiesTransformer`、`RarityEnum` |
| `net.jadenxgamer.elysium_api.impl.core.block` | 3 | `MobBarrierBlock`、`SolidStructureVoidBlock`、`GlassStructureVoidBlock` |
| `net.jadenxgamer.elysium_api.impl.config` | 3 | `QuickPlaceConfig`、`ElysiumConfig`、`ElysiumConfigImpl` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.mosaic` | 3 | `EntryType`、`SubBiomeBehavior`、`MosaicBiomeEntry` |
| `net.jadenxgamer.elysium_api.impl.event` | 2 | `ElysiumClientEvents`、`ElysiumEvents` |
| `net.jadenxgamer.elysium_api` | 2 | `ElysiumAPIClient`、`ElysiumAPI` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.brewing_recipe` | 2 | `ElysiumBrewingRecipeHelper`、`ElysiumBrewingRecipe` |
| `net.jadenxgamer.elysium_api.impl.client.assetdriven.fog_settings` | 2 | `FogSettingsManager`、`FogSettings` |
| `net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings` | 2 | `LightmapSettingsManager`、`LightmapSettings` |
| `net.jadenxgamer.elysium_api.api.surface_rules` | 2 | `SurfaceRulesRegistry`、`ElysiumSurfaceRules` |
| `net.jadenxgamer.elysium_api.impl.core.item` | 2 | `PanoramaScreenshotItem`、`ElysiumArrowItem` |
| `net.jadenxgamer.elysium_api.impl.client.conditional_resource` | 2 | `ClientIConditionContext`、`ConditionalAssetsHelper` |
| `net.jadenxgamer.elysium_api.api.surface_rules.condition` | 2 | `BlockMatchConditionSource`、`BiomeTagConditionSource` |
| `net.jadenxgamer.elysium_api.impl.core.misc.neoforge` | 2 | `ElysiumDataMaps`、`EffectsBiomeModifier` |
| `net.jadenxgamer.elysium_api.impl.core.worldgen.structure` | 2 | `GiantJigsawStructure`、`MultilayerJigsawStructure` |
| `net.jadenxgamer.elysium_api.impl.mixin.compat` | 1 | `LevelUtilsMixin` |
| `net.jadenxgamer.elysium_api.impl.core.biome` | 1 | `MosaicBiomeSource` |
| `net.jadenxgamer.elysium_api.impl.mixin.biome` | 1 | `NoiseGeneratorSettingsAccessor` |
| `net.jadenxgamer.elysium_api.api.event` | 1 | `BlockOnPlaceEvent` |
| `net.jadenxgamer.elysium_api.impl.core.surface_rules` | 1 | `ElysiumSurfaceRulesManager` |
| `net.jadenxgamer.elysium_api.api.registry` | 1 | `ElysiumReflection` |
| `net.jadenxgamer.elysium_api.api.client.screen_flash` | 1 | `ScreenFlash` |
| `net.jadenxgamer.elysium_api.impl.networking.to_client` | 1 | `ScreenFlashPayload` |
| `net.jadenxgamer.elysium_api.impl.core.datadriven.block` | 1 | `BlockSoundTransformer` |
| `net.jadenxgamer.elysium_api.api.extensions` | 1 | `IElysiumBlockExtension` |
| `net.jadenxgamer.elysium_api.api.client.sound` | 1 | `LoopedEntityBoundSoundInstance` |
| `net.jadenxgamer.elysium_api.impl.core.worldgen.feature` | 1 | `StructureStamp` |
| … | | 其余 5 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ElysiumClientEvents` | class | — | EventBusSubscriber | 7 | 92 | [ElysiumClientEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumClientEvents.java) |
| `ElysiumEvents` | class | — | EventBusSubscriber | 8 | 85 | [ElysiumEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumEvents.java) |
| `ElysiumAPIClient` | class | — | Mod | 0 | 13 | [ElysiumAPIClient.java](net/jadenxgamer/elysium_api/ElysiumAPIClient.java) |
| `FallbackResourceManagerMixin` | class | — | Mixin | 0 | 80 | [FallbackResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/FallbackResourceManagerMixin.java) |
| `ReloadableResourceManagerMixin` | class | — | Mixin | 1 | 33 | [ReloadableResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ReloadableResourceManagerMixin.java) |
| `ElysiumAPI` | class | — | Mod | 2 | 41 | [ElysiumAPI.java](net/jadenxgamer/elysium_api/ElysiumAPI.java) |
| `BrewingStandBlockEntityMixin` | class | — | Mixin | 0 | 20 | [BrewingStandBlockEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/BrewingStandBlockEntityMixin.java) |
| `LookupRegistryHelper` | class | — | — | 10 | 58 | [LookupRegistryHelper.java](net/jadenxgamer/elysium_api/api/util/LookupRegistryHelper.java) |
| `LivingEntityMixin` | class | — | Mixin | 0 | 33 | [LivingEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/LivingEntityMixin.java) |
| `AbstractClientPlayerMixin` | class | — | Mixin | 1 | 35 | [AbstractClientPlayerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/AbstractClientPlayerMixin.java) |
| `NetherSproutsBlockMixin` | class | — | Mixin | 0 | 22 | [NetherSproutsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherSproutsBlockMixin.java) |
| `BlockBehaviorMixin` | class | — | Mixin | 0 | 38 | [BlockBehaviorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockBehaviorMixin.java) |
| `BlockMixin` | class | IElysiumBlockExtension | Mixin | 0 | 50 | [BlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockMixin.java) |
| `BlockStateBaseMixin` | class | — | Mixin | 2 | 154 | [BlockStateBaseMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockStateBaseMixin.java) |
| `NetherWartBlockMixin` | class | — | Mixin | 0 | 22 | [NetherWartBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherWartBlockMixin.java) |
| `SoulFireBlockMixin` | class | BaseFireBlock | Mixin | 0 | 31 | [SoulFireBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/SoulFireBlockMixin.java) |
| `FungusBlockMixin` | class | — | Mixin | 0 | 22 | [FungusBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/FungusBlockMixin.java) |
| `RootsBlockMixin` | class | — | Mixin | 0 | 22 | [RootsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/RootsBlockMixin.java) |
| `BlockAccessor` | interface | — | Mixin | 0 | 15 | [BlockAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockAccessor.java) |
| `ItemEnchantmentsMutableMixin` | class | — | Mixin | 0 | 21 | [ItemEnchantmentsMutableMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/ItemEnchantmentsMutableMixin.java) |
| `IItemExtensionMixin` | interface | — | Mixin | 0 | 58 | [IItemExtensionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/IItemExtensionMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 64 | [ItemStackMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemStackMixin.java) |
| `ItemMixin` | class | — | Mixin | 1 | 26 | [ItemMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 43 | [GameRendererMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/GameRendererMixin.java) |
| `ClientLevelMixin` | class | — | Mixin | 0 | 35 | [ClientLevelMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ClientLevelMixin.java) |
| `RegistryAccessHelper` | class | — | — | 7 | 51 | [RegistryAccessHelper.java](net/jadenxgamer/elysium_api/api/util/RegistryAccessHelper.java) |
| `EnchantmentHelperMixin` | class | — | Mixin | 0 | 27 | [EnchantmentHelperMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantmentHelperMixin.java) |
| `LevelUtilsMixin` | class | — | Mixin、Pseudo | 0 | 61 | [LevelUtilsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/compat/LevelUtilsMixin.java) |
| `ElysiumBrewingRecipeHelper` | class | — | — | 5 | 38 | [ElysiumBrewingRecipeHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/brewing_recipe/ElysiumBrewingRecipeHelper.java) |
| `MosaicBiomeSource` | class | BiomeSource | — | 20 | 537 | [MosaicBiomeSource.java](net/jadenxgamer/elysium_api/impl/core/biome/MosaicBiomeSource.java) |
| `PlayerSkinAccessor` | interface | — | Mixin | 0 | 17 | [PlayerSkinAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PlayerSkinAccessor.java) |
| `PiglinAiMixin` | class | — | Mixin | 0 | 20 | [PiglinAiMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PiglinAiMixin.java) |
| `PistonStructureResolverMixin` | class | — | Mixin | 0 | 76 | [PistonStructureResolverMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/PistonStructureResolverMixin.java) |
| `EnchantRandomlyFunctionMixin` | class | — | Mixin | 0 | 24 | [EnchantRandomlyFunctionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantRandomlyFunctionMixin.java) |
| `CreativeModeTabMixin` | class | — | Mixin | 1 | 48 | [CreativeModeTabMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/CreativeModeTabMixin.java) |
| `PotionBrewingMixin` | class | — | Mixin | 0 | 28 | [PotionBrewingMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionBrewingMixin.java) |
| `PotionSlotMixin` | class | — | Mixin | 0 | 25 | [PotionSlotMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionSlotMixin.java) |
| `NoiseGeneratorSettingsAccessor` | interface | — | Mixin | 0 | 17 | [NoiseGeneratorSettingsAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/biome/NoiseGeneratorSettingsAccessor.java) |
| `DecoratedPotPatternsMixin` | class | — | Mixin | 0 | 24 | [DecoratedPotPatternsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/DecoratedPotPatternsMixin.java) |
| `JigsawReplacementProcessorMixin` | class | — | Mixin | 0 | 32 | [JigsawReplacementProcessorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/JigsawReplacementProcessorMixin.java) |
| `StructureTemplateMixin` | class | — | Mixin | 1 | 37 | [StructureTemplateMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/StructureTemplateMixin.java) |
| `LightTextureMixin` | class | — | Mixin | 0 | 77 | [LightTextureMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/LightTextureMixin.java) |
| `FogSettingsManager` | class | SimpleJsonResourceReloadListener | — | 5 | 114 | [FogSettingsManager.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/fog_settings/FogSettingsManager.java) |
| `LightmapSettingsManager` | class | SimpleJsonResourceReloadListener | — | 7 | 131 | [LightmapSettingsManager.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/lightmap_settings/LightmapSettingsManager.java) |
| `SurfaceRulesRegistry` | class | — | — | 2 | 25 | [SurfaceRulesRegistry.java](net/jadenxgamer/elysium_api/api/surface_rules/SurfaceRulesRegistry.java) |
| `BlockOnPlaceEvent` | class | Event | — | 5 | 43 | [BlockOnPlaceEvent.java](net/jadenxgamer/elysium_api/api/event/BlockOnPlaceEvent.java) |
| `ClientItemData` | class | — | — | 5 | 39 | [ClientItemData.java](net/jadenxgamer/elysium_api/api/util/ClientItemData.java) |
| `ElysiumSurfaceRulesManager` | class | — | — | 2 | 47 | [ElysiumSurfaceRulesManager.java](net/jadenxgamer/elysium_api/impl/core/surface_rules/ElysiumSurfaceRulesManager.java) |
| `BlockPropertiesTransformerHelper` | class | — | — | 6 | 204 | [BlockPropertiesTransformerHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/properties_transformer/BlockPropertiesTransformerHelper.java) |
| `ElysiumEmiBrewingRecipe` | class | EmiRecipe | — | 8 | 77 | [ElysiumEmiBrewingRecipe.java](net/jadenxgamer/elysium_api/impl/compat/ElysiumEmiBrewingRecipe.java) |
| `BlockStatePropertiesCondition` | class | — | — | 14 | 218 | [BlockStatePropertiesCondition.java](net/jadenxgamer/elysium_api/impl/util/BlockStatePropertiesCondition.java) |
| `ElysiumTimeTracker` | class | ElysiumTimeTracker.TimeTracker | — | 23 | 219 | [ElysiumTimeTracker.java](net/jadenxgamer/elysium_api/api/util/ElysiumTimeTracker.java) |
| `AfterUseItemEnum` | enum | StringRepresentable | — | 2 | 29 | [AfterUseItemEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/AfterUseItemEnum.java) |
| `ItemPropertiesTransformerHelper` | class | — | — | 5 | 115 | [ItemPropertiesTransformerHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/item/properties_transformer/ItemPropertiesTransformerHelper.java) |
| `ElysiumBrewingRecipe` | record | — | — | 3 | 56 | [ElysiumBrewingRecipe.java](net/jadenxgamer/elysium_api/impl/core/datadriven/brewing_recipe/ElysiumBrewingRecipe.java) |
| `MobBarrierBlock` | class | Block | — | 11 | 86 | [MobBarrierBlock.java](net/jadenxgamer/elysium_api/impl/core/block/MobBarrierBlock.java) |
| `PanoramaScreenshotItem` | class | Item | — | 1 | 63 | [PanoramaScreenshotItem.java](net/jadenxgamer/elysium_api/impl/core/item/PanoramaScreenshotItem.java) |
| `QuickPlaceConfig` | enum | StringRepresentable | — | 1 | 21 | [QuickPlaceConfig.java](net/jadenxgamer/elysium_api/impl/config/QuickPlaceConfig.java) |
| `ElysiumConfig` | class | — | — | 0 | 22 | [ElysiumConfig.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfig.java) |
| `ElysiumConfigImpl` | class | — | — | 1 | 23 | [ElysiumConfigImpl.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfigImpl.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ElysiumClientEvents` | 7 | `net.jadenxgamer.elysium_api.impl.event` | [ElysiumClientEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumClientEvents.java) |
| `ElysiumEvents` | 7 | `net.jadenxgamer.elysium_api.impl.event` | [ElysiumEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumEvents.java) |

## Mixin（34 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `FallbackResourceManagerMixin` | `FallbackResourceManager` | [FallbackResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/FallbackResourceManagerMixin.java) |
| `ReloadableResourceManagerMixin` | `ReloadableResourceManager` | [ReloadableResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ReloadableResourceManagerMixin.java) |
| `BrewingStandBlockEntityMixin` | `BrewingStandBlockEntity` | [BrewingStandBlockEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/BrewingStandBlockEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/LivingEntityMixin.java) |
| `AbstractClientPlayerMixin` | `AbstractClientPlayer` | [AbstractClientPlayerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/AbstractClientPlayerMixin.java) |
| `NetherSproutsBlockMixin` | `NetherSproutsBlock` | [NetherSproutsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherSproutsBlockMixin.java) |
| `BlockBehaviorMixin` | `BlockBehavior` | [BlockBehaviorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockBehaviorMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockMixin.java) |
| `BlockStateBaseMixin` | `BlockStateBase` | [BlockStateBaseMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockStateBaseMixin.java) |
| `NetherWartBlockMixin` | `NetherWartBlock` | [NetherWartBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherWartBlockMixin.java) |
| `SoulFireBlockMixin` | `SoulFireBlock` | [SoulFireBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/SoulFireBlockMixin.java) |
| `FungusBlockMixin` | `FungusBlock` | [FungusBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/FungusBlockMixin.java) |
| `RootsBlockMixin` | `RootsBlock` | [RootsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/RootsBlockMixin.java) |
| `BlockAccessor` | `BlockAccessor` | [BlockAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockAccessor.java) |
| `ItemEnchantmentsMutableMixin` | `ItemEnchantmentsMutable` | [ItemEnchantmentsMutableMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/ItemEnchantmentsMutableMixin.java) |
| `IItemExtensionMixin` | `IItemExtension` | [IItemExtensionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/IItemExtensionMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemStackMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemMixin.java) |
| `GameRendererMixin` | `GameRenderer` | [GameRendererMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/GameRendererMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ClientLevelMixin.java) |
| `EnchantmentHelperMixin` | `EnchantmentHelper` | [EnchantmentHelperMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantmentHelperMixin.java) |
| `LevelUtilsMixin` | `LevelUtils` | [LevelUtilsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/compat/LevelUtilsMixin.java) |
| `PlayerSkinAccessor` | `PlayerSkinAccessor` | [PlayerSkinAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PlayerSkinAccessor.java) |
| `PiglinAiMixin` | `PiglinAi` | [PiglinAiMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PiglinAiMixin.java) |
| `PistonStructureResolverMixin` | `PistonStructureResolver` | [PistonStructureResolverMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/PistonStructureResolverMixin.java) |
| `EnchantRandomlyFunctionMixin` | `EnchantRandomlyFunction` | [EnchantRandomlyFunctionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantRandomlyFunctionMixin.java) |
| `CreativeModeTabMixin` | `CreativeModeTab` | [CreativeModeTabMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/CreativeModeTabMixin.java) |
| `PotionBrewingMixin` | `PotionBrewing` | [PotionBrewingMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionBrewingMixin.java) |
| `PotionSlotMixin` | `PotionSlot` | [PotionSlotMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionSlotMixin.java) |
| `NoiseGeneratorSettingsAccessor` | `NoiseGeneratorSettingsAccessor` | [NoiseGeneratorSettingsAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/biome/NoiseGeneratorSettingsAccessor.java) |
| `DecoratedPotPatternsMixin` | `DecoratedPotPatterns` | [DecoratedPotPatternsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/DecoratedPotPatternsMixin.java) |
| `JigsawReplacementProcessorMixin` | `JigsawReplacementProcessor` | [JigsawReplacementProcessorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/JigsawReplacementProcessorMixin.java) |
| `StructureTemplateMixin` | `StructureTemplate` | [StructureTemplateMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/StructureTemplateMixin.java) |
| `LightTextureMixin` | `LightTexture` | [LightTextureMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/LightTextureMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `QuickPlaceConfig` | — | [QuickPlaceConfig.java](net/jadenxgamer/elysium_api/impl/config/QuickPlaceConfig.java) |
| `ElysiumConfig` | — | [ElysiumConfig.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfig.java) |
| `ElysiumConfigImpl` | — | [ElysiumConfigImpl.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfigImpl.java) |

## 全部类（107）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ElysiumClientEvents` | class | — | EventBusSubscriber | 7 | 92 | [ElysiumClientEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumClientEvents.java) |
| `ElysiumEvents` | class | — | EventBusSubscriber | 8 | 85 | [ElysiumEvents.java](net/jadenxgamer/elysium_api/impl/event/ElysiumEvents.java) |
| `ElysiumAPIClient` | class | — | Mod | 0 | 13 | [ElysiumAPIClient.java](net/jadenxgamer/elysium_api/ElysiumAPIClient.java) |
| `FallbackResourceManagerMixin` | class | — | Mixin | 0 | 80 | [FallbackResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/FallbackResourceManagerMixin.java) |
| `ReloadableResourceManagerMixin` | class | — | Mixin | 1 | 33 | [ReloadableResourceManagerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ReloadableResourceManagerMixin.java) |
| `ElysiumAPI` | class | — | Mod | 2 | 41 | [ElysiumAPI.java](net/jadenxgamer/elysium_api/ElysiumAPI.java) |
| `BrewingStandBlockEntityMixin` | class | — | Mixin | 0 | 20 | [BrewingStandBlockEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/BrewingStandBlockEntityMixin.java) |
| `LookupRegistryHelper` | class | — | — | 10 | 58 | [LookupRegistryHelper.java](net/jadenxgamer/elysium_api/api/util/LookupRegistryHelper.java) |
| `LivingEntityMixin` | class | — | Mixin | 0 | 33 | [LivingEntityMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/LivingEntityMixin.java) |
| `AbstractClientPlayerMixin` | class | — | Mixin | 1 | 35 | [AbstractClientPlayerMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/AbstractClientPlayerMixin.java) |
| `NetherSproutsBlockMixin` | class | — | Mixin | 0 | 22 | [NetherSproutsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherSproutsBlockMixin.java) |
| `BlockBehaviorMixin` | class | — | Mixin | 0 | 38 | [BlockBehaviorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockBehaviorMixin.java) |
| `BlockMixin` | class | IElysiumBlockExtension | Mixin | 0 | 50 | [BlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockMixin.java) |
| `BlockStateBaseMixin` | class | — | Mixin | 2 | 154 | [BlockStateBaseMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockStateBaseMixin.java) |
| `NetherWartBlockMixin` | class | — | Mixin | 0 | 22 | [NetherWartBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/NetherWartBlockMixin.java) |
| `SoulFireBlockMixin` | class | BaseFireBlock | Mixin | 0 | 31 | [SoulFireBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/SoulFireBlockMixin.java) |
| `FungusBlockMixin` | class | — | Mixin | 0 | 22 | [FungusBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/FungusBlockMixin.java) |
| `RootsBlockMixin` | class | — | Mixin | 0 | 22 | [RootsBlockMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/RootsBlockMixin.java) |
| `BlockAccessor` | interface | — | Mixin | 0 | 15 | [BlockAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/block/BlockAccessor.java) |
| `ItemEnchantmentsMutableMixin` | class | — | Mixin | 0 | 21 | [ItemEnchantmentsMutableMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/ItemEnchantmentsMutableMixin.java) |
| `IItemExtensionMixin` | interface | — | Mixin | 0 | 58 | [IItemExtensionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/IItemExtensionMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 64 | [ItemStackMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemStackMixin.java) |
| `ItemMixin` | class | — | Mixin | 1 | 26 | [ItemMixin.java](net/jadenxgamer/elysium_api/impl/mixin/item/ItemMixin.java) |
| `GameRendererMixin` | class | — | Mixin | 0 | 43 | [GameRendererMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/GameRendererMixin.java) |
| `ClientLevelMixin` | class | — | Mixin | 0 | 35 | [ClientLevelMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/ClientLevelMixin.java) |
| `RegistryAccessHelper` | class | — | — | 7 | 51 | [RegistryAccessHelper.java](net/jadenxgamer/elysium_api/api/util/RegistryAccessHelper.java) |
| `EnchantmentHelperMixin` | class | — | Mixin | 0 | 27 | [EnchantmentHelperMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantmentHelperMixin.java) |
| `LevelUtilsMixin` | class | — | Mixin、Pseudo | 0 | 61 | [LevelUtilsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/compat/LevelUtilsMixin.java) |
| `ElysiumBrewingRecipeHelper` | class | — | — | 5 | 38 | [ElysiumBrewingRecipeHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/brewing_recipe/ElysiumBrewingRecipeHelper.java) |
| `MosaicBiomeSource` | class | BiomeSource | — | 20 | 537 | [MosaicBiomeSource.java](net/jadenxgamer/elysium_api/impl/core/biome/MosaicBiomeSource.java) |
| `PlayerSkinAccessor` | interface | — | Mixin | 0 | 17 | [PlayerSkinAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PlayerSkinAccessor.java) |
| `PiglinAiMixin` | class | — | Mixin | 0 | 20 | [PiglinAiMixin.java](net/jadenxgamer/elysium_api/impl/mixin/entity/PiglinAiMixin.java) |
| `PistonStructureResolverMixin` | class | — | Mixin | 0 | 76 | [PistonStructureResolverMixin.java](net/jadenxgamer/elysium_api/impl/mixin/block/PistonStructureResolverMixin.java) |
| `EnchantRandomlyFunctionMixin` | class | — | Mixin | 0 | 24 | [EnchantRandomlyFunctionMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/EnchantRandomlyFunctionMixin.java) |
| `CreativeModeTabMixin` | class | — | Mixin | 1 | 48 | [CreativeModeTabMixin.java](net/jadenxgamer/elysium_api/impl/mixin/enchantment/CreativeModeTabMixin.java) |
| `PotionBrewingMixin` | class | — | Mixin | 0 | 28 | [PotionBrewingMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionBrewingMixin.java) |
| `PotionSlotMixin` | class | — | Mixin | 0 | 25 | [PotionSlotMixin.java](net/jadenxgamer/elysium_api/impl/mixin/brewing/PotionSlotMixin.java) |
| `NoiseGeneratorSettingsAccessor` | interface | — | Mixin | 0 | 17 | [NoiseGeneratorSettingsAccessor.java](net/jadenxgamer/elysium_api/impl/mixin/biome/NoiseGeneratorSettingsAccessor.java) |
| `DecoratedPotPatternsMixin` | class | — | Mixin | 0 | 24 | [DecoratedPotPatternsMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/DecoratedPotPatternsMixin.java) |
| `JigsawReplacementProcessorMixin` | class | — | Mixin | 0 | 32 | [JigsawReplacementProcessorMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/JigsawReplacementProcessorMixin.java) |
| `StructureTemplateMixin` | class | — | Mixin | 1 | 37 | [StructureTemplateMixin.java](net/jadenxgamer/elysium_api/impl/mixin/misc/StructureTemplateMixin.java) |
| `LightTextureMixin` | class | — | Mixin | 0 | 77 | [LightTextureMixin.java](net/jadenxgamer/elysium_api/impl/mixin/client/LightTextureMixin.java) |
| `FogSettingsManager` | class | SimpleJsonResourceReloadListener | — | 5 | 114 | [FogSettingsManager.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/fog_settings/FogSettingsManager.java) |
| `LightmapSettingsManager` | class | SimpleJsonResourceReloadListener | — | 7 | 131 | [LightmapSettingsManager.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/lightmap_settings/LightmapSettingsManager.java) |
| `SurfaceRulesRegistry` | class | — | — | 2 | 25 | [SurfaceRulesRegistry.java](net/jadenxgamer/elysium_api/api/surface_rules/SurfaceRulesRegistry.java) |
| `BlockOnPlaceEvent` | class | Event | — | 5 | 43 | [BlockOnPlaceEvent.java](net/jadenxgamer/elysium_api/api/event/BlockOnPlaceEvent.java) |
| `ClientItemData` | class | — | — | 5 | 39 | [ClientItemData.java](net/jadenxgamer/elysium_api/api/util/ClientItemData.java) |
| `ElysiumSurfaceRulesManager` | class | — | — | 2 | 47 | [ElysiumSurfaceRulesManager.java](net/jadenxgamer/elysium_api/impl/core/surface_rules/ElysiumSurfaceRulesManager.java) |
| `BlockPropertiesTransformerHelper` | class | — | — | 6 | 204 | [BlockPropertiesTransformerHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/properties_transformer/BlockPropertiesTransformerHelper.java) |
| `ElysiumEmiBrewingRecipe` | class | EmiRecipe | — | 8 | 77 | [ElysiumEmiBrewingRecipe.java](net/jadenxgamer/elysium_api/impl/compat/ElysiumEmiBrewingRecipe.java) |
| `BlockStatePropertiesCondition` | class | — | — | 14 | 218 | [BlockStatePropertiesCondition.java](net/jadenxgamer/elysium_api/impl/util/BlockStatePropertiesCondition.java) |
| `ElysiumTimeTracker` | class | ElysiumTimeTracker.TimeTracker | — | 23 | 219 | [ElysiumTimeTracker.java](net/jadenxgamer/elysium_api/api/util/ElysiumTimeTracker.java) |
| `AfterUseItemEnum` | enum | StringRepresentable | — | 2 | 29 | [AfterUseItemEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/AfterUseItemEnum.java) |
| `ItemPropertiesTransformerHelper` | class | — | — | 5 | 115 | [ItemPropertiesTransformerHelper.java](net/jadenxgamer/elysium_api/impl/core/datadriven/item/properties_transformer/ItemPropertiesTransformerHelper.java) |
| `ElysiumBrewingRecipe` | record | — | — | 3 | 56 | [ElysiumBrewingRecipe.java](net/jadenxgamer/elysium_api/impl/core/datadriven/brewing_recipe/ElysiumBrewingRecipe.java) |
| `MobBarrierBlock` | class | Block | — | 11 | 86 | [MobBarrierBlock.java](net/jadenxgamer/elysium_api/impl/core/block/MobBarrierBlock.java) |
| `PanoramaScreenshotItem` | class | Item | — | 1 | 63 | [PanoramaScreenshotItem.java](net/jadenxgamer/elysium_api/impl/core/item/PanoramaScreenshotItem.java) |
| `QuickPlaceConfig` | enum | StringRepresentable | — | 1 | 21 | [QuickPlaceConfig.java](net/jadenxgamer/elysium_api/impl/config/QuickPlaceConfig.java) |
| `ElysiumConfig` | class | — | — | 0 | 22 | [ElysiumConfig.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfig.java) |
| `ElysiumConfigImpl` | class | — | — | 1 | 23 | [ElysiumConfigImpl.java](net/jadenxgamer/elysium_api/impl/config/ElysiumConfigImpl.java) |
| `ElysiumReflection` | class | — | — | 17 | 237 | [ElysiumReflection.java](net/jadenxgamer/elysium_api/api/registry/ElysiumReflection.java) |
| `SolidStructureVoidBlock` | class | Block | — | 5 | 69 | [SolidStructureVoidBlock.java](net/jadenxgamer/elysium_api/impl/core/block/SolidStructureVoidBlock.java) |
| `ScreenFlash` | class | — | — | 5 | 110 | [ScreenFlash.java](net/jadenxgamer/elysium_api/api/client/screen_flash/ScreenFlash.java) |
| `ScreenFlashPayload` | record | CustomPacketPayload | — | 2 | 41 | [ScreenFlashPayload.java](net/jadenxgamer/elysium_api/impl/networking/to_client/ScreenFlashPayload.java) |
| `BlockSoundTransformer` | record | — | — | 1 | 41 | [BlockSoundTransformer.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/BlockSoundTransformer.java) |
| `UseBehaviorImpl` | class | — | — | 9 | 203 | [UseBehaviorImpl.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/UseBehaviorImpl.java) |
| `BlockPropertiesTransformer` | record | — | — | 2 | 87 | [BlockPropertiesTransformer.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/properties_transformer/BlockPropertiesTransformer.java) |
| `ItemPropertiesTransformer` | record | — | — | 1 | 46 | [ItemPropertiesTransformer.java](net/jadenxgamer/elysium_api/impl/core/datadriven/item/properties_transformer/ItemPropertiesTransformer.java) |
| `GlassStructureVoidBlock` | class | SolidStructureVoidBlock | — | 4 | 33 | [GlassStructureVoidBlock.java](net/jadenxgamer/elysium_api/impl/core/block/GlassStructureVoidBlock.java) |
| `ElysiumArrowItem` | class | ArrowItem | — | 2 | 51 | [ElysiumArrowItem.java](net/jadenxgamer/elysium_api/impl/core/item/ElysiumArrowItem.java) |
| `ElysiumItems` | class | — | — | 1 | 24 | [ElysiumItems.java](net/jadenxgamer/elysium_api/impl/registry/ElysiumItems.java) |
| `ElysiumBlocks` | class | — | — | 2 | 49 | [ElysiumBlocks.java](net/jadenxgamer/elysium_api/impl/registry/ElysiumBlocks.java) |
| `ClientIConditionContext` | class | IContext | — | 2 | 31 | [ClientIConditionContext.java](net/jadenxgamer/elysium_api/impl/client/conditional_resource/ClientIConditionContext.java) |
| `BlockMatchConditionSource` | record | ConditionSource | — | 3 | 37 | [BlockMatchConditionSource.java](net/jadenxgamer/elysium_api/api/surface_rules/condition/BlockMatchConditionSource.java) |
| `IElysiumBlockExtension` | interface | — | — | 0 | 11 | [IElysiumBlockExtension.java](net/jadenxgamer/elysium_api/api/extensions/IElysiumBlockExtension.java) |
| `LoopedEntityBoundSoundInstance` | class | AbstractTickableSoundInstance | OnlyIn | 3 | 45 | [LoopedEntityBoundSoundInstance.java](net/jadenxgamer/elysium_api/api/client/sound/LoopedEntityBoundSoundInstance.java) |
| `StructureStamp` | class | Feature | — | 7 | 185 | [StructureStamp.java](net/jadenxgamer/elysium_api/impl/core/worldgen/feature/StructureStamp.java) |
| `ElysiumDataMaps` | class | — | — | 0 | 16 | [ElysiumDataMaps.java](net/jadenxgamer/elysium_api/impl/core/misc/neoforge/ElysiumDataMaps.java) |
| `ElysiumMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 37 | [ElysiumMixinPlugin.java](net/jadenxgamer/elysium_api/impl/mixin/ElysiumMixinPlugin.java) |
| `ElysiumTerrablenderHelper` | class | — | — | 2 | 16 | [ElysiumTerrablenderHelper.java](net/jadenxgamer/elysium_api/impl/compat/ElysiumTerrablenderHelper.java) |
| `StructureHelper` | class | — | — | 0 | 10 | [StructureHelper.java](net/jadenxgamer/elysium_api/impl/util/StructureHelper.java) |
| `ConditionalAssetsHelper` | class | — | — | 3 | 101 | [ConditionalAssetsHelper.java](net/jadenxgamer/elysium_api/impl/client/conditional_resource/ConditionalAssetsHelper.java) |
| `ElysiumPayloads` | class | — | — | 1 | 15 | [ElysiumPayloads.java](net/jadenxgamer/elysium_api/impl/networking/ElysiumPayloads.java) |
| `UseBehavior` | record | — | — | 4 | 100 | [UseBehavior.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/UseBehavior.java) |
| `PosEnum` | enum | StringRepresentable | — | 2 | 35 | [PosEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/PosEnum.java) |
| `UseBehaviorTypeEnum` | enum | StringRepresentable | — | 2 | 32 | [UseBehaviorTypeEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/use_behaviors/UseBehaviorTypeEnum.java) |
| `PushReactionEnum` | enum | StringRepresentable | — | 2 | 37 | [PushReactionEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/block/properties_transformer/PushReactionEnum.java) |
| `RarityEnum` | enum | StringRepresentable | — | 2 | 35 | [RarityEnum.java](net/jadenxgamer/elysium_api/impl/core/datadriven/item/properties_transformer/RarityEnum.java) |
| `EntryType` | enum | StringRepresentable | — | 1 | 21 | [EntryType.java](net/jadenxgamer/elysium_api/impl/core/datadriven/mosaic/EntryType.java) |
| `SubBiomeBehavior` | enum | StringRepresentable | — | 1 | 22 | [SubBiomeBehavior.java](net/jadenxgamer/elysium_api/impl/core/datadriven/mosaic/SubBiomeBehavior.java) |
| `MosaicBiomeEntry` | record | — | — | 1 | 42 | [MosaicBiomeEntry.java](net/jadenxgamer/elysium_api/impl/core/datadriven/mosaic/MosaicBiomeEntry.java) |
| `GiantJigsawStructure` | class | Structure | — | 3 | 156 | [GiantJigsawStructure.java](net/jadenxgamer/elysium_api/impl/core/worldgen/structure/GiantJigsawStructure.java) |
| `MultilayerJigsawStructure` | class | Structure | — | 4 | 145 | [MultilayerJigsawStructure.java](net/jadenxgamer/elysium_api/impl/core/worldgen/structure/MultilayerJigsawStructure.java) |
| `ProtectNonReplaceableProcessor` | class | StructureProcessor | — | 3 | 46 | [ProtectNonReplaceableProcessor.java](net/jadenxgamer/elysium_api/impl/core/worldgen/structure/processor/ProtectNonReplaceableProcessor.java) |
| `EffectsBiomeModifier` | record | BiomeModifier | — | 2 | 77 | [EffectsBiomeModifier.java](net/jadenxgamer/elysium_api/impl/core/misc/neoforge/EffectsBiomeModifier.java) |
| `ElysiumEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 1 | 39 | [ElysiumEmiPlugin.java](net/jadenxgamer/elysium_api/impl/compat/ElysiumEmiPlugin.java) |
| `ElysiumJeiPlugin` | class | IModPlugin | JeiPlugin | 2 | 55 | [ElysiumJeiPlugin.java](net/jadenxgamer/elysium_api/impl/compat/ElysiumJeiPlugin.java) |
| `ElysiumRegistries` | class | — | — | 4 | 104 | [ElysiumRegistries.java](net/jadenxgamer/elysium_api/impl/registry/ElysiumRegistries.java) |
| `ElysiumAttributes` | class | — | — | 1 | 15 | [ElysiumAttributes.java](net/jadenxgamer/elysium_api/impl/registry/ElysiumAttributes.java) |
| `ConditionalBoolean` | record | — | — | 2 | 37 | [ConditionalBoolean.java](net/jadenxgamer/elysium_api/impl/util/ConditionalBoolean.java) |
| `ConditionalInt` | record | — | — | 2 | 37 | [ConditionalInt.java](net/jadenxgamer/elysium_api/impl/util/ConditionalInt.java) |
| `FogLightSettingsType` | enum | StringRepresentable | — | 2 | 34 | [FogLightSettingsType.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/FogLightSettingsType.java) |
| `FogSettings` | record | — | — | 2 | 54 | [FogSettings.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/fog_settings/FogSettings.java) |
| `LightmapSettings` | record | — | — | 3 | 81 | [LightmapSettings.java](net/jadenxgamer/elysium_api/impl/client/assetdriven/lightmap_settings/LightmapSettings.java) |
| `ElysiumSurfaceRules` | class | — | — | 2 | 18 | [ElysiumSurfaceRules.java](net/jadenxgamer/elysium_api/api/surface_rules/ElysiumSurfaceRules.java) |
| `BiomeTagConditionSource` | record | LazyYCondition | — | 4 | 46 | [BiomeTagConditionSource.java](net/jadenxgamer/elysium_api/api/surface_rules/condition/BiomeTagConditionSource.java) |
| `ElysiumTags` | class | — | — | 4 | 49 | [ElysiumTags.java](net/jadenxgamer/elysium_api/api/tags/ElysiumTags.java) |
