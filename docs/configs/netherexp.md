---
页面: configs/netherexp
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `netherexp` 配置项

> **2 个文件 / 208 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/netherexp](../../mods/netherexp/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `netherexp-startup.toml` | `startupSettings.enableJNESplashTexts` | `True` | bool | Adds new JNE inspired splash texts alongside the vanilla one | `netherexp:JNEConfigImpl` |
| `netherexp-startup.toml` | `startupSettings.redSplashText` | `True` | bool | Splash texts added by JNE will be a wonderful red color oppo | `netherexp:JNEConfigImpl` |
| `netherexp-startup.toml` | `startupSettings.profanity` | `CENSORED` | str | Allowed Values: DISABLED, CENSORED, UNFILTERED | `netherexp:JNEConfigImpl`、`netherexp:SplashManagerMixin` |
| `netherexp-startup.toml` | `startupSettings.developerMode` | `False` | bool | I suggest you keep this disabled if you're just a casual pla | `configured:Config`、`netherexp:JNEConfigImpl`、`ponderer:Config` |
| `netherexp-startup.toml` | `packSettings.soulCampfireSoulSoil` | `True` | bool | Changes the soot part of the soul campfire texture to become | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.fossilOreConversionChance` | `0.02` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.soulGlassMovementSlowdown` | `0.6` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.soulSwirlsCooldown` | `50` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.soulSpeedDuration` | `7` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.boneMealSoulSwirls` | `True` | bool | Weather soul swirls can be bone-mealed to duplicate or not | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.brushWispsOut` | `True` | bool | If enabled wisps can be forced out of ecto soul sand with a  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.wispEmergingChance` | `0.02` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.wispEmergingChanceBrush` | `0.07` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.ectoSoulSandBrushDamage` | `4` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.convertsToSuspiciousSoulSand` | `True` | bool | Weather ecto soul sand converts to suspicious soul sand by e | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.wispArchaeologyDefaultLootTable` | `netherexp:archaeology/wisp_arch_default` | str | Defines the loot table which the game will use when no regis | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.suspiciousSoulSandDecayChance` | `0.05` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.suspiciousSoulSandMaxDecay` | `5` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.lesionGrowthChance` | `0.1` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.lesionDropsPerHarvest` | `12` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sporeshroomHeightVelocity` | `1.0` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.geyserHeightVelocity` | `1.2` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.geyserCooldown` | `5` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.blackIcicleGrowthChance` | `0.28` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.thinIceBreakingChance` | `0.015` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.thinIceBreakingChanceSprinting` | `0.2` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.nearbyThinIceBreakingChance` | `0.4` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.blackIceFrostsWaterChance` | `0.6` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.blackIceTainting` | `True` | bool | Black ice will taint netherrack into pale soul slate | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sorrowsquashTainting` | `True` | bool | Sorrowsquash taints nearby soul ground blocks into sorroweed | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.shouldSorrowsquashFall` | `True` | bool | Weather sorrowsquashes fall if the stem it's attached to bre | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sorrowsquishedDamageMultiplier` | `1.5` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sorrowsquishedMaxDamage` | `30` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sorrowsquashGrowthChance` | `0.2` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.cerebrageGrowthChance` | `0.1` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.minCerebrageDropped` | `3` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.maxCerebrageDropped` | `6` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.cerebrageSeedsDropChance` | `0.05` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.cerebrageGrowsBrainTrees` | `True` | bool | If cerebrage skulls are bone-mealed past their last growth s | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.shotgunBarrelBullets` | `10` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.canAnythingBreakFrogmist` | `False` | bool | This config makes it breakable with anything regardless of w | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.hazeBlockCooldown` | `8` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.ciergeOfTreacheryCompletionCooldown` | `1800` | int | Range: 0 ~ 86400 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.brazierChestRefillCooldown` | `3600` | int | Range: 0 ~ 86400 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `blockSettings.sculkGrinderExperience` | `450` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.immunityConsumption` | `600` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.amplifierScalesImmunityConsumption` | `True` | bool | Weather the amount of duration depleted multiplies with the  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.tweakObtainingTearsMusicDisc` | `True` | bool | If disabled then the disc is obtained by redirecting a fireb | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.shotgunSelfRecoil` | `0.35` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.pointBlankSelfRecoilBonus` | `0.45` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.pointBlankSelfRecoilDistance` | `18.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.shotgunFistBullets` | `25` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.shotgunFistCooldown` | `40` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.pumpChargeShotgunBullets` | `10` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.pumpChargeShotgunCooldown` | `15` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.slugBlockDamageStrength` | `1.0` | float | Range: -1.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.counterforce_iframes` | `20` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.willOWispStackSize` | `64` | int | Range: 1 ~ 99 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.stackablePotions` | `True` | bool | When enabled almost all types of potions can be stacked to 1 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `itemSettings.removeSoulSpeedEnchantment` | `True` | bool | This will not retroactively remove pre-existing items with t | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.wispBoredomChance` | `0.02` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.blackIceFreezingTicks` | `100` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.skeletonFossilization` | `True` | bool | Upon death skeletons will fossilize soul soil blocks they ar | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.witherSkeletonFossilization` | `True` | bool | Upon death wither skeletons will fossilize soul soil blocks  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.wispsDroppedByApparition` | `2` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.apparitionPossessionCooldown` | `300` | int | Range: > -1 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.apparitionsCanBeSalted` | `True` | bool | Salted apparitions cannot possess any mobs, although they wi | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.apparitionsCanPossessMobs` | `True` | bool | Apparitions can take control of certain mobs they kill and t | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.apparitionsCanPossessGargoyles` | `True` | bool | Apparitions can use certain gargoyle statues to turn themsel | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.possessedMobsUnleashApparition` | `True` | bool | Upon death possessed mobs have a chance to unleash an appari | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.hardDifficultyUnleashingMultiplier` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.projectilesPhaseThroughGhosts` | `True` | bool | Projectiles specified in the "phantasm_hull_protects_blackli | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.diminishingBlazes` | `True` | bool | Blazes will visibly become dimmer the lower their health is  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.minVesselBullets` | `16` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.maxVesselBullets` | `20` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.vesselAttackTime` | `110` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.vesselShootsAtAttackTime` | `100` | int | Range: > 50 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.vesselAccurateDistance` | `5` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeTeleportsAfterHit` | `True` | bool | Banshees hurt by an entity will try to teleport away to a ra | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeAnchorInterval` | `3` | int | Range: > 1 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeAttackInterval` | `40` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeAttackIntervalBonus` | `30` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeAttackIntervalStagger` | `10` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeRedirectBehavior` | `STUN` | str | Allowed Values: DO_NOTHING, STUN, INSTAKILL | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeStunTimer` | `100` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.genericWillOWispManoeuvrability` | `0.22` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeWillOWispManoeuvrability` | `0.2` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.manoeuvrabilityAffectedByDifficulty` | `True` | bool |  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.willOWispWindPropulsion` | `True` | bool | When a will o' wisp is hit with a wind charge it'll accelera | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabPetrifyWithGhastTear` | `True` | bool | Whether you can use ghast tears to petrify ecto slabs | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.petrifiedEctoSlabShatterDisturbance` | `20` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabForceOutWithShovel` | `True` | bool | Whether you can force an ecto slab out with a shovel | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.forcedOutEctoSlabFriendlyFire` | `True` | bool | Whether forced out ecto slabs can deal friendly fire to enti | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.explosionShattersEctoSlabStack` | `True` | bool | Whether explosion damage can shatter the ecto slab stack | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.explosionShatterCooldown` | `300` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.petrificationPetrifiesSwirls` | `True` | bool | Whether petrification should petrify nearby swirls | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.petrificationSwirlsRange` | `32` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.petrificationSwirlsRangePerStack` | `4` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.naturalPetrifiedEctoSlabChance` | `0.12` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.naturalPetrifiedEctoSlabMinStack` | `8` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.naturalPetrifiedEctoSlabMaxStack` | `12` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.naturalEctoSlabPackMin` | `2` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.naturalEctoSlabPackMax` | `5` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.emergeBurstDamageMultiplier` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.absoluteMaximumStackSize` | `16` | int | Range: 1 ~ 99 | `netherexp:EctoSlab`、`netherexp:EctoSlabRenderer`、`netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabExtraJumpDelay` | `0` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabExtraBurrowCooldown` | `0` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabSpeedPerStack` | `0.05` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabSpeedCap` | `0.6` | float | Range: -1.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabDamagePerStack` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabDamageCap` | `-1.0` | float | Range: -1.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabKnockbackResistancePerStack` | `0.15` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabKnockbackResistanceCap` | `-1.0` | float | Range: -1.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabJumpStrengthPerStack` | `0.05` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabJumpStrengthCap` | `-1.0` | float | Range: -1.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabMaxStackSize` | `4` | int | Range: 1 ~ 16 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabMaxDigTime` | `80` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.vesselUnleashingOdds` | `0.25` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.ectoSlabUnleashingOdds` | `0.05` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.bansheeUnleashingOdds` | `0.15` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.stampedeUnleashingOdds` | `0.5` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.stampedeStriditeSheddingChance` | `0.1` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.minStampedeStriditeDrops` | `1` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.maxStampedeStriditeDrops` | `5` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.pixelConsistentMagmaCubes` | `True` | bool | Magma Cube textures will scale with the model and conform to | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `entitySettings.stampedeBefriendingOdds` | `8` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.netherWorldGenOverhaul` | `True` | bool |  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.improvedNetherBiomeSource` | `True` | bool |  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.brighterNetherFog` | `True` | bool | It overall makes the nether feel warmer and helps make out s | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.redNetherWastesFog` | `False` | bool | Changes the nether wastes fog back to being red like in vani | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.betterSoulSandValleyParticles` | `True` | bool | Dust like particles will blow in the soul sand valley instea | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.bonePike` | `True` | bool | Generates bone pikes on the soul sand valley surface | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.ectoSoulSand` | `True` | bool | Generates ecto soul sand patches in the soul sand valley | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.ectoplasmLake` | `True` | bool | Generates ectoplasm lakes in the soul sand valley | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.fossilFuelOre` | `True` | bool | Generates fossil fuel ore deposits in the soul sand valley | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.fossilOre` | `True` | bool | Generates fossil ore deposits in the soul sand valley | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.mound` | `True` | bool | Generates mounds on the ceiling and floor of the soul sand v | `netherexp:JNEConfigImpl`、`netherexp:JNEFeatureTypes` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.soulMagma` | `True` | bool | Generates soul magma patches towards the upper regions of th | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.paleSoulSlate` | `True` | bool | Generates pale soul slate embedded in the walls or surface o | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.soulSwirls` | `True` | bool | Generates soul swirls in the soul sand valley floor and ceil | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.blottedNetherrack` | `True` | bool | Generates blotted netherrack patches all throughout the neth | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.siltmarram` | `True` | bool | Generates siltmarram on silt beaches and coves with a differ | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.netherrackSpeleothem` | `True` | bool | Generates large netherrack speleothem all throughout the net | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.pyroclastCrusts` | `True` | bool | Generates pyroclast crusts on the lava sea surfaces | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.siltFlintOre` | `True` | bool | Generates silt flint ore deposits in silt beaches and coves | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.biomeFeaturesSettings.removeNetherNoodleCaves` | `True` | bool | Prevents those ugly ravines and noodle caves from cutting th | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `worldSettings.subBiomesSettings.blackIceGlaciers` | `True` | bool | Enables the tertiary sub-biome for soul sand valleys | `netherexp:JNEConfigImpl`、`netherexp:JNESurfaceRules` |
| `netherexp-common.toml` | `visualAndSoundSettings.uncappedNetherFogDistance` | `True` | bool | Usually in vanilla the nether fog cannot exceed past 12 chun | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.blackIceParticles` | `True` | bool | Black ice blocks will produce black flake particles undernea | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ectoplasmParticles` | `True` | bool | Ectoplasm will produce rays of light and rising particles fr | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ectoplasmSounds` | `True` | bool | Ectoplasm will whisper incomprehensible gibberish to players | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistParticles` | `True` | bool | Periodically misty particles will form around the distance f | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistSpawnRate` | `6` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistMinDistance` | `32.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistMaxDistance` | `80.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistScale` | `60.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistOpacity` | `0.5` | float | Range: 0.0 ~ 1.0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistMotionMultiplier` | `0.05` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.netherMistDissipateDistance` | `20.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.soulSandValleyWindSpeed` | `0.2` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.windyAshScaleMultiplier` | `1.6` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.driftingSoulsSpawnQuantity` | `4` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.driftingSoulsSpawnRadius` | `32` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.willOWispParticles` | `True` | bool | Fired will o' wisps will produce wisp-like trail particles b | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.willOWispSounds` | `True` | bool | Fired will o' wisps will make a weeping sound that helps wit | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.improvedFireParticles` | `True` | bool | Adds more dynamic and animated particles to all fire blocks | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.fireSmokeParticles` | `True` | bool | Enables new smoke particles for fire | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.fireEmberParticles` | `True` | bool | Enables new ember particles for fire | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.lavaPerlinNoiseGradient` | `True` | bool | Adds slight color variation gradients to lava to break up th | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.lavaGradientColorA` | `#FFFFFFFF` | str | Color §cMUST§r be in #RRGGBB or #AARRGGBB format | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.LavaGradientColorB` | `#FFE28001` | str | Color §cMUST§r be in #RRGGBB or #AARRGGBB format | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.LavaGradientNoiseScale` | `14.0` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.shotgunScreenshake` | `True` | bool | Toggle the screenshake when a shotgun is fired | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ectoSlabEmergeBurstScreenshake` | `True` | bool | Toggle the emerge burst screen shake | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ectoSlabPetrificationScreenshake` | `True` | bool | Toggle petrification screen shake | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.enableHeatDistortion` | `True` | bool | Toggles all heat distortion effects in JNE; To disable indiv | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.biomeHeatDistortion` | `True` | bool | Heat distortion is applied to far away objects when in certa | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.lavaHeatDistortion` | `True` | bool | A much stronger heat distortion is applied when close to lav | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionIntensity` | `0.003` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionSpeed` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionMinDistance` | `12.0` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionMaxDistance` | `128.0` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaIntensity` | `0.006000000052154064` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaSpeed` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaMinDistance` | `5e-324` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaMaxDistance` | `64.0` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaCheckPeriod` | `100` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.heatDistortionLavaProximity` | `2.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.requiredSourceBlocksForLavaHeatDistortion` | `12` | int | Range: > 0 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.enableSoulGlassScreenFilter` | `True` | bool | Toggles the on-screen filter when submerged inside soul glas | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.noSoulGlassRipple` | `False` | bool | Removes the ripple effect from soul glass entirely and leave | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.improvedBrewingStandParticles` | `True` | bool | Improves the brewing stand's particles | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.potionConsumptionParticles` | `True` | bool | Adds a particle effect around the player after drinking from | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.improvedNetherPortalParticles` | `True` | bool | Improves the nether portal's particles | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.enableNetherBiomeLightmaps` | `True` | bool | Gives every nether biome a custom lightmap that compliments  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.stampedeScreenshake` | `True` | bool | Toggle stampede screen shake | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ciergeOfTreacheryRedLights` | `True` | bool | When the betrayed effect is active, it'll turn all lighting  | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ciergeOfTreacheryFog` | `True` | bool | When the betrayed effect is active, a dense red fog will app | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.ciergeOfTreacheryParticles` | `True` | bool | Cierge of Treachery will produce red haze and sparkle partic | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.dungeonsFlameParticles` | `True` | bool | Replaces all flame particles with animated ones from Minecra | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.improvedBurnParticles` | `True` | bool | Replaces the ugly fire texture when mobs are burning with pa | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `visualAndSoundSettings.mobBurningGlow` | `True` | bool | Mobs on fire will pulse a glow corresponding to the fire typ | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.ectoplasmFreezes` | `True` | bool | Ectoplasm will slowly start to deal freezing damage when sub | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.ectoplasmRustsNetherite` | `True` | bool | Ectoplasm will rust all netherite upon contact | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.silverParanormalDamageMultiplier` | `1.8` | float | Range: 4.9E-324 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.silverParanormalProtectionDamage` | `5.0` | float | Range: 0.0 ~ 1.7976931348623157E308 | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.silverInflictsParanormalSlowness` | `True` | bool | Silver armor and weapons will inflict possessed and ghost mo | `netherexp:JNEConfigImpl` |
| `netherexp-common.toml` | `gameMechanicSettings.showBetaWarningPopUp` | `True` | bool | Pretty self-explanatory; When running beta builds of JNE, a  | `netherexp:JNEConfigImpl` |
