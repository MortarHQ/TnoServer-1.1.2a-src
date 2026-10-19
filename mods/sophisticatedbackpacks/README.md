# Sophisticated Backpacks（`sophisticatedbackpacks`）

> jar：`[精妙背包]sophisticatedbackpacks.jar` ｜ 版本：`3.25.36` ｜ 作者：P3pp3rF1y, Ridanisaurus
> 规模：**176 个类 / 37 个包 / 18615 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 19

**依赖**：`sophisticatedcore`、`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.p3pp3rf1y.sophisticatedbackpacks.backpack` | 14 | `BackpackBlockEntity`、`BackpackBlock`、`BackpackItem` |
| `net.p3pp3rf1y.sophisticatedbackpacks.network` | 12 | `BlockPickPayload`、`SyncClientInfoPayload`、`EntityToolSwapPayload` |
| `net.p3pp3rf1y.sophisticatedbackpacks.command` | 11 | `DynamicCommand`、`TemplateCommand`、`SBPCommand` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception` | 10 | `InceptionFluidHandler`、`InceptionInventoryHandler`、`SubBackpacksHandler` |
| `net.p3pp3rf1y.sophisticatedbackpacks.registry.tool` | 9 | `ToolRegistry`、`SwordRegistry`、`ItemMatcherFactory` |
| `net.p3pp3rf1y.sophisticatedbackpacks.data` | 9 | `SBPRecipeProvider`、`SBPBlockLootSubProvider`、`SBInjectLootSubProvider` |
| `net.p3pp3rf1y.sophisticatedbackpacks.api` | 9 | `IItemHandlerInteractionUpgrade`、`IFluidHandlerWrapperUpgrade`、`IEntityToolSwapUpgrade` |
| `net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper` | 8 | `BackpackWrapper`、`BackpackFluidHandler`、`BackpackSettingsHandler` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit` | 8 | `DepositUpgradeItem`、`DepositFilterLogic`、`DepositFilterLogicContainer` |
| `net.p3pp3rf1y.sophisticatedbackpacks.client.render` | 7 | `BackpackDynamicModel`、`BackpackBlockEntityRenderer`、`BackpackItemStackRenderer` |
| `net.p3pp3rf1y.sophisticatedbackpacks.init` | 6 | `ModItems`、`ModItemsClient`、`ModBlocks` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.toolswapper` | 5 | `ToolSwapperUpgradeWrapper`、`ToolSwapperUpgradeItem`、`ToolSwapperUpgradeContainer` |
| `net.p3pp3rf1y.sophisticatedbackpacks.crafting` | 5 | `BackpackUpgradeRecipe`、`SmithingBackpackUpgradeRecipe`、`BasicBackpackRecipe` |
| `net.p3pp3rf1y.sophisticatedbackpacks.client.gui` | 5 | `BackpackScreen`、`IBackpackScreen`、`BackpackSettingsScreen` |
| `net.p3pp3rf1y.sophisticatedbackpacks.settings` | 5 | `BackToBackpackTab`、`BackpackMainSettingsCategory`、`BackpackSettingsTabControl` |
| `net.p3pp3rf1y.sophisticatedbackpacks.common.gui` | 4 | `BackpackContext`、`BackpackSettingsContainerMenu`、`BackpackContainer` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing` | 4 | `SmithingUpgradeContainer`、`SmithingUpgradeTab`、`SmithingUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill` | 4 | `RefillUpgradeWrapper`、`RefillUpgradeTab`、`RefillUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil` | 4 | `AnvilUpgradeContainer`、`AnvilUpgradeItem`、`AnvilUpgradeWrapper` |
| `net.p3pp3rf1y.sophisticatedbackpacks.registry` | 3 | `RegistryLoader`、`IRegistryDataLoader`、`JsonHelper` |
| `net.p3pp3rf1y.sophisticatedbackpacks.util` | 3 | `PlayerInventoryHandler`、`InventoryInteractionHelper`、`PlayerInventoryProvider` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.curios` | 3 | `CuriosCompatClient`、`BackpackCurioRenderer`、`CuriosCompat` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.accessories` | 3 | `AccessoriesCompatClient`、`AccessoriesCompat`、`BackpackAccessoryRenderer` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock` | 3 | `RestockUpgradeItem`、`RestockUpgradeTab`、`RestockUpgradeWrapper` |
| `net.p3pp3rf1y.sophisticatedbackpacks.common` | 2 | `EntityBackpackAdditionHandler`、`CommonEventHandler` |
| `net.p3pp3rf1y.sophisticatedbackpacks` | 2 | `Config`、`SophisticatedBackpacks` |
| `net.p3pp3rf1y.sophisticatedbackpacks.client` | 2 | `ClientEventHandler`、`KeybindHandler` |
| `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting` | 2 | `EverlastingBackpackItemEntity`、`EverlastingUpgradeItem` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.sawmill` | 2 | `SawmillCompatClient`、`SawmillCompat` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.chipped` | 2 | `ChippedCompatClient`、`ChippedCompat` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.rei` | 2 | `BackpackReiClientPlugin`、`BackpackReiCommonPlugin` |
| `net.p3pp3rf1y.sophisticatedbackpacks.client.init` | 2 | `ModBlockColors`、`ModItemColors` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.subtypes` | 2 | `SubtypeInterpreters`、`BackpackSubtypeInterpreter` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common` | 1 | `DyeRecipesMaker` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat` | 1 | `CompatModIds` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.emi` | 1 | `BackpackEmiPlugin` |
| `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.jei` | 1 | `BackpackJeiPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EntityBackpackAdditionHandler` | class | — | — | 22 | 468 | [EntityBackpackAdditionHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/common/EntityBackpackAdditionHandler.java) |
| `CommonEventHandler` | class | — | — | 15 | 287 | [CommonEventHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/common/CommonEventHandler.java) |
| `ToolRegistry` | class | ToolRegistry.ToolsLoaderBase | — | 28 | 387 | [ToolRegistry.java](net/p3pp3rf1y/sophisticatedbackpacks/registry/tool/ToolRegistry.java) |
| `Config` | class | IUpgradeCountLimitConfig | — | 22 | 638 | [Config.java](net/p3pp3rf1y/sophisticatedbackpacks/Config.java) |
| `SophisticatedBackpacks` | class | — | Mod | 6 | 83 | [SophisticatedBackpacks.java](net/p3pp3rf1y/sophisticatedbackpacks/SophisticatedBackpacks.java) |
| `BackpackBlockEntity` | class | BlockEntity | — | 36 | 320 | [BackpackBlockEntity.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackBlockEntity.java) |
| `ClientEventHandler` | class | — | — | 9 | 155 | [ClientEventHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/client/ClientEventHandler.java) |
| `BackpackDynamicModel` | class | ItemOverrides | — | 59 | 1197 | [BackpackDynamicModel.java](net/p3pp3rf1y/sophisticatedbackpacks/client/render/BackpackDynamicModel.java) |
| `BackpackBlock` | class | Block | — | 30 | 354 | [BackpackBlock.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackBlock.java) |
| `BackpackWrapper` | class | IBackpackWrapper | — | 77 | 653 | [BackpackWrapper.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/BackpackWrapper.java) |
| `BackpackContext` | class | BackpackContext.Item | — | 84 | 610 | [BackpackContext.java](net/p3pp3rf1y/sophisticatedbackpacks/common/gui/BackpackContext.java) |
| `ToolSwapperUpgradeWrapper` | class | UpgradeWrapperBase | — | 32 | 455 | [ToolSwapperUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/toolswapper/ToolSwapperUpgradeWrapper.java) |
| `InceptionFluidHandler` | class | IStorageFluidHandler | — | 17 | 194 | [InceptionFluidHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/inception/InceptionFluidHandler.java) |
| `InceptionInventoryHandler` | class | ITrackedContentsItemHandler | — | 16 | 140 | [InceptionInventoryHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/inception/InceptionInventoryHandler.java) |
| `BackpackItem` | class | ItemBase | — | 29 | 364 | [BackpackItem.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackItem.java) |
| `RegistryLoader` | class | SimpleJsonResourceReloadListener | — | 10 | 158 | [RegistryLoader.java](net/p3pp3rf1y/sophisticatedbackpacks/registry/RegistryLoader.java) |
| `KeybindHandler` | class | IKeyConflictContext | — | 11 | 232 | [KeybindHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/client/KeybindHandler.java) |
| `SmithingUpgradeContainer` | class | UpgradeContainerBase | — | 31 | 203 | [SmithingUpgradeContainer.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/smithing/SmithingUpgradeContainer.java) |
| `DatapackBackpackTemplateManager` | class | SimplePreparableReloadListener | — | 5 | 82 | [DatapackBackpackTemplateManager.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/DatapackBackpackTemplateManager.java) |
| `BackpackFluidHandler` | class | IStorageFluidHandler | — | 14 | 136 | [BackpackFluidHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/BackpackFluidHandler.java) |
| `SBPRecipeProvider` | class | RecipeProvider | — | 4 | 694 | [SBPRecipeProvider.java](net/p3pp3rf1y/sophisticatedbackpacks/data/SBPRecipeProvider.java) |
| `IItemHandlerInteractionUpgrade` | interface | — | — | 0 | 9 | [IItemHandlerInteractionUpgrade.java](net/p3pp3rf1y/sophisticatedbackpacks/api/IItemHandlerInteractionUpgrade.java) |
| `IRegistryDataLoader` | interface | — | — | 0 | 13 | [IRegistryDataLoader.java](net/p3pp3rf1y/sophisticatedbackpacks/registry/IRegistryDataLoader.java) |
| `SwordRegistry` | class | IRegistryDataLoader | — | 6 | 109 | [SwordRegistry.java](net/p3pp3rf1y/sophisticatedbackpacks/registry/tool/SwordRegistry.java) |
| `RefillUpgradeWrapper` | class | UpgradeWrapperBase | — | 20 | 442 | [RefillUpgradeWrapper.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/refill/RefillUpgradeWrapper.java) |
| `SubBackpacksHandler` | class | — | — | 9 | 83 | [SubBackpacksHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/inception/SubBackpacksHandler.java) |
| `BackpackSettingsHandler` | class | SettingsHandler | — | 7 | 65 | [BackpackSettingsHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/BackpackSettingsHandler.java) |
| `PlayerInventoryHandler` | class | — | — | 6 | 66 | [PlayerInventoryHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/util/PlayerInventoryHandler.java) |
| `BackpackBlockEntityRenderer` | class | BlockEntityRenderer<BackpackBlockEnt | — | 2 | 66 | [BackpackBlockEntityRenderer.java](net/p3pp3rf1y/sophisticatedbackpacks/client/render/BackpackBlockEntityRenderer.java) |
| `ModItems` | class | OptionalDispenseItemBehavior | — | 8 | 674 | [ModItems.java](net/p3pp3rf1y/sophisticatedbackpacks/init/ModItems.java) |
| `EverlastingBackpackItemEntity` | class | ItemEntity | — | 6 | 63 | [EverlastingBackpackItemEntity.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/everlasting/EverlastingBackpackItemEntity.java) |
| `BackpackInventoryHandler` | class | InventoryHandler | — | 3 | 29 | [BackpackInventoryHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/BackpackInventoryHandler.java) |
| `InventoryModificationHandler` | class | — | — | 2 | 37 | [InventoryModificationHandler.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/wrapper/InventoryModificationHandler.java) |
| `BackpackUpgradeRecipe` | class | ShapedRecipe | — | 5 | 62 | [BackpackUpgradeRecipe.java](net/p3pp3rf1y/sophisticatedbackpacks/crafting/BackpackUpgradeRecipe.java) |
| `SmithingBackpackUpgradeRecipe` | class | SmithingTransformRecipe | — | 5 | 60 | [SmithingBackpackUpgradeRecipe.java](net/p3pp3rf1y/sophisticatedbackpacks/crafting/SmithingBackpackUpgradeRecipe.java) |
| `SBPBlockLootSubProvider` | class | BlockLootSubProvider | — | 3 | 53 | [SBPBlockLootSubProvider.java](net/p3pp3rf1y/sophisticatedbackpacks/data/SBPBlockLootSubProvider.java) |
| `IFluidHandlerWrapperUpgrade` | interface | — | — | 0 | 11 | [IFluidHandlerWrapperUpgrade.java](net/p3pp3rf1y/sophisticatedbackpacks/api/IFluidHandlerWrapperUpgrade.java) |
| `DynamicCommand` | class | — | — | 7 | 314 | [DynamicCommand.java](net/p3pp3rf1y/sophisticatedbackpacks/command/DynamicCommand.java) |
| `TemplateCommand` | class | — | — | 7 | 296 | [TemplateCommand.java](net/p3pp3rf1y/sophisticatedbackpacks/command/TemplateCommand.java) |
| `AnvilUpgradeContainer` | class | UpgradeContainerBase | — | 20 | 167 | [AnvilUpgradeContainer.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/anvil/AnvilUpgradeContainer.java) |
| `BackpackShapes` | class | BackpackShapes.ModelJsonSource | — | 18 | 282 | [BackpackShapes.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackShapes.java) |
| `BackpackItemClient` | class | — | — | 1 | 18 | [BackpackItemClient.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackItemClient.java) |
| `BasicBackpackRecipe` | class | ShapedRecipe | — | 4 | 45 | [BasicBackpackRecipe.java](net/p3pp3rf1y/sophisticatedbackpacks/crafting/BasicBackpackRecipe.java) |
| `SmithingBackpackUpgradeRecipeBuilder` | class | SmithingTransformRecipeBuilder | — | 1 | 32 | [SmithingBackpackUpgradeRecipeBuilder.java](net/p3pp3rf1y/sophisticatedbackpacks/crafting/SmithingBackpackUpgradeRecipeBuilder.java) |
| `BackpackDyeRecipe` | class | StorageDyeRecipeBase | — | 3 | 36 | [BackpackDyeRecipe.java](net/p3pp3rf1y/sophisticatedbackpacks/crafting/BackpackDyeRecipe.java) |
| `CuriosCompatClient` | class | — | — | 1 | 17 | [CuriosCompatClient.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/curios/CuriosCompatClient.java) |
| `SawmillCompatClient` | class | — | — | 1 | 21 | [SawmillCompatClient.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/sawmill/SawmillCompatClient.java) |
| `DyeRecipesMaker` | class | — | — | 4 | 67 | [DyeRecipesMaker.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/recipeviewers/common/DyeRecipesMaker.java) |
| `AccessoriesCompatClient` | class | — | — | 1 | 17 | [AccessoriesCompatClient.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/accessories/AccessoriesCompatClient.java) |
| `ChippedCompatClient` | class | — | — | 1 | 33 | [ChippedCompatClient.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/chipped/ChippedCompatClient.java) |
| `BackpackItemStackRenderer` | class | BlockEntityWithoutLevelRenderer | — | 1 | 59 | [BackpackItemStackRenderer.java](net/p3pp3rf1y/sophisticatedbackpacks/client/render/BackpackItemStackRenderer.java) |
| `ModItemsClient` | class | — | — | 2 | 190 | [ModItemsClient.java](net/p3pp3rf1y/sophisticatedbackpacks/init/ModItemsClient.java) |
| `SmithingUpgradeTab` | class | UpgradeSettingsTab | — | 14 | 230 | [SmithingUpgradeTab.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/smithing/SmithingUpgradeTab.java) |
| `RefillUpgradeTab` | class | UpgradeSettingsTab | — | 13 | 203 | [RefillUpgradeTab.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/refill/RefillUpgradeTab.java) |
| `RefillUpgradeItem` | class | UpgradeItemBase | — | 5 | 43 | [RefillUpgradeItem.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/refill/RefillUpgradeItem.java) |
| `InceptionUpgradeItem` | class | UpgradeItemBase | — | 5 | 77 | [InceptionUpgradeItem.java](net/p3pp3rf1y/sophisticatedbackpacks/upgrades/inception/InceptionUpgradeItem.java) |
| `BackpackStorage` | class | SavedData | — | 16 | 186 | [BackpackStorage.java](net/p3pp3rf1y/sophisticatedbackpacks/backpack/BackpackStorage.java) |
| `BackpackSettingsContainerMenu` | class | SettingsContainerMenu | — | 5 | 73 | [BackpackSettingsContainerMenu.java](net/p3pp3rf1y/sophisticatedbackpacks/common/gui/BackpackSettingsContainerMenu.java) |
| `BackpackReiClientPlugin` | class | REIClientPlugin | REIPluginClient | 7 | 89 | [BackpackReiClientPlugin.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/recipeviewers/rei/BackpackReiClientPlugin.java) |
| `AccessoriesCompat` | class | ICompat | — | 5 | 78 | [AccessoriesCompat.java](net/p3pp3rf1y/sophisticatedbackpacks/compat/accessories/AccessoriesCompat.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](net/p3pp3rf1y/sophisticatedbackpacks/Config.java) |

## 按包浏览

- `net.p3pp3rf1y.sophisticatedbackpacks`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.api`（9 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.backpack`（14 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper`（8 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.client`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.client.gui`（5 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.client.init`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.client.render`（7 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.command`（11 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.common`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.common.gui`（4 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat`（1 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.accessories`（3 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.chipped`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.curios`（3 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common`（1 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.common.subtypes`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.emi`（1 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.jei`（1 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.recipeviewers.rei`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.compat.sawmill`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.crafting`（5 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.data`（9 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.init`（6 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.network`（12 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.registry`（3 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.registry.tool`（9 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.settings`（5 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil`（4 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit`（8 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.everlasting`（2 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception`（10 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill`（4 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock`（3 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing`（4 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.upgrades.toolswapper`（5 类）
- `net.p3pp3rf1y.sophisticatedbackpacks.util`（3 类）
