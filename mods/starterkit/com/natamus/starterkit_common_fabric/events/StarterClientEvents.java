package com.natamus.starterkit_common_fabric.events;

import com.natamus.collective_common_fabric.implementations.networking.api.Dispatcher;
import com.natamus.starterkit_common_fabric.data.Constants;
import com.natamus.starterkit_common_fabric.data.ConstantsClient;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import com.natamus.starterkit_common_fabric.functions.StarterClientFunctions;
import com.natamus.starterkit_common_fabric.inventory.StarterKitInventoryScreen;
import com.natamus.starterkit_common_fabric.networking.packets.ToServerAnnounceModIsInstalledPacket;

public class StarterClientEvents {
   public static void onClientTick() {
      if (VariablesClient.waitingForAnnouncement && ConstantsClient.mc.method_1562() != null) {
         VariablesClient.waitingForAnnouncement = false;
         Dispatcher.sendToServer(new ToServerAnnounceModIsInstalledPacket());
      }

      if (VariablesClient.openChooseKitScreen && ConstantsClient.mc.field_1755 == null) {
         VariablesClient.openChooseKitScreen = false;
         StarterClientFunctions.showInitialChooseKitScreen();
      }

      if (VariablesClient.priorPlayerEquipment != null) {
         if (!(ConstantsClient.mc.field_1755 instanceof StarterKitInventoryScreen)) {
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
