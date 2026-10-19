package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public final class AncientSteleBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<AncientSteleBlock> CODEC = simpleCodec(AncientSteleBlock::new);
   private static final VoxelShape NORTH_SHAPE = Shapes.or(Block.box(0.75, 0.0, 3.75, 15.5, 2.25, 12.0), Block.box(3.4, 2.0, 6.3, 12.8, 17.7, 9.7)).optimize();
   private static final Map<Direction, VoxelShape> SHAPES = Map.of(
      Direction.NORTH,
      NORTH_SHAPE,
      Direction.EAST,
      rotateNorthShape(Direction.EAST),
      Direction.SOUTH,
      rotateNorthShape(Direction.SOUTH),
      Direction.WEST,
      rotateNorthShape(Direction.WEST)
   );

   public AncientSteleBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPES.get(state.getValue(FACING));
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPES.get(state.getValue(FACING));
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Shapes.empty();
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new AncientSteleBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return level.isClientSide ? null : (tickerLevel, pos, blockState, blockEntity) -> {
         if (blockEntity instanceof AncientSteleBlockEntity stele) {
            AncientSteleBlockEntity.serverTick(tickerLevel, pos, blockState, stele);
         }
      };
   }

   private static VoxelShape rotateNorthShape(Direction facing) {
      VoxelShape rotated = Shapes.empty();

      for (AABB box : NORTH_SHAPE.toAabbs()) {
         double x1 = box.minX * 16.0;
         double y1 = box.minY * 16.0;
         double z1 = box.minZ * 16.0;
         double x2 = box.maxX * 16.0;
         double y2 = box.maxY * 16.0;
         double z2 = box.maxZ * 16.0;

         VoxelShape part = switch (facing) {
            case EAST -> Block.box(16.0 - z2, y1, x1, 16.0 - z1, y2, x2);
            case SOUTH -> Block.box(16.0 - x2, y1, 16.0 - z2, 16.0 - x1, y2, 16.0 - z1);
            case WEST -> Block.box(z1, y1, 16.0 - x2, z2, y2, 16.0 - x1);
            default -> NORTH_SHAPE;
         };
         rotated = Shapes.or(rotated, part);
      }

      return rotated.optimize();
   }
}
