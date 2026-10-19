package com.benji.netherman.client.renderer.item;

import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.common.item.GeoBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GeoBlockItemRenderer extends GeoItemRenderer<GeoBlockItem> {
   public GeoBlockItemRenderer(GeoBlockItem item) {
      super(new GeoModel<GeoBlockItem>() {
         public ResourceLocation getModelResource(GeoBlockItem animatable) {
            return animatable.modelLocation;
         }

         public ResourceLocation getTextureResource(GeoBlockItem animatable) {
            return animatable.textureLocation;
         }

         public ResourceLocation getAnimationResource(GeoBlockItem animatable) {
            return animatable.animationLocation;
         }
      });
      if (item.emissiveTextureLocation != null) {
         this.addRenderLayer(new GenericEmissiveLayer(this, item.emissiveTextureLocation));
      }
   }
}
