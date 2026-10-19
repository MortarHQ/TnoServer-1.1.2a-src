package com.mortal_cultivation.client.model;

import com.mortal_cultivation.item.SpiritGlassesItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public final class SpiritGlassesGeoModel extends DefaultedItemGeoModel<SpiritGlassesItem> {
   public SpiritGlassesGeoModel() {
      super(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_glasses"));
      this.withAltTexture(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_glasses/spirit_glasses_model"));
   }

   public RenderType getRenderType(SpiritGlassesItem animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }
}
