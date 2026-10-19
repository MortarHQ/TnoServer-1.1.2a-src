package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Gauntlet_of_Bulwark_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox gauntlet_fist;
   private final AdvancedModelBox fist_guard;
   private final AdvancedModelBox void_stone_knuckle;
   private final AdvancedModelBox spike;
   private final AdvancedModelBox void_spike;
   private final AdvancedModelBox void_spike4;
   private final AdvancedModelBox void_spike2;
   private final AdvancedModelBox void_spike3;
   private final AdvancedModelBox thumb;
   private final AdvancedModelBox gauntlet_arm;
   private final AdvancedModelBox gauntlet_arm2;
   private final AdvancedModelBox gauntlet_shoulder;
   private final AdvancedModelBox big_void_stone;
   private final AdvancedModelBox flame_spike;
   private final AdvancedModelBox cube_r1;
   private final AdvancedModelBox flame_spike2;
   private final AdvancedModelBox cube_r2;
   private final AdvancedModelBox arm_pad;

   public Gauntlet_of_Bulwark_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 19.0F, 0.0F);
      this.gauntlet_fist = new AdvancedModelBox(this);
      this.gauntlet_fist.setRotationPoint(0.0F, 0.0F, -3.5F);
      this.root.addChild(this.gauntlet_fist);
      this.gauntlet_fist.setTextureOffset(40, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);
      this.fist_guard = new AdvancedModelBox(this);
      this.fist_guard.setRotationPoint(-2.0F, 0.5F, -6.5F);
      this.gauntlet_fist.addChild(this.fist_guard);
      this.setRotationAngle(this.fist_guard, 0.0F, -0.9163F, 0.0F);
      this.fist_guard.setTextureOffset(81, 0).addBox(-4.0F, -6.0F, -4.0F, 5.0F, 11.0F, 7.0F, 0.0F, false);
      this.fist_guard.setTextureOffset(82, 18).addBox(-4.0F, -6.0F, -4.5F, 5.0F, 11.0F, 7.0F, 0.3F, false);
      this.void_stone_knuckle = new AdvancedModelBox(this);
      this.void_stone_knuckle.setRotationPoint(-5.25F, -2.5F, -6.0F);
      this.gauntlet_fist.addChild(this.void_stone_knuckle);
      this.void_stone_knuckle.setTextureOffset(56, 56).addBox(0.0F, 0.5F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
      this.spike = new AdvancedModelBox(this);
      this.spike.setRotationPoint(-4.0F, -1.5F, -8.0F);
      this.gauntlet_fist.addChild(this.spike);
      this.void_spike = new AdvancedModelBox(this);
      this.void_spike.setRotationPoint(0.0F, 1.5F, -1.0F);
      this.spike.addChild(this.void_spike);
      this.setRotationAngle(this.void_spike, 0.0F, 0.0436F, 0.0F);
      this.void_spike.setTextureOffset(56, 38).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 0.0F, false);
      this.void_spike4 = new AdvancedModelBox(this);
      this.void_spike4.setRotationPoint(6.5F, -4.25F, 0.5F);
      this.spike.addChild(this.void_spike4);
      this.setRotationAngle(this.void_spike4, 0.0161F, 0.2628F, 1.5416F);
      this.void_spike4.setTextureOffset(56, 38).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 0.0F, false);
      this.void_spike2 = new AdvancedModelBox(this);
      this.void_spike2.setRotationPoint(0.0F, 5.0F, 0.0F);
      this.spike.addChild(this.void_spike2);
      this.setRotationAngle(this.void_spike2, 0.1162F, 0.0468F, -0.0553F);
      this.void_spike2.setTextureOffset(56, 38).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 0.0F, false);
      this.void_spike3 = new AdvancedModelBox(this);
      this.void_spike3.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.spike.addChild(this.void_spike3);
      this.setRotationAngle(this.void_spike3, -0.1548F, 0.0493F, 0.0735F);
      this.void_spike3.setTextureOffset(56, 38).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 0.0F, 8.0F, 0.0F, false);
      this.thumb = new AdvancedModelBox(this);
      this.thumb.setRotationPoint(3.0F, -4.5F, -4.5F);
      this.gauntlet_fist.addChild(this.thumb);
      this.setRotationAngle(this.thumb, 0.2618F, 0.0F, 0.0F);
      this.thumb.setTextureOffset(101, 11).addBox(-3.0F, -3.0F, -6.0F, 5.0F, 4.0F, 7.0F, 0.0F, false);
      this.thumb.setTextureOffset(75, 37).addBox(-3.0F, -3.0F, -6.0F, 5.0F, 4.0F, 7.0F, 0.3F, false);
      this.gauntlet_arm = new AdvancedModelBox(this);
      this.gauntlet_arm.setRotationPoint(0.0F, 0.0F, 2.5F);
      this.root.addChild(this.gauntlet_arm);
      this.gauntlet_arm.setTextureOffset(0, 45).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.gauntlet_arm2 = new AdvancedModelBox(this);
      this.gauntlet_arm2.setRotationPoint(0.0F, 0.0F, -3.5F);
      this.root.addChild(this.gauntlet_arm2);
      this.gauntlet_arm2.setTextureOffset(56, 16).addBox(-4.0F, -4.0F, 0.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
      this.gauntlet_arm2.setTextureOffset(110, 0).addBox(-3.5F, -4.0F, 2.0F, 6.0F, 8.0F, 3.0F, 0.3F, false);
      this.gauntlet_shoulder = new AdvancedModelBox(this);
      this.gauntlet_shoulder.setRotationPoint(2.0F, 0.0F, 9.0F);
      this.gauntlet_arm2.addChild(this.gauntlet_shoulder);
      this.setRotationAngle(this.gauntlet_shoulder, 0.0F, 0.6109F, 0.0F);
      this.gauntlet_shoulder.setTextureOffset(0, 0).addBox(-8.0F, -5.0F, -6.0F, 8.0F, 10.0F, 12.0F, 0.0F, false);
      this.gauntlet_shoulder.setTextureOffset(32, 45).addBox(-9.0F, -6.0F, -7.0F, 9.0F, 12.0F, 3.0F, 0.0F, false);
      this.gauntlet_shoulder.setTextureOffset(30, 22).addBox(-2.0F, -6.0F, -4.0F, 2.0F, 12.0F, 11.0F, 0.0F, false);
      this.gauntlet_shoulder.setTextureOffset(0, 69).addBox(-2.0F, -7.0F, -8.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
      this.gauntlet_shoulder.setTextureOffset(0, 69).addBox(-2.0F, 6.0F, -8.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
      this.big_void_stone = new AdvancedModelBox(this);
      this.big_void_stone.setRotationPoint(-8.0F, 0.0F, -2.0F);
      this.gauntlet_shoulder.addChild(this.big_void_stone);
      this.setRotationAngle(this.big_void_stone, 0.0F, 0.6109F, 0.0F);
      this.big_void_stone.setTextureOffset(56, 30).addBox(-8.0F, -2.0F, 0.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
      this.big_void_stone.setTextureOffset(0, 61).addBox(-8.0F, -2.0F, 0.0F, 8.0F, 4.0F, 4.0F, 0.3F, false);
      this.flame_spike = new AdvancedModelBox(this);
      this.flame_spike.setRotationPoint(-6.682F, 0.0F, 3.182F);
      this.gauntlet_shoulder.addChild(this.flame_spike);
      this.cube_r1 = new AdvancedModelBox(this);
      this.cube_r1.setRotationPoint(0.182F, 0.0F, -0.182F);
      this.flame_spike.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, 0.0F, -0.7854F, 0.0F);
      this.cube_r1.setTextureOffset(-9, 80).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, 0.0F, false);
      this.flame_spike2 = new AdvancedModelBox(this);
      this.flame_spike2.setRotationPoint(-9.682F, 0.0F, -1.818F);
      this.gauntlet_shoulder.addChild(this.flame_spike2);
      this.setRotationAngle(this.flame_spike2, 0.0F, -0.7854F, 0.0F);
      this.cube_r2 = new AdvancedModelBox(this);
      this.cube_r2.setRotationPoint(1.182F, 0.0F, -2.182F);
      this.flame_spike2.addChild(this.cube_r2);
      this.setRotationAngle(this.cube_r2, 0.0F, -0.7854F, 0.0F);
      this.cube_r2.setTextureOffset(-9, 80).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, 0.0F, false);
      this.arm_pad = new AdvancedModelBox(this);
      this.arm_pad.setRotationPoint(-4.5F, 0.0F, 0.0F);
      this.gauntlet_arm2.addChild(this.arm_pad);
      this.setRotationAngle(this.arm_pad, 0.0F, -0.48F, 0.0F);
      this.arm_pad.setTextureOffset(0, 22).addBox(-1.5F, -5.0F, -2.0F, 5.0F, 10.0F, 10.0F, 0.0F, false);
      this.arm_pad.setTextureOffset(40, 16).addBox(-3.5F, -6.0F, -3.0F, 5.0F, 4.0F, 2.0F, 0.0F, false);
      this.arm_pad.setTextureOffset(56, 46).addBox(-3.5F, -4.0F, -1.0F, 5.0F, 8.0F, 2.0F, 0.0F, false);
      this.arm_pad.setTextureOffset(40, 16).addBox(-3.5F, 2.0F, -3.0F, 5.0F, 4.0F, 2.0F, 0.0F, false);
      this.arm_pad.setTextureOffset(32, 60).addBox(1.5F, -6.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.arm_pad.setTextureOffset(32, 60).addBox(1.5F, 4.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.gauntlet_fist,
         this.fist_guard,
         this.void_stone_knuckle,
         this.spike,
         this.void_spike,
         this.void_spike4,
         this.void_spike2,
         this.void_spike3,
         this.thumb,
         this.gauntlet_arm,
         this.gauntlet_arm2,
         new AdvancedModelBox[]{this.gauntlet_shoulder, this.big_void_stone, this.flame_spike, this.cube_r1, this.flame_spike2, this.cube_r2, this.arm_pad}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
