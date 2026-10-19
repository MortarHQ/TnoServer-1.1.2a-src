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

public class TasselsModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart Tassels;
   private final ModelPart bone;
   private final ModelPart bone2;
   private final ModelPart Tassels2;
   private final ModelPart bone3;
   private final ModelPart bone4;

   public TasselsModel(ModelPart root) {
      this.root = root.getChild("root");
      this.Tassels = this.root.getChild("Tassels");
      this.bone = this.Tassels.getChild("bone");
      this.bone2 = this.Tassels.getChild("bone2");
      this.Tassels2 = this.root.getChild("Tassels2");
      this.bone3 = this.Tassels2.getChild("bone3");
      this.bone4 = this.Tassels2.getChild("bone4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition Tassels = root.addOrReplaceChild(
         "Tassels",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offset(0.0F, -24.0F, 0.0F)
      );
      PartDefinition bone = Tassels.addOrReplaceChild(
         "bone", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 3.4582F, -2.815F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r1 = bone.addOrReplaceChild(
         "Body_r1",
         CubeListBuilder.create().texOffs(10, 16).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8308F, 0.9886F, 0.3814F, -0.0876F, -0.0869F, -0.7778F)
      );
      PartDefinition Body_r2 = bone.addOrReplaceChild(
         "Body_r2",
         CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8953F, 1.0104F, 0.3908F, -0.0873F, -0.2618F, 0.0F)
      );
      PartDefinition Body_r3 = bone.addOrReplaceChild(
         "Body_r3",
         CubeListBuilder.create()
            .texOffs(16, 16)
            .addBox(2.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
            .texOffs(4, 16)
            .addBox(1.5F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0474F, 5.4758F, -0.572F, 0.0873F, -0.2618F, 0.0F)
      );
      PartDefinition bone2 = Tassels.addOrReplaceChild(
         "bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 3.4582F, -2.815F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r4 = bone2.addOrReplaceChild(
         "Body_r4",
         CubeListBuilder.create().texOffs(10, 16).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-5.8308F, 0.9886F, 0.3814F, -0.0876F, 0.0869F, 0.7778F)
      );
      PartDefinition Body_r5 = bone2.addOrReplaceChild(
         "Body_r5",
         CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-5.8953F, 1.0104F, 0.3908F, -0.0873F, 0.2618F, 0.0F)
      );
      PartDefinition Body_r6 = bone2.addOrReplaceChild(
         "Body_r6",
         CubeListBuilder.create()
            .texOffs(16, 16)
            .mirror()
            .addBox(-3.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
            .mirror(false)
            .texOffs(4, 16)
            .mirror()
            .addBox(-4.5F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(-3.0474F, 5.4758F, -0.572F, 0.0873F, 0.2618F, 0.0F)
      );
      PartDefinition Tassels2 = root.addOrReplaceChild(
         "Tassels2",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offset(0.0F, -24.0F, 0.0F)
      );
      PartDefinition bone3 = Tassels2.addOrReplaceChild(
         "bone3",
         CubeListBuilder.create()
            .texOffs(10, 19)
            .addBox(4.7808F, -2.0614F, 0.5814F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(10, 19)
            .mirror()
            .addBox(-2.7808F, -2.0614F, 0.5814F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(-3.0F, 3.4582F, -2.815F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition Body_r7 = bone3.addOrReplaceChild(
         "Body_r7",
         CubeListBuilder.create().texOffs(4, 16).mirror().addBox(-4.5F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(2.9526F, 5.4758F, -0.572F, 0.0873F, 0.2618F, 0.0F)
      );
      PartDefinition Body_r8 = bone3.addOrReplaceChild(
         "Body_r8",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .mirror()
            .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(0, 22)
            .mirror()
            .addBox(-3.5F, -0.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(0.1047F, 1.0104F, 0.3908F, -0.0873F, 0.2618F, 0.0F)
      );
      PartDefinition Body_r9 = bone3.addOrReplaceChild(
         "Body_r9",
         CubeListBuilder.create().texOffs(10, 16).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(0.1692F, 0.9886F, 0.3814F, -0.0876F, 0.0869F, 0.7778F)
      );
      PartDefinition Body_r10 = bone3.addOrReplaceChild(
         "Body_r10",
         CubeListBuilder.create().texOffs(10, 16).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8308F, 0.9886F, 0.3814F, -0.0876F, -0.0869F, -0.7778F)
      );
      PartDefinition Body_r11 = bone3.addOrReplaceChild(
         "Body_r11",
         CubeListBuilder.create()
            .texOffs(0, 22)
            .addBox(-0.5F, -0.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 16)
            .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8953F, 1.0104F, 0.3908F, -0.0873F, -0.2618F, 0.0F)
      );
      PartDefinition Body_r12 = bone3.addOrReplaceChild(
         "Body_r12",
         CubeListBuilder.create().texOffs(4, 16).addBox(1.5F, -1.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0474F, 5.4758F, -0.572F, 0.0873F, -0.2618F, 0.0F)
      );
      PartDefinition bone4 = Tassels2.addOrReplaceChild(
         "bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 3.4582F, -2.815F, -0.0436F, 0.0F, 0.0F)
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
