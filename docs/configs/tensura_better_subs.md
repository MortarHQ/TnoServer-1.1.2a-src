---
页面: configs/tensura_better_subs
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `tensura_better_subs` 配置项

> **1 个文件 / 20 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/tensura_better_subs](../../mods/tensura_better_subs/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `tensura_better_subs.toml` | `spiderEyesEatenForPoison` | `25` | int | How many spider eyes should be eaten to acquire Poison (2nd  | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `killsForStrength` | `100` | int | How many entities should be killed to acquire Strength (1st  | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `spiderKillsForPoison` | `50` | int | How many black spiders should be killed for Poison (1st requ | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `subordinatePlayerSkillManipulation` | `False` | bool | Should owners be able to manipulate subordinate player skill | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:RequestSubordinateAbilityModeChangePacket` |
| `tensura_better_subs.toml` | `damageTakenForStrengthenBody` | `5000.0` | float | How much damage needs to be taken to acquire Strengthen Body | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `slimeKillsForRegen` | `500` | int | How many tensura:slime should be killed to acquire Self Rege | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `centipedeKillsForParalysis` | `50` | int | How many centipedes should be killed to acquire Paralysis | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `subordinatePlayerSkillMenu` | `False` | bool | Should owners be able to see subordinate player skills | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:MixinThoughtCommunicationSkill`、`tensura_better_subs:RequestSubordinateAbilityModeChangePacket` |
| `tensura_better_subs.toml` | `epForStrengthenBody` | `20000.0` | float | How much EP required to acquire Strengthen Body | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `epForWaterCurrentControl` | `7000.0` | float | How much EP required to acquire Water Current Control | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `epForInfiniteRegeneration` | `1000000.0` | float | How much EP required to acquire Infinite Regeneration (addit | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `killsForSteelStrength` | `1000` | int | How may entities should be killed to acquire Steel Strength  | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `epForSteelStrength` | `20000.0` | float | How much EP required to acquire Steel Strength (2nd requirem | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `epForStrength` | `4000.0` | float | How much EP required to acquire Strength (2nd requirement) | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `epForHaki` | `100000.0` | float | How much EP required to acquire Haki (Entities getting Haki  | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:ExpandedSkillStorage` |
| `tensura_better_subs.toml` | `SkillGranting.skillGrantCooldown` | `1200` | int | Ability granting cooldown | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:SubordinateSkillGrantingPacket` |
| `tensura_better_subs.toml` | `SkillGranting.ownerCostMultiplier` | `10.0` | float | Multiplier of the acquisition cost subtracted from owner's c | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:SubordinateSkillGrantingPacket` |
| `tensura_better_subs.toml` | `SkillGranting.defaultAbilityGrantCost` | `5000.0` | float | Default cost for abilities without a cost. Granting abilitie | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:SkillGranting` |
| `tensura_better_subs.toml` | `SkillGranting.allowNonSkillGrants` | `False` | bool | Allow the granting of abilities without a default acquisitio | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:SkillGranting` |
| `tensura_better_subs.toml` | `SkillGranting.skillGrantCostMultiplier` | `1.0` | float | Overall acquisition cost multiplier | `tensura_better_subs:ExpandedSkillConfig`、`tensura_better_subs:SkillGranting` |
