package com.chronodawn.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TemporalParticleEmitterBlock extends Block {
   private static final VoxelShape SHAPE = Shapes.empty();

   public TemporalParticleEmitterBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.NONE).strength(-1.0F, 3600000.0F).noCollission().noOcclusion().air().pushReaction(PushReaction.BLOCK);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (random.nextFloat() < 0.7F) {
         double x = pos.getX() + random.nextDouble();
         double y = pos.getY() + random.nextDouble() * 0.3;
         double z = pos.getZ() + random.nextDouble();
         double velocityX = (random.nextDouble() - 0.5) * 0.02;
         double velocityY = random.nextDouble() * 0.1 + 0.15;
         double velocityZ = (random.nextDouble() - 0.5) * 0.02;
         level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, velocityX, velocityY, velocityZ);
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.INVISIBLE;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return true;
   }
}
