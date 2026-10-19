package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;

public class CapabilityHelper {
   public static void runOnItemHandler(Entity entity, Consumer<IItemHandler> run) {
      runOnCapability(entity, ItemHandler.ENTITY, null, run);
   }

   public static <T> T getFromItemHandler(Level level, BlockPos pos, @Nullable Direction context, Function<IItemHandler, T> get, T defaultValue) {
      return getFromCapability(level, pos, ItemHandler.BLOCK, context, get, defaultValue);
   }

   public static <T> T getFromItemHandler(Level level, BlockPos pos, Function<IItemHandler, T> get, T defaultValue) {
      return getFromItemHandler(level, pos, null, get, defaultValue);
   }

   public static <T, C> void runOnCapability(Entity entity, EntityCapability<T, C> capability, @Nullable C context, Consumer<T> run) {
      runOnCapability(run, (T)entity.getCapability(capability, context));
   }

   public static <T, C> void runOnCapability(ItemStack stack, ItemCapability<T, C> capability, @Nullable C context, Consumer<T> run) {
      runOnCapability(run, (T)stack.getCapability(capability, context));
   }

   private static <T> void runOnCapability(Consumer<T> run, @Nullable T t) {
      if (t != null) {
         run.accept(t);
      }
   }

   public static <T, C, U> U getFromCapability(ItemStack stack, ItemCapability<T, C> capability, @Nullable C context, Function<T, U> get, U defaultValue) {
      T t = (T)stack.getCapability(capability, context);
      return t == null ? defaultValue : get.apply(t);
   }

   public static <T, C, U> U getFromCapability(
      Level level, BlockPos pos, BlockCapability<T, C> capability, @Nullable C context, Function<T, U> get, U defaultValue
   ) {
      return getFromCapability(level, pos, null, null, capability, context, get, defaultValue);
   }

   public static <T, C, U> U getFromCapability(
      BlockEntity blockEntity, BlockCapability<T, C> capability, @Nullable C context, Function<T, U> get, U defaultValue
   ) {
      return blockEntity.getLevel() == null
         ? defaultValue
         : getFromCapability(
            blockEntity.getLevel(), blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, capability, context, get, defaultValue
         );
   }

   public static <T, C, U> U getFromCapability(
      Level level,
      BlockPos pos,
      @Nullable BlockState state,
      @Nullable BlockEntity blockEntity,
      BlockCapability<T, C> capability,
      @Nullable C context,
      Function<T, U> get,
      U defaultValue
   ) {
      T t = (T)level.getCapability(capability, pos, state, blockEntity, context);
      return t == null ? defaultValue : get.apply(t);
   }

   public static <T> T getFromFluidHandler(BlockEntity be, Direction side, Function<IFluidHandler, T> get, T defaultValue) {
      return getFromCapability(be, FluidHandler.BLOCK, side, get, defaultValue);
   }

   public static <T> T getFromFluidHandler(ItemStack stack, Function<IFluidHandlerItem, T> get, T defaultValue) {
      return getFromCapability(stack, FluidHandler.ITEM, null, get, defaultValue);
   }

   public static void runOnFluidHandler(ItemStack stack, Consumer<IFluidHandlerItem> run) {
      runOnCapability(stack, FluidHandler.ITEM, null, run);
   }
}
