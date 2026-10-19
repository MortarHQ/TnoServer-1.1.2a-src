package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.event.PlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerManagerMixin {
   @Inject(
      method = "respawn(Lnet/minecraft/server/level/ServerPlayer;ZLnet/minecraft/world/entity/Entity$RemovalReason;)Lnet/minecraft/server/level/ServerPlayer;",
      at = @At("HEAD")
   )
   private void onPlayerLoggedOut(ServerPlayer player, boolean alive, RemovalReason removalReason, CallbackInfoReturnable<ServerPlayer> cir) {
      PlayerEvents.LOGGED_OUT.invoker().handleConnection(player);
   }
}
