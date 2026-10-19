# Platform（`platform`）

> jar：`[前置]platform.jar` ｜ 版本：`1.3.3` ｜ 作者：ItsBlackGear
> 规模：**163 个类 / 54 个包 / 8637 行** ｜ 事件处理类 6 ｜ Mixin 26 ｜ 注册/管理类 49

**官方描述**：Cross-Platform library mod based on Architectury to access both Forge and Fabric APIs

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blackgear.platform.core.helper` | 10 | `BlockRegistry`、`ItemRegistry`、`EntityRegistry` |
| `com.blackgear.platform.core.mixin.access` | 10 | `ScreenAccessor`、`ItemPropertiesAccessor`、`NearPlaneAccessor` |
| `com.blackgear.platform.core.util.config` | 8 | `SimpleConfigSpec`、`ConfigFileTypeHandler`、`SimpleConfigBuilder` |
| `com.blackgear.platform.core` | 7 | `BuiltInCoreRegistry`、`CoreRegistry`、`RegistryBuilder` |
| `com.blackgear.platform.core.events` | 6 | `ConfigEvents`、`DataLifecycleEvents`、`DatapackSyncEvents` |
| `com.blackgear.platform.common.integration` | 6 | `BlockIntegration`、`MobIntegration`、`TradeIntegration` |
| `com.blackgear.platform.common.worldgen.placement.parameters` | 6 | `Continentalness`、`Weirdness`、`Depth` |
| `com.blackgear.platform.core.mixin.client` | 5 | `ItemRendererMixin`、`AbstractContainerScreenMixin`、`AbstractClientPlayerMixin` |
| `com.blackgear.platform.core.neoforge` | 5 | `CoreRegistryImpl`、`RegistryBuilderImpl`、`EnvironmentImpl` |
| `com.blackgear.platform.client.v2.render` | 4 | `BuiltinItemRendererRegistry`、`ItemRendererRegistry`、`DynamicItemRenderer` |
| `com.blackgear.platform.core.mixin.neoforge` | 4 | `LootPoolMixin`、`ComposterBlockMixin`、`HoneycombItemMixin` |
| `com.blackgear.platform.core.networking` | 4 | `ServerListenerEvents`、`Networking`、`PayloadDistributor` |
| `com.blackgear.platform.core.helper.neoforge` | 4 | `ItemRegistryImpl`、`AttachmentRegistryImpl`、`DataSerializerRegistryImpl` |
| `com.blackgear.platform.client.event` | 4 | `LocalPlayerEvents`、`HudRenderEvent`、`FogRendering` |
| `com.blackgear.platform.common.worldgen.modifier` | 4 | `FeatureManager`、`BiomeManager`、`BiomeWriter` |
| `com.blackgear.platform.common.data` | 4 | `TagRegistry`、`LootModifier`、`DataTransformer` |
| `com.blackgear.platform.core.api.registrar.bootstrap` | 4 | `ConfiguredFeatureRegistrar`、`NoiseRegistrar`、`BootstrapRegistrar` |
| `com.blackgear.platform.core.util.event` | 4 | `Event`、`SimpleEvent`、`CancellableResult` |
| `com.blackgear.platform.client.animator.v2` | 4 | `KeyframeAnimations`、`Keyframe`、`AnimationChannel` |
| `com.blackgear.platform.client.animator` | 4 | `AnimatedPoint`、`MathAnimator`、`AnimatedModel` |
| `com.blackgear.platform.neoforge.client` | 3 | `ForgeClientEvents`、`ForgeInputEvents`、`ForgeClientEventHandler` |
| `com.blackgear.platform.common.data.neoforge` | 3 | `LootModifierImpl`、`EntityDataImpl`、`LootPoolAccess` |
| `com.blackgear.platform.core.events.neoforge` | 3 | `ServerLifecycle`、`ResourcePackManagerImpl`、`ResourceReloadManagerImpl` |
| `com.blackgear.platform.core.mixin.common` | 3 | `BlockEntityTypeMixin`、`ResourceLocationMixin`、`OverworldBiomeBuilderMixin` |
| `com.blackgear.platform.client.event.screen` | 3 | `TooltipEvents`、`HudRendering`、`HudInteractions` |
| `com.blackgear.platform.common.integration.neoforge` | 3 | `BlockIntegrationImpl`、`TradeIntegrationImpl`、`MobIntegrationImpl` |
| `com.blackgear.platform.common.worldgen.placement` | 3 | `BiomeSpawnPlacement`、`Placement`、`BiomePlacement` |
| `com.blackgear.platform.neoforge` | 2 | `ForgeCommonEvents`、`PlatformForge` |
| `com.blackgear.platform.core.mixin.neoforge.client` | 2 | `BoatRendererMixin`、`BEWLRMixin` |
| `com.blackgear.platform.core.util.config.neoforge` | 2 | `ForgeConfigBuilder`、`ForgeConfigValue` |
| `com.blackgear.platform.core.networking.neoforge` | 2 | `NetworkingImpl`、`PayloadDistributorImpl` |
| `com.blackgear.platform.client.event.screen.api` | 2 | `ScreenAccessImpl`、`ScreenAccess` |
| `com.blackgear.platform.core.api.registrar` | 2 | `Registrar`、`RegistrarBuilder` |
| `com.blackgear.platform.core.api.registrar.neoforge` | 2 | `RegistrarImpl`、`RegistrarBuilderImpl` |
| `com.blackgear.platform.common` | 2 | `CommonSetup`、`CreativeTabs` |
| `com.blackgear.platform.common.neoforge` | 1 | `CreativeTabsImpl` |
| `com.blackgear.platform.core.mixin.config` | 1 | `ClientHandshakePacketListenerImplMixin` |
| `com.blackgear.platform.common.events` | 1 | `EntityEvents` |
| `com.blackgear.platform.client.v2.render.neoforge` | 1 | `BuiltinItemRendererRegistryImpl` |
| `com.blackgear.platform.core.mixin.neoforge.access` | 1 | `LootTableAccessor` |
| … | | 其余 14 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ForgeClientEvents` | class | — | EventBusSubscriber | 18 | 200 | [ForgeClientEvents.java](com/blackgear/platform/neoforge/client/ForgeClientEvents.java) |
| `ForgeCommonEvents` | class | — | EventBusSubscriber | 6 | 68 | [ForgeCommonEvents.java](com/blackgear/platform/neoforge/ForgeCommonEvents.java) |
| `ForgeInputEvents` | class | — | EventBusSubscriber | 6 | 62 | [ForgeInputEvents.java](com/blackgear/platform/neoforge/client/ForgeInputEvents.java) |
| `LootModifierImpl` | class | — | EventBusSubscriber | 4 | 61 | [LootModifierImpl.java](com/blackgear/platform/common/data/neoforge/LootModifierImpl.java) |
| `CreativeTabsImpl` | class | — | EventBusSubscriber | 5 | 50 | [CreativeTabsImpl.java](com/blackgear/platform/common/neoforge/CreativeTabsImpl.java) |
| `ServerLifecycle` | class | — | EventBusSubscriber | 4 | 33 | [ServerLifecycle.java](com/blackgear/platform/core/events/neoforge/ServerLifecycle.java) |
| `BlockRegistry` | class | — | — | 20 | 126 | [BlockRegistry.java](com/blackgear/platform/core/helper/BlockRegistry.java) |
| `ConfigEvents` | interface | — | — | 0 | 12 | [ConfigEvents.java](com/blackgear/platform/core/events/ConfigEvents.java) |
| `SimpleConfigSpec` | class | UnmodifiableConfigWrapper | — | 57 | 592 | [SimpleConfigSpec.java](com/blackgear/platform/core/util/config/SimpleConfigSpec.java) |
| `ClientHandshakePacketListenerImplMixin` | class | — | Mixin | 1 | 34 | [ClientHandshakePacketListenerImplMixin.java](com/blackgear/platform/core/mixin/config/ClientHandshakePacketListenerImplMixin.java) |
| `ItemRegistry` | class | — | — | 11 | 69 | [ItemRegistry.java](com/blackgear/platform/core/helper/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 11 | 78 | [EntityRegistry.java](com/blackgear/platform/core/helper/EntityRegistry.java) |
| `BlockEntityRegistry` | class | — | — | 4 | 32 | [BlockEntityRegistry.java](com/blackgear/platform/core/helper/BlockEntityRegistry.java) |
| `BlockEntityTypeMixin` | class | — | Mixin | 0 | 28 | [BlockEntityTypeMixin.java](com/blackgear/platform/core/mixin/common/BlockEntityTypeMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 3 | 97 | [ItemRendererMixin.java](com/blackgear/platform/core/mixin/client/ItemRendererMixin.java) |
| `ConfigFileTypeHandler` | class | RuntimeException | — | 6 | 146 | [ConfigFileTypeHandler.java](com/blackgear/platform/core/util/config/ConfigFileTypeHandler.java) |
| `PlatformForge` | class | — | Mod | 0 | 12 | [PlatformForge.java](com/blackgear/platform/neoforge/PlatformForge.java) |
| `ForgeClientEventHandler` | class | — | — | 1 | 12 | [ForgeClientEventHandler.java](com/blackgear/platform/neoforge/client/ForgeClientEventHandler.java) |
| `EntityEvents` | interface | — | — | 0 | 41 | [EntityEvents.java](com/blackgear/platform/common/events/EntityEvents.java) |
| `BuiltinItemRendererRegistry` | class | — | — | 3 | 28 | [BuiltinItemRendererRegistry.java](com/blackgear/platform/client/v2/render/BuiltinItemRendererRegistry.java) |
| `ItemRendererRegistry` | class | — | — | 3 | 45 | [ItemRendererRegistry.java](com/blackgear/platform/client/v2/render/ItemRendererRegistry.java) |
| `BuiltinItemRendererRegistryImpl` | class | BuiltinItemRendererRegistry | — | 3 | 27 | [BuiltinItemRendererRegistryImpl.java](com/blackgear/platform/client/v2/render/neoforge/BuiltinItemRendererRegistryImpl.java) |
| `BuiltInCoreRegistry` | class | — | — | 18 | 159 | [BuiltInCoreRegistry.java](com/blackgear/platform/core/BuiltInCoreRegistry.java) |
| `LootPoolMixin` | class | LootPoolAccess | Mixin | 2 | 29 | [LootPoolMixin.java](com/blackgear/platform/core/mixin/neoforge/LootPoolMixin.java) |
| `LootTableAccessor` | interface | — | Mixin | 0 | 14 | [LootTableAccessor.java](com/blackgear/platform/core/mixin/neoforge/access/LootTableAccessor.java) |
| `DataLifecycleEvents` | interface | — | — | 0 | 11 | [DataLifecycleEvents.java](com/blackgear/platform/core/events/DataLifecycleEvents.java) |
| `DatapackSyncEvents` | interface | — | — | 0 | 11 | [DatapackSyncEvents.java](com/blackgear/platform/core/events/DatapackSyncEvents.java) |
| `SimpleConfigBuilder` | class | ConfigBuilder | — | 20 | 262 | [SimpleConfigBuilder.java](com/blackgear/platform/core/util/config/SimpleConfigBuilder.java) |
| `ItemPropertyRegistry` | class | — | — | 5 | 44 | [ItemPropertyRegistry.java](com/blackgear/platform/common/item/ItemPropertyRegistry.java) |
| `CoreRegistry` | class | — | — | 11 | 62 | [CoreRegistry.java](com/blackgear/platform/core/CoreRegistry.java) |
| `ServerListenerEvents` | interface | — | — | 0 | 12 | [ServerListenerEvents.java](com/blackgear/platform/core/networking/ServerListenerEvents.java) |
| `SoundRegistry` | class | SoundType | — | 11 | 102 | [SoundRegistry.java](com/blackgear/platform/core/helper/SoundRegistry.java) |
| `ItemRegistryImpl` | class | — | — | 1 | 15 | [ItemRegistryImpl.java](com/blackgear/platform/core/helper/neoforge/ItemRegistryImpl.java) |
| `CoreRegistryImpl` | class | CoreRegistry | — | 14 | 92 | [CoreRegistryImpl.java](com/blackgear/platform/core/neoforge/CoreRegistryImpl.java) |
| `ComposterBlockMixin` | class | — | Mixin | 0 | 26 | [ComposterBlockMixin.java](com/blackgear/platform/core/mixin/neoforge/ComposterBlockMixin.java) |
| `HoneycombItemMixin` | class | — | Mixin | 1 | 20 | [HoneycombItemMixin.java](com/blackgear/platform/core/mixin/neoforge/HoneycombItemMixin.java) |
| `BoatRendererMixin` | class | — | Mixin | 0 | 26 | [BoatRendererMixin.java](com/blackgear/platform/core/mixin/neoforge/client/BoatRendererMixin.java) |
| `AbstractContainerScreenMixin` | class | — | Mixin | 0 | 62 | [AbstractContainerScreenMixin.java](com/blackgear/platform/core/mixin/client/AbstractContainerScreenMixin.java) |
| `AbstractClientPlayerMixin` | class | Player | Mixin | 1 | 29 | [AbstractClientPlayerMixin.java](com/blackgear/platform/core/mixin/client/AbstractClientPlayerMixin.java) |
| `ScreenAccessor` | interface | — | Mixin | 0 | 29 | [ScreenAccessor.java](com/blackgear/platform/core/mixin/access/ScreenAccessor.java) |
| `ItemPropertiesAccessor` | interface | — | Mixin | 0 | 17 | [ItemPropertiesAccessor.java](com/blackgear/platform/core/mixin/access/ItemPropertiesAccessor.java) |
| `ServerLifecycleEvents` | interface | — | — | 0 | 14 | [ServerLifecycleEvents.java](com/blackgear/platform/core/events/ServerLifecycleEvents.java) |
| `BiomeManagerImpl` | class | BiomeWriter | — | 17 | 172 | [BiomeManagerImpl.java](com/blackgear/platform/common/worldgen/modifier/neoforge/BiomeManagerImpl.java) |
| `ItemPropertyRegistryImpl` | class | — | — | 4 | 28 | [ItemPropertyRegistryImpl.java](com/blackgear/platform/common/item/neoforge/ItemPropertyRegistryImpl.java) |
| `LocalPlayerEvents` | interface | — | OnlyIn | 0 | 27 | [LocalPlayerEvents.java](com/blackgear/platform/client/event/LocalPlayerEvents.java) |
| `TooltipEvents` | interface | — | OnlyIn | 0 | 28 | [TooltipEvents.java](com/blackgear/platform/client/event/screen/TooltipEvents.java) |
| `RegistryBuilder` | class | — | — | 6 | 46 | [RegistryBuilder.java](com/blackgear/platform/core/RegistryBuilder.java) |
| `ParticleRegistry` | class | — | — | 8 | 59 | [ParticleRegistry.java](com/blackgear/platform/core/helper/ParticleRegistry.java) |
| `DataSerializerRegistry` | class | — | — | 4 | 26 | [DataSerializerRegistry.java](com/blackgear/platform/core/helper/DataSerializerRegistry.java) |
| `AttachmentRegistry` | class | — | — | 6 | 46 | [AttachmentRegistry.java](com/blackgear/platform/core/helper/AttachmentRegistry.java) |
| `AttachmentRegistryImpl` | class | AttachmentRegistry | — | 7 | 74 | [AttachmentRegistryImpl.java](com/blackgear/platform/core/helper/neoforge/AttachmentRegistryImpl.java) |
| `DataSerializerRegistryImpl` | class | DataSerializerRegistry | — | 3 | 30 | [DataSerializerRegistryImpl.java](com/blackgear/platform/core/helper/neoforge/DataSerializerRegistryImpl.java) |
| `ForgeConfigBuilder` | class | ConfigBuilder | — | 16 | 114 | [ForgeConfigBuilder.java](com/blackgear/platform/core/util/config/neoforge/ForgeConfigBuilder.java) |
| `WorldGenRegistry` | class | — | — | 9 | 69 | [WorldGenRegistry.java](com/blackgear/platform/common/worldgen/WorldGenRegistry.java) |
| `FeatureManager` | class | — | — | 11 | 82 | [FeatureManager.java](com/blackgear/platform/common/worldgen/modifier/FeatureManager.java) |
| `RegistryHolder` | interface | — | — | 0 | 25 | [RegistryHolder.java](com/blackgear/platform/core/RegistryHolder.java) |
| `ClientboundConfigSyncPayload` | record | CustomPacketPayload | — | 2 | 30 | [ClientboundConfigSyncPayload.java](com/blackgear/platform/core/networking/packet/ClientboundConfigSyncPayload.java) |
| `DynamicRegistry` | class | — | — | 1 | 22 | [DynamicRegistry.java](com/blackgear/platform/core/helper/DynamicRegistry.java) |
| `DynamicRegistryImpl` | class | — | — | 2 | 20 | [DynamicRegistryImpl.java](com/blackgear/platform/core/helper/neoforge/DynamicRegistryImpl.java) |
| `RegistryBuilderImpl` | class | — | — | 2 | 29 | [RegistryBuilderImpl.java](com/blackgear/platform/core/neoforge/RegistryBuilderImpl.java) |

## 事件处理（`@SubscribeEvent`，6 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ForgeClientEvents` | 18 | `com.blackgear.platform.neoforge.client` | [ForgeClientEvents.java](com/blackgear/platform/neoforge/client/ForgeClientEvents.java) |
| `ForgeCommonEvents` | 6 | `com.blackgear.platform.neoforge` | [ForgeCommonEvents.java](com/blackgear/platform/neoforge/ForgeCommonEvents.java) |
| `ForgeInputEvents` | 6 | `com.blackgear.platform.neoforge.client` | [ForgeInputEvents.java](com/blackgear/platform/neoforge/client/ForgeInputEvents.java) |
| `ServerLifecycle` | 4 | `com.blackgear.platform.core.events.neoforge` | [ServerLifecycle.java](com/blackgear/platform/core/events/neoforge/ServerLifecycle.java) |
| `LootModifierImpl` | 1 | `com.blackgear.platform.common.data.neoforge` | [LootModifierImpl.java](com/blackgear/platform/common/data/neoforge/LootModifierImpl.java) |
| `CreativeTabsImpl` | 1 | `com.blackgear.platform.common.neoforge` | [CreativeTabsImpl.java](com/blackgear/platform/common/neoforge/CreativeTabsImpl.java) |

## Mixin（26 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientHandshakePacketListenerImplMixin` | `ClientHandshakePacketListenerImpl` | [ClientHandshakePacketListenerImplMixin.java](com/blackgear/platform/core/mixin/config/ClientHandshakePacketListenerImplMixin.java) |
| `BlockEntityTypeMixin` | `BlockEntityType` | [BlockEntityTypeMixin.java](com/blackgear/platform/core/mixin/common/BlockEntityTypeMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](com/blackgear/platform/core/mixin/client/ItemRendererMixin.java) |
| `LootPoolMixin` | `LootPool` | [LootPoolMixin.java](com/blackgear/platform/core/mixin/neoforge/LootPoolMixin.java) |
| `LootTableAccessor` | `LootTableAccessor` | [LootTableAccessor.java](com/blackgear/platform/core/mixin/neoforge/access/LootTableAccessor.java) |
| `ComposterBlockMixin` | `ComposterBlock` | [ComposterBlockMixin.java](com/blackgear/platform/core/mixin/neoforge/ComposterBlockMixin.java) |
| `HoneycombItemMixin` | `HoneycombItem` | [HoneycombItemMixin.java](com/blackgear/platform/core/mixin/neoforge/HoneycombItemMixin.java) |
| `BoatRendererMixin` | `BoatRenderer` | [BoatRendererMixin.java](com/blackgear/platform/core/mixin/neoforge/client/BoatRendererMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](com/blackgear/platform/core/mixin/client/AbstractContainerScreenMixin.java) |
| `AbstractClientPlayerMixin` | `AbstractClientPlayer` | [AbstractClientPlayerMixin.java](com/blackgear/platform/core/mixin/client/AbstractClientPlayerMixin.java) |
| `ScreenAccessor` | `ScreenAccessor` | [ScreenAccessor.java](com/blackgear/platform/core/mixin/access/ScreenAccessor.java) |
| `ItemPropertiesAccessor` | `ItemPropertiesAccessor` | [ItemPropertiesAccessor.java](com/blackgear/platform/core/mixin/access/ItemPropertiesAccessor.java) |
| `WeatheringCopperMixin` | `WeatheringCopper` | [WeatheringCopperMixin.java](com/blackgear/platform/core/mixin/neoforge/WeatheringCopperMixin.java) |
| `BEWLRMixin` | `BEWLR` | [BEWLRMixin.java](com/blackgear/platform/core/mixin/neoforge/client/BEWLRMixin.java) |
| `ResourceLocationMixin` | `ResourceLocation` | [ResourceLocationMixin.java](com/blackgear/platform/core/mixin/common/ResourceLocationMixin.java) |
| `OverworldBiomeBuilderMixin` | `OverworldBiomeBuilder` | [OverworldBiomeBuilderMixin.java](com/blackgear/platform/core/mixin/common/OverworldBiomeBuilderMixin.java) |
| `MinecraftMixin` | `Minecraft` | [MinecraftMixin.java](com/blackgear/platform/core/mixin/client/MinecraftMixin.java) |
| `ModelBakeryMixin` | `ModelBakery` | [ModelBakeryMixin.java](com/blackgear/platform/core/mixin/client/ModelBakeryMixin.java) |
| `NearPlaneAccessor` | `NearPlaneAccessor` | [NearPlaneAccessor.java](com/blackgear/platform/core/mixin/access/NearPlaneAccessor.java) |
| `SpawnPlacementsAccessor` | `SpawnPlacementsAccessor` | [SpawnPlacementsAccessor.java](com/blackgear/platform/core/mixin/access/SpawnPlacementsAccessor.java) |
| `PackRepositoryAccessor` | `PackRepositoryAccessor` | [PackRepositoryAccessor.java](com/blackgear/platform/core/mixin/access/PackRepositoryAccessor.java) |
| `SensorTypeAccessor` | `SensorTypeAccessor` | [SensorTypeAccessor.java](com/blackgear/platform/core/mixin/access/SensorTypeAccessor.java) |
| `LevelResourceAccessor` | `LevelResourceAccessor` | [LevelResourceAccessor.java](com/blackgear/platform/core/mixin/access/LevelResourceAccessor.java) |
| `ActivityAccessor` | `ActivityAccessor` | [ActivityAccessor.java](com/blackgear/platform/core/mixin/access/ActivityAccessor.java) |
| `CameraAccessor` | `CameraAccessor` | [CameraAccessor.java](com/blackgear/platform/core/mixin/access/CameraAccessor.java) |
| `SimpleParticleTypeAccessor` | `SimpleParticleTypeAccessor` | [SimpleParticleTypeAccessor.java](com/blackgear/platform/core/mixin/access/SimpleParticleTypeAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigEvents` | — | [ConfigEvents.java](com/blackgear/platform/core/events/ConfigEvents.java) |
| `SimpleConfigSpec` | — | [SimpleConfigSpec.java](com/blackgear/platform/core/util/config/SimpleConfigSpec.java) |
| `ConfigFileTypeHandler` | — | [ConfigFileTypeHandler.java](com/blackgear/platform/core/util/config/ConfigFileTypeHandler.java) |
| `SimpleConfigBuilder` | — | [SimpleConfigBuilder.java](com/blackgear/platform/core/util/config/SimpleConfigBuilder.java) |
| `ForgeConfigBuilder` | — | [ForgeConfigBuilder.java](com/blackgear/platform/core/util/config/neoforge/ForgeConfigBuilder.java) |
| `ClientboundConfigSyncPayload` | — | [ClientboundConfigSyncPayload.java](com/blackgear/platform/core/networking/packet/ClientboundConfigSyncPayload.java) |
| `ConfigTracker` | — | [ConfigTracker.java](com/blackgear/platform/core/util/config/ConfigTracker.java) |
| `ConfigLoader` | — | [ConfigLoader.java](com/blackgear/platform/core/util/config/ConfigLoader.java) |
| `ConfigBuilder` | — | [ConfigBuilder.java](com/blackgear/platform/core/util/config/ConfigBuilder.java) |
| `ModConfig` | — | [ModConfig.java](com/blackgear/platform/core/util/config/ModConfig.java) |
| `ForgeConfigValue` | — | [ForgeConfigValue.java](com/blackgear/platform/core/util/config/neoforge/ForgeConfigValue.java) |
| `ConfiguredFeatureRegistrar` | — | [ConfiguredFeatureRegistrar.java](com/blackgear/platform/core/api/registrar/bootstrap/ConfiguredFeatureRegistrar.java) |
| `IConfigSpec` | — | [IConfigSpec.java](com/blackgear/platform/core/util/config/IConfigSpec.java) |

## 按包浏览

- `architectury_inject_platform_common_ed0f76848f614fd3be6834dfcfd111bc_861306c3aea8aa0e8215a1843d062f58d9dd08604edd1b913466325593e3f257Platformcommon1211133devjar`（1 类）
- `com.blackgear.platform`（1 类）
- `com.blackgear.platform.client`（1 类）
- `com.blackgear.platform.client.animator`（4 类）
- `com.blackgear.platform.client.animator.v2`（4 类）
- `com.blackgear.platform.client.api.model`（1 类）
- `com.blackgear.platform.client.event`（4 类）
- `com.blackgear.platform.client.event.screen`（3 类）
- `com.blackgear.platform.client.event.screen.api`（2 类）
- `com.blackgear.platform.client.neoforge`（1 类）
- `com.blackgear.platform.client.v2.render`（4 类）
- `com.blackgear.platform.client.v2.render.neoforge`（1 类）
- `com.blackgear.platform.common`（2 类）
- `com.blackgear.platform.common.block`（1 类）
- `com.blackgear.platform.common.block.neoforge`（1 类）
- `com.blackgear.platform.common.data`（4 类）
- `com.blackgear.platform.common.data.neoforge`（3 类）
- `com.blackgear.platform.common.events`（1 类）
- `com.blackgear.platform.common.integration`（6 类）
- `com.blackgear.platform.common.integration.neoforge`（3 类）
- `com.blackgear.platform.common.item`（1 类）
- `com.blackgear.platform.common.item.neoforge`（1 类）
- `com.blackgear.platform.common.neoforge`（1 类）
- `com.blackgear.platform.common.resource`（1 类）
- `com.blackgear.platform.common.worldgen`（1 类）
- `com.blackgear.platform.common.worldgen.modifier`（4 类）
- `com.blackgear.platform.common.worldgen.modifier.neoforge`（1 类）
- `com.blackgear.platform.common.worldgen.placement`（3 类）
- `com.blackgear.platform.common.worldgen.placement.parameters`（6 类）
- `com.blackgear.platform.core`（7 类）
- `com.blackgear.platform.core.api.registrar`（2 类）
- `com.blackgear.platform.core.api.registrar.bootstrap`（4 类）
- `com.blackgear.platform.core.api.registrar.neoforge`（2 类）
- `com.blackgear.platform.core.events`（6 类）
- `com.blackgear.platform.core.events.neoforge`（3 类）
- `com.blackgear.platform.core.helper`（10 类）
- `com.blackgear.platform.core.helper.neoforge`（4 类）
- `com.blackgear.platform.core.mixin.access`（10 类）
- `com.blackgear.platform.core.mixin.client`（5 类）
- `com.blackgear.platform.core.mixin.common`（3 类）
- `com.blackgear.platform.core.mixin.config`（1 类）
- `com.blackgear.platform.core.mixin.neoforge`（4 类）
- `com.blackgear.platform.core.mixin.neoforge.access`（1 类）
- `com.blackgear.platform.core.mixin.neoforge.client`（2 类）
- `com.blackgear.platform.core.neoforge`（5 类）
- `com.blackgear.platform.core.networking`（4 类）
- `com.blackgear.platform.core.networking.neoforge`（2 类）
- `com.blackgear.platform.core.networking.packet`（1 类）
- `com.blackgear.platform.core.util`（1 类）
- `com.blackgear.platform.core.util.config`（8 类）
- `com.blackgear.platform.core.util.config.neoforge`（2 类）
- `com.blackgear.platform.core.util.event`（4 类）
- `com.blackgear.platform.neoforge`（2 类）
- `com.blackgear.platform.neoforge.client`（3 类）
