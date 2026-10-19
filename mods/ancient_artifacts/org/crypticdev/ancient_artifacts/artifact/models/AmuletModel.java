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

public class AmuletModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart Amuelt2;
   private final ModelPart Amulet1;

   public AmuletModel(ModelPart root) {
      this.root = root.getChild("root");
      this.Amuelt2 = this.root.getChild("Amuelt2");
      this.Amulet1 = this.root.getChild("Amulet1");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      PartDefinition Amuelt2 = root.addOrReplaceChild(
         "Amuelt2",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(0, 16)
            .addBox(-3.5F, 0.05F, -2.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      PartDefinition Body_r1 = Amuelt2.addOrReplaceChild(
         "Body_r1",
         CubeListBuilder.create()
            .texOffs(26, 5)
            .addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
            .texOffs(26, 1)
            .addBox(-1.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 4.55F, -2.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body_r2 = Amuelt2.addOrReplaceChild(
         "Body_r2",
         CubeListBuilder.create().texOffs(26, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)),
         PartPose.offsetAndRotation(0.0F, 4.55F, -2.7F, 0.0F, 0.0F, -2.3562F)
      );
      PartDefinition Amulet1 = root.addOrReplaceChild(
         "Amulet1",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(0, 16)
            .addBox(-3.5F, 0.05F, -2.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      PartDefinition Body_r3 = Amulet1.addOrReplaceChild(
         "Body_r3",
         CubeListBuilder.create()
            .texOffs(26, 5)
            .addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
            .texOffs(26, 1)
            .addBox(-1.0F, -1.0F, -0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 4.75F, -2.7F, 0.0F, 0.0F, 0.7854F)
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
