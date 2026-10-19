package com.github.b4ndithelps.tennogamenolife.client.model;

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
import net.minecraft.world.entity.LivingEntity;

public class XiubiHaloModel extends EntityModel<LivingEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "xiubi_halo"), "main"
   );
   private final ModelPart bone4;
   private final ModelPart bone3;
   private final ModelPart bone;
   private final ModelPart bone2;

   public XiubiHaloModel(ModelPart root) {
      this.bone4 = root.getChild("bone4");
      this.bone3 = this.bone4.getChild("bone3");
      this.bone = this.bone4.getChild("bone");
      this.bone2 = this.bone4.getChild("bone2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone4 = partdefinition.addOrReplaceChild(
         "bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.7F, 19.8F, 1.0F, 0.0F, (float) Math.PI, 0.0F)
      );
      bone4.addOrReplaceChild(
         "bone3",
         CubeListBuilder.create().texOffs(0, 17).addBox(0.5307F, -2.6955F, -3.25F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(9.5F, -28.7F, 1.55F, 0.0F, 0.0F, -0.3927F)
      );
      bone4.addOrReplaceChild(
         "bone",
         CubeListBuilder.create().texOffs(0, 0).addBox(0.2437F, -4.0026F, -4.25F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.0F, -27.75F, 1.25F, 0.0F, 0.0F, -0.3927F)
      );
      bone4.addOrReplaceChild(
         "bone2",
         CubeListBuilder.create().texOffs(12, 17).addBox(0.2833F, -2.445F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.75F, -27.75F, 1.2F, 0.0F, 0.0F, -0.3927F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float time = ageInTicks * 0.02F;
      this.bone.xRot = time % 5.0F * 2.0F * (float) Math.PI / 5.0F;
      this.bone2.xRot = time % 3.75F * 2.4F * (float) Math.PI / 3.75F;
      float bone3Time = time % 5.0F;
      if (bone3Time < 2.0F) {
         this.bone3.xRot = (float) (-Math.PI / 2) * (bone3Time / 2.0F);
      } else if (bone3Time < 3.5F) {
         this.bone3.xRot = (float) (-Math.PI / 2) * (1.0F - (bone3Time - 2.0F) / 1.5F);
      } else {
         this.bone3.xRot = (float) (Math.PI * 2) * (bone3Time - 3.5F) / 1.5F;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
