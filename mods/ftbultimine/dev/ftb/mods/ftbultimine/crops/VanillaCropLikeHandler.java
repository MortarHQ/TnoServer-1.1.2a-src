package dev.ftb.mods.ftbultimine.crops;

import dev.ftb.mods.ftbultimine.api.crop.CropLikeHandler;
import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public enum VanillaCropLikeHandler implements CropLikeHandler {
   INSTANCE;

   public static boolean looksLikeACrop(BlockState state) {
      return state.getBlock() instanceof BushBlock || state.getBlock() instanceof CocoaBlock;
   }

   public static boolean equivalentForSelection(BlockState orig, BlockState other) {
      return looksLikeACrop(orig) && looksLikeACrop(other) && getBushType(orig.getBlock()) == getBushType(other.getBlock());
   }

   @Override
   public boolean isApplicable(Level level, BlockPos pos, BlockState state) {
      return state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)
         || state.getBlock() instanceof SweetBerryBushBlock && (Integer)state.getValue(SweetBerryBushBlock.AGE) >= 3
         || state.getBlock() instanceof CocoaBlock && (Integer)state.getValue(CocoaBlock.AGE) >= 2;
   }

   @Override
   public boolean doHarvesting(Player player, BlockPos pos, BlockState state, ItemCollector itemCollector) {
      BlockEntity blockEntity = state.hasBlockEntity() ? player.level().getBlockEntity(pos) : null;

      for (ItemStack stack : Block.getDrops(state, (ServerLevel)player.level(), pos, blockEntity, player, ItemStack.EMPTY)) {
         if (Block.byItem(stack.getItem()) == state.getBlock() && this.consumesItemToReplant(state)) {
            stack.shrink(1);
         }

         itemCollector.add(stack);
      }

      this.resetAge(player.level(), pos, state);
      return true;
   }

   @Override
   public boolean isEquivalent(BlockState original, BlockState state) {
      return equivalentForSelection(original, state);
   }

   private static int getBushType(Block block) {
      if (block instanceof CropBlock) {
         return 1;
      } else if (block instanceof SaplingBlock) {
         return 2;
      } else if (block instanceof CocoaBlock) {
         return 3;
      } else {
         return block instanceof SweetBerryBushBlock ? 4 : 0;
      }
   }

   private boolean consumesItemToReplant(BlockState state) {
      return state.getBlock() != Blocks.SWEET_BERRY_BUSH;
   }

   private void resetAge(Level level, BlockPos pos, BlockState currentState) {
      if (currentState.getBlock() instanceof CropBlock cropBlock) {
         BlockState newState = (BlockState)currentState.setValue(cropBlock.getAgeProperty(), 0);
         level.setBlock(pos, newState, 3);
      } else if (currentState.getBlock() instanceof SweetBerryBushBlock) {
         int currentAge = (Integer)currentState.getValue(SweetBerryBushBlock.AGE);
         level.setBlock(pos, (BlockState)currentState.setValue(SweetBerryBushBlock.AGE, Math.min(currentAge, 1)), 3);
      } else if (currentState.getBlock() instanceof CocoaBlock) {
         level.setBlock(pos, (BlockState)currentState.setValue(CocoaBlock.AGE, 0), 3);
      }
   }
}
