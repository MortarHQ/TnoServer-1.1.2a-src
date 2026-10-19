package dev.ftb.mods.ftbquests.block.entity;

import dev.ftb.mods.ftbquests.item.LootCrateItem;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import net.minecraft.world.level.block.state.BlockState;

public class LootCrateOpenerBlockEntity extends BlockEntity {
   private static final LootCrateOpenerBlockEntity.ItemEntry EMPTY_ENTRY = new LootCrateOpenerBlockEntity.ItemEntry(ItemStack.EMPTY);
   private static final int MAX_ITEM_TYPES = 64;
   private UUID owner = null;
   private final Map<LootCrateOpenerBlockEntity.ItemEntry, Integer> outputs = new LinkedHashMap<>();

   public LootCrateOpenerBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType)ModBlockEntityTypes.LOOT_CRATE_OPENER.get(), blockPos, blockState);
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      this.outputs.clear();
      ListTag itemTag = compoundTag.getList("Items", 10);
      itemTag.forEach(el -> {
         if (el instanceof CompoundTag tag) {
            ItemStack stack = ItemStack.parseOptional(provider, tag.getCompound("item"));
            int amount = tag.getInt("amount");
            this.outputs.put(new LootCrateOpenerBlockEntity.ItemEntry(stack), amount);
         }
      });
      this.owner = compoundTag.hasUUID("Owner") ? compoundTag.getUUID("Owner") : null;
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      ListTag itemTag = new ListTag();
      this.outputs.forEach((item, amount) -> {
         CompoundTag tag = new CompoundTag();
         tag.put("item", item.stack.save(provider, new CompoundTag()));
         tag.putInt("amount", amount);
         itemTag.add(tag);
      });
      if (!itemTag.isEmpty()) {
         compoundTag.put("Items", itemTag);
      }

      if (this.owner != null) {
         compoundTag.putUUID("Owner", this.owner);
      }
   }

   protected void applyImplicitComponents(DataComponentInput dataComponentInput) {
      super.applyImplicitComponents(dataComponentInput);
      this.outputs.clear();
      ((ItemContainerContents)dataComponentInput.getOrDefault((DataComponentType)ModDataComponents.LOOT_CRATE_ITEMS.get(), ItemContainerContents.EMPTY))
         .stream()
         .forEach(stack -> this.outputs.put(new LootCrateOpenerBlockEntity.ItemEntry(stack), stack.getCount()));
   }

   protected void collectImplicitComponents(Builder builder) {
      super.collectImplicitComponents(builder);
      builder.set(
         (DataComponentType)ModDataComponents.LOOT_CRATE_ITEMS.get(),
         ItemContainerContents.fromItems(this.outputs.keySet().stream().map(LootCrateOpenerBlockEntity.ItemEntry::stack).toList())
      );
   }

   public UUID getOwner() {
      return this.owner;
   }

   public void setOwner(UUID owner) {
      this.owner = owner;
   }

   public int getOutputCount() {
      return this.outputs.values().stream().mapToInt(v -> v).sum();
   }

   protected int _getSlots() {
      return 2;
   }

   protected ItemStack _getStackInSlot(int slot) {
      return slot == 0 ? ItemStack.EMPTY : this.outputs.keySet().stream().findFirst().orElse(EMPTY_ENTRY).stack;
   }

   protected ItemStack _insertItem(int slot, ItemStack stack, boolean simulate) {
      if (slot == 0 && this.level != null && this.level.getServer() != null && !this.level.isClientSide && this.outputs.size() < 64) {
         LootCrate crate = LootCrateItem.getCrate(stack, false);
         if (crate == null) {
            return stack;
         }

         ServerPlayer player = this.owner == null ? null : this.level.getServer().getPlayerList().getPlayer(this.owner);
         boolean update = false;
         int nAttempts = stack.getCount();

         for (WeightedReward wr : crate.getTable().generateWeightedRandomRewards(this.level.getRandom(), nAttempts, true)) {
            List<ItemStack> stacks = new ArrayList<>();
            if (wr.getReward().automatedClaimPre(this, stacks, this.level.random, this.owner, player)) {
               update = true;
               if (!simulate) {
                  for (ItemStack stack1 : stacks) {
                     LootCrateOpenerBlockEntity.ItemEntry entry = new LootCrateOpenerBlockEntity.ItemEntry(stack1);
                     int newAmount = this.outputs.getOrDefault(entry, 0) + stack1.getCount();
                     this.outputs.put(entry, newAmount);
                  }

                  wr.getReward().automatedClaimPost(this, this.owner, player);
               }
            }
         }

         if (update && !simulate) {
            this.setChanged();
         }

         return ItemStack.EMPTY;
      } else {
         return stack;
      }
   }

   protected boolean _isItemValid(int slot, ItemStack stack) {
      return slot == 0 && LootCrateItem.getCrate(stack, this.level.isClientSide) != null;
   }

   protected ItemStack _extractItem(int slot, int amount, boolean simulate) {
      if (this.level != null && slot != 0 && amount > 0 && !this.outputs.isEmpty()) {
         LootCrateOpenerBlockEntity.ItemEntry entry = this.outputs.keySet().stream().findFirst().orElseThrow();
         ItemStack stack1 = entry.stack().copy();
         int count = this.outputs.get(entry);
         int toExtract = Math.min(count, Math.min(amount, stack1.getMaxStackSize()));
         stack1.setCount(toExtract);
         if (!simulate && !this.level.isClientSide) {
            count -= toExtract;
            if (count <= 0) {
               this.outputs.remove(entry);
            } else {
               this.outputs.put(entry, count);
            }

            this.setChanged();
         }

         return stack1;
      } else {
         return ItemStack.EMPTY;
      }
   }

   private record ItemEntry(ItemStack stack) {
      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else {
            return o != null && this.getClass() == o.getClass()
               ? ItemStack.isSameItemSameComponents(this.stack, ((LootCrateOpenerBlockEntity.ItemEntry)o).stack)
               : false;
         }
      }

      @Override
      public int hashCode() {
         return ItemStack.hashItemAndComponents(this.stack);
      }
   }
}
