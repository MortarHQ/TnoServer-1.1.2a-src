package com.tno_tensura_extras.mixin;

import com.tno_tensura_extras.util.AlignmentFieldManager;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerAlignmentFieldMixin {
   @Shadow
   public ServerPlayer player;

   @Inject(
      method = "handleMovePlayer",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/server/level/ServerLevel;)V",
         shift = Shift.AFTER
      )
   )
   private void tno_tensura_extras$clipAlignmentFieldPacket(ServerboundMovePlayerPacket packet, CallbackInfo callbackInfo) {
      if (packet.hasPosition()) {
         Vec3 requestedTarget = new Vec3(packet.getX(this.player.getX()), packet.getY(this.player.getY()), packet.getZ(this.player.getZ()));
         Vec3 clippedTarget = AlignmentFieldManager.clipPlayerTarget(this.player, requestedTarget);
         if (!(clippedTarget.distanceToSqr(requestedTarget) < 1.0E-10)) {
            ServerboundMovePlayerPacketAccessor accessor = (ServerboundMovePlayerPacketAccessor)packet;
            accessor.tno_tensura_extras$setX(clippedTarget.x);
            accessor.tno_tensura_extras$setY(clippedTarget.y);
            accessor.tno_tensura_extras$setZ(clippedTarget.z);
         }
      }
   }
}
