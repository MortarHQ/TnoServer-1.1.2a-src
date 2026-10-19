package net.regions_unexplored.world.level.block.plant.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.tag.RUBlockTags;

public class RuSnowFlowerBlock extends FlowerBlock {
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);

   public RuSnowFlowerBlock(Holder<MobEffect> mobEffect, int i, Properties properties) {
      super(mobEffect, i, properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Vec3 vec3 = state.getOffset(getter, pos);
      return SHAPE.move(vec3.x, vec3.y, vec3.z);
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      if (state.is(Blocks.SNOW)) {
         return state.getValue(SnowLayerBlock.LAYERS) == 8 ? state.is(RUBlockTags.SUPPORTS_FROZEN_GRASS) : false;
      } else {
         return state.is(BlockTags.DIRT) || state.is(RUBlockTags.SUPPORTS_FROZEN_GRASS);
      }
   }
}
