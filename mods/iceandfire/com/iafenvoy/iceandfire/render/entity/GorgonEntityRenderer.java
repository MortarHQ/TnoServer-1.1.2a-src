package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.GorgonEntity;
import com.iafenvoy.iceandfire.render.entity.feature.GorgonEyesFeatureRenderer;
import com.iafenvoy.iceandfire.render.model.GorgonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class GorgonEntityRenderer extends MobRenderer<GorgonEntity, GorgonModel> {
   public static final ResourceLocation PASSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/gorgon/gorgon_passive.png");
   public static final ResourceLocation AGRESSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/gorgon/gorgon_active.png");
   public static final ResourceLocation DEAD_TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/gorgon/gorgon_decapitated.png");

   public GorgonEntityRenderer(Context context) {
      super(context, new GorgonModel(), 0.4F);
      this.layers.add(new GorgonEyesFeatureRenderer(this));
   }

   public void scale(GorgonEntity LivingEntityIn, PoseStack stack, float partialTickTime) {
      stack.scale(0.85F, 0.85F, 0.85F);
   }

   public ResourceLocation getTexture(GorgonEntity gorgon) {
      if (gorgon.getAnimation() == GorgonEntity.ANIMATION_SCARE) {
         return AGRESSIVE_TEXTURE;
      } else {
         return gorgon.deathTime > 0 ? DEAD_TEXTURE : PASSIVE_TEXTURE;
      }
   }
}
