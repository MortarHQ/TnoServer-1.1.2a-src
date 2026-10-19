package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.block.entity.LightningWoodLingzhiBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public final class LightningWoodLingzhiRenderer extends GeoBlockRenderer<LightningWoodLingzhiBlockEntity> {
   public LightningWoodLingzhiRenderer(Context context) {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "lightning_wood_lingzhi")));
      this.addRenderLayer(new AutoGlowingGeoLayer(this));
   }
}
