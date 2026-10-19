package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Tidal_Tentacle_Claws_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox claw1;
   private final AdvancedModelBox claw2;
   private final AdvancedModelBox claw3;
   private final AdvancedModelBox claw4;

   public Tidal_Tentacle_Claws_Model() {
      this.texWidth = 32;
      this.texHeight = 32;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.claw1 = new AdvancedModelBox(this);
      this.claw1.setRotationPoint(0.0F, 0.0F, 0.7F);
      this.root.addChild(this.claw1);
      this.setRotationAngle(this.claw1, 0.48F, 0.0F, 0.0F);
      this.claw1.setTextureOffset(9, 10).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.claw2 = new AdvancedModelBox(this);
      this.claw2.setRotationPoint(0.0F, 2.0F, 0.7F);
      this.root.addChild(this.claw2);
      this.setRotationAngle(this.claw2, -0.48F, 0.0F, 0.0F);
      this.claw2.setTextureOffset(0, 10).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.claw3 = new AdvancedModelBox(this);
      this.claw3.setRotationPoint(1.0F, 1.0F, 0.7F);
      this.root.addChild(this.claw3);
      this.setRotationAngle(this.claw3, 0.0F, 0.48F, 0.0F);
      this.claw3.setTextureOffset(0, 5).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);
      this.claw4 = new AdvancedModelBox(this);
      this.claw4.setRotationPoint(-1.0F, 1.0F, 0.7F);
      this.root.addChild(this.claw4);
      this.setRotationAngle(this.claw4, 0.0F, -0.48F, 0.0F);
      this.claw4.setTextureOffset(0, 0).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.claw1, this.claw2, this.claw3, this.claw4);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void setAttributes(float rotX, float rotY) {
      this.resetToDefaultPose();
      this.resetToDefaultPose();
      this.root.rotateAngleX = (float)Math.toRadians(rotX);
      this.root.rotateAngleY = (float)Math.toRadians(rotY);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
