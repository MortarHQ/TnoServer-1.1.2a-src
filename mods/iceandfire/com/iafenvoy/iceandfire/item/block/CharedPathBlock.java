package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class CharedPathBlock extends DirtPathBlock {
   public static final BooleanProperty REVERTS = BooleanProperty.create("revert");
   public final int dragonType;

   public CharedPathBlock(int dragonType) {
      super(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .sound(dragonType != 1 ? SoundType.GRAVEL : SoundType.GLASS)
            .strength(0.6F)
            .friction(dragonType != 1 ? 0.6F : 0.98F)
            .randomTicks()
            .requiresCorrectToolForDrops()
      );
      this.dragonType = dragonType;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(REVERTS, Boolean.FALSE));
   }

   public static String getNameFromType(int dragonType) {
      return switch (dragonType) {
         case 0 -> "chared_dirt_path";
         case 1 -> "frozen_dirt_path";
         case 2 -> "crackled_dirt_path";
         default -> "";
      };
   }

   public BlockState getSmushedState(int dragonType) {
      return switch (dragonType) {
         case 0 -> ((Block)IafBlocks.CHARRED_DIRT.get()).defaultBlockState();
         case 1 -> ((Block)IafBlocks.FROZEN_DIRT.get()).defaultBlockState();
         case 2 -> ((Block)IafBlocks.CRACKLED_DIRT.get()).defaultBlockState();
         default -> null;
      };
   }

   public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      super.tick(state, world, pos, rand);
      if (!world.isClientSide) {
         if (!world.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3))) {
            return;
         }

         if ((Boolean)state.getValue(REVERTS) && rand.nextInt(3) == 0) {
            world.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
         }
      }

      if (world.getBlockState(pos.above()).isSolid()) {
         world.setBlockAndUpdate(pos, this.getSmushedState(this.dragonType));
      }

      this.updateBlockState(world, pos);
   }

   private void updateBlockState(Level worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos.above()).isSolid()) {
         worldIn.setBlockAndUpdate(pos, this.getSmushedState(this.dragonType));
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{REVERTS});
   }
}
