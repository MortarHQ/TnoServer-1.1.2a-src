---
页面: configs/stextras
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `stextras` 配置项

> **11 个文件 / 219 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/stextras](../../mods/stextras/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `stextras/general_config.toml` | `versionControl` | `True` | bool | If true, dedicated servers require clients to report the sam | `stextras:STGeneralConfig`、`stextras:VersionControl` |
| `stextras/general_config.toml` | `versionCheckTimeOut` | `30000` | int | How long the server waits for a client-initiated version rep | `stextras:STGeneralConfig`、`stextras:VersionControl` |
| `stextras/prestige_config.toml` | `questREBoostDaily` | `True` | bool | If true, daily quest RE rewards scale with prestige. | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `allowRepeatedPrestigeBossKills` | `True` | bool | If true, the same boss can be killed repeatedly for prestige | `stextras:PrestigeUtils`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questPriority` | `True` | bool | If true, matching daily, weekly, required, and race prestige | `stextras:HandlePrestigeListeners`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `ImmediateRaceQuests` | `False` | bool | If true, race prestige quests are applied as soon as the pla | `stextras:HandlePrestigeListeners`、`stextras:RacePrestigeAssignmentService`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `disabledRacePrestigeRaces` | `[]` | list | Race ids to disable from race prestige entirely. Disabled ra | `stextras:DebugCommands`、`stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questsUseSmallCaps` | `True` | bool | Should the QuestDefinition messages use small caps. | `stextras:HandlePrestigeListeners`、`stextras:RaceMessages`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questREBoostWeekly` | `True` | bool | If true, weekly quest RE rewards scale with prestige. | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questREBoost` | `True` | bool | Should the quests RE rewards boost based on the players pres | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `validBosses` | `["tensura:charybdis", "tensura:elemental` | list | List of valid bosses that count to your required boss kills. | `stextras:PrestigeUtils`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `prestigeQuestRerollMode` | `NONE` | str | Which quest schedules reroll when a player prestiges. Valid  | `stextras:PrestigeUtils`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questREBoostRepeatable` | `False` | bool | If true, repeatable quest RE rewards scale with prestige. | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questREBoostAmount` | `0.05` | float | The percentage that the RE rewards will boost based on the p | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `overrideSoulGradeLevels` | `False` | bool | If true, use the configured soul grade thresholds below inst | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `additionalSoulGradeForAutoLevels` | `0` | int | Additional soul grade added to the auto-calculated total whe | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `soulGradeLevelThresholds` | `[20, 40, 60, 80, 100, 120]` | list | Soul grade needed for levels 1 through 6 when overrideSoulGr | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `questREBoostRequired` | `False` | bool | If true, required quest RE rewards scale with prestige. | `stextras:STExtarsStorage`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `skipDupe` | `True` | bool | If true, quests that are similor be skipped until a differen | `stextras:QuestDuplicateSignatures`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `killOwnedEntities` | `False` | bool | If entities that are owned by you or other players will cont | `stextras:HandlePrestigeListeners`、`stextras:STPrestigeConfig` |
| `stextras/prestige_config.toml` | `ignoreLockedSkillsOnReset` | `False` | bool | Prevents locked skills from reducing the number of unique sk | `stextras:ReincarnationMenuMixin`、`stextras:STPrestigeConfig` |
| `stextras/skill_lock_config.toml` | `getNeededEPToUnlockSkill` | `10000000.0` | float | The amount of EP needed to unlock a skill. | `stextras:STExtarsStorage`、`stextras:STSkillLockConfig` |
| `stextras/skill_lock_config.toml` | `validSkillsTOLock` | `["tensura:absolute_severance", "tensura:` | list | List of valid skills to lock. | `stextras:STExtarsStorage`、`stextras:STSkillLockConfig` |
| `stextras/skill_lock_config.toml` | `getSoulGradeCostToUnlockSkill` | `3` | int | The Soul Grades Cost to unlock a skill. | `stextras:STExtarsStorage`、`stextras:STSkillLockConfig` |
| `stextras/skill_lock_config.toml` | `maxLocksPerLevel` | `[0, 1, 1, 2, 2, 2, 3]` | list | Index 0 = Level 0, Index 1 = Level 1, etc. | `stextras:STExtarsStorage`、`stextras:STSkillLockConfig` |
| `stextras/skill_lock_config.toml` | `RestSkillData` | `False` | bool | If the data in locked skill should be reset | `stextras:HandleSkillLock`、`stextras:STSkillLockConfig` |
| `stextras/skill_lock_config.toml` | `RestSkillDataSkill` | `["tensura:predator", "tensura:gourmet", ` | list | If RestSkillData is false you can list Specific skill you do | `stextras:HandleSkillLock`、`stextras:STSkillLockConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.maxAura` | `13000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.maxSpiritualHealth` | `0.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.epRequirement` | `20000.0` | float | EP requirement to evolve into Divine Kobold. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.minMagicule` | `7000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.movementSpeed` | `0.03999999910593033` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.minAura` | `13000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.attack` | `0.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.attackSpeed` | `0.5` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.maxHealth` | `32.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.maxMagicule` | `7000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldTrader.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.maxAura` | `240000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.maxSpiritualHealth` | `3000.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.epRequirement` | `400000.0` | float | EP requirement to evolve into Divine Kobold. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.minMagicule` | `160000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.movementSpeed` | `0.10000000149011612` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.minAura` | `240000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.attack` | `2.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.attackSpeed` | `0.09000000357627869` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.maxHealth` | `255.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.maxMagicule` | `160000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldChieftain.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.maxAura` | `130000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.maxSpiritualHealth` | `300.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.epRequirement` | `100000.0` | float | EP requirement to evolve into Divine Kobold. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.minMagicule` | `70000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.movementSpeed` | `0.05999999865889549` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.minAura` | `130000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.attack` | `1.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.attackSpeed` | `0.20000000298023224` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.maxHealth` | `62.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.maxMagicule` | `70000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `KoboldWarrior.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/kobold_config.toml` | `Kobold.maxAura` | `800.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `Kobold.maxSpiritualHealth` | `0.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `Kobold.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/kobold_config.toml` | `Kobold.minAura` | `500.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `Kobold.minMagicule` | `300.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `Kobold.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/kobold_config.toml` | `Kobold.attack` | `-0.20000000298023224` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/kobold_config.toml` | `Kobold.attackSpeed` | `-0.5` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/kobold_config.toml` | `Kobold.maxHealth` | `-2.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/kobold_config.toml` | `Kobold.movementSpeed` | `0.009999999776482582` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/kobold_config.toml` | `Kobold.maxMagicule` | `500.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `Kobold.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/kobold_config.toml` | `DivineKobold.maxAura` | `1300000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `DivineKobold.maxSpiritualHealth` | `6000.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `DivineKobold.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/kobold_config.toml` | `DivineKobold.epRequirement` | `2000000.0` | float | EP requirement to evolve into Divine Kobold. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/kobold_config.toml` | `DivineKobold.minMagicule` | `700000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `DivineKobold.movementSpeed` | `0.12999999523162842` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/kobold_config.toml` | `DivineKobold.minAura` | `1300000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/kobold_config.toml` | `DivineKobold.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/kobold_config.toml` | `DivineKobold.attack` | `3.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/kobold_config.toml` | `DivineKobold.attackSpeed` | `1.0` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/kobold_config.toml` | `DivineKobold.maxHealth` | `1080.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/kobold_config.toml` | `DivineKobold.maxMagicule` | `700000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/kobold_config.toml` | `DivineKobold.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/wispfolk_config.toml` | `blacklistedDimensions` | `["tensura:labyrinth"]` | list | Dimensions where Wisp Mode phasing is disabled. | `stextras:WispModeEffect`、`stextras:WispfolkConfig` |
| `stextras/race/wispfolk_config.toml` | `opacSupport` | `False` | bool | If true and Open Parties and Claims is installed, Wisp Mode  | `stextras:WispModeEffect`、`stextras:WispfolkConfig` |
| `stextras/race/wispfolk_config.toml` | `blacklistedBlocks` | `["minecraft:bedrock"]` | list | Blocks Wisp Mode cannot phase through. These blocks render r | `stextras:WispModeEffect`、`stextras:WispfolkConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.maxAura` | `2000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.maxSpiritualHealth` | `300.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.epRequirement` | `100000.0` | float | EP requirement to evolve into Zephyr Wisp. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.minMagicule` | `5000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.movementSpeed` | `0.03999999910593033` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.minAura` | `2000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.attack` | `-0.5` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.attackSpeed` | `0.0` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.maxHealth` | `30.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.maxMagicule` | `5000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `GaleWisp.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.maxAura` | `1000000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.maxSpiritualHealth` | `6000.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.epRequirement` | `2000000.0` | float | EP requirement to evolve into Zephyr Wisp. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.minMagicule` | `1000000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.movementSpeed` | `0.11999999731779099` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.minAura` | `1000000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.size` | `-0.15000000596046448` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.attack` | `6.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.attackSpeed` | `1.0` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.maxHealth` | `980.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.maxMagicule` | `1000000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `DivineWisp.knockbackResistance` | `0.20000000298023224` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.maxAura` | `1000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.maxSpiritualHealth` | `0.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.minAura` | `1000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.minMagicule` | `3000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.size` | `-0.25` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.attack` | `-0.699999988079071` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.attackSpeed` | `0.0` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.maxHealth` | `0.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.movementSpeed` | `0.029999999329447746` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.maxMagicule` | `3000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `Wispfolk.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.maxAura` | `150000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.maxSpiritualHealth` | `3000.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.epRequirement` | `400000.0` | float | EP requirement to evolve into Zephyr Wisp. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.minMagicule` | `250000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.movementSpeed` | `0.07000000029802322` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.minAura` | `150000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.size` | `-0.15000000596046448` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.attack` | `4.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.attackSpeed` | `0.5` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.maxHealth` | `580.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.maxMagicule` | `250000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/wispfolk_config.toml` | `ZephyrWisp.knockbackResistance` | `0.10000000149011612` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.maxAura` | `600.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.maxSpiritualHealth` | `0.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.minAura` | `200.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.minMagicule` | `1000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.attack` | `0.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.attackSpeed` | `-0.5` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.maxHealth` | `12.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.movementSpeed` | `0.019999999552965164` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.maxMagicule` | `3000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `MysticFox.knockbackResistance` | `0.0` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.maxAura` | `1000000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.maxSpiritualHealth` | `7000.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.epRequirement` | `2000000.0` | float | EP requirement to evolve into Ninetail. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.minMagicule` | `1000000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.movementSpeed` | `0.10000000149011612` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.minAura` | `1000000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.attack` | `6.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.attackSpeed` | `1.100000023841858` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.maxHealth` | `980.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.maxMagicule` | `1000000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `DivineFox.knockbackResistance` | `0.30000001192092896` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.maxAura` | `30000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.maxSpiritualHealth` | `440.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.epRequirement` | `100000.0` | float | EP requirement to evolve into Ninetail. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.minMagicule` | `70000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.movementSpeed` | `0.03999999910593033` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.minAura` | `30000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.attack` | `3.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.attackSpeed` | `0.0` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.maxHealth` | `160.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.maxMagicule` | `70000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `SoulBeast.knockbackResistance` | `0.10000000149011612` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.maxAura` | `70000.0` | float | Maximum aura. | `foxablazeultimate:AzathothDragons`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.maxSpiritualHealth` | `4500.0` | float | Bonus Max Spiritual Health. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.swimSpeed` | `0.0` | float | Bonus Swimming Speed Multiplier. | `cataclysm:Coral_Golem_Model`、`cataclysm:Deepling_Angler_Model`、`cataclysm:Deepling_Brute_Model` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.epRequirement` | `400000.0` | float | EP requirement to evolve into Ninetail. | `mysticism:AmpedGuitarWolfRace`、`mysticism:AngelConfig`、`mysticism:AntConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.minMagicule` | `330000.0` | float | Minimal magicule. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.movementSpeed` | `0.07999999821186066` | float | Bonus Movement Speed. | `iceandfire:AquaticAIGetInWaterGoal`、`iceandfire:AquaticAIGetOutOfWaterGoal`、`iceandfire:DeathWormAIGetInSandGoal` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.bossRequirement` | `4` | int | The number of Bosses defeated to evolve into Ninetail. | `mysticism:AntConfig`、`mysticism:ArmyWaspSavantRace`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.minAura` | `70000.0` | float | Minimal aura. | `mysticism:AngelConfig`、`mysticism:AntConfig`、`mysticism:BeetleConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.size` | `0.0` | float | Bonus Size. | `ancient_artifacts:AncientArtifacts`、`ancient_artifacts:ArtifactText`、`ancient_artifacts:RefinementComponent` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.attack` | `5.0` | float | Bonus Attack Damage. | `ancient_artifacts:PlayerMixin`、`architectury:EventHandlerImplCommon`、`architectury:PlayerEvent` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.attackSpeed` | `0.5` | float | Bonus Attack Speed. | `cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Weapon`、`cataclysm:CommonConfig` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.maxHealth` | `300.0` | float | Bonus Max Health. | `block_factorys_bosses:KrakenEntity`、`cataclysm:ClientEvent`、`crafttweaker:ExpandLivingEntity` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.maxMagicule` | `330000.0` | float | Maximum magicule. | `foxablazeultimate:VelgryndSkill`、`mortal_cultivation:GreatSageSkill`、`mortal_cultivation:MischiefSkill` |
| `stextras/race/mystic_fox_config.toml` | `Ninetail.knockbackResistance` | `0.20000000298023224` | float | Bonus Knockback Resistance. | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `stextras/ability/skill/intrinsic_config.toml` | `Blacksmith.cooldownMastered` | `30` | int | The cooldown in seconds when the skill is mastered. | `foxablazeultimate:FortunaSkill`、`mysticism:BulletPunchSkill`、`mysticism:ClangorousSoulSkill` |
| `stextras/ability/skill/intrinsic_config.toml` | `Blacksmith.cooldown` | `60` | int | The cooldown in seconds. | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `stextras/ability/skill/unique_config.toml` | `Imitator.entries` | `["tensura:hinata_sakaguchi/BOSS/As the s` | list | List of entities. Format: 'entity_id/TYPE/Description/skill1 | `ancient_artifacts:AncientArtifacts`、`architectury:ClientTooltipComponentRegistryImpl`、`architectury:DeferredRegister` |
| `stextras/ability/skill/unique_config.toml` | `Imitator.cooldown` | `1800` | int | The cooldown in seconds for the Imitator skill. | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.cooldownMastered` | `30` | int | The cooldown in seconds when the skill is mastered. | `foxablazeultimate:FortunaSkill`、`mysticism:BulletPunchSkill`、`mysticism:ClangorousSoulSkill` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.hold_duration` | `10` | int | The duration that you can hold the skill in seconds. | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.cooldown` | `60` | int | The cooldown in seconds. | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.hold_duration_mastered` | `15` | int | The duration that you can hold the skill in seconds if maste | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.magicule_cost` | `15.0` | float | The magicule cost per second. | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.xray_radius` | `25` | int | The block xray radius used by Keen Nose. | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.xray_radius_amplifier_multiplier` | `0.2` | float | How much each Keen Nose effect amplifier increases the block | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig` |
| `stextras/ability/skill/extra_config.toml` | `KeenNose.block_outlines` | `["minecraft:coal_ore,minecraft:deepslate` | list | Blocks Keen Nose outlines. Format: 'block_id[,block_id...]/# | `stextras:KeenNoseSkill`、`stextras:STExtrasExtraSkillConfig`、`tensura_tno:STExtrasKeenNoseNeoForgeCompat` |
| `stextras/internal/tensura_config_patcher_state.toml` | `startingRaces.applied` | `["stextras:kobold", "stextras:wispfolk",` | list | Values STExtras has already injected into Tensura at least o | `artifacts:ItemConfigs`、`betterdeserttemples:ConfigGeneralNeoForge`、`bookshelf:LootModificationHandler` |
| `stextras/internal/tensura_config_patcher_state.toml` | `startingRaces.optOut` | `["stextras:kobold", "stextras:wispfolk",` | list | Values that were auto-added once, then manually removed from | `stextras:TensuraConfigPatcher`、`stextras:TensuraConfigPatcherStateConfig` |
| `stextras/internal/tensura_config_patcher_state.toml` | `randomRaces.applied` | `["stextras:kobold", "stextras:wispfolk",` | list | Values STExtras has already injected into Tensura at least o | `artifacts:ItemConfigs`、`betterdeserttemples:ConfigGeneralNeoForge`、`bookshelf:LootModificationHandler` |
| `stextras/internal/tensura_config_patcher_state.toml` | `randomRaces.optOut` | `["stextras:kobold", "stextras:wispfolk",` | list | Values that were auto-added once, then manually removed from | `stextras:TensuraConfigPatcher`、`stextras:TensuraConfigPatcherStateConfig` |
| `stextras/internal/tensura_config_patcher_state.toml` | `startingSkills.applied` | `["stextras:imitator"]` | list | Values STExtras has already injected into Tensura at least o | `artifacts:ItemConfigs`、`betterdeserttemples:ConfigGeneralNeoForge`、`bookshelf:LootModificationHandler` |
| `stextras/internal/tensura_config_patcher_state.toml` | `startingSkills.optOut` | `[]` | list | Values that were auto-added once, then manually removed from | `stextras:TensuraConfigPatcher`、`stextras:TensuraConfigPatcherStateConfig` |
| `stextras/internal/tensura_config_patcher_state.toml` | `creatorUniqueSkills.applied` | `[]` | list | Values STExtras has already injected into Tensura at least o | `artifacts:ItemConfigs`、`betterdeserttemples:ConfigGeneralNeoForge`、`bookshelf:LootModificationHandler` |
| `stextras/internal/tensura_config_patcher_state.toml` | `creatorUniqueSkills.optOut` | `[]` | list | Values that were auto-added once, then manually removed from | `stextras:TensuraConfigPatcher`、`stextras:TensuraConfigPatcherStateConfig` |
| `stextras/client/general.json` | `loadingScreen.enabled` | `False` | bool |  | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `stextras/client/general.json` | `loadingScreen.animatedBackground` | `False` | bool |  | `stextras:STExtrasClientConfig` |
