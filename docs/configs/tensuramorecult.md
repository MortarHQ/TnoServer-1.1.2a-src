---
页面: configs/tensuramorecult
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tensuramorecult` 配置项

> **1 个文件 / 49 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tensuramorecult](../../mods/tensuramorecult/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tensuramorecult-server.toml` | `bosses.azazelHealth` | `5000.0` | float | Range: 1.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.humanHealth` | `10000.0` | float | Range: 1.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.azazelStatsMultiplier` | `3.0` | float | Range: 0.1 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.humanStatsMultiplier` | `5.0` | float | Range: 0.1 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.azazelDamageMultiplier` | `2.0` | float | Range: 0.1 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.humanDamageMultiplier` | `3.0` | float | Range: 0.1 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.azazelEP` | `1000000.0` | float | Range: 0.0 ~ 1.0E8 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.humanEP` | `2000000.0` | float | Range: 0.0 ~ 1.0E8 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.azazelSpiritualHealth` | `10800.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.humanSpiritualHealth` | `18000.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.azazelGreed` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.humanGreed` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.azazelPride` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.humanPride` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.azazelResistances` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.humanResistances` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.toggleBossSkills` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.greedSkillId` | `tensura:greed` | str |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.prideSkillId` | `tensura:pride` | str |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `bosses.skills.resistanceSkillIds` | `tensura:abnormal_condition_resistance,te` | str |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `mobs.healthScale` | `1.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `mobs.damageScale` | `1.0` | float | Range: 0.0 ~ 100.0 | `block_factorys_bosses:AbstractBossEntity`、`block_factorys_bosses:AbstractStateBossEntity`、`block_factorys_bosses:InfernalDragonEntity` |
| `tensuramorecult-server.toml` | `mobs.epScale` | `1.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig`、`tensuramoreskills:ExterminationBladeItem` |
| `tensuramorecult-server.toml` | `mobs.spiritualHealthScale` | `1.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.matchHihiirokaneAttack` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.matchHihiirokaneDurability` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.weaponAttackFloor` | `80.0` | float | Range: 0.0 ~ 10000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.helmetArmor` | `8.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.chestArmor` | `15.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.legsArmor` | `12.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.bootsArmor` | `8.0` | float | Range: 0.0 ~ 100.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorToughness` | `6.0` | float | Range: 0.0 ~ 100.0 | `jei:IngredientSorterComparators`、`tensuramorecult:CultConfig`、`tensuramoreskills:BerettaEntity` |
| `tensuramorecult-server.toml` | `gear.armorKnockbackResistance` | `0.5` | float | Range: 0.0 ~ 1.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorAuraPerPiece` | `750.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorMagiculePerPiece` | `750.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorSpiritualHealthPerPiece` | `50.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorFlameResistancePerPiece` | `0.05` | float | Range: 0.0 ~ 1.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorDarknessResistancePerPiece` | `0.05` | float | Range: 0.0 ~ 1.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.weaponAura` | `1000.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.weaponMagicule` | `1000.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.shieldAura` | `1000.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.shieldMagicule` | `1000.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.shieldSpiritualHealth` | `75.0` | float | Range: 0.0 ~ 1000000.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.shieldMagicResistance` | `0.1` | float | Range: 0.0 ~ 1.0 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.weaponItemEP` | `5000.0` | float | Range: 0.0 ~ 1.0E8 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.armorItemEP` | `5000.0` | float | Range: 0.0 ~ 1.0E8 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `gear.shieldItemEP` | `5000.0` | float | Range: 0.0 ~ 1.0E8 | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `mechanics.tensuraDebuffs` | `True` | bool |  | `tensuramorecult:CultConfig` |
| `tensuramorecult-server.toml` | `mechanics.believerInfiniteRegeneration` | `True` | bool |  | `tensuramorecult:CultConfig` |
