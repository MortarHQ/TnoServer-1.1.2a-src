package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.LightningWoodLingzhiBlockEntity;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class LightningWoodLingzhiBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<LightningWoodLingzhiBlock> CODEC = simpleCodec(LightningWoodLingzhiBlock::new);
   private static final VoxelShape NORTH_SHAPE = Shapes.or(
      Block.box(4.0, 4.7, 3.0, 13.0, 6.4, 5.0),
      new VoxelShape[]{
         Block.box(1.5, 4.8, 5.0, 14.5, 6.6, 7.0),
         Block.box(1.0, 5.0, 7.0, 15.0, 6.8, 10.0),
         Block.box(2.0, 5.2, 10.0, 14.0, 6.9, 12.0),
         Block.box(3.5, 5.3, 12.0, 12.5, 7.0, 14.0),
         Block.box(5.5, 5.4, 14.0, 10.5, 7.0, 15.1),
         Block.box(2.25, 7.1, 9.0, 10.0, 9.2, 13.0),
         Block.box(3.0, 7.1, 13.0, 10.0, 9.2, 15.0),
         Block.box(4.5, 7.1, 8.0, 7.5, 9.2, 9.0),
         Block.box(5.9, 4.2, 15.0, 7.8, 8.2, 16.0),
         Block.box(8.2, 4.7, 14.8, 10.1, 8.3, 16.0)
      }
   );
   private static final VoxelShape EAST_SHAPE = rotateClockwise(NORTH_SHAPE);
   private static final VoxelShape SOUTH_SHAPE = rotateClockwise(EAST_SHAPE);
   private static final VoxelShape WEST_SHAPE = rotateClockwise(SOUTH_SHAPE);

   public LightningWoodLingzhiBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<LightningWoodLingzhiBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction clickedFace = context.getClickedFace();
      if (clickedFace.getAxis().isHorizontal()) {
         BlockState state = (BlockState)this.defaultBlockState().setValue(FACING, clickedFace);
         if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
            return state;
         }
      }

      for (Direction direction : context.getNearestLookingDirections()) {
         if (direction.getAxis().isHorizontal()) {
            BlockState state = (BlockState)this.defaultBlockState().setValue(FACING, direction.getOpposite());
            if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
               return state;
            }
         }
      }

      return null;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case EAST -> EAST_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         default -> NORTH_SHAPE;
      };
   }

   private static VoxelShape rotateClockwise(VoxelShape shape) {
      VoxelShape[] rotated = new VoxelShape[]{Shapes.empty()};
      shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> rotated[0] = Shapes.or(rotated[0], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX)));
      return rotated[0].optimize();
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new LightningWoodLingzhiBlockEntity(pos, state);
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getBlockState(pos.relative(((Direction)state.getValue(FACING)).getOpposite())).is((Block)BlockRegistry.SPIRIT_LOG.get());
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return direction == ((Direction)state.getValue(FACING)).getOpposite() && !state.canSurvive(level, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, direction, neighbor, level, pos, neighborPos);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int count = 2 + random.nextInt(2);
      double phase = random.nextDouble() * Math.PI * 2.0;
      Direction facing = (Direction)state.getValue(FACING);

      for (int i = 0; i < count; i++) {
         double angle = phase + (Math.PI * 2) * i / count;
         double radius = 0.29 + random.nextDouble() * 0.05;
         level.addParticle(
            (ParticleOptions)ParticleRegistry.LIGHTNING_WOOD_ARC.get(),
            pos.getX() + 0.5 + facing.getStepX() * 0.08 + Math.cos(angle) * radius,
            pos.getY() + 0.43 + random.nextDouble() * 0.12,
            pos.getZ() + 0.5 + facing.getStepZ() * 0.08 + Math.sin(angle) * radius,
            -Math.sin(angle) * 0.008,
            0.002,
            Math.cos(angle) * 0.008
         );
      }
   }
}
