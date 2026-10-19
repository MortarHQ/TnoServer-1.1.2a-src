package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.AzazelArmorModel;
import com.benji.netherman.common.item.AzazelArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AzazelArmorRenderer extends GeoArmorRenderer<AzazelArmorItem> {
   public AzazelArmorRenderer() {
      super(new AzazelArmorModel());
   }
}
