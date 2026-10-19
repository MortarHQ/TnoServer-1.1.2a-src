package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.ManipulatorModel;
import com.benji.netherman.common.entity.ManipulatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ManipulatorRenderer extends GeoEntityRenderer<ManipulatorEntity> {
   public ManipulatorRenderer(Context renderManager) {
      super(renderManager, new ManipulatorModel());
      this.addRenderLayer(new GenericEmissiveLayer(this, NetherExp.location("textures/entity/manipulator_emissive.png")));
   }
}
