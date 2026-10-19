package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.The_Baby_Leviathan_Model;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class The_Baby_Leviathan_Renderer extends MobRenderer<The_Baby_Leviathan_Entity, The_Baby_Leviathan_Model> {
   private static final ResourceLocation BABY_LEVIATHAN_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/the_baby_leviathan.png"
   );

   public The_Baby_Leviathan_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new The_Baby_Leviathan_Model(), 0.25F);
   }

   public ResourceLocation getTextureLocation(The_Baby_Leviathan_Entity entity) {
      return BABY_LEVIATHAN_TEXTURES;
   }

   protected void scale(The_Baby_Leviathan_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
   }
}
