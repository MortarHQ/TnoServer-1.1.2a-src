package noobanidus.mods.lootr.neoforge.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;

@EventBusSubscriber(modid = "lootr")
public class HandleTick {
   @SubscribeEvent
   public static void onServerTick(Post event) {
      BlockEntityTicker.onServerTick(event.getServer());
   }
}
