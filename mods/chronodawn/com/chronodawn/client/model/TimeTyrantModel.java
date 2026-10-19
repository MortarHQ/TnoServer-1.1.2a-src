package com.chronodawn.client.model;

import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class TimeTyrantModel extends EntityModel<TimeTyrantEntity> {
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftForearm;
   private final ModelPart rightForearm;
   private final ModelPart leftTimeHourglass;
   private final ModelPart rightTimeHourglass;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public TimeTyrantModel(ModelPart root) {
      this.root = root.getChild("root");
      this.body = this.root.getChild("body");
      this.leftArm = this.body.getChild("left_arm");
      this.rightArm = this.body.getChild("right_arm");
      this.head = this.body.getChild("head");
      this.leftLeg = this.root.getChild("left_leg");
      this.rightLeg = this.root.getChild("right_leg");
      this.leftForearm = this.leftArm.getChild("arm_left2_r1");
      this.rightForearm = this.rightArm.getChild("arm_right2_r1");
      this.leftTimeHourglass = this.leftArm.getChild("left_time_hourglass2");
      this.rightTimeHourglass = this.rightArm.getChild("right_time_hourglass");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(68, 0).addBox(-7.0F, -23.0F, -7.0F, 14.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition body_r1 = body.addOrReplaceChild(
         "body_r1",
         CubeListBuilder.create()
            .texOffs(0, 28)
            .addBox(-8.0F, -34.0F, -6.0F, 16.0F, 21.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-11.0F, -35.0F, -7.0F, 22.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = body.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(30, 76)
            .addBox(0.0F, -6.0F, -4.0F, 8.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(0, 60)
            .addBox(0.0F, -4.0F, -3.0F, 7.0F, 20.0F, 8.0F, new CubeDeformation(-0.2F)),
         PartPose.offset(11.0F, -40.0F, 0.0F)
      );
      PartDefinition arm_left3_r1 = left_arm.addOrReplaceChild(
         "arm_left3_r1",
         CubeListBuilder.create().texOffs(106, 62).addBox(0.0F, -4.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(0.0F, 30.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition arm_left2_r1 = left_arm.addOrReplaceChild(
         "arm_left2_r1",
         CubeListBuilder.create().texOffs(96, 109).addBox(0.0F, -3.0F, -3.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition left_time_hourglass2 = left_arm.addOrReplaceChild(
         "left_time_hourglass2",
         CubeListBuilder.create()
            .texOffs(30, 60)
            .addBox(23.0F, -13.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(112, 48)
            .addBox(24.0F, -8.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(112, 54)
            .addBox(24.0F, -12.9F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
            .texOffs(126, 48)
            .addBox(24.5F, -11.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(126, 54)
            .addBox(25.0F, -12.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-22.0F, 0.0F, 0.0F)
      );
      PartDefinition right_arm = body.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create()
            .texOffs(68, 76)
            .addBox(-8.0F, -6.0F, -4.0F, 8.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(54, 48)
            .addBox(-7.0F, -4.0F, -3.0F, 7.0F, 20.0F, 8.0F, new CubeDeformation(-0.2F)),
         PartPose.offset(-11.0F, -40.0F, 0.0F)
      );
      PartDefinition arm_right3_r1 = right_arm.addOrReplaceChild(
         "arm_right3_r1",
         CubeListBuilder.create().texOffs(96, 94).addBox(-8.0F, -4.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(0.0F, 30.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition arm_right2_r1 = right_arm.addOrReplaceChild(
         "arm_right2_r1",
         CubeListBuilder.create().texOffs(106, 77).addBox(-7.0F, -3.0F, -3.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 18.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition right_time_hourglass = right_arm.addOrReplaceChild(
         "right_time_hourglass",
         CubeListBuilder.create()
            .texOffs(30, 60)
            .addBox(-6.0F, -13.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(112, 48)
            .addBox(-5.0F, -8.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(112, 54)
            .addBox(-5.0F, -12.9F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F))
            .texOffs(126, 48)
            .addBox(-4.5F, -11.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(126, 54)
            .addBox(-4.0F, -12.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -32.0F, 0.0F));
      PartDefinition head_r1 = head.addOrReplaceChild(
         "head_r1",
         CubeListBuilder.create()
            .texOffs(114, 0)
            .addBox(-8.0F, -25.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(110, 13)
            .addBox(6.0F, -25.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition head_r2 = head.addOrReplaceChild(
         "head_r2",
         CubeListBuilder.create()
            .texOffs(0, 88)
            .addBox(-6.0F, -22.0F, -8.0F, 12.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(54, 28)
            .addBox(-6.0F, -22.0F, -7.0F, 12.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(84, 48).addBox(1.0F, 19.0F, -10.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -16.0F, 0.0F)
      );
      PartDefinition leg_left_r1 = left_leg.addOrReplaceChild(
         "leg_left_r1",
         CubeListBuilder.create().texOffs(96, 27).addBox(2.0F, -3.0F, -8.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(-1.0F, 11.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leg_left_r2 = left_leg.addOrReplaceChild(
         "leg_left_r2",
         CubeListBuilder.create().texOffs(32, 94).addBox(2.0F, -10.0F, -8.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition right_leg = root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(70, 13).addBox(-9.0F, 19.0F, -10.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -16.0F, 0.0F)
      );
      PartDefinition leg_right_r1 = right_leg.addOrReplaceChild(
         "leg_right_r1",
         CubeListBuilder.create().texOffs(64, 94).addBox(-7.0F, -3.0F, -8.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(-1.0F, 11.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leg_right_r2 = right_leg.addOrReplaceChild(
         "leg_right_r2",
         CubeListBuilder.create().texOffs(0, 94).addBox(-7.0F, -10.0F, -8.0F, 7.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 256, 256);
   }

   public void setupAnim(TimeTyrantEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.xRot = 0.0F;
      this.head.yRot = 0.0F;
      this.body.xRot = 0.0F;
      this.body.yRot = 0.0F;
      this.leftArm.xRot = 0.0F;
      this.leftArm.zRot = 0.0F;
      this.rightArm.xRot = 0.0F;
      this.rightArm.zRot = 0.0F;
      this.leftForearm.xRot = 0.0F;
      this.rightForearm.xRot = 0.0F;
      this.leftLeg.xRot = 0.0F;
      this.rightLeg.xRot = 0.0F;
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
      this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount;
      this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      if (limbSwingAmount < 0.01F && entity.attackAnim <= 0.0F) {
         float idleSwing = ageInTicks * 0.03F;
         this.rightArm.xRot = this.rightArm.xRot + Mth.cos(idleSwing) * 0.08F;
         this.leftArm.xRot = this.leftArm.xRot + Mth.cos(idleSwing + (float) Math.PI) * 0.08F;
         this.body.yRot = Mth.cos(idleSwing * 0.5F) * 0.05F;
      }

      if (entity.attackAnim > 0.0F) {
         float attackProgress = entity.attackAnim;
         float armRaise = Mth.sin(attackProgress * (float) Math.PI);
         this.rightArm.xRot = -2.5F * armRaise;
         this.leftArm.xRot = -2.5F * armRaise;
         this.rightArm.zRot = 0.5F * armRaise;
         this.leftArm.zRot = -0.5F * armRaise;
         this.leftForearm.xRot = -0.5F * armRaise;
         this.rightForearm.xRot = -0.5F * armRaise;
         this.body.xRot = 0.2F * armRaise;
      }

      int phase = entity.getPhase();
      if (phase == 2) {
         if (limbSwingAmount < 0.01F && entity.attackAnim <= 0.0F) {
            float fastIdleSwing = ageInTicks * 0.08F;
            this.rightArm.xRot = this.rightArm.xRot + Mth.cos(fastIdleSwing) * 0.1F;
            this.leftArm.xRot = this.leftArm.xRot + Mth.cos(fastIdleSwing + (float) Math.PI) * 0.1F;
         }
      } else if (phase == 3 && entity.attackAnim <= 0.0F && limbSwingAmount < 0.01F) {
         float dangerSwing = ageInTicks * 0.05F;
         this.rightArm.xRot = -1.5F + Mth.cos(dangerSwing) * 0.2F;
         this.leftArm.xRot = -1.5F + Mth.cos(dangerSwing + (float) Math.PI) * 0.2F;
         this.rightArm.zRot = 0.3F;
         this.leftArm.zRot = -0.3F;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
