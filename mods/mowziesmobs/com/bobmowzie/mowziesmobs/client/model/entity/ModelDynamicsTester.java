package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.MMModelAnimator;
import com.bobmowzie.mowziesmobs.server.entity.EntityDynamicsTester;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class ModelDynamicsTester<T extends EntityDynamicsTester> extends AdvancedModelBase<T> {
   public AdvancedModelRenderer root;
   public AdvancedModelRenderer body1;
   public AdvancedModelRenderer body2;
   public AdvancedModelRenderer body3;
   public AdvancedModelRenderer body4;
   public AdvancedModelRenderer body5;
   public AdvancedModelRenderer body6;
   public AdvancedModelRenderer[] body;
   public AdvancedModelRenderer[] bodydynamic;
   private final MMModelAnimator animator = MMModelAnimator.create();

   public ModelDynamicsTester() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.root = new AdvancedModelRenderer(this, 1, 0);
      this.root.setRotationPoint(0.0F, 0.0F, -16.0F);
      this.root.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F);
      this.body1 = new AdvancedModelRenderer(this, 1, 0);
      this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.body1.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 8.0F, 0.0F);
      this.body2 = new AdvancedModelRenderer(this, 1, 0);
      this.body2.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.body2.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F);
      this.body3 = new AdvancedModelRenderer(this, 1, 0);
      this.body3.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.body3.addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 8.0F, 0.0F);
      this.body4 = new AdvancedModelRenderer(this, 1, 0);
      this.body4.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.body4.addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F, 0.0F);
      this.body5 = new AdvancedModelRenderer(this, 1, 0);
      this.body5.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.body5.addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, 0.0F);
      this.body6 = new AdvancedModelRenderer(this, 1, 0);
      this.body6.setRotationPoint(0.0F, 0.0F, 8.0F);
      this.updateDefaultPose();
      this.root.addChild(this.body1);
      this.body1.addChild(this.body2);
      this.body2.addChild(this.body3);
      this.body3.addChild(this.body4);
      this.body4.addChild(this.body5);
      this.body5.addChild(this.body6);
      this.body = new AdvancedModelRenderer[]{this.body1, this.body2, this.body3, this.body4, this.body5, this.body6};
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.body1.showModel = false;
      this.body2.showModel = false;
      this.body3.showModel = false;
      this.body4.showModel = false;
      this.body5.showModel = false;
      this.body6.showModel = false;
      this.root.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
   }

   public void setRotateAngle(BasicModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      this.root.rotationPointZ += 16.0F;
   }
}
