package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

@ParametersAreNonnullByDefault
public class IceSheetBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
   public static final MapCodec<IceSheetBlock> CODEC = simpleCodec(IceSheetBlock::new);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty GLOWING = BlockStateProperties.LIT;
   public static final IntegerProperty GROWTH_DISTANCE = BlockStateProperties.AGE_3;
   private final MultifaceSpreader spreader = new MultifaceSpreader(this);

   public IceSheetBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(GLOWING, false)).setValue(WATERLOGGED, false)).setValue(GROWTH_DISTANCE, 0)
      );
   }

   protected MapCodec<? extends MultifaceBlock> codec() {
      return CODEC;
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      if (random.nextFloat() < 0.25F && serverLevel.getBrightness(LightLayer.BLOCK, blockPos) > 12 - blockState.getLightBlock(serverLevel, blockPos)) {
         serverLevel.removeBlock(blockPos, false);
      }

      if (random.nextFloat() < 0.05F) {
         this.spreadFromRandomFaceWithinBlock(blockState, serverLevel, blockPos, random);
         if ((Integer)blockState.getValue(GROWTH_DISTANCE) > 0) {
            this.spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, random);
         }
      }
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
      if (level.getBrightness(LightLayer.BLOCK, blockPos) > 12 - blockState.getLightBlock(level, blockPos) && random.nextFloat() < 0.2F) {
      }
   }

   public BlockState updateShape(
      BlockState thisState, Direction directionToNeighbor, BlockState neighborState, LevelAccessor levelAccessor, BlockPos thisPos, BlockPos neighborPos
   ) {
      if ((Boolean)thisState.getValue(WATERLOGGED)) {
         levelAccessor.scheduleTick(thisPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
      }

      return super.updateShape(thisState, directionToNeighbor, neighborState, levelAccessor, thisPos, neighborPos);
   }

   public BlockState getStateForPlacement(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction neighborDirection) {
      if (!this.isFaceSupported(neighborDirection)) {
         return null;
      }

      BlockPos neighborPos = blockPos.relative(neighborDirection);
      BlockState updatedState;
      if (blockState.is(this)) {
         updatedState = blockState;
      } else {
         updatedState = blockState.getFluidState().isSourceOfType(Fluids.WATER)
            ? (BlockState)this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)
            : this.defaultBlockState();
      }

      if (canAttachTo(blockGetter, neighborDirection, neighborPos, blockGetter.getBlockState(neighborPos))) {
         updatedState = (BlockState)updatedState.setValue(getFaceProperty(neighborDirection), true);
         BlockState adjacentBlock = blockGetter.getBlockState(neighborPos);
         if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
            updatedState = (BlockState)updatedState.setValue(GLOWING, true);
         }
      } else {
         updatedState = null;
      }

      return updatedState;
   }

   @NotNull
   public MultifaceSpreader getSpreader() {
      return this.spreader;
   }

   public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block neighborBlock, BlockPos neighborPos, boolean bl) {
      if (!level.isClientSide) {
         for (Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
            Direction direction = entry.getKey();
            BooleanProperty property = entry.getValue();
            if ((Boolean)blockState.getValue(property)) {
               BlockPos adjacentPos = blockPos.relative(direction);
               BlockState adjacentBlock = level.getBlockState(adjacentPos);
               if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
                  if (!(Boolean)blockState.getValue(GLOWING)) {
                     level.setBlock(blockPos, (BlockState)blockState.setValue(GLOWING, true), 2);
                  }

                  return;
               }
            }
         }

         if ((Boolean)blockState.getValue(GLOWING)) {
            level.setBlock(blockPos, (BlockState)blockState.setValue(GLOWING, false), 2);
         }
      }
   }

   public FluidState getFluidState(BlockState blockState) {
      return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
   }

   protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{GLOWING, WATERLOGGED, GROWTH_DISTANCE});
   }

   public boolean spreadFromRandomFaceWithinBlock(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      List<Direction> possibleSourceDirections = Lists.newArrayList(DIRECTIONS)
         .stream()
         .filter(direction -> hasFace(blockState, direction))
         .collect(Collectors.toList());
      Collections.shuffle(possibleSourceDirections);

      for (Direction possibleSourceDirection : possibleSourceDirections) {
         List<Direction> possibleTargetDirections = Lists.newArrayList(DIRECTIONS)
            .stream()
            .filter(direction -> direction.getAxis() != possibleSourceDirection.getAxis() && !(Boolean)blockState.getValue(getFaceProperty(direction)))
            .collect(Collectors.toList());
         Collections.shuffle(possibleTargetDirections);

         for (Direction targetDirection : possibleTargetDirections) {
            BlockPos neighborPos = blockPos.relative(targetDirection);
            if (canAttachTo(serverLevel, targetDirection, neighborPos, serverLevel.getBlockState(neighborPos))) {
               BlockState updatedState = (BlockState)blockState.setValue(getFaceProperty(targetDirection), true);
               BlockState adjacentBlock = serverLevel.getBlockState(neighborPos);
               if (adjacentBlock.is(BlockModule.CREEPING_ICE_GLOWS_ON)) {
                  updatedState = (BlockState)updatedState.setValue(GLOWING, true);
               }

               serverLevel.setBlock(blockPos, updatedState, 2);
               return true;
            }
         }
      }

      return false;
   }

   public boolean spreadFromRandomFaceTowardRandomDirection(
      BlockState spreadOriginBlockState, ServerLevel serverLevel, BlockPos spreadOriginBlockPos, RandomSource random
   ) {
      List<Direction> directions = Lists.newArrayList(DIRECTIONS);
      Collections.shuffle(directions);
      return directions.stream()
         .filter(direction -> hasFace(spreadOriginBlockState, direction))
         .anyMatch(direction -> this.spreadFromFaceTowardRandomDirection(spreadOriginBlockState, serverLevel, spreadOriginBlockPos, direction, random, false));
   }

   public boolean spreadFromFaceTowardRandomDirection(
      BlockState spreadOriginBlockState, LevelAccessor levelAccessor, BlockPos spreadOriginBlockPos, Direction faceDirection, RandomSource random, boolean bl
   ) {
      List<Direction> list = Arrays.asList(DIRECTIONS);
      Collections.shuffle(list, new Random(random.nextLong()));
      return list.stream()
         .anyMatch(
            targetDirection -> this.spreadFromFaceTowardDirection(
               spreadOriginBlockState, levelAccessor, spreadOriginBlockPos, faceDirection, targetDirection, bl
            )
         );
   }

   public boolean spreadFromFaceTowardDirection(
      BlockState spreadOriginBlockState,
      LevelAccessor levelAccessor,
      BlockPos spreadOriginBlockPos,
      Direction faceDirection,
      Direction targetDirection,
      boolean bl
   ) {
      Optional<Pair<BlockPos, Direction>> optional = this.getSpreadFromFaceTowardDirection(
         spreadOriginBlockState, levelAccessor, spreadOriginBlockPos, faceDirection, targetDirection
      );
      if (optional.isPresent()) {
         Pair<BlockPos, Direction> pair = optional.get();
         return this.spreadToFace(levelAccessor, (BlockPos)pair.getFirst(), (Direction)pair.getSecond(), bl, spreadOriginBlockState);
      } else {
         return false;
      }
   }

   private Optional<Pair<BlockPos, Direction>> getSpreadFromFaceTowardDirection(
      BlockState spreadOriginBlockState, LevelAccessor levelAccessor, BlockPos spreadOriginBlockPos, Direction faceDirection, Direction targetDirection
   ) {
      if (targetDirection.getAxis() == faceDirection.getAxis()
         || !hasFace(spreadOriginBlockState, faceDirection)
         || hasFace(spreadOriginBlockState, targetDirection)) {
         return Optional.empty();
      }

      if (this.canSpreadToFace(levelAccessor, spreadOriginBlockPos, targetDirection, spreadOriginBlockState)) {
         return Optional.of(Pair.of(spreadOriginBlockPos, targetDirection));
      }

      BlockPos blockPos2 = spreadOriginBlockPos.relative(targetDirection);
      if (this.canSpreadToFace(levelAccessor, blockPos2, faceDirection, spreadOriginBlockState)) {
         return Optional.of(Pair.of(blockPos2, faceDirection));
      }

      BlockPos blockPos3 = blockPos2.relative(faceDirection);
      Direction direction3 = targetDirection.getOpposite();
      return this.canSpreadToFace(levelAccessor, blockPos3, direction3, spreadOriginBlockState)
         ? Optional.of(Pair.of(blockPos3, direction3))
         : Optional.empty();
   }

   private boolean canSpreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, BlockState spreadOriginBlockState) {
      BlockState blockState = levelAccessor.getBlockState(blockPos);
      if (!this.canSpreadInto(levelAccessor, blockState, blockPos, spreadOriginBlockState)) {
         return false;
      }

      BlockState blockState2 = this.getStateForPlacement(blockState, levelAccessor, blockPos, direction);
      return blockState2 != null;
   }

   private boolean spreadToFace(LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, boolean bl, BlockState spreadOriginBlockState) {
      if (!spreadOriginBlockState.hasProperty(GROWTH_DISTANCE)) {
         return false;
      }

      BlockState oldBlockState = levelAccessor.getBlockState(blockPos);
      BlockState newBlockState = this.getStateForPlacement(oldBlockState, levelAccessor, blockPos, direction);
      if (newBlockState != null) {
         newBlockState = (BlockState)newBlockState.setValue(GROWTH_DISTANCE, (Integer)spreadOriginBlockState.getValue(GROWTH_DISTANCE) - 1);
         if (bl) {
            levelAccessor.getChunk(blockPos).markPosForPostprocessing(blockPos);
         }

         return levelAccessor.setBlock(blockPos, newBlockState, 2);
      } else {
         return false;
      }
   }

   private boolean canSpreadInto(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos, BlockState spreadOriginBlockState) {
      return (
            blockState.isAir()
               || blockState.is(this) && (Integer)blockState.getValue(GROWTH_DISTANCE) < (Integer)spreadOriginBlockState.getValue(GROWTH_DISTANCE)
               || blockState.is(Blocks.WATER) && blockState.getFluidState().isSource()
         )
         && levelAccessor.getBrightness(LightLayer.BLOCK, blockPos) <= 12 - blockState.getLightBlock(levelAccessor, blockPos);
   }
}
