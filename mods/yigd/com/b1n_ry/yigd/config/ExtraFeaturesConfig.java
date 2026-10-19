package com.b1n_ry.yigd.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.RequiresRestart;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;

public class ExtraFeaturesConfig {
   @CollapsibleObject
   public boolean enableSoulbound = true;
   @CollapsibleObject
   public ExtraFeaturesConfig.DeathSightConfig deathSightEnchant = new ExtraFeaturesConfig.DeathSightConfig();
   @CollapsibleObject
   public ExtraFeaturesConfig.GraveKeyConfig graveKeys = new ExtraFeaturesConfig.GraveKeyConfig();
   @CollapsibleObject
   public ExtraFeaturesConfig.ScrollConfig deathScroll = new ExtraFeaturesConfig.ScrollConfig();
   @CollapsibleObject
   public ExtraFeaturesConfig.GraveCompassConfig graveCompass = new ExtraFeaturesConfig.GraveCompassConfig();

   public static class DeathSightConfig {
      public boolean enabled = false;
      public double range = 64.0;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public ExtraFeaturesConfig.DeathSightConfig.GraveTargets targets = ExtraFeaturesConfig.DeathSightConfig.GraveTargets.PLAYER_GRAVES;

      public enum GraveTargets {
         OWN_GRAVES,
         PLAYER_GRAVES,
         ALL_GRAVES;
      }
   }

   public static class GraveCompassConfig {
      public boolean receiveOnRespawn = false;
      public boolean consumeOnUse = true;
      public boolean deleteWhenUnlinked = true;
      public boolean cloneRecoveryCompassWithGUI = false;
      @RequiresRestart
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget pointToClosest = ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED;

      public enum CompassGraveTarget {
         DISABLED,
         PLAYER,
         ALL;
      }
   }

   public static class GraveKeyConfig {
      public boolean enabled = false;
      public boolean rebindable = true;
      public boolean required = true;
      public boolean receiveOnRespawn = true;
      public boolean obtainableFromGui = false;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public ExtraFeaturesConfig.GraveKeyConfig.KeyTargeting targeting = ExtraFeaturesConfig.GraveKeyConfig.KeyTargeting.PLAYER_GRAVE;

      public enum KeyTargeting {
         ANY_GRAVE,
         PLAYER_GRAVE,
         SPECIFIC_GRAVE;
      }
   }

   public static class ScrollConfig {
      public boolean enabled = false;
      public boolean rebindable = false;
      public boolean receiveOnRespawn = false;
      @EnumHandler(option = EnumDisplayOption.BUTTON)
      public ExtraFeaturesConfig.ScrollConfig.ClickFunction clickFunction = ExtraFeaturesConfig.ScrollConfig.ClickFunction.VIEW_CONTENTS;
      public boolean consumeOnUse = false;
      public int useTime = 0;
      public int useCooldown = 0;

      public enum ClickFunction {
         RESTORE_CONTENTS,
         VIEW_CONTENTS,
         TELEPORT_TO_LOCATION;
      }
   }
}
