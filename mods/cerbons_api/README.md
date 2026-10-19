# Cerbons API（`cerbons_api`）

> jar：`[前置]CerbonsAPI.jar` ｜ 版本：`1.3.0` ｜ 作者：CerbonXD
> 规模：**85 个类 / 31 个包 / 4332 行** ｜ 事件处理类 2 ｜ Mixin 8 ｜ 注册/管理类 17

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.cerbon.cerbons_api.api.static_utilities` | 13 | `RegistryUtils`、`MathUtils`、`PacketUtils` |
| `com.cerbon.cerbons_api.api.network` | 7 | `DelayedPacketRegistrationHandler`、`PacketRegistrationHandler`、`INetworkHandler` |
| `com.cerbon.cerbons_api.mixin.multipart_entities` | 6 | `EntityMixin`、`AABBMixin`、`ProjectileMixin` |
| `com.cerbon.cerbons_api.api.general.event` | 5 | `EventSeries`、`EventScheduler`、`IEvent` |
| `com.cerbon.cerbons_api.api.multipart_entities.entity` | 5 | `EntityBounds`、`EntityPart`、`MultipartEntity` |
| `com.cerbon.cerbons_api.api.general.particle` | 5 | `ClientParticleBuilder`、`SimpleParticle`、`SimpleParticleProvider` |
| `com.cerbon.cerbons_api.neoforge.platform` | 4 | `NeoForgeRegistryHelper`、`NeoForgeCapabilityHelper`、`NeoForgeMenuTypeHelper` |
| `com.cerbon.cerbons_api.platform.services` | 4 | `IRegistryHelper`、`ICapabilityHelper`、`IMenuTypeHelper` |
| `com.cerbon.cerbons_api.api.multipart_entities.util` | 4 | `OrientedBox`、`CompoundOrientedBox`、`QuaternionD` |
| `com.cerbon.cerbons_api.api.registry` | 4 | `ResourcefulRegistry`、`RegistryEntries`、`RegistryEntry` |
| `com.cerbon.cerbons_api.api.network.data` | 4 | `CommonPacketWrapper`、`PacketContext`、`PacketContainer` |
| `com.cerbon.cerbons_api.api.general.data` | 3 | `HistoricalData`、`WeakHashPredicate`、`BooleanFlag` |
| `com.cerbon.cerbons_api.mixin.multipart_entities.client` | 2 | `EntityRenderDispatcherMixin`、`MultiplayerGameModeMixin` |
| `com.cerbon.cerbons_api.neoforge.registry` | 2 | `NeoForgeResourcefulRegistry`、`NeoForgeRegistryEntry` |
| `com.cerbon.cerbons_api.neoforge.event` | 1 | `NeoForgeEvents` |
| `com.cerbon.cerbons_api.neoforge.network` | 1 | `NeoForgeNetworkHandler` |
| `com.cerbon.cerbons_api.api.item.registry` | 1 | `ItemRegistry` |
| `com.cerbon.cerbons_api.neoforge` | 1 | `CerbonsAPINeoForge` |
| `com.cerbon.cerbons_api.api.block.registry` | 1 | `BlockRegistry` |
| `com.cerbon.cerbons_api.neoforge.attachment.saved_data` | 1 | `LevelEventScheduler` |
| `com.cerbon.cerbons_api.packet` | 1 | `CerbonsApiPacketHandler` |
| `com.cerbon.cerbons_api.api.network.exceptions` | 1 | `RegistrationException` |
| `com.cerbon.cerbons_api.packet.custom` | 1 | `MultipartEntityInteractionC2SPacket` |
| `com.cerbon.cerbons_api.api.multipart_entities.client` | 1 | `PlayerInteractMultipartEntity` |
| `architectury_inject_CERBONsAPI_common_a31486a8fa8344528de97a237b22c338_f6f9d45edca97e508e13c36c55afb1f41050fbd4ad9c993def34fa3fed489d35CerbonsAPICommon121130devjar` | 1 | `PlatformMethods` |
| `com.cerbon.cerbons_api` | 1 | `CerbonsAPI` |
| `com.cerbon.cerbons_api.platform` | 1 | `Services` |
| `com.cerbon.cerbons_api.api.item.enums` | 1 | `ToolType` |
| `com.cerbon.cerbons_api.api.general.random` | 1 | `WeightedRandom` |
| `com.cerbon.cerbons_api.api.general.math` | 1 | `ReferencedAxisRotator` |
| `com.cerbon.cerbons_api.util` | 1 | `Constants` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeEvents` | class | — | EventBusSubscriber | 2 | 21 | [NeoForgeEvents.java](com/cerbon/cerbons_api/neoforge/event/NeoForgeEvents.java) |
| `DelayedPacketRegistrationHandler` | class | IPacketRegistrar | — | 3 | 38 | [DelayedPacketRegistrationHandler.java](com/cerbon/cerbons_api/api/network/DelayedPacketRegistrationHandler.java) |
| `PacketRegistrationHandler` | class | INetworkHandler | — | 3 | 39 | [PacketRegistrationHandler.java](com/cerbon/cerbons_api/api/network/PacketRegistrationHandler.java) |
| `NeoForgeNetworkHandler` | class | PacketRegistrationHandler | — | 5 | 81 | [NeoForgeNetworkHandler.java](com/cerbon/cerbons_api/neoforge/network/NeoForgeNetworkHandler.java) |
| `ItemRegistry` | class | — | — | 19 | 152 | [ItemRegistry.java](com/cerbon/cerbons_api/api/item/registry/ItemRegistry.java) |
| `CerbonsAPINeoForge` | class | — | Mod | 1 | 28 | [CerbonsAPINeoForge.java](com/cerbon/cerbons_api/neoforge/CerbonsAPINeoForge.java) |
| `BlockRegistry` | class | — | — | 12 | 88 | [BlockRegistry.java](com/cerbon/cerbons_api/api/block/registry/BlockRegistry.java) |
| `LevelEventScheduler` | class | SavedData | — | 3 | 43 | [LevelEventScheduler.java](com/cerbon/cerbons_api/neoforge/attachment/saved_data/LevelEventScheduler.java) |
| `CerbonsApiPacketHandler` | class | — | — | 1 | 16 | [CerbonsApiPacketHandler.java](com/cerbon/cerbons_api/packet/CerbonsApiPacketHandler.java) |
| `EntityMixin` | class | — | Mixin | 2 | 29 | [EntityMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/EntityMixin.java) |
| `EntityRenderDispatcherMixin` | class | — | Mixin | 1 | 48 | [EntityRenderDispatcherMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/EntityRenderDispatcherMixin.java) |
| `INetworkHandler` | interface | — | — | 0 | 61 | [INetworkHandler.java](com/cerbon/cerbons_api/api/network/INetworkHandler.java) |
| `RegistryUtils` | class | — | — | 6 | 40 | [RegistryUtils.java](com/cerbon/cerbons_api/api/static_utilities/RegistryUtils.java) |
| `EventSeries` | class | IEvent | — | 4 | 47 | [EventSeries.java](com/cerbon/cerbons_api/api/general/event/EventSeries.java) |
| `EventScheduler` | class | — | — | 2 | 31 | [EventScheduler.java](com/cerbon/cerbons_api/api/general/event/EventScheduler.java) |
| `NeoForgeRegistryHelper` | class | IRegistryHelper | — | 1 | 14 | [NeoForgeRegistryHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeRegistryHelper.java) |
| `IRegistryHelper` | interface | — | — | 0 | 9 | [IRegistryHelper.java](com/cerbon/cerbons_api/platform/services/IRegistryHelper.java) |
| `OrientedBox` | class | — | — | 26 | 416 | [OrientedBox.java](com/cerbon/cerbons_api/api/multipart_entities/util/OrientedBox.java) |
| `NeoForgeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 3 | 35 | [NeoForgeResourcefulRegistry.java](com/cerbon/cerbons_api/neoforge/registry/NeoForgeResourcefulRegistry.java) |
| `NeoForgeRegistryEntry` | class | RegistryEntry<T> | — | 2 | 24 | [NeoForgeRegistryEntry.java](com/cerbon/cerbons_api/neoforge/registry/NeoForgeRegistryEntry.java) |
| `AABBMixin` | class | — | Mixin | 1 | 19 | [AABBMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/AABBMixin.java) |
| `ProjectileMixin` | class | Entity | Mixin | 1 | 33 | [ProjectileMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ProjectileMixin.java) |
| `ShapesMixin` | class | — | Mixin | 1 | 21 | [ShapesMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ShapesMixin.java) |
| `InvokerArrayVoxelShape` | interface | — | Mixin | 0 | 16 | [InvokerArrayVoxelShape.java](com/cerbon/cerbons_api/mixin/multipart_entities/InvokerArrayVoxelShape.java) |
| `VoxelShapeMixin` | class | — | Mixin | 1 | 21 | [VoxelShapeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/VoxelShapeMixin.java) |
| `MultiplayerGameModeMixin` | class | — | Mixin | 3 | 90 | [MultiplayerGameModeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/MultiplayerGameModeMixin.java) |
| `RegistrationException` | class | RuntimeException | — | 0 | 23 | [RegistrationException.java](com/cerbon/cerbons_api/api/network/exceptions/RegistrationException.java) |
| `ResourcefulRegistry` | interface | — | — | 0 | 22 | [ResourcefulRegistry.java](com/cerbon/cerbons_api/api/registry/ResourcefulRegistry.java) |
| `RegistryEntries` | class | — | — | 2 | 19 | [RegistryEntries.java](com/cerbon/cerbons_api/api/registry/RegistryEntries.java) |
| `RegistryEntry` | interface | — | — | 0 | 12 | [RegistryEntry.java](com/cerbon/cerbons_api/api/registry/RegistryEntry.java) |
| `MultipartEntityInteractionC2SPacket` | class | — | — | 4 | 88 | [MultipartEntityInteractionC2SPacket.java](com/cerbon/cerbons_api/packet/custom/MultipartEntityInteractionC2SPacket.java) |
| `MathUtils` | class | — | — | 17 | 241 | [MathUtils.java](com/cerbon/cerbons_api/api/static_utilities/MathUtils.java) |
| `EntityBounds` | class | — | — | 16 | 186 | [EntityBounds.java](com/cerbon/cerbons_api/api/multipart_entities/entity/EntityBounds.java) |
| `EntityPart` | class | — | — | 15 | 130 | [EntityPart.java](com/cerbon/cerbons_api/api/multipart_entities/entity/EntityPart.java) |
| `HistoricalData` | class | List<T> | — | 24 | 173 | [HistoricalData.java](com/cerbon/cerbons_api/api/general/data/HistoricalData.java) |
| `NeoForgeCapabilityHelper` | class | ICapabilityHelper | — | 1 | 14 | [NeoForgeCapabilityHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeCapabilityHelper.java) |
| `ICapabilityHelper` | interface | — | — | 0 | 9 | [ICapabilityHelper.java](com/cerbon/cerbons_api/platform/services/ICapabilityHelper.java) |
| `PacketUtils` | class | — | — | 4 | 39 | [PacketUtils.java](com/cerbon/cerbons_api/api/static_utilities/PacketUtils.java) |
| `CapabilityUtils` | class | — | — | 1 | 12 | [CapabilityUtils.java](com/cerbon/cerbons_api/api/static_utilities/CapabilityUtils.java) |
| `ClientParticleBuilder` | class | — | — | 14 | 146 | [ClientParticleBuilder.java](com/cerbon/cerbons_api/api/general/particle/ClientParticleBuilder.java) |
| `NeoForgeMenuTypeHelper` | class | IMenuTypeHelper | — | 3 | 41 | [NeoForgeMenuTypeHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeMenuTypeHelper.java) |
| `IMenuTypeHelper` | interface | — | — | 0 | 35 | [IMenuTypeHelper.java](com/cerbon/cerbons_api/platform/services/IMenuTypeHelper.java) |
| `IPacketRegistrar` | interface | — | — | 0 | 18 | [IPacketRegistrar.java](com/cerbon/cerbons_api/api/network/IPacketRegistrar.java) |
| `Network` | class | — | — | 2 | 21 | [Network.java](com/cerbon/cerbons_api/api/network/Network.java) |
| `CommonNetwork` | record | — | — | 2 | 36 | [CommonNetwork.java](com/cerbon/cerbons_api/api/network/CommonNetwork.java) |
| `CommonPacketWrapper` | record | CustomPacketPayload | — | 1 | 13 | [CommonPacketWrapper.java](com/cerbon/cerbons_api/api/network/data/CommonPacketWrapper.java) |
| `PacketContext` | record | — | — | 0 | 10 | [PacketContext.java](com/cerbon/cerbons_api/api/network/data/PacketContext.java) |
| `PacketContainer` | record | — | — | 3 | 39 | [PacketContainer.java](com/cerbon/cerbons_api/api/network/data/PacketContainer.java) |
| `MenuTypeUtils` | class | — | — | 3 | 33 | [MenuTypeUtils.java](com/cerbon/cerbons_api/api/static_utilities/MenuTypeUtils.java) |
| `CompoundOrientedBox` | class | AABB | — | 11 | 230 | [CompoundOrientedBox.java](com/cerbon/cerbons_api/api/multipart_entities/util/CompoundOrientedBox.java) |
| `IEvent` | interface | — | — | 0 | 12 | [IEvent.java](com/cerbon/cerbons_api/api/general/event/IEvent.java) |
| `TimedEvent` | class | IEvent | — | 4 | 52 | [TimedEvent.java](com/cerbon/cerbons_api/api/general/event/TimedEvent.java) |
| `Event` | class | IEvent | — | 4 | 42 | [Event.java](com/cerbon/cerbons_api/api/general/event/Event.java) |
| `SimpleParticle` | class | TextureSheetParticle | — | 18 | 192 | [SimpleParticle.java](com/cerbon/cerbons_api/api/general/particle/SimpleParticle.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 6 | 41 | [NeoForgePlatformHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgePlatformHelper.java) |
| `Dispatcher` | class | — | — | 11 | 65 | [Dispatcher.java](com/cerbon/cerbons_api/api/network/Dispatcher.java) |
| `MiscUtils` | class | — | — | 7 | 35 | [MiscUtils.java](com/cerbon/cerbons_api/api/static_utilities/MiscUtils.java) |
| `RandomUtils` | class | — | — | 7 | 48 | [RandomUtils.java](com/cerbon/cerbons_api/api/static_utilities/RandomUtils.java) |
| `VecUtils` | class | — | — | 8 | 63 | [VecUtils.java](com/cerbon/cerbons_api/api/static_utilities/VecUtils.java) |
| `MultipartEntity` | interface | — | — | 0 | 9 | [MultipartEntity.java](com/cerbon/cerbons_api/api/multipart_entities/entity/MultipartEntity.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeEvents` | 2 | `com.cerbon.cerbons_api.neoforge.event` | [NeoForgeEvents.java](com/cerbon/cerbons_api/neoforge/event/NeoForgeEvents.java) |
| `NeoForgeNetworkHandler` | 1 | `com.cerbon.cerbons_api.neoforge.network` | [NeoForgeNetworkHandler.java](com/cerbon/cerbons_api/neoforge/network/NeoForgeNetworkHandler.java) |

## Mixin（8 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `EntityMixin` | `Entity` | [EntityMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/EntityMixin.java) |
| `EntityRenderDispatcherMixin` | `EntityRenderDispatcher` | [EntityRenderDispatcherMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/EntityRenderDispatcherMixin.java) |
| `AABBMixin` | `AABB` | [AABBMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/AABBMixin.java) |
| `ProjectileMixin` | `Projectile` | [ProjectileMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ProjectileMixin.java) |
| `ShapesMixin` | `Shapes` | [ShapesMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ShapesMixin.java) |
| `InvokerArrayVoxelShape` | `InvokerArrayVoxelShape` | [InvokerArrayVoxelShape.java](com/cerbon/cerbons_api/mixin/multipart_entities/InvokerArrayVoxelShape.java) |
| `VoxelShapeMixin` | `VoxelShape` | [VoxelShapeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/VoxelShapeMixin.java) |
| `MultiplayerGameModeMixin` | `MultiplayerGameMode` | [MultiplayerGameModeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/MultiplayerGameModeMixin.java) |

## 全部类（85）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeEvents` | class | — | EventBusSubscriber | 2 | 21 | [NeoForgeEvents.java](com/cerbon/cerbons_api/neoforge/event/NeoForgeEvents.java) |
| `DelayedPacketRegistrationHandler` | class | IPacketRegistrar | — | 3 | 38 | [DelayedPacketRegistrationHandler.java](com/cerbon/cerbons_api/api/network/DelayedPacketRegistrationHandler.java) |
| `PacketRegistrationHandler` | class | INetworkHandler | — | 3 | 39 | [PacketRegistrationHandler.java](com/cerbon/cerbons_api/api/network/PacketRegistrationHandler.java) |
| `NeoForgeNetworkHandler` | class | PacketRegistrationHandler | — | 5 | 81 | [NeoForgeNetworkHandler.java](com/cerbon/cerbons_api/neoforge/network/NeoForgeNetworkHandler.java) |
| `ItemRegistry` | class | — | — | 19 | 152 | [ItemRegistry.java](com/cerbon/cerbons_api/api/item/registry/ItemRegistry.java) |
| `CerbonsAPINeoForge` | class | — | Mod | 1 | 28 | [CerbonsAPINeoForge.java](com/cerbon/cerbons_api/neoforge/CerbonsAPINeoForge.java) |
| `BlockRegistry` | class | — | — | 12 | 88 | [BlockRegistry.java](com/cerbon/cerbons_api/api/block/registry/BlockRegistry.java) |
| `LevelEventScheduler` | class | SavedData | — | 3 | 43 | [LevelEventScheduler.java](com/cerbon/cerbons_api/neoforge/attachment/saved_data/LevelEventScheduler.java) |
| `CerbonsApiPacketHandler` | class | — | — | 1 | 16 | [CerbonsApiPacketHandler.java](com/cerbon/cerbons_api/packet/CerbonsApiPacketHandler.java) |
| `EntityMixin` | class | — | Mixin | 2 | 29 | [EntityMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/EntityMixin.java) |
| `EntityRenderDispatcherMixin` | class | — | Mixin | 1 | 48 | [EntityRenderDispatcherMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/EntityRenderDispatcherMixin.java) |
| `INetworkHandler` | interface | — | — | 0 | 61 | [INetworkHandler.java](com/cerbon/cerbons_api/api/network/INetworkHandler.java) |
| `RegistryUtils` | class | — | — | 6 | 40 | [RegistryUtils.java](com/cerbon/cerbons_api/api/static_utilities/RegistryUtils.java) |
| `EventSeries` | class | IEvent | — | 4 | 47 | [EventSeries.java](com/cerbon/cerbons_api/api/general/event/EventSeries.java) |
| `EventScheduler` | class | — | — | 2 | 31 | [EventScheduler.java](com/cerbon/cerbons_api/api/general/event/EventScheduler.java) |
| `NeoForgeRegistryHelper` | class | IRegistryHelper | — | 1 | 14 | [NeoForgeRegistryHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeRegistryHelper.java) |
| `IRegistryHelper` | interface | — | — | 0 | 9 | [IRegistryHelper.java](com/cerbon/cerbons_api/platform/services/IRegistryHelper.java) |
| `OrientedBox` | class | — | — | 26 | 416 | [OrientedBox.java](com/cerbon/cerbons_api/api/multipart_entities/util/OrientedBox.java) |
| `NeoForgeResourcefulRegistry` | class | ResourcefulRegistry<T> | — | 3 | 35 | [NeoForgeResourcefulRegistry.java](com/cerbon/cerbons_api/neoforge/registry/NeoForgeResourcefulRegistry.java) |
| `NeoForgeRegistryEntry` | class | RegistryEntry<T> | — | 2 | 24 | [NeoForgeRegistryEntry.java](com/cerbon/cerbons_api/neoforge/registry/NeoForgeRegistryEntry.java) |
| `AABBMixin` | class | — | Mixin | 1 | 19 | [AABBMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/AABBMixin.java) |
| `ProjectileMixin` | class | Entity | Mixin | 1 | 33 | [ProjectileMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ProjectileMixin.java) |
| `ShapesMixin` | class | — | Mixin | 1 | 21 | [ShapesMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/ShapesMixin.java) |
| `InvokerArrayVoxelShape` | interface | — | Mixin | 0 | 16 | [InvokerArrayVoxelShape.java](com/cerbon/cerbons_api/mixin/multipart_entities/InvokerArrayVoxelShape.java) |
| `VoxelShapeMixin` | class | — | Mixin | 1 | 21 | [VoxelShapeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/VoxelShapeMixin.java) |
| `MultiplayerGameModeMixin` | class | — | Mixin | 3 | 90 | [MultiplayerGameModeMixin.java](com/cerbon/cerbons_api/mixin/multipart_entities/client/MultiplayerGameModeMixin.java) |
| `RegistrationException` | class | RuntimeException | — | 0 | 23 | [RegistrationException.java](com/cerbon/cerbons_api/api/network/exceptions/RegistrationException.java) |
| `ResourcefulRegistry` | interface | — | — | 0 | 22 | [ResourcefulRegistry.java](com/cerbon/cerbons_api/api/registry/ResourcefulRegistry.java) |
| `RegistryEntries` | class | — | — | 2 | 19 | [RegistryEntries.java](com/cerbon/cerbons_api/api/registry/RegistryEntries.java) |
| `RegistryEntry` | interface | — | — | 0 | 12 | [RegistryEntry.java](com/cerbon/cerbons_api/api/registry/RegistryEntry.java) |
| `MultipartEntityInteractionC2SPacket` | class | — | — | 4 | 88 | [MultipartEntityInteractionC2SPacket.java](com/cerbon/cerbons_api/packet/custom/MultipartEntityInteractionC2SPacket.java) |
| `MathUtils` | class | — | — | 17 | 241 | [MathUtils.java](com/cerbon/cerbons_api/api/static_utilities/MathUtils.java) |
| `EntityBounds` | class | — | — | 16 | 186 | [EntityBounds.java](com/cerbon/cerbons_api/api/multipart_entities/entity/EntityBounds.java) |
| `EntityPart` | class | — | — | 15 | 130 | [EntityPart.java](com/cerbon/cerbons_api/api/multipart_entities/entity/EntityPart.java) |
| `HistoricalData` | class | List<T> | — | 24 | 173 | [HistoricalData.java](com/cerbon/cerbons_api/api/general/data/HistoricalData.java) |
| `NeoForgeCapabilityHelper` | class | ICapabilityHelper | — | 1 | 14 | [NeoForgeCapabilityHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeCapabilityHelper.java) |
| `ICapabilityHelper` | interface | — | — | 0 | 9 | [ICapabilityHelper.java](com/cerbon/cerbons_api/platform/services/ICapabilityHelper.java) |
| `PacketUtils` | class | — | — | 4 | 39 | [PacketUtils.java](com/cerbon/cerbons_api/api/static_utilities/PacketUtils.java) |
| `CapabilityUtils` | class | — | — | 1 | 12 | [CapabilityUtils.java](com/cerbon/cerbons_api/api/static_utilities/CapabilityUtils.java) |
| `ClientParticleBuilder` | class | — | — | 14 | 146 | [ClientParticleBuilder.java](com/cerbon/cerbons_api/api/general/particle/ClientParticleBuilder.java) |
| `NeoForgeMenuTypeHelper` | class | IMenuTypeHelper | — | 3 | 41 | [NeoForgeMenuTypeHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgeMenuTypeHelper.java) |
| `IMenuTypeHelper` | interface | — | — | 0 | 35 | [IMenuTypeHelper.java](com/cerbon/cerbons_api/platform/services/IMenuTypeHelper.java) |
| `IPacketRegistrar` | interface | — | — | 0 | 18 | [IPacketRegistrar.java](com/cerbon/cerbons_api/api/network/IPacketRegistrar.java) |
| `Network` | class | — | — | 2 | 21 | [Network.java](com/cerbon/cerbons_api/api/network/Network.java) |
| `CommonNetwork` | record | — | — | 2 | 36 | [CommonNetwork.java](com/cerbon/cerbons_api/api/network/CommonNetwork.java) |
| `CommonPacketWrapper` | record | CustomPacketPayload | — | 1 | 13 | [CommonPacketWrapper.java](com/cerbon/cerbons_api/api/network/data/CommonPacketWrapper.java) |
| `PacketContext` | record | — | — | 0 | 10 | [PacketContext.java](com/cerbon/cerbons_api/api/network/data/PacketContext.java) |
| `PacketContainer` | record | — | — | 3 | 39 | [PacketContainer.java](com/cerbon/cerbons_api/api/network/data/PacketContainer.java) |
| `MenuTypeUtils` | class | — | — | 3 | 33 | [MenuTypeUtils.java](com/cerbon/cerbons_api/api/static_utilities/MenuTypeUtils.java) |
| `CompoundOrientedBox` | class | AABB | — | 11 | 230 | [CompoundOrientedBox.java](com/cerbon/cerbons_api/api/multipart_entities/util/CompoundOrientedBox.java) |
| `IEvent` | interface | — | — | 0 | 12 | [IEvent.java](com/cerbon/cerbons_api/api/general/event/IEvent.java) |
| `TimedEvent` | class | IEvent | — | 4 | 52 | [TimedEvent.java](com/cerbon/cerbons_api/api/general/event/TimedEvent.java) |
| `Event` | class | IEvent | — | 4 | 42 | [Event.java](com/cerbon/cerbons_api/api/general/event/Event.java) |
| `SimpleParticle` | class | TextureSheetParticle | — | 18 | 192 | [SimpleParticle.java](com/cerbon/cerbons_api/api/general/particle/SimpleParticle.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 6 | 41 | [NeoForgePlatformHelper.java](com/cerbon/cerbons_api/neoforge/platform/NeoForgePlatformHelper.java) |
| `Dispatcher` | class | — | — | 11 | 65 | [Dispatcher.java](com/cerbon/cerbons_api/api/network/Dispatcher.java) |
| `MiscUtils` | class | — | — | 7 | 35 | [MiscUtils.java](com/cerbon/cerbons_api/api/static_utilities/MiscUtils.java) |
| `RandomUtils` | class | — | — | 7 | 48 | [RandomUtils.java](com/cerbon/cerbons_api/api/static_utilities/RandomUtils.java) |
| `VecUtils` | class | — | — | 8 | 63 | [VecUtils.java](com/cerbon/cerbons_api/api/static_utilities/VecUtils.java) |
| `MultipartEntity` | interface | — | — | 0 | 9 | [MultipartEntity.java](com/cerbon/cerbons_api/api/multipart_entities/entity/MultipartEntity.java) |
| `MultipartAwareEntity` | interface | — | — | 0 | 19 | [MultipartAwareEntity.java](com/cerbon/cerbons_api/api/multipart_entities/entity/MultipartAwareEntity.java) |
| `PlayerInteractMultipartEntity` | class | — | — | 0 | 9 | [PlayerInteractMultipartEntity.java](com/cerbon/cerbons_api/api/multipart_entities/client/PlayerInteractMultipartEntity.java) |
| `IPlatformHelper` | interface | — | — | 0 | 22 | [IPlatformHelper.java](com/cerbon/cerbons_api/platform/services/IPlatformHelper.java) |
| `SoundUtils` | class | — | — | 4 | 43 | [SoundUtils.java](com/cerbon/cerbons_api/api/static_utilities/SoundUtils.java) |
| `MobUtils` | class | — | — | 4 | 30 | [MobUtils.java](com/cerbon/cerbons_api/api/static_utilities/MobUtils.java) |
| `ParticleUtils` | class | — | — | 4 | 33 | [ParticleUtils.java](com/cerbon/cerbons_api/api/static_utilities/ParticleUtils.java) |
| `QuaternionD` | class | — | — | 8 | 115 | [QuaternionD.java](com/cerbon/cerbons_api/api/multipart_entities/util/QuaternionD.java) |
| `Matrix3d` | class | — | — | 8 | 107 | [Matrix3d.java](com/cerbon/cerbons_api/api/multipart_entities/util/Matrix3d.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_CERBONsAPI_common_a31486a8fa8344528de97a237b22c338_f6f9d45edca97e508e13c36c55afb1f41050fbd4ad9c993def34fa3fed489d35CerbonsAPICommon121130devjar/PlatformMethods.java) |
| `CerbonsAPI` | class | — | — | 1 | 10 | [CerbonsAPI.java](com/cerbon/cerbons_api/CerbonsAPI.java) |
| `Services` | class | — | — | 1 | 22 | [Services.java](com/cerbon/cerbons_api/platform/Services.java) |
| `Side` | enum | — | — | 1 | 11 | [Side.java](com/cerbon/cerbons_api/api/network/data/Side.java) |
| `Geometries` | class | — | — | 1 | 56 | [Geometries.java](com/cerbon/cerbons_api/api/static_utilities/Geometries.java) |
| `Vec3Colors` | class | — | — | 0 | 28 | [Vec3Colors.java](com/cerbon/cerbons_api/api/static_utilities/Vec3Colors.java) |
| `MutableBox` | class | — | — | 2 | 20 | [MutableBox.java](com/cerbon/cerbons_api/api/multipart_entities/entity/MutableBox.java) |
| `ToolType` | enum | — | — | 0 | 10 | [ToolType.java](com/cerbon/cerbons_api/api/item/enums/ToolType.java) |
| `ResourcefulRegistries` | class | — | — | 1 | 11 | [ResourcefulRegistries.java](com/cerbon/cerbons_api/api/registry/ResourcefulRegistries.java) |
| `WeightedRandom` | class | — | — | 3 | 49 | [WeightedRandom.java](com/cerbon/cerbons_api/api/general/random/WeightedRandom.java) |
| `WeakHashPredicate` | class | Predicate<T> | — | 1 | 24 | [WeakHashPredicate.java](com/cerbon/cerbons_api/api/general/data/WeakHashPredicate.java) |
| `BooleanFlag` | class | — | — | 2 | 19 | [BooleanFlag.java](com/cerbon/cerbons_api/api/general/data/BooleanFlag.java) |
| `SimpleParticleProvider` | class | ParticleProvider<SimpleParticleType> | — | 1 | 29 | [SimpleParticleProvider.java](com/cerbon/cerbons_api/api/general/particle/SimpleParticleProvider.java) |
| `IParticleGeometry` | interface | — | — | 0 | 10 | [IParticleGeometry.java](com/cerbon/cerbons_api/api/general/particle/IParticleGeometry.java) |
| `ParticleContext` | record | — | — | 0 | 9 | [ParticleContext.java](com/cerbon/cerbons_api/api/general/particle/ParticleContext.java) |
| `ReferencedAxisRotator` | class | — | — | 1 | 19 | [ReferencedAxisRotator.java](com/cerbon/cerbons_api/api/general/math/ReferencedAxisRotator.java) |
| `Constants` | class | — | — | 0 | 10 | [Constants.java](com/cerbon/cerbons_api/util/Constants.java) |
