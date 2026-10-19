package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.FacePuzzleLeftUpModel;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FacePuzzleLeftUpRenderer extends GeoBlockRenderer<FacePuzzleBlockEntity> {
   public FacePuzzleLeftUpRenderer(Context context) {
      super(new FacePuzzleLeftUpModel());
   }
}
