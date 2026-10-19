package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AzazelSpikeModel;
import com.benji.netherman.common.entity.AzazelSpikeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelSpikeRenderer extends GeoEntityRenderer<AzazelSpikeEntity> {
   public AzazelSpikeRenderer(Context renderManager) {
      super(renderManager, new AzazelSpikeModel());
      this.shadowRadius = 0.0F;
      ResourceLocation emissiveTexture = NetherExp.location("textures/entity/azazel_spikes_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }

   protected int getBlockLightLevel(AzazelSpikeEntity entity, BlockPos pos) {
      return 15;
   }
}
