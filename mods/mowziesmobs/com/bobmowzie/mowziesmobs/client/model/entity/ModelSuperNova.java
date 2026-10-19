package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class ModelSuperNova<T extends EntitySuperNova> extends AdvancedModelBase<T> {
   private final AdvancedModelRenderer body1;
   private final AdvancedModelRenderer body2;
   private final AdvancedModelRenderer body3;
   private final AdvancedModelRenderer body4;

   public ModelSuperNova() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.body1 = new AdvancedModelRenderer(this, 0, 0);
      this.body1.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.body1.addBox(-32.0F, -32.0F, -32.0F, 64.0F, 64.0F, 64.0F, 0.0F, false);
      this.body2 = new AdvancedModelRenderer(this, 0, 0);
      this.body2.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.body2.addBox(-32.0F, -32.0F, -32.0F, 64.0F, 64.0F, 64.0F, 0.0F, false);
      this.setRotationAngle(this.body2, (float) (Math.PI / 2), (float) (Math.PI / 2), (float) (Math.PI / 2));
      this.body3 = new AdvancedModelRenderer(this, 0, 0);
      this.body3.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.body3.addBox(-32.0F, -32.0F, -32.0F, 64.0F, 64.0F, 64.0F, 0.0F, false);
      this.setRotationAngle(this.body3, (float) Math.PI, (float) Math.PI, (float) Math.PI);
      this.body4 = new AdvancedModelRenderer(this, 0, 0);
      this.body4.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.body4.addBox(-32.0F, -32.0F, -32.0F, 64.0F, 64.0F, 64.0F, 0.0F, false);
      this.setRotationAngle(this.body4, (float) (Math.PI * 3.0 / 2.0), (float) (Math.PI * 3.0 / 2.0), (float) (Math.PI * 3.0 / 2.0));
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.body4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      this.body1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float ageFrac = ageInTicks / EntitySuperNova.DURATION;
      float scale = (float)(Math.pow(ageFrac, 0.5) * 3.0 + 0.05 * Math.cos(ageInTicks * 3.0F));
      float opacity = (float)Math.max((1.0 - Math.pow(ageFrac, 4.0)) * 0.4F, 1.0E-5);
      this.body1.setOpacity(opacity);
      this.body2.setOpacity(opacity);
      this.body3.setOpacity(opacity);
      this.body4.setOpacity(opacity);
      this.body4.setScale(scale * 0.4F, scale * 0.4F, scale * 0.4F);
      this.body3.setScale(scale * 0.6F, scale * 0.6F, scale * 0.6F);
      this.body2.setScale(scale * 0.8F, scale * 0.8F, scale * 0.8F);
      this.body1.setScale(scale, scale, scale);
   }

   public void setRotationAngle(BasicModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
