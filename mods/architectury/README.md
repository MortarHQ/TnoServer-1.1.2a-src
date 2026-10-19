# Architectury（`architectury`）

> jar：`[前置]architectury-13.0.8-neoforge.jar` ｜ 版本：`13.0.8` ｜ 作者：shedaniel
> 规模：**207 个类 / 77 个包 / 11658 行** ｜ 事件处理类 8 ｜ Mixin 20 ｜ 注册/管理类 47

**依赖**：`minecraft`、`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.architectury.event.events.common` | 13 | `CommandRegistrationEvent`、`LootEvent`、`CommandPerformEvent` |
| `dev.architectury.event.events.client` | 12 | `ClientCommandRegistrationEvent`、`ClientRecipeUpdateEvent`、`ClientScreenInputEvent` |
| `dev.architectury.extensions.injected` | 9 | `InjectedRegistryEntryExtension`、`InjectedEntityTypeExtension`、`InjectedItemExtension` |
| `dev.architectury.mixin.inject` | 8 | `MixinBlock`、`MixinBucketItem`、`MixinItemProperties` |
| `dev.architectury.impl` | 7 | `NetworkAggregator`、`RegistrySupplierImpl`、`TooltipEventColorContextImpl` |
| `dev.architectury.utils` | 7 | `EnvExecutor`、`PlatformExpectedError`、`GameInstance` |
| `dev.architectury.utils.value` | 7 | `BooleanValue`、`FloatSupplier`、`LongValue` |
| `dev.architectury.event.forge` | 6 | `EventHandlerImplClient`、`EventHandlerImplCommon`、`EventHandlerImplServer` |
| `dev.architectury.mixin.forge` | 6 | `MixinFallingBlockEntity`、`MixinLevelEvent`、`MixinClientLevel` |
| `dev.architectury.networking.simple` | 6 | `SimpleNetworkManager`、`MessageType`、`BaseS2CMessage` |
| `dev.architectury.event` | 6 | `EventFactory`、`EventHandler`、`CompoundEventResult` |
| `dev.architectury.hooks.level.biome` | 6 | `BiomeHooks`、`SpawnProperties`、`BiomeProperties` |
| `dev.architectury.registry.registries` | 6 | `RegistrarManager`、`RegistrySupplier`、`DeferredRegister` |
| `dev.architectury.networking.transformers` | 5 | `SplitPacketTransformer`、`PacketTransformer`、`SinglePacketCollector` |
| `dev.architectury.mixin.forge.neoforge` | 4 | `MixinBucketItem`、`LiquidBlockAccessor`、`BucketItemAccessor` |
| `dev.architectury.core.fluid` | 4 | `ArchitecturyFluidAttributesForge`、`SimpleArchitecturyFluidAttributes`、`ArchitecturyFlowingFluid` |
| `dev.architectury.hooks.fluid.forge` | 4 | `FluidStackHooksImpl`、`LiquidBlockHooksImpl`、`FluidBucketHooksImpl` |
| `dev.architectury.registry.client.rendering.forge` | 3 | `ColorHandlerRegistryImpl`、`BlockEntityRendererRegistryImpl`、`RenderTypeRegistryImpl` |
| `dev.architectury.networking` | 3 | `NetworkManager`、`SpawnEntityPacket`、`NetworkChannel` |
| `dev.architectury.registry.client.rendering` | 3 | `BlockEntityRendererRegistry`、`ColorHandlerRegistry`、`RenderTypeRegistry` |
| `dev.architectury.registry` | 3 | `CreativeTabRegistry`、`ReloadListenerRegistry`、`CreativeTabOutput` |
| `dev.architectury.hooks.fluid` | 3 | `FluidStackHooks`、`LiquidBlockHooks`、`FluidBucketHooks` |
| `dev.architectury.hooks.level.entity` | 3 | `ItemEntityHooks`、`EntityHooks`、`PlayerHooks` |
| `dev.architectury.core.item` | 3 | `ArchitecturySpawnEggItem`、`ArchitecturyBucketItem`、`ArchitecturyMobBucketItem` |
| `dev.architectury.hooks.item.tool` | 3 | `AxeItemHooks`、`ShovelItemHooks`、`HoeItemHooks` |
| `dev.architectury.registry.registries.options` | 3 | `RegistrarOption`、`DefaultIdRegistrarOption`、`StandardRegistrarOption` |
| `dev.architectury.registry.client.level.entity.forge` | 2 | `EntityRendererRegistryImpl`、`EntityModelLayerRegistryImpl` |
| `dev.architectury.registry.level.entity.forge` | 2 | `EntityAttributeRegistryImpl`、`SpawnPlacementsRegistryImpl` |
| `dev.architectury.registry.forge` | 2 | `CreativeTabRegistryImpl`、`ReloadListenerRegistryImpl` |
| `dev.architectury.registry.client.level.entity` | 2 | `EntityRendererRegistry`、`EntityModelLayerRegistry` |
| `dev.architectury.registry.menu` | 2 | `MenuRegistry`、`ExtendedMenuProvider` |
| `dev.architectury.registry.level.entity` | 2 | `EntityAttributeRegistry`、`SpawnPlacementsRegistry` |
| `dev.architectury.registry.level.entity.trade` | 2 | `TradeRegistry`、`SimpleTrade` |
| `dev.architectury.hooks.level.entity.forge` | 2 | `ItemEntityHooksImpl`、`PlayerHooksImpl` |
| `dev.architectury.platform` | 2 | `Platform`、`Mod` |
| `dev.architectury.hooks.client.screen` | 2 | `ScreenHooks`、`ScreenAccess` |
| `dev.architectury.annotations` | 2 | `ForgeEvent`、`ForgeEventCancellable` |
| `dev.architectury.hooks` | 2 | `PackRepositoryHooks`、`DyeColorHooks` |
| `dev.architectury.hooks.forge` | 2 | `PackRepositoryHooksImpl`、`DyeColorHooksImpl` |
| `dev.architectury.registry.registries.forge` | 1 | `RegistrarManagerImpl` |
| … | | 其余 37 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `EventHandlerImplClient` | class | — | OnlyIn | 44 | 419 | [EventHandlerImplClient.java](dev/architectury/event/forge/EventHandlerImplClient.java) |
| `EventHandlerImplCommon` | class | ChatEvent.ChatComponent | — | 52 | 444 | [EventHandlerImplCommon.java](dev/architectury/event/forge/EventHandlerImplCommon.java) |
| `RegistrarManagerImpl` | class | Registrar<T> | — | 41 | 419 | [RegistrarManagerImpl.java](dev/architectury/registry/registries/forge/RegistrarManagerImpl.java) |
| `ColorHandlerRegistryImpl` | class | — | — | 6 | 79 | [ColorHandlerRegistryImpl.java](dev/architectury/registry/client/rendering/forge/ColorHandlerRegistryImpl.java) |
| `NetworkManager` | class | — | — | 30 | 231 | [NetworkManager.java](dev/architectury/networking/NetworkManager.java) |
| `ClientCommandRegistrationEvent` | interface | — | — | 0 | 45 | [ClientCommandRegistrationEvent.java](dev/architectury/event/events/client/ClientCommandRegistrationEvent.java) |
| `EntityRendererRegistryImpl` | class | — | — | 2 | 32 | [EntityRendererRegistryImpl.java](dev/architectury/registry/client/level/entity/forge/EntityRendererRegistryImpl.java) |
| `CommandRegistrationEvent` | interface | — | — | 0 | 15 | [CommandRegistrationEvent.java](dev/architectury/event/events/common/CommandRegistrationEvent.java) |
| `EntityAttributeRegistryImpl` | class | — | — | 2 | 32 | [EntityAttributeRegistryImpl.java](dev/architectury/registry/level/entity/forge/EntityAttributeRegistryImpl.java) |
| `EntityModelLayerRegistryImpl` | class | — | — | 2 | 31 | [EntityModelLayerRegistryImpl.java](dev/architectury/registry/client/level/entity/forge/EntityModelLayerRegistryImpl.java) |
| `BlockEntityRendererRegistry` | class | — | OnlyIn | 1 | 23 | [BlockEntityRendererRegistry.java](dev/architectury/registry/client/rendering/BlockEntityRendererRegistry.java) |
| `BlockEntityRendererRegistryImpl` | class | — | — | 1 | 13 | [BlockEntityRendererRegistryImpl.java](dev/architectury/registry/client/rendering/forge/BlockEntityRendererRegistryImpl.java) |
| `NetworkManagerImpl` | class | — | — | 16 | 135 | [NetworkManagerImpl.java](dev/architectury/networking/forge/NetworkManagerImpl.java) |
| `CreativeTabRegistryImpl` | class | CreativeTabRegistryImpl.TabKey | — | 22 | 202 | [CreativeTabRegistryImpl.java](dev/architectury/registry/forge/CreativeTabRegistryImpl.java) |
| `ColorHandlerRegistry` | class | — | OnlyIn | 4 | 56 | [ColorHandlerRegistry.java](dev/architectury/registry/client/rendering/ColorHandlerRegistry.java) |
| `ArchitecturyNeoForge` | class | — | Mod | 0 | 19 | [ArchitecturyNeoForge.java](dev/architectury/neoforge/ArchitecturyNeoForge.java) |
| `MixinFallingBlockEntity` | class | Entity | Mixin | 1 | 39 | [MixinFallingBlockEntity.java](dev/architectury/mixin/forge/MixinFallingBlockEntity.java) |
| `CreativeTabRegistry` | class | — | — | 23 | 158 | [CreativeTabRegistry.java](dev/architectury/registry/CreativeTabRegistry.java) |
| `FuelRegistryImpl` | class | — | — | 3 | 38 | [FuelRegistryImpl.java](dev/architectury/registry/fuel/forge/FuelRegistryImpl.java) |
| `EntityRendererRegistry` | class | — | OnlyIn | 1 | 24 | [EntityRendererRegistry.java](dev/architectury/registry/client/level/entity/EntityRendererRegistry.java) |
| `SimpleNetworkManager` | class | — | — | 5 | 50 | [SimpleNetworkManager.java](dev/architectury/networking/simple/SimpleNetworkManager.java) |
| `MixinLevelEvent` | class | EventHandlerImplCommon.LevelEventAtt | Mixin | 0 | 25 | [MixinLevelEvent.java](dev/architectury/mixin/forge/MixinLevelEvent.java) |
| `MixinCommandSourceStack` | class | ClientCommandRegistrationEvent.Clien | Mixin | 0 | 50 | [MixinCommandSourceStack.java](dev/architectury/mixin/forge/client/MixinCommandSourceStack.java) |
| `EventFactory` | class | Event<T> | — | 26 | 254 | [EventFactory.java](dev/architectury/event/EventFactory.java) |
| `ClientRecipeUpdateEvent` | interface | — | OnlyIn | 0 | 15 | [ClientRecipeUpdateEvent.java](dev/architectury/event/events/client/ClientRecipeUpdateEvent.java) |
| `MenuRegistry` | class | — | — | 9 | 92 | [MenuRegistry.java](dev/architectury/registry/menu/MenuRegistry.java) |
| `BiomeModificationsImpl` | class | BiomeModificationsImpl.BiomeWrapped | — | 69 | 601 | [BiomeModificationsImpl.java](dev/architectury/registry/level/biome/forge/BiomeModificationsImpl.java) |
| `MixinClientLevel` | class | Level | Mixin | 2 | 50 | [MixinClientLevel.java](dev/architectury/mixin/forge/MixinClientLevel.java) |
| `MixinItemExtension` | interface | — | Mixin | 0 | 17 | [MixinItemExtension.java](dev/architectury/mixin/forge/MixinItemExtension.java) |
| `MixinBucketItem` | class | — | Mixin | 1 | 36 | [MixinBucketItem.java](dev/architectury/mixin/forge/neoforge/MixinBucketItem.java) |
| `LiquidBlockAccessor` | interface | — | Mixin | 0 | 13 | [LiquidBlockAccessor.java](dev/architectury/mixin/forge/neoforge/LiquidBlockAccessor.java) |
| `BucketItemAccessor` | interface | — | Mixin | 0 | 13 | [BucketItemAccessor.java](dev/architectury/mixin/forge/neoforge/BucketItemAccessor.java) |
| `MixinBlock` | class | InjectedBlockExtension | Mixin | 0 | 10 | [MixinBlock.java](dev/architectury/mixin/inject/MixinBlock.java) |
| `MixinBucketItem` | class | InjectedBucketItemExtension | Mixin | 0 | 10 | [MixinBucketItem.java](dev/architectury/mixin/inject/MixinBucketItem.java) |
| `MixinItemProperties` | class | InjectedItemPropertiesExtension | Mixin | 0 | 54 | [MixinItemProperties.java](dev/architectury/mixin/inject/MixinItemProperties.java) |
| `MixinLiquidBlock` | class | InjectedLiquidBlockExtension | Mixin | 0 | 10 | [MixinLiquidBlock.java](dev/architectury/mixin/inject/MixinLiquidBlock.java) |
| `MixinItem` | class | InjectedItemExtension | Mixin | 1 | 30 | [MixinItem.java](dev/architectury/mixin/inject/MixinItem.java) |
| `MixinEntityType` | class | InjectedEntityTypeExtension | Mixin | 0 | 10 | [MixinEntityType.java](dev/architectury/mixin/inject/MixinEntityType.java) |
| `EventHandler` | class | — | — | 4 | 51 | [EventHandler.java](dev/architectury/event/EventHandler.java) |
| `EventHandlerImplServer` | class | — | OnlyIn | 0 | 12 | [EventHandlerImplServer.java](dev/architectury/event/forge/EventHandlerImplServer.java) |
| `EventHandlerImpl` | class | — | — | 3 | 24 | [EventHandlerImpl.java](dev/architectury/event/forge/EventHandlerImpl.java) |
| `MenuRegistryImpl` | class | — | — | 4 | 37 | [MenuRegistryImpl.java](dev/architectury/registry/menu/forge/MenuRegistryImpl.java) |
| `EntityAttributeRegistry` | class | — | — | 1 | 21 | [EntityAttributeRegistry.java](dev/architectury/registry/level/entity/EntityAttributeRegistry.java) |
| `ItemPropertiesRegistry` | class | — | OnlyIn | 2 | 29 | [ItemPropertiesRegistry.java](dev/architectury/registry/item/ItemPropertiesRegistry.java) |
| `ItemPropertiesRegistryImpl` | class | — | — | 2 | 19 | [ItemPropertiesRegistryImpl.java](dev/architectury/registry/item/forge/ItemPropertiesRegistryImpl.java) |
| `ClientTooltipComponentRegistry` | class | — | OnlyIn | 1 | 23 | [ClientTooltipComponentRegistry.java](dev/architectury/registry/client/gui/ClientTooltipComponentRegistry.java) |
| `ClientTooltipComponentRegistryImpl` | class | — | Internal、OnlyIn | 1 | 45 | [ClientTooltipComponentRegistryImpl.java](dev/architectury/registry/client/gui/forge/ClientTooltipComponentRegistryImpl.java) |
| `EntityModelLayerRegistry` | class | — | OnlyIn | 1 | 20 | [EntityModelLayerRegistry.java](dev/architectury/registry/client/level/entity/EntityModelLayerRegistry.java) |
| `ParticleProviderRegistryImpl` | class | ParticleProviderRegistry.ExtendedSpr | — | 13 | 137 | [ParticleProviderRegistryImpl.java](dev/architectury/registry/client/particle/forge/ParticleProviderRegistryImpl.java) |
| `ArchitecturyFluidAttributesForge` | class | FluidType | — | 36 | 213 | [ArchitecturyFluidAttributesForge.java](dev/architectury/core/fluid/ArchitecturyFluidAttributesForge.java) |
| `SimpleArchitecturyFluidAttributes` | class | ArchitecturyFluidAttributes | — | 47 | 293 | [SimpleArchitecturyFluidAttributes.java](dev/architectury/core/fluid/SimpleArchitecturyFluidAttributes.java) |
| `FluidStack` | class | DataComponentHolder | — | 41 | 261 | [FluidStack.java](dev/architectury/fluid/FluidStack.java) |
| `ClientScreenInputEvent` | interface | — | OnlyIn | 0 | 56 | [ClientScreenInputEvent.java](dev/architectury/event/events/client/ClientScreenInputEvent.java) |
| `BiomeHooks` | class | BiomeHooks.BiomeWrapped | — | 48 | 395 | [BiomeHooks.java](dev/architectury/hooks/level/biome/BiomeHooks.java) |
| `SpawnEntityPacket` | class | CustomPacketPayload | — | 7 | 189 | [SpawnEntityPacket.java](dev/architectury/networking/SpawnEntityPacket.java) |
| `NetworkAggregator` | class | CustomPacketPayload | Internal | 11 | 247 | [NetworkAggregator.java](dev/architectury/impl/NetworkAggregator.java) |
| `RegistrySupplierImpl` | interface | — | Internal | 0 | 73 | [RegistrySupplierImpl.java](dev/architectury/impl/RegistrySupplierImpl.java) |
| `MixinLightningBolt` | class | Entity | Mixin | 1 | 40 | [MixinLightningBolt.java](dev/architectury/mixin/MixinLightningBolt.java) |
| `MixinInventory` | class | — | Mixin | 1 | 33 | [MixinInventory.java](dev/architectury/mixin/forge/MixinInventory.java) |
| `MixinMinecraft` | class | — | Mixin | 2 | 23 | [MixinMinecraft.java](dev/architectury/mixin/forge/MixinMinecraft.java) |

## 事件处理（`@SubscribeEvent`，8 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EventHandlerImplCommon` | 50 | `dev.architectury.event.forge` | [EventHandlerImplCommon.java](dev/architectury/event/forge/EventHandlerImplCommon.java) |
| `EventHandlerImplClient` | 44 | `dev.architectury.event.forge` | [EventHandlerImplClient.java](dev/architectury/event/forge/EventHandlerImplClient.java) |
| `RegistrarManagerImpl` | 3 | `dev.architectury.registry.registries.forge` | [RegistrarManagerImpl.java](dev/architectury/registry/registries/forge/RegistrarManagerImpl.java) |
| `ColorHandlerRegistryImpl` | 2 | `dev.architectury.registry.client.rendering.forge` | [ColorHandlerRegistryImpl.java](dev/architectury/registry/client/rendering/forge/ColorHandlerRegistryImpl.java) |
| `EntityRendererRegistryImpl` | 1 | `dev.architectury.registry.client.level.entity.forge` | [EntityRendererRegistryImpl.java](dev/architectury/registry/client/level/entity/forge/EntityRendererRegistryImpl.java) |
| `EntityAttributeRegistryImpl` | 1 | `dev.architectury.registry.level.entity.forge` | [EntityAttributeRegistryImpl.java](dev/architectury/registry/level/entity/forge/EntityAttributeRegistryImpl.java) |
| `EntityModelLayerRegistryImpl` | 1 | `dev.architectury.registry.client.level.entity.forge` | [EntityModelLayerRegistryImpl.java](dev/architectury/registry/client/level/entity/forge/EntityModelLayerRegistryImpl.java) |
| `FuelRegistryImpl` | 1 | `dev.architectury.registry.fuel.forge` | [FuelRegistryImpl.java](dev/architectury/registry/fuel/forge/FuelRegistryImpl.java) |

## Mixin（20 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinFallingBlockEntity` | `FallingBlockEntity` | [MixinFallingBlockEntity.java](dev/architectury/mixin/forge/MixinFallingBlockEntity.java) |
| `MixinLevelEvent` | `LevelEvent` | [MixinLevelEvent.java](dev/architectury/mixin/forge/MixinLevelEvent.java) |
| `MixinCommandSourceStack` | `CommandSourceStack` | [MixinCommandSourceStack.java](dev/architectury/mixin/forge/client/MixinCommandSourceStack.java) |
| `MixinClientLevel` | `ClientLevel` | [MixinClientLevel.java](dev/architectury/mixin/forge/MixinClientLevel.java) |
| `MixinItemExtension` | `ItemExtension` | [MixinItemExtension.java](dev/architectury/mixin/forge/MixinItemExtension.java) |
| `MixinBucketItem` | `BucketItem` | [MixinBucketItem.java](dev/architectury/mixin/forge/neoforge/MixinBucketItem.java) |
| `LiquidBlockAccessor` | `LiquidBlockAccessor` | [LiquidBlockAccessor.java](dev/architectury/mixin/forge/neoforge/LiquidBlockAccessor.java) |
| `BucketItemAccessor` | `BucketItemAccessor` | [BucketItemAccessor.java](dev/architectury/mixin/forge/neoforge/BucketItemAccessor.java) |
| `MixinBlock` | `Block` | [MixinBlock.java](dev/architectury/mixin/inject/MixinBlock.java) |
| `MixinBucketItem` | `BucketItem` | [MixinBucketItem.java](dev/architectury/mixin/inject/MixinBucketItem.java) |
| `MixinItemProperties` | `ItemProperties` | [MixinItemProperties.java](dev/architectury/mixin/inject/MixinItemProperties.java) |
| `MixinLiquidBlock` | `LiquidBlock` | [MixinLiquidBlock.java](dev/architectury/mixin/inject/MixinLiquidBlock.java) |
| `MixinItem` | `Item` | [MixinItem.java](dev/architectury/mixin/inject/MixinItem.java) |
| `MixinEntityType` | `EntityType` | [MixinEntityType.java](dev/architectury/mixin/inject/MixinEntityType.java) |
| `MixinLightningBolt` | `LightningBolt` | [MixinLightningBolt.java](dev/architectury/mixin/MixinLightningBolt.java) |
| `MixinInventory` | `Inventory` | [MixinInventory.java](dev/architectury/mixin/forge/MixinInventory.java) |
| `MixinMinecraft` | `Minecraft` | [MixinMinecraft.java](dev/architectury/mixin/forge/MixinMinecraft.java) |
| `MixinChunkSerializer` | `ChunkSerializer` | [MixinChunkSerializer.java](dev/architectury/mixin/forge/neoforge/MixinChunkSerializer.java) |
| `MixinFluid` | `Fluid` | [MixinFluid.java](dev/architectury/mixin/inject/MixinFluid.java) |
| `MixinFoodPropertiesBuilder` | `FoodPropertiesBuilder` | [MixinFoodPropertiesBuilder.java](dev/architectury/mixin/inject/MixinFoodPropertiesBuilder.java) |

## 按包浏览

- `architectury_inject_architectury_common_90b77f7e91db42de8d10e5cc821610bb_5bd1878b82892d39a5055ed8d0e40a74688282c910e605d53cbddbcf6ff84c53architectury1308devjar`（1 类）
- `dev.architectury.annotations`（2 类）
- `dev.architectury.core.block`（1 类）
- `dev.architectury.core.fluid`（4 类）
- `dev.architectury.core.item`（3 类）
- `dev.architectury.event`（6 类）
- `dev.architectury.event.events.client`（12 类）
- `dev.architectury.event.events.common`（13 类）
- `dev.architectury.event.forge`（6 类）
- `dev.architectury.extensions`（1 类）
- `dev.architectury.extensions.injected`（9 类）
- `dev.architectury.extensions.network`（1 类）
- `dev.architectury.fluid`（1 类）
- `dev.architectury.fluid.forge`（1 类）
- `dev.architectury.hooks`（2 类）
- `dev.architectury.hooks.block`（1 类）
- `dev.architectury.hooks.client.screen`（2 类）
- `dev.architectury.hooks.client.screen.forge`（1 类）
- `dev.architectury.hooks.fluid`（3 类）
- `dev.architectury.hooks.fluid.forge`（4 类）
- `dev.architectury.hooks.forge`（2 类）
- `dev.architectury.hooks.item`（1 类）
- `dev.architectury.hooks.item.food`（1 类）
- `dev.architectury.hooks.item.food.forge`（1 类）
- `dev.architectury.hooks.item.forge`（1 类）
- `dev.architectury.hooks.item.tool`（3 类）
- `dev.architectury.hooks.item.tool.forge`（1 类）
- `dev.architectury.hooks.level`（1 类）
- `dev.architectury.hooks.level.biome`（6 类）
- `dev.architectury.hooks.level.biome.forge`（1 类）
- `dev.architectury.hooks.level.entity`（3 类）
- `dev.architectury.hooks.level.entity.forge`（2 类）
- `dev.architectury.impl`（7 类）
- `dev.architectury.mixin`（1 类）
- `dev.architectury.mixin.forge`（6 类）
- `dev.architectury.mixin.forge.client`（1 类）
- `dev.architectury.mixin.forge.neoforge`（4 类）
- `dev.architectury.mixin.inject`（8 类）
- `dev.architectury.neoforge`（1 类）
- `dev.architectury.networking`（3 类）
- `dev.architectury.networking.forge`（1 类）
- `dev.architectury.networking.simple`（6 类）
- `dev.architectury.networking.transformers`（5 类）
- `dev.architectury.platform`（2 类）
- `dev.architectury.platform.forge`（1 类）
- `dev.architectury.platform.hooks`（1 类）
- `dev.architectury.plugin.forge`（1 类）
- `dev.architectury.registry`（3 类）
- `dev.architectury.registry.client.gui`（1 类）
- `dev.architectury.registry.client.gui.forge`（1 类）
- `dev.architectury.registry.client.keymappings`（1 类）
- `dev.architectury.registry.client.keymappings.forge`（1 类）
- `dev.architectury.registry.client.level.entity`（2 类）
- `dev.architectury.registry.client.level.entity.forge`（2 类）
- `dev.architectury.registry.client.particle`（1 类）
- `dev.architectury.registry.client.particle.forge`（1 类）
- `dev.architectury.registry.client.rendering`（3 类）
- `dev.architectury.registry.client.rendering.forge`（3 类）
- `dev.architectury.registry.forge`（2 类）
- `dev.architectury.registry.fuel`（1 类）
- `dev.architectury.registry.fuel.forge`（1 类）
- `dev.architectury.registry.item`（1 类）
- `dev.architectury.registry.item.forge`（1 类）
- `dev.architectury.registry.level.biome`（1 类）
- `dev.architectury.registry.level.biome.forge`（1 类）
- `dev.architectury.registry.level.entity`（2 类）
- `dev.architectury.registry.level.entity.forge`（2 类）
- `dev.architectury.registry.level.entity.trade`（2 类）
- `dev.architectury.registry.level.entity.trade.forge`（1 类）
- `dev.architectury.registry.menu`（2 类）
- `dev.architectury.registry.menu.forge`（1 类）
- `dev.architectury.registry.registries`（6 类）
- `dev.architectury.registry.registries.forge`（1 类）
- `dev.architectury.registry.registries.options`（3 类）
- `dev.architectury.utils`（7 类）
- `dev.architectury.utils.forge`（1 类）
- `dev.architectury.utils.value`（7 类）
