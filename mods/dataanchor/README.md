# Data Anchor（`dataanchor`）

> jar：`[前置]DataAnchor-.jar` ｜ 版本：`2.0.0.12` ｜ 作者：Corgi Taco
> 规模：**67 个类 / 23 个包 / 2883 行** ｜ 事件处理类 1 ｜ Mixin 11 ｜ 注册/管理类 2

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `dev.corgitaco.dataanchor.mixin` | 10 | `BlockEntityMixin`、`ServerEntityMixin`、`EntityMixin` |
| `dev.corgitaco.dataanchor.data` | 8 | `ClientTrackedData`、`TrackedData`、`SyncedTrackedData` |
| `dev.corgitaco.dataanchor.data.type.entity` | 6 | `ServerEntityTrackedData`、`SyncedEntityTrackedData`、`EntityTrackedData` |
| `dev.corgitaco.dataanchor.data.type.chunk` | 6 | `ChunkBlockStateInterceptor`、`LevelChunkTrackedData`、`ProtoChunkTrackedData` |
| `dev.corgitaco.dataanchor.network` | 5 | `Packet`、`S2CNetworkContainer`、`BiDirectionalNetworkContainer` |
| `dev.corgitaco.dataanchor.neoforge.network` | 4 | `NeoForgeNetworkHandler`、`S2CNeoForgePacketBroadcaster`、`BidirectionalNeoForgePacketBroadcaster` |
| `dev.corgitaco.dataanchor.data.type.blockentity` | 4 | `ServerBlockEntityTrackedData`、`BlockEntityTrackedData`、`SyncedBlockEntityTrackedData` |
| `dev.corgitaco.dataanchor.network.broadcast` | 4 | `PacketBroadcaster`、`S2CPacketBroadcaster`、`C2SPacketBroadcaster` |
| `dev.corgitaco.dataanchor.data.type.level` | 4 | `SyncedLevelTrackedData`、`TrackedLevelSavedData`、`LevelTrackedData` |
| `dev.corgitaco.dataanchor.data.registry` | 3 | `TrackedDataRegistry`、`TrackedDataRegistries`、`TrackedDataKey` |
| `dev.corgitaco.dataanchor.neoforge` | 1 | `DataAnchorNeoForge` |
| `dev.corgitaco.dataanchor.mixin.client` | 1 | `LevelMixin` |
| `dev.corgitaco.dataanchor.data.type.blockentity.network` | 1 | `SyncBlockEntityTrackedDataS2C` |
| `dev.corgitaco.dataanchor.test.data` | 1 | `TestSyncedBlockEntityTrackedData` |
| `dev.corgitaco.dataanchor.data.type.entity.network` | 1 | `SyncEntityTrackedDataS2C` |
| `dev.corgitaco.dataanchor` | 1 | `DataAnchor` |
| `dev.corgitaco.dataanchor.data.type.level.network` | 1 | `SyncLevelTrackedDataS2C` |
| `dev.corgitaco.dataanchor.data.type.chunk.network` | 1 | `SyncLevelChunkTrackedDataS2C` |
| `dev.corgitaco.dataanchor.test.data.player` | 1 | `TestSyncedPlayerTrackedData` |
| `dev.corgitaco.dataanchor.test.data.level` | 1 | `TestSyncedLevelTrackedData` |
| `dev.corgitaco.dataanchor.test.data.chunk` | 1 | `TestSyncedLevelChunkTrackedData` |
| `dev.corgitaco.dataanchor.util` | 1 | `ServiceUtil` |
| `architectury_inject_DataAnchor_common_731a993464924cdcaed5ccdb6bd1d900_27ae029e4d8f9e9737e0eefa151635f45dfce233e57ed09206a233a8a58cf92eData_Anchorcommon121120012devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeNetworkHandler` | class | — | EventBusSubscriber | 4 | 42 | [NeoForgeNetworkHandler.java](dev/corgitaco/dataanchor/neoforge/network/NeoForgeNetworkHandler.java) |
| `DataAnchorNeoForge` | class | — | Mod | 0 | 13 | [DataAnchorNeoForge.java](dev/corgitaco/dataanchor/neoforge/DataAnchorNeoForge.java) |
| `BlockEntityMixin` | class | TrackedDataContainer<BlockEntity | Mixin | 0 | 101 | [BlockEntityMixin.java](dev/corgitaco/dataanchor/mixin/BlockEntityMixin.java) |
| `ServerEntityMixin` | class | — | Mixin | 0 | 51 | [ServerEntityMixin.java](dev/corgitaco/dataanchor/mixin/ServerEntityMixin.java) |
| `EntityMixin` | class | TrackedDataContainer<Entity | Mixin | 0 | 98 | [EntityMixin.java](dev/corgitaco/dataanchor/mixin/EntityMixin.java) |
| `TrackedDataRegistry` | class | — | — | 6 | 70 | [TrackedDataRegistry.java](dev/corgitaco/dataanchor/data/registry/TrackedDataRegistry.java) |
| `ServerLevelMixin` | class | Level | Mixin | 0 | 126 | [ServerLevelMixin.java](dev/corgitaco/dataanchor/mixin/ServerLevelMixin.java) |
| `ChunkAccessMixin` | class | TrackedDataContainer<ChunkAccess | Mixin | 0 | 88 | [ChunkAccessMixin.java](dev/corgitaco/dataanchor/mixin/ChunkAccessMixin.java) |
| `ChunkSerializerMixin` | class | — | Mixin | 0 | 74 | [ChunkSerializerMixin.java](dev/corgitaco/dataanchor/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | class | TrackedDataContainer<Level | Mixin | 2 | 130 | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/LevelMixin.java) |
| `ChunkMixin` | class | ChunkBlockStateInterceptor.Internal | Mixin | 0 | 20 | [ChunkMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 0 | 45 | [ChunkMapMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMapMixin.java) |
| `PlayerListMixin` | class | — | Mixin | 0 | 32 | [PlayerListMixin.java](dev/corgitaco/dataanchor/mixin/PlayerListMixin.java) |
| `LevelMixin` | class | LevelAccessor | Mixin | 1 | 68 | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/client/LevelMixin.java) |
| `ServerBlockEntityTrackedData` | class | BlockEntityTrackedData | — | 0 | 12 | [ServerBlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/ServerBlockEntityTrackedData.java) |
| `BlockEntityTrackedData` | class | TrackedData<BlockEntity> | — | 3 | 34 | [BlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/BlockEntityTrackedData.java) |
| `SyncedBlockEntityTrackedData` | class | BlockEntityTrackedData | — | 3 | 33 | [SyncedBlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/SyncedBlockEntityTrackedData.java) |
| `SyncBlockEntityTrackedDataS2C` | record | Packet | — | 3 | 52 | [SyncBlockEntityTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/blockentity/network/SyncBlockEntityTrackedDataS2C.java) |
| `TestSyncedBlockEntityTrackedData` | class | SyncedBlockEntityTrackedData | — | 2 | 33 | [TestSyncedBlockEntityTrackedData.java](dev/corgitaco/dataanchor/test/data/TestSyncedBlockEntityTrackedData.java) |
| `S2CNeoForgePacketBroadcaster` | class | S2CPacketBroadcaster | AutoService | 8 | 54 | [S2CNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/S2CNeoForgePacketBroadcaster.java) |
| `BidirectionalNeoForgePacketBroadcaster` | class | BiDirectionalPacketBroadcaster | AutoService | 9 | 59 | [BidirectionalNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/BidirectionalNeoForgePacketBroadcaster.java) |
| `PendingBlockEntityTick` | interface | — | — | 0 | 6 | [PendingBlockEntityTick.java](dev/corgitaco/dataanchor/data/type/blockentity/PendingBlockEntityTick.java) |
| `Packet` | interface | — | — | 0 | 22 | [Packet.java](dev/corgitaco/dataanchor/network/Packet.java) |
| `S2CNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [S2CNetworkContainer.java](dev/corgitaco/dataanchor/network/S2CNetworkContainer.java) |
| `BiDirectionalNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [BiDirectionalNetworkContainer.java](dev/corgitaco/dataanchor/network/BiDirectionalNetworkContainer.java) |
| `C2SNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [C2SNetworkContainer.java](dev/corgitaco/dataanchor/network/C2SNetworkContainer.java) |
| `NetworkContainer` | class | — | — | 2 | 38 | [NetworkContainer.java](dev/corgitaco/dataanchor/network/NetworkContainer.java) |
| `PacketBroadcaster` | interface | — | — | 0 | 14 | [PacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/PacketBroadcaster.java) |
| `S2CPacketBroadcaster` | interface | — | — | 0 | 40 | [S2CPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/S2CPacketBroadcaster.java) |
| `C2SPacketBroadcaster` | interface | — | — | 0 | 10 | [C2SPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/C2SPacketBroadcaster.java) |
| `BiDirectionalPacketBroadcaster` | interface | — | — | 0 | 6 | [BiDirectionalPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/BiDirectionalPacketBroadcaster.java) |
| `C2SNeoForgePacketBroadcaster` | class | C2SPacketBroadcaster | AutoService | 2 | 20 | [C2SNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/C2SNeoForgePacketBroadcaster.java) |
| `ClientTrackedData` | interface | — | — | 0 | 5 | [ClientTrackedData.java](dev/corgitaco/dataanchor/data/ClientTrackedData.java) |
| `TrackedData` | interface | — | — | 1 | 400 | [TrackedData.java](dev/corgitaco/dataanchor/data/TrackedData.java) |
| `ServerEntityTrackedData` | class | EntityTrackedData | — | 0 | 12 | [ServerEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/ServerEntityTrackedData.java) |
| `SyncedEntityTrackedData` | class | EntityTrackedData | — | 3 | 33 | [SyncedEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/SyncedEntityTrackedData.java) |
| `EntityTrackedData` | class | TrackedData<Entity> permits ServerEn | — | 2 | 23 | [EntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/EntityTrackedData.java) |
| `SyncEntityTrackedDataS2C` | record | Packet | — | 3 | 53 | [SyncEntityTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/entity/network/SyncEntityTrackedDataS2C.java) |
| `PlayerTrackedData` | class | EntityTrackedData | — | 5 | 43 | [PlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/PlayerTrackedData.java) |
| `SyncedLevelTrackedData` | class | LevelTrackedData | — | 5 | 47 | [SyncedLevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/SyncedLevelTrackedData.java) |
| `ChunkBlockStateInterceptor` | interface | — | — | 0 | 19 | [ChunkBlockStateInterceptor.java](dev/corgitaco/dataanchor/data/type/chunk/ChunkBlockStateInterceptor.java) |
| `DataAnchor` | class | — | — | 3 | 55 | [DataAnchor.java](dev/corgitaco/dataanchor/DataAnchor.java) |
| `SyncedTrackedData` | interface | — | — | 0 | 24 | [SyncedTrackedData.java](dev/corgitaco/dataanchor/data/SyncedTrackedData.java) |
| `TrackedDataContainer` | interface | — | — | 0 | 69 | [TrackedDataContainer.java](dev/corgitaco/dataanchor/data/TrackedDataContainer.java) |
| `TickableTrackedData` | interface | — | — | 0 | 6 | [TickableTrackedData.java](dev/corgitaco/dataanchor/data/TickableTrackedData.java) |
| `ServerTrackedData` | interface | — | — | 0 | 5 | [ServerTrackedData.java](dev/corgitaco/dataanchor/data/ServerTrackedData.java) |
| `ServerPlayerTrackedData` | class | PlayerTrackedData | — | 1 | 20 | [ServerPlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/ServerPlayerTrackedData.java) |
| `SyncedPlayerTrackedData` | class | PlayerTrackedData | — | 3 | 39 | [SyncedPlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/SyncedPlayerTrackedData.java) |
| `TrackedLevelSavedData` | class | SavedData | — | 3 | 100 | [TrackedLevelSavedData.java](dev/corgitaco/dataanchor/data/type/level/TrackedLevelSavedData.java) |
| `LevelTrackedData` | class | TrackedData<Level> permits ServerLev | — | 1 | 20 | [LevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/LevelTrackedData.java) |
| `ServerLevelTrackedData` | class | LevelTrackedData | — | 3 | 29 | [ServerLevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/ServerLevelTrackedData.java) |
| `SyncLevelTrackedDataS2C` | record | Packet | — | 3 | 49 | [SyncLevelTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/level/network/SyncLevelTrackedDataS2C.java) |
| `LevelChunkTrackedData` | class | ChunkTrackedData | — | 1 | 15 | [LevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/LevelChunkTrackedData.java) |
| `ProtoChunkTrackedData` | class | ChunkTrackedData | — | 1 | 16 | [ProtoChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ProtoChunkTrackedData.java) |
| `SyncedLevelChunkTrackedData` | class | LevelChunkTrackedData | — | 3 | 35 | [SyncedLevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/SyncedLevelChunkTrackedData.java) |
| `ChunkTrackedData` | class | TrackedData<ChunkAccess> | — | 3 | 30 | [ChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ChunkTrackedData.java) |
| `ServerLevelChunkTrackedData` | class | LevelChunkTrackedData | — | 0 | 12 | [ServerLevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ServerLevelChunkTrackedData.java) |
| `SyncLevelChunkTrackedDataS2C` | record | Packet | — | 3 | 54 | [SyncLevelChunkTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/chunk/network/SyncLevelChunkTrackedDataS2C.java) |
| `TrackedDataRegistries` | class | — | — | 0 | 19 | [TrackedDataRegistries.java](dev/corgitaco/dataanchor/data/registry/TrackedDataRegistries.java) |
| `TrackedDataKey` | class | — | — | 3 | 58 | [TrackedDataKey.java](dev/corgitaco/dataanchor/data/registry/TrackedDataKey.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NeoForgeNetworkHandler` | 1 | `dev.corgitaco.dataanchor.neoforge.network` | [NeoForgeNetworkHandler.java](dev/corgitaco/dataanchor/neoforge/network/NeoForgeNetworkHandler.java) |

## Mixin（11 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](dev/corgitaco/dataanchor/mixin/BlockEntityMixin.java) |
| `ServerEntityMixin` | `ServerEntity` | [ServerEntityMixin.java](dev/corgitaco/dataanchor/mixin/ServerEntityMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](dev/corgitaco/dataanchor/mixin/EntityMixin.java) |
| `ServerLevelMixin` | `ServerLevel` | [ServerLevelMixin.java](dev/corgitaco/dataanchor/mixin/ServerLevelMixin.java) |
| `ChunkAccessMixin` | `ChunkAccess` | [ChunkAccessMixin.java](dev/corgitaco/dataanchor/mixin/ChunkAccessMixin.java) |
| `ChunkSerializerMixin` | `ChunkSerializer` | [ChunkSerializerMixin.java](dev/corgitaco/dataanchor/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | `Level` | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/LevelMixin.java) |
| `ChunkMixin` | `Chunk` | [ChunkMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMixin.java) |
| `ChunkMapMixin` | `ChunkMap` | [ChunkMapMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMapMixin.java) |
| `PlayerListMixin` | `PlayerList` | [PlayerListMixin.java](dev/corgitaco/dataanchor/mixin/PlayerListMixin.java) |
| `LevelMixin` | `Level` | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/client/LevelMixin.java) |

## 全部类（67）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NeoForgeNetworkHandler` | class | — | EventBusSubscriber | 4 | 42 | [NeoForgeNetworkHandler.java](dev/corgitaco/dataanchor/neoforge/network/NeoForgeNetworkHandler.java) |
| `DataAnchorNeoForge` | class | — | Mod | 0 | 13 | [DataAnchorNeoForge.java](dev/corgitaco/dataanchor/neoforge/DataAnchorNeoForge.java) |
| `BlockEntityMixin` | class | TrackedDataContainer<BlockEntity | Mixin | 0 | 101 | [BlockEntityMixin.java](dev/corgitaco/dataanchor/mixin/BlockEntityMixin.java) |
| `ServerEntityMixin` | class | — | Mixin | 0 | 51 | [ServerEntityMixin.java](dev/corgitaco/dataanchor/mixin/ServerEntityMixin.java) |
| `EntityMixin` | class | TrackedDataContainer<Entity | Mixin | 0 | 98 | [EntityMixin.java](dev/corgitaco/dataanchor/mixin/EntityMixin.java) |
| `TrackedDataRegistry` | class | — | — | 6 | 70 | [TrackedDataRegistry.java](dev/corgitaco/dataanchor/data/registry/TrackedDataRegistry.java) |
| `ServerLevelMixin` | class | Level | Mixin | 0 | 126 | [ServerLevelMixin.java](dev/corgitaco/dataanchor/mixin/ServerLevelMixin.java) |
| `ChunkAccessMixin` | class | TrackedDataContainer<ChunkAccess | Mixin | 0 | 88 | [ChunkAccessMixin.java](dev/corgitaco/dataanchor/mixin/ChunkAccessMixin.java) |
| `ChunkSerializerMixin` | class | — | Mixin | 0 | 74 | [ChunkSerializerMixin.java](dev/corgitaco/dataanchor/mixin/ChunkSerializerMixin.java) |
| `LevelMixin` | class | TrackedDataContainer<Level | Mixin | 2 | 130 | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/LevelMixin.java) |
| `ChunkMixin` | class | ChunkBlockStateInterceptor.Internal | Mixin | 0 | 20 | [ChunkMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMixin.java) |
| `ChunkMapMixin` | class | — | Mixin | 0 | 45 | [ChunkMapMixin.java](dev/corgitaco/dataanchor/mixin/ChunkMapMixin.java) |
| `PlayerListMixin` | class | — | Mixin | 0 | 32 | [PlayerListMixin.java](dev/corgitaco/dataanchor/mixin/PlayerListMixin.java) |
| `LevelMixin` | class | LevelAccessor | Mixin | 1 | 68 | [LevelMixin.java](dev/corgitaco/dataanchor/mixin/client/LevelMixin.java) |
| `ServerBlockEntityTrackedData` | class | BlockEntityTrackedData | — | 0 | 12 | [ServerBlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/ServerBlockEntityTrackedData.java) |
| `BlockEntityTrackedData` | class | TrackedData<BlockEntity> | — | 3 | 34 | [BlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/BlockEntityTrackedData.java) |
| `SyncedBlockEntityTrackedData` | class | BlockEntityTrackedData | — | 3 | 33 | [SyncedBlockEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/blockentity/SyncedBlockEntityTrackedData.java) |
| `SyncBlockEntityTrackedDataS2C` | record | Packet | — | 3 | 52 | [SyncBlockEntityTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/blockentity/network/SyncBlockEntityTrackedDataS2C.java) |
| `TestSyncedBlockEntityTrackedData` | class | SyncedBlockEntityTrackedData | — | 2 | 33 | [TestSyncedBlockEntityTrackedData.java](dev/corgitaco/dataanchor/test/data/TestSyncedBlockEntityTrackedData.java) |
| `S2CNeoForgePacketBroadcaster` | class | S2CPacketBroadcaster | AutoService | 8 | 54 | [S2CNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/S2CNeoForgePacketBroadcaster.java) |
| `BidirectionalNeoForgePacketBroadcaster` | class | BiDirectionalPacketBroadcaster | AutoService | 9 | 59 | [BidirectionalNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/BidirectionalNeoForgePacketBroadcaster.java) |
| `PendingBlockEntityTick` | interface | — | — | 0 | 6 | [PendingBlockEntityTick.java](dev/corgitaco/dataanchor/data/type/blockentity/PendingBlockEntityTick.java) |
| `Packet` | interface | — | — | 0 | 22 | [Packet.java](dev/corgitaco/dataanchor/network/Packet.java) |
| `S2CNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [S2CNetworkContainer.java](dev/corgitaco/dataanchor/network/S2CNetworkContainer.java) |
| `BiDirectionalNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [BiDirectionalNetworkContainer.java](dev/corgitaco/dataanchor/network/BiDirectionalNetworkContainer.java) |
| `C2SNetworkContainer` | class | NetworkContainer | — | 1 | 24 | [C2SNetworkContainer.java](dev/corgitaco/dataanchor/network/C2SNetworkContainer.java) |
| `NetworkContainer` | class | — | — | 2 | 38 | [NetworkContainer.java](dev/corgitaco/dataanchor/network/NetworkContainer.java) |
| `PacketBroadcaster` | interface | — | — | 0 | 14 | [PacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/PacketBroadcaster.java) |
| `S2CPacketBroadcaster` | interface | — | — | 0 | 40 | [S2CPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/S2CPacketBroadcaster.java) |
| `C2SPacketBroadcaster` | interface | — | — | 0 | 10 | [C2SPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/C2SPacketBroadcaster.java) |
| `BiDirectionalPacketBroadcaster` | interface | — | — | 0 | 6 | [BiDirectionalPacketBroadcaster.java](dev/corgitaco/dataanchor/network/broadcast/BiDirectionalPacketBroadcaster.java) |
| `C2SNeoForgePacketBroadcaster` | class | C2SPacketBroadcaster | AutoService | 2 | 20 | [C2SNeoForgePacketBroadcaster.java](dev/corgitaco/dataanchor/neoforge/network/C2SNeoForgePacketBroadcaster.java) |
| `ClientTrackedData` | interface | — | — | 0 | 5 | [ClientTrackedData.java](dev/corgitaco/dataanchor/data/ClientTrackedData.java) |
| `TrackedData` | interface | — | — | 1 | 400 | [TrackedData.java](dev/corgitaco/dataanchor/data/TrackedData.java) |
| `ServerEntityTrackedData` | class | EntityTrackedData | — | 0 | 12 | [ServerEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/ServerEntityTrackedData.java) |
| `SyncedEntityTrackedData` | class | EntityTrackedData | — | 3 | 33 | [SyncedEntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/SyncedEntityTrackedData.java) |
| `EntityTrackedData` | class | TrackedData<Entity> permits ServerEn | — | 2 | 23 | [EntityTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/EntityTrackedData.java) |
| `SyncEntityTrackedDataS2C` | record | Packet | — | 3 | 53 | [SyncEntityTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/entity/network/SyncEntityTrackedDataS2C.java) |
| `PlayerTrackedData` | class | EntityTrackedData | — | 5 | 43 | [PlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/PlayerTrackedData.java) |
| `SyncedLevelTrackedData` | class | LevelTrackedData | — | 5 | 47 | [SyncedLevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/SyncedLevelTrackedData.java) |
| `ChunkBlockStateInterceptor` | interface | — | — | 0 | 19 | [ChunkBlockStateInterceptor.java](dev/corgitaco/dataanchor/data/type/chunk/ChunkBlockStateInterceptor.java) |
| `DataAnchor` | class | — | — | 3 | 55 | [DataAnchor.java](dev/corgitaco/dataanchor/DataAnchor.java) |
| `SyncedTrackedData` | interface | — | — | 0 | 24 | [SyncedTrackedData.java](dev/corgitaco/dataanchor/data/SyncedTrackedData.java) |
| `TrackedDataContainer` | interface | — | — | 0 | 69 | [TrackedDataContainer.java](dev/corgitaco/dataanchor/data/TrackedDataContainer.java) |
| `TickableTrackedData` | interface | — | — | 0 | 6 | [TickableTrackedData.java](dev/corgitaco/dataanchor/data/TickableTrackedData.java) |
| `ServerTrackedData` | interface | — | — | 0 | 5 | [ServerTrackedData.java](dev/corgitaco/dataanchor/data/ServerTrackedData.java) |
| `ServerPlayerTrackedData` | class | PlayerTrackedData | — | 1 | 20 | [ServerPlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/ServerPlayerTrackedData.java) |
| `SyncedPlayerTrackedData` | class | PlayerTrackedData | — | 3 | 39 | [SyncedPlayerTrackedData.java](dev/corgitaco/dataanchor/data/type/entity/SyncedPlayerTrackedData.java) |
| `TrackedLevelSavedData` | class | SavedData | — | 3 | 100 | [TrackedLevelSavedData.java](dev/corgitaco/dataanchor/data/type/level/TrackedLevelSavedData.java) |
| `LevelTrackedData` | class | TrackedData<Level> permits ServerLev | — | 1 | 20 | [LevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/LevelTrackedData.java) |
| `ServerLevelTrackedData` | class | LevelTrackedData | — | 3 | 29 | [ServerLevelTrackedData.java](dev/corgitaco/dataanchor/data/type/level/ServerLevelTrackedData.java) |
| `SyncLevelTrackedDataS2C` | record | Packet | — | 3 | 49 | [SyncLevelTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/level/network/SyncLevelTrackedDataS2C.java) |
| `LevelChunkTrackedData` | class | ChunkTrackedData | — | 1 | 15 | [LevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/LevelChunkTrackedData.java) |
| `ProtoChunkTrackedData` | class | ChunkTrackedData | — | 1 | 16 | [ProtoChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ProtoChunkTrackedData.java) |
| `SyncedLevelChunkTrackedData` | class | LevelChunkTrackedData | — | 3 | 35 | [SyncedLevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/SyncedLevelChunkTrackedData.java) |
| `ChunkTrackedData` | class | TrackedData<ChunkAccess> | — | 3 | 30 | [ChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ChunkTrackedData.java) |
| `ServerLevelChunkTrackedData` | class | LevelChunkTrackedData | — | 0 | 12 | [ServerLevelChunkTrackedData.java](dev/corgitaco/dataanchor/data/type/chunk/ServerLevelChunkTrackedData.java) |
| `SyncLevelChunkTrackedDataS2C` | record | Packet | — | 3 | 54 | [SyncLevelChunkTrackedDataS2C.java](dev/corgitaco/dataanchor/data/type/chunk/network/SyncLevelChunkTrackedDataS2C.java) |
| `TrackedDataRegistries` | class | — | — | 0 | 19 | [TrackedDataRegistries.java](dev/corgitaco/dataanchor/data/registry/TrackedDataRegistries.java) |
| `TrackedDataKey` | class | — | — | 3 | 58 | [TrackedDataKey.java](dev/corgitaco/dataanchor/data/registry/TrackedDataKey.java) |
| `TestSyncedPlayerTrackedData` | class | SyncedPlayerTrackedData | — | 2 | 24 | [TestSyncedPlayerTrackedData.java](dev/corgitaco/dataanchor/test/data/player/TestSyncedPlayerTrackedData.java) |
| `TestSyncedLevelTrackedData` | class | SyncedLevelTrackedData | — | 2 | 34 | [TestSyncedLevelTrackedData.java](dev/corgitaco/dataanchor/test/data/level/TestSyncedLevelTrackedData.java) |
| `TestSyncedLevelChunkTrackedData` | class | SyncedLevelChunkTrackedData | — | 2 | 35 | [TestSyncedLevelChunkTrackedData.java](dev/corgitaco/dataanchor/test/data/chunk/TestSyncedLevelChunkTrackedData.java) |
| `ServiceUtil` | class | — | — | 1 | 13 | [ServiceUtil.java](dev/corgitaco/dataanchor/util/ServiceUtil.java) |
| `InternalDirtyMarker` | interface | — | — | 0 | 8 | [InternalDirtyMarker.java](dev/corgitaco/dataanchor/data/InternalDirtyMarker.java) |
| `DirtyMarker` | interface | — | — | 0 | 8 | [DirtyMarker.java](dev/corgitaco/dataanchor/data/DirtyMarker.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_DataAnchor_common_731a993464924cdcaed5ccdb6bd1d900_27ae029e4d8f9e9737e0eefa151635f45dfce233e57ed09206a233a8a58cf92eData_Anchorcommon121120012devjar/PlatformMethods.java) |
