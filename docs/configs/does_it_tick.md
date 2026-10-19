---
页面: configs/does_it_tick
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `does_it_tick` 配置项

> **1 个文件 / 17 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/does_it_tick](../../mods/does_it_tick/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `does_it_tick-common.toml` | `Living Entities Tick Settings.OptimizeEntitiesTicking` | `True` | bool | If you disable this, entities will not stop ticking when the | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.LivingEntitiesMaxHorizontalTickDistance` | `64` | int | Range: > 1 | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.LivingEntitiesMaxVerticalTickDistance` | `32` | int | Range: > 1 | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.EntitiesWhitelist` | `["minecraft:ender_dragon", "minecraft:gh` | list | If you don't want an entity to be affected by the optimizati | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.EntitiesModIDWhiteList` | `["create", "witherstormmod"]` | list | If you don't want entities of a mod to be affected by the op | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.TickRaidersInRaid` | `True` | bool | With this turned on, all the raider won't stop ticking in ra | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.RaidEntitiesWhiteList` | `["minecraft:witch", "minecraft:vex"]` | list | Similar with entity whitelist, but only take effect in raid. | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.RaidEntitiesModIDWhiteList` | `[]` | list | Similar with entity modID whitelist, but only take effect in | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.DimensionWhitelist` | `[]` | list | Entities in these dimensions will be affected by the optimiz | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.IgnoreDeadEntities` | `False` | bool | If this is enabled, tickable check will run a lot faster, bu | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.IgnoreHostileEntities` | `False` | bool | If this is enabled, this mod will only work on passive entit | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Living Entities Tick Settings.IgnorePassiveEntities` | `False` | bool | If this is enabled, this mod will only work on hostile entit | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Item Entities Tick Settings.OptimizeItemMovement` | `False` | bool | Slow down item entities' ticking speed by 1/4 | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Item Entities Tick Settings.ItemWhiteList` | `["minecraft:cobblestone"]` | list | Require 'OptimizeItemMovement' to be true | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Misc.DisableOnClient` | `True` | bool |  | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Misc.SendWarningMessageWhenPlayerLogIn` | `True` | bool |  | `does_it_tick:DoesItTick` |
| `does_it_tick-common.toml` | `Misc.DisableInActiveChunks` | `True` | bool | If you disable this, entities near player bases may be affec | `does_it_tick:DoesItTick` |
