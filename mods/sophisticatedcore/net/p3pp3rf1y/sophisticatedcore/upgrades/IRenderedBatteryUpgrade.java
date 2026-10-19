package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;

public interface IRenderedBatteryUpgrade {
   void setBatteryRenderInfoUpdateCallback(Consumer<IRenderedBatteryUpgrade.BatteryRenderInfo> var1);

   void forceUpdateBatteryRenderInfo();

   class BatteryRenderInfo {
      private static final String CHARGE_RATIO_TAG = "chargeRatio";
      private float chargeRatio;

      public BatteryRenderInfo(float chargeRatio) {
         this.chargeRatio = chargeRatio;
      }

      public CompoundTag serialize() {
         CompoundTag ret = new CompoundTag();
         ret.putFloat("chargeRatio", this.chargeRatio);
         return ret;
      }

      public static IRenderedBatteryUpgrade.BatteryRenderInfo deserialize(CompoundTag tag) {
         return new IRenderedBatteryUpgrade.BatteryRenderInfo(tag.getFloat("chargeRatio"));
      }

      public void setChargeRatio(float chargeRatio) {
         this.chargeRatio = chargeRatio;
      }

      public float getChargeRatio() {
         return this.chargeRatio;
      }
   }
}
