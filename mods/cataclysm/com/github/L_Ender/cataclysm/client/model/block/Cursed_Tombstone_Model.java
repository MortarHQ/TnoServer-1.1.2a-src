package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Cursed_Tombstone_Model extends AdvancedEntityModel<Entity> {
   public final AdvancedModelBox root;
   private final AdvancedModelBox cube_r1;
   private final AdvancedModelBox cube_r2;
   private final AdvancedModelBox head;
   private final AdvancedModelBox maw;

   public Cursed_Tombstone_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 0).addBox(-7.0F, -24.0F, -2.0F, 14.0F, 22.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(36, 0).addBox(-1.5F, -22.0F, -3.0F, 3.0F, 5.0F, 1.0F, 0.0F, false);
      this.root.setTextureOffset(0, 26).addBox(-8.0F, -2.0F, -3.0F, 16.0F, 2.0F, 6.0F, 0.0F, false);
      this.cube_r1 = new AdvancedModelBox(this);
      this.cube_r1.setRotationPoint(-7.0F, -21.5F, 0.0F);
      this.root.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, 0.0F, 0.2618F, 0.2182F);
      this.cube_r1.setTextureOffset(0, 34).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, 0.2F, true);
      this.cube_r1.setTextureOffset(16, 34).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, 0.1F, true);
      this.cube_r2 = new AdvancedModelBox(this);
      this.cube_r2.setRotationPoint(7.0F, -21.5F, 0.0F);
      this.root.addChild(this.cube_r2);
      this.setRotationAngle(this.cube_r2, 0.0F, -0.2618F, -0.2182F);
      this.cube_r2.setTextureOffset(16, 34).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, 0.1F, false);
      this.cube_r2.setTextureOffset(0, 34).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, 0.2F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -24.0F, 0.0F);
      this.root.addChild(this.head);
      this.head.setTextureOffset(0, 63).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 7.0F, 8.0F, -0.1F, false);
      this.head.setTextureOffset(0, 48).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 7.0F, 8.0F, 0.0F, false);
      this.maw = new AdvancedModelBox(this);
      this.maw.setRotationPoint(0.0F, -3.5F, -2.0F);
      this.head.addChild(this.maw);
      this.setRotationAngle(this.maw, 0.0F, 0.0F, 0.0F);
      this.maw.setTextureOffset(43, 55).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 5.0F, 4.0F, 0.0F, false);
      this.maw.setTextureOffset(43, 64).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 5.0F, 4.0F, -0.1F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.cube_r1, this.cube_r2, this.head, this.maw);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
