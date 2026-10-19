package com.natamus.starterkit_common_forge.networking.packets;

import com.google.common.collect.Maps;
import com.natamus.collective_common_forge.implementations.networking.data.PacketContext;
import com.natamus.collective_common_forge.implementations.networking.data.Side;
import com.natamus.starterkit_common_forge.data.VariablesClient;
import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientReceiveKitDataPacket {
   public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath("starterkit", "to_client_receive_kit_data_packet");
   private final HashMap<String, String> packetStarterGearEntries;
   private final HashMap<String, String> packetStarterKitDescriptions;

   public ToClientReceiveKitDataPacket(HashMap<String, String> entriesIn, HashMap<String, String> descriptionsIn) {
      this.packetStarterGearEntries = entriesIn;
      this.packetStarterKitDescriptions = descriptionsIn;
   }

   public static ToClientReceiveKitDataPacket decode(FriendlyByteBuf buf) {
      HashMap<String, String> entriesIn = (HashMap<String, String>)buf.readMap(
         Maps::newHashMapWithExpectedSize, FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf
      );
      HashMap<String, String> descriptionsIn = (HashMap<String, String>)buf.readMap(
         Maps::newHashMapWithExpectedSize, FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf
      );
      return new ToClientReceiveKitDataPacket(entriesIn, descriptionsIn);
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeMap(this.packetStarterGearEntries, (a, b) -> a.writeUtf(b), (a, b) -> a.writeUtf(b));
      buf.writeMap(this.packetStarterKitDescriptions, (a, b) -> a.writeUtf(b), (a, b) -> a.writeUtf(b));
   }

   public static void handle(PacketContext<ToClientReceiveKitDataPacket> ctx) {
      if (ctx.side().equals(Side.CLIENT)) {
         ToClientReceiveKitDataPacket packet = (ToClientReceiveKitDataPacket)ctx.message();
         VariablesClient.cachedStarterGearEntries = packet.packetStarterGearEntries;
         VariablesClient.cachedStarterKitDescriptions = packet.packetStarterKitDescriptions;
         VariablesClient.openChooseKitScreen = true;
      }
   }
}
