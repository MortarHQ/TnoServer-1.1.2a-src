package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class The_Leviathan_Tongue_End_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox tongue;

   public The_Leviathan_Tongue_End_Model() {
      this.texWidth = 256;
      this.texHeight = 256;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.tongue = new AdvancedModelBox(this);
      this.tongue.setRotationPoint(0.0F, 70.0F, -4.2F);
      this.root.addChild(this.tongue);
      this.tongue.setTextureOffset(74, 93).addBox(-3.0F, -73.0F, -0.3F, 6.0F, 6.0F, 9.0F, 0.0F, false);
      this.tongue.setTextureOffset(190, 106).addBox(0.0F, -76.0F, 0.7F, 0.0F, 12.0F, 7.0F, 0.0F, false);
      this.tongue.setTextureOffset(139, 9).addBox(-6.0F, -70.0F, 0.7F, 12.0F, 0.0F, 7.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.tongue);
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
