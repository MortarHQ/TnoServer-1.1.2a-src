package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ChronoUrsidModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ChronoUrsidEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ChronoUrsidRenderer extends MobRenderer<ChronoUrsidEntity, ChronoUrsidModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/chrono_ursid.png");

   public ChronoUrsidRenderer(Context context) {
      super(context, new ChronoUrsidModel(context.bakeLayer(ChronoUrsidModel.LAYER_LOCATION)), 0.9F);
   }

   public ResourceLocation getTextureLocation(ChronoUrsidEntity entity) {
      return TEXTURE;
   }

   protected void scale(ChronoUrsidEntity entity, PoseStack poseStack, float partialTick) {
      float scale = entity.isBaby() ? 0.6F : 1.2F;
      poseStack.scale(scale, scale, scale);
   }
}
