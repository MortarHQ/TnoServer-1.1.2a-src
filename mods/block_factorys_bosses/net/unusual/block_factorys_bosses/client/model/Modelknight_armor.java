package net.unusual.block_factorys_bosses.client.model;

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
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class Modelknight_armor<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelknight_armor"), "main");
   public final ModelPart left_pants;
   public final ModelPart left_boots;
   public final ModelPart right_leg;
   public final ModelPart right_boots;
   public final ModelPart torso;
   public final ModelPart torso_legging;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart head2;

   public Modelknight_armor(ModelPart root) {
      this.left_pants = root.getChild("left_pants");
      this.left_boots = root.getChild("left_boots");
      this.right_leg = root.getChild("right_leg");
      this.right_boots = root.getChild("right_boots");
      this.torso = root.getChild("torso");
      this.torso_legging = root.getChild("torso_legging");
      this.left_arm = root.getChild("left_arm");
      this.right_arm = root.getChild("right_arm");
      this.head2 = root.getChild("head2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition left_pants = partdefinition.addOrReplaceChild(
         "left_pants",
         CubeListBuilder.create()
            .texOffs(24, 55)
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.74F))
            .texOffs(56, 41)
            .addBox(-2.0F, 1.35F, -4.5F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.5F)),
         PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      PartDefinition left_boots = partdefinition.addOrReplaceChild(
         "left_boots",
         CubeListBuilder.create().texOffs(32, 29).addBox(-2.0F, 5.0F, -3.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_leg = partdefinition.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create()
            .texOffs(24, 55)
            .mirror()
            .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.74F))
            .mirror(false)
            .texOffs(56, 41)
            .mirror()
            .addBox(-2.0F, 1.35F, -4.5F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_boots = partdefinition.addOrReplaceChild(
         "right_boots",
         CubeListBuilder.create().texOffs(32, 29).mirror().addBox(-3.0F, 5.0F, -3.0F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false),
         PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      PartDefinition torso = partdefinition.addOrReplaceChild(
         "torso",
         CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-4.0F, -0.65F, -3.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.75F))
            .texOffs(32, 13)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition torso_legging = partdefinition.addOrReplaceChild(
         "torso_legging",
         CubeListBuilder.create().texOffs(81, 11).addBox(-4.0F, -12.25F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)),
         PartPose.offset(0.0F, 12.25F, 0.0F)
      );
      PartDefinition left_arm = partdefinition.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(32, 43)
            .addBox(0.25F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .texOffs(0, 48)
            .addBox(-3.275F, -6.325F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .texOffs(54, 29)
            .addBox(1.4F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .texOffs(40, 55)
            .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .texOffs(56, 13)
            .addBox(1.5F, 4.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = partdefinition.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create()
            .texOffs(32, 43)
            .mirror()
            .addBox(-6.25F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false)
            .texOffs(0, 48)
            .mirror()
            .addBox(-2.725F, -6.325F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false)
            .texOffs(54, 29)
            .mirror()
            .addBox(-7.4F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false)
            .texOffs(40, 55)
            .mirror()
            .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .mirror(false)
            .texOffs(56, 13)
            .mirror()
            .addBox(-4.5F, 4.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition head2 = partdefinition.addOrReplaceChild(
         "head2",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
            .texOffs(0, 16)
            .addBox(-0.25F, -18.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
            .texOffs(0, 32)
            .addBox(-7.75F, -11.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)),
         PartPose.offset(0.0F, 0.5F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.left_pants.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.left_boots.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.right_boots.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.torso_legging.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.head2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
