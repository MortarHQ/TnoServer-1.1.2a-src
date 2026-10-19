package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AzazelModel;
import com.benji.netherman.common.entity.AzazelEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelRenderer extends GeoEntityRenderer<AzazelEntity> {
   public AzazelRenderer(Context renderManager) {
      super(renderManager, new AzazelModel());
      this.shadowRadius = 1.5F;
      ResourceLocation emissiveTexture = NetherExp.location("textures/entity/azazel_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }
}
