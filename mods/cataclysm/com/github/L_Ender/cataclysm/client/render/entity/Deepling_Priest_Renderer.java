package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Deepling_Priest_Model;
import com.github.L_Ender.cataclysm.client.render.layer.AbstractDeepling_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.LayerDeepling_PriestItem;
import com.github.L_Ender.cataclysm.client.render.layer.LayerDeepling_Priest_Light;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Priest_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Deepling_Priest_Renderer extends MobRenderer<Deepling_Priest_Entity, Deepling_Priest_Model> {
   private static final ResourceLocation DEEPLING_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/deepling/deepling_priest.png");
   private static final ResourceLocation DEEPLING_LAYER_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/deepling/deepling_priest_layer.png"
   );

   public Deepling_Priest_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Deepling_Priest_Model(), 0.7F);
      this.addLayer(new AbstractDeepling_Layer(this, DEEPLING_LAYER_TEXTURES));
      this.addLayer(new LayerDeepling_PriestItem(this, renderManagerIn.getItemInHandRenderer()));
      this.addLayer(new LayerDeepling_Priest_Light(this));
   }

   public ResourceLocation getTextureLocation(Deepling_Priest_Entity entity) {
      return DEEPLING_TEXTURES;
   }

   protected void scale(Deepling_Priest_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.0F, 1.0F, 1.0F);
   }
}
