# Configured（`configured`）

> jar：`[配置界面]configured.jar` ｜ 版本：`2.6.3` ｜ 作者：MrCrayfish
> 规模：**83 个类 / 20 个包 / 6556 行** ｜ 事件处理类 3 ｜ Mixin 0 ｜ 注册/管理类 7

**依赖**：`neoforge`、`minecraft`、`framework`、`jei`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.mrcrayfish.configured.client.screen` | 14 | `ConfigScreen`、`ModConfigSelectionScreen`、`ListMenuScreen` |
| `com.mrcrayfish.configured.api` | 11 | `ConfigType`、`IModConfigProvider`、`IModConfig` |
| `com.mrcrayfish.configured.impl.framework` | 7 | `FrameworkModConfig`、`FrameworkConfigHelper`、`FrameworkConfigProvider` |
| `com.mrcrayfish.configured.impl.jei` | 7 | `JeiConfig`、`JeiConfigProvider`、`ConfiguredJeiPlugin` |
| `com.mrcrayfish.configured.client` | 6 | `NeoForgeClientEvents`、`ClientConfigured`、`ClientConfigHelper` |
| `com.mrcrayfish.configured.impl.neoforge` | 6 | `NeoForgeConfig`、`NeoForgeConfigProvider`、`NeoForgeValue` |
| `com.mrcrayfish.configured` | 5 | `Configured`、`Events`、`Config` |
| `com.mrcrayfish.configured.client.screen.list` | 5 | `IListConfigValue`、`ListTypes`、`EnumListType` |
| `com.mrcrayfish.configured.network` | 4 | `NeoForgeNetwork`、`ClientPlayHandler`、`ConfiguredCodecs` |
| `com.mrcrayfish.configured.platform` | 3 | `NeoForgeConfigHelper`、`NeoForgePlatformHelper`、`Services` |
| `com.mrcrayfish.configured.client.screen.widget` | 3 | `ConfiguredButton`、`IconButton`、`CheckBoxButton` |
| `com.mrcrayfish.configured.util` | 2 | `ConfigHelper`、`NeoForgeConfigHelper` |
| `com.mrcrayfish.configured.impl.framework.handler` | 2 | `FrameworkClientHandler`、`FrameworkServerHandler` |
| `com.mrcrayfish.configured.platform.services` | 2 | `IConfigHelper`、`IPlatformHelper` |
| `com.mrcrayfish.configured.api.util` | 1 | `ConfigScreenHelper` |
| `com.mrcrayfish.configured.network.handler` | 1 | `NeoForgeServerPlayHandler` |
| `com.mrcrayfish.configured.network.payload` | 1 | `SyncNeoForgeConfigPayload` |
| `com.mrcrayfish.configured.client.util` | 1 | `ScreenUtil` |
| `com.mrcrayfish.configured.network.message` | 1 | `MessageSessionData` |
| `com.mrcrayfish.configured.impl.framework.message` | 1 | `MessageFramework` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeClientEvents` | class | — | EventBusSubscriber | 4 | 65 | [NeoForgeClientEvents.java](com/mrcrayfish/configured/client/NeoForgeClientEvents.java) |
| `ClientConfigured` | class | — | EventBusSubscriber | 3 | 55 | [ClientConfigured.java](com/mrcrayfish/configured/client/ClientConfigured.java) |
| `NeoForgeNetwork` | class | — | EventBusSubscriber | 1 | 43 | [NeoForgeNetwork.java](com/mrcrayfish/configured/network/NeoForgeNetwork.java) |
| `ConfigScreen` | class | ListMenuScreen | — | 34 | 791 | [ConfigScreen.java](com/mrcrayfish/configured/client/screen/ConfigScreen.java) |
| `Configured` | class | — | Mod | 3 | 42 | [Configured.java](com/mrcrayfish/configured/Configured.java) |
| `ModConfigSelectionScreen` | class | ListMenuScreen | — | 18 | 331 | [ModConfigSelectionScreen.java](com/mrcrayfish/configured/client/screen/ModConfigSelectionScreen.java) |
| `ListMenuScreen` | class | TooltipScreen | — | 26 | 289 | [ListMenuScreen.java](com/mrcrayfish/configured/client/screen/ListMenuScreen.java) |
| `FrameworkModConfig` | class | IModConfig | — | 20 | 314 | [FrameworkModConfig.java](com/mrcrayfish/configured/impl/framework/FrameworkModConfig.java) |
| `Events` | class | — | — | 1 | 11 | [Events.java](com/mrcrayfish/configured/Events.java) |
| `ConfigHelper` | class | — | — | 16 | 142 | [ConfigHelper.java](com/mrcrayfish/configured/util/ConfigHelper.java) |
| `ClientConfigHelper` | class | — | — | 9 | 51 | [ClientConfigHelper.java](com/mrcrayfish/configured/client/ClientConfigHelper.java) |
| `ClientPlayHandler` | class | — | — | 1 | 12 | [ClientPlayHandler.java](com/mrcrayfish/configured/network/ClientPlayHandler.java) |
| `NeoForgeConfig` | class | IModConfig | — | 14 | 245 | [NeoForgeConfig.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfig.java) |
| `FrameworkClientHandler` | class | — | — | 1 | 39 | [FrameworkClientHandler.java](com/mrcrayfish/configured/impl/framework/handler/FrameworkClientHandler.java) |
| `ConfigScreenHelper` | class | — | — | 3 | 26 | [ConfigScreenHelper.java](com/mrcrayfish/configured/api/util/ConfigScreenHelper.java) |
| `NeoForgeConfigHelper` | class | — | — | 13 | 155 | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/util/NeoForgeConfigHelper.java) |
| `ClientHandler` | class | — | — | 4 | 50 | [ClientHandler.java](com/mrcrayfish/configured/client/ClientHandler.java) |
| `EditListScreen` | class | Screen | — | 15 | 291 | [EditListScreen.java](com/mrcrayfish/configured/client/screen/EditListScreen.java) |
| `Config` | class | — | — | 9 | 129 | [Config.java](com/mrcrayfish/configured/Config.java) |
| `NeoForgeServerPlayHandler` | class | — | — | 1 | 122 | [NeoForgeServerPlayHandler.java](com/mrcrayfish/configured/network/handler/NeoForgeServerPlayHandler.java) |
| `FrameworkConfigHelper` | class | — | — | 1 | 11 | [FrameworkConfigHelper.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigHelper.java) |
| `FrameworkServerHandler` | class | — | — | 3 | 135 | [FrameworkServerHandler.java](com/mrcrayfish/configured/impl/framework/handler/FrameworkServerHandler.java) |
| `JeiConfig` | class | IModConfig | — | 9 | 78 | [JeiConfig.java](com/mrcrayfish/configured/impl/jei/JeiConfig.java) |
| `NeoForgeConfigHelper` | class | IConfigHelper | — | 3 | 64 | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/platform/NeoForgeConfigHelper.java) |
| `IConfigHelper` | interface | — | — | 0 | 12 | [IConfigHelper.java](com/mrcrayfish/configured/platform/services/IConfigHelper.java) |
| `WorldSelectionScreen` | class | ListMenuScreen | — | 12 | 294 | [WorldSelectionScreen.java](com/mrcrayfish/configured/client/screen/WorldSelectionScreen.java) |
| `ConfiguredCodecs` | class | — | — | 2 | 17 | [ConfiguredCodecs.java](com/mrcrayfish/configured/network/ConfiguredCodecs.java) |
| `SyncNeoForgeConfigPayload` | record | CustomPacketPayload | — | 2 | 34 | [SyncNeoForgeConfigPayload.java](com/mrcrayfish/configured/network/payload/SyncNeoForgeConfigPayload.java) |
| `NeoForgeConfigProvider` | class | IModConfigProvider | — | 2 | 35 | [NeoForgeConfigProvider.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfigProvider.java) |
| `FrameworkConfigProvider` | class | IModConfigProvider | — | 1 | 25 | [FrameworkConfigProvider.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigProvider.java) |
| `JeiConfigProvider` | class | IModConfigProvider | — | 1 | 26 | [JeiConfigProvider.java](com/mrcrayfish/configured/impl/jei/JeiConfigProvider.java) |
| `ConfiguredJeiPlugin` | class | IModPlugin | JeiPlugin | 3 | 27 | [ConfiguredJeiPlugin.java](com/mrcrayfish/configured/impl/jei/ConfiguredJeiPlugin.java) |
| `ConfigType` | enum | — | — | 4 | 42 | [ConfigType.java](com/mrcrayfish/configured/api/ConfigType.java) |
| `IModConfigProvider` | interface | — | — | 0 | 8 | [IModConfigProvider.java](com/mrcrayfish/configured/api/IModConfigProvider.java) |
| `IModConfig` | interface | — | — | 0 | 59 | [IModConfig.java](com/mrcrayfish/configured/api/IModConfig.java) |
| `IConfigEntry` | interface | — | — | 0 | 25 | [IConfigEntry.java](com/mrcrayfish/configured/api/IConfigEntry.java) |
| `IConfigValue` | interface | — | — | 0 | 38 | [IConfigValue.java](com/mrcrayfish/configured/api/IConfigValue.java) |
| `ChangeEnumScreen` | class | TooltipScreen | — | 13 | 191 | [ChangeEnumScreen.java](com/mrcrayfish/configured/client/screen/ChangeEnumScreen.java) |
| `ConfirmationScreen` | class | Screen | — | 10 | 129 | [ConfirmationScreen.java](com/mrcrayfish/configured/client/screen/ConfirmationScreen.java) |
| `IListConfigValue` | interface | — | — | 0 | 15 | [IListConfigValue.java](com/mrcrayfish/configured/client/screen/list/IListConfigValue.java) |
| `ConfiguredButton` | class | Button | — | 2 | 34 | [ConfiguredButton.java](com/mrcrayfish/configured/client/screen/widget/ConfiguredButton.java) |
| `ScreenUtil` | class | — | — | 5 | 50 | [ScreenUtil.java](com/mrcrayfish/configured/client/util/ScreenUtil.java) |
| `NeoForgeValue` | class | IConfigValue<T> | — | 15 | 127 | [NeoForgeValue.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeValue.java) |
| `FrameworkValue` | class | IConfigValue<T> | — | 15 | 110 | [FrameworkValue.java](com/mrcrayfish/configured/impl/framework/FrameworkValue.java) |
| `JeiValue` | class | IConfigValue<T> | — | 15 | 97 | [JeiValue.java](com/mrcrayfish/configured/impl/jei/JeiValue.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 12 | 89 | [NeoForgePlatformHelper.java](com/mrcrayfish/configured/platform/NeoForgePlatformHelper.java) |
| `ClientSessionData` | class | — | — | 4 | 23 | [ClientSessionData.java](com/mrcrayfish/configured/client/ClientSessionData.java) |
| `TooltipScreen` | class | Screen | — | 6 | 57 | [TooltipScreen.java](com/mrcrayfish/configured/client/screen/TooltipScreen.java) |
| `RequestScreen` | class | ListMenuScreen | — | 6 | 98 | [RequestScreen.java](com/mrcrayfish/configured/client/screen/RequestScreen.java) |
| `ExecutionContext` | record | — | — | 13 | 61 | [ExecutionContext.java](com/mrcrayfish/configured/api/ExecutionContext.java) |
| `EditStringScreen` | class | TooltipScreen | — | 4 | 101 | [EditStringScreen.java](com/mrcrayfish/configured/client/screen/EditStringScreen.java) |
| `ActiveConfirmationScreen` | class | ConfirmationScreen | — | 1 | 21 | [ActiveConfirmationScreen.java](com/mrcrayfish/configured/client/screen/ActiveConfirmationScreen.java) |
| `ServerPlayHelper` | class | — | — | 2 | 56 | [ServerPlayHelper.java](com/mrcrayfish/configured/network/ServerPlayHelper.java) |
| `MessageSessionData` | record | CustomPacketPayload | — | 2 | 26 | [MessageSessionData.java](com/mrcrayfish/configured/network/message/MessageSessionData.java) |
| `NeoForgeFolderEntry` | class | IConfigEntry | — | 7 | 101 | [NeoForgeFolderEntry.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeFolderEntry.java) |
| `NeoForgeListValue` | class | NeoForgeValue | — | 5 | 64 | [NeoForgeListValue.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeListValue.java) |
| `FrameworkFolderEntry` | class | IConfigEntry | — | 8 | 80 | [FrameworkFolderEntry.java](com/mrcrayfish/configured/impl/framework/FrameworkFolderEntry.java) |
| `MessageFramework` | class | CustomPacketPayload | — | 6 | 67 | [MessageFramework.java](com/mrcrayfish/configured/impl/framework/message/MessageFramework.java) |
| `JeiCategoryListEntry` | class | IConfigEntry | — | 7 | 57 | [JeiCategoryListEntry.java](com/mrcrayfish/configured/impl/jei/JeiCategoryListEntry.java) |
| `JeiCategoryEntry` | class | IConfigEntry | — | 8 | 75 | [JeiCategoryEntry.java](com/mrcrayfish/configured/impl/jei/JeiCategoryEntry.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeClientEvents` | 4 | `com.mrcrayfish.configured.client` | [NeoForgeClientEvents.java](com/mrcrayfish/configured/client/NeoForgeClientEvents.java) |
| `ClientConfigured` | 2 | `com.mrcrayfish.configured.client` | [ClientConfigured.java](com/mrcrayfish/configured/client/ClientConfigured.java) |
| `NeoForgeNetwork` | 1 | `com.mrcrayfish.configured.network` | [NeoForgeNetwork.java](com/mrcrayfish/configured/network/NeoForgeNetwork.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ClientConfigured` | EventBusSubscriber | [ClientConfigured.java](com/mrcrayfish/configured/client/ClientConfigured.java) |
| `ConfigScreen` | — | [ConfigScreen.java](com/mrcrayfish/configured/client/screen/ConfigScreen.java) |
| `Configured` | Mod | [Configured.java](com/mrcrayfish/configured/Configured.java) |
| `ModConfigSelectionScreen` | — | [ModConfigSelectionScreen.java](com/mrcrayfish/configured/client/screen/ModConfigSelectionScreen.java) |
| `FrameworkModConfig` | — | [FrameworkModConfig.java](com/mrcrayfish/configured/impl/framework/FrameworkModConfig.java) |
| `ConfigHelper` | — | [ConfigHelper.java](com/mrcrayfish/configured/util/ConfigHelper.java) |
| `ClientConfigHelper` | — | [ClientConfigHelper.java](com/mrcrayfish/configured/client/ClientConfigHelper.java) |
| `NeoForgeConfig` | — | [NeoForgeConfig.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfig.java) |
| `ConfigScreenHelper` | — | [ConfigScreenHelper.java](com/mrcrayfish/configured/api/util/ConfigScreenHelper.java) |
| `NeoForgeConfigHelper` | — | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/util/NeoForgeConfigHelper.java) |
| `Config` | — | [Config.java](com/mrcrayfish/configured/Config.java) |
| `FrameworkConfigHelper` | — | [FrameworkConfigHelper.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigHelper.java) |
| `JeiConfig` | — | [JeiConfig.java](com/mrcrayfish/configured/impl/jei/JeiConfig.java) |
| `NeoForgeConfigHelper` | — | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/platform/NeoForgeConfigHelper.java) |
| `IConfigHelper` | — | [IConfigHelper.java](com/mrcrayfish/configured/platform/services/IConfigHelper.java) |
| `ConfiguredCodecs` | — | [ConfiguredCodecs.java](com/mrcrayfish/configured/network/ConfiguredCodecs.java) |
| `SyncNeoForgeConfigPayload` | — | [SyncNeoForgeConfigPayload.java](com/mrcrayfish/configured/network/payload/SyncNeoForgeConfigPayload.java) |
| `NeoForgeConfigProvider` | — | [NeoForgeConfigProvider.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfigProvider.java) |
| `FrameworkConfigProvider` | — | [FrameworkConfigProvider.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigProvider.java) |
| `JeiConfigProvider` | — | [JeiConfigProvider.java](com/mrcrayfish/configured/impl/jei/JeiConfigProvider.java) |
| `ConfiguredJeiPlugin` | JeiPlugin | [ConfiguredJeiPlugin.java](com/mrcrayfish/configured/impl/jei/ConfiguredJeiPlugin.java) |
| `ConfigType` | — | [ConfigType.java](com/mrcrayfish/configured/api/ConfigType.java) |
| `IModConfigProvider` | — | [IModConfigProvider.java](com/mrcrayfish/configured/api/IModConfigProvider.java) |
| `IModConfig` | — | [IModConfig.java](com/mrcrayfish/configured/api/IModConfig.java) |
| `IConfigEntry` | — | [IConfigEntry.java](com/mrcrayfish/configured/api/IConfigEntry.java) |
| `IConfigValue` | — | [IConfigValue.java](com/mrcrayfish/configured/api/IConfigValue.java) |
| `IListConfigValue` | — | [IListConfigValue.java](com/mrcrayfish/configured/client/screen/list/IListConfigValue.java) |
| `ConfiguredButton` | — | [ConfiguredButton.java](com/mrcrayfish/configured/client/screen/widget/ConfiguredButton.java) |

## 全部类（83）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeClientEvents` | class | — | EventBusSubscriber | 4 | 65 | [NeoForgeClientEvents.java](com/mrcrayfish/configured/client/NeoForgeClientEvents.java) |
| `ClientConfigured` | class | — | EventBusSubscriber | 3 | 55 | [ClientConfigured.java](com/mrcrayfish/configured/client/ClientConfigured.java) |
| `NeoForgeNetwork` | class | — | EventBusSubscriber | 1 | 43 | [NeoForgeNetwork.java](com/mrcrayfish/configured/network/NeoForgeNetwork.java) |
| `ConfigScreen` | class | ListMenuScreen | — | 34 | 791 | [ConfigScreen.java](com/mrcrayfish/configured/client/screen/ConfigScreen.java) |
| `Configured` | class | — | Mod | 3 | 42 | [Configured.java](com/mrcrayfish/configured/Configured.java) |
| `ModConfigSelectionScreen` | class | ListMenuScreen | — | 18 | 331 | [ModConfigSelectionScreen.java](com/mrcrayfish/configured/client/screen/ModConfigSelectionScreen.java) |
| `ListMenuScreen` | class | TooltipScreen | — | 26 | 289 | [ListMenuScreen.java](com/mrcrayfish/configured/client/screen/ListMenuScreen.java) |
| `FrameworkModConfig` | class | IModConfig | — | 20 | 314 | [FrameworkModConfig.java](com/mrcrayfish/configured/impl/framework/FrameworkModConfig.java) |
| `Events` | class | — | — | 1 | 11 | [Events.java](com/mrcrayfish/configured/Events.java) |
| `ConfigHelper` | class | — | — | 16 | 142 | [ConfigHelper.java](com/mrcrayfish/configured/util/ConfigHelper.java) |
| `ClientConfigHelper` | class | — | — | 9 | 51 | [ClientConfigHelper.java](com/mrcrayfish/configured/client/ClientConfigHelper.java) |
| `ClientPlayHandler` | class | — | — | 1 | 12 | [ClientPlayHandler.java](com/mrcrayfish/configured/network/ClientPlayHandler.java) |
| `NeoForgeConfig` | class | IModConfig | — | 14 | 245 | [NeoForgeConfig.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfig.java) |
| `FrameworkClientHandler` | class | — | — | 1 | 39 | [FrameworkClientHandler.java](com/mrcrayfish/configured/impl/framework/handler/FrameworkClientHandler.java) |
| `ConfigScreenHelper` | class | — | — | 3 | 26 | [ConfigScreenHelper.java](com/mrcrayfish/configured/api/util/ConfigScreenHelper.java) |
| `NeoForgeConfigHelper` | class | — | — | 13 | 155 | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/util/NeoForgeConfigHelper.java) |
| `ClientHandler` | class | — | — | 4 | 50 | [ClientHandler.java](com/mrcrayfish/configured/client/ClientHandler.java) |
| `EditListScreen` | class | Screen | — | 15 | 291 | [EditListScreen.java](com/mrcrayfish/configured/client/screen/EditListScreen.java) |
| `Config` | class | — | — | 9 | 129 | [Config.java](com/mrcrayfish/configured/Config.java) |
| `NeoForgeServerPlayHandler` | class | — | — | 1 | 122 | [NeoForgeServerPlayHandler.java](com/mrcrayfish/configured/network/handler/NeoForgeServerPlayHandler.java) |
| `FrameworkConfigHelper` | class | — | — | 1 | 11 | [FrameworkConfigHelper.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigHelper.java) |
| `FrameworkServerHandler` | class | — | — | 3 | 135 | [FrameworkServerHandler.java](com/mrcrayfish/configured/impl/framework/handler/FrameworkServerHandler.java) |
| `JeiConfig` | class | IModConfig | — | 9 | 78 | [JeiConfig.java](com/mrcrayfish/configured/impl/jei/JeiConfig.java) |
| `NeoForgeConfigHelper` | class | IConfigHelper | — | 3 | 64 | [NeoForgeConfigHelper.java](com/mrcrayfish/configured/platform/NeoForgeConfigHelper.java) |
| `IConfigHelper` | interface | — | — | 0 | 12 | [IConfigHelper.java](com/mrcrayfish/configured/platform/services/IConfigHelper.java) |
| `WorldSelectionScreen` | class | ListMenuScreen | — | 12 | 294 | [WorldSelectionScreen.java](com/mrcrayfish/configured/client/screen/WorldSelectionScreen.java) |
| `ConfiguredCodecs` | class | — | — | 2 | 17 | [ConfiguredCodecs.java](com/mrcrayfish/configured/network/ConfiguredCodecs.java) |
| `SyncNeoForgeConfigPayload` | record | CustomPacketPayload | — | 2 | 34 | [SyncNeoForgeConfigPayload.java](com/mrcrayfish/configured/network/payload/SyncNeoForgeConfigPayload.java) |
| `NeoForgeConfigProvider` | class | IModConfigProvider | — | 2 | 35 | [NeoForgeConfigProvider.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeConfigProvider.java) |
| `FrameworkConfigProvider` | class | IModConfigProvider | — | 1 | 25 | [FrameworkConfigProvider.java](com/mrcrayfish/configured/impl/framework/FrameworkConfigProvider.java) |
| `JeiConfigProvider` | class | IModConfigProvider | — | 1 | 26 | [JeiConfigProvider.java](com/mrcrayfish/configured/impl/jei/JeiConfigProvider.java) |
| `ConfiguredJeiPlugin` | class | IModPlugin | JeiPlugin | 3 | 27 | [ConfiguredJeiPlugin.java](com/mrcrayfish/configured/impl/jei/ConfiguredJeiPlugin.java) |
| `ConfigType` | enum | — | — | 4 | 42 | [ConfigType.java](com/mrcrayfish/configured/api/ConfigType.java) |
| `IModConfigProvider` | interface | — | — | 0 | 8 | [IModConfigProvider.java](com/mrcrayfish/configured/api/IModConfigProvider.java) |
| `IModConfig` | interface | — | — | 0 | 59 | [IModConfig.java](com/mrcrayfish/configured/api/IModConfig.java) |
| `IConfigEntry` | interface | — | — | 0 | 25 | [IConfigEntry.java](com/mrcrayfish/configured/api/IConfigEntry.java) |
| `IConfigValue` | interface | — | — | 0 | 38 | [IConfigValue.java](com/mrcrayfish/configured/api/IConfigValue.java) |
| `ChangeEnumScreen` | class | TooltipScreen | — | 13 | 191 | [ChangeEnumScreen.java](com/mrcrayfish/configured/client/screen/ChangeEnumScreen.java) |
| `ConfirmationScreen` | class | Screen | — | 10 | 129 | [ConfirmationScreen.java](com/mrcrayfish/configured/client/screen/ConfirmationScreen.java) |
| `IListConfigValue` | interface | — | — | 0 | 15 | [IListConfigValue.java](com/mrcrayfish/configured/client/screen/list/IListConfigValue.java) |
| `ConfiguredButton` | class | Button | — | 2 | 34 | [ConfiguredButton.java](com/mrcrayfish/configured/client/screen/widget/ConfiguredButton.java) |
| `ScreenUtil` | class | — | — | 5 | 50 | [ScreenUtil.java](com/mrcrayfish/configured/client/util/ScreenUtil.java) |
| `NeoForgeValue` | class | IConfigValue<T> | — | 15 | 127 | [NeoForgeValue.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeValue.java) |
| `FrameworkValue` | class | IConfigValue<T> | — | 15 | 110 | [FrameworkValue.java](com/mrcrayfish/configured/impl/framework/FrameworkValue.java) |
| `JeiValue` | class | IConfigValue<T> | — | 15 | 97 | [JeiValue.java](com/mrcrayfish/configured/impl/jei/JeiValue.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 12 | 89 | [NeoForgePlatformHelper.java](com/mrcrayfish/configured/platform/NeoForgePlatformHelper.java) |
| `ClientSessionData` | class | — | — | 4 | 23 | [ClientSessionData.java](com/mrcrayfish/configured/client/ClientSessionData.java) |
| `TooltipScreen` | class | Screen | — | 6 | 57 | [TooltipScreen.java](com/mrcrayfish/configured/client/screen/TooltipScreen.java) |
| `RequestScreen` | class | ListMenuScreen | — | 6 | 98 | [RequestScreen.java](com/mrcrayfish/configured/client/screen/RequestScreen.java) |
| `ExecutionContext` | record | — | — | 13 | 61 | [ExecutionContext.java](com/mrcrayfish/configured/api/ExecutionContext.java) |
| `EditStringScreen` | class | TooltipScreen | — | 4 | 101 | [EditStringScreen.java](com/mrcrayfish/configured/client/screen/EditStringScreen.java) |
| `ActiveConfirmationScreen` | class | ConfirmationScreen | — | 1 | 21 | [ActiveConfirmationScreen.java](com/mrcrayfish/configured/client/screen/ActiveConfirmationScreen.java) |
| `ServerPlayHelper` | class | — | — | 2 | 56 | [ServerPlayHelper.java](com/mrcrayfish/configured/network/ServerPlayHelper.java) |
| `MessageSessionData` | record | CustomPacketPayload | — | 2 | 26 | [MessageSessionData.java](com/mrcrayfish/configured/network/message/MessageSessionData.java) |
| `NeoForgeFolderEntry` | class | IConfigEntry | — | 7 | 101 | [NeoForgeFolderEntry.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeFolderEntry.java) |
| `NeoForgeListValue` | class | NeoForgeValue | — | 5 | 64 | [NeoForgeListValue.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeListValue.java) |
| `FrameworkFolderEntry` | class | IConfigEntry | — | 8 | 80 | [FrameworkFolderEntry.java](com/mrcrayfish/configured/impl/framework/FrameworkFolderEntry.java) |
| `MessageFramework` | class | CustomPacketPayload | — | 6 | 67 | [MessageFramework.java](com/mrcrayfish/configured/impl/framework/message/MessageFramework.java) |
| `JeiCategoryListEntry` | class | IConfigEntry | — | 7 | 57 | [JeiCategoryListEntry.java](com/mrcrayfish/configured/impl/jei/JeiCategoryListEntry.java) |
| `JeiCategoryEntry` | class | IConfigEntry | — | 8 | 75 | [JeiCategoryEntry.java](com/mrcrayfish/configured/impl/jei/JeiCategoryEntry.java) |
| `IPlatformHelper` | interface | — | — | 0 | 38 | [IPlatformHelper.java](com/mrcrayfish/configured/platform/services/IPlatformHelper.java) |
| `ValueEntry` | class | IConfigEntry | — | 7 | 52 | [ValueEntry.java](com/mrcrayfish/configured/api/ValueEntry.java) |
| `ListTypes` | class | — | — | 7 | 86 | [ListTypes.java](com/mrcrayfish/configured/client/screen/list/ListTypes.java) |
| `Constants` | class | — | — | 0 | 11 | [Constants.java](com/mrcrayfish/configured/Constants.java) |
| `Bootstrap` | class | — | — | 1 | 10 | [Bootstrap.java](com/mrcrayfish/configured/Bootstrap.java) |
| `NeoForgeEnumValue` | class | IAllowedEnums<T> | — | 1 | 28 | [NeoForgeEnumValue.java](com/mrcrayfish/configured/impl/neoforge/NeoForgeEnumValue.java) |
| `FrameworkEnumValue` | class | IAllowedEnums<T> | — | 1 | 17 | [FrameworkEnumValue.java](com/mrcrayfish/configured/impl/framework/FrameworkEnumValue.java) |
| `FrameworkListValue` | class | FrameworkValue | — | 2 | 38 | [FrameworkListValue.java](com/mrcrayfish/configured/impl/framework/FrameworkListValue.java) |
| `JeiListValue` | class | JeiValue | — | 4 | 46 | [JeiListValue.java](com/mrcrayfish/configured/impl/jei/JeiListValue.java) |
| `Services` | class | — | — | 1 | 18 | [Services.java](com/mrcrayfish/configured/platform/Services.java) |
| `ActionResult` | record | — | — | 4 | 26 | [ActionResult.java](com/mrcrayfish/configured/api/ActionResult.java) |
| `IAllowedEnums` | interface | — | — | 0 | 8 | [IAllowedEnums.java](com/mrcrayfish/configured/api/IAllowedEnums.java) |
| `Environment` | enum | — | — | 0 | 7 | [Environment.java](com/mrcrayfish/configured/api/Environment.java) |
| `ModContext` | record | — | — | 0 | 5 | [ModContext.java](com/mrcrayfish/configured/api/ModContext.java) |
| `EditingTracker` | class | — | — | 3 | 52 | [EditingTracker.java](com/mrcrayfish/configured/client/EditingTracker.java) |
| `ILabelProvider` | interface | — | — | 0 | 6 | [ILabelProvider.java](com/mrcrayfish/configured/client/screen/ILabelProvider.java) |
| `IEditing` | interface | — | — | 0 | 8 | [IEditing.java](com/mrcrayfish/configured/client/screen/IEditing.java) |
| `IColouredTooltip` | interface | — | — | 0 | 47 | [IColouredTooltip.java](com/mrcrayfish/configured/client/screen/IColouredTooltip.java) |
| `EnumListType` | class | IListType<T> | — | 4 | 43 | [EnumListType.java](com/mrcrayfish/configured/client/screen/list/EnumListType.java) |
| `ListType` | class | IListType<T> | — | 3 | 32 | [ListType.java](com/mrcrayfish/configured/client/screen/list/ListType.java) |
| `IListType` | interface | — | — | 0 | 13 | [IListType.java](com/mrcrayfish/configured/client/screen/list/IListType.java) |
| `IconButton` | class | ConfiguredButton | — | 2 | 64 | [IconButton.java](com/mrcrayfish/configured/client/screen/widget/IconButton.java) |
| `CheckBoxButton` | class | AbstractButton | — | 4 | 39 | [CheckBoxButton.java](com/mrcrayfish/configured/client/screen/widget/CheckBoxButton.java) |
