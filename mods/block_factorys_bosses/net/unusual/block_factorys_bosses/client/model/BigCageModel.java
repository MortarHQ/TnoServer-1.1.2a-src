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
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigCageModel<T extends CageEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelunderworldcage"), "main");
   public final ModelPart main;

   public BigCageModel(ModelPart root) {
      this.main = root.getChild("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition root = meshdefinition.getRoot();
      PartDefinition main = root.addOrReplaceChild(
         "main",
         CubeListBuilder.create()
            .texOffs(0, 126)
            .addBox(-22.0F, -7.0F, -22.0F, 44.0F, 7.0F, 44.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-19.0F, -95.0F, -19.0F, 38.0F, 88.0F, 38.0F, new CubeDeformation(0.0F))
            .texOffs(152, 0)
            .addBox(-22.0F, -102.0F, -22.0F, 44.0F, 7.0F, 44.0F, new CubeDeformation(0.0F))
            .texOffs(152, 78)
            .addBox(22.0F, -102.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(152, 78)
            .mirror()
            .addBox(-29.0F, -102.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(152, 51)
            .addBox(-7.0F, -113.0F, -2.0F, 14.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(152, 66)
            .addBox(-4.0F, -110.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      main.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create()
            .texOffs(152, 78)
            .mirror()
            .addBox(-29.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(152, 78)
            .mirror()
            .addBox(-29.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(152, 78)
            .addBox(22.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -98.5F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 512, 512);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.main.yRot = entity.getFlip() ? (float) (Math.PI / 2) : 0.0F;
   }
}
