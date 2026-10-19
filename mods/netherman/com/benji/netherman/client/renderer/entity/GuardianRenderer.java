package com.benji.netherman.client.renderer.entity;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GuardianEmissiveLayer;
import com.benji.netherman.client.model.GuardianModel;
import com.benji.netherman.common.entity.GuardianEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GuardianRenderer extends GeoEntityRenderer<GuardianEntity> {
   public GuardianRenderer(Context renderManager) {
      super(renderManager, new GuardianModel());
      this.shadowRadius = 1.0F;
      this.addRenderLayer(new GuardianEmissiveLayer(this, NetherExp.location("textures/entity/guardian_emissive.png")));
   }
}
