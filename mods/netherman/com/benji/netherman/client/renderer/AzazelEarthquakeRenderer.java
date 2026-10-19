package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.AzazelEarthquakeModel;
import com.benji.netherman.common.entity.AzazelEarthquakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelEarthquakeRenderer extends GeoEntityRenderer<AzazelEarthquakeEntity> {
   public AzazelEarthquakeRenderer(Context renderManager) {
      super(renderManager, new AzazelEarthquakeModel());
   }
}
