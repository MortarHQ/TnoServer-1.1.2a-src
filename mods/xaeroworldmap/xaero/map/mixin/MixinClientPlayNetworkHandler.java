package xaero.map.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.WorldMapSession;
import xaero.map.core.IWorldMapClientPlayNetHandler;
import xaero.map.core.XaeroWorldMapCore;

@Mixin(ClientPacketListener.class)
public class MixinClientPlayNetworkHandler implements IWorldMapClientPlayNetHandler {
   WorldMapSession xaero_worldmapSession;

   @Inject(
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"
      ),
      method = "handleChunkBlocksUpdate"
   )
   public void onOnChunkDeltaUpdate(ClientboundSectionBlocksUpdatePacket packet, CallbackInfo info) {
      XaeroWorldMapCore.onMultiBlockChange(packet);
   }

   @Inject(at = @At("HEAD"), method = "updateLevelChunk")
   public void onOnChunkData(int x, int z, ClientboundLevelChunkPacketData packet, CallbackInfo info) {
      XaeroWorldMapCore.onChunkData(x, z, packet);
   }

   @Inject(
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"
      ),
      method = "handleLevelChunkWithLight"
   )
   public void onHandleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket packet, CallbackInfo info) {
      XaeroWorldMapCore.onHandleLevelChunkWithLight(packet);
   }

   @Inject(
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"
      ),
      method = "handleLightUpdatePacket"
   )
   public void onHandleLightUpdatePacket(ClientboundLightUpdatePacket packet, CallbackInfo info) {
      XaeroWorldMapCore.onHandleLightUpdatePacket(packet);
   }

   @Inject(at = @At("HEAD"), method = "queueLightRemoval")
   public void onQueueLightRemoval(ClientboundForgetLevelChunkPacket packet, CallbackInfo info) {
      XaeroWorldMapCore.onQueueLightRemoval(packet);
   }

   @Inject(
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"
      ),
      method = "handleBlockUpdate"
   )
   public void onOnBlockUpdate(ClientboundBlockUpdatePacket packet, CallbackInfo info) {
      XaeroWorldMapCore.onBlockChange(packet);
   }

   @Inject(
      at = @At(
         value = "INVOKE",
         shift = Shift.AFTER,
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"
      ),
      method = "handleSetSpawn"
   )
   public void onOnPlayerSpawnPosition(ClientboundSetDefaultSpawnPositionPacket packet, CallbackInfo info) {
      XaeroWorldMapCore.handlePlayerSetSpawnPacket(packet);
   }

   @Override
   public WorldMapSession getXaero_worldmapSession() {
      return this.xaero_worldmapSession;
   }

   @Override
   public void setXaero_worldmapSession(WorldMapSession session) {
      this.xaero_worldmapSession = session;
   }

   @Inject(at = @At("HEAD"), method = "close")
   public void onCleanup(CallbackInfo info) {
      XaeroWorldMapCore.onPlayNetHandlerCleanup((ClientPacketListener)this);
   }
}
