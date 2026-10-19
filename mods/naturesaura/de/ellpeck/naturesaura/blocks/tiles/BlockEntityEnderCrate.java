package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.BlockEnderCrate;
import de.ellpeck.naturesaura.gui.ContainerEnderCrate;
import de.ellpeck.naturesaura.gui.ModContainers;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class BlockEntityEnderCrate extends BlockEntityImpl implements MenuProvider {
   public String name;
   public final IItemHandlerModifiable wrappedEnderStorage = new IItemHandlerModifiable() {
      public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
         this.getStorage().setStackInSlot(slot, stack);
      }

      public int getSlots() {
         return this.getStorage().getSlots();
      }

      @Nonnull
      public ItemStack getStackInSlot(int slot) {
         return this.getStorage().getStackInSlot(slot);
      }

      @Nonnull
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
         if (!BlockEntityEnderCrate.this.canUseRightNow(20)) {
            return stack;
         }

         ItemStack remain = this.getStorage().insertItem(slot, stack, simulate);
         if (!simulate) {
            BlockEntityEnderCrate.this.drainAura((stack.getCount() - remain.getCount()) * 20);
         }

         return remain;
      }

      @Nonnull
      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         if (!BlockEntityEnderCrate.this.canUseRightNow(20)) {
            return ItemStack.EMPTY;
         }

         ItemStack extracted = this.getStorage().extractItem(slot, amount, simulate);
         if (!simulate) {
            BlockEntityEnderCrate.this.drainAura(extracted.getCount() * 20);
         }

         return extracted;
      }

      public int getSlotLimit(int slot) {
         return this.getStorage().getSlotLimit(slot);
      }

      public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
         return this.getStorage().isItemValid(slot, stack);
      }

      private IItemHandlerModifiable getStorage() {
         return ILevelData.getOverworldData(BlockEntityEnderCrate.this.level).getEnderStorage(BlockEntityEnderCrate.this.name);
      }
   };

   public BlockEntityEnderCrate(BlockPos pos, BlockState state) {
      super(ModBlockEntities.ENDER_CRATE, pos, state);
   }

   public boolean canOpen() {
      return this.name != null;
   }

   @Override
   public void dropInventory() {
   }

   @Override
   public void modifyDrop(ItemStack regularItem) {
      if (this.name != null) {
         regularItem.set(BlockEnderCrate.Data.TYPE, new BlockEnderCrate.Data(this.name));
      }
   }

   @Override
   public void loadDataOnPlace(ItemStack stack) {
      super.loadDataOnPlace(stack);
      if (!this.level.isClientSide) {
         String name = BlockEnderCrate.getEnderName(stack);
         if (name != null && !name.isEmpty()) {
            this.name = name;
         }
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK && this.name != null) {
         compound.putString("name", this.name);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK && compound.contains("name")) {
         this.name = compound.getString("name");
      }
   }

   public void drainAura(int amount) {
      if (amount > 0) {
         BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 35, this.worldPosition);
         IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, amount);
      }
   }

   public Component getDisplayName() {
      return Component.translatable("info.naturesaura.ender_crate", new Object[]{ChatFormatting.ITALIC + this.name + ChatFormatting.RESET});
   }

   @Nullable
   public AbstractContainerMenu createMenu(int window, Inventory inv, Player player) {
      return new ContainerEnderCrate(ModContainers.ENDER_CRATE, window, player, this.wrappedEnderStorage);
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
