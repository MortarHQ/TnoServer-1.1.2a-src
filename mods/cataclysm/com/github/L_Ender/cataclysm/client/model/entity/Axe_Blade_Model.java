package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Axe_Blade_Model extends AdvancedEntityModel<Axe_Blade_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox blade;
   private final AdvancedModelBox vfx;
   private final AdvancedModelBox cube_r1;
   private final AdvancedModelBox cube_r2;

   public Axe_Blade_Model() {
      this.texWidth = 256;
      this.texHeight = 256;
      this.root = new AdvancedModelBox(this, "root");
      this.root.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.blade = new AdvancedModelBox(this, "blade");
      this.blade.setRotationPoint(0.0F, -16.0F, 0.0F);
      this.root.addChild(this.blade);
      this.blade.setTextureOffset(41, 39).addBox(-1.0F, -16.0F, -14.0F, 2.0F, 32.0F, 28.0F, 0.0F, false);
      this.vfx = new AdvancedModelBox(this, "vfx");
      this.vfx.setRotationPoint(0.0F, 0.0F, -14.0F);
      this.blade.addChild(this.vfx);
      this.cube_r1 = new AdvancedModelBox(this, "cube_r1");
      this.cube_r1.setRotationPoint(0.0F, 16.0F, 11.0F);
      this.vfx.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, 0.0F, 0.0F, -0.9599F);
      this.cube_r1.setTextureOffset(0, 70).addBox(-1.0F, -10.0F, -11.0F, 0.0F, 10.0F, 30.0F, 0.0F, false);
      this.cube_r2 = new AdvancedModelBox(this, "cube_r2");
      this.cube_r2.setRotationPoint(0.0F, 16.0F, 11.0F);
      this.vfx.addChild(this.cube_r2);
      this.setRotationAngle(this.cube_r2, 0.0F, 0.0F, 0.9599F);
      this.cube_r2.setTextureOffset(72, 70).addBox(1.0F, -10.0F, -11.0F, 0.0F, 10.0F, 30.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.blade, this.vfx, this.cube_r1, this.cube_r2);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Axe_Blade_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      this.root.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.root.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
      float randomValue = (float)(0.1F + Math.random() * 0.9F);
      this.vfx.rotationPointZ += randomValue * 2.0F;
      this.blade.rotationPointZ += randomValue * 6.0F;
      this.blade.setScale(1.0F, 1.0F, 1.0F + randomValue * 0.2F);
   }
}
