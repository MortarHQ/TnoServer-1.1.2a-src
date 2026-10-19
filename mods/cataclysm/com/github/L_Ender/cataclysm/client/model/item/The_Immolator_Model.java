package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class The_Immolator_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox cube_r1;

   public The_Immolator_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 42).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(42, 42).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(54, 42).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 2.0F, 3.0F, -0.2F, false);
      this.root.setTextureOffset(42, 47).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(39, 23).addBox(-1.5F, -33.0F, -1.5F, 3.0F, 16.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(-1.5F, -20.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.3F, false);
      this.root.setTextureOffset(0, 5).addBox(0.0F, -20.0F, 1.8F, 0.0F, 5.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(4, 5).addBox(0.0F, -20.0F, -3.8F, 0.0F, 5.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(4, 12).addBox(-3.8F, -20.0F, 0.2F, 2.0F, 5.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(0, 12).addBox(1.8F, -20.0F, 0.2F, 2.0F, 5.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(0.0F, -41.0F, -9.5F, 0.0F, 22.0F, 19.0F, 0.0F, false);
      this.root.setTextureOffset(39, 0).addBox(-9.5F, -41.0F, 0.0F, 19.0F, 22.0F, 0.0F, 0.0F, false);
      this.cube_r1 = new AdvancedModelBox(this);
      this.cube_r1.setRotationPoint(0.0F, 3.0F, 0.0F);
      this.root.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, 0.0F, 0.0F, -0.7854F);
      this.cube_r1.setTextureOffset(42, 51).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entity, float pullAmount, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.cube_r1);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
