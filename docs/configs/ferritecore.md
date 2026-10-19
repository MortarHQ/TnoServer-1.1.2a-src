---
页面: configs/ferritecore
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `ferritecore` 配置项

> **1 个文件 / 12 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/ferritecore](../../mods/ferritecore/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `ferritecore-mixin.toml` | `compactFastMap` | `False` | bool | Use a slightly more compact, but also slightly slower repres | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `useSmallThreadingDetector` | `False` | bool | Replace objects used to detect multi-threaded access to chun | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `cacheMultipartPredicates` | `True` | bool | Cache the predicate instances used in multipart models | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `multipartDeduplication` | `True` | bool | Do not create a new MultipartBakedModel instance for each bl | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `blockstateCacheDeduplication` | `True` | bool | Deduplicate cached data for blockstates, most importantly co | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `modelResourceLocations` | `True` | bool | Avoid creation of new strings when creating ModelResourceLoc | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `modelSides` | `True` | bool | Use smaller data structures for "simple" models, especially  | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `replaceNeighborLookup` | `True` | bool | Replace the blockstate neighbor table | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `populateNeighborTable` | `False` | bool | Populate the neighbor table used by vanilla. Enabling this s | `ferritecore:CrashNeighborTable`、`ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `replacePropertyMap` | `True` | bool | Do not store the properties of a state explicitly and read t | `ferritecore:FastMapStateHolder`、`ferritecore:FastMapStateHolderMixin`、`ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `bakedQuadDeduplication` | `True` | bool | Deduplicate vertex data of baked quads in the basic model im | `ferritecore:FerriteConfig` |
| `ferritecore-mixin.toml` | `dataComponentPatch` | `True` | bool | Save memory overhead from empty data component maps/patched | `ferritecore:FerriteConfig`、`jei:FullTypedItemStack`、`jei:NormalizedTypedItemStack` |
