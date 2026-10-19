---
页面: configs/morerelics
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `morerelics` 配置项

> **2 个文件 / 4 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/morerelics](../../mods/morerelics/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `morerelics-common.toml` | `VertebraX.excludedEffects` | `["minecraft:levitation"]` | list | List of effects which can't be filtered/reduced by vertebrax | `morerelics:Config` |
| `morerelics-common.toml` | `VertebraX.oppositeEffectMappings` | `["minecraft:weakness/minecraft:strength"` | list | You can include modded effects here but first effect should  | `morerelics:Config` |
| `morerelics-client.toml` | `Weaver's Spool.silkBarCorner` | `BOTTOM_RIGHT` | str | Allowed Values: TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIG | `morerelics:Config` |
| `morerelics-client.toml` | `Cyberpsychosis.cyberpsychosisDisguiseEffect` | `True` | bool | Disguises some passive and neutral mobs as hostile entities  | `morerelics:Config` |
