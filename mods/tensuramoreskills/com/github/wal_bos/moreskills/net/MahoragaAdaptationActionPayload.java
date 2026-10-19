package com.github.wal_bos.moreskills.net;

import com.github.wal_bos.moreskills.ability.skill.ultimate.DivineGeneralMahoragaSkill;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record MahoragaAdaptationActionPayload(MahoragaAdaptationActionPayload.Action action, int index) implements CustomPacketPayload {
   public static final Type<MahoragaAdaptationActionPayload> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mahoraga_adaptation_action")
   );
   public static final StreamCodec<FriendlyByteBuf, MahoragaAdaptationActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MahoragaAdaptationActionPayload::write, MahoragaAdaptationActionPayload::new
   );

   public MahoragaAdaptationActionPayload(FriendlyByteBuf buf) {
      this((MahoragaAdaptationActionPayload.Action)buf.readEnum(MahoragaAdaptationActionPayload.Action.class), buf.readVarInt());
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeEnum(this.action);
      buf.writeVarInt(this.index);
   }

   public static void handle(MahoragaAdaptationActionPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(() -> {
            if (context.getPlayer() instanceof ServerPlayer player) {
               switch (payload.action) {
                  case SELECT:
                     DivineGeneralMahoragaSkill.selectAdaptationEntry(player, payload.index());
                     break;
                  case TOGGLE:
                     DivineGeneralMahoragaSkill.toggleAdaptationEntry(player, payload.index());
               }
            }
         });
      }
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public enum Action {
      SELECT,
      TOGGLE;
   }
}
