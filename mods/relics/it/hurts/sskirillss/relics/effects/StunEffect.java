package it.hurts.sskirillss.relics.effects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.models.effects.StunStarModel;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.RenderHighlightEvent.Block;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;

public class StunEffect extends MobEffect {
   public StunEffect() {
      super(MobEffectCategory.HARMFUL, 6829738);
   }

   @EventBusSubscriber(modid = "relics", value = Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void onMouseInput(InteractionKeyMappingTriggered event) {
         Player player = Minecraft.getInstance().player;
         if (player != null && player.hasEffect(EffectRegistry.STUN)) {
            event.setSwingHand(false);
            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onBlockHighlight(Block event) {
         Player player = Minecraft.getInstance().player;
         if (player != null && player.hasEffect(EffectRegistry.STUN)) {
            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onEntityRender(Pre<?, ?> event) {
         LivingEntity entity = event.getEntity();
         if (entity.hasEffect(EffectRegistry.STUN) && !entity.isDeadOrDying()) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(0.0F, entity.getBbHeight() + 0.15F, 0.0F);
            poseStack.scale(0.175F, 0.175F, 0.175F);
            poseStack.scale(1.0F, -1.0F, 1.0F);
            int stars = 10;
            float ticks = entity.tickCount + event.getPartialTick();

            for (int i = 0; i < stars; i++) {
               poseStack.pushPose();
               poseStack.translate(0.0F, Mth.sin(ticks * 0.1F + i * 20) * 0.2F, 0.0F);
               poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.cos(ticks / 10.0F) / 7.0F * (180.0F / (float)Math.PI)));
               poseStack.mulPose(Axis.YP.rotationDegrees(ticks / 20.0F * (180.0F / (float)Math.PI) + i * (360.0F / stars)));
               poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(ticks / 10.0F) / 7.0F * (180.0F / (float)Math.PI)));
               poseStack.translate(0.0F, 0.0F, 1.0F + stars * 0.15F);
               StunStarModel.createBodyLayer()
                  .bakeRoot()
                  .render(
                     event.getPoseStack(),
                     event.getMultiBufferSource().getBuffer(RenderType.entityCutout(StunStarModel.TEXTURE.getModel())),
                     event.getPackedLight(),
                     OverlayTexture.NO_OVERLAY
                  );
               poseStack.popPose();
            }

            poseStack.popPose();
         }
      }
   }
}
