package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.AzazelGuideBookModel;
import com.benji.netherman.common.entity.AzazelGuideBookEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelGuideBookRenderer extends GeoEntityRenderer<AzazelGuideBookEntity> {
   public AzazelGuideBookRenderer(Context renderManager) {
      super(renderManager, new AzazelGuideBookModel());
   }
}
