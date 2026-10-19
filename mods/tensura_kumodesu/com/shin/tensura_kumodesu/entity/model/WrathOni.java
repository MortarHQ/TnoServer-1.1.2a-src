package com.shin.tensura_kumodesu.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class WrathOni<T extends LivingEntity> extends HierarchicalModel<T> implements CustomTensuraModelFormat<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "wrathoni"), "main");
   private final ModelPart head;
   private final ModelPart RightHorn;
   private final ModelPart RightBottom;
   private final ModelPart RightMiddle;
   private final ModelPart RightTop;
   private final ModelPart LeftHorn;
   private final ModelPart LeftBottom;
   private final ModelPart LeftMiddle;
   private final ModelPart LeftTop;
   public final ModelPart root;

   public WrathOni(ModelPart root) {
      super(RenderType::entityTranslucent);
      this.root = root;
      this.head = root.getChild("head");
      this.RightHorn = this.head.getChild("RightHorn");
      this.RightBottom = this.RightHorn.getChild("RightBottom");
      this.RightMiddle = this.RightHorn.getChild("RightMiddle");
      this.RightTop = this.RightHorn.getChild("RightTop");
      this.LeftHorn = this.head.getChild("LeftHorn");
      this.LeftBottom = this.LeftHorn.getChild("LeftBottom");
      this.LeftMiddle = this.LeftHorn.getChild("LeftMiddle");
      this.LeftTop = this.LeftHorn.getChild("LeftTop");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightHorn = head.addOrReplaceChild(
         "RightHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.8F, -7.0F, -4.2F, -0.1745F, 0.3054F, 0.0F)
      );
      PartDefinition RightBottom = RightHorn.addOrReplaceChild("RightBottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornright2_r1 = RightBottom.addOrReplaceChild(
         "hornright2_r1",
         CubeListBuilder.create().texOffs(8, 16).mirror().addBox(-1.0F, -2.7F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.27F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition hornright1_r1 = RightBottom.addOrReplaceChild(
         "hornright1_r1",
         CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F)
      );
      PartDefinition RightMiddle = RightHorn.addOrReplaceChild("RightMiddle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornright4_r1 = RightMiddle.addOrReplaceChild(
         "hornright4_r1",
         CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-1.0F, -3.55F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.37F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition hornright3_r1 = RightMiddle.addOrReplaceChild(
         "hornright3_r1",
         CubeListBuilder.create().texOffs(8, 16).mirror().addBox(-1.0F, -2.7F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.31F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition RightTop = RightHorn.addOrReplaceChild("RightTop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornright6_r1 = RightTop.addOrReplaceChild(
         "hornright6_r1",
         CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, -5.55F, -1.15F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition hornright5_r1 = RightTop.addOrReplaceChild(
         "hornright5_r1",
         CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-1.0F, -3.55F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.41F)).mirror(false),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition LeftHorn = head.addOrReplaceChild(
         "LeftHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(2.8F, -7.0F, -4.2F, -0.1745F, -0.3054F, 0.0F)
      );
      PartDefinition LeftBottom = LeftHorn.addOrReplaceChild("LeftBottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornleft2_r1 = LeftBottom.addOrReplaceChild(
         "hornleft2_r1",
         CubeListBuilder.create().texOffs(8, 21).addBox(-1.0F, -2.7F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.27F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition hornleft1_r1 = LeftBottom.addOrReplaceChild(
         "hornleft1_r1",
         CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6981F, 0.0F, 0.0F)
      );
      PartDefinition LeftMiddle = LeftHorn.addOrReplaceChild("LeftMiddle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornleft4_r1 = LeftMiddle.addOrReplaceChild(
         "hornleft4_r1",
         CubeListBuilder.create().texOffs(16, 21).addBox(-1.0F, -3.55F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.37F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition hornleft3_r1 = LeftMiddle.addOrReplaceChild(
         "hornleft3_r1",
         CubeListBuilder.create().texOffs(8, 21).addBox(-1.0F, -2.7F, -1.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.31F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition LeftTop = LeftHorn.addOrReplaceChild("LeftTop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition hornleft6_r1 = LeftTop.addOrReplaceChild(
         "hornleft6_r1",
         CubeListBuilder.create().texOffs(16, 21).addBox(-1.0F, -3.55F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.41F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition hornleft5_r1 = LeftTop.addOrReplaceChild(
         "hornleft5_r1",
         CubeListBuilder.create().texOffs(24, 21).addBox(-1.0F, -5.55F, -1.15F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   @Override
   public void renderToBuffer(
      @NotNull PoseStack poseStack,
      @NotNull VertexConsumer vertexConsumer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha,
      Player player
   ) {
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay);
   }

   @NotNull
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Model model) {
      if ((entity instanceof Player || entity instanceof CloneEntity) && model instanceof HumanoidModel) {
         this.head.copyFrom(((HumanoidModel)model).head);
      }
   }
}
