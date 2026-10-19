package net.p3pp3rf1y.sophisticatedcore.upgrades.blockconverter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public abstract class BlockConverterUpgradeContainer<R extends SingleItemRecipe, W extends BlockConverterUpgradeWrapper<?, ?>, C extends BlockConverterUpgradeContainer<R, W, C, RC>, RC extends BlockConverterRecipeContainer<R, W, RC, C>>
   extends UpgradeContainerBase<W, C> {
   private static final String DATA_SHIFT_CLICK_INTO_STORAGE = "shiftClickIntoStorage";
   private final RC recipeContainer;

   public BlockConverterUpgradeContainer(Player player, int upgradeContainerId, W upgradeWrapper, UpgradeContainerType<W, C> type) {
      super(player, upgradeContainerId, upgradeWrapper, type);
      ContainerLevelAccess worldPosCallable = player.level().isClientSide
         ? ContainerLevelAccess.NULL
         : ContainerLevelAccess.create(player.level(), player.blockPosition());
      this.recipeContainer = this.createRecipeContainer(worldPosCallable);
   }

   public RC getRecipeContainer() {
      return this.recipeContainer;
   }

   protected abstract RC createRecipeContainer(ContainerLevelAccess var1);

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

   @Override
   public boolean mergeIntoStorageFirst(Slot slot) {
      return this.recipeContainer.isNotResultSlot(slot) || this.shouldShiftClickIntoStorage();
   }

   @Override
   public boolean allowsPickupAll(Slot slot) {
      return this.recipeContainer.isNotResultSlot(slot);
   }
}
