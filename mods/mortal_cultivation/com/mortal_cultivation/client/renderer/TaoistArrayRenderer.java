package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.client.model.TaoistArrayModel;
import com.mortal_cultivation.entity.TaoistArrayEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TaoistArrayRenderer extends GeoEntityRenderer<TaoistArrayEntity> {
   public TaoistArrayRenderer(Context renderManager) {
      super(renderManager, new TaoistArrayModel());
   }

   public ResourceLocation getTextureLocation(TaoistArrayEntity entity) {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/effects/taoist_array/taoist_array.png");
   }
}
