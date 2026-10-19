package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Spear_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Coral_Spear_Model extends AdvancedEntityModel<ThrownCoral_Spear_Entity> {
   private final AdvancedModelBox coral_spear;
   private final AdvancedModelBox coral;
   private final AdvancedModelBox coral2;
   private final AdvancedModelBox head;
   private final AdvancedModelBox head2;

   public Coral_Spear_Model() {
      this.texWidth = 32;
      this.texHeight = 32;
      this.coral_spear = new AdvancedModelBox(this);
      this.coral_spear.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.coral_spear.setTextureOffset(0, 0).addBox(-0.5F, -23.0F, -0.5F, 1.0F, 23.0F, 1.0F, 0.0F, false);
      this.coral = new AdvancedModelBox(this);
      this.coral.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.coral_spear.addChild(this.coral);
      this.setRotationAngle(this.coral, 0.0F, 0.7854F, 0.0F);
      this.coral.setTextureOffset(4, 13).addBox(0.0F, -13.0F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, false);
      this.coral2 = new AdvancedModelBox(this);
      this.coral2.setRotationPoint(4.0F, -5.0F, 4.0F);
      this.coral_spear.addChild(this.coral2);
      this.setRotationAngle(this.coral2, 0.0F, 0.7854F, 0.0F);
      this.coral2.setTextureOffset(4, 8).addBox(0.0F, -13.0F, -6.0F, 0.0F, 5.0F, 6.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -29.0F, 0.0F);
      this.coral_spear.addChild(this.head);
      this.setRotationAngle(this.head, 0.0F, 0.7854F, 0.0F);
      this.head.setTextureOffset(16, 10).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 10.0F, 0.0F, 0.0F, false);
      this.head2 = new AdvancedModelBox(this);
      this.head2.setRotationPoint(0.0F, -29.0F, 0.0F);
      this.coral_spear.addChild(this.head2);
      this.setRotationAngle(this.head2, 0.0F, -0.7854F, 0.0F);
      this.head2.setTextureOffset(16, 0).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 10.0F, 0.0F, 0.0F, false);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.coral_spear);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.coral_spear, this.head, this.head2, this.coral, this.coral2);
   }

   public void setupAnim(ThrownCoral_Spear_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
