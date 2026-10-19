package fuzs.illagerinvasion.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;

public class InvokerEntityModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart hat;
   private final ModelPart arms;
   private final ModelPart body;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;
   private final ModelPart rightArm;
   private final ModelPart leftArm;

   public InvokerEntityModel(ModelPart root) {
      this.root = root;
      this.head = root.getChild("head");
      this.hat = this.head.getChild("hat");
      this.body = root.getChild("body");
      this.hat.visible = false;
      this.arms = root.getChild("arms");
      this.leftLeg = root.getChild("left_leg");
      this.rightLeg = root.getChild("right_leg");
      this.leftArm = root.getChild("left_arm");
      this.rightArm = root.getChild("right_arm");
   }

   public static LayerDefinition getTexturedModelData(CubeDeformation cubeDeformation) {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition modelPartData = modelData.getRoot();
      PartDefinition modelPartData2 = modelPartData.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, cubeDeformation), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      modelPartData2.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, cubeDeformation.extend(0.45F)), PartPose.ZERO
      );
      modelPartData2.addOrReplaceChild(
         "nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, cubeDeformation), PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(16, 20)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation)
            .texOffs(0, 38)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 23.0F, 6.0F, cubeDeformation.extend(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition modelPartData3 = modelPartData.addOrReplaceChild(
         "arms",
         CubeListBuilder.create()
            .texOffs(44, 22)
            .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation)
            .texOffs(40, 38)
            .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, cubeDeformation),
         PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F)
      );
      modelPartData3.addOrReplaceChild(
         "left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation), PartPose.ZERO
      );
      modelPartData.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
         PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
         PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 128, 128);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      float z = 0.038F;
      this.root.xRot = Mth.sin(entity.tickCount * 0.1F) * (float) (Math.PI / 180.0);
      this.root.zRot = Mth.sin(entity.tickCount * 0.1F) * (float) (Math.PI / 180.0);
      if (entity.getDeltaMovement().x != 0.0 || entity.getDeltaMovement().z != 0.0) {
         this.body.xRot = 0.15F * limbSwingAmount;
         this.rightLeg.xRot = 0.15F * limbSwingAmount;
         this.leftLeg.xRot = 0.15F * limbSwingAmount;
      }

      if (this.riding) {
         this.rightArm.xRot = (float) (-Math.PI / 5);
         this.rightArm.yRot = 0.0F;
         this.rightArm.zRot = 0.0F;
         this.leftArm.xRot = (float) (-Math.PI / 5);
         this.leftArm.yRot = 0.0F;
         this.leftArm.zRot = 0.0F;
         this.rightLeg.xRot = -1.4137167F;
         this.rightLeg.yRot = (float) (Math.PI / 10);
         this.rightLeg.zRot = 0.07853982F;
         this.leftLeg.xRot = -1.4137167F;
         this.leftLeg.yRot = (float) (-Math.PI / 10);
         this.leftLeg.zRot = -0.07853982F;
      } else {
         this.rightArm.yRot = 0.0F;
         this.rightArm.zRot = 0.0F;
         this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
         this.leftArm.yRot = 0.0F;
         this.leftArm.zRot = 0.0F;
      }

      IllagerArmPose state = entity.getArmPose();
      if (state == IllagerArmPose.ATTACKING) {
         if (entity.getMainHandItem().isEmpty()) {
            AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
         } else {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, entity, this.attackTime, ageInTicks);
         }
      } else if (state == IllagerArmPose.SPELLCASTING) {
         this.rightArm.z = 0.0F;
         this.rightArm.x = -5.0F;
         this.leftArm.z = 0.0F;
         this.leftArm.x = 5.0F;
         this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
         this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
         this.rightArm.zRot = (float) (Math.PI * 3.0 / 4.0);
         this.leftArm.zRot = (float) (-Math.PI * 3.0 / 4.0);
         this.rightArm.yRot = 0.0F;
         this.leftArm.yRot = 0.0F;
      } else if (state == IllagerArmPose.BOW_AND_ARROW) {
         this.rightArm.yRot = -0.1F + this.head.yRot;
         this.rightArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
         this.leftArm.xRot = -0.9424779F + this.head.xRot;
         this.leftArm.yRot = this.head.yRot - 0.4F;
         this.leftArm.zRot = (float) (Math.PI / 2);
      } else if (state == IllagerArmPose.CROSSBOW_HOLD) {
         AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
      } else if (state == IllagerArmPose.CROSSBOW_CHARGE) {
         AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, entity, true);
      } else if (state == IllagerArmPose.CELEBRATING) {
         this.rightArm.z = 0.0F;
         this.rightArm.x = -5.0F;
         this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
         this.rightArm.zRot = 2.670354F;
         this.rightArm.yRot = 0.0F;
         this.leftArm.z = 0.0F;
         this.leftArm.x = 5.0F;
         this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
         this.leftArm.zRot = (float) (-Math.PI * 3.0 / 4.0);
         this.leftArm.yRot = 0.0F;
      }

      boolean bl;
      this.arms.visible = bl = state == IllagerArmPose.CROSSED;
      this.leftArm.visible = !bl;
      this.rightArm.visible = !bl;
   }

   private ModelPart getAttackingArm(HumanoidArm arm) {
      return arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
   }

   public ModelPart getHat() {
      return this.hat;
   }

   public ModelPart getHead() {
      return this.head;
   }

   public void translateToHand(HumanoidArm arm, PoseStack matrices) {
      this.getAttackingArm(arm).translateAndRotate(matrices);
   }
}
