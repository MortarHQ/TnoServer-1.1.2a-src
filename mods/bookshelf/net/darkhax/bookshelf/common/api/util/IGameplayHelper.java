package net.darkhax.bookshelf.common.api.util;

import java.util.function.BiFunction;
import net.darkhax.bookshelf.common.api.registry.register.RegisterMenuScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface IGameplayHelper {
   RandomSource RNG = RandomSource.create();

   default ItemStack getCraftingRemainder(ItemStack input) {
      if (input.getItem().hasCraftingRemainingItem()) {
         Item remainder = input.getItem().getCraftingRemainingItem();
         if (remainder != null) {
            return remainder.getDefaultInstance();
         }
      }

      return ItemStack.EMPTY;
   }

   default ItemStack inventoryInsert(ServerLevel level, BlockPos pos, Direction side, ItemStack stack) {
      if (stack.isEmpty()) {
         return stack;
      }

      Container container = this.getContainer(level, pos);
      return container != null ? HopperBlockEntity.addItem(null, container, stack, side) : stack;
   }

   @Nullable
   default Container getContainer(ServerLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof WorldlyContainerHolder holder) {
         return holder.getContainer(state, level, pos);
      } else {
         return level.getBlockEntity(pos) instanceof Container beContainer ? beContainer : null;
      }
   }

   default ItemStack addItem(ItemStack stack, NonNullList<ItemStack> inventory, int[] slots) {
      for (int slot : slots) {
         if (stack.isEmpty()) {
            return stack;
         }

         ItemStack existing = (ItemStack)inventory.get(slot);
         if (existing.isEmpty()) {
            inventory.set(slot, stack);
            return ItemStack.EMPTY;
         }

         if (existing.getCount() < existing.getMaxStackSize() && ItemStack.isSameItemSameComponents(existing, stack)) {
            int availableSpace = existing.getMaxStackSize() - existing.getCount();
            int movedAmount = Math.min(stack.getCount(), availableSpace);
            stack.shrink(movedAmount);
            existing.grow(movedAmount);
         }
      }

      return stack;
   }

   <T extends BlockEntity> Builder<T> blockEntityBuilder(BiFunction<BlockPos, BlockState, T> var1, Block... var2);

   <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void bindMenu(MenuType<? extends M> var1, RegisterMenuScreen.ScreenFactory<M, U> var2);

   default void dropRemainders(Level level, BlockPos pos, ItemStack old) {
      if (!level.isClientSide && !old.isEmpty()) {
         ItemStack remainder = this.getCraftingRemainder(old);
         if (!remainder.isEmpty()) {
            Block.popResource(level, pos, remainder.copy());
         }
      }
   }

   net.minecraft.world.item.CreativeModeTab.Builder tabBuilder();
}
