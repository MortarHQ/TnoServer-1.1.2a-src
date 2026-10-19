package com.tensura_tno.client.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.TensuraTNOMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent.Pre;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

@EventBusSubscriber(modid = "tensura_tno", value = Dist.CLIENT)
public final class FoxSpiritRacePlayerRenderHandler {
   private FoxSpiritRacePlayerRenderHandler() {
   }

   @SubscribeEvent
   public static void onRenderPlayerPre(Pre event) {
      if (TensuraTNOCompatConfig.isRacePlayerModelsEnabled()) {
         Player player = event.getEntity();
         if (player.isSpectator() && PlayerFoxSpiritRenderManager.isFoxSpiritRace(player)) {
            event.setCanceled(true);
         } else if (PlayerFoxSpiritRenderManager.shouldRenderAsFoxSpirit(player)) {
            event.setCanceled(true);
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();

            try {
               PlayerFoxSpiritRenderManager.render(player, poseStack, event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick());
            } catch (Throwable throwable) {
               TensuraTNOMod.LOGGER
                  .error("[TensuraTNO] Failed to render fox-spirit model for player {}: {}", player.getGameProfile().getName(), throwable.toString());
            } finally {
               poseStack.popPose();
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      PlayerFoxSpiritRenderManager.forget(event.getEntity().getUUID());
   }
}
