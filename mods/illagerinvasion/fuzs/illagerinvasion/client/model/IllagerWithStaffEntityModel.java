package fuzs.illagerinvasion.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.world.entity.monster.Inquisitor;
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
import net.minecraft.world.item.Items;

public class IllagerWithStaffEntityModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart hat;
   private final ModelPart body;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;
   private final ModelPart rightArm;
   private final ModelPart leftArm;

   public IllagerWithStaffEntityModel(ModelPart root) {
      this.root = root;
      this.head = root.getChild("head");
      this.hat = this.head.getChild("hat");
      this.body = root.getChild("body");
      this.hat.visible = false;
      this.leftLeg = root.getChild("left_leg");
      this.rightLeg = root.getChild("right_leg");
      this.leftArm = root.getChild("left_arm");
      this.rightArm = root.getChild("right_arm");
   }

   public static LayerDefinition getTexturedModelData() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition modelPartData = modelData.getRoot();
      PartDefinition modelPartData2 = modelPartData.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.3F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition modelPartData3 = modelPartData.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      modelPartData2.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.3F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO
      );
      modelPartData2.addOrReplaceChild(
         "nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(16, 20)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
            .texOffs(0, 38)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -1.35F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -1.35F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      modelPartData.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      modelPartData3.addOrReplaceChild(
         "staff",
         CubeListBuilder.create().texOffs(112, 0).addBox(0.0F, -4.9F, -9.5F, 1.0F, 28.0F, 1.0F),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(modelData, 128, 64);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.body.xRot = 0.2F;
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
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
         this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
         this.rightArm.yRot = 0.0F;
         this.rightArm.zRot = 0.0F;
         this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount * 0.5F - 1.2F;
         this.leftArm.yRot = 0.0F;
         this.leftArm.zRot = 0.0F;
         this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
         this.rightLeg.yRot = 0.0F;
         this.rightLeg.zRot = 0.0F;
         this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
         this.leftLeg.yRot = 0.0F;
         this.leftLeg.zRot = 0.0F;
      }

      IllagerArmPose state = entity.getArmPose();
      HumanoidArm mainArm = entity.getMainArm();
      boolean hasShield = entity.getOffhandItem().is(Items.SHIELD);
      if (state == IllagerArmPose.ATTACKING) {
         if (entity.getMainHandItem().isEmpty()) {
            AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
         } else {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, entity, this.attackTime, ageInTicks);
         }

         if (hasShield && mainArm == HumanoidArm.RIGHT) {
            this.leftArm.xRot = -1.05F;
            this.leftArm.yRot = (float) (Math.PI / 6);
         }

         if (hasShield && mainArm == HumanoidArm.LEFT) {
            this.rightArm.xRot = 200.0F;
            this.rightArm.yRot = (float) (-Math.PI / 6);
         }

         if (((Inquisitor)entity).getStunnedState()) {
            this.head.xRot = 18.5F;
            this.head.yRot = Mth.cos(ageInTicks * 0.8F) * 0.3F;
            this.rightArm.xRot = 200.0F;
            this.rightArm.yRot = (float) (Math.PI / 6);
            this.leftArm.xRot = 200.0F;
            this.leftArm.yRot = (float) (-Math.PI / 6);
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
