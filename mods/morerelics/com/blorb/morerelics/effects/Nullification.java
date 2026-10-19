package com.blorb.morerelics.effects;

import com.blorb.morerelics.MoreRelics;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;
import org.jetbrains.annotations.NotNull;

public class Nullification extends MobEffect {
   public static boolean isRendering = false;

   public Nullification() {
      super(MobEffectCategory.BENEFICIAL, 16777215);
   }

   @EventBusSubscriber(modid = "morerelics", value = Dist.CLIENT)
   public static class ClientEvent {
      private static final Map<ResourceLocation, ResourceLocation> cache = new ConcurrentHashMap<>();
      private static final float OUTLINE_THICKNESS = 0.06F;

      @SubscribeEvent(priority = EventPriority.HIGH)
      public static void onEntityRender(Pre<?, ?> event) {
         LivingEntity entity = event.getEntity();
         if (!entity.isInvisible() && entity.hasEffect(MoreRelics.NULLIFICATION)) {
            if (!Nullification.isRendering) {
               Nullification.isRendering = true;
               BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
               immediate.endBatch();
               PoseStack poseStack = getScaledPoseStack(event, entity);
               setTransparency(1.0F);
               renderTranslucentEntity(event.getEntity(), poseStack, immediate, event.getPackedLight(), event.getPartialTick());
               immediate.endBatch();
               resetTransparency();
               poseStack.popPose();
               Nullification.isRendering = false;
            }
         }
      }

      @NotNull
      private static PoseStack getScaledPoseStack(Pre<?, ?> event, LivingEntity entity) {
         PoseStack poseStack = event.getPoseStack();
         poseStack.pushPose();
         AABB box = entity.getBoundingBox();
         double entityWidth = box.getXsize();
         double entityHeight = box.getYsize();
         double entityDepth = box.getZsize();
         float scaleX = (float)((entityWidth + 0.12F) / entityWidth);
         float scaleY = (float)((entityHeight + 0.12F) / entityHeight);
         float scaleZ = (float)((entityDepth + 0.12F) / entityDepth);
         poseStack.scale(scaleX, scaleY, scaleZ);
         return poseStack;
      }

      private static void setTransparency(float alpha) {
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, alpha);
      }

      private static void renderTranslucentEntity(LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer, int light, float partialTick) {
         if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity) instanceof LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingRenderer
            )
          {
            livingRenderer.render(entity, 0.0F, partialTick, poseStack, buffer, light);
         }
      }

      private static void resetTransparency() {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
      }

      @SubscribeEvent
      public static void onAddLayers(AddLayers event) {
         for (Model skin : event.getSkins()) {
            PlayerRenderer renderer = (PlayerRenderer)event.getSkin(skin);
            if (renderer != null) {
            }
         }
      }
   }
}
