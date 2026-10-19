package net.p3pp3rf1y.sophisticatedcore.compat.chipped;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class BlockTransformationUpgradeContainer extends UpgradeContainerBase<BlockTransformationUpgradeWrapper, BlockTransformationUpgradeContainer> {
   private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
   private final BlockTransformationRecipeContainer recipeContainer;

   public BlockTransformationUpgradeContainer(
      Player player,
      int upgradeContainerId,
      BlockTransformationUpgradeWrapper upgradeWrapper,
      UpgradeContainerType<BlockTransformationUpgradeWrapper, BlockTransformationUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      ContainerLevelAccess worldPosCallable = player.level().isClientSide
         ? ContainerLevelAccess.NULL
         : ContainerLevelAccess.create(player.level(), player.blockPosition());
      this.recipeContainer = new BlockTransformationRecipeContainer(this, upgradeWrapper.getRecipeType(), this.slots::add, this, worldPosCallable);
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("shiftClickIntoStorage")) {
         this.setShiftClickIntoStorage(data.getBoolean("shiftClickIntoStorage"));
      } else {
         this.recipeContainer.handlePacket(data);
      }
   }

   public boolean shouldShiftClickIntoStorage() {
      return this.upgradeWrapper.shouldShiftClickIntoStorage();
   }

   public void setShiftClickIntoStorage(boolean shiftClickIntoStorage) {
      this.upgradeWrapper.setShiftClickIntoStorage(shiftClickIntoStorage);
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), "shiftClickIntoStorage", shiftClickIntoStorage));
   }

   public BlockTransformationRecipeContainer getRecipeContainer() {
      return this.recipeContainer;
   }

   @Override
   public boolean mergeIntoStorageFirst(Slot slot) {
      return this.recipeContainer.isNotResultSlot(slot) || this.shouldShiftClickIntoStorage();
   }

   @Override
   public boolean allowsPickupAll(Slot slot) {
      return this.recipeContainer.isNotResultSlot(slot);
   }
}
