package com.mongoose.royalvariations.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class GloveModel extends HumanoidModel<LivingEntity> {
   public GloveModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 1.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
      PartDefinition LeftArmLayer_r1 = LeftArm.addOrReplaceChild(
         "LeftArmLayer_r1",
         CubeListBuilder.create().texOffs(101, 47).mirror().addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false),
         PartPose.offsetAndRotation(3.1F, 5.6929F, 0.0F, -1.5708F, -0.7854F, 1.5708F)
      );
      PartDefinition LeftArmLayer_r2 = LeftArm.addOrReplaceChild(
         "LeftArmLayer_r2",
         CubeListBuilder.create()
            .texOffs(70, 17)
            .mirror()
            .addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
            .mirror(false)
            .texOffs(89, 23)
            .mirror()
            .addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
            .mirror(false)
            .texOffs(89, 0)
            .mirror()
            .addBox(-2.0F, -5.8F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.leftArm.visible = !entityIn.isInvisible();
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
