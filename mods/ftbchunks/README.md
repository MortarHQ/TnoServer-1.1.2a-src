# FTB Chunks（`ftbchunks`）

> jar：`[FTB 区块]ftbchunks.jar` ｜ 版本：`2101.1.20` ｜ 作者：FTB Team
> 规模：**161 个类 / 25 个包 / 15755 行** ｜ 事件处理类 1 ｜ Mixin 12 ｜ 注册/管理类 9

**依赖**：`neoforge`、`minecraft`、`architectury`、`ftblibrary`、`ftbteams`、`ftbbackups3`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.ftb.mods.ftbchunks.client.map` | 21 | `MapManager`、`PendingUpdateEvents`、`WaypointManagerImpl` |
| `dev.ftb.mods.ftbchunks.net` | 21 | `SendGeneralDataPacket`、`RequestBlockColorPacket`、`RequestMapDataPacket` |
| `dev.ftb.mods.ftbchunks.client.gui` | 16 | `ChunkScreen`、`LargeMapScreen`、`MinimapInfoSortScreen` |
| `dev.ftb.mods.ftbchunks.core.mixin` | 12 | `ClientPacketListenerMixin`、`PistonBaseBlockMixin`、`ItemFrameMixin` |
| `dev.ftb.mods.ftbchunks` | 11 | `FTBChunksCommands`、`FTBChunks`、`CustomMinYRegistryImpl` |
| `dev.ftb.mods.ftbchunks.data` | 10 | `ClaimedChunkManagerImpl`、`ChunkTeamDataImpl`、`ClaimExpirationManager` |
| `dev.ftb.mods.ftbchunks.api` | 10 | `ClaimedChunkManager`、`ChunkTeamData`、`FTBChunksAPI` |
| `dev.ftb.mods.ftbchunks.client` | 9 | `FTBChunksClient`、`FTBChunksClientConfig`、`FTBChunksClientAPIImpl` |
| `dev.ftb.mods.ftbchunks.client.mapicon` | 7 | `EntityIconUtils`、`EntityMapIcon`、`WaypointMapIcon` |
| `dev.ftb.mods.ftbchunks.util` | 7 | `FireSpreadHelper`、`PistonHelper`、`DimensionFilter` |
| `dev.ftb.mods.ftbchunks.client.minimap.components` | 7 | `BiomeComponent`、`FPSComponent`、`ZoneInfoComponent` |
| `dev.ftb.mods.ftbchunks.client.map.color` | 5 | `IgnoredBlockColor`、`ColorUtils`、`BlockColors` |
| `dev.ftb.mods.ftbchunks.neoforge` | 4 | `FTBChunksForge`、`FTBChunksExpectedImpl`、`FTBCUtilsImpl` |
| `dev.ftb.mods.ftbchunks.api.client.event` | 3 | `WaypointManagerEvent`、`ChunksUpdatedFromServerEvent`、`MapIconEvent` |
| `dev.ftb.mods.ftbchunks.api.client.icon` | 3 | `MapIcon`、`MapType`、`WaypointIcon` |
| `dev.ftb.mods.ftbchunks.api.client.minimap` | 3 | `MinimapContext`、`MinimapInfoComponent`、`TranslatedOption` |
| `dev.ftb.mods.ftbchunks.api.client.waypoint` | 2 | `WaypointManager`、`Waypoint` |
| `dev.ftb.mods.ftbchunks.api.event` | 2 | `ClaimedChunkEvent`、`CustomMinYEvent` |
| `dev.ftb.mods.ftbchunks.core` | 2 | `BlockStateFTBC`、`BiomeFTBC` |
| `dev.ftb.mods.ftbchunks.client.neoforge` | 1 | `FTBChunksClientImpl` |
| `dev.ftb.mods.ftbchunks.client.minimap` | 1 | `MinimapComponentConfig` |
| `dev.ftb.mods.ftbchunks.neoforge.integration` | 1 | `FTBBackups3Integration` |
| `dev.ftb.mods.ftbchunks.api.client` | 1 | `FTBChunksClientAPI` |
| `dev.ftb.mods.ftbchunks.integration` | 1 | `PermissionsHelper` |
| `architectury_inject_FTBChunks121_common_31935692caf3484da5f048f8d908811d_2bc1009072b093f1411761284e516c92fb7252980e0b90cba697d97c751d40e1ftbchunks2101120devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `FTBChunksCommands` | class | — | — | 38 | 883 | [FTBChunksCommands.java](dev/ftb/mods/ftbchunks/FTBChunksCommands.java) |
| `FTBChunksClient` | enum | — | — | 57 | 1371 | [FTBChunksClient.java](dev/ftb/mods/ftbchunks/client/FTBChunksClient.java) |
| `ClientPacketListenerMixin` | class | — | Mixin | 3 | 30 | [ClientPacketListenerMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ClientPacketListenerMixin.java) |
| `FTBChunksForge` | class | — | Mod | 3 | 60 | [FTBChunksForge.java](dev/ftb/mods/ftbchunks/neoforge/FTBChunksForge.java) |
| `ClaimedChunkManagerImpl` | class | ClaimedChunkManager | — | 23 | 256 | [ClaimedChunkManagerImpl.java](dev/ftb/mods/ftbchunks/data/ClaimedChunkManagerImpl.java) |
| `ChunkTeamDataImpl` | class | ChunkTeamData | — | 55 | 794 | [ChunkTeamDataImpl.java](dev/ftb/mods/ftbchunks/data/ChunkTeamDataImpl.java) |
| `MapManager` | class | MapTask | — | 22 | 362 | [MapManager.java](dev/ftb/mods/ftbchunks/client/map/MapManager.java) |
| `FTBChunks` | class | — | — | 36 | 593 | [FTBChunks.java](dev/ftb/mods/ftbchunks/FTBChunks.java) |
| `PistonBaseBlockMixin` | class | — | Mixin | 1 | 35 | [PistonBaseBlockMixin.java](dev/ftb/mods/ftbchunks/core/mixin/PistonBaseBlockMixin.java) |
| `ItemFrameMixin` | class | — | Mixin | 1 | 21 | [ItemFrameMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ItemFrameMixin.java) |
| `BlockStateMixin` | class | BlockStateFTBC | Mixin | 1 | 21 | [BlockStateMixin.java](dev/ftb/mods/ftbchunks/core/mixin/BlockStateMixin.java) |
| `VehicleEntityMixin` | class | — | Mixin | 1 | 21 | [VehicleEntityMixin.java](dev/ftb/mods/ftbchunks/core/mixin/VehicleEntityMixin.java) |
| `FireBlockMixin` | class | — | Mixin | 1 | 29 | [FireBlockMixin.java](dev/ftb/mods/ftbchunks/core/mixin/FireBlockMixin.java) |
| `WaypointManagerEvent` | interface | — | — | 0 | 12 | [WaypointManagerEvent.java](dev/ftb/mods/ftbchunks/api/client/event/WaypointManagerEvent.java) |
| `ChunkScreen` | class | AbstractThreePanelScreen | — | 25 | 314 | [ChunkScreen.java](dev/ftb/mods/ftbchunks/client/gui/ChunkScreen.java) |
| `LargeMapScreen` | class | BaseScreen | — | 23 | 439 | [LargeMapScreen.java](dev/ftb/mods/ftbchunks/client/gui/LargeMapScreen.java) |
| `PendingUpdateEvents` | class | — | — | 1 | 36 | [PendingUpdateEvents.java](dev/ftb/mods/ftbchunks/client/map/PendingUpdateEvents.java) |
| `WaypointManagerImpl` | class | Iterable<WaypointImpl> | — | 15 | 194 | [WaypointManagerImpl.java](dev/ftb/mods/ftbchunks/client/map/WaypointManagerImpl.java) |
| `UtilMixin` | class | — | Mixin | 1 | 31 | [UtilMixin.java](dev/ftb/mods/ftbchunks/core/mixin/UtilMixin.java) |
| `MinimapInfoSortScreen` | class | AbstractThreePanelScreen | — | 21 | 291 | [MinimapInfoSortScreen.java](dev/ftb/mods/ftbchunks/client/gui/MinimapInfoSortScreen.java) |
| `WaypointEditorScreen` | class | AbstractGroupedButtonListScreen | — | 21 | 321 | [WaypointEditorScreen.java](dev/ftb/mods/ftbchunks/client/gui/WaypointEditorScreen.java) |
| `ChunkScreenPanel` | class | Panel | — | 22 | 399 | [ChunkScreenPanel.java](dev/ftb/mods/ftbchunks/client/gui/ChunkScreenPanel.java) |
| `CustomMinYRegistryImpl` | class | CustomMinYEvent.CustomMinYRegistry | — | 3 | 36 | [CustomMinYRegistryImpl.java](dev/ftb/mods/ftbchunks/CustomMinYRegistryImpl.java) |
| `FlowingFluidMixin` | class | — | Mixin | 1 | 29 | [FlowingFluidMixin.java](dev/ftb/mods/ftbchunks/core/mixin/FlowingFluidMixin.java) |
| `GuiMixin` | class | — | Mixin | 2 | 25 | [GuiMixin.java](dev/ftb/mods/ftbchunks/core/mixin/GuiMixin.java) |
| `ArmorStandMixin` | class | — | Mixin | 1 | 31 | [ArmorStandMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ArmorStandMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 1 | 30 | [ChunkMapMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ChunkMapMixin.java) |
| `BiomeMixin` | class | BiomeFTBC | Mixin | 2 | 21 | [BiomeMixin.java](dev/ftb/mods/ftbchunks/core/mixin/BiomeMixin.java) |
| `ClaimExpirationManager` | enum | — | — | 6 | 131 | [ClaimExpirationManager.java](dev/ftb/mods/ftbchunks/data/ClaimExpirationManager.java) |
| `FTBChunksClientConfig` | interface | — | — | 0 | 196 | [FTBChunksClientConfig.java](dev/ftb/mods/ftbchunks/client/FTBChunksClientConfig.java) |
| `FTBChunksClientImpl` | class | — | — | 3 | 37 | [FTBChunksClientImpl.java](dev/ftb/mods/ftbchunks/client/neoforge/FTBChunksClientImpl.java) |
| `MapRegion` | class | MapTask | — | 28 | 288 | [MapRegion.java](dev/ftb/mods/ftbchunks/client/map/MapRegion.java) |
| `FTBChunksWorldConfig` | interface | — | — | 0 | 258 | [FTBChunksWorldConfig.java](dev/ftb/mods/ftbchunks/FTBChunksWorldConfig.java) |
| `SendGeneralDataPacket` | record | CustomPacketPayload | — | 5 | 63 | [SendGeneralDataPacket.java](dev/ftb/mods/ftbchunks/net/SendGeneralDataPacket.java) |
| `RequestBlockColorPacket` | record | CustomPacketPayload | — | 2 | 24 | [RequestBlockColorPacket.java](dev/ftb/mods/ftbchunks/net/RequestBlockColorPacket.java) |
| `ClaimedChunkImpl` | class | ClaimedChunk | — | 24 | 209 | [ClaimedChunkImpl.java](dev/ftb/mods/ftbchunks/data/ClaimedChunkImpl.java) |
| `ClaimedChunkManager` | interface | — | — | 0 | 46 | [ClaimedChunkManager.java](dev/ftb/mods/ftbchunks/api/ClaimedChunkManager.java) |
| `WaypointManager` | interface | — | — | 0 | 21 | [WaypointManager.java](dev/ftb/mods/ftbchunks/api/client/waypoint/WaypointManager.java) |
| `SliceCreationGUI` | class | BaseScreen | — | 17 | 402 | [SliceCreationGUI.java](dev/ftb/mods/ftbchunks/client/gui/SliceCreationGUI.java) |
| `EntityIconSettingsScreen` | class | AbstractGroupedButtonListScreen | — | 7 | 129 | [EntityIconSettingsScreen.java](dev/ftb/mods/ftbchunks/client/gui/EntityIconSettingsScreen.java) |
| `RequestMapDataPacket` | record | CustomPacketPayload | — | 2 | 32 | [RequestMapDataPacket.java](dev/ftb/mods/ftbchunks/net/RequestMapDataPacket.java) |
| `SendManyChunksPacket` | record | CustomPacketPayload | — | 5 | 68 | [SendManyChunksPacket.java](dev/ftb/mods/ftbchunks/net/SendManyChunksPacket.java) |
| `LoginDataPacket` | record | CustomPacketPayload | — | 2 | 27 | [LoginDataPacket.java](dev/ftb/mods/ftbchunks/net/LoginDataPacket.java) |
| `PlayerVisibilityPacket` | record | CustomPacketPayload | — | 6 | 74 | [PlayerVisibilityPacket.java](dev/ftb/mods/ftbchunks/net/PlayerVisibilityPacket.java) |
| `ChunksUpdatedFromServerEvent` | class | — | — | 6 | 56 | [ChunksUpdatedFromServerEvent.java](dev/ftb/mods/ftbchunks/api/client/event/ChunksUpdatedFromServerEvent.java) |
| `AddWaypointOverlay` | class | EditStringConfigOverlay | — | 16 | 234 | [AddWaypointOverlay.java](dev/ftb/mods/ftbchunks/client/gui/AddWaypointOverlay.java) |
| `MinimapComponentConfig` | class | StringMapValue | — | 3 | 34 | [MinimapComponentConfig.java](dev/ftb/mods/ftbchunks/client/minimap/MinimapComponentConfig.java) |
| `MapDimension` | class | MapTask | — | 19 | 240 | [MapDimension.java](dev/ftb/mods/ftbchunks/client/map/MapDimension.java) |
| `MapChunk` | class | — | — | 21 | 129 | [MapChunk.java](dev/ftb/mods/ftbchunks/client/map/MapChunk.java) |
| `EntityTypeBoolMapValue` | class | BaseValue | — | 5 | 93 | [EntityTypeBoolMapValue.java](dev/ftb/mods/ftbchunks/EntityTypeBoolMapValue.java) |
| `PartialPackets` | class | — | — | 2 | 82 | [PartialPackets.java](dev/ftb/mods/ftbchunks/net/PartialPackets.java) |
| `PlayerDeathPacket` | record | CustomPacketPayload | — | 2 | 27 | [PlayerDeathPacket.java](dev/ftb/mods/ftbchunks/net/PlayerDeathPacket.java) |
| `ShareWaypointPacket` | record | CustomPacketPayload | — | 2 | 106 | [ShareWaypointPacket.java](dev/ftb/mods/ftbchunks/net/ShareWaypointPacket.java) |
| `RequestChunkChangePacket` | record | CustomPacketPayload | — | 4 | 120 | [RequestChunkChangePacket.java](dev/ftb/mods/ftbchunks/net/RequestChunkChangePacket.java) |
| `ChunkChangeResponsePacket` | record | CustomPacketPayload | — | 2 | 34 | [ChunkChangeResponsePacket.java](dev/ftb/mods/ftbchunks/net/ChunkChangeResponsePacket.java) |
| `UpdateForceLoadExpiryPacket` | record | CustomPacketPayload | — | 2 | 52 | [UpdateForceLoadExpiryPacket.java](dev/ftb/mods/ftbchunks/net/UpdateForceLoadExpiryPacket.java) |
| `SyncRXPacket` | record | CustomPacketPayload | — | 2 | 35 | [SyncRXPacket.java](dev/ftb/mods/ftbchunks/net/SyncRXPacket.java) |
| `AddWaypointPacket` | record | CustomPacketPayload | — | 2 | 45 | [AddWaypointPacket.java](dev/ftb/mods/ftbchunks/net/AddWaypointPacket.java) |
| `OpenClaimGUIPacket` | record | CustomPacketPayload | — | 2 | 41 | [OpenClaimGUIPacket.java](dev/ftb/mods/ftbchunks/net/OpenClaimGUIPacket.java) |
| `SendChunkPacket` | record | CustomPacketPayload | — | 3 | 55 | [SendChunkPacket.java](dev/ftb/mods/ftbchunks/net/SendChunkPacket.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `FTBChunksClientImpl` | 1 | `dev.ftb.mods.ftbchunks.client.neoforge` | [FTBChunksClientImpl.java](dev/ftb/mods/ftbchunks/client/neoforge/FTBChunksClientImpl.java) |

## Mixin（12 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ClientPacketListenerMixin` | `ClientPacketListener` | [ClientPacketListenerMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ClientPacketListenerMixin.java) |
| `PistonBaseBlockMixin` | `PistonBaseBlock` | [PistonBaseBlockMixin.java](dev/ftb/mods/ftbchunks/core/mixin/PistonBaseBlockMixin.java) |
| `ItemFrameMixin` | `ItemFrame` | [ItemFrameMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ItemFrameMixin.java) |
| `BlockStateMixin` | `BlockState` | [BlockStateMixin.java](dev/ftb/mods/ftbchunks/core/mixin/BlockStateMixin.java) |
| `VehicleEntityMixin` | `VehicleEntity` | [VehicleEntityMixin.java](dev/ftb/mods/ftbchunks/core/mixin/VehicleEntityMixin.java) |
| `FireBlockMixin` | `FireBlock` | [FireBlockMixin.java](dev/ftb/mods/ftbchunks/core/mixin/FireBlockMixin.java) |
| `UtilMixin` | `Util` | [UtilMixin.java](dev/ftb/mods/ftbchunks/core/mixin/UtilMixin.java) |
| `FlowingFluidMixin` | `FlowingFluid` | [FlowingFluidMixin.java](dev/ftb/mods/ftbchunks/core/mixin/FlowingFluidMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](dev/ftb/mods/ftbchunks/core/mixin/GuiMixin.java) |
| `ArmorStandMixin` | `ArmorStand` | [ArmorStandMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ArmorStandMixin.java) |
| `ChunkMapMixin` | `ChunkMap` | [ChunkMapMixin.java](dev/ftb/mods/ftbchunks/core/mixin/ChunkMapMixin.java) |
| `BiomeMixin` | `Biome` | [BiomeMixin.java](dev/ftb/mods/ftbchunks/core/mixin/BiomeMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `FTBChunksClientConfig` | — | [FTBChunksClientConfig.java](dev/ftb/mods/ftbchunks/client/FTBChunksClientConfig.java) |
| `FTBChunksWorldConfig` | — | [FTBChunksWorldConfig.java](dev/ftb/mods/ftbchunks/FTBChunksWorldConfig.java) |
| `MinimapComponentConfig` | — | [MinimapComponentConfig.java](dev/ftb/mods/ftbchunks/client/minimap/MinimapComponentConfig.java) |

## 按包浏览

- `architectury_inject_FTBChunks121_common_31935692caf3484da5f048f8d908811d_2bc1009072b093f1411761284e516c92fb7252980e0b90cba697d97c751d40e1ftbchunks2101120devjar`（1 类）
- `dev.ftb.mods.ftbchunks`（11 类）
- `dev.ftb.mods.ftbchunks.api`（10 类）
- `dev.ftb.mods.ftbchunks.api.client`（1 类）
- `dev.ftb.mods.ftbchunks.api.client.event`（3 类）
- `dev.ftb.mods.ftbchunks.api.client.icon`（3 类）
- `dev.ftb.mods.ftbchunks.api.client.minimap`（3 类）
- `dev.ftb.mods.ftbchunks.api.client.waypoint`（2 类）
- `dev.ftb.mods.ftbchunks.api.event`（2 类）
- `dev.ftb.mods.ftbchunks.client`（9 类）
- `dev.ftb.mods.ftbchunks.client.gui`（16 类）
- `dev.ftb.mods.ftbchunks.client.map`（21 类）
- `dev.ftb.mods.ftbchunks.client.map.color`（5 类）
- `dev.ftb.mods.ftbchunks.client.mapicon`（7 类）
- `dev.ftb.mods.ftbchunks.client.minimap`（1 类）
- `dev.ftb.mods.ftbchunks.client.minimap.components`（7 类）
- `dev.ftb.mods.ftbchunks.client.neoforge`（1 类）
- `dev.ftb.mods.ftbchunks.core`（2 类）
- `dev.ftb.mods.ftbchunks.core.mixin`（12 类）
- `dev.ftb.mods.ftbchunks.data`（10 类）
- `dev.ftb.mods.ftbchunks.integration`（1 类）
- `dev.ftb.mods.ftbchunks.neoforge`（4 类）
- `dev.ftb.mods.ftbchunks.neoforge.integration`（1 类）
- `dev.ftb.mods.ftbchunks.net`（21 类）
- `dev.ftb.mods.ftbchunks.util`（7 类）
