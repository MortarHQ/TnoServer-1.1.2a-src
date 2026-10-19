package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.FacePuzzleRightDownModel;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FacePuzzleRightDownRenderer extends GeoBlockRenderer<FacePuzzleBlockEntity> {
   public FacePuzzleRightDownRenderer(Context context) {
      super(new FacePuzzleRightDownModel());
   }
}
