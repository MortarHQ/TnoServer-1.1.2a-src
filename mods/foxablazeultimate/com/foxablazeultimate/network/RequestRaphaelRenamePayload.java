package com.foxablazeultimate.network;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.ability.skill.ciel.CielAwakening;
import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.ability.skill.raphael.RaphaelSkill;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.networking.NetworkManager.PacketContext;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record RequestRaphaelRenamePayload(String name) implements CustomPacketPayload {
   public static final Type<RequestRaphaelRenamePayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "request_raphael_rename"));
   public static final StreamCodec<FriendlyByteBuf, RequestRaphaelRenamePayload> STREAM_CODEC = CustomPacketPayload.codec(
      RequestRaphaelRenamePayload::encode, RequestRaphaelRenamePayload::new
   );

   public RequestRaphaelRenamePayload(FriendlyByteBuf buf) {
      this(buf.readUtf(64));
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUtf(this.name, 64);
   }

   public static void handle(RequestRaphaelRenamePayload payload, PacketContext context) {
      if (context.getPlayer() instanceof ServerPlayer player) {
         context.queue(() -> {
            if (CielState.isNamingUnlocked(player)) {
               CielAwakening.tryAwaken(player);
            } else {
               Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get());
               if (opt.isEmpty()) {
                  FoxAblazeUltimateMod.LOGGER.debug("[FoxAblazeUltimate] 玩家 {} 发起拉斐尔重命名但本人没有该技能，忽略", player.getGameProfile().getName());
               } else {
                  String sanitized = payload.name == null ? "" : payload.name.trim();
                  RaphaelSkill.applyCustomName(opt.get(), sanitized, player);
               }
            }
         });
      }
   }

   public Type<RequestRaphaelRenamePayload> type() {
      return TYPE;
   }
}
