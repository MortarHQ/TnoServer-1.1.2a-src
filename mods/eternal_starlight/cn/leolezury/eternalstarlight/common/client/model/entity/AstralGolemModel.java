package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class AstralGolemModel<T extends AstralGolem> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "main");
   public static final ModelLayerLocation INNER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "inner_armor");
   public static final ModelLayerLocation OUTER_ARMOR_LOCATION = new ModelLayerLocation(EternalStarlight.id("astral_golem"), "outer_armor");
   private float armXRot = 0.0F;
   private int tintColor = -1;

   public AstralGolemModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, 26.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(5.0F, 26.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      AstralGolemMaterial material = entity.getMaterial();
      this.tintColor = material == null ? -1 : material.tintColor();
      if (entity.getMainHandItem().isEmpty()) {
         this.leftArm.xRot = this.leftArm.xRot + this.armXRot;
         this.rightArm.xRot = this.rightArm.xRot + this.armXRot;
      } else {
         this.rightArm.xRot = this.rightArm.xRot - 1.57F - this.armXRot;
      }

      this.head.y += 12.0F;
      this.hat.y += 12.0F;
      this.head.visible = true;
      this.body.y += 12.0F;
      this.leftArm.y += 12.0F;
      this.rightArm.y += 12.0F;
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
      super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
      this.leftArmPose = ArmPose.EMPTY;
      int animationTick = entity.getAttackAnimationTick();
      if (animationTick > 0) {
         this.armXRot = -2.0F + 1.5F * Mth.triangleWave(animationTick - partialTick, 10.0F);
      } else {
         this.armXRot = 0.0F;
      }

      if (entity.isGolemBlocking()) {
         this.leftArmPose = ArmPose.BLOCK;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, this.tintColor == -1 ? color : ARGB32.multiply(color, this.tintColor));
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, this.tintColor == -1 ? color : ARGB32.multiply(color, this.tintColor));
      this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, this.tintColor == -1 ? color : ARGB32.multiply(color, this.tintColor));
      this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, this.tintColor == -1 ? color : ARGB32.multiply(color, this.tintColor));
   }
}
