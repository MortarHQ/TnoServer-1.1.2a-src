package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.ThrownCoral_Spear_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Coral_Bardiche_Model extends AdvancedEntityModel<ThrownCoral_Spear_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox spear_head;
   private final AdvancedModelBox blade;

   public Coral_Bardiche_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.spear_head = new AdvancedModelBox(this);
      this.spear_head.setRotationPoint(0.0F, -19.0F, 0.0F);
      this.root.addChild(this.spear_head);
      this.spear_head.setTextureOffset(0, 0).addBox(-1.0F, -17.0F, -1.0F, 2.0F, 42.0F, 2.0F, 0.0F, false);
      this.spear_head.setTextureOffset(9, 24).addBox(-1.0F, -17.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.25F, false);
      this.spear_head.setTextureOffset(9, 24).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.25F, false);
      this.spear_head.setTextureOffset(23, 24).addBox(-1.0F, 22.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.25F, false);
      this.blade = new AdvancedModelBox(this);
      this.blade.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spear_head.addChild(this.blade);
      this.blade.setTextureOffset(22, 0).addBox(-5.0F, -17.0F, -0.5F, 3.0F, 12.0F, 1.0F, 0.0F, false);
      this.blade.setTextureOffset(9, 0).addBox(-7.0F, -23.0F, 0.0F, 6.0F, 23.0F, 0.0F, 0.0F, false);
      this.blade.setTextureOffset(22, 14).addBox(-2.0F, -17.0F, 0.0F, 1.0F, 11.0F, 0.0F, 0.0F, false);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.spear_head, this.blade);
   }

   public void setupAnim(ThrownCoral_Spear_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
