package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Maledictus_Model;
import com.github.L_Ender.cataclysm.client.render.layer.MaledictusRiderLayer;
import com.github.L_Ender.cataclysm.client.render.layer.Maledictus_Cicle_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.Maledictus_Layer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Maledictus_Renderer extends MobRenderer<Maledictus_Entity, Maledictus_Model> {
   private static final ResourceLocation MALEDICTUS_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/maledictus/maledictus_armor.png"
   );

   public Maledictus_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Maledictus_Model(renderManagerIn.bakeLayer(CMModelLayers.MALEDICTUS_MODEL)), 0.75F);
      this.addLayer(new Maledictus_Layer(this));
      this.addLayer(new Maledictus_Cicle_Layer(this, renderManagerIn));
      this.addLayer(new MaledictusRiderLayer(this));
   }

   public ResourceLocation getTextureLocation(Maledictus_Entity entity) {
      return MALEDICTUS_TEXTURES;
   }

   protected float getFlipDegrees(Maledictus_Entity entity) {
      return 0.0F;
   }

   protected void scale(Maledictus_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
   }
}
