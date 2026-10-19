package xaero.map.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import xaero.map.server.player.IServerPlayer;
import xaero.map.server.player.ServerPlayerData;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer implements IServerPlayer {
   private ServerPlayerData xaeroWorldMapPlayerData;

   @Override
   public ServerPlayerData getXaeroWorldMapPlayerData() {
      return this.xaeroWorldMapPlayerData;
   }

   @Override
   public void setXaeroWorldMapPlayerData(ServerPlayerData data) {
      this.xaeroWorldMapPlayerData = data;
   }
}
