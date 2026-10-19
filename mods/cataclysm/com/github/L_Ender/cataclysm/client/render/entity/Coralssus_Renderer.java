package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Coralssus_Model;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Coralssus_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Coralssus_Renderer extends MobRenderer<Coralssus_Entity, Coralssus_Model> {
   private static final ResourceLocation FIRE_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/coralssus_fire.png");
   private static final ResourceLocation HORN_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/coralssus_horn.png");
   private static final ResourceLocation TUBE_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/coralssus_tube.png");
   private static final ResourceLocation SPONGE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/deepling/coralssus_sponge_horn.png"
   );

   public Coralssus_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Coralssus_Model(), 1.7F);
   }

   public ResourceLocation getTextureLocation(Coralssus_Entity entity) {
      if (entity.isSponge()) {
         return SPONGE_TEXTURE;
      }

      return switch (entity.getVariant()) {
         case FIRE -> FIRE_TEXTURE;
         case HORN -> HORN_TEXTURE;
         case TUBE -> TUBE_TEXTURE;
      };
   }

   protected float getFlipDegrees(Coralssus_Entity entity) {
      return 0.0F;
   }

   protected void scale(Coralssus_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.35F, 1.35F, 1.35F);
   }
}
