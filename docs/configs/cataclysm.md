---
页面: configs/cataclysm
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `cataclysm` 配置项

> **3 个文件 / 483 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/cataclysm](../../mods/cataclysm/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `cataclysm.toml` | `Etc.shadersCompat` | `False` | bool | Off the Custom Rendering | `cataclysm:AfterImageParticle`、`cataclysm:CMClientConfig`、`cataclysm:Cindaria_Layer` |
| `cataclysm.toml` | `Etc.ScreenShake(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `Etc.setThirdPerson(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `Etc.setFirstPerson(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `bosses Common settings.custombossbar(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `bosses Common settings.ReturnHome` | `20` | int | Range: 0 ~ 200 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:IABoss_monster` |
| `cataclysm.toml` | `bosses Music.BossMusic(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `bosses Music.BossMusicVolume` | `1` | int | Range: 1 ~ 1000000 | `cataclysm:BossMusicSound`、`cataclysm:CMClientConfig`、`cataclysm:ClientConfig` |
| `cataclysm.toml` | `bosses Music.IgnisSeparatePhaseMusic(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `bosses Music.EnderGuardianSeparatePhaseMusic(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `bosses Music.LeviathanSeparatePhaseMusic(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `Weapon.Armor Infinity Durability(on/off)` | `True` | bool |  | — |
| `cataclysm.toml` | `Weapon.BulwarkOfTheFlameCooldown` | `80` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.GauntletOfBulwarkCooldown` | `80` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.InfernalForgeCooldown` | `80` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.VoidForgeCooldown` | `120` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.TheIncineratorCooldown` | `400` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.WASWMissileCooldown` | `40` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.WASW's WitherMissiledamage` | `16.0` | float |  | — |
| `cataclysm.toml` | `Weapon.WASWHowitzerCooldown` | `100` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.VASWCooldown` | `120` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.VoidCoreCooldown` | `160` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.Sandstormcooldown` | `300` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.SoulRenderCooldown` | `100` | int | Range: 0 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `Weapon.gauntletofMaelstromCooldown` | `180` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.immolatorCooldown` | `300` | int | Range: 0 ~ 1000000 | — |
| `cataclysm.toml` | `Weapon.Ceraunus 's Lightning Storm Damage` | `6.0` | float |  | — |
| `cataclysm.toml` | `Weapon.Ceraunus's Wave Damage'` | `6.0` | float |  | — |
| `cataclysm.toml` | `Weapon.Ceraunus Cooldown` | `150` | int |  | — |
| `cataclysm.toml` | `Weapon.Astrape's Lightning Spear Damage` | `11.0` | float |  | — |
| `cataclysm.toml` | `Weapon.Astrape's Area Damage'` | `2.0` | float |  | — |
| `cataclysm.toml` | `Weapon.Astrape Cooldown` | `80` | int |  | — |
| `cataclysm.toml` | `Block.Cursed Tombstone Summon cooldown Minute` | `1` | int |  | — |
| `cataclysm.toml` | `Entity damage.Voidrunedamage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.Ashenbreathdamage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.DeathLaserdamage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:The_Harbinger_Entity` |
| `cataclysm.toml` | `Entity damage.DeathLaserHpdamage` | `5.0` | float | Range: 0.0 ~ 100.0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:The_Harbinger_Entity` |
| `cataclysm.toml` | `Entity damage.Laserdamage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:The_Harbinger_Entity` |
| `cataclysm.toml` | `Entity damage.BlazingBonedamage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.LionfishSpikedamage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.WitherHowizterdamage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.DimensionalRiftdamage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.WitherHomingMissiledamage` | `3.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.AbyssBlastdamage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Entity damage.AbyssBlastHpdamage` | `0.1` | float | Range: 0.0 ~ 1.0 | — |
| `cataclysm.toml` | `Entity damage.AbyssOrbdamage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:The_Leviathan_Entity` |
| `cataclysm.toml` | `Entity damage.Lavabombradius` | `2` | int | Range: 1 ~ 7 | — |
| `cataclysm.toml` | `Entity damage.Amethyst Cluster Damage` | `12.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Sandstorm Damage` | `5.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Ancient Desert Stele Damage` | `18.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Phantom Arrow Damage` | `8.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Phantom Halberd Damage` | `12.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Cursed Sandstorm Damage` | `7.5` | float |  | — |
| `cataclysm.toml` | `Entity damage.Flame Jet Damage` | `7.0` | float |  | — |
| `cataclysm.toml` | `Entity damage.Flare Bomb Damage` | `7.0` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianDamageCap` | `22.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianDamageTime` | `30` | int | Range: 0 ~ 100 | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ender Guardian.EnderguardianBlockBreaking` | `True` | bool | Ender guardian's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's prevent attacks from far away Range` | `12.0` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's gravity Punch Hp Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's Teleport attack Hp Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's knockback Hp Damage` | `0.06` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's Uppercut Hp Damage` | `0.1` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's RocketPunch Hp Damage` | `0.1` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.Guardian's area attack Hp Damage` | `0.08` | float |  | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianBlockBreaking X` | `15` | int |  | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianBlockBreaking Y` | `2` | int |  | — |
| `cataclysm.toml` | `Ender Guardian.EnderGuardianBlockBreaking Z` | `15` | int |  | — |
| `cataclysm.toml` | `Netherite Monstrosity.LavabombMagazine` | `3` | int | Range: 1 ~ 1000000 | — |
| `cataclysm.toml` | `Netherite Monstrosity.Lavabombamount` | `3` | int | Range: 1 ~ 1000000 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Netherite_Monstrosity_Entity` |
| `cataclysm.toml` | `Netherite Monstrosity.LavaBombDuration` | `350` | int | Range: 1 ~ 10000 | — |
| `cataclysm.toml` | `Netherite Monstrosity.LavaBombRandomDuration` | `150` | int | Range: 1 ~ 10000 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityHealingMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityDamageCap` | `22.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Monstrosity.MonstrosityDamageTime` | `10` | int | Range: 0 ~ 100 | — |
| `cataclysm.toml` | `Netherite Monstrosity.NetheritemonstrosityBodyBloking` | `True` | bool | Monstrosity's bodyBlocking verdict | — |
| `cataclysm.toml` | `Netherite Monstrosity.Monstrosity's attack Hp Damage` | `0.08` | float |  | — |
| `cataclysm.toml` | `Netherite Monstrosity.Monstrosity's prevent attacks from far away Rang` | `18.0` | float |  | — |
| `cataclysm.toml` | `Netherite Monstrosity.monstrosityBlockBreaking` | `True` | bool | Monstrosity's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `Ender Golem.EndergolemBlockBreaking` | `False` | bool | Ender Golem's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `Ender Golem.Endergolem's prevent attacks from far away Range` | `6.0` | float |  | — |
| `cataclysm.toml` | `Ender Golem.GolemHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ender Golem.GolemDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.IgnisHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.IgnisDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.IgnisNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.IgnisHealingMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.Ignis's prevent attacks from far away Range` | `15.0` | float |  | — |
| `cataclysm.toml` | `Ignis.IgnisDamageCap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ignis.IgnisDamageTime` | `15` | int | Range: 0 ~ 100 | — |
| `cataclysm.toml` | `Ignis.IgnisBlockBreaking` | `True` | bool | Ignis's cracked block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `revenant.RevenantHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `revenant.RevenantDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Prowler.The Prowler's prevent attacks from far away Range` | `16.0` | float |  | — |
| `cataclysm.toml` | `The Prowler.ProwlerHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Prowler.ProwlerDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Harbinger.HarbingerHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Harbinger.HarbingerDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Harbinger.HarbingerHealingMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Harbinger.Harbinger's WitherMissiledamage` | `8.0` | float |  | — |
| `cataclysm.toml` | `The Harbinger.Harbinger's laser damage` | `5.0` | float |  | — |
| `cataclysm.toml` | `The Harbinger.The Harbinger's prevent attacks from far away Range` | `35.0` | float |  | — |
| `cataclysm.toml` | `The Harbinger.The Harbinger DamageCap` | `22.0` | float |  | — |
| `cataclysm.toml` | `The Harbinger.The Harbinger DamageTime` | `12` | int |  | — |
| `cataclysm.toml` | `The Harbinger.The Harbinger Light A Fire` | `True` | bool |  | — |
| `cataclysm.toml` | `The Harbinger.The Harbinger's charge attack Hp Damage` | `0.06` | float |  | — |
| `cataclysm.toml` | `The Leviathan.LeviathanHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Leviathan.LeviathanDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Leviathan.LeviathanNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Leviathan.Leviathan's prevent attacks from far away Range` | `38.0` | float |  | — |
| `cataclysm.toml` | `The Leviathan.Leviathan's Bite Hp Damage` | `0.1` | float |  | — |
| `cataclysm.toml` | `The Leviathan.Leviathan's Rush Hp Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `The Leviathan.Leviathan's TailSwing Hp Damage` | `0.08` | float |  | — |
| `cataclysm.toml` | `The Leviathan.Leviathan's Tentacle Hp Damage` | `0.03` | float |  | — |
| `cataclysm.toml` | `The Leviathan.LeviathanDamageCap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Leviathan.Leviathan DamageTime` | `15` | int |  | — |
| `cataclysm.toml` | `The Leviathan.LeviathanBlockBreaking` | `True` | bool | Leviathan's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `The Leviathan.LeviathanImmuneOutofWater` | `True` | bool | Leviathan Immune Out of Water | — |
| `cataclysm.toml` | `The Baby Leviathan.BabyLeviathanHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `The Baby Leviathan.BabyLeviathanDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Modern Remnant.ModernRemnantHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Modern Remnant.ModernRemnantDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Netherite Ministrosity.MinistrosityHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Amethyst Crab.AmethystCrabHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Amethyst Crab.AmethystCrabEarthQuakeDamage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Amethyst Crab.AmethystCrabDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ancient Remnant.AncientRemnantHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ancient Remnant.AncientRemnantDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ancient Remnant.AncientRemnantNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ancient Remnant.Ancient Remnant's prevent attacks from far away Range` | `14.0` | float |  | — |
| `cataclysm.toml` | `Ancient Remnant.AncientRemnantCap` | `21.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Ancient Remnant.Ancient Remnant DamageTime` | `12` | int |  | — |
| `cataclysm.toml` | `Ancient Remnant.AncientRemnantBlockBreaking` | `True` | bool | Ancient Remnant's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `Ancient Remnant.Remnant's Charge Hp Damage` | `0.1` | float |  | — |
| `cataclysm.toml` | `Ancient Remnant.Remnant's Normal attack Hp Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `Ancient Remnant.Remnant's Stomp Hp Damage` | `0.03` | float |  | — |
| `cataclysm.toml` | `Ancient Remnant.Remnant's EarthQuakeDamage` | `11.0` | float |  | — |
| `cataclysm.toml` | `Koboleton.CauseKoboletontoDropItemInHandPercent` | `5.0` | float | Range: 0.0 ~ 100.0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Koboleton_Entity` |
| `cataclysm.toml` | `Kobolediator.KobolediatorBlockBreaking` | `False` | bool | Kobolediator's block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `Kobolediator.KobolediatorHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Kobolediator.KobolediatorDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Wadjet.WadjetHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Wadjet.WadjetDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Aptrgangr.AptrgangrHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Aptrgangr.AptrgangrDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Aptrgangr.AptrgangrAxeBladeDamage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Clawdian.ClawdianHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Clawdian.ClawdianDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Scylla.ScyllaHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Scylla.ScyllaDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Scylla.Scylla's prevent attacks from far away Range` | `10.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Spear Damage` | `14.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Lightning Storm` | `10.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Lightning Area` | `4.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Snake Damage` | `16.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Anchor Damage` | `16.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla NatureHealing` | `25.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla DamageCap` | `22.0` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla DamageTime` | `25` | int |  | — |
| `cataclysm.toml` | `Scylla.Scylla's HP Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Spin Hp Damage` | `0.07` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla's Lightning Storm HP Damage` | `0.04` | float |  | — |
| `cataclysm.toml` | `Scylla.Scylla Weather Change` | `True` | bool |  | — |
| `cataclysm.toml` | `Maledictus.MaledictusHealthMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Maledictus.MaledictusDamageMultiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Maledictus.Maledictus's prevent attacks from far away Range` | `12.0` | float |  | — |
| `cataclysm.toml` | `Maledictus.MaledictusNatureHealing` | `25.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Maledictus.Maledictus' Phantom Halberd Damage'` | `10.0` | float |  | — |
| `cataclysm.toml` | `Maledictus.MaledictusDamageCap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | — |
| `cataclysm.toml` | `Maledictus.Maledictus DamageTime` | `30` | int |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's melee Hp Damage` | `0.05` | float |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's Shock Wave Hp Damage` | `0.03` | float |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's AOE Hp Damage` | `0.15` | float |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's Flying Smash Hp Damage` | `0.1` | float |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's Jump Smash Hp Damage` | `0.08` | float |  | — |
| `cataclysm.toml` | `Maledictus.Maledictus's Phantom Arrow Damage` | `5.0` | float |  | — |
| `cataclysm.toml` | `Maledictus.MaledictusBlockBreaking` | `True` | bool | Maledictus's cracked block breaking ignore the MobGriefing | — |
| `cataclysm.toml` | `spawning.DeeplingSpawnWeight` | `2` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.DeeplingSpawnRolls` | `30` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Deepling_Entity` |
| `cataclysm.toml` | `spawning.DeeplingBruteSpawnWeight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.DeeplingBruteSpawnRolls` | `50` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Deepling_Brute_Entity` |
| `cataclysm.toml` | `spawning.DeeplingAnglerSpawnWeight` | `2` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.DeeplingAnglerSpawnRolls` | `30` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Deepling_Angler_Entity` |
| `cataclysm.toml` | `spawning.DeeplingPriestSpawnWeight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.DeeplingPriestSpawnRolls` | `70` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Deepling_Priest_Entity` |
| `cataclysm.toml` | `spawning.DeeplingWarlockSpawnWeight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.DeeplingWarlockSpawnRolls` | `70` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Deepling_Warlock_Entity` |
| `cataclysm.toml` | `spawning.CoralgolemSpawnWeight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.CoralgolemSpawnRolls` | `70` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Coral_Golem_Entity` |
| `cataclysm.toml` | `spawning.AmethystCrabSpawnWeight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.AmethystCrabSpawnRolls` | `20` | int | Range: > 0 | `cataclysm:Amethyst_Crab_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.KoboletonSpawnWeight` | `15` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.KoboletonSpawnRolls` | `1` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig`、`cataclysm:Koboleton_Entity` |
| `cataclysm.toml` | `spawning.IgnitedBerserkerSpawnWeight` | `5` | int | Range: 0 ~ 1000 | `cataclysm:CMCommonConfig`、`cataclysm:CMWorldRegistry`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `spawning.IgnitedBerserkerSpawnRolls` | `2` | int | Range: > 0 | `cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm.toml` | `World Generation.cursedPyramidCheckRange` | `2` | int | Range: 0 ~ 5 | — |
| `cataclysm.toml` | `World Generation.cursedPyramidHeightVariance` | `2` | int | Range: 0 ~ 32 | — |
| `cataclysm.toml` | `Boss Respawner.MonstrosityRespawner` | `True` | bool | on/off | — |
| `cataclysm.toml` | `Boss Respawner.EnderGuardianRespawner` | `True` | bool | on/off | — |
| `cataclysm.toml` | `Boss Respawner.HarbingerRespawner` | `True` | bool | on/off | — |
| `cataclysm.toml` | `Boss Respawner.RemnantRespawner` | `True` | bool | on/off | — |
| `cataclysm.toml` | `Boss Respawner.ScyllaRespawner` | `True` | bool | on/off | — |
| `cataclysm-client.toml` | `client.shadersCompat` | `False` | bool | Off the Custom Rendering | `cataclysm:AfterImageParticle`、`cataclysm:CMClientConfig`、`cataclysm:Cindaria_Layer` |
| `cataclysm-client.toml` | `client.ScreenShake(on/off)` | `True` | bool |  | — |
| `cataclysm-client.toml` | `client.setThirdPerson(on/off)` | `True` | bool |  | — |
| `cataclysm-client.toml` | `client.setFirstPerson(on/off)` | `True` | bool |  | — |
| `cataclysm-client.toml` | `client.custombossbar(on/off)` | `True` | bool |  | — |
| `cataclysm-client.toml` | `client.BossMusic(on/off)` | `True` | bool |  | — |
| `cataclysm-client.toml` | `client.BossMusicVolume` | `1` | int | Range: 1 ~ 100 | `cataclysm:BossMusicSound`、`cataclysm:CMClientConfig`、`cataclysm:ClientConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ignitium_armor.armor_config.armor_multiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ignitium_armor.armor_config.toughness` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ignitium_armor.armor_config.knockback_resistance` | `0.15` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData`、`chronodawn:RegistryDrivenTestGenerator` |
| `cataclysm-common.toml` | `tools_and_abilities.cursium_armor.armor_config.armor_multiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.cursium_armor.armor_config.toughness` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.cursium_armor.armor_config.knockback_resistance` | `0.05` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData`、`chronodawn:RegistryDrivenTestGenerator` |
| `cataclysm-common.toml` | `tools_and_abilities.bone_reptile_armor.armor_config.armor_multiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.bone_reptile_armor.armor_config.toughness` | `2.5` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.bone_reptile_armor.armor_config.knockback_resistan` | `0.15` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData`、`chronodawn:RegistryDrivenTestGenerator` |
| `cataclysm-common.toml` | `tools_and_abilities.bloom_stone_armor.armor_config.armor_multiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.bloom_stone_armor.armor_config.toughness` | `2.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.bloom_stone_armor.armor_config.knockback_resistanc` | `0.1` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData`、`chronodawn:RegistryDrivenTestGenerator` |
| `cataclysm-common.toml` | `tools_and_abilities.incinerator.incinerator_cooldown` | `400` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.incinerator.tool_config.attack_damage` | `14.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.incinerator.tool_config.attack_speed` | `1.4` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.bulwark_of_the_flame.bulwark_of_the_flame_cooldown` | `80` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_guard.tool_config.attack_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_guard.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_bulwark.gauntlet_of_bulwark_cooldown` | `80` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_bulwark.tool_config.attack_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_bulwark.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_maelstrom.maelstrom_cooldown` | `180` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_maelstrom.tool_config.attack_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.gauntlet_of_maelstrom.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.infernal_forge.infernal_forge_cooldown` | `80` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.infernal_forge.tool_config.attack_damage` | `13.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.infernal_forge.tool_config.attack_speed` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.void_forge.void_forge_cooldown` | `100` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_forge.void_forge_rune_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_forge.tool_config.attack_damage` | `13.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.void_forge.tool_config.attack_speed` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.brontes.brontes_storm_damage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.brontes.brontes_storm_area_damage` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.brontes.tool_config.attack_damage` | `13.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.brontes.tool_config.attack_speed` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.wither_assault_shoulder_weapon.missile_cooldown` | `40` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.wither_assault_shoulder_weapon.missile_damage` | `16.0` | float | Range: 0.0 ~ 100000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.wither_assault_shoulder_weapon.howitzer_cooldown` | `100` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_assault_shoulder_weapon.howitzer_cooldown` | `160` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_assault_shoulder_weapon.vasw_rune_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_core.void_core_cooldown` | `160` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.void_core.void_core_rune_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.sandstorm.sandstorm_cooldown` | `300` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.soul_render.soul_render_cooldown` | `100` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.soul_render.phantom_halberd_damage` | `12.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.soul_render.tool_config.attack_damage` | `15.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.soul_render.tool_config.attack_speed` | `1.05` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.immolator.immolator_cooldown` | `250` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.immolator.tool_config.attack_damage` | `8.5` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.immolator.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.tidal_claws.tool_config.attack_damage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.tidal_claws.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.ceraunus.ceraunus_wave_damage` | `6.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ceraunus.ceraunus_cooldown` | `150` | int | Range: 0 ~ 1000000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ceraunus.tool_config.attack_damage` | `16.0` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.ceraunus.tool_config.attack_speed` | `0.7` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.astrape.astrape_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.astrape.astrape_area_damage` | `2.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.astrape.astrape_cooldown` | `60` | int | Range: 0 ~ 1000000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.astrape.tool_config.attack_damage` | `10.5` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.astrape.tool_config.attack_speed` | `1.4` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.meat_shredder.tool_config.attack_damage` | `8.5` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.meat_shredder.tool_config.attack_speed` | `1.4` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.annihilator.tool_config.attack_damage` | `8.5` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.annihilator.tool_config.attack_speed` | `1.6` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `tools_and_abilities.cursed_bow.cursed_bow_damage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.wrath_of_the_desert.wrath_of_the_desert_damage` | `7.5` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.laser_gatling.laser_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ancient_spear.sand_storm_damage` | `6.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `tools_and_abilities.ancient_spear.tool_config.attack_damage` | `9.5` | float | Range: 0.0 ~ 1000000.0 | `attributefix:RangeConfig`、`cataclysm:CommonConfig`、`chronodawn:RegistryDrivenTestData` |
| `cataclysm-common.toml` | `tools_and_abilities.ancient_spear.tool_config.attack_speed` | `1.4` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`foxablazeultimate:RaphaelSkill`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.etc.return_home` | `20` | int | Range: 0 ~ 200 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.gravity_punch_hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.teleport_attack_hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.knockback_hp_damage` | `0.06` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.uppercut_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.rocket_punch_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.area_attack_hp_damage` | `0.08` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.block_break_x` | `15` | int | Range: 0 ~ 20 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.block_break_y` | `2` | int | Range: 0 ~ 10 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.block_break_z` | `15` | int | Range: 0 ~ 20 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.void_rune_damage` | `9.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.separate_phase_music` | `True` | bool | Enable separate music per boss phase | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ender_guardian.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ender_guardian.cap_config.damage_cap` | `22.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.ender_guardian.cap_config.dps_cap` | `13.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.cap_config.range_cap` | `12.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.ender_guardian.respawner_config.respawner` | `True` | bool | Respawner | `cataclysm:Ancient_Remnant_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_guardian.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_golem.void_rune_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_golem.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ender_golem.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ender_golem.cap_config.damage_cap` | `999999.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.ender_golem.cap_config.dps_cap` | `999999.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_golem.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_golem.cap_config.range_cap` | `8.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ender_golem.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.ender_golem.ignore_mobgriefing_config.ignore_mobgriefing` | `False` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.lava_bomb_magazine` | `15` | int | Range: 0 ~ 20 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.lava_bomb_amount` | `3` | int | Range: 1 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.lava_bomb_duration` | `350` | int | Range: 1 ~ 10000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.lava_bomb_random_duration` | `150` | int | Range: 1 ~ 10000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.body_collided` | `True` | bool | Body collide like shulker | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.smash_hp_damage` | `0.08` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.flame_jet_damage` | `7.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.flare_bomb_damage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.cap_config.damage_cap` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.cap_config.dps_cap` | `20.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.cap_config.range_cap` | `18.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.respawner_config.respawner` | `True` | bool | Respawner | `cataclysm:Ancient_Remnant_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_monstrosity.ignore_mobgriefing_config.ignore_mobgriefin` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.netherite_ministrosity.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.netherite_ministrosity.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ignis.healing_multiplier` | `1.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignis.separate_phase_music` | `True` | bool | Enable separate music per boss phase | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignis.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ignis.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ignis.cap_config.damage_cap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.ignis.cap_config.dps_cap` | `14.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignis.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignis.cap_config.range_cap` | `15.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignis.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.ignis.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignited_revenant.ashen_breath_damage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignited_revenant.blazing_bone_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ignited_revenant.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ignited_revenant.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.harbinger.harbinger_auto_heal` | `2.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.harbinger_life_steal` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.wither_missile_damage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.laser_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.death_laser_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.death_laser_hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.charge_hp_damage` | `0.06` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.harbinger.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.harbinger.cap_config.damage_cap` | `22.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.harbinger.cap_config.dps_cap` | `14.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.cap_config.range_cap` | `35.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.harbinger.respawner_config.respawner` | `True` | bool | Respawner | `cataclysm:Ancient_Remnant_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.harbinger.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.prowler.range_cap` | `15.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.prowler.homing_missile` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.prowler.death_laser_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.prowler.death_laser_hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.prowler.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.prowler.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.amethyst_crab.earth_quake_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.amethyst_crab.amethyst_cluster_damage` | `12.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.amethyst_crab.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.amethyst_crab.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.leviathan.bite_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.rush_hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.tail_swing_hp_damage` | `0.06` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.tentacle_hp_damage` | `0.06` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.dimensional_rift_damage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.abyss_blast_damage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.abyss_blast_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.abyss_orb_damage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.immune_out_of_water` | `True` | bool | Leviathan Immune Out of Water. | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.separate_phase_music` | `True` | bool | Enable separate music per boss phase | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.leviathan.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.leviathan.cap_config.damage_cap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.leviathan.cap_config.dps_cap` | `15.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.cap_config.range_cap` | `38.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.leviathan.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.baby_leviathan.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.baby_leviathan.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.charge_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig`、`cataclysm:Lightning_Spear_Entity` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.stomp_hp_damage` | `0.03` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.earthquake_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.ancient_desert_stele_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.cap_config.damage_cap` | `21.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.cap_config.dps_cap` | `14.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.cap_config.range_cap` | `14.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.respawner_config.respawner` | `True` | bool | Respawner | `cataclysm:Ancient_Remnant_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.ancient_remnant.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.modern_remnant.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.modern_remnant.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.koboleton.drop_hand` | `5.0` | float | Range: 0.0 ~ 100.0 | `cataclysm:CommonConfig`、`creativecore:CreativeCoreGuiRegistry` |
| `cataclysm-common.toml` | `mobs.kobolediator.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.kobolediator.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.kobolediator.ignore_mobgriefing_config.ignore_mobgriefing` | `False` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.wadjet.sandstorm_damage` | `6.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.wadjet.ancient_desert_stele_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.wadjet.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.wadjet.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.maledictus.phantom_halberd_damage` | `11.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig`、`cataclysm:Lightning_Spear_Entity` |
| `cataclysm-common.toml` | `mobs.maledictus.shock_wave_hp_damage` | `0.03` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.aoe_hp_damage` | `0.15` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.flying_smash_hp_damage` | `0.1` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.smash_hp_damage` | `0.08` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.phantom_arrow_damage` | `5.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.maledictus.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.maledictus.cap_config.damage_cap` | `20.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.maledictus.cap_config.dps_cap` | `13.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.cap_config.range_cap` | `14.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.maledictus.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.maledictus.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.aptrgangr.axe_blade_damage` | `8.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.aptrgangr.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.aptrgangr.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.aptrgangr.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.scylla.spear_damage` | `14.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.lightning_storm_damage` | `10.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.lightning_area_damage` | `4.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.snake_damage` | `16.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.anchor_damage` | `16.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.weather_change` | `True` | bool | Weather Change | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.hp_damage` | `0.05` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig`、`cataclysm:Lightning_Spear_Entity` |
| `cataclysm-common.toml` | `mobs.scylla.spin_hp_damage` | `0.07` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.lightning_storm_hp_damage` | `0.04` | float | Range: 0.0 ~ 1.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.scylla.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.scylla.cap_config.damage_cap` | `21.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.scylla.cap_config.dps_cap` | `13.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.cap_config.range_cap` | `12.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.respawner_config.respawner` | `True` | bool | Respawner | `cataclysm:Ancient_Remnant_Entity`、`cataclysm:CMCommonConfig`、`cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.scylla.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `mobs.scylla.ignore_mobgriefing_config.ignore_mobgriefing` | `True` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.clawdian.combat_config.health_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.clawdian.combat_config.attack_multiplier` | `1.0` | float | Range: 0.1 ~ 1000000.0 | `cataclysm:CommonConfig`、`mowziesmobs:ConfigHandler` |
| `cataclysm-common.toml` | `mobs.clawdian.cap_config.damage_cap` | `21.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`eternal_starlight:ESItems` |
| `cataclysm-common.toml` | `mobs.clawdian.cap_config.dps_cap` | `12.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.clawdian.cap_config.dps_limit_time` | `20` | int | Range: 1 ~ 100 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.clawdian.cap_config.range_cap` | `12.0` | float | Range: 1.0 ~ 1000000.0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.clawdian.ignore_mobgriefing_config.ignore_mobgriefing` | `False` | bool | Ignore MobGriefing | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `mobs.clawdian.nature_heal_config.nature_heal` | `25.0` | float | Range: 0.0 ~ 1000000.0 | `cataclysm:CommonConfig`、`cataclysm:ModAttribute` |
| `cataclysm-common.toml` | `blocks.cursed_tombstone.cursed_tombstone_cooldown` | `1` | int | Range: 0 ~ 30 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_spawn_weight` | `2` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_spawn_rolls` | `30` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_brute_spawn_weight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_brute_spawn_rolls` | `50` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_angler_spawn_weight` | `2` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_angler_spawn_rolls` | `30` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_priest_spawn_weight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_priest_spawn_rolls` | `70` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_warlock_spawn_weight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.deepling_warlock_spawn_rolls` | `70` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.coral_golem_spawn_weight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.coral_golem_spawn_rolls` | `70` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.amethyst_crab_spawn_weight` | `1` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.amethyst_crab_spawn_rolls` | `20` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.koboleton_spawn_weight` | `15` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.koboleton_spawn_rolls` | `1` | int | Range: > 0 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.ignited_berserker_spawn_weight` | `7` | int | Range: 0 ~ 1000 | `cataclysm:CommonConfig` |
| `cataclysm-common.toml` | `spawning.ignited_berserker_spawn_rolls` | `2` | int | Range: > 0 | `cataclysm:CommonConfig` |
