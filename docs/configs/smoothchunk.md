---
页面: configs/smoothchunk
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `smoothchunk` 配置项

> **1 个文件 / 8 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/smoothchunk](../../mods/smoothchunk/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `smoothchunk.json` | `chunkSaveDelay.desc:` | `Delay before a chunk is saved to disk, d` | str |  | — |
| `smoothchunk.json` | `chunkSaveDelay.chunkSaveDelay` | `300` | int |  | `smoothchunk:ChunkMapMixin`、`smoothchunk:CommonConfiguration` |
| `smoothchunk.json` | `chunkUnloadLimit.desc:` | `Maximum amount of chunks unloaded per ti` | str |  | — |
| `smoothchunk.json` | `chunkUnloadLimit.chunkUnloadLimit` | `20` | int |  | `smoothchunk:ChunkMapSlowUnload`、`smoothchunk:CommonConfiguration` |
| `smoothchunk.json` | `disableProtoSave.desc:` | `Disables saving of protochunks(not fully` | str |  | — |
| `smoothchunk.json` | `disableProtoSave.disableProtoSave` | `True` | bool |  | `smoothchunk:ChunkMapMixin`、`smoothchunk:CommonConfiguration` |
| `smoothchunk.json` | `debugLogging.desc:` | `Enables debug logging of how many chunks` | str |  | — |
| `smoothchunk.json` | `debugLogging.debugLogging` | `False` | bool |  | `chunksending:CommonConfiguration`、`chunksending:PlayerChunkSenderMixin`、`smoothchunk:ChunkMapMixin` |
