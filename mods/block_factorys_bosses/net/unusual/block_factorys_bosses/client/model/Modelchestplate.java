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

public class Modelchestplate<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelchestplate"), "main");
   public final ModelPart body;
   public final ModelPart jetpack;
   public final ModelPart leftArm;
   public final ModelPart rightArm;

   public Modelchestplate(ModelPart root) {
      this.body = root.getChild("body");
      this.jetpack = this.body.getChild("jetpack");
      this.leftArm = root.getChild("leftArm");
      this.rightArm = root.getChild("rightArm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(40, 16)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.76F))
            .texOffs(0, 0)
            .addBox(-5.0F, 0.0F, -4.0F, 10.0F, 7.0F, 2.0F, new CubeDeformation(0.5F))
            .texOffs(24, 0)
            .addBox(-5.0F, 0.0F, 2.0F, 10.0F, 7.0F, 2.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition jetpack = body.addOrReplaceChild("jetpack", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition leftArm = partdefinition.addOrReplaceChild(
         "leftArm",
         CubeListBuilder.create()
            .texOffs(48, 0)
            .addBox(-0.9F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .texOffs(18, 9)
            .addBox(0.35F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .texOffs(0, 9)
            .addBox(1.6F, 4.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      PartDefinition rightArm = partdefinition.addOrReplaceChild(
         "rightArm",
         CubeListBuilder.create()
            .texOffs(48, 0)
            .mirror()
            .addBox(-3.1F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .mirror(false)
            .texOffs(18, 9)
            .mirror()
            .addBox(-5.35F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false)
            .texOffs(0, 9)
            .mirror()
            .addBox(-4.6F, 4.5F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
