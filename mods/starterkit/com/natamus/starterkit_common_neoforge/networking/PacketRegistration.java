package com.natamus.starterkit_common_neoforge.networking;

import com.natamus.collective_common_neoforge.implementations.networking.api.Network;
import com.natamus.starterkit_common_neoforge.networking.packets.ToClientAskIfModIsInstalledPacket;
import com.natamus.starterkit_common_neoforge.networking.packets.ToClientReceiveKitDataPacket;
import com.natamus.starterkit_common_neoforge.networking.packets.ToClientSelectFirstSlotPacket;
import com.natamus.starterkit_common_neoforge.networking.packets.ToServerAnnounceModIsInstalledPacket;
import com.natamus.starterkit_common_neoforge.networking.packets.ToServerSendKitChoicePacket;

public class PacketRegistration {
   public void init() {
      this.initClientPackets();
      this.initServerPackets();
   }

   private void initClientPackets() {
      Network.registerPacket(
            ToClientAskIfModIsInstalledPacket.CHANNEL,
            ToClientAskIfModIsInstalledPacket.class,
            ToClientAskIfModIsInstalledPacket::encode,
            ToClientAskIfModIsInstalledPacket::decode,
            ToClientAskIfModIsInstalledPacket::handle
         )
         .registerPacket(
            ToClientReceiveKitDataPacket.CHANNEL,
            ToClientReceiveKitDataPacket.class,
            ToClientReceiveKitDataPacket::encode,
            ToClientReceiveKitDataPacket::decode,
            ToClientReceiveKitDataPacket::handle
         )
         .registerPacket(
            ToClientSelectFirstSlotPacket.CHANNEL,
            ToClientSelectFirstSlotPacket.class,
            ToClientSelectFirstSlotPacket::encode,
            ToClientSelectFirstSlotPacket::decode,
            ToClientSelectFirstSlotPacket::handle
         );
   }

   private void initServerPackets() {
      Network.registerPacket(
            ToServerAnnounceModIsInstalledPacket.CHANNEL,
            ToServerAnnounceModIsInstalledPacket.class,
            ToServerAnnounceModIsInstalledPacket::encode,
            ToServerAnnounceModIsInstalledPacket::decode,
            ToServerAnnounceModIsInstalledPacket::handle
         )
         .registerPacket(
            ToServerSendKitChoicePacket.CHANNEL,
            ToServerSendKitChoicePacket.class,
            ToServerSendKitChoicePacket::encode,
            ToServerSendKitChoicePacket::decode,
            ToServerSendKitChoicePacket::handle
         );
   }
}
