# FTB Filter System（`ftbfiltersystem`）

> jar：`[ftbq附属]ftbfiltersystem.jar` ｜ 版本：`21.1.4` ｜ 作者：FTB Team
> 规模：**74 个类 / 21 个包 / 4728 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 6

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`、`jei`、`ftbxmodcompat`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbfiltersystem.client.gui` | 13 | `ItemConfigScreen`、`ItemTagConfigScreen`、`FilterScreen` |
| `dev.ftb.mods.ftbfiltersystem.filter` | 11 | `ItemTagFilter`、`ItemFilter`、`BlockFilter` |
| `dev.ftb.mods.ftbfiltersystem.api.filter` | 5 | `AbstractCompoundFilter`、`AbstractComparisonFilter`、`AbstractSmartFilter` |
| `dev.ftb.mods.ftbfiltersystem.util` | 5 | `LRUCache`、`PlatformUtil`、`FilterParser` |
| `dev.ftb.mods.ftbfiltersystem.filter.compound` | 5 | `RootFilter`、`OrFilter`、`NotFilter` |
| `dev.ftb.mods.ftbfiltersystem.api` | 4 | `FTBFilterSystemRegistry`、`FTBFilterSystemAPI`、`NumericComparison` |
| `dev.ftb.mods.ftbfiltersystem.integration.jei` | 3 | `FFSScreenHandler`、`FFSGhostHandler`、`JEIIntegration` |
| `dev.ftb.mods.ftbfiltersystem.api.client.gui` | 3 | `AbstractFilterConfigScreen`、`AbstractComparisonConfigScreen`、`AbstractFilterScreen` |
| `dev.ftb.mods.ftbfiltersystem.registry` | 3 | `FilterRegistry`、`ModItems`、`ModDataComponents` |
| `dev.ftb.mods.ftbfiltersystem` | 3 | `FilterSystemCommands`、`FilterSystemAPIImpl`、`FTBFilterSystem` |
| `dev.ftb.mods.ftbfiltersystem.client` | 3 | `FTBFilterSystemClient`、`GuiUtil`、`SelectionPanel` |
| `dev.ftb.mods.ftbfiltersystem.client.gui.widget` | 3 | `ItemWidget`、`CustomSelectionList`、`CustomStringWidget` |
| `dev.ftb.mods.ftbfiltersystem.api.client` | 3 | `FilterScreenFactory`、`FTBFilterSystemClientAPI`、`Textures` |
| `dev.ftb.mods.ftbfiltersystem.api.event` | 2 | `FilterRegistrationEvent`、`CustomFilterEvent` |
| `dev.ftb.mods.ftbfiltersystem.network` | 2 | `FTBFilterSystemNet`、`SyncFilterMessage` |
| `dev.ftb.mods.ftbfiltersystem.api.event.client` | 1 | `ClientFilterRegistrationEvent` |
| `dev.ftb.mods.ftbfiltersystem.neoforge` | 1 | `FTBFilterSystemNeoForge` |
| `dev.ftb.mods.ftbfiltersystem.registry.item` | 1 | `SmartFilterItem` |
| `dev.ftb.mods.ftbfiltersystem.util.neoforge` | 1 | `PlatformUtilImpl` |
| `architectury_inject_FTBFilterSystem121_common_68cfb66ee56442ffb7c4f9f0b64226e2_4febfdcc118a4cb683d4ac73623201b7208fee2f330e9ec5b17726fd247ca809ftbfiltersystem2114devjar` | 1 | `PlatformMethods` |
| `dev.ftb.mods.ftbfiltersystem.api.client.gui.widget` | 1 | `CustomCheckbox` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ItemConfigScreen` | class | AbstractFilterConfigScreen | — | 21 | 307 | [ItemConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemConfigScreen.java) |
| `ClientFilterRegistrationEvent` | interface | — | — | 0 | 12 | [ClientFilterRegistrationEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/client/ClientFilterRegistrationEvent.java) |
| `FTBFilterSystemNeoForge` | class | — | Mod | 0 | 12 | [FTBFilterSystemNeoForge.java](dev/ftb/mods/ftbfiltersystem/neoforge/FTBFilterSystemNeoForge.java) |
| `ItemTagConfigScreen` | class | AbstractFilterConfigScreen | — | 12 | 142 | [ItemTagConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemTagConfigScreen.java) |
| `FilterScreen` | class | AbstractFilterScreen | — | 43 | 573 | [FilterScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/FilterScreen.java) |
| `FilterRegistrationEvent` | interface | — | — | 0 | 12 | [FilterRegistrationEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/FilterRegistrationEvent.java) |
| `AbstractItemEditorConfigScreen` | class | AbstractFilterConfigScreen | — | 9 | 115 | [AbstractItemEditorConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/AbstractItemEditorConfigScreen.java) |
| `ModConfigScreen` | class | AbstractFilterConfigScreen | — | 15 | 159 | [ModConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ModConfigScreen.java) |
| `FFSScreenHandler` | class | IScreenHandler<T> | — | 8 | 45 | [FFSScreenHandler.java](dev/ftb/mods/ftbfiltersystem/integration/jei/FFSScreenHandler.java) |
| `AbstractFilterConfigScreen` | class | Screen | — | 13 | 138 | [AbstractFilterConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractFilterConfigScreen.java) |
| `AbstractComparisonConfigScreen` | class | AbstractFilterConfigScreen | — | 7 | 120 | [AbstractComparisonConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractComparisonConfigScreen.java) |
| `FilterRegistry` | class | FTBFilterSystemRegistry | — | 7 | 60 | [FilterRegistry.java](dev/ftb/mods/ftbfiltersystem/registry/FilterRegistry.java) |
| `ExpressionConfigScreen` | class | AbstractItemEditorConfigScreen | — | 6 | 75 | [ExpressionConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ExpressionConfigScreen.java) |
| `ComponentConfigScreen` | class | AbstractItemEditorConfigScreen | — | 7 | 88 | [ComponentConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ComponentConfigScreen.java) |
| `FilterSystemCommands` | class | — | — | 10 | 218 | [FilterSystemCommands.java](dev/ftb/mods/ftbfiltersystem/FilterSystemCommands.java) |
| `FTBFilterSystemRegistry` | interface | — | — | 0 | 14 | [FTBFilterSystemRegistry.java](dev/ftb/mods/ftbfiltersystem/api/FTBFilterSystemRegistry.java) |
| `StackSizeConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [StackSizeConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/StackSizeConfigScreen.java) |
| `MaxCountConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [MaxCountConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/MaxCountConfigScreen.java) |
| `FoodValueConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [FoodValueConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/FoodValueConfigScreen.java) |
| `DurabilityConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [DurabilityConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/DurabilityConfigScreen.java) |
| `CustomConfigScreen` | class | AbstractFilterConfigScreen | — | 4 | 62 | [CustomConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/CustomConfigScreen.java) |
| `FFSGhostHandler` | class | IGhostIngredientHandler<S> | — | 4 | 43 | [FFSGhostHandler.java](dev/ftb/mods/ftbfiltersystem/integration/jei/FFSGhostHandler.java) |
| `FTBFilterSystemClient` | enum | FTBFilterSystemClientAPI | — | 10 | 122 | [FTBFilterSystemClient.java](dev/ftb/mods/ftbfiltersystem/client/FTBFilterSystemClient.java) |
| `ItemTagFilter` | class | AbstractSmartFilter | — | 5 | 55 | [ItemTagFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ItemTagFilter.java) |
| `ItemFilter` | class | AbstractSmartFilter | — | 5 | 59 | [ItemFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ItemFilter.java) |
| `BlockFilter` | class | AbstractSmartFilter | — | 5 | 42 | [BlockFilter.java](dev/ftb/mods/ftbfiltersystem/filter/BlockFilter.java) |
| `CustomFilterEvent` | interface | — | — | 0 | 14 | [CustomFilterEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/CustomFilterEvent.java) |
| `SmartFilterItem` | class | Item | — | 5 | 66 | [SmartFilterItem.java](dev/ftb/mods/ftbfiltersystem/registry/item/SmartFilterItem.java) |
| `ItemWidget` | class | AbstractWidget | — | 7 | 75 | [ItemWidget.java](dev/ftb/mods/ftbfiltersystem/client/gui/widget/ItemWidget.java) |
| `FilterSystemAPIImpl` | enum | FTBFilterSystemAPI.API | — | 11 | 92 | [FilterSystemAPIImpl.java](dev/ftb/mods/ftbfiltersystem/FilterSystemAPIImpl.java) |
| `ComponentFilter` | class | AbstractSmartFilter | — | 10 | 104 | [ComponentFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ComponentFilter.java) |
| `JEIIntegration` | class | IModPlugin | JeiPlugin | 2 | 29 | [JEIIntegration.java](dev/ftb/mods/ftbfiltersystem/integration/jei/JEIIntegration.java) |
| `FilterScreenFactory` | interface | — | — | 0 | 11 | [FilterScreenFactory.java](dev/ftb/mods/ftbfiltersystem/api/client/FilterScreenFactory.java) |
| `FTBFilterSystemClientAPI` | interface | — | — | 0 | 9 | [FTBFilterSystemClientAPI.java](dev/ftb/mods/ftbfiltersystem/api/client/FTBFilterSystemClientAPI.java) |
| `AbstractFilterScreen` | class | Screen | NonExtendable | 3 | 20 | [AbstractFilterScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractFilterScreen.java) |
| `ModItems` | class | — | — | 2 | 26 | [ModItems.java](dev/ftb/mods/ftbfiltersystem/registry/ModItems.java) |
| `GuiUtil` | class | — | — | 8 | 103 | [GuiUtil.java](dev/ftb/mods/ftbfiltersystem/client/GuiUtil.java) |
| `CustomSelectionList` | class | — | — | 10 | 70 | [CustomSelectionList.java](dev/ftb/mods/ftbfiltersystem/client/gui/widget/CustomSelectionList.java) |
| `CustomFilter` | class | AbstractSmartFilter | — | 6 | 53 | [CustomFilter.java](dev/ftb/mods/ftbfiltersystem/filter/CustomFilter.java) |
| `ExpressionFilter` | class | AbstractSmartFilter | — | 7 | 63 | [ExpressionFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ExpressionFilter.java) |
| `FTBFilterSystemAPI` | class | — | — | 7 | 97 | [FTBFilterSystemAPI.java](dev/ftb/mods/ftbfiltersystem/api/FTBFilterSystemAPI.java) |
| `NumericComparison` | record | IntPredicate | — | 7 | 100 | [NumericComparison.java](dev/ftb/mods/ftbfiltersystem/api/NumericComparison.java) |
| `AbstractCompoundFilter` | class | AbstractSmartFilter | — | 5 | 62 | [AbstractCompoundFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/AbstractCompoundFilter.java) |
| `AbstractComparisonFilter` | class | AbstractSmartFilter | — | 5 | 34 | [AbstractComparisonFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/AbstractComparisonFilter.java) |
| `ModDataComponents` | class | — | — | 0 | 17 | [ModDataComponents.java](dev/ftb/mods/ftbfiltersystem/registry/ModDataComponents.java) |
| `LRUCache` | class | — | — | 5 | 64 | [LRUCache.java](dev/ftb/mods/ftbfiltersystem/util/LRUCache.java) |
| `PlatformUtil` | class | — | — | 1 | 15 | [PlatformUtil.java](dev/ftb/mods/ftbfiltersystem/util/PlatformUtil.java) |
| `FilterParser` | class | — | — | 5 | 93 | [FilterParser.java](dev/ftb/mods/ftbfiltersystem/util/FilterParser.java) |
| `NBTUtil` | class | — | — | 1 | 36 | [NBTUtil.java](dev/ftb/mods/ftbfiltersystem/util/NBTUtil.java) |
| `PlatformUtilImpl` | class | — | — | 1 | 10 | [PlatformUtilImpl.java](dev/ftb/mods/ftbfiltersystem/util/neoforge/PlatformUtilImpl.java) |
| `SelectionPanel` | class | — | — | 8 | 127 | [SelectionPanel.java](dev/ftb/mods/ftbfiltersystem/client/SelectionPanel.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBFilterSystem121_common_68cfb66ee56442ffb7c4f9f0b64226e2_4febfdcc118a4cb683d4ac73623201b7208fee2f330e9ec5b17726fd247ca809ftbfiltersystem2114devjar/PlatformMethods.java) |
| `FTBFilterSystem` | class | — | — | 3 | 78 | [FTBFilterSystem.java](dev/ftb/mods/ftbfiltersystem/FTBFilterSystem.java) |
| `FTBFilterSystemNet` | interface | — | — | 2 | 24 | [FTBFilterSystemNet.java](dev/ftb/mods/ftbfiltersystem/network/FTBFilterSystemNet.java) |
| `SyncFilterMessage` | record | CustomPacketPayload | — | 2 | 55 | [SyncFilterMessage.java](dev/ftb/mods/ftbfiltersystem/network/SyncFilterMessage.java) |
| `FoodValueFilter` | class | AbstractComparisonFilter | — | 3 | 38 | [FoodValueFilter.java](dev/ftb/mods/ftbfiltersystem/filter/FoodValueFilter.java) |
| `DurabilityFilter` | class | AbstractComparisonFilter | — | 4 | 46 | [DurabilityFilter.java](dev/ftb/mods/ftbfiltersystem/filter/DurabilityFilter.java) |
| `StackSizeFilter` | class | AbstractComparisonFilter | — | 3 | 36 | [StackSizeFilter.java](dev/ftb/mods/ftbfiltersystem/filter/StackSizeFilter.java) |
| `MaxStackSizeFilter` | class | AbstractComparisonFilter | — | 3 | 36 | [MaxStackSizeFilter.java](dev/ftb/mods/ftbfiltersystem/filter/MaxStackSizeFilter.java) |
| `ModFilter` | class | AbstractSmartFilter | — | 4 | 41 | [ModFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ModFilter.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ItemConfigScreen` | — | [ItemConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemConfigScreen.java) |
| `ItemTagConfigScreen` | — | [ItemTagConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemTagConfigScreen.java) |
| `AbstractItemEditorConfigScreen` | — | [AbstractItemEditorConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/AbstractItemEditorConfigScreen.java) |
| `ModConfigScreen` | — | [ModConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ModConfigScreen.java) |
| `AbstractFilterConfigScreen` | — | [AbstractFilterConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractFilterConfigScreen.java) |
| `AbstractComparisonConfigScreen` | — | [AbstractComparisonConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractComparisonConfigScreen.java) |
| `ExpressionConfigScreen` | — | [ExpressionConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ExpressionConfigScreen.java) |
| `ComponentConfigScreen` | — | [ComponentConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ComponentConfigScreen.java) |
| `StackSizeConfigScreen` | — | [StackSizeConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/StackSizeConfigScreen.java) |
| `MaxCountConfigScreen` | — | [MaxCountConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/MaxCountConfigScreen.java) |
| `FoodValueConfigScreen` | — | [FoodValueConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/FoodValueConfigScreen.java) |
| `DurabilityConfigScreen` | — | [DurabilityConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/DurabilityConfigScreen.java) |
| `CustomConfigScreen` | — | [CustomConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/CustomConfigScreen.java) |

## 全部类（74）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ItemConfigScreen` | class | AbstractFilterConfigScreen | — | 21 | 307 | [ItemConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemConfigScreen.java) |
| `ClientFilterRegistrationEvent` | interface | — | — | 0 | 12 | [ClientFilterRegistrationEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/client/ClientFilterRegistrationEvent.java) |
| `FTBFilterSystemNeoForge` | class | — | Mod | 0 | 12 | [FTBFilterSystemNeoForge.java](dev/ftb/mods/ftbfiltersystem/neoforge/FTBFilterSystemNeoForge.java) |
| `ItemTagConfigScreen` | class | AbstractFilterConfigScreen | — | 12 | 142 | [ItemTagConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ItemTagConfigScreen.java) |
| `FilterScreen` | class | AbstractFilterScreen | — | 43 | 573 | [FilterScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/FilterScreen.java) |
| `FilterRegistrationEvent` | interface | — | — | 0 | 12 | [FilterRegistrationEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/FilterRegistrationEvent.java) |
| `AbstractItemEditorConfigScreen` | class | AbstractFilterConfigScreen | — | 9 | 115 | [AbstractItemEditorConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/AbstractItemEditorConfigScreen.java) |
| `ModConfigScreen` | class | AbstractFilterConfigScreen | — | 15 | 159 | [ModConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ModConfigScreen.java) |
| `FFSScreenHandler` | class | IScreenHandler<T> | — | 8 | 45 | [FFSScreenHandler.java](dev/ftb/mods/ftbfiltersystem/integration/jei/FFSScreenHandler.java) |
| `AbstractFilterConfigScreen` | class | Screen | — | 13 | 138 | [AbstractFilterConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractFilterConfigScreen.java) |
| `AbstractComparisonConfigScreen` | class | AbstractFilterConfigScreen | — | 7 | 120 | [AbstractComparisonConfigScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractComparisonConfigScreen.java) |
| `FilterRegistry` | class | FTBFilterSystemRegistry | — | 7 | 60 | [FilterRegistry.java](dev/ftb/mods/ftbfiltersystem/registry/FilterRegistry.java) |
| `ExpressionConfigScreen` | class | AbstractItemEditorConfigScreen | — | 6 | 75 | [ExpressionConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ExpressionConfigScreen.java) |
| `ComponentConfigScreen` | class | AbstractItemEditorConfigScreen | — | 7 | 88 | [ComponentConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/ComponentConfigScreen.java) |
| `FilterSystemCommands` | class | — | — | 10 | 218 | [FilterSystemCommands.java](dev/ftb/mods/ftbfiltersystem/FilterSystemCommands.java) |
| `FTBFilterSystemRegistry` | interface | — | — | 0 | 14 | [FTBFilterSystemRegistry.java](dev/ftb/mods/ftbfiltersystem/api/FTBFilterSystemRegistry.java) |
| `StackSizeConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [StackSizeConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/StackSizeConfigScreen.java) |
| `MaxCountConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [MaxCountConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/MaxCountConfigScreen.java) |
| `FoodValueConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [FoodValueConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/FoodValueConfigScreen.java) |
| `DurabilityConfigScreen` | class | AbstractComparisonConfigScreen | — | 0 | 12 | [DurabilityConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/DurabilityConfigScreen.java) |
| `CustomConfigScreen` | class | AbstractFilterConfigScreen | — | 4 | 62 | [CustomConfigScreen.java](dev/ftb/mods/ftbfiltersystem/client/gui/CustomConfigScreen.java) |
| `FFSGhostHandler` | class | IGhostIngredientHandler<S> | — | 4 | 43 | [FFSGhostHandler.java](dev/ftb/mods/ftbfiltersystem/integration/jei/FFSGhostHandler.java) |
| `FTBFilterSystemClient` | enum | FTBFilterSystemClientAPI | — | 10 | 122 | [FTBFilterSystemClient.java](dev/ftb/mods/ftbfiltersystem/client/FTBFilterSystemClient.java) |
| `ItemTagFilter` | class | AbstractSmartFilter | — | 5 | 55 | [ItemTagFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ItemTagFilter.java) |
| `ItemFilter` | class | AbstractSmartFilter | — | 5 | 59 | [ItemFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ItemFilter.java) |
| `BlockFilter` | class | AbstractSmartFilter | — | 5 | 42 | [BlockFilter.java](dev/ftb/mods/ftbfiltersystem/filter/BlockFilter.java) |
| `CustomFilterEvent` | interface | — | — | 0 | 14 | [CustomFilterEvent.java](dev/ftb/mods/ftbfiltersystem/api/event/CustomFilterEvent.java) |
| `SmartFilterItem` | class | Item | — | 5 | 66 | [SmartFilterItem.java](dev/ftb/mods/ftbfiltersystem/registry/item/SmartFilterItem.java) |
| `ItemWidget` | class | AbstractWidget | — | 7 | 75 | [ItemWidget.java](dev/ftb/mods/ftbfiltersystem/client/gui/widget/ItemWidget.java) |
| `FilterSystemAPIImpl` | enum | FTBFilterSystemAPI.API | — | 11 | 92 | [FilterSystemAPIImpl.java](dev/ftb/mods/ftbfiltersystem/FilterSystemAPIImpl.java) |
| `ComponentFilter` | class | AbstractSmartFilter | — | 10 | 104 | [ComponentFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ComponentFilter.java) |
| `JEIIntegration` | class | IModPlugin | JeiPlugin | 2 | 29 | [JEIIntegration.java](dev/ftb/mods/ftbfiltersystem/integration/jei/JEIIntegration.java) |
| `FilterScreenFactory` | interface | — | — | 0 | 11 | [FilterScreenFactory.java](dev/ftb/mods/ftbfiltersystem/api/client/FilterScreenFactory.java) |
| `FTBFilterSystemClientAPI` | interface | — | — | 0 | 9 | [FTBFilterSystemClientAPI.java](dev/ftb/mods/ftbfiltersystem/api/client/FTBFilterSystemClientAPI.java) |
| `AbstractFilterScreen` | class | Screen | NonExtendable | 3 | 20 | [AbstractFilterScreen.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/AbstractFilterScreen.java) |
| `ModItems` | class | — | — | 2 | 26 | [ModItems.java](dev/ftb/mods/ftbfiltersystem/registry/ModItems.java) |
| `GuiUtil` | class | — | — | 8 | 103 | [GuiUtil.java](dev/ftb/mods/ftbfiltersystem/client/GuiUtil.java) |
| `CustomSelectionList` | class | — | — | 10 | 70 | [CustomSelectionList.java](dev/ftb/mods/ftbfiltersystem/client/gui/widget/CustomSelectionList.java) |
| `CustomFilter` | class | AbstractSmartFilter | — | 6 | 53 | [CustomFilter.java](dev/ftb/mods/ftbfiltersystem/filter/CustomFilter.java) |
| `ExpressionFilter` | class | AbstractSmartFilter | — | 7 | 63 | [ExpressionFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ExpressionFilter.java) |
| `FTBFilterSystemAPI` | class | — | — | 7 | 97 | [FTBFilterSystemAPI.java](dev/ftb/mods/ftbfiltersystem/api/FTBFilterSystemAPI.java) |
| `NumericComparison` | record | IntPredicate | — | 7 | 100 | [NumericComparison.java](dev/ftb/mods/ftbfiltersystem/api/NumericComparison.java) |
| `AbstractCompoundFilter` | class | AbstractSmartFilter | — | 5 | 62 | [AbstractCompoundFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/AbstractCompoundFilter.java) |
| `AbstractComparisonFilter` | class | AbstractSmartFilter | — | 5 | 34 | [AbstractComparisonFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/AbstractComparisonFilter.java) |
| `ModDataComponents` | class | — | — | 0 | 17 | [ModDataComponents.java](dev/ftb/mods/ftbfiltersystem/registry/ModDataComponents.java) |
| `LRUCache` | class | — | — | 5 | 64 | [LRUCache.java](dev/ftb/mods/ftbfiltersystem/util/LRUCache.java) |
| `PlatformUtil` | class | — | — | 1 | 15 | [PlatformUtil.java](dev/ftb/mods/ftbfiltersystem/util/PlatformUtil.java) |
| `FilterParser` | class | — | — | 5 | 93 | [FilterParser.java](dev/ftb/mods/ftbfiltersystem/util/FilterParser.java) |
| `NBTUtil` | class | — | — | 1 | 36 | [NBTUtil.java](dev/ftb/mods/ftbfiltersystem/util/NBTUtil.java) |
| `PlatformUtilImpl` | class | — | — | 1 | 10 | [PlatformUtilImpl.java](dev/ftb/mods/ftbfiltersystem/util/neoforge/PlatformUtilImpl.java) |
| `SelectionPanel` | class | — | — | 8 | 127 | [SelectionPanel.java](dev/ftb/mods/ftbfiltersystem/client/SelectionPanel.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_FTBFilterSystem121_common_68cfb66ee56442ffb7c4f9f0b64226e2_4febfdcc118a4cb683d4ac73623201b7208fee2f330e9ec5b17726fd247ca809ftbfiltersystem2114devjar/PlatformMethods.java) |
| `FTBFilterSystem` | class | — | — | 3 | 78 | [FTBFilterSystem.java](dev/ftb/mods/ftbfiltersystem/FTBFilterSystem.java) |
| `FTBFilterSystemNet` | interface | — | — | 2 | 24 | [FTBFilterSystemNet.java](dev/ftb/mods/ftbfiltersystem/network/FTBFilterSystemNet.java) |
| `SyncFilterMessage` | record | CustomPacketPayload | — | 2 | 55 | [SyncFilterMessage.java](dev/ftb/mods/ftbfiltersystem/network/SyncFilterMessage.java) |
| `FoodValueFilter` | class | AbstractComparisonFilter | — | 3 | 38 | [FoodValueFilter.java](dev/ftb/mods/ftbfiltersystem/filter/FoodValueFilter.java) |
| `DurabilityFilter` | class | AbstractComparisonFilter | — | 4 | 46 | [DurabilityFilter.java](dev/ftb/mods/ftbfiltersystem/filter/DurabilityFilter.java) |
| `StackSizeFilter` | class | AbstractComparisonFilter | — | 3 | 36 | [StackSizeFilter.java](dev/ftb/mods/ftbfiltersystem/filter/StackSizeFilter.java) |
| `MaxStackSizeFilter` | class | AbstractComparisonFilter | — | 3 | 36 | [MaxStackSizeFilter.java](dev/ftb/mods/ftbfiltersystem/filter/MaxStackSizeFilter.java) |
| `ModFilter` | class | AbstractSmartFilter | — | 4 | 41 | [ModFilter.java](dev/ftb/mods/ftbfiltersystem/filter/ModFilter.java) |
| `RootFilter` | class | AndFilter | — | 2 | 25 | [RootFilter.java](dev/ftb/mods/ftbfiltersystem/filter/compound/RootFilter.java) |
| `OrFilter` | class | AbstractCompoundFilter | — | 3 | 35 | [OrFilter.java](dev/ftb/mods/ftbfiltersystem/filter/compound/OrFilter.java) |
| `NotFilter` | class | AbstractCompoundFilter | — | 4 | 40 | [NotFilter.java](dev/ftb/mods/ftbfiltersystem/filter/compound/NotFilter.java) |
| `OnlyOneFilter` | class | AbstractCompoundFilter | — | 3 | 37 | [OnlyOneFilter.java](dev/ftb/mods/ftbfiltersystem/filter/compound/OnlyOneFilter.java) |
| `AndFilter` | class | AbstractCompoundFilter | — | 3 | 35 | [AndFilter.java](dev/ftb/mods/ftbfiltersystem/filter/compound/AndFilter.java) |
| `FilterException` | class | RuntimeException | — | 0 | 12 | [FilterException.java](dev/ftb/mods/ftbfiltersystem/api/FilterException.java) |
| `AbstractSmartFilter` | class | SmartFilter | — | 4 | 42 | [AbstractSmartFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/AbstractSmartFilter.java) |
| `SmartFilter` | interface | — | — | 0 | 52 | [SmartFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/SmartFilter.java) |
| `DumpedFilter` | record | — | — | 0 | 5 | [DumpedFilter.java](dev/ftb/mods/ftbfiltersystem/api/filter/DumpedFilter.java) |
| `Textures` | class | — | — | 1 | 16 | [Textures.java](dev/ftb/mods/ftbfiltersystem/api/client/Textures.java) |
| `CustomCheckbox` | class | AbstractButton | — | 4 | 73 | [CustomCheckbox.java](dev/ftb/mods/ftbfiltersystem/api/client/gui/widget/CustomCheckbox.java) |
| `FilterCache` | enum | — | — | 2 | 31 | [FilterCache.java](dev/ftb/mods/ftbfiltersystem/util/FilterCache.java) |
| `GhostDropReceiver` | interface | — | — | 0 | 11 | [GhostDropReceiver.java](dev/ftb/mods/ftbfiltersystem/client/gui/GhostDropReceiver.java) |
| `CustomStringWidget` | class | StringWidget | — | 3 | 41 | [CustomStringWidget.java](dev/ftb/mods/ftbfiltersystem/client/gui/widget/CustomStringWidget.java) |
