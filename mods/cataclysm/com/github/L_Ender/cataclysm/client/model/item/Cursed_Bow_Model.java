package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Cursed_Bow_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox bow;
   private final AdvancedModelBox arm1;
   private final AdvancedModelBox arm2;
   private final AdvancedModelBox bow_string;
   private final AdvancedModelBox string;
   private final AdvancedModelBox string2;
   private final AdvancedModelBox arrow;
   private final AdvancedModelBox arrow_pivot1;
   private final AdvancedModelBox arrow2;
   private final AdvancedModelBox arrow_pivot2;
   private final AdvancedModelBox arrow3;
   private final AdvancedModelBox arrow_pivot3;

   public Cursed_Bow_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.bow = new AdvancedModelBox(this);
      this.bow.setRotationPoint(5.0E-4F, -17.0644F, -6.2737F);
      this.root.addChild(this.bow);
      this.bow.setTextureOffset(26, 49).addBox(-1.0F, -11.4356F, -1.2263F, 2.0F, 18.0F, 3.0F, 0.0F, false);
      this.bow.setTextureOffset(0, 0).addBox(0.0F, -8.4356F, -9.2263F, 0.0F, 12.0F, 8.0F, 0.0F, false);
      this.arm1 = new AdvancedModelBox(this);
      this.arm1.setRotationPoint(-5.0E-4F, -7.5162F, -0.7263F);
      this.bow.addChild(this.arm1);
      this.setRotationAngle(this.arm1, -0.5672F, 0.0F, 0.0F);
      this.arm1.setTextureOffset(43, 22).addBox(-2.4995F, -15.6119F, -3.9929F, 5.0F, 18.0F, 4.0F, 0.0F, false);
      this.arm1.setTextureOffset(38, 45).addBox(5.0E-4F, -22.846F, -2.9929F, 0.0F, 23.0F, 5.0F, 0.0F, false);
      this.arm2 = new AdvancedModelBox(this);
      this.arm2.setRotationPoint(-5.0E-4F, 2.6838F, -1.0263F);
      this.bow.addChild(this.arm2);
      this.setRotationAngle(this.arm2, 0.5672F, 0.0F, 0.0F);
      this.arm2.setTextureOffset(0, 49).addBox(5.0E-4F, -0.0933F, -2.6868F, 0.0F, 23.0F, 5.0F, 0.0F, false);
      this.arm2.setTextureOffset(11, 49).addBox(-1.4995F, -2.0933F, -3.6868F, 3.0F, 18.0F, 4.0F, 0.0F, false);
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
      this.arrow = new AdvancedModelBox(this);
      this.arrow.setRotationPoint(0.3536F, -1.4356F, 0.2737F);
      this.bow_string.addChild(this.arrow);
      this.setRotationAngle(this.arrow, -0.0436F, 0.0F, 0.0F);
      this.arrow_pivot1 = new AdvancedModelBox(this);
      this.arrow_pivot1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.arrow.addChild(this.arrow_pivot1);
      this.setRotationAngle(this.arrow_pivot1, 0.0F, 0.0F, -0.7854F);
      this.arrow_pivot1.setTextureOffset(0, 0).addBox(-3.0F, 0.0F, -20.0F, 5.0F, 0.0F, 21.0F, 0.0F, false);
      this.arrow_pivot1.setTextureOffset(0, 22).addBox(-0.5F, -2.5F, -20.0F, 0.0F, 5.0F, 21.0F, 0.0F, false);
      this.arrow_pivot1.setTextureOffset(3, 22).addBox(-3.0F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);
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
      this.string2.rotateAngleX = this.string2.rotateAngleX + (float)Math.toRadians(pullAmount * 25.0F);
      this.string.rotateAngleX = this.string.rotateAngleX + (float)Math.toRadians(pullAmount * -25.0F);
      this.arm1.rotateAngleX = this.arm1.rotateAngleX + (float)Math.toRadians(pullAmount * -15.0F);
      this.arm2.rotateAngleX = this.arm2.rotateAngleX + (float)Math.toRadians(pullAmount * 15.0F);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.bow_string,
         this.bow,
         this.arm1,
         this.arm2,
         this.string,
         this.string2,
         this.arrow,
         this.arrow2,
         this.arrow3,
         this.arrow_pivot1,
         this.arrow_pivot2,
         new AdvancedModelBox[]{this.arrow_pivot3}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
