package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.SecondhandArcherModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.SecondhandArcherEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SecondhandArcherRenderer extends MobRenderer<SecondhandArcherEntity, SecondhandArcherModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/secondhand_archer.png");

   public SecondhandArcherRenderer(Context context) {
      super(context, new SecondhandArcherModel(context.bakeLayer(SecondhandArcherModel.LAYER_LOCATION)), 0.5F);
      this.addLayer(new ItemInHandLayer(this, context.getItemInHandRenderer()));
   }

   public ResourceLocation getTextureLocation(SecondhandArcherEntity entity) {
      return TEXTURE;
   }
}
