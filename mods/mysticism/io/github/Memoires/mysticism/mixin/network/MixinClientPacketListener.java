package io.github.Memoires.mysticism.mixin.network;

import com.mojang.authlib.GameProfile;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.ChatType.Bound;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
   @Unique
   private static final ThreadLocal<UUID> CHAT_SENDER = new ThreadLocal<>();

   @Inject(method = "handlePlayerChat(Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;)V", at = @At("HEAD"))
   private void captureSender(ClientboundPlayerChatPacket packet, CallbackInfo ci) {
      CHAT_SENDER.set(packet.sender());
   }

   @Inject(method = "handlePlayerChat(Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;)V", at = @At("RETURN"))
   private void clearSender(ClientboundPlayerChatPacket packet, CallbackInfo ci) {
      CHAT_SENDER.remove();
   }

   @Redirect(
      method = "handlePlayerChat(Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/multiplayer/chat/ChatListener;handlePlayerChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lcom/mojang/authlib/GameProfile;Lnet/minecraft/network/chat/ChatType$Bound;)V"
      )
   )
   private void corruptChatName(ChatListener instance, PlayerChatMessage playerChatMessage, GameProfile gameProfile, Bound bound) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer viewer = mc.player;
      if (viewer == null) {
         instance.handlePlayerChatMessage(playerChatMessage, gameProfile, bound);
      } else {
         Player sender = mc.level.getPlayerByUUID(gameProfile.getId());
         if (sender == null) {
            instance.handlePlayerChatMessage(playerChatMessage, gameProfile, bound);
         } else if (!SkillUtils.isSkillToggled(sender, (ManasSkill)UniqueMysticismSkills.FOURTH_WALL.get())) {
            instance.handlePlayerChatMessage(playerChatMessage, gameProfile, bound);
         } else {
            double analysisLevel = viewer.getAttributeValue(TensuraAttributes.ANALYSIS_LEVEL);
            if (analysisLevel >= 10.0) {
               instance.handlePlayerChatMessage(playerChatMessage, gameProfile, bound);
            } else {
               GameProfile corruptedProfile = new GameProfile(gameProfile.getId(), "§8§kUnknown");
               instance.handlePlayerChatMessage(playerChatMessage, corruptedProfile, bound);
            }
         }
      }
   }
}
