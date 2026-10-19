package com.b1n_ry.yigd.config;

import java.util.ArrayList;
import java.util.List;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

public class RespawnConfig {
   @Comment("On respawn, all players will receive these effects")
   public List<RespawnConfig.EffectConfig> respawnEffects = new ArrayList<>();
   @Comment("HP given to player at respawn. If 0 or negative, default health will apply")
   public int respawnHealth = -1;
   @Comment("If false, player will respawn with the same hunger level as when they died")
   public boolean resetHunger = true;
   @Comment("Hunger given to player at respawn. If negative, default hunger will apply")
   public int respawnHunger = -1;
   @Comment("If false, player will respawn with the same saturation level as when they died")
   public boolean resetSaturation = true;
   @Comment("Saturation given to player at respawn. If negative, default saturation will apply")
   public float respawnSaturation = -1.0F;
   @Comment("Extra items that will be given to player once respawned")
   public List<RespawnConfig.ExtraItemDrop> extraItemDrops = new ArrayList<>();
   @Comment("If true, items with both curse of binding and soulbound will not be forced onto the player")
   public boolean treatBindingCurse = true;

   public static class EffectConfig {
      public String effectName = "";
      public int effectLevel = 0;
      public int effectTime = 0;
      public boolean showBubbles = false;
   }

   public static class ExtraItemDrop {
      public String itemId = "";
      public int count = 0;
      public String itemNbt = "";
   }
}
