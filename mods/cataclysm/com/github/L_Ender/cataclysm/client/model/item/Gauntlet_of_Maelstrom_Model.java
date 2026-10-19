package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Gauntlet_of_Maelstrom_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox gauntlet_fist;
   private final AdvancedModelBox gauntlet_fist_r1;
   private final AdvancedModelBox gauntlet_fist_r2;
   private final AdvancedModelBox void_stone_knuckle;
   private final AdvancedModelBox gauntlet_arm;
   private final AdvancedModelBox gauntlet_arm2;
   private final AdvancedModelBox gauntlet_shoulder;
   private final AdvancedModelBox big_void_stone;

   public Gauntlet_of_Maelstrom_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 19.0F, 0.0F);
      this.gauntlet_fist = new AdvancedModelBox(this);
      this.gauntlet_fist.setRotationPoint(0.0F, 0.0F, -3.5F);
      this.root.addChild(this.gauntlet_fist);
      this.gauntlet_fist.setTextureOffset(24, 30).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);
      this.gauntlet_fist.setTextureOffset(34, 64).addBox(-5.0F, -4.5F, -10.0F, 5.0F, 9.0F, 11.0F, 0.0F, false);
      this.gauntlet_fist.setTextureOffset(37, 68).addBox(-5.0F, -8.5F, -10.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);
      this.gauntlet_fist.setTextureOffset(29, 68).addBox(-5.0F, 4.5F, -10.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);
      this.gauntlet_fist.setTextureOffset(23, 69).addBox(-5.0F, 4.5F, -6.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
      this.gauntlet_fist.setTextureOffset(23, 74).addBox(-5.0F, -7.5F, -6.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
      this.gauntlet_fist_r1 = new AdvancedModelBox(this);
      this.gauntlet_fist_r1.setRotationPoint(-2.0F, 0.0F, -8.5F);
      this.gauntlet_fist.addChild(this.gauntlet_fist_r1);
      this.setRotationAngle(this.gauntlet_fist_r1, 0.0F, 0.2618F, 0.0F);
      this.gauntlet_fist_r1.setTextureOffset(64, 46).addBox(-2.0F, -5.0F, -7.5F, 4.0F, 10.0F, 8.0F, 0.0F, false);
      this.gauntlet_fist_r2 = new AdvancedModelBox(this);
      this.gauntlet_fist_r2.setRotationPoint(3.0F, 0.0F, -6.0F);
      this.gauntlet_fist.addChild(this.gauntlet_fist_r2);
      this.setRotationAngle(this.gauntlet_fist_r2, 0.0F, -0.48F, 0.0F);
      this.gauntlet_fist_r2.setTextureOffset(72, 29).addBox(-1.5F, -4.5F, -7.0F, 3.0F, 9.0F, 8.0F, 0.25F, false);
      this.void_stone_knuckle = new AdvancedModelBox(this);
      this.void_stone_knuckle.setRotationPoint(-5.25F, -2.5F, -6.0F);
      this.gauntlet_fist.addChild(this.void_stone_knuckle);
      this.void_stone_knuckle.setTextureOffset(0, 0).addBox(0.0F, 0.5F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
      this.gauntlet_arm = new AdvancedModelBox(this);
      this.gauntlet_arm.setRotationPoint(0.0F, 0.0F, 2.5F);
      this.root.addChild(this.gauntlet_arm);
      this.gauntlet_arm.setTextureOffset(0, 22).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.gauntlet_arm.setTextureOffset(0, 38).addBox(-2.5F, -4.5F, -1.0F, 7.0F, 9.0F, 2.0F, 0.0F, false);
      this.gauntlet_arm2 = new AdvancedModelBox(this);
      this.gauntlet_arm2.setRotationPoint(0.0F, 0.0F, -3.5F);
      this.root.addChild(this.gauntlet_arm2);
      this.gauntlet_arm2.setTextureOffset(34, 16).addBox(-4.0F, -4.0F, 0.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
      this.gauntlet_arm2.setTextureOffset(73, 2).addBox(-4.0F, -4.0F, 0.0F, 6.0F, 8.0F, 6.0F, -0.1F, false);
      this.gauntlet_arm2.setTextureOffset(81, 16).addBox(-3.5F, -3.5F, 0.0F, 5.0F, 7.0F, 6.0F, 0.0F, false);
      this.gauntlet_shoulder = new AdvancedModelBox(this);
      this.gauntlet_shoulder.setRotationPoint(2.0F, 0.0F, 9.0F);
      this.gauntlet_arm2.addChild(this.gauntlet_shoulder);
      this.setRotationAngle(this.gauntlet_shoulder, 0.0F, 0.6109F, 0.0F);
      this.gauntlet_shoulder.setTextureOffset(0, 0).addBox(-8.0F, -5.0F, -6.0F, 8.0F, 10.0F, 12.0F, 0.0F, false);
      this.gauntlet_shoulder.setTextureOffset(38, 84).addBox(-8.0F, -5.0F, -6.0F, 8.0F, 10.0F, 12.0F, 0.3F, false);
      this.big_void_stone = new AdvancedModelBox(this);
      this.big_void_stone.setRotationPoint(-8.0F, 0.0F, -2.0F);
      this.gauntlet_shoulder.addChild(this.big_void_stone);
      this.setRotationAngle(this.big_void_stone, 0.0F, 0.6109F, 0.0F);
      this.big_void_stone.setTextureOffset(28, 0).addBox(-8.0F, -2.0F, 0.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);
      this.big_void_stone.setTextureOffset(30, 52).addBox(-10.0F, -3.0F, -1.0F, 11.0F, 6.0F, 6.0F, 0.0F, false);
      this.big_void_stone.setTextureOffset(0, 49).addBox(-3.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.2F, false);
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
         this.gauntlet_fist_r1,
         this.gauntlet_fist_r2,
         this.void_stone_knuckle,
         this.gauntlet_arm,
         this.gauntlet_arm2,
         this.gauntlet_shoulder,
         this.big_void_stone
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
