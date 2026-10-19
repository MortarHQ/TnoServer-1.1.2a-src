---
页面: configs/spears
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `spears` 配置项

> **1 个文件 / 7 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/spears](../../mods/spears/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `spears-common.toml` | `lunge_hunger_cutoff` | `True` | bool | Stops lunge from working if the player is below 3 hunger poi | `spears:PiercingWeapon`、`spears:SpearsConfig` |
| `spears-common.toml` | `spear_charge_attacks` | `True` | bool | Whether spears have a charge attack | `spears:Spears`、`spears:SpearsConfig` |
| `spears-common.toml` | `spear_stabbing_animation` | `True` | bool | Whether spears have a stabbing animation | `spears:Spears`、`spears:SpearsConfig` |
| `spears-common.toml` | `tridents_use_spear_attack_animation` | `False` | bool | Whether tridents attack like spears | `spears:SpearsConfig`、`spears:SpearsNeoForge` |
| `spears-common.toml` | `can_use_lunge_while_gliding` | `False` | bool | Allows lunging while using an Elytra | `spears:PiercingWeapon`、`spears:SpearsConfig` |
| `spears-common.toml` | `can_use_lunge_while_in_water` | `False` | bool | Allows lunging while underwater | `spears:PiercingWeapon`、`spears:SpearsConfig` |
| `spears-common.toml` | `attribute_swap_bypasses_minimum_attack_charge` | `True` | bool | Enables attribute swapping on spears for lunge swapping | — |
