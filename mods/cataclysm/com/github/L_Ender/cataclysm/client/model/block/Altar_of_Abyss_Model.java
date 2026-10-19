package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Altar_of_Abyss_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox tentacle;
   private final AdvancedModelBox skul;
   private final AdvancedModelBox maw;

   public Altar_of_Abyss_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 20).addBox(-8.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);
      this.root.setTextureOffset(0, 40).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 5.0F, 12.0F, 0.0F, false);
      this.root.setTextureOffset(73, 16).addBox(-9.0F, -2.0F, 5.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(59, 70).addBox(-9.0F, -2.0F, -9.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(42, 69).addBox(5.0F, -2.0F, -9.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(13, 58).addBox(5.0F, -2.0F, 5.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
      this.tentacle = new AdvancedModelBox(this);
      this.tentacle.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.tentacle);
      this.tentacle.setTextureOffset(0, 75).addBox(0.0F, -4.0F, -10.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.tentacle.setTextureOffset(30, 74).addBox(-4.0F, -4.0F, 8.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
      this.tentacle.setTextureOffset(17, 74).addBox(8.0F, -6.0F, 0.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);
      this.tentacle.setTextureOffset(72, 73).addBox(-10.0F, -6.0F, -4.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);
      this.tentacle.setTextureOffset(0, 58).addBox(8.0F, -14.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, false);
      this.tentacle.setTextureOffset(41, 52).addBox(-10.0F, -14.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, false);
      this.tentacle.setTextureOffset(65, 32).addBox(-4.0F, -12.0F, -10.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
      this.tentacle.setTextureOffset(62, 59).addBox(-4.0F, -12.0F, 8.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
      this.skul = new AdvancedModelBox(this);
      this.skul.setRotationPoint(0.0F, -11.0F, 2.0F);
      this.root.addChild(this.skul);
      this.setRotationAngle(this.skul, -0.5236F, 0.0F, 0.0F);
      this.skul.setTextureOffset(49, 0).addBox(-6.0F, -9.0F, 0.0F, 12.0F, 9.0F, 6.0F, 0.0F, false);
      this.skul.setTextureOffset(0, 20).addBox(4.0F, -9.0F, -4.0F, 2.0F, 7.0F, 4.0F, 0.0F, false);
      this.skul.setTextureOffset(0, 0).addBox(-6.0F, -9.0F, -4.0F, 2.0F, 7.0F, 4.0F, 0.0F, false);
      this.skul.setTextureOffset(37, 40).addBox(-4.0F, -13.0F, -1.0F, 8.0F, 4.0F, 7.0F, 0.0F, false);
      this.skul.setTextureOffset(54, 52).addBox(-4.0F, -13.0F, -5.0F, 8.0F, 2.0F, 4.0F, 0.0F, false);
      this.maw = new AdvancedModelBox(this);
      this.maw.setRotationPoint(0.0F, -11.0F, -2.0F);
      this.root.addChild(this.maw);
      this.setRotationAngle(this.maw, 0.5236F, 0.0F, 0.0F);
      this.maw.setTextureOffset(49, 20).addBox(-5.0F, -8.0F, -3.0F, 10.0F, 8.0F, 3.0F, 0.0F, false);
      this.maw.setTextureOffset(43, 76).addBox(-5.0F, -8.0F, 0.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
      this.maw.setTextureOffset(0, 40).addBox(4.0F, -8.0F, 0.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
      this.maw.setTextureOffset(68, 43).addBox(-3.0F, -12.0F, -4.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
      this.maw.setTextureOffset(21, 65).addBox(-3.0F, -12.0F, 0.0F, 6.0F, 4.0F, 4.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.tentacle, this.skul, this.maw);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void animate(AltarOfAbyss_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float amount = beak.getChompProgress(partialTick);
      this.progressRotationPrev(this.skul, amount, (float)Math.toRadians(30.0), 0.0F, 0.0F, 30.0F);
      this.progressRotationPrev(this.maw, amount, (float)Math.toRadians(-30.0), 0.0F, 0.0F, 30.0F);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
