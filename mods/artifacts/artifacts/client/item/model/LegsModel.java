package artifacts.client.item.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class LegsModel extends HumanoidModel<LivingEntity> {
   public LegsModel(ModelPart part) {
      super(part, RenderType::entityCutoutNoCull);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.leftLeg, this.rightLeg);
   }

   public static MeshDefinition createLegs(float delta, CubeListBuilder leftLeg, CubeListBuilder rightLeg) {
      CubeDeformation deformation = new CubeDeformation(delta);
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot()
         .addOrReplaceChild("left_leg", leftLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(1.9F, 12.0F, 0.0F));
      mesh.getRoot()
         .addOrReplaceChild(
            "right_leg", rightLeg.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation), PartPose.offset(-1.9F, 12.0F, 0.0F)
         );
      return mesh;
   }

   public static MeshDefinition createSleevedLegs(float delta, CubeListBuilder leftLeg, CubeListBuilder rightLeg) {
      CubeDeformation deformation = new CubeDeformation(delta + 0.25F);
      leftLeg.texOffs(0, 16);
      leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation);
      rightLeg.texOffs(16, 16);
      rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation);
      return createLegs(delta, leftLeg, rightLeg);
   }

   public static MeshDefinition createBoots(float delta) {
      return createBoots(delta, CubeListBuilder.create(), CubeListBuilder.create());
   }

   public static MeshDefinition createBoots(float delta, CubeListBuilder leftLeg, CubeListBuilder rightLeg) {
      CubeDeformation deformation = new CubeDeformation(delta, delta / 4.0F, delta / 4.0F);
      leftLeg.texOffs(0, 16);
      leftLeg.addBox(-2.0F, 9.0F + delta * 3.0F / 4.0F, -3.0F - delta * 5.0F / 4.0F, 4.0F, 3.0F, 1.0F, deformation);
      rightLeg.texOffs(16, 16);
      rightLeg.addBox(-2.0F, 9.0F + delta * 3.0F / 4.0F, -3.0F - delta * 5.0F / 4.0F, 4.0F, 3.0F, 1.0F, deformation);
      return createLegs(delta, leftLeg, rightLeg);
   }

   public static MeshDefinition createSlippers(CubeListBuilder leftLeg, CubeListBuilder rightLeg) {
      leftLeg.texOffs(32, 0);
      leftLeg.addBox(-2.5F, 8.51F, -7.01F, 5.0F, 4.0F, 5.0F);
      rightLeg.texOffs(32, 16);
      rightLeg.addBox(-2.5F, 8.51F, -7.0F, 5.0F, 4.0F, 5.0F);
      return createSleevedLegs(0.51F, leftLeg, rightLeg);
   }

   public static MeshDefinition createAquaDashers(float delta) {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      CubeDeformation deformation = new CubeDeformation(0.0F, delta, delta);
      leftLeg.texOffs(0, 16);
      leftLeg.addBox(2.0F + delta, 0.0F, 1.0F + delta * 3.0F / 2.0F, 0.0F, 12.0F, 4.0F, deformation);
      rightLeg.texOffs(16, 16);
      rightLeg.addBox(-2.0F - delta, 0.0F, 1.0F + delta * 3.0F / 2.0F, 0.0F, 12.0F, 4.0F, deformation);
      return createBoots(delta, leftLeg, rightLeg);
   }

   public static MeshDefinition createBunnyHoppers() {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      leftLeg.texOffs(32, 9);
      leftLeg.addBox(-0.5F, 10.0F, -7.5F, 1.0F, 1.0F, 1.0F);
      rightLeg.texOffs(32, 25);
      rightLeg.addBox(-0.5F, 10.0F, -7.5F, 1.0F, 1.0F, 1.0F);
      leftLeg.texOffs(52, 6);
      leftLeg.addBox(-1.0F, 9.0F, 2.0F, 2.0F, 2.0F, 2.0F);
      rightLeg.texOffs(52, 22);
      rightLeg.addBox(-1.0F, 9.0F, 2.0F, 2.0F, 2.0F, 2.0F);
      MeshDefinition mesh = createSlippers(leftLeg, rightLeg);
      mesh.getRoot()
         .getChild("left_leg")
         .addOrReplaceChild(
            "left_ear",
            CubeListBuilder.create().texOffs(52, 0).addBox(-3.15F, 3.51F, -3.01F, 2.0F, 5.0F, 1.0F),
            PartPose.rotation(0.0F, (float) (-Math.PI / 12), 0.0F)
         );
      mesh.getRoot()
         .getChild("right_leg")
         .addOrReplaceChild(
            "left_ear",
            CubeListBuilder.create().texOffs(52, 16).addBox(-3.15F, 3.51F, -3.0F, 2.0F, 5.0F, 1.0F),
            PartPose.rotation(0.0F, (float) (-Math.PI / 12), 0.0F)
         );
      mesh.getRoot()
         .getChild("left_leg")
         .addOrReplaceChild(
            "right_ear",
            CubeListBuilder.create().texOffs(58, 0).addBox(1.15F, 3.51F, -3.01F, 2.0F, 5.0F, 1.0F),
            PartPose.rotation(0.0F, (float) (Math.PI / 12), 0.0F)
         );
      mesh.getRoot()
         .getChild("right_leg")
         .addOrReplaceChild(
            "right_ear",
            CubeListBuilder.create().texOffs(58, 16).addBox(1.15F, 3.51F, -3.0F, 2.0F, 5.0F, 1.0F),
            PartPose.rotation(0.0F, (float) (Math.PI / 12), 0.0F)
         );
      return mesh;
   }

   public static MeshDefinition createFlippers() {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      leftLeg.texOffs(0, 16);
      leftLeg.addBox(-2.0F, 11.5F, -16.0F, 9.0F, 0.0F, 20.0F);
      rightLeg.texOffs(0, 36);
      rightLeg.addBox(-7.0F, 11.5F, -16.0F, 9.0F, 0.0F, 20.0F);
      return createLegs(0.5F, leftLeg, rightLeg);
   }

   public static MeshDefinition createKittySlippers() {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      leftLeg.texOffs(32, 9);
      leftLeg.addBox(-2.0F, 7.51F, -4.0F, 1.0F, 1.0F, 2.0F);
      rightLeg.texOffs(32, 25);
      rightLeg.addBox(-2.0F, 7.51F, -4.0F, 1.0F, 1.0F, 2.0F);
      leftLeg.texOffs(38, 9);
      leftLeg.addBox(1.0F, 7.51F, -4.0F, 1.0F, 1.0F, 2.0F);
      rightLeg.texOffs(38, 25);
      rightLeg.addBox(1.0F, 7.51F, -4.0F, 1.0F, 1.0F, 2.0F);
      leftLeg.texOffs(44, 9);
      leftLeg.addBox(-1.5F, 10.51F, -8.0F, 3.0F, 2.0F, 1.0F);
      rightLeg.texOffs(44, 25);
      rightLeg.addBox(-1.5F, 10.51F, -8.0F, 3.0F, 2.0F, 1.0F);
      return createSlippers(leftLeg, rightLeg);
   }

   public static MeshDefinition createSnowshoes() {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      leftLeg.texOffs(0, 16);
      leftLeg.addBox(-2.5F, 11.5F, -10.0F, 10.0F, 0.0F, 22.0F);
      rightLeg.texOffs(0, 38);
      rightLeg.addBox(-7.5F, 11.5F, -10.0F, 10.0F, 0.0F, 22.0F);
      return createLegs(0.5F, leftLeg, rightLeg);
   }

   public static MeshDefinition createSteadfastSpikes() {
      CubeListBuilder leftLeg = CubeListBuilder.create();
      CubeListBuilder rightLeg = CubeListBuilder.create();
      leftLeg.texOffs(32, 0);
      leftLeg.addBox(-1.5F, 9.0F, -7.0F, 1.0F, 3.0F, 5.0F);
      rightLeg.texOffs(43, 0);
      rightLeg.addBox(-1.5F, 9.0F, -7.0F, 1.0F, 3.0F, 5.0F);
      leftLeg.texOffs(32, 8);
      leftLeg.addBox(0.5F, 9.0F, -7.0F, 1.0F, 3.0F, 5.0F);
      rightLeg.texOffs(43, 8);
      rightLeg.addBox(0.5F, 9.0F, -7.0F, 1.0F, 3.0F, 5.0F);
      return createSleevedLegs(0.5F, leftLeg, rightLeg);
   }
}
