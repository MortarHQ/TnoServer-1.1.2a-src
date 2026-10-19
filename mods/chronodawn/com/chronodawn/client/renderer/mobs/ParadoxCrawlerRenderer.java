package com.chronodawn.client.renderer.mobs;

import com.chronodawn.client.model.ParadoxCrawlerModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ParadoxCrawlerEntity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ParadoxCrawlerRenderer extends MobRenderer<ParadoxCrawlerEntity, ParadoxCrawlerModel> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/mobs/paradox_crawler.png");

   public ParadoxCrawlerRenderer(Context context) {
      super(context, new ParadoxCrawlerModel(context.bakeLayer(ParadoxCrawlerModel.LAYER_LOCATION)), 0.8F);
      this.addLayer(new ParadoxCrawlerEmissiveLayer(this));
   }

   public ResourceLocation getTextureLocation(ParadoxCrawlerEntity entity) {
      return TEXTURE;
   }
}
