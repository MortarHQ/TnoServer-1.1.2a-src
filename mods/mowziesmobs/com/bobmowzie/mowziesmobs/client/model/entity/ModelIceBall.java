package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class ModelIceBall<T extends EntityIceBall> extends AdvancedModelBase<T> {
   private final AdvancedModelRenderer body1;
   private final AdvancedModelRenderer body2;
   private final AdvancedModelRenderer core;

   public ModelIceBall() {
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.body1 = new AdvancedModelRenderer(this, 32, 0);
      this.body1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.body1.addBox(-12.0F, -12.0F, -12.0F, 24.0F, 24.0F, 24.0F, 0.0F, false);
      this.body2 = new AdvancedModelRenderer(this, 32, 0);
      this.body2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.setRotationAngle(this.body2, 0.7854F, 0.0F, 0.7854F);
      this.body2.addBox(-12.0F, -12.0F, -12.0F, 24.0F, 24.0F, 24.0F, 0.0F, false);
      this.core = new AdvancedModelRenderer(this, 8, 0);
      this.core.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.core.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.body1.setOpacity(0.65F);
      this.body2.setOpacity(0.65F);
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.body1.rotateAngleX = 0.0F;
      this.body1.rotateAngleY = 0.0F;
      this.body1.rotateAngleZ = -ageInTicks * 0.4F;
      this.body2.rotateAngleX = 0.7854F;
      this.body2.rotateAngleY = 0.7854F + ageInTicks * 0.4F;
      this.body2.rotateAngleZ = 0.7854F + ageInTicks * 0.4F;
      this.body2.showModel = true;
      this.core.rotateAngleZ = ageInTicks * 0.4F;
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.core.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
   }

   public void setRotationAngle(BasicModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
