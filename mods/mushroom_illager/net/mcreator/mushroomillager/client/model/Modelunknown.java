package net.mcreator.mushroomillager.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelunknown<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("mushroom_illager", "modelunknown"), "main"
   );
   public final ModelPart head;
   public final ModelPart hat;
   public final ModelPart nose;
   public final ModelPart body;
   public final ModelPart arms;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart Stick;
   public final ModelPart Stick2;
   public final ModelPart left_leg;
   public final ModelPart right_leg;

   public Modelunknown(ModelPart root) {
      this.head = root.getChild("head");
      this.hat = this.head.getChild("hat");
      this.nose = this.head.getChild("nose");
      this.body = root.getChild("body");
      this.arms = root.getChild("arms");
      this.left_arm = root.getChild("left_arm");
      this.right_arm = root.getChild("right_arm");
      this.Stick = this.right_arm.getChild("Stick");
      this.Stick2 = this.Stick.getChild("Stick2");
      this.left_leg = root.getChild("left_leg");
      this.right_leg = root.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition hat_r1 = hat.addOrReplaceChild(
         "hat_r1",
         CubeListBuilder.create().texOffs(50, 13).addBox(-3.0F, -1.5F, 0.0F, 6.0F, 3.5F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.75F, -34.5F, -3.0F, 0.0F, 0.8727F, 0.0F)
      );
      PartDefinition hat_r2 = hat.addOrReplaceChild(
         "hat_r2",
         CubeListBuilder.create().texOffs(50, 7).addBox(0.0F, -1.0F, -3.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.75F, -35.0F, -3.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition hat_r3 = hat.addOrReplaceChild(
         "hat_r3",
         CubeListBuilder.create().texOffs(50, 16).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, -36.0F, 1.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition hat_r4 = hat.addOrReplaceChild(
         "hat_r4",
         CubeListBuilder.create().texOffs(50, 16).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, -36.0F, 1.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition nose = head.addOrReplaceChild(
         "nose",
         CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(16, 20)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 38)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.25F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 0.3F));
      PartDefinition left_arm = partdefinition.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = partdefinition.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition Stick = right_arm.addOrReplaceChild("Stick", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition Stick_r1 = Stick.addOrReplaceChild(
         "Stick_r1",
         CubeListBuilder.create().texOffs(33, 48).addBox(0.0F, -1.0F, -1.1F, 1.0F, 1.1F, 1.1F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -7.35F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r2 = Stick.addOrReplaceChild(
         "Stick_r2",
         CubeListBuilder.create().texOffs(33, 50).addBox(0.0F, -1.0F, -1.1F, 1.0F, 1.1F, 1.1F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 7.35F, -7.35F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r3 = Stick.addOrReplaceChild(
         "Stick_r3",
         CubeListBuilder.create().texOffs(31, 52).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -7.45F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r4 = Stick.addOrReplaceChild(
         "Stick_r4",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -5.95F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r5 = Stick.addOrReplaceChild(
         "Stick_r5",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -4.55F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r6 = Stick.addOrReplaceChild(
         "Stick_r6",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -3.15F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r7 = Stick.addOrReplaceChild(
         "Stick_r7",
         CubeListBuilder.create().texOffs(31, 60).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -1.75F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick_r8 = Stick.addOrReplaceChild(
         "Stick_r8",
         CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -6.0F, -8.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 6.0F, -0.25F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2 = Stick.addOrReplaceChild("Stick2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));
      PartDefinition Stick2_r1 = Stick2.addOrReplaceChild(
         "Stick2_r1",
         CubeListBuilder.create().texOffs(33, 48).addBox(0.0F, -1.0F, -1.1F, 1.0F, 1.1F, 1.1F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -7.35F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r2 = Stick2.addOrReplaceChild(
         "Stick2_r2",
         CubeListBuilder.create().texOffs(33, 50).addBox(0.0F, -1.0F, -1.1F, 1.0F, 1.1F, 1.1F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 7.35F, -7.35F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r3 = Stick2.addOrReplaceChild(
         "Stick2_r3",
         CubeListBuilder.create().texOffs(31, 52).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -7.45F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r4 = Stick2.addOrReplaceChild(
         "Stick2_r4",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -5.95F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r5 = Stick2.addOrReplaceChild(
         "Stick2_r5",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -4.55F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r6 = Stick2.addOrReplaceChild(
         "Stick2_r6",
         CubeListBuilder.create().texOffs(31, 56).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -3.15F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r7 = Stick2.addOrReplaceChild(
         "Stick2_r7",
         CubeListBuilder.create().texOffs(31, 60).addBox(0.0F, -1.9F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 8.75F, -1.75F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Stick2_r8 = Stick2.addOrReplaceChild(
         "Stick2_r8",
         CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -6.0F, -8.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 6.0F, -0.25F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = partdefinition.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_leg = partdefinition.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.arms.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.yRot = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.xRot = headPitch / (180.0F / (float)Math.PI);
      this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
      this.left_leg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
      this.right_leg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
