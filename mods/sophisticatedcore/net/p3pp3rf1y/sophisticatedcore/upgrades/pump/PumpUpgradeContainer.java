package net.p3pp3rf1y.sophisticatedcore.upgrades.pump;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public class PumpUpgradeContainer extends UpgradeContainerBase<PumpUpgradeWrapper, PumpUpgradeContainer> {
   private static final String DATA_IS_INPUT = "isInput";
   private static final String DATA_INTERACT_WITH_HAND = "interactWithHand";
   private static final String DATA_INTERACT_WITH_WORLD = "interactWithWorld";
   public static final String DATA_INTERACT_WITH_FLUID_HANDLERS = "interactWithFluidHandlers";
   private final FluidFilterContainer fluidFilterContainer;

   public PumpUpgradeContainer(
      Player player, int upgradeContainerId, PumpUpgradeWrapper upgradeWrapper, UpgradeContainerType<PumpUpgradeWrapper, PumpUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      this.fluidFilterContainer = new FluidFilterContainer(player, this, upgradeWrapper::getFluidFilterLogic);
   }

   public void setIsInput(boolean input) {
      this.upgradeWrapper.setIsInput(input);
      this.sendBooleanToServer("isInput", input);
   }

   public boolean isInput() {
      return this.upgradeWrapper.isInput();
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("isInput")) {
         this.setIsInput(data.getBoolean("isInput"));
      } else if (data.contains("interactWithHand")) {
         this.setInteractWithHand(data.getBoolean("interactWithHand"));
      } else if (data.contains("interactWithWorld")) {
         this.setInteractWithWorld(data.getBoolean("interactWithWorld"));
      } else if (data.contains("interactWithFluidHandlers")) {
         this.setInteractWithFluidHandlers(data.getBoolean("interactWithFluidHandlers"));
      }

      this.fluidFilterContainer.handlePacket(data);
   }

   public FluidFilterContainer getFluidFilterContainer() {
      return this.fluidFilterContainer;
   }

   public void setInteractWithHand(boolean interactWithHand) {
      this.upgradeWrapper.setInteractWithHand(interactWithHand);
      this.sendBooleanToServer("interactWithHand", interactWithHand);
   }

   public boolean shouldInteractWithHand() {
      return this.upgradeWrapper.shouldInteractWithHand();
   }

   public void setInteractWithWorld(boolean interactWithWorld) {
      this.upgradeWrapper.setInteractWithWorld(interactWithWorld);
      this.sendBooleanToServer("interactWithWorld", interactWithWorld);
   }

   public boolean shouldInteractWithWorld() {
      return this.upgradeWrapper.shouldInteractWithWorld();
   }

   public void setInteractWithFluidHandlers(boolean interact) {
      this.upgradeWrapper.setInteractWithFluidHandlers(interact);
      this.sendBooleanToServer("interactWithFluidHandlers", interact);
   }

   public boolean shouldInteractWithFluidHandlers() {
      return this.upgradeWrapper.shouldInteractWithFluidHandlers();
   }
}
