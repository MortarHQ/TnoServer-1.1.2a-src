package com.faboslav.variantsandventures.common.client.render.entity;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.ThicketEntityModel;
import com.faboslav.variantsandventures.common.client.render.entity.feature.ThicketOverlayFeatureRenderer;
import com.faboslav.variantsandventures.common.entity.mob.ThicketEntity;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThicketEntityRenderer extends AbstractZombieRenderer<ThicketEntity, ThicketEntityModel<ThicketEntity>> {
   public static final ResourceLocation TEXTURE = VariantsAndVentures.makeID("textures/entity/thicket/thicket.png");

   public ThicketEntityRenderer(Context context) {
      super(
         context,
         new ThicketEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.THICKET)),
         new ThicketEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.THICKET_INNER_ARMOR)),
         new ThicketEntityModel(context.bakeLayer(VariantsAndVenturesModelLayers.THICKET_OUTER_ARMOR))
      );
      this.addLayer(new ThicketOverlayFeatureRenderer(this, context.getModelSet()));
   }

   public ResourceLocation getTextureLocation(ThicketEntity thicket) {
      return TEXTURE;
   }

   protected void setupRotations(ThicketEntity thicket, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
      super.setupRotations(thicket, matrices, animationProgress, bodyYaw, tickDelta, scale);
      float h = thicket.getSwimAmount(tickDelta);
      if (h > 0.0F) {
         float angle = Mth.lerp(h, thicket.getXRot(), -10.0F - thicket.getXRot());
         matrices.mulPose(Axis.XP.rotationDegrees(angle));
      }
   }
}
