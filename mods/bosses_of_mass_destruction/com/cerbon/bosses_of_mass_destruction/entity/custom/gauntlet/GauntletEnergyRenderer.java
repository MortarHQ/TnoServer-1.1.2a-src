package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GauntletEnergyRenderer implements IRendererWithModel, IRenderer<GauntletEntity> {
   private final GeoModel<GauntletEntity> geoModel;
   private final Context context;
   private final ResourceLocation armorTexture = ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/obsidilith_armor.png");
   private GauntletEnergyRenderer.RenderHelper geoModelProvider;
   private GauntletEntity gauntletEntity;
   private RenderType type;

   public GauntletEnergyRenderer(GeoModel<GauntletEntity> geoModel, Context context) {
      this.geoModel = geoModel;
      this.context = context;
   }

   public void render(GauntletEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float renderAge = entity.tickCount + partialTicks;
      float textureOffset = renderAge * 0.01F;
      if (this.geoModelProvider == null) {
         this.geoModelProvider = new GauntletEnergyRenderer.RenderHelper(entity, this.geoModel, this.context);
      }

      this.gauntletEntity = entity;
      this.type = RenderType.energySwirl(this.armorTexture, textureOffset, textureOffset);
   }

   @Override
   public void render(
      BakedGeoModel model,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      VertexConsumer energyBuffer = buffer.getBuffer(this.type);
      if (this.gauntletEntity != null) {
         if (this.type != null) {
            float renderAlpha = this.gauntletEntity.energyShieldHandler.getRenderAlpha();
            if (renderAlpha != 0.0F) {
               float lerpedAlpha = Mth.lerp(partialTicks, renderAlpha - 0.1F, renderAlpha);
               int colour = ARGB32.colorFromFloat(lerpedAlpha, 0.8F * lerpedAlpha, 0.2F * lerpedAlpha, 0.2F * lerpedAlpha);
               if (this.geoModelProvider != null) {
                  this.geoModelProvider
                     .actuallyRender(
                        poseStack,
                        this.gauntletEntity,
                        model,
                        this.type,
                        buffer,
                        energyBuffer,
                        false,
                        partialTicks,
                        packedLightIn,
                        OverlayTexture.NO_OVERLAY,
                        colour
                     );
               }
            }
         }
      }
   }

   private static class RenderHelper extends GeoEntityRenderer<GauntletEntity> {
      private final GauntletEntity gauntletEntity;

      public RenderHelper(GauntletEntity gauntletEntity, GeoModel<GauntletEntity> parentModel, Context context) {
         super(context, parentModel);
         this.gauntletEntity = gauntletEntity;
      }

      public void renderCube(PoseStack poseStack, GeoCube cube, VertexConsumer buffer, int packedLight, int packedOverlay, int colour) {
         poseStack.pushPose();
         poseStack.scale(1.1F, 1.05F, 1.1F);
         super.renderCube(poseStack, cube, buffer, 15728880, packedOverlay, colour);
         poseStack.popPose();
      }

      public GauntletEntity getAnimatable() {
         return this.gauntletEntity;
      }
   }
}
