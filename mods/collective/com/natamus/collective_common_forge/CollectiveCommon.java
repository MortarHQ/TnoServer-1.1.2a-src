package com.natamus.collective_common_forge;

import com.natamus.collective_common_forge.config.CollectiveConfigHandler;
import com.natamus.collective_common_forge.config.LoadJSONFiles;
import com.natamus.collective_common_forge.data.Constants;
import com.natamus.collective_common_forge.data.GlobalVariables;

public class CollectiveCommon {
   public static void init() {
      Constants.LOG.info("Loading Collective version 8.3.");
      CollectiveConfigHandler.initConfig();
      GlobalVariables.generateHashMaps();
      LoadJSONFiles.startListening();
      loadEvents();
   }

   public static void loadEvents() {
   }
}
