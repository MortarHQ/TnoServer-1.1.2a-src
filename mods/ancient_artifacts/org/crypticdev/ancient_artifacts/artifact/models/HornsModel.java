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

public class HornsModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart waist;
   private final ModelPart body;
   private final ModelPart head;

   public HornsModel(ModelPart root) {
      this.root = root.getChild("root");
      this.waist = this.root.getChild("waist");
      this.body = this.waist.getChild("body");
      this.head = this.body.getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition waist = root.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
      PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
      PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition cube_r1 = head.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create()
            .texOffs(0, 24)
            .mirror()
            .addBox(-3.0F, -1.0F, -1.0F, 3.5F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(0, 17)
            .mirror()
            .addBox(-3.0F, 1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, -0.1745F, 0.0F, 0.2182F)
      );
      PartDefinition cube_r2 = head.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create()
            .texOffs(0, 17)
            .addBox(1.0F, 1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 24)
            .addBox(-0.5F, -1.0F, -1.0F, 3.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, -0.1745F, 0.0F, -0.2182F)
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
