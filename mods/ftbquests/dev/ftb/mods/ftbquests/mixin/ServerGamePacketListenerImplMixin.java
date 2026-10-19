package dev.ftb.mods.ftbquests.mixin;

import dev.ftb.mods.ftbquests.quest.translation.TranslationManager;
import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
   @Shadow
   public ServerPlayer player;

   @Inject(
      method = "handleClientInformation(Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/server/level/ServerLevel;)V",
         shift = Shift.AFTER
      )
   )
   public void handleClientInformation(ServerboundClientInformationPacket packet, CallbackInfo ci) {
      if (!this.player.clientInformation().language().equals(packet.information().language())) {
         TranslationManager.syncTable(this.player, packet.information().language());
      }
   }
}
