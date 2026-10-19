package com.tensura_tno.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tensura_tno.block.RimuruPlushBlock;
import com.tensura_tno.block.entity.RimuruPlushBlockEntity;
import com.tensura_tno.client.model.RimuruPlushModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public final class RimuruPlushRenderer extends GeoBlockRenderer<RimuruPlushBlockEntity> {
   public RimuruPlushRenderer(Context context) {
      this(context, "rimuru_plush", 0.64F);
   }

   public RimuruPlushRenderer(Context context, String assetName, float scale) {
      super(new RimuruPlushModel(assetName));
      this.withScale(scale);
   }

   protected void rotateBlock(Direction facing, PoseStack poseStack) {
      poseStack.mulPose(Axis.YP.rotationDegrees(-22.5F * RimuruPlushBlock.getRotationStep(((RimuruPlushBlockEntity)this.getAnimatable()).getBlockState())));
   }
}
