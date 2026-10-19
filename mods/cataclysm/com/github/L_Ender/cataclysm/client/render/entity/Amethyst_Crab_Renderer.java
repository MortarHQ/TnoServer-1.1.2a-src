package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Amethyst_Crab_Model;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Amethyst_Crab_Renderer extends MobRenderer<Amethyst_Crab_Entity, Amethyst_Crab_Model> {
   private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/amethyst_crab.png");
   private static final ResourceLocation KRABS_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/mr_amethyst_krabs.png");

   public Amethyst_Crab_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Amethyst_Crab_Model(), 1.5F);
   }

   public ResourceLocation getTextureLocation(Amethyst_Crab_Entity entity) {
      return entity.isKrusty() ? KRABS_TEXTURES : TEXTURES;
   }

   protected void scale(Amethyst_Crab_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
   }

   protected float getFlipDegrees(Amethyst_Crab_Entity entity) {
      return 0.0F;
   }
}
