package net.unusual.block_factorys_bosses.mixins.server;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(ServerBossEvent.class)
public abstract class ServerBossEventMixin extends BossEvent {
   public ServerBossEventMixin(UUID id, Component name, BossBarColor color, BossBarOverlay overlay) {
      super(id, name, color, overlay);
   }

   @Inject(
      method = "addPlayer",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V")
   )
   private void onAddPlayer(ServerPlayer player, CallbackInfo ci) {
      BossHandling.TrackedBoss boss = BossHandling.TRACKED_BOSSES.getOrDefault(this.getId(), null);
      if (boss != null) {
         player.connection.send(new UpdateBossBarTypeMessage(this.getId(), boss));
      }
   }
}
