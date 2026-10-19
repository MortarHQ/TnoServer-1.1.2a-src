package org.crypticdev.ancient_artifacts.neoforge;

import java.util.function.Function;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import org.crypticdev.ancient_artifacts.AncientArtifactsClient;

@EventBusSubscriber(modid = "ancient_artifacts", value = Dist.CLIENT)
public class AncientArtifactsNeoForgeClient {
   @SubscribeEvent
   public static void setup(FMLClientSetupEvent event) {
      AncientArtifactsClient.initClient();
   }

   @SubscribeEvent
   public static void onAddLayers(AddLayers event) {
      for (Model skinType : event.getSkins()) {
         if (event.getSkin(skinType) instanceof LivingEntityRenderer<?, ?> le) {
            for (Function<RenderLayerParent, RenderLayer> renderLayerFunction : AncientArtifactsClient.models) {
               le.addLayer(renderLayerFunction.apply(le));
            }
         }
      }
   }
}
