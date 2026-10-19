package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.HydraEntity;
import com.iafenvoy.iceandfire.render.entity.feature.GenericGlowingFeatureRenderer;
import com.iafenvoy.iceandfire.render.entity.feature.HydraHeadFeatureRenderer;
import com.iafenvoy.iceandfire.render.model.HydraBodyModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class HydraEntityRenderer extends MobRenderer<HydraEntity, HydraBodyModel> {
   public static final ResourceLocation TEXUTURE_0 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hydra/hydra_0.png");
   public static final ResourceLocation TEXUTURE_1 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hydra/hydra_1.png");
   public static final ResourceLocation TEXUTURE_2 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hydra/hydra_2.png");
   public static final ResourceLocation TEXUTURE_EYES = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/hydra/hydra_eyes.png");

   public HydraEntityRenderer(Context context) {
      super(context, new HydraBodyModel(), 1.2F);
      this.addLayer(new HydraHeadFeatureRenderer(this));
      this.addLayer(new GenericGlowingFeatureRenderer(this, TEXUTURE_EYES));
   }

   public void scale(HydraEntity LivingEntityIn, PoseStack stack, float partialTickTime) {
      stack.scale(1.75F, 1.75F, 1.75F);
   }

   public ResourceLocation getTexture(HydraEntity gorgon) {
      return switch (gorgon.getVariant()) {
         case 1 -> TEXUTURE_1;
         case 2 -> TEXUTURE_2;
         default -> TEXUTURE_0;
      };
   }
}
