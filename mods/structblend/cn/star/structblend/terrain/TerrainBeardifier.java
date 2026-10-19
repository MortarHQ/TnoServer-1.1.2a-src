package cn.star.structblend.terrain;

import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierOrMarker;

public final class TerrainBeardifier implements BeardifierOrMarker {
   private final BeardifierOrMarker vanilla;
   private final SurfaceBlendPlan plan;

   public TerrainBeardifier(BeardifierOrMarker vanilla, SurfaceBlendPlan plan) {
      this.vanilla = vanilla;
      this.plan = plan;
   }

   public double compute(FunctionContext context) {
      double original = this.vanilla.compute(context);
      int x = context.blockX();
      int z = context.blockZ();
      if (!this.plan.contains(x, z)) {
         return original;
      }

      float natural = this.plan.natural(x, z);
      int y = context.blockY();
      double result = original;
      float desired = this.plan.target(x, z);
      if (!Float.isNaN(desired)) {
         double difference = desired - natural;
         double strength = clamp((Math.abs(difference) - 0.25) / 1.25);
         if (difference > 0.0 && y >= natural - 16.0F && y <= desired + 2.0F) {
            double fill = (this.plan.deepSupport(x, z) ? 4.0 : 6.0) * strength * clamp((desired + 1.0 - y) / 1.5);
            result = Math.max(result, fill);
         } else if (difference < 0.0 && y > desired && y <= natural + 4.0F) {
            double cut = -4.0 * strength * clamp((y - desired) / 1.5);
            result = Math.min(result, cut);
         }
      }

      float underpin = this.plan.underpinStrength(x, z);
      if (underpin > 0.0F) {
         int foundationTop = this.plan.underpinTop(x, z);
         double top = foundationTop;
         double bottom = foundationTop - 12.0 - 36.0 * underpin;
         if (this.plan.broadSupport(x, z)) {
            top = Math.min(foundationTop, Float.isNaN(desired) ? natural : Math.max(natural, desired));
            bottom = Math.max(foundationTop - 48.0, Math.min(bottom, natural - 2.0));
         }

         if (y <= top && y >= bottom) {
            double fill = 4.0 * underpin * clamp((y - bottom) / 6.0) * clamp((top + 1.5 - y) / 2.0);
            result = Math.max(result, fill);
         }
      }

      int contactTop = this.plan.contactTop(x, z);
      if (contactTop != Integer.MIN_VALUE && y <= contactTop && y > natural) {
         result = Math.max(result, 4.0);
      }

      float clearance = this.plan.clearanceStrength(x, z);
      if (clearance > 0.0F && y > this.plan.clearanceFloor(x, z) && y <= natural + 4.0F) {
         result = Math.min(result, -4.0 * clearance);
      }

      return result;
   }

   public double minValue() {
      return Double.NEGATIVE_INFINITY;
   }

   public double maxValue() {
      return Double.POSITIVE_INFINITY;
   }

   private static double clamp(double value) {
      return Math.max(0.0, Math.min(1.0, value));
   }
}
