package com.natamus.starterkit_common_fabric.networking.packets;

import com.google.common.collect.Maps;
import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import java.util.HashMap;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public class ToClientReceiveKitDataPacket {
   public static final class_2960 CHANNEL = class_2960.method_60655("starterkit", "to_client_receive_kit_data_packet");
   private final HashMap<String, String> packetStarterGearEntries;
   private final HashMap<String, String> packetStarterKitDescriptions;

   public ToClientReceiveKitDataPacket(HashMap<String, String> entriesIn, HashMap<String, String> descriptionsIn) {
      this.packetStarterGearEntries = entriesIn;
      this.packetStarterKitDescriptions = descriptionsIn;
   }

   public static ToClientReceiveKitDataPacket decode(class_2540 buf) {
      HashMap<String, String> entriesIn = (HashMap<String, String>)buf.method_34069(
         Maps::newHashMapWithExpectedSize, class_2540::method_19772, class_2540::method_19772
      );
      HashMap<String, String> descriptionsIn = (HashMap<String, String>)buf.method_34069(
         Maps::newHashMapWithExpectedSize, class_2540::method_19772, class_2540::method_19772
      );
      return new ToClientReceiveKitDataPacket(entriesIn, descriptionsIn);
   }

   public void encode(class_2540 buf) {
      buf.method_34063(this.packetStarterGearEntries, (a, b) -> a.method_10814(b), (a, b) -> a.method_10814(b));
      buf.method_34063(this.packetStarterKitDescriptions, (a, b) -> a.method_10814(b), (a, b) -> a.method_10814(b));
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
