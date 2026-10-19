package artifacts.client.item.model;

import artifacts.client.item.ArtifactLayers;
import artifacts.client.item.RendererUtil;
import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class BeltModel extends HumanoidModel<LivingEntity> {
   protected final ModelPart charm = this.body.getChild("charm");
   private final float xOffset;
   private final float zOffset;
   private final float rotation;

   public BeltModel(ModelPart part, Function<ResourceLocation, RenderType> renderType, float xOffset, float zOffset, float rotation) {
      super(part, renderType);
      this.xOffset = xOffset;
      this.zOffset = zOffset;
      this.rotation = rotation;
   }

   public BeltModel(ModelPart part, float xOffset, float zOffset, float rotation) {
      this(part, RenderType::entityCutoutNoCull, xOffset, zOffset, rotation);
   }

   public void setCharmPosition(int slot) {
      float xOffset = slot % 2 == 0 ? this.xOffset : -this.xOffset;
      float zOffset = slot % 4 < 2 ? this.zOffset : -this.zOffset;
      this.charm.setPos(xOffset, 9.0F, zOffset);
      float rotation = slot % 4 < 2 ? 0.0F : (float) -Math.PI;
      rotation += slot % 2 == 0 ^ slot % 4 >= 2 ? this.rotation : -this.rotation;
      this.charm.yRot = rotation;
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.body);
   }

   public static BeltModel createCloudInABottleModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.CLOUD_IN_A_BOTTLE), RenderType::entityTranslucent, 3.0F, -3.0F, -0.5F) {
         private final ModelPart cloud = this.charm.getChild("cloud");

         public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.cloud.yRot = ageInTicks / 50.0F;
            this.cloud.y = Mth.cos(ageInTicks / 30.0F) / 2.0F;
         }
      };
   }

   public static HumanoidModel<LivingEntity> createHeliumFlamingoModel() {
      ModelPart part = RendererUtil.bakeLayer(ArtifactLayers.HELIUM_FLAMINGO);
      return new HumanoidModel<LivingEntity>(part, RenderType::entityCutoutNoCull) {
         protected Iterable<ModelPart> headParts() {
            return ImmutableList.of();
         }

         protected Iterable<ModelPart> bodyParts() {
            return ImmutableList.of(this.body);
         }
      };
   }

   public static BeltModel createObsidianSkullModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.OBSIDIAN_SKULL), 4.5F, -4.0F, -0.5F);
   }

   public static BeltModel createAntidoteVesselModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.ANTIDOTE_VESSEL), 4.0F, -3.0F, -0.5F);
   }

   public static BeltModel createUniversalAttractorModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.UNIVERSAL_ATTRACTOR), 2.5F, -3.0F, 0.0F);
   }

   public static BeltModel createCrystalHeartModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.CRYSTAL_HEART), RenderType::entityTranslucent, 2.5F, -3.01F, 0.0F);
   }

   public static BeltModel createChorusTotemModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.CHORUS_TOTEM), 4.0F, -3.0F, -0.5F);
   }

   public static BeltModel createWarpDriveModel() {
      return new BeltModel(RendererUtil.bakeLayer(ArtifactLayers.WARP_DRIVE), 3.5F, -3.0F, -0.3F);
   }

   private static MeshDefinition createBelt(CubeListBuilder charm) {
      CubeDeformation deformation = new CubeDeformation(0.5F);
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot()
         .addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.ZERO);
      mesh.getRoot().getChild("body").addOrReplaceChild("charm", charm, PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createAntidoteVessel() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F);
      charm.texOffs(0, 26);
      charm.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F);
      return createBelt(charm);
   }

   public static MeshDefinition createCloudInABottle() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F);
      charm.texOffs(0, 25);
      charm.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F);
      MeshDefinition mesh = createBelt(charm);
      mesh.getRoot()
         .getChild("body")
         .getChild("charm")
         .addOrReplaceChild("cloud", CubeListBuilder.create().texOffs(8, 25).addBox(-1.0F, 1.5F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createCrystalHeart() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.5F, 0.0F, 0.0F, 2.0F, 3.0F, 1.0F);
      charm.texOffs(6, 16);
      charm.addBox(0.5F, 0.0F, 0.0F, 2.0F, 3.0F, 1.0F);
      charm.texOffs(0, 20);
      charm.addBox(-0.5F, 1.0F, 0.0F, 1.0F, 4.0F, 1.0F);
      charm.texOffs(4, 20);
      charm.addBox(-1.5F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      charm.texOffs(8, 20);
      charm.addBox(0.5F, 3.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      return createBelt(charm);
   }

   public static MeshDefinition createHeliumFlamingo() {
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot()
         .addOrReplaceChild(
            "body",
            CubeListBuilder.create()
               .texOffs(16, 36)
               .addBox(-1.0F, 1.0F, -14.0F, 2.0F, 3.0F, 5.0F)
               .texOffs(0, 18)
               .addBox(4.0F, 9.0F, -7.0F, 4.0F, 4.0F, 14.0F)
               .texOffs(0, 0)
               .addBox(-8.0F, 9.0F, -7.0F, 4.0F, 4.0F, 14.0F)
               .texOffs(36, 0)
               .addBox(-4.0F, 9.0F, 3.0F, 8.0F, 4.0F, 4.0F)
               .texOffs(36, 8)
               .addBox(-4.0F, 9.0F, -7.0F, 8.0F, 4.0F, 4.0F)
               .texOffs(0, 36)
               .addBox(-2.0F, 1.0F, -9.0F, 4.0F, 11.0F, 4.0F),
            PartPose.ZERO
         );
      return mesh;
   }

   public static MeshDefinition createObsidianSkull() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.5F, 0.0F, 0.0F, 5.0F, 3.0F, 4.0F);
      charm.texOffs(18, 16);
      charm.addBox(-1.5F, 3.0F, 0.0F, 1.0F, 1.0F, 2.0F);
      charm.texOffs(18, 19);
      charm.addBox(0.5F, 3.0F, 0.0F, 1.0F, 1.0F, 2.0F);
      return createBelt(charm);
   }

   public static MeshDefinition createUniversalAttractor() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.5F, 0.0F, 0.0F, 5.0F, 2.0F, 1.0F);
      charm.texOffs(0, 19);
      charm.addBox(-2.5F, 2.0F, 0.0F, 2.0F, 4.0F, 1.0F);
      charm.texOffs(6, 19);
      charm.addBox(0.5F, 2.0F, 0.0F, 2.0F, 4.0F, 1.0F);
      return createBelt(charm);
   }

   public static MeshDefinition createChorusTotem() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-1.5F, -1.0F, -1.0F, 3.0F, 7.0F, 2.0F);
      charm.texOffs(10, 16);
      charm.addBox(-3.5F, 0.0F, 1.0F, 7.0F, 4.0F, 0.0F);
      charm.texOffs(0, 25);
      charm.addBox(-2.5F, 3.0F, -2.0F, 5.0F, 2.0F, 2.0F);
      charm.texOffs(10, 20);
      charm.addBox(-1.0F, 1.0F, -1.5F, 2.0F, 2.0F, 0.0F);
      return createBelt(charm);
   }

   public static MeshDefinition createWarpDrive() {
      CubeListBuilder charm = CubeListBuilder.create();
      charm.texOffs(0, 16);
      charm.addBox(-2.5F, 0.0F, -2.0F, 5.0F, 4.0F, 4.0F);
      charm.texOffs(0, 24);
      charm.addBox(-3.0F, 1.0F, -1.0F, 1.0F, 2.0F, 2.0F);
      charm.texOffs(6, 24);
      charm.addBox(2.0F, 1.0F, -1.0F, 1.0F, 2.0F, 2.0F);
      charm.texOffs(0, 28);
      charm.addBox(-2.5F, 1.0F, 0.0F, 5.0F, 2.0F, 0.0F);
      charm.texOffs(0, 30);
      charm.addBox(-2.5F, 2.0F, -1.0F, 5.0F, 0.0F, 2.0F);
      return createBelt(charm);
   }
}
