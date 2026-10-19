package xaero.map.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xaero.map.WorldMap;
import xaero.map.cache.placeholder.PlaceholderBlockGetter;

public class BlockStateShortShapeCache {
   private Map<BlockState, Boolean> shortBlockStates;
   private BlockState lastShortChecked = null;
   private boolean lastShortCheckedResult = false;
   private PlaceholderBlockGetter placeholderBlockGetter;
   private CompletableFuture<Boolean> ioThreadWaitingFor;
   private Supplier<Boolean> ioThreadWaitingForSupplier;

   public BlockStateShortShapeCache() {
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
               Supplier<Boolean> supplier = () -> this.isShort(state);
               CompletableFuture<Boolean> future = Minecraft.getInstance().submit(supplier);
               boolean isIOThread = Thread.currentThread() == WorldMap.mapRunnerThread;
               if (isIOThread) {
                  this.ioThreadWaitingForSupplier = supplier;
                  this.ioThreadWaitingFor = future;
               }

               Boolean result = future.join();
               if (isIOThread) {
                  this.ioThreadWaitingForSupplier = null;
                  this.ioThreadWaitingFor = null;
               }

               return result;
            }

            try {
               this.placeholderBlockGetter.setPlaceholderState(state);
               VoxelShape shape = state.getShape(this.placeholderBlockGetter, BlockPos.ZERO);
               cached = shape.max(Axis.Y) < 0.25;
            } catch (Throwable t) {
               WorldMap.LOGGER.info("Defaulting world-dependent block state shape to not short: " + state);
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

   public void supplyForIOThread() {
      if (!Minecraft.getInstance().isSameThread()) {
         throw new RuntimeException(new IllegalAccessException("Supplying from the wrong thread!"));
      }

      CompletableFuture<Boolean> waitingFor = this.ioThreadWaitingFor;
      if (waitingFor != null && !waitingFor.isDone()) {
         waitingFor.complete(this.ioThreadWaitingForSupplier.get());
      }
   }
}
