package team.lodestar.lodestone.systems.blockentity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

public class LodestoneBlockEntityInventory extends ItemStackHandler {
   public LodestoneBlockEntity blockEntity;
   public final int slotCount;
   public final int allowedItemSize;
   public Predicate<ItemStack> inputPredicate = s -> true;
   public Runnable contentsChangeBehavior;
   public boolean autoSync;
   public ArrayList<ItemStack> nonEmptyItemStacks = new ArrayList<>();
   private int emptySlots;
   private int firstEmptyItemIndex;

   public LodestoneBlockEntityInventory(LodestoneBlockEntity blockEntity, int slotCount, int allowedItemSize) {
      super(slotCount);
      this.blockEntity = blockEntity;
      this.slotCount = slotCount;
      this.allowedItemSize = allowedItemSize;
   }

   public LodestoneBlockEntityInventory setInputPredicate(Predicate<ItemStack> inputPredicate) {
      this.inputPredicate = inputPredicate;
      return this;
   }

   public LodestoneBlockEntityInventory onContentsChanged(Runnable contentsChangeBehavior) {
      this.contentsChangeBehavior = contentsChangeBehavior;
      return this;
   }

   public LodestoneBlockEntityInventory triggerBlockEntityUpdate() {
      this.autoSync = true;
      return this;
   }

   public NonNullList<ItemStack> getStacks() {
      return this.stacks;
   }

   public ArrayList<ItemStack> getNonEmptyStacks() {
      return this.nonEmptyItemStacks;
   }

   public int getEmptySlotCount() {
      return this.emptySlots;
   }

   public int getFilledSlotCount() {
      return this.slotCount - this.emptySlots;
   }

   public boolean hasEmptySlots() {
      return this.getFilledSlotCount() != this.slotCount;
   }

   public int getFirstEmptyItemIndex() {
      return this.firstEmptyItemIndex;
   }

   public boolean isEmpty() {
      return this.nonEmptyItemStacks.isEmpty();
   }

   public void onContentsChanged(int slot) {
      this.updateInventoryCaches();
      if (this.autoSync) {
         BlockStateHelper.updateState(this.blockEntity.getLevel(), this.blockEntity.getBlockPos());
      }

      if (this.contentsChangeBehavior != null) {
         this.contentsChangeBehavior.run();
      }
   }

   public int getSlots() {
      return this.slotCount;
   }

   public int getSlotLimit(int slot) {
      return this.allowedItemSize;
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return !this.inputPredicate.test(stack) ? false : super.isItemValid(slot, stack);
   }

   public void updateInventoryCaches() {
      NonNullList<ItemStack> stacks = this.getStacks();
      this.nonEmptyItemStacks = stacks.stream().filter(s -> !s.isEmpty()).collect(Collectors.toCollection(ArrayList::new));
      this.emptySlots = (int)stacks.stream().filter(ItemStack::isEmpty).count();

      for (int i = 0; i < stacks.size(); i++) {
         ItemStack stack = (ItemStack)stacks.get(i);
         if (stack.isEmpty()) {
            this.firstEmptyItemIndex = i;
            return;
         }
      }

      this.firstEmptyItemIndex = -1;
   }

   public void load(Provider provider, CompoundTag compound) {
      this.load(provider, compound, "inventory");
   }

   public void load(Provider provider, CompoundTag compound, String name) {
      this.deserializeNBT(provider, compound.getCompound(name));
      if (this.stacks.size() != this.slotCount) {
         int missing = this.slotCount - this.stacks.size();

         for (int i = 0; i < missing; i++) {
            this.stacks.add(ItemStack.EMPTY);
         }
      }

      this.updateInventoryCaches();
   }

   public void save(Provider provider, CompoundTag compound) {
      this.save(provider, compound, "inventory");
   }

   public void save(Provider provider, CompoundTag compound, String name) {
      compound.put(name, this.serializeNBT(provider));
   }

   public void clear() {
      for (int i = 0; i < this.slotCount; i++) {
         this.setStackInSlot(i, ItemStack.EMPTY);
      }
   }

   public void dumpItems(Level level, BlockPos pos) {
      this.dumpItems(level, pos.getCenter());
   }

   public void dumpItems(Level level, Vec3 pos) {
      for (int i = 0; i < this.slotCount; i++) {
         if (!this.getStackInSlot(i).isEmpty()) {
            level.addFreshEntity(new ItemEntity(level, pos.x(), pos.y(), pos.z(), this.getStackInSlot(i)));
            this.setStackInSlot(i, ItemStack.EMPTY);
         }
      }
   }

   public ItemStack interact(ServerLevel level, Player player, InteractionHand handIn) {
      this.updateInventoryCaches();
      ItemStack heldStack = player.getItemInHand(handIn);
      if (heldStack.isEmpty() || !this.isEmpty()) {
         if (!this.nonEmptyItemStacks.isEmpty()) {
            ItemStack takeOutStack = this.nonEmptyItemStacks.getLast();
            if (takeOutStack.is(heldStack.getItem())) {
               return this.takeItemFromPlayer(player, heldStack);
            }
         }

         if (heldStack.isEmpty() || !this.hasEmptySlots()) {
            ItemStack extractedStack = this.extractItem(level, heldStack, player);
            if (!heldStack.isEmpty() && !extractedStack.isEmpty()) {
               this.takeItemFromPlayer(player, heldStack);
            }

            return extractedStack;
         }
      }

      return this.takeItemFromPlayer(player, heldStack);
   }

   public ItemStack extractItem(ServerLevel level, ItemStack heldStack, Player player) {
      List<ItemStack> nonEmptyStacks = this.nonEmptyItemStacks;
      if (nonEmptyStacks.isEmpty()) {
         return heldStack;
      }

      ItemStack takeOutStack = nonEmptyStacks.getLast();
      int slot = this.stacks.indexOf(takeOutStack);
      if (this.extractItem(slot, takeOutStack.getCount(), true).equals(ItemStack.EMPTY)) {
         return heldStack;
      }

      this.giveItemToPlayer(player, takeOutStack, slot);
      return takeOutStack;
   }

   public void giveItemToPlayer(Player playerEntity, ItemStack stack, int slot) {
      ItemHandlerHelper.giveItemToPlayer(playerEntity, stack);
      this.setStackInSlot(slot, ItemStack.EMPTY);
   }

   public ItemStack takeItemFromPlayer(Player playerEntity, ItemStack stack) {
      return this.insertItem(stack);
   }

   public ItemStack insertItem(ItemStack stack) {
      if (!stack.isEmpty()) {
         ItemStack simulate = this.insertItem(stack, true);
         if (simulate.equals(stack)) {
            return ItemStack.EMPTY;
         }

         int count = stack.getCount() - simulate.getCount();
         if (count > this.allowedItemSize) {
            count = this.allowedItemSize;
         }

         ItemStack input = stack.split(count);
         this.insertItem(input, false);
         return input;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public ItemStack insertItem(ItemStack stack, boolean simulate) {
      return ItemHandlerHelper.insertItem(this, stack, simulate);
   }
}
