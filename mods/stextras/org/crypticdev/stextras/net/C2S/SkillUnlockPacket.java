package org.crypticdev.stextras.net.C2S;

import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class SkillUnlockPacket implements CustomPacketPayload {
   private final ResourceLocation skillId;
   public static final Type<SkillUnlockPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "skill_unlock"));
   public static final StreamCodec<FriendlyByteBuf, SkillUnlockPacket> STREAM_CODEC = StreamCodec.of(
      (buf, pkt) -> buf.writeResourceLocation(pkt.skillId), buf -> new SkillUnlockPacket(buf.readResourceLocation())
   );

   public SkillUnlockPacket(ResourceLocation skillId) {
      this.skillId = skillId;
   }

   public ResourceLocation skillId() {
      return this.skillId;
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void registerCommon() {
   }

   public static void registerServer() {
      NetworkManager.registerReceiver(
         NetworkManager.c2s(),
         TYPE,
         STREAM_CODEC,
         (payload, context) -> {
            if (context.getPlayer() instanceof ServerPlayer player) {
               ResourceLocation var4 = payload.skillId();
               context.queue(
                  () -> {
                     if (STExtarsStorage.Player.canUnlockSkill(player, var4)) {
                        STExtarsStorage.Player.setSoulGrade(
                           player, STExtarsStorage.Player.getSoulGrade(player) - STExtarsStorage.Player.getRequiredSoulGradeToUnlockSkill()
                        );
                        TensuraStorages.getExistenceFrom(player)
                           .setMagicule(TensuraStorages.getExistenceFrom(player).getMagicule() - STExtarsStorage.Player.getRequiredMagiculeToUnlockSkill());
                        STExtarsStorage.Player.unlockSkill(player, var4);
                     }
                  }
               );
            }
         }
      );
   }

   public static void sendToServer(ResourceLocation skillId) {
      NetworkManager.sendToServer(new SkillUnlockPacket(skillId));
   }
}
