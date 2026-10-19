package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.DreadScuttlerEntity;
import com.iafenvoy.iceandfire.render.entity.feature.GenericGlowingFeatureRenderer;
import com.iafenvoy.iceandfire.render.model.DreadScuttlerModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DreadScuttlerEntityRenderer extends MobRenderer<DreadScuttlerEntity, DreadScuttlerModel> {
   public static final ResourceLocation TEXTURE_EYES = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_scuttler_eyes.png");
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_scuttler.png");

   public DreadScuttlerEntityRenderer(Context context) {
      super(context, new DreadScuttlerModel(), 0.75F);
      this.addLayer(new GenericGlowingFeatureRenderer(this, TEXTURE_EYES));
   }

   public void scale(DreadScuttlerEntity LivingEntityIn, PoseStack stack, float partialTickTime) {
      stack.scale(LivingEntityIn.getSize(), LivingEntityIn.getSize(), LivingEntityIn.getSize());
   }

   public ResourceLocation getTexture(DreadScuttlerEntity beast) {
      return TEXTURE;
   }
}
