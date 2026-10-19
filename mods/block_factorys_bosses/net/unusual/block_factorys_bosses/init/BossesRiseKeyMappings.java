package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.DodgeRollMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(Dist.CLIENT)
public class BossesRiseKeyMappings {
   public static final KeyMapping DODGE_ROLL = new KeyMapping("key.block_factorys_bosses.dodge_roll", 90, "key.categories.movement") {
      private boolean isDownOld = false;

      public void setDown(boolean isDown) {
         super.setDown(isDown);
         if (this.isDownOld != isDown && isDown) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
               PacketDistributor.sendToServer(new DodgeRollMessage(0, player.input.leftImpulse, player.input.forwardImpulse), new CustomPacketPayload[0]);
               DodgeRollMessage.pressAction(player, 0, player.input.leftImpulse, player.input.forwardImpulse);
            }
         }

         this.isDownOld = isDown;
      }
   };

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(DODGE_ROLL);
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class KeyEventListener {
      @SubscribeEvent
      public static void onClientTick(Post event) {
         if (Minecraft.getInstance().screen == null) {
            BossesRiseKeyMappings.DODGE_ROLL.consumeClick();
         }
      }
   }
}
