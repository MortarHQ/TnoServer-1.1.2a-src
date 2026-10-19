package com.natamus.collective_common_neoforge;

import com.natamus.collective_common_neoforge.config.CollectiveConfigHandler;
import com.natamus.collective_common_neoforge.config.LoadJSONFiles;
import com.natamus.collective_common_neoforge.data.Constants;
import com.natamus.collective_common_neoforge.data.GlobalVariables;

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
