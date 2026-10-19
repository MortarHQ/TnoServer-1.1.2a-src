package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import com.mortal_cultivation.client.model.AncientSteleGeoModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public final class AncientSteleRenderer extends GeoBlockRenderer<AncientSteleBlockEntity> {
   public AncientSteleRenderer(Context context) {
      super(new AncientSteleGeoModel());
      this.addRenderLayer(new AncientSteleRuneLayer(this));
   }

   public AABB getRenderBoundingBox(AncientSteleBlockEntity blockEntity) {
      return new AABB(blockEntity.getBlockPos()).inflate(2.0);
   }
}
