package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.client.animation.Door_Of_Seal_Animation;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Door_Of_Seal_Model extends AdvancedEntityModel<Entity> {
   public final AdvancedModelBox roots;
   public final AdvancedModelBox left_door;
   public final AdvancedModelBox right_door;
   public final AdvancedModelBox lock;
   public final AdvancedModelBox cube_r1;

   public Door_Of_Seal_Model() {
      this.texWidth = 256;
      this.texHeight = 256;
      this.roots = new AdvancedModelBox(this, "roots");
      this.roots.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.left_door = new AdvancedModelBox(this, "left_door");
      this.left_door.setRotationPoint(40.0F, -64.0F, 0.0F);
      this.roots.addChild(this.left_door);
      this.left_door.setTextureOffset(0, 0).addBox(-40.0F, -64.0F, -8.0F, 40.0F, 128.0F, 16.0F, 0.0F, true);
      this.right_door = new AdvancedModelBox(this, "right_door");
      this.right_door.setRotationPoint(-40.0F, -64.0F, 0.0F);
      this.roots.addChild(this.right_door);
      this.right_door.setTextureOffset(0, 0).addBox(0.0F, -64.0F, -8.0F, 40.0F, 128.0F, 16.0F, 0.0F, false);
      this.lock = new AdvancedModelBox(this, "lock");
      this.lock.setRotationPoint(0.0F, -24.0F, -9.0F);
      this.roots.addChild(this.lock);
      this.cube_r1 = new AdvancedModelBox(this, "cube_r1");
      this.cube_r1.setRotationPoint(0.0F, 7.9F, 0.0F);
      this.lock.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, 0.0F, 0.0F, 0.7854F);
      this.cube_r1.setTextureOffset(0, 144).addBox(-21.6F, -21.6F, -1.0F, 32.0F, 32.0F, 2.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.roots);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.roots, this.left_door, this.right_door, this.lock, this.cube_r1);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(Door_Of_Seal_BlockEntity entity, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = entity.tickCount + partialTick;
      this.animate(entity.getAnimationState("opening"), Door_Of_Seal_Animation.OPEN, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("open"), Door_Of_Seal_Animation.OPEN_IDLE, ageInTicks, 1.0F);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
