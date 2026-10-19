package it.hurts.sskirillss.relics.network.packets.capability;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CapabilitySyncPacket implements CustomPacketPayload {
   private final CompoundTag data;
   public static final Type<CapabilitySyncPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "capability_sync"));
   public static final StreamCodec<ByteBuf, CapabilitySyncPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.COMPOUND_TAG, CapabilitySyncPacket::getData, CapabilitySyncPacket::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public boolean handle(IPayloadContext ctx) {
      ctx.enqueueWork(this::doSync);
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   private void doSync() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         ;
      }
   }

   public CompoundTag getData() {
      return this.data;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CapabilitySyncPacket other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$data = this.getData();
         Object other$data = other.getData();
         return this$data == null ? other$data == null : this$data.equals(other$data);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof CapabilitySyncPacket;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $data = this.getData();
      return result * 59 + ($data == null ? 43 : $data.hashCode());
   }

   @Override
   public String toString() {
      return "CapabilitySyncPacket(data=" + this.getData() + ")";
   }

   public CapabilitySyncPacket(CompoundTag data) {
      this.data = data;
   }
}
