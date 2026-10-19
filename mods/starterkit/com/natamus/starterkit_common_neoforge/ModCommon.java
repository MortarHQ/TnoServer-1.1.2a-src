package com.natamus.starterkit_common_neoforge;

import com.natamus.starterkit_common_neoforge.config.ConfigHandler;
import com.natamus.starterkit_common_neoforge.functions.StarterDataFunctions;
import com.natamus.starterkit_common_neoforge.functions.StarterGearFunctions;
import com.natamus.starterkit_common_neoforge.networking.PacketRegistration;

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
