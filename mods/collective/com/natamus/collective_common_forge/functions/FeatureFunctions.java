package com.natamus.collective_common_forge.functions;

import com.natamus.collective_common_forge.data.GlobalVariables;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.stream.IntStream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class FeatureFunctions {
   public static boolean placeBonusChest(Level level, BlockPos blockposIn) {
      ChunkPos chunkPos = new ChunkPos(blockposIn);
      IntArrayList list0 = Util.toShuffledList(IntStream.rangeClosed(chunkPos.getMinBlockX(), chunkPos.getMaxBlockX()), GlobalVariables.randomSource);
      IntArrayList list1 = Util.toShuffledList(IntStream.rangeClosed(chunkPos.getMinBlockZ(), chunkPos.getMaxBlockZ()), GlobalVariables.randomSource);
      MutableBlockPos mutableBlockPos = new MutableBlockPos();
      IntListIterator var6 = list0.iterator();

      while (var6.hasNext()) {
         Integer i = (Integer)var6.next();
         IntListIterator var8 = list1.iterator();

         while (var8.hasNext()) {
            Integer j = (Integer)var8.next();
            mutableBlockPos.set(i, 0, j);
            BlockPos heightMapPos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, mutableBlockPos);
            if (level.isEmptyBlock(heightMapPos) || level.getBlockState(heightMapPos).getCollisionShape(level, heightMapPos).isEmpty()) {
               level.setBlock(heightMapPos, Blocks.CHEST.defaultBlockState(), 2);
               RandomizableContainer.setBlockEntityLootTable(level, GlobalVariables.randomSource, heightMapPos, BuiltInLootTables.SPAWN_BONUS_CHEST);
               BlockState blockState = Blocks.TORCH.defaultBlockState();

               for (Direction direction : Plane.HORIZONTAL) {
                  BlockPos blockPos = heightMapPos.relative(direction);
                  if (blockState.canSurvive(level, blockPos)) {
                     level.setBlock(blockPos, blockState, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
