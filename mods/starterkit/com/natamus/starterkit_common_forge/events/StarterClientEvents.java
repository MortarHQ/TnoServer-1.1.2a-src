package com.natamus.starterkit_common_forge.events;

import com.natamus.collective_common_forge.implementations.networking.api.Dispatcher;
import com.natamus.starterkit_common_forge.data.Constants;
import com.natamus.starterkit_common_forge.data.ConstantsClient;
import com.natamus.starterkit_common_forge.data.VariablesClient;
import com.natamus.starterkit_common_forge.functions.StarterClientFunctions;
import com.natamus.starterkit_common_forge.inventory.StarterKitInventoryScreen;
import com.natamus.starterkit_common_forge.networking.packets.ToServerAnnounceModIsInstalledPacket;

public class StarterClientEvents {
   public static void onClientTick() {
      if (VariablesClient.waitingForAnnouncement && ConstantsClient.mc.getConnection() != null) {
         VariablesClient.waitingForAnnouncement = false;
         Dispatcher.sendToServer(new ToServerAnnounceModIsInstalledPacket());
      }

      if (VariablesClient.openChooseKitScreen && ConstantsClient.mc.screen == null) {
         VariablesClient.openChooseKitScreen = false;
         StarterClientFunctions.showInitialChooseKitScreen();
      }

      if (VariablesClient.priorPlayerEquipment != null) {
         if (!(ConstantsClient.mc.screen instanceof StarterKitInventoryScreen)) {
            if (!StarterClientFunctions.removeLocalPlayerEquipment()) {
               Constants.logger.warn("[Starter Kit] Unable to remove local player equipment.");
            } else if (!StarterClientFunctions.setPriorLocalPlayerEquipment()) {
               Constants.logger.warn("[Starter Kit] Unable to set local player's prior equipment.");
            } else {
               StarterClientFunctions.clearStarterKitClientCache();
            }
         }
      }
   }
}
