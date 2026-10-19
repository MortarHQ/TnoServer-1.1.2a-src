package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.EyeModel;
import com.benji.netherman.common.block.entity.EyeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EyeRenderer extends GeoBlockRenderer<EyeBlockEntity> {
   public EyeRenderer(Context context) {
      super(new EyeModel());
      ResourceLocation emissiveTexture = NetherExp.location("textures/block/eye_block_emissive.png");
      this.addRenderLayer(new GenericEmissiveLayer(this, emissiveTexture));
   }
}
