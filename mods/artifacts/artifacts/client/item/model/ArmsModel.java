package artifacts.client.item.model;

import artifacts.client.item.ArtifactLayers;
import artifacts.client.item.RendererUtil;
import artifacts.extensions.pocketpiston.LivingEntityExtensions;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class ArmsModel extends HumanoidModel<LivingEntity> {
   public ArmsModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
      super(part, renderType);
   }

   public ArmsModel(ModelPart part) {
      this(part, RenderType::entityCutoutNoCull);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.leftArm, this.rightArm);
   }

   public void renderArm(HumanoidArm handSide, PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      this.getArm(handSide).visible = true;
      this.getArm(handSide.getOpposite()).visible = false;
      this.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, color);
   }

   public static ArmsModel createClawsModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.claws(hasSlimArms)));
   }

   public static ArmsModel createGloveModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.glove(hasSlimArms)));
   }

   public static ArmsModel createGoldenHookModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.goldenHook(hasSlimArms)));
   }

   public static ArmsModel createPocketPistonModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.pocketPiston(hasSlimArms))) {
         public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            HumanoidArm mainHandSide = RendererUtil.getArmSide(entity, entity.swingingArm);
            this.getPistonHead(mainHandSide.getOpposite()).y = 0.0F;
            this.getPistonHead(mainHandSide).y = ((LivingEntityExtensions)entity).artifacts$getPocketPistonLength() * 2.0F;
         }

         private ModelPart getPistonHead(HumanoidArm arm) {
            return this.getArm(arm).getChild("artifact").getChild("piston_head");
         }
      };
   }

   public static ArmsModel createOnionRingModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.onionRing(hasSlimArms)));
   }

   public static ArmsModel createPickaxeHeaterModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.pickaxeHeater(hasSlimArms)));
   }

   public static ArmsModel createWitheredBraceletModel(boolean hasSlimArms) {
      return new ArmsModel(RendererUtil.bakeLayer(ArtifactLayers.witheredBracelet(hasSlimArms)));
   }

   public static MeshDefinition createEmptyArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean hasSlimArms) {
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot().addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
      mesh.getRoot().addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
      float armWidth = hasSlimArms ? 3.0F : 4.0F;
      mesh.getRoot().getChild("left_arm").addOrReplaceChild("artifact", leftArm, PartPose.offset(-1.0F + armWidth / 2.0F, 10.0F, 0.0F));
      mesh.getRoot().getChild("right_arm").addOrReplaceChild("artifact", rightArm, PartPose.offset(1.0F - armWidth / 2.0F, 10.0F, 0.0F));
      return mesh;
   }

   public static MeshDefinition createArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean hasSlimArms) {
      float armWidth = hasSlimArms ? 3.0F : 4.0F;
      leftArm.texOffs(0, 0);
      leftArm.addBox(-armWidth / 2.0F, -12.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.5F));
      rightArm.texOffs(16, 0);
      rightArm.addBox(-armWidth / 2.0F, -12.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.5F));
      return createEmptyArms(leftArm, rightArm, hasSlimArms);
   }

   public static MeshDefinition createSleevedArms(CubeListBuilder leftArm, CubeListBuilder rightArm, boolean hasSlimArms) {
      float armWidth = hasSlimArms ? 3.0F : 4.0F;
      leftArm.texOffs(0, 16);
      leftArm.addBox(-armWidth / 2.0F, -12.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.75F));
      rightArm.texOffs(16, 16);
      rightArm.addBox(-armWidth / 2.0F, -12.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.75F));
      return createArms(leftArm, rightArm, hasSlimArms);
   }

   public static MeshDefinition createSleevedArms(boolean hasSlimArms) {
      return createSleevedArms(CubeListBuilder.create(), CubeListBuilder.create(), hasSlimArms);
   }

   public static MeshDefinition createClaws(boolean hasSlimArms) {
      CubeListBuilder leftArm = CubeListBuilder.create();
      CubeListBuilder rightArm = CubeListBuilder.create();
      leftArm.texOffs(0, 0);
      leftArm.addBox(hasSlimArms ? -2.0F : -1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 1.0F);
      rightArm.texOffs(8, 0);
      rightArm.addBox(hasSlimArms ? -1.0F : -1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 1.0F);
      leftArm.texOffs(0, 6);
      leftArm.addBox(hasSlimArms ? -2.0F : -1.5F, 0.0F, 0.5F, 3.0F, 5.0F, 1.0F);
      rightArm.texOffs(8, 6);
      rightArm.addBox(hasSlimArms ? -1.0F : -1.5F, 0.0F, 0.5F, 3.0F, 5.0F, 1.0F);
      leftArm.texOffs(16, 0);
      leftArm.addBox(hasSlimArms ? 1.0F : 1.5F, 0.0F, -1.5F, 1.0F, 4.0F, 1.0F);
      rightArm.texOffs(20, 0);
      rightArm.addBox(hasSlimArms ? -2.0F : -2.5F, 0.0F, -1.5F, 1.0F, 4.0F, 1.0F);
      leftArm.texOffs(16, 6);
      leftArm.addBox(hasSlimArms ? 1.0F : 1.5F, 0.0F, 0.5F, 1.0F, 4.0F, 1.0F);
      rightArm.texOffs(20, 6);
      rightArm.addBox(hasSlimArms ? -2.0F : -2.5F, 0.0F, 0.5F, 1.0F, 4.0F, 1.0F);
      return createEmptyArms(leftArm, rightArm, hasSlimArms);
   }

   public static MeshDefinition createGoldenHook(boolean hasSlimArms) {
      CubeListBuilder leftArm = CubeListBuilder.create();
      CubeListBuilder rightArm = CubeListBuilder.create();
      leftArm.texOffs(32, 0);
      leftArm.addBox(-2.5F, 2.0F, -0.5F, 5.0F, 5.0F, 1.0F);
      rightArm.texOffs(48, 0);
      rightArm.addBox(-2.5F, 2.0F, -0.5F, 5.0F, 5.0F, 1.0F);
      leftArm.texOffs(32, 6);
      leftArm.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      rightArm.texOffs(48, 6);
      rightArm.addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      return createSleevedArms(leftArm, rightArm, hasSlimArms);
   }

   public static MeshDefinition createPocketPiston(boolean hasSlimArms) {
      CubeListBuilder leftArm = CubeListBuilder.create();
      CubeListBuilder rightArm = CubeListBuilder.create();
      CubeListBuilder leftPistonHead = CubeListBuilder.create();
      CubeListBuilder rightPistonHead = CubeListBuilder.create();
      float armWidth = hasSlimArms ? 3.0F : 4.0F;
      float armDepth = 4.0F;
      float d = 0.135F;
      CubeDeformation baseDeformation = new CubeDeformation(d * armWidth, d * 3.0F, d * armDepth);
      leftArm.texOffs(0, 0);
      leftArm.addBox(-armWidth / 2.0F, -3.0F, -armDepth / 2.0F, armWidth, 3.0F, armDepth, baseDeformation);
      rightArm.texOffs(16, 0);
      rightArm.addBox(-armWidth / 2.0F, -3.0F, -armDepth / 2.0F, armWidth, 3.0F, armDepth, baseDeformation);
      CubeDeformation rodDeformation = new CubeDeformation(d * armWidth / 2.0F, 0.0F, d * armDepth / 2.0F);
      leftPistonHead.texOffs(0, 12);
      leftPistonHead.addBox(-(armWidth - 2.0F) / 2.0F, -2.0F + d * 3.0F, -(armDepth - 2.0F) / 2.0F, armWidth - 2.0F, 2.0F, armDepth - 2.0F, rodDeformation);
      rightPistonHead.texOffs(16, 12);
      rightPistonHead.addBox(-(armWidth - 2.0F) / 2.0F, -2.0F + d * 3.0F, -(armDepth - 2.0F) / 2.0F, armWidth - 2.0F, 2.0F, armDepth - 2.0F, rodDeformation);
      CubeDeformation headDeformation = new CubeDeformation(d * armWidth, d, d * armDepth);
      leftPistonHead.texOffs(0, 7);
      leftPistonHead.addBox(-armWidth / 2.0F, d * 3.0F + d, -armDepth / 2.0F, armWidth, 1.0F, armDepth, headDeformation);
      rightPistonHead.texOffs(16, 7);
      rightPistonHead.addBox(-armWidth / 2.0F, d * 3.0F + d, -armDepth / 2.0F, armWidth, 1.0F, armDepth, headDeformation);
      MeshDefinition mesh = createEmptyArms(leftArm, rightArm, hasSlimArms);
      mesh.getRoot().getChild("left_arm").getChild("artifact").addOrReplaceChild("piston_head", leftPistonHead, PartPose.ZERO);
      mesh.getRoot().getChild("right_arm").getChild("artifact").addOrReplaceChild("piston_head", rightPistonHead, PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createBracelet(boolean hasSlimArms, int size, int h) {
      CubeListBuilder leftArm = CubeListBuilder.create();
      CubeListBuilder rightArm = CubeListBuilder.create();
      float armWidth = hasSlimArms ? size - 1 : size;
      leftArm.texOffs(0, 0);
      leftArm.addBox(-1.0F - armWidth / 2.0F, h, -1.0F - size / 2.0F, armWidth + 2.0F, 2.0F, 1.0F);
      rightArm.texOffs(16, 0);
      rightArm.addBox(-1.0F - armWidth / 2.0F, h, -1.0F - size / 2.0F, armWidth + 2.0F, 2.0F, 1.0F);
      leftArm.texOffs(0, 3);
      leftArm.addBox(-1.0F - armWidth / 2.0F, h, size / 2.0F, armWidth + 2.0F, 2.0F, 1.0F);
      rightArm.texOffs(16, 3);
      rightArm.addBox(-1.0F - armWidth / 2.0F, h, size / 2.0F, armWidth + 2.0F, 2.0F, 1.0F);
      leftArm.texOffs(0, 6);
      leftArm.addBox(armWidth / 2.0F, h, -size / 2.0F, 1.0F, 2.0F, size);
      rightArm.texOffs(16, 6);
      rightArm.addBox(armWidth / 2.0F, h, -size / 2.0F, 1.0F, 2.0F, size);
      leftArm.texOffs(0, 12);
      leftArm.addBox(-1.0F - armWidth / 2.0F, h, -size / 2.0F, 1.0F, 2.0F, size);
      rightArm.texOffs(16, 12);
      rightArm.addBox(-1.0F - armWidth / 2.0F, h, -size / 2.0F, 1.0F, 2.0F, size);
      return createEmptyArms(leftArm, rightArm, hasSlimArms);
   }

   public static MeshDefinition createPickaxeHeater(boolean hasSlimArms) {
      CubeListBuilder leftArm = CubeListBuilder.create();
      CubeListBuilder rightArm = CubeListBuilder.create();
      float armWidth = hasSlimArms ? 3.0F : 4.0F;
      float armDepth = 4.0F;
      float deformationY = 1.0833334F;
      CubeDeformation deformation = new CubeDeformation(0.0F, 0.083333336F, 0.25F);
      leftArm.texOffs(32, 0);
      leftArm.addBox(armWidth / 2.0F + 0.5F, 0.5F - 2.0F * deformationY - 1.0F, -armDepth / 4.0F, 1.0F, 2.0F, 2.0F, deformation);
      rightArm.texOffs(48, 0);
      rightArm.addBox(-armWidth / 2.0F - 1.5F, 0.5F - 2.0F * deformationY - 1.0F, -armDepth / 4.0F, 1.0F, 2.0F, 2.0F, deformation);
      return createSleevedArms(leftArm, rightArm, hasSlimArms);
   }
}
