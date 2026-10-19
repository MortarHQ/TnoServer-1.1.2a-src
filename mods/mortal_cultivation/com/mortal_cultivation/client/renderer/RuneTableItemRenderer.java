package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.item.RuneTableItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class RuneTableItemRenderer extends GeoItemRenderer<RuneTableItem> {
   public RuneTableItemRenderer() {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "rune_table")));
   }
}
