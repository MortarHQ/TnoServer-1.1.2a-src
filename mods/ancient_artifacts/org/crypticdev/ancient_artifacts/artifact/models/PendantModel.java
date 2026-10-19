package org.crypticdev.ancient_artifacts.artifact.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class PendantModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart Pendant1;
   private final ModelPart Pendant2;

   public PendantModel(ModelPart root) {
      this.root = root.getChild("root");
      this.Pendant1 = this.root.getChild("Pendant1");
      this.Pendant2 = this.root.getChild("Pendant2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      PartDefinition Pendant1 = root.addOrReplaceChild(
         "Pendant1",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(27, 6)
            .addBox(-0.5F, 5.05F, -2.625F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 16)
            .addBox(-4.0F, -0.2F, -2.1F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      PartDefinition Body_r1 = Pendant1.addOrReplaceChild(
         "Body_r1",
         CubeListBuilder.create().texOffs(26, 5).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 4.55F, -2.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Pendant2 = root.addOrReplaceChild(
         "Pendant2",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(0, 16)
            .addBox(-4.0F, -0.2F, -2.1F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.45F))
            .texOffs(27, 9)
            .addBox(-0.5F, 5.05F, -2.625F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      PartDefinition Body_r2 = Pendant2.addOrReplaceChild(
         "Body_r2",
         CubeListBuilder.create().texOffs(26, 5).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 4.55F, -2.7F, 0.0F, 0.0F, 0.7854F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
      this.root.render(poseStack, vertexConsumer, i, j);
   }

   public ModelPart root() {
      return this.root;
   }
}
