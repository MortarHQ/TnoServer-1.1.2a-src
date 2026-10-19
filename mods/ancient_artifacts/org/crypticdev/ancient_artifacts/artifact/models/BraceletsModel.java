package org.crypticdev.ancient_artifacts.artifact.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class BraceletsModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart Bracelet1;
   private final ModelPart RightArm;
   private final ModelPart LeftArm;
   private final ModelPart Bracelets2;
   private final ModelPart RightArm2;
   private final ModelPart LeftArm2;
   private final ModelPart Bracelets3;
   private final ModelPart RightArm3;
   private final ModelPart LeftArm3;

   public BraceletsModel(ModelPart root) {
      this.root = root.getChild("root");
      this.Bracelet1 = this.root.getChild("Bracelet1");
      this.RightArm = this.Bracelet1.getChild("RightArm");
      this.LeftArm = this.Bracelet1.getChild("LeftArm");
      this.Bracelets2 = this.root.getChild("Bracelets2");
      this.RightArm2 = this.Bracelets2.getChild("RightArm2");
      this.LeftArm2 = this.Bracelets2.getChild("LeftArm2");
      this.Bracelets3 = this.root.getChild("Bracelets3");
      this.RightArm3 = this.Bracelets3.getChild("RightArm3");
      this.LeftArm3 = this.Bracelets3.getChild("LeftArm3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition Bracelet1 = root.addOrReplaceChild("Bracelet1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition RightArm = Bracelet1.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offset(-5.0F, -22.0F, 0.0F));
      PartDefinition Right_Arm_r1 = RightArm.addOrReplaceChild(
         "Right_Arm_r1",
         CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F)),
         PartPose.offsetAndRotation(-0.9F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition LeftArm = Bracelet1.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offset(5.0F, -22.0F, 0.0F));
      PartDefinition Left_Arm_r1 = LeftArm.addOrReplaceChild(
         "Left_Arm_r1",
         CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F)),
         PartPose.offsetAndRotation(0.9F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition Bracelets2 = root.addOrReplaceChild("Bracelets2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition RightArm2 = Bracelets2.addOrReplaceChild("RightArm2", CubeListBuilder.create(), PartPose.offset(-5.0F, -22.0F, 0.0F));
      PartDefinition Right_Arm_r2 = RightArm2.addOrReplaceChild(
         "Right_Arm_r2",
         CubeListBuilder.create().texOffs(18, 10).mirror().addBox(-5.0F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-1.4F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition Right_Arm_r3 = RightArm2.addOrReplaceChild(
         "Right_Arm_r3",
         CubeListBuilder.create().texOffs(16, 23).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.46F)),
         PartPose.offsetAndRotation(-0.9F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition Right_Arm_r4 = RightArm2.addOrReplaceChild(
         "Right_Arm_r4",
         CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offsetAndRotation(-0.9F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition LeftArm2 = Bracelets2.addOrReplaceChild("LeftArm2", CubeListBuilder.create(), PartPose.offset(5.0F, -22.0F, 0.0F));
      PartDefinition Left_Arm_r2 = LeftArm2.addOrReplaceChild(
         "Left_Arm_r2",
         CubeListBuilder.create().texOffs(18, 10).addBox(2.0F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.4F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition Left_Arm_r3 = LeftArm2.addOrReplaceChild(
         "Left_Arm_r3",
         CubeListBuilder.create().texOffs(16, 23).mirror().addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.46F)).mirror(false),
         PartPose.offsetAndRotation(0.9F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition Left_Arm_r4 = LeftArm2.addOrReplaceChild(
         "Left_Arm_r4",
         CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offsetAndRotation(0.9F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition Bracelets3 = root.addOrReplaceChild("Bracelets3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition RightArm3 = Bracelets3.addOrReplaceChild("RightArm3", CubeListBuilder.create(), PartPose.offset(-5.0F, -22.0F, 0.0F));
      PartDefinition Right_Arm_r5 = RightArm3.addOrReplaceChild(
         "Right_Arm_r5",
         CubeListBuilder.create().texOffs(18, 9).mirror().addBox(-6.0F, -3.5F, 0.0F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-1.4F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition Right_Arm_r6 = RightArm3.addOrReplaceChild(
         "Right_Arm_r6",
         CubeListBuilder.create().texOffs(16, 23).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.46F)),
         PartPose.offsetAndRotation(-0.8F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition Right_Arm_r7 = RightArm3.addOrReplaceChild(
         "Right_Arm_r7",
         CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offsetAndRotation(-0.9F, 7.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition LeftArm3 = Bracelets3.addOrReplaceChild("LeftArm3", CubeListBuilder.create(), PartPose.offset(5.0F, -22.0F, 0.0F));
      PartDefinition Left_Arm_r5 = LeftArm3.addOrReplaceChild(
         "Left_Arm_r5",
         CubeListBuilder.create().texOffs(18, 9).addBox(2.0F, -3.5F, 0.0F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.4F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition Left_Arm_r6 = LeftArm3.addOrReplaceChild(
         "Left_Arm_r6",
         CubeListBuilder.create()
            .texOffs(16, 23)
            .addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.46F))
            .texOffs(0, 7)
            .addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offsetAndRotation(0.9F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void renderLeftToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
      this.LeftArm.render(poseStack, vertexConsumer, i, j);
      this.LeftArm2.render(poseStack, vertexConsumer, i, j);
      this.LeftArm3.render(poseStack, vertexConsumer, i, j);
   }

   public void renderRightToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
      this.RightArm.render(poseStack, vertexConsumer, i, j);
      this.RightArm2.render(poseStack, vertexConsumer, i, j);
      this.RightArm3.render(poseStack, vertexConsumer, i, j);
   }

   public ModelPart root() {
      return this.root;
   }
}
