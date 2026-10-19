package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.DreadLichEntity;
import com.iafenvoy.iceandfire.render.entity.feature.GenericGlowingFeatureRenderer;
import com.iafenvoy.iceandfire.render.model.DreadLichModel;
import com.iafenvoy.uranus.client.model.util.HideableLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class DreadLichEntityRenderer extends MobRenderer<DreadLichEntity, DreadLichModel> {
   public static final ResourceLocation TEXTURE_EYES = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_eyes.png");
   public static final ResourceLocation TEXTURE_0 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_0.png");
   public static final ResourceLocation TEXTURE_1 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_1.png");
   public static final ResourceLocation TEXTURE_2 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_2.png");
   public static final ResourceLocation TEXTURE_3 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_3.png");
   public static final ResourceLocation TEXTURE_4 = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/dread/dread_lich_4.png");
   public final HideableLayer<DreadLichEntity, DreadLichModel, ItemInHandLayer<DreadLichEntity, DreadLichModel>> itemLayer;

   public DreadLichEntityRenderer(Context context) {
      super(context, new DreadLichModel(0.0F), 0.6F);
      this.addLayer(new GenericGlowingFeatureRenderer(this, TEXTURE_EYES));
      this.itemLayer = new HideableLayer(new ItemInHandLayer(this, context.getItemInHandRenderer()), this);
      this.addLayer(this.itemLayer);
   }

   protected void scale(DreadLichEntity entity, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(0.95F, 0.95F, 0.95F);
      if (entity.getAnimation() == ((DreadLichModel)this.getModel()).getSpawnAnimation()) {
         this.itemLayer.hidden = entity.getAnimationTick() <= ((DreadLichModel)this.getModel()).getSpawnAnimation().getDuration() - 10;
      } else {
         this.itemLayer.hidden = false;
      }
   }

   public ResourceLocation getTexture(DreadLichEntity entity) {
      return switch (entity.getVariant()) {
         case 1 -> TEXTURE_1;
         case 2 -> TEXTURE_2;
         case 3 -> TEXTURE_3;
         case 4 -> TEXTURE_4;
         default -> TEXTURE_0;
      };
   }
}
