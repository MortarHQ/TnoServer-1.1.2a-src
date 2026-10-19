# Jupiter（`jupiter`）

> jar：`[前置]Jupiter.jar` ｜ 版本：`2.3.7` ｜ 作者：IAFEnvoy
> 规模：**114 个类 / 27 个包 / 7220 行** ｜ 事件处理类 2 ｜ Mixin 2 ｜ 注册/管理类 4

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.iafenvoy.jupiter.config.entry` | 26 | `ConfigGroupEntry`、`BaseEntry`、`SeparatorEntry` |
| `com.iafenvoy.jupiter.render.widget.builder` | 9 | `ConfigGroupWidgetBuilder`、`TextFieldWidgetBuilder`、`EnumWidgetBuilder` |
| `com.iafenvoy.jupiter.render.screen` | 8 | `ConfigListScreen`、`ConfigSelectScreen`、`ClientConfigScreen` |
| `com.iafenvoy.jupiter.util` | 7 | `CopyOnWriteHashMap`、`RLUtil`、`TextUtil` |
| `com.iafenvoy.jupiter.config.interfaces` | 6 | `RangeConfigEntry`、`ConfigMetaProvider`、`ConfigBuilder` |
| `com.iafenvoy.jupiter.render.screen.dialog` | 6 | `AbstractListDialog`、`EnumSelectWidget`、`Dialog` |
| `com.iafenvoy.jupiter.config.type` | 5 | `ListConfigType`、`SingleConfigType`、`ConfigType` |
| `com.iafenvoy.jupiter` | 4 | `ServerConfigManager`、`ConfigManager`、`Jupiter` |
| `com.iafenvoy.jupiter.network` | 4 | `ClientConfigNetwork`、`ServerConfigNetwork`、`ClientNetworkHelper` |
| `com.iafenvoy.jupiter.config` | 4 | `ConfigGroup`、`ConfigDataFixer`、`ConfigSource` |
| `com.iafenvoy.jupiter.config.container` | 3 | `AbstractConfigContainer`、`FileConfigContainer`、`AutoInitConfigContainer` |
| `com.iafenvoy.jupiter.network.payload` | 3 | `ConfigSyncPayload`、`ConfigRequestPayload`、`ConfigErrorPayload` |
| `com.iafenvoy.jupiter._loader.neoforge` | 2 | `JupiterNeoForgeClient`、`JupiterNeoForge` |
| `com.iafenvoy.jupiter.mixin` | 2 | `AutoConfigAccessor`、`AutoConfigMixin` |
| `com.iafenvoy.jupiter.compat` | 2 | `ExtraConfigManager`、`ExtraConfigHolder` |
| `com.iafenvoy.jupiter.compat.clothconfig` | 2 | `ClothConfigHolder`、`ClothConfigLoader` |
| `com.iafenvoy.jupiter.compat.forgeconfigspec` | 2 | `NightConfigHolder`、`ConfigSpecLoader` |
| `com.iafenvoy.jupiter._loader.neoforge.network` | 2 | `ClientNetworkHelperImpl`、`ServerNetworkHelperImpl` |
| `com.iafenvoy.jupiter.render.internal` | 2 | `JupiterConfigListScreen`、`JupiterConfigListWidget` |
| `com.iafenvoy.jupiter.config.container.wrapper` | 2 | `ExtraConfigWrapper`、`RemoteConfigWrapper` |
| `com.iafenvoy.jupiter.interfaces` | 2 | `IConfigEnumEntry`、`IConfigEntry` |
| `com.iafenvoy.jupiter.api` | 2 | `JupiterConfigEntry`、`JupiterConfig` |
| `com.iafenvoy.jupiter.internal` | 2 | `ConfigButtonReplaceStrategy`、`JupiterSettings` |
| `com.iafenvoy.jupiter.render` | 2 | `BadgeRenderer`、`TitleStack` |
| `com.iafenvoy.jupiter.render.screen.scrollbar` | 2 | `HorizontalScrollBar`、`VerticalScrollBar` |
| `com.iafenvoy.jupiter.render.widget` | 2 | `WidgetBuilder`、`TextFieldWithErrorWidget` |
| `com.iafenvoy.jupiter.test` | 1 | `TestConfig` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `JupiterNeoForgeClient` | class | — | EventBusSubscriber | 3 | 51 | [JupiterNeoForgeClient.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForgeClient.java) |
| `JupiterNeoForge` | class | — | EventBusSubscriber、Mod | 3 | 50 | [JupiterNeoForge.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForge.java) |
| `ServerConfigManager` | class | ResourceManagerReloadListener | — | 7 | 63 | [ServerConfigManager.java](com/iafenvoy/jupiter/ServerConfigManager.java) |
| `ConfigManager` | class | ResourceManagerReloadListener | — | 7 | 50 | [ConfigManager.java](com/iafenvoy/jupiter/ConfigManager.java) |
| `ClientConfigNetwork` | class | — | — | 5 | 57 | [ClientConfigNetwork.java](com/iafenvoy/jupiter/network/ClientConfigNetwork.java) |
| `AutoConfigAccessor` | interface | — | Mixin、Pseudo | 0 | 19 | [AutoConfigAccessor.java](com/iafenvoy/jupiter/mixin/AutoConfigAccessor.java) |
| `AutoConfigMixin` | class | — | Mixin、Pseudo | 1 | 30 | [AutoConfigMixin.java](com/iafenvoy/jupiter/mixin/AutoConfigMixin.java) |
| `ExtraConfigManager` | class | — | — | 6 | 86 | [ExtraConfigManager.java](com/iafenvoy/jupiter/compat/ExtraConfigManager.java) |
| `ConfigListScreen` | class | Screen | — | 15 | 222 | [ConfigListScreen.java](com/iafenvoy/jupiter/render/screen/ConfigListScreen.java) |
| `ConfigSelectScreen` | class | Screen | — | 12 | 220 | [ConfigSelectScreen.java](com/iafenvoy/jupiter/render/screen/ConfigSelectScreen.java) |
| `AbstractConfigContainer` | class | MapCodec | — | 22 | 170 | [AbstractConfigContainer.java](com/iafenvoy/jupiter/config/container/AbstractConfigContainer.java) |
| `ClothConfigHolder` | class | ExtraConfigHolder | — | 15 | 232 | [ClothConfigHolder.java](com/iafenvoy/jupiter/compat/clothconfig/ClothConfigHolder.java) |
| `ClientConfigScreen` | class | ConfigContainerScreen | — | 0 | 12 | [ClientConfigScreen.java](com/iafenvoy/jupiter/render/screen/ClientConfigScreen.java) |
| `ServerConfigNetwork` | class | — | — | 3 | 58 | [ServerConfigNetwork.java](com/iafenvoy/jupiter/network/ServerConfigNetwork.java) |
| `NightConfigHolder` | class | ExtraConfigHolder | — | 14 | 243 | [NightConfigHolder.java](com/iafenvoy/jupiter/compat/forgeconfigspec/NightConfigHolder.java) |
| `ConfigContainerScreen` | class | ConfigListScreen | — | 9 | 134 | [ConfigContainerScreen.java](com/iafenvoy/jupiter/render/screen/ConfigContainerScreen.java) |
| `ClientNetworkHelperImpl` | class | ClientNetworkHelper | — | 3 | 35 | [ClientNetworkHelperImpl.java](com/iafenvoy/jupiter/_loader/neoforge/network/ClientNetworkHelperImpl.java) |
| `ClientNetworkHelper` | interface | — | — | 0 | 19 | [ClientNetworkHelper.java](com/iafenvoy/jupiter/network/ClientNetworkHelper.java) |
| `ConfigGroup` | class | MapCodec | — | 12 | 118 | [ConfigGroup.java](com/iafenvoy/jupiter/config/ConfigGroup.java) |
| `WidgetBuilderManager` | class | WidgetBuilder | — | 6 | 99 | [WidgetBuilderManager.java](com/iafenvoy/jupiter/render/screen/WidgetBuilderManager.java) |
| `ServerConfigScreen` | class | ConfigContainerScreen | — | 1 | 21 | [ServerConfigScreen.java](com/iafenvoy/jupiter/render/screen/ServerConfigScreen.java) |
| `SingleConfigScreen` | class | ConfigListScreen | — | 2 | 29 | [SingleConfigScreen.java](com/iafenvoy/jupiter/render/screen/SingleConfigScreen.java) |
| `JupiterConfigListScreen` | class | Screen | Internal | 4 | 112 | [JupiterConfigListScreen.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListScreen.java) |
| `JupiterConfigListWidget` | class | ObjectSelectionList | Internal | 11 | 110 | [JupiterConfigListWidget.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListWidget.java) |
| `ConfigDataFixer` | class | — | — | 4 | 40 | [ConfigDataFixer.java](com/iafenvoy/jupiter/config/ConfigDataFixer.java) |
| `ExtraConfigWrapper` | class | AbstractConfigContainer | — | 7 | 62 | [ExtraConfigWrapper.java](com/iafenvoy/jupiter/config/container/wrapper/ExtraConfigWrapper.java) |
| `ConfigGroupEntry` | class | BaseEntry | — | 8 | 66 | [ConfigGroupEntry.java](com/iafenvoy/jupiter/config/entry/ConfigGroupEntry.java) |
| `BaseEntry` | class | ConfigEntry<T> | — | 24 | 211 | [BaseEntry.java](com/iafenvoy/jupiter/config/entry/BaseEntry.java) |
| `ServerNetworkHelperImpl` | class | ServerNetworkHelper | — | 4 | 44 | [ServerNetworkHelperImpl.java](com/iafenvoy/jupiter/_loader/neoforge/network/ServerNetworkHelperImpl.java) |
| `ServerNetworkHelper` | interface | — | — | 0 | 24 | [ServerNetworkHelper.java](com/iafenvoy/jupiter/network/ServerNetworkHelper.java) |
| `ConfigSyncPayload` | record | CustomPacketPayload | — | 1 | 29 | [ConfigSyncPayload.java](com/iafenvoy/jupiter/network/payload/ConfigSyncPayload.java) |
| `ConfigRequestPayload` | record | CustomPacketPayload | — | 1 | 22 | [ConfigRequestPayload.java](com/iafenvoy/jupiter/network/payload/ConfigRequestPayload.java) |
| `ConfigErrorPayload` | record | CustomPacketPayload | — | 1 | 19 | [ConfigErrorPayload.java](com/iafenvoy/jupiter/network/payload/ConfigErrorPayload.java) |
| `IConfigEnumEntry` | interface | — | Comment | 0 | 22 | [IConfigEnumEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEnumEntry.java) |
| `IConfigEntry` | interface | — | Comment | 0 | 54 | [IConfigEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEntry.java) |
| `ConfigSource` | record | — | — | 0 | 12 | [ConfigSource.java](com/iafenvoy/jupiter/config/ConfigSource.java) |
| `ConfigSide` | enum | — | — | 2 | 26 | [ConfigSide.java](com/iafenvoy/jupiter/config/ConfigSide.java) |
| `ListConfigType` | class | ConfigType | — | 1 | 17 | [ListConfigType.java](com/iafenvoy/jupiter/config/type/ListConfigType.java) |
| `SingleConfigType` | class | ConfigType | — | 0 | 8 | [SingleConfigType.java](com/iafenvoy/jupiter/config/type/SingleConfigType.java) |
| `ConfigType` | class | — | — | 1 | 21 | [ConfigType.java](com/iafenvoy/jupiter/config/type/ConfigType.java) |
| `MapConfigType` | class | ConfigType | — | 1 | 17 | [MapConfigType.java](com/iafenvoy/jupiter/config/type/MapConfigType.java) |
| `ConfigTypes` | interface | — | — | 0 | 38 | [ConfigTypes.java](com/iafenvoy/jupiter/config/type/ConfigTypes.java) |
| `RangeConfigEntry` | interface | — | — | 0 | 10 | [RangeConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/RangeConfigEntry.java) |
| `ConfigMetaProvider` | interface | ConfigMetaProvider | Internal | 3 | 43 | [ConfigMetaProvider.java](com/iafenvoy/jupiter/config/interfaces/ConfigMetaProvider.java) |
| `ConfigBuilder` | interface | — | — | 0 | 16 | [ConfigBuilder.java](com/iafenvoy/jupiter/config/interfaces/ConfigBuilder.java) |
| `TextFieldConfigEntry` | interface | — | — | 0 | 8 | [TextFieldConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/TextFieldConfigEntry.java) |
| `ConfigEntry` | interface | — | — | 0 | 64 | [ConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/ConfigEntry.java) |
| `FileConfigContainer` | class | AbstractConfigContainer | — | 4 | 54 | [FileConfigContainer.java](com/iafenvoy/jupiter/config/container/FileConfigContainer.java) |
| `AutoInitConfigContainer` | class | FileConfigContainer | — | 2 | 65 | [AutoInitConfigContainer.java](com/iafenvoy/jupiter/config/container/AutoInitConfigContainer.java) |
| `RemoteConfigWrapper` | class | AbstractConfigContainer | — | 4 | 32 | [RemoteConfigWrapper.java](com/iafenvoy/jupiter/config/container/wrapper/RemoteConfigWrapper.java) |
| `SeparatorEntry` | class | ConfigEntry<Unit> | — | 23 | 147 | [SeparatorEntry.java](com/iafenvoy/jupiter/config/entry/SeparatorEntry.java) |
| `ExtraConfigHolder` | interface | — | — | 0 | 20 | [ExtraConfigHolder.java](com/iafenvoy/jupiter/compat/ExtraConfigHolder.java) |
| `ConfigSpecLoader` | class | — | — | 2 | 86 | [ConfigSpecLoader.java](com/iafenvoy/jupiter/compat/forgeconfigspec/ConfigSpecLoader.java) |
| `ClothConfigLoader` | class | — | — | 2 | 52 | [ClothConfigLoader.java](com/iafenvoy/jupiter/compat/clothconfig/ClothConfigLoader.java) |
| `JupiterConfigEntry` | interface | — | — | 0 | 15 | [JupiterConfigEntry.java](com/iafenvoy/jupiter/api/JupiterConfigEntry.java) |
| `JupiterConfig` | @interface | — | Documented、Retention、Target | 0 | 14 | [JupiterConfig.java](com/iafenvoy/jupiter/api/JupiterConfig.java) |
| `TestConfig` | class | FileConfigContainer | — | 1 | 47 | [TestConfig.java](com/iafenvoy/jupiter/test/TestConfig.java) |
| `ConfigButtonReplaceStrategy` | enum | — | — | 0 | 8 | [ConfigButtonReplaceStrategy.java](com/iafenvoy/jupiter/internal/ConfigButtonReplaceStrategy.java) |
| `ConfigGroupWidgetBuilder` | class | AbstractButtonWidgetBuilder | — | 1 | 40 | [ConfigGroupWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/ConfigGroupWidgetBuilder.java) |
| `DoubleEntry` | class | BaseEntry | — | 15 | 130 | [DoubleEntry.java](com/iafenvoy/jupiter/config/entry/DoubleEntry.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `JupiterNeoForge` | 3 | `com.iafenvoy.jupiter._loader.neoforge` | [JupiterNeoForge.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForge.java) |
| `JupiterNeoForgeClient` | 2 | `com.iafenvoy.jupiter._loader.neoforge` | [JupiterNeoForgeClient.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForgeClient.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AutoConfigAccessor` | `AutoConfigAccessor` | [AutoConfigAccessor.java](com/iafenvoy/jupiter/mixin/AutoConfigAccessor.java) |
| `AutoConfigMixin` | `AutoConfig` | [AutoConfigMixin.java](com/iafenvoy/jupiter/mixin/AutoConfigMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ServerConfigManager` | — | [ServerConfigManager.java](com/iafenvoy/jupiter/ServerConfigManager.java) |
| `ConfigManager` | — | [ConfigManager.java](com/iafenvoy/jupiter/ConfigManager.java) |
| `ClientConfigNetwork` | — | [ClientConfigNetwork.java](com/iafenvoy/jupiter/network/ClientConfigNetwork.java) |
| `AutoConfigAccessor` | Mixin;Pseudo | [AutoConfigAccessor.java](com/iafenvoy/jupiter/mixin/AutoConfigAccessor.java) |
| `AutoConfigMixin` | Mixin;Pseudo | [AutoConfigMixin.java](com/iafenvoy/jupiter/mixin/AutoConfigMixin.java) |
| `ExtraConfigManager` | — | [ExtraConfigManager.java](com/iafenvoy/jupiter/compat/ExtraConfigManager.java) |
| `ConfigListScreen` | — | [ConfigListScreen.java](com/iafenvoy/jupiter/render/screen/ConfigListScreen.java) |
| `ConfigSelectScreen` | — | [ConfigSelectScreen.java](com/iafenvoy/jupiter/render/screen/ConfigSelectScreen.java) |
| `AbstractConfigContainer` | — | [AbstractConfigContainer.java](com/iafenvoy/jupiter/config/container/AbstractConfigContainer.java) |
| `ClothConfigHolder` | — | [ClothConfigHolder.java](com/iafenvoy/jupiter/compat/clothconfig/ClothConfigHolder.java) |
| `ClientConfigScreen` | — | [ClientConfigScreen.java](com/iafenvoy/jupiter/render/screen/ClientConfigScreen.java) |
| `ServerConfigNetwork` | — | [ServerConfigNetwork.java](com/iafenvoy/jupiter/network/ServerConfigNetwork.java) |
| `NightConfigHolder` | — | [NightConfigHolder.java](com/iafenvoy/jupiter/compat/forgeconfigspec/NightConfigHolder.java) |
| `ConfigContainerScreen` | — | [ConfigContainerScreen.java](com/iafenvoy/jupiter/render/screen/ConfigContainerScreen.java) |
| `ConfigGroup` | — | [ConfigGroup.java](com/iafenvoy/jupiter/config/ConfigGroup.java) |
| `ServerConfigScreen` | — | [ServerConfigScreen.java](com/iafenvoy/jupiter/render/screen/ServerConfigScreen.java) |
| `SingleConfigScreen` | — | [SingleConfigScreen.java](com/iafenvoy/jupiter/render/screen/SingleConfigScreen.java) |
| `JupiterConfigListScreen` | Internal | [JupiterConfigListScreen.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListScreen.java) |
| `JupiterConfigListWidget` | Internal | [JupiterConfigListWidget.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListWidget.java) |
| `ConfigDataFixer` | — | [ConfigDataFixer.java](com/iafenvoy/jupiter/config/ConfigDataFixer.java) |
| `ExtraConfigWrapper` | — | [ExtraConfigWrapper.java](com/iafenvoy/jupiter/config/container/wrapper/ExtraConfigWrapper.java) |
| `ConfigGroupEntry` | — | [ConfigGroupEntry.java](com/iafenvoy/jupiter/config/entry/ConfigGroupEntry.java) |
| `ConfigSyncPayload` | — | [ConfigSyncPayload.java](com/iafenvoy/jupiter/network/payload/ConfigSyncPayload.java) |
| `ConfigRequestPayload` | — | [ConfigRequestPayload.java](com/iafenvoy/jupiter/network/payload/ConfigRequestPayload.java) |
| `ConfigErrorPayload` | — | [ConfigErrorPayload.java](com/iafenvoy/jupiter/network/payload/ConfigErrorPayload.java) |
| `IConfigEnumEntry` | Comment | [IConfigEnumEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEnumEntry.java) |
| `IConfigEntry` | Comment | [IConfigEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEntry.java) |
| `ConfigSource` | — | [ConfigSource.java](com/iafenvoy/jupiter/config/ConfigSource.java) |
| `ConfigSide` | — | [ConfigSide.java](com/iafenvoy/jupiter/config/ConfigSide.java) |
| `ListConfigType` | — | [ListConfigType.java](com/iafenvoy/jupiter/config/type/ListConfigType.java) |

## 全部类（114）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `JupiterNeoForgeClient` | class | — | EventBusSubscriber | 3 | 51 | [JupiterNeoForgeClient.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForgeClient.java) |
| `JupiterNeoForge` | class | — | EventBusSubscriber、Mod | 3 | 50 | [JupiterNeoForge.java](com/iafenvoy/jupiter/_loader/neoforge/JupiterNeoForge.java) |
| `ServerConfigManager` | class | ResourceManagerReloadListener | — | 7 | 63 | [ServerConfigManager.java](com/iafenvoy/jupiter/ServerConfigManager.java) |
| `ConfigManager` | class | ResourceManagerReloadListener | — | 7 | 50 | [ConfigManager.java](com/iafenvoy/jupiter/ConfigManager.java) |
| `ClientConfigNetwork` | class | — | — | 5 | 57 | [ClientConfigNetwork.java](com/iafenvoy/jupiter/network/ClientConfigNetwork.java) |
| `AutoConfigAccessor` | interface | — | Mixin、Pseudo | 0 | 19 | [AutoConfigAccessor.java](com/iafenvoy/jupiter/mixin/AutoConfigAccessor.java) |
| `AutoConfigMixin` | class | — | Mixin、Pseudo | 1 | 30 | [AutoConfigMixin.java](com/iafenvoy/jupiter/mixin/AutoConfigMixin.java) |
| `ExtraConfigManager` | class | — | — | 6 | 86 | [ExtraConfigManager.java](com/iafenvoy/jupiter/compat/ExtraConfigManager.java) |
| `ConfigListScreen` | class | Screen | — | 15 | 222 | [ConfigListScreen.java](com/iafenvoy/jupiter/render/screen/ConfigListScreen.java) |
| `ConfigSelectScreen` | class | Screen | — | 12 | 220 | [ConfigSelectScreen.java](com/iafenvoy/jupiter/render/screen/ConfigSelectScreen.java) |
| `AbstractConfigContainer` | class | MapCodec | — | 22 | 170 | [AbstractConfigContainer.java](com/iafenvoy/jupiter/config/container/AbstractConfigContainer.java) |
| `ClothConfigHolder` | class | ExtraConfigHolder | — | 15 | 232 | [ClothConfigHolder.java](com/iafenvoy/jupiter/compat/clothconfig/ClothConfigHolder.java) |
| `ClientConfigScreen` | class | ConfigContainerScreen | — | 0 | 12 | [ClientConfigScreen.java](com/iafenvoy/jupiter/render/screen/ClientConfigScreen.java) |
| `ServerConfigNetwork` | class | — | — | 3 | 58 | [ServerConfigNetwork.java](com/iafenvoy/jupiter/network/ServerConfigNetwork.java) |
| `NightConfigHolder` | class | ExtraConfigHolder | — | 14 | 243 | [NightConfigHolder.java](com/iafenvoy/jupiter/compat/forgeconfigspec/NightConfigHolder.java) |
| `ConfigContainerScreen` | class | ConfigListScreen | — | 9 | 134 | [ConfigContainerScreen.java](com/iafenvoy/jupiter/render/screen/ConfigContainerScreen.java) |
| `ClientNetworkHelperImpl` | class | ClientNetworkHelper | — | 3 | 35 | [ClientNetworkHelperImpl.java](com/iafenvoy/jupiter/_loader/neoforge/network/ClientNetworkHelperImpl.java) |
| `ClientNetworkHelper` | interface | — | — | 0 | 19 | [ClientNetworkHelper.java](com/iafenvoy/jupiter/network/ClientNetworkHelper.java) |
| `ConfigGroup` | class | MapCodec | — | 12 | 118 | [ConfigGroup.java](com/iafenvoy/jupiter/config/ConfigGroup.java) |
| `WidgetBuilderManager` | class | WidgetBuilder | — | 6 | 99 | [WidgetBuilderManager.java](com/iafenvoy/jupiter/render/screen/WidgetBuilderManager.java) |
| `ServerConfigScreen` | class | ConfigContainerScreen | — | 1 | 21 | [ServerConfigScreen.java](com/iafenvoy/jupiter/render/screen/ServerConfigScreen.java) |
| `SingleConfigScreen` | class | ConfigListScreen | — | 2 | 29 | [SingleConfigScreen.java](com/iafenvoy/jupiter/render/screen/SingleConfigScreen.java) |
| `JupiterConfigListScreen` | class | Screen | Internal | 4 | 112 | [JupiterConfigListScreen.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListScreen.java) |
| `JupiterConfigListWidget` | class | ObjectSelectionList | Internal | 11 | 110 | [JupiterConfigListWidget.java](com/iafenvoy/jupiter/render/internal/JupiterConfigListWidget.java) |
| `ConfigDataFixer` | class | — | — | 4 | 40 | [ConfigDataFixer.java](com/iafenvoy/jupiter/config/ConfigDataFixer.java) |
| `ExtraConfigWrapper` | class | AbstractConfigContainer | — | 7 | 62 | [ExtraConfigWrapper.java](com/iafenvoy/jupiter/config/container/wrapper/ExtraConfigWrapper.java) |
| `ConfigGroupEntry` | class | BaseEntry | — | 8 | 66 | [ConfigGroupEntry.java](com/iafenvoy/jupiter/config/entry/ConfigGroupEntry.java) |
| `BaseEntry` | class | ConfigEntry<T> | — | 24 | 211 | [BaseEntry.java](com/iafenvoy/jupiter/config/entry/BaseEntry.java) |
| `ServerNetworkHelperImpl` | class | ServerNetworkHelper | — | 4 | 44 | [ServerNetworkHelperImpl.java](com/iafenvoy/jupiter/_loader/neoforge/network/ServerNetworkHelperImpl.java) |
| `ServerNetworkHelper` | interface | — | — | 0 | 24 | [ServerNetworkHelper.java](com/iafenvoy/jupiter/network/ServerNetworkHelper.java) |
| `ConfigSyncPayload` | record | CustomPacketPayload | — | 1 | 29 | [ConfigSyncPayload.java](com/iafenvoy/jupiter/network/payload/ConfigSyncPayload.java) |
| `ConfigRequestPayload` | record | CustomPacketPayload | — | 1 | 22 | [ConfigRequestPayload.java](com/iafenvoy/jupiter/network/payload/ConfigRequestPayload.java) |
| `ConfigErrorPayload` | record | CustomPacketPayload | — | 1 | 19 | [ConfigErrorPayload.java](com/iafenvoy/jupiter/network/payload/ConfigErrorPayload.java) |
| `IConfigEnumEntry` | interface | — | Comment | 0 | 22 | [IConfigEnumEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEnumEntry.java) |
| `IConfigEntry` | interface | — | Comment | 0 | 54 | [IConfigEntry.java](com/iafenvoy/jupiter/interfaces/IConfigEntry.java) |
| `ConfigSource` | record | — | — | 0 | 12 | [ConfigSource.java](com/iafenvoy/jupiter/config/ConfigSource.java) |
| `ConfigSide` | enum | — | — | 2 | 26 | [ConfigSide.java](com/iafenvoy/jupiter/config/ConfigSide.java) |
| `ListConfigType` | class | ConfigType | — | 1 | 17 | [ListConfigType.java](com/iafenvoy/jupiter/config/type/ListConfigType.java) |
| `SingleConfigType` | class | ConfigType | — | 0 | 8 | [SingleConfigType.java](com/iafenvoy/jupiter/config/type/SingleConfigType.java) |
| `ConfigType` | class | — | — | 1 | 21 | [ConfigType.java](com/iafenvoy/jupiter/config/type/ConfigType.java) |
| `MapConfigType` | class | ConfigType | — | 1 | 17 | [MapConfigType.java](com/iafenvoy/jupiter/config/type/MapConfigType.java) |
| `ConfigTypes` | interface | — | — | 0 | 38 | [ConfigTypes.java](com/iafenvoy/jupiter/config/type/ConfigTypes.java) |
| `RangeConfigEntry` | interface | — | — | 0 | 10 | [RangeConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/RangeConfigEntry.java) |
| `ConfigMetaProvider` | interface | ConfigMetaProvider | Internal | 3 | 43 | [ConfigMetaProvider.java](com/iafenvoy/jupiter/config/interfaces/ConfigMetaProvider.java) |
| `ConfigBuilder` | interface | — | — | 0 | 16 | [ConfigBuilder.java](com/iafenvoy/jupiter/config/interfaces/ConfigBuilder.java) |
| `TextFieldConfigEntry` | interface | — | — | 0 | 8 | [TextFieldConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/TextFieldConfigEntry.java) |
| `ConfigEntry` | interface | — | — | 0 | 64 | [ConfigEntry.java](com/iafenvoy/jupiter/config/interfaces/ConfigEntry.java) |
| `FileConfigContainer` | class | AbstractConfigContainer | — | 4 | 54 | [FileConfigContainer.java](com/iafenvoy/jupiter/config/container/FileConfigContainer.java) |
| `AutoInitConfigContainer` | class | FileConfigContainer | — | 2 | 65 | [AutoInitConfigContainer.java](com/iafenvoy/jupiter/config/container/AutoInitConfigContainer.java) |
| `RemoteConfigWrapper` | class | AbstractConfigContainer | — | 4 | 32 | [RemoteConfigWrapper.java](com/iafenvoy/jupiter/config/container/wrapper/RemoteConfigWrapper.java) |
| `SeparatorEntry` | class | ConfigEntry<Unit> | — | 23 | 147 | [SeparatorEntry.java](com/iafenvoy/jupiter/config/entry/SeparatorEntry.java) |
| `ExtraConfigHolder` | interface | — | — | 0 | 20 | [ExtraConfigHolder.java](com/iafenvoy/jupiter/compat/ExtraConfigHolder.java) |
| `ConfigSpecLoader` | class | — | — | 2 | 86 | [ConfigSpecLoader.java](com/iafenvoy/jupiter/compat/forgeconfigspec/ConfigSpecLoader.java) |
| `ClothConfigLoader` | class | — | — | 2 | 52 | [ClothConfigLoader.java](com/iafenvoy/jupiter/compat/clothconfig/ClothConfigLoader.java) |
| `JupiterConfigEntry` | interface | — | — | 0 | 15 | [JupiterConfigEntry.java](com/iafenvoy/jupiter/api/JupiterConfigEntry.java) |
| `JupiterConfig` | @interface | — | Documented、Retention、Target | 0 | 14 | [JupiterConfig.java](com/iafenvoy/jupiter/api/JupiterConfig.java) |
| `TestConfig` | class | FileConfigContainer | — | 1 | 47 | [TestConfig.java](com/iafenvoy/jupiter/test/TestConfig.java) |
| `ConfigButtonReplaceStrategy` | enum | — | — | 0 | 8 | [ConfigButtonReplaceStrategy.java](com/iafenvoy/jupiter/internal/ConfigButtonReplaceStrategy.java) |
| `ConfigGroupWidgetBuilder` | class | AbstractButtonWidgetBuilder | — | 1 | 40 | [ConfigGroupWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/ConfigGroupWidgetBuilder.java) |
| `DoubleEntry` | class | BaseEntry | — | 15 | 130 | [DoubleEntry.java](com/iafenvoy/jupiter/config/entry/DoubleEntry.java) |
| `IntegerEntry` | class | BaseEntry | — | 15 | 130 | [IntegerEntry.java](com/iafenvoy/jupiter/config/entry/IntegerEntry.java) |
| `LongEntry` | class | BaseEntry | — | 15 | 133 | [LongEntry.java](com/iafenvoy/jupiter/config/entry/LongEntry.java) |
| `FloatEntry` | class | BaseEntry | — | 15 | 115 | [FloatEntry.java](com/iafenvoy/jupiter/config/entry/FloatEntry.java) |
| `CopyOnWriteHashMap` | class | Map<K | — | 17 | 179 | [CopyOnWriteHashMap.java](com/iafenvoy/jupiter/util/CopyOnWriteHashMap.java) |
| `BadgeRenderer` | class | — | — | 3 | 25 | [BadgeRenderer.java](com/iafenvoy/jupiter/render/BadgeRenderer.java) |
| `JupiterScreen` | interface | — | — | 0 | 56 | [JupiterScreen.java](com/iafenvoy/jupiter/render/screen/JupiterScreen.java) |
| `HorizontalScrollBar` | class | — | — | 11 | 82 | [HorizontalScrollBar.java](com/iafenvoy/jupiter/render/screen/scrollbar/HorizontalScrollBar.java) |
| `VerticalScrollBar` | class | — | — | 11 | 82 | [VerticalScrollBar.java](com/iafenvoy/jupiter/render/screen/scrollbar/VerticalScrollBar.java) |
| `AbstractListDialog` | class | Dialog | — | 11 | 129 | [AbstractListDialog.java](com/iafenvoy/jupiter/render/screen/dialog/AbstractListDialog.java) |
| `WidgetBuilder` | class | JupiterScreen | — | 14 | 108 | [WidgetBuilder.java](com/iafenvoy/jupiter/render/widget/WidgetBuilder.java) |
| `StringEntry` | class | BaseEntry | — | 9 | 77 | [StringEntry.java](com/iafenvoy/jupiter/config/entry/StringEntry.java) |
| `MapIntegerEntry` | class | MapBaseEntry | — | 9 | 90 | [MapIntegerEntry.java](com/iafenvoy/jupiter/config/entry/MapIntegerEntry.java) |
| `ListEnumEntry` | class | — | — | 9 | 94 | [ListEnumEntry.java](com/iafenvoy/jupiter/config/entry/ListEnumEntry.java) |
| `ResourceLocationEntry` | class | BaseEntry | — | 9 | 79 | [ResourceLocationEntry.java](com/iafenvoy/jupiter/config/entry/ResourceLocationEntry.java) |
| `BooleanEntry` | class | BaseEntry | — | 7 | 66 | [BooleanEntry.java](com/iafenvoy/jupiter/config/entry/BooleanEntry.java) |
| `MapDoubleEntry` | class | MapBaseEntry | — | 9 | 90 | [MapDoubleEntry.java](com/iafenvoy/jupiter/config/entry/MapDoubleEntry.java) |
| `ListLongEntry` | class | ListBaseEntry | — | 9 | 84 | [ListLongEntry.java](com/iafenvoy/jupiter/config/entry/ListLongEntry.java) |
| `ListStringEntry` | class | ListBaseEntry | — | 9 | 84 | [ListStringEntry.java](com/iafenvoy/jupiter/config/entry/ListStringEntry.java) |
| `ListBaseEntry` | class | BaseEntry | — | 6 | 40 | [ListBaseEntry.java](com/iafenvoy/jupiter/config/entry/ListBaseEntry.java) |
| `ListDoubleEntry` | class | ListBaseEntry | — | 9 | 84 | [ListDoubleEntry.java](com/iafenvoy/jupiter/config/entry/ListDoubleEntry.java) |
| `ListIntegerEntry` | class | ListBaseEntry | — | 9 | 84 | [ListIntegerEntry.java](com/iafenvoy/jupiter/config/entry/ListIntegerEntry.java) |
| `EnumEntry` | class | — | — | 8 | 86 | [EnumEntry.java](com/iafenvoy/jupiter/config/entry/EnumEntry.java) |
| `EntryDoubleEntry` | class | EntryBaseEntry | Internal | 8 | 72 | [EntryDoubleEntry.java](com/iafenvoy/jupiter/config/entry/EntryDoubleEntry.java) |
| `ListBooleanEntry` | class | ListBaseEntry | — | 9 | 84 | [ListBooleanEntry.java](com/iafenvoy/jupiter/config/entry/ListBooleanEntry.java) |
| `MapStringEntry` | class | MapBaseEntry | — | 9 | 90 | [MapStringEntry.java](com/iafenvoy/jupiter/config/entry/MapStringEntry.java) |
| `EntryIntegerEntry` | class | EntryBaseEntry | Internal | 8 | 71 | [EntryIntegerEntry.java](com/iafenvoy/jupiter/config/entry/EntryIntegerEntry.java) |
| `MapBaseEntry` | class | BaseEntry | — | 6 | 40 | [MapBaseEntry.java](com/iafenvoy/jupiter/config/entry/MapBaseEntry.java) |
| `EntryStringEntry` | class | EntryBaseEntry | Internal | 8 | 72 | [EntryStringEntry.java](com/iafenvoy/jupiter/config/entry/EntryStringEntry.java) |
| `RLUtil` | class | — | — | 3 | 22 | [RLUtil.java](com/iafenvoy/jupiter/util/RLUtil.java) |
| `TextUtil` | interface | — | — | 0 | 23 | [TextUtil.java](com/iafenvoy/jupiter/util/TextUtil.java) |
| `JupiterUtils` | class | — | — | 2 | 26 | [JupiterUtils.java](com/iafenvoy/jupiter/util/JupiterUtils.java) |
| `EnumHelper` | class | — | — | 4 | 24 | [EnumHelper.java](com/iafenvoy/jupiter/util/EnumHelper.java) |
| `EnumSelectWidget` | class | — | — | 8 | 81 | [EnumSelectWidget.java](com/iafenvoy/jupiter/render/screen/dialog/EnumSelectWidget.java) |
| `Jupiter` | class | — | — | 3 | 63 | [Jupiter.java](com/iafenvoy/jupiter/Jupiter.java) |
| `Platform` | class | — | — | 2 | 16 | [Platform.java](com/iafenvoy/jupiter/Platform.java) |
| `ValueChangeCallback` | interface | — | — | 0 | 7 | [ValueChangeCallback.java](com/iafenvoy/jupiter/config/interfaces/ValueChangeCallback.java) |
| `EntryBaseEntry` | class | BaseEntry | — | 1 | 13 | [EntryBaseEntry.java](com/iafenvoy/jupiter/config/entry/EntryBaseEntry.java) |
| `TextFormatter` | class | — | — | 1 | 36 | [TextFormatter.java](com/iafenvoy/jupiter/util/TextFormatter.java) |
| `Comment` | @interface | — | Retention、Target | 0 | 15 | [Comment.java](com/iafenvoy/jupiter/util/Comment.java) |
| `JupiterSettings` | class | AutoInitConfigContainer | — | 2 | 50 | [JupiterSettings.java](com/iafenvoy/jupiter/internal/JupiterSettings.java) |
| `TitleStack` | class | — | — | 4 | 73 | [TitleStack.java](com/iafenvoy/jupiter/render/TitleStack.java) |
| `Dialog` | class | Screen | — | 4 | 47 | [Dialog.java](com/iafenvoy/jupiter/render/screen/dialog/Dialog.java) |
| `ListDialog` | class | AbstractListDialog | — | 3 | 34 | [ListDialog.java](com/iafenvoy/jupiter/render/screen/dialog/ListDialog.java) |
| `EnumSelectDialog` | class | — | — | 2 | 47 | [EnumSelectDialog.java](com/iafenvoy/jupiter/render/screen/dialog/EnumSelectDialog.java) |
| `MapDialog` | class | AbstractListDialog | — | 3 | 34 | [MapDialog.java](com/iafenvoy/jupiter/render/screen/dialog/MapDialog.java) |
| `TextFieldWithErrorWidget` | class | EditBox | — | 2 | 30 | [TextFieldWithErrorWidget.java](com/iafenvoy/jupiter/render/widget/TextFieldWithErrorWidget.java) |
| `TextFieldWidgetBuilder` | class | WidgetBuilder | — | 3 | 57 | [TextFieldWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/TextFieldWidgetBuilder.java) |
| `EnumWidgetBuilder` | class | — | — | 1 | 28 | [EnumWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/EnumWidgetBuilder.java) |
| `ListWidgetBuilder` | class | AbstractButtonWidgetBuilder | — | 1 | 32 | [ListWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/ListWidgetBuilder.java) |
| `MapWidgetBuilder` | class | AbstractButtonWidgetBuilder | — | 1 | 32 | [MapWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/MapWidgetBuilder.java) |
| `AbstractButtonWidgetBuilder` | class | WidgetBuilder | — | 4 | 44 | [AbstractButtonWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/AbstractButtonWidgetBuilder.java) |
| `SeparatorWidgetBuilder` | class | WidgetBuilder | — | 4 | 51 | [SeparatorWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/SeparatorWidgetBuilder.java) |
| `EntryWidgetBuilder` | class | WidgetBuilder | — | 3 | 58 | [EntryWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/EntryWidgetBuilder.java) |
| `ButtonWidgetBuilder` | class | AbstractButtonWidgetBuilder | — | 1 | 28 | [ButtonWidgetBuilder.java](com/iafenvoy/jupiter/render/widget/builder/ButtonWidgetBuilder.java) |
