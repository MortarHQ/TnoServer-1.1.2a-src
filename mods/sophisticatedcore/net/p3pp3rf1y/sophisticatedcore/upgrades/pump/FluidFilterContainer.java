package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.p3pp3rf1y.sophisticatedcore.common.gui.IServerUpdater;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;

public class FluidFilterContainer {
   private final Player player;
   private final IServerUpdater serverUpdater;
   private final Supplier<FluidFilterLogic> fluidFilterLogic;
   private static final String DATA_FLUID = "setFluid";

   public FluidFilterContainer(Player player, IServerUpdater serverUpdater, Supplier<FluidFilterLogic> fluidFilterLogic) {
      this.player = player;
      this.serverUpdater = serverUpdater;
      this.fluidFilterLogic = fluidFilterLogic;
   }

   public FluidStack getFluid(int index) {
      return this.fluidFilterLogic.get().getFluid(index);
   }

   public void setFluid(int index, FluidStack fluid) {
      this.fluidFilterLogic.get().setFluid(index, fluid);
      this.serverUpdater.sendDataToServer(() -> this.serializeSetFluidData(index, fluid));
   }

   private CompoundTag serializeSetFluidData(int index, FluidStack fluid) {
      CompoundTag ret = new CompoundTag();
      CompoundTag fluidNbt = new CompoundTag();
      fluidNbt.putInt("index", index);
      fluidNbt.put("fluid", fluid.saveOptional(this.player.level().registryAccess()));
      ret.put("setFluid", fluidNbt);
      return ret;
   }

   public boolean handlePacket(CompoundTag data) {
      if (data.contains("setFluid")) {
         CompoundTag fluidData = data.getCompound("setFluid");
         FluidStack fluid = FluidStack.parseOptional(this.player.level().registryAccess(), fluidData.getCompound("fluid"));
         this.setFluid(fluidData.getInt("index"), fluid);
         return true;
      } else {
         return false;
      }
   }

   public int getNumberOfFluidFilters() {
      return this.fluidFilterLogic.get().getNumberOfFluidFilters();
   }

   public void slotClick(int index) {
      ItemStack carried = this.player.containerMenu.getCarried();
      if (carried.isEmpty()) {
         this.setFluid(index, FluidStack.EMPTY);
      } else {
         CapabilityHelper.runOnCapability(carried, FluidHandler.ITEM, null, itemFluidHandler -> {
            FluidStack containedFluid = itemFluidHandler.drain(1000, FluidAction.SIMULATE);
            if (!containedFluid.isEmpty()) {
               this.setFluid(index, containedFluid);
            }
         });
      }
   }
}
