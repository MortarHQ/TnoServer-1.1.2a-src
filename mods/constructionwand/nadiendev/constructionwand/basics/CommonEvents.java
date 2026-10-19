package nadiendev.constructionwand.basics;

import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = "constructionwand")
public class CommonEvents {
   @SubscribeEvent
   public static void serverStarting(ServerStartingEvent e) {
      ReplacementRegistry.init();
   }

   @SubscribeEvent
   public static void logOut(PlayerLoggedOutEvent e) {
      Player player = e.getEntity();
      if (!player.level().isClientSide) {
         ConstructionWand.instance.undoHistory.removePlayer(player);
      }
   }
}
