package com.moepus.byepregen.PostProcess;

import it.unimi.dsi.fastutil.shorts.ShortList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

public final class PostProcessGenerationOptimizer {
   private static final Direction[] UP_ONLY = new Direction[]{Direction.UP};
   private static final Direction[] UP_DOWN_ONLY = new Direction[]{Direction.UP, Direction.DOWN};
   private static final ClassValue<Boolean> HAS_CUSTOM_UPDATE_SHAPE = new ClassValue<Boolean>() {
      protected Boolean computeValue(Class<?> type) {
         return PostProcessGenerationOptimizer.hasCustomUpdateShape(type);
      }
   };

   private PostProcessGenerationOptimizer() {
   }

   public static boolean isNoOpPostProcess(BlockState state) {
      Block block = state.getBlock();
      return block.getClass().equals(Block.class)
         ? true
         : block instanceof LightBlock
            || block instanceof LeavesBlock
            || block instanceof BrushableBlock
            || block instanceof SlabBlock
            || block instanceof TrapDoorBlock
            || block instanceof BeehiveBlock
            || block instanceof FallingBlock;
   }

   public static BlockState updateFromNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos) {
      Block block = state.getBlock();
      switch (block) {
         case SnowLayerBlock ignored:
            return updateShapeWithoutNeighbourLookup(state, level, pos, Direction.DOWN);
         case BushBlock ignored:
            return updateShapeWithoutNeighbourLookup(state, level, pos, Direction.DOWN);
         case CarpetBlock ignored:
            return updateShapeWithoutNeighbourLookup(state, level, pos, Direction.DOWN);
         case CactusBlock ignored:
            return updateShapeWithoutNeighbourLookup(state, level, pos, Direction.DOWN);
         case SnowyDirtBlock ignored:
            return updateFromNeighbourShapes(state, level, pos, UP_ONLY);
         case MagmaBlock ignored:
            return updateFromNeighbourShapes(state, level, pos, UP_ONLY);
         case CocoaBlock ignored:
            return updateFromNeighbourShape(state, level, pos, (Direction)state.getValue(CocoaBlock.FACING));
         case CaveVinesBlock ignored:
            return updateFromNeighbourShapes(state, level, pos, UP_DOWN_ONLY);
         case CaveVinesPlantBlock ignored:
            return updateFromNeighbourShapes(state, level, pos, UP_DOWN_ONLY);
         default:
            return !HAS_CUSTOM_UPDATE_SHAPE.get(block.getClass()) ? state : Block.updateFromNeighbourShapes(state, level, pos);
      }
   }

   private static boolean hasCustomUpdateShape(Class<?> type) {
      Class<?> current = type;

      while (current != null && current != Block.class) {
         try {
            current.getDeclaredMethod("updateShape", BlockState.class, Direction.class, BlockState.class, LevelAccessor.class, BlockPos.class, BlockPos.class);
            return true;
         } catch (NoSuchMethodException var3) {
            current = current.getSuperclass();
         } catch (RuntimeException | LinkageError ignored) {
            return true;
         }
      }

      return false;
   }

   private static BlockState updateFromNeighbourShape(BlockState state, LevelAccessor level, BlockPos pos, Direction direction) {
      MutableBlockPos mutablePos = new MutableBlockPos();
      mutablePos.setWithOffset(pos, direction);
      return state.updateShape(direction, level.getBlockState(mutablePos), level, pos, mutablePos);
   }

   private static BlockState updateFromNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, Direction[] directions) {
      BlockState updatedState = state;
      MutableBlockPos mutablePos = new MutableBlockPos();

      for (Direction direction : directions) {
         mutablePos.setWithOffset(pos, direction);
         updatedState = updatedState.updateShape(direction, level.getBlockState(mutablePos), level, pos, mutablePos);
      }

      return updatedState;
   }

   private static BlockState updateShapeWithoutNeighbourLookup(BlockState state, LevelAccessor level, BlockPos pos, Direction direction) {
      return state.updateShape(direction, state, level, pos, pos);
   }

   public static void preprocessPostProcessingLists(LevelChunk chunk, ShortList[] postProcessing) {
      processPostProcessingLists(PostProcessingContext.postProcess(chunk), postProcessing);
   }

   public static void preNormalizeAndFilterChunkLocalPostProcessingLists(ChunkAccess chunk, ShortList[] postProcessing) {
      processPostProcessingLists(PostProcessingContext.preNorm(chunk), postProcessing);
   }

   private static void processPostProcessingLists(PostProcessingContext context, ShortList[] postProcessing) {
      context.preProcess(postProcessing);

      for (int sectionIndex = 0; sectionIndex < postProcessing.length; sectionIndex++) {
         ShortList list = postProcessing[sectionIndex];
         if (list != null && !list.isEmpty()) {
            context.processList(sectionIndex, list);
         }
      }
   }
}
