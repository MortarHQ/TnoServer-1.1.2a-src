package it.hurts.sskirillss.relics.items.relics.base.data.leveling;

import it.hurts.sskirillss.relics.config.data.StatConfigData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

public class StatData {
   private final String id;
   private Pair<UpgradeOperation, Double> upgradeModifier;
   private Pair<Double, Double> initialValue;
   private Pair<Double, Double> thresholdValue;
   private Function<Double, ? extends Number> formatValue;

   public static StatData.StatDataBuilder builder(String id) {
      StatData.StatDataBuilder builder = new StatData.StatDataBuilder();
      builder.id(id);
      return builder;
   }

   public StatConfigData toConfigData() {
      return new StatConfigData(
         (Double)this.initialValue.getKey(),
         (Double)this.initialValue.getValue(),
         (Double)this.thresholdValue.getKey(),
         (Double)this.thresholdValue.getValue(),
         (UpgradeOperation)this.upgradeModifier.getKey(),
         (Double)this.upgradeModifier.getValue()
      );
   }

   private static Function<Double, ? extends Number> $default$formatValue() {
      return Double::doubleValue;
   }

   StatData(
      String id,
      Pair<UpgradeOperation, Double> upgradeModifier,
      Pair<Double, Double> initialValue,
      Pair<Double, Double> thresholdValue,
      Function<Double, ? extends Number> formatValue
   ) {
      this.id = id;
      this.upgradeModifier = upgradeModifier;
      this.initialValue = initialValue;
      this.thresholdValue = thresholdValue;
      this.formatValue = formatValue;
   }

   public String getId() {
      return this.id;
   }

   public Pair<UpgradeOperation, Double> getUpgradeModifier() {
      return this.upgradeModifier;
   }

   public Pair<Double, Double> getInitialValue() {
      return this.initialValue;
   }

   public Pair<Double, Double> getThresholdValue() {
      return this.thresholdValue;
   }

   public Function<Double, ? extends Number> getFormatValue() {
      return this.formatValue;
   }

   public void setUpgradeModifier(Pair<UpgradeOperation, Double> upgradeModifier) {
      this.upgradeModifier = upgradeModifier;
   }

   public void setInitialValue(Pair<Double, Double> initialValue) {
      this.initialValue = initialValue;
   }

   public void setThresholdValue(Pair<Double, Double> thresholdValue) {
      this.thresholdValue = thresholdValue;
   }

   public void setFormatValue(Function<Double, ? extends Number> formatValue) {
      this.formatValue = formatValue;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof StatData other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$id = this.getId();
         Object other$id = other.getId();
         if (this$id == null ? other$id == null : this$id.equals(other$id)) {
            Object this$upgradeModifier = this.getUpgradeModifier();
            Object other$upgradeModifier = other.getUpgradeModifier();
            if (this$upgradeModifier == null ? other$upgradeModifier == null : this$upgradeModifier.equals(other$upgradeModifier)) {
               Object this$initialValue = this.getInitialValue();
               Object other$initialValue = other.getInitialValue();
               if (this$initialValue == null ? other$initialValue == null : this$initialValue.equals(other$initialValue)) {
                  Object this$thresholdValue = this.getThresholdValue();
                  Object other$thresholdValue = other.getThresholdValue();
                  if (this$thresholdValue == null ? other$thresholdValue == null : this$thresholdValue.equals(other$thresholdValue)) {
                     Object this$formatValue = this.getFormatValue();
                     Object other$formatValue = other.getFormatValue();
                     return this$formatValue == null ? other$formatValue == null : this$formatValue.equals(other$formatValue);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StatData;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $id = this.getId();
      result = result * 59 + ($id == null ? 43 : $id.hashCode());
      Object $upgradeModifier = this.getUpgradeModifier();
      result = result * 59 + ($upgradeModifier == null ? 43 : $upgradeModifier.hashCode());
      Object $initialValue = this.getInitialValue();
      result = result * 59 + ($initialValue == null ? 43 : $initialValue.hashCode());
      Object $thresholdValue = this.getThresholdValue();
      result = result * 59 + ($thresholdValue == null ? 43 : $thresholdValue.hashCode());
      Object $formatValue = this.getFormatValue();
      return result * 59 + ($formatValue == null ? 43 : $formatValue.hashCode());
   }

   @Override
   public String toString() {
      return "StatData(id="
         + this.getId()
         + ", upgradeModifier="
         + this.getUpgradeModifier()
         + ", initialValue="
         + this.getInitialValue()
         + ", thresholdValue="
         + this.getThresholdValue()
         + ", formatValue="
         + this.getFormatValue()
         + ")";
   }

   public static class StatDataBuilder {
      private String id;
      private boolean formatValue$set;
      private Function<Double, ? extends Number> formatValue$value;
      private Pair<UpgradeOperation, Double> upgradeModifier = Pair.of(UpgradeOperation.ADD, 0.0);
      private Pair<Double, Double> initialValue = Pair.of(0.0, 0.0);
      private Pair<Double, Double> thresholdValue = Pair.of(Double.MIN_VALUE, Double.MAX_VALUE);

      private StatData.StatDataBuilder id(String id) {
         this.id = id;
         return this;
      }

      public StatData.StatDataBuilder initialValue(double min, double max) {
         this.initialValue = Pair.of(min, max);
         return this;
      }

      public StatData.StatDataBuilder thresholdValue(double min, double max) {
         this.thresholdValue = Pair.of(min, max);
         return this;
      }

      public StatData.StatDataBuilder upgradeModifier(UpgradeOperation operation, double step) {
         this.upgradeModifier = Pair.of(operation, step);
         return this;
      }

      StatDataBuilder() {
      }

      public StatData.StatDataBuilder formatValue(Function<Double, ? extends Number> formatValue) {
         this.formatValue$value = formatValue;
         this.formatValue$set = true;
         return this;
      }

      public StatData build() {
         Function<Double, ? extends Number> formatValue$value = this.formatValue$value;
         if (!this.formatValue$set) {
            formatValue$value = StatData.$default$formatValue();
         }

         return new StatData(this.id, this.upgradeModifier, this.initialValue, this.thresholdValue, formatValue$value);
      }

      @Override
      public String toString() {
         return "StatData.StatDataBuilder(id="
            + this.id
            + ", upgradeModifier="
            + this.upgradeModifier
            + ", initialValue="
            + this.initialValue
            + ", thresholdValue="
            + this.thresholdValue
            + ", formatValue$value="
            + this.formatValue$value
            + ")";
      }
   }
}
