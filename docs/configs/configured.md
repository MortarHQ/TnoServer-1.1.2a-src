---
页面: configs/configured
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `configured` 配置项

> **2 个文件 / 6 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/configured](../../mods/configured/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `configured-client.properties` | `forceConfiguredMenu` | `false` | string |  | `configured:Config` |
| `configured-client.properties` | `includeFoldersInSearch` | `false` | string |  | `configured:Config` |
| `configured-client.properties` | `changedFormatting` | `ITALIC` | string |  | `configured:Config` |
| `configured-developer.properties` | `developerMode` | `false` | string |  | `configured:Config`、`netherexp:JNEConfigImpl`、`ponderer:Config` |
| `configured-developer.properties` | `broadcastLogs` | `true` | string |  | `configured:Config` |
| `configured-developer.properties` | `developers` | `` | string |  | `collective:RegisterMod`、`configured:Config` |
