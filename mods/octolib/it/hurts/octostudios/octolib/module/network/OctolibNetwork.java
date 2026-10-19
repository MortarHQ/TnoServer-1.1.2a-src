package it.hurts.octostudios.octolib.module.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.NetworkReceiver;
import dev.architectury.networking.NetworkManager.Side;
import dev.architectury.platform.Platform;
import it.hurts.octostudios.octolib.module.config.network.SyncConfigPacket;
import it.hurts.octostudios.octolib.module.config.network.TestScreenPacket;
import it.hurts.octostudios.octolib.module.config.network.UnholyAbominationPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.api.distmarker.Dist;

public class OctolibNetwork {
   public static void init() {
      registerS2C(SyncConfigPacket.TYPE, SyncConfigPacket.STREAM_CODEC, Packet::handle);
      registerS2C(TestScreenPacket.TYPE, TestScreenPacket.STREAM_CODEC, Packet::handle);
      registerS2C(UnholyAbominationPacket.TYPE, UnholyAbominationPacket.STREAM_CODEC, Packet::handle);
   }

   public static <T extends CustomPacketPayload> void registerS2C(Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, NetworkReceiver<T> receiver) {
      if (Platform.getEnv() == Dist.DEDICATED_SERVER) {
         NetworkManager.registerS2CPayloadType(type, codec);
      } else {
         NetworkManager.registerReceiver(Side.S2C, type, codec, receiver);
      }
   }

   public static <T extends CustomPacketPayload> void registerC2S(Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, NetworkReceiver<T> receiver) {
      NetworkManager.registerReceiver(Side.C2S, type, codec, receiver);
   }
}
