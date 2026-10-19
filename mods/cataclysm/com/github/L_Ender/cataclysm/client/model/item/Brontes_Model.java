package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.cataclysm.entity.projectile.Brontes_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Brontes_Model extends AdvancedEntityModel<Brontes_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox hammer_head;
   private final AdvancedModelBox back;
   private final AdvancedModelBox handle;
   private final AdvancedModelBox handle2;

   public Brontes_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.hammer_head = new AdvancedModelBox(this);
      this.hammer_head.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.root.addChild(this.hammer_head);
      this.hammer_head.setTextureOffset(36, 0).addBox(-3.0F, -38.0F, -2.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(6, 51).addBox(3.25F, -33.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(56, 52).addBox(-3.25F, -33.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(52, 36).addBox(-3.25F, -37.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(56, 44).addBox(3.25F, -37.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(36, 13).addBox(-3.5F, -38.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(18, 51).addBox(-3.5F, -33.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(18, 56).addBox(0.5F, -38.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(42, 58).addBox(0.5F, -38.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(6, 59).addBox(0.5F, -33.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(60, 0).addBox(0.5F, -33.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(56, 13).addBox(-3.5F, -33.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(46, 13).addBox(-3.5F, -38.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(0, 19).addBox(-4.0F, -39.0F, -10.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(28, 36).addBox(-4.5F, -39.5F, -11.0F, 9.0F, 10.0F, 3.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(32, 19).addBox(-4.0F, -39.0F, -9.0F, 8.0F, 9.0F, 8.0F, 0.3F, false);
      this.back = new AdvancedModelBox(this);
      this.back.setRotationPoint(0.0F, -35.0F, 9.0F);
      this.hammer_head.addChild(this.back);
      this.back.setTextureOffset(0, 36).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 9.0F, 6.0F, 0.0F, false);
      this.back.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 9.0F, 10.0F, 0.3F, false);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(0.0F, 12.0F, 0.5F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(0, 51).addBox(-0.5F, -18.0F, -1.5F, 1.0F, 14.0F, 2.0F, 0.0F, false);
      this.handle.setTextureOffset(28, 49).addBox(0.0F, 0.0F, -4.0F, 0.0F, 8.0F, 7.0F, 0.0F, false);
      this.handle.setTextureOffset(60, 5).addBox(-0.5F, -4.0F, -1.5F, 1.0F, 4.0F, 2.0F, 0.2F, false);
      this.handle.setTextureOffset(52, 60).addBox(-0.5F, -17.0F, -1.5F, 1.0F, 1.0F, 2.0F, 0.2F, false);
      this.handle2 = new AdvancedModelBox(this);
      this.handle2.setRotationPoint(0.0F, 7.0F, 0.5F);
      this.root.addChild(this.handle2);
      this.handle2.setTextureOffset(42, 49).addBox(-1.5F, -18.0F, -2.5F, 3.0F, 5.0F, 4.0F, 0.0F, false);
   }

   public void setupAnim(Brontes_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.hammer_head, this.back, this.handle, this.handle2);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
