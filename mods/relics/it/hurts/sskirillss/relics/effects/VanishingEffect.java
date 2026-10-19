package it.hurts.sskirillss.relics.effects;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;

public class VanishingEffect extends MobEffect {
   public VanishingEffect() {
      super(MobEffectCategory.BENEFICIAL, 6829738);
   }

   @EventBusSubscriber(modid = "relics", value = Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void onEntityRender(Pre<?, ?> event) {
         if (event.getEntity().hasEffect(EffectRegistry.VANISHING)) {
            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onHandRender(RenderHandEvent event) {
         if (Minecraft.getInstance().player.hasEffect(EffectRegistry.VANISHING) && event.getItemStack().isEmpty()) {
            event.setCanceled(true);
         }
      }
   }
}
