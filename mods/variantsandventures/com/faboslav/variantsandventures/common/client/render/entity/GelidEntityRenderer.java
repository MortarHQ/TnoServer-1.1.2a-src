package com.faboslav.variantsandventures.common.client.render.entity;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.GelidEntityModel;
import com.faboslav.variantsandventures.common.client.render.entity.feature.GelidOverlayFeatureRenderer;
import com.faboslav.variantsandventures.common.entity.mob.GelidEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GelidEntityRenderer extends AbstractZombieRenderer<GelidEntity, GelidEntityModel<GelidEntity>> {
   public static final ResourceLocation TEXTURE = VariantsAndVentures.makeID("textures/entity/gelid/gelid.png");

   public GelidEntityRenderer(Context context) {
      super(
         context,
         new GelidEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.GELID)),
         new GelidEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.GELID_INNER_ARMOR)),
         new GelidEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.GELID_OUTER_ARMOR))
      );
      this.addLayer(new GelidOverlayFeatureRenderer(this, context.getModelSet()));
   }

   public ResourceLocation getTextureLocation(GelidEntity gelid) {
      return TEXTURE;
   }

   protected void scale(GelidEntity gelid, PoseStack matrixStack, float partialTick) {
      matrixStack.scale(1.0625F, 1.0625F, 1.0625F);
      super.scale(gelid, matrixStack, partialTick);
   }

   protected void setupRotations(GelidEntity gelid, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
      super.setupRotations(gelid, matrices, animationProgress, bodyYaw, tickDelta, scale);
      float h = gelid.getSwimAmount(tickDelta);
      if (h > 0.0F) {
         float angle = Mth.lerp(h, gelid.getXRot(), -10.0F - gelid.getXRot());
         matrices.mulPose(Axis.XP.rotationDegrees(angle));
      }
   }
}
