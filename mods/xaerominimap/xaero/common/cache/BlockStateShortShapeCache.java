package xaero.common.cache;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xaero.common.IXaeroMinimap;
import xaero.common.cache.placeholder.PlaceholderBlockGetter;
import xaero.hud.minimap.MinimapLogs;

public class BlockStateShortShapeCache {
   private IXaeroMinimap modMain;
   private Map<BlockState, Boolean> shortBlockStates;
   private BlockState lastShortChecked = null;
   private boolean lastShortCheckedResult = false;
   private PlaceholderBlockGetter placeholderBlockGetter;

   public BlockStateShortShapeCache(IXaeroMinimap modMain) {
      this.modMain = modMain;
      this.shortBlockStates = new HashMap<>();
      this.placeholderBlockGetter = new PlaceholderBlockGetter();
   }

   public boolean isShort(BlockState state) {
      if (state != null && !(state.getBlock() instanceof AirBlock) && !(state.getBlock() instanceof LiquidBlock)) {
         Boolean cached;
         synchronized (this.shortBlockStates) {
            if (state == this.lastShortChecked) {
               return this.lastShortCheckedResult;
            }

            cached = this.shortBlockStates.get(state);
         }

         if (cached == null) {
            if (!Minecraft.getInstance().isSameThread()) {
               return (Boolean)Minecraft.getInstance().submit(() -> this.isShort(state)).join();
            }

            try {
               this.placeholderBlockGetter.setPlaceholderState(state);
               VoxelShape shape = state.getShape(this.placeholderBlockGetter, BlockPos.ZERO);
               cached = shape.max(Axis.Y) < 0.25;
            } catch (Throwable t) {
               MinimapLogs.LOGGER.info("Defaulting world-dependent block state shape to not short: " + state);
               cached = false;
            }

            synchronized (this.shortBlockStates) {
               this.shortBlockStates.put(state, cached);
               this.lastShortChecked = state;
               this.lastShortCheckedResult = cached;
            }
         }

         return cached;
      } else {
         return false;
      }
   }

   public void reset() {
      synchronized (this.shortBlockStates) {
         this.shortBlockStates.clear();
         this.lastShortChecked = null;
         this.lastShortCheckedResult = false;
      }
   }
}
