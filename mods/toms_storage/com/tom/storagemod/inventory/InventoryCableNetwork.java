package com.tom.storagemod.inventory;

import com.tom.storagemod.block.IInventoryCable;
import com.tom.storagemod.util.BlockFace;
import com.tom.storagemod.util.WorldStates;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class InventoryCableNetwork {
   private final Level level;
   private Map<BlockPos, InventoryCableNetwork.CableCache> caches = new HashMap<>();

   public InventoryCableNetwork(Level level) {
      this.level = level;
   }

   public Collection<BlockPos> getNetworkNodes(BlockPos from) {
      InventoryCableNetwork.CableCache cache = this.caches.get(from);
      if (cache != null) {
         return cache.attached;
      }

      Set<BlockPos> checked = new HashSet<>();
      Stack<BlockFace> next = new Stack<>();
      Set<BlockPos> cables = new HashSet<>();
      Set<BlockPos> attached = new HashSet<>();
      next.add(new BlockFace(from, null));

      while (!next.isEmpty()) {
         BlockFace p = next.pop();
         if (!checked.contains(p.pos())) {
            checked.add(p.pos());
            if (this.level.isLoaded(p.pos())) {
               BlockState st = this.level.getBlockState(p.pos());
               if (st.getBlock() instanceof IInventoryCable c && (p.from() == null || c.canConnectFrom(st, p.from()))) {
                  InventoryCableNetwork.CableCache cc = this.caches.get(p.pos());
                  if (cc != null) {
                     cables.addAll(cc.cables);
                     attached.addAll(cc.attached);
                     checked.addAll(cc.cables);
                  } else {
                     if (c.isFunctionalNode()) {
                        attached.add(p.pos());
                     } else {
                        cables.add(p.pos());
                     }

                     next.addAll(c.nextScan(this.level, st, p.pos()));
                  }
               }
            }
         }
      }

      InventoryCableNetwork.CableCache cc = new InventoryCableNetwork.CableCache(cables, attached);
      cables.forEach(p -> this.caches.put(p, cc));
      return cc.attached;
   }

   public void markNodeInvalid(BlockPos pos) {
      InventoryCableNetwork.CableCache cc = this.caches.get(pos);
      if (cc != null) {
         cc.cables.forEach(this.caches::remove);
      }
   }

   public void markNodeInvalid(BlockFace pos) {
      this.markNodeInvalid(pos.pos());
   }

   public static InventoryCableNetwork getNetwork(Level level) {
      return WorldStates.cableNetworks.computeIfAbsent(level, InventoryCableNetwork::new);
   }

   private record CableCache(Set<BlockPos> cables, Set<BlockPos> attached) {
   }
}
