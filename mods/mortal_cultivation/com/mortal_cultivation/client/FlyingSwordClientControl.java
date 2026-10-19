package com.mortal_cultivation.client;

import com.mortal_cultivation.entity.FlyingSwordEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME, value = Dist.CLIENT)
public final class FlyingSwordClientControl {
   private FlyingSwordClientControl() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onMovementInput(MovementInputUpdateEvent event) {
      if (event.getEntity().getVehicle() instanceof FlyingSwordEntity sword) {
         Input var5 = event.getInput();
         boolean descending = Minecraft.getInstance().options.keySprint.isDown();
         float vertical = (var5.jumping ? 1.0F : 0.0F) - (descending ? 1.0F : 0.0F);
         sword.setLocalControlInput(var5.leftImpulse, var5.forwardImpulse, vertical);
      }
   }
}
