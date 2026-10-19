package it.hurts.octostudios.octolib.module.config.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import it.hurts.octostudios.octolib.client.screen.TestGearScreen;
import it.hurts.octostudios.octolib.module.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class TestScreenPacket extends Packet {
   public static Type<TestScreenPacket> TYPE = Packet.createType("octolib", "test_screen");
   public static StreamCodec<RegistryFriendlyByteBuf, TestScreenPacket> STREAM_CODEC = Packet.createCodec(TestScreenPacket::write, TestScreenPacket::new);

   public TestScreenPacket(RegistryFriendlyByteBuf buf) {
      super(buf);
   }

   public TestScreenPacket() {
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   protected void handleClient(PacketContext packetContext) {
      Minecraft.getInstance().setScreen(new TestGearScreen());
   }
}
