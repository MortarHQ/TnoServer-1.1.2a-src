package net.p3pp3rf1y.sophisticatedcore.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeSlotChangeResult;

public record SyncSlotChangeErrorPayload(UpgradeSlotChangeResult slotChangeError) implements CustomPacketPayload {
   public static final Type<SyncSlotChangeErrorPayload> TYPE = new Type(SophisticatedCore.getRL("sync_slot_change_error"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SyncSlotChangeErrorPayload> STREAM_CODEC = StreamCodec.composite(
      UpgradeSlotChangeResult.STREAM_CODEC, SyncSlotChangeErrorPayload::slotChangeError, SyncSlotChangeErrorPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(SyncSlotChangeErrorPayload payload, IPayloadContext context) {
      if (context.player().containerMenu instanceof StorageContainerMenuBase<?> menu) {
         menu.updateSlotChangeError(payload.slotChangeError);
      }
   }
}
