package com.blorb.morerelics.effects;

import com.blorb.morerelics.MoreRelics;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Post;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;

public class Static extends MobEffect {
   public Static() {
      super(MobEffectCategory.HARMFUL, 11119017);
   }

   @EventBusSubscriber(modid = "morerelics", value = Dist.CLIENT)
   public static class ClientEvent {
      private static final Map<LivingEntityRenderer<?, ?>, Float> previousShadows = new WeakHashMap<>();

      @SubscribeEvent
      public static void onRender(Pre<?, ?> event) {
         Minecraft mc = Minecraft.getInstance();
         Player localPlayer = mc.player;
         if (localPlayer != null) {
            if (localPlayer.hasEffect(MoreRelics.STATIC)) {
               LivingEntity entityBeingRendered = event.getEntity();
               if (entityBeingRendered != localPlayer) {
                  LivingEntityRenderer<?, ? extends EntityModel<? extends LivingEntity>> renderer = event.getRenderer();
                  previousShadows.putIfAbsent(renderer, renderer.shadowRadius);
                  renderer.shadowRadius = 0.0F;
                  event.setCanceled(true);
               }
            }
         }
      }

      @SubscribeEvent
      public static void onRenderPost(Post<?, ?> event) {
         LivingEntityRenderer<?, ? extends EntityModel<? extends LivingEntity>> renderer = event.getRenderer();
         Float original = previousShadows.remove(renderer);
         if (original != null) {
            renderer.shadowRadius = original;
         }
      }
   }
}
