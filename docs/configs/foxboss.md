---
页面: configs/foxboss
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `foxboss` 配置项

> **1 个文件 / 8 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/foxboss](../../mods/foxboss/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `foxboss-common.toml` | `milim.burstRange` | `48.0` | float | Range: 4.0 ~ 96.0 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.volleyIntervalTicks` | `20` | int | Range: 10 ~ 60 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.dragonStarDamage` | `300.0` | float | Range: 0.0 ~ 1000000.0 | `foxboss:DragonStarEntity`、`foxboss:FoxBossConfig`、`foxboss:MilimSatanaelBonuses` |
| `foxboss-common.toml` | `milim.burstCooldownMinTicks` | `3600` | int | Range: 20 ~ 72000 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.burstCooldownMaxTicks` | `6000` | int | Range: 20 ~ 72000 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.satanaelCombatWrathPerSecond` | `4.0` | float | Range: 0.0 ~ 100.0 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.satanaelInjuredWrathPerSecond` | `6.0` | float | Range: 0.0 ~ 100.0 | `foxboss:FoxBossConfig` |
| `foxboss-common.toml` | `milim.naturalSpawnReference` | `HINATA` | str | Allowed Values: HINATA, SHIZU | `foxboss:FoxBossConfig` |
