# Placebo（`placebo`）

> jar：`[前置]Placebo.jar` ｜ 版本：`9.9.1` ｜ 作者：Shadows_of_Fire
> 规模：**101 个类 / 27 个包 / 9225 行** ｜ 事件处理类 9 ｜ Mixin 6 ｜ 注册/管理类 12

**依赖**：`minecraft`、`neoforge`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.shadowsoffire.placebo.util` | 14 | `AbstractBiMap`、`PlaceboTaskQueue`、`ClientUtil` |
| `dev.shadowsoffire.placebo.menu` | 10 | `BlockEntityMenu`、`QuickMoveHandler`、`PlaceboContainerMenu` |
| `dev.shadowsoffire.placebo.config` | 7 | `Configuration`、`ConfigCategory`、`ConfigElement` |
| `dev.shadowsoffire.placebo.reload` | 6 | `DynamicRegistry`、`WeightedDynamicRegistry`、`RegistryCallback` |
| `dev.shadowsoffire.placebo.json` | 6 | `WeightedItemStack`、`RandomAttributeModifier`、`JsonUtil` |
| `dev.shadowsoffire.placebo.patreon` | 4 | `PatreonPreview`、`WingsManager`、`TrailsManager` |
| `dev.shadowsoffire.placebo.mixin` | 4 | `ItemStackMixin`、`AnvilBlockMixin`、`DataProviderMixin` |
| `dev.shadowsoffire.placebo.datagen` | 4 | `LegacyRecipeProvider`、`DataGenBuilder`、`FilteredOrderingFactory` |
| `dev.shadowsoffire.placebo.commands` | 4 | `SerializeLootTableCommand`、`HandToJsonCommand`、`PlaceboCommand` |
| `dev.shadowsoffire.placebo.screen` | 4 | `TickableTextList`、`ScreenUtil`、`PlaceboContainerScreen` |
| `dev.shadowsoffire.placebo.codec` | 4 | `PlaceboCodecs`、`CodecMap`、`CodecProvider` |
| `dev.shadowsoffire.placebo.systems.wanderer` | 3 | `WandererTradesRegistry`、`BasicWandererTrade`、`WandererTrade` |
| `dev.shadowsoffire.placebo` | 3 | `PlaceboClient`、`Placebo`、`PlaceboConfig` |
| `dev.shadowsoffire.placebo.network` | 3 | `PayloadHelper`、`VanillaPacketDispatcher`、`PayloadProvider` |
| `dev.shadowsoffire.placebo.block_entity` | 3 | `TickingBlockEntityType`、`TickingBlockEntity`、`TickingEntityBlock` |
| `dev.shadowsoffire.placebo.patreon.wings` | 3 | `Wing`、`WingLayer`、`IWingModel` |
| `dev.shadowsoffire.placebo.mixin.client` | 2 | `AbstractContainerScreenMixin`、`ChatComponentMixin` |
| `dev.shadowsoffire.placebo.systems.mixes` | 2 | `MixRegistry`、`JsonMix` |
| `dev.shadowsoffire.placebo.tabs` | 2 | `TabFillingRegistry`、`ITabFiller` |
| `dev.shadowsoffire.placebo.cap` | 2 | `InternalItemHandler`、`ModifiableEnergyStorage` |
| `dev.shadowsoffire.placebo.util.data` | 2 | `DynamicRegistryProvider`、`RuntimeDatagenHelpers` |
| `dev.shadowsoffire.placebo.systems.gear` | 2 | `GearSetRegistry`、`GearSet` |
| `dev.shadowsoffire.placebo.events` | 2 | `AnvilLandEvent`、`ResourceReloadEvent` |
| `dev.shadowsoffire.placebo.payloads` | 2 | `PatreonDisablePayload`、`ButtonClickPayload` |
| `dev.shadowsoffire.placebo.registry` | 1 | `DeferredHelper` |
| `dev.shadowsoffire.placebo.loot` | 1 | `StackLootEntry` |
| `dev.shadowsoffire.placebo.color` | 1 | `GradientColor` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `WandererTradesRegistry` | class | DynamicRegistry | EventBusSubscriber | 4 | 61 | [WandererTradesRegistry.java](dev/shadowsoffire/placebo/systems/wanderer/WandererTradesRegistry.java) |
| `PlaceboClient` | class | — | EventBusSubscriber | 12 | 115 | [PlaceboClient.java](dev/shadowsoffire/placebo/PlaceboClient.java) |
| `Placebo` | class | — | Mod | 6 | 85 | [Placebo.java](dev/shadowsoffire/placebo/Placebo.java) |
| `PatreonPreview` | class | — | EventBusSubscriber | 1 | 24 | [PatreonPreview.java](dev/shadowsoffire/placebo/patreon/PatreonPreview.java) |
| `Configuration` | class | Reader | — | 71 | 894 | [Configuration.java](dev/shadowsoffire/placebo/config/Configuration.java) |
| `DynamicRegistry` | class | — | — | 40 | 404 | [DynamicRegistry.java](dev/shadowsoffire/placebo/reload/DynamicRegistry.java) |
| `DeferredHelper` | class | — | — | 65 | 515 | [DeferredHelper.java](dev/shadowsoffire/placebo/registry/DeferredHelper.java) |
| `ConfigCategory` | class | Map<String | — | 41 | 366 | [ConfigCategory.java](dev/shadowsoffire/placebo/config/ConfigCategory.java) |
| `ConfigElement` | class | IConfigElement | — | 29 | 328 | [ConfigElement.java](dev/shadowsoffire/placebo/config/ConfigElement.java) |
| `WingsManager` | class | — | — | 3 | 80 | [WingsManager.java](dev/shadowsoffire/placebo/patreon/WingsManager.java) |
| `TrailsManager` | class | — | — | 3 | 92 | [TrailsManager.java](dev/shadowsoffire/placebo/patreon/TrailsManager.java) |
| `Property` | class | — | — | 81 | 701 | [Property.java](dev/shadowsoffire/placebo/config/Property.java) |
| `ItemStackMixin` | class | CachedObject.CachedObjectSource | Mixin | 2 | 34 | [ItemStackMixin.java](dev/shadowsoffire/placebo/mixin/ItemStackMixin.java) |
| `AnvilBlockMixin` | class | — | Mixin | 1 | 22 | [AnvilBlockMixin.java](dev/shadowsoffire/placebo/mixin/AnvilBlockMixin.java) |
| `AbstractContainerScreenMixin` | class | DrawsOnLeft | Mixin | 0 | 10 | [AbstractContainerScreenMixin.java](dev/shadowsoffire/placebo/mixin/client/AbstractContainerScreenMixin.java) |
| `LegacyRecipeProvider` | class | RecipeProvider | — | 15 | 218 | [LegacyRecipeProvider.java](dev/shadowsoffire/placebo/datagen/LegacyRecipeProvider.java) |
| `MixRegistry` | class | DynamicRegistry | — | 8 | 89 | [MixRegistry.java](dev/shadowsoffire/placebo/systems/mixes/MixRegistry.java) |
| `DataProviderMixin` | interface | — | Mixin | 0 | 53 | [DataProviderMixin.java](dev/shadowsoffire/placebo/mixin/DataProviderMixin.java) |
| `TabFillingRegistry` | class | — | — | 8 | 68 | [TabFillingRegistry.java](dev/shadowsoffire/placebo/tabs/TabFillingRegistry.java) |
| `InternalItemHandler` | class | ItemStackHandler | — | 2 | 19 | [InternalItemHandler.java](dev/shadowsoffire/placebo/cap/InternalItemHandler.java) |
| `AbstractBiMap` | class | ForwardingMap | — | 45 | 398 | [AbstractBiMap.java](dev/shadowsoffire/placebo/util/AbstractBiMap.java) |
| `DynamicRegistryProvider` | class | DataProvider | — | 7 | 129 | [DynamicRegistryProvider.java](dev/shadowsoffire/placebo/util/data/DynamicRegistryProvider.java) |
| `WeightedDynamicRegistry` | class | — | — | 6 | 110 | [WeightedDynamicRegistry.java](dev/shadowsoffire/placebo/reload/WeightedDynamicRegistry.java) |
| `GearSetRegistry` | class | WeightedDynamicRegistry | — | 2 | 52 | [GearSetRegistry.java](dev/shadowsoffire/placebo/systems/gear/GearSetRegistry.java) |
| `HashCacheMixin` | class | — | Mixin | 1 | 33 | [HashCacheMixin.java](dev/shadowsoffire/placebo/mixin/HashCacheMixin.java) |
| `ChatComponentMixin` | class | — | Mixin | 1 | 14 | [ChatComponentMixin.java](dev/shadowsoffire/placebo/mixin/client/ChatComponentMixin.java) |
| `BlockEntityMenu` | class | PlaceboContainerMenu | — | 1 | 26 | [BlockEntityMenu.java](dev/shadowsoffire/placebo/menu/BlockEntityMenu.java) |
| `RegistryCallback` | interface | RegistryCallback<R> | — | 2 | 45 | [RegistryCallback.java](dev/shadowsoffire/placebo/reload/RegistryCallback.java) |
| `SerializeLootTableCommand` | class | — | — | 1 | 52 | [SerializeLootTableCommand.java](dev/shadowsoffire/placebo/commands/SerializeLootTableCommand.java) |
| `PayloadHelper` | class | IPayloadHandler<T> | — | 3 | 88 | [PayloadHelper.java](dev/shadowsoffire/placebo/network/PayloadHelper.java) |
| `QuickMoveHandler` | class | — | — | 4 | 73 | [QuickMoveHandler.java](dev/shadowsoffire/placebo/menu/QuickMoveHandler.java) |
| `PlaceboTaskQueue` | class | PlaceboTaskQueue.Task | — | 7 | 88 | [PlaceboTaskQueue.java](dev/shadowsoffire/placebo/util/PlaceboTaskQueue.java) |
| `PlaceboConfig` | class | — | — | 1 | 29 | [PlaceboConfig.java](dev/shadowsoffire/placebo/PlaceboConfig.java) |
| `TickableTextList` | class | FormattedCharSink | — | 20 | 154 | [TickableTextList.java](dev/shadowsoffire/placebo/screen/TickableTextList.java) |
| `AnvilLandEvent` | class | Event | — | 5 | 44 | [AnvilLandEvent.java](dev/shadowsoffire/placebo/events/AnvilLandEvent.java) |
| `PlaceboContainerMenu` | class | AbstractContainerMenu | — | 13 | 115 | [PlaceboContainerMenu.java](dev/shadowsoffire/placebo/menu/PlaceboContainerMenu.java) |
| `ConfigFlags` | class | — | — | 2 | 37 | [ConfigFlags.java](dev/shadowsoffire/placebo/config/ConfigFlags.java) |
| `IConfigElement` | interface | — | — | 0 | 63 | [IConfigElement.java](dev/shadowsoffire/placebo/config/IConfigElement.java) |
| `ConfigGuiType` | enum | — | — | 0 | 12 | [ConfigGuiType.java](dev/shadowsoffire/placebo/config/ConfigGuiType.java) |
| `ClientUtil` | class | — | — | 5 | 92 | [ClientUtil.java](dev/shadowsoffire/placebo/util/ClientUtil.java) |
| `ReloadListenerPayloads` | class | CustomPacketPayload | Internal | 23 | 183 | [ReloadListenerPayloads.java](dev/shadowsoffire/placebo/reload/ReloadListenerPayloads.java) |
| `TickingBlockEntityType` | class | BlockEntityType | — | 3 | 42 | [TickingBlockEntityType.java](dev/shadowsoffire/placebo/block_entity/TickingBlockEntityType.java) |
| `TickingBlockEntity` | interface | — | — | 0 | 14 | [TickingBlockEntity.java](dev/shadowsoffire/placebo/block_entity/TickingBlockEntity.java) |
| `TickingEntityBlock` | interface | — | — | 0 | 29 | [TickingEntityBlock.java](dev/shadowsoffire/placebo/block_entity/TickingEntityBlock.java) |
| `VanillaPacketDispatcher` | class | — | — | 2 | 22 | [VanillaPacketDispatcher.java](dev/shadowsoffire/placebo/network/VanillaPacketDispatcher.java) |
| `ScreenUtil` | class | — | — | 1 | 10 | [ScreenUtil.java](dev/shadowsoffire/placebo/screen/ScreenUtil.java) |
| `ResourceReloadEvent` | class | Event | — | 2 | 24 | [ResourceReloadEvent.java](dev/shadowsoffire/placebo/events/ResourceReloadEvent.java) |
| `MenuUtil` | class | — | — | 4 | 45 | [MenuUtil.java](dev/shadowsoffire/placebo/menu/MenuUtil.java) |
| `StackLootEntry` | class | LootPoolSingletonContainer | — | 2 | 62 | [StackLootEntry.java](dev/shadowsoffire/placebo/loot/StackLootEntry.java) |
| `DataGenBuilder` | class | — | — | 10 | 99 | [DataGenBuilder.java](dev/shadowsoffire/placebo/datagen/DataGenBuilder.java) |
| `RuntimeDatagenHelpers` | class | — | — | 5 | 50 | [RuntimeDatagenHelpers.java](dev/shadowsoffire/placebo/util/data/RuntimeDatagenHelpers.java) |
| `HandToJsonCommand` | class | — | — | 2 | 56 | [HandToJsonCommand.java](dev/shadowsoffire/placebo/commands/HandToJsonCommand.java) |
| `PlaceboCommand` | class | — | — | 1 | 18 | [PlaceboCommand.java](dev/shadowsoffire/placebo/commands/PlaceboCommand.java) |
| `GetDimensionTypeCommand` | class | — | — | 1 | 31 | [GetDimensionTypeCommand.java](dev/shadowsoffire/placebo/commands/GetDimensionTypeCommand.java) |
| `BasicWandererTrade` | class | BasicItemListing | — | 14 | 105 | [BasicWandererTrade.java](dev/shadowsoffire/placebo/systems/wanderer/BasicWandererTrade.java) |
| `PlaceboContainerScreen` | class | AbstractContainerScreen | — | 1 | 20 | [PlaceboContainerScreen.java](dev/shadowsoffire/placebo/screen/PlaceboContainerScreen.java) |
| `WeightedItemStack` | class | IntrusiveBase | — | 3 | 47 | [WeightedItemStack.java](dev/shadowsoffire/placebo/json/WeightedItemStack.java) |
| `SimplerMenuProvider` | class | MenuProvider | — | 2 | 29 | [SimplerMenuProvider.java](dev/shadowsoffire/placebo/menu/SimplerMenuProvider.java) |
| `SimpleDataSlots` | class | SimpleDataSlots.LambdaDataSlot | — | 8 | 68 | [SimpleDataSlots.java](dev/shadowsoffire/placebo/menu/SimpleDataSlots.java) |
| `FilteredOrderingFactory` | record | FieldOrderingFactory | — | 13 | 96 | [FilteredOrderingFactory.java](dev/shadowsoffire/placebo/datagen/FilteredOrderingFactory.java) |

## 事件处理（`@SubscribeEvent`，9 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `PlaceboClient` | 4 | `dev.shadowsoffire.placebo` | [PlaceboClient.java](dev/shadowsoffire/placebo/PlaceboClient.java) |
| `DeferredHelper` | 3 | `dev.shadowsoffire.placebo.registry` | [DeferredHelper.java](dev/shadowsoffire/placebo/registry/DeferredHelper.java) |
| `PlaceboTaskQueue` | 3 | `dev.shadowsoffire.placebo.util` | [PlaceboTaskQueue.java](dev/shadowsoffire/placebo/util/PlaceboTaskQueue.java) |
| `Placebo` | 2 | `dev.shadowsoffire.placebo` | [Placebo.java](dev/shadowsoffire/placebo/Placebo.java) |
| `TrailsManager` | 2 | `dev.shadowsoffire.placebo.patreon` | [TrailsManager.java](dev/shadowsoffire/placebo/patreon/TrailsManager.java) |
| `WandererTradesRegistry` | 1 | `dev.shadowsoffire.placebo.systems.wanderer` | [WandererTradesRegistry.java](dev/shadowsoffire/placebo/systems/wanderer/WandererTradesRegistry.java) |
| `PatreonPreview` | 1 | `dev.shadowsoffire.placebo.patreon` | [PatreonPreview.java](dev/shadowsoffire/placebo/patreon/PatreonPreview.java) |
| `WingsManager` | 1 | `dev.shadowsoffire.placebo.patreon` | [WingsManager.java](dev/shadowsoffire/placebo/patreon/WingsManager.java) |
| `PayloadHelper` | 1 | `dev.shadowsoffire.placebo.network` | [PayloadHelper.java](dev/shadowsoffire/placebo/network/PayloadHelper.java) |

## Mixin（6 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](dev/shadowsoffire/placebo/mixin/ItemStackMixin.java) |
| `AnvilBlockMixin` | `AnvilBlock` | [AnvilBlockMixin.java](dev/shadowsoffire/placebo/mixin/AnvilBlockMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](dev/shadowsoffire/placebo/mixin/client/AbstractContainerScreenMixin.java) |
| `DataProviderMixin` | `DataProvider` | [DataProviderMixin.java](dev/shadowsoffire/placebo/mixin/DataProviderMixin.java) |
| `HashCacheMixin` | `HashCache` | [HashCacheMixin.java](dev/shadowsoffire/placebo/mixin/HashCacheMixin.java) |
| `ChatComponentMixin` | `ChatComponent` | [ChatComponentMixin.java](dev/shadowsoffire/placebo/mixin/client/ChatComponentMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Configuration` | — | [Configuration.java](dev/shadowsoffire/placebo/config/Configuration.java) |
| `ConfigCategory` | — | [ConfigCategory.java](dev/shadowsoffire/placebo/config/ConfigCategory.java) |
| `ConfigElement` | — | [ConfigElement.java](dev/shadowsoffire/placebo/config/ConfigElement.java) |
| `PlaceboConfig` | — | [PlaceboConfig.java](dev/shadowsoffire/placebo/PlaceboConfig.java) |
| `ConfigFlags` | — | [ConfigFlags.java](dev/shadowsoffire/placebo/config/ConfigFlags.java) |
| `IConfigElement` | — | [IConfigElement.java](dev/shadowsoffire/placebo/config/IConfigElement.java) |
| `ConfigGuiType` | — | [ConfigGuiType.java](dev/shadowsoffire/placebo/config/ConfigGuiType.java) |

## 全部类（101）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `WandererTradesRegistry` | class | DynamicRegistry | EventBusSubscriber | 4 | 61 | [WandererTradesRegistry.java](dev/shadowsoffire/placebo/systems/wanderer/WandererTradesRegistry.java) |
| `PlaceboClient` | class | — | EventBusSubscriber | 12 | 115 | [PlaceboClient.java](dev/shadowsoffire/placebo/PlaceboClient.java) |
| `Placebo` | class | — | Mod | 6 | 85 | [Placebo.java](dev/shadowsoffire/placebo/Placebo.java) |
| `PatreonPreview` | class | — | EventBusSubscriber | 1 | 24 | [PatreonPreview.java](dev/shadowsoffire/placebo/patreon/PatreonPreview.java) |
| `Configuration` | class | Reader | — | 71 | 894 | [Configuration.java](dev/shadowsoffire/placebo/config/Configuration.java) |
| `DynamicRegistry` | class | — | — | 40 | 404 | [DynamicRegistry.java](dev/shadowsoffire/placebo/reload/DynamicRegistry.java) |
| `DeferredHelper` | class | — | — | 65 | 515 | [DeferredHelper.java](dev/shadowsoffire/placebo/registry/DeferredHelper.java) |
| `ConfigCategory` | class | Map<String | — | 41 | 366 | [ConfigCategory.java](dev/shadowsoffire/placebo/config/ConfigCategory.java) |
| `ConfigElement` | class | IConfigElement | — | 29 | 328 | [ConfigElement.java](dev/shadowsoffire/placebo/config/ConfigElement.java) |
| `WingsManager` | class | — | — | 3 | 80 | [WingsManager.java](dev/shadowsoffire/placebo/patreon/WingsManager.java) |
| `TrailsManager` | class | — | — | 3 | 92 | [TrailsManager.java](dev/shadowsoffire/placebo/patreon/TrailsManager.java) |
| `Property` | class | — | — | 81 | 701 | [Property.java](dev/shadowsoffire/placebo/config/Property.java) |
| `ItemStackMixin` | class | CachedObject.CachedObjectSource | Mixin | 2 | 34 | [ItemStackMixin.java](dev/shadowsoffire/placebo/mixin/ItemStackMixin.java) |
| `AnvilBlockMixin` | class | — | Mixin | 1 | 22 | [AnvilBlockMixin.java](dev/shadowsoffire/placebo/mixin/AnvilBlockMixin.java) |
| `AbstractContainerScreenMixin` | class | DrawsOnLeft | Mixin | 0 | 10 | [AbstractContainerScreenMixin.java](dev/shadowsoffire/placebo/mixin/client/AbstractContainerScreenMixin.java) |
| `LegacyRecipeProvider` | class | RecipeProvider | — | 15 | 218 | [LegacyRecipeProvider.java](dev/shadowsoffire/placebo/datagen/LegacyRecipeProvider.java) |
| `MixRegistry` | class | DynamicRegistry | — | 8 | 89 | [MixRegistry.java](dev/shadowsoffire/placebo/systems/mixes/MixRegistry.java) |
| `DataProviderMixin` | interface | — | Mixin | 0 | 53 | [DataProviderMixin.java](dev/shadowsoffire/placebo/mixin/DataProviderMixin.java) |
| `TabFillingRegistry` | class | — | — | 8 | 68 | [TabFillingRegistry.java](dev/shadowsoffire/placebo/tabs/TabFillingRegistry.java) |
| `InternalItemHandler` | class | ItemStackHandler | — | 2 | 19 | [InternalItemHandler.java](dev/shadowsoffire/placebo/cap/InternalItemHandler.java) |
| `AbstractBiMap` | class | ForwardingMap | — | 45 | 398 | [AbstractBiMap.java](dev/shadowsoffire/placebo/util/AbstractBiMap.java) |
| `DynamicRegistryProvider` | class | DataProvider | — | 7 | 129 | [DynamicRegistryProvider.java](dev/shadowsoffire/placebo/util/data/DynamicRegistryProvider.java) |
| `WeightedDynamicRegistry` | class | — | — | 6 | 110 | [WeightedDynamicRegistry.java](dev/shadowsoffire/placebo/reload/WeightedDynamicRegistry.java) |
| `GearSetRegistry` | class | WeightedDynamicRegistry | — | 2 | 52 | [GearSetRegistry.java](dev/shadowsoffire/placebo/systems/gear/GearSetRegistry.java) |
| `HashCacheMixin` | class | — | Mixin | 1 | 33 | [HashCacheMixin.java](dev/shadowsoffire/placebo/mixin/HashCacheMixin.java) |
| `ChatComponentMixin` | class | — | Mixin | 1 | 14 | [ChatComponentMixin.java](dev/shadowsoffire/placebo/mixin/client/ChatComponentMixin.java) |
| `BlockEntityMenu` | class | PlaceboContainerMenu | — | 1 | 26 | [BlockEntityMenu.java](dev/shadowsoffire/placebo/menu/BlockEntityMenu.java) |
| `RegistryCallback` | interface | RegistryCallback<R> | — | 2 | 45 | [RegistryCallback.java](dev/shadowsoffire/placebo/reload/RegistryCallback.java) |
| `SerializeLootTableCommand` | class | — | — | 1 | 52 | [SerializeLootTableCommand.java](dev/shadowsoffire/placebo/commands/SerializeLootTableCommand.java) |
| `PayloadHelper` | class | IPayloadHandler<T> | — | 3 | 88 | [PayloadHelper.java](dev/shadowsoffire/placebo/network/PayloadHelper.java) |
| `QuickMoveHandler` | class | — | — | 4 | 73 | [QuickMoveHandler.java](dev/shadowsoffire/placebo/menu/QuickMoveHandler.java) |
| `PlaceboTaskQueue` | class | PlaceboTaskQueue.Task | — | 7 | 88 | [PlaceboTaskQueue.java](dev/shadowsoffire/placebo/util/PlaceboTaskQueue.java) |
| `PlaceboConfig` | class | — | — | 1 | 29 | [PlaceboConfig.java](dev/shadowsoffire/placebo/PlaceboConfig.java) |
| `TickableTextList` | class | FormattedCharSink | — | 20 | 154 | [TickableTextList.java](dev/shadowsoffire/placebo/screen/TickableTextList.java) |
| `AnvilLandEvent` | class | Event | — | 5 | 44 | [AnvilLandEvent.java](dev/shadowsoffire/placebo/events/AnvilLandEvent.java) |
| `PlaceboContainerMenu` | class | AbstractContainerMenu | — | 13 | 115 | [PlaceboContainerMenu.java](dev/shadowsoffire/placebo/menu/PlaceboContainerMenu.java) |
| `ConfigFlags` | class | — | — | 2 | 37 | [ConfigFlags.java](dev/shadowsoffire/placebo/config/ConfigFlags.java) |
| `IConfigElement` | interface | — | — | 0 | 63 | [IConfigElement.java](dev/shadowsoffire/placebo/config/IConfigElement.java) |
| `ConfigGuiType` | enum | — | — | 0 | 12 | [ConfigGuiType.java](dev/shadowsoffire/placebo/config/ConfigGuiType.java) |
| `ClientUtil` | class | — | — | 5 | 92 | [ClientUtil.java](dev/shadowsoffire/placebo/util/ClientUtil.java) |
| `ReloadListenerPayloads` | class | CustomPacketPayload | Internal | 23 | 183 | [ReloadListenerPayloads.java](dev/shadowsoffire/placebo/reload/ReloadListenerPayloads.java) |
| `TickingBlockEntityType` | class | BlockEntityType | — | 3 | 42 | [TickingBlockEntityType.java](dev/shadowsoffire/placebo/block_entity/TickingBlockEntityType.java) |
| `TickingBlockEntity` | interface | — | — | 0 | 14 | [TickingBlockEntity.java](dev/shadowsoffire/placebo/block_entity/TickingBlockEntity.java) |
| `TickingEntityBlock` | interface | — | — | 0 | 29 | [TickingEntityBlock.java](dev/shadowsoffire/placebo/block_entity/TickingEntityBlock.java) |
| `VanillaPacketDispatcher` | class | — | — | 2 | 22 | [VanillaPacketDispatcher.java](dev/shadowsoffire/placebo/network/VanillaPacketDispatcher.java) |
| `ScreenUtil` | class | — | — | 1 | 10 | [ScreenUtil.java](dev/shadowsoffire/placebo/screen/ScreenUtil.java) |
| `ResourceReloadEvent` | class | Event | — | 2 | 24 | [ResourceReloadEvent.java](dev/shadowsoffire/placebo/events/ResourceReloadEvent.java) |
| `MenuUtil` | class | — | — | 4 | 45 | [MenuUtil.java](dev/shadowsoffire/placebo/menu/MenuUtil.java) |
| `StackLootEntry` | class | LootPoolSingletonContainer | — | 2 | 62 | [StackLootEntry.java](dev/shadowsoffire/placebo/loot/StackLootEntry.java) |
| `DataGenBuilder` | class | — | — | 10 | 99 | [DataGenBuilder.java](dev/shadowsoffire/placebo/datagen/DataGenBuilder.java) |
| `RuntimeDatagenHelpers` | class | — | — | 5 | 50 | [RuntimeDatagenHelpers.java](dev/shadowsoffire/placebo/util/data/RuntimeDatagenHelpers.java) |
| `HandToJsonCommand` | class | — | — | 2 | 56 | [HandToJsonCommand.java](dev/shadowsoffire/placebo/commands/HandToJsonCommand.java) |
| `PlaceboCommand` | class | — | — | 1 | 18 | [PlaceboCommand.java](dev/shadowsoffire/placebo/commands/PlaceboCommand.java) |
| `GetDimensionTypeCommand` | class | — | — | 1 | 31 | [GetDimensionTypeCommand.java](dev/shadowsoffire/placebo/commands/GetDimensionTypeCommand.java) |
| `BasicWandererTrade` | class | BasicItemListing | — | 14 | 105 | [BasicWandererTrade.java](dev/shadowsoffire/placebo/systems/wanderer/BasicWandererTrade.java) |
| `PlaceboContainerScreen` | class | AbstractContainerScreen | — | 1 | 20 | [PlaceboContainerScreen.java](dev/shadowsoffire/placebo/screen/PlaceboContainerScreen.java) |
| `WeightedItemStack` | class | IntrusiveBase | — | 3 | 47 | [WeightedItemStack.java](dev/shadowsoffire/placebo/json/WeightedItemStack.java) |
| `SimplerMenuProvider` | class | MenuProvider | — | 2 | 29 | [SimplerMenuProvider.java](dev/shadowsoffire/placebo/menu/SimplerMenuProvider.java) |
| `SimpleDataSlots` | class | SimpleDataSlots.LambdaDataSlot | — | 8 | 68 | [SimpleDataSlots.java](dev/shadowsoffire/placebo/menu/SimpleDataSlots.java) |
| `FilteredOrderingFactory` | record | FieldOrderingFactory | — | 13 | 96 | [FilteredOrderingFactory.java](dev/shadowsoffire/placebo/datagen/FilteredOrderingFactory.java) |
| `SpecialTooltipItem` | interface | — | — | 0 | 5 | [SpecialTooltipItem.java](dev/shadowsoffire/placebo/util/SpecialTooltipItem.java) |
| `EnchantmentUtils` | class | — | — | 6 | 71 | [EnchantmentUtils.java](dev/shadowsoffire/placebo/util/EnchantmentUtils.java) |
| `DeferredSet` | record | Set<E> | — | 14 | 83 | [DeferredSet.java](dev/shadowsoffire/placebo/util/DeferredSet.java) |
| `PlaceboUtil` | class | — | — | 7 | 72 | [PlaceboUtil.java](dev/shadowsoffire/placebo/util/PlaceboUtil.java) |
| `StepFunction` | record | Float2FloatFunction | — | 12 | 121 | [StepFunction.java](dev/shadowsoffire/placebo/util/StepFunction.java) |
| `Offset` | record | StringRepresentable | — | 10 | 119 | [Offset.java](dev/shadowsoffire/placebo/util/Offset.java) |
| `GearSet` | record | CodecProvider<GearSet> | — | 7 | 109 | [GearSet.java](dev/shadowsoffire/placebo/systems/gear/GearSet.java) |
| `TickableText` | class | — | — | 9 | 70 | [TickableText.java](dev/shadowsoffire/placebo/screen/TickableText.java) |
| `RandomAttributeModifier` | record | — | — | 6 | 102 | [RandomAttributeModifier.java](dev/shadowsoffire/placebo/json/RandomAttributeModifier.java) |
| `JsonUtil` | class | — | — | 2 | 29 | [JsonUtil.java](dev/shadowsoffire/placebo/json/JsonUtil.java) |
| `PatreonUtils` | class | — | — | 1 | 82 | [PatreonUtils.java](dev/shadowsoffire/placebo/patreon/PatreonUtils.java) |
| `Wing` | class | EntityModel | — | 5 | 95 | [Wing.java](dev/shadowsoffire/placebo/patreon/wings/Wing.java) |
| `IDataUpdateListener` | interface | — | — | 0 | 6 | [IDataUpdateListener.java](dev/shadowsoffire/placebo/menu/IDataUpdateListener.java) |
| `PlaceboCodecs` | class | — | — | 6 | 41 | [PlaceboCodecs.java](dev/shadowsoffire/placebo/codec/PlaceboCodecs.java) |
| `CodecMap` | class | Codec<V> | — | 9 | 85 | [CodecMap.java](dev/shadowsoffire/placebo/codec/CodecMap.java) |
| `ModifiableEnergyStorage` | class | EnergyStorage | — | 5 | 42 | [ModifiableEnergyStorage.java](dev/shadowsoffire/placebo/cap/ModifiableEnergyStorage.java) |
| `PatreonDisablePayload` | record | CustomPacketPayload | — | 7 | 92 | [PatreonDisablePayload.java](dev/shadowsoffire/placebo/payloads/PatreonDisablePayload.java) |
| `ButtonClickPayload` | record | CustomPacketPayload | — | 7 | 64 | [ButtonClickPayload.java](dev/shadowsoffire/placebo/payloads/ButtonClickPayload.java) |
| `GradientColor` | class | TextColor | — | 1 | 284 | [GradientColor.java](dev/shadowsoffire/placebo/color/GradientColor.java) |
| `DynamicHolder` | class | Supplier<R> | — | 9 | 74 | [DynamicHolder.java](dev/shadowsoffire/placebo/reload/DynamicHolder.java) |
| `WandererTrade` | interface | — | — | 0 | 9 | [WandererTrade.java](dev/shadowsoffire/placebo/systems/wanderer/WandererTrade.java) |
| `JsonMix` | record | — | — | 3 | 51 | [JsonMix.java](dev/shadowsoffire/placebo/systems/mixes/JsonMix.java) |
| `PayloadProvider` | interface | — | — | 0 | 35 | [PayloadProvider.java](dev/shadowsoffire/placebo/network/PayloadProvider.java) |
| `OptionalStackCodec` | class | MapCodec | — | 3 | 58 | [OptionalStackCodec.java](dev/shadowsoffire/placebo/json/OptionalStackCodec.java) |
| `NBTAdapter` | class | JsonDeserializer<CompoundTag> | — | 2 | 38 | [NBTAdapter.java](dev/shadowsoffire/placebo/json/NBTAdapter.java) |
| `ChancedEffectInstance` | record | — | — | 2 | 45 | [ChancedEffectInstance.java](dev/shadowsoffire/placebo/json/ChancedEffectInstance.java) |
| `WingLayer` | class | RenderLayer | — | 1 | 44 | [WingLayer.java](dev/shadowsoffire/placebo/patreon/wings/WingLayer.java) |
| `IWingModel` | interface | — | — | 0 | 14 | [IWingModel.java](dev/shadowsoffire/placebo/patreon/wings/IWingModel.java) |
| `ITabFiller` | interface | — | — | 0 | 27 | [ITabFiller.java](dev/shadowsoffire/placebo/tabs/ITabFiller.java) |
| `SlotUpdateListener` | interface | — | — | 0 | 8 | [SlotUpdateListener.java](dev/shadowsoffire/placebo/menu/SlotUpdateListener.java) |
| `LockedSlot` | class | Slot | — | 2 | 21 | [LockedSlot.java](dev/shadowsoffire/placebo/menu/LockedSlot.java) |
| `FilteredSlot` | class | SlotItemHandler | — | 3 | 36 | [FilteredSlot.java](dev/shadowsoffire/placebo/menu/FilteredSlot.java) |
| `CodecProvider` | interface | — | — | 0 | 8 | [CodecProvider.java](dev/shadowsoffire/placebo/codec/CodecProvider.java) |
| `MapBackedCodec` | class | Codec<V> | — | 2 | 51 | [MapBackedCodec.java](dev/shadowsoffire/placebo/codec/MapBackedCodec.java) |
| `FieldOrderingFactory` | interface | — | — | 1 | 46 | [FieldOrderingFactory.java](dev/shadowsoffire/placebo/datagen/FieldOrderingFactory.java) |
| `RandomRange` | record | — | — | 3 | 19 | [RandomRange.java](dev/shadowsoffire/placebo/util/RandomRange.java) |
| `DrawsOnLeft` | interface | — | — | 0 | 52 | [DrawsOnLeft.java](dev/shadowsoffire/placebo/util/DrawsOnLeft.java) |
| `LinearEquation` | class | — | — | 3 | 28 | [LinearEquation.java](dev/shadowsoffire/placebo/util/LinearEquation.java) |
| `CachedObject` | class | — | — | 4 | 66 | [CachedObject.java](dev/shadowsoffire/placebo/util/CachedObject.java) |
| `RunnableReloader` | class | SimplePreparableReloadListener | — | 3 | 27 | [RunnableReloader.java](dev/shadowsoffire/placebo/util/RunnableReloader.java) |
| `DynRegBiMap` | class | — | — | 0 | 14 | [DynRegBiMap.java](dev/shadowsoffire/placebo/reload/DynRegBiMap.java) |
