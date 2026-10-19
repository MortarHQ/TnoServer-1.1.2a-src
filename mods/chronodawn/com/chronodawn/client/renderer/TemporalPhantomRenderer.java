package com.chronodawn.client.renderer;

import com.chronodawn.client.model.TemporalPhantomModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.TemporalPhantomEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class TemporalPhantomRenderer extends MobRenderer<TemporalPhantomEntity, TemporalPhantomModel<TemporalPhantomEntity>> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/temporal_phantom.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "temporal_phantom"), "main");

   public TemporalPhantomRenderer(Context context) {
      super(context, new TemporalPhantomModel(context.bakeLayer(LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(TemporalPhantomEntity entity) {
      return TEXTURE;
   }

   protected void scale(TemporalPhantomEntity entity, PoseStack poseStack, float partialTick) {
      super.scale(entity, poseStack, partialTick);
      if (entity.isPhaseShiftActive()) {
      }
   }

   public RenderType getRenderType(TemporalPhantomEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      ResourceLocation texture = this.getTextureLocation(entity);
      return RenderType.entityTranslucent(texture);
   }
}
