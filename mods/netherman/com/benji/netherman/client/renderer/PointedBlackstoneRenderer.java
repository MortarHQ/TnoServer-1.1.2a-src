package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.PointedBlackstoneModel;
import com.benji.netherman.common.block.entity.PointedBlackstoneBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PointedBlackstoneRenderer extends GeoBlockRenderer<PointedBlackstoneBlockEntity> {
   public PointedBlackstoneRenderer(Context context) {
      super(new PointedBlackstoneModel());
   }
}
