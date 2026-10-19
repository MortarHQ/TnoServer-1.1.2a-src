package com.github.L_Ender.cataclysm.config;

import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ClientConfig {
   private static final String LANG_PREFIX = "config.cataclysm.";
   public final BooleanValue shadersCompat;
   public final BooleanValue firstPerson;
   public final BooleanValue thirdPerson;
   public final BooleanValue ScreenShake;
   public final BooleanValue custombossbar;
   public final BooleanValue BossMusic;
   public final IntValue BossMusicVolume;

   public ClientConfig(Builder builder) {
      builder.push("client");
      this.shadersCompat = buildBoolean(builder, "shadersCompat", CMClientConfig.shadersCompat, "Off the Custom Rendering");
      this.ScreenShake = buildBoolean(builder, "ScreenShake(on/off)", CMClientConfig.ScreenShake, "ScreenShake(on/off)");
      this.firstPerson = buildBoolean(builder, "setThirdPerson(on/off)", CMClientConfig.thirdPerson, "Forced viewpoint change when hit by a grab attack");
      this.thirdPerson = buildBoolean(builder, "setFirstPerson(on/off)", CMClientConfig.firstPerson, "Forced viewpoint change when hit by a grab attack");
      this.custombossbar = buildBoolean(builder, "custombossbar(on/off)", CMClientConfig.customBossBars, "custombossbar(on/off)");
      this.BossMusic = buildBoolean(builder, "BossMusic(on/off)", CMClientConfig.BossMusic, "custombossbar(on/off)");
      this.BossMusicVolume = buildInt(builder, "BossMusicVolume", CMClientConfig.BossMusicVolume, 1, 100, "BossMusicVolume");
      builder.pop();
   }

   private static BooleanValue buildBoolean(Builder builder, String trans, boolean defaultValue, String comment) {
      return builder.comment(comment).translation("config.cataclysm." + trans).define(trans, defaultValue);
   }

   private static IntValue buildInt(Builder builder, String trans, int defaultValue, int min, int max, String comment) {
      return builder.comment(comment).translation("config.cataclysm." + trans).defineInRange(trans, defaultValue, min, max);
   }

   private static DoubleValue buildDouble(Builder builder, String trans, double defaultValue, double min, double max, String comment) {
      return builder.comment(comment).translation("config.cataclysm." + trans).defineInRange(trans, defaultValue, min, max);
   }
}
