---
页面: configs/kaleidoscope_cookery
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `kaleidoscope_cookery` 配置项

> **2 个文件 / 10 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/kaleidoscope_cookery](../../mods/kaleidoscope_cookery/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `kaleidoscope_cookery-common.toml` | `cookery.SatiatedShieldAbsorbEnabled` | `True` | bool | Whether enabling the Satiated Shield effect. | `kaleidoscope_cookery:GeneralConfig` |
| `kaleidoscope_cookery-common.toml` | `cookery.SatiatedShieldAbsorbExcessDamage` | `True` | bool | Whether the Satiated Shield effect should absorb excess dama | `kaleidoscope_cookery:GeneralConfig` |
| `kaleidoscope_cookery-common.toml` | `cookery.IS_SATIATED_SHIELD_DISABLE_WHEN_HUNGRY_EFFECT` | `True` | bool | If true, the Satiated Shield effect will not apply while the | — |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_MIN_FOOD_LEVEL` | `4` | int | Range: 1 ~ 20 | `kaleidoscope_cookery:GeneralConfig`、`kaleidoscope_cookery:SatiatedShieldEvent` |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_ADDITIONAL_EXHAUSTION_PER_DAMAGE` | `2.0` | float | Range: 0.0 ~ 40.0 | — |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_DAMAGE_REDUCTION_PERCENT` | `1.0` | float | Range: 0.0 ~ 1.0 | `kaleidoscope_cookery:GeneralConfig`、`kaleidoscope_cookery:SatiatedShieldEvent` |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_MAX_DAMAGE_REDUCTION` | `64.0` | float | Range: 0.0 ~ 2.147483647E9 | `kaleidoscope_cookery:GeneralConfig`、`kaleidoscope_cookery:SatiatedShieldEvent` |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_MIN_DAMAGE` | `0.0` | float | Range: 0.0 ~ 2.147483647E9 | `kaleidoscope_cookery:GeneralConfig`、`kaleidoscope_cookery:SatiatedShieldEvent` |
| `kaleidoscope_cookery-common.toml` | `cookery.SATIATED_SHIELD_WEAKNESS_DAMAGE_MULTIPLIER` | `2.0` | float | Range: 1.0 ~ 2.147483647E9 | — |
| `kaleidoscope_cookery-client.toml` | `cookery.ShowFoodEffectTooltips` | `True` | bool | Whether to show food effect tooltips when hovering over food | `kaleidoscope_cookery:ClientConfig` |
