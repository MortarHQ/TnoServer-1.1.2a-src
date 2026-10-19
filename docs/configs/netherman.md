---
页面: configs/netherman
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `netherman` 配置项

> **1 个文件 / 59 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/netherman](../../mods/netherman/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `netherman-common.toml` | `Azazel Boss Configuration.maxHealth` | `800.0` | float | Range: 100.0 ~ 10000.0 | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `netherman-common.toml` | `Azazel Boss Configuration.movementSpeed` | `0.2` | float | Range: 0.05 ~ 1.0 | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `netherman-common.toml` | `Azazel Boss Configuration.knockbackResistance` | `1.0` | float | Range: 0.0 ~ 1.0 | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `netherman-common.toml` | `Azazel Attack Damage.launchAttackDamage` | `8.0` | float | Range: 0.0 ~ 100.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Attack Damage.pullAttackDamage` | `5.0` | float | Range: 0.0 ~ 100.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Attack Damage.windAttackDamage` | `5.0` | float | Range: 0.0 ~ 100.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Attack Damage.wheelAttackDamage` | `4.0` | float | Range: 0.0 ~ 100.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Attack Frequencies.attackChance` | `80` | int | Range: 10 ~ 600 | `netherman:AzazelConfig`、`netherman:AzazelHumanEntity`、`twilightforest:BreathAttackGoal` |
| `netherman-common.toml` | `Azazel Attack Frequencies.passiveSummonChance` | `600` | int | Range: 100 ~ 2400 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.playerDetectionRadius` | `20.0` | float | Range: 1.0 ~ 128.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.miniBossCooldown` | `18000` | int | Range: 1200 ~ 1000000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.civilianNpcCooldown` | `72000` | int | Range: 1200 ~ 1000000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.believersSpawnCount` | `5` | int | Range: 1 ~ 20 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.believersSpawnRadius` | `6.0` | float | Range: 1.0 ~ 32.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.believersMaxNearby` | `5` | int | Range: 1 ~ 50 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.believersSuccessCooldown` | `72000` | int | Range: 1200 ~ 1000000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Nether Spawner Configuration.believersFailCooldown` | `600` | int | Range: 20 ~ 72000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Trophy Mask Configuration.maskRegenCooldown` | `2400` | int | Range: 200 ~ 72000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Trophy Mask Configuration.maskFireImmunity` | `True` | bool | Does wearing the Azazel Trophy mask grant fire immunity? | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Defense & Melee.meleeAttackRadius` | `6.0` | float | Range: 1.0 ~ 20.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Defense & Melee.meleeAttackChance` | `30` | int | Range: 0 ~ 100 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Defense & Melee.shieldHitsMin` | `5` | int | Range: 1 ~ 100 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Defense & Melee.shieldHitsMax` | `20` | int | Range: 1 ~ 100 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Midas Attack.midasBossUnitCount` | `3` | int | Range: 0 ~ 10 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Midas Attack.midasGuardianCount` | `2` | int | Range: 0 ~ 10 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Midas Attack.midasFireDamage` | `8.0` | float | Range: 0.0 ~ 100.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Midas Attack.midasGoldTime` | `40` | int | Range: 1 ~ 2000 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Prison Attack.prisonRadius` | `5` | int | Range: 2 ~ 15 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Prison Attack.prisonDuration` | `200` | int | Range: 60 ~ 1200 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Prison Attack.prisonMaxHeight` | `6` | int | Range: 3 ~ 15 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Armor Configuration.azazelArmorBlockBreaking` | `True` | bool | Allows the Azazel Chestplate to break blocks when flying int | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Configuration.humanMaxHealth` | `1000.0` | float | Range: 100.0 ~ 10000.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Configuration.humanMovementSpeed` | `0.25` | float | Range: 0.05 ~ 1.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Configuration.humanKnockbackResistance` | `1.0` | float | Range: 0.0 ~ 1.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanStompDamage` | `25.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanStompKnockback` | `1.2` | float | Range: 0.0 ~ 10.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanScythe1Damage` | `15.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanScythe1Knockback` | `0.5` | float | Range: 0.0 ~ 10.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanScythe2Damage` | `25.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanScythe2Knockback` | `6.0` | float | Range: 0.0 ~ 20.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMeleeDamage` | `20.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMeleeKnockback` | `8.0` | float | Range: 0.0 ~ 20.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMidPushDamage` | `10.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMidPushKnockback` | `8.0` | float | Range: 0.0 ~ 20.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMidPullDamage` | `20.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpearMidPullKnockback` | `5.0` | float | Range: 0.0 ~ 20.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanChargeDamage` | `30.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanChargeKnockback` | `1.5` | float | Range: 0.0 ~ 10.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSplashDamage` | `20.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpikeDamage` | `10.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanSpikeWitherDuration` | `100` | int | Range: 0 ~ 1200 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanMeleeAttackRadius` | `2.0` | float | Range: 1.0 ~ 50.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanMidAttackRadiusMax` | `8.0` | float | Range: 1.0 ~ 50.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanLongAttackRadiusMin` | `6.0` | float | Range: 1.0 ~ 50.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanMovementStopRadius` | `8.0` | float | Range: 1.0 ~ 50.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanProjectileSpikeDamage` | `10.0` | float | Range: 0.0 ~ 200.0 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `Azazel Human Attack Config.humanProjectileSpikeWitherDuration` | `100` | int | Range: 0 ~ 1200 | `netherman:AzazelConfig` |
| `netherman-common.toml` | `NPC Doctor Config.doctorVanillaPotionsOnly` | `False` | bool | true = the Doctor will only give potions with effects from v | `netherman:AzazelConfig` |
| `netherman-common.toml` | `NPC Doctor Config.doctorAllowedModNamespaces` | `["minecraft"]` | list | A list of mod IDs (namespaces) allowed to be given as potion | `netherman:AzazelConfig` |
