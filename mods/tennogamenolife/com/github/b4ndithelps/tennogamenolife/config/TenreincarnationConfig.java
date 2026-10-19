package com.github.b4ndithelps.tennogamenolife.config;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = "tennogamenolife")
public class TenreincarnationConfig {
   private static final Logger LOGGER = LogManager.getLogger(TenreincarnationConfig.class);
   public static final Builder COMMON_BUILDER = new Builder();
   public static final ModConfigSpec COMMON_SPEC;
   public static final TenreincarnationConfig.CommonConfig COMMON;

   public static boolean isSkillEnabled(ResourceLocation skillId) {
      if (skillId != null && "tennogamenolife".equals(skillId.getNamespace())) {
         return switch (skillId.getPath()) {
            case "barrier" -> COMMON.barrierEnabled.get();
            case "black_arrow" -> COMMON.blackArrowEnabled.get();
            case "magic_beads" -> COMMON.magicBeadsEnabled.get();
            case "tengeki" -> COMMON.tengekiEnabled.get();
            case "godly_smite" -> COMMON.godlySmiteEnabled.get();
            case "reisen" -> COMMON.reisenEnabled.get();
            case "far_cry" -> COMMON.farCryEnabled.get();
            default -> true;
         };
      } else {
         return true;
      }
   }

   public static boolean isDestructiveSkill(ResourceLocation skillId) {
      if (skillId != null && "tennogamenolife".equals(skillId.getNamespace())) {
         return switch (skillId.getPath()) {
            case "tengeki", "godly_smite", "reisen", "far_cry" -> true;
            default -> false;
         };
      } else {
         return false;
      }
   }

   public static boolean isDestructiveSkillsDisabled() {
      return (Boolean)COMMON.destructiveSkillsDisabled.get();
   }

   public static boolean shouldPreventTerrainDamage() {
      return (Boolean)COMMON.preventTerrainDamage.get();
   }

   public static boolean shouldGiveExMachinaWeaponOnStart() {
      return (Boolean)COMMON.giveExMachinaWeaponOnStart.get();
   }

   public static boolean shouldGiveFlugelSickleOnStart() {
      return (Boolean)COMMON.giveFlugelSickleOnStart.get();
   }

   @SubscribeEvent
   public static void onModConfigEvent(ModConfigEvent configEvent) {
      if (configEvent.getConfig().getSpec() == COMMON_SPEC) {
         LOGGER.info("Loading TenNoGameNoLife config...");
      }
   }

   static {
      Pair<TenreincarnationConfig.CommonConfig, ModConfigSpec> pair = new Builder().configure(TenreincarnationConfig.CommonConfig::new);
      COMMON_SPEC = (ModConfigSpec)pair.getRight();
      COMMON = (TenreincarnationConfig.CommonConfig)pair.getLeft();
   }

   public static class CommonConfig {
      public final BooleanValue enableSkillSpecialization;
      public final DoubleValue skillExperienceMultiplier;
      public final IntValue maxSkillLevel;
      public final BooleanValue barrierEnabled;
      public final BooleanValue blackArrowEnabled;
      public final BooleanValue magicBeadsEnabled;
      public final BooleanValue tengekiEnabled;
      public final BooleanValue godlySmiteEnabled;
      public final BooleanValue reisenEnabled;
      public final BooleanValue farCryEnabled;
      public final BooleanValue destructiveSkillsDisabled;
      public final BooleanValue preventTerrainDamage;
      public final BooleanValue giveExMachinaWeaponOnStart;
      public final BooleanValue giveFlugelSickleOnStart;

      public CommonConfig(Builder builder) {
         builder.comment("Skill Configuration").push("skills");
         this.enableSkillSpecialization = builder.comment("Enable skill specialization feature").define("enableSkillSpecialization", true);
         this.skillExperienceMultiplier = builder.comment("Multiplier for skill experience gain").defineInRange("skillExperienceMultiplier", 1.0, 0.1, 10.0);
         this.maxSkillLevel = builder.comment("Maximum level for skills").defineInRange("maxSkillLevel", 100, 1, 1000);
         builder.comment("Enable or disable individual mod skills. Disabled skills cannot be obtained.").push("availability");
         this.barrierEnabled = builder.comment("Enable the Barrier skill").define("barrier", true);
         this.blackArrowEnabled = builder.comment("Enable the Black Arrow skill").define("black_arrow", true);
         this.magicBeadsEnabled = builder.comment("Enable the Magic Beads skill").define("magic_beads", true);
         this.tengekiEnabled = builder.comment("Enable the Tengeki skill").define("tengeki", true);
         this.godlySmiteEnabled = builder.comment("Enable the Godly Smite skill").define("godly_smite", true);
         this.reisenEnabled = builder.comment("Enable the Reisen skill").define("reisen", true);
         this.farCryEnabled = builder.comment("Enable the Far Cry skill").define("far_cry", true);
         builder.pop();
         builder.pop();
         builder.comment("Starting equipment granted when a player first becomes one of the mod races.").push("startingEquipment");
         this.giveExMachinaWeaponOnStart = builder.comment(
               new String[]{"Whether Ex Machina players receive an Ex Machina Cannon when starting as the race.", "是否让机铠种玩家在开局时自动获得机铠炮。"}
            )
            .define("giveExMachinaWeapon", true);
         this.giveFlugelSickleOnStart = builder.comment(
               new String[]{"Whether Flugel players receive a Flugel Sickle when starting as the race.", "是否让天翼种玩家在开局时自动获得天翼之镰。"}
            )
            .define("giveFlugelSickle", true);
         builder.pop();
         builder.comment("Server Safety Rules for destructive ultimate skills (Tengeki / Godly Smite / Reisen / Far Cry).").push("safety");
         this.destructiveSkillsDisabled = builder.comment(
               new String[]{
                  "If true, all destructive ultimate skills (Tengeki, Godly Smite, Reisen, Far Cry) are completely disabled.",
                  "Activations will be blocked and a chat message will inform the player.",
                  "Default: false (skills enabled)."
               }
            )
            .define("destructiveSkillsDisabled", false);
         this.preventTerrainDamage = builder.comment(
               new String[]{
                  "Safety fallback: if true, destructive ultimate skills will NOT damage blocks or modify terrain,",
                  "even when the skills themselves are allowed to be cast (entity damage / VFX still apply).",
                  "Recommended for hardcore PvP / survival servers that don't want griefing. Default: false (skills behave as designed)."
               }
            )
            .define("preventTerrainDamage", false);
         builder.pop();
      }
   }
}
