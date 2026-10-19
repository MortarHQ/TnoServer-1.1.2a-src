package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Void_Forge_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox hammer_head;
   private final AdvancedModelBox back;
   private final AdvancedModelBox handle;
   private final AdvancedModelBox handle2;
   private final AdvancedModelBox crystal;
   private final AdvancedModelBox crystal2;
   private final AdvancedModelBox crystal3;

   public Void_Forge_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 11.0F, 0.0F);
      this.hammer_head = new AdvancedModelBox(this);
      this.hammer_head.setRotationPoint(0.0F, 20.0F, -1.0F);
      this.root.addChild(this.hammer_head);
      this.hammer_head.setTextureOffset(33, 15).addBox(-3.0F, -38.0F, -2.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(7, 46).addBox(3.25F, -33.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(51, 53).addBox(-3.25F, -33.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(38, 53).addBox(-3.25F, -37.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(38, 53).addBox(3.25F, -37.5F, -2.0F, 0.0F, 2.0F, 6.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(7, 55).addBox(-3.5F, -38.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(58, 15).addBox(-3.5F, -33.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(58, 27).addBox(0.5F, -38.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(58, 33).addBox(0.5F, -38.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(60, 0).addBox(0.5F, -33.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(60, 6).addBox(0.5F, -33.5F, 2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(58, 21).addBox(-3.5F, -33.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(18, 56).addBox(-3.5F, -38.5F, -2.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(0, 0).addBox(-4.0F, -39.0F, -10.0F, 8.0F, 9.0F, 8.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(33, 29).addBox(-4.5F, -39.5F, -11.0F, 9.0F, 10.0F, 3.0F, 0.0F, false);
      this.hammer_head.setTextureOffset(0, 18).addBox(-4.0F, -39.0F, -9.0F, 8.0F, 9.0F, 8.0F, 0.3F, false);
      this.back = new AdvancedModelBox(this);
      this.back.setRotationPoint(0.0F, -35.0F, 9.0F);
      this.hammer_head.addChild(this.back);
      this.back.setTextureOffset(33, 0).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 9.0F, 5.0F, 0.0F, false);
      this.back.setTextureOffset(0, 36).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 5.0F, 4.0F, 0.0F, false);
      this.crystal = new AdvancedModelBox(this);
      this.crystal.setRotationPoint(0.0F, -35.0F, 12.0F);
      this.hammer_head.addChild(this.crystal);
      this.crystal.setTextureOffset(0, 63).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
      this.crystal.setTextureOffset(0, 77).addBox(-3.0F, -1.0F, -3.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
      this.crystal2 = new AdvancedModelBox(this);
      this.crystal2.setRotationPoint(0.0F, -39.75F, 9.75F);
      this.hammer_head.addChild(this.crystal2);
      this.setRotationAngle(this.crystal2, 0.7854F, 0.0F, 0.0F);
      this.crystal2.setTextureOffset(0, 63).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
      this.crystal2.setTextureOffset(0, 77).addBox(-3.0F, -1.0F, -3.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
      this.crystal3 = new AdvancedModelBox(this);
      this.crystal3.setRotationPoint(0.0F, -30.25F, 9.75F);
      this.hammer_head.addChild(this.crystal3);
      this.setRotationAngle(this.crystal3, -0.7854F, 0.0F, 0.0F);
      this.crystal3.setTextureOffset(0, 63).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
      this.crystal3.setTextureOffset(0, 77).addBox(-3.0F, -1.0F, -3.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(0.0F, 12.0F, 0.5F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(0, 46).addBox(-0.5F, -18.0F, -1.5F, 1.0F, 14.0F, 2.0F, 0.0F, false);
      this.handle.setTextureOffset(19, 43).addBox(0.0F, -2.0F, -3.5F, 0.0F, 6.0F, 6.0F, 0.0F, false);
      this.handle.setTextureOffset(25, 36).addBox(-0.5F, -4.0F, -1.5F, 1.0F, 4.0F, 2.0F, 0.2F, false);
      this.handle2 = new AdvancedModelBox(this);
      this.handle2.setRotationPoint(0.0F, 7.0F, 0.5F);
      this.root.addChild(this.handle2);
      this.handle2.setTextureOffset(38, 43).addBox(-1.5F, -18.0F, -2.5F, 3.0F, 5.0F, 4.0F, 0.0F, false);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.hammer_head, this.back, this.handle, this.handle2, this.crystal, this.crystal2, this.crystal3);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
