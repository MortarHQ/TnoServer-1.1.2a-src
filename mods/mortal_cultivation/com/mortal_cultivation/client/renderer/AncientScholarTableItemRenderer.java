package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.item.AncientScholarTableItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public final class AncientScholarTableItemRenderer extends GeoItemRenderer<AncientScholarTableItem> {
   public AncientScholarTableItemRenderer() {
      super(new DefaultedBlockGeoModel(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "ancient_scholar_table")));
   }
}
