# Resourcefulconfig（`resourcefulconfig`）

> jar：`[前置]resourcefulconfig.jar` ｜ 版本：`3.0.11` ｜ 作者：?
> 规模：**139 个类 / 41 个包 / 8855 行** ｜ 事件处理类 0 ｜ Mixin 4 ｜ 注册/管理类 0

**官方描述**：A library for creating config files for your mod across multiple platforms.

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.teamresourceful.resourcefulconfig.client.components.options.types` | 12 | `DropdownWidget`、`SelectWidget`、`RangeOptionWidget` |
| `com.teamresourceful.resourcefulconfig.api.annotations` | 8 | `Config`、`ConfigObject`、`ConfigButton` |
| `com.teamresourceful.resourcefulconfig.api.types.info` | 8 | `ResourcefulConfigColorGradient`、`ResourcefulConfigColor`、`ResourcefulConfigInfo` |
| `com.teamresourceful.resourcefulconfig.client.components.options.types.color` | 8 | `HsbColor`、`HueSelector`、`RecentColorStorage` |
| `com.teamresourceful.resourcefulconfig.web.config.validators` | 8 | `HashedPasswordValidator`、`OrValidator`、`Validators` |
| `com.teamresourceful.resourcefulconfig.common.loader` | 6 | `JavaConfigParser`、`ParsedConfig`、`Writer` |
| `com.teamresourceful.resourcefulconfig.api.types.options` | 6 | `EntryData`、`Option`、`EntryType` |
| `com.teamresourceful.resourcefulconfig.api.types.entries` | 5 | `ResourcefulConfigFieldBackedValueEntry`、`ResourcefulConfigEntry`、`ResourcefulConfigValueEntry` |
| `com.teamresourceful.resourcefulconfig.common.info` | 5 | `ConfigInfoLoader`、`ParsedGradient`、`ParsedInfo` |
| `com.teamresourceful.resourcefulconfig.client.components.base` | 5 | `ContainerWidget`、`ListWidget`、`SpriteButton` |
| `com.teamresourceful.resourcefulconfig.api.client` | 4 | `ResourcefulConfigScreen`、`ResourcefulConfigUI`、`ModalWidgetConstructor` |
| `com.teamresourceful.resourcefulconfig.web.server.paths` | 4 | `GetConfigPath`、`GetConfigsPath`、`BasePath` |
| `com.teamresourceful.resourcefulconfig.common.jsonc` | 4 | `JsoncObject`、`JsoncArray`、`JsoncElement` |
| `com.teamresourceful.resourcefulconfig.client` | 3 | `ConfigScreen`、`ConfigsScreen`、`UIConstants` |
| `com.teamresourceful.resourcefulconfig.client.components.configs` | 3 | `ConfigItem`、`ConfigHeaderItem`、`ConfigsListWidget` |
| `com.teamresourceful.resourcefulconfig.mixins.common` | 3 | `PlayerListAccessor`、`DedicatedServerAccessor`、`SettingsAccessor` |
| `com.teamresourceful.resourcefulconfig.common.compat.minecraft` | 3 | `DedicatedServerEntry`、`DedicatedServerConfig`、`DedicatedServerInfo` |
| `com.teamresourceful.resourcefulconfig.common.loader.entries` | 3 | `ParsedObservableEntry`、`ParsedInstanceEntry`、`ParsedObjectEntry` |
| `com.teamresourceful.resourcefulconfig.client.utils` | 3 | `ConfigSearching`、`KeyCodeHelper`、`State` |
| `com.teamresourceful.resourcefulconfig.api.types` | 3 | `ResourcefulConfig`、`ResourcefulConfigButton`、`ResourcefulConfigCategory` |
| `com.teamresourceful.resourcefulconfig.client.screens.base` | 3 | `OverlayScreen`、`CloseableScreen`、`ModalOverlay` |
| `com.teamresourceful.resourcefulconfig.client.components.options` | 3 | `OptionItem`、`Options`、`OptionsListWidget` |
| `com.teamresourceful.resourcefulconfig.client.components.options.range` | 3 | `DecimalOptionRange`、`WholeOptionRange`、`OptionRange` |
| `com.teamresourceful.resourcefulconfig.client.components.header` | 3 | `HeaderWidget`、`HeaderControlsWidget`、`HeaderContentWidget` |
| `com.teamresourceful.resourcefulconfig.neoforge` | 2 | `ResourcefulConfigNeoForge`、`ResourcefulConfigNeoForgeClient` |
| `com.teamresourceful.resourcefulconfig.api.loader` | 2 | `Configurator`、`ConfigParser` |
| `com.teamresourceful.resourcefulconfig.common.config` | 2 | `Configurations`、`ParsingUtils` |
| `com.teamresourceful.resourcefulconfig.client.components.options.misc.draggable` | 2 | `DraggableItem`、`DraggableList` |
| `com.teamresourceful.resourcefulconfig.client.components.categories` | 2 | `CategoryItem`、`CategoriesListWidget` |
| `com.teamresourceful.resourcefulconfig.web.utils` | 2 | `WebServerUtils`、`WebVerifier` |
| `com.teamresourceful.resourcefulconfig.api.patching` | 1 | `ConfigPatchEvent` |
| `com.teamresourceful.resourcefulconfig.mixins.client` | 1 | `MinecraftMixin` |
| `com.teamresourceful.resourcefulconfig.web.config` | 1 | `WebServerConfig` |
| `com.teamresourceful.resourcefulconfig.common.compat` | 1 | `CompatabilityLayers` |
| `com.teamresourceful.resourcefulconfig.common.utils` | 1 | `ModUtils` |
| `com.teamresourceful.resourcefulconfig.common.utils.neoforge` | 1 | `ModUtilsImpl` |
| `architectury_inject_ResourcefulConfig_common_025dc4c1b61e415da2db908771d16764_4e02d2328a00f614ed87609898068dbb37df14c08315d5da18510f42ca193db3resourcefulconfig3011devjar` | 1 | `PlatformMethods` |
| `com.teamresourceful.resourcefulconfig.web.info` | 1 | `UserJwtPayload` |
| `com.teamresourceful.resourcefulconfig.web.server` | 1 | `WebServer` |
| `com.teamresourceful.resourcefulconfig.common.loader.buttons` | 1 | `ParsedButton` |
| … | | 其余 1 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ResourcefulConfigNeoForge` | class | — | Mod | 2 | 40 | [ResourcefulConfigNeoForge.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForge.java) |
| `ConfigScreen` | class | Screen | — | 10 | 146 | [ConfigScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigScreen.java) |
| `JavaConfigParser` | class | ConfigParser | — | 12 | 271 | [JavaConfigParser.java](com/teamresourceful/resourcefulconfig/common/loader/JavaConfigParser.java) |
| `ResourcefulConfigScreen` | class | — | — | 5 | 49 | [ResourcefulConfigScreen.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigScreen.java) |
| `ConfigPatchEvent` | interface | — | — | 1 | 60 | [ConfigPatchEvent.java](com/teamresourceful/resourcefulconfig/api/patching/ConfigPatchEvent.java) |
| `ConfigsScreen` | class | Screen | — | 5 | 71 | [ConfigsScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigsScreen.java) |
| `ConfigItem` | class | ContainerWidget | — | 5 | 68 | [ConfigItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigItem.java) |
| `ResourcefulConfigNeoForgeClient` | class | — | — | 2 | 32 | [ResourcefulConfigNeoForgeClient.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForgeClient.java) |
| `PlayerListAccessor` | interface | — | Mixin | 0 | 12 | [PlayerListAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/PlayerListAccessor.java) |
| `DedicatedServerAccessor` | interface | — | Mixin | 0 | 13 | [DedicatedServerAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/DedicatedServerAccessor.java) |
| `SettingsAccessor` | interface | — | Mixin | 0 | 16 | [SettingsAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/SettingsAccessor.java) |
| `MinecraftMixin` | class | — | Mixin | 0 | 30 | [MinecraftMixin.java](com/teamresourceful/resourcefulconfig/mixins/client/MinecraftMixin.java) |
| `DedicatedServerEntry` | class | ResourcefulConfigValueEntry | — | 27 | 284 | [DedicatedServerEntry.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerEntry.java) |
| `Configurator` | class | — | — | 14 | 116 | [Configurator.java](com/teamresourceful/resourcefulconfig/api/loader/Configurator.java) |
| `ConfigHeaderItem` | class | ContainerWidget | — | 4 | 50 | [ConfigHeaderItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigHeaderItem.java) |
| `GetConfigPath` | record | BasePath | Internal | 7 | 161 | [GetConfigPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigPath.java) |
| `Configurations` | record | Iterable<ResourcefulConfig> | Internal | 6 | 51 | [Configurations.java](com/teamresourceful/resourcefulconfig/common/config/Configurations.java) |
| `DedicatedServerConfig` | class | ResourcefulConfig | — | 8 | 81 | [DedicatedServerConfig.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerConfig.java) |
| `ParsedObservableEntry` | record | ResourcefulConfigValueEntry | — | 24 | 271 | [ParsedObservableEntry.java](com/teamresourceful/resourcefulconfig/common/loader/entries/ParsedObservableEntry.java) |
| `ResourcefulConfigUI` | class | — | — | 5 | 47 | [ResourcefulConfigUI.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigUI.java) |
| `ResourcefulConfigFieldBackedValueEntry` | interface | — | — | 0 | 345 | [ResourcefulConfigFieldBackedValueEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigFieldBackedValueEntry.java) |
| `ConfigSearching` | class | — | — | 7 | 143 | [ConfigSearching.java](com/teamresourceful/resourcefulconfig/client/utils/ConfigSearching.java) |
| `GetConfigsPath` | record | BasePath | Internal | 3 | 59 | [GetConfigsPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigsPath.java) |
| `WebServerConfig` | record | — | — | 2 | 36 | [WebServerConfig.java](com/teamresourceful/resourcefulconfig/web/config/WebServerConfig.java) |
| `ConfigInfoLoader` | class | — | — | 3 | 75 | [ConfigInfoLoader.java](com/teamresourceful/resourcefulconfig/common/info/ConfigInfoLoader.java) |
| `ParsedConfig` | record | ResourcefulConfig | — | 4 | 72 | [ParsedConfig.java](com/teamresourceful/resourcefulconfig/common/loader/ParsedConfig.java) |
| `Config` | @interface | — | Retention、Target | 0 | 20 | [Config.java](com/teamresourceful/resourcefulconfig/api/annotations/Config.java) |
| `ConfigObject` | @interface | — | Retention、Target | 0 | 12 | [ConfigObject.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigObject.java) |
| `ConfigButton` | @interface | — | Retention、Target | 0 | 15 | [ConfigButton.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigButton.java) |
| `ConfigOption` | class | — | — | 0 | 79 | [ConfigOption.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigOption.java) |
| `ConfigEntry` | @interface | — | Retention、Target | 0 | 21 | [ConfigEntry.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigEntry.java) |
| `ConfigInfo` | @interface | — | Retention、Target | 0 | 67 | [ConfigInfo.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigInfo.java) |
| `ConfigParser` | interface | — | — | 0 | 35 | [ConfigParser.java](com/teamresourceful/resourcefulconfig/api/loader/ConfigParser.java) |
| `ResourcefulConfig` | interface | — | — | 0 | 39 | [ResourcefulConfig.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfig.java) |
| `ResourcefulConfigButton` | interface | — | — | 0 | 18 | [ResourcefulConfigButton.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigButton.java) |
| `ResourcefulConfigCategory` | interface | — | — | 0 | 6 | [ResourcefulConfigCategory.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigCategory.java) |
| `ResourcefulConfigColorGradient` | interface | — | — | 3 | 41 | [ResourcefulConfigColorGradient.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColorGradient.java) |
| `ResourcefulConfigColor` | interface | — | — | 0 | 8 | [ResourcefulConfigColor.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColor.java) |
| `ResourcefulConfigInfo` | interface | — | — | 0 | 22 | [ResourcefulConfigInfo.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigInfo.java) |
| `ResourcefulConfigColorValue` | interface | — | — | 0 | 18 | [ResourcefulConfigColorValue.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColorValue.java) |
| `ResourcefulConfigInfoButton` | interface | — | — | 3 | 31 | [ResourcefulConfigInfoButton.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigInfoButton.java) |
| `ResourcefulConfigLink` | interface | — | — | 3 | 49 | [ResourcefulConfigLink.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigLink.java) |
| `ResourcefulConfigEntry` | interface | — | — | 0 | 13 | [ResourcefulConfigEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigEntry.java) |
| `ResourcefulConfigValueEntry` | interface | — | — | 0 | 57 | [ResourcefulConfigValueEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigValueEntry.java) |
| `ResourcefulConfigObjectEntry` | interface | — | — | 0 | 14 | [ResourcefulConfigObjectEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigObjectEntry.java) |
| `ContainerWidget` | class | AbstractWidget | — | 20 | 129 | [ContainerWidget.java](com/teamresourceful/resourcefulconfig/client/components/base/ContainerWidget.java) |
| `ConfigsListWidget` | class | ListWidget | — | 1 | 18 | [ConfigsListWidget.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigsListWidget.java) |
| `EntryData` | record | — | — | 11 | 75 | [EntryData.java](com/teamresourceful/resourcefulconfig/api/types/options/EntryData.java) |
| `DraggableItem` | class | BaseWidget | — | 5 | 75 | [DraggableItem.java](com/teamresourceful/resourcefulconfig/client/components/options/misc/draggable/DraggableItem.java) |
| `OverlayScreen` | class | Screen | — | 5 | 53 | [OverlayScreen.java](com/teamresourceful/resourcefulconfig/client/screens/base/OverlayScreen.java) |
| `CompatabilityLayers` | class | — | — | 1 | 11 | [CompatabilityLayers.java](com/teamresourceful/resourcefulconfig/common/compat/CompatabilityLayers.java) |
| `ModUtils` | class | — | — | 6 | 51 | [ModUtils.java](com/teamresourceful/resourcefulconfig/common/utils/ModUtils.java) |
| `ListWidget` | class | ContainerWidget | — | 12 | 167 | [ListWidget.java](com/teamresourceful/resourcefulconfig/client/components/base/ListWidget.java) |
| `CategoryItem` | class | BaseWidget | — | 3 | 55 | [CategoryItem.java](com/teamresourceful/resourcefulconfig/client/components/categories/CategoryItem.java) |
| `OptionItem` | class | ContainerWidget | — | 4 | 83 | [OptionItem.java](com/teamresourceful/resourcefulconfig/client/components/options/OptionItem.java) |
| `Options` | class | — | — | 5 | 231 | [Options.java](com/teamresourceful/resourcefulconfig/client/components/options/Options.java) |
| `DecimalOptionRange` | record | OptionRange | — | 10 | 80 | [DecimalOptionRange.java](com/teamresourceful/resourcefulconfig/client/components/options/range/DecimalOptionRange.java) |
| `WholeOptionRange` | record | OptionRange | — | 12 | 110 | [WholeOptionRange.java](com/teamresourceful/resourcefulconfig/client/components/options/range/WholeOptionRange.java) |
| `DraggableList` | class | ListWidget | — | 13 | 152 | [DraggableList.java](com/teamresourceful/resourcefulconfig/client/components/options/misc/draggable/DraggableList.java) |
| `DropdownWidget` | class | BaseWidget | — | 11 | 151 | [DropdownWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/DropdownWidget.java) |

## Mixin（4 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PlayerListAccessor` | `PlayerListAccessor` | [PlayerListAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/PlayerListAccessor.java) |
| `DedicatedServerAccessor` | `DedicatedServerAccessor` | [DedicatedServerAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/DedicatedServerAccessor.java) |
| `SettingsAccessor` | `SettingsAccessor` | [SettingsAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/SettingsAccessor.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/teamresourceful/resourcefulconfig/mixins/client/MinecraftMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ResourcefulConfigNeoForge` | Mod | [ResourcefulConfigNeoForge.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForge.java) |
| `ConfigScreen` | — | [ConfigScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigScreen.java) |
| `JavaConfigParser` | — | [JavaConfigParser.java](com/teamresourceful/resourcefulconfig/common/loader/JavaConfigParser.java) |
| `ResourcefulConfigScreen` | — | [ResourcefulConfigScreen.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigScreen.java) |
| `ConfigPatchEvent` | — | [ConfigPatchEvent.java](com/teamresourceful/resourcefulconfig/api/patching/ConfigPatchEvent.java) |
| `ConfigsScreen` | — | [ConfigsScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigsScreen.java) |
| `ConfigItem` | — | [ConfigItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigItem.java) |
| `ResourcefulConfigNeoForgeClient` | — | [ResourcefulConfigNeoForgeClient.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForgeClient.java) |
| `Configurator` | — | [Configurator.java](com/teamresourceful/resourcefulconfig/api/loader/Configurator.java) |
| `ConfigHeaderItem` | — | [ConfigHeaderItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigHeaderItem.java) |
| `GetConfigPath` | Internal | [GetConfigPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigPath.java) |
| `Configurations` | Internal | [Configurations.java](com/teamresourceful/resourcefulconfig/common/config/Configurations.java) |
| `DedicatedServerConfig` | — | [DedicatedServerConfig.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerConfig.java) |
| `ResourcefulConfigUI` | — | [ResourcefulConfigUI.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigUI.java) |
| `ResourcefulConfigFieldBackedValueEntry` | — | [ResourcefulConfigFieldBackedValueEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigFieldBackedValueEntry.java) |
| `ConfigSearching` | — | [ConfigSearching.java](com/teamresourceful/resourcefulconfig/client/utils/ConfigSearching.java) |
| `GetConfigsPath` | Internal | [GetConfigsPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigsPath.java) |
| `WebServerConfig` | — | [WebServerConfig.java](com/teamresourceful/resourcefulconfig/web/config/WebServerConfig.java) |
| `ConfigInfoLoader` | — | [ConfigInfoLoader.java](com/teamresourceful/resourcefulconfig/common/info/ConfigInfoLoader.java) |
| `ParsedConfig` | — | [ParsedConfig.java](com/teamresourceful/resourcefulconfig/common/loader/ParsedConfig.java) |
| `Config` | Retention;Target | [Config.java](com/teamresourceful/resourcefulconfig/api/annotations/Config.java) |
| `ConfigObject` | Retention;Target | [ConfigObject.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigObject.java) |
| `ConfigButton` | Retention;Target | [ConfigButton.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigButton.java) |
| `ConfigOption` | — | [ConfigOption.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigOption.java) |
| `ConfigEntry` | Retention;Target | [ConfigEntry.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigEntry.java) |
| `ConfigInfo` | Retention;Target | [ConfigInfo.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigInfo.java) |
| `ConfigParser` | — | [ConfigParser.java](com/teamresourceful/resourcefulconfig/api/loader/ConfigParser.java) |
| `ResourcefulConfig` | — | [ResourcefulConfig.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfig.java) |
| `ResourcefulConfigButton` | — | [ResourcefulConfigButton.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigButton.java) |
| `ResourcefulConfigCategory` | — | [ResourcefulConfigCategory.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigCategory.java) |

## 全部类（139）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ResourcefulConfigNeoForge` | class | — | Mod | 2 | 40 | [ResourcefulConfigNeoForge.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForge.java) |
| `ConfigScreen` | class | Screen | — | 10 | 146 | [ConfigScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigScreen.java) |
| `JavaConfigParser` | class | ConfigParser | — | 12 | 271 | [JavaConfigParser.java](com/teamresourceful/resourcefulconfig/common/loader/JavaConfigParser.java) |
| `ResourcefulConfigScreen` | class | — | — | 5 | 49 | [ResourcefulConfigScreen.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigScreen.java) |
| `ConfigPatchEvent` | interface | — | — | 1 | 60 | [ConfigPatchEvent.java](com/teamresourceful/resourcefulconfig/api/patching/ConfigPatchEvent.java) |
| `ConfigsScreen` | class | Screen | — | 5 | 71 | [ConfigsScreen.java](com/teamresourceful/resourcefulconfig/client/ConfigsScreen.java) |
| `ConfigItem` | class | ContainerWidget | — | 5 | 68 | [ConfigItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigItem.java) |
| `ResourcefulConfigNeoForgeClient` | class | — | — | 2 | 32 | [ResourcefulConfigNeoForgeClient.java](com/teamresourceful/resourcefulconfig/neoforge/ResourcefulConfigNeoForgeClient.java) |
| `PlayerListAccessor` | interface | — | Mixin | 0 | 12 | [PlayerListAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/PlayerListAccessor.java) |
| `DedicatedServerAccessor` | interface | — | Mixin | 0 | 13 | [DedicatedServerAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/DedicatedServerAccessor.java) |
| `SettingsAccessor` | interface | — | Mixin | 0 | 16 | [SettingsAccessor.java](com/teamresourceful/resourcefulconfig/mixins/common/SettingsAccessor.java) |
| `MinecraftMixin` | class | — | Mixin | 0 | 30 | [MinecraftMixin.java](com/teamresourceful/resourcefulconfig/mixins/client/MinecraftMixin.java) |
| `DedicatedServerEntry` | class | ResourcefulConfigValueEntry | — | 27 | 284 | [DedicatedServerEntry.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerEntry.java) |
| `Configurator` | class | — | — | 14 | 116 | [Configurator.java](com/teamresourceful/resourcefulconfig/api/loader/Configurator.java) |
| `ConfigHeaderItem` | class | ContainerWidget | — | 4 | 50 | [ConfigHeaderItem.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigHeaderItem.java) |
| `GetConfigPath` | record | BasePath | Internal | 7 | 161 | [GetConfigPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigPath.java) |
| `Configurations` | record | Iterable<ResourcefulConfig> | Internal | 6 | 51 | [Configurations.java](com/teamresourceful/resourcefulconfig/common/config/Configurations.java) |
| `DedicatedServerConfig` | class | ResourcefulConfig | — | 8 | 81 | [DedicatedServerConfig.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerConfig.java) |
| `ParsedObservableEntry` | record | ResourcefulConfigValueEntry | — | 24 | 271 | [ParsedObservableEntry.java](com/teamresourceful/resourcefulconfig/common/loader/entries/ParsedObservableEntry.java) |
| `ResourcefulConfigUI` | class | — | — | 5 | 47 | [ResourcefulConfigUI.java](com/teamresourceful/resourcefulconfig/api/client/ResourcefulConfigUI.java) |
| `ResourcefulConfigFieldBackedValueEntry` | interface | — | — | 0 | 345 | [ResourcefulConfigFieldBackedValueEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigFieldBackedValueEntry.java) |
| `ConfigSearching` | class | — | — | 7 | 143 | [ConfigSearching.java](com/teamresourceful/resourcefulconfig/client/utils/ConfigSearching.java) |
| `GetConfigsPath` | record | BasePath | Internal | 3 | 59 | [GetConfigsPath.java](com/teamresourceful/resourcefulconfig/web/server/paths/GetConfigsPath.java) |
| `WebServerConfig` | record | — | — | 2 | 36 | [WebServerConfig.java](com/teamresourceful/resourcefulconfig/web/config/WebServerConfig.java) |
| `ConfigInfoLoader` | class | — | — | 3 | 75 | [ConfigInfoLoader.java](com/teamresourceful/resourcefulconfig/common/info/ConfigInfoLoader.java) |
| `ParsedConfig` | record | ResourcefulConfig | — | 4 | 72 | [ParsedConfig.java](com/teamresourceful/resourcefulconfig/common/loader/ParsedConfig.java) |
| `Config` | @interface | — | Retention、Target | 0 | 20 | [Config.java](com/teamresourceful/resourcefulconfig/api/annotations/Config.java) |
| `ConfigObject` | @interface | — | Retention、Target | 0 | 12 | [ConfigObject.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigObject.java) |
| `ConfigButton` | @interface | — | Retention、Target | 0 | 15 | [ConfigButton.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigButton.java) |
| `ConfigOption` | class | — | — | 0 | 79 | [ConfigOption.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigOption.java) |
| `ConfigEntry` | @interface | — | Retention、Target | 0 | 21 | [ConfigEntry.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigEntry.java) |
| `ConfigInfo` | @interface | — | Retention、Target | 0 | 67 | [ConfigInfo.java](com/teamresourceful/resourcefulconfig/api/annotations/ConfigInfo.java) |
| `ConfigParser` | interface | — | — | 0 | 35 | [ConfigParser.java](com/teamresourceful/resourcefulconfig/api/loader/ConfigParser.java) |
| `ResourcefulConfig` | interface | — | — | 0 | 39 | [ResourcefulConfig.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfig.java) |
| `ResourcefulConfigButton` | interface | — | — | 0 | 18 | [ResourcefulConfigButton.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigButton.java) |
| `ResourcefulConfigCategory` | interface | — | — | 0 | 6 | [ResourcefulConfigCategory.java](com/teamresourceful/resourcefulconfig/api/types/ResourcefulConfigCategory.java) |
| `ResourcefulConfigColorGradient` | interface | — | — | 3 | 41 | [ResourcefulConfigColorGradient.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColorGradient.java) |
| `ResourcefulConfigColor` | interface | — | — | 0 | 8 | [ResourcefulConfigColor.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColor.java) |
| `ResourcefulConfigInfo` | interface | — | — | 0 | 22 | [ResourcefulConfigInfo.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigInfo.java) |
| `ResourcefulConfigColorValue` | interface | — | — | 0 | 18 | [ResourcefulConfigColorValue.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigColorValue.java) |
| `ResourcefulConfigInfoButton` | interface | — | — | 3 | 31 | [ResourcefulConfigInfoButton.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigInfoButton.java) |
| `ResourcefulConfigLink` | interface | — | — | 3 | 49 | [ResourcefulConfigLink.java](com/teamresourceful/resourcefulconfig/api/types/info/ResourcefulConfigLink.java) |
| `ResourcefulConfigEntry` | interface | — | — | 0 | 13 | [ResourcefulConfigEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigEntry.java) |
| `ResourcefulConfigValueEntry` | interface | — | — | 0 | 57 | [ResourcefulConfigValueEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigValueEntry.java) |
| `ResourcefulConfigObjectEntry` | interface | — | — | 0 | 14 | [ResourcefulConfigObjectEntry.java](com/teamresourceful/resourcefulconfig/api/types/entries/ResourcefulConfigObjectEntry.java) |
| `ContainerWidget` | class | AbstractWidget | — | 20 | 129 | [ContainerWidget.java](com/teamresourceful/resourcefulconfig/client/components/base/ContainerWidget.java) |
| `ConfigsListWidget` | class | ListWidget | — | 1 | 18 | [ConfigsListWidget.java](com/teamresourceful/resourcefulconfig/client/components/configs/ConfigsListWidget.java) |
| `EntryData` | record | — | — | 11 | 75 | [EntryData.java](com/teamresourceful/resourcefulconfig/api/types/options/EntryData.java) |
| `DraggableItem` | class | BaseWidget | — | 5 | 75 | [DraggableItem.java](com/teamresourceful/resourcefulconfig/client/components/options/misc/draggable/DraggableItem.java) |
| `OverlayScreen` | class | Screen | — | 5 | 53 | [OverlayScreen.java](com/teamresourceful/resourcefulconfig/client/screens/base/OverlayScreen.java) |
| `CompatabilityLayers` | class | — | — | 1 | 11 | [CompatabilityLayers.java](com/teamresourceful/resourcefulconfig/common/compat/CompatabilityLayers.java) |
| `ModUtils` | class | — | — | 6 | 51 | [ModUtils.java](com/teamresourceful/resourcefulconfig/common/utils/ModUtils.java) |
| `ListWidget` | class | ContainerWidget | — | 12 | 167 | [ListWidget.java](com/teamresourceful/resourcefulconfig/client/components/base/ListWidget.java) |
| `CategoryItem` | class | BaseWidget | — | 3 | 55 | [CategoryItem.java](com/teamresourceful/resourcefulconfig/client/components/categories/CategoryItem.java) |
| `OptionItem` | class | ContainerWidget | — | 4 | 83 | [OptionItem.java](com/teamresourceful/resourcefulconfig/client/components/options/OptionItem.java) |
| `Options` | class | — | — | 5 | 231 | [Options.java](com/teamresourceful/resourcefulconfig/client/components/options/Options.java) |
| `DecimalOptionRange` | record | OptionRange | — | 10 | 80 | [DecimalOptionRange.java](com/teamresourceful/resourcefulconfig/client/components/options/range/DecimalOptionRange.java) |
| `WholeOptionRange` | record | OptionRange | — | 12 | 110 | [WholeOptionRange.java](com/teamresourceful/resourcefulconfig/client/components/options/range/WholeOptionRange.java) |
| `DraggableList` | class | ListWidget | — | 13 | 152 | [DraggableList.java](com/teamresourceful/resourcefulconfig/client/components/options/misc/draggable/DraggableList.java) |
| `DropdownWidget` | class | BaseWidget | — | 11 | 151 | [DropdownWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/DropdownWidget.java) |
| `CloseableScreen` | interface | — | — | 0 | 9 | [CloseableScreen.java](com/teamresourceful/resourcefulconfig/client/screens/base/CloseableScreen.java) |
| `WebServerUtils` | class | — | Internal | 4 | 77 | [WebServerUtils.java](com/teamresourceful/resourcefulconfig/web/utils/WebServerUtils.java) |
| `ParsingUtils` | class | — | — | 3 | 39 | [ParsingUtils.java](com/teamresourceful/resourcefulconfig/common/config/ParsingUtils.java) |
| `DedicatedServerInfo` | class | ResourcefulConfigInfo | — | 8 | 56 | [DedicatedServerInfo.java](com/teamresourceful/resourcefulconfig/common/compat/minecraft/DedicatedServerInfo.java) |
| `ModUtilsImpl` | class | — | — | 2 | 16 | [ModUtilsImpl.java](com/teamresourceful/resourcefulconfig/common/utils/neoforge/ModUtilsImpl.java) |
| `Writer` | class | — | — | 6 | 130 | [Writer.java](com/teamresourceful/resourcefulconfig/common/loader/Writer.java) |
| `JsoncObject` | class | JsoncElement | — | 7 | 86 | [JsoncObject.java](com/teamresourceful/resourcefulconfig/common/jsonc/JsoncObject.java) |
| `JsoncArray` | class | JsoncElement | — | 8 | 68 | [JsoncArray.java](com/teamresourceful/resourcefulconfig/common/jsonc/JsoncArray.java) |
| `Observable` | class | Supplier<T> | — | 6 | 47 | [Observable.java](com/teamresourceful/resourcefulconfig/api/types/entries/Observable.java) |
| `Option` | class | — | — | 6 | 111 | [Option.java](com/teamresourceful/resourcefulconfig/api/types/options/Option.java) |
| `SpriteButton` | class | AbstractButton | — | 9 | 83 | [SpriteButton.java](com/teamresourceful/resourcefulconfig/client/components/base/SpriteButton.java) |
| `SelectWidget` | class | BaseWidget | — | 9 | 151 | [SelectWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/SelectWidget.java) |
| `RangeOptionWidget` | class | BaseWidget | — | 7 | 161 | [RangeOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/RangeOptionWidget.java) |
| `MultilineStringOptionWidget` | class | BaseWidget | — | 5 | 71 | [MultilineStringOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/MultilineStringOptionWidget.java) |
| `KeybindOptionWidget` | class | BaseWidget | — | 5 | 78 | [KeybindOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/KeybindOptionWidget.java) |
| `ColorOptionWidget` | class | BaseWidget | — | 8 | 152 | [ColorOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/ColorOptionWidget.java) |
| `DraggableListOptionWidget` | class | BaseWidget | — | 5 | 143 | [DraggableListOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/DraggableListOptionWidget.java) |
| `HsbColor` | record | — | — | 7 | 64 | [HsbColor.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/HsbColor.java) |
| `KeyCodeHelper` | class | — | — | 2 | 159 | [KeyCodeHelper.java](com/teamresourceful/resourcefulconfig/client/utils/KeyCodeHelper.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_ResourcefulConfig_common_025dc4c1b61e415da2db908771d16764_4e02d2328a00f614ed87609898068dbb37df14c08315d5da18510f42ca193db3resourcefulconfig3011devjar/PlatformMethods.java) |
| `UserJwtPayload` | record | — | Internal | 1 | 27 | [UserJwtPayload.java](com/teamresourceful/resourcefulconfig/web/info/UserJwtPayload.java) |
| `WebServer` | class | — | Internal | 4 | 84 | [WebServer.java](com/teamresourceful/resourcefulconfig/web/server/WebServer.java) |
| `BasePath` | interface | — | Internal | 0 | 35 | [BasePath.java](com/teamresourceful/resourcefulconfig/web/server/paths/BasePath.java) |
| `PostSavePath` | record | BasePath | Internal | 4 | 109 | [PostSavePath.java](com/teamresourceful/resourcefulconfig/web/server/paths/PostSavePath.java) |
| `HashedPasswordValidator` | record | Validator | — | 3 | 59 | [HashedPasswordValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/HashedPasswordValidator.java) |
| `OrValidator` | record | Validator | — | 2 | 32 | [OrValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/OrValidator.java) |
| `Validators` | class | — | — | 1 | 21 | [Validators.java](com/teamresourceful/resourcefulconfig/web/config/validators/Validators.java) |
| `IfValidator` | record | Validator | — | 2 | 36 | [IfValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/IfValidator.java) |
| `Validator` | interface | — | — | 0 | 9 | [Validator.java](com/teamresourceful/resourcefulconfig/web/config/validators/Validator.java) |
| `DerivedPasswordValidator` | record | Validator | — | 2 | 39 | [DerivedPasswordValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/DerivedPasswordValidator.java) |
| `AlwaysFalseValidator` | class | Validator | — | 2 | 22 | [AlwaysFalseValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/AlwaysFalseValidator.java) |
| `PasswordValidator` | record | Validator | — | 2 | 22 | [PasswordValidator.java](com/teamresourceful/resourcefulconfig/web/config/validators/PasswordValidator.java) |
| `WebVerifier` | class | — | Internal | 4 | 74 | [WebVerifier.java](com/teamresourceful/resourcefulconfig/web/utils/WebVerifier.java) |
| `ParsedGradient` | record | ResourcefulConfigColorGradient | — | 1 | 13 | [ParsedGradient.java](com/teamresourceful/resourcefulconfig/common/info/ParsedGradient.java) |
| `ParsedInfo` | record | ResourcefulConfigInfo | — | 3 | 54 | [ParsedInfo.java](com/teamresourceful/resourcefulconfig/common/info/ParsedInfo.java) |
| `ParsedColor` | record | ResourcefulConfigColorValue | — | 1 | 15 | [ParsedColor.java](com/teamresourceful/resourcefulconfig/common/info/ParsedColor.java) |
| `ParsedLink` | record | ResourcefulConfigLink | — | 2 | 23 | [ParsedLink.java](com/teamresourceful/resourcefulconfig/common/info/ParsedLink.java) |
| `ParsedCategory` | record | ResourcefulConfigCategory | — | 3 | 43 | [ParsedCategory.java](com/teamresourceful/resourcefulconfig/common/loader/ParsedCategory.java) |
| `Loader` | class | — | — | 4 | 124 | [Loader.java](com/teamresourceful/resourcefulconfig/common/loader/Loader.java) |
| `Patcher` | class | — | — | 3 | 47 | [Patcher.java](com/teamresourceful/resourcefulconfig/common/loader/Patcher.java) |
| `ParsedInstanceEntry` | record | ResourcefulConfigFieldBackedValueEnt | — | 3 | 64 | [ParsedInstanceEntry.java](com/teamresourceful/resourcefulconfig/common/loader/entries/ParsedInstanceEntry.java) |
| `ParsedObjectEntry` | record | ResourcefulConfigObjectEntry | — | 2 | 30 | [ParsedObjectEntry.java](com/teamresourceful/resourcefulconfig/common/loader/entries/ParsedObjectEntry.java) |
| `ParsedButton` | record | ResourcefulConfigButton | — | 2 | 34 | [ParsedButton.java](com/teamresourceful/resourcefulconfig/common/loader/buttons/ParsedButton.java) |
| `JsoncElement` | interface | — | — | 0 | 33 | [JsoncElement.java](com/teamresourceful/resourcefulconfig/common/jsonc/JsoncElement.java) |
| `JsoncPrimitive` | class | JsoncElement | — | 4 | 46 | [JsoncPrimitive.java](com/teamresourceful/resourcefulconfig/common/jsonc/JsoncPrimitive.java) |
| `ModalWidgetConstructor` | interface | — | — | 0 | 9 | [ModalWidgetConstructor.java](com/teamresourceful/resourcefulconfig/api/client/ModalWidgetConstructor.java) |
| `GenericModalOverlay` | class | ModalOverlay | Internal | 1 | 22 | [GenericModalOverlay.java](com/teamresourceful/resourcefulconfig/api/client/GenericModalOverlay.java) |
| `Category` | @interface | — | Retention、Target | 0 | 15 | [Category.java](com/teamresourceful/resourcefulconfig/api/annotations/Category.java) |
| `Comment` | @interface | — | Retention、Target | 0 | 15 | [Comment.java](com/teamresourceful/resourcefulconfig/api/annotations/Comment.java) |
| `Translatable` | interface | — | — | 0 | 32 | [Translatable.java](com/teamresourceful/resourcefulconfig/api/types/info/Translatable.java) |
| `TooltipProvider` | interface | — | — | 0 | 8 | [TooltipProvider.java](com/teamresourceful/resourcefulconfig/api/types/info/TooltipProvider.java) |
| `EntryType` | enum | — | — | 3 | 37 | [EntryType.java](com/teamresourceful/resourcefulconfig/api/types/options/EntryType.java) |
| `AnnotationGetter` | interface | — | — | 0 | 8 | [AnnotationGetter.java](com/teamresourceful/resourcefulconfig/api/types/options/AnnotationGetter.java) |
| `Position` | enum | — | — | 0 | 7 | [Position.java](com/teamresourceful/resourcefulconfig/api/types/options/Position.java) |
| `TranslatableValue` | record | — | — | 4 | 33 | [TranslatableValue.java](com/teamresourceful/resourcefulconfig/api/types/options/TranslatableValue.java) |
| `UIConstants` | class | — | — | 0 | 24 | [UIConstants.java](com/teamresourceful/resourcefulconfig/client/UIConstants.java) |
| `ModSprites` | class | — | — | 3 | 54 | [ModSprites.java](com/teamresourceful/resourcefulconfig/client/components/ModSprites.java) |
| `HeaderWidget` | class | ContainerWidget | — | 0 | 26 | [HeaderWidget.java](com/teamresourceful/resourcefulconfig/client/components/header/HeaderWidget.java) |
| `HeaderControlsWidget` | class | ContainerWidget | — | 2 | 60 | [HeaderControlsWidget.java](com/teamresourceful/resourcefulconfig/client/components/header/HeaderControlsWidget.java) |
| `HeaderContentWidget` | class | ContainerWidget | — | 2 | 74 | [HeaderContentWidget.java](com/teamresourceful/resourcefulconfig/client/components/header/HeaderContentWidget.java) |
| `CustomButton` | class | AbstractButton | — | 3 | 44 | [CustomButton.java](com/teamresourceful/resourcefulconfig/client/components/base/CustomButton.java) |
| `BaseWidget` | class | AbstractWidget | — | 2 | 24 | [BaseWidget.java](com/teamresourceful/resourcefulconfig/client/components/base/BaseWidget.java) |
| `CategoriesListWidget` | class | ListWidget | — | 1 | 18 | [CategoriesListWidget.java](com/teamresourceful/resourcefulconfig/client/components/categories/CategoriesListWidget.java) |
| `OptionsListWidget` | class | ListWidget | — | 1 | 18 | [OptionsListWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/OptionsListWidget.java) |
| `OptionRange` | interface | — | — | 0 | 20 | [OptionRange.java](com/teamresourceful/resourcefulconfig/client/components/options/range/OptionRange.java) |
| `BooleanOptionWidget` | class | BaseWidget | — | 3 | 47 | [BooleanOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/BooleanOptionWidget.java) |
| `NumberOptionWidget` | class | EditBox | — | 4 | 84 | [NumberOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/NumberOptionWidget.java) |
| `StringOptionWidget` | class | EditBox | — | 4 | 81 | [StringOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/StringOptionWidget.java) |
| `ObjectOptionWidget` | class | BaseWidget | — | 4 | 62 | [ObjectOptionWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/ObjectOptionWidget.java) |
| `ResetableWidget` | interface | — | — | 0 | 6 | [ResetableWidget.java](com/teamresourceful/resourcefulconfig/client/components/options/types/ResetableWidget.java) |
| `HueSelector` | class | BaseWidget | — | 3 | 45 | [HueSelector.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/HueSelector.java) |
| `RecentColorStorage` | class | — | — | 3 | 40 | [RecentColorStorage.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/RecentColorStorage.java) |
| `HsbState` | class | State<HsbColor> | — | 3 | 30 | [HsbState.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/HsbState.java) |
| `EyedropperButton` | class | SpriteButton | — | 2 | 100 | [EyedropperButton.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/EyedropperButton.java) |
| `SaturationBrightnessSelector` | class | BaseWidget | — | 4 | 86 | [SaturationBrightnessSelector.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/SaturationBrightnessSelector.java) |
| `PresetsSelector` | class | BaseWidget | — | 3 | 144 | [PresetsSelector.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/PresetsSelector.java) |
| `AlphaSelector` | class | BaseWidget | — | 3 | 61 | [AlphaSelector.java](com/teamresourceful/resourcefulconfig/client/components/options/types/color/AlphaSelector.java) |
| `State` | interface | — | — | 2 | 30 | [State.java](com/teamresourceful/resourcefulconfig/client/utils/State.java) |
| `ModalOverlay` | class | OverlayScreen | — | 4 | 82 | [ModalOverlay.java](com/teamresourceful/resourcefulconfig/client/screens/base/ModalOverlay.java) |
