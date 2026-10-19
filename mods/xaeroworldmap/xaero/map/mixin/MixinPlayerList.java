package xaero.map.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.server.core.XaeroWorldMapServerCore;

@Mixin(PlayerList.class)
public class MixinPlayerList {
   @Inject(at = @At("HEAD"), method = "sendLevelInfo")
   public void onSendWorldInfo(ServerPlayer player, ServerLevel world, CallbackInfo info) {
      XaeroWorldMapServerCore.onServerWorldInfo(player);
   }
}
