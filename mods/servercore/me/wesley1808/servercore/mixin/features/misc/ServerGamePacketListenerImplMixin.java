package me.wesley1808.servercore.mixin.features.misc;

import java.util.Collections;
import java.util.Set;
import me.wesley1808.servercore.common.config.Config;
import me.wesley1808.servercore.common.utils.ChunkManager;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin extends ServerCommonPacketListenerImpl {
   @Shadow
   public ServerPlayer player;

   public ServerGamePacketListenerImplMixin(MinecraftServer minecraftServer, Connection connection, CommonListenerCookie cookie) {
      super(minecraftServer, connection, cookie);
   }

   @Shadow
   public abstract void teleport(double var1, double var3, double var5, float var7, float var8, Set<RelativeMovement> var9);

   @Inject(
      method = "handleMoveVehicle(Lnet/minecraft/network/protocol/game/ServerboundMoveVehiclePacket;)V",
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;lengthSqr()D", shift = Shift.AFTER, ordinal = 0)
   )
   private void servercore$handleMoveVehicle(
      ServerboundMoveVehiclePacket packet,
      CallbackInfo ci,
      Entity entity,
      ServerLevel serverLevel,
      double fromX,
      double fromY,
      double fromZ,
      double toX,
      double toY,
      double toZ,
      float yRot,
      float xRot,
      double l,
      double m,
      double n
   ) {
      if (this.servercore$shouldPreventMovement(serverLevel, entity, fromX, fromZ, toX, toY, toZ)) {
         this.connection.send(new ClientboundMoveVehiclePacket(entity));
         ci.cancel();
      }
   }

   @Inject(
      method = "handleMovePlayer(Lnet/minecraft/network/protocol/game/ServerboundMovePlayerPacket;)V",
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getBoundingBox()Lnet/minecraft/world/phys/AABB;", ordinal = 0)
   )
   private void servercore$handleMovePlayer(
      ServerboundMovePlayerPacket packet,
      CallbackInfo ci,
      ServerLevel serverLevel,
      double toX,
      double toY,
      double toZ,
      float yRot,
      float xRot,
      double fromX,
      double fromY,
      double fromZ
   ) {
      if (this.servercore$shouldPreventMovement(serverLevel, this.player, fromX, fromZ, toX, toY, toZ)) {
         this.teleport(fromX, fromY, fromZ, yRot, xRot, Collections.emptySet());
         ci.cancel();
      }
   }

   @Unique
   private boolean servercore$shouldPreventMovement(ServerLevel level, Entity entity, double fromX, double fromZ, double toX, double toY, double toZ) {
      return Config.get().features().preventMovingIntoUnloadedChunks()
         && (fromX != toX || fromZ != toZ)
         && !ChunkManager.areChunksLoadedForMove(level, entity.getBoundingBox().expandTowards(new Vec3(toX, toY, toZ).subtract(entity.position())));
   }
}
