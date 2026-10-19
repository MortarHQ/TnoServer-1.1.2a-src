package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;

public class Amethyst_Cluster_Projectile_Model extends AdvancedEntityModel<Amethyst_Cluster_Projectile_Entity> {
   private final AdvancedModelBox roots;
   private final AdvancedModelBox bone;
   private final AdvancedModelBox bone2;
   private final AdvancedModelBox bone4;
   private final AdvancedModelBox bone3;

   public Amethyst_Cluster_Projectile_Model() {
      this.texWidth = 32;
      this.texHeight = 32;
      this.roots = new AdvancedModelBox(this);
      this.roots.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.roots.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
      this.roots.setTextureOffset(8, 19).addBox(-2.0F, -12.0F, 0.0F, 4.0F, 10.0F, 0.0F, 0.0F, false);
      this.roots.setTextureOffset(18, 17).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
      this.roots.setTextureOffset(0, 8).addBox(0.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, 0.0F, false);
      this.roots.setTextureOffset(16, 0).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
      this.bone = new AdvancedModelBox(this);
      this.bone.setRotationPoint(3.0F, 0.875F, -3.0F);
      this.roots.addChild(this.bone);
      this.setRotationAngle(this.bone, 0.3054F, -0.7418F, 0.0F);
      this.bone.setTextureOffset(16, 4).addBox(0.0F, -3.875F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, false);
      this.bone.setTextureOffset(0, 21).addBox(-1.5F, -3.875F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);
      this.bone.setTextureOffset(20, 10).addBox(-1.0F, -1.875F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
      this.bone.setTextureOffset(8, 12).addBox(-1.5F, 1.125F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
      this.bone2 = new AdvancedModelBox(this);
      this.bone2.setRotationPoint(3.0F, 0.875F, 3.0F);
      this.roots.addChild(this.bone2);
      this.setRotationAngle(this.bone2, -0.3054F, 0.7418F, 0.0F);
      this.bone2.setTextureOffset(16, 4).addBox(0.0F, -3.875F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, false);
      this.bone2.setTextureOffset(0, 21).addBox(-1.5F, -3.875F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, false);
      this.bone2.setTextureOffset(20, 10).addBox(-1.0F, -1.875F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
      this.bone2.setTextureOffset(8, 12).addBox(-1.5F, 1.125F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
      this.bone4 = new AdvancedModelBox(this);
      this.bone4.setRotationPoint(-3.0F, 0.875F, 3.0F);
      this.roots.addChild(this.bone4);
      this.setRotationAngle(this.bone4, -0.3054F, -0.7418F, 0.0F);
      this.bone4.setTextureOffset(16, 4).addBox(0.0F, -3.875F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, true);
      this.bone4.setTextureOffset(0, 21).addBox(-1.5F, -3.875F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, true);
      this.bone4.setTextureOffset(20, 10).addBox(-1.0F, -1.875F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);
      this.bone4.setTextureOffset(8, 12).addBox(-1.5F, 1.125F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, true);
      this.bone3 = new AdvancedModelBox(this);
      this.bone3.setRotationPoint(-3.0F, 0.875F, -3.0F);
      this.roots.addChild(this.bone3);
      this.setRotationAngle(this.bone3, 0.3054F, 0.7418F, 0.0F);
      this.bone3.setTextureOffset(16, 4).addBox(0.0F, -3.875F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, true);
      this.bone3.setTextureOffset(0, 21).addBox(-1.5F, -3.875F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F, true);
      this.bone3.setTextureOffset(20, 10).addBox(-1.0F, -1.875F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);
      this.bone3.setTextureOffset(8, 12).addBox(-1.5F, 1.125F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, true);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.roots, this.bone, this.bone2, this.bone3, this.bone4);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.roots);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(
      Amethyst_Cluster_Projectile_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch
   ) {
   }
}
