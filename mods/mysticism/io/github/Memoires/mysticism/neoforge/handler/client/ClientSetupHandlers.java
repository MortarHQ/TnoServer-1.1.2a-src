package io.github.Memoires.mysticism.neoforge.handler.client;

import io.github.Memoires.mysticism.client.layer.multi.TailedBeastCloakLayer;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.world.dimension.MysticismDimensionEffects;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = "mysticism", value = Dist.CLIENT)
public class ClientSetupHandlers {
   @SubscribeEvent
   private static void onDimensionSpecialEffectRegistry(RegisterDimensionSpecialEffectsEvent event) {
      event.register(MysticismDimensions.ELEMENTAL_REALM_LOCATION, new MysticismDimensionEffects.ElementalRealm());
      event.register(MysticismDimensions.KAMUI_DIMENSION_LOCATION, new MysticismDimensionEffects.KamuiDimension());
   }

   @SubscribeEvent
   public static void onLayersAdd(AddLayers event) {
      for (EntityType<?> type : event.getEntityTypes()) {
         if (event.getRenderer(type) instanceof LivingEntityRenderer renderer) {
            renderer.addLayer(new TailedBeastCloakLayer(renderer));
         }
      }

      for (Model model : event.getSkins()) {
         if (event.getSkin(model) instanceof LivingEntityRenderer renderer) {
            renderer.addLayer(new TailedBeastCloakLayer(renderer));
         }
      }
   }
}
