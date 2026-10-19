package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.FacePuzzleRightUpModel;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FacePuzzleRightUpRenderer extends GeoBlockRenderer<FacePuzzleBlockEntity> {
   public FacePuzzleRightUpRenderer(Context context) {
      super(new FacePuzzleRightUpModel());
   }
}
