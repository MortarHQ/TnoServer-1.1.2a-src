package com.github.wal_bos.moreskills.world.block;

import com.github.wal_bos.moreskills.handler.PathsRitualHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathsPortalBlock extends Block {
   public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

   public PathsPortalBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(AXIS, Axis.X));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AXIS});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (level instanceof ServerLevel serverLevel && entity instanceof ServerPlayer player) {
         PathsRitualHandler.tickPlayerInPathsPortal(serverLevel, player, pos);
      }
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return switch (rotation) {
         case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
            switch ((Axis)state.getValue(AXIS)) {
               case X:
                  yield (BlockState)state.setValue(AXIS, Axis.Z);
               case Z:
                  yield (BlockState)state.setValue(AXIS, Axis.X);
               default:
                  yield state;
            }
         }
         default -> state;
      };
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
      return 1.0F;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      for (int i = 0; i < 3; i++) {
         double x = pos.getX() + random.nextDouble();
         double y = pos.getY() + random.nextDouble();
         double z = pos.getZ() + random.nextDouble();
         double dx = (random.nextDouble() - 0.5) * 0.35;
         double dy = (random.nextDouble() - 0.5) * 0.35;
         double dz = (random.nextDouble() - 0.5) * 0.35;
         level.addParticle(ParticleTypes.REVERSE_PORTAL, x, y, z, dx, dy, dz);
      }
   }
}
