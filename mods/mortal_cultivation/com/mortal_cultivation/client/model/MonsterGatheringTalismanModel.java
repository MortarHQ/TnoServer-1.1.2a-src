package com.mortal_cultivation.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.entity.MonsterGatheringTalismanEntity;
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

public class MonsterGatheringTalismanModel extends EntityModel<MonsterGatheringTalismanEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "monster_gathering_talisman"), "main"
   );
   private final ModelPart bone;

   public MonsterGatheringTalismanModel(ModelPart root) {
      this.bone = root.getChild("bone");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone = partdefinition.addOrReplaceChild(
         "bone",
         CubeListBuilder.create().texOffs(1, 2).addBox(-4.25F, -13.0F, -0.25F, 8.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 12.8F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 16, 16);
   }

   public void setupAnim(MonsterGatheringTalismanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.bone.yRot = ageInTicks * 0.1F;
      this.bone.y = 12.8F + (float)Math.sin(ageInTicks * 0.1F) * 1.0F;
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      poseStack.pushPose();
      this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      poseStack.popPose();
   }
}
