package com.mortal_cultivation.client;

import com.mortal_cultivation.client.renderer.PlayerMonkeyTailLayer;
import com.mortal_cultivation.client.renderer.SpiritMonkeyRenderer;
import com.mortal_cultivation.registry.CultivationEntities;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class SpiritMonkeyClientEvents {
   private SpiritMonkeyClientEvents() {
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)CultivationEntities.SPIRIT_MONKEY.get(), SpiritMonkeyRenderer::new);
   }

   @SubscribeEvent
   public static void addPlayerLayers(AddLayers event) {
      for (Model skin : event.getSkins()) {
         if (event.getSkin(skin) instanceof PlayerRenderer renderer) {
            renderer.addLayer(new PlayerMonkeyTailLayer(renderer));
         }
      }
   }
}
