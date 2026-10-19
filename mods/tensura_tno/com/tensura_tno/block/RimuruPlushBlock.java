package com.tensura_tno.block;

import com.mojang.serialization.MapCodec;
import com.tensura_tno.block.entity.RimuruPlushBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RimuruPlushBlock extends BaseEntityBlock {
   public static final MapCodec<RimuruPlushBlock> CODEC = simpleCodec(RimuruPlushBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final IntegerProperty ROTATION_OFFSET = IntegerProperty.create("rotation_offset", 0, 3);
   public static final BooleanProperty SITTING = BooleanProperty.create("sitting");
   public static final float MODEL_SCALE = 0.64F;

   public RimuruPlushBlock() {
      this(Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.WOOL).strength(0.8F).noOcclusion());
   }

   protected RimuruPlushBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(ROTATION_OFFSET, 0))
            .setValue(SITTING, false)
      );
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new RimuruPlushBlockEntity(pos, state);
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return withRotationStep(this.defaultBlockState(), RimuruPlushShapes.placementStep(context.getRotation()));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, ROTATION_OFFSET, SITTING});
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return withRotationStep(state, mirror.mirror(getRotationStep(state), 16));
   }

   public static int getRotationStep(BlockState state) {
      return RimuruPlushShapes.rotationStep((Direction)state.getValue(FACING), (Integer)state.getValue(ROTATION_OFFSET));
   }

   private static BlockState withRotationStep(BlockState state, int step) {
      return (BlockState)((BlockState)state.setValue(FACING, RimuruPlushShapes.facingForStep(step))).setValue(ROTATION_OFFSET, step & 3);
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return RimuruPlushShapes.get(getRotationStep(state), (Boolean)state.getValue(SITTING));
   }

   protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return this.getShape(state, level, pos, context);
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (player.isShiftKeyDown() && player.mayBuild() && !player.isSpectator()) {
         if (!level.isClientSide) {
            BlockState next = (BlockState)state.cycle(SITTING);
            Block.pushEntitiesUp(state, next, level, pos);
            level.setBlockAndUpdate(pos, next);
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }
}
