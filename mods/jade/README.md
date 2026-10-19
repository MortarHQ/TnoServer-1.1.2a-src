# Jade（`jade`）

> jar：`[玉 🔍] Jade-1.21.1-NeoForge-15.10.5.jar` ｜ 版本：`${file.jarVersion}` ｜ 作者：Snownee
> 规模：**231 个类 / 32 个包 / 20984 行** ｜ 事件处理类 0 ｜ Mixin 6 ｜ 注册/管理类 17

**官方描述**：Minecraft mod shows what you are looking at. (Hwyla fork)

**依赖**：`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `snownee.jade.addon.vanilla` | 37 | `CommandBlockProvider`、`NextEntityDropProvider`、`FallingBlockProvider` |
| `snownee.jade.api` | 21 | `IWailaClientRegistration`、`AccessorClientHandler`、`IWailaCommonRegistration` |
| `snownee.jade.impl.ui` | 16 | `BoxElement`、`ElementHelper`、`ItemStackElement` |
| `snownee.jade.api.ui` | 13 | `Color`、`BoxStyle`、`ProgressStyle` |
| `snownee.jade.util` | 12 | `CommonProxy`、`ClientProxy`、`JsonConfig` |
| `snownee.jade.impl` | 12 | `WailaClientRegistration`、`ClientRegistrationSession`、`EntityAccessorClientHandler` |
| `snownee.jade.api.view` | 10 | `ItemViewUtils`、`ClientViewGroup`、`ItemView` |
| `snownee.jade.addon.access` | 9 | `BlockAmountProvider`、`EntityDetailsProvider`、`EntityVariantProvider` |
| `snownee.jade.gui` | 8 | `HomeConfigScreen`、`WailaConfigScreen`、`PluginsConfigScreen` |
| `snownee.jade.network` | 7 | `RequestEntityPacket`、`RequestBlockPacket`、`ReceiveDataPacket` |
| `snownee.jade.addon.universal` | 7 | `ItemIterator`、`ItemStorageProvider`、`ItemCollector` |
| `snownee.jade.mixin` | 6 | `AbstractFurnaceBlockEntityAccess`、`EntityAccess`、`CanItemPerformAbilityAccess` |
| `snownee.jade.gui.config` | 6 | `OptionsList`、`NotUglyEditBox`、`OptionsNav` |
| `snownee.jade.impl.config.entry` | 6 | `ConfigEntry`、`StringConfigEntry`、`BooleanConfigEntry` |
| `snownee.jade.impl.template` | 6 | `TemplateEntityServerDataProvider`、`TemplateBlockServerDataProvider`、`TemplateServerDataProvider` |
| `snownee.jade.api.callback` | 6 | `JadeItemModNameCallback`、`JadeRayTraceCallback`、`JadeTooltipCollectedCallback` |
| `snownee.jade.overlay` | 5 | `DatapackBlockManager`、`DisplayHelper`、`WailaTickHandler` |
| `snownee.jade.addon.harvest` | 5 | `SimpleToolHandler`、`ShearsToolHandler`、`ToolHandler` |
| `snownee.jade.addon.core` | 5 | `BlockFaceProvider`、`DistanceProvider`、`ObjectNameProvider` |
| `snownee.jade.addon.debug` | 4 | `RegistryNameProvider`、`BlockStatesProvider`、`BlockPropertiesProvider` |
| `snownee.jade.gui.config.value` | 4 | `OptionValue`、`SliderOptionValue`、`CycleOptionValue` |
| `snownee.jade.impl.lookup` | 4 | `HierarchyLookup`、`PairHierarchyLookup`、`WrappedHierarchyLookup` |
| `snownee.jade.track` | 4 | `HealthTrackInfo`、`ProgressTrackInfo`、`TrackInfo` |
| `snownee.jade` | 3 | `JadeClient`、`JadeInternals`、`Jade` |
| `snownee.jade.api.config` | 3 | `IWailaConfig`、`IPluginConfig`、`IgnoreList` |
| `snownee.jade.api.theme` | 3 | `IThemeHelper`、`TextSetting`、`Theme` |
| `snownee.jade.impl.config` | 2 | `WailaConfig`、`PluginConfig` |
| `snownee.jade.api.platform` | 2 | `PlatformWailaClientRegistration`、`CustomEnchantPower` |
| `snownee.jade.command` | 2 | `JadeClientCommand`、`JadeServerCommand` |
| `snownee.jade.impl.theme` | 1 | `ThemeHelper` |
| `snownee.jade.compat` | 1 | `JEICompat` |
| `snownee.jade.api.fluid` | 1 | `JadeFluidObject` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `CommonProxy` | class | — | Mod | 46 | 549 | [CommonProxy.java](snownee/jade/util/CommonProxy.java) |
| `WailaClientRegistration` | class | IWailaClientRegistration | — | 57 | 548 | [WailaClientRegistration.java](snownee/jade/impl/WailaClientRegistration.java) |
| `ClientRegistrationSession` | class | — | — | 27 | 246 | [ClientRegistrationSession.java](snownee/jade/impl/ClientRegistrationSession.java) |
| `WailaConfig` | class | IWailaConfig | — | 82 | 703 | [WailaConfig.java](snownee/jade/impl/config/WailaConfig.java) |
| `EntityAccessorClientHandler` | class | AccessorClientHandler<EntityAccessor | — | 5 | 82 | [EntityAccessorClientHandler.java](snownee/jade/impl/EntityAccessorClientHandler.java) |
| `BlockAccessorClientHandler` | class | AccessorClientHandler<BlockAccessor> | — | 5 | 94 | [BlockAccessorClientHandler.java](snownee/jade/impl/BlockAccessorClientHandler.java) |
| `PluginConfig` | class | IPluginConfig | — | 25 | 332 | [PluginConfig.java](snownee/jade/impl/config/PluginConfig.java) |
| `AbstractFurnaceBlockEntityAccess` | interface | — | Mixin | 0 | 15 | [AbstractFurnaceBlockEntityAccess.java](snownee/jade/mixin/AbstractFurnaceBlockEntityAccess.java) |
| `HomeConfigScreen` | class | Screen | — | 9 | 400 | [HomeConfigScreen.java](snownee/jade/gui/HomeConfigScreen.java) |
| `OptionsList` | class | ContainerObjectSelectionList | — | 65 | 608 | [OptionsList.java](snownee/jade/gui/config/OptionsList.java) |
| `ClientProxy` | class | — | — | 31 | 355 | [ClientProxy.java](snownee/jade/util/ClientProxy.java) |
| `NotUglyEditBox` | class | AbstractWidget | — | 48 | 554 | [NotUglyEditBox.java](snownee/jade/gui/config/NotUglyEditBox.java) |
| `BlockAccessorImpl` | class | AccessorImpl | — | 26 | 267 | [BlockAccessorImpl.java](snownee/jade/impl/BlockAccessorImpl.java) |
| `WailaCommonRegistration` | class | IWailaCommonRegistration | — | 13 | 159 | [WailaCommonRegistration.java](snownee/jade/impl/WailaCommonRegistration.java) |
| `CommonRegistrationSession` | class | — | — | 10 | 98 | [CommonRegistrationSession.java](snownee/jade/impl/CommonRegistrationSession.java) |
| `EntityAccess` | interface | — | Mixin | 0 | 13 | [EntityAccess.java](snownee/jade/mixin/EntityAccess.java) |
| `CanItemPerformAbilityAccess` | interface | — | Mixin | 0 | 13 | [CanItemPerformAbilityAccess.java](snownee/jade/mixin/CanItemPerformAbilityAccess.java) |
| `IWailaClientRegistration` | interface | — | NonExtendable | 0 | 149 | [IWailaClientRegistration.java](snownee/jade/api/IWailaClientRegistration.java) |
| `PlatformWailaClientRegistration` | interface | — | — | 0 | 8 | [PlatformWailaClientRegistration.java](snownee/jade/api/platform/PlatformWailaClientRegistration.java) |
| `DatapackBlockManager` | class | — | — | 4 | 56 | [DatapackBlockManager.java](snownee/jade/overlay/DatapackBlockManager.java) |
| `WailaConfigScreen` | class | PreviewOptionsScreen | — | 2 | 203 | [WailaConfigScreen.java](snownee/jade/gui/WailaConfigScreen.java) |
| `AccessorClientHandler` | interface | — | — | 0 | 25 | [AccessorClientHandler.java](snownee/jade/api/AccessorClientHandler.java) |
| `DisplayHelper` | class | IDisplayHelper | — | 24 | 422 | [DisplayHelper.java](snownee/jade/overlay/DisplayHelper.java) |
| `RegistryNameProvider` | class | RegistryNameProvider | — | 8 | 111 | [RegistryNameProvider.java](snownee/jade/addon/debug/RegistryNameProvider.java) |
| `PluginsConfigScreen` | class | PreviewOptionsScreen | — | 3 | 81 | [PluginsConfigScreen.java](snownee/jade/gui/PluginsConfigScreen.java) |
| `PreviewOptionsScreen` | class | BaseOptionsScreen | — | 15 | 208 | [PreviewOptionsScreen.java](snownee/jade/gui/PreviewOptionsScreen.java) |
| `EntityAccessorImpl` | class | AccessorImpl | — | 18 | 201 | [EntityAccessorImpl.java](snownee/jade/impl/EntityAccessorImpl.java) |
| `ConfigEntry` | class | — | — | 10 | 69 | [ConfigEntry.java](snownee/jade/impl/config/entry/ConfigEntry.java) |
| `SessionSearchTreesMixin` | class | — | Mixin | 0 | 21 | [SessionSearchTreesMixin.java](snownee/jade/mixin/SessionSearchTreesMixin.java) |
| `StringRenderOutputMixin` | class | — | Mixin | 0 | 68 | [StringRenderOutputMixin.java](snownee/jade/mixin/StringRenderOutputMixin.java) |
| `FontMixin` | class | JadeFont | Mixin | 0 | 65 | [FontMixin.java](snownee/jade/mixin/FontMixin.java) |
| `IWailaCommonRegistration` | interface | — | NonExtendable | 0 | 23 | [IWailaCommonRegistration.java](snownee/jade/api/IWailaCommonRegistration.java) |
| `CommandBlockProvider` | enum | IBlockComponentProvider | — | 5 | 55 | [CommandBlockProvider.java](snownee/jade/addon/vanilla/CommandBlockProvider.java) |
| `SimpleToolHandler` | class | ToolHandler | — | 7 | 91 | [SimpleToolHandler.java](snownee/jade/addon/harvest/SimpleToolHandler.java) |
| `JadeClient` | class | — | — | 10 | 335 | [JadeClient.java](snownee/jade/JadeClient.java) |
| `RequestEntityPacket` | record | CustomPacketPayload | — | 2 | 42 | [RequestEntityPacket.java](snownee/jade/network/RequestEntityPacket.java) |
| `RequestBlockPacket` | record | CustomPacketPayload | — | 2 | 42 | [RequestBlockPacket.java](snownee/jade/network/RequestBlockPacket.java) |
| `BaseOptionsScreen` | class | Screen | — | 14 | 266 | [BaseOptionsScreen.java](snownee/jade/gui/BaseOptionsScreen.java) |
| `Tooltip` | class | ITooltip | — | 21 | 325 | [Tooltip.java](snownee/jade/impl/Tooltip.java) |
| `BoxElement` | class | Element | — | 17 | 401 | [BoxElement.java](snownee/jade/impl/ui/BoxElement.java) |
| `Color` | class | — | — | 24 | 285 | [Color.java](snownee/jade/api/ui/Color.java) |
| `IWailaConfig` | interface | SimpleStringRepresentable | NonExtendable | 0 | 235 | [IWailaConfig.java](snownee/jade/api/config/IWailaConfig.java) |
| `JadeClientCommand` | class | — | — | 1 | 39 | [JadeClientCommand.java](snownee/jade/command/JadeClientCommand.java) |
| `JsonConfig` | class | — | — | 7 | 130 | [JsonConfig.java](snownee/jade/util/JsonConfig.java) |
| `WailaExceptionHandler` | class | — | — | 2 | 75 | [WailaExceptionHandler.java](snownee/jade/util/WailaExceptionHandler.java) |
| `WailaTickHandler` | class | — | — | 4 | 198 | [WailaTickHandler.java](snownee/jade/overlay/WailaTickHandler.java) |
| `ShearsToolHandler` | class | SimpleToolHandler | — | 3 | 38 | [ShearsToolHandler.java](snownee/jade/addon/harvest/ShearsToolHandler.java) |
| `ToolHandler` | interface | — | — | 0 | 15 | [ToolHandler.java](snownee/jade/addon/harvest/ToolHandler.java) |
| `ItemIterator` | class | ItemIterator.SlottedItemIterator | — | 15 | 120 | [ItemIterator.java](snownee/jade/addon/universal/ItemIterator.java) |
| `ItemStorageProvider` | class | ItemStorageProvider | — | 14 | 320 | [ItemStorageProvider.java](snownee/jade/addon/universal/ItemStorageProvider.java) |
| `ReceiveDataPacket` | record | CustomPacketPayload | — | 4 | 84 | [ReceiveDataPacket.java](snownee/jade/network/ReceiveDataPacket.java) |
| `ElementHelper` | class | IElementHelper | — | 16 | 111 | [ElementHelper.java](snownee/jade/impl/ui/ElementHelper.java) |
| `StringConfigEntry` | class | ConfigEntry | — | 2 | 27 | [StringConfigEntry.java](snownee/jade/impl/config/entry/StringConfigEntry.java) |
| `BooleanConfigEntry` | class | ConfigEntry | — | 2 | 23 | [BooleanConfigEntry.java](snownee/jade/impl/config/entry/BooleanConfigEntry.java) |
| `FloatConfigEntry` | class | ConfigEntry | — | 3 | 45 | [FloatConfigEntry.java](snownee/jade/impl/config/entry/FloatConfigEntry.java) |
| `IntConfigEntry` | class | ConfigEntry | — | 3 | 46 | [IntConfigEntry.java](snownee/jade/impl/config/entry/IntConfigEntry.java) |
| `EnumConfigEntry` | class | — | — | 3 | 48 | [EnumConfigEntry.java](snownee/jade/impl/config/entry/EnumConfigEntry.java) |
| `ThemeHelper` | class | SimpleJsonResourceReloadListener | — | 14 | 215 | [ThemeHelper.java](snownee/jade/impl/theme/ThemeHelper.java) |
| `BoxStyle` | class | BoxStyle | — | 19 | 356 | [BoxStyle.java](snownee/jade/api/ui/BoxStyle.java) |
| `IPluginConfig` | interface | — | NonExtendable | 0 | 26 | [IPluginConfig.java](snownee/jade/api/config/IPluginConfig.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AbstractFurnaceBlockEntityAccess` | `AbstractFurnaceBlockEntityAccess` | [AbstractFurnaceBlockEntityAccess.java](snownee/jade/mixin/AbstractFurnaceBlockEntityAccess.java) |
| `EntityAccess` | `EntityAccess` | [EntityAccess.java](snownee/jade/mixin/EntityAccess.java) |
| `CanItemPerformAbilityAccess` | `CanItemPerformAbilityAccess` | [CanItemPerformAbilityAccess.java](snownee/jade/mixin/CanItemPerformAbilityAccess.java) |
| `SessionSearchTreesMixin` | `SessionSearchTrees` | [SessionSearchTreesMixin.java](snownee/jade/mixin/SessionSearchTreesMixin.java) |
| `StringRenderOutputMixin` | `StringRenderOutput` | [StringRenderOutputMixin.java](snownee/jade/mixin/StringRenderOutputMixin.java) |
| `FontMixin` | `Font` | [FontMixin.java](snownee/jade/mixin/FontMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `WailaConfig` | — | [WailaConfig.java](snownee/jade/impl/config/WailaConfig.java) |
| `PluginConfig` | — | [PluginConfig.java](snownee/jade/impl/config/PluginConfig.java) |
| `HomeConfigScreen` | — | [HomeConfigScreen.java](snownee/jade/gui/HomeConfigScreen.java) |
| `WailaConfigScreen` | — | [WailaConfigScreen.java](snownee/jade/gui/WailaConfigScreen.java) |
| `PluginsConfigScreen` | — | [PluginsConfigScreen.java](snownee/jade/gui/PluginsConfigScreen.java) |
| `ConfigEntry` | — | [ConfigEntry.java](snownee/jade/impl/config/entry/ConfigEntry.java) |
| `IWailaConfig` | NonExtendable | [IWailaConfig.java](snownee/jade/api/config/IWailaConfig.java) |
| `JsonConfig` | — | [JsonConfig.java](snownee/jade/util/JsonConfig.java) |
| `StringConfigEntry` | — | [StringConfigEntry.java](snownee/jade/impl/config/entry/StringConfigEntry.java) |
| `BooleanConfigEntry` | — | [BooleanConfigEntry.java](snownee/jade/impl/config/entry/BooleanConfigEntry.java) |
| `FloatConfigEntry` | — | [FloatConfigEntry.java](snownee/jade/impl/config/entry/FloatConfigEntry.java) |
| `IntConfigEntry` | — | [IntConfigEntry.java](snownee/jade/impl/config/entry/IntConfigEntry.java) |
| `EnumConfigEntry` | — | [EnumConfigEntry.java](snownee/jade/impl/config/entry/EnumConfigEntry.java) |
| `IPluginConfig` | NonExtendable | [IPluginConfig.java](snownee/jade/api/config/IPluginConfig.java) |

## 按包浏览

- `snownee.jade`（3 类）
- `snownee.jade.addon.access`（9 类）
- `snownee.jade.addon.core`（5 类）
- `snownee.jade.addon.debug`（4 类）
- `snownee.jade.addon.harvest`（5 类）
- `snownee.jade.addon.universal`（7 类）
- `snownee.jade.addon.vanilla`（37 类）
- `snownee.jade.api`（21 类）
- `snownee.jade.api.callback`（6 类）
- `snownee.jade.api.config`（3 类）
- `snownee.jade.api.fluid`（1 类）
- `snownee.jade.api.platform`（2 类）
- `snownee.jade.api.theme`（3 类）
- `snownee.jade.api.ui`（13 类）
- `snownee.jade.api.view`（10 类）
- `snownee.jade.command`（2 类）
- `snownee.jade.compat`（1 类）
- `snownee.jade.gui`（8 类）
- `snownee.jade.gui.config`（6 类）
- `snownee.jade.gui.config.value`（4 类）
- `snownee.jade.impl`（12 类）
- `snownee.jade.impl.config`（2 类）
- `snownee.jade.impl.config.entry`（6 类）
- `snownee.jade.impl.lookup`（4 类）
- `snownee.jade.impl.template`（6 类）
- `snownee.jade.impl.theme`（1 类）
- `snownee.jade.impl.ui`（16 类）
- `snownee.jade.mixin`（6 类）
- `snownee.jade.network`（7 类）
- `snownee.jade.overlay`（5 类）
- `snownee.jade.track`（4 类）
- `snownee.jade.util`（12 类）
