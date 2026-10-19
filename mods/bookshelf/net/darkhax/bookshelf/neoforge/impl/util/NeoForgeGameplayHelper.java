package net.darkhax.bookshelf.neoforge.impl.util;

import java.util.function.BiFunction;
import net.darkhax.bookshelf.common.api.registry.register.RegisterMenuScreen;
import net.darkhax.bookshelf.common.api.util.IGameplayHelper;
import net.darkhax.bookshelf.neoforge.mixin.access.gui.screen.AccessorMenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class NeoForgeGameplayHelper implements IGameplayHelper {
   @Override
   public ItemStack getCraftingRemainder(ItemStack input) {
      Item item = input.getItem();
      return item.hasCraftingRemainingItem(input) ? item.getCraftingRemainingItem(input) : ItemStack.EMPTY;
   }

   @Override
   public ItemStack inventoryInsert(ServerLevel level, BlockPos pos, Direction side, ItemStack stack) {
      IItemHandler inventory = (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos, side);
      return inventory != null ? ItemHandlerHelper.insertItemStacked(inventory, stack, false) : IGameplayHelper.super.inventoryInsert(level, pos, side, stack);
   }

   @Override
   public <T extends BlockEntity> Builder<T> blockEntityBuilder(BiFunction<BlockPos, BlockState, T> factory, Block... validBlocks) {
      BlockEntitySupplier<T> supplier = factory::apply;
      return Builder.of(supplier, validBlocks);
   }

   @Override
   public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void bindMenu(
      MenuType<? extends M> type, RegisterMenuScreen.ScreenFactory<M, U> factory
   ) {
      ScreenConstructor<M, U> screenFactory = factory::create;
      AccessorMenuScreens.register(type, screenFactory);
   }

   @Override
   public net.minecraft.world.item.CreativeModeTab.Builder tabBuilder() {
      return CreativeModeTab.builder();
   }
}
