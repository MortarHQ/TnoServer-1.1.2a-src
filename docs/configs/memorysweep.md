---
页面: configs/memorysweep
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `memorysweep` 配置项

> **1 个文件 / 8 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/memorysweep](../../mods/memorysweep/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `memorysweep.json` | `_comment1` | `Whether to enable MemorySweep.` | str |  | `memorysweep:ModConfig` |
| `memorysweep.json` | `memorySweep` | `True` | bool |  | `memorysweep:ClothConfig`、`memorysweep:MemorySweep`、`memorysweep:ModConfig` |
| `memorysweep.json` | `_comment2` | `The interval time of memory cleaning. Un` | str |  | `memorysweep:ModConfig` |
| `memorysweep.json` | `sweepInterval` | `900` | int |  | `memorysweep:ClothConfig`、`memorysweep:ModConfig`、`memorysweep:SweepUtil` |
| `memorysweep.json` | `_comment3` | `The minimum memory usage percentage (cal` | str |  | `memorysweep:ModConfig` |
| `memorysweep.json` | `minMemoryUsage` | `75` | int |  | `memorysweep:ClothConfig`、`memorysweep:ModConfig`、`memorysweep:SweepUtil` |
| `memorysweep.json` | `_comment4` | `Enable silent memory cleaning; no UI mes` | str |  | `memorysweep:ModConfig` |
| `memorysweep.json` | `silent` | `False` | bool |  | `chunky:BossBarTaskUpdateListener`、`chunky:Chunky`、`chunky:ChunkyNeoForge` |
