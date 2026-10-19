---
页面: configs/traddon
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `traddon` 配置项

> **3 个文件 / 137 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/traddon](../../mods/traddon/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `traddon/general.toml` | `General.refresh` | `False` | bool | Should the TR Addon skills and races be added to the startin | `cataclysm:LightningRender`、`eternal_starlight:BoltRenderer`、`ftbchunks:FTBChunksClientConfig` |
| `traddon/race/shardling_config.toml` | `Shardling.minAura` | `1500.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `Shardling.maxAura` | `2500.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `Shardling.minMagicule` | `300.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `Shardling.maxMagicule` | `600.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `Shardling.size` | `-0.6000000238418579` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `traddon/race/shardling_config.toml` | `Shardling.maxHealth` | `-6.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `traddon/race/shardling_config.toml` | `Shardling.maxSpiritualHealth` | `10.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `Shardling.attack` | `1.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `traddon/race/shardling_config.toml` | `Shardling.attackSpeed` | `0.1` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `traddon/race/shardling_config.toml` | `Shardling.knockbackResistance` | `0.25` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `traddon/race/shardling_config.toml` | `Shardling.movementSpeed` | `0.0` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `traddon/race/shardling_config.toml` | `Shardling.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.epRequirement` | `100000.0` | float | EP requirement to evolve into Diamond Sovereign. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.minAura` | `100000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.maxAura` | `100000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.minMagicule` | `100000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.maxMagicule` | `100000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.maxHealth` | `140.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.maxSpiritualHealth` | `440.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.attack` | `3.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.attackSpeed` | `0.3` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.knockbackResistance` | `0.5` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.movementSpeed` | `0.06` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `traddon/race/shardling_config.toml` | `GeodeGolem.swimSpeed` | `0.4` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `traddon/race/shardling_config.toml` | `PrismKnight.epRequirement` | `400000.0` | float | EP requirement to evolve into Diamond Sovereign. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `traddon/race/shardling_config.toml` | `PrismKnight.minAura` | `400000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `PrismKnight.maxAura` | `400000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `PrismKnight.minMagicule` | `400000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `PrismKnight.maxMagicule` | `400000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `PrismKnight.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `traddon/race/shardling_config.toml` | `PrismKnight.maxHealth` | `380.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `traddon/race/shardling_config.toml` | `PrismKnight.maxSpiritualHealth` | `2340.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `PrismKnight.attack` | `6.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `traddon/race/shardling_config.toml` | `PrismKnight.attackSpeed` | `0.6` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `traddon/race/shardling_config.toml` | `PrismKnight.knockbackResistance` | `0.75` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `traddon/race/shardling_config.toml` | `PrismKnight.movementSpeed` | `0.1` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `traddon/race/shardling_config.toml` | `PrismKnight.swimSpeed` | `0.5` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.epRequirement` | `2000000.0` | float | EP requirement to evolve into Diamond Sovereign. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.minAura` | `1000000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.maxAura` | `1000000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.minMagicule` | `1000000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.maxMagicule` | `1000000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.maxHealth` | `780.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.maxSpiritualHealth` | `4940.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.attack` | `8.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.attackSpeed` | `0.9` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.knockbackResistance` | `1.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.movementSpeed` | `0.12` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `traddon/race/shardling_config.toml` | `DiamondSovereign.swimSpeed` | `1.2` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `traddon/ability/skill/unique_config.toml` | `deviator.hpMultiplier` | `0.1` | float | The multiplier of the maximum Health that a target needs to  | `tensura:AllyBoostEffect`、`tensura:AspectualMagicConfig`、`tensura:DiamondPathEffect` |
| `traddon/ability/skill/unique_config.toml` | `deviator.shpMultiplier` | `0.1` | float | The multiplier of the maximum Spiritual Health that a target | `tensura:AllyBoostEffect`、`tensura:AspectualMagicConfig`、`tensura:HinataSakaguchiEntity` |
| `traddon/ability/skill/unique_config.toml` | `deviator.epMultiplier` | `0.25` | float | The multiplier of the user's maximum EP that a target needs  | `tensura:AspectualMagicConfig`、`tensura:ExtraSkillConfig`、`tensura:IntrinsicSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `deviator.resistanceMultiplier` | `0.5` | float | The multiplier of the possession requirement multipliers whe | `tensura:AspectualMagicConfig`、`tensura:IntrinsicSkillConfig`、`tensura:PossessionMagic` |
| `traddon/ability/skill/unique_config.toml` | `deviator.maxHealth` | `1000.0` | float | The maximum amount of HP the user can get from possessing an | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `traddon/ability/skill/unique_config.toml` | `deviator.maxAttack` | `100.0` | float | The maximum amount of Attack Damage the user can get from po | `mysticism:MixinPossessionSkill`、`tensura:AspectualMagicConfig`、`tensura:AttributeConfig` |
| `traddon/ability/skill/unique_config.toml` | `deviator.bodyDespawnTick` | `300` | int | The number of seconds that Possession Bodies will despawn. ( | `tensura:AspectualMagicConfig`、`tensura:IntrinsicSkillConfig`、`tensura:PossessionMagic` |
| `traddon/ability/skill/unique_config.toml` | `deviator.spiritualDamageMultiplier` | `1.0` | float | The extra damage multiplier for Devastator when not mastered | `tensuramoreskills:EntityConfigs`、`tensuramoreskills:MoreSkillsConfigs`、`tensuramoreskills:TrueAuthorityOfGreedSkill` |
| `traddon/ability/skill/unique_config.toml` | `deviator.masterDamageMultiplier` | `1.5` | float | The extra damage multiplier for Devastator when mastered. | `traddon:DevastatorSkill`、`traddon:DeviatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `deviator.uniqueSkillChance` | `0.1` | float | chance for deviator to get unique skills when possessing an  | `traddon:DeviatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `deviator.masterUniqueSkillChance` | `0.2` | float | chance for deviator to get unique skills when possessing an  | `traddon:DeviatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `deviator.mpAcquirement` | `50000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `deviator.range` | `4.0` | float | Range | `ancient_artifacts:RoyalBeastSkillMixin`、`artifacts:MagnetismMobEffect`、`artifacts:MimicEntity` |
| `traddon/ability/skill/unique_config.toml` | `devastator.mpAcquirement` | `80000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `devastator.attack` | `-0.25` | float | The attack damage reduction percentage granted by the Despai | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `traddon/ability/skill/unique_config.toml` | `devastator.attackSpeed` | `-0.25` | float | The attack speed reduction percentage granted by the Despair | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.movementSpeed` | `-0.25` | float | The movement speed reduction percentage granted by the Despa | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `traddon/ability/skill/unique_config.toml` | `devastator.chantSpeed` | `-0.25` | float | The chant speed reduction percentage granted by the Despair  | `foxablazeultimate:MammonSkill`、`mysticism:SpiritualistSkill`、`mysticism:UniqueMysticismSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.masterDamageMultiplier` | `1.5` | float | The extra damage multiplier for Devastator when mastered. | `traddon:DevastatorSkill`、`traddon:DeviatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.spiritualDamageMultiplier` | `1.0` | float | The extra damage multiplier for Devastator when not mastered | `tensuramoreskills:EntityConfigs`、`tensuramoreskills:MoreSkillsConfigs`、`tensuramoreskills:TrueAuthorityOfGreedSkill` |
| `traddon/ability/skill/unique_config.toml` | `devastator.armorDurability` | `2` | int | The armor durability reduction multiplier for Devastator. | `tensura:AncientGiantRace`、`tensura:BerserkerSkill`、`tensura:DivineGiantRace` |
| `traddon/ability/skill/unique_config.toml` | `devastator.weaponDurability` | `2` | int | The weapon durability reduction multiplier for Devastator. | `tensura:BerserkerSkill`、`tensura:UniqueSkillConfig`、`traddon:DevastatorSkill` |
| `traddon/ability/skill/unique_config.toml` | `devastator.epDifferenceMultiplier` | `0.2` | float | The EP difference multiplier for Devastator. | `foxablazeultimate:FortunaSkill`、`mysticism:CrasherSkill`、`mysticism:UniqueMysticismSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.physicalDamageMultiplier` | `1.5` | float | The physical damage multiplier for Devastator. | `traddon:DevastatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.endOfDaysRadius` | `16.0` | float | The radius for the End of Days effect. | `traddon:DevastatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.surgeAttack` | `0.1` | float | The attack damage multiplier for the Post Mortem Surge effec | `traddon:PostMortemSurgeEffect`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.surgeAttackSpeed` | `0.1` | float | The attack speed multiplier for the Post Mortem Surge effect | `traddon:PostMortemSurgeEffect`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.tyrantsHakiCost` | `100.0` | float | The magicule cost for the Tyrant's Haki mode. | `traddon:DevastatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `devastator.endOfDaysCost` | `2500.0` | float | The magicule cost for the End of Days mode. | `traddon:DevastatorSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `CrystalDust.magiculeCost` | `200.0` | float | Magicule Cost to activate the Default Mode. | `foxablazeultimate:FortunaAcquisitionHandler`、`foxablazeultimate:FortunaSkill`、`foxablazeultimate:HellSniperBeamEntity` |
| `traddon/ability/skill/unique_config.toml` | `CrystalDust.dustRange` | `4` | int | The spawn range in block of the Crystal Dust. | `traddon:CrystalDustSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `CrystalDust.dustDamage` | `2` | int | The damage per second of the Crystal Dust. | `traddon:CrystalDustSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `CrystalDust.dustRadius` | `3` | int | The radius of the Crystal Dust. | `traddon:CrystalDustSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `CrystalDust.dustCooldown` | `12` | int | The cooldown of the Crystal Dust (halved with mastery). | `traddon:CrystalDustSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `RefractiveShell.refractiveShellSpeed` | `0.8` | float | The movement speed multiplier when Dispersion is active. | `traddon:RefractiveShellSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `RefractiveShell.magiculeCostRefractiveShell` | `100.0` | float | The magicule cost per point of damage canceled by the Refrac | `traddon:RefractiveShellSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `PhotonBlade.photonBladeBaseDamage` | `6.0` | float | The base extra damage dealt by Photon Blade on hit. | `traddon:PhotonBladeEffect`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `PhotonBlade.photonBladeMasteredExtraDamage` | `12.0` | float | The extra damage dealt by Photon Blade on hit when mastered. | `traddon:PhotonBladeEffect`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `PhotonBlade.physicalResist` | `True` | bool | Physical resist degradation from effect. | `traddon:PhotonBladeEffect`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `ResonantCore.mpAcquirement` | `30000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `ResonantCore.spectralRefractionCooldown` | `100` | int | Cooldown in ticks for Spectral Refraction active mode. | `traddon:ResonantCoreSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `ResonantCore.spectralRefractionDuration` | `200` | int | Duration in ticks for Spectral Refraction to be held. | `traddon:ResonantCoreSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `ResonantCore.dataOverloadCooldown` | `100` | int | Cooldown in ticks for Data Overload active mode. | `traddon:ResonantCoreSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `ResonantCore.dataOverloadStunDuration` | `60` | int | Stun duration in ticks for Data Overload. | `traddon:ResonantCoreSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.acousticNullRadius` | `100` | int | Radius in blocks for Acoustic Nullification. | `traddon:SilentiumSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.acousticNullDuration` | `200` | int | Duration in ticks for Acoustic Nullification. | `traddon:SilentiumSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.acousticNullCooldown` | `400` | int | Cooldown in ticks for Acoustic Nullification. | `traddon:SilentiumSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.vibroEdgeDuration` | `200` | int | Attack boost duration for Vibro-Edge (ticks). | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.vibroEdgeAmplifier` | `2` | int | Attack boost amplifier for Vibro-Edge. | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `silentium.echoLocationDuration` | `400` | int | Night vision duration for Echo Location (ticks). | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.mpAcquirement` | `70000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.spellLearnChance` | `0.2` | float | Chance to learn a spell upon being hit by it (Analytical Mir | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.maxStoredMagicules` | `2.0` | float | Maximum stored magicules from absorbed spells (Energy Conden | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.maxStoredDamage` | `2000.0` | float | Maximum stored damage from damage taken (Prismatic Fracture) | `traddon:TotalReflectionSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.prismaticFractureCooldown` | `30` | int | Cooldown in ticks for Prismatic Fracture active mode. | `traddon:TotalReflectionSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.maxRestoredPerSecond` | `0.1` | float | Maximum amount of magicules restored per second from stored  | `traddon:TotalReflectionSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.moveSpeed` | `-0.2` | float | Movement speed multiplier when Full Vector Reversal is activ | `cataclysm:Amethyst_Crab_Entity`、`cataclysm:AttackMoveGoal`、`cataclysm:CmAttackGoal` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.vectorReversalMagiculeCost` | `50.0` | float | Magicule cost per point of damage reflected in Full Vector R | `traddon:TotalReflectionSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.vectorReversalMaintainCost` | `50.0` | float | Magicule cost per second to maintain Full Vector Reversal. | `traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `TotalReflection.prismaticFractureCost` | `100.0` | float | Magicule cost for Prismatic Fracture. | `traddon:TotalReflectionSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `origin.meleeDodge` | `0.2` | float | The melee dodge chance bonus granted by the Origin skill. | `tensura:AllSeeingEyeSkill`、`tensura:AllyBoostEffect`、`tensura:CommanderSkill` |
| `traddon/ability/skill/unique_config.toml` | `origin.projectileDodge` | `0.2` | float | The projectile dodge chance bonus granted by the Origin skil | `tensura:AllSeeingEyeSkill`、`tensura:AllyBoostEffect`、`tensura:CommanderSkill` |
| `traddon/ability/skill/unique_config.toml` | `origin.dodgeNegation` | `0.2` | float | The dodge negation chance bonus granted by the Origin skill. | `tensura:CommanderSkill`、`tensura:CookSkill`、`tensura:MathematicianSkill` |
| `traddon/ability/skill/unique_config.toml` | `origin.mpAcquirement` | `500.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `origin.learningPoint` | `9.0` | float | The learning points bonus granted by the Origin skill. | `mysticism:AbilityHandler`、`mysticism:ScholarSkill`、`mysticism:UniqueMysticismSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `origin.masteryPoint` | `9.0` | float | The mastery points bonus granted by the Origin skill. | `mysticism:AbilityHandler`、`mysticism:ScholarSkill`、`mysticism:UniqueMysticismSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `origin.auraPercentage` | `2.5` | float | Extra Aura gain percentage granted by the Origin skill. | `tensura:BerserkerSkill`、`tensura:GourmandSkill`、`tensura:UniqueSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `origin.magiculePercentage` | `2.5` | float | Extra Magicule gain percentage granted by the Origin skill. | `mysticism:CultivatorSkill`、`mysticism:UniqueMysticismSkillConfig`、`tensura:BerserkerSkill` |
| `traddon/ability/skill/unique_config.toml` | `origin.regenLevel` | `5` | int | The level of the Self Regeneration effect granted by the Ori | `tensura:CommonSkillConfig`、`tensura:SelfRegenerationSkill`、`traddon:OriginSkill` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.mpAcquirement` | `45000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.maxMinions` | `5` | int | The maximum number of summoned minions allowed at one time. | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.summonCost` | `50` | int | The Cost in Souls to summon a minion. | `tensura_tno:SpiritSummonSkill`、`tensuramoreskills:MoreSkillsConfigs`、`traddon:NecromancerSkill` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.minionHealth` | `20.0` | float | The health for summoned minions. | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.minionAttackDamage` | `6.0` | float | The attack damage for summoned minions. | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.minionSpeed` | `0.25` | float | The speed for summoned minions. | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.minionArmor` | `10.0` | float | The armor for summoned minions. | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `necromancer.summonedDeathSoulReturn` | `25.0` | float | The Soul Points returned when a summoned minion dies (as a m | `traddon:NecromancerSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.mpAcquirement` | `6000.0` | float | The MP cost to acquire the Spatial Samurai skill. | `mysticism:ButcherSkill`、`mysticism:CoalescenceSkill`、`mysticism:ConstantSkill` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.cleaveMaxHealthMultiplier` | `0.15` | float | The maximum health multiplier used to calculate the Cleave e | `traddon:SpatialSamuraiSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.cleaveMagiculePerDamage` | `100.0` | float | The magicule cost per point of extra damage for the Cleave e | `traddon:SpatialSamuraiSkill`、`traddon:TrAddonSkillConfig` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.projectileDamage` | `8.0` | float | The damage of the Severance Cutter projectile. | `tensura:AbsoluteSeveranceSkill`、`tensura:AspectualMagicConfig`、`tensura:FloatMagic` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.projectileDamageMastered` | `12.0` | float | The damage of the Severance Cutter projectile when mastered. | `tensura:AbsoluteSeveranceSkill`、`tensura:UniqueSkillConfig`、`traddon:SpatialSamuraiSkill` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.projectileSize` | `0.5` | float | The size of the Severance Cutter projectile. | `tensura:AbsoluteSeveranceSkill`、`tensura:UniqueSkillConfig`、`traddon:SpatialSamuraiSkill` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.projectileSizeMastered` | `0.75` | float | The size of the Severance Cutter projectile when mastered. | `tensura:AbsoluteSeveranceSkill`、`tensura:UniqueSkillConfig`、`traddon:SpatialSamuraiSkill` |
| `traddon/ability/skill/unique_config.toml` | `spatialSamurai.projectileCooldown` | `100` | int | The cooldown of the Severance Cutter projectile. | `tensura:AbsoluteSeveranceSkill`、`tensura:UniqueSkillConfig`、`traddon:SpatialSamuraiSkill` |
