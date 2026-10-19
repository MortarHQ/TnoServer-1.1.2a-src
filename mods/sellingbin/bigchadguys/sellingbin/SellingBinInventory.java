package bigchadguys.sellingbin;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.serializable.INbtSerializable;
import bigchadguys.sellingbin.init.ModConfigs;
import bigchadguys.sellingbin.trade.Trade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SellingBinInventory implements WorldlyContainer, INbtSerializable<CompoundTag> {
   private final BlockEntity blockEntity;
   private NonNullList<ItemStack> inventory;
   private int[] availableSlots;

   public SellingBinInventory(BlockEntity blockEntity, int slots) {
      this.blockEntity = blockEntity;
      this.setCapacity(slots);
   }

   public void setCapacity(int size) {
      this.availableSlots = IntStream.range(0, size).toArray();
      NonNullList<ItemStack> oldInventory = this.inventory;
      this.inventory = NonNullList.withSize(size, ItemStack.EMPTY);
      if (oldInventory != null) {
         for (int i = 0; i < Math.min(this.inventory.size(), oldInventory.size()); i++) {
            this.inventory.set(i, (ItemStack)oldInventory.get(i));
         }
      }

      this.setChanged();
   }

   public int[] getSlotsForFace(Direction side) {
      return this.availableSlots;
   }

   public void startOpen(Player player) {
   }

   public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
      return true;
   }

   public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
      return true;
   }

   public int getContainerSize() {
      return this.inventory.size();
   }

   public boolean isEmpty() {
      return this.inventory.stream().allMatch(ItemStack::isEmpty);
   }

   public ItemStack getItem(int slot) {
      return (ItemStack)this.inventory.get(slot);
   }

   public ItemStack removeItem(int slot, int amount) {
      ItemStack stack = ContainerHelper.removeItem(this.inventory, slot, amount);
      if (!stack.isEmpty()) {
         this.setChanged();
      }

      return stack;
   }

   public ItemStack removeItemNoUpdate(int slot) {
      return ContainerHelper.takeItem(this.inventory, slot);
   }

   public void setItem(int slot, ItemStack stack) {
      this.inventory.set(slot, stack);
      if (stack.getCount() > this.getMaxStackSize()) {
         stack.setCount(this.getMaxStackSize());
      }

      this.setChanged();
   }

   public boolean stillValid(Player player) {
      return this.blockEntity == null || Container.stillValidBlockEntity(this.blockEntity, player);
   }

   public void clearContent() {
      this.inventory.clear();
   }

   public void setChanged() {
      if (this.blockEntity != null) {
         this.blockEntity.setChanged();
      }
   }

   public void executeTrades() {
      List<ItemStack> rewards = new ArrayList<>();

      for (Trade trade : ModConfigs.SELLING_BIN.getTrades()) {
         while (this.executeTrade(trade)) {
            trade.getOutput().generate().ifPresent(stack -> rewards.add(stack.copy()));
         }
      }

      rewards.removeIf(reward -> {
         int left = reward.getCount();

         for (int slot : this.availableSlots) {
            ItemStack stack = this.getItem(slot);
            if (left <= 0) {
               break;
            }

            if (stack.isEmpty() || ItemStack.isSameItemSameComponents(stack, reward)) {
               int difference = Math.min(reward.getMaxStackSize() - stack.getCount(), left);
               ItemStack copy = reward.copy();
               copy.setCount(stack.getCount() + difference);
               this.setItem(slot, copy);
               left -= difference;
            }
         }

         if (left <= 0) {
            return true;
         }

         reward.setCount(left);
         return false;
      });
   }

   public boolean executeTrade(Trade trade) {
      int matchesLeft = trade.getInput().getCount();
      Map<Integer, ItemStack> matches = new LinkedHashMap<>();

      for (int slot : this.availableSlots) {
         ItemStack stack = (ItemStack)this.inventory.get(slot);
         if (trade.getInput().getFilter().test(stack)) {
            int difference = Math.min(stack.getCount(), matchesLeft);
            matchesLeft -= difference;
            matches.put(slot, stack);
            if (matchesLeft <= 0) {
               break;
            }
         }
      }

      if (matchesLeft != 0) {
         return false;
      }

      matchesLeft = trade.getInput().getCount();

      for (Entry<Integer, ItemStack> entry : matches.entrySet()) {
         int slot = entry.getKey();
         ItemStack stack = entry.getValue();
         int difference = Math.min(stack.getCount(), matchesLeft);
         stack.shrink(difference);
         matchesLeft -= difference;
         this.setItem(slot, stack);
      }

      return true;
   }

   @Override
   public Optional<CompoundTag> writeNbt() {
      CompoundTag nbt = new CompoundTag();
      ListTag slots = new ListTag();

      for (int i = 0; i < this.inventory.size(); i++) {
         ItemStack stack = (ItemStack)this.inventory.get(i);
         if (!stack.isEmpty()) {
            int slotIndex = i;
            Adapters.ITEM_STACK.writeNbt(stack).ifPresent(tag -> {
               Adapters.INT.writeNbt(slotIndex).ifPresent(tag2 -> ((CompoundTag)tag).put("slot", tag2));
               slots.add(tag);
            });
         }
      }

      nbt.put("slots", slots);
      return Optional.of(nbt);
   }

   public void readNbt(CompoundTag nbt) {
      this.inventory = NonNullList.withSize(this.inventory.size(), ItemStack.EMPTY);
      ListTag inventory = nbt.getList("slots", 10);

      for (int i = 0; i < inventory.size(); i++) {
         CompoundTag entry = inventory.getCompound(i);
         ItemStack stack = Adapters.ITEM_STACK.readNbt(entry).orElse(ItemStack.EMPTY);
         int slotIndex = Adapters.INT.readNbt(entry.get("slot")).orElse(-1);
         if (slotIndex >= 0) {
            if (slotIndex >= this.inventory.size()) {
               this.setCapacity(slotIndex + 1);
            }

            this.inventory.set(slotIndex, stack);
         }
      }
   }
}
