package com.tensura_tno.mixin.client;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "io.github.Memoires.mysticism.ability.skill.unique.CaptivatorSkill", remap = false)
public class CaptivatorSkillNetworkFixMixin {
   @Redirect(
      method = "switchSkillMode",
      at = @At(
         value = "INVOKE",
         target = "Ldev/architectury/networking/NetworkManager;sendToPlayer(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V",
         remap = false
      ),
      remap = false
   )
   private void tensuraTno$safeSendLieModePacket(ServerPlayer player, CustomPacketPayload packet) {
      try {
         NetworkManager.sendToPlayer(player, packet);
      } catch (Exception var4) {
      }
   }
}
