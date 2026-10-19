package artifacts.neoforge.client;

import artifacts.client.UmbrellaArmPoseHelper;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforged.neoforge.common.NeoForge;

public class UmbrellaArmPoseHandler {
   public static void setup() {
      NeoForge.EVENT_BUS.addListener(UmbrellaArmPoseHandler::onLivingRender);
   }

   public static void onLivingRender(Pre<?, ?> event) {
      UmbrellaArmPoseHelper.setUmbrellaArmPose(event.getRenderer().getModel(), event.getEntity());
   }
}
