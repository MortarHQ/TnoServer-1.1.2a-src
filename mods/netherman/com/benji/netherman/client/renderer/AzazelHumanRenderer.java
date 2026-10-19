package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AzazelHumanModel;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelHumanRenderer extends GeoEntityRenderer<AzazelHumanEntity> {
   public AzazelHumanRenderer(Context renderManager) {
      super(renderManager, new AzazelHumanModel());
      this.shadowRadius = 1.5F;
      ResourceLocation emissiveTexture = NetherExp.location("textures/entity/azazel_human_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }

   protected int getBlockLightLevel(AzazelHumanEntity entity, BlockPos pos) {
      return 15;
   }
}
