package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.StymphalianBirdEntity;
import com.iafenvoy.iceandfire.render.model.StymphalianBirdModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class StymphalianBirdEntityRenderer extends MobRenderer<StymphalianBirdEntity, StymphalianBirdModel> {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/stymphalianbird/stymphalian_bird.png");

   public StymphalianBirdEntityRenderer(Context context) {
      super(context, new StymphalianBirdModel(), 0.6F);
   }

   public void scale(StymphalianBirdEntity LivingEntityIn, PoseStack stack, float partialTickTime) {
      stack.scale(0.75F, 0.75F, 0.75F);
   }

   public ResourceLocation getTexture(StymphalianBirdEntity cyclops) {
      return TEXTURE;
   }
}
