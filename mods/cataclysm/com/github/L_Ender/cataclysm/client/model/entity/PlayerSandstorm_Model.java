package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.LivingEntity;

public class PlayerSandstorm_Model extends AdvancedEntityModel<LivingEntity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox storm;
   private final AdvancedModelBox storm2;
   private final AdvancedModelBox storm3;
   private final AdvancedModelBox storm4;

   public PlayerSandstorm_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.storm = new AdvancedModelBox(this);
      this.storm.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.root.addChild(this.storm);
      this.storm.setTextureOffset(65, 72).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.storm2 = new AdvancedModelBox(this);
      this.storm2.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.storm.addChild(this.storm2);
      this.storm2.setTextureOffset(0, 72).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, 0.0F, false);
      this.storm3 = new AdvancedModelBox(this);
      this.storm3.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.storm2.addChild(this.storm3);
      this.storm3.setTextureOffset(0, 39).addBox(-12.0F, -4.0F, -12.0F, 24.0F, 8.0F, 24.0F, 0.0F, false);
      this.storm4 = new AdvancedModelBox(this);
      this.storm4.setRotationPoint(0.0F, -9.0F, 0.0F);
      this.storm3.addChild(this.storm4);
      this.storm4.setTextureOffset(0, 0).addBox(-15.0F, -4.0F, -15.0F, 30.0F, 8.0F, 30.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.storm, this.storm2, this.storm3, this.storm4);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      AdvancedModelBox[] stormBoxes = new AdvancedModelBox[]{this.storm, this.storm2, this.storm3, this.storm4};
      float walkSpeed = 0.5F;
      float walkDegree = 1.0F;
      this.chainFlap(stormBoxes, walkSpeed, walkDegree * 0.1F, -2.0, ageInTicks, 1.0F);
      this.storm.rotateAngleY -= ageInTicks * 1.0F;
      this.storm2.rotateAngleY = this.storm2.rotateAngleY - (this.storm.rotateAngleY + ageInTicks * 0.5F);
      this.storm3.rotateAngleY = this.storm3.rotateAngleY - (this.storm.rotateAngleY + this.storm2.rotateAngleY + ageInTicks * 0.3F);
      this.storm4.rotateAngleY = this.storm4.rotateAngleY - (this.storm.rotateAngleY + this.storm2.rotateAngleY + this.storm3.rotateAngleY + ageInTicks * 0.6F);
   }

   public void setRotationAngle(AdvancedModelBox modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }
}
