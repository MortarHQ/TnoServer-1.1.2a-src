package twilightforest.util;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;

public class BlockCapabilityDirectionalCache<R> {
   private final Map<BlockCapabilityDirectionalCache.BlockPosAndDirection, BlockCapabilityCache<R, Direction>> data = new HashMap<>();

   @Nullable
   public R get(BlockCapability<R, Direction> capability, ServerLevel level, BlockPos pos, Direction direction) {
      BlockCapabilityCache<R, Direction> cache = this.data.get(new BlockCapabilityDirectionalCache.BlockPosAndDirection(pos, direction));
      if (cache == null) {
         cache = BlockCapabilityCache.create(capability, level, pos, direction);
         this.data.put(new BlockCapabilityDirectionalCache.BlockPosAndDirection(pos, direction), cache);
      }

      return (R)cache.getCapability();
   }

   private record BlockPosAndDirection(BlockPos pos, Direction direction) {
   }
}
