package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.LaserModel;
import com.benji.netherman.common.entity.LaserEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LaserRenderer extends GeoEntityRenderer<LaserEntity> {
   public LaserRenderer(Context renderManager) {
      super(renderManager, new LaserModel());
      this.shadowRadius = 0.0F;
      ResourceLocation emissiveTexture = NetherExp.location("textures/entity/laser_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }

   public boolean shouldRender(LaserEntity entity, Frustum camera, double camX, double camY, double camZ) {
      return true;
   }
}
