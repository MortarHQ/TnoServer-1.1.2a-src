package com.natamus.starterkit_common_forge;

import com.natamus.starterkit_common_forge.config.ConfigHandler;
import com.natamus.starterkit_common_forge.functions.StarterDataFunctions;
import com.natamus.starterkit_common_forge.functions.StarterGearFunctions;
import com.natamus.starterkit_common_forge.networking.PacketRegistration;

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
