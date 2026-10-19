package xaero.map.common.config;

import java.nio.file.Files;
import java.nio.file.Path;
import xaero.map.WorldMap;
import xaero.map.platform.Services;

public class LegacyCommonConfigInit {
   public void init(String configFileName) {
      Path configDestinationPath = Services.PLATFORM.getConfigDir();
      Path configPath = configDestinationPath.resolve(configFileName);
      if (Services.PLATFORM.isDedicatedServer() && !Files.exists(configPath)) {
         Path oldConfigPath = Services.PLATFORM.getGameDir().resolve(configFileName);
         if (Files.exists(oldConfigPath)) {
            configPath = oldConfigPath;
         }
      }

      LegacyCommonConfigIO io = new LegacyCommonConfigIO(configPath);
      WorldMap.commonConfigIO = io;
      if (Files.exists(configPath)) {
         io.load();
      }
   }
}
