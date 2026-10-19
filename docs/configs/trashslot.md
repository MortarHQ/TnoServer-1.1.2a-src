---
页面: configs/trashslot
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `trashslot` 配置项

> **1 个文件 / 5 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/trashslot](../../mods/trashslot/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `trashslot-common.toml` | `enableHints` | `True` | bool | TrashSlot will show a hint the first time the trash slot is  | `trashslot:TrashSlotConfigData`、`trashslot:TrashSlotGuiHandler` |
| `trashslot-common.toml` | `allowDeletionWhileTrashSlotIsInvisible` | `False` | bool | Not recommended, but this will allow you to use the keybinds | `trashslot:TrashSlotConfigData`、`trashslot:TrashSlotGuiHandler` |
| `trashslot-common.toml` | `deletionDenyList` | `[]` | list | List of items that cannot be deleted, in comma-separated for | `trashslot:TrashHelper`、`trashslot:TrashSlotConfigData` |
| `trashslot-common.toml` | `instantDeletion` | `False` | bool | This causes the deletion slot to delete items instantly, sim | `trashslot:TrashSlotConfig`、`trashslot:TrashSlotConfigData`、`trashslot:TrashSlotGuiHandler` |
| `trashslot-common.toml` | `enableDeleteKeysInCreative` | `True` | bool | Set to true if you want the delete keybindings to work in cr | `trashslot:TrashSlotConfigData`、`trashslot:TrashSlotGuiHandler` |
