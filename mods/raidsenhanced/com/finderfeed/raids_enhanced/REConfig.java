package com.finderfeed.raids_enhanced;

import com.finderfeed.fdlib.systems.config.Comment;
import com.finderfeed.fdlib.systems.config.ConfigValue;
import com.finderfeed.fdlib.systems.config.ReflectiveJsonConfig;

public class REConfig extends ReflectiveJsonConfig {
   @ConfigValue
   public float zapperStaffBallLightningDamage = 7.0F;
   @ConfigValue
   public float zapperStaffLightningDamage = 15.0F;
   @ConfigValue
   public int zapperStaffLightningUseCooldown = 60;
   @ConfigValue
   public int zapperStaffBallLightningUseCooldown = 20;
   @ConfigValue
   public float handcannonBombDamage = 10.0F;
   @ConfigValue
   public int handcannonUseCooldown = 30;
   @ConfigValue
   public REConfig.RaidDrillConfig raidDrill = new REConfig.RaidDrillConfig();

   public REConfig() {
      super(RaidsEnhanced.location("raidsenhanced"));
   }

   public boolean isClientside() {
      return false;
   }

   public static class RaidDrillConfig {
      @ConfigValue
      public int minRaidersSpawn = 2;
      @ConfigValue
      public int maxRaidersSpawn = 3;
      @ConfigValue
      @Comment("If raider drill is not touched after it spawns illagers it burrows again after this time (in ticks)")
      public int burrowAgainAfter = 200;
      @ConfigValue
      @Comment("This is to prevent it infinitely burrowing and spawning illagers.")
      public int automaticBurrowTimes = 3;
   }
}
