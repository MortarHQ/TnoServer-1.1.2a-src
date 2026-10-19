# oneenoughitem（`oneenoughitem`）

> jar：`[矿物统一]OneEnoughItem.jar` ｜ 版本：`1.0.8` ｜ 作者：Flechazo, SnowMerry, zhenshiz, _yi_ran_
> 规模：**62 个类 / 20 个包 / 7232 行** ｜ 事件处理类 3 ｜ Mixin 5 ｜ 注册/管理类 5

**官方描述**：Mafuyu never like you.

**依赖**：`neoforge`、`oelib`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.mafuyu404.oneenoughitem.client.gui` | 6 | `ReplacementEditorScreen`、`BaseObjectSelectionScreen`、`ItemSelectionScreen` |
| `com.mafuyu404.oneenoughitem.api` | 6 | `DomainRegistry`、`EditableItem`、`DomainAdapter` |
| `com.mafuyu404.oneenoughitem.client.gui.components` | 6 | `ItemGridWidget`、`DataDisplayWidget`、`ScrollablePanel` |
| `com.mafuyu404.oneenoughitem.mixin` | 5 | `ItemStackMixin`、`ItemStackAccessor`、`CreativeModeTabMixin` |
| `com.mafuyu404.oneenoughitem.util` | 5 | `MixinUtils`、`JsonReloadMixinHelper`、`JECHCompat` |
| `com.mafuyu404.oneenoughitem.client.gui.util` | 5 | `GuiUtils`、`PathUtils`、`UiTooltipRenderHelper` |
| `com.mafuyu404.oneenoughitem.data` | 5 | `ItemReplacementValidator`、`Replacements`、`BaseReplacementValidator` |
| `com.mafuyu404.oneenoughitem.client.gui.cache` | 4 | `AbstractGlobalReplacementCache`、`EditorCache`、`ItemGlobalReplacementCache` |
| `com.mafuyu404.oneenoughitem.client.gui.editor` | 4 | `DataController`、`PanelsLayoutHelper`、`FileActions` |
| `com.mafuyu404.oneenoughitem.init` | 3 | `ItemReplacementCache`、`AbstractReplacementStrategy`、`OEIReplacementStrategy` |
| `com.mafuyu404.oneenoughitem.event` | 2 | `ClientEventHandler`、`ServerEventHandler` |
| `com.mafuyu404.oneenoughitem.client` | 2 | `ModKeyMappings`、`ClientContext` |
| `com.mafuyu404.oneenoughitem` | 2 | `Oneenoughitem`、`OEIClient` |
| `com.mafuyu404.oneenoughitem.client.gui.manager` | 1 | `ReplacementEditorManager` |
| `com.mafuyu404.oneenoughitem.event.base` | 1 | `AbstractReplacementEventHandler` |
| `com.mafuyu404.oneenoughitem.web` | 1 | `WebEditorServer` |
| `com.mafuyu404.oneenoughitem.api.adapter` | 1 | `ItemDomainAdapter` |
| `com.mafuyu404.oneenoughitem.init.config` | 1 | `OEIConfig` |
| `com.mafuyu404.oneenoughitem.api.adapter.ui` | 1 | `ItemReplacementUiAdapter` |
| `com.mafuyu404.oneenoughitem.init.access` | 1 | `CreativeModeTabIconRefresher` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 10 | 113 | [ClientEventHandler.java](com/mafuyu404/oneenoughitem/event/ClientEventHandler.java) |
| `ServerEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 8 | 77 | [ServerEventHandler.java](com/mafuyu404/oneenoughitem/event/ServerEventHandler.java) |
| `ModKeyMappings` | class | — | EventBusSubscriber | 1 | 20 | [ModKeyMappings.java](com/mafuyu404/oneenoughitem/client/ModKeyMappings.java) |
| `ReplacementEditorManager` | class | — | — | 47 | 556 | [ReplacementEditorManager.java](com/mafuyu404/oneenoughitem/client/gui/manager/ReplacementEditorManager.java) |
| `Oneenoughitem` | class | — | Mod | 0 | 31 | [Oneenoughitem.java](com/mafuyu404/oneenoughitem/Oneenoughitem.java) |
| `OEIClient` | class | — | Mod | 0 | 17 | [OEIClient.java](com/mafuyu404/oneenoughitem/OEIClient.java) |
| `ReplacementEditorScreen` | class | Screen | — | 44 | 658 | [ReplacementEditorScreen.java](com/mafuyu404/oneenoughitem/client/gui/ReplacementEditorScreen.java) |
| `ItemStackMixin` | class | — | Mixin | 7 | 134 | [ItemStackMixin.java](com/mafuyu404/oneenoughitem/mixin/ItemStackMixin.java) |
| `AbstractReplacementEventHandler` | class | — | — | 8 | 70 | [AbstractReplacementEventHandler.java](com/mafuyu404/oneenoughitem/event/base/AbstractReplacementEventHandler.java) |
| `ItemStackAccessor` | interface | — | Mixin | 0 | 13 | [ItemStackAccessor.java](com/mafuyu404/oneenoughitem/mixin/ItemStackAccessor.java) |
| `WebEditorServer` | class | — | — | 25 | 446 | [WebEditorServer.java](com/mafuyu404/oneenoughitem/web/WebEditorServer.java) |
| `DomainRegistry` | class | — | — | 9 | 55 | [DomainRegistry.java](com/mafuyu404/oneenoughitem/api/DomainRegistry.java) |
| `BaseObjectSelectionScreen` | class | Screen | — | 24 | 262 | [BaseObjectSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/BaseObjectSelectionScreen.java) |
| `AbstractGlobalReplacementCache` | class | BaseCache | — | 33 | 316 | [AbstractGlobalReplacementCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/AbstractGlobalReplacementCache.java) |
| `ItemReplacementCache` | class | — | — | 24 | 264 | [ItemReplacementCache.java](com/mafuyu404/oneenoughitem/init/ItemReplacementCache.java) |
| `CreativeModeTabMixin` | class | CreativeModeTabIconRefresher | Mixin | 2 | 107 | [CreativeModeTabMixin.java](com/mafuyu404/oneenoughitem/mixin/CreativeModeTabMixin.java) |
| `TagLoaderMixin` | class | — | Mixin | 1 | 139 | [TagLoaderMixin.java](com/mafuyu404/oneenoughitem/mixin/TagLoaderMixin.java) |
| `SimpleJsonResourceReloadListenerMixin` | class | — | Mixin | 0 | 48 | [SimpleJsonResourceReloadListenerMixin.java](com/mafuyu404/oneenoughitem/mixin/SimpleJsonResourceReloadListenerMixin.java) |
| `ItemDomainAdapter` | class | DomainAdapter | — | 20 | 143 | [ItemDomainAdapter.java](com/mafuyu404/oneenoughitem/api/adapter/ItemDomainAdapter.java) |
| `MixinUtils` | class | — | — | 20 | 302 | [MixinUtils.java](com/mafuyu404/oneenoughitem/util/MixinUtils.java) |
| `ItemSelectionScreen` | class | BaseObjectSelectionScreen | — | 10 | 116 | [ItemSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/ItemSelectionScreen.java) |
| `BaseTagSelectionScreen` | class | Screen | — | 12 | 176 | [BaseTagSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/BaseTagSelectionScreen.java) |
| `ItemTagSelectionScreen` | class | BaseTagSelectionScreen | — | 1 | 28 | [ItemTagSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/ItemTagSelectionScreen.java) |
| `GuiUtils` | class | — | — | 19 | 184 | [GuiUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/GuiUtils.java) |
| `OEIConfig` | record | — | — | 4 | 74 | [OEIConfig.java](com/mafuyu404/oneenoughitem/init/config/OEIConfig.java) |
| `FileSelectionScreen` | class | Screen | — | 8 | 129 | [FileSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/FileSelectionScreen.java) |
| `EditorCache` | class | BaseCache | — | 15 | 157 | [EditorCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/EditorCache.java) |
| `ItemGlobalReplacementCache` | class | AbstractGlobalReplacementCache | — | 9 | 85 | [ItemGlobalReplacementCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/ItemGlobalReplacementCache.java) |
| `ItemGridWidget` | class | AbstractWidget | — | 5 | 116 | [ItemGridWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/ItemGridWidget.java) |
| `PathUtils` | class | — | — | 11 | 145 | [PathUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/PathUtils.java) |
| `ItemReplacementValidator` | class | BaseReplacementValidator | — | 4 | 30 | [ItemReplacementValidator.java](com/mafuyu404/oneenoughitem/data/ItemReplacementValidator.java) |
| `EditableItem` | interface | — | — | 0 | 9 | [EditableItem.java](com/mafuyu404/oneenoughitem/api/EditableItem.java) |
| `ItemReplacementUiAdapter` | class | ReplacementUiAdapter | — | 4 | 39 | [ItemReplacementUiAdapter.java](com/mafuyu404/oneenoughitem/api/adapter/ui/ItemReplacementUiAdapter.java) |
| `JsonReloadMixinHelper` | class | — | — | 5 | 174 | [JsonReloadMixinHelper.java](com/mafuyu404/oneenoughitem/util/JsonReloadMixinHelper.java) |
| `JECHCompat` | class | — | — | 1 | 13 | [JECHCompat.java](com/mafuyu404/oneenoughitem/util/JECHCompat.java) |
| `Utils` | class | — | — | 6 | 100 | [Utils.java](com/mafuyu404/oneenoughitem/util/Utils.java) |
| `ClientContext` | class | — | — | 3 | 18 | [ClientContext.java](com/mafuyu404/oneenoughitem/client/ClientContext.java) |
| `BaseCache` | class | — | — | 14 | 161 | [BaseCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/BaseCache.java) |
| `DataController` | class | — | — | 8 | 59 | [DataController.java](com/mafuyu404/oneenoughitem/client/gui/editor/DataController.java) |
| `DataDisplayWidget` | class | AbstractWidget | — | 7 | 111 | [DataDisplayWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/DataDisplayWidget.java) |
| `ScrollablePanel` | class | AbstractWidget | — | 12 | 180 | [ScrollablePanel.java](com/mafuyu404/oneenoughitem/client/gui/components/ScrollablePanel.java) |
| `UiTooltipRenderHelper` | class | — | — | 5 | 83 | [UiTooltipRenderHelper.java](com/mafuyu404/oneenoughitem/client/gui/util/UiTooltipRenderHelper.java) |
| `ReplacementUtils` | class | — | — | 9 | 115 | [ReplacementUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/ReplacementUtils.java) |
| `Replacements` | record | — | DataDriven | 6 | 128 | [Replacements.java](com/mafuyu404/oneenoughitem/data/Replacements.java) |
| `BaseReplacementValidator` | class | ServerContextAware<Replacements> | — | 5 | 39 | [BaseReplacementValidator.java](com/mafuyu404/oneenoughitem/data/BaseReplacementValidator.java) |
| `ValidationStreams` | class | — | — | 9 | 75 | [ValidationStreams.java](com/mafuyu404/oneenoughitem/data/ValidationStreams.java) |
| `ReplacementControl` | class | — | — | 5 | 40 | [ReplacementControl.java](com/mafuyu404/oneenoughitem/util/ReplacementControl.java) |
| `PanelsLayoutHelper` | class | — | — | 2 | 85 | [PanelsLayoutHelper.java](com/mafuyu404/oneenoughitem/client/gui/editor/PanelsLayoutHelper.java) |
| `TagDisplayWidget` | class | AbstractWidget | — | 6 | 140 | [TagDisplayWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/TagDisplayWidget.java) |
| `FileListWidget` | class | ObjectSelectionList | — | 9 | 155 | [FileListWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/FileListWidget.java) |
| `TagListWidget` | class | ObjectSelectionList | — | 7 | 100 | [TagListWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/TagListWidget.java) |
| `ModUtils` | class | — | — | 2 | 17 | [ModUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/ModUtils.java) |
| `AbstractReplacementStrategy` | class | ReplacementStrategy | — | 6 | 48 | [AbstractReplacementStrategy.java](com/mafuyu404/oneenoughitem/init/AbstractReplacementStrategy.java) |
| `OEIReplacementStrategy` | class | AbstractReplacementStrategy | — | 6 | 44 | [OEIReplacementStrategy.java](com/mafuyu404/oneenoughitem/init/OEIReplacementStrategy.java) |
| `Validators` | class | — | — | 3 | 50 | [Validators.java](com/mafuyu404/oneenoughitem/data/Validators.java) |
| `DomainAdapter` | interface | — | — | 0 | 47 | [DomainAdapter.java](com/mafuyu404/oneenoughitem/api/DomainAdapter.java) |
| `ReplacementUiAdapter` | interface | — | — | 0 | 17 | [ReplacementUiAdapter.java](com/mafuyu404/oneenoughitem/api/ReplacementUiAdapter.java) |
| `DomainRuntimeCache` | interface | — | — | 0 | 17 | [DomainRuntimeCache.java](com/mafuyu404/oneenoughitem/api/DomainRuntimeCache.java) |
| `ReplacementStrategy` | interface | — | — | 0 | 24 | [ReplacementStrategy.java](com/mafuyu404/oneenoughitem/api/ReplacementStrategy.java) |
| `FileActions` | class | — | — | 4 | 55 | [FileActions.java](com/mafuyu404/oneenoughitem/client/gui/editor/FileActions.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientEventHandler` | 2 | `com.mafuyu404.oneenoughitem.event` | [ClientEventHandler.java](com/mafuyu404/oneenoughitem/event/ClientEventHandler.java) |
| `ServerEventHandler` | 2 | `com.mafuyu404.oneenoughitem.event` | [ServerEventHandler.java](com/mafuyu404/oneenoughitem/event/ServerEventHandler.java) |
| `ModKeyMappings` | 1 | `com.mafuyu404.oneenoughitem.client` | [ModKeyMappings.java](com/mafuyu404/oneenoughitem/client/ModKeyMappings.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](com/mafuyu404/oneenoughitem/mixin/ItemStackMixin.java) |
| `ItemStackAccessor` | `ItemStackAccessor` | [ItemStackAccessor.java](com/mafuyu404/oneenoughitem/mixin/ItemStackAccessor.java) |
| `CreativeModeTabMixin` | `CreativeModeTab` | [CreativeModeTabMixin.java](com/mafuyu404/oneenoughitem/mixin/CreativeModeTabMixin.java) |
| `TagLoaderMixin` | `TagLoader` | [TagLoaderMixin.java](com/mafuyu404/oneenoughitem/mixin/TagLoaderMixin.java) |
| `SimpleJsonResourceReloadListenerMixin` | `SimpleJsonResourceReloadListener` | [SimpleJsonResourceReloadListenerMixin.java](com/mafuyu404/oneenoughitem/mixin/SimpleJsonResourceReloadListenerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `OEIConfig` | — | [OEIConfig.java](com/mafuyu404/oneenoughitem/init/config/OEIConfig.java) |

## 全部类（62）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 10 | 113 | [ClientEventHandler.java](com/mafuyu404/oneenoughitem/event/ClientEventHandler.java) |
| `ServerEventHandler` | class | AbstractReplacementEventHandler | EventBusSubscriber | 8 | 77 | [ServerEventHandler.java](com/mafuyu404/oneenoughitem/event/ServerEventHandler.java) |
| `ModKeyMappings` | class | — | EventBusSubscriber | 1 | 20 | [ModKeyMappings.java](com/mafuyu404/oneenoughitem/client/ModKeyMappings.java) |
| `ReplacementEditorManager` | class | — | — | 47 | 556 | [ReplacementEditorManager.java](com/mafuyu404/oneenoughitem/client/gui/manager/ReplacementEditorManager.java) |
| `Oneenoughitem` | class | — | Mod | 0 | 31 | [Oneenoughitem.java](com/mafuyu404/oneenoughitem/Oneenoughitem.java) |
| `OEIClient` | class | — | Mod | 0 | 17 | [OEIClient.java](com/mafuyu404/oneenoughitem/OEIClient.java) |
| `ReplacementEditorScreen` | class | Screen | — | 44 | 658 | [ReplacementEditorScreen.java](com/mafuyu404/oneenoughitem/client/gui/ReplacementEditorScreen.java) |
| `ItemStackMixin` | class | — | Mixin | 7 | 134 | [ItemStackMixin.java](com/mafuyu404/oneenoughitem/mixin/ItemStackMixin.java) |
| `AbstractReplacementEventHandler` | class | — | — | 8 | 70 | [AbstractReplacementEventHandler.java](com/mafuyu404/oneenoughitem/event/base/AbstractReplacementEventHandler.java) |
| `ItemStackAccessor` | interface | — | Mixin | 0 | 13 | [ItemStackAccessor.java](com/mafuyu404/oneenoughitem/mixin/ItemStackAccessor.java) |
| `WebEditorServer` | class | — | — | 25 | 446 | [WebEditorServer.java](com/mafuyu404/oneenoughitem/web/WebEditorServer.java) |
| `DomainRegistry` | class | — | — | 9 | 55 | [DomainRegistry.java](com/mafuyu404/oneenoughitem/api/DomainRegistry.java) |
| `BaseObjectSelectionScreen` | class | Screen | — | 24 | 262 | [BaseObjectSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/BaseObjectSelectionScreen.java) |
| `AbstractGlobalReplacementCache` | class | BaseCache | — | 33 | 316 | [AbstractGlobalReplacementCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/AbstractGlobalReplacementCache.java) |
| `ItemReplacementCache` | class | — | — | 24 | 264 | [ItemReplacementCache.java](com/mafuyu404/oneenoughitem/init/ItemReplacementCache.java) |
| `CreativeModeTabMixin` | class | CreativeModeTabIconRefresher | Mixin | 2 | 107 | [CreativeModeTabMixin.java](com/mafuyu404/oneenoughitem/mixin/CreativeModeTabMixin.java) |
| `TagLoaderMixin` | class | — | Mixin | 1 | 139 | [TagLoaderMixin.java](com/mafuyu404/oneenoughitem/mixin/TagLoaderMixin.java) |
| `SimpleJsonResourceReloadListenerMixin` | class | — | Mixin | 0 | 48 | [SimpleJsonResourceReloadListenerMixin.java](com/mafuyu404/oneenoughitem/mixin/SimpleJsonResourceReloadListenerMixin.java) |
| `ItemDomainAdapter` | class | DomainAdapter | — | 20 | 143 | [ItemDomainAdapter.java](com/mafuyu404/oneenoughitem/api/adapter/ItemDomainAdapter.java) |
| `MixinUtils` | class | — | — | 20 | 302 | [MixinUtils.java](com/mafuyu404/oneenoughitem/util/MixinUtils.java) |
| `ItemSelectionScreen` | class | BaseObjectSelectionScreen | — | 10 | 116 | [ItemSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/ItemSelectionScreen.java) |
| `BaseTagSelectionScreen` | class | Screen | — | 12 | 176 | [BaseTagSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/BaseTagSelectionScreen.java) |
| `ItemTagSelectionScreen` | class | BaseTagSelectionScreen | — | 1 | 28 | [ItemTagSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/ItemTagSelectionScreen.java) |
| `GuiUtils` | class | — | — | 19 | 184 | [GuiUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/GuiUtils.java) |
| `OEIConfig` | record | — | — | 4 | 74 | [OEIConfig.java](com/mafuyu404/oneenoughitem/init/config/OEIConfig.java) |
| `FileSelectionScreen` | class | Screen | — | 8 | 129 | [FileSelectionScreen.java](com/mafuyu404/oneenoughitem/client/gui/FileSelectionScreen.java) |
| `EditorCache` | class | BaseCache | — | 15 | 157 | [EditorCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/EditorCache.java) |
| `ItemGlobalReplacementCache` | class | AbstractGlobalReplacementCache | — | 9 | 85 | [ItemGlobalReplacementCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/ItemGlobalReplacementCache.java) |
| `ItemGridWidget` | class | AbstractWidget | — | 5 | 116 | [ItemGridWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/ItemGridWidget.java) |
| `PathUtils` | class | — | — | 11 | 145 | [PathUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/PathUtils.java) |
| `ItemReplacementValidator` | class | BaseReplacementValidator | — | 4 | 30 | [ItemReplacementValidator.java](com/mafuyu404/oneenoughitem/data/ItemReplacementValidator.java) |
| `EditableItem` | interface | — | — | 0 | 9 | [EditableItem.java](com/mafuyu404/oneenoughitem/api/EditableItem.java) |
| `ItemReplacementUiAdapter` | class | ReplacementUiAdapter | — | 4 | 39 | [ItemReplacementUiAdapter.java](com/mafuyu404/oneenoughitem/api/adapter/ui/ItemReplacementUiAdapter.java) |
| `JsonReloadMixinHelper` | class | — | — | 5 | 174 | [JsonReloadMixinHelper.java](com/mafuyu404/oneenoughitem/util/JsonReloadMixinHelper.java) |
| `JECHCompat` | class | — | — | 1 | 13 | [JECHCompat.java](com/mafuyu404/oneenoughitem/util/JECHCompat.java) |
| `Utils` | class | — | — | 6 | 100 | [Utils.java](com/mafuyu404/oneenoughitem/util/Utils.java) |
| `ClientContext` | class | — | — | 3 | 18 | [ClientContext.java](com/mafuyu404/oneenoughitem/client/ClientContext.java) |
| `BaseCache` | class | — | — | 14 | 161 | [BaseCache.java](com/mafuyu404/oneenoughitem/client/gui/cache/BaseCache.java) |
| `DataController` | class | — | — | 8 | 59 | [DataController.java](com/mafuyu404/oneenoughitem/client/gui/editor/DataController.java) |
| `DataDisplayWidget` | class | AbstractWidget | — | 7 | 111 | [DataDisplayWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/DataDisplayWidget.java) |
| `ScrollablePanel` | class | AbstractWidget | — | 12 | 180 | [ScrollablePanel.java](com/mafuyu404/oneenoughitem/client/gui/components/ScrollablePanel.java) |
| `UiTooltipRenderHelper` | class | — | — | 5 | 83 | [UiTooltipRenderHelper.java](com/mafuyu404/oneenoughitem/client/gui/util/UiTooltipRenderHelper.java) |
| `ReplacementUtils` | class | — | — | 9 | 115 | [ReplacementUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/ReplacementUtils.java) |
| `Replacements` | record | — | DataDriven | 6 | 128 | [Replacements.java](com/mafuyu404/oneenoughitem/data/Replacements.java) |
| `BaseReplacementValidator` | class | ServerContextAware<Replacements> | — | 5 | 39 | [BaseReplacementValidator.java](com/mafuyu404/oneenoughitem/data/BaseReplacementValidator.java) |
| `ValidationStreams` | class | — | — | 9 | 75 | [ValidationStreams.java](com/mafuyu404/oneenoughitem/data/ValidationStreams.java) |
| `ReplacementControl` | class | — | — | 5 | 40 | [ReplacementControl.java](com/mafuyu404/oneenoughitem/util/ReplacementControl.java) |
| `PanelsLayoutHelper` | class | — | — | 2 | 85 | [PanelsLayoutHelper.java](com/mafuyu404/oneenoughitem/client/gui/editor/PanelsLayoutHelper.java) |
| `TagDisplayWidget` | class | AbstractWidget | — | 6 | 140 | [TagDisplayWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/TagDisplayWidget.java) |
| `FileListWidget` | class | ObjectSelectionList | — | 9 | 155 | [FileListWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/FileListWidget.java) |
| `TagListWidget` | class | ObjectSelectionList | — | 7 | 100 | [TagListWidget.java](com/mafuyu404/oneenoughitem/client/gui/components/TagListWidget.java) |
| `ModUtils` | class | — | — | 2 | 17 | [ModUtils.java](com/mafuyu404/oneenoughitem/client/gui/util/ModUtils.java) |
| `AbstractReplacementStrategy` | class | ReplacementStrategy | — | 6 | 48 | [AbstractReplacementStrategy.java](com/mafuyu404/oneenoughitem/init/AbstractReplacementStrategy.java) |
| `OEIReplacementStrategy` | class | AbstractReplacementStrategy | — | 6 | 44 | [OEIReplacementStrategy.java](com/mafuyu404/oneenoughitem/init/OEIReplacementStrategy.java) |
| `Validators` | class | — | — | 3 | 50 | [Validators.java](com/mafuyu404/oneenoughitem/data/Validators.java) |
| `DomainAdapter` | interface | — | — | 0 | 47 | [DomainAdapter.java](com/mafuyu404/oneenoughitem/api/DomainAdapter.java) |
| `ReplacementUiAdapter` | interface | — | — | 0 | 17 | [ReplacementUiAdapter.java](com/mafuyu404/oneenoughitem/api/ReplacementUiAdapter.java) |
| `DomainRuntimeCache` | interface | — | — | 0 | 17 | [DomainRuntimeCache.java](com/mafuyu404/oneenoughitem/api/DomainRuntimeCache.java) |
| `ReplacementStrategy` | interface | — | — | 0 | 24 | [ReplacementStrategy.java](com/mafuyu404/oneenoughitem/api/ReplacementStrategy.java) |
| `FileActions` | class | — | — | 4 | 55 | [FileActions.java](com/mafuyu404/oneenoughitem/client/gui/editor/FileActions.java) |
| `ObjectDropdownController` | class | — | — | 2 | 72 | [ObjectDropdownController.java](com/mafuyu404/oneenoughitem/client/gui/editor/ObjectDropdownController.java) |
| `CreativeModeTabIconRefresher` | interface | — | — | 0 | 6 | [CreativeModeTabIconRefresher.java](com/mafuyu404/oneenoughitem/init/access/CreativeModeTabIconRefresher.java) |
