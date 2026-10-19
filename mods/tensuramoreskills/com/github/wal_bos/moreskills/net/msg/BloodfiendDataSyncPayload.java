package com.github.wal_bos.moreskills.net.msg;

import com.github.wal_bos.moreskills.net.client.ClientPayloadExecutor;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record BloodfiendDataSyncPayload(boolean bloodfiend, int blood, int maxBlood, int generation, int totalBloodDrained, int totalThrallsTurned)
   implements CustomPacketPayload {
   public static final Type<BloodfiendDataSyncPayload> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "bloodfiend_data_sync"));
   public static final StreamCodec<FriendlyByteBuf, BloodfiendDataSyncPayload> STREAM_CODEC = CustomPacketPayload.codec(
      BloodfiendDataSyncPayload::encode, BloodfiendDataSyncPayload::new
   );

   public BloodfiendDataSyncPayload(FriendlyByteBuf buf) {
      this(buf.readBoolean(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeBoolean(this.bloodfiend);
      buf.writeVarInt(this.blood);
      buf.writeVarInt(this.maxBlood);
      buf.writeVarInt(this.generation);
      buf.writeVarInt(this.totalBloodDrained);
      buf.writeVarInt(this.totalThrallsTurned);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.CLIENT) {
         context.queue(() -> ClientPayloadExecutor.bloodfiendDataSync(this));
      }
   }

   @NotNull
   public Type<BloodfiendDataSyncPayload> type() {
      return TYPE;
   }
}
