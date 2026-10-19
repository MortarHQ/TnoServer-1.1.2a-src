package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record MitamaOfferingActionPayload(
   MitamaFaithSkill.MitamaMenuKind kind,
   UUID targetUuid,
   MitamaFaithSkill.MitamaOfferingStat stat,
   double amount,
   ResourceLocation skillId,
   boolean skillAction
) implements CustomPacketPayload {
   public static final Type<MitamaOfferingActionPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mitama_offering_action"));
   public static final StreamCodec<FriendlyByteBuf, MitamaOfferingActionPayload> STREAM_CODEC = CustomPacketPayload.codec(
      MitamaOfferingActionPayload::write, MitamaOfferingActionPayload::new
   );

   public MitamaOfferingActionPayload(FriendlyByteBuf buf) {
      this(
         (MitamaFaithSkill.MitamaMenuKind)buf.readEnum(MitamaFaithSkill.MitamaMenuKind.class),
         buf.readUUID(),
         (MitamaFaithSkill.MitamaOfferingStat)buf.readEnum(MitamaFaithSkill.MitamaOfferingStat.class),
         buf.readDouble(),
         buf.readResourceLocation(),
         buf.readBoolean()
      );
   }

   public static MitamaOfferingActionPayload stat(
      MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid, MitamaFaithSkill.MitamaOfferingStat stat, double amount
   ) {
      return new MitamaOfferingActionPayload(kind, targetUuid, stat, amount, ResourceLocation.withDefaultNamespace("empty"), false);
   }

   public static MitamaOfferingActionPayload skill(MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid, ResourceLocation skillId) {
      return new MitamaOfferingActionPayload(kind, targetUuid, MitamaFaithSkill.MitamaOfferingStat.MAGICULE, 0.0, skillId, true);
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeEnum(this.kind);
      buf.writeUUID(this.targetUuid);
      buf.writeEnum(this.stat);
      buf.writeDouble(this.amount);
      buf.writeResourceLocation(this.skillId);
      buf.writeBoolean(this.skillAction);
   }

   public static void handle(MitamaOfferingActionPayload payload, PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               if (context.getPlayer() instanceof ServerPlayer player) {
                  MitamaFaithSkill.performMenuAction(
                     player, payload.kind(), payload.targetUuid(), payload.stat(), payload.amount(), payload.skillAction() ? payload.skillId() : null
                  );
               }
            }
         );
      }
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
