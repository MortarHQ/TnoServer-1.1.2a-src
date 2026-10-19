package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;

public class FluidFilterLogic {
   private final List<FluidStack> fluidFilters;
   private final ItemStack upgrade;
   private final Consumer<ItemStack> saveHandler;
   private boolean noFilter = true;

   public FluidFilterLogic(int filterSlots, ItemStack upgrade, Consumer<ItemStack> saveHandler) {
      this.fluidFilters = NonNullList.withSize(filterSlots, FluidStack.EMPTY);
      this.upgrade = upgrade;
      this.saveHandler = saveHandler;
      this.deserializeFluidFilters();
      this.updateNoFilter();
   }

   private void deserializeFluidFilters() {
      List<SimpleFluidContent> deserializedFilters = (List<SimpleFluidContent>)this.upgrade
         .getOrDefault(ModCoreDataComponents.FLUID_FILTERS, Collections.emptyList());

      for (int i = 0; i < deserializedFilters.size() && i < this.fluidFilters.size(); i++) {
         this.fluidFilters.set(i, deserializedFilters.get(i).copy());
      }
   }

   private void updateNoFilter() {
      this.noFilter = true;

      for (FluidStack fluidFilter : this.fluidFilters) {
         if (!fluidFilter.isEmpty()) {
            this.noFilter = false;
            return;
         }
      }
   }

   public boolean fluidMatches(FluidStack fluid) {
      return this.noFilter || this.matchesFluidFilter(fluid);
   }

   private boolean matchesFluidFilter(FluidStack fluid) {
      for (FluidStack fluidFilter : this.fluidFilters) {
         if (FluidStack.isSameFluidSameComponents(fluidFilter, fluid)) {
            return true;
         }
      }

      return false;
   }

   private void save() {
      this.saveHandler.accept(this.upgrade);
   }

   public void setFluid(int index, FluidStack fluid) {
      this.fluidFilters.set(index, fluid.copy());
      this.serializeFluidFilters();
      this.updateNoFilter();
      this.save();
   }

   public FluidStack getFluid(int index) {
      return this.fluidFilters.get(index);
   }

   public int getNumberOfFluidFilters() {
      return this.fluidFilters.size();
   }

   private void serializeFluidFilters() {
      this.upgrade.set(ModCoreDataComponents.FLUID_FILTERS, this.fluidFilters.stream().map(SimpleFluidContent::copyOf).toList());
   }
}
