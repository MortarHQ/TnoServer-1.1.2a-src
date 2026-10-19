package artifacts.neoforge.integration.curios;

import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.mixin.accessors.client.LivingEntityRendererAccessor;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModLootTables;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import top.theillusivec4.curios.client.render.CuriosLayer;

public class CuriosCompatClient {
   public static void setup(IEventBus modBus) {
      if (!PlatformServices.platformHelper.isModLoaded("cclayer")) {
         EquipmentRenderingManager.register(new CuriosRenderingHandler());
      }

      modBus.addListener(CuriosCompatClient::onAddLayers);
   }

   private static void onAddLayers(AddLayers event) {
      label29:
      for (EntityType<?> entity : ModLootTables.ENTITY_EQUIPMENT.keySet()) {
         EntityRenderer<?> renderer = event.getRenderer(entity);
         if (renderer != null) {
            LivingEntityRenderer livingEntityRenderer = (LivingEntityRenderer)renderer;

            for (RenderLayer<?, ?> layer : ((LivingEntityRendererAccessor)livingEntityRenderer).getLayers()) {
               if (layer instanceof CuriosLayer) {
                  continue label29;
               }
            }

            livingEntityRenderer.addLayer(new CuriosLayer(livingEntityRenderer));
         }
      }
   }
}
