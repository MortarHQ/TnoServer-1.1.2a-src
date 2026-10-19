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

public class ChestEmblemModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart ChestEmblem;
   private final ModelPart Body;
   private final ModelPart ChestEmblem2;
   private final ModelPart Body2;
   private final ModelPart ChestEmblem3;
   private final ModelPart Body3;

   public ChestEmblemModel(ModelPart root) {
      this.root = root.getChild("root");
      this.ChestEmblem = this.root.getChild("ChestEmblem");
      this.Body = this.ChestEmblem.getChild("Body");
      this.ChestEmblem2 = this.root.getChild("ChestEmblem2");
      this.Body2 = this.ChestEmblem2.getChild("Body2");
      this.ChestEmblem3 = this.root.getChild("ChestEmblem3");
      this.Body3 = this.ChestEmblem3.getChild("Body3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      PartDefinition ChestEmblem = root.addOrReplaceChild("ChestEmblem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.4F));
      PartDefinition Body = ChestEmblem.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
      PartDefinition Body_r1 = Body.addOrReplaceChild(
         "Body_r1",
         CubeListBuilder.create().texOffs(10, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(0.0F, 5.5F, -2.6F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r2 = Body.addOrReplaceChild(
         "Body_r2",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 5.0F, -2.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body_r3 = Body.addOrReplaceChild(
         "Body_r3",
         CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(0.0F, 6.0F, -2.6F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition ChestEmblem2 = root.addOrReplaceChild("ChestEmblem2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.4F));
      PartDefinition Body2 = ChestEmblem2.addOrReplaceChild(
         "Body2",
         CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, 0.5F, -2.75F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      PartDefinition Body_r4 = Body2.addOrReplaceChild(
         "Body_r4",
         CubeListBuilder.create().texOffs(10, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(0.0F, 5.5F, -2.6F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r5 = Body2.addOrReplaceChild(
         "Body_r5",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 5.0F, -2.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body_r6 = Body2.addOrReplaceChild(
         "Body_r6",
         CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(0.0F, 6.0F, -2.6F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition ChestEmblem3 = root.addOrReplaceChild("ChestEmblem3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.4F));
      PartDefinition Body3 = ChestEmblem3.addOrReplaceChild("Body3", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
      PartDefinition Body_r7 = Body3.addOrReplaceChild(
         "Body_r7",
         CubeListBuilder.create().texOffs(10, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(0.0F, 5.5F, -2.6F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r8 = Body3.addOrReplaceChild(
         "Body_r8",
         CubeListBuilder.create()
            .texOffs(0, 23)
            .addBox(-4.5F, -4.5F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-3.0F, -3.0F, -0.075F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 5.0F, -2.65F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body_r9 = Body3.addOrReplaceChild(
         "Body_r9",
         CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(0.0F, 6.0F, -2.6F, -0.0436F, 0.0F, 0.0F)
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
