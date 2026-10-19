package com.natamus.starterkit_common_fabric;

import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.functions.StarterDataFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterGearFunctions;
import com.natamus.starterkit_common_fabric.networking.PacketRegistration;

public class ModCommon {
   public static void init() {
      ConfigHandler.initConfig();
      registerPackets();
      load();
   }

   private static void load() {
      StarterDataFunctions.initConfigFolders();
      StarterGearFunctions.processKitFiles();
   }

   public static void registerPackets() {
      new PacketRegistration().init();
   }
}
