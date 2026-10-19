package it.hurts.sskirillss.relics.config.data;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import org.apache.commons.lang3.tuple.Pair;

public class StatConfigData {
   @Prop(comment = "Minimum base value of the stat. A random value within this range is assigned when the relic is first created")
   private double minInitialValue;
   @Prop(comment = "Maximum base value of the stat. A random value within this range is assigned when the relic is first created")
   private double maxInitialValue;
   @Prop(comment = "Minimum threshold value for the stat, representing hard limits that cannot be surpassed through ability level upgrades or other methods")
   private double minThresholdValue;
   @Prop(comment = "Maximum threshold value for the stat, representing hard limits that cannot be surpassed through ability level upgrades or other methods")
   private double maxThresholdValue;
   @Prop(
      comment = "Type of mathematical operation used to calculate the stat's value based on the ability level. Supported operations include:\nMULTIPLY_BASE: x + ((x * y) * z),\nMULTIPLY_TOTAL: x * (y + 1)^z,\nADD: x + (y * z).\n\n...where x - Base stat value, y - Value of [upgradeModifier], z - Current relic level\n"
   )
   private UpgradeOperation upgradeOperation;
   @Prop(comment = "Modifier applied to the base value of the stat, depending on the [upgradeOperation] parameter.")
   private double upgradeModifier;

   public StatData toData(IRelicItem relic, String ability, String stat) {
      StatData data = relic.constructDefaultRelicData().getAbilities().getAbilities().get(ability).getStats().get(stat);
      data.setInitialValue(Pair.of(this.minInitialValue, this.maxInitialValue));
      data.setThresholdValue(Pair.of(this.minThresholdValue, this.maxThresholdValue));
      data.setUpgradeModifier(Pair.of(this.upgradeOperation, this.upgradeModifier));
      return data;
   }

   public double getMinInitialValue() {
      return this.minInitialValue;
   }

   public double getMaxInitialValue() {
      return this.maxInitialValue;
   }

   public double getMinThresholdValue() {
      return this.minThresholdValue;
   }

   public double getMaxThresholdValue() {
      return this.maxThresholdValue;
   }

   public UpgradeOperation getUpgradeOperation() {
      return this.upgradeOperation;
   }

   public double getUpgradeModifier() {
      return this.upgradeModifier;
   }

   public void setMinInitialValue(double minInitialValue) {
      this.minInitialValue = minInitialValue;
   }

   public void setMaxInitialValue(double maxInitialValue) {
      this.maxInitialValue = maxInitialValue;
   }

   public void setMinThresholdValue(double minThresholdValue) {
      this.minThresholdValue = minThresholdValue;
   }

   public void setMaxThresholdValue(double maxThresholdValue) {
      this.maxThresholdValue = maxThresholdValue;
   }

   public void setUpgradeOperation(UpgradeOperation upgradeOperation) {
      this.upgradeOperation = upgradeOperation;
   }

   public void setUpgradeModifier(double upgradeModifier) {
      this.upgradeModifier = upgradeModifier;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof StatConfigData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (Double.compare(this.getMinInitialValue(), other.getMinInitialValue()) != 0) {
            return false;
         }

         if (Double.compare(this.getMaxInitialValue(), other.getMaxInitialValue()) != 0) {
            return false;
         }

         if (Double.compare(this.getMinThresholdValue(), other.getMinThresholdValue()) != 0) {
            return false;
         }

         if (Double.compare(this.getMaxThresholdValue(), other.getMaxThresholdValue()) != 0) {
            return false;
         }

         if (Double.compare(this.getUpgradeModifier(), other.getUpgradeModifier()) != 0) {
            return false;
         }

         Object this$upgradeOperation = this.getUpgradeOperation();
         Object other$upgradeOperation = other.getUpgradeOperation();
         return this$upgradeOperation == null ? other$upgradeOperation == null : this$upgradeOperation.equals(other$upgradeOperation);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StatConfigData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $minInitialValue = Double.doubleToLongBits(this.getMinInitialValue());
      result = result * 59 + (int)($minInitialValue >>> 32 ^ $minInitialValue);
      long $maxInitialValue = Double.doubleToLongBits(this.getMaxInitialValue());
      result = result * 59 + (int)($maxInitialValue >>> 32 ^ $maxInitialValue);
      long $minThresholdValue = Double.doubleToLongBits(this.getMinThresholdValue());
      result = result * 59 + (int)($minThresholdValue >>> 32 ^ $minThresholdValue);
      long $maxThresholdValue = Double.doubleToLongBits(this.getMaxThresholdValue());
      result = result * 59 + (int)($maxThresholdValue >>> 32 ^ $maxThresholdValue);
      long $upgradeModifier = Double.doubleToLongBits(this.getUpgradeModifier());
      result = result * 59 + (int)($upgradeModifier >>> 32 ^ $upgradeModifier);
      Object $upgradeOperation = this.getUpgradeOperation();
      return result * 59 + ($upgradeOperation == null ? 43 : $upgradeOperation.hashCode());
   }

   @Override
   public String toString() {
      return "StatConfigData(minInitialValue="
         + this.getMinInitialValue()
         + ", maxInitialValue="
         + this.getMaxInitialValue()
         + ", minThresholdValue="
         + this.getMinThresholdValue()
         + ", maxThresholdValue="
         + this.getMaxThresholdValue()
         + ", upgradeOperation="
         + this.getUpgradeOperation()
         + ", upgradeModifier="
         + this.getUpgradeModifier()
         + ")";
   }

   public StatConfigData() {
   }

   public StatConfigData(
      double minInitialValue,
      double maxInitialValue,
      double minThresholdValue,
      double maxThresholdValue,
      UpgradeOperation upgradeOperation,
      double upgradeModifier
   ) {
      this.minInitialValue = minInitialValue;
      this.maxInitialValue = maxInitialValue;
      this.minThresholdValue = minThresholdValue;
      this.maxThresholdValue = maxThresholdValue;
      this.upgradeOperation = upgradeOperation;
      this.upgradeModifier = upgradeModifier;
   }
}
