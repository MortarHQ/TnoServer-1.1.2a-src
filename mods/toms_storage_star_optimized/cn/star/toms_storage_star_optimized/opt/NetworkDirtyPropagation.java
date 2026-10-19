package cn.star.toms_storage_star_optimized.opt;

import com.tom.storagemod.Config;
import com.tom.storagemod.StorageTags;
import com.tom.storagemod.block.IInventoryCable;
import com.tom.storagemod.block.IInventoryNode;
import com.tom.storagemod.inventory.PlatformInventoryAccess.BlockInventoryAccess;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.InventoryConnectorConfigUtil;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.ArrayDeque;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class NetworkDirtyPropagation {
   private NetworkDirtyPropagation() {
   }

   private static boolean hasInventoryAccess(Level level, BlockPos pos, BlockState state) {
      if (!InventoryConnectorConfigUtil.canConnect(state)) {
         return false;
      }

      for (Direction direction : Direction.values()) {
         if (BlockInventoryAccess.hasInventoryAt(level, pos, state, direction)) {
            return true;
         }
      }

      return false;
   }

   public static void markConnectedNetworksDirty(Level level, BlockPos start, boolean requireFullRebuild) {
      if (level != null && !level.isClientSide) {
         int range = Config.get().invConnectorScanRange;
         int rangeSq = range * range;
         ArrayDeque<BlockPos> queue = new ArrayDeque<>();
         LongOpenHashSet visited = new LongOpenHashSet();
         queue.add(start);
         visited.add(start.asLong());

         for (Direction direction : Direction.values()) {
            BlockPos next = start.relative(direction);
            queue.add(next);
            visited.add(next.asLong());
         }

         while (!queue.isEmpty()) {
            BlockPos current = queue.removeFirst();
            if (!(current.distSqr(start) >= rangeSq)) {
               if (level.getBlockEntity(current) instanceof INetworkDirtyReceiver receiver) {
                  receiver.tsso$markDirtyAt(start, requireFullRebuild);
               }

               BlockState state = level.getBlockState(current);
               if (state.getBlock() instanceof IInventoryCable cable) {
                  for (BlockFace next : cable.nextScan(level, state, current)) {
                     BlockPos nextPos = next.pos();
                     if (visited.add(nextPos.asLong())) {
                        queue.addLast(nextPos);
                     }
                  }
               } else if (!state.is(StorageTags.TRIMS) && !(state.getBlock() instanceof IInventoryNode) && !current.equals(start)) {
                  if (hasInventoryAccess(level, current, state)) {
                     for (Direction direction : Direction.values()) {
                        BlockPos next = current.relative(direction);
                        if (visited.add(next.asLong())) {
                           queue.addLast(next);
                        }
                     }
                  }
               } else {
                  for (Direction direction : Direction.values()) {
                     BlockPos next = current.relative(direction);
                     if (visited.add(next.asLong())) {
                        queue.addLast(next);
                     }
                  }
               }
            }
         }
      }
   }
}
