---
页面: configs/coroutil
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `coroutil` 配置项

> **1 个文件 / 3 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/coroutil](../../mods/coroutil/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `CoroUtil/General.toml` | `general.useLoggingLog` | `True` | bool | logging | `coroutil:CULog`、`coroutil:ConfigCoroUtil` |
| `CoroUtil/General.toml` | `general.useLoggingDebug` | `False` | bool | - | `coroutil:CULog`、`coroutil:ConfigCoroUtil` |
| `CoroUtil/General.toml` | `general.useLoggingError` | `True` | bool | - | `coroutil:CULog`、`coroutil:ConfigCoroUtil`、`coroutil:CoroUtilPath` |
