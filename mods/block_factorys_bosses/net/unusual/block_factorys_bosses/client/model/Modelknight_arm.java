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

public class Modelknight_arm<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelknight_arm"), "main");
   public final ModelPart r_arm;
   public final ModelPart l_arm;

   public Modelknight_arm(ModelPart root) {
      this.r_arm = root.getChild("r_arm");
      this.l_arm = root.getChild("l_arm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition r_arm = partdefinition.addOrReplaceChild(
         "r_arm",
         CubeListBuilder.create()
            .texOffs(0, 11)
            .mirror()
            .addBox(-5.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(16, 0)
            .mirror()
            .addBox(-4.0F, 4.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(0, 0)
            .mirror()
            .addBox(-2.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(0, 23)
            .mirror()
            .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition l_arm = partdefinition.addOrReplaceChild(
         "l_arm",
         CubeListBuilder.create()
            .texOffs(0, 11)
            .addBox(-1.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(16, 0)
            .addBox(1.0F, 4.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(0.0F, -6.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 23)
            .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.r_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.l_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
