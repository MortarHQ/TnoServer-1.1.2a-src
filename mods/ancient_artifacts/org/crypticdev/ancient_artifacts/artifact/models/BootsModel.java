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

public class BootsModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart Boots;
   private final ModelPart LeftBoot;
   private final ModelPart RightBoot;

   public BootsModel(ModelPart root) {
      this.root = root.getChild("root");
      this.Boots = this.root.getChild("Boots");
      this.LeftBoot = this.Boots.getChild("LeftBoot");
      this.RightBoot = this.Boots.getChild("RightBoot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition Boots = root.addOrReplaceChild("Boots", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition LeftBoot = Boots.addOrReplaceChild(
         "LeftBoot",
         CubeListBuilder.create()
            .texOffs(0, 8)
            .addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F))
            .texOffs(16, 0)
            .addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(0, 16)
            .addBox(-2.0F, 6.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(1.9F, -12.0F, 0.0F)
      );
      PartDefinition RightBoot = Boots.addOrReplaceChild(
         "RightBoot",
         CubeListBuilder.create()
            .texOffs(16, 0)
            .mirror()
            .addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.4F))
            .mirror(false)
            .texOffs(0, 8)
            .mirror()
            .addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F))
            .mirror(false)
            .texOffs(0, 16)
            .mirror()
            .addBox(-2.0F, 6.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.5F))
            .mirror(false),
         PartPose.offset(-1.9F, -12.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void renderLeftToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
      this.LeftBoot.render(poseStack, vertexConsumer, i, j);
   }

   public void renderRightToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
      this.RightBoot.render(poseStack, vertexConsumer, i, j);
   }

   public ModelPart root() {
      return this.root;
   }
}
