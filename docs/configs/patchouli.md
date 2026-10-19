---
页面: configs/patchouli
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `patchouli` 配置项

> **1 个文件 / 7 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/patchouli](../../mods/patchouli/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `patchouli-client.toml` | `disableAdvancementLocking` | `False` | bool | Set this to true to disable advancement locking for ALL book | `patchouli:Book`、`patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfig` |
| `patchouli-client.toml` | `noAdvancementBooks` | `[]` | list | Granular list of Book ID's to disable advancement locking fo | `patchouli:Book`、`patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfig` |
| `patchouli-client.toml` | `testingMode` | `False` | bool | Enable testing mode. By default this doesn't do anything, bu | `patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfig`、`patchouli:PatchouliConfigAccess` |
| `patchouli-client.toml` | `inventoryButtonBook` | `` | str | Set this to the ID of a book to have it show up in players'  | `patchouli:MixinInventoryScreen`、`patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfigAccess` |
| `patchouli-client.toml` | `useShiftForQuickLookup` | `False` | bool | Set this to true to use Shift instead of Ctrl for the invent | `patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfigAccess`、`patchouli:StubPatchouliAPI` |
| `patchouli-client.toml` | `textOverflowMode` | `RESIZE` | str | Allowed Values: OVERFLOW, TRUNCATE, RESIZE | `patchouli:NeoForgePatchouliConfig` |
| `patchouli-client.toml` | `quickLookupTime` | `10` | int | How long in ticks the quick lookup key needs to be pressed b | `patchouli:NeoForgePatchouliConfig`、`patchouli:PatchouliConfigAccess`、`patchouli:StubPatchouliAPI` |
