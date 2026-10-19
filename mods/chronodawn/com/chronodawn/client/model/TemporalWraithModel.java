package com.chronodawn.client.model;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.TemporalWraithEntity;
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
import net.minecraft.util.Mth;

public class TemporalWraithModel extends EntityModel<TemporalWraithEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "temporal_wraith"), "main");
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart left_arm;
   private final ModelPart right_arm;
   private final ModelPart head;
   private final ModelPart headwear;
   private final ModelPart legs;

   public TemporalWraithModel(ModelPart root) {
      this.root = root.getChild("root");
      this.body = this.root.getChild("body");
      this.left_arm = this.body.getChild("left_arm");
      this.right_arm = this.body.getChild("right_arm");
      this.head = this.body.getChild("head");
      this.headwear = this.head.getChild("headwear");
      this.legs = this.root.getChild("legs");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(18, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -24.0F, 0.0F)
      );
      PartDefinition left_arm = body.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(42, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = body.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(42, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition headwear = head.addOrReplaceChild(
         "headwear",
         CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition legs = root.addOrReplaceChild(
         "legs",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-3.0F, -12.0F, -1.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(0, 24)
            .addBox(-2.0F, -7.0F, 0.0F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(TemporalWraithEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.right_arm.xRot = -1.5F;
      this.left_arm.xRot = -1.5F;
      if (entity.attackAnim > 0.0F) {
         float armRaise = Mth.sin(entity.attackAnim * (float) Math.PI);
         this.left_arm.xRot -= 1.2F * armRaise;
         this.right_arm.xRot -= 1.2F * armRaise;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
