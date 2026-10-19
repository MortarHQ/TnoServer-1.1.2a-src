package net.p3pp3rf1y.sophisticatedcore.upgrades.infinity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.inventory.IInventoryPartHandler;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.util.SlotRange;
import org.apache.commons.lang3.function.TriFunction;

public abstract class InfinityInventoryPart implements IInventoryPartHandler {
   private final InventoryHandler parent;
   private final SlotRange slotRange;
   private final int permissionLevel;
   private final Map<Integer, ItemStack> cachedStacks = new HashMap<>();

   protected InfinityInventoryPart(InventoryHandler parent, SlotRange slotRange, int permissionLevel) {
      this.parent = parent;
      this.slotRange = slotRange;
      this.permissionLevel = permissionLevel;
      parent.addListener(this::onParentSlotChanged);
   }

   private void onParentSlotChanged(int slot) {
      if (this.slotRange.isInRange(slot)) {
         this.cachedStacks.remove(slot);
      }
   }

   @Override
   public boolean isInfinite(int slot) {
      return !this.parent.getSlotStack(slot).isEmpty();
   }

   @Override
   public int getSlotLimit(int slot) {
      return Integer.MAX_VALUE;
   }

   @Override
   public boolean isItemValid(int slot, ItemStack stack, @Nullable Player player, BiPredicate<Integer, ItemStack> isItemValidSuper) {
      return player != null && player.hasPermissions(this.permissionLevel) && this.parent.getSlotStack(slot).isEmpty() && isItemValidSuper.test(slot, stack);
   }

   @Override
   public boolean isSlotAccessible(int slot) {
      return true;
   }

   @Override
   public int getStackLimit(int slot, ItemStack stack) {
      return Integer.MAX_VALUE;
   }

   @Override
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      return this.parent.getSlotStack(slot).copyWithCount(amount);
   }

   @Override
   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate, TriFunction<Integer, ItemStack, Boolean, ItemStack> insertSuper) {
      if (!this.parent.getSlotStack(slot).isEmpty()) {
         return stack;
      }

      this.cachedStacks.remove(slot);
      return (ItemStack)insertSuper.apply(slot, stack, simulate);
   }

   @Override
   public void setStackInSlot(int slot, ItemStack stack, BiConsumer<Integer, ItemStack> setStackInSlotSuper) {
      if (this.parent.getSlotStack(slot).isEmpty()) {
         this.parent.setSlotStack(slot, stack);
      }
   }

   @Override
   public ItemStack getStackInSlot(int slot, IntFunction<ItemStack> getStackInSlotSuper) {
      if (this.cachedStacks.containsKey(slot) && this.cachedStacks.get(slot).isEmpty() != this.parent.getSlotStack(slot).isEmpty()) {
         this.cachedStacks.remove(slot);
      }

      return this.cachedStacks.computeIfAbsent(slot, s -> this.parent.getSlotStack(s).copyWithCount(Integer.MAX_VALUE));
   }

   @Override
   public int getSlots() {
      return this.slotRange.numberOfSlots();
   }

   public static class Admin extends InfinityInventoryPart {
      public static final String NAME = "infinity";

      protected Admin(InventoryHandler parent, SlotRange slotRange) {
         super(parent, slotRange, 2);
      }

      @Override
      public String getName() {
         return "infinity";
      }
   }

   public static class Survival extends InfinityInventoryPart {
      public static final String NAME = "survival_infinity";

      protected Survival(InventoryHandler parent, SlotRange slotRange) {
         super(parent, slotRange, 0);
      }

      @Override
      public String getName() {
         return "survival_infinity";
      }
   }
}
