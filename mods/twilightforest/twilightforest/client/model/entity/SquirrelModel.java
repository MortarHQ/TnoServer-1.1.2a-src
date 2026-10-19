package twilightforest.client.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.entity.passive.Squirrel;

public class SquirrelModel extends QuadrupedModel<Squirrel> {
   private final ModelPart tail = this.body.getChild("tail");
   private final ModelPart tailPart1 = this.tail.getChild("tail_1");
   private final ModelPart tailPart2 = this.tailPart1.getChild("tail_2");

   public SquirrelModel(ModelPart root) {
      super(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition checkForPack() {
      return JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? createJappaModel() : create();
   }

   private static LayerDefinition create() {
      MeshDefinition meshdefinition = QuadrupedModel.createBodyMesh(0, CubeDeformation.NONE);
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-2.0F, -5.0F, -3.0F, 4.0F, 4.0F, 4.0F)
            .texOffs(16, 0)
            .addBox(-2.0F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F)
            .texOffs(16, 0)
            .addBox(1.0F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
         PartPose.offset(0.0F, 22.0F, -2.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().mirror().texOffs(0, 8).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 5.0F), PartPose.offset(0.0F, 21.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().mirror().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-2.0F, 23.0F, 2.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().mirror().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.0F, 23.0F, 2.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(-2.0F, 23.0F, -2.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.0F, 23.0F, -2.0F)
      );
      PartDefinition tail = body.addOrReplaceChild(
         "tail",
         CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, -1.5F, 0.5F, 1.0F, 1.0F, 1.0F).texOffs(0, 20).addBox(-1.5F, -4.0F, 1.0F, 3.0F, 3.0F, 3.0F),
         PartPose.offset(0.0F, 0.0F, 2.0F)
      );
      PartDefinition tail1 = tail.addOrReplaceChild(
         "tail_1", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F), PartPose.offset(-1.5F, -4.0F, 2.5F)
      );
      tail1.addOrReplaceChild(
         "tail_2", CubeListBuilder.create().texOffs(0, 26).addBox(1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F), PartPose.offset(-1.5F, -3.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   private static LayerDefinition createJappaModel() {
      MeshDefinition meshdefinition = QuadrupedModel.createBodyMesh(1, CubeDeformation.NONE);
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F)
            .addBox(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F)
            .texOffs(0, 2)
            .addBox(1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F),
         PartPose.offset(0.0F, 20.0F, -3.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 5.0F), PartPose.offset(0.0F, 23.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(-1.5F, 23.0F, -2.5F)
      );
      partdefinition.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(4, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.5F, 23.0F, -2.5F)
      );
      partdefinition.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(-1.5F, 23.0F, 1.5F)
      );
      partdefinition.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().texOffs(4, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(1.5F, 23.0F, 1.5F)
      );
      PartDefinition tail = body.addOrReplaceChild(
         "tail", CubeListBuilder.create().texOffs(18, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F), PartPose.offset(0.0F, -3.0F, 2.0F)
      );
      PartDefinition tail1 = tail.addOrReplaceChild(
         "tail_1", CubeListBuilder.create().texOffs(13, 11).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 5.0F), PartPose.offset(0.0F, 4.0F, 0.5F)
      );
      tail1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.ZERO);
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(Squirrel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      if (limbSwingAmount > 0.2) {
         float wiggle = Math.min(limbSwingAmount, 0.6F);
         this.tail.xRot = (JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? 2.5F : 0.2F)
            + (Mth.cos(ageInTicks * 0.6662F) - (float) (Math.PI / 3)) * wiggle;
         this.tailPart1.xRot = Mth.cos(ageInTicks * 0.7774F) * 1.2F * wiggle;
         this.tailPart2.xRot = Mth.cos(ageInTicks * 0.8886F + (float) (Math.PI / 2)) * 1.4F * wiggle;
      } else {
         this.tail.xRot = (JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? 2.5F : 0.2F) + Mth.cos(ageInTicks * 0.3335F) * 0.15F;
         this.tailPart1.xRot = 0.1F + Mth.cos(ageInTicks * 0.4445F) * 0.2F;
         this.tailPart2.xRot = 0.1F + Mth.cos(ageInTicks * 0.5555F) * 0.25F;
      }
   }
}
