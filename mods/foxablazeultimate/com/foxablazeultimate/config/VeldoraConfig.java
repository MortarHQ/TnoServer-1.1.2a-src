package com.foxablazeultimate.config;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VeldoraConfig extends ManasConfig {
   public VeldoraConfig.VeldoraSettings Veldora = new VeldoraConfig.VeldoraSettings();

   public static VeldoraConfig get() {
      return VeldoraConfig.Holder.instance;
   }

   public static void setInstance(VeldoraConfig cfg) {
      VeldoraConfig.Holder.instance = cfg;
   }

   public String getFileName() {
      return "veldora";
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

   private static final class Holder {
      static VeldoraConfig instance;
   }

   public static class VeldoraSettings extends ManasSubConfig {
      @Comment("Passive wind damage boost multiplier. 0.5 means +50% wind damage.")
      public double windDamageBoost = 0.5;
      @Comment("Passive lightning damage boost multiplier. 0.6 means +60% lightning damage.")
      public double thunderDamageBoost = 0.6;
      @Comment("Passive reduction for incoming wind damage. 0.35 means 35% less damage.")
      public double windDamageReduction = 0.35;
      @Comment("Passive reduction for incoming lightning damage. 0.35 means 35% less damage.")
      public double thunderDamageReduction = 0.35;
      @Comment("Chance for Storm Guard to knock back a nearby melee attacker. 0.2 means 20%.")
      public float counterAttackChance = 0.2F;
      @Comment("Knockback strength applied by Storm Guard.")
      public double counterKnockback = 1.5;
      @Comment("Magicule cost per second while holding Storm Breath.")
      public double magiculeCostBreath = 350.0;
      @Comment("Magicule cost per second while holding Storm Breath when mastered.")
      public double magiculeCostBreathMastered = 200.0;
      @Comment("Damage dealt by Storm Breath.")
      public double breathDamage = 18.0;
      @Comment("Damage dealt by Storm Breath when mastered.")
      public double breathDamageMastered = 28.0;
      @Comment("Storm Breath range in blocks.")
      public float breathRange = 15.0F;
      @Comment("Reserved breath duration value in ticks. 20 ticks = 1 second.")
      public float breathDuration = 100.0F;
      @Comment("Cooldown after releasing Storm Breath, in seconds.")
      public int breathCooldown = 3;
      @Comment("Magicule cost for Storm Lightning.")
      public double magiculeCostLightning = 2000.0;
      @Comment("Magicule cost for Storm Lightning when mastered.")
      public double magiculeCostLightningMastered = 1000.0;
      @Comment("Storm Lightning target/raycast range in blocks.")
      public double lightningRange = 30.0;
      @Comment("Primary damage dealt by Storm Lightning.")
      public double lightningDamage = 45.0;
      @Comment("Primary damage dealt by Storm Lightning when mastered.")
      public double lightningDamageMastered = 70.0;
      @Comment("Black lightning impact radius in blocks.")
      public float lightningRadius = 4.0F;
      @Comment("Additional black lightning visual bolts spawned around the impact.")
      public int lightningAdditionalVisual = 12;
      @Comment("Storm Lightning cooldown in seconds.")
      public int lightningCooldown = 6;
      @Comment("Storm Lightning cooldown in seconds when mastered.")
      public int lightningCooldownMastered = 4;
      @Comment("Magicule cost for Storm Domain.")
      public double magiculeCostDomain = 10000.0;
      @Comment("Magicule cost for Storm Domain when mastered.")
      public double magiculeCostDomainMastered = 6000.0;
      @Comment("Storm Domain radius in blocks.")
      public double domainRadius = 16.0;
      @Comment("Storm Domain radius in blocks when mastered.")
      public double domainRadiusMastered = 32.0;
      @Comment("Wind blade damage dealt by each Storm Domain pulse.")
      public double domainDamage = 12.0;
      @Comment("Storm Domain lightning target radius in blocks. Used by the opening black lightning and periodic vanilla lightning.")
      public double domainLightningTargetRadius = 32.0;
      @Comment("Tensura black lightning damage dealt to hostile enemies when Storm Domain is pressed.")
      public double domainOpeningBlackLightningDamage = 55.0;
      @Comment("Secondary magic damage dealt by the Storm Domain opening black lightning.")
      public double domainOpeningBlackLightningSecondaryDamage = 18.0;
      @Comment("Impact radius of each Storm Domain opening black lightning bolt.")
      public float domainOpeningBlackLightningImpactRadius = 2.0F;
      @Comment("Additional visual bolts for each Storm Domain opening black lightning strike.")
      public int domainOpeningBlackLightningAdditionalVisual = 4;
      @Comment("Storm Domain active duration in seconds.")
      public int domainDurationSeconds = 35;
      @Comment("Storm Domain active duration in seconds when mastered.")
      public int domainDurationMasteredSeconds = 45;
      @Comment("Ticks between Storm Domain pulses. 10 ticks = 0.5 seconds.")
      public int domainTickInterval = 10;
      @Comment("Ticks between Storm Domain wind blade volleys. 40 ticks = 2 seconds. Set to 0 to disable wind blades.")
      public int domainWindBladeIntervalTicks = 40;
      @Comment("Knockback strength applied by Storm Domain pulses.")
      public double domainKnockback = 0.75;
      @Comment("Movement Interference effect amplifier applied by Storm Domain pulses.")
      public int domainMovementInterferenceLevel = 1;
      @Comment("Storm Domain cooldown in seconds.")
      public int domainCooldown = 240;
      @Comment("Storm Domain cooldown in seconds when mastered.")
      public int domainCooldownMastered = 240;
      @Comment("Ticks between Storm Domain barrier shell visual refreshes.")
      public int domainBarrierVisualInterval = 5;
      @Comment("Particle point count used for the Storm Domain barrier shell visual.")
      public int domainBarrierShellPoints = 72;
      @Comment("If true, Storm Domain temporarily forces thunderstorm weather while active.")
      public boolean domainThunderstormWeather = true;
      @Comment("Chance per interval while Storm Domain is active to drop one vanilla lightning bolt inside the domain. 0.3 means 30%.")
      public float domainVanillaLightningChance = 0.3F;
      @Comment("Ticks between vanilla lightning checks while Storm Domain is active. 20 ticks = 1 second.")
      public int domainVanillaLightningIntervalTicks = 20;
      @Comment("Magicule cost for Storm Black Magic Slash.")
      public double magiculeCostSlash = 2300.0;
      @Comment("Magicule cost for Storm Black Magic Slash when mastered.")
      public double magiculeCostSlashMastered = 1200.0;
      @Comment("Primary damage dealt by Storm Black Magic Slash.")
      public double slashDamage = 65.0;
      @Comment("Primary damage dealt by Storm Black Magic Slash when mastered.")
      public double slashDamageMastered = 95.0;
      @Comment("Storm Black Magic Slash range in blocks.")
      public float slashRange = 20.0F;
      @Comment("Maximum hold time for Storm Black Magic Slash in ticks. 200 ticks = 10 seconds.")
      public int slashMaxHoldTicks = 200;
      @Comment("Ticks spent raising the sword overhead before Storm Black Magic Slash swings.")
      public int slashRaiseTicks = 12;
      @Comment("Ticks spent swinging the sword down. The slash effect activates only after this completes.")
      public int slashSwingTicks = 6;
      @Comment("Mist beam length for Storm Black Magic Slash in blocks. Higher values make the gluttony-mist-like visual longer.")
      public float slashMistRange = 36.0F;
      @Comment("Life of each Storm Black Magic Slash mist beam entity in ticks. It is refreshed while the key is held.")
      public int slashMistLifeTicks = 35;
      @Comment("Ticks between Storm Black Magic Slash mist beam damage applications. 10 ticks = 0.5 seconds.")
      public int slashMistDamageIntervalTicks = 10;
      @Comment("Secondary lightning elemental damage multiplier for Storm Black Magic Slash mist hits.")
      public float slashMistSecondaryDamageMultiplier = 0.25F;
      @Comment("Knockback strength applied by Storm Black Magic Slash mist hits.")
      public float slashMistKnockback = 1.2F;
      @Comment("Storm Black Magic Slash cooldown in seconds before mastery multiplier.")
      public int slashCooldown = 4;
      @Comment("Generic cooldown multiplier used by modes that do not have a dedicated mastered cooldown.")
      public double masteredCooldownMultiplier = 0.6;
   }
}
