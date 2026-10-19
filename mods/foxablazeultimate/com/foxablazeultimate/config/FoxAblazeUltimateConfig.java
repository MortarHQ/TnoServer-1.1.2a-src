package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.resources.ResourceLocation;

public class FoxAblazeUltimateConfig extends ManasConfig {
   public FoxAblazeUltimateConfig.GeneralSettings General = new FoxAblazeUltimateConfig.GeneralSettings();
   public FoxAblazeUltimateConfig.SkillLockingSettings SkillLocking = new FoxAblazeUltimateConfig.SkillLockingSettings();

   public static void setInstance(FoxAblazeUltimateConfig cfg) {
      FoxAblazeUltimateConfig.Holder.instance = cfg;
   }

   public static FoxAblazeUltimateConfig get() {
      return FoxAblazeUltimateConfig.Holder.instance;
   }

   public static boolean playSkillAcquisitionSounds() {
      FoxAblazeUltimateConfig cfg = get();
      return cfg == null || cfg.General == null || cfg.General.playSkillAcquisitionSounds;
   }

   public static boolean disableCrystalSkillLocking(ResourceLocation skillId) {
      if (skillId != null && "foxablazeultimate".equals(skillId.getNamespace())) {
         FoxAblazeUltimateConfig cfg = get();
         if (cfg != null && cfg.SkillLocking != null) {
            return switch (skillId.getPath()) {
               case "raphael", "ciel" -> cfg.SkillLocking.disableRaphaelLocking;
               case "fortuna" -> cfg.SkillLocking.disableFortunaLocking;
               case "mammon" -> cfg.SkillLocking.disableMammonLocking;
               case "beelzebub", "beelzebuth" -> cfg.SkillLocking.disableBeelzebubLocking;
               case "uriel" -> cfg.SkillLocking.disableUrielLocking;
               case "veldora" -> cfg.SkillLocking.disableVeldoraLocking;
               case "satanael" -> cfg.SkillLocking.disableSatanaelLocking;
               case "shub_niggurath" -> cfg.SkillLocking.disableShubNiggurathLocking;
               case "yog_sothoth" -> cfg.SkillLocking.disableYogSothothLocking;
               case "velgrynd" -> cfg.SkillLocking.disableVelgryndLocking;
               case "azathoth" -> cfg.SkillLocking.disableAzathothLocking;
               default -> false;
            };
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public String getFileName() {
      return "general";
   }

   public Path getConfigPath() {
      return Paths.get("config", "fox_ultimate", this.getFileName() + ".toml");
   }

   public void load() {
      Path p = this.getConfigPath();

      try {
         if (Files.exists(p) && Files.size(p) == 0L) {
            Files.delete(p);
            FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] Deleted empty config {}, rebuilding defaults.", p);
         }
      } catch (IOException e) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Config pre-check failed ({}): {}", p, e.getMessage());
      }

      super.load();
   }

   public static class GeneralSettings extends ManasSubConfig {
      @Comment("Play the corresponding voice/sound when a Fox Ablaze Ultimate skill is obtained. Default true.")
      public boolean playSkillAcquisitionSounds = true;
   }

   private static final class Holder {
      static FoxAblazeUltimateConfig instance;
   }

   public static class SkillLockingSettings extends ManasSubConfig {
      @Comment("When true, Wisdom Crystals cannot make Raphael lockable. Default false.")
      public boolean disableRaphaelLocking = false;
      @Comment("When true, Wisdom Crystals cannot make Fortuna lockable. Default false.")
      public boolean disableFortunaLocking = false;
      @Comment("When true, no crystal permission can make Mammon lockable. Default false.")
      public boolean disableMammonLocking = false;
      @Comment("When true, Gluttony Crystals cannot make Beelzebub lockable. Default false.")
      public boolean disableBeelzebubLocking = false;
      @Comment("When true, Covenant Crystals cannot make Uriel lockable. Default false.")
      public boolean disableUrielLocking = false;
      @Comment("When true, Dragon Essence cannot make Veldora lockable. Default false.")
      public boolean disableVeldoraLocking = false;
      @Comment("When true, Wrath Crystals cannot make Satanael lockable. Default false.")
      public boolean disableSatanaelLocking = false;
      @Comment("When true, Harvest Crystals cannot make Shub-Niggurath lockable. Default false.")
      public boolean disableShubNiggurathLocking = false;
      @Comment("When true, Time Crystals cannot make Yog-Sothoth lockable. Default false.")
      public boolean disableYogSothothLocking = false;
      @Comment("When true, Flame Crystals cannot make Velgrynd lockable. Default false.")
      public boolean disableVelgryndLocking = false;
      @Comment("When true, Void Crystals cannot make Azathoth lockable. Default false.")
      public boolean disableAzathothLocking = false;
   }
}
