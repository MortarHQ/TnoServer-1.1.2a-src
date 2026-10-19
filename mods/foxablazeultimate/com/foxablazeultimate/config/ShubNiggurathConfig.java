package com.foxablazeultimate.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ShubNiggurathConfig extends ManasConfig {
   public ShubNiggurathConfig.ShubNiggurathSettings ShubNiggurath = new ShubNiggurathConfig.ShubNiggurathSettings();

   public static ShubNiggurathConfig get() {
      return ShubNiggurathConfig.Holder.instance;
   }

   public static void setInstance(ShubNiggurathConfig config) {
      ShubNiggurathConfig.Holder.instance = config;
   }

   public String getFileName() {
      return "shub_niggurath";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      super.load();
      if (this.migrateCreationRecipes()) {
         this.save();
      }
   }

   private boolean migrateCreationRecipes() {
      List<String> configured = this.ShubNiggurath.creationRecipes;
      List<String> migrated = new ArrayList<>();
      boolean changed = configured == null;
      if (configured != null) {
         for (String recipe : configured) {
            if (ShubNiggurathConfig.RecipeDefaults.RETIRED.contains(canonicalRecipe(recipe))) {
               changed = true;
            } else {
               migrated.add(recipe);
            }
         }
      }

      if (configured == null || this.ShubNiggurath.creationRecipeDataVersion < 1) {
         Set<String> existing = new HashSet<>();
         migrated.forEach(recipex -> existing.add(canonicalRecipe(recipex)));

         for (String recipe : ShubNiggurathConfig.RecipeDefaults.CURRENT) {
            if (existing.add(canonicalRecipe(recipe))) {
               migrated.add(recipe);
               changed = true;
            }
         }

         this.ShubNiggurath.creationRecipeDataVersion = 1;
         changed = true;
      }

      if (changed) {
         this.ShubNiggurath.creationRecipes = migrated;
      }

      return changed;
   }

   private static String canonicalRecipe(String raw) {
      if (raw == null) {
         return "";
      }

      String[] output = raw.trim().toLowerCase(Locale.ROOT).split("=", 2);
      String[] inputs = output.length == 2 ? output[0].split("\\+", 2) : new String[0];
      if (inputs.length != 2) {
         return raw.trim().toLowerCase(Locale.ROOT);
      }

      String first = inputs[0].trim();
      String second = inputs[1].trim();
      if (first.compareTo(second) > 0) {
         String swap = first;
         first = second;
         second = swap;
      }

      return first + "+" + second + "=" + output[1].trim();
   }

   private static final class Holder {
      static ShubNiggurathConfig instance;
   }

   private static final class RecipeDefaults {
      private static final int CURRENT_VERSION = 1;
      private static final List<String> CURRENT = List.of(
         "tensura:self_regeneration+tensura:strengthen_body=tensura:ultraspeed_regeneration",
         "tensura:self_regeneration+tensura:ultraspeed_regeneration=tensura:infinite_regeneration",
         "tensura:ranged_barrier+tensura:body_armor=tensura:multilayer_barrier",
         "tensura:strength+tensura:strengthen_body=tensura:steel_strength",
         "tensura:thought_acceleration+tensura:analytical_appraisal=tensura:sage",
         "tensura:telepathy+tensura:voice_cannon=tensura:thought_communication",
         "tensura:coercion+tensura:magic_aura=tensura:haki",
         "tensura:coercion+tensura:haki=tensura:mortal_fear",
         "tensura:earth_transform+tensura:magic_earth_transform=tensura:earth_manipulation",
         "tensura:flame_transform+tensura:magic_flame_transform=tensura:flame_manipulation",
         "tensura:water_transform+tensura:magic_water_transform=tensura:water_manipulation",
         "tensura:wind_transform+tensura:magic_wind_transform=tensura:wind_manipulation",
         "tensura:space_transform+tensura:magic_space_transform=tensura:spatial_manipulation",
         "tensura:gravity_field+tensura:gravity_flight=tensura:gravity_manipulation",
         "tensura:voice_cannon+tensura:ultrasonic_waves=tensura:sound_manipulation",
         "tensura:thunder_breath+tensura:black_lightning=tensura:lightning_manipulation",
         "tensura:earth_manipulation+tensura:magic_earth_transform=tensura:earth_domination",
         "tensura:flame_manipulation+tensura:magic_flame_transform=tensura:flame_domination",
         "tensura:water_manipulation+tensura:magic_water_transform=tensura:water_domination",
         "tensura:wind_manipulation+tensura:magic_wind_transform=tensura:wind_domination",
         "tensura:spatial_manipulation+tensura:magic_space_transform=tensura:spatial_domination",
         "tensura:gravity_manipulation+tensura:gravity_field=tensura:gravity_domination",
         "tensura:lightning_manipulation+tensura:black_lightning=tensura:lightning_domination",
         "tensura:sound_manipulation+tensura:sense_soundwave=tensura:sound_domination",
         "tensura:flame_manipulation+tensura:sense_heat_source=tensura:flame_domination",
         "tensura:spatial_motion+tensura:multilayer_barrier=tensura:spatial_domination"
      );
      private static final Set<String> RETIRED = Set.of(
         ShubNiggurathConfig.canonicalRecipe("tensura:danger_sense+tensura:magic_sense=tensura:sense_soundwave"),
         ShubNiggurathConfig.canonicalRecipe("tensura:molecular_manipulation+tensura:magic_jamming=tensura:mana_manipulation"),
         ShubNiggurathConfig.canonicalRecipe("tensura:predator+tensura:starved=tensura:gluttony"),
         ShubNiggurathConfig.canonicalRecipe("tensura:ultraspeed_regeneration+tensura:magic_aura=tensura:infinite_regeneration"),
         ShubNiggurathConfig.canonicalRecipe("tensura:water_manipulation+tensura:wind_manipulation=tensura:weather_manipulation"),
         ShubNiggurathConfig.canonicalRecipe("tensura:weather_manipulation+tensura:lightning_manipulation=tensura:weather_domination")
      );
   }

   public static class ShubNiggurathSettings extends ManasSubConfig {
      @Comment("Maximum EP required to evolve Raphael into Shub-Niggurath. This EP is not consumed.")
      public double requiredMaxEP = 2000000.0;
      @Comment("Current magicule required and consumed when Shub-Niggurath is acquired.")
      public double requiredMagicule = 1000000.0;
      @Comment("Number of currently owned mastered skills required for the evolution.")
      public int requiredMasteredSkillCount = 20;
      @Comment("Mastery granted to Shub-Niggurath after evolution.")
      public int acquisitionGrantMastery = 0;
      @Comment("Chance for a Harvest Crystal to drop when a player with Shub-Niggurath harvests a mature crop.")
      public double harvestCrystalDropChance = 0.01;
      @Comment("Ticks between automatic scans that archive newly acquired eligible skills.")
      public int archiveScanIntervalTicks = 20;
      @Comment("Internal creation-recipe data version. Keep this value unchanged unless resetting defaults.")
      public int creationRecipeDataVersion = 0;
      @Comment("Temporary duration in Tensura skill-system seconds when copying a Unique Skill.")
      public int uniqueCopyDurationSeconds = 120;
      @Comment("Temporary duration in Tensura skill-system seconds when gifting a Unique Skill.")
      public int uniqueGiftDurationSeconds = 120;
      @Comment("Legacy compatibility setting. Copy-to-self still excludes Unique and Ultimate Skills.")
      public boolean forbidUltimateSkillCopy = true;
      @Comment("Whether a mastered Shub-Niggurath may gift archived Ultimate Skills to non-player subordinates.")
      public boolean enableMasteredUltimateSkillGifting = true;
      @Comment("Maximum Ultimate Skill instances allowed on a subordinate receiving an Ultimate Skill gift.")
      public int maxUltimateSkillsPerSubordinate = 1;
      @Comment("Maximum distance in blocks for Ability Gift.")
      public double giftRange = 24.0;
      @Comment("Warehouse synthesis action cooldown in Tensura skill-system seconds.")
      public int creationCooldownSeconds = 10;
      @Comment("Warehouse copy-to-self action cooldown in Tensura skill-system seconds.")
      public int copyCooldownSeconds = 300;
      @Comment("Permanent base EP percentage consumed after a successful self-copy.")
      public double copyPermanentEpCostPercent = 2.0;
      @Comment("Ability Gift cooldown in Tensura skill-system seconds.")
      public int giftCooldownSeconds = 300;
      @Comment("Current magicule percentage consumed after a successful Ability Gift.")
      public double giftMagiculeCostPercent = 25.0;
      @Comment("Permanent base EP percentage consumed after successful synthesis.")
      public double synthesisPermanentEpCostPercent = 5.0;
      @Comment("Whether synthesis consumes both material skills after a successful synthesis.")
      public boolean synthesisConsumesMaterials = true;
      @Comment("Lore-inspired Shub-Niggurath synthesis recipes formatted as input_a+input_b=output.")
      public List<String> creationRecipes = new ArrayList<>(ShubNiggurathConfig.RecipeDefaults.CURRENT);
   }
}
