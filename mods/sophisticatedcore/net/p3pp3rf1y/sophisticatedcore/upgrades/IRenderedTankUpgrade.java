package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public interface IRenderedTankUpgrade {
   void setTankRenderInfoUpdateCallback(Consumer<IRenderedTankUpgrade.TankRenderInfo> var1);

   void forceUpdateTankRenderInfo();

   class TankRenderInfo {
      private static final String FLUID_TAG = "fluid";
      private static final String FILL_RATIO_TAG = "fillRatio";
      @Nullable
      private FluidStack fluidStack;
      private float fillRatio;

      public TankRenderInfo() {
         this(null, 0.0F);
      }

      public TankRenderInfo(@Nullable FluidStack fluidStack, float fillRatio) {
         this.fluidStack = fluidStack;
         this.fillRatio = fillRatio;
      }

      public CompoundTag serialize() {
         CompoundTag ret = new CompoundTag();
         if (this.fluidStack != null) {
            ret.put("fluid", RegistryHelper.getRegistryAccess().map(registryAccess -> this.fluidStack.saveOptional(registryAccess)).orElse(new CompoundTag()));
            ret.putFloat("fillRatio", this.fillRatio);
         }

         return ret;
      }

      public static IRenderedTankUpgrade.TankRenderInfo deserialize(CompoundTag tag) {
         if (tag.contains("fluid")) {
            FluidStack fluidStack = RegistryHelper.getRegistryAccess()
               .map(registryAccess -> FluidStack.parseOptional(registryAccess, tag.getCompound("fluid")))
               .orElse(FluidStack.EMPTY);
            if (!fluidStack.isEmpty()) {
               return new IRenderedTankUpgrade.TankRenderInfo(fluidStack, tag.getFloat("fillRatio"));
            }
         }

         return new IRenderedTankUpgrade.TankRenderInfo();
      }

      public void setFluid(FluidStack fluidStack) {
         this.fluidStack = fluidStack.copy();
      }

      public Optional<FluidStack> getFluid() {
         return Optional.ofNullable(this.fluidStack);
      }

      public void setFillRatio(float fillRatio) {
         this.fillRatio = fillRatio;
      }

      public float getFillRatio() {
         return this.fillRatio;
      }
   }
}
