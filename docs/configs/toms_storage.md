---
页面: configs/toms_storage
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `toms_storage` 配置项

> **2 个文件 / 16 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/toms_storage](../../mods/toms_storage/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `toms_storage-common.toml` | `importantInfo` | `True` | bool | This may appear confusing to many of you, but it is a new se | `toms_storage:Config` |
| `toms_storage-common.toml` | `blockedMods` | `[]` | list | List of mod ids whose blocks is ignored by the inventory con | `toms_storage:Config` |
| `toms_storage-common.toml` | `blockedBlocks` | `[]` | list | List of block ids ignored by the inventory connector | `toms_storage:Config` |
| `toms_storage-server.toml` | `inventoryConnectorRange` | `16` | int | Range: 4 ~ 256 | `toms_storage:Config` |
| `toms_storage-server.toml` | `onlyTrimsConnect` | `False` | bool | Only Allow Trims to Connect Inventories | `toms_storage:Config` |
| `toms_storage-server.toml` | `invCableConnectorMaxScanSize` | `2048` | int | Range: > 16 | `toms_storage:Config` |
| `toms_storage-server.toml` | `wirelessReach` | `16` | int | Range: 4 ~ 64 | `toms_storage:Config` |
| `toms_storage-server.toml` | `advWirelessRange` | `64` | int | Range: 16 ~ 512 | `toms_storage:AdvWirelessTerminalItem`、`toms_storage:Config` |
| `toms_storage-server.toml` | `wirelessTermBeaconLvl` | `1` | int | Range: -1 ~ 4 | `toms_storage:AbstractStorageTerminalScreen`、`toms_storage:AdvWirelessTerminalItem`、`toms_storage:Config` |
| `toms_storage-server.toml` | `wirelessTermBeaconLvlDim` | `4` | int | Range: -1 ~ 4 | `toms_storage:Config` |
| `toms_storage-server.toml` | `invLinkBeaconLvl` | `0` | int | Range: -1 ~ 4 | `toms_storage:Config`、`toms_storage:InventoryCableConnectorBlock`、`toms_storage:InventoryCableConnectorBlockEntity` |
| `toms_storage-server.toml` | `invLinkBeaconRange` | `4096` | int | Range: > 0 | `toms_storage:Config`、`toms_storage:InventoryCableConnectorBlock`、`toms_storage:InventoryCableConnectorBlockEntity` |
| `toms_storage-server.toml` | `invLinkBeaconLvlSameDim` | `1` | int | Range: -1 ~ 4 | `toms_storage:Config`、`toms_storage:InventoryCableConnectorBlock`、`toms_storage:InventoryCableConnectorBlockEntity` |
| `toms_storage-server.toml` | `invLinkBeaconLvlCrossDim` | `2` | int | Range: -1 ~ 4 | `toms_storage:Config`、`toms_storage:InventoryCableConnectorBlock`、`toms_storage:InventoryCableConnectorBlockEntity` |
| `toms_storage-server.toml` | `runMultithreaded` | `True` | bool | Use multithreading to improve performance | `toms_storage:Config`、`toms_storage:MultiInventoryAccess`、`toms_storage:StorageTerminalBlockEntity` |
| `toms_storage-server.toml` | `basicHopperCooldown` | `10` | int | Range: 1 ~ 200 | `toms_storage:BasicInventoryHopperBlockEntity`、`toms_storage:Config` |
