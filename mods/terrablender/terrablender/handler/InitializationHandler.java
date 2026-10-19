package terrablender.handler;

import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import terrablender.util.LevelUtils;

public class InitializationHandler {
   public static void onServerAboutToStart(ServerAboutToStartEvent event) {
      LevelUtils.initializeOnServerStart(event.getServer());
   }
}
