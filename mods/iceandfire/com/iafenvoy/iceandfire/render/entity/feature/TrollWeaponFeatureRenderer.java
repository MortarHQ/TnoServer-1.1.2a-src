package com.iafenvoy.iceandfire.render.entity.feature;

import com.iafenvoy.iceandfire.entity.GorgonEntity;
import com.iafenvoy.iceandfire.entity.TrollEntity;
import com.iafenvoy.iceandfire.render.entity.TrollEntityRenderer;
import com.iafenvoy.iceandfire.render.model.TrollModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class TrollWeaponFeatureRenderer extends RenderLayer<TrollEntity, TrollModel> {
   public TrollWeaponFeatureRenderer(TrollEntityRenderer renderer) {
      super(renderer);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      TrollEntity troll,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (troll.getWeaponType() != null && !GorgonEntity.isStoneMob(troll)) {
         RenderType tex = RenderType.entityCutout(troll.getWeaponType().getTexture());
         ((TrollModel)this.getParentModel()).renderToBuffer(matrixStackIn, bufferIn.getBuffer(tex), packedLightIn, OverlayTexture.NO_OVERLAY, -1);
      }
   }
}
