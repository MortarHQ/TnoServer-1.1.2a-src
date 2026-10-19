package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.tag.IafBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ElementalFlowerBlock extends BushBlock {
   private static final MapCodec<? extends BushBlock> CODEC = simpleCodec(s -> new ElementalFlowerBlock());

   public ElementalFlowerBlock() {
      super(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
      );
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
      if (state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.DIRT)
         || state.is(Blocks.COARSE_DIRT)
         || state.is(Blocks.PODZOL)
         || state.is(Blocks.FARMLAND)
         || state.is(BlockTags.SAND)) {
         return true;
      } else if (this == IafBlocks.FIRE_LILY.get()) {
         return state.is(BlockTags.SAND) || state.is(Blocks.NETHERRACK);
      } else {
         return this == IafBlocks.LIGHTNING_LILY.get()
            ? state.is(BlockTags.DIRT) || state.is(IafBlockTags.GRASSES)
            : state.is(BlockTags.ICE) || state.is(BlockTags.SNOW) || state.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON);
      }
   }
}
