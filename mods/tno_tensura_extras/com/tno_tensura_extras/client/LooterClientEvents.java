package com.tno_tensura_extras.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tno_tensura_extras.util.LooterManager;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.event.GeoRenderEvent.Entity.CompileRenderLayers;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public final class LooterClientEvents {
   private static final Set<UUID> KNOWN_SHADOWS = ConcurrentHashMap.newKeySet();

   private LooterClientEvents() {
   }

   public static void setKnownShadow(UUID entityId, boolean shadow) {
      if (entityId != null) {
         if (shadow) {
            KNOWN_SHADOWS.add(entityId);
         } else {
            KNOWN_SHADOWS.remove(entityId);
         }
      }
   }

   static boolean isShadowClient(LivingEntity entity) {
      return KNOWN_SHADOWS.contains(entity.getUUID()) || LooterManager.isShadow(entity);
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != null && minecraft.player != null) {
         for (LivingEntity living : level.getEntitiesOfClass(
            LivingEntity.class,
            minecraft.player.getBoundingBox().inflate(64.0),
            livingx -> livingx.isAlive() && !livingx.isInvisible() && isShadowClient(livingx)
         )) {
            LooterAuraRenderer.tickParticles(living);
         }
      }
   }

   public static void onAddLayers(AddLayers event) {
      for (EntityType<?> entityType : event.getEntityTypes()) {
         if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            addShadowLayer(livingRenderer);
         }
      }
   }

   public static void onCompileGeoLayers(CompileRenderLayers event) {
      GeoEntityRenderer renderer = event.getRenderer();
      event.addLayer(new LooterClientEvents.GeoShadowLayer(renderer));
   }

   private static void addShadowLayer(LivingEntityRenderer<?, ?> renderer) {
      renderer.addLayer(new LooterClientEvents.ShadowLayer(renderer));
   }

   private static final class GeoShadowLayer<T extends Entity & GeoAnimatable> extends GeoRenderLayer<T> {
      private GeoShadowLayer(GeoEntityRenderer<T> renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         T animatable,
         BakedGeoModel bakedModel,
         RenderType renderType,
         MultiBufferSource bufferSource,
         VertexConsumer buffer,
         float partialTick,
         int packedLight,
         int packedOverlay
      ) {
         if (animatable instanceof LivingEntity living && LooterClientEvents.isShadowClient(living) && !living.isInvisible()) {
            GeoRenderer<T> renderer = this.getRenderer();
            ResourceLocation texture = renderer.getTextureLocation(animatable);
            RenderType shadowType = renderer.getRenderType(animatable, texture, bufferSource, partialTick);
            if (shadowType != null) {
               VertexConsumer shadowBuffer = bufferSource.getBuffer(shadowType);
               renderer.reRender(bakedModel, poseStack, bufferSource, animatable, shadowType, shadowBuffer, partialTick, packedLight, packedOverlay, -12961222);
               LooterAuraRenderer.render(poseStack, bufferSource, living, partialTick, packedLight, packedOverlay);
            }
         }
      }
   }

   private static final class ShadowLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
      private final RenderLayerParent<T, M> parent;

      private ShadowLayer(RenderLayerParent<T, M> parent) {
         super(parent);
         this.parent = parent;
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         T entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (LooterClientEvents.isShadowClient(entity) && !entity.isInvisible()) {
            ResourceLocation texture = this.parent.getTextureLocation(entity);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(texture));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), -12961222);
            LooterAuraRenderer.render(poseStack, buffer, entity, partialTick, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
         }
      }
   }
}
