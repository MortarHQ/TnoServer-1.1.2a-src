package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.FacePuzzleLeftDownModel;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FacePuzzleLeftDownRenderer extends GeoBlockRenderer<FacePuzzleBlockEntity> {
   public FacePuzzleLeftDownRenderer(Context context) {
      super(new FacePuzzleLeftDownModel());
   }
}
