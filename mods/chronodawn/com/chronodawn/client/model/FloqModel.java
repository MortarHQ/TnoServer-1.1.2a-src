package com.chronodawn.client.model;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.FloqEntity;
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

public class FloqModel extends EntityModel<FloqEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "floq"), "main");
   private final ModelPart body;
   private final ModelPart right_eye;
   private final ModelPart left_eye;
   private final ModelPart mouth;

   public FloqModel(ModelPart root) {
      this.body = root.getChild("body");
      this.right_eye = root.getChild("right_eye");
      this.left_eye = root.getChild("left_eye");
      this.mouth = root.getChild("mouth");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      PartDefinition right_eye = partdefinition.addOrReplaceChild(
         "right_eye",
         CubeListBuilder.create().texOffs(32, 4).addBox(1.3F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      PartDefinition left_eye = partdefinition.addOrReplaceChild(
         "left_eye",
         CubeListBuilder.create().texOffs(32, 0).addBox(-3.3F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      PartDefinition mouth = partdefinition.addOrReplaceChild(
         "mouth",
         CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   public void setupAnim(FloqEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.attackAnim > 0.0F) {
         float progress = Mth.sin(entity.attackAnim * (float) Math.PI);
         this.mouth.xRot += 0.5F * progress;
         this.body.xRot += 0.2F * progress;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.right_eye.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.left_eye.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.mouth.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
