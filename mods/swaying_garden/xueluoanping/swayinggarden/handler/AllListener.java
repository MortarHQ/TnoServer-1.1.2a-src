package xueluoanping.swayinggarden.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent.UpdateCause;
import xueluoanping.swayinggarden.SwayingGarden;
import xueluoanping.swayinggarden.client.shader.IrisHook;

@EventBusSubscriber(modid = "swaying_garden")
public class AllListener {
   @SubscribeEvent
   public static void onTagsUpdatedEvent(TagsUpdatedEvent tagsUpdatedEvent) {
      if (tagsUpdatedEvent.getUpdateCause() == UpdateCause.CLIENT_PACKET_RECEIVED) {
         try {
            Class<?> ignored = Class.forName(
               "net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings", false, Thread.currentThread().getContextClassLoader()
            );
            IrisHook.reload();
         } catch (Exception ignored) {
            SwayingGarden.logger("Have you installed Iris?");
         }
      }
   }
}
