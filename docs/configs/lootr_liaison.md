---
页面: configs/lootr_liaison
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `lootr_liaison` 配置项

> **1 个文件 / 7 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/lootr_liaison](../../mods/lootr_liaison/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `lootr_liaison-common.toml` | `general.finalization_delay_ticks` | `20` | int | Range: 0 ~ 400 | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `general.max_finalizations_per_tick` | `8` | int | Range: 1 ~ 64 | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `mimics.enable_artifacts_mimic_compat` | `True` | bool | Default: true. | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `mimics.mimic_roll_timing` | `finalization` | str | Default: finalization. | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `mimics.exclude_container_types` | `["minecraft:barrel"]` | list | Example: minecraft:barrel. Default: minecraft:barrel only. | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `mimics.respect_artifacts_config` | `True` | bool | Default: true. | `lootr_liaison:Config` |
| `lootr_liaison-common.toml` | `mimics.max_mimic_checks_per_tick` | `8` | int | Range: 1 ~ 64 | `lootr_liaison:Config` |
