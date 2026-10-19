package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Wither_Howitzer_Model extends AdvancedEntityModel<Wither_Howitzer_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox warhead;

   public Wither_Howitzer_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.setTextureOffset(22, 20).addBox(-3.0F, -3.0F, 2.5F, 6.0F, 6.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(28, 0).addBox(-3.0F, -3.0F, 4.5F, 6.0F, 6.0F, 2.0F, 0.3F, false);
      this.root.setTextureOffset(0, 20).addBox(-4.0F, -4.0F, 6.5F, 8.0F, 8.0F, 3.0F, 0.0F, false);
      this.warhead = new AdvancedModelBox(this);
      this.warhead.setRotationPoint(3.0F, 4.0F, -8.5F);
      this.root.addChild(this.warhead);
      this.warhead.setTextureOffset(0, 0).addBox(-7.0F, -8.0F, -1.0F, 8.0F, 8.0F, 12.0F, 0.0F, false);
      this.warhead.setTextureOffset(0, 31).addBox(-7.0F, -8.0F, 7.75F, 8.0F, 8.0F, 3.0F, 0.5F, false);
      this.warhead.setTextureOffset(0, 31).addBox(-7.0F, -8.0F, 1.25F, 8.0F, 8.0F, 3.0F, 0.5F, false);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.warhead);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Wither_Howitzer_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
