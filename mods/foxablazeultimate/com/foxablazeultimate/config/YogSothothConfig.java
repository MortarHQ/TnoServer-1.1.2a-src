package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YogSothothConfig extends ManasConfig {
   public YogSothothConfig.YogSothothSettings YogSothoth = new YogSothothConfig.YogSothothSettings();

   public static YogSothothConfig get() {
      return YogSothothConfig.Holder.instance;
   }

   public static void setInstance(YogSothothConfig config) {
      YogSothothConfig.Holder.instance = config;
   }

   public static YogSothothConfig.YogSothothSettings settings() {
      YogSothothConfig config = get();
      return config != null && config.YogSothoth != null ? config.YogSothoth : new YogSothothConfig.YogSothothSettings();
   }

   public String getFileName() {
      return "yog_sothoth";
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
      private static YogSothothConfig instance;
   }

   public static class YogSothothSettings extends ManasSubConfig {
      @Comment("Permanent maximum-magicule capacity consumed when the four prerequisite skills evolve into Yog-Sothoth.")
      public double acquisitionMaximumMagiculeCost = 5000000.0;
      @Comment("Mastery granted when Yog-Sothoth is acquired.")
      public int acquisitionGrantMastery = 0;
      @Comment("Magicules consumed once per second while Time Stop is active. This is current magicule, not permanent capacity.")
      public double timeStopMagiculeCostPerSecond = 100000.0;
      @Comment("Ticks between Time Stop magicule drains. 20 ticks equals one second.")
      public int timeStopDrainIntervalTicks = 20;
      @Comment("Number of pages in Yog-Sothoth's imaginary space. Each page contains 27 slots.")
      public int spatialStoragePages = 6;
      @Comment("Maximum stack size for items stored in Yog-Sothoth's imaginary space.")
      public int spatialStorageMaxStackSize = 999;
   }
}
