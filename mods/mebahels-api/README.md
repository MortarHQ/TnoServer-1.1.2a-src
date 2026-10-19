# Mebahel's API（`mebahels-api`）

> jar：`[前置]mebahels-api.jar` ｜ 版本：`1.0.20-fabric-1.21.1` ｜ 作者：?
> 规模：**18 个类 / 8 个包 / 1712 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 2

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.mebahelsapi.util` | 5 | `MovementUtil`、`ModNetworking`、`ChestOpenSyncClient` |
| `net.mebahelsapi.processor` | 3 | `WaterRemovalScheduler`、`MyProcessors`、`WaterRemovalProcessor` |
| `net.mebahelsapi.network` | 3 | `SyncedInventoryWrapper`、`UpdateChestPayload`、`ChestOpenPayload` |
| `net.mebahelsapi.screenhandler` | 2 | `ModChestScreenHandler`、`ModScreenHandlers` |
| `net.mebahelsapi` | 2 | `MebahelsAPIClient`、`MebahelsAPI` |
| `net.mebahelsapi.block.entity.base` | 1 | `BaseChestBlockEntity` |
| `net.mebahelsapi.block.block.custom.base` | 1 | `BaseChestBlock` |
| `net.mebahelsapi.screen` | 1 | `ModChestScreen` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BaseChestBlockEntity` | class | class_2595 | — | 34 | 461 | [BaseChestBlockEntity.java](net/mebahelsapi/block/entity/base/BaseChestBlockEntity.java) |
| `ModChestScreenHandler` | class | class_1703 | — | 7 | 134 | [ModChestScreenHandler.java](net/mebahelsapi/screenhandler/ModChestScreenHandler.java) |
| `ModScreenHandlers` | class | — | — | 1 | 19 | [ModScreenHandlers.java](net/mebahelsapi/screenhandler/ModScreenHandlers.java) |
| `MovementUtil` | class | — | — | 13 | 327 | [MovementUtil.java](net/mebahelsapi/util/MovementUtil.java) |
| `WaterRemovalScheduler` | class | — | — | 13 | 208 | [WaterRemovalScheduler.java](net/mebahelsapi/processor/WaterRemovalScheduler.java) |
| `BaseChestBlock` | class | class_2281 | — | 8 | 147 | [BaseChestBlock.java](net/mebahelsapi/block/block/custom/base/BaseChestBlock.java) |
| `ModNetworking` | class | — | — | 1 | 27 | [ModNetworking.java](net/mebahelsapi/util/ModNetworking.java) |
| `MebahelsAPIClient` | class | ClientModInitializer | Environment | 1 | 18 | [MebahelsAPIClient.java](net/mebahelsapi/MebahelsAPIClient.java) |
| `SyncedInventoryWrapper` | class | class_1263 | — | 10 | 75 | [SyncedInventoryWrapper.java](net/mebahelsapi/network/SyncedInventoryWrapper.java) |
| `ModChestScreen` | class | class_465 | Environment | 3 | 47 | [ModChestScreen.java](net/mebahelsapi/screen/ModChestScreen.java) |
| `ChestOpenSyncClient` | class | — | Environment | 1 | 38 | [ChestOpenSyncClient.java](net/mebahelsapi/util/ChestOpenSyncClient.java) |
| `LargeEntityPathNodeMaker` | class | class_14 | — | 1 | 35 | [LargeEntityPathNodeMaker.java](net/mebahelsapi/util/LargeEntityPathNodeMaker.java) |
| `MebahelsAPI` | class | ModInitializer | — | 2 | 54 | [MebahelsAPI.java](net/mebahelsapi/MebahelsAPI.java) |
| `UpdateChestPayload` | record | class_8710 | — | 1 | 21 | [UpdateChestPayload.java](net/mebahelsapi/network/UpdateChestPayload.java) |
| `ChestOpenPayload` | record | class_8710 | — | 1 | 21 | [ChestOpenPayload.java](net/mebahelsapi/network/ChestOpenPayload.java) |
| `MyProcessors` | class | — | — | 1 | 18 | [MyProcessors.java](net/mebahelsapi/processor/MyProcessors.java) |
| `WaterRemovalProcessor` | class | class_3491 | — | 2 | 48 | [WaterRemovalProcessor.java](net/mebahelsapi/processor/WaterRemovalProcessor.java) |
| `ChestOpenSync` | class | — | — | 1 | 14 | [ChestOpenSync.java](net/mebahelsapi/util/ChestOpenSync.java) |

## 全部类（18）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BaseChestBlockEntity` | class | class_2595 | — | 34 | 461 | [BaseChestBlockEntity.java](net/mebahelsapi/block/entity/base/BaseChestBlockEntity.java) |
| `ModChestScreenHandler` | class | class_1703 | — | 7 | 134 | [ModChestScreenHandler.java](net/mebahelsapi/screenhandler/ModChestScreenHandler.java) |
| `ModScreenHandlers` | class | — | — | 1 | 19 | [ModScreenHandlers.java](net/mebahelsapi/screenhandler/ModScreenHandlers.java) |
| `MovementUtil` | class | — | — | 13 | 327 | [MovementUtil.java](net/mebahelsapi/util/MovementUtil.java) |
| `WaterRemovalScheduler` | class | — | — | 13 | 208 | [WaterRemovalScheduler.java](net/mebahelsapi/processor/WaterRemovalScheduler.java) |
| `BaseChestBlock` | class | class_2281 | — | 8 | 147 | [BaseChestBlock.java](net/mebahelsapi/block/block/custom/base/BaseChestBlock.java) |
| `ModNetworking` | class | — | — | 1 | 27 | [ModNetworking.java](net/mebahelsapi/util/ModNetworking.java) |
| `MebahelsAPIClient` | class | ClientModInitializer | Environment | 1 | 18 | [MebahelsAPIClient.java](net/mebahelsapi/MebahelsAPIClient.java) |
| `SyncedInventoryWrapper` | class | class_1263 | — | 10 | 75 | [SyncedInventoryWrapper.java](net/mebahelsapi/network/SyncedInventoryWrapper.java) |
| `ModChestScreen` | class | class_465 | Environment | 3 | 47 | [ModChestScreen.java](net/mebahelsapi/screen/ModChestScreen.java) |
| `ChestOpenSyncClient` | class | — | Environment | 1 | 38 | [ChestOpenSyncClient.java](net/mebahelsapi/util/ChestOpenSyncClient.java) |
| `LargeEntityPathNodeMaker` | class | class_14 | — | 1 | 35 | [LargeEntityPathNodeMaker.java](net/mebahelsapi/util/LargeEntityPathNodeMaker.java) |
| `MebahelsAPI` | class | ModInitializer | — | 2 | 54 | [MebahelsAPI.java](net/mebahelsapi/MebahelsAPI.java) |
| `UpdateChestPayload` | record | class_8710 | — | 1 | 21 | [UpdateChestPayload.java](net/mebahelsapi/network/UpdateChestPayload.java) |
| `ChestOpenPayload` | record | class_8710 | — | 1 | 21 | [ChestOpenPayload.java](net/mebahelsapi/network/ChestOpenPayload.java) |
| `MyProcessors` | class | — | — | 1 | 18 | [MyProcessors.java](net/mebahelsapi/processor/MyProcessors.java) |
| `WaterRemovalProcessor` | class | class_3491 | — | 2 | 48 | [WaterRemovalProcessor.java](net/mebahelsapi/processor/WaterRemovalProcessor.java) |
| `ChestOpenSync` | class | — | — | 1 | 14 | [ChestOpenSync.java](net/mebahelsapi/util/ChestOpenSync.java) |
