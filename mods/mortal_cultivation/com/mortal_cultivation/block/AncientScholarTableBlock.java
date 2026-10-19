package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public final class AncientScholarTableBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<AncientScholarTableBlock> CODEC = simpleCodec(AncientScholarTableBlock::new);
   public static final EnumProperty<AncientScholarTableBlock.TablePart> PART = EnumProperty.create("part", AncientScholarTableBlock.TablePart.class);
   private static final double[][] MAIN_NORTH_BOXES = new double[][]{
      {0.0, 13.86, 1.7, 14.7, 15.804, 14.3},
      {10.89, 0.3, 2.21, 13.59, 14.1, 4.91},
      {10.89, 0.3, 11.09, 13.59, 14.1, 13.79},
      {0.0, 3.39, 2.81, 11.82, 4.8, 4.31},
      {0.0, 3.39, 11.69, 11.82, 4.8, 13.19},
      {11.67, 3.66, 4.64, 12.81, 4.8, 11.36},
      {0.0, 10.92, 2.33, 13.2, 14.25, 3.62},
      {0.0, 10.92, 12.38, 13.2, 14.25, 13.67},
      {12.18, 11.16, 3.62, 13.47, 14.25, 12.38}
   };
   private static final double[][] EXTENSION_NORTH_BOXES = new double[][]{
      {1.3, 13.86, 1.7, 16.0, 15.804, 14.3},
      {2.41, 0.3, 2.21, 5.11, 14.1, 4.91},
      {2.41, 0.3, 11.09, 5.11, 14.1, 13.79},
      {4.18, 3.39, 2.81, 16.0, 4.8, 4.31},
      {4.18, 3.39, 11.69, 16.0, 4.8, 13.19},
      {3.19, 3.66, 4.64, 4.33, 4.8, 11.36},
      {2.8, 10.92, 2.33, 16.0, 14.25, 3.62},
      {2.8, 10.92, 12.38, 16.0, 14.25, 13.67},
      {2.53, 11.16, 3.62, 3.82, 14.25, 12.38}
   };
   private static final Map<Direction, VoxelShape> MAIN_SHAPES = createDirectionalShapes(MAIN_NORTH_BOXES);
   private static final Map<Direction, VoxelShape> EXTENSION_SHAPES = createDirectionalShapes(EXTENSION_NORTH_BOXES);

   public AncientScholarTableBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
            .setValue(PART, AncientScholarTableBlock.TablePart.MAIN)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PART});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction facing = context.getHorizontalDirection();
      BlockPos extensionPos = context.getClickedPos().relative(facing.getCounterClockWise());
      Level level = context.getLevel();
      return level.getBlockState(extensionPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(extensionPos)
         ? (BlockState)this.defaultBlockState().setValue(FACING, facing)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      if (!level.isClientSide) {
         BlockPos extensionPos = pos.relative(getOtherPartDirection(state));
         level.setBlock(extensionPos, (BlockState)state.setValue(PART, AncientScholarTableBlock.TablePart.EXTENSION), 3);
         level.blockUpdated(pos, Blocks.AIR);
         state.updateNeighbourShapes(level, pos, 3);
      }
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if (direction == getOtherPartDirection(state)) {
         return this.isMatchingPart(state, neighborState) ? state : Blocks.AIR.defaultBlockState();
      } else {
         return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
      }
   }

   protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      if (!newState.is(this)) {
         if (!level.isClientSide
            && state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN
            && level.getBlockEntity(pos) instanceof AncientScholarTableBlockEntity table) {
            table.dropContents(level);
         }

         BlockPos otherPos = pos.relative(getOtherPartDirection(state));
         BlockState otherState = level.getBlockState(otherPos);
         if (this.isMatchingPart(state, otherState)) {
            level.removeBlock(otherPos, false);
         }
      }

      super.onRemove(state, level, pos, newState, movedByPiston);
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      BlockState mirroredState = state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
      return mirror == Mirror.NONE
         ? mirroredState
         : (BlockState)mirroredState.setValue(PART, ((AncientScholarTableBlock.TablePart)state.getValue(PART)).getOpposite());
   }

   public RenderShape getRenderShape(BlockState state) {
      return state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return getPartShape(state);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return getPartShape(state);
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Shapes.empty();
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      BlockPos mainPos = state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? pos : pos.relative(getOtherPartDirection(state));
      if (level.getBlockEntity(mainPos) instanceof AncientScholarTableBlockEntity table) {
         if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(table, buffer -> buffer.writeBlockPos(mainPos));
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? new AncientScholarTableBlockEntity(pos, state) : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return !level.isClientSide && state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? (tickerLevel, pos, blockState, blockEntity) -> {
         if (blockEntity instanceof AncientScholarTableBlockEntity table) {
            AncientScholarTableBlockEntity.tick(tickerLevel, pos, blockState, table);
         }
      } : null;
   }

   private static VoxelShape getPartShape(BlockState state) {
      Map<Direction, VoxelShape> shapes = state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? MAIN_SHAPES : EXTENSION_SHAPES;
      return shapes.get(state.getValue(FACING));
   }

   private static Direction getOtherPartDirection(BlockState state) {
      Direction facing = (Direction)state.getValue(FACING);
      return state.getValue(PART) == AncientScholarTableBlock.TablePart.MAIN ? facing.getCounterClockWise() : facing.getClockWise();
   }

   private boolean isMatchingPart(BlockState state, BlockState otherState) {
      return otherState.is(this) && otherState.getValue(FACING) == state.getValue(FACING) && otherState.getValue(PART) != state.getValue(PART);
   }

   private static Map<Direction, VoxelShape> createDirectionalShapes(double[][] northBoxes) {
      return Map.of(
         Direction.NORTH,
         createShape(northBoxes, Direction.NORTH),
         Direction.EAST,
         createShape(northBoxes, Direction.EAST),
         Direction.SOUTH,
         createShape(northBoxes, Direction.SOUTH),
         Direction.WEST,
         createShape(northBoxes, Direction.WEST)
      );
   }

   private static VoxelShape createShape(double[][] boxes, Direction facing) {
      VoxelShape shape = Shapes.empty();

      for (double[] box : boxes) {
         double x1 = box[0];
         double y1 = box[1];
         double z1 = box[2];
         double x2 = box[3];
         double y2 = box[4];
         double z2 = box[5];

         VoxelShape rotatedBox = switch (facing) {
            case NORTH -> Block.box(x1, y1, z1, x2, y2, z2);
            case EAST -> Block.box(16.0 - z2, y1, x1, 16.0 - z1, y2, x2);
            case SOUTH -> Block.box(16.0 - x2, y1, 16.0 - z2, 16.0 - x1, y2, 16.0 - z1);
            case WEST -> Block.box(z1, y1, 16.0 - x2, z2, y2, 16.0 - x1);
            default -> throw new IllegalArgumentException("Horizontal direction required: " + facing);
         };
         shape = Shapes.or(shape, rotatedBox);
      }

      return shape.optimize();
   }

   public enum TablePart implements StringRepresentable {
      MAIN("main"),
      EXTENSION("extension");

      private final String serializedName;

      TablePart(String serializedName) {
         this.serializedName = serializedName;
      }

      public String getSerializedName() {
         return this.serializedName;
      }

      private AncientScholarTableBlock.TablePart getOpposite() {
         return this == MAIN ? EXTENSION : MAIN;
      }
   }
}
