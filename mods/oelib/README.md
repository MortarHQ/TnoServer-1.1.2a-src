# OELib（`oelib`）

> jar：`[前置]OELib.jar` ｜ 版本：`0.2.3` ｜ 作者：Flechazo
> 规模：**206 个类 / 61 个包 / 16168 行** ｜ 事件处理类 8 ｜ Mixin 1 ｜ 注册/管理类 36

**官方描述**：Mafuyu never like you:(

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `cc.sighs.oelib.registry.action` | 22 | `RegistryBatchAction`、`RegistrationAction`、`EntityRendererAction` |
| `cc.sighs.oelib.registry.extra` | 15 | `EntityRendererRegister`、`CommandRegister`、`MenuRegister` |
| `cc.sighs.oelib.event` | 10 | `EventAutoRegistration`、`EventSide`、`EventBus` |
| `cc.sighs.oelib.dev.example` | 10 | `ExampleRegistry`、`TestPacketCommand`、`ExampleBlock` |
| `cc.sighs.oelib.config.ui.scissor` | 8 | `Rectangle`、`FloatingRectangle`、`ScissorsHandlerImpl` |
| `cc.sighs.oelib.config` | 7 | `ConfigManager`、`ClientConfigManager`、`ServerConfigManager` |
| `cc.sighs.oelib.network.api` | 7 | `NetworkAutoRegistration`、`NetworkManager`、`INetworkPacket` |
| `cc.sighs.oelib.network.serialization` | 7 | `RegistryCodec`、`ComponentIO`、`CustomCodecResolver` |
| `cc.sighs.oelib.config.api` | 6 | `ConfigEvents`、`ConfigSyncEvent`、`ConfigSaveEvent` |
| `cc.sighs.oelib.config.ui.entries` | 6 | `AbstractConfigEntry`、`FieldEntry`、`MapEntry` |
| `cc.sighs.oelib.neoforge.registry` | 5 | `ClientRegistrationHandler`、`ServerRegistrationHandler`、`MenuRegistryImpl` |
| `cc.sighs.oelib.bless` | 5 | `ShaderEvents`、`OverlayRegistry`、`FestivalToastConfig` |
| `cc.sighs.oelib.registry.api` | 5 | `ExtendedMenuTypeFactory`、`ExtendedMenuProvider`、`ScreenFactory` |
| `cc.sighs.oelib.config.util` | 4 | `ConfigSerializationUtil`、`ConfigIOUtil`、`ConfigGuiUtil` |
| `cc.sighs.oelib.config.model` | 4 | `ConfigMeta`、`ConfigValueMeta`、`ConfigSide` |
| `cc.sighs.oelib.bless.render` | 4 | `AbstractShaderOverlay`、`ChongYangOverlay`、`NewYearOverlay` |
| `cc.sighs.oelib.config.field` | 3 | `ConfigField`、`BaseFieldBuilder`、`FieldBuilder` |
| `cc.sighs.oelib.registry.spi` | 3 | `IMenuRegistry`、`IRegistrationDispatcher`、`IFuelRegistry` |
| `cc.sighs.oelib.dev.example.net` | 3 | `BigPacketSender`、`BigComponentPacket`、`OpenGuiPacket` |
| `cc.sighs.oelib.registry` | 3 | `RegistrationDispatcher`、`RegisterSupplier`、`DeferredRegister` |
| `cc.sighs.oelib.event.events` | 3 | `ClientTickEvent`、`LevelTickEvent`、`ServerTickEvent` |
| `cc.sighs.oelib.renderer` | 3 | `FluidRenderers`、`FluidRef`、`FluidRenderAttributes` |
| `cc.sighs.oelib.util` | 3 | `CodecUtils`、`ClassUtil`、`AnnotationScanUtil` |
| `cc.sighs.oelib.data.api` | 3 | `DataDriven`、`DataValidator`、`ExpressionFunction` |
| `cc.sighs.oelib.neoforge.data` | 2 | `DataManager`、`DataManagerImpl` |
| `cc.sighs.oelib.neoforge.network` | 2 | `NetworkManagerImpl`、`NeoForgeNetworkContext` |
| `cc.sighs.oelib.neoforge.bless` | 2 | `FestivalToastManager`、`OverlayRenderer` |
| `cc.sighs.oelib.neoforge.config` | 2 | `ConfigHotReloadListeners`、`ConfigEventDispatcherImpl` |
| `cc.sighs.oelib.neoforge` | 2 | `OELibNeoForgeClient`、`OELibNeoForge` |
| `cc.sighs.oelib.data` | 2 | `DataRegistry`、`DataManager` |
| `cc.sighs.oelib.icon` | 2 | `DynamicIconRegistry`、`IconVariant` |
| `cc.sighs.oelib.config.serialization` | 2 | `TomlOps`、`TomlTreeAdapter` |
| `cc.sighs.oelib.config.net` | 2 | `ConfigUpdateRequestPacket`、`ConfigSyncPacket` |
| `cc.sighs.oelib.network.util` | 2 | `NetworkUtil`、`ReflectionUtil` |
| `cc.sighs.oelib.config.ui` | 2 | `ConfigUiHint`、`ConfigUiType` |
| `cc.sighs.oelib.data.mvel.gen` | 2 | `ExpressionFunctionRegistry`、`ExpressionFunctionsRegistrar` |
| `cc.sighs.oelib.neoforge.event` | 2 | `DataReloadEvent`、`TickBridge` |
| `cc.sighs.oelib.dev` | 2 | `DevConfig`、`ExampleInit` |
| `cc.sighs.oelib.data.net` | 2 | `DataSyncPacket`、`DataSyncChunkPacket` |
| `cc.sighs.oelib.network.chunk` | 2 | `GenericChunkPacket`、`GenericChunkAssembler` |
| … | | 其余 21 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DataManager` | class | SimpleJsonResourceReloadListener | EventBusSubscriber | 22 | 339 | [DataManager.java](cc/sighs/oelib/neoforge/data/DataManager.java) |
| `NetworkManagerImpl` | class | INetworkManager | EventBusSubscriber | 5 | 334 | [NetworkManagerImpl.java](cc/sighs/oelib/neoforge/network/NetworkManagerImpl.java) |
| `DataManagerImpl` | class | IDataManager | EventBusSubscriber | 14 | 113 | [DataManagerImpl.java](cc/sighs/oelib/neoforge/data/DataManagerImpl.java) |
| `ClientRegistrationHandler` | class | — | — | 13 | 217 | [ClientRegistrationHandler.java](cc/sighs/oelib/neoforge/registry/ClientRegistrationHandler.java) |
| `FestivalToastManager` | class | — | EventBusSubscriber、OnlyIn | 2 | 35 | [FestivalToastManager.java](cc/sighs/oelib/neoforge/bless/FestivalToastManager.java) |
| `ConfigHotReloadListeners` | class | — | EventBusSubscriber | 2 | 26 | [ConfigHotReloadListeners.java](cc/sighs/oelib/neoforge/config/ConfigHotReloadListeners.java) |
| `ServerRegistrationHandler` | class | — | — | 14 | 259 | [ServerRegistrationHandler.java](cc/sighs/oelib/neoforge/registry/ServerRegistrationHandler.java) |
| `OverlayRenderer` | class | — | EventBusSubscriber | 1 | 44 | [OverlayRenderer.java](cc/sighs/oelib/neoforge/bless/OverlayRenderer.java) |
| `ConfigScreen` | class | Screen | — | 20 | 669 | [ConfigScreen.java](cc/sighs/oelib/config/ui/screen/ConfigScreen.java) |
| `ConfigEvents` | class | — | — | 5 | 40 | [ConfigEvents.java](cc/sighs/oelib/config/api/ConfigEvents.java) |
| `OELibNeoForgeClient` | class | — | Mod | 0 | 21 | [OELibNeoForgeClient.java](cc/sighs/oelib/neoforge/OELibNeoForgeClient.java) |
| `ConfigManager` | class | — | — | 17 | 131 | [ConfigManager.java](cc/sighs/oelib/config/ConfigManager.java) |
| `ConfigFixRegistry` | class | — | — | 13 | 193 | [ConfigFixRegistry.java](cc/sighs/oelib/config/datafix/ConfigFixRegistry.java) |
| `NeoForgeIconHandlerMixin` | class | — | Mixin | 0 | 26 | [NeoForgeIconHandlerMixin.java](cc/sighs/oelib/neoforge/mixin/NeoForgeIconHandlerMixin.java) |
| `ClientConfigManager` | class | ResourceManagerReloadListener | — | 2 | 49 | [ClientConfigManager.java](cc/sighs/oelib/config/ClientConfigManager.java) |
| `OELibNeoForge` | class | — | Mod | 0 | 16 | [OELibNeoForge.java](cc/sighs/oelib/neoforge/OELibNeoForge.java) |
| `ServerConfigManager` | class | ResourceManagerReloadListener | — | 7 | 143 | [ServerConfigManager.java](cc/sighs/oelib/config/ServerConfigManager.java) |
| `DataRegistry` | class | — | — | 15 | 183 | [DataRegistry.java](cc/sighs/oelib/data/DataRegistry.java) |
| `NetworkAutoRegistration` | class | — | — | 4 | 120 | [NetworkAutoRegistration.java](cc/sighs/oelib/network/api/NetworkAutoRegistration.java) |
| `DynamicIconRegistry` | class | — | — | 17 | 172 | [DynamicIconRegistry.java](cc/sighs/oelib/icon/DynamicIconRegistry.java) |
| `EventAutoRegistration` | class | — | — | 3 | 197 | [EventAutoRegistration.java](cc/sighs/oelib/event/EventAutoRegistration.java) |
| `ConfigSerializationUtil` | class | — | — | 16 | 362 | [ConfigSerializationUtil.java](cc/sighs/oelib/config/util/ConfigSerializationUtil.java) |
| `NetworkManager` | class | — | — | 1 | 59 | [NetworkManager.java](cc/sighs/oelib/network/api/NetworkManager.java) |
| `INetworkManager` | interface | — | — | 0 | 30 | [INetworkManager.java](cc/sighs/oelib/network/spi/INetworkManager.java) |
| `ConfigEventDispatcherImpl` | class | IConfigEventDispatcher | — | 5 | 37 | [ConfigEventDispatcherImpl.java](cc/sighs/oelib/neoforge/config/ConfigEventDispatcherImpl.java) |
| `MenuRegistryImpl` | class | IMenuRegistry | — | 2 | 22 | [MenuRegistryImpl.java](cc/sighs/oelib/neoforge/registry/MenuRegistryImpl.java) |
| `EventSide` | enum | — | — | 0 | 8 | [EventSide.java](cc/sighs/oelib/event/EventSide.java) |
| `EventBus` | class | — | — | 22 | 356 | [EventBus.java](cc/sighs/oelib/event/EventBus.java) |
| `Rectangle` | class | Cloneable | — | 53 | 549 | [Rectangle.java](cc/sighs/oelib/config/ui/scissor/Rectangle.java) |
| `FloatingRectangle` | class | Cloneable | — | 49 | 529 | [FloatingRectangle.java](cc/sighs/oelib/config/ui/scissor/FloatingRectangle.java) |
| `TomlOps` | class | AbstractStringBuilder | — | 39 | 407 | [TomlOps.java](cc/sighs/oelib/config/serialization/TomlOps.java) |
| `ConfigField` | class | BaseFieldBuilder | — | 17 | 248 | [ConfigField.java](cc/sighs/oelib/config/field/ConfigField.java) |
| `DataManager` | class | — | — | 13 | 78 | [DataManager.java](cc/sighs/oelib/data/DataManager.java) |
| `IMenuRegistry` | interface | — | — | 0 | 14 | [IMenuRegistry.java](cc/sighs/oelib/registry/spi/IMenuRegistry.java) |
| `ShaderEvents` | class | — | OnlyIn | 1 | 23 | [ShaderEvents.java](cc/sighs/oelib/bless/ShaderEvents.java) |
| `NeoForgeConfigEvent` | class | Event | — | 1 | 50 | [NeoForgeConfigEvent.java](cc/sighs/oelib/neoforge/config/event/NeoForgeConfigEvent.java) |
| `AbstractConfigEntry` | class | GuiEventListener | — | 15 | 70 | [AbstractConfigEntry.java](cc/sighs/oelib/config/ui/entries/AbstractConfigEntry.java) |
| `ConfigUpdateRequestPacket` | record | INetworkPacket<ConfigUpdateRequestPa | NetworkPacket | 1 | 34 | [ConfigUpdateRequestPacket.java](cc/sighs/oelib/config/net/ConfigUpdateRequestPacket.java) |
| `ConfigSyncPacket` | record | INetworkPacket<ConfigSyncPacket> | NetworkPacket | 1 | 22 | [ConfigSyncPacket.java](cc/sighs/oelib/config/net/ConfigSyncPacket.java) |
| `ConfigSyncEvent` | record | — | — | 0 | 7 | [ConfigSyncEvent.java](cc/sighs/oelib/config/api/ConfigSyncEvent.java) |
| `ConfigSaveEvent` | record | — | — | 0 | 7 | [ConfigSaveEvent.java](cc/sighs/oelib/config/api/ConfigSaveEvent.java) |
| `ConfigLoadEvent` | record | — | — | 0 | 7 | [ConfigLoadEvent.java](cc/sighs/oelib/config/api/ConfigLoadEvent.java) |
| `ConfigChangedEvent` | record | — | — | 0 | 7 | [ConfigChangedEvent.java](cc/sighs/oelib/config/api/ConfigChangedEvent.java) |
| `ConfigIOUtil` | class | — | — | 9 | 249 | [ConfigIOUtil.java](cc/sighs/oelib/config/util/ConfigIOUtil.java) |
| `IConfigEventDispatcher` | interface | — | — | 0 | 19 | [IConfigEventDispatcher.java](cc/sighs/oelib/config/spi/IConfigEventDispatcher.java) |
| `INetworkPacket` | interface | — | — | 0 | 65 | [INetworkPacket.java](cc/sighs/oelib/network/api/INetworkPacket.java) |
| `NetworkPacketTypes` | class | — | — | 1 | 40 | [NetworkPacketTypes.java](cc/sighs/oelib/network/api/NetworkPacketTypes.java) |
| `NetworkPacket` | @interface | — | Retention、Target | 0 | 21 | [NetworkPacket.java](cc/sighs/oelib/network/api/NetworkPacket.java) |
| `RegistryCodec` | @interface | — | Retention、Target | 0 | 13 | [RegistryCodec.java](cc/sighs/oelib/network/serialization/RegistryCodec.java) |
| `NetworkUtil` | class | — | — | 10 | 117 | [NetworkUtil.java](cc/sighs/oelib/network/util/NetworkUtil.java) |
| `RegistrationDispatcherImpl` | class | ClientRegistrationHandler.ClientEven | — | 1 | 27 | [RegistrationDispatcherImpl.java](cc/sighs/oelib/neoforge/registry/RegistrationDispatcherImpl.java) |
| `FuelRegistryImpl` | class | IFuelRegistry | — | 1 | 12 | [FuelRegistryImpl.java](cc/sighs/oelib/neoforge/registry/FuelRegistryImpl.java) |
| `ExampleRegistry` | class | — | — | 2 | 47 | [ExampleRegistry.java](cc/sighs/oelib/dev/example/ExampleRegistry.java) |
| `TestPacketCommand` | class | — | — | 2 | 36 | [TestPacketCommand.java](cc/sighs/oelib/dev/example/TestPacketCommand.java) |
| `BigPacketSender` | class | — | — | 9 | 585 | [BigPacketSender.java](cc/sighs/oelib/dev/example/net/BigPacketSender.java) |
| `ConfigUnit` | class | — | — | 11 | 124 | [ConfigUnit.java](cc/sighs/oelib/config/ConfigUnit.java) |
| `ConfigUiHint` | class | — | — | 11 | 68 | [ConfigUiHint.java](cc/sighs/oelib/config/ui/ConfigUiHint.java) |
| `ScissorsHandlerImpl` | class | ScissorsHandler | — | 6 | 80 | [ScissorsHandlerImpl.java](cc/sighs/oelib/config/ui/scissor/ScissorsHandlerImpl.java) |
| `ConfigGuiUtil` | class | — | — | 8 | 127 | [ConfigGuiUtil.java](cc/sighs/oelib/config/util/ConfigGuiUtil.java) |
| `ConfigMeta` | class | — | — | 11 | 81 | [ConfigMeta.java](cc/sighs/oelib/config/model/ConfigMeta.java) |

## 事件处理（`@SubscribeEvent`，8 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientRegistrationHandler` | 11 | `cc.sighs.oelib.neoforge.registry` | [ClientRegistrationHandler.java](cc/sighs/oelib/neoforge/registry/ClientRegistrationHandler.java) |
| `ServerRegistrationHandler` | 8 | `cc.sighs.oelib.neoforge.registry` | [ServerRegistrationHandler.java](cc/sighs/oelib/neoforge/registry/ServerRegistrationHandler.java) |
| `DataManager` | 2 | `cc.sighs.oelib.neoforge.data` | [DataManager.java](cc/sighs/oelib/neoforge/data/DataManager.java) |
| `FestivalToastManager` | 2 | `cc.sighs.oelib.neoforge.bless` | [FestivalToastManager.java](cc/sighs/oelib/neoforge/bless/FestivalToastManager.java) |
| `ConfigHotReloadListeners` | 2 | `cc.sighs.oelib.neoforge.config` | [ConfigHotReloadListeners.java](cc/sighs/oelib/neoforge/config/ConfigHotReloadListeners.java) |
| `NetworkManagerImpl` | 1 | `cc.sighs.oelib.neoforge.network` | [NetworkManagerImpl.java](cc/sighs/oelib/neoforge/network/NetworkManagerImpl.java) |
| `DataManagerImpl` | 1 | `cc.sighs.oelib.neoforge.data` | [DataManagerImpl.java](cc/sighs/oelib/neoforge/data/DataManagerImpl.java) |
| `OverlayRenderer` | 1 | `cc.sighs.oelib.neoforge.bless` | [OverlayRenderer.java](cc/sighs/oelib/neoforge/bless/OverlayRenderer.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `NeoForgeIconHandlerMixin` | `NeoForgeIconHandler` | [NeoForgeIconHandlerMixin.java](cc/sighs/oelib/neoforge/mixin/NeoForgeIconHandlerMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHotReloadListeners` | EventBusSubscriber | [ConfigHotReloadListeners.java](cc/sighs/oelib/neoforge/config/ConfigHotReloadListeners.java) |
| `ConfigScreen` | — | [ConfigScreen.java](cc/sighs/oelib/config/ui/screen/ConfigScreen.java) |
| `ConfigEvents` | — | [ConfigEvents.java](cc/sighs/oelib/config/api/ConfigEvents.java) |
| `ConfigManager` | — | [ConfigManager.java](cc/sighs/oelib/config/ConfigManager.java) |
| `ConfigFixRegistry` | — | [ConfigFixRegistry.java](cc/sighs/oelib/config/datafix/ConfigFixRegistry.java) |
| `ClientConfigManager` | — | [ClientConfigManager.java](cc/sighs/oelib/config/ClientConfigManager.java) |
| `ServerConfigManager` | — | [ServerConfigManager.java](cc/sighs/oelib/config/ServerConfigManager.java) |
| `ConfigSerializationUtil` | — | [ConfigSerializationUtil.java](cc/sighs/oelib/config/util/ConfigSerializationUtil.java) |
| `ConfigEventDispatcherImpl` | — | [ConfigEventDispatcherImpl.java](cc/sighs/oelib/neoforge/config/ConfigEventDispatcherImpl.java) |
| `ConfigField` | — | [ConfigField.java](cc/sighs/oelib/config/field/ConfigField.java) |
| `NeoForgeConfigEvent` | — | [NeoForgeConfigEvent.java](cc/sighs/oelib/neoforge/config/event/NeoForgeConfigEvent.java) |
| `AbstractConfigEntry` | — | [AbstractConfigEntry.java](cc/sighs/oelib/config/ui/entries/AbstractConfigEntry.java) |
| `ConfigUpdateRequestPacket` | NetworkPacket | [ConfigUpdateRequestPacket.java](cc/sighs/oelib/config/net/ConfigUpdateRequestPacket.java) |
| `ConfigSyncPacket` | NetworkPacket | [ConfigSyncPacket.java](cc/sighs/oelib/config/net/ConfigSyncPacket.java) |
| `ConfigSyncEvent` | — | [ConfigSyncEvent.java](cc/sighs/oelib/config/api/ConfigSyncEvent.java) |
| `ConfigSaveEvent` | — | [ConfigSaveEvent.java](cc/sighs/oelib/config/api/ConfigSaveEvent.java) |
| `ConfigLoadEvent` | — | [ConfigLoadEvent.java](cc/sighs/oelib/config/api/ConfigLoadEvent.java) |
| `ConfigChangedEvent` | — | [ConfigChangedEvent.java](cc/sighs/oelib/config/api/ConfigChangedEvent.java) |
| `ConfigIOUtil` | — | [ConfigIOUtil.java](cc/sighs/oelib/config/util/ConfigIOUtil.java) |
| `IConfigEventDispatcher` | — | [IConfigEventDispatcher.java](cc/sighs/oelib/config/spi/IConfigEventDispatcher.java) |
| `ConfigUnit` | — | [ConfigUnit.java](cc/sighs/oelib/config/ConfigUnit.java) |
| `ConfigUiHint` | — | [ConfigUiHint.java](cc/sighs/oelib/config/ui/ConfigUiHint.java) |
| `ConfigGuiUtil` | — | [ConfigGuiUtil.java](cc/sighs/oelib/config/util/ConfigGuiUtil.java) |
| `ConfigMeta` | — | [ConfigMeta.java](cc/sighs/oelib/config/model/ConfigMeta.java) |
| `ConfigValueMeta` | — | [ConfigValueMeta.java](cc/sighs/oelib/config/model/ConfigValueMeta.java) |
| `DevConfig` | — | [DevConfig.java](cc/sighs/oelib/dev/DevConfig.java) |
| `ConfigCodec` | — | [ConfigCodec.java](cc/sighs/oelib/config/ConfigCodec.java) |
| `ConfigRecordCodecBuilder` | — | [ConfigRecordCodecBuilder.java](cc/sighs/oelib/config/ConfigRecordCodecBuilder.java) |
| `ConfigAccess` | — | [ConfigAccess.java](cc/sighs/oelib/config/ConfigAccess.java) |
| `ConfigUiType` | — | [ConfigUiType.java](cc/sighs/oelib/config/ui/ConfigUiType.java) |

## 按包浏览

- `cc.sighs.oelib`（2 类）
- `cc.sighs.oelib.bless`（5 类）
- `cc.sighs.oelib.bless.render`（4 类）
- `cc.sighs.oelib.bless.util`（1 类）
- `cc.sighs.oelib.config`（7 类）
- `cc.sighs.oelib.config.api`（6 类）
- `cc.sighs.oelib.config.datafix`（1 类）
- `cc.sighs.oelib.config.field`（3 类）
- `cc.sighs.oelib.config.model`（4 类）
- `cc.sighs.oelib.config.net`（2 类）
- `cc.sighs.oelib.config.serialization`（2 类）
- `cc.sighs.oelib.config.spi`（1 类）
- `cc.sighs.oelib.config.ui`（2 类）
- `cc.sighs.oelib.config.ui.entries`（6 类）
- `cc.sighs.oelib.config.ui.scissor`（8 类）
- `cc.sighs.oelib.config.ui.screen`（1 类）
- `cc.sighs.oelib.config.ui.widget`（2 类）
- `cc.sighs.oelib.config.util`（4 类）
- `cc.sighs.oelib.data`（2 类）
- `cc.sighs.oelib.data.api`（3 类）
- `cc.sighs.oelib.data.mvel`（2 类）
- `cc.sighs.oelib.data.mvel.gen`（2 类）
- `cc.sighs.oelib.data.mvel.processor`（1 类）
- `cc.sighs.oelib.data.net`（2 类）
- `cc.sighs.oelib.data.spi`（1 类）
- `cc.sighs.oelib.dev`（2 类）
- `cc.sighs.oelib.dev.example`（10 类）
- `cc.sighs.oelib.dev.example.event`（1 类）
- `cc.sighs.oelib.dev.example.net`（3 类）
- `cc.sighs.oelib.event`（10 类）
- `cc.sighs.oelib.event.events`（3 类）
- `cc.sighs.oelib.icon`（2 类）
- `cc.sighs.oelib.neoforge`（2 类）
- `cc.sighs.oelib.neoforge.bless`（2 类）
- `cc.sighs.oelib.neoforge.client.renderer`（1 类）
- `cc.sighs.oelib.neoforge.config`（2 类）
- `cc.sighs.oelib.neoforge.config.event`（1 类）
- `cc.sighs.oelib.neoforge.data`（2 类）
- `cc.sighs.oelib.neoforge.event`（2 类）
- `cc.sighs.oelib.neoforge.example`（1 类）
- `cc.sighs.oelib.neoforge.example.oelib_mvel_gen`（1 类）
- `cc.sighs.oelib.neoforge.mixin`（1 类）
- `cc.sighs.oelib.neoforge.network`（2 类）
- `cc.sighs.oelib.neoforge.platform`（1 类）
- `cc.sighs.oelib.neoforge.registry`（5 类）
- `cc.sighs.oelib.neoforge.util`（1 类）
- `cc.sighs.oelib.network.api`（7 类）
- `cc.sighs.oelib.network.chunk`（2 类）
- `cc.sighs.oelib.network.serialization`（7 类）
- `cc.sighs.oelib.network.spi`（1 类）
- `cc.sighs.oelib.network.util`（2 类）
- `cc.sighs.oelib.platform`（2 类）
- `cc.sighs.oelib.registry`（3 类）
- `cc.sighs.oelib.registry.action`（22 类）
- `cc.sighs.oelib.registry.api`（5 类）
- `cc.sighs.oelib.registry.extra`（15 类）
- `cc.sighs.oelib.registry.spi`（3 类）
- `cc.sighs.oelib.renderer`（3 类）
- `cc.sighs.oelib.renderer.spi`（1 类）
- `cc.sighs.oelib.util`（3 类）
- `cc.sighs.oelib.util.spi`（1 类）
