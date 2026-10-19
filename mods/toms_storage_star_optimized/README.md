# Toms Storage Star Optimized（`toms_storage_star_optimized`）

> jar：`[汤姆存储优化]tomsstoragestaroptimized.jar` ｜ 版本：`0.9.3` ｜ 作者：TheFlareStar
> 规模：**10 个类 / 4 个包 / 683 行** ｜ 事件处理类 1 ｜ Mixin 5 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`、`toms_storage`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `cn.star.toms_storage_star_optimized.mixin` | 5 | `StorageTerminalBlockEntityMixin`、`InventoryConnectorBlockEntityMixin`、`StorageTerminalMenuMixin` |
| `cn.star.toms_storage_star_optimized.opt` | 3 | `NetworkDirtyEvents`、`INetworkDirtyReceiver`、`NetworkDirtyPropagation` |
| `cn.star.toms_storage_star_optimized` | 1 | `TomsStorageStarOptimized` |
| `cn.star.toms_storage_star_optimized.api` | 1 | `TerminalDirtyState` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkDirtyEvents` | class | — | — | 4 | 54 | [NetworkDirtyEvents.java](cn/star/toms_storage_star_optimized/opt/NetworkDirtyEvents.java) |
| `TomsStorageStarOptimized` | class | — | Mod | 0 | 16 | [TomsStorageStarOptimized.java](cn/star/toms_storage_star_optimized/TomsStorageStarOptimized.java) |
| `StorageTerminalBlockEntityMixin` | class | TerminalDirtyState | Mixin | 4 | 137 | [StorageTerminalBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalBlockEntityMixin.java) |
| `InventoryConnectorBlockEntityMixin` | class | INetworkDirtyReceiver | Mixin | 3 | 84 | [InventoryConnectorBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/InventoryConnectorBlockEntityMixin.java) |
| `StorageTerminalMenuMixin` | class | — | Mixin | 0 | 49 | [StorageTerminalMenuMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalMenuMixin.java) |
| `PlatformMultiInventoryAccessMixin` | class | — | Mixin | 8 | 228 | [PlatformMultiInventoryAccessMixin.java](cn/star/toms_storage_star_optimized/mixin/PlatformMultiInventoryAccessMixin.java) |
| `MultiInventoryAccessAccessor` | interface | — | Mixin | 0 | 14 | [MultiInventoryAccessAccessor.java](cn/star/toms_storage_star_optimized/mixin/MultiInventoryAccessAccessor.java) |
| `INetworkDirtyReceiver` | interface | — | — | 0 | 8 | [INetworkDirtyReceiver.java](cn/star/toms_storage_star_optimized/opt/INetworkDirtyReceiver.java) |
| `NetworkDirtyPropagation` | class | — | — | 2 | 87 | [NetworkDirtyPropagation.java](cn/star/toms_storage_star_optimized/opt/NetworkDirtyPropagation.java) |
| `TerminalDirtyState` | interface | — | — | 0 | 6 | [TerminalDirtyState.java](cn/star/toms_storage_star_optimized/api/TerminalDirtyState.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `NetworkDirtyEvents` | 2 | `cn.star.toms_storage_star_optimized.opt` | [NetworkDirtyEvents.java](cn/star/toms_storage_star_optimized/opt/NetworkDirtyEvents.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StorageTerminalBlockEntityMixin` | `StorageTerminalBlockEntity` | [StorageTerminalBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalBlockEntityMixin.java) |
| `InventoryConnectorBlockEntityMixin` | `InventoryConnectorBlockEntity` | [InventoryConnectorBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/InventoryConnectorBlockEntityMixin.java) |
| `StorageTerminalMenuMixin` | `StorageTerminalMenu` | [StorageTerminalMenuMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalMenuMixin.java) |
| `PlatformMultiInventoryAccessMixin` | `PlatformMultiInventoryAccess` | [PlatformMultiInventoryAccessMixin.java](cn/star/toms_storage_star_optimized/mixin/PlatformMultiInventoryAccessMixin.java) |
| `MultiInventoryAccessAccessor` | `MultiInventoryAccessAccessor` | [MultiInventoryAccessAccessor.java](cn/star/toms_storage_star_optimized/mixin/MultiInventoryAccessAccessor.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkDirtyEvents` | class | — | — | 4 | 54 | [NetworkDirtyEvents.java](cn/star/toms_storage_star_optimized/opt/NetworkDirtyEvents.java) |
| `TomsStorageStarOptimized` | class | — | Mod | 0 | 16 | [TomsStorageStarOptimized.java](cn/star/toms_storage_star_optimized/TomsStorageStarOptimized.java) |
| `StorageTerminalBlockEntityMixin` | class | TerminalDirtyState | Mixin | 4 | 137 | [StorageTerminalBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalBlockEntityMixin.java) |
| `InventoryConnectorBlockEntityMixin` | class | INetworkDirtyReceiver | Mixin | 3 | 84 | [InventoryConnectorBlockEntityMixin.java](cn/star/toms_storage_star_optimized/mixin/InventoryConnectorBlockEntityMixin.java) |
| `StorageTerminalMenuMixin` | class | — | Mixin | 0 | 49 | [StorageTerminalMenuMixin.java](cn/star/toms_storage_star_optimized/mixin/StorageTerminalMenuMixin.java) |
| `PlatformMultiInventoryAccessMixin` | class | — | Mixin | 8 | 228 | [PlatformMultiInventoryAccessMixin.java](cn/star/toms_storage_star_optimized/mixin/PlatformMultiInventoryAccessMixin.java) |
| `MultiInventoryAccessAccessor` | interface | — | Mixin | 0 | 14 | [MultiInventoryAccessAccessor.java](cn/star/toms_storage_star_optimized/mixin/MultiInventoryAccessAccessor.java) |
| `INetworkDirtyReceiver` | interface | — | — | 0 | 8 | [INetworkDirtyReceiver.java](cn/star/toms_storage_star_optimized/opt/INetworkDirtyReceiver.java) |
| `NetworkDirtyPropagation` | class | — | — | 2 | 87 | [NetworkDirtyPropagation.java](cn/star/toms_storage_star_optimized/opt/NetworkDirtyPropagation.java) |
| `TerminalDirtyState` | interface | — | — | 0 | 6 | [TerminalDirtyState.java](cn/star/toms_storage_star_optimized/api/TerminalDirtyState.java) |
