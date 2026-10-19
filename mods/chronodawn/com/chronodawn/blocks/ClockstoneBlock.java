package com.chronodawn.blocks;

import com.chronodawn.ChronoDawn;
import com.chronodawn.registry.ModBlocks;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class ClockstoneBlock extends Block {
   public ClockstoneBlock(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel(state -> 9);
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!state.is(newState.getBlock())) {
         this.destroyConnectedPortal(level, pos);
      }

      super.onRemove(state, level, pos, newState, isMoving);
   }

   private void destroyConnectedPortal(Level level, BlockPos framePos) {
      Set<BlockPos> visited = new HashSet<>();
      Queue<BlockPos> queue = new LinkedList<>();

      for (Direction direction : Direction.values()) {
         BlockPos adjacent = framePos.relative(direction);
         if (level.getBlockState(adjacent).is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
            queue.add(adjacent);
            visited.add(adjacent);
         }
      }

      while (!queue.isEmpty()) {
         BlockPos current = queue.poll();
         level.removeBlock(current, false);

         for (Direction direction : Direction.values()) {
            BlockPos adjacent = current.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacent);
            if (adjacentState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get()) && !visited.contains(adjacent)) {
               queue.add(adjacent);
               visited.add(adjacent);
            }
         }
      }

      if (!visited.isEmpty()) {
         ChronoDawn.LOGGER.debug("Destroyed {} connected portal blocks due to frame break at {}", visited.size(), framePos);
      }
   }
}
