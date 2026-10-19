package cn.star.toms_storage_star_optimized.opt;

import com.tom.storagemod.StorageTags;
import com.tom.storagemod.block.IInventoryCable;
import com.tom.storagemod.block.IInventoryNode;
import com.tom.storagemod.inventory.PlatformInventoryAccess.BlockInventoryAccess;
import com.tom.storagemod.util.InventoryConnectorConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;

public final class NetworkDirtyEvents {
   private boolean isNetworkRelevant(Level level, BlockPos pos, BlockState state) {
      Block block = state.getBlock();
      return block instanceof IInventoryCable || block instanceof IInventoryNode || state.is(StorageTags.TRIMS) || this.hasInventoryAccess(level, pos, state);
   }

   private boolean hasInventoryAccess(Level level, BlockPos pos, BlockState state) {
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

   @SubscribeEvent
   public void onBlockPlaced(EntityPlaceEvent event) {
      if (event.getLevel() instanceof Level level) {
         if (this.isNetworkRelevant(level, event.getPos(), event.getPlacedBlock())) {
            NetworkDirtyPropagation.markConnectedNetworksDirty(level, event.getPos(), false);
         }
      }
   }

   @SubscribeEvent
   public void onBlockBroken(BreakEvent event) {
      Level level = event.getPlayer().level();
      if (this.isNetworkRelevant(level, event.getPos(), event.getState())) {
         NetworkDirtyPropagation.markConnectedNetworksDirty(level, event.getPos(), true);
      }
   }
}
