# EMI（`emi`）

> jar：`[EMI]emi.jar` ｜ 版本：`1.1.22+1.21.1+neoforge` ｜ 作者：Emi
> 规模：**316 个类 / 42 个包 / 31920 行** ｜ 事件处理类 1 ｜ Mixin 28 ｜ 注册/管理类 23

**依赖**：`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.emi.emi.config` | 19 | `EmiConfig`、`ConfigEnum`、`RecipeBookAction` |
| `dev.emi.emi.screen.widget.config` | 16 | `ListWidget`、`ConfigEntryWidget`、`ConfigSearch` |
| `dev.emi.emi.recipe.special` | 15 | `EmiAnvilRepairItemRecipe`、`EmiRepairItemRecipe`、`EmiAnvilEnchantRecipe` |
| `dev.emi.emi.api.widget` | 15 | `SlotWidget`、`RecipeFillButtonWidget`、`Widget` |
| `dev.emi.emi.search` | 15 | `EmiSearch`、`LogicalAndQuery`、`RegexNameQuery` |
| `dev.emi.emi.registry` | 14 | `EmiRegistryImpl`、`EmiRecipes`、`EmiRecipeFiller` |
| `dev.emi.emi.api.recipe` | 14 | `EmiWorldInteractionRecipe`、`EmiRecipeManager`、`EmiIngredientRecipe` |
| `dev.emi.emi.screen` | 13 | `EmiScreenManager`、`RecipeScreen`、`ConfigScreen` |
| `dev.emi.emi.chess` | 13 | `ChessBoard`、`NetworkedMoveGenerator`、`EmiChess` |
| `dev.emi.emi.mixin.accessor` | 12 | `BrewingRecipeRegistryAccessor`、`BakedModelManagerAccessor`、`SmithingTransformRecipeAccessor` |
| `dev.emi.emi.mixin` | 12 | `ToastManagerMixin`、`RecipeBookWidgetMixin`、`AbstractInventoryScreenMixin` |
| `dev.emi.emi.runtime` | 12 | `EmiReloadManager`、`EmiFavorite`、`EmiDrawContext` |
| `dev.emi.emi.recipe` | 12 | `EmiSyntheticIngredientRecipe`、`EmiTagRecipe`、`EmiAnvilRecipe` |
| `dev.emi.emi.api.stack` | 11 | `EmiStack`、`EmiRegistryAdapter`、`ItemEmiStack` |
| `dev.emi.emi.data` | 10 | `RecipeDefaults`、`EmiRecipeCategoryProperties`、`RecipeDefaultLoader` |
| `dev.emi.emi.bom` | 10 | `BoM`、`TreeCost`、`MaterialTree` |
| `dev.emi.emi.api` | 9 | `EmiInitRegistry`、`EmiRegistry`、`EmiDragDropHandler` |
| `dev.emi.emi.screen.tooltip` | 8 | `RecipeCostTooltipComponent`、`RecipeTooltipComponent`、`RemainderTooltipComponent` |
| `dev.emi.emi.jemi` | 7 | `JemiRecipeHandler`、`JemiRecipe`、`JemiPlugin` |
| `dev.emi.emi.network` | 7 | `FillRecipeC2SPacket`、`CommandS2CPacket`、`CreateItemC2SPacket` |
| `dev.emi.emi.jemi.impl` | 6 | `JemiRecipeSlotBuilder`、`JemiRecipeSlot`、`JemiRecipeLayoutBuilder` |
| `dev.emi.emi.handler` | 5 | `InventoryRecipeHandler`、`StonecuttingRecipeHandler`、`CraftingRecipeHandler` |
| `dev.emi.emi.widget` | 5 | `RecipeScreenshotButtonWidget`、`RecipeBackground`、`RecipeTreeButtonWidget` |
| `dev.emi.emi.jemi.runtime` | 5 | `JemiDragDropHandler`、`JemiRecipesGui`、`JemiIngredientListOverlay` |
| `dev.emi.emi.api.render` | 5 | `EmiTooltipMetadata`、`EmiTooltipComponents`、`EmiRender` |
| `dev.emi.emi.mixinsupport.annotation` | 5 | `InvokeTarget`、`StripConstructors`、`Transform` |
| `dev.emi.emi.platform.neoforge` | 4 | `EmiClientNeoForge`、`EmiNeoForge`、`EmiPacketHandler` |
| `dev.emi.emi` | 4 | `EmiPort`、`EmiRenderHelper`、`VanillaPlugin` |
| `dev.emi.emi.stack.serializer` | 4 | `ItemEmiStackSerializer`、`ListEmiIngredientSerializer`、`FluidEmiStackSerializer` |
| `dev.emi.emi.screen.widget` | 4 | `EmiSearchWidget`、`SidebarButtonWidget`、`SizedButtonWidget` |
| `dev.emi.emi.api.recipe.handler` | 3 | `StandardRecipeHandler`、`EmiRecipeHandler`、`EmiCraftContext` |
| `dev.emi.emi.mixin.conversion` | 3 | `ItemStackMixin`、`ItemConvertibleMixin`、`FluidMixin` |
| `dev.emi.emi.platform` | 3 | `EmiAgnos`、`EmiClient`、`EmiMain` |
| `dev.emi.emi.mixinsupport` | 3 | `EmiMixinPlugin`、`EmiMixinTransformation`、`MixinPlaceholder` |
| `dev.emi.emi.runtime.dev` | 2 | `RecipeError`、`EmiDev` |
| `dev.emi.emi.input` | 2 | `EmiBind`、`EmiInput` |
| `dev.emi.emi.jemi.widget` | 2 | `JemiSlotWidget`、`JemiTankWidget` |
| `dev.emi.emi.api.stack.serializer` | 2 | `EmiStackSerializer`、`EmiIngredientSerializer` |
| `dev.emi.emi.api.neoforge` | 2 | `NeoForgeEmiStack`、`NeoForgeEmiIngredient` |
| `dev.emi.emi.com.unascribed.qdcss` | 1 | `QDCSS` |
| … | | 其余 2 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EmiScreenManager` | class | EmiStackInteraction | — | 87 | 2038 | [EmiScreenManager.java](dev/emi/emi/screen/EmiScreenManager.java) |
| `EmiClientNeoForge` | class | — | EventBusSubscriber | 7 | 100 | [EmiClientNeoForge.java](dev/emi/emi/platform/neoforge/EmiClientNeoForge.java) |
| `BrewingRecipeRegistryAccessor` | interface | — | Mixin | 0 | 23 | [BrewingRecipeRegistryAccessor.java](dev/emi/emi/mixin/accessor/BrewingRecipeRegistryAccessor.java) |
| `RecipeScreen` | class | Screen | — | 24 | 660 | [RecipeScreen.java](dev/emi/emi/screen/RecipeScreen.java) |
| `ConfigScreen` | class | Screen | — | 24 | 487 | [ConfigScreen.java](dev/emi/emi/screen/ConfigScreen.java) |
| `JemiRecipeHandler` | class | EmiRecipeHandler<T> | — | 11 | 254 | [JemiRecipeHandler.java](dev/emi/emi/jemi/JemiRecipeHandler.java) |
| `BoMScreen` | class | Screen | — | 29 | 906 | [BoMScreen.java](dev/emi/emi/screen/BoMScreen.java) |
| `ToastManagerMixin` | class | — | Mixin | 1 | 23 | [ToastManagerMixin.java](dev/emi/emi/mixin/ToastManagerMixin.java) |
| `BakedModelManagerAccessor` | interface | — | Mixin | 0 | 15 | [BakedModelManagerAccessor.java](dev/emi/emi/mixin/accessor/BakedModelManagerAccessor.java) |
| `InventoryRecipeHandler` | class | StandardRecipeHandler<InventoryMenu> | — | 7 | 95 | [InventoryRecipeHandler.java](dev/emi/emi/handler/InventoryRecipeHandler.java) |
| `StonecuttingRecipeHandler` | class | StandardRecipeHandler<StonecutterMen | — | 5 | 77 | [StonecuttingRecipeHandler.java](dev/emi/emi/handler/StonecuttingRecipeHandler.java) |
| `RecipeBookWidgetMixin` | class | — | Mixin | 3 | 39 | [RecipeBookWidgetMixin.java](dev/emi/emi/mixin/RecipeBookWidgetMixin.java) |
| `SmithingTransformRecipeAccessor` | interface | — | Mixin | 0 | 19 | [SmithingTransformRecipeAccessor.java](dev/emi/emi/mixin/accessor/SmithingTransformRecipeAccessor.java) |
| `SmithingTrimRecipeAccessor` | interface | — | Mixin | 0 | 19 | [SmithingTrimRecipeAccessor.java](dev/emi/emi/mixin/accessor/SmithingTrimRecipeAccessor.java) |
| `ItemRendererAccessor` | interface | — | Mixin | 0 | 16 | [ItemRendererAccessor.java](dev/emi/emi/mixin/accessor/ItemRendererAccessor.java) |
| `EmiNeoForge` | class | — | Mod | 2 | 43 | [EmiNeoForge.java](dev/emi/emi/platform/neoforge/EmiNeoForge.java) |
| `EmiRegistryImpl` | class | EmiRegistry | — | 21 | 154 | [EmiRegistryImpl.java](dev/emi/emi/registry/EmiRegistryImpl.java) |
| `CraftingRecipeHandler` | class | StandardRecipeHandler<CraftingMenu> | — | 4 | 49 | [CraftingRecipeHandler.java](dev/emi/emi/handler/CraftingRecipeHandler.java) |
| `CoercedRecipeHandler` | class | StandardRecipeHandler<T> | — | 4 | 75 | [CoercedRecipeHandler.java](dev/emi/emi/handler/CoercedRecipeHandler.java) |
| `CookingRecipeHandler` | class | StandardRecipeHandler<T> | — | 3 | 39 | [CookingRecipeHandler.java](dev/emi/emi/handler/CookingRecipeHandler.java) |
| `FillRecipeC2SPacket` | class | EmiPacket | — | 6 | 282 | [FillRecipeC2SPacket.java](dev/emi/emi/network/FillRecipeC2SPacket.java) |
| `AbstractInventoryScreenMixin` | class | AbstractContainerScreen | Mixin | 8 | 151 | [AbstractInventoryScreenMixin.java](dev/emi/emi/mixin/AbstractInventoryScreenMixin.java) |
| `EmiConfig` | class | — | — | 12 | 705 | [EmiConfig.java](dev/emi/emi/config/EmiConfig.java) |
| `EmiPacketHandler` | class | — | — | 5 | 60 | [EmiPacketHandler.java](dev/emi/emi/platform/neoforge/EmiPacketHandler.java) |
| `EmiWorldInteractionRecipe` | class | EmiRecipe | — | 20 | 233 | [EmiWorldInteractionRecipe.java](dev/emi/emi/api/recipe/EmiWorldInteractionRecipe.java) |
| `EmiRecipeManager` | interface | — | — | 0 | 25 | [EmiRecipeManager.java](dev/emi/emi/api/recipe/EmiRecipeManager.java) |
| `StandardRecipeHandler` | interface | — | — | 1 | 107 | [StandardRecipeHandler.java](dev/emi/emi/api/recipe/handler/StandardRecipeHandler.java) |
| `EmiRecipeHandler` | interface | — | — | 0 | 36 | [EmiRecipeHandler.java](dev/emi/emi/api/recipe/handler/EmiRecipeHandler.java) |
| `EmiReloadManager` | class | Runnable | — | 10 | 253 | [EmiReloadManager.java](dev/emi/emi/runtime/EmiReloadManager.java) |
| `ConfigPresetScreen` | class | Screen | — | 12 | 163 | [ConfigPresetScreen.java](dev/emi/emi/screen/ConfigPresetScreen.java) |
| `ListWidget` | class | AbstractContainerEventHandler | — | 53 | 509 | [ListWidget.java](dev/emi/emi/screen/widget/config/ListWidget.java) |
| `MinecraftClientMixin` | class | — | Mixin | 2 | 43 | [MinecraftClientMixin.java](dev/emi/emi/mixin/MinecraftClientMixin.java) |
| `HandledScreenMixin` | class | Screen | Mixin | 2 | 67 | [HandledScreenMixin.java](dev/emi/emi/mixin/HandledScreenMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 35 | [ItemStackMixin.java](dev/emi/emi/mixin/ItemStackMixin.java) |
| `ScreenMixin` | class | — | Mixin | 2 | 28 | [ScreenMixin.java](dev/emi/emi/mixin/ScreenMixin.java) |
| `HoeItemMixin` | class | — | Mixin | 2 | 30 | [HoeItemMixin.java](dev/emi/emi/mixin/HoeItemMixin.java) |
| `ShovelItemAccessor` | interface | — | Mixin | 0 | 17 | [ShovelItemAccessor.java](dev/emi/emi/mixin/accessor/ShovelItemAccessor.java) |
| `HoeItemAccessor` | interface | — | Mixin | 0 | 20 | [HoeItemAccessor.java](dev/emi/emi/mixin/accessor/HoeItemAccessor.java) |
| `HandledScreenAccessor` | interface | — | Mixin | 0 | 29 | [HandledScreenAccessor.java](dev/emi/emi/mixin/accessor/HandledScreenAccessor.java) |
| `AxeItemAccessor` | interface | — | Mixin | 0 | 16 | [AxeItemAccessor.java](dev/emi/emi/mixin/accessor/AxeItemAccessor.java) |
| `ItemStackMixin` | class | EmiStackConvertible | Mixin | 2 | 20 | [ItemStackMixin.java](dev/emi/emi/mixin/conversion/ItemStackMixin.java) |
| `ItemConvertibleMixin` | interface | — | Mixin | 0 | 21 | [ItemConvertibleMixin.java](dev/emi/emi/mixin/conversion/ItemConvertibleMixin.java) |
| `EmiAnvilRepairItemRecipe` | class | EmiRecipe | — | 11 | 103 | [EmiAnvilRepairItemRecipe.java](dev/emi/emi/recipe/special/EmiAnvilRepairItemRecipe.java) |
| `JemiRecipe` | class | Widget | — | 15 | 203 | [JemiRecipe.java](dev/emi/emi/jemi/JemiRecipe.java) |
| `JemiRecipeSlotBuilder` | class | IRecipeSlotBuilder | — | 20 | 140 | [JemiRecipeSlotBuilder.java](dev/emi/emi/jemi/impl/JemiRecipeSlotBuilder.java) |
| `QDCSS` | class | QDCSS.QDCSSException | — | 49 | 467 | [QDCSS.java](dev/emi/emi/com/unascribed/qdcss/QDCSS.java) |
| `EmiPort` | class | — | — | 36 | 239 | [EmiPort.java](dev/emi/emi/EmiPort.java) |
| `EmiRenderHelper` | class | — | — | 26 | 364 | [EmiRenderHelper.java](dev/emi/emi/EmiRenderHelper.java) |
| `VanillaPlugin` | class | EmiPlugin | EmiEntrypoint | 18 | 997 | [VanillaPlugin.java](dev/emi/emi/VanillaPlugin.java) |
| `ConfigEnumScreen` | class | Screen | — | 7 | 104 | [ConfigEnumScreen.java](dev/emi/emi/screen/ConfigEnumScreen.java) |
| `EmiRepairItemRecipe` | class | EmiPatternCraftingRecipe | — | 5 | 72 | [EmiRepairItemRecipe.java](dev/emi/emi/recipe/special/EmiRepairItemRecipe.java) |
| `EmiStack` | class | EmiIngredient | — | 36 | 209 | [EmiStack.java](dev/emi/emi/api/stack/EmiStack.java) |
| `EmiIngredientRecipe` | class | SlotWidget | — | 16 | 133 | [EmiIngredientRecipe.java](dev/emi/emi/api/recipe/EmiIngredientRecipe.java) |
| `EmiRecipes` | class | EmiRecipeManager | — | 14 | 337 | [EmiRecipes.java](dev/emi/emi/registry/EmiRecipes.java) |
| `EmiRecipeFiller` | class | — | — | 9 | 419 | [EmiRecipeFiller.java](dev/emi/emi/registry/EmiRecipeFiller.java) |
| `CommandS2CPacket` | class | EmiPacket | — | 3 | 67 | [CommandS2CPacket.java](dev/emi/emi/network/CommandS2CPacket.java) |
| `StackBatcher` | class | MultiBufferSource | — | 26 | 394 | [StackBatcher.java](dev/emi/emi/screen/StackBatcher.java) |
| `RecipeScreenshotButtonWidget` | class | RecipeButtonWidget | — | 2 | 45 | [RecipeScreenshotButtonWidget.java](dev/emi/emi/widget/RecipeScreenshotButtonWidget.java) |
| `MouseMixin` | class | — | Mixin | 4 | 117 | [MouseMixin.java](dev/emi/emi/mixin/MouseMixin.java) |
| `KeyboardMixin` | class | — | Mixin | 2 | 65 | [KeyboardMixin.java](dev/emi/emi/mixin/KeyboardMixin.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EmiClientNeoForge` | 3 | `dev.emi.emi.platform.neoforge` | [EmiClientNeoForge.java](dev/emi/emi/platform/neoforge/EmiClientNeoForge.java) |

## Mixin（28 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BrewingRecipeRegistryAccessor` | `BrewingRecipeRegistryAccessor` | [BrewingRecipeRegistryAccessor.java](dev/emi/emi/mixin/accessor/BrewingRecipeRegistryAccessor.java) |
| `ToastManagerMixin` | `ToastManager` | [ToastManagerMixin.java](dev/emi/emi/mixin/ToastManagerMixin.java) |
| `BakedModelManagerAccessor` | `BakedModelManagerAccessor` | [BakedModelManagerAccessor.java](dev/emi/emi/mixin/accessor/BakedModelManagerAccessor.java) |
| `RecipeBookWidgetMixin` | `RecipeBookWidget` | [RecipeBookWidgetMixin.java](dev/emi/emi/mixin/RecipeBookWidgetMixin.java) |
| `SmithingTransformRecipeAccessor` | `SmithingTransformRecipeAccessor` | [SmithingTransformRecipeAccessor.java](dev/emi/emi/mixin/accessor/SmithingTransformRecipeAccessor.java) |
| `SmithingTrimRecipeAccessor` | `SmithingTrimRecipeAccessor` | [SmithingTrimRecipeAccessor.java](dev/emi/emi/mixin/accessor/SmithingTrimRecipeAccessor.java) |
| `ItemRendererAccessor` | `ItemRendererAccessor` | [ItemRendererAccessor.java](dev/emi/emi/mixin/accessor/ItemRendererAccessor.java) |
| `AbstractInventoryScreenMixin` | `AbstractInventoryScreen` | [AbstractInventoryScreenMixin.java](dev/emi/emi/mixin/AbstractInventoryScreenMixin.java) |
| `MinecraftClientMixin` | `MinecraftClient` | [MinecraftClientMixin.java](dev/emi/emi/mixin/MinecraftClientMixin.java) |
| `HandledScreenMixin` | `HandledScreen` | [HandledScreenMixin.java](dev/emi/emi/mixin/HandledScreenMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](dev/emi/emi/mixin/ItemStackMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](dev/emi/emi/mixin/ScreenMixin.java) |
| `HoeItemMixin` | `HoeItem` | [HoeItemMixin.java](dev/emi/emi/mixin/HoeItemMixin.java) |
| `ShovelItemAccessor` | `ShovelItemAccessor` | [ShovelItemAccessor.java](dev/emi/emi/mixin/accessor/ShovelItemAccessor.java) |
| `HoeItemAccessor` | `HoeItemAccessor` | [HoeItemAccessor.java](dev/emi/emi/mixin/accessor/HoeItemAccessor.java) |
| `HandledScreenAccessor` | `HandledScreenAccessor` | [HandledScreenAccessor.java](dev/emi/emi/mixin/accessor/HandledScreenAccessor.java) |
| `AxeItemAccessor` | `AxeItemAccessor` | [AxeItemAccessor.java](dev/emi/emi/mixin/accessor/AxeItemAccessor.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](dev/emi/emi/mixin/conversion/ItemStackMixin.java) |
| `ItemConvertibleMixin` | `ItemConvertible` | [ItemConvertibleMixin.java](dev/emi/emi/mixin/conversion/ItemConvertibleMixin.java) |
| `MouseMixin` | `Mouse` | [MouseMixin.java](dev/emi/emi/mixin/MouseMixin.java) |
| `KeyboardMixin` | `Keyboard` | [KeyboardMixin.java](dev/emi/emi/mixin/KeyboardMixin.java) |
| `CraftingResultSlotMixin` | `CraftingResultSlot` | [CraftingResultSlotMixin.java](dev/emi/emi/mixin/CraftingResultSlotMixin.java) |
| `DrawContextMixin` | `DrawContext` | [DrawContextMixin.java](dev/emi/emi/mixin/DrawContextMixin.java) |
| `OrderedTextTooltipComponentAccessor` | `OrderedTextTooltipComponentAccessor` | [OrderedTextTooltipComponentAccessor.java](dev/emi/emi/mixin/accessor/OrderedTextTooltipComponentAccessor.java) |
| `CraftingResultSlotAccessor` | `CraftingResultSlotAccessor` | [CraftingResultSlotAccessor.java](dev/emi/emi/mixin/accessor/CraftingResultSlotAccessor.java) |
| `DrawContextAccessor` | `DrawContextAccessor` | [DrawContextAccessor.java](dev/emi/emi/mixin/accessor/DrawContextAccessor.java) |
| `FluidMixin` | `Fluid` | [FluidMixin.java](dev/emi/emi/mixin/conversion/FluidMixin.java) |
| `PluginCallerMixin` | `PluginCaller` | [PluginCallerMixin.java](dev/emi/emi/mixin/jei/PluginCallerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigScreen` | — | [ConfigScreen.java](dev/emi/emi/screen/ConfigScreen.java) |
| `EmiConfig` | — | [EmiConfig.java](dev/emi/emi/config/EmiConfig.java) |
| `ConfigPresetScreen` | — | [ConfigPresetScreen.java](dev/emi/emi/screen/ConfigPresetScreen.java) |
| `ConfigEnumScreen` | — | [ConfigEnumScreen.java](dev/emi/emi/screen/ConfigEnumScreen.java) |
| `ConfigEntryWidget` | — | [ConfigEntryWidget.java](dev/emi/emi/screen/widget/config/ConfigEntryWidget.java) |
| `ConfigSearch` | — | [ConfigSearch.java](dev/emi/emi/screen/widget/config/ConfigSearch.java) |
| `ConfigJumpButton` | — | [ConfigJumpButton.java](dev/emi/emi/screen/widget/config/ConfigJumpButton.java) |
| `ConfigEnum` | — | [ConfigEnum.java](dev/emi/emi/config/ConfigEnum.java) |
| `ConfigPresets` | — | [ConfigPresets.java](dev/emi/emi/config/ConfigPresets.java) |

## 按包浏览

- `dev.emi.emi`（4 类）
- `dev.emi.emi.api`（9 类）
- `dev.emi.emi.api.neoforge`（2 类）
- `dev.emi.emi.api.recipe`（14 类）
- `dev.emi.emi.api.recipe.handler`（3 类）
- `dev.emi.emi.api.render`（5 类）
- `dev.emi.emi.api.stack`（11 类）
- `dev.emi.emi.api.stack.serializer`（2 类）
- `dev.emi.emi.api.widget`（15 类）
- `dev.emi.emi.bom`（10 类）
- `dev.emi.emi.chess`（13 类）
- `dev.emi.emi.com.unascribed.qdcss`（1 类）
- `dev.emi.emi.config`（19 类）
- `dev.emi.emi.data`（10 类）
- `dev.emi.emi.handler`（5 类）
- `dev.emi.emi.input`（2 类）
- `dev.emi.emi.jemi`（7 类）
- `dev.emi.emi.jemi.impl`（6 类）
- `dev.emi.emi.jemi.runtime`（5 类）
- `dev.emi.emi.jemi.widget`（2 类）
- `dev.emi.emi.mixin`（12 类）
- `dev.emi.emi.mixin.accessor`（12 类）
- `dev.emi.emi.mixin.conversion`（3 类）
- `dev.emi.emi.mixin.jei`（1 类）
- `dev.emi.emi.mixinsupport`（3 类）
- `dev.emi.emi.mixinsupport.annotation`（5 类）
- `dev.emi.emi.network`（7 类）
- `dev.emi.emi.platform`（3 类）
- `dev.emi.emi.platform.neoforge`（4 类）
- `dev.emi.emi.recipe`（12 类）
- `dev.emi.emi.recipe.special`（15 类）
- `dev.emi.emi.registry`（14 类）
- `dev.emi.emi.runtime`（12 类）
- `dev.emi.emi.runtime.dev`（2 类）
- `dev.emi.emi.screen`（13 类）
- `dev.emi.emi.screen.tooltip`（8 类）
- `dev.emi.emi.screen.widget`（4 类）
- `dev.emi.emi.screen.widget.config`（16 类）
- `dev.emi.emi.search`（15 类）
- `dev.emi.emi.stack.serializer`（4 类）
- `dev.emi.emi.util`（1 类）
- `dev.emi.emi.widget`（5 类）
