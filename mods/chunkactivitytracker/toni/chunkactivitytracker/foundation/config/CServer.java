package toni.chunkactivitytracker.foundation.config;

import toni.lib.config.ConfigBase;
import toni.lib.config.ConfigBase.ConfigBool;

public class CServer extends ConfigBase {
   public final ConfigBool storeHeightmaps = this.b(
      false,
      "Store Heightmap Info",
      new String[]{"Store initial chunk heightmap data. Useful for some mods, disabled by default as it takes up much more space."}
   );

   public String getName() {
      return "server";
   }
}
