# Patchouli（`patchouli`）

> jar：`[帕秋莉手册]Patchouli.jar` ｜ 版本：`${file.jarVersion}` ｜ 作者：Vazkii
> 规模：**162 个类 / 33 个包 / 12562 行** ｜ 事件处理类 2 ｜ Mixin 10 ｜ 注册/管理类 12

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `vazkii.patchouli.client.book.page` | 17 | `PageMultiblock`、`PageEntity`、`PageQuest` |
| `vazkii.patchouli.api` | 16 | `BookDrawScreenEvent`、`PatchouliConfigAccess`、`BookContentsReloadEvent` |
| `vazkii.patchouli.client.book` | 14 | `ClientBookRegistry`、`BookEntry`、`BookCategory` |
| `vazkii.patchouli.client.book.gui.button` | 10 | `GuiButtonBookConfig`、`GuiButtonBookMarkRead`、`GuiButtonBookArrow` |
| `vazkii.patchouli.client.book.gui` | 9 | `GuiBook`、`GuiBookEntry`、`GuiBookEntryList` |
| `vazkii.patchouli.client.book.template.component` | 9 | `ComponentEntity`、`ComponentItemStack`、`ComponentText` |
| `vazkii.patchouli.mixin.client` | 8 | `MixinClientAdvancements`、`AccessorClientAdvancements`、`MixinLevelRenderer` |
| `vazkii.patchouli.common.multiblock` | 8 | `MultiblockRegistry`、`AbstractMultiblock`、`DenseMultiblock` |
| `vazkii.patchouli.client.book.template.variable` | 7 | `ItemStackVariableSerializer`、`ItemStackArrayVariableSerializer`、`VariableHelperImpl` |
| `vazkii.patchouli.client.book.text` | 5 | `BookTextParser`、`TextLayouter`、`SpanState` |
| `vazkii.patchouli.client.book.template` | 5 | `VariableAssigner`、`TemplateInclusion`、`TemplateComponent` |
| `vazkii.patchouli.client.handler` | 4 | `MultiblockVisualizationHandler`、`BookRightClickHandler`、`TooltipHandler` |
| `vazkii.patchouli.client.book.page.abstr` | 4 | `PageDoubleRecipeRegistry`、`PageDoubleRecipe`、`PageSimpleProcessingRecipe` |
| `vazkii.patchouli.common.util` | 4 | `ItemStackUtil`、`EntityUtil`、`SerializationUtil` |
| `vazkii.patchouli.client.base` | 4 | `BookModel`、`ClientAdvancements`、`PersistentData` |
| `vazkii.patchouli.common.base` | 3 | `PatchouliAPIImpl`、`PatchouliConfig`、`PatchouliSounds` |
| `vazkii.patchouli.common.book` | 3 | `BookRegistry`、`Book`、`BookFolderLoader` |
| `vazkii.patchouli.api.stub` | 3 | `StubPatchouliAPI`、`StubMultiblock`、`StubMatcher` |
| `vazkii.patchouli.client.book.template.test` | 3 | `RecipeTestProcessor`、`EntityTestProcessor`、`ComponentCustomTest` |
| `vazkii.patchouli.common.item` | 3 | `ItemModBook`、`PatchouliItems`、`PatchouliDataComponents` |
| `vazkii.patchouli.xplat` | 3 | `IClientXplatAbstractions`、`IXplatAbstractions`、`XplatModContainer` |
| `vazkii.patchouli.neoforge.client` | 2 | `NeoForgeClientInitializer`、`NeoForgeClientXplatImpl` |
| `vazkii.patchouli.neoforge.common` | 2 | `NeoForgeModInitializer`、`NeoForgePatchouliConfig` |
| `vazkii.patchouli.mixin` | 2 | `AccessorSmithingTransformRecipe`、`AccessorSmithingTrimRecipe` |
| `vazkii.patchouli.common.handler` | 2 | `LecternEventHandler`、`ReloadContentsHandler` |
| `vazkii.patchouli.neoforge.xplat` | 2 | `NeoForgeXplatImpl`、`NeoForgeXplatModContainer` |
| `vazkii.patchouli.network` | 2 | `MessageOpenBookGui`、`MessageReloadBookContents` |
| `vazkii.patchouli.common.advancement` | 2 | `PatchouliCriteriaTriggers`、`BookOpenTrigger` |
| `vazkii.patchouli.client.gui` | 2 | `GuiButtonInventoryBook`、`GuiAdvancementsExt` |
| `vazkii.patchouli.neoforge.network` | 1 | `NeoForgeNetworkHandler` |
| `vazkii.patchouli.neoforge.network.handler` | 1 | `NeoForgeClientPayloadHandler` |
| `vazkii.patchouli.common.command` | 1 | `OpenBookCommand` |
| `vazkii.patchouli.client.jei` | 1 | `PatchouliJeiPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeClientInitializer` | class | — | EventBusSubscriber | 7 | 123 | [NeoForgeClientInitializer.java](vazkii/patchouli/neoforge/client/NeoForgeClientInitializer.java) |
| `NeoForgeModInitializer` | class | — | EventBusSubscriber、Mod | 3 | 77 | [NeoForgeModInitializer.java](vazkii/patchouli/neoforge/common/NeoForgeModInitializer.java) |
| `MultiblockVisualizationHandler` | class | BufferSource | — | 18 | 385 | [MultiblockVisualizationHandler.java](vazkii/patchouli/client/handler/MultiblockVisualizationHandler.java) |
| `AccessorSmithingTransformRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTransformRecipe.java](vazkii/patchouli/mixin/AccessorSmithingTransformRecipe.java) |
| `AccessorSmithingTrimRecipe` | interface | — | Mixin | 0 | 19 | [AccessorSmithingTrimRecipe.java](vazkii/patchouli/mixin/AccessorSmithingTrimRecipe.java) |
| `PageDoubleRecipeRegistry` | class | — | — | 2 | 51 | [PageDoubleRecipeRegistry.java](vazkii/patchouli/client/book/page/abstr/PageDoubleRecipeRegistry.java) |
| `ClientBookRegistry` | class | JsonDeserializer<BookPage> | — | 7 | 147 | [ClientBookRegistry.java](vazkii/patchouli/client/book/ClientBookRegistry.java) |
| `NeoForgeNetworkHandler` | class | — | — | 3 | 30 | [NeoForgeNetworkHandler.java](vazkii/patchouli/neoforge/network/NeoForgeNetworkHandler.java) |
| `MixinClientAdvancements` | class | — | Mixin | 1 | 17 | [MixinClientAdvancements.java](vazkii/patchouli/mixin/client/MixinClientAdvancements.java) |
| `AccessorClientAdvancements` | interface | — | Mixin | 0 | 15 | [AccessorClientAdvancements.java](vazkii/patchouli/mixin/client/AccessorClientAdvancements.java) |
| `MixinLevelRenderer` | class | — | Mixin | 1 | 32 | [MixinLevelRenderer.java](vazkii/patchouli/mixin/client/MixinLevelRenderer.java) |
| `AccessorScreen` | interface | — | Mixin | 0 | 18 | [AccessorScreen.java](vazkii/patchouli/mixin/client/AccessorScreen.java) |
| `MixinInventoryScreen` | class | EffectRenderingInventoryScreen | Mixin | 1 | 57 | [MixinInventoryScreen.java](vazkii/patchouli/mixin/client/MixinInventoryScreen.java) |
| `LecternEventHandler` | class | — | — | 2 | 56 | [LecternEventHandler.java](vazkii/patchouli/common/handler/LecternEventHandler.java) |
| `MultiblockRegistry` | class | — | — | 1 | 20 | [MultiblockRegistry.java](vazkii/patchouli/common/multiblock/MultiblockRegistry.java) |
| `GuiBook` | class | Screen | — | 46 | 550 | [GuiBook.java](vazkii/patchouli/client/book/gui/GuiBook.java) |
| `NeoForgeClientPayloadHandler` | class | — | — | 3 | 32 | [NeoForgeClientPayloadHandler.java](vazkii/patchouli/neoforge/network/handler/NeoForgeClientPayloadHandler.java) |
| `PatchouliAPIImpl` | class | PatchouliAPI.IPatchouliAPI | — | 35 | 265 | [PatchouliAPIImpl.java](vazkii/patchouli/common/base/PatchouliAPIImpl.java) |
| `BookRegistry` | class | — | — | 6 | 131 | [BookRegistry.java](vazkii/patchouli/common/book/BookRegistry.java) |
| `StubPatchouliAPI` | class | PatchouliAPI.IPatchouliAPI | — | 39 | 225 | [StubPatchouliAPI.java](vazkii/patchouli/api/stub/StubPatchouliAPI.java) |
| `AccessorKeyMapping` | interface | — | Mixin | 0 | 15 | [AccessorKeyMapping.java](vazkii/patchouli/mixin/client/AccessorKeyMapping.java) |
| `MixinSystemReport` | class | — | Mixin | 1 | 17 | [MixinSystemReport.java](vazkii/patchouli/mixin/client/MixinSystemReport.java) |
| `AccessorMultiBufferSource` | interface | — | Mixin | 0 | 18 | [AccessorMultiBufferSource.java](vazkii/patchouli/mixin/client/AccessorMultiBufferSource.java) |
| `ItemStackUtil` | class | — | — | 12 | 202 | [ItemStackUtil.java](vazkii/patchouli/common/util/ItemStackUtil.java) |
| `AbstractMultiblock` | class | IMultiblock | — | 22 | 180 | [AbstractMultiblock.java](vazkii/patchouli/common/multiblock/AbstractMultiblock.java) |
| `BookDrawScreenEvent` | class | Event | — | 6 | 49 | [BookDrawScreenEvent.java](vazkii/patchouli/api/BookDrawScreenEvent.java) |
| `GuiBookEntry` | class | GuiBook | — | 29 | 318 | [GuiBookEntry.java](vazkii/patchouli/client/book/gui/GuiBookEntry.java) |
| `PageDoubleRecipe` | class | PageWithText | — | 11 | 94 | [PageDoubleRecipe.java](vazkii/patchouli/client/book/page/abstr/PageDoubleRecipe.java) |
| `NeoForgePatchouliConfig` | class | — | — | 8 | 91 | [NeoForgePatchouliConfig.java](vazkii/patchouli/neoforge/common/NeoForgePatchouliConfig.java) |
| `ReloadContentsHandler` | class | — | — | 1 | 13 | [ReloadContentsHandler.java](vazkii/patchouli/common/handler/ReloadContentsHandler.java) |
| `PatchouliConfig` | class | — | — | 7 | 93 | [PatchouliConfig.java](vazkii/patchouli/common/base/PatchouliConfig.java) |
| `BookRightClickHandler` | class | — | — | 3 | 91 | [BookRightClickHandler.java](vazkii/patchouli/client/handler/BookRightClickHandler.java) |
| `TooltipHandler` | class | — | — | 1 | 111 | [TooltipHandler.java](vazkii/patchouli/client/handler/TooltipHandler.java) |
| `BookCrashHandler` | class | Supplier<String> | — | 2 | 62 | [BookCrashHandler.java](vazkii/patchouli/client/handler/BookCrashHandler.java) |
| `BookEntry` | class | AbstractReadStateHolder | — | 22 | 317 | [BookEntry.java](vazkii/patchouli/client/book/BookEntry.java) |
| `DenseMultiblock` | class | AbstractMultiblock | — | 9 | 206 | [DenseMultiblock.java](vazkii/patchouli/common/multiblock/DenseMultiblock.java) |
| `PatchouliConfigAccess` | interface | — | — | 0 | 26 | [PatchouliConfigAccess.java](vazkii/patchouli/api/PatchouliConfigAccess.java) |
| `StubMultiblock` | class | IMultiblock | — | 12 | 78 | [StubMultiblock.java](vazkii/patchouli/api/stub/StubMultiblock.java) |
| `BookTextParser` | class | — | — | 13 | 404 | [BookTextParser.java](vazkii/patchouli/client/book/text/BookTextParser.java) |
| `GuiBookEntryList` | class | GuiBook | — | 16 | 216 | [GuiBookEntryList.java](vazkii/patchouli/client/book/gui/GuiBookEntryList.java) |
| `GuiButtonBookConfig` | class | GuiButtonBook | — | 0 | 12 | [GuiButtonBookConfig.java](vazkii/patchouli/client/book/gui/button/GuiButtonBookConfig.java) |
| `PageMultiblock` | class | PageWithText | — | 9 | 216 | [PageMultiblock.java](vazkii/patchouli/client/book/page/PageMultiblock.java) |
| `PageSimpleProcessingRecipe` | class | — | — | 3 | 38 | [PageSimpleProcessingRecipe.java](vazkii/patchouli/client/book/page/abstr/PageSimpleProcessingRecipe.java) |
| `RecipeTestProcessor` | class | IComponentProcessor | — | 2 | 44 | [RecipeTestProcessor.java](vazkii/patchouli/client/book/template/test/RecipeTestProcessor.java) |
| `Book` | class | — | — | 13 | 236 | [Book.java](vazkii/patchouli/common/book/Book.java) |
| `ItemModBook` | class | Item | — | 9 | 117 | [ItemModBook.java](vazkii/patchouli/common/item/ItemModBook.java) |
| `OpenBookCommand` | class | — | — | 2 | 65 | [OpenBookCommand.java](vazkii/patchouli/common/command/OpenBookCommand.java) |
| `EntityUtil` | class | — | — | 3 | 73 | [EntityUtil.java](vazkii/patchouli/common/util/EntityUtil.java) |
| `SerializedMultiblock` | class | — | — | 6 | 117 | [SerializedMultiblock.java](vazkii/patchouli/common/multiblock/SerializedMultiblock.java) |
| `SparseMultiblock` | class | AbstractMultiblock | — | 7 | 86 | [SparseMultiblock.java](vazkii/patchouli/common/multiblock/SparseMultiblock.java) |
| `StateMatcher` | class | IStateMatcher | — | 15 | 108 | [StateMatcher.java](vazkii/patchouli/common/multiblock/StateMatcher.java) |
| `BookContentsReloadEvent` | class | Event | — | 1 | 17 | [BookContentsReloadEvent.java](vazkii/patchouli/api/BookContentsReloadEvent.java) |
| `BookModel` | class | BakedModel | — | 15 | 107 | [BookModel.java](vazkii/patchouli/client/base/BookModel.java) |
| `ClientAdvancements` | class | Toast | — | 6 | 86 | [ClientAdvancements.java](vazkii/patchouli/client/base/ClientAdvancements.java) |
| `BookCategory` | class | AbstractReadStateHolder | — | 19 | 192 | [BookCategory.java](vazkii/patchouli/client/book/BookCategory.java) |
| `LiquidBlockVertexConsumer` | record | VertexConsumer | — | 7 | 39 | [LiquidBlockVertexConsumer.java](vazkii/patchouli/client/book/LiquidBlockVertexConsumer.java) |
| `TextLayouter` | class | — | — | 13 | 243 | [TextLayouter.java](vazkii/patchouli/client/book/text/TextLayouter.java) |
| `GuiBookLanding` | class | GuiBook | — | 14 | 247 | [GuiBookLanding.java](vazkii/patchouli/client/book/gui/GuiBookLanding.java) |
| `PageEntity` | class | PageWithText | — | 6 | 115 | [PageEntity.java](vazkii/patchouli/client/book/page/PageEntity.java) |
| `ComponentEntity` | class | TemplateComponent | — | 5 | 81 | [ComponentEntity.java](vazkii/patchouli/client/book/template/component/ComponentEntity.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeClientInitializer` | 5 | `vazkii.patchouli.neoforge.client` | [NeoForgeClientInitializer.java](vazkii/patchouli/neoforge/client/NeoForgeClientInitializer.java) |
| `NeoForgeModInitializer` | 3 | `vazkii.patchouli.neoforge.common` | [NeoForgeModInitializer.java](vazkii/patchouli/neoforge/common/NeoForgeModInitializer.java) |

## Mixin（10 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessorSmithingTransformRecipe` | `AccessorSmithingTransformRecipe` | [AccessorSmithingTransformRecipe.java](vazkii/patchouli/mixin/AccessorSmithingTransformRecipe.java) |
| `AccessorSmithingTrimRecipe` | `AccessorSmithingTrimRecipe` | [AccessorSmithingTrimRecipe.java](vazkii/patchouli/mixin/AccessorSmithingTrimRecipe.java) |
| `MixinClientAdvancements` | `ClientAdvancements` | [MixinClientAdvancements.java](vazkii/patchouli/mixin/client/MixinClientAdvancements.java) |
| `AccessorClientAdvancements` | `AccessorClientAdvancements` | [AccessorClientAdvancements.java](vazkii/patchouli/mixin/client/AccessorClientAdvancements.java) |
| `MixinLevelRenderer` | `LevelRenderer` | [MixinLevelRenderer.java](vazkii/patchouli/mixin/client/MixinLevelRenderer.java) |
| `AccessorScreen` | `AccessorScreen` | [AccessorScreen.java](vazkii/patchouli/mixin/client/AccessorScreen.java) |
| `MixinInventoryScreen` | `InventoryScreen` | [MixinInventoryScreen.java](vazkii/patchouli/mixin/client/MixinInventoryScreen.java) |
| `AccessorKeyMapping` | `AccessorKeyMapping` | [AccessorKeyMapping.java](vazkii/patchouli/mixin/client/AccessorKeyMapping.java) |
| `MixinSystemReport` | `SystemReport` | [MixinSystemReport.java](vazkii/patchouli/mixin/client/MixinSystemReport.java) |
| `AccessorMultiBufferSource` | `AccessorMultiBufferSource` | [AccessorMultiBufferSource.java](vazkii/patchouli/mixin/client/AccessorMultiBufferSource.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `NeoForgePatchouliConfig` | — | [NeoForgePatchouliConfig.java](vazkii/patchouli/neoforge/common/NeoForgePatchouliConfig.java) |
| `PatchouliConfig` | — | [PatchouliConfig.java](vazkii/patchouli/common/base/PatchouliConfig.java) |
| `PatchouliConfigAccess` | — | [PatchouliConfigAccess.java](vazkii/patchouli/api/PatchouliConfigAccess.java) |
| `GuiButtonBookConfig` | — | [GuiButtonBookConfig.java](vazkii/patchouli/client/book/gui/button/GuiButtonBookConfig.java) |

## 按包浏览

- `vazkii.patchouli.api`（16 类）
- `vazkii.patchouli.api.stub`（3 类）
- `vazkii.patchouli.client.base`（4 类）
- `vazkii.patchouli.client.book`（14 类）
- `vazkii.patchouli.client.book.gui`（9 类）
- `vazkii.patchouli.client.book.gui.button`（10 类）
- `vazkii.patchouli.client.book.page`（17 类）
- `vazkii.patchouli.client.book.page.abstr`（4 类）
- `vazkii.patchouli.client.book.template`（5 类）
- `vazkii.patchouli.client.book.template.component`（9 类）
- `vazkii.patchouli.client.book.template.test`（3 类）
- `vazkii.patchouli.client.book.template.variable`（7 类）
- `vazkii.patchouli.client.book.text`（5 类）
- `vazkii.patchouli.client.gui`（2 类）
- `vazkii.patchouli.client.handler`（4 类）
- `vazkii.patchouli.client.jei`（1 类）
- `vazkii.patchouli.common.advancement`（2 类）
- `vazkii.patchouli.common.base`（3 类）
- `vazkii.patchouli.common.book`（3 类）
- `vazkii.patchouli.common.command`（1 类）
- `vazkii.patchouli.common.handler`（2 类）
- `vazkii.patchouli.common.item`（3 类）
- `vazkii.patchouli.common.multiblock`（8 类）
- `vazkii.patchouli.common.util`（4 类）
- `vazkii.patchouli.mixin`（2 类）
- `vazkii.patchouli.mixin.client`（8 类）
- `vazkii.patchouli.neoforge.client`（2 类）
- `vazkii.patchouli.neoforge.common`（2 类）
- `vazkii.patchouli.neoforge.network`（1 类）
- `vazkii.patchouli.neoforge.network.handler`（1 类）
- `vazkii.patchouli.neoforge.xplat`（2 类）
- `vazkii.patchouli.network`（2 类）
- `vazkii.patchouli.xplat`（3 类）
