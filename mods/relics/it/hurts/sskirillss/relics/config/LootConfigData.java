package it.hurts.sskirillss.relics.config;

import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;

public class LootConfigData implements OctoConfig {
   private double relicGenChance = 0.33;

   public double getRelicGenChance() {
      return this.relicGenChance;
   }

   public void setRelicGenChance(double relicGenChance) {
      this.relicGenChance = relicGenChance;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LootConfigData other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : Double.compare(this.getRelicGenChance(), other.getRelicGenChance()) == 0;
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LootConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $relicGenChance = Double.doubleToLongBits(this.getRelicGenChance());
      return result * 59 + (int)($relicGenChance >>> 32 ^ $relicGenChance);
   }

   @Override
   public String toString() {
      return "LootConfigData(relicGenChance=" + this.getRelicGenChance() + ")";
   }
}
