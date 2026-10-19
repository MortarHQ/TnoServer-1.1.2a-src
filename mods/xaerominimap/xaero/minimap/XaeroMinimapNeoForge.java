package xaero.minimap;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import xaero.common.PlatformContext;
import xaero.common.PlatformContextNeoForge;

@Mod("xaerominimap")
public class XaeroMinimapNeoForge extends XaeroMinimap {
   public static String fileLayoutID = "minimap_neoforge";
   private final PlatformContextNeoForge platformContextNeoForge = (PlatformContextNeoForge)this.platformContext;

   public XaeroMinimapNeoForge(IEventBus modEventBus) {
      this.platformContextNeoForge.registerEvents(modEventBus);
   }

   @Override
   protected PlatformContext createPlatformContext() {
      return new PlatformContextNeoForge(this);
   }

   @Override
   public String getFileLayoutID() {
      return fileLayoutID;
   }
}
