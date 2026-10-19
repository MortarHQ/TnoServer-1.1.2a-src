package twilightforest.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class TFArmorModel extends HumanoidModel<LivingEntity> {
   public TFArmorModel(ModelPart root) {
      super(root);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity instanceof ArmorStand stand) {
         this.head.xRot = (float) (Math.PI / 180.0) * stand.getHeadPose().getX();
         this.head.yRot = (float) (Math.PI / 180.0) * stand.getHeadPose().getY();
         this.head.zRot = (float) (Math.PI / 180.0) * stand.getHeadPose().getZ();
         this.body.xRot = (float) (Math.PI / 180.0) * stand.getBodyPose().getX();
         this.body.yRot = (float) (Math.PI / 180.0) * stand.getBodyPose().getY();
         this.body.zRot = (float) (Math.PI / 180.0) * stand.getBodyPose().getZ();
         this.leftArm.xRot = (float) (Math.PI / 180.0) * stand.getLeftArmPose().getX();
         this.leftArm.yRot = (float) (Math.PI / 180.0) * stand.getLeftArmPose().getY();
         this.leftArm.zRot = (float) (Math.PI / 180.0) * stand.getLeftArmPose().getZ();
         this.rightArm.xRot = (float) (Math.PI / 180.0) * stand.getRightArmPose().getX();
         this.rightArm.yRot = (float) (Math.PI / 180.0) * stand.getRightArmPose().getY();
         this.rightArm.zRot = (float) (Math.PI / 180.0) * stand.getRightArmPose().getZ();
         this.leftLeg.xRot = (float) (Math.PI / 180.0) * stand.getLeftLegPose().getX();
         this.leftLeg.yRot = (float) (Math.PI / 180.0) * stand.getLeftLegPose().getY();
         this.leftLeg.zRot = (float) (Math.PI / 180.0) * stand.getLeftLegPose().getZ();
         this.rightLeg.xRot = (float) (Math.PI / 180.0) * stand.getRightLegPose().getX();
         this.rightLeg.yRot = (float) (Math.PI / 180.0) * stand.getRightLegPose().getY();
         this.rightLeg.zRot = (float) (Math.PI / 180.0) * stand.getRightLegPose().getZ();
         this.hat.copyFrom(this.head);
      } else {
         super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      }
   }
}
