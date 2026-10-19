package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Hippocamtus_Model;
import com.github.L_Ender.cataclysm.client.render.layer.Hippocamtus_Layer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters.Hippocamtus_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Hippocamtus_Renderer extends MobRenderer<Hippocamtus_Entity, Hippocamtus_Model> {
   private static final ResourceLocation KOBOLEDIATOR_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/sea/hippocamtus.png");

   public Hippocamtus_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Hippocamtus_Model(renderManagerIn.bakeLayer(CMModelLayers.HIPPOCAMTUS_MODEL)), 0.75F);
      this.addLayer(new Hippocamtus_Layer(this));
   }

   public ResourceLocation getTextureLocation(Hippocamtus_Entity entity) {
      return KOBOLEDIATOR_TEXTURES;
   }

   protected float getFlipDegrees(Hippocamtus_Entity entity) {
      return 0.0F;
   }

   protected void scale(Hippocamtus_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
   }
}
