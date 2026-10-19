package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class Wrath_of_Desert_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox bow;
   private final AdvancedModelBox bow_r1;
   private final AdvancedModelBox arm1;
   private final AdvancedModelBox cube_r8_r1;
   private final AdvancedModelBox arm2;
   private final AdvancedModelBox bow_string;
   private final AdvancedModelBox string;
   private final AdvancedModelBox string2;
   private final AdvancedModelBox arrow3;
   private final AdvancedModelBox arrow_pivot3;
   private final AdvancedModelBox third_sand3;
   private final AdvancedModelBox third_sand2;
   private final AdvancedModelBox third_sand1;
   private final AdvancedModelBox arrow2;
   private final AdvancedModelBox arrow_pivot2;
   private final AdvancedModelBox second_sand3;
   private final AdvancedModelBox second_sand2;
   private final AdvancedModelBox second_sand1;
   private final AdvancedModelBox arrow1;
   private final AdvancedModelBox arrow_pivot1;
   private final AdvancedModelBox first_sand3;
   private final AdvancedModelBox first_sand2;
   private final AdvancedModelBox first_sand1;

   public Wrath_of_Desert_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.bow = new AdvancedModelBox(this);
      this.bow.setRotationPoint(5.0E-4F, -17.0644F, -6.2737F);
      this.root.addChild(this.bow);
      this.bow.setTextureOffset(26, 49).addBox(-1.0F, -11.4356F, -1.2263F, 2.0F, 18.0F, 3.0F, 0.0F, false);
      this.bow.setTextureOffset(36, 91).addBox(0.0F, -11.4356F, 1.7737F, 0.0F, 19.0F, 2.0F, 0.0F, false);
      this.bow.setTextureOffset(0, 0).addBox(0.0F, -8.4356F, -9.2263F, 0.0F, 12.0F, 8.0F, 0.0F, false);
      this.bow_r1 = new AdvancedModelBox(this);
      this.bow_r1.setRotationPoint(-1.5F, -2.4356F, 0.2737F);
      this.bow.addChild(this.bow_r1);
      this.setRotationAngle(this.bow_r1, -0.7854F, 0.0F, 0.0F);
      this.bow_r1.setTextureOffset(36, 83).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, false);
      this.bow_r1.setTextureOffset(36, 73).addBox(2.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, false);
      this.arm1 = new AdvancedModelBox(this);
      this.arm1.setRotationPoint(-5.0E-4F, -7.5162F, -0.7263F);
      this.bow.addChild(this.arm1);
      this.setRotationAngle(this.arm1, -0.5672F, 0.0F, 0.0F);
      this.arm1.setTextureOffset(43, 22).addBox(-2.4995F, -15.6119F, -3.9929F, 5.0F, 18.0F, 4.0F, 0.0F, false);
      this.arm1.setTextureOffset(47, 8).addBox(-2.4995F, -0.6119F, -11.9929F, 5.0F, 6.0F, 8.0F, 0.0F, false);
      this.arm1.setTextureOffset(61, 39).addBox(2.5005F, -15.6119F, -3.9929F, 5.0F, 3.0F, 2.0F, 0.0F, false);
      this.arm1.setTextureOffset(65, 44).addBox(2.5005F, -10.6119F, -3.9929F, 4.0F, 2.0F, 1.0F, 0.0F, false);
      this.arm1.setTextureOffset(65, 47).addBox(-6.4995F, -10.6119F, -3.9929F, 4.0F, 2.0F, 1.0F, 0.0F, false);
      this.arm1.setTextureOffset(61, 34).addBox(-7.4995F, -15.6119F, -3.9929F, 5.0F, 3.0F, 2.0F, 0.0F, false);
      this.arm1.setTextureOffset(38, 45).addBox(5.0E-4F, -22.846F, -2.9929F, 0.0F, 23.0F, 5.0F, 0.0F, false);
      this.cube_r8_r1 = new AdvancedModelBox(this);
      this.cube_r8_r1.setRotationPoint(5.0E-4F, -11.1119F, -4.4929F);
      this.arm1.addChild(this.cube_r8_r1);
      this.setRotationAngle(this.cube_r8_r1, 0.0F, 0.0F, 0.7854F);
      this.cube_r8_r1.setTextureOffset(61, 28).addBox(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);
      this.arm2 = new AdvancedModelBox(this);
      this.arm2.setRotationPoint(-5.0E-4F, 2.6838F, -1.0263F);
      this.bow.addChild(this.arm2);
      this.setRotationAngle(this.arm2, 0.5672F, 0.0F, 0.0F);
      this.arm2.setTextureOffset(0, 49).addBox(5.0E-4F, -0.0933F, -2.6868F, 0.0F, 23.0F, 5.0F, 0.0F, false);
      this.arm2.setTextureOffset(11, 49).addBox(-1.4995F, -2.0933F, -3.6868F, 3.0F, 18.0F, 4.0F, 0.0F, false);
      this.arm2.setTextureOffset(73, 11).addBox(-1.4995F, -4.0933F, -10.6868F, 3.0F, 4.0F, 7.0F, 0.0F, false);
      this.arm2.setTextureOffset(11, 77).addBox(-1.4995F, 3.9067F, -3.6868F, 3.0F, 4.0F, 4.0F, 0.3F, false);
      this.bow_string = new AdvancedModelBox(this);
      this.bow_string.setRotationPoint(-5.0E-4F, -1.5F, 10.0F);
      this.bow.addChild(this.bow_string);
      this.string = new AdvancedModelBox(this);
      this.string.setRotationPoint(0.5005F, 0.0F, 0.0F);
      this.bow_string.addChild(this.string);
      this.string.setTextureOffset(0, 22).addBox(-1.001F, 0.0859F, -0.2263F, 1.0F, 19.0F, 0.0F, 0.0F, false);
      this.string2 = new AdvancedModelBox(this);
      this.string2.setRotationPoint(-0.4995F, 0.0F, -0.0485F);
      this.bow_string.addChild(this.string2);
      this.string2.setTextureOffset(17, 0).addBox(-0.001F, -18.9142F, -0.1778F, 1.0F, 19.0F, 0.0F, 0.0F, false);
      this.arrow1 = new AdvancedModelBox(this);
      this.arrow1.setRotationPoint(0.3536F, -1.4356F, 0.2737F);
      this.bow_string.addChild(this.arrow1);
      this.setRotationAngle(this.arrow1, -0.0436F, 0.0F, 0.0F);
      this.arrow_pivot1 = new AdvancedModelBox(this);
      this.arrow_pivot1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.arrow1.addChild(this.arrow_pivot1);
      this.setRotationAngle(this.arrow_pivot1, 0.0F, 0.0F, -0.7854F);
      this.arrow_pivot1.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -20.0F, 5.0F, 0.0F, 21.0F, 0.0F, false);
      this.arrow_pivot1.setTextureOffset(0, 22).addBox(-0.5F, -2.5F, -20.0F, 0.0F, 5.0F, 21.0F, 0.0F, false);
      this.arrow_pivot1.setTextureOffset(3, 22).addBox(-3.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
      this.first_sand3 = new AdvancedModelBox(this);
      this.first_sand3.setRotationPoint(-0.5F, 0.0F, -12.0F);
      this.arrow_pivot1.addChild(this.first_sand3);
      this.setRotationAngle(this.first_sand3, 0.0F, 0.0F, -1.0036F);
      this.first_sand3.setTextureOffset(0, 107).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.first_sand2 = new AdvancedModelBox(this);
      this.first_sand2.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.first_sand3.addChild(this.first_sand2);
      this.setRotationAngle(this.first_sand2, 0.0F, 0.0F, -0.48F);
      this.first_sand2.setTextureOffset(0, 112).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 2.0F, 0.0F, false);
      this.first_sand1 = new AdvancedModelBox(this);
      this.first_sand1.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.first_sand2.addChild(this.first_sand1);
      this.first_sand1.setTextureOffset(0, 119).addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 2.0F, 0.0F, false);
      this.arrow2 = new AdvancedModelBox(this);
      this.arrow2.setRotationPoint(0.3536F, -1.4356F, 0.2737F);
      this.bow_string.addChild(this.arrow2);
      this.setRotationAngle(this.arrow2, 0.0873F, 0.0873F, 0.0F);
      this.arrow_pivot2 = new AdvancedModelBox(this);
      this.arrow_pivot2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.arrow2.addChild(this.arrow_pivot2);
      this.setRotationAngle(this.arrow_pivot2, 0.0F, 0.0F, -0.7854F);
      this.arrow_pivot2.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -20.0F, 5.0F, 0.0F, 21.0F, 0.0F, false);
      this.arrow_pivot2.setTextureOffset(0, 22).addBox(-0.5F, -2.5F, -20.0F, 0.0F, 5.0F, 21.0F, 0.0F, false);
      this.arrow_pivot2.setTextureOffset(3, 22).addBox(-3.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
      this.second_sand3 = new AdvancedModelBox(this);
      this.second_sand3.setRotationPoint(-0.5F, 0.0F, -12.0F);
      this.arrow_pivot2.addChild(this.second_sand3);
      this.setRotationAngle(this.second_sand3, 0.0F, 0.0F, -1.0036F);
      this.second_sand3.setTextureOffset(0, 107).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.second_sand2 = new AdvancedModelBox(this);
      this.second_sand2.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.second_sand3.addChild(this.second_sand2);
      this.setRotationAngle(this.second_sand2, 0.0F, 0.0F, -0.48F);
      this.second_sand2.setTextureOffset(0, 112).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 2.0F, 0.0F, false);
      this.second_sand1 = new AdvancedModelBox(this);
      this.second_sand1.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.second_sand2.addChild(this.second_sand1);
      this.second_sand1.setTextureOffset(0, 119).addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 2.0F, 0.0F, false);
      this.arrow3 = new AdvancedModelBox(this);
      this.arrow3.setRotationPoint(0.3536F, -1.4356F, 0.2737F);
      this.bow_string.addChild(this.arrow3);
      this.setRotationAngle(this.arrow3, 0.0873F, -0.0873F, 0.0F);
      this.arrow_pivot3 = new AdvancedModelBox(this);
      this.arrow_pivot3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.arrow3.addChild(this.arrow_pivot3);
      this.setRotationAngle(this.arrow_pivot3, 0.0F, 0.0F, -0.7854F);
      this.arrow_pivot3.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -20.0F, 5.0F, 0.0F, 21.0F, 0.0F, false);
      this.arrow_pivot3.setTextureOffset(0, 22).addBox(-0.5F, -2.5F, -20.0F, 0.0F, 5.0F, 21.0F, 0.0F, false);
      this.arrow_pivot3.setTextureOffset(3, 22).addBox(-3.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
      this.third_sand3 = new AdvancedModelBox(this);
      this.third_sand3.setRotationPoint(-0.5F, 0.0F, -12.0F);
      this.arrow_pivot3.addChild(this.third_sand3);
      this.setRotationAngle(this.third_sand3, 0.0F, 0.0F, -1.0036F);
      this.third_sand3.setTextureOffset(0, 107).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
      this.third_sand2 = new AdvancedModelBox(this);
      this.third_sand2.setRotationPoint(0.0F, 0.0F, -3.0F);
      this.third_sand3.addChild(this.third_sand2);
      this.setRotationAngle(this.third_sand2, 0.0F, 0.0F, -0.48F);
      this.third_sand2.setTextureOffset(0, 112).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 2.0F, 0.0F, false);
      this.third_sand1 = new AdvancedModelBox(this);
      this.third_sand1.setRotationPoint(0.0F, 0.0F, -4.0F);
      this.third_sand2.addChild(this.third_sand1);
      this.third_sand1.setTextureOffset(0, 119).addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 2.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entity, float pullAmount, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      this.bow_string.rotationPointZ += pullAmount * 9.0F;
      float scale = pullAmount * 1.2F;
      this.arrow_pivot1.setScale(scale, scale, scale);
      this.arrow_pivot2.setScale(scale, scale, scale);
      this.arrow_pivot3.setScale(scale, scale, scale);
      this.first_sand3.setScale(scale, scale, scale);
      this.second_sand3.setScale(scale, scale, scale);
      this.third_sand3.setScale(scale, scale, scale);
      this.first_sand2.setScale(scale, scale, scale);
      this.second_sand2.setScale(scale, scale, scale);
      this.third_sand2.setScale(scale, scale, scale);
      this.first_sand1.setScale(scale, scale, scale);
      this.second_sand1.setScale(scale, scale, scale);
      this.third_sand1.setScale(scale, scale, scale);
      this.string2.rotateAngleX = this.string2.rotateAngleX + (float)Math.toRadians(pullAmount * 25.0F);
      this.string.rotateAngleX = this.string.rotateAngleX + (float)Math.toRadians(pullAmount * -25.0F);
      this.arm1.rotateAngleX = this.arm1.rotateAngleX + (float)Math.toRadians(pullAmount * -15.0F);
      this.arm2.rotateAngleX = this.arm2.rotateAngleX + (float)Math.toRadians(pullAmount * 15.0F);
      this.first_sand3.rotateAngleZ += ageInTicks * 0.7F;
      AdvancedModelBox var8 = this.first_sand2;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.first_sand3.rotateAngleZ + ageInTicks * 0.5F);
      var8 = this.first_sand1;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.first_sand3.rotateAngleZ - this.first_sand2.rotateAngleZ + ageInTicks * 0.3F);
      this.second_sand3.rotateAngleZ += ageInTicks * 0.7F;
      var8 = this.second_sand2;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.second_sand3.rotateAngleZ + ageInTicks * 0.5F);
      var8 = this.second_sand1;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.second_sand3.rotateAngleZ - this.second_sand2.rotateAngleZ + ageInTicks * 0.3F);
      this.third_sand3.rotateAngleZ += ageInTicks * 0.7F;
      var8 = this.third_sand2;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.third_sand3.rotateAngleZ + ageInTicks * 0.5F);
      var8 = this.third_sand1;
      var8.rotateAngleZ = var8.rotateAngleZ + (-this.third_sand3.rotateAngleZ - this.third_sand2.rotateAngleZ + ageInTicks * 0.3F);
   }

   public void animateStack(ItemStack itemStackIn) {
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.bow,
         this.bow_r1,
         this.arm1,
         this.cube_r8_r1,
         this.arm2,
         this.bow_string,
         this.string,
         this.string2,
         this.arrow3,
         this.arrow_pivot3,
         this.third_sand3,
         new AdvancedModelBox[]{
            this.third_sand2,
            this.third_sand1,
            this.arrow2,
            this.arrow_pivot2,
            this.second_sand3,
            this.second_sand2,
            this.second_sand1,
            this.arrow1,
            this.arrow_pivot1,
            this.first_sand3,
            this.first_sand2,
            this.first_sand1
         }
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
