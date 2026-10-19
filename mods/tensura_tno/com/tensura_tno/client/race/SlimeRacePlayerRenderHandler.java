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
public final class SlimeRacePlayerRenderHandler {
   private SlimeRacePlayerRenderHandler() {
   }

   @SubscribeEvent
   public static void onRenderPlayerPre(Pre event) {
      if (TensuraTNOCompatConfig.isRacePlayerModelsEnabled()) {
         Player player = event.getEntity();
         if (player.isSpectator() && PlayerSlimeRenderManager.isSlimeRace(player)) {
            event.setCanceled(true);
         } else if (PlayerSlimeRenderManager.shouldRenderAsSlime(player)) {
            event.setCanceled(true);
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();

            try {
               PlayerSlimeRenderManager.render(player, poseStack, event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick());
            } catch (Throwable t) {
               TensuraTNOMod.LOGGER.error("[TensuraTNO] Failed to render slime for player {}: {}", player.getGameProfile().getName(), t.toString());
            }

            poseStack.popPose();
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLogout(PlayerLoggedOutEvent event) {
      PlayerSlimeRenderManager.forget(event.getEntity().getUUID());
   }
}
