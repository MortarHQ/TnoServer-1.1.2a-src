package cn.star.toms_storage_star_optimized;

import cn.star.toms_storage_star_optimized.opt.NetworkDirtyEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod("toms_storage_star_optimized")
public final class TomsStorageStarOptimized {
   public static final String MODID = "toms_storage_star_optimized";

   public TomsStorageStarOptimized(IEventBus modEventBus) {
      NeoForge.EVENT_BUS.register(new NetworkDirtyEvents());
   }
}
