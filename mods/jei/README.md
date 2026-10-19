# Just Enough Items（`jei`）

> jar：`[JEI物品管理器]jei.jar` ｜ 版本：`19.27.0.340` ｜ 作者：mezz
> 规模：**667 个类 / 128 个包 / 44907 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 96

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `mezz.jei.common.util` | 23 | `RegistryUtil`、`ServerCommandUtil`、`ImmutableRect2i` |
| `mezz.jei.gui.recipes` | 19 | `RecipesGui`、`RecipeGuiLogic`、`RecipeLayoutWithButtons` |
| `mezz.jei.common.config` | 18 | `ClientConfig`、`ConfigManager`、`JeiClientConfigs` |
| `mezz.jei.gui.input` | 17 | `ClientInputHandler`、`ICharTypedHandler`、`IDragHandler` |
| `mezz.jei.gui.input.handlers` | 16 | `DeleteItemInputHandler`、`TextFieldInputHandler`、`CombinedInputHandler` |
| `mezz.jei.library.load.registration` | 14 | `GuiHandlerRegistration`、`RecipeTransferRegistration`、`RecipeRegistration` |
| `mezz.jei.common.config.file` | 14 | `ConfigValue`、`ConfigSchema`、`ConfigSerializer` |
| `mezz.jei.api.registration` | 13 | `IGuiHandlerRegistration`、`IRecipeCategoryRegistration`、`IRecipeTransferRegistration` |
| `mezz.jei.library.plugins.debug` | 13 | `DebugSimpleRecipeManagerPlugin`、`DebugRecipeCategory`、`DebugFocusRecipeCategory` |
| `mezz.jei.api.runtime` | 13 | `IIngredientManager`、`IEditModeConfig`、`IRecipesGui` |
| `mezz.jei.library.recipes` | 12 | `RecipeManagerInternal`、`RecipeManager`、`RecipeTransferManager` |
| `mezz.jei.neoforge.platform` | 12 | `RecipeHelper`、`ConfigHelper`、`BrewingRecipeMaker` |
| `mezz.jei.common.gui.elements` | 12 | `TextWidget`、`DrawableSprite`、`OffsetDrawable` |
| `mezz.jei.library.ingredients` | 11 | `IngredientManager`、`TypedIngredientSet`、`SimpleIngredientAcceptor` |
| `mezz.jei.gui.ingredients` | 11 | `IngredientFilter`、`ListElementInfo`、`IngredientSorterComparators` |
| `mezz.jei.common.platform` | 11 | `IPlatformConfigHelper`、`IPlatformRecipeHelper`、`IPlatformItemStackHelper` |
| `mezz.jei.gui.overlay` | 10 | `IngredientGridWithNavigation`、`IngredientListOverlay`、`ConfigButtonController` |
| `mezz.jei.core.util` | 10 | `ReflectionUtil`、`SubString`、`TimeUtil` |
| `mezz.jei.api.recipe` | 9 | `IRecipeManager`、`RecipeType`、`IRecipeCatalystLookup` |
| `mezz.jei.gui.config` | 8 | `InternalKeyMappings`、`LookupHistoryJsonConfig`、`BookmarkJsonConfig` |
| `mezz.jei.common.network.packets` | 8 | `PacketRecipeTransfer`、`PacketSetHotbarItemStack`、`PlayToClientPacket` |
| `mezz.jei.api.gui.widgets` | 8 | `ISlottedRecipeWidget`、`IRecipeExtrasBuilder`、`IRecipeWidget` |
| `mezz.jei.api.recipe.vanilla` | 8 | `IJeiFuelingRecipe`、`IJeiShapedRecipeBuilder`、`IVanillaRecipeFactory` |
| `mezz.jei.library.plugins.debug.ingredients` | 8 | `ErrorIngredientHelper`、`DebugIngredientRenderer`、`ErrorIngredientRenderer` |
| `mezz.jei.library.plugins.vanilla.ingredients.subtypes` | 8 | `FireworkRocketSubtypeInterpreter`、`PaintingSubtypeInterpreter`、`InstrumentSubtypeInterpreter` |
| `mezz.jei.library.gui.recipes` | 7 | `RecipeLayout`、`RecipeLayoutInputHandler`、`RecipesGuiDummy` |
| `mezz.jei.library.plugins.vanilla.anvil` | 7 | `AnvilRecipeMaker`、`SmithingRecipeCategory`、`AnvilRecipeCategory` |
| `mezz.jei.gui.bookmarks` | 7 | `RecipeBookmark`、`BookmarkList`、`IngredientBookmark` |
| `mezz.jei.api.gui.ingredient` | 7 | `IRecipeSlotDrawablesView`、`IRecipeSlotDrawable`、`IRecipeSlotsView` |
| `mezz.jei.common.input.keys` | 7 | `JeiKeyConflictContext`、`JeiKeyModifier`、`AbstractJeiKeyMappingBuilder` |
| `mezz.jei.core.search` | 7 | `PrefixedSearchable`、`ISearchStorage`、`ISearchable` |
| `mezz.jei.api.helpers` | 7 | `IStackHelper`、`IColorHelper`、`ICodecHelper` |
| `mezz.jei.library.transfer` | 6 | `BasicRecipeTransferHandler`、`RecipeTransferHandlerHelper`、`PlayerRecipeTransferHandler` |
| `mezz.jei.library.gui.ingredients` | 6 | `RecipeSlot`、`RendererOverrides`、`TagContentTooltipComponent` |
| `mezz.jei.api.recipe.transfer` | 6 | `IRecipeTransferHandlerHelper`、`IRecipeTransferManager`、`IRecipeTransferHandler` |
| `mezz.jei.api.runtime.config` | 6 | `IJeiConfigManager`、`IJeiConfigCategory`、`IJeiConfigValueSerializer` |
| `mezz.jei.library.plugins.vanilla.crafting` | 6 | `JeiShapedRecipe`、`CraftingRecipeCategory`、`VanillaRecipes` |
| `mezz.jei.api.gui.handlers` | 6 | `IScreenHandler`、`IGhostIngredientHandler`、`IGuiContainerHandler` |
| `mezz.jei.gui.overlay.bookmarks` | 6 | `BookmarkDragManager`、`BookmarkOverlay`、`BookmarkDrag` |
| `mezz.jei.library.color` | 6 | `MMCQ`、`ColorHelper`、`ColorUtil` |
| … | | 其余 88 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RecipesGui` | class | Screen | — | 44 | 620 | [RecipesGui.java](mezz/jei/gui/recipes/RecipesGui.java) |
| `RecipeManagerInternal` | class | — | — | 18 | 253 | [RecipeManagerInternal.java](mezz/jei/library/recipes/RecipeManagerInternal.java) |
| `RecipeManager` | class | IRecipeManager | — | 17 | 207 | [RecipeManager.java](mezz/jei/library/recipes/RecipeManager.java) |
| `BasicRecipeTransferHandlerServer` | class | — | — | 15 | 269 | [BasicRecipeTransferHandlerServer.java](mezz/jei/common/transfer/BasicRecipeTransferHandlerServer.java) |
| `ClientConfig` | class | IClientConfig | — | 32 | 267 | [ClientConfig.java](mezz/jei/common/config/ClientConfig.java) |
| `JustEnoughItems` | class | — | Mod | 0 | 31 | [JustEnoughItems.java](mezz/jei/neoforge/JustEnoughItems.java) |
| `GuiHandlerRegistration` | class | IGuiHandlerRegistration | — | 7 | 91 | [GuiHandlerRegistration.java](mezz/jei/library/load/registration/GuiHandlerRegistration.java) |
| `BasicRecipeTransferHandler` | class | IRecipeTransferHandler<C | — | 10 | 258 | [BasicRecipeTransferHandler.java](mezz/jei/library/transfer/BasicRecipeTransferHandler.java) |
| `RecipeLayout` | class | IRecipeLayoutDrawable<R> | — | 31 | 460 | [RecipeLayout.java](mezz/jei/library/gui/recipes/RecipeLayout.java) |
| `RecipeTransferRegistration` | class | IRecipeTransferRegistration | — | 8 | 104 | [RecipeTransferRegistration.java](mezz/jei/library/load/registration/RecipeTransferRegistration.java) |
| `RecipeRegistration` | class | IRecipeRegistration | — | 6 | 67 | [RecipeRegistration.java](mezz/jei/library/load/registration/RecipeRegistration.java) |
| `RecipeCatalystRegistration` | class | IRecipeCatalystRegistration | — | 6 | 91 | [RecipeCatalystRegistration.java](mezz/jei/library/load/registration/RecipeCatalystRegistration.java) |
| `RecipeTransferHandlerHelper` | class | IRecipeTransferHandlerHelper | — | 8 | 97 | [RecipeTransferHandlerHelper.java](mezz/jei/library/transfer/RecipeTransferHandlerHelper.java) |
| `RecipeLayoutInputHandler` | class | IJeiInputHandler | — | 8 | 206 | [RecipeLayoutInputHandler.java](mezz/jei/library/gui/recipes/RecipeLayoutInputHandler.java) |
| `IGuiHandlerRegistration` | interface | — | — | 1 | 40 | [IGuiHandlerRegistration.java](mezz/jei/api/registration/IGuiHandlerRegistration.java) |
| `RecipeCategoryRegistration` | class | IRecipeCategoryRegistration | — | 2 | 60 | [RecipeCategoryRegistration.java](mezz/jei/library/load/registration/RecipeCategoryRegistration.java) |
| `RecipeManagerPluginHelper` | class | IRecipeManagerPluginHelper | — | 1 | 20 | [RecipeManagerPluginHelper.java](mezz/jei/library/load/registration/RecipeManagerPluginHelper.java) |
| `TypedRecipeManagerPluginAdapter` | class | IRecipeManagerPlugin | — | 5 | 94 | [TypedRecipeManagerPluginAdapter.java](mezz/jei/library/load/registration/TypedRecipeManagerPluginAdapter.java) |
| `PlayerRecipeTransferHandler` | class | IRecipeTransferHandler<InventoryMenu | — | 6 | 91 | [PlayerRecipeTransferHandler.java](mezz/jei/library/transfer/PlayerRecipeTransferHandler.java) |
| `RecipeSlotBuilder` | class | IRecipeSlotBuilder | — | 29 | 261 | [RecipeSlotBuilder.java](mezz/jei/library/gui/recipes/layout/builder/RecipeSlotBuilder.java) |
| `RecipeSlot` | class | IRecipeSlotView | — | 26 | 333 | [RecipeSlot.java](mezz/jei/library/gui/ingredients/RecipeSlot.java) |
| `DebugSimpleRecipeManagerPlugin` | class | ISimpleRecipeManagerPlugin<RecipeHol | — | 6 | 63 | [DebugSimpleRecipeManagerPlugin.java](mezz/jei/library/plugins/debug/DebugSimpleRecipeManagerPlugin.java) |
| `IngredientManager` | class | IIngredientManager | — | 22 | 251 | [IngredientManager.java](mezz/jei/library/ingredients/IngredientManager.java) |
| `IRecipeTransferHandlerHelper` | interface | — | — | 0 | 36 | [IRecipeTransferHandlerHelper.java](mezz/jei/api/recipe/transfer/IRecipeTransferHandlerHelper.java) |
| `IRecipeManagerPluginHelper` | interface | — | — | 0 | 9 | [IRecipeManagerPluginHelper.java](mezz/jei/api/recipe/advanced/IRecipeManagerPluginHelper.java) |
| `IRecipeCategoryRegistration` | interface | — | — | 0 | 11 | [IRecipeCategoryRegistration.java](mezz/jei/api/registration/IRecipeCategoryRegistration.java) |
| `IRecipeTransferRegistration` | interface | — | — | 0 | 31 | [IRecipeTransferRegistration.java](mezz/jei/api/registration/IRecipeTransferRegistration.java) |
| `IRecipeCatalystRegistration` | interface | — | — | 0 | 35 | [IRecipeCatalystRegistration.java](mezz/jei/api/registration/IRecipeCatalystRegistration.java) |
| `IRecipeRegistration` | interface | — | — | 0 | 45 | [IRecipeRegistration.java](mezz/jei/api/registration/IRecipeRegistration.java) |
| `ClientInputHandler` | class | — | — | 10 | 100 | [ClientInputHandler.java](mezz/jei/gui/input/ClientInputHandler.java) |
| `GuiEventHandler` | class | — | — | 6 | 123 | [GuiEventHandler.java](mezz/jei/gui/events/GuiEventHandler.java) |
| `RecipeGuiLogic` | class | IRecipeGuiLogic | — | 24 | 327 | [RecipeGuiLogic.java](mezz/jei/gui/recipes/RecipeGuiLogic.java) |
| `EventRegistration` | class | — | — | 3 | 113 | [EventRegistration.java](mezz/jei/neoforge/startup/EventRegistration.java) |
| `IngredientManagerBuilder` | class | IModIngredientRegistration | — | 16 | 241 | [IngredientManagerBuilder.java](mezz/jei/library/load/registration/IngredientManagerBuilder.java) |
| `RecipeBookGuiHandler` | class | IGuiContainerHandler<T> | — | 1 | 37 | [RecipeBookGuiHandler.java](mezz/jei/library/plugins/vanilla/gui/RecipeBookGuiHandler.java) |
| `ItemStackHelper` | class | IIngredientHelper<ItemStack> | — | 26 | 222 | [ItemStackHelper.java](mezz/jei/library/plugins/vanilla/ingredients/ItemStackHelper.java) |
| `RecipeTransferManager` | class | IRecipeTransferManager | — | 3 | 67 | [RecipeTransferManager.java](mezz/jei/library/recipes/RecipeTransferManager.java) |
| `UniversalRecipeTransferHandlerAdapter` | class | IRecipeTransferHandler<C | — | 4 | 43 | [UniversalRecipeTransferHandlerAdapter.java](mezz/jei/library/recipes/UniversalRecipeTransferHandlerAdapter.java) |
| `InternalRecipeManagerPlugin` | class | IRecipeManagerPlugin | — | 3 | 63 | [InternalRecipeManagerPlugin.java](mezz/jei/library/recipes/InternalRecipeManagerPlugin.java) |
| `ConfigManager` | class | IJeiConfigManager | — | 2 | 29 | [ConfigManager.java](mezz/jei/common/config/ConfigManager.java) |
| `IJeiConfigManager` | interface | — | — | 0 | 9 | [IJeiConfigManager.java](mezz/jei/api/runtime/config/IJeiConfigManager.java) |
| `IRecipeManager` | interface | — | — | 0 | 65 | [IRecipeManager.java](mezz/jei/api/recipe/IRecipeManager.java) |
| `IRecipeTransferManager` | interface | — | — | 0 | 10 | [IRecipeTransferManager.java](mezz/jei/api/recipe/transfer/IRecipeTransferManager.java) |
| `IRecipeTransferHandler` | interface | — | — | 0 | 21 | [IRecipeTransferHandler.java](mezz/jei/api/recipe/transfer/IRecipeTransferHandler.java) |
| `IUniversalRecipeTransferHandler` | interface | — | — | 0 | 18 | [IUniversalRecipeTransferHandler.java](mezz/jei/api/recipe/transfer/IUniversalRecipeTransferHandler.java) |
| `IRecipeManagerPlugin` | interface | — | — | 0 | 15 | [IRecipeManagerPlugin.java](mezz/jei/api/recipe/advanced/IRecipeManagerPlugin.java) |
| `ISimpleRecipeManagerPlugin` | interface | — | — | 0 | 17 | [ISimpleRecipeManagerPlugin.java](mezz/jei/api/recipe/advanced/ISimpleRecipeManagerPlugin.java) |
| `GuiEventListenerFocusHandler` | class | IFocusHandler | — | 3 | 31 | [GuiEventListenerFocusHandler.java](mezz/jei/gui/input/focus/GuiEventListenerFocusHandler.java) |
| `JeiEventHandlers` | record | — | — | 0 | 8 | [JeiEventHandlers.java](mezz/jei/gui/startup/JeiEventHandlers.java) |
| `IngredientGridWithNavigation` | class | IRecipeFocusSource | — | 36 | 471 | [IngredientGridWithNavigation.java](mezz/jei/gui/overlay/IngredientGridWithNavigation.java) |
| `GhostIngredientDragManager` | class | IDragHandler | — | 11 | 203 | [GhostIngredientDragManager.java](mezz/jei/gui/ghost/GhostIngredientDragManager.java) |
| `NetworkHandler` | class | — | — | 4 | 76 | [NetworkHandler.java](mezz/jei/neoforge/network/NetworkHandler.java) |
| `RuntimeEventSubscriptions` | class | — | — | 3 | 38 | [RuntimeEventSubscriptions.java](mezz/jei/neoforge/events/RuntimeEventSubscriptions.java) |
| `EventSubscription` | class | — | — | 2 | 30 | [EventSubscription.java](mezz/jei/neoforge/events/EventSubscription.java) |
| `PermanentEventSubscriptions` | class | — | — | 2 | 30 | [PermanentEventSubscriptions.java](mezz/jei/neoforge/events/PermanentEventSubscriptions.java) |
| `RuntimeRegistration` | class | IRuntimeRegistration | — | 14 | 113 | [RuntimeRegistration.java](mezz/jei/library/load/registration/RuntimeRegistration.java) |
| `DebugRecipeCategory` | class | IRecipeCategory<DebugRecipe> | — | 16 | 248 | [DebugRecipeCategory.java](mezz/jei/library/plugins/debug/DebugRecipeCategory.java) |
| `RecipeCategorySortingConfig` | class | MappedSortingConfig | — | 1 | 26 | [RecipeCategorySortingConfig.java](mezz/jei/library/config/RecipeCategorySortingConfig.java) |
| `EditModeConfig` | class | IEditModeConfig | — | 16 | 248 | [EditModeConfig.java](mezz/jei/library/config/EditModeConfig.java) |
| `RecipeTransferUtil` | class | Strategy<ItemStack> | — | 11 | 272 | [RecipeTransferUtil.java](mezz/jei/common/transfer/RecipeTransferUtil.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfig` | — | [ClientConfig.java](mezz/jei/common/config/ClientConfig.java) |
| `ConfigManager` | — | [ConfigManager.java](mezz/jei/common/config/ConfigManager.java) |
| `IJeiConfigManager` | — | [IJeiConfigManager.java](mezz/jei/api/runtime/config/IJeiConfigManager.java) |
| `RecipeCategorySortingConfig` | — | [RecipeCategorySortingConfig.java](mezz/jei/library/config/RecipeCategorySortingConfig.java) |
| `EditModeConfig` | — | [EditModeConfig.java](mezz/jei/library/config/EditModeConfig.java) |
| `JeiClientConfigs` | — | [JeiClientConfigs.java](mezz/jei/common/config/JeiClientConfigs.java) |
| `IngredientFilterConfig` | — | [IngredientFilterConfig.java](mezz/jei/common/config/IngredientFilterConfig.java) |
| `IJeiClientConfigs` | — | [IJeiClientConfigs.java](mezz/jei/common/config/IJeiClientConfigs.java) |
| `IClientConfig` | — | [IClientConfig.java](mezz/jei/common/config/IClientConfig.java) |
| `ConfigValue` | — | [ConfigValue.java](mezz/jei/common/config/file/ConfigValue.java) |
| `GuiConfigData` | — | [GuiConfigData.java](mezz/jei/gui/startup/GuiConfigData.java) |
| `LookupHistoryJsonConfig` | — | [LookupHistoryJsonConfig.java](mezz/jei/gui/config/LookupHistoryJsonConfig.java) |
| `BookmarkJsonConfig` | — | [BookmarkJsonConfig.java](mezz/jei/gui/config/BookmarkJsonConfig.java) |
| `LegacyBookmarkConfig` | — | [LegacyBookmarkConfig.java](mezz/jei/gui/config/LegacyBookmarkConfig.java) |
| `ConfigButtonController` | — | [ConfigButtonController.java](mezz/jei/gui/overlay/ConfigButtonController.java) |
| `ConfigHelper` | — | [ConfigHelper.java](mezz/jei/neoforge/platform/ConfigHelper.java) |
| `ModIdFormatConfig` | — | [ModIdFormatConfig.java](mezz/jei/library/config/ModIdFormatConfig.java) |
| `IngredientGridConfig` | — | [IngredientGridConfig.java](mezz/jei/common/config/IngredientGridConfig.java) |
| `DebugConfig` | — | [DebugConfig.java](mezz/jei/common/config/DebugConfig.java) |
| `SortingConfig` | — | [SortingConfig.java](mezz/jei/common/config/sorting/SortingConfig.java) |
| `ConfigSchema` | — | [ConfigSchema.java](mezz/jei/common/config/file/ConfigSchema.java) |
| `ConfigSerializer` | — | [ConfigSerializer.java](mezz/jei/common/config/file/ConfigSerializer.java) |
| `ConfigCategoryBuilder` | — | [ConfigCategoryBuilder.java](mezz/jei/common/config/file/ConfigCategoryBuilder.java) |
| `IPlatformConfigHelper` | — | [IPlatformConfigHelper.java](mezz/jei/common/platform/IPlatformConfigHelper.java) |
| `ServerConfigPathUtil` | — | [ServerConfigPathUtil.java](mezz/jei/common/util/ServerConfigPathUtil.java) |
| `IBookmarkConfig` | — | [IBookmarkConfig.java](mezz/jei/gui/config/IBookmarkConfig.java) |
| `ILookupHistoryConfig` | — | [ILookupHistoryConfig.java](mezz/jei/gui/config/ILookupHistoryConfig.java) |
| `IngredientTypeSortingConfig` | — | [IngredientTypeSortingConfig.java](mezz/jei/gui/config/IngredientTypeSortingConfig.java) |
| `ModNameSortingConfig` | — | [ModNameSortingConfig.java](mezz/jei/gui/config/ModNameSortingConfig.java) |
| `ServerConfig` | — | [ServerConfig.java](mezz/jei/neoforge/config/ServerConfig.java) |

## 按包浏览

- `mezz.jei.api`（2 类）
- `mezz.jei.api.constants`（4 类）
- `mezz.jei.api.gui`（2 类）
- `mezz.jei.api.gui.builder`（6 类）
- `mezz.jei.api.gui.buttons`（2 类）
- `mezz.jei.api.gui.drawable`（5 类）
- `mezz.jei.api.gui.handlers`（6 类）
- `mezz.jei.api.gui.ingredient`（7 类）
- `mezz.jei.api.gui.inputs`（4 类）
- `mezz.jei.api.gui.placement`（3 类）
- `mezz.jei.api.gui.widgets`（8 类）
- `mezz.jei.api.helpers`（7 类）
- `mezz.jei.api.ingredients`（6 类）
- `mezz.jei.api.ingredients.rendering`（1 类）
- `mezz.jei.api.ingredients.subtypes`（4 类）
- `mezz.jei.api.neoforge`（1 类）
- `mezz.jei.api.recipe`（9 类）
- `mezz.jei.api.recipe.advanced`（4 类）
- `mezz.jei.api.recipe.category`（2 类）
- `mezz.jei.api.recipe.category.extensions`（3 类）
- `mezz.jei.api.recipe.category.extensions.vanilla.crafting`（2 类）
- `mezz.jei.api.recipe.category.extensions.vanilla.smithing`（2 类）
- `mezz.jei.api.recipe.transfer`（6 类）
- `mezz.jei.api.recipe.vanilla`（8 类）
- `mezz.jei.api.registration`（13 类）
- `mezz.jei.api.runtime`（13 类）
- `mezz.jei.api.runtime.config`（6 类）
- `mezz.jei.common`（3 类）
- `mezz.jei.common.codecs`（3 类）
- `mezz.jei.common.config`（18 类）
- `mezz.jei.common.config.file`（14 类）
- `mezz.jei.common.config.file.serializers`（6 类）
- `mezz.jei.common.config.sorting`（2 类）
- `mezz.jei.common.config.sorting.serializers`（2 类）
- `mezz.jei.common.gui`（4 类）
- `mezz.jei.common.gui.elements`（12 类）
- `mezz.jei.common.gui.textures`（2 类）
- `mezz.jei.common.input`（4 类）
- `mezz.jei.common.input.keys`（7 类）
- `mezz.jei.common.network`（4 类）
- `mezz.jei.common.network.codecs`（1 类）
- `mezz.jei.common.network.packets`（8 类）
- `mezz.jei.common.network.packets.handlers`（1 类）
- `mezz.jei.common.platform`（11 类）
- `mezz.jei.common.transfer`（5 类）
- `mezz.jei.common.util`（23 类）
- `mezz.jei.core`（1 类）
- `mezz.jei.core.collect`（4 类）
- `mezz.jei.core.search`（7 类）
- `mezz.jei.core.search.suffixtree`（3 类）
- `mezz.jei.core.util`（10 类）
- `mezz.jei.core.util.function`（3 类）
- `mezz.jei.gui`（2 类）
- `mezz.jei.gui.bookmarks`（7 类）
- `mezz.jei.gui.config`（8 类）
- `mezz.jei.gui.config.file.serializers`（1 类）
- `mezz.jei.gui.elements`（2 类）
- `mezz.jei.gui.events`（1 类）
- `mezz.jei.gui.filter`（2 类）
- `mezz.jei.gui.ghost`（3 类）
- `mezz.jei.gui.ingredients`（11 类）
- `mezz.jei.gui.input`（17 类）
- `mezz.jei.gui.input.focus`（4 类）
- `mezz.jei.gui.input.handlers`（16 类）
- `mezz.jei.gui.overlay`（10 类）
- `mezz.jei.gui.overlay.bookmarks`（6 类）
- `mezz.jei.gui.overlay.bookmarks.history`（3 类）
- `mezz.jei.gui.overlay.elements`（4 类）
- `mezz.jei.gui.plugins`（1 类）
- `mezz.jei.gui.recipes`（19 类）
- `mezz.jei.gui.recipes.layouts`（2 类）
- `mezz.jei.gui.recipes.lookups`（6 类）
- `mezz.jei.gui.search`（4 类）
- `mezz.jei.gui.startup`（5 类）
- `mezz.jei.gui.util`（5 类）
- `mezz.jei.library.color`（6 类）
- `mezz.jei.library.config`（5 类）
- `mezz.jei.library.config.serializers`（2 类）
- `mezz.jei.library.focus`（3 类）
- `mezz.jei.library.gui`（3 类）
- `mezz.jei.library.gui.elements`（1 类）
- `mezz.jei.library.gui.helpers`（3 类）
- `mezz.jei.library.gui.ingredients`（6 类）
- `mezz.jei.library.gui.recipes`（7 类）
- `mezz.jei.library.gui.recipes.layout.builder`（4 类）
- `mezz.jei.library.gui.recipes.supplier.builder`（2 类）
- `mezz.jei.library.gui.widgets`（5 类）
- `mezz.jei.library.helpers`（2 类）
- `mezz.jei.library.ingredients`（11 类）
- `mezz.jei.library.ingredients.itemStacks`（4 类）
- `mezz.jei.library.ingredients.subtypes`（3 类）
- `mezz.jei.library.load`（5 类）
- `mezz.jei.library.load.registration`（14 类）
- `mezz.jei.library.plugins.debug`（13 类）
- `mezz.jei.library.plugins.debug.ingredients`（8 类）
- `mezz.jei.library.plugins.jei`（1 类）
- `mezz.jei.library.plugins.jei.info`（2 类）
- `mezz.jei.library.plugins.jei.tags`（4 类）
- `mezz.jei.library.plugins.vanilla`（2 类）
- `mezz.jei.library.plugins.vanilla.anvil`（7 类）
- `mezz.jei.library.plugins.vanilla.brewing`（3 类）
- `mezz.jei.library.plugins.vanilla.compostable`（3 类）
- `mezz.jei.library.plugins.vanilla.cooking`（5 类）
- `mezz.jei.library.plugins.vanilla.cooking.fuel`（3 类）
- `mezz.jei.library.plugins.vanilla.crafting`（6 类）
- `mezz.jei.library.plugins.vanilla.crafting.replacers`（4 类）
- `mezz.jei.library.plugins.vanilla.grindstone`（3 类）
- `mezz.jei.library.plugins.vanilla.gui`（3 类）
- `mezz.jei.library.plugins.vanilla.ingredients`（2 类）
- `mezz.jei.library.plugins.vanilla.ingredients.fluid`（2 类）
- `mezz.jei.library.plugins.vanilla.ingredients.subtypes`（8 类）
- `mezz.jei.library.plugins.vanilla.stonecutting`（1 类）
- `mezz.jei.library.recipes`（12 类）
- `mezz.jei.library.recipes.collect`（5 类）
- `mezz.jei.library.render`（2 类）
- `mezz.jei.library.render.batch`（3 类）
- `mezz.jei.library.runtime`（2 类）
- `mezz.jei.library.startup`（2 类）
- `mezz.jei.library.transfer`（6 类）
- `mezz.jei.library.util`（5 类）
- `mezz.jei.neoforge`（3 类）
- `mezz.jei.neoforge.config`（1 类）
- `mezz.jei.neoforge.events`（3 类）
- `mezz.jei.neoforge.input`（5 类）
- `mezz.jei.neoforge.network`（3 类）
- `mezz.jei.neoforge.platform`（12 类）
- `mezz.jei.neoforge.plugins.neoforge`（1 类）
- `mezz.jei.neoforge.startup`（3 类）
