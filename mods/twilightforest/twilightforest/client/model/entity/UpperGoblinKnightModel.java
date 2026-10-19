package twilightforest.client.model.entity;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.entity.monster.UpperGoblinKnight;

public class UpperGoblinKnightModel extends HumanoidModel<UpperGoblinKnight> {
   private final ModelPart breastplate = this.body.getChild("breastplate");
   private final ModelPart shield = this.leftArm.getChild("shield");

   public UpperGoblinKnightModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition checkForPack() {
      return JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? createJappaModel() : create();
   }

   private static LayerDefinition create() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      hat.addOrReplaceChild(
         "helmet",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -11.0F, -3.5F, 7.0F, 11.0F, 7.0F),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F)
      );
      PartDefinition rightHorn = hat.addOrReplaceChild(
         "right_horn_1",
         CubeListBuilder.create().texOffs(28, 0).addBox(-6.0F, -1.5F, -1.5F, 7.0F, 3.0F, 3.0F),
         PartPose.offsetAndRotation(-3.5F, -9.0F, 0.0F, 0.0F, (float) (Math.PI / 12), (float) (Math.PI / 18))
      );
      rightHorn.addOrReplaceChild(
         "right_horn_2",
         CubeListBuilder.create().texOffs(28, 6).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F),
         PartPose.offsetAndRotation(-5.5F, 0.0F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 18))
      );
      PartDefinition leftHorn = hat.addOrReplaceChild(
         "left_horn_1",
         CubeListBuilder.create().mirror().texOffs(28, 0).addBox(-1.0F, -1.5F, -1.5F, 7.0F, 3.0F, 3.0F),
         PartPose.offsetAndRotation(3.5F, -9.0F, 0.0F, 0.0F, (float) (-Math.PI / 12), (float) (-Math.PI / 18))
      );
      leftHorn.addOrReplaceChild(
         "left_horn_2",
         CubeListBuilder.create().mirror().texOffs(28, 6).addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F),
         PartPose.offsetAndRotation(5.5F, 0.0F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 18))
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 18)
            .addBox(-5.5F, 0.0F, -2.0F, 11.0F, 8.0F, 4.0F)
            .texOffs(30, 24)
            .addBox(-6.5F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F)
            .texOffs(30, 24)
            .addBox(5.5F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F),
         PartPose.offset(0.0F, 12.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "breastplate", CubeListBuilder.create().texOffs(64, 0).addBox(-6.5F, 0.0F, -3.0F, 13.0F, 12.0F, 6.0F), PartPose.offset(0.0F, -0.5F, 0.0F)
      );
      PartDefinition rightArm = partdefinition.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(44, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-6.5F, 14.0F, 0.0F)
      );
      rightArm.addOrReplaceChild(
         "spear",
         CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 40.0F, 2.0F),
         PartPose.offsetAndRotation(-2.0F, 8.5F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      PartDefinition leftArm = partdefinition.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(44, 16).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(6.5F, 14.0F, 0.0F)
      );
      leftArm.addOrReplaceChild(
         "shield",
         CubeListBuilder.create().texOffs(63, 36).addBox(-6.0F, -6.0F, -2.0F, 12.0F, 20.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(30, 16).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(-4.0F, 20.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(30, 16).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(4.0F, 20.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 64);
   }

   private static LayerDefinition createJappaModel() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(28, 0)
            .addBox(-8.0F, -14.0F, -1.9F, 16.0F, 14.0F, 2.0F)
            .texOffs(116, 0)
            .addBox(-6.0F, -12.0F, -0.9F, 4.0F, 2.0F, 2.0F)
            .texOffs(116, 4)
            .addBox(2.0F, -12.0F, -1.0F, 4.0F, 2.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, (float) (-Math.PI / 4), 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      head.addOrReplaceChild(
         "helm",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 11.0F, 7.0F),
         PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, (float) (Math.PI / 4), 0.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(0, 18).addBox(-5.5F, 0.0F, -2.0F, 11.0F, 8.0F, 4.0F), PartPose.offset(0.0F, 12.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "breastplate", CubeListBuilder.create().texOffs(64, 0).addBox(-6.5F, 0.0F, -3.0F, 13.0F, 12.0F, 6.0F), PartPose.offset(0.0F, -0.5F, 0.0F)
      );
      PartDefinition rightArm = partdefinition.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(44, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
         PartPose.offsetAndRotation(-5.5F, 14.0F, 0.0F, -2.3876104F, 0.0F, 0.10000737F)
      );
      PartDefinition leftArm = partdefinition.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(44, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
         PartPose.offsetAndRotation(5.5F, 14.0F, 0.0F, 0.20001474F, 0.0F, 0.10000737F)
      );
      rightArm.addOrReplaceChild(
         "spear",
         CubeListBuilder.create().texOffs(108, 0).addBox(-1.0F, -19.0F, -1.0F, 2.0F, 40.0F, 2.0F),
         PartPose.offsetAndRotation(-2.0F, 8.5F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      leftArm.addOrReplaceChild(
         "shield",
         CubeListBuilder.create().texOffs(63, 36).addBox(-6.0F, -6.0F, -2.0F, 12.0F, 20.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 6.083185F, 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(30, 24).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(-4.0F, 20.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(30, 16).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F), PartPose.offset(4.0F, 20.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 64);
   }

   public void setupAnim(UpperGoblinKnight entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean hasShield = entity.hasShield();
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.head.zRot = 0.0F;
      this.hat.yRot = this.head.yRot;
      this.hat.xRot = this.head.xRot;
      this.hat.zRot = this.head.zRot;
      this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
      float leftConstraint = hasShield ? 0.2F : limbSwingAmount;
      if (entity.isShieldDisabled()) {
         this.leftArm.zRot = Mth.cos(entity.tickCount * 3.25F) * (float) Math.PI * 0.4F * (float) (Math.PI / 180.0) - 0.4F;
      } else {
         this.leftArm.zRot = 0.0F;
      }

      this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * leftConstraint * 0.5F;
      this.rightArm.zRot = 0.0F;
      this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.rightLeg.yRot = 0.0F;
      this.leftLeg.yRot = 0.0F;
      if (this.riding) {
         this.rightArm.xRot -= (float) (Math.PI / 5);
         this.leftArm.xRot -= (float) (Math.PI / 5);
         this.rightLeg.xRot = 0.0F;
         this.leftLeg.xRot = 0.0F;
      }

      if (this.leftArmPose != ArmPose.EMPTY) {
         this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) (Math.PI / 10);
      }

      this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) (Math.PI / 10);
      this.rightArm.xRot -= 2.0734513F;
      if (entity.heavySpearTimer > 0) {
         this.rightArm.xRot = this.rightArm.xRot - this.getArmRotationDuringSwing(60.0F - entity.heavySpearTimer) * (float) (Math.PI / 180.0);
      }

      this.rightArm.yRot = 0.0F;
      this.leftArm.yRot = 0.0F;
      AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
      this.leftArm.zRot = -this.leftArm.zRot;
      this.shield.xRot = (float) (Math.PI * 2) - this.leftArm.xRot;
      this.breastplate.visible = entity.hasArmor();
      this.shield.visible = entity.hasShield();
   }

   private float getArmRotationDuringSwing(float attackTime) {
      if (attackTime <= 10.0F) {
         return attackTime;
      } else if (attackTime > 10.0F && attackTime <= 30.0F) {
         return 10.0F;
      } else if (attackTime > 30.0F && attackTime <= 33.0F) {
         return (attackTime - 30.0F) * -8.0F + 10.0F;
      } else if (attackTime > 33.0F && attackTime <= 50.0F) {
         return -15.0F;
      } else {
         return attackTime > 50.0F && attackTime <= 60.0F ? (10.0F - (attackTime - 50.0F)) * -1.5F : 0.0F;
      }
   }
}
