package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.client.model.BerettaModel;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class BerettaRenderer extends GeoEntityRenderer<BerettaEntity> {
   public BerettaRenderer(Context context) {
      super(context, new BerettaModel());
      this.shadowRadius = 1.7F;
      this.scaleWidth = 0.78F;
      this.scaleHeight = 0.78F;
   }

   public RenderType getRenderType(BerettaEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
      return RenderType.entityCutoutNoCull(texture);
   }
}
