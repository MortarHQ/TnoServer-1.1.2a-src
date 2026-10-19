package twilightforest.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.item.travellers_gear.TravellersGearLogic;

public record PerformSidestepPacket(boolean isLeftStepSide) implements CustomPacketPayload {
   public static final Type<PerformSidestepPacket> TYPE = new Type(TwilightForestMod.prefix("perform_sidestep_packet"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PerformSidestepPacket> STREAM_CODEC = CustomPacketPayload.codec(
      PerformSidestepPacket::write, PerformSidestepPacket::new
   );

   public PerformSidestepPacket(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
      this(registryFriendlyByteBuf.readBoolean());
   }

   private void write(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
      registryFriendlyByteBuf.writeBoolean(this.isLeftStepSide);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(PerformSidestepPacket message, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         if (!TravellersGearLogic.tryPerformSidestep(ctx.player(), message.isLeftStepSide)) {
            TravellersGearLogic.handleSidestepAbuse(ctx.player());
         }
      });
   }
}
