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

public class Modelicespike_pr<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelicespike_pr"), "main");
   public final ModelPart icespike;

   public Modelicespike_pr(ModelPart root) {
      this.icespike = root.getChild("icespike");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition icespike = partdefinition.addOrReplaceChild(
         "icespike",
         CubeListBuilder.create()
            .texOffs(168, 98)
            .addBox(-6.0F, -6.0F, -12.0F, 13.0F, 14.0F, 23.0F, new CubeDeformation(-0.05F))
            .texOffs(228, 216)
            .addBox(-6.0F, -6.0F, -21.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(222, 0)
            .addBox(0.0F, -2.0F, -30.0F, 7.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
            .texOffs(220, 135)
            .addBox(-6.0F, -6.0F, 11.0F, 10.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(68, 218)
            .addBox(-9.0F, -9.0F, -7.0F, 11.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.25F, 6.0F, 0.5F, 1.5708F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 512, 512);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.icespike.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
