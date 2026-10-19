package lain.mods.cos.impl.inventory;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import lain.mods.cos.api.inventory.CAStacksBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class InventoryCosArmor extends CAStacksBase implements Container, MenuProvider {
   protected static final Component Name = Component.literal("InventoryCosArmor");
   protected static final int MINSIZE = 11;
   protected final Collection<Object> listeners = new CopyOnWriteArrayList<>();

   public InventoryCosArmor() {
      super(11);
   }

   public void clearContent() {
      for (int i = 0; i < this.getSlots(); i++) {
         this.setStackInSlot(i, ItemStack.EMPTY);
      }
   }

   public void stopOpen(Player player) {
   }

   public AbstractContainerMenu createMenu(int windowId, Inventory invPlayer, Player player) {
      return new ContainerCosArmor(invPlayer, this, player, windowId);
   }

   public ItemStack removeItem(int slot, int num) {
      return this.extractItem(slot, num, false);
   }

   public Component getDisplayName() {
      return Name;
   }

   public int getMaxStackSize() {
      return 64;
   }

   public int getContainerSize() {
      return this.getSlots();
   }

   public boolean isEmpty() {
      for (int i = 0; i < this.getSlots(); i++) {
         if (!this.getStackInSlot(i).isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getItem(int p_70301_1_) {
      return this.getStackInSlot(p_70301_1_);
   }

   public boolean canPlaceItem(int slot, ItemStack stack) {
      return true;
   }

   public boolean stillValid(Player player) {
      return true;
   }

   public void setChanged() {
   }

   protected void onContentsChanged(int slot) {
      this.listeners
         .stream()
         .filter(InventoryCosArmor.ContentsChangeListener.class::isInstance)
         .map(InventoryCosArmor.ContentsChangeListener.class::cast)
         .forEach(l -> l.accept(this, slot));
   }

   protected void onLoad() {
      for (int i = 0; i < this.stacks.size(); i++) {
         int slot = i;
         this.listeners
            .stream()
            .filter(InventoryCosArmor.ContentsChangeListener.class::isInstance)
            .map(InventoryCosArmor.ContentsChangeListener.class::cast)
            .forEach(l -> l.accept(this, slot));
      }

      for (String modid : this.hidden.keySet()) {
         for (String identifier : this.hidden.get(modid)) {
            this.listeners
               .stream()
               .filter(InventoryCosArmor.HiddenFlagsChangeListener.class::isInstance)
               .map(InventoryCosArmor.HiddenFlagsChangeListener.class::cast)
               .forEach(l -> l.accept(this, modid, identifier));
         }
      }
   }

   public void startOpen(Player player) {
   }

   public ItemStack removeItemNoUpdate(int slot) {
      return this.extractItem(slot, Integer.MAX_VALUE, false);
   }

   @Override
   public boolean setHidden(String modid, String identifier, boolean set) {
      boolean changed;
      if (changed = super.setHidden(modid, identifier, set)) {
         this.listeners
            .stream()
            .filter(InventoryCosArmor.HiddenFlagsChangeListener.class::isInstance)
            .map(InventoryCosArmor.HiddenFlagsChangeListener.class::cast)
            .forEach(l -> l.accept(this, modid, identifier));
      }

      return changed;
   }

   public void setItem(int slot, ItemStack stack) {
      this.setStackInSlot(slot, stack);
   }

   @Override
   public void setSize(int size) {
      if (size < 11) {
         size = 11;
      }

      super.setSize(size);
   }

   public boolean setUpdateListener(InventoryCosArmor.ContentsChangeListener listener) {
      return listener != null && !this.listeners.contains(listener) ? this.listeners.add(listener) : false;
   }

   public boolean setUpdateListener(InventoryCosArmor.HiddenFlagsChangeListener listener) {
      return listener != null && !this.listeners.contains(listener) ? this.listeners.add(listener) : false;
   }

   @FunctionalInterface
   public interface ContentsChangeListener {
      void accept(InventoryCosArmor var1, int var2);
   }

   @FunctionalInterface
   public interface HiddenFlagsChangeListener {
      void accept(InventoryCosArmor var1, String var2, String var3);
   }
}
