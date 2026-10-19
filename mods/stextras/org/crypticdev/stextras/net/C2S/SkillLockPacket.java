package org.crypticdev.stextras.net.C2S;

import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class SkillLockPacket implements CustomPacketPayload {
   private final ResourceLocation skillId;
   public static final Type<SkillLockPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("stextras", "skill_lock"));
   public static final StreamCodec<FriendlyByteBuf, SkillLockPacket> STREAM_CODEC = StreamCodec.of(
      (buf, pkt) -> buf.writeResourceLocation(pkt.skillId), buf -> new SkillLockPacket(buf.readResourceLocation())
   );

   public SkillLockPacket(ResourceLocation skillId) {
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
      NetworkManager.registerReceiver(NetworkManager.c2s(), TYPE, STREAM_CODEC, (payload, context) -> {
         if (context.getPlayer() instanceof ServerPlayer player) {
            ResourceLocation var4 = payload.skillId();
            context.queue(() -> {
               if (STExtarsStorage.Player.canLockSkill(player, var4)) {
                  ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(var4);
                  if (skill == null) {
                     return;
                  }

                  STExtarsStorage.Player.lockSkill(player, var4);
               }
            });
         }
      });
   }

   public static void sendToServer(ResourceLocation skillId) {
      NetworkManager.sendToServer(new SkillLockPacket(skillId));
   }
}
