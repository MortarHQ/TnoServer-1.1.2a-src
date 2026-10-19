# You're in Grave Danger（`yigd`）

> jar：`[墓碑]youre-in-grave-danger.jar` ｜ 版本：`2.0.13` ｜ 作者：b1n_ry
> 规模：**80 个类 / 17 个包 / 9674 行** ｜ 事件处理类 3 ｜ Mixin 7 ｜ 注册/管理类 10

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.b1n_ry.yigd.config` | 13 | `YigdConfig`、`CommandConfig`、`CompatConfig` |
| `com.b1n_ry.yigd.networking.packets` | 13 | `SyncConfigS2CPacket`、`UpdateConfigC2SPacket`、`PlayerSelectionS2CPacket` |
| `com.b1n_ry.yigd.data` | 8 | `DeathInfoManager`、`GraveItem`、`TimePoint` |
| `com.b1n_ry.yigd.mixin` | 7 | `RegistryDataLoaderMixin`、`AbstractConfigScreenMixin`、`CompassItemMixin` |
| `com.b1n_ry.yigd.util` | 7 | `YigdCommands`、`YigdResourceHandler`、`GraveCompassHelper` |
| `com.b1n_ry.yigd.compat` | 6 | `CuriosCompat`、`AccessoriesCompat`、`CompatComponent` |
| `com.b1n_ry.yigd.networking` | 5 | `ServerPacketHandler`、`ClientPacketHandler`、`PacketInitializer` |
| `com.b1n_ry.yigd.components` | 5 | `GraveComponent`、`InventoryComponent`、`ExpComponent` |
| `com.b1n_ry.yigd.events` | 4 | `YigdEvents`、`YigdClientEventHandler`、`YigdServerEventHandler` |
| `com.b1n_ry.yigd.client.gui` | 3 | `GraveOverviewScreen`、`PlayerSelectionScreen`、`GraveSelectionScreen` |
| `com.b1n_ry.yigd` | 2 | `Yigd`、`DeathHandler` |
| `com.b1n_ry.yigd.item` | 2 | `DeathScrollItem`、`GraveKeyItem` |
| `com.b1n_ry.yigd.client` | 1 | `YigdClient` |
| `com.b1n_ry.yigd.block.entity` | 1 | `GraveBlockEntity` |
| `com.b1n_ry.yigd.client.render` | 1 | `GraveBlockEntityRenderer` |
| `com.b1n_ry.yigd.block` | 1 | `GraveBlock` |
| `com.b1n_ry.yigd.compat.misc_compat_mods` | 1 | `TwilightCompat` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `YigdEvents` | class | Event | — | 49 | 362 | [YigdEvents.java](com/b1n_ry/yigd/events/YigdEvents.java) |
| `YigdClientEventHandler` | class | — | — | 1 | 40 | [YigdClientEventHandler.java](com/b1n_ry/yigd/events/YigdClientEventHandler.java) |
| `YigdServerEventHandler` | class | — | — | 6 | 321 | [YigdServerEventHandler.java](com/b1n_ry/yigd/events/YigdServerEventHandler.java) |
| `RegistryDataLoaderMixin` | class | — | Mixin | 1 | 44 | [RegistryDataLoaderMixin.java](com/b1n_ry/yigd/mixin/RegistryDataLoaderMixin.java) |
| `ServerEventHandler` | class | — | — | 8 | 195 | [ServerEventHandler.java](com/b1n_ry/yigd/events/ServerEventHandler.java) |
| `AbstractConfigScreenMixin` | class | — | Mixin | 1 | 29 | [AbstractConfigScreenMixin.java](com/b1n_ry/yigd/mixin/AbstractConfigScreenMixin.java) |
| `YigdClient` | class | — | Mod | 1 | 44 | [YigdClient.java](com/b1n_ry/yigd/client/YigdClient.java) |
| `Yigd` | class | — | Mod | 2 | 122 | [Yigd.java](com/b1n_ry/yigd/Yigd.java) |
| `ServerPacketHandler` | class | — | — | 9 | 279 | [ServerPacketHandler.java](com/b1n_ry/yigd/networking/ServerPacketHandler.java) |
| `ClientPacketHandler` | class | — | — | 4 | 33 | [ClientPacketHandler.java](com/b1n_ry/yigd/networking/ClientPacketHandler.java) |
| `GraveComponent` | class | — | — | 42 | 845 | [GraveComponent.java](com/b1n_ry/yigd/components/GraveComponent.java) |
| `YigdConfig` | class | ConfigData | Config | 2 | 39 | [YigdConfig.java](com/b1n_ry/yigd/config/YigdConfig.java) |
| `GraveBlockEntity` | class | BlockEntity | — | 21 | 298 | [GraveBlockEntity.java](com/b1n_ry/yigd/block/entity/GraveBlockEntity.java) |
| `InventoryComponent` | class | — | — | 37 | 783 | [InventoryComponent.java](com/b1n_ry/yigd/components/InventoryComponent.java) |
| `DeathInfoManager` | class | SavedData | — | 18 | 237 | [DeathInfoManager.java](com/b1n_ry/yigd/data/DeathInfoManager.java) |
| `GraveBlockEntityRenderer` | class | BlockEntityRenderer<GraveBlockEntity | — | 11 | 309 | [GraveBlockEntityRenderer.java](com/b1n_ry/yigd/client/render/GraveBlockEntityRenderer.java) |
| `CompassItemMixin` | class | — | Mixin | 1 | 43 | [CompassItemMixin.java](com/b1n_ry/yigd/mixin/CompassItemMixin.java) |
| `CuriosCompat` | class | CompatComponent | — | 20 | 461 | [CuriosCompat.java](com/b1n_ry/yigd/compat/CuriosCompat.java) |
| `SyncConfigS2CPacket` | record | CustomPacketPayload | — | 1 | 31 | [SyncConfigS2CPacket.java](com/b1n_ry/yigd/networking/packets/SyncConfigS2CPacket.java) |
| `UpdateConfigC2SPacket` | record | CustomPacketPayload | — | 1 | 27 | [UpdateConfigC2SPacket.java](com/b1n_ry/yigd/networking/packets/UpdateConfigC2SPacket.java) |
| `CommandConfig` | class | — | — | 0 | 16 | [CommandConfig.java](com/b1n_ry/yigd/config/CommandConfig.java) |
| `AccessoriesCompat` | class | CompatComponent | — | 19 | 459 | [AccessoriesCompat.java](com/b1n_ry/yigd/compat/AccessoriesCompat.java) |
| `YigdCommands` | class | — | — | 18 | 336 | [YigdCommands.java](com/b1n_ry/yigd/util/YigdCommands.java) |
| `GraveBlock` | class | BaseEntityBlock | — | 16 | 359 | [GraveBlock.java](com/b1n_ry/yigd/block/GraveBlock.java) |
| `CompassModelMixin` | class | — | Mixin | 1 | 24 | [CompassModelMixin.java](com/b1n_ry/yigd/mixin/CompassModelMixin.java) |
| `DedicatedServerMixin` | class | — | Mixin | 1 | 27 | [DedicatedServerMixin.java](com/b1n_ry/yigd/mixin/DedicatedServerMixin.java) |
| `LevelChunkMixin` | class | — | Mixin | 1 | 23 | [LevelChunkMixin.java](com/b1n_ry/yigd/mixin/LevelChunkMixin.java) |
| `EndPlatformFeatureMixin` | class | — | Mixin | 1 | 21 | [EndPlatformFeatureMixin.java](com/b1n_ry/yigd/mixin/EndPlatformFeatureMixin.java) |
| `CompatConfig` | class | — | — | 0 | 26 | [CompatConfig.java](com/b1n_ry/yigd/config/CompatConfig.java) |
| `YigdResourceHandler` | class | ResourceManagerReloadListener | — | 6 | 113 | [YigdResourceHandler.java](com/b1n_ry/yigd/util/YigdResourceHandler.java) |
| `GraveOverviewScreen` | class | Screen | — | 14 | 502 | [GraveOverviewScreen.java](com/b1n_ry/yigd/client/gui/GraveOverviewScreen.java) |
| `DeathHandler` | class | — | — | 2 | 81 | [DeathHandler.java](com/b1n_ry/yigd/DeathHandler.java) |
| `CompatComponent` | class | — | — | 16 | 99 | [CompatComponent.java](com/b1n_ry/yigd/compat/CompatComponent.java) |
| `DeathScrollItem` | class | Item | — | 12 | 225 | [DeathScrollItem.java](com/b1n_ry/yigd/item/DeathScrollItem.java) |
| `PlayerSelectionScreen` | class | Screen | — | 14 | 273 | [PlayerSelectionScreen.java](com/b1n_ry/yigd/client/gui/PlayerSelectionScreen.java) |
| `GraveSelectionScreen` | class | Screen | — | 14 | 268 | [GraveSelectionScreen.java](com/b1n_ry/yigd/client/gui/GraveSelectionScreen.java) |
| `MapEntryConfig` | class | MapEntryConfig | — | 0 | 37 | [MapEntryConfig.java](com/b1n_ry/yigd/config/MapEntryConfig.java) |
| `RespawnConfig` | class | — | — | 0 | 38 | [RespawnConfig.java](com/b1n_ry/yigd/config/RespawnConfig.java) |
| `GraveRenderingConfig` | class | — | — | 0 | 14 | [GraveRenderingConfig.java](com/b1n_ry/yigd/config/GraveRenderingConfig.java) |
| `ExpConfig` | class | — | — | 0 | 17 | [ExpConfig.java](com/b1n_ry/yigd/config/ExpConfig.java) |
| `InventoryConfig` | class | — | — | 0 | 36 | [InventoryConfig.java](com/b1n_ry/yigd/config/InventoryConfig.java) |
| `ExtraFeaturesConfig` | class | — | — | 0 | 82 | [ExtraFeaturesConfig.java](com/b1n_ry/yigd/config/ExtraFeaturesConfig.java) |
| `GraveConfig` | class | — | — | 0 | 137 | [GraveConfig.java](com/b1n_ry/yigd/config/GraveConfig.java) |
| `CosmeticArmorCompat` | class | CompatComponent | — | 13 | 172 | [CosmeticArmorCompat.java](com/b1n_ry/yigd/compat/CosmeticArmorCompat.java) |
| `TravelersBackpackCompat` | class | CompatComponent | — | 14 | 163 | [TravelersBackpackCompat.java](com/b1n_ry/yigd/compat/TravelersBackpackCompat.java) |
| `PacketInitializer` | class | — | — | 1 | 37 | [PacketInitializer.java](com/b1n_ry/yigd/networking/PacketInitializer.java) |
| `PlayerSelectionS2CPacket` | record | CustomPacketPayload | — | 2 | 31 | [PlayerSelectionS2CPacket.java](com/b1n_ry/yigd/networking/packets/PlayerSelectionS2CPacket.java) |
| `LockGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 24 | [LockGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/LockGraveC2SPacket.java) |
| `GraveSelectionRequestC2SPacket` | record | CustomPacketPayload | — | 1 | 22 | [GraveSelectionRequestC2SPacket.java](com/b1n_ry/yigd/networking/packets/GraveSelectionRequestC2SPacket.java) |
| `RequestKeyC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [RequestKeyC2SPacket.java](com/b1n_ry/yigd/networking/packets/RequestKeyC2SPacket.java) |
| `DeleteGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [DeleteGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/DeleteGraveC2SPacket.java) |
| `RobGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 35 | [RobGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/RobGraveC2SPacket.java) |
| `RestoreGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 35 | [RestoreGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/RestoreGraveC2SPacket.java) |
| `GraveOverviewS2CPacket` | record | CustomPacketPayload | — | 2 | 46 | [GraveOverviewS2CPacket.java](com/b1n_ry/yigd/networking/packets/GraveOverviewS2CPacket.java) |
| `GraveSelectionS2CPacket` | record | CustomPacketPayload | — | 2 | 36 | [GraveSelectionS2CPacket.java](com/b1n_ry/yigd/networking/packets/GraveSelectionS2CPacket.java) |
| `RequestCompassC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [RequestCompassC2SPacket.java](com/b1n_ry/yigd/networking/packets/RequestCompassC2SPacket.java) |
| `GraveOverviewRequestC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [GraveOverviewRequestC2SPacket.java](com/b1n_ry/yigd/networking/packets/GraveOverviewRequestC2SPacket.java) |
| `ExpComponent` | class | — | — | 16 | 129 | [ExpComponent.java](com/b1n_ry/yigd/components/ExpComponent.java) |
| `GraveKeyItem` | class | Item | — | 5 | 81 | [GraveKeyItem.java](com/b1n_ry/yigd/item/GraveKeyItem.java) |
| `RespawnComponent` | class | — | — | 10 | 177 | [RespawnComponent.java](com/b1n_ry/yigd/components/RespawnComponent.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ServerEventHandler` | 8 | `com.b1n_ry.yigd.events` | [ServerEventHandler.java](com/b1n_ry/yigd/events/ServerEventHandler.java) |
| `YigdServerEventHandler` | 6 | `com.b1n_ry.yigd.events` | [YigdServerEventHandler.java](com/b1n_ry/yigd/events/YigdServerEventHandler.java) |
| `YigdClientEventHandler` | 1 | `com.b1n_ry.yigd.events` | [YigdClientEventHandler.java](com/b1n_ry/yigd/events/YigdClientEventHandler.java) |

## Mixin（7 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `RegistryDataLoaderMixin` | `RegistryDataLoader` | [RegistryDataLoaderMixin.java](com/b1n_ry/yigd/mixin/RegistryDataLoaderMixin.java) |
| `AbstractConfigScreenMixin` | `AbstractConfigScreen` | [AbstractConfigScreenMixin.java](com/b1n_ry/yigd/mixin/AbstractConfigScreenMixin.java) |
| `CompassItemMixin` | `CompassItem` | [CompassItemMixin.java](com/b1n_ry/yigd/mixin/CompassItemMixin.java) |
| `CompassModelMixin` | `CompassModel` | [CompassModelMixin.java](com/b1n_ry/yigd/mixin/CompassModelMixin.java) |
| `DedicatedServerMixin` | `DedicatedServer` | [DedicatedServerMixin.java](com/b1n_ry/yigd/mixin/DedicatedServerMixin.java) |
| `LevelChunkMixin` | `LevelChunk` | [LevelChunkMixin.java](com/b1n_ry/yigd/mixin/LevelChunkMixin.java) |
| `EndPlatformFeatureMixin` | `EndPlatformFeature` | [EndPlatformFeatureMixin.java](com/b1n_ry/yigd/mixin/EndPlatformFeatureMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `AbstractConfigScreenMixin` | Mixin | [AbstractConfigScreenMixin.java](com/b1n_ry/yigd/mixin/AbstractConfigScreenMixin.java) |
| `YigdConfig` | Config | [YigdConfig.java](com/b1n_ry/yigd/config/YigdConfig.java) |
| `SyncConfigS2CPacket` | — | [SyncConfigS2CPacket.java](com/b1n_ry/yigd/networking/packets/SyncConfigS2CPacket.java) |
| `UpdateConfigC2SPacket` | — | [UpdateConfigC2SPacket.java](com/b1n_ry/yigd/networking/packets/UpdateConfigC2SPacket.java) |
| `CommandConfig` | — | [CommandConfig.java](com/b1n_ry/yigd/config/CommandConfig.java) |
| `CompatConfig` | — | [CompatConfig.java](com/b1n_ry/yigd/config/CompatConfig.java) |
| `MapEntryConfig` | — | [MapEntryConfig.java](com/b1n_ry/yigd/config/MapEntryConfig.java) |
| `RespawnConfig` | — | [RespawnConfig.java](com/b1n_ry/yigd/config/RespawnConfig.java) |
| `GraveRenderingConfig` | — | [GraveRenderingConfig.java](com/b1n_ry/yigd/config/GraveRenderingConfig.java) |
| `ExpConfig` | — | [ExpConfig.java](com/b1n_ry/yigd/config/ExpConfig.java) |
| `InventoryConfig` | — | [InventoryConfig.java](com/b1n_ry/yigd/config/InventoryConfig.java) |
| `ExtraFeaturesConfig` | — | [ExtraFeaturesConfig.java](com/b1n_ry/yigd/config/ExtraFeaturesConfig.java) |
| `GraveConfig` | — | [GraveConfig.java](com/b1n_ry/yigd/config/GraveConfig.java) |

## 全部类（80）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `YigdEvents` | class | Event | — | 49 | 362 | [YigdEvents.java](com/b1n_ry/yigd/events/YigdEvents.java) |
| `YigdClientEventHandler` | class | — | — | 1 | 40 | [YigdClientEventHandler.java](com/b1n_ry/yigd/events/YigdClientEventHandler.java) |
| `YigdServerEventHandler` | class | — | — | 6 | 321 | [YigdServerEventHandler.java](com/b1n_ry/yigd/events/YigdServerEventHandler.java) |
| `RegistryDataLoaderMixin` | class | — | Mixin | 1 | 44 | [RegistryDataLoaderMixin.java](com/b1n_ry/yigd/mixin/RegistryDataLoaderMixin.java) |
| `ServerEventHandler` | class | — | — | 8 | 195 | [ServerEventHandler.java](com/b1n_ry/yigd/events/ServerEventHandler.java) |
| `AbstractConfigScreenMixin` | class | — | Mixin | 1 | 29 | [AbstractConfigScreenMixin.java](com/b1n_ry/yigd/mixin/AbstractConfigScreenMixin.java) |
| `YigdClient` | class | — | Mod | 1 | 44 | [YigdClient.java](com/b1n_ry/yigd/client/YigdClient.java) |
| `Yigd` | class | — | Mod | 2 | 122 | [Yigd.java](com/b1n_ry/yigd/Yigd.java) |
| `ServerPacketHandler` | class | — | — | 9 | 279 | [ServerPacketHandler.java](com/b1n_ry/yigd/networking/ServerPacketHandler.java) |
| `ClientPacketHandler` | class | — | — | 4 | 33 | [ClientPacketHandler.java](com/b1n_ry/yigd/networking/ClientPacketHandler.java) |
| `GraveComponent` | class | — | — | 42 | 845 | [GraveComponent.java](com/b1n_ry/yigd/components/GraveComponent.java) |
| `YigdConfig` | class | ConfigData | Config | 2 | 39 | [YigdConfig.java](com/b1n_ry/yigd/config/YigdConfig.java) |
| `GraveBlockEntity` | class | BlockEntity | — | 21 | 298 | [GraveBlockEntity.java](com/b1n_ry/yigd/block/entity/GraveBlockEntity.java) |
| `InventoryComponent` | class | — | — | 37 | 783 | [InventoryComponent.java](com/b1n_ry/yigd/components/InventoryComponent.java) |
| `DeathInfoManager` | class | SavedData | — | 18 | 237 | [DeathInfoManager.java](com/b1n_ry/yigd/data/DeathInfoManager.java) |
| `GraveBlockEntityRenderer` | class | BlockEntityRenderer<GraveBlockEntity | — | 11 | 309 | [GraveBlockEntityRenderer.java](com/b1n_ry/yigd/client/render/GraveBlockEntityRenderer.java) |
| `CompassItemMixin` | class | — | Mixin | 1 | 43 | [CompassItemMixin.java](com/b1n_ry/yigd/mixin/CompassItemMixin.java) |
| `CuriosCompat` | class | CompatComponent | — | 20 | 461 | [CuriosCompat.java](com/b1n_ry/yigd/compat/CuriosCompat.java) |
| `SyncConfigS2CPacket` | record | CustomPacketPayload | — | 1 | 31 | [SyncConfigS2CPacket.java](com/b1n_ry/yigd/networking/packets/SyncConfigS2CPacket.java) |
| `UpdateConfigC2SPacket` | record | CustomPacketPayload | — | 1 | 27 | [UpdateConfigC2SPacket.java](com/b1n_ry/yigd/networking/packets/UpdateConfigC2SPacket.java) |
| `CommandConfig` | class | — | — | 0 | 16 | [CommandConfig.java](com/b1n_ry/yigd/config/CommandConfig.java) |
| `AccessoriesCompat` | class | CompatComponent | — | 19 | 459 | [AccessoriesCompat.java](com/b1n_ry/yigd/compat/AccessoriesCompat.java) |
| `YigdCommands` | class | — | — | 18 | 336 | [YigdCommands.java](com/b1n_ry/yigd/util/YigdCommands.java) |
| `GraveBlock` | class | BaseEntityBlock | — | 16 | 359 | [GraveBlock.java](com/b1n_ry/yigd/block/GraveBlock.java) |
| `CompassModelMixin` | class | — | Mixin | 1 | 24 | [CompassModelMixin.java](com/b1n_ry/yigd/mixin/CompassModelMixin.java) |
| `DedicatedServerMixin` | class | — | Mixin | 1 | 27 | [DedicatedServerMixin.java](com/b1n_ry/yigd/mixin/DedicatedServerMixin.java) |
| `LevelChunkMixin` | class | — | Mixin | 1 | 23 | [LevelChunkMixin.java](com/b1n_ry/yigd/mixin/LevelChunkMixin.java) |
| `EndPlatformFeatureMixin` | class | — | Mixin | 1 | 21 | [EndPlatformFeatureMixin.java](com/b1n_ry/yigd/mixin/EndPlatformFeatureMixin.java) |
| `CompatConfig` | class | — | — | 0 | 26 | [CompatConfig.java](com/b1n_ry/yigd/config/CompatConfig.java) |
| `YigdResourceHandler` | class | ResourceManagerReloadListener | — | 6 | 113 | [YigdResourceHandler.java](com/b1n_ry/yigd/util/YigdResourceHandler.java) |
| `GraveOverviewScreen` | class | Screen | — | 14 | 502 | [GraveOverviewScreen.java](com/b1n_ry/yigd/client/gui/GraveOverviewScreen.java) |
| `DeathHandler` | class | — | — | 2 | 81 | [DeathHandler.java](com/b1n_ry/yigd/DeathHandler.java) |
| `CompatComponent` | class | — | — | 16 | 99 | [CompatComponent.java](com/b1n_ry/yigd/compat/CompatComponent.java) |
| `DeathScrollItem` | class | Item | — | 12 | 225 | [DeathScrollItem.java](com/b1n_ry/yigd/item/DeathScrollItem.java) |
| `PlayerSelectionScreen` | class | Screen | — | 14 | 273 | [PlayerSelectionScreen.java](com/b1n_ry/yigd/client/gui/PlayerSelectionScreen.java) |
| `GraveSelectionScreen` | class | Screen | — | 14 | 268 | [GraveSelectionScreen.java](com/b1n_ry/yigd/client/gui/GraveSelectionScreen.java) |
| `MapEntryConfig` | class | MapEntryConfig | — | 0 | 37 | [MapEntryConfig.java](com/b1n_ry/yigd/config/MapEntryConfig.java) |
| `RespawnConfig` | class | — | — | 0 | 38 | [RespawnConfig.java](com/b1n_ry/yigd/config/RespawnConfig.java) |
| `GraveRenderingConfig` | class | — | — | 0 | 14 | [GraveRenderingConfig.java](com/b1n_ry/yigd/config/GraveRenderingConfig.java) |
| `ExpConfig` | class | — | — | 0 | 17 | [ExpConfig.java](com/b1n_ry/yigd/config/ExpConfig.java) |
| `InventoryConfig` | class | — | — | 0 | 36 | [InventoryConfig.java](com/b1n_ry/yigd/config/InventoryConfig.java) |
| `ExtraFeaturesConfig` | class | — | — | 0 | 82 | [ExtraFeaturesConfig.java](com/b1n_ry/yigd/config/ExtraFeaturesConfig.java) |
| `GraveConfig` | class | — | — | 0 | 137 | [GraveConfig.java](com/b1n_ry/yigd/config/GraveConfig.java) |
| `CosmeticArmorCompat` | class | CompatComponent | — | 13 | 172 | [CosmeticArmorCompat.java](com/b1n_ry/yigd/compat/CosmeticArmorCompat.java) |
| `TravelersBackpackCompat` | class | CompatComponent | — | 14 | 163 | [TravelersBackpackCompat.java](com/b1n_ry/yigd/compat/TravelersBackpackCompat.java) |
| `PacketInitializer` | class | — | — | 1 | 37 | [PacketInitializer.java](com/b1n_ry/yigd/networking/PacketInitializer.java) |
| `PlayerSelectionS2CPacket` | record | CustomPacketPayload | — | 2 | 31 | [PlayerSelectionS2CPacket.java](com/b1n_ry/yigd/networking/packets/PlayerSelectionS2CPacket.java) |
| `LockGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 24 | [LockGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/LockGraveC2SPacket.java) |
| `GraveSelectionRequestC2SPacket` | record | CustomPacketPayload | — | 1 | 22 | [GraveSelectionRequestC2SPacket.java](com/b1n_ry/yigd/networking/packets/GraveSelectionRequestC2SPacket.java) |
| `RequestKeyC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [RequestKeyC2SPacket.java](com/b1n_ry/yigd/networking/packets/RequestKeyC2SPacket.java) |
| `DeleteGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [DeleteGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/DeleteGraveC2SPacket.java) |
| `RobGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 35 | [RobGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/RobGraveC2SPacket.java) |
| `RestoreGraveC2SPacket` | record | CustomPacketPayload | — | 1 | 35 | [RestoreGraveC2SPacket.java](com/b1n_ry/yigd/networking/packets/RestoreGraveC2SPacket.java) |
| `GraveOverviewS2CPacket` | record | CustomPacketPayload | — | 2 | 46 | [GraveOverviewS2CPacket.java](com/b1n_ry/yigd/networking/packets/GraveOverviewS2CPacket.java) |
| `GraveSelectionS2CPacket` | record | CustomPacketPayload | — | 2 | 36 | [GraveSelectionS2CPacket.java](com/b1n_ry/yigd/networking/packets/GraveSelectionS2CPacket.java) |
| `RequestCompassC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [RequestCompassC2SPacket.java](com/b1n_ry/yigd/networking/packets/RequestCompassC2SPacket.java) |
| `GraveOverviewRequestC2SPacket` | record | CustomPacketPayload | — | 1 | 23 | [GraveOverviewRequestC2SPacket.java](com/b1n_ry/yigd/networking/packets/GraveOverviewRequestC2SPacket.java) |
| `ExpComponent` | class | — | — | 16 | 129 | [ExpComponent.java](com/b1n_ry/yigd/components/ExpComponent.java) |
| `GraveKeyItem` | class | Item | — | 5 | 81 | [GraveKeyItem.java](com/b1n_ry/yigd/item/GraveKeyItem.java) |
| `RespawnComponent` | class | — | — | 10 | 177 | [RespawnComponent.java](com/b1n_ry/yigd/components/RespawnComponent.java) |
| `InvModCompat` | interface | — | — | 0 | 58 | [InvModCompat.java](com/b1n_ry/yigd/compat/InvModCompat.java) |
| `TwilightCompat` | class | — | — | 2 | 49 | [TwilightCompat.java](com/b1n_ry/yigd/compat/misc_compat_mods/TwilightCompat.java) |
| `GraveItem` | class | — | — | 1 | 19 | [GraveItem.java](com/b1n_ry/yigd/data/GraveItem.java) |
| `TimePoint` | class | — | — | 10 | 104 | [TimePoint.java](com/b1n_ry/yigd/data/TimePoint.java) |
| `GraveCompassHelper` | class | — | — | 9 | 179 | [GraveCompassHelper.java](com/b1n_ry/yigd/util/GraveCompassHelper.java) |
| `GraveItemModificationConsumer` | interface | — | — | 0 | 10 | [GraveItemModificationConsumer.java](com/b1n_ry/yigd/util/GraveItemModificationConsumer.java) |
| `LightGraveData` | record | — | — | 4 | 58 | [LightGraveData.java](com/b1n_ry/yigd/networking/LightGraveData.java) |
| `LightPlayerData` | record | — | — | 2 | 30 | [LightPlayerData.java](com/b1n_ry/yigd/networking/LightPlayerData.java) |
| `GraveyardData` | class | — | — | 1 | 63 | [GraveyardData.java](com/b1n_ry/yigd/data/GraveyardData.java) |
| `EffectComponent` | class | — | — | 4 | 111 | [EffectComponent.java](com/b1n_ry/yigd/components/EffectComponent.java) |
| `ExpDropBehaviour` | enum | — | — | 0 | 9 | [ExpDropBehaviour.java](com/b1n_ry/yigd/config/ExpDropBehaviour.java) |
| `ClaimPriority` | enum | — | — | 0 | 7 | [ClaimPriority.java](com/b1n_ry/yigd/config/ClaimPriority.java) |
| `DropType` | enum | — | — | 0 | 7 | [DropType.java](com/b1n_ry/yigd/config/DropType.java) |
| `GraveStatus` | enum | — | — | 1 | 16 | [GraveStatus.java](com/b1n_ry/yigd/data/GraveStatus.java) |
| `DirectionalPos` | record | — | — | 1 | 16 | [DirectionalPos.java](com/b1n_ry/yigd/data/DirectionalPos.java) |
| `ListMode` | enum | — | — | 0 | 7 | [ListMode.java](com/b1n_ry/yigd/data/ListMode.java) |
| `DeathContext` | record | — | — | 0 | 11 | [DeathContext.java](com/b1n_ry/yigd/data/DeathContext.java) |
| `YigdTags` | interface | — | — | 0 | 22 | [YigdTags.java](com/b1n_ry/yigd/util/YigdTags.java) |
| `DropRule` | enum | — | — | 0 | 9 | [DropRule.java](com/b1n_ry/yigd/util/DropRule.java) |
| `GraveOverrideAreas` | class | — | — | 1 | 54 | [GraveOverrideAreas.java](com/b1n_ry/yigd/util/GraveOverrideAreas.java) |
