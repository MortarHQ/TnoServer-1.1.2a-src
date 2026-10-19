package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.entity.monster.Kobold;

public class KoboldModel extends HumanoidModel<Kobold> {
   private final ModelPart jaw = this.getHead().getChild("mouth");
   private boolean isJumping;

   public KoboldModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition checkForPack() {
      return JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? createJappaModel() : create();
   }

   private static LayerDefinition create() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -7.0F, -3.0F, 7.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 13.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      head.addOrReplaceChild(
         "right_ear",
         CubeListBuilder.create().texOffs(48, 20).addBox(0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
         PartPose.offsetAndRotation(3.5F, -3.0F, -1.0F, 0.0F, (float) (Math.PI / 12), -0.3490659F)
      );
      head.addOrReplaceChild(
         "left_ear",
         CubeListBuilder.create().texOffs(48, 25).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
         PartPose.offsetAndRotation(-3.5F, -3.0F, -1.0F, 0.0F, (float) (-Math.PI / 12), 0.3490659F)
      );
      head.addOrReplaceChild(
         "snout", CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 2.0F, 3.0F), PartPose.offset(0.0F, -2.0F, -3.0F)
      );
      head.addOrReplaceChild(
         "mouth",
         CubeListBuilder.create().texOffs(28, 5).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 1.0F, 3.0F),
         PartPose.offsetAndRotation(0.0F, -2.0F, -3.0F, 0.20944F, 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(12, 19).addBox(0.0F, 0.0F, 0.0F, 7.0F, 7.0F, 4.0F), PartPose.offset(-3.5F, 12.0F, -2.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(36, 17).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(-3.5F, 12.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().mirror().texOffs(36, 17).addBox(0.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(3.5F, 12.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.0F, 19.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().mirror().texOffs(0, 20).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(2.0F, 19.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   private static LayerDefinition createJappaModel() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F).texOffs(20, 0).addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
         PartPose.offset(0.0F, 12.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      head.addOrReplaceChild(
         "mouth",
         CubeListBuilder.create().texOffs(26, 5).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
         PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0.21816616F, 0.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "right_ear",
         CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
         PartPose.offsetAndRotation(-3.0F, -4.0F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI * 5.0 / 12.0))
      );
      head.addOrReplaceChild(
         "left_ear",
         CubeListBuilder.create().texOffs(42, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
         PartPose.offsetAndRotation(3.0F, -4.0F, 0.0F, 0.0F, 0.0F, (float) (Math.PI * 5.0 / 12.0))
      );
      partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(12, 12).addBox(-3.5F, 0.0F, -2.0F, 7.0F, 7.0F, 4.0F), PartPose.offset(0.0F, 12.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(34, 12).addBox(-2.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(-4.5F, 13.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(34, 22).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(4.5F, 13.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(-1.9F, 19.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(1.9F, 19.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   public void setupAnim(Kobold entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.rightArm.zRot = 0.0F;
      this.leftArm.zRot = 0.0F;
      this.rightArm.xRot = -0.47123894F;
      this.leftArm.xRot = -0.47123894F;
      this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.rightLeg.yRot = 0.0F;
      this.leftLeg.yRot = 0.0F;
      this.rightArm.zRot = this.rightArm.zRot + (Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F);
      this.leftArm.zRot = this.leftArm.zRot - (Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F);
      this.rightArm.xRot = this.rightArm.xRot + Mth.sin(ageInTicks * 0.267F) * 0.25F;
      this.leftArm.xRot = this.leftArm.xRot - Mth.sin(ageInTicks * 0.267F) * 0.25F;
      if (this.isJumping) {
         this.jaw.xRot = 1.44F;
      } else {
         this.jaw.xRot = 0.20944F;
      }
   }

   public void translateToHand(HumanoidArm arm, PoseStack stack) {
      super.translateToHand(arm, stack);
      stack.translate(0.0F, -0.075F, 0.0F);
      stack.scale(0.75F, 0.75F, 0.75F);
   }

   public void prepareMobModel(Kobold entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      this.isJumping = !entity.isNoAi() && entity.getDeltaMovement().y() > 0.0;
   }
}
