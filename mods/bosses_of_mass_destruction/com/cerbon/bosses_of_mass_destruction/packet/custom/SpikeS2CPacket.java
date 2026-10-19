package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.cerbon.cerbons_api.api.network.data.PacketContext;
import com.cerbon.cerbons_api.api.network.data.Side;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class SpikeS2CPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("cerbons_api", "spike_s2c_packet");
   public static final StreamCodec<FriendlyByteBuf, SpikeS2CPacket> STREAM_CODEC = StreamCodec.ofMember(SpikeS2CPacket::write, SpikeS2CPacket::new);
   private final int id;
   private final List<BlockPos> spikePositions;

   public SpikeS2CPacket(int id, List<BlockPos> spikePositions) {
      this.id = id;
      this.spikePositions = spikePositions;
   }

   public SpikeS2CPacket(FriendlyByteBuf buf) {
      this.id = buf.readInt();
      int size = buf.readInt();
      this.spikePositions = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         this.spikePositions.add(buf.readBlockPos());
      }
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.id);
      buf.writeInt(this.spikePositions.size());

      for (BlockPos spikePos : this.spikePositions) {
         buf.writeBlockPos(spikePos);
      }
   }

   public static void handle(PacketContext<SpikeS2CPacket> ctx) {
      if (!ctx.side().equals(Side.SERVER)) {
         SpikeS2CPacket packet = (SpikeS2CPacket)ctx.message();
         Minecraft client = Minecraft.getInstance();
         client.execute(() -> {
            ClientLevel clientLevel = client.level;
            if (clientLevel != null) {
               if (clientLevel.getEntity(packet.id) instanceof VoidBlossomEntity voidBlossomEntity) {
                  packet.spikePositions.forEach(voidBlossomEntity.clientSpikeHandler::addSpike);
               }
            }
         });
      }
   }

   public static Type<CustomPacketPayload> type() {
      return new Type(CHANNEL);
   }
}
