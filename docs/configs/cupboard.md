---
页面: configs/cupboard
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `cupboard` 配置项

> **1 个文件 / 10 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/cupboard](../../mods/cupboard/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `cupboard.json` | `showCommandExecutionErrors.desc:` | `Whether to display errors during command` | str |  | — |
| `cupboard.json` | `showCommandExecutionErrors.showCommandExecutionErrors` | `True` | bool |  | `cupboard:CommandExceptionLoggingMixin`、`cupboard:CommonConfiguration` |
| `cupboard.json` | `debugChunkloadAttempts.desc:` | `Enables debug logging of chunks being fo` | str |  | — |
| `cupboard.json` | `debugChunkloadAttempts.debugChunkloadAttempts` | `False` | bool |  | `cupboard:ChunkLoadDebug`、`cupboard:CommonConfiguration` |
| `cupboard.json` | `skipErrorOnEntityLoad.desc:` | `Prevent crashes on entity loading: defau` | str |  | — |
| `cupboard.json` | `skipErrorOnEntityLoad.skipErrorOnEntityLoad` | `False` | bool |  | `cupboard:CommonConfiguration`、`cupboard:EntityLoadMixin` |
| `cupboard.json` | `logOffthreadEntityAdd.desc:` | `Entities should only be added on the ser` | str |  | — |
| `cupboard.json` | `logOffthreadEntityAdd.logOffthreadEntityAdd` | `True` | bool |  | `cupboard:CommonConfiguration`、`cupboard:ServerAddEntityMixin` |
| `cupboard.json` | `forceHeapDumpOnOOM.desc:` | `Enables creating a heap dump automatical` | str |  | — |
| `cupboard.json` | `forceHeapDumpOnOOM.forceHeapDumpOnOOM` | `False` | bool |  | `cupboard:CommonConfiguration`、`cupboard:Cupboard` |
