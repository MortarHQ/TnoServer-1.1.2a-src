package com.benji.netherman.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrimsonHoneyBlock extends HalfTransparentBlock {
   protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

   public CrimsonHoneyBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
   }

   public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
      if (entity.isSuppressingBounce()) {
         super.updateEntityAfterFallOn(level, entity);
      } else {
         this.bounceUpAndBack(entity);
      }
   }

   private void bounceUpAndBack(Entity entity) {
      Vec3 vec3 = entity.getDeltaMovement();
      if (vec3.y < 0.0) {
         double dx = entity.getX() - entity.xo;
         double dz = entity.getZ() - entity.zo;
         double bounceY = -vec3.y * 2.0;
         double pushX = -dx * 8.0;
         double pushZ = -dz * 8.0;
         entity.setDeltaMovement(pushX, bounceY, pushZ);
         entity.hurtMarked = true;
      }
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (!entity.isSuppressingBounce()) {
         double entityBottom = entity.getY();
         double blockTop = pos.getY() + 1.0;
         if (entityBottom < blockTop - 0.1) {
            double dx = entity.getX() - entity.xo;
            double dz = entity.getZ() - entity.zo;
            if (Math.abs(dx) > 0.05 || Math.abs(dz) > 0.05) {
               entity.setDeltaMovement(-dx * 4.0, 0.6, -dz * 4.0);
               entity.hurtMarked = true;
            }
         }

         super.entityInside(state, level, pos, entity);
      }
   }
}
