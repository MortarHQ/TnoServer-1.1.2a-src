package com.github.wal_bos.moreskills.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TheCoordinateModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "thecoordinate"), "main"
   );
   private final ModelPart bone5;
   private final ModelPart bone6;
   private final ModelPart bone;
   private final ModelPart bone2;
   private final ModelPart bone3;
   private final ModelPart bone4;
   private final ModelPart bb_main;

   public TheCoordinateModel(ModelPart root) {
      this.bone5 = root.getChild("bone5");
      this.bone6 = root.getChild("bone6");
      this.bone = root.getChild("bone");
      this.bone2 = root.getChild("bone2");
      this.bone3 = root.getChild("bone3");
      this.bone4 = root.getChild("bone4");
      this.bb_main = root.getChild("bb_main");
   }

   public static LayerDefinition createBodyLayer() {
      return TheCoordinateModelGeometry.create();
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int packedColor) {
      this.bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
      this.bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
   }
}
