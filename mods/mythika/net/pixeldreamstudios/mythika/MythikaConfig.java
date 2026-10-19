package net.pixeldreamstudios.mythika;

import java.util.ArrayList;
import java.util.List;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

@Config(name = "mythika")
public class MythikaConfig implements ConfigData {
   public static final int CURRENT_VERSION = 2;
   @Excluded
   public int configVersion = 0;
   @Category("cyclops")
   @Tooltip
   public double cyclopsHealth = 160.0;
   @Category("cyclops")
   @Tooltip
   public double cyclopsAttackDamage = 26.0;
   @Category("cyclops")
   @Tooltip
   public boolean cyclopsBreaksBlocks = true;
   @Category("cyclops")
   @Tooltip
   public double cyclopsSlamRadius = 4.0;
   @Category("cyclops")
   @Tooltip
   @BoundedDiscrete(min = 0L, max = 100L)
   public int cyclopsSpawnWeight = 8;
   @Category("minotaur")
   @Tooltip
   public double minotaurHealth = 110.0;
   @Category("minotaur")
   @Tooltip
   public double minotaurAttackDamage = 15.0;
   @Category("minotaur")
   @Tooltip
   public double minotaurChargeDamage = 22.0;
   @Category("minotaur")
   @Tooltip
   public boolean minotaurChargeBreaksBlocks = true;
   @Category("minotaur")
   @Tooltip
   @BoundedDiscrete(min = 0L, max = 100L)
   public int minotaurSpawnWeight = 6;
   @Category("satyr")
   @Tooltip
   public double satyrHealth = 24.0;
   @Category("satyr")
   @Tooltip
   public double satyrAttackDamage = 5.0;
   @Category("satyr")
   @Tooltip
   @BoundedDiscrete(min = 0L, max = 100L)
   public int satyrSpawnWeight = 12;
   @Category("satyr")
   @Tooltip
   public boolean satyrPlaysMusic = true;
   @Category("satyr")
   @Tooltip
   public boolean satyrLightsCampfires = true;

   public List<String> migrate() {
      if (this.configVersion >= 2) {
         return List.of();
      }

      List<String> moved = new ArrayList<>();
      if (this.configVersion < 2) {
         this.cyclopsHealth = retune("cyclopsHealth", this.cyclopsHealth, 120.0, 160.0, moved);
         this.cyclopsAttackDamage = retune("cyclopsAttackDamage", this.cyclopsAttackDamage, 16.0, 26.0, moved);
         this.cyclopsSlamRadius = retune("cyclopsSlamRadius", this.cyclopsSlamRadius, 3.0, 4.0, moved);
         this.minotaurHealth = retune("minotaurHealth", this.minotaurHealth, 80.0, 110.0, moved);
         this.minotaurAttackDamage = retune("minotaurAttackDamage", this.minotaurAttackDamage, 11.0, 15.0, moved);
         this.minotaurChargeDamage = retune("minotaurChargeDamage", this.minotaurChargeDamage, 14.0, 22.0, moved);
      }

      this.configVersion = 2;
      return moved;
   }

   private static double retune(String name, double current, double was, double now, List<String> moved) {
      if (current != was) {
         return current;
      }

      moved.add(name + " " + current + " -> " + now);
      return now;
   }
}
