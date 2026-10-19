---
页面: configs/jupiter
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `jupiter` 配置项

> **1 个文件 / 4 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/jupiter](../../mods/jupiter/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `jupiter.json` | `general.loadForgeConfigs` | `True` | bool |  | `jupiter:ConfigSpecLoader`、`jupiter:JupiterSettings` |
| `jupiter.json` | `general.loadClothConfigs` | `True` | bool |  | `jupiter:ClothConfigLoader`、`jupiter:JupiterSettings` |
| `jupiter.json` | `general.configButtonReplacement` | `UNAVAILABLE_ONLY` | str |  | `jupiter:JupiterNeoForgeClient`、`jupiter:JupiterSettings` |
| `jupiter.json` | `general.redirectAutoConfigScreen` | `False` | bool |  | `jupiter:AutoConfigMixin`、`jupiter:JupiterSettings` |
