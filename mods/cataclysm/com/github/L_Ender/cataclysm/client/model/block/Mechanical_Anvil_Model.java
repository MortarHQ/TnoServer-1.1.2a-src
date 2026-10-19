package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Mechanical_Anvil_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox base;
   private final AdvancedModelBox gear;
   private final AdvancedModelBox sub_gear;
   private final AdvancedModelBox sub_gear2;

   public Mechanical_Anvil_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 12).addBox(-6.0F, -12.0F, -4.0F, 12.0F, 3.0F, 8.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(-8.0F, -14.0F, -3.0F, 16.0F, 6.0F, 6.0F, 0.0F, false);
      this.root.setTextureOffset(10, 58).addBox(2.5F, -14.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.2F, false);
      this.root.setTextureOffset(10, 58).addBox(-8.5F, -14.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.2F, true);
      this.root.setTextureOffset(0, 23).addBox(-7.0F, -13.0F, 4.0F, 14.0F, 5.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(4, 70).addBox(5.5F, -13.0F, 4.0F, 2.0F, 5.0F, 4.0F, 0.2F, false);
      this.root.setTextureOffset(4, 70).addBox(5.5F, -13.0F, -8.0F, 2.0F, 5.0F, 4.0F, 0.2F, false);
      this.root.setTextureOffset(4, 70).addBox(-7.5F, -13.0F, 4.0F, 2.0F, 5.0F, 4.0F, 0.2F, true);
      this.root.setTextureOffset(4, 70).addBox(-7.5F, -13.0F, -8.0F, 2.0F, 5.0F, 4.0F, 0.2F, true);
      this.root.setTextureOffset(0, 32).addBox(-7.0F, -13.0F, -8.0F, 14.0F, 5.0F, 4.0F, 0.0F, false);
      this.base = new AdvancedModelBox(this);
      this.base.setRotationPoint(-2.0F, 0.0F, 0.0F);
      this.root.addChild(this.base);
      this.base.setTextureOffset(38, 36).addBox(-5.0F, -3.0F, 2.0F, 10.0F, 3.0F, 3.0F, 0.0F, false);
      this.base.setTextureOffset(40, 18).addBox(-3.0F, -8.0F, 3.0F, 10.0F, 5.0F, 0.0F, 0.0F, false);
      this.base.setTextureOffset(20, 42).addBox(-3.0F, -8.0F, -3.0F, 10.0F, 5.0F, 0.0F, 0.0F, false);
      this.base.setTextureOffset(20, 47).addBox(-8.0F, -6.0F, 3.5F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.base.setTextureOffset(32, 50).addBox(-8.0F, -6.0F, -3.5F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.base.setTextureOffset(38, 23).addBox(5.0F, -3.0F, -5.0F, 3.0F, 3.0F, 10.0F, 0.0F, false);
      this.base.setTextureOffset(0, 51).addBox(6.0F, -2.0F, -6.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.base.setTextureOffset(12, 53).addBox(6.0F, -2.0F, 3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.base.setTextureOffset(46, 0).addBox(6.0F, -5.0F, -2.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);
      this.base.setTextureOffset(40, 12).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 3.0F, 3.0F, 0.0F, false);
      this.gear = new AdvancedModelBox(this);
      this.gear.setRotationPoint(2.0F, -5.5F, 0.0F);
      this.base.addChild(this.gear);
      this.setRotationAngle(this.gear, 0.0F, 0.0F, 0.7854F);
      this.gear.setTextureOffset(40, 42).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F, 0.0F, false);
      this.gear.setTextureOffset(0, 41).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.0F, false);
      this.gear.setTextureOffset(44, 50).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.sub_gear = new AdvancedModelBox(this);
      this.sub_gear.setRotationPoint(9.0F, -8.0F, 0.0F);
      this.base.addChild(this.sub_gear);
      this.sub_gear.setTextureOffset(66, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.sub_gear.setTextureOffset(79, 7).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, 0.1F, false);
      this.sub_gear.setTextureOffset(66, 6).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.sub_gear.setTextureOffset(78, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.sub_gear2 = new AdvancedModelBox(this);
      this.sub_gear2.setRotationPoint(-5.0F, -8.0F, 0.0F);
      this.base.addChild(this.sub_gear2);
      this.sub_gear2.setTextureOffset(66, 0).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.sub_gear2.setTextureOffset(79, 7).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, 0.1F, false);
      this.sub_gear2.setTextureOffset(66, 6).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.sub_gear2.setTextureOffset(78, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.base, this.gear, this.sub_gear, this.sub_gear2);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(Mechanical_fusion_Anvil_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = beak.tickCount + partialTick;
      this.gear.rotateAngleZ -= ageInTicks * 0.1F;
      this.sub_gear.rotateAngleZ += ageInTicks * 0.2F;
      this.sub_gear2.rotateAngleZ += ageInTicks * 0.2F;
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
