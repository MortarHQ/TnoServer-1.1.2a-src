---
页面: configs/artifacts
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `artifacts` 配置项

> **3 个文件 / 164 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/artifacts](../../mods/artifacts/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `artifacts/client.toml` | `cooldownOverlayOffset` | `10` | int | Range: -2147483648 ~ 2147483647 | `artifacts:ClientConfig`、`artifacts:CooldownOverlayRenderer` |
| `artifacts/client.toml` | `enableCooldownOverlay` | `True` | bool | Allowed Values: true, false | `artifacts:ClientConfig`、`artifacts:CooldownOverlayRenderer` |
| `artifacts/client.toml` | `heliumFlamingoOverlayOffset` | `0` | int | Range: -2147483648 ~ 2147483647 | `artifacts:ClientConfig`、`artifacts:HeliumFlamingoOverlay` |
| `artifacts/client.toml` | `showFirstPersonGloves` | `True` | bool | Allowed Values: true, false | `artifacts:ArmRenderHandler`、`artifacts:ClientConfig` |
| `artifacts/client.toml` | `showTooltips` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactItem`、`artifacts:ClientConfig`、`artifacts:ItemStackMixin` |
| `artifacts/client.toml` | `useModdedMimicTextures` | `True` | bool | Allowed Values: true, false | `artifacts:ClientConfig`、`artifacts:MimicChestLayer` |
| `artifacts/items.toml` | `anglers_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `anglers_hat.luckOfTheSeaLevelBonus` | `1` | int | Range: 0 ~ 100 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `anglers_hat.lureLevelBonus` | `1` | int | Range: 0 ~ 100 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `antidote_vessel.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `antidote_vessel.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `antidote_vessel.maxEffectDuration` | `5` | int | Range: 0 ~ 72000 | `artifacts:CureEffects`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `aqua_dashers.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `aqua_dashers.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `bunny_hoppers.fallDamageMultiplier` | `0.0` | float | Range: 0.0 ~ 1.0 | `artifacts:DoubleJump`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `bunny_hoppers.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `bunny_hoppers.jumpStrengthBonus` | `0.4` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `bunny_hoppers.modifyHurtSounds` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `bunny_hoppers.safeFallDistanceBonus` | `10.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `charm_of_shrinking.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `charm_of_shrinking.scaleModifier` | `-0.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs`、`friendsandfoes:CrabEntity`、`friendsandfoes:CrabEntityModel` |
| `artifacts/items.toml` | `charm_of_sinking.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `charm_of_sinking.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `charm_of_sinking.oxygenBonus` | `1.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `charm_of_sinking.underwaterFallDamage` | `False` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `chorus_totem.consumeOnUse` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs`、`yigd:DeathScrollItem`、`yigd:ExtraFeaturesConfig` |
| `artifacts/items.toml` | `chorus_totem.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `chorus_totem.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `chorus_totem.healthRestored` | `10` | int | Range: > 0 | `artifacts:DeathProtectionTeleport`、`artifacts:ItemConfigs`、`artifacts:LivingEntityMixin` |
| `artifacts/items.toml` | `chorus_totem.teleportationChance` | `1.0` | float | Range: 0.0 ~ 1.0 | `artifacts:DeathProtectionTeleport`、`artifacts:ItemConfigs`、`artifacts:LivingEntityMixin` |
| `artifacts/items.toml` | `cloud_in_a_bottle.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `cloud_in_a_bottle.fallDamageMultiplier` | `0.0` | float | Range: 0.0 ~ 1.0 | `artifacts:DoubleJump`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cloud_in_a_bottle.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `cloud_in_a_bottle.safeFallDistanceBonus` | `3.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cloud_in_a_bottle.sprintJumpHorizontalVelocity` | `0.25` | float | Range: > 0.0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cloud_in_a_bottle.sprintJumpVerticalVelocity` | `0.25` | float | Range: > 0.0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cowboy_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `cowboy_hat.mountSpeedBonus` | `0.4` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cross_necklace.bonusInvincibilityTicks` | `20.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `cross_necklace.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `cross_necklace.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `crystal_heart.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `crystal_heart.healthBonus` | `10.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs`、`tensura_tno:SpiritEnhancementSkill` |
| `artifacts/items.toml` | `digging_claws.blockBreakSpeedBonus` | `0.3` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `digging_claws.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `digging_claws.toolTier` | `stone` | str | Allowed Values: none, wood, stone, iron, diamond, netherite | `artifacts:ItemConfigs`、`kubejs:DiggerItemBuilder`、`kubejs:HandheldItemBuilder` |
| `artifacts/items.toml` | `eternal_steak.cooldown` | `15` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `eternal_steak.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `everlasting_beef.cooldown` | `15` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `everlasting_beef.dropRate` | `0.002` | float | Range: 0.0 ~ 1.0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `everlasting_beef.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `everlasting_beef.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `feral_claws.attackSpeedBonus` | `0.3` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs`、`foxablazeultimate:RaphaelConfig`、`foxablazeultimate:RaphaelSkill` |
| `artifacts/items.toml` | `feral_claws.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `fire_gauntlet.fireDuration` | `8.0` | float | Range: 0 ~ 72000 | `artifacts:FireEffect`、`artifacts:ItemConfigs`、`mysticism:SparkSkill` |
| `artifacts/items.toml` | `fire_gauntlet.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `flame_pendant.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `flame_pendant.fireDuration` | `10` | int | Range: 0 ~ 72000 | `artifacts:FireEffect`、`artifacts:ItemConfigs`、`mysticism:SparkSkill` |
| `artifacts/items.toml` | `flame_pendant.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `flame_pendant.grantFireResistance` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `flame_pendant.strikeChance` | `0.4` | float | Range: 0.0 ~ 1.0 | `artifacts:FireEffect`、`artifacts:ItemConfigs`、`artifacts:LightningEffect` |
| `artifacts/items.toml` | `flippers.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `flippers.swimSpeedBonus` | `0.7` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `golden_hook.entityExperienceBonus` | `0.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `golden_hook.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `helium_flamingo.cooldown` | `3` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `helium_flamingo.flightDuration` | `8` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs`、`artifacts:SwimData`、`artifacts:SwimInAir` |
| `artifacts/items.toml` | `helium_flamingo.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `helium_flamingo.rechargeDuration` | `15` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs`、`artifacts:SwimData`、`artifacts:SwimInAir` |
| `artifacts/items.toml` | `kitty_slippers.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `kitty_slippers.modifyHurtSounds` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `kitty_slippers.repelCreepers` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `kitty_slippers.repelPhantoms` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `lucky_scarf.fortuneLevelBonus` | `1` | int | Range: 0 ~ 100 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `lucky_scarf.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `night_vision_goggles.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `night_vision_goggles.strength` | `0.15` | float | Range: 0.0 ~ 1.0 | `artifacts:ItemConfigs`、`block_factorys_bosses:BigChainBlock`、`block_factorys_bosses:BoneRemainsBlock` |
| `artifacts/items.toml` | `novelty_drinking_hat.drinkingSpeedBonus` | `1.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `novelty_drinking_hat.eatingSpeedBonus` | `0.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `novelty_drinking_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `obsidian_skull.cooldown` | `60` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `obsidian_skull.fireResistanceDuration` | `30` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `obsidian_skull.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `onion_ring.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `onion_ring.hasteDurationPerFoodPoint` | `6` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `onion_ring.hasteLevel` | `2` | int | Range: 0 ~ 256 | `artifacts:ItemConfigs`、`tensura:AgilityMagic`、`tensura:AspectualMagicConfig` |
| `artifacts/items.toml` | `panic_necklace.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `panic_necklace.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `panic_necklace.speedDuration` | `8` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs`、`tensura:AgilityMagic`、`tensura:AspectualMagicConfig` |
| `artifacts/items.toml` | `panic_necklace.speedLevel` | `1` | int | Range: 0 ~ 256 | `artifacts:ItemConfigs`、`tensura:AgilityMagic`、`tensura:AspectualMagicConfig` |
| `artifacts/items.toml` | `pickaxe_heater.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `pickaxe_heater.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `plastic_drinking_hat.drinkingSpeedBonus` | `1.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `plastic_drinking_hat.eatingSpeedBonus` | `0.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `plastic_drinking_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `pocket_piston.attackKnockbackBonus` | `0.75` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `pocket_piston.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `power_glove.attackDamageBonus` | `4.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs`、`crafttweaker:ExpandTier`、`kubejs:HandheldItemBuilder` |
| `artifacts/items.toml` | `power_glove.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `rooted_boots.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `rooted_boots.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `rooted_boots.growPlantsAfterEating` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `rooted_boots.hungerReplenishingDuration` | `10` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `running_shoes.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `running_shoes.sprintingSpeedBonus` | `0.4` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `running_shoes.sprintingStepHeightBonus` | `0.5` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `scarf_of_invisibility.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `scarf_of_invisibility.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `scarf_of_invisibility.hideWhenInvisible` | `False` | bool | Allowed Values: true, false | `artifacts:ArtifactRenderer`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `shock_pendant.cancelLightningDamage` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `shock_pendant.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `shock_pendant.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `shock_pendant.strikeChance` | `0.25` | float | Range: 0.0 ~ 1.0 | `artifacts:FireEffect`、`artifacts:ItemConfigs`、`artifacts:LightningEffect` |
| `artifacts/items.toml` | `snorkel.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `snorkel.isInfinite` | `False` | bool | Allowed Values: true, false | `artifacts:ItemConfigs`、`artifacts:TooltipHelper`、`cataclysm:Cursed_bow` |
| `artifacts/items.toml` | `snorkel.waterBreathingDuration` | `30` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `snowshoes.allowWalkingOnPowderedSnow` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `snowshoes.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `snowshoes.movementSpeedOnSnowBonus` | `0.3` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `steadfast_spikes.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `steadfast_spikes.knockbackResistance` | `1.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs`、`cataclysm:CMCommonConfig`、`cataclysm:Cataclysm_Armor` |
| `artifacts/items.toml` | `steadfast_spikes.slipperinessReduction` | `1.0` | float | Range: -Infinity ~ Infinity | `artifacts:ArtifactHooks`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `strider_shoes.cancelHotFloorDamage` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `strider_shoes.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `strider_shoes.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `superstitious_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `superstitious_hat.lootingLevelBonus` | `1` | int | Range: 0 ~ 100 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `thorn_pendant.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `thorn_pendant.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `thorn_pendant.maxDamage` | `6` | int | Range: > 0 | `artifacts:ItemConfigs`、`artifacts:ThornsEffect`、`chronodawn:AnvilRepairHandler` |
| `artifacts/items.toml` | `thorn_pendant.minDamage` | `2` | int | Range: > 0 | `artifacts:ItemConfigs`、`artifacts:ThornsEffect`、`crafttweaker:ConditionDamagedAtLeast` |
| `artifacts/items.toml` | `thorn_pendant.strikeChance` | `0.5` | float | Range: 0.0 ~ 1.0 | `artifacts:FireEffect`、`artifacts:ItemConfigs`、`artifacts:LightningEffect` |
| `artifacts/items.toml` | `umbrella.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `umbrella.isGlider` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `umbrella.isShield` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs`、`balm:BalmHooks`、`balm:NeoForgeBalmHooks` |
| `artifacts/items.toml` | `universal_attractor.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `universal_attractor.magnetismLevel` | `5` | int | Range: 0 ~ 256 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `vampiric_glove.absorptionChance` | `1.0` | float | Range: 0.0 ~ 1.0 | `artifacts:ArtifactHooks`、`artifacts:DamageAbsorption`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `vampiric_glove.absorptionRatio` | `0.2` | float | Range: > 0.0 | `artifacts:ArtifactHooks`、`artifacts:DamageAbsorption`、`artifacts:ItemConfigs` |
| `artifacts/items.toml` | `vampiric_glove.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `vampiric_glove.maxHealingPerHit` | `6` | int | Range: > 0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `villager_hat.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `villager_hat.reputationBonus` | `75.0` | float | Range: -Infinity ~ Infinity | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `warp_drive.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `warp_drive.enabled` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `artifacts/items.toml` | `warp_drive.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `warp_drive.hungerCost` | `2` | int | Range: > 0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `warp_drive.nullifyEnderPearlDamage` | `True` | bool | Allowed Values: true, false | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `whoopee_cushion.fartChance` | `0.12` | float | Range: 0.0 ~ 1.0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `whoopee_cushion.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `withered_bracelet.cooldown` | `0` | int | Range: 0 ~ 72000 | `artifacts:ArtifactsConfigScreen`、`artifacts:AttackEffects`、`artifacts:ClientConfig` |
| `artifacts/items.toml` | `withered_bracelet.generateAsLoot` | `True` | bool | Allowed Values: true, false | `artifacts:ArtifactsConfigScreen`、`artifacts:ItemConfigs`、`artifacts:Language` |
| `artifacts/items.toml` | `withered_bracelet.witherChance` | `0.3` | float | Range: 0.0 ~ 1.0 | `artifacts:ItemConfigs` |
| `artifacts/items.toml` | `withered_bracelet.witherDuration` | `8` | int | Range: 0 ~ 72000 | `artifacts:ItemConfigs`、`netherman:AzazelSpikeEntity`、`netherman:AzazelSpikesProjectileEntity` |
| `artifacts/items.toml` | `withered_bracelet.witherLevel` | `2` | int | Range: 0 ~ 256 | `artifacts:ItemConfigs`、`tensura:DarknessCannonProjectile`、`tensura:SpiritualMagicConfig` |
| `artifacts/general.toml` | `archaeologyChance` | `0.0625` | float | Range: 0.0 ~ 1.0 | `artifacts:ConfigValueChance`、`artifacts:GeneralConfig`、`artifacts:LootModifiers` |
| `artifacts/general.toml` | `artifactRarity` | `1.0` | float | Range: > 0.0 | `artifacts:ArtifactRarityAdjustedChance`、`artifacts:GeneralConfig` |
| `artifacts/general.toml` | `entityEquipmentChance` | `0.0015` | float | Range: 0.0 ~ 1.0 | `artifacts:ConfigValueChance`、`artifacts:EntityEquipment`、`artifacts:GeneralConfig` |
| `artifacts/general.toml` | `campsite.allowLightSources` | `True` | bool | Allowed Values: true, false | `artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
| `artifacts/general.toml` | `campsite.campsiteCount` | `40` | int | Range: > 0 | `artifacts:CampsiteCountPlacement`、`artifacts:GeneralConfig`、`artifacts:PlacedFeatures` |
| `artifacts/general.toml` | `campsite.maxY` | `40` | int | Range: -2147483648 ~ 2147483647 | `artifacts:CampsiteHeightRangePlacement`、`artifacts:GeneralConfig`、`betterdeserttemples:BoundingBoxAccessor` |
| `artifacts/general.toml` | `campsite.mimicChance` | `0.3` | float | Range: 0.0 ~ 1.0 | `artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
| `artifacts/general.toml` | `campsite.minY` | `-60` | int | Range: -2147483648 ~ 2147483647 | `artifacts:CampsiteHeightRangePlacement`、`artifacts:GeneralConfig`、`betterdeserttemples:BoundingBoxAccessor` |
| `artifacts/general.toml` | `campsite.useModdedChests` | `True` | bool | Allowed Values: true, false | `artifacts:CampsiteFeature`、`artifacts:GeneralConfig` |
