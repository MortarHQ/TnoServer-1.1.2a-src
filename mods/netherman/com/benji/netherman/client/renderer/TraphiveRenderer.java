package com.benji.netherman.client.renderer;

import com.benji.netherman.client.layer.TraphiveAnimatedEmissiveLayer;
import com.benji.netherman.client.model.TraphiveModel;
import com.benji.netherman.common.block.entity.TraphiveBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TraphiveRenderer extends GeoBlockRenderer<TraphiveBlockEntity> {
   public TraphiveRenderer(Context context) {
      super(new TraphiveModel());
      this.addRenderLayer(new TraphiveAnimatedEmissiveLayer(this));
   }

   public RenderType getRenderType(TraphiveBlockEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityTranslucent(texture);
   }
}
