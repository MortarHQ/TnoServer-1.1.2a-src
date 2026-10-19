package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.animation.Boss_Respawn_Spawner_Animation;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Boss_Respawn_Spawner_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox bone;
   private final AdvancedModelBox one;
   private final AdvancedModelBox two;
   private final AdvancedModelBox three;
   private final AdvancedModelBox four;
   private final AdvancedModelBox bone2;
   private final AdvancedModelBox bone3;

   public Boss_Respawn_Spawner_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this, "root");
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(33, 0).addBox(-4.0F, -18.0F, -4.0F, 8.0F, 14.0F, 8.0F, 0.0F, false);
      this.bone = new AdvancedModelBox(this, "bone");
      this.bone.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.bone);
      this.one = new AdvancedModelBox(this, "one");
      this.one.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bone.addChild(this.one);
      this.one.setTextureOffset(0, 0).addBox(-8.0F, -18.0F, 0.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);
      this.two = new AdvancedModelBox(this, "two");
      this.two.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bone.addChild(this.two);
      this.two.setTextureOffset(0, 0).addBox(0.0F, -18.0F, 0.0F, 8.0F, 18.0F, 8.0F, 0.0F, true);
      this.three = new AdvancedModelBox(this, "three");
      this.three.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bone.addChild(this.three);
      this.three.setTextureOffset(0, 27).addBox(0.0F, -18.0F, -8.0F, 8.0F, 18.0F, 8.0F, 0.0F, true);
      this.four = new AdvancedModelBox(this, "four");
      this.four.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.bone.addChild(this.four);
      this.four.setTextureOffset(0, 27).addBox(-8.0F, -18.0F, -8.0F, 8.0F, 18.0F, 8.0F, 0.0F, false);
      this.bone2 = new AdvancedModelBox(this, "bone2");
      this.bone2.setRotationPoint(0.0F, -18.0F, 7.0F);
      this.root.addChild(this.bone2);
      this.bone2.setTextureOffset(33, 23).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 4.0F, 6.0F, 0.0F, false);
      this.bone2.setTextureOffset(33, 41).addBox(-3.0F, -4.0F, -7.0F, 6.0F, 0.0F, 1.0F, 0.0F, false);
      this.bone3 = new AdvancedModelBox(this, "bone3");
      this.bone3.setRotationPoint(0.0F, -18.0F, -7.0F);
      this.root.addChild(this.bone3);
      this.bone3.setTextureOffset(33, 34).addBox(-4.0F, -4.0F, 1.0F, 8.0F, 4.0F, 2.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.bone, this.one, this.two, this.three, this.four, this.bone2, this.bone3);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(Boss_Respawn_Spawner_Block_Entity entity, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = entity.tickCount + partialTick;
      this.animate(entity.getAnimationState("opening"), Boss_Respawn_Spawner_Animation.SPAWNING, ageInTicks, 1.0F);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
