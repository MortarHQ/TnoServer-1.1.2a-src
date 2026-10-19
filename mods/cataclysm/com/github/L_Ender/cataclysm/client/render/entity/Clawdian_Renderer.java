package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Clawdian_Model;
import com.github.L_Ender.cataclysm.client.render.layer.Clawdian_Hold_Block_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.Clawdian_Hold_Entity_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.LayerGenericGlowing;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters.Clawdian_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Clawdian_Renderer extends MobRenderer<Clawdian_Entity, Clawdian_Model> {
   private static final ResourceLocation KOBOLEDIATOR_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/sea/shrimp.png");
   private static final ResourceLocation LAYER = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/sea/shrimp_glow.png");

   public Clawdian_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Clawdian_Model(renderManagerIn.bakeLayer(CMModelLayers.CLAWDIAN_MODEL)), 1.8F);
      this.addLayer(new LayerGenericGlowing(this, LAYER));
      this.addLayer(new Clawdian_Hold_Block_Layer(this, renderManagerIn.getBlockRenderDispatcher()));
      this.addLayer(new Clawdian_Hold_Entity_Layer(this, renderManagerIn.getEntityRenderDispatcher()));
   }

   public ResourceLocation getTextureLocation(Clawdian_Entity entity) {
      return KOBOLEDIATOR_TEXTURES;
   }

   protected float getFlipDegrees(Clawdian_Entity entity) {
      return 0.0F;
   }

   protected void scale(Clawdian_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.1F, 1.1F, 1.1F);
   }
}
