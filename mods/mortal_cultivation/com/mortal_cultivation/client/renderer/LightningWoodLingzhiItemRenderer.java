package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.item.LightningWoodLingzhiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public final class LightningWoodLingzhiItemRenderer extends GeoItemRenderer<LightningWoodLingzhiItem> {
   public LightningWoodLingzhiItemRenderer() {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "lightning_wood_lingzhi")));
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }
}
