package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerClientDataPayload;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public abstract class UpgradeContainerBase<W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>> implements IServerUpdater {
   protected final ArrayList<Slot> slots = new ArrayList<>();
   private final int upgradeContainerId;
   protected W upgradeWrapper;
   protected final Player player;
   private final UpgradeContainerType<W, C> type;
   private boolean isOpen = false;

   protected UpgradeContainerBase(Player player, int upgradeContainerId, W upgradeWrapper, UpgradeContainerType<W, C> type) {
      this.upgradeContainerId = upgradeContainerId;
      this.upgradeWrapper = upgradeWrapper;
      this.player = player;
      this.type = type;
   }

   public List<Slot> getSlots() {
      return this.slots;
   }

   public UpgradeContainerType<W, C> getType() {
      return this.type;
   }

   public void setIsOpen(boolean isOpen) {
      this.isOpen = isOpen;
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   @Override
   public void sendBooleanToServer(String key, boolean value) {
      if (this.player.level().isClientSide) {
         this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), key, value));
      }
   }

   @Override
   public void sendDataToServer(Supplier<CompoundTag> supplyData) {
      if (this.player.level().isClientSide) {
         CompoundTag data = supplyData.get();
         data.putInt("containerId", this.upgradeContainerId);
         PacketDistributor.sendToServer(new SyncContainerClientDataPayload(data), new CustomPacketPayload[0]);
      }
   }

   public void onInit() {
   }

   public abstract void handlePacket(CompoundTag var1);

   public ItemStack getUpgradeStack() {
      return this.upgradeWrapper.getUpgradeStack();
   }

   public W getUpgradeWrapper() {
      return this.upgradeWrapper;
   }

   public void setUpgradeWrapper(IUpgradeWrapper updatedUpgradeWrapper) {
      this.upgradeWrapper = (W)updatedUpgradeWrapper;
   }

   public boolean containsSlot(Slot slot) {
      for (Slot containerSlot : this.slots) {
         if (containerSlot == slot) {
            return true;
         }
      }

      return false;
   }

   public ItemStack getSlotStackToTransfer(Slot slot) {
      return slot.getItem();
   }

   public void onTakeFromSlot(Slot slot, Player player, ItemStack slotStack) {
      slot.onTake(player, slotStack);
   }

   public boolean mergeIntoStorageFirst(Slot slot) {
      return true;
   }

   public boolean allowsPickupAll(Slot slot) {
      return true;
   }

   public int getUpgradeContainerId() {
      return this.upgradeContainerId;
   }
}
