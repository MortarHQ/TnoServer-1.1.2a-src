---
页面: configs/byepregen
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `byepregen` 配置项

> **1 个文件 / 7 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/byepregen](../../mods/byepregen/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `byepregen.json` | `enablePlacedFeatureMixin` | `False` | bool |  | `byepregen:Config`、`byepregen:PlacedFeatureMixin` |
| `byepregen.json` | `enableFastTickChunks` | `False` | bool |  | `byepregen:Config`、`byepregen:FastTickChunksMixinAdjuster`、`byepregen:ServerChunkCacheTickChunksMixin` |
| `byepregen.json` | `enableArenaPalette` | `True` | bool |  | `byepregen:ChunkAccessArenaMixin`、`byepregen:ChunkSerializerArenaReadMixin`、`byepregen:Config` |
| `byepregen.json` | `enableServerRuntimeArenaPalette` | `False` | bool |  | `byepregen:ChunkAccessArenaMixin`、`byepregen:ChunkSerializerArenaReadMixin`、`byepregen:Config` |
| `byepregen.json` | `enableClientArenaPalette` | `False` | bool |  | `byepregen:ChunkAccessArenaMixin`、`byepregen:Config`、`byepregen:MixinPlugin` |
| `byepregen.json` | `enableGcFreeWorldgenSave` | `True` | bool |  | `byepregen:Config`、`byepregen:GcFreeCompat`、`byepregen:MixinPlugin` |
| `byepregen.json` | `enableYALightEngine` | `False` | bool |  | `byepregen:Byepregen`、`byepregen:Config`、`byepregen:MixinPlugin` |
