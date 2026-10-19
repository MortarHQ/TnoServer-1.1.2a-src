package com.foxablazeultimate.world;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;

public final class FoxAblazeGameRules {
   public static Key<BooleanValue> RAPHAEL_TRULY_UNIQUE;
   public static Key<BooleanValue> BEELZEBUB_TRULY_UNIQUE;
   public static Key<BooleanValue> VELDORA_TRULY_UNIQUE;
   public static Key<BooleanValue> SATANAEL_TRULY_UNIQUE;
   public static Key<BooleanValue> SHUB_NIGGURATH_TRULY_UNIQUE;
   public static Key<BooleanValue> FORTUNA_TRULY_UNIQUE;
   public static Key<BooleanValue> MAMMON_TRULY_UNIQUE;
   public static Key<BooleanValue> HELL_SNIPER_TRULY_UNIQUE;
   public static Key<BooleanValue> YOG_SOTHOTH_TRULY_UNIQUE;
   public static Key<BooleanValue> BEELZEBUB_DISABLE_CAPTURE;
   public static Key<BooleanValue> PROTECT_ULTIMATE_ON_RESET;

   private FoxAblazeGameRules() {
   }

   public static void init() {
      RAPHAEL_TRULY_UNIQUE = GameRules.register("raphaelTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      BEELZEBUB_TRULY_UNIQUE = GameRules.register("beelzebubTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      VELDORA_TRULY_UNIQUE = GameRules.register("veldoraTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      SATANAEL_TRULY_UNIQUE = GameRules.register("satanaelTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      SHUB_NIGGURATH_TRULY_UNIQUE = GameRules.register("shubNiggurathTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      FORTUNA_TRULY_UNIQUE = GameRules.register("fortunaTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      MAMMON_TRULY_UNIQUE = GameRules.register("mammonTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      HELL_SNIPER_TRULY_UNIQUE = GameRules.register("hellSniperTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      YOG_SOTHOTH_TRULY_UNIQUE = GameRules.register("yogSothothTrulyUnique", Category.PLAYER, BooleanValue.create(true));
      BEELZEBUB_DISABLE_CAPTURE = GameRules.register("beelzebubDisableCapture", Category.PLAYER, BooleanValue.create(false));
      PROTECT_ULTIMATE_ON_RESET = GameRules.register("protectUltimateOnReset", Category.PLAYER, BooleanValue.create(false));
   }
}
