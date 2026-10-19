package com.github.L_Ender.cataclysm.config;

import com.github.L_Ender.cataclysm.Cataclysm;

public class CMCommonConfig {
   public static void Commonbake(CommonConfig config) {
      try {
         CMCommonConfig.EnderGuardian.healthMultiplier = (Double)config.MOBS.ENDERGUARDIAN.combatConfig.healthMultiplier.get();
         CMCommonConfig.EnderGuardian.attackMultiplier = (Double)config.MOBS.ENDERGUARDIAN.combatConfig.attackMultiplier.get();
         CMCommonConfig.EnderGuardian.damageCap = (Double)config.MOBS.ENDERGUARDIAN.capConfig.damageCap.get();
         CMCommonConfig.EnderGuardian.dpsCap = (Double)config.MOBS.ENDERGUARDIAN.capConfig.dpsCap.get();
         CMCommonConfig.EnderGuardian.dpsLimitTime = (Integer)config.MOBS.ENDERGUARDIAN.capConfig.dpslimittime.get();
         CMCommonConfig.EnderGuardian.rangeCap = (Double)config.MOBS.ENDERGUARDIAN.capConfig.rangeCap.get();
         CMCommonConfig.EnderGuardian.natureHeal = (Double)config.MOBS.ENDERGUARDIAN.healConfig.heal.get();
         CMCommonConfig.EnderGuardian.respawner = (Boolean)config.MOBS.ENDERGUARDIAN.respawnerConfig.Respawner.get();
         CMCommonConfig.EnderGuardian.ignoreMobGriefing = (Boolean)config.MOBS.ENDERGUARDIAN.mobGriefingConfig.Ignore.get();
         CMCommonConfig.EnderGuardian.GravityPunchHpdamage = (Double)config.MOBS.ENDERGUARDIAN.GravityPunchHpdamage.get();
         CMCommonConfig.EnderGuardian.TeleportAttackHpdamage = (Double)config.MOBS.ENDERGUARDIAN.TeleportAttackHpdamage.get();
         CMCommonConfig.EnderGuardian.KnockbackHpdamage = (Double)config.MOBS.ENDERGUARDIAN.KnockbackHpdamage.get();
         CMCommonConfig.EnderGuardian.UppercutHpdamage = (Double)config.MOBS.ENDERGUARDIAN.UppercutHpdamage.get();
         CMCommonConfig.EnderGuardian.RocketPunchHpdamage = (Double)config.MOBS.ENDERGUARDIAN.RocketPunchHpdamage.get();
         CMCommonConfig.EnderGuardian.AreaAttackHpdamage = (Double)config.MOBS.ENDERGUARDIAN.AreaAttackHpdamage.get();
         CMCommonConfig.EnderGuardian.BlockBreakingX = (Integer)config.MOBS.ENDERGUARDIAN.BlockBreakingX.get();
         CMCommonConfig.EnderGuardian.BlockBreakingY = (Integer)config.MOBS.ENDERGUARDIAN.BlockBreakingY.get();
         CMCommonConfig.EnderGuardian.BlockBreakingZ = (Integer)config.MOBS.ENDERGUARDIAN.BlockBreakingZ.get();
         CMCommonConfig.EnderGuardian.VoidRuneDamage = (Double)config.MOBS.ENDERGUARDIAN.VoidRuneDamage.get();
         CMCommonConfig.EnderGuardian.SeparatePhaseMusic = (Boolean)config.MOBS.ENDERGUARDIAN.SeparatePhaseMusic.get();
         CMCommonConfig.EnderGolem.healthMultiplier = (Double)config.MOBS.ENDERGOLEM.combatConfig.healthMultiplier.get();
         CMCommonConfig.EnderGolem.attackMultiplier = (Double)config.MOBS.ENDERGOLEM.combatConfig.attackMultiplier.get();
         CMCommonConfig.EnderGolem.damageCap = (Double)config.MOBS.ENDERGOLEM.capConfig.damageCap.get();
         CMCommonConfig.EnderGolem.dpsCap = (Double)config.MOBS.ENDERGOLEM.capConfig.dpsCap.get();
         CMCommonConfig.EnderGolem.dpsLimitTime = (Integer)config.MOBS.ENDERGOLEM.capConfig.dpslimittime.get();
         CMCommonConfig.EnderGolem.rangeCap = (Double)config.MOBS.ENDERGOLEM.capConfig.rangeCap.get();
         CMCommonConfig.EnderGolem.natureHeal = (Double)config.MOBS.ENDERGOLEM.healConfig.heal.get();
         CMCommonConfig.EnderGolem.ignoreMobGriefing = (Boolean)config.MOBS.ENDERGOLEM.mobGriefingConfig.Ignore.get();
         CMCommonConfig.EnderGolem.VoidRuneDamage = (Double)config.MOBS.ENDERGOLEM.VoidRuneDamage.get();
         CMCommonConfig.NetheriteMonstrosity.healthMultiplier = (Double)config.MOBS.NETHERITE_MONSTROSITY.combatConfig.healthMultiplier.get();
         CMCommonConfig.NetheriteMonstrosity.attackMultiplier = (Double)config.MOBS.NETHERITE_MONSTROSITY.combatConfig.attackMultiplier.get();
         CMCommonConfig.NetheriteMonstrosity.damageCap = (Double)config.MOBS.NETHERITE_MONSTROSITY.capConfig.damageCap.get();
         CMCommonConfig.NetheriteMonstrosity.dpsCap = (Double)config.MOBS.NETHERITE_MONSTROSITY.capConfig.dpsCap.get();
         CMCommonConfig.NetheriteMonstrosity.dpsLimitTime = (Integer)config.MOBS.NETHERITE_MONSTROSITY.capConfig.dpslimittime.get();
         CMCommonConfig.NetheriteMonstrosity.rangeCap = (Double)config.MOBS.NETHERITE_MONSTROSITY.capConfig.rangeCap.get();
         CMCommonConfig.NetheriteMonstrosity.natureHeal = (Double)config.MOBS.NETHERITE_MONSTROSITY.healConfig.heal.get();
         CMCommonConfig.NetheriteMonstrosity.respawner = (Boolean)config.MOBS.NETHERITE_MONSTROSITY.respawnerConfig.Respawner.get();
         CMCommonConfig.NetheriteMonstrosity.ignoreMobGriefing = (Boolean)config.MOBS.NETHERITE_MONSTROSITY.mobGriefingConfig.Ignore.get();
         CMCommonConfig.NetheriteMonstrosity.Lavabombmagazine = (Integer)config.MOBS.NETHERITE_MONSTROSITY.Lavabombmagazine.get();
         CMCommonConfig.NetheriteMonstrosity.Lavabombamount = (Integer)config.MOBS.NETHERITE_MONSTROSITY.Lavabombamount.get();
         CMCommonConfig.NetheriteMonstrosity.LavabombDuration = (Integer)config.MOBS.NETHERITE_MONSTROSITY.LavabombDuration.get();
         CMCommonConfig.NetheriteMonstrosity.LavabombRandomDuration = (Integer)config.MOBS.NETHERITE_MONSTROSITY.LavabombRandomDuration.get();
         CMCommonConfig.NetheriteMonstrosity.BodyCollided = (Boolean)config.MOBS.NETHERITE_MONSTROSITY.BodyCollided.get();
         CMCommonConfig.NetheriteMonstrosity.SmashHpdamage = (Double)config.MOBS.NETHERITE_MONSTROSITY.SmashHpdamage.get();
         CMCommonConfig.NetheriteMonstrosity.FlameJetDamage = (Double)config.MOBS.NETHERITE_MONSTROSITY.FlameJetDamage.get();
         CMCommonConfig.NetheriteMonstrosity.FlareBombDamage = (Double)config.MOBS.NETHERITE_MONSTROSITY.FlareBombDamage.get();
         CMCommonConfig.NetheriteMinistrosity.healthMultiplier = (Double)config.MOBS.NETHERITE_MINISTROSITY.combatConfig.healthMultiplier.get();
         CMCommonConfig.NetheriteMinistrosity.attackMultiplier = (Double)config.MOBS.NETHERITE_MINISTROSITY.combatConfig.attackMultiplier.get();
         CMCommonConfig.Ignis.healthMultiplier = (Double)config.MOBS.IGNIS.combatConfig.healthMultiplier.get();
         CMCommonConfig.Ignis.attackMultiplier = (Double)config.MOBS.IGNIS.combatConfig.attackMultiplier.get();
         CMCommonConfig.Ignis.damageCap = (Double)config.MOBS.IGNIS.capConfig.damageCap.get();
         CMCommonConfig.Ignis.dpsCap = (Double)config.MOBS.IGNIS.capConfig.dpsCap.get();
         CMCommonConfig.Ignis.dpsLimitTime = (Integer)config.MOBS.IGNIS.capConfig.dpslimittime.get();
         CMCommonConfig.Ignis.rangeCap = (Double)config.MOBS.IGNIS.capConfig.rangeCap.get();
         CMCommonConfig.Ignis.natureHeal = (Double)config.MOBS.IGNIS.healConfig.heal.get();
         CMCommonConfig.Ignis.ignoreMobGriefing = (Boolean)config.MOBS.IGNIS.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Ignis.HealingMultiplier = (Double)config.MOBS.IGNIS.HealingMultiplier.get();
         CMCommonConfig.Ignis.SeparatePhaseMusic = (Boolean)config.MOBS.IGNIS.SeparatePhaseMusic.get();
         CMCommonConfig.IgnitedRevenant.healthMultiplier = (Double)config.MOBS.REVENANT.combatConfig.healthMultiplier.get();
         CMCommonConfig.IgnitedRevenant.attackMultiplier = (Double)config.MOBS.REVENANT.combatConfig.attackMultiplier.get();
         CMCommonConfig.IgnitedRevenant.AshenbreathDamage = (Double)config.MOBS.REVENANT.AshenbreathDamage.get();
         CMCommonConfig.IgnitedRevenant.BlazingBoneDamage = (Double)config.MOBS.REVENANT.BlazingBoneDamage.get();
         CMCommonConfig.Harbinger.healthMultiplier = (Double)config.MOBS.HARBINGER.combatConfig.healthMultiplier.get();
         CMCommonConfig.Harbinger.attackMultiplier = (Double)config.MOBS.HARBINGER.combatConfig.attackMultiplier.get();
         CMCommonConfig.Harbinger.damageCap = (Double)config.MOBS.HARBINGER.capConfig.damageCap.get();
         CMCommonConfig.Harbinger.dpsCap = (Double)config.MOBS.HARBINGER.capConfig.dpsCap.get();
         CMCommonConfig.Harbinger.dpsLimitTime = (Integer)config.MOBS.HARBINGER.capConfig.dpslimittime.get();
         CMCommonConfig.Harbinger.rangeCap = (Double)config.MOBS.HARBINGER.capConfig.rangeCap.get();
         CMCommonConfig.Harbinger.natureHeal = (Double)config.MOBS.HARBINGER.healConfig.heal.get();
         CMCommonConfig.Harbinger.respawner = (Boolean)config.MOBS.HARBINGER.respawnerConfig.Respawner.get();
         CMCommonConfig.Harbinger.ignoreMobGriefing = (Boolean)config.MOBS.HARBINGER.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Harbinger.AutoHeal = (Double)config.MOBS.HARBINGER.AutoHeal.get();
         CMCommonConfig.Harbinger.LifeSteal = (Double)config.MOBS.HARBINGER.LifeSteal.get();
         CMCommonConfig.Harbinger.WitherMissiledamage = (Double)config.MOBS.HARBINGER.WitherMissiledamage.get();
         CMCommonConfig.Harbinger.Laserdamage = (Double)config.MOBS.HARBINGER.Laserdamage.get();
         CMCommonConfig.Harbinger.DeathLaserdamage = (Double)config.MOBS.HARBINGER.DeathLaserdamage.get();
         CMCommonConfig.Harbinger.DeathLaserHpdamage = (Double)config.MOBS.HARBINGER.DeathLaserHpdamage.get();
         CMCommonConfig.Harbinger.ChargeHpDamage = (Double)config.MOBS.HARBINGER.ChargeHpDamage.get();
         CMCommonConfig.Prowler.healthMultiplier = (Double)config.MOBS.PROWLER.combatConfig.healthMultiplier.get();
         CMCommonConfig.Prowler.attackMultiplier = (Double)config.MOBS.PROWLER.combatConfig.attackMultiplier.get();
         CMCommonConfig.Prowler.rangeCap = (Double)config.MOBS.PROWLER.rangeCap.get();
         CMCommonConfig.Prowler.HomingMissiledamage = (Double)config.MOBS.PROWLER.HomingMissiledamage.get();
         CMCommonConfig.Prowler.DeathLaserdamage = (Double)config.MOBS.PROWLER.DeathLaserdamage.get();
         CMCommonConfig.Prowler.DeathLaserHpdamage = (Double)config.MOBS.PROWLER.DeathLaserHpdamage.get();
         CMCommonConfig.Leviathan.healthMultiplier = (Double)config.MOBS.LEVIATHAN.combatConfig.healthMultiplier.get();
         CMCommonConfig.Leviathan.attackMultiplier = (Double)config.MOBS.LEVIATHAN.combatConfig.attackMultiplier.get();
         CMCommonConfig.Leviathan.damageCap = (Double)config.MOBS.LEVIATHAN.capConfig.damageCap.get();
         CMCommonConfig.Leviathan.dpsCap = (Double)config.MOBS.LEVIATHAN.capConfig.dpsCap.get();
         CMCommonConfig.Leviathan.dpsLimitTime = (Integer)config.MOBS.LEVIATHAN.capConfig.dpslimittime.get();
         CMCommonConfig.Leviathan.rangeCap = (Double)config.MOBS.LEVIATHAN.capConfig.rangeCap.get();
         CMCommonConfig.Leviathan.natureHeal = (Double)config.MOBS.LEVIATHAN.healConfig.heal.get();
         CMCommonConfig.Leviathan.ignoreMobGriefing = (Boolean)config.MOBS.LEVIATHAN.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Leviathan.BiteHpDamage = (Double)config.MOBS.LEVIATHAN.BiteHpDamage.get();
         CMCommonConfig.Leviathan.RushHpDamage = (Double)config.MOBS.LEVIATHAN.RushHpDamage.get();
         CMCommonConfig.Leviathan.TailSwingHpDamage = (Double)config.MOBS.LEVIATHAN.TailSwingHpDamage.get();
         CMCommonConfig.Leviathan.TentacleHpDamage = (Double)config.MOBS.LEVIATHAN.TentacleHpDamage.get();
         CMCommonConfig.Leviathan.DimensionalRiftDamage = (Double)config.MOBS.LEVIATHAN.DimensionalRiftDamage.get();
         CMCommonConfig.Leviathan.AbyssBlastDamage = (Double)config.MOBS.LEVIATHAN.AbyssBlastDamage.get();
         CMCommonConfig.Leviathan.AbyssBlastHpDamage = (Double)config.MOBS.LEVIATHAN.AbyssBlastHpDamage.get();
         CMCommonConfig.Leviathan.AbyssOrbdamage = (Double)config.MOBS.LEVIATHAN.AbyssOrbdamage.get();
         CMCommonConfig.Leviathan.ImmuneOutofWater = (Boolean)config.MOBS.LEVIATHAN.ImmuneOutofWater.get();
         CMCommonConfig.Leviathan.SeparatePhaseMusic = (Boolean)config.MOBS.LEVIATHAN.SeparatePhaseMusic.get();
         CMCommonConfig.BabyLeviathan.healthMultiplier = (Double)config.MOBS.BABY_LEVIATHAN.combatConfig.healthMultiplier.get();
         CMCommonConfig.BabyLeviathan.attackMultiplier = (Double)config.MOBS.BABY_LEVIATHAN.combatConfig.attackMultiplier.get();
         CMCommonConfig.AmethystCrab.healthMultiplier = (Double)config.MOBS.CRAB.combatConfig.healthMultiplier.get();
         CMCommonConfig.AmethystCrab.attackMultiplier = (Double)config.MOBS.CRAB.combatConfig.attackMultiplier.get();
         CMCommonConfig.AmethystCrab.EarthQuakeDamage = (Double)config.MOBS.CRAB.EarthQuakeDamage.get();
         CMCommonConfig.AmethystCrab.AmethystClusterDamage = (Double)config.MOBS.CRAB.AmethystClusterDamage.get();
         CMCommonConfig.AncientRemnant.healthMultiplier = (Double)config.MOBS.ANCIENT_REMNANT.combatConfig.healthMultiplier.get();
         CMCommonConfig.AncientRemnant.attackMultiplier = (Double)config.MOBS.ANCIENT_REMNANT.combatConfig.attackMultiplier.get();
         CMCommonConfig.AncientRemnant.damageCap = (Double)config.MOBS.ANCIENT_REMNANT.capConfig.damageCap.get();
         CMCommonConfig.AncientRemnant.dpsCap = (Double)config.MOBS.ANCIENT_REMNANT.capConfig.dpsCap.get();
         CMCommonConfig.AncientRemnant.dpsLimitTime = (Integer)config.MOBS.ANCIENT_REMNANT.capConfig.dpslimittime.get();
         CMCommonConfig.AncientRemnant.rangeCap = (Double)config.MOBS.ANCIENT_REMNANT.capConfig.rangeCap.get();
         CMCommonConfig.AncientRemnant.natureHeal = (Double)config.MOBS.ANCIENT_REMNANT.healConfig.heal.get();
         CMCommonConfig.AncientRemnant.respawner = (Boolean)config.MOBS.ANCIENT_REMNANT.respawnerConfig.Respawner.get();
         CMCommonConfig.AncientRemnant.ignoreMobGriefing = (Boolean)config.MOBS.ANCIENT_REMNANT.mobGriefingConfig.Ignore.get();
         CMCommonConfig.AncientRemnant.ChargeHpDamage = (Double)config.MOBS.ANCIENT_REMNANT.ChargeHpDamage.get();
         CMCommonConfig.AncientRemnant.HpDamage = (Double)config.MOBS.ANCIENT_REMNANT.HpDamage.get();
         CMCommonConfig.AncientRemnant.StompHpDamage = (Double)config.MOBS.ANCIENT_REMNANT.StompHpDamage.get();
         CMCommonConfig.AncientRemnant.EarthQuakeDamage = (Double)config.MOBS.ANCIENT_REMNANT.EarthQuakeDamage.get();
         CMCommonConfig.AncientRemnant.AncientDesertSteledamage = (Double)config.MOBS.ANCIENT_REMNANT.AncientDesertSteledamage.get();
         CMCommonConfig.ModernRemnant.healthMultiplier = (Double)config.MOBS.MODERN_REMNANT.combatConfig.healthMultiplier.get();
         CMCommonConfig.ModernRemnant.attackMultiplier = (Double)config.MOBS.MODERN_REMNANT.combatConfig.attackMultiplier.get();
         CMCommonConfig.Koboleton.CauseKoboletontoDropItemInHandPercent = (Double)config.MOBS.KOBOLETON.CauseKoboletontoDropItemInHandPercent.get();
         CMCommonConfig.Kobolediator.healthMultiplier = (Double)config.MOBS.KOBOLEDIATOR.combatConfig.healthMultiplier.get();
         CMCommonConfig.Kobolediator.attackMultiplier = (Double)config.MOBS.KOBOLEDIATOR.combatConfig.attackMultiplier.get();
         CMCommonConfig.Kobolediator.ignoreMobGriefing = (Boolean)config.MOBS.KOBOLEDIATOR.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Wadjet.healthMultiplier = (Double)config.MOBS.WADJET.combatConfig.healthMultiplier.get();
         CMCommonConfig.Wadjet.attackMultiplier = (Double)config.MOBS.WADJET.combatConfig.attackMultiplier.get();
         CMCommonConfig.Wadjet.Sandstorm_damage = (Double)config.MOBS.WADJET.Sandstorm_damage.get();
         CMCommonConfig.Wadjet.AncientDesertSteledamage = (Double)config.MOBS.WADJET.AncientDesertSteledamage.get();
         CMCommonConfig.Scylla.healthMultiplier = (Double)config.MOBS.SCYLLA.combatConfig.healthMultiplier.get();
         CMCommonConfig.Scylla.attackMultiplier = (Double)config.MOBS.SCYLLA.combatConfig.attackMultiplier.get();
         CMCommonConfig.Scylla.damageCap = (Double)config.MOBS.SCYLLA.capConfig.damageCap.get();
         CMCommonConfig.Scylla.dpsCap = (Double)config.MOBS.SCYLLA.capConfig.dpsCap.get();
         CMCommonConfig.Scylla.dpsLimitTime = (Integer)config.MOBS.SCYLLA.capConfig.dpslimittime.get();
         CMCommonConfig.Scylla.rangeCap = (Double)config.MOBS.SCYLLA.capConfig.rangeCap.get();
         CMCommonConfig.Scylla.natureHeal = (Double)config.MOBS.SCYLLA.healConfig.heal.get();
         CMCommonConfig.Scylla.respawner = (Boolean)config.MOBS.SCYLLA.respawnerConfig.Respawner.get();
         CMCommonConfig.Scylla.ignoreMobGriefing = (Boolean)config.MOBS.SCYLLA.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Scylla.SpearDamage = (Double)config.MOBS.SCYLLA.SpearDamage.get();
         CMCommonConfig.Scylla.LightningStormDamage = (Double)config.MOBS.SCYLLA.LightningStormDamage.get();
         CMCommonConfig.Scylla.LightningAreaDamage = (Double)config.MOBS.SCYLLA.LightningAreaDamage.get();
         CMCommonConfig.Scylla.SnakeDamage = (Double)config.MOBS.SCYLLA.SnakeDamage.get();
         CMCommonConfig.Scylla.AnchorDamage = (Double)config.MOBS.SCYLLA.AnchorDamage.get();
         CMCommonConfig.Scylla.WeatherChange = (Boolean)config.MOBS.SCYLLA.WeatherChange.get();
         CMCommonConfig.Scylla.HpDamage = (Double)config.MOBS.SCYLLA.HpDamage.get();
         CMCommonConfig.Scylla.SpinHpDamage = (Double)config.MOBS.SCYLLA.SpinHpDamage.get();
         CMCommonConfig.Scylla.StormHpDamage = (Double)config.MOBS.SCYLLA.StormHpDamage.get();
         CMCommonConfig.Clawdian.healthMultiplier = (Double)config.MOBS.CLAWDIAN.combatConfig.healthMultiplier.get();
         CMCommonConfig.Clawdian.attackMultiplier = (Double)config.MOBS.CLAWDIAN.combatConfig.attackMultiplier.get();
         CMCommonConfig.Clawdian.damageCap = (Double)config.MOBS.CLAWDIAN.capConfig.damageCap.get();
         CMCommonConfig.Clawdian.dpsCap = (Double)config.MOBS.CLAWDIAN.capConfig.dpsCap.get();
         CMCommonConfig.Clawdian.dpsLimitTime = (Integer)config.MOBS.CLAWDIAN.capConfig.dpslimittime.get();
         CMCommonConfig.Clawdian.rangeCap = (Double)config.MOBS.CLAWDIAN.capConfig.rangeCap.get();
         CMCommonConfig.Clawdian.natureHeal = (Double)config.MOBS.CLAWDIAN.healConfig.heal.get();
         CMCommonConfig.Clawdian.ignoreMobGriefing = (Boolean)config.MOBS.CLAWDIAN.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Maledictus.healthMultiplier = (Double)config.MOBS.MALEDICTUS.combatConfig.healthMultiplier.get();
         CMCommonConfig.Maledictus.attackMultiplier = (Double)config.MOBS.MALEDICTUS.combatConfig.attackMultiplier.get();
         CMCommonConfig.Maledictus.damageCap = (Double)config.MOBS.MALEDICTUS.capConfig.damageCap.get();
         CMCommonConfig.Maledictus.dpsCap = (Double)config.MOBS.MALEDICTUS.capConfig.dpsCap.get();
         CMCommonConfig.Maledictus.dpsLimitTime = (Integer)config.MOBS.MALEDICTUS.capConfig.dpslimittime.get();
         CMCommonConfig.Maledictus.rangeCap = (Double)config.MOBS.MALEDICTUS.capConfig.rangeCap.get();
         CMCommonConfig.Maledictus.natureHeal = (Double)config.MOBS.MALEDICTUS.healConfig.heal.get();
         CMCommonConfig.Maledictus.ignoreMobGriefing = (Boolean)config.MOBS.MALEDICTUS.mobGriefingConfig.Ignore.get();
         CMCommonConfig.Maledictus.PhantomHalberdDamage = (Double)config.MOBS.MALEDICTUS.PhantomHalberdDamage.get();
         CMCommonConfig.Maledictus.HpDamage = (Double)config.MOBS.MALEDICTUS.HpDamage.get();
         CMCommonConfig.Maledictus.ShockWaveHpDamage = (Double)config.MOBS.MALEDICTUS.ShockWaveHpDamage.get();
         CMCommonConfig.Maledictus.AOEHpDamage = (Double)config.MOBS.MALEDICTUS.AOEHpDamage.get();
         CMCommonConfig.Maledictus.FlyingSmashHpDamage = (Double)config.MOBS.MALEDICTUS.FlyingSmashHpDamage.get();
         CMCommonConfig.Maledictus.SmashHpDamage = (Double)config.MOBS.MALEDICTUS.SmashHpDamage.get();
         CMCommonConfig.Maledictus.PhantomArrowDamage = (Double)config.MOBS.MALEDICTUS.PhantomArrowDamage.get();
         CMCommonConfig.Aptrgangr.healthMultiplier = (Double)config.MOBS.APTRGANGR.combatConfig.healthMultiplier.get();
         CMCommonConfig.Aptrgangr.attackMultiplier = (Double)config.MOBS.APTRGANGR.combatConfig.attackMultiplier.get();
         CMCommonConfig.Aptrgangr.natureHeal = (Double)config.MOBS.APTRGANGR.healConfig.heal.get();
         CMCommonConfig.Aptrgangr.AxeBladeDamage = (Double)config.MOBS.APTRGANGR.AxeBladeDamage.get();
         CMCommonConfig.ETC.ReturnHome = (Integer)config.MOBS.ETC.ReturnHome.get();
         CMCommonConfig.Spawning.DeeplingSpawnWeight = (Integer)config.SPAWNING.DeeplingSpawnWeight.get();
         CMCommonConfig.Spawning.DeeplingSpawnRolls = (Integer)config.SPAWNING.DeeplingSpawnRolls.get();
         CMCommonConfig.Spawning.DeeplingBruteSpawnWeight = (Integer)config.SPAWNING.DeeplingBruteSpawnWeight.get();
         CMCommonConfig.Spawning.DeeplingBruteSpawnRolls = (Integer)config.SPAWNING.DeeplingBruteSpawnRolls.get();
         CMCommonConfig.Spawning.DeeplingAnglerSpawnWeight = (Integer)config.SPAWNING.DeeplingAnglerSpawnWeight.get();
         CMCommonConfig.Spawning.DeeplingAnglerSpawnRolls = (Integer)config.SPAWNING.DeeplingAnglerSpawnRolls.get();
         CMCommonConfig.Spawning.DeeplingPriestSpawnWeight = (Integer)config.SPAWNING.DeeplingPriestSpawnWeight.get();
         CMCommonConfig.Spawning.DeeplingPriestSpawnRolls = (Integer)config.SPAWNING.DeeplingPriestSpawnRolls.get();
         CMCommonConfig.Spawning.DeeplingWarlockSpawnWeight = (Integer)config.SPAWNING.DeeplingWarlockSpawnWeight.get();
         CMCommonConfig.Spawning.DeeplingWarlockSpawnRolls = (Integer)config.SPAWNING.DeeplingWarlockSpawnRolls.get();
         CMCommonConfig.Spawning.CoralgolemSpawnWeight = (Integer)config.SPAWNING.CoralgolemSpawnWeight.get();
         CMCommonConfig.Spawning.CoralgolemSpawnRolls = (Integer)config.SPAWNING.CoralgolemSpawnRolls.get();
         CMCommonConfig.Spawning.AmethystCrabSpawnWeight = (Integer)config.SPAWNING.AmethystCrabSpawnWeight.get();
         CMCommonConfig.Spawning.AmethystCrabSpawnRolls = (Integer)config.SPAWNING.AmethystCrabSpawnRolls.get();
         CMCommonConfig.Spawning.KoboletonSpawnWeight = (Integer)config.SPAWNING.KoboletonSpawnWeight.get();
         CMCommonConfig.Spawning.KoboletonSpawnRolls = (Integer)config.SPAWNING.KoboletonSpawnRolls.get();
         CMCommonConfig.Spawning.IgnitedBerserkerSpawnWeight = (Integer)config.SPAWNING.IgnitedBerserkerSpawnWeight.get();
         CMCommonConfig.Spawning.IgnitedBerserkerSpawnRolls = (Integer)config.SPAWNING.IgnitedBerserkerSpawnRolls.get();
         CMCommonConfig.IgnitiumArmor.armorMultiplier = (Double)config.TOOLS_AND_ABILITIES.IGNITIUM_ARMOR.armorConfig.armormultiplier.get();
         CMCommonConfig.IgnitiumArmor.toughness = (Double)config.TOOLS_AND_ABILITIES.IGNITIUM_ARMOR.armorConfig.toughness.get();
         CMCommonConfig.IgnitiumArmor.knockbackResistance = (Double)config.TOOLS_AND_ABILITIES.IGNITIUM_ARMOR.armorConfig.knockbackRes.get();
         CMCommonConfig.CursiumArmor.armorMultiplier = (Double)config.TOOLS_AND_ABILITIES.CURSIUM_ARMOR.armorConfig.armormultiplier.get();
         CMCommonConfig.CursiumArmor.toughness = (Double)config.TOOLS_AND_ABILITIES.CURSIUM_ARMOR.armorConfig.toughness.get();
         CMCommonConfig.CursiumArmor.knockbackResistance = (Double)config.TOOLS_AND_ABILITIES.CURSIUM_ARMOR.armorConfig.knockbackRes.get();
         CMCommonConfig.BoneReptileArmor.armorMultiplier = (Double)config.TOOLS_AND_ABILITIES.BONE_REPTILE_ARMOR.armorConfig.armormultiplier.get();
         CMCommonConfig.BoneReptileArmor.toughness = (Double)config.TOOLS_AND_ABILITIES.BONE_REPTILE_ARMOR.armorConfig.toughness.get();
         CMCommonConfig.BoneReptileArmor.knockbackResistance = (Double)config.TOOLS_AND_ABILITIES.BONE_REPTILE_ARMOR.armorConfig.knockbackRes.get();
         CMCommonConfig.BloomStoneArmor.armorMultiplier = (Double)config.TOOLS_AND_ABILITIES.BLOOM_STONE_ARMOR.armorConfig.armormultiplier.get();
         CMCommonConfig.BloomStoneArmor.toughness = (Double)config.TOOLS_AND_ABILITIES.BLOOM_STONE_ARMOR.armorConfig.toughness.get();
         CMCommonConfig.BloomStoneArmor.knockbackResistance = (Double)config.TOOLS_AND_ABILITIES.BLOOM_STONE_ARMOR.armorConfig.knockbackRes.get();
         CMCommonConfig.Incinerator.attackDamage = (Double)config.TOOLS_AND_ABILITIES.INCINERATOR.toolConfig.attackDamage.get();
         CMCommonConfig.Incinerator.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.INCINERATOR.toolConfig.attackSpeed.get();
         CMCommonConfig.Incinerator.cooldown = (Integer)config.TOOLS_AND_ABILITIES.INCINERATOR.IncineratorCooldown.get();
         CMCommonConfig.BulwarkOfTheFlame.cooldown = (Integer)config.TOOLS_AND_ABILITIES.BULWARK_OF_THE_FLAME.BulwarkOfTheFlameCooldown.get();
         CMCommonConfig.GauntletOfGuard.attackDamage = (Double)config.TOOLS_AND_ABILITIES.GAUNTLER_OF_GUARD.toolConfig.attackDamage.get();
         CMCommonConfig.GauntletOfGuard.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.GAUNTLER_OF_GUARD.toolConfig.attackSpeed.get();
         CMCommonConfig.GauntletOfBulwark.attackDamage = (Double)config.TOOLS_AND_ABILITIES.GAUNTLET_OF_BULWARK.toolConfig.attackDamage.get();
         CMCommonConfig.GauntletOfBulwark.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.GAUNTLET_OF_BULWARK.toolConfig.attackSpeed.get();
         CMCommonConfig.GauntletOfBulwark.cooldown = (Integer)config.TOOLS_AND_ABILITIES.GAUNTLET_OF_BULWARK.GauntletOfBulwarkCooldown.get();
         CMCommonConfig.GauntletOfMaelstrom.attackDamage = (Double)config.TOOLS_AND_ABILITIES.GAUNTLER_OF_MAELSTROM.toolConfig.attackDamage.get();
         CMCommonConfig.GauntletOfMaelstrom.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.GAUNTLER_OF_MAELSTROM.toolConfig.attackSpeed.get();
         CMCommonConfig.GauntletOfMaelstrom.cooldown = (Integer)config.TOOLS_AND_ABILITIES.GAUNTLER_OF_MAELSTROM.GauntletOfMaelstromCooldown.get();
         CMCommonConfig.InfernalForge.attackDamage = (Double)config.TOOLS_AND_ABILITIES.INFERNAL_FORGE.toolConfig.attackDamage.get();
         CMCommonConfig.InfernalForge.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.INFERNAL_FORGE.toolConfig.attackSpeed.get();
         CMCommonConfig.InfernalForge.cooldown = (Integer)config.TOOLS_AND_ABILITIES.INFERNAL_FORGE.InfernalForgeCooldown.get();
         CMCommonConfig.VoidForge.attackDamage = (Double)config.TOOLS_AND_ABILITIES.VOID_FORGE.toolConfig.attackDamage.get();
         CMCommonConfig.VoidForge.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.VOID_FORGE.toolConfig.attackSpeed.get();
         CMCommonConfig.VoidForge.cooldown = (Integer)config.TOOLS_AND_ABILITIES.VOID_FORGE.VoidForgeCooldown.get();
         CMCommonConfig.VoidForge.runeDamage = (Double)config.TOOLS_AND_ABILITIES.VOID_FORGE.VoidRuneDamage.get();
         CMCommonConfig.Brontes.attackDamage = (Double)config.TOOLS_AND_ABILITIES.BRONTES.toolConfig.attackDamage.get();
         CMCommonConfig.Brontes.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.BRONTES.toolConfig.attackSpeed.get();
         CMCommonConfig.Brontes.stormareadamage = (Double)config.TOOLS_AND_ABILITIES.BRONTES.StormAreaDamage.get();
         CMCommonConfig.Brontes.stormdamage = (Double)config.TOOLS_AND_ABILITIES.BRONTES.StormDamage.get();
         CMCommonConfig.MeatShredder.attackDamage = (Double)config.TOOLS_AND_ABILITIES.MEATH_SHREDDER.toolConfig.attackDamage.get();
         CMCommonConfig.MeatShredder.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.MEATH_SHREDDER.toolConfig.attackSpeed.get();
         CMCommonConfig.Annihilator.attackDamage = (Double)config.TOOLS_AND_ABILITIES.ANNIHILATOR.toolConfig.attackDamage.get();
         CMCommonConfig.Annihilator.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.ANNIHILATOR.toolConfig.attackSpeed.get();
         CMCommonConfig.WASW.missileCooldown = (Integer)config.TOOLS_AND_ABILITIES.WASW.MissileCooldown.get();
         CMCommonConfig.WASW.missileDamage = (Double)config.TOOLS_AND_ABILITIES.WASW.MissileDamage.get();
         CMCommonConfig.WASW.howitzerCooldown = (Integer)config.TOOLS_AND_ABILITIES.WASW.HowitzerCooldown.get();
         CMCommonConfig.VASW.howitzerCooldown = (Integer)config.TOOLS_AND_ABILITIES.VASW.HowitzerCooldown.get();
         CMCommonConfig.VASW.runeDamage = (Double)config.TOOLS_AND_ABILITIES.VASW.VoidRuneDamage.get();
         CMCommonConfig.VoidCore.cooldown = (Integer)config.TOOLS_AND_ABILITIES.VOID_CORE.VoidCoreCooldown.get();
         CMCommonConfig.VoidCore.runeDamage = (Double)config.TOOLS_AND_ABILITIES.VOID_CORE.VoidRuneDamage.get();
         CMCommonConfig.SandstormInABottle.cooldown = (Integer)config.TOOLS_AND_ABILITIES.SANDSTORM_IN_A_BOTTLE.SandStormCooldown.get();
         CMCommonConfig.SoulRender.attackDamage = (Double)config.TOOLS_AND_ABILITIES.SOUL_RENDER.toolConfig.attackDamage.get();
         CMCommonConfig.SoulRender.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.SOUL_RENDER.toolConfig.attackSpeed.get();
         CMCommonConfig.SoulRender.cooldown = (Integer)config.TOOLS_AND_ABILITIES.SOUL_RENDER.SoulRenderCooldown.get();
         CMCommonConfig.SoulRender.phantomHalberdDamage = (Double)config.TOOLS_AND_ABILITIES.SOUL_RENDER.PhantomHalberddamage.get();
         CMCommonConfig.Immolator.attackDamage = (Double)config.TOOLS_AND_ABILITIES.IMMOLATOR.toolConfig.attackDamage.get();
         CMCommonConfig.Immolator.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.IMMOLATOR.toolConfig.attackSpeed.get();
         CMCommonConfig.Immolator.cooldown = (Integer)config.TOOLS_AND_ABILITIES.IMMOLATOR.ImmolatorCooldown.get();
         CMCommonConfig.Ceraunus.attackDamage = (Double)config.TOOLS_AND_ABILITIES.CERAUNUS.toolConfig.attackDamage.get();
         CMCommonConfig.Ceraunus.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.CERAUNUS.toolConfig.attackSpeed.get();
         CMCommonConfig.Ceraunus.cooldown = (Integer)config.TOOLS_AND_ABILITIES.CERAUNUS.CeraunusCooldown.get();
         CMCommonConfig.Ceraunus.waveDamage = (Double)config.TOOLS_AND_ABILITIES.CERAUNUS.CeraunusWaveDamage.get();
         CMCommonConfig.TidalClaws.attackDamage = (Double)config.TOOLS_AND_ABILITIES.TIDAL_CLAW.toolConfig.attackDamage.get();
         CMCommonConfig.TidalClaws.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.TIDAL_CLAW.toolConfig.attackSpeed.get();
         CMCommonConfig.Astrape.attackDamage = (Double)config.TOOLS_AND_ABILITIES.ASTRAPE.toolConfig.attackDamage.get();
         CMCommonConfig.Astrape.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.ASTRAPE.toolConfig.attackSpeed.get();
         CMCommonConfig.Astrape.damage = (Double)config.TOOLS_AND_ABILITIES.ASTRAPE.AstrapeDamage.get();
         CMCommonConfig.Astrape.areaDamage = (Double)config.TOOLS_AND_ABILITIES.ASTRAPE.AstrapeAreaDamage.get();
         CMCommonConfig.Astrape.cooldown = (Integer)config.TOOLS_AND_ABILITIES.ASTRAPE.AstrapeCooldown.get();
         CMCommonConfig.CursedBow.damage = (Double)config.TOOLS_AND_ABILITIES.CURSED_BOW.CursedBowDamage.get();
         CMCommonConfig.WrathOfTheDesert.damage = (Double)config.TOOLS_AND_ABILITIES.WRATH_OF_THE_DESERT.WrathOfTheDesertDamage.get();
         CMCommonConfig.LaserGatling.damage = (Double)config.TOOLS_AND_ABILITIES.LASER_GATLING.LaserDamage.get();
         CMCommonConfig.AncientSpear.attackDamage = (Double)config.TOOLS_AND_ABILITIES.ANCIENTSPEAR.toolConfig.attackDamage.get();
         CMCommonConfig.AncientSpear.attackSpeed = (Double)config.TOOLS_AND_ABILITIES.ANCIENTSPEAR.toolConfig.attackSpeed.get();
         CMCommonConfig.AncientSpear.sandstormdamage = (Double)config.TOOLS_AND_ABILITIES.ANCIENTSPEAR.SandstormDamage.get();
         CMCommonConfig.Blocks.CursedTombstoneCooldown = (Integer)config.BLOCKS.CURSEDTOMBSTONE.CursedTombstoneCooldown.get();
      } catch (Exception e) {
         Cataclysm.LOGGER.warn("An exception was caused trying to load the Common config for CM");
         e.printStackTrace();
      }
   }

   public static class AmethystCrab {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double EarthQuakeDamage = 5.0;
      public static double AmethystClusterDamage = 12.0;
   }

   public static class AncientRemnant {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 21.0;
      public static double dpsCap = 14.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 14.0;
      public static double natureHeal = 25.0;
      public static boolean respawner = true;
      public static boolean ignoreMobGriefing = true;
      public static double ChargeHpDamage = 0.1;
      public static double HpDamage = 0.05;
      public static double StompHpDamage = 0.03;
      public static double EarthQuakeDamage = 11.0;
      public static double AncientDesertSteledamage = 11.0;
   }

   public static class AncientSpear {
      public static double attackDamage = 9.5;
      public static double attackSpeed = 1.4;
      public static double sandstormdamage = 6.0;
   }

   public static class Annihilator {
      public static double attackDamage = 8.5;
      public static double attackSpeed = 1.6;
   }

   public static class Aptrgangr {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double natureHeal = 25.0;
      public static double AxeBladeDamage = 8.0;
   }

   public static class Astrape {
      public static double attackDamage = 10.5;
      public static double attackSpeed = 1.4;
      public static double damage = 11.0;
      public static double areaDamage = 2.0;
      public static int cooldown = 60;
   }

   public static class BabyLeviathan {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
   }

   public static class Blocks {
      public static int CursedTombstoneCooldown = 1;
   }

   public static class BloomStoneArmor {
      public static double armorMultiplier = 1.0;
      public static double toughness = 2.0;
      public static double knockbackResistance = 0.1;
   }

   public static class BoneReptileArmor {
      public static double armorMultiplier = 1.0;
      public static double toughness = 2.5;
      public static double knockbackResistance = 0.15;
   }

   public static class Brontes {
      public static double attackDamage = 13.0;
      public static double attackSpeed = 1.0;
      public static double stormareadamage = 1.0;
      public static double stormdamage = 10.0;
   }

   public static class BulwarkOfTheFlame {
      public static int cooldown = 80;
   }

   public static class Ceraunus {
      public static double attackDamage = 16.0;
      public static double attackSpeed = 0.7;
      public static int cooldown = 150;
      public static double waveDamage = 6.0;
   }

   public static class Clawdian {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 21.0;
      public static double dpsCap = 12.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 12.0;
      public static double natureHeal = 25.0;
      public static boolean ignoreMobGriefing = false;
   }

   public static class CursedBow {
      public static double damage = 8.0;
   }

   public static class CursiumArmor {
      public static double armorMultiplier = 1.0;
      public static double toughness = 4.0;
      public static double knockbackResistance = 0.05;
   }

   public static class ETC {
      public static int ReturnHome = 20;
   }

   public static class EnderGolem {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 999999.0;
      public static double dpsCap = 999999.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 8.0;
      public static double natureHeal = 25.0;
      public static boolean ignoreMobGriefing = false;
      public static double VoidRuneDamage = 7.0;
   }

   public static class EnderGuardian {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 22.0;
      public static double dpsCap = 13.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 12.0;
      public static double natureHeal = 25.0;
      public static boolean respawner = true;
      public static boolean ignoreMobGriefing = true;
      public static double GravityPunchHpdamage = 0.05;
      public static double TeleportAttackHpdamage = 0.05;
      public static double KnockbackHpdamage = 0.06;
      public static double UppercutHpdamage = 0.1;
      public static double RocketPunchHpdamage = 0.1;
      public static double AreaAttackHpdamage = 0.08;
      public static int BlockBreakingX = 15;
      public static int BlockBreakingY = 2;
      public static int BlockBreakingZ = 15;
      public static double VoidRuneDamage = 9.0;
      public static boolean SeparatePhaseMusic = true;
   }

   public static class GauntletOfBulwark {
      public static double attackDamage = 11.0;
      public static double attackSpeed = 1.6;
      public static int cooldown = 80;
   }

   public static class GauntletOfGuard {
      public static double attackDamage = 11.0;
      public static double attackSpeed = 1.6;
   }

   public static class GauntletOfMaelstrom {
      public static double attackDamage = 11.0;
      public static double attackSpeed = 1.6;
      public static int cooldown = 180;
   }

   public static class Harbinger {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 22.0;
      public static double dpsCap = 14.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 35.0;
      public static double natureHeal = 25.0;
      public static boolean respawner = true;
      public static boolean ignoreMobGriefing = true;
      public static double AutoHeal = 2.0;
      public static double LifeSteal = 5.0;
      public static double WitherMissiledamage = 8.0;
      public static double Laserdamage = 5.0;
      public static double DeathLaserdamage = 5.0;
      public static double DeathLaserHpdamage = 0.05;
      public static double ChargeHpDamage = 0.06;
   }

   public static class Ignis {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 20.0;
      public static double dpsCap = 14.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 15.0;
      public static double natureHeal = 25.0;
      public static boolean ignoreMobGriefing = true;
      public static double HealingMultiplier = 1.0;
      public static boolean SeparatePhaseMusic = true;
   }

   public static class IgnitedRevenant {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double AshenbreathDamage = 4.0;
      public static double BlazingBoneDamage = 5.0;
   }

   public static class IgnitiumArmor {
      public static double armorMultiplier = 1.0;
      public static double toughness = 4.0;
      public static double knockbackResistance = 0.15;
   }

   public static class Immolator {
      public static double attackDamage = 8.5;
      public static double attackSpeed = 1.6;
      public static int cooldown = 250;
   }

   public static class Incinerator {
      public static double attackDamage = 14.0;
      public static double attackSpeed = 1.4;
      public static int cooldown = 400;
   }

   public static class InfernalForge {
      public static double attackDamage = 13.0;
      public static double attackSpeed = 1.0;
      public static int cooldown = 80;
   }

   public static class Kobolediator {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static boolean ignoreMobGriefing = false;
   }

   public static class Koboleton {
      public static double CauseKoboletontoDropItemInHandPercent = 5.0;
   }

   public static class LaserGatling {
      public static double damage = 7.0;
   }

   public static class Leviathan {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 20.0;
      public static double dpsCap = 15.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 38.0;
      public static double natureHeal = 25.0;
      public static boolean ignoreMobGriefing = true;
      public static double BiteHpDamage = 0.1;
      public static double RushHpDamage = 0.05;
      public static double TailSwingHpDamage = 0.06;
      public static double TentacleHpDamage = 0.06;
      public static double DimensionalRiftDamage = 10.0;
      public static double AbyssBlastDamage = 10.0;
      public static double AbyssBlastHpDamage = 0.1;
      public static double AbyssOrbdamage = 4.0;
      public static boolean ImmuneOutofWater = true;
      public static boolean SeparatePhaseMusic = true;
   }

   public static class Maledictus {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 20.0;
      public static double dpsCap = 13.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 14.0;
      public static double natureHeal = 25.0;
      public static boolean ignoreMobGriefing = true;
      public static double PhantomHalberdDamage = 11.0;
      public static double HpDamage = 0.05;
      public static double ShockWaveHpDamage = 0.03;
      public static double AOEHpDamage = 0.15;
      public static double FlyingSmashHpDamage = 0.1;
      public static double SmashHpDamage = 0.08;
      public static double PhantomArrowDamage = 5.0;
   }

   public static class MeatShredder {
      public static double attackDamage = 8.5;
      public static double attackSpeed = 1.4;
   }

   public static class ModernRemnant {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
   }

   public static class NetheriteMinistrosity {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
   }

   public static class NetheriteMonstrosity {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 25.0;
      public static double dpsCap = 20.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 18.0;
      public static double natureHeal = 25.0;
      public static boolean respawner = true;
      public static boolean ignoreMobGriefing = true;
      public static int Lavabombmagazine = 3;
      public static int Lavabombamount = 3;
      public static int LavabombDuration = 350;
      public static int LavabombRandomDuration = 150;
      public static boolean BodyCollided = true;
      public static double SmashHpdamage = 0.08;
      public static double FlameJetDamage = 7.0;
      public static double FlareBombDamage = 10.0;
   }

   public static class Prowler {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double rangeCap = 15.0;
      public static double HomingMissiledamage = 5.0;
      public static double DeathLaserdamage = 5.0;
      public static double DeathLaserHpdamage = 0.05;
   }

   public static class SandstormInABottle {
      public static int cooldown = 300;
   }

   public static class Scylla {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double damageCap = 21.0;
      public static double dpsCap = 13.0;
      public static int dpsLimitTime = 20;
      public static double rangeCap = 12.0;
      public static double natureHeal = 25.0;
      public static boolean respawner = true;
      public static boolean ignoreMobGriefing = true;
      public static double SpearDamage = 14.0;
      public static double LightningStormDamage = 10.0;
      public static double LightningAreaDamage = 4.0;
      public static double SnakeDamage = 16.0;
      public static double AnchorDamage = 16.0;
      public static boolean WeatherChange = true;
      public static double HpDamage = 0.05;
      public static double SpinHpDamage = 0.07;
      public static double StormHpDamage = 0.04;
   }

   public static class SoulRender {
      public static double attackDamage = 15.0;
      public static double attackSpeed = 1.05;
      public static int cooldown = 100;
      public static double phantomHalberdDamage = 12.0;
   }

   public static class Spawning {
      public static int DeeplingSpawnWeight = 2;
      public static int DeeplingSpawnRolls = 30;
      public static int DeeplingBruteSpawnWeight = 1;
      public static int DeeplingBruteSpawnRolls = 50;
      public static int DeeplingAnglerSpawnWeight = 2;
      public static int DeeplingAnglerSpawnRolls = 30;
      public static int DeeplingPriestSpawnWeight = 1;
      public static int DeeplingPriestSpawnRolls = 70;
      public static int DeeplingWarlockSpawnWeight = 1;
      public static int DeeplingWarlockSpawnRolls = 70;
      public static int CoralgolemSpawnWeight = 1;
      public static int CoralgolemSpawnRolls = 70;
      public static int AmethystCrabSpawnWeight = 1;
      public static int AmethystCrabSpawnRolls = 20;
      public static int KoboletonSpawnWeight = 15;
      public static int KoboletonSpawnRolls = 1;
      public static int IgnitedBerserkerSpawnWeight = 7;
      public static int IgnitedBerserkerSpawnRolls = 2;
   }

   public static class TidalClaws {
      public static double attackDamage = 8.0;
      public static double attackSpeed = 1.6;
   }

   public static class VASW {
      public static int howitzerCooldown = 160;
      public static double runeDamage = 7.0;
   }

   public static class VoidCore {
      public static int cooldown = 160;
      public static double runeDamage = 7.0;
   }

   public static class VoidForge {
      public static double attackDamage = 13.0;
      public static double attackSpeed = 1.0;
      public static int cooldown = 100;
      public static double runeDamage = 7.0;
   }

   public static class WASW {
      public static int missileCooldown = 40;
      public static double missileDamage = 16.0;
      public static int howitzerCooldown = 100;
   }

   public static class Wadjet {
      public static double healthMultiplier = 1.0;
      public static double attackMultiplier = 1.0;
      public static double Sandstorm_damage = 6.0;
      public static double AncientDesertSteledamage = 11.0;
   }

   public static class WrathOfTheDesert {
      public static double damage = 7.5;
   }
}
