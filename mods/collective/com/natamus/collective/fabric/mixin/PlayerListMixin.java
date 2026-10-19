package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import net.minecraft.class_2535;
import net.minecraft.class_3222;
import net.minecraft.class_3324;
import net.minecraft.class_8792;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_3324.class, priority = 1001)
public class PlayerListMixin {
   @Inject(method = "placeNewPlayer", at = @At("TAIL"))
   public void PlayerList_placeNewPlayer(class_2535 connection, class_3222 serverPlayer, class_8792 commonListenerCookie, CallbackInfo ci) {
      ((CollectivePlayerEvents.Player_Logged_In)CollectivePlayerEvents.PLAYER_LOGGED_IN.invoker()).onPlayerLoggedIn(serverPlayer.method_37908(), serverPlayer);
   }

   @Inject(method = "remove", at = @At("HEAD"))
   public void PlayerList_remove(class_3222 player, CallbackInfo ci) {
      ((CollectivePlayerEvents.Player_Logged_Out)CollectivePlayerEvents.PLAYER_LOGGED_OUT.invoker()).onPlayerLoggedOut(player.method_37908(), player);
   }
}
