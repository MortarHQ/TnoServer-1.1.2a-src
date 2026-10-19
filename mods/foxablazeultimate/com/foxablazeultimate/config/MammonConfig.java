package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MammonConfig extends ManasConfig {
   public MammonConfig.MammonSettings Mammon = new MammonConfig.MammonSettings();

   public static MammonConfig get() {
      return MammonConfig.Holder.instance;
   }

   public static void setInstance(MammonConfig config) {
      MammonConfig.Holder.instance = config;
   }

   public static MammonConfig.MammonSettings settings() {
      MammonConfig config = get();
      return config == null ? new MammonConfig.MammonSettings() : config.Mammon;
   }

   public String getFileName() {
      return "mammon";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      Path path = this.getConfigPath();

      try {
         if (Files.exists(path) && Files.size(path) == 0L) {
            Files.delete(path);
            FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] Deleted empty config {}, rebuilding defaults.", path);
         }
      } catch (IOException exception) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Config pre-check failed ({}): {}", path, exception.getMessage());
      }

      super.load();
   }

   private static final class Holder {
      private static MammonConfig instance;
   }

   public static class MammonSettings extends ManasSubConfig {
      @Comment("Permanent maximum-magicule capacity consumed when mastered Greed awakens into Mammon.")
      public double acquisitionMaximumMagiculeCost = 1000000.0;
      @Comment("Ticks between automatic Mammon acquisition checks.")
      public int acquisitionCheckIntervalTicks = 20;
      @Comment("Mastery granted when Mammon is acquired.")
      public int acquisitionGrantMastery = 0;
      @Comment("Maximum targeting range in blocks for Life Steal and Skill Steal.")
      public double targetingRange = 20.0;
      @Comment("Base charge time in seconds for Mammon's inherited Heart Control mode. Proximity and desire modifiers scale with this value.")
      public int heartControlBaseChargeSeconds = 10;
      @Comment("Base charge time in seconds for Mammon's Death Wish mode.")
      public int deathWishBaseChargeSeconds = 5;
      @Comment("Fraction of the target's maximum EP permanently transferred by Life Steal. 0.01 means 1 percent.")
      public double lifeStealEpFraction = 0.01;
      @Comment("Fraction of the target's maximum EP permanently transferred by Life Steal after Mammon is mastered. 0.05 means 5 percent.")
      public double lifeStealEpFractionMastered = 0.05;
      @Comment("Magicules consumed by every Life Steal attempt.")
      public double lifeStealMagiculeCost = 3000.0;
      @Comment("Life Steal success cooldown in Tensura skill-system seconds.")
      public int lifeStealSuccessCooldownSeconds = 10;
      @Comment("Life Steal failure cooldown in Tensura skill-system seconds.")
      public int lifeStealFailureCooldownSeconds = 3;
      @Comment("Magicules consumed by every Skill Steal attempt.")
      public double skillStealMagiculeCost = 10000.0;
      @Comment("Skill Steal success cooldown in Tensura skill-system seconds.")
      public int skillStealSuccessCooldownSeconds = 30;
      @Comment("Skill Steal failure cooldown in Tensura skill-system seconds.")
      public int skillStealFailureCooldownSeconds = 3;
      @Comment("Whether Skill Steal may target players.")
      public boolean skillStealAllowsPlayers = true;
      @Comment("When true, one living target can only suffer one successful Mammon Skill Steal in its lifetime.")
      public boolean oneSuccessfulSkillStealPerTarget = true;
      @Comment("Whether Skill Steal respects Tensura's no-skill-plunder entity tag.")
      public boolean respectNoSkillPlunderEntityTag = true;
      @Comment("Whether Skill Steal is blocked by Anti-Skill.")
      public boolean respectAntiSkill = true;
   }
}
