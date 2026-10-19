package com.iafenvoy.iceandfire.config;

import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer;
import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer.AutoInitConfigCategoryBase;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.jupiter.config.entry.DoubleEntry;
import com.iafenvoy.jupiter.config.entry.IntegerEntry;
import com.iafenvoy.jupiter.config.entry.SeparatorEntry;
import com.iafenvoy.jupiter.config.entry.BooleanEntry.Builder;
import net.minecraft.resources.ResourceLocation;

public class IafCommonConfig extends AutoInitConfigContainer {
   public static final IafCommonConfig INSTANCE = new IafCommonConfig();
   public final IafCommonConfig.DragonConfig dragon = new IafCommonConfig.DragonConfig();
   public final IafCommonConfig.HippogryphsConfig hippogryphs = new IafCommonConfig.HippogryphsConfig();
   public final IafCommonConfig.PixieConfig pixie = new IafCommonConfig.PixieConfig();
   public final IafCommonConfig.CyclopsConfig cyclops = new IafCommonConfig.CyclopsConfig();
   public final IafCommonConfig.SirenConfig siren = new IafCommonConfig.SirenConfig();
   public final IafCommonConfig.GorgonConfig gorgon = new IafCommonConfig.GorgonConfig();
   public final IafCommonConfig.DeathwormConfig deathworm = new IafCommonConfig.DeathwormConfig();
   public final IafCommonConfig.CockatriceConfig cockatrice = new IafCommonConfig.CockatriceConfig();
   public final IafCommonConfig.StymphalianBirdConfig stymphalianBird = new IafCommonConfig.StymphalianBirdConfig();
   public final IafCommonConfig.TrollConfig troll = new IafCommonConfig.TrollConfig();
   public final IafCommonConfig.AmphithereConfig amphithere = new IafCommonConfig.AmphithereConfig();
   public final IafCommonConfig.SeaSerpentConfig seaSerpent = new IafCommonConfig.SeaSerpentConfig();
   public final IafCommonConfig.LichConfig lich = new IafCommonConfig.LichConfig();
   public final IafCommonConfig.HydraConfig hydra = new IafCommonConfig.HydraConfig();
   public final IafCommonConfig.HippocampusConfig hippocampus = new IafCommonConfig.HippocampusConfig();
   public final IafCommonConfig.GhostConfig ghost = new IafCommonConfig.GhostConfig();
   public final IafCommonConfig.ToolsConfig tools = new IafCommonConfig.ToolsConfig();
   public final IafCommonConfig.ArmorsConfig armors = new IafCommonConfig.ArmorsConfig();
   public final IafCommonConfig.WorldGenConfig worldGen = new IafCommonConfig.WorldGenConfig();
   public final IafCommonConfig.Misc misc = new IafCommonConfig.Misc();

   public IafCommonConfig() {
      super(ResourceLocation.fromNamespaceAndPath("iceandfire", "common"), "screen.iceandfire.common.title", "./config/iceandfire/iaf-common.json");
   }

   public static class AmphithereConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry spawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.amphithere.spawn", true).key("spawn")).build();
      public final IntegerEntry spawnWeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.amphithere.spawnWeight", 50
            )
            .range(0, 400)
            .key("spawnWeight"))
         .build();
      public final DoubleEntry villagerSearchLength = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.amphithere.villagerSearchLength", 48.0
            )
            .range(0.0, 1024.0)
            .key("villagerSearchLength"))
         .build();
      public final IntegerEntry tameTime = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.amphithere.tameTime", 400
            )
            .min(0)
            .key("tameTime"))
         .build();
      public final DoubleEntry flightSpeed = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.amphithere.flightSpeed", 1.75
            )
            .range(0.0, 20.0)
            .key("flightSpeed"))
         .build();
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.amphithere.maxHealth", 50.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.amphithere.attackDamage", 7.0
            )
            .min(0.0)
            .key("attackDamage"))
         .build();

      public AmphithereConfig() {
         super("amphithere", "config.iceandfire.category.amphithere");
      }
   }

   public static class ArmorsConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry dragonSteelBaseDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.armors.dragonSteelBaseDamage", 25.0
            )
            .min(0.0)
            .key("dragonSteelBaseDamage"))
         .build();
      public final IntegerEntry dragonSteelBaseDurability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonSteelBaseDurability", 8000
            )
            .min(0)
            .key("dragonSteelBaseDurability"))
         .build();
      public final DoubleEntry dragonsteelArmorToughness = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.armors.dragonsteelArmorToughness", 6.0
            )
            .min(0.0)
            .key("dragonsteelArmorToughness"))
         .build();
      public final IntegerEntry dragonsteelHelmetArmor = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelHelmetArmor", 7
            )
            .min(0)
            .key("dragonsteelHelmetArmor"))
         .build();
      public final IntegerEntry dragonsteelHelmetDurability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelHelmetDurability", 1760
            )
            .min(0)
            .key("dragonsteelHelmetDurability"))
         .build();
      public final IntegerEntry dragonsteelChestplateArmor = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelChestplateArmor", 12
            )
            .min(0)
            .key("dragonsteelChestplateArmor"))
         .build();
      public final IntegerEntry dragonsteelChestplateDurability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelChestplateDurability", 2560
            )
            .min(0)
            .key("dragonsteelChestplateDurability"))
         .build();
      public final IntegerEntry dragonsteelLeggingsArmor = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelLeggingsArmor", 9
            )
            .min(0)
            .key("dragonsteelLeggingsArmor"))
         .build();
      public final IntegerEntry dragonsteelLeggingsDurability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelLeggingsDurability", 2400
            )
            .min(0)
            .key("dragonsteelLeggingsDurability"))
         .build();
      public final IntegerEntry dragonsteelBootsArmor = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelBootsArmor", 6
            )
            .min(0)
            .key("dragonsteelBootsArmor"))
         .build();
      public final IntegerEntry dragonsteelBootsDurability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelBootsDurability", 2080
            )
            .min(0)
            .key("dragonsteelBootsDurability"))
         .build();
      public final IntegerEntry dragonsteelArmorEnchantability = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.armors.dragonsteelArmorEnchantability", 30
            )
            .min(0)
            .key("dragonsteelArmorEnchantability"))
         .build();
      public final DoubleEntry dragonsteelArmorKnockbackResistance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.armors.dragonsteelArmorKnockbackResistance", 0.1
            )
            .range(0.0, 0.25)
            .key("dragonsteelArmorKnockbackResistance"))
         .build();

      public ArmorsConfig() {
         super("armors", "config.iceandfire.category.armors");
      }
   }

   public static class CockatriceConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry spawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.cockatrice.spawn", true).key("spawn")).build();
      public final IntegerEntry spawnWeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.cockatrice.spawnWeight", 4
            )
            .range(0, 20)
            .key("spawnWeight"))
         .build();
      public final IntegerEntry chickenSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.cockatrice.chickenSearchLength", 32
            )
            .range(0, 1024)
            .key("chickenSearchLength"))
         .build();
      public final DoubleEntry eggChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cockatrice.eggChance", 0.03333333333333333
            )
            .range(0.0, 1.0)
            .key("eggChance"))
         .build();
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cockatrice.maxHealth", 40.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final BooleanEntry chickensLayRottenEggs = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.cockatrice.chickensLayRottenEggs", true)
            .key("chickensLayRottenEggs"))
         .build();

      public CockatriceConfig() {
         super("cockatrice", "config.iceandfire.category.cockatrice");
      }
   }

   public static class CyclopsConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry spawnWanderingChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cyclops.spawnWanderingChance", 0.0011111111111111111
            )
            .range(0.0, 1.0)
            .key("spawnWanderingChance"))
         .build();
      public final IntegerEntry sheepSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.cyclops.sheepSearchLength", 17
            )
            .range(0, 1024)
            .key("sheepSearchLength"))
         .build();
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cyclops.maxHealth", 150.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cyclops.attackDamage", 15.0
            )
            .min(0.0)
            .key("attackDamage"))
         .build();
      public final DoubleEntry biteDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.cyclops.biteDamage", 40.0
            )
            .min(0.0)
            .key("biteDamage"))
         .build();
      public final BooleanEntry griefing = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.cyclops.griefing", true).key("griefing")).build();

      public CyclopsConfig() {
         super("cyclops", "config.iceandfire.category.cyclops");
      }
   }

   public static class DeathwormConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry spawnChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.deathworm.spawnChance", 0.03333333333333333
            )
            .range(0.0, 1.0)
            .key("spawnChance"))
         .build();
      public final IntegerEntry targetSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.deathworm.targetSearchLength", 48
            )
            .range(0, 1024)
            .key("targetSearchLength"))
         .build();
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.deathworm.maxHealth", 10.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.deathworm.attackDamage", 3.0
            )
            .range(0.0, 30.0)
            .key("attackDamage"))
         .build();
      public final BooleanEntry attackMonsters = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.deathworm.attackMonsters", true)
            .key("attackMonsters"))
         .build();

      public DeathwormConfig() {
         super("deathworm", "config.iceandfire.category.deathworm");
      }
   }

   public static class DragonConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.maxHealth", 500.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final IntegerEntry eggBornTime = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.eggBornTime", 7200
            )
            .min(0)
            .key("eggBornTime"))
         .build();
      public final IntegerEntry maxPathingNodes = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.maxPathingNodes", 5000
            )
            .min(0)
            .key("maxPathingNodes"))
         .build();
      public final BooleanEntry villagersFear = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.villagersFear", true)
            .key("villagersFear"))
         .build();
      public final BooleanEntry animalsFear = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.animalsFear", true).key("animalsFear"))
         .build();
      public final SeparatorEntry s1 = SeparatorEntry.builder().build();
      public final BooleanEntry generateSkeletons = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.generate.skeletons", true)
            .key("generate.skeletons"))
         .build();
      public final DoubleEntry generateSkeletonChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.generate.skeletonChance", 0.0033333333333333335
            )
            .range(0.0, 1.0)
            .key("generate.skeletonChance"))
         .build();
      public final DoubleEntry generateDenGoldChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.generate.denGoldAmount", 0.25
            )
            .range(0.0, 1.0)
            .key("generate.denGoldAmount"))
         .build();
      public final DoubleEntry generateOreRatio = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.generate.oreRatio", 0.022222222222222223
            )
            .range(0.0, 1.0)
            .key("generate.oreRatio"))
         .build();
      public final SeparatorEntry s2 = SeparatorEntry.builder().build();
      public final BooleanEntry griefing = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.griefing", true).key("griefing")).build();
      public final BooleanEntry tamedGriefing = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.tamedGriefing", true)
            .key("tamedGriefing"))
         .build();
      public final IntegerEntry flapNoiseDistance = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.flapNoiseDistance", 4
            )
            .range(0, 32)
            .key("flapNoiseDistance"))
         .build();
      public final IntegerEntry fluteDistance = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.fluteDistance", 8
            )
            .range(0, 512)
            .key("fluteDistance"))
         .build();
      public final IntegerEntry attackDamage = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.attackDamage", 17
            )
            .min(0)
            .key("attackDamage"))
         .build();
      public final DoubleEntry attackDamageFire = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.attackDamageFire", 2.0
            )
            .min(0.0)
            .key("attackDamageFire"))
         .build();
      public final DoubleEntry attackDamageIce = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.attackDamageIce", 2.5
            )
            .min(0.0)
            .key("attackDamageIce"))
         .build();
      public final DoubleEntry attackDamageLightning = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.attackDamageLightning", 3.5
            )
            .min(0.0)
            .key("attackDamageLightning"))
         .build();
      public final IntegerEntry maxFlight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.maxFlight", 256
            )
            .range(-2302, 2302)
            .key("maxFlight"))
         .build();
      public final IntegerEntry goldSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.goldSearchLength", 30
            )
            .range(0, 256)
            .key("goldSearchLength"))
         .build();
      public final BooleanEntry canHealFromBiting = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.canHealFromBiting", false)
            .key("canHealFromBiting"))
         .build();
      public final BooleanEntry canDespawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.canDespawn", true).key("canDespawn"))
         .build();
      public final BooleanEntry sleep = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.sleep", true).key("sleep")).build();
      public final BooleanEntry digWhenStuck = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.digWhenStuck", true).key("digWhenStuck"))
         .build();
      public final IntegerEntry breakBlockCooldown = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.breakBlockCooldown", 5
            )
            .min(0)
            .key("breakBlockCooldown"))
         .build();
      public final IntegerEntry targetSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.targetSearchLength", 128
            )
            .range(0, 1024)
            .key("targetSearchLength"))
         .build();
      public final IntegerEntry wanderFromHomeDistance = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.wanderFromHomeDistance", 40
            )
            .range(0, 1024)
            .key("wanderFromHomeDistance"))
         .build();
      public final IntegerEntry hungerTickRate = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.hungerTickRate", 3000
            )
            .min(1)
            .key("hungerTickRate"))
         .build();
      public final DoubleEntry blockBreakingDropChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.blockBreakingDropChance", 0.1
            )
            .range(0.0, 1.0)
            .key("blockBreakingDropChance"))
         .build();
      public final BooleanEntry explosiveBreath = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.explosiveBreath", false)
            .key("explosiveBreath"))
         .build();
      public final BooleanEntry chunkLoadSummonCrystal = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.chunkLoadSummonCrystal", true)
            .key("chunkLoadSummonCrystal"))
         .build();
      public final DoubleEntry dragonFlightSpeedMod = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.dragonFlightSpeedMod", 1.0
            )
            .range(1.0E-4, 50.0)
            .key("dragonFlightSpeedMod"))
         .build();
      public final IntegerEntry maxTamedDragonAge = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.maxTamedDragonAge", 128
            )
            .range(0, 128)
            .key("maxTamedDragonAge"))
         .build();
      public final DoubleEntry maxBreathTimeMul = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.maxBreathTimeMul", 2.0
            )
            .min(0.0)
            .key("maxBreathTimeMul"))
         .build();
      public final BooleanEntry neutralToPlayer = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.neutralToPlayer", false)
            .key("neutralToPlayer"))
         .build();
      public final BooleanEntry enableBrushDragonScales = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.enableBrushDragonScales", true)
            .key("enableBrushDragonScales"))
         .build();
      public final IntegerEntry maxBrushScalesDropPerTime = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.dragon.maxBrushScalesDropPerTime", 2
            )
            .min(1)
            .key("maxBrushScalesDropPerTime"))
         .build();
      public final DoubleEntry brushTimesMul = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.dragon.brushTimesMul", 1.0
            )
            .min(0.0)
            .key("brushTimesMul"))
         .build();
      public final SeparatorEntry s3 = SeparatorEntry.builder().build();
      public final BooleanEntry lootSkull = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.loot.skull", true).key("loot.skull"))
         .build();
      public final BooleanEntry lootHeart = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.loot.heart", true).key("loot.heart"))
         .build();
      public final BooleanEntry lootBlood = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.dragon.loot.blood", true).key("loot.blood"))
         .build();

      public DragonConfig() {
         super("dragon", "config.iceandfire.category.dragon");
      }
   }

   public static class GhostConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.ghost.maxHealth", 30.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.ghost.attackDamage", 3.0
            )
            .min(0.0)
            .key("attackDamage"))
         .build();
      public final BooleanEntry fromPlayerDeaths = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.ghost.fromPlayerDeaths", true)
            .key("fromPlayerDeaths"))
         .build();
      public final BooleanEntry alwaysSpawnFromChest = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.ghost.alwaysSpawnFromChest", false)
            .key("alwaysSpawnFromChest"))
         .build();

      public GhostConfig() {
         super("ghost", "config.iceandfire.category.ghost");
      }
   }

   public static class GorgonConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.gorgon.maxHealth", 100.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();

      public GorgonConfig() {
         super("gorgon", "config.iceandfire.category.gorgon");
      }
   }

   public static class HippocampusConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry spawnChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.hippocampus.spawnChance", 0.025
            )
            .range(0.0, 1.0)
            .key("spawnChance"))
         .build();
      public final DoubleEntry swimSpeedMod = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.hippocampus.swimSpeedMod", 1.0
            )
            .range(1.0E-4, 10.0)
            .key("swimSpeedMod"))
         .build();

      public HippocampusConfig() {
         super("hippocampus", "config.iceandfire.category.hippocampus");
      }
   }

   public static class HippogryphsConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry spawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.hippogryphs.spawn", true).key("spawn")).build();
      public final IntegerEntry spawnWeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.hippogryphs.spawnWeight", 2
            )
            .range(0, 20)
            .key("spawnWeight"))
         .build();
      public final DoubleEntry fightSpeedMod = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.hippogryphs.fightSpeedMod", 1.0
            )
            .range(1.0E-4, 50.0)
            .key("fightSpeedMod"))
         .build();

      public HippogryphsConfig() {
         super("hippogryphs", "config.iceandfire.category.hippogryphs");
      }
   }

   public static class HydraConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.hydra.maxHealth", 250.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();

      public HydraConfig() {
         super("hydra", "config.iceandfire.category.hydra");
      }
   }

   public static class LichConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry spawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.lich.spawn", true).key("spawn")).build();
      public final IntegerEntry spawnWeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.lich.spawnWeight", 4
            )
            .range(0, 20)
            .key("spawnWeight"))
         .build();
      public final DoubleEntry spawnChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.lich.spawnChance", 0.03333333333333333
            )
            .range(0.0, 1.0)
            .key("spawnChance"))
         .build();

      public LichConfig() {
         super("lich", "config.iceandfire.category.lich");
      }
   }

   public static class Misc extends AutoInitConfigCategoryBase {
      public final BooleanEntry enableDragonSeeker = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.misc.enableDragonSeeker", true)
            .key("enableDragonSeeker"))
         .build();
      public final DoubleEntry dreadQueenMaxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.misc.dreadQueenMaxHealth", 750.0
            )
            .min(0.0)
            .key("dreadQueenMaxHealth"))
         .build();

      public Misc() {
         super("misc", "config.iceandfire.category.misc");
      }
   }

   public static class PixieConfig extends AutoInitConfigCategoryBase {
      public final IntegerEntry size = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.pixie.size", 5
            )
            .range(0, 100)
            .key("size"))
         .build();
      public final BooleanEntry stealItems = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.pixie.stealItems", false).key("stealItems"))
         .build();

      public PixieConfig() {
         super("pixie", "config.iceandfire.category.pixie");
      }
   }

   public static class SeaSerpentConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry spawnChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.seaSerpent.spawnChance", 0.004
            )
            .range(0.0, 1.0)
            .key("spawnChance"))
         .build();
      public final BooleanEntry griefing = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.seaSerpent.griefing", true).key("griefing")).build();
      public final DoubleEntry baseHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.seaSerpent.baseHealth", 20.0
            )
            .min(0.0)
            .key("baseHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.seaSerpent.attackDamage", 4.0
            )
            .min(0.0)
            .key("attackDamage"))
         .build();

      public SeaSerpentConfig() {
         super("seaSerpent", "config.iceandfire.category.seaSerpent");
      }
   }

   public static class SirenConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.siren.maxHealth", 50.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final IntegerEntry maxSingTime = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.siren.maxSingTime", 12000
            )
            .min(0)
            .key("maxSingTime"))
         .build();
      public final IntegerEntry timeBetweenSongs = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.siren.timeBetweenSongs", 2000
            )
            .min(0)
            .key("timeBetweenSongs"))
         .build();

      public SirenConfig() {
         super("siren", "config.iceandfire.category.siren");
      }
   }

   public static class StymphalianBirdConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry spawnChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.bird.spawnChance", 0.0125
            )
            .range(0.0, 1.0)
            .key("spawnChance"))
         .build();
      public final IntegerEntry targetSearchLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.bird.targetSearchLength", 48
            )
            .range(0, 1024)
            .key("targetSearchLength"))
         .build();
      public final DoubleEntry featherDropChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.bird.featherDropChance", 0.04
            )
            .range(0.0, 1.0)
            .key("featherDropChance"))
         .build();
      public final DoubleEntry featherAttackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.bird.featherAttackDamage", 1.0
            )
            .min(0.0)
            .key("featherAttackDamage"))
         .build();
      public final IntegerEntry flockLength = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.bird.flockLength", 40
            )
            .range(0, 200)
            .key("flockLength"))
         .build();
      public final IntegerEntry flightHeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.bird.flightHeight", 80
            )
            .range(-2032, 2032)
            .key("flightHeight"))
         .build();
      public final BooleanEntry attackAnimals = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.bird.attackAnimals", false)
            .key("attackAnimals"))
         .build();

      public StymphalianBirdConfig() {
         super("bird", "config.iceandfire.category.bird");
      }
   }

   public static class ToolsConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry dragonFireAbility = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.tools.dragonFireAbility", true)
            .key("dragonFireAbility"))
         .build();
      public final BooleanEntry dragonIceAbility = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.tools.dragonIceAbility", true)
            .key("dragonIceAbility"))
         .build();
      public final BooleanEntry dragonLightningAbility = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.tools.dragonLightningAbility", true)
            .key("dragonLightningAbility"))
         .build();
      public final IntegerEntry dragonsteelFireDuration = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.tools.dragonsteelFireDuration", 15
            )
            .min(0)
            .key("dragonsteelFireDuration"))
         .build();
      public final IntegerEntry dragonBloodFireDuration = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.tools.dragonBloodFireDuration", 5
            )
            .min(0)
            .key("dragonBloodFireDuration"))
         .build();
      public final IntegerEntry dragonsteelFrozenDuration = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.tools.dragonsteelFrozenDuration", 300
            )
            .min(0)
            .key("dragonsteelFrozenDuration"))
         .build();
      public final IntegerEntry dragonBloodFrozenDuration = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.tools.dragonBloodFrozenDuration", 100
            )
            .min(0)
            .key("dragonBloodFrozenDuration"))
         .build();
      public final BooleanEntry phantasmalBladeAbility = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.tools.phantasmalBladeAbility", true)
            .key("phantasmalBladeAbility"))
         .build();
      public final SeparatorEntry s1 = SeparatorEntry.builder().build();
      public final DoubleEntry dragonLightningSearchRange = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.tools.dragonLightningSearchRange", 10.0
            )
            .range(0.0, 128.0)
            .key("dragonLightningSearchRange"))
         .build();
      public final DoubleEntry dragonLightningDamageReduction = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.tools.dragonLightningDamageReduction", 0.5
            )
            .range(0.0, 1.0)
            .key("dragonLightningDamageReduction"))
         .build();
      public final IntegerEntry dragonLightningMaxSearchCount = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.tools.dragonLightningMaxSearchCount", 10
            )
            .range(0, 1024)
            .key("dragonLightningMaxSearchCount"))
         .build();

      public ToolsConfig() {
         super("tools", "config.iceandfire.category.tools");
      }
   }

   public static class TrollConfig extends AutoInitConfigCategoryBase {
      public final BooleanEntry spawn = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.troll.spawn", true).key("spawn")).build();
      public final IntegerEntry spawnWeight = (IntegerEntry)((com.iafenvoy.jupiter.config.entry.IntegerEntry.Builder)IntegerEntry.builder(
               "config.iceandfire.troll.spawnWeight", 60
            )
            .range(0, 200)
            .key("spawnWeight"))
         .build();
      public final BooleanEntry dropWeapon = (BooleanEntry)((Builder)BooleanEntry.builder("config.iceandfire.troll.dropWeapon", true).key("dropWeapon"))
         .build();
      public final DoubleEntry maxHealth = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.troll.maxHealth", 50.0
            )
            .min(1.0)
            .key("maxHealth"))
         .build();
      public final DoubleEntry attackDamage = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.troll.attackDamage", 10.0
            )
            .min(0.0)
            .key("attackDamage"))
         .build();

      public TrollConfig() {
         super("troll", "config.iceandfire.category.troll");
      }
   }

   public static class WorldGenConfig extends AutoInitConfigCategoryBase {
      public final DoubleEntry dangerousDistanceLimit = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.dangerousDistanceLimit", 1000.0
            )
            .min(0.0)
            .key("dangerousDistanceLimit"))
         .build();
      public final DoubleEntry generateFireDragonCaveChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateFireDragonCaveChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateFireDragonCaveChance"))
         .build();
      public final DoubleEntry generateFireDragonRoostChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateFireDragonRoostChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateFireDragonRoostChance"))
         .build();
      public final DoubleEntry generateIceDragonCaveChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateIceDragonCaveChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateIceDragonCaveChance"))
         .build();
      public final DoubleEntry generateIceDragonRoostChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateIceDragonRoostChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateIceDragonRoostChance"))
         .build();
      public final DoubleEntry generateLightningDragonCaveChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateLightningDragonCaveChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateLightningDragonCaveChance"))
         .build();
      public final DoubleEntry generateLightningDragonRoostChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateLightningDragonRoostChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateLightningDragonRoostChance"))
         .build();
      public final DoubleEntry generateCyclopsCaveChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateCyclopsCaveChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateCyclopsCaveChance"))
         .build();
      public final DoubleEntry generateGorgonTempleChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateGorgonTempleChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateGorgonTempleChance"))
         .build();
      public final DoubleEntry generateGraveYardChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateGraveYardChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateGraveYardChance"))
         .build();
      public final DoubleEntry generateHydraCaveChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateHydraCaveChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateHydraCaveChance"))
         .build();
      public final DoubleEntry generateMausoleumChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateMausoleumChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateMausoleumChance"))
         .build();
      public final DoubleEntry generatePixieVillageChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generatePixieVillageChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generatePixieVillageChance"))
         .build();
      public final DoubleEntry generateSirenIslandChance = (DoubleEntry)((com.iafenvoy.jupiter.config.entry.DoubleEntry.Builder)DoubleEntry.builder(
               "config.iceandfire.worldgen.generateSirenIslandChance", 0.5
            )
            .range(0.0, 1.0)
            .key("generateSirenIslandChance"))
         .build();

      public WorldGenConfig() {
         super("worldgen", "config.iceandfire.category.worldgen");
      }
   }
}
