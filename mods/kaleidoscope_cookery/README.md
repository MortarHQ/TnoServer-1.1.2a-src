# Kaleidoscope Cookery（`kaleidoscope_cookery`）

> jar：`[森罗物语：厨房]kaleidoscopecookery.jar` ｜ 版本：`1.4.1-neoforge+mc1.21.1` ｜ 作者：ysbbbbbb, tartaric_acid, Azumic
> 规模：**366 个类 / 81 个包 / 33739 行** ｜ 事件处理类 35 ｜ Mixin 6 ｜ 注册/管理类 10

**依赖**：`neoforge`、`minecraft`、`kubejs`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.ysbbbbbb.kaleidoscopecookery.item` | 27 | `RecipeItem`、`TransmutationLunchBagItem`、`BowlFoodBlockItem` |
| `com.github.ysbbbbbb.kaleidoscopecookery.init` | 18 | `ModRecipes`、`ModLootModifier`、`ModEntities` |
| `com.github.ysbbbbbb.kaleidoscopecookery.client.render.block` | 14 | `RecipeBlockEntityRender`、`MillstoneBlockEntityRender`、`FoodBiteThreeByThreeBlockEntityRender` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.block` | 13 | `RecipeBlockComponentProvider`、`FoodBiteBlockComponentProvider`、`TableComponentProvider` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe` | 13 | `StockpotRecipeProvider`、`FoodBiteRecipeProvider`、`ShapedRecipeProvider` |
| `com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen` | 12 | `ShawarmaSpitBlock`、`SteamerBlock`、`MillstoneBlock` |
| `com.github.ysbbbbbb.kaleidoscopecookery.event` | 11 | `SpecialRecipeItemEvent`、`ModTradesEvent`、`EntityJoinWorldEvent` |
| `com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe` | 11 | `FlexPotRecipe`、`RiceBowlRecipe`、`ChoppingBoardRecipe` |
| `com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer` | 9 | `MillstoneRecipeSerializer`、`FlexPotRecipeSerializer`、`StockpotRecipeSerializer` |
| `com.github.ysbbbbbb.kaleidoscopecookery.init.registry` | 8 | `CapabilitiesRegistry`、`CompatRegistry`、`CommonRegistry` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen` | 8 | `DataGenerators`、`ModRecipeGenerator`、`LootTableGenerator` |
| `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen` | 8 | `StockpotBlockEntity`、`PotBlockEntity`、`TeapotBlockEntity` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder` | 8 | `StockpotRecipeBuilder`、`FlexStockpotRecipeBuilder`、`FlexPotRecipeBuilder` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category` | 8 | `ReiPotRecipeCategory`、`ReiStockpotRecipeCategory`、`ReiFlexPotRecipeCategory` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category` | 8 | `FlexPotRecipeCategory`、`FlexStockpotRecipeCategory`、`SteamerRecipeCategory` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.emi.category` | 8 | `EmiTeapotRecipe`、`EmiChoppingBoardRecipe`、`EmiFlexStockpotRecipe` |
| `com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity` | 8 | `IPot`、`IMillstone`、`IShawarmaSpit` |
| `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | 7 | `ProjectileDodgeEvent`、`VitalityEvent`、`SatiatedShieldEvent` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes` | 7 | `RecipeItemScenes`、`ShawarmaSpitScenes`、`SteamerScenes` |
| `com.github.ysbbbbbb.kaleidoscopecookery.mixin` | 6 | `FallingBlockEntityMixin`、`LivingEntityMixin`、`PowderSnowBlockMixin` |
| `com.github.ysbbbbbb.kaleidoscopecookery.block.misc` | 6 | `RecipeBlock`、`TrashCanBlock`、`ChiliRistraBlock` |
| `com.github.ysbbbbbb.kaleidoscopecookery.block.decoration` | 6 | `TableBlock`、`StackableFoodBlock`、`PlateBlock` |
| `com.github.ysbbbbbb.kaleidoscopecookery.util` | 6 | `ItemUtils`、`BlockDrop`、`FluidUtils` |
| `com.github.ysbbbbbb.kaleidoscopecookery.client.model` | 6 | `TeapotModel`、`ScarecrowModel`、`MillstoneModel` |
| `com.github.ysbbbbbb.kaleidoscopecookery.effect` | 6 | `InstantSmeltingEffect`、`FlatulenceEffect`、`VigorEffect` |
| `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration` | 5 | `RecipeBlockEntity`、`TableBlockEntity`、`FruitBasketBlockEntity` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs.recipe` | 5 | `MillstoneRecipeSchema`、`PotRecipeSchema`、`StockpotRecipeSchema` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen.tag` | 5 | `TagItem`、`TagBlock`、`TagEntityType` |
| `com.github.ysbbbbbb.kaleidoscopecookery.client.init` | 4 | `ClientSetupEvent`、`ParticleFactoryRegistry`、`InitClientTooltip` |
| `com.github.ysbbbbbb.kaleidoscopecookery.api.event` | 4 | `RecipeItemEvent`、`StockpotMatchRecipeEvent`、`MillstoneMatchRecipeEvent` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable` | 4 | `BlockLootTables`、`EntityLootTables`、`ChestLootTables` |
| `com.github.ysbbbbbb.kaleidoscopecookery.block.crop` | 4 | `RiceCropBlock`、`BaseCropBlock`、`ChiliCropBlock` |
| `com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase` | 4 | `SoupBaseManager`、`SimpleSoupBase`、`FluidSoupBase` |
| `com.github.ysbbbbbb.kaleidoscopecookery.block.food` | 4 | `FoodBiteBlock`、`FoodBiteThreeByThreeBlock`、`FoodBiteOneByTwoBlock` |
| `com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.init` | 4 | `KitchenBlockPonderScreen`、`KitchenBlockPonderTag`、`PonderCompat` |
| `com.github.ysbbbbbb.kaleidoscopecookery.client.event` | 3 | `CameraEvent`、`ModModelEvent`、`PlayerRenderEvent` |
| `com.github.ysbbbbbb.kaleidoscopecookery.entity` | 3 | `ScarecrowEntity`、`SitEntity`、`ThrowableBaoziEntity` |
| `com.github.ysbbbbbb.kaleidoscopecookery.loot` | 3 | `RecipeRandomlyFunction`、`AdvanceBlockMatchTool`、`AdvanceEntityMatchTool` |
| `com.github.ysbbbbbb.kaleidoscopecookery.datagen.model` | 3 | `BlockStateGenerator`、`BlockModelGenerator`、`ItemModelGenerator` |
| `com.github.ysbbbbbb.kaleidoscopecookery.client.resources` | 3 | `ItemRenderReplacer`、`ItemRenderReplacerReloadListener`、`LegacyPackRepositorySource` |
| … | | 其余 41 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SpecialRecipeItemEvent` | class | — | EventBusSubscriber | 4 | 81 | [SpecialRecipeItemEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/SpecialRecipeItemEvent.java) |
| `CapabilitiesRegistry` | class | — | EventBusSubscriber | 1 | 17 | [CapabilitiesRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CapabilitiesRegistry.java) |
| `CompatRegistry` | class | — | EventBusSubscriber | 2 | 26 | [CompatRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CompatRegistry.java) |
| `ModTradesEvent` | class | ItemListing | EventBusSubscriber | 10 | 328 | [ModTradesEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ModTradesEvent.java) |
| `ClientSetupEvent` | class | — | EventBusSubscriber | 9 | 145 | [ClientSetupEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ClientSetupEvent.java) |
| `ParticleFactoryRegistry` | class | — | EventBusSubscriber | 1 | 21 | [ParticleFactoryRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ParticleFactoryRegistry.java) |
| `CommonRegistry` | class | — | EventBusSubscriber | 4 | 101 | [CommonRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CommonRegistry.java) |
| `EntityJoinWorldEvent` | class | — | EventBusSubscriber | 1 | 26 | [EntityJoinWorldEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/EntityJoinWorldEvent.java) |
| `ModRecipes` | class | — | EventBusSubscriber | 1 | 83 | [ModRecipes.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModRecipes.java) |
| `DatapackReloadListenerEvent` | class | — | EventBusSubscriber | 1 | 15 | [DatapackReloadListenerEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/DatapackReloadListenerEvent.java) |
| `SickleHarvestNetherWartEvent` | class | — | EventBusSubscriber | 1 | 39 | [SickleHarvestNetherWartEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/SickleHarvestNetherWartEvent.java) |
| `ArmorEffectEvent` | class | — | EventBusSubscriber | 1 | 32 | [ArmorEffectEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ArmorEffectEvent.java) |
| `RightClickEvent` | class | — | EventBusSubscriber | 2 | 83 | [RightClickEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/RightClickEvent.java) |
| `ChangeTargetEvent` | class | — | EventBusSubscriber | 1 | 18 | [ChangeTargetEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ChangeTargetEvent.java) |
| `HoeUseEvent` | class | — | EventBusSubscriber | 1 | 53 | [HoeUseEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/HoeUseEvent.java) |
| `ScarecrowFarmlandTrampleEvent` | class | — | EventBusSubscriber | 1 | 27 | [ScarecrowFarmlandTrampleEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ScarecrowFarmlandTrampleEvent.java) |
| `LeftClickEvent` | class | — | EventBusSubscriber | 2 | 42 | [LeftClickEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/LeftClickEvent.java) |
| `AddVillageStructuresEvent` | class | — | EventBusSubscriber | 2 | 67 | [AddVillageStructuresEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/AddVillageStructuresEvent.java) |
| `ProjectileDodgeEvent` | class | — | EventBusSubscriber | 2 | 77 | [ProjectileDodgeEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/ProjectileDodgeEvent.java) |
| `VitalityEvent` | class | — | EventBusSubscriber | 1 | 55 | [VitalityEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/VitalityEvent.java) |
| `SatiatedShieldEvent` | class | — | EventBusSubscriber | 3 | 71 | [SatiatedShieldEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/SatiatedShieldEvent.java) |
| `HinderEvent` | class | — | EventBusSubscriber | 1 | 25 | [HinderEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/HinderEvent.java) |
| `FlatulenceEvent` | class | — | EventBusSubscriber | 2 | 56 | [FlatulenceEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/FlatulenceEvent.java) |
| `PreservationEvent` | class | — | EventBusSubscriber | 1 | 37 | [PreservationEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/PreservationEvent.java) |
| `FlatulenceServerEvent` | class | — | EventBusSubscriber | 2 | 39 | [FlatulenceServerEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/FlatulenceServerEvent.java) |
| `CameraEvent` | class | — | EventBusSubscriber | 1 | 27 | [CameraEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/CameraEvent.java) |
| `ModModelEvent` | class | — | EventBusSubscriber | 2 | 48 | [ModModelEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/ModModelEvent.java) |
| `PlayerRenderEvent` | class | — | EventBusSubscriber | 1 | 20 | [PlayerRenderEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/PlayerRenderEvent.java) |
| `ModLootModifier` | class | — | EventBusSubscriber | 1 | 31 | [ModLootModifier.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModLootModifier.java) |
| `InitClientTooltip` | class | — | EventBusSubscriber | 1 | 21 | [InitClientTooltip.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/InitClientTooltip.java) |
| `DataGenerators` | class | — | EventBusSubscriber | 1 | 48 | [DataGenerators.java](com/github/ysbbbbbb/kaleidoscopecookery/datagen/DataGenerators.java) |
| `ModEntitiesRender` | class | — | EventBusSubscriber | 2 | 42 | [ModEntitiesRender.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ModEntitiesRender.java) |
| `ModEntities` | class | — | EventBusSubscriber | 1 | 27 | [ModEntities.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModEntities.java) |
| `StockpotBlockEntity` | class | BaseBlockEntity | — | 35 | 650 | [StockpotBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/kitchen/StockpotBlockEntity.java) |
| `PotBlockEntity` | class | BaseBlockEntity | — | 32 | 617 | [PotBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/kitchen/PotBlockEntity.java) |
| `RecipeItem` | class | BlockItem | — | 22 | 411 | [RecipeItem.java](com/github/ysbbbbbb/kaleidoscopecookery/item/RecipeItem.java) |
| `FoodBiteRegistry` | class | — | — | 22 | 326 | [FoodBiteRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/FoodBiteRegistry.java) |
| `KaleidoscopeCookery` | class | — | Mod | 0 | 59 | [KaleidoscopeCookery.java](com/github/ysbbbbbb/kaleidoscopecookery/KaleidoscopeCookery.java) |
| `ScarecrowEntity` | class | LivingEntity | — | 44 | 511 | [ScarecrowEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/entity/ScarecrowEntity.java) |
| `FallingBlockEntityMixin` | class | — | Mixin | 2 | 91 | [FallingBlockEntityMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/FallingBlockEntityMixin.java) |
| `TeapotBlockEntity` | class | BaseBlockEntity | — | 20 | 411 | [TeapotBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/kitchen/TeapotBlockEntity.java) |
| `MillstoneBlockEntity` | class | BaseBlockEntity | — | 21 | 408 | [MillstoneBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/kitchen/MillstoneBlockEntity.java) |
| `RecipeItemEvent` | class | Event | — | 5 | 56 | [RecipeItemEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/api/event/RecipeItemEvent.java) |
| `SteamerBlockEntity` | class | BaseBlockEntity | — | 18 | 418 | [SteamerBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/kitchen/SteamerBlockEntity.java) |
| `TransmutationLunchBagItem` | class | Item | — | 26 | 467 | [TransmutationLunchBagItem.java](com/github/ysbbbbbb/kaleidoscopecookery/item/TransmutationLunchBagItem.java) |
| `BlockLootTables` | class | BlockLootSubProvider | — | 15 | 308 | [BlockLootTables.java](com/github/ysbbbbbb/kaleidoscopecookery/datagen/lootable/BlockLootTables.java) |
| `PlateRegistry` | class | — | — | 17 | 157 | [PlateRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/PlateRegistry.java) |
| `NetworkHandler` | class | — | — | 1 | 15 | [NetworkHandler.java](com/github/ysbbbbbb/kaleidoscopecookery/network/NetworkHandler.java) |
| `RecipeBlock` | class | FaceAttachedHorizontalDirectionalBlo | — | 11 | 180 | [RecipeBlock.java](com/github/ysbbbbbb/kaleidoscopecookery/block/misc/RecipeBlock.java) |
| `LivingEntityMixin` | class | Entity | Mixin | 1 | 33 | [LivingEntityMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/LivingEntityMixin.java) |
| `PowderSnowBlockMixin` | class | — | Mixin | 1 | 21 | [PowderSnowBlockMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/PowderSnowBlockMixin.java) |
| `BlockMixin` | class | — | Mixin | 1 | 30 | [BlockMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/BlockMixin.java) |
| `MobBucketItemMixin` | class | — | Mixin | 1 | 40 | [MobBucketItemMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/MobBucketItemMixin.java) |
| `RecipeBlockEntity` | class | BaseBlockEntity | — | 3 | 35 | [RecipeBlockEntity.java](com/github/ysbbbbbb/kaleidoscopecookery/blockentity/decoration/RecipeBlockEntity.java) |
| `StockpotMatchRecipeEvent` | class | Event | — | 6 | 65 | [StockpotMatchRecipeEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/api/event/StockpotMatchRecipeEvent.java) |
| `MillstoneMatchRecipeEvent` | class | Event | — | 6 | 64 | [MillstoneMatchRecipeEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/api/event/MillstoneMatchRecipeEvent.java) |
| `StockpotRecipeBuilder` | class | RecipeBuilder | — | 21 | 148 | [StockpotRecipeBuilder.java](com/github/ysbbbbbb/kaleidoscopecookery/datagen/builder/StockpotRecipeBuilder.java) |
| `FlexStockpotRecipeBuilder` | class | RecipeBuilder | — | 20 | 139 | [FlexStockpotRecipeBuilder.java](com/github/ysbbbbbb/kaleidoscopecookery/datagen/builder/FlexStockpotRecipeBuilder.java) |
| `ClientRecipeItemTooltip` | class | ClientTooltipComponent | — | 3 | 67 | [ClientRecipeItemTooltip.java](com/github/ysbbbbbb/kaleidoscopecookery/client/tooltip/ClientRecipeItemTooltip.java) |
| `RecipeBlockEntityRender` | class | BlockEntityRenderer<RecipeBlockEntit | — | 1 | 60 | [RecipeBlockEntityRender.java](com/github/ysbbbbbb/kaleidoscopecookery/client/render/block/RecipeBlockEntityRender.java) |

## 事件处理（`@SubscribeEvent`，35 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientSetupEvent` | 6 | `com.github.ysbbbbbb.kaleidoscopecookery.client.init` | [ClientSetupEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ClientSetupEvent.java) |
| `SpecialRecipeItemEvent` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [SpecialRecipeItemEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/SpecialRecipeItemEvent.java) |
| `CommonRegistry` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.init.registry` | [CommonRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CommonRegistry.java) |
| `RightClickEvent` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [RightClickEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/RightClickEvent.java) |
| `LeftClickEvent` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [LeftClickEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/LeftClickEvent.java) |
| `FlatulenceServerEvent` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [FlatulenceServerEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/FlatulenceServerEvent.java) |
| `ModEntitiesRender` | 2 | `com.github.ysbbbbbb.kaleidoscopecookery.client.init` | [ModEntitiesRender.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ModEntitiesRender.java) |
| `CapabilitiesRegistry` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init.registry` | [CapabilitiesRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CapabilitiesRegistry.java) |
| `CompatRegistry` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init.registry` | [CompatRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/CompatRegistry.java) |
| `ModTradesEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [ModTradesEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ModTradesEvent.java) |
| `ParticleFactoryRegistry` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.client.init` | [ParticleFactoryRegistry.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/ParticleFactoryRegistry.java) |
| `EntityJoinWorldEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [EntityJoinWorldEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/EntityJoinWorldEvent.java) |
| `ModRecipes` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init` | [ModRecipes.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModRecipes.java) |
| `DatapackReloadListenerEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init.registry` | [DatapackReloadListenerEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/init/registry/DatapackReloadListenerEvent.java) |
| `SickleHarvestNetherWartEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [SickleHarvestNetherWartEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/SickleHarvestNetherWartEvent.java) |
| `ArmorEffectEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [ArmorEffectEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ArmorEffectEvent.java) |
| `ChangeTargetEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [ChangeTargetEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ChangeTargetEvent.java) |
| `HoeUseEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [HoeUseEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/HoeUseEvent.java) |
| `ScarecrowFarmlandTrampleEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [ScarecrowFarmlandTrampleEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/ScarecrowFarmlandTrampleEvent.java) |
| `AddVillageStructuresEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event` | [AddVillageStructuresEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/AddVillageStructuresEvent.java) |
| `ProjectileDodgeEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [ProjectileDodgeEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/ProjectileDodgeEvent.java) |
| `VitalityEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [VitalityEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/VitalityEvent.java) |
| `SatiatedShieldEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [SatiatedShieldEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/SatiatedShieldEvent.java) |
| `HinderEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [HinderEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/HinderEvent.java) |
| `FlatulenceEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [FlatulenceEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/FlatulenceEvent.java) |
| `PreservationEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.event.effect` | [PreservationEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/event/effect/PreservationEvent.java) |
| `CameraEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.client.event` | [CameraEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/CameraEvent.java) |
| `ModModelEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.client.event` | [ModModelEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/ModModelEvent.java) |
| `PlayerRenderEvent` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.client.event` | [PlayerRenderEvent.java](com/github/ysbbbbbb/kaleidoscopecookery/client/event/PlayerRenderEvent.java) |
| `ModLootModifier` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init` | [ModLootModifier.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModLootModifier.java) |
| `InitClientTooltip` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.client.init` | [InitClientTooltip.java](com/github/ysbbbbbb/kaleidoscopecookery/client/init/InitClientTooltip.java) |
| `DataGenerators` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.datagen` | [DataGenerators.java](com/github/ysbbbbbb/kaleidoscopecookery/datagen/DataGenerators.java) |
| `ModEntities` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.init` | [ModEntities.java](com/github/ysbbbbbb/kaleidoscopecookery/init/ModEntities.java) |
| `MillstoneCompat` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.compat.create` | [MillstoneCompat.java](com/github/ysbbbbbb/kaleidoscopecookery/compat/create/MillstoneCompat.java) |
| `CookingPotCompat` | 1 | `com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight` | [CookingPotCompat.java](com/github/ysbbbbbb/kaleidoscopecookery/compat/farmersdelight/CookingPotCompat.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `FallingBlockEntityMixin` | `FallingBlockEntity` | [FallingBlockEntityMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/FallingBlockEntityMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/LivingEntityMixin.java) |
| `PowderSnowBlockMixin` | `PowderSnowBlock` | [PowderSnowBlockMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/PowderSnowBlockMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/BlockMixin.java) |
| `MobBucketItemMixin` | `MobBucketItem` | [MobBucketItemMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/MobBucketItemMixin.java) |
| `VillagerMixin` | `Villager` | [VillagerMixin.java](com/github/ysbbbbbb/kaleidoscopecookery/mixin/VillagerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfig` | — | [ClientConfig.java](com/github/ysbbbbbb/kaleidoscopecookery/config/ClientConfig.java) |
| `GeneralConfig` | — | [GeneralConfig.java](com/github/ysbbbbbb/kaleidoscopecookery/config/GeneralConfig.java) |

## 按包浏览

- `com.github.ysbbbbbb.kaleidoscopecookery`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.advancements.critereon`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.api.client.render`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.api.event`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.api.item`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.api.recipe.soupbase`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.crop`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.decoration`（6 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.dispenser`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.drink`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.food`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen`（12 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.block.misc`（6 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.blockentity`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.decoration`（5 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.food`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.blockentity.misc`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.animation`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.event`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.gui.overlay`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.init`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.model`（6 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.particle`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.render.block`（14 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.render.entity.layer`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.render.soupbase`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.resources`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.client.tooltip`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.create`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.emi`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.emi.category`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.farmersdelight`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.harvest`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.jade`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.jade.block`（13 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.jei`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.jei.category`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs.recipe`（5 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.kubejs.util`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.init`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.scenes`（7 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.rei`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.rei.category`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.compat.tetra`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.config`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting.container`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting.output`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe`（11 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer`（9 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.crafting.soupbase`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.advancement`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.lootable`（4 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.model`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.recipe`（13 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datagen.tag`（5 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datamap`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.datamap.resources`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.effect`（6 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.entity`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.entity.ai`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.event`（11 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.event.effect`（7 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.init`（18 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.init.registry`（8 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.init.tag`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.inventory.itemhandler`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.inventory.tooltip`（2 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.item`（27 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.item.quality`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.loot`（3 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.mixin`（6 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.network`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.network.message`（1 类）
- `com.github.ysbbbbbb.kaleidoscopecookery.util`（6 类）
