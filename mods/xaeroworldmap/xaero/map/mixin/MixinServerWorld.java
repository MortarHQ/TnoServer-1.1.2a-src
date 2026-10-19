package xaero.map.mixin;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import xaero.map.capabilities.ServerWorldCapabilities;
import xaero.map.core.IWorldMapServerLevel;

@Mixin(ServerLevel.class)
public class MixinServerWorld implements IWorldMapServerLevel {
   public ServerWorldCapabilities xaero_wm_capabilities;

   @Override
   public ServerWorldCapabilities getXaero_wm_capabilities() {
      return this.xaero_wm_capabilities;
   }

   @Override
   public void setXaero_wm_capabilities(ServerWorldCapabilities capabilities) {
      this.xaero_wm_capabilities = capabilities;
   }
}
