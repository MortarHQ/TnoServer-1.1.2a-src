package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MossyCarpetBlock extends Block implements BonemealableBlock {
   public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
   public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
   private static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
   private static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
   private static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
   private static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
   private static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
      (Map)Util.make(Maps.newEnumMap(Direction.class), enumMap -> {
         enumMap.put(Direction.NORTH, NORTH);
         enumMap.put(Direction.EAST, EAST);
         enumMap.put(Direction.SOUTH, SOUTH);
         enumMap.put(Direction.WEST, WEST);
      })
   );
   private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private static final VoxelShape WEST_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);
   private static final VoxelShape EAST_SHORT_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
   private static final VoxelShape NORTH_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
   private static final VoxelShape SOUTH_SHORT_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
   private final Map<BlockState, VoxelShape> shapesCache;

   public MapCodec<MossyCarpetBlock> codec() {
      return CODEC;
   }

   public MossyCarpetBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(BASE, true))
                     .setValue(NORTH, WallSide.NONE))
                  .setValue(EAST, WallSide.NONE))
               .setValue(SOUTH, WallSide.NONE))
            .setValue(WEST, WallSide.NONE)
      );
      this.shapesCache = ImmutableMap.copyOf(
         this.getStateDefinition().getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
      );
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
      return Shapes.empty();
   }

   private static VoxelShape calculateShape(BlockState state) {
      VoxelShape shape = Shapes.empty();
      if ((Boolean)state.getValue(BASE)) {
         shape = DOWN_AABB;
      }
      shape = switch ((WallSide)state.getValue(NORTH)) {
         case NONE -> shape;
         case LOW -> Shapes.or(shape, NORTH_SHORT_AABB);
         case TALL -> Shapes.or(shape, NORTH_AABB);
         default -> throw new MatchException(null, null);
      };

      shape = switch ((WallSide)state.getValue(SOUTH)) {
         case NONE -> shape;
         case LOW -> Shapes.or(shape, SOUTH_SHORT_AABB);
         case TALL -> Shapes.or(shape, SOUTH_AABB);
         default -> throw new MatchException(null, null);
      };

      shape = switch ((WallSide)state.getValue(EAST)) {
         case NONE -> shape;
         case LOW -> Shapes.or(shape, EAST_SHORT_AABB);
         case TALL -> Shapes.or(shape, EAST_AABB);
         default -> throw new MatchException(null, null);
      };

      shape = switch ((WallSide)state.getValue(WEST)) {
         case NONE -> shape;
         case LOW -> Shapes.or(shape, WEST_SHORT_AABB);
         case TALL -> Shapes.or(shape, WEST_AABB);
         default -> throw new MatchException(null, null);
      };
      return shape.isEmpty() ? Shapes.block() : shape;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return this.shapesCache.get(state);
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(BASE) ? DOWN_AABB : Shapes.empty();
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState floorState = level.getBlockState(pos.below());
      return state.getValue(BASE) ? !floorState.isAir() : floorState.is(this) && (Boolean)floorState.getValue(BASE);
   }

   private static boolean hasFaces(BlockState state) {
      return state.getValue(BASE) ? true : PROPERTY_BY_DIRECTION.values().stream().anyMatch(property -> state.getValue(property) != WallSide.NONE);
   }

   private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
      BlockPos adjacent = pos.relative(direction);
      BlockState adjacentState = level.getBlockState(adjacent);
      return direction != Direction.UP && MultifaceBlock.canAttachTo(level, direction, adjacent, adjacentState);
   }

   private static BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos, boolean flag) {
      BlockState aboveState = null;
      BlockState belowState = null;
      flag |= state.getValue(BASE);

      for (Direction direction : Plane.HORIZONTAL) {
         EnumProperty<WallSide> property = getPropertyForFace(direction);
         WallSide wallSide = canSupportAtFace(level, pos, direction) ? (flag ? WallSide.LOW : (WallSide)state.getValue(property)) : WallSide.NONE;
         if (wallSide == WallSide.LOW) {
            if (aboveState == null) {
               aboveState = level.getBlockState(pos.above());
            }

            if (aboveState.is(ModBlocks.PALE_MOSS_CARPET.get()) && aboveState.getValue(property) != WallSide.NONE && !(Boolean)aboveState.getValue(BASE)) {
               wallSide = WallSide.TALL;
            }

            if (!(Boolean)state.getValue(BASE)) {
               if (belowState == null) {
                  belowState = level.getBlockState(pos.below());
               }

               if (belowState.is(ModBlocks.PALE_MOSS_CARPET.get()) && belowState.getValue(property) == WallSide.NONE) {
                  wallSide = WallSide.NONE;
               }
            }
         }

         state = (BlockState)state.setValue(property, wallSide);
      }

      return state;
   }

   public static void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flag) {
      BlockState base = ModBlocks.PALE_MOSS_CARPET.get().defaultBlockState();
      BlockState updatedState = getUpdatedState(base, level, pos, true);
      level.setBlock(pos, updatedState, flag);
      BlockState topperState = createTopperWithSideChance(level, pos, random::nextBoolean);
      if (!topperState.isAir()) {
         level.setBlock(pos.above(), topperState, flag);
         BlockState reUpdatedState = getUpdatedState(updatedState, level, pos, true);
         level.setBlock(pos, reUpdatedState, flag);
      }
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      if (!level.isClientSide) {
         RandomSource random = level.getRandom();
         BlockState topperState = createTopperWithSideChance(level, pos, random::nextBoolean);
         if (!topperState.isAir()) {
            level.setBlock(pos.above(), topperState, 3);
         }
      }
   }

   private static BlockState createTopperWithSideChance(BlockGetter level, BlockPos pos, BooleanSupplier flag) {
      BlockPos above = pos.above();
      BlockState aboveState = level.getBlockState(above);
      boolean isCarpet = aboveState.is(ModBlocks.PALE_MOSS_CARPET.get());
      if ((!isCarpet || !(Boolean)aboveState.getValue(BASE)) && (isCarpet || aboveState.canBeReplaced())) {
         BlockState baselessCarpet = (BlockState)ModBlocks.PALE_MOSS_CARPET.get().defaultBlockState().setValue(BASE, false);
         BlockState updatedState = getUpdatedState(baselessCarpet, level, pos.above(), true);

         for (Direction direction : Plane.HORIZONTAL) {
            EnumProperty<WallSide> property = getPropertyForFace(direction);
            if (updatedState.getValue(property) != WallSide.NONE && !flag.getAsBoolean()) {
               updatedState = (BlockState)updatedState.setValue(property, WallSide.NONE);
            }
         }

         return hasFaces(updatedState) && updatedState != aboveState ? updatedState : Blocks.AIR.defaultBlockState();
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return getUpdatedState(this.defaultBlockState(), context.getLevel(), context.getClickedPos(), true);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if (!state.canSurvive(level, pos)) {
         return Blocks.AIR.defaultBlockState();
      }

      BlockState updatedState = getUpdatedState(state, level, pos, false);
      return !hasFaces(updatedState) ? Blocks.AIR.defaultBlockState() : updatedState;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BASE, NORTH, EAST, SOUTH, WEST});
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return switch (rotation) {
         case CLOCKWISE_180 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (WallSide)state.getValue(SOUTH)))
                  .setValue(EAST, (WallSide)state.getValue(WEST)))
               .setValue(SOUTH, (WallSide)state.getValue(NORTH)))
            .setValue(WEST, (WallSide)state.getValue(EAST));
         case COUNTERCLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (WallSide)state.getValue(EAST)))
                  .setValue(EAST, (WallSide)state.getValue(SOUTH)))
               .setValue(SOUTH, (WallSide)state.getValue(WEST)))
            .setValue(WEST, (WallSide)state.getValue(NORTH));
         case CLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (WallSide)state.getValue(WEST)))
                  .setValue(EAST, (WallSide)state.getValue(NORTH)))
               .setValue(SOUTH, (WallSide)state.getValue(EAST)))
            .setValue(WEST, (WallSide)state.getValue(SOUTH));
         default -> state;
      };
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return switch (mirror) {
         case LEFT_RIGHT -> (BlockState)((BlockState)state.setValue(NORTH, (WallSide)state.getValue(SOUTH))).setValue(SOUTH, (WallSide)state.getValue(NORTH));
         case FRONT_BACK -> (BlockState)((BlockState)state.setValue(EAST, (WallSide)state.getValue(WEST))).setValue(WEST, (WallSide)state.getValue(EAST));
         default -> super.mirror(state, mirror);
      };
   }

   @Nullable
   public static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
      return PROPERTY_BY_DIRECTION.get(direction);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return (Boolean)state.getValue(BASE) && !createTopperWithSideChance(level, pos, () -> true).isAir();
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      BlockState topperState = createTopperWithSideChance(level, pos, () -> true);
      if (!topperState.isAir()) {
         level.setBlock(pos.above(), topperState, 3);
      }
   }
}
