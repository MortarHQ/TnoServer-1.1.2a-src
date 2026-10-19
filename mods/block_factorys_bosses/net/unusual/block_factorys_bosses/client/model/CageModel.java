package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CageModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelcage"), "main");
   public final ModelPart bone2;

   public CageModel(ModelPart root) {
      this.bone2 = root.getChild("bone2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone2 = partdefinition.addOrReplaceChild(
         "bone2",
         CubeListBuilder.create()
            .texOffs(80, 37)
            .addBox(-2.0F, -15.0F, -5.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(80, 42)
            .mirror()
            .addBox(-2.0F, -15.0F, 24.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(80, 47)
            .addBox(-19.0F, -15.0F, 12.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(80, 47)
            .mirror()
            .addBox(10.0F, -15.0F, 12.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(0, 62)
            .addBox(-14.0F, 32.0F, 0.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-12.0F, -10.0F, 2.0F, 20.0F, 42.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(80, 0)
            .addBox(-14.0F, -15.0F, 0.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.0F, -13.0F, -12.0F)
      );
      bone2.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(80, 29).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, -19.0F, 12.0F, 0.0F, -0.7854F, 0.0F)
      );
      bone2.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(80, 29).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, -19.0F, 12.0F, 0.0F, 0.7854F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 256, 256);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
