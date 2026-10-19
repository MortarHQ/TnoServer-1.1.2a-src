package net.regions_unexplored.block.type.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShortFlowerBlock extends FlowerBlock {
   protected static final VoxelShape SHAPE_SHORT = Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);

   public ShortFlowerBlock(Holder<MobEffect> effect, float duration, Properties properties) {
      super(effect, duration, properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Vec3 vec3 = state.getOffset(getter, pos);
      return SHAPE_SHORT.move(vec3.x, vec3.y, vec3.z);
   }
}
