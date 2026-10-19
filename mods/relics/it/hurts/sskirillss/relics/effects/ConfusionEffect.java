package it.hurts.sskirillss.relics.effects;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.client.player.Input;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public class ConfusionEffect extends MobEffect {
   public ConfusionEffect() {
      super(MobEffectCategory.HARMFUL, 6829738);
   }

   @EventBusSubscriber(modid = "relics", value = Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void onMovementInput(MovementInputUpdateEvent event) {
         Player player = event.getEntity();
         if (player.hasEffect(EffectRegistry.CONFUSION)) {
            Input input = event.getInput();
            input.leftImpulse *= -1.0F;
            input.forwardImpulse *= -1.0F;
         }
      }
   }
}
