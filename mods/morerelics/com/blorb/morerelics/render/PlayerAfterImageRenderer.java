package com.blorb.morerelics.render;

import com.blorb.morerelics.MoreRelics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.RenderPlayerEvent.Post;

@EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class PlayerAfterImageRenderer {
   public static boolean isRendering = false;

   @SubscribeEvent
   public static void onRenderPlayer(Post event) {
   }

   @SubscribeEvent
   public static void recordTick(Pre event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null) {
         for (Player player : mc.level.players()) {
            MobEffectInstance instance = player.getEffect(MoreRelics.ACCELERATING);
            if (instance != null && instance.getAmplifier() == 9) {
               AfterImageTracker.record(player);
            } else {
               AfterImageTracker.clear(player);
            }
         }
      }
   }
}
