package xaero.map.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import xaero.map.server.IMinecraftServer;
import xaero.map.server.MinecraftServerData;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer implements IMinecraftServer {
   private MinecraftServerData xaeroWorldMapServerData;

   @Override
   public MinecraftServerData getXaeroWorldMapServerData() {
      return this.xaeroWorldMapServerData;
   }

   @Override
   public void setXaeroWorldMapServerData(MinecraftServerData data) {
      this.xaeroWorldMapServerData = data;
   }
}
