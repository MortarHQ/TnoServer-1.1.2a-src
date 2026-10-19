package it.hurts.octostudios.octolib.module.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class Packet implements CustomPacketPayload {
   protected Packet() {
   }

   public Packet(RegistryFriendlyByteBuf buf) {
   }

   public abstract void write(RegistryFriendlyByteBuf var1);

   public final void handle(PacketContext packetContext) {
      switch (packetContext.getEnvironment()) {
         case CLIENT:
            this.handleClient(packetContext);
            break;
         case SERVER:
            this.handleServer(packetContext);
      }
   }

   @OnlyIn(Dist.CLIENT)
   protected void handleClient(PacketContext packetContext) {
   }

   protected void handleServer(PacketContext packetContext) {
   }

   public static <T extends Packet> Type<T> createType(String namespace, String path) {
      return new Type(ResourceLocation.fromNamespaceAndPath(namespace, path));
   }

   public static <B extends ByteBuf, T extends Packet> StreamCodec<B, T> createCodec(StreamMemberEncoder<B, T> encoder, StreamDecoder<B, T> decoder) {
      return CustomPacketPayload.codec(encoder, decoder);
   }
}
