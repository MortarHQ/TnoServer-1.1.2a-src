package artifacts.client.item.model;

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
import net.minecraft.world.entity.LivingEntity;

public class HeadModel extends HumanoidModel<LivingEntity> {
   public HeadModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
      super(part, renderType);
   }

   public HeadModel(ModelPart part) {
      this(part, RenderType::entityCutoutNoCull);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of();
   }

   public static MeshDefinition createEmptyHat(CubeListBuilder head) {
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot().addOrReplaceChild("head", head, PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createHat(CubeListBuilder head) {
      CubeDeformation deformation = new CubeDeformation(0.51F);
      head.texOffs(0, 0);
      head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation);
      return createEmptyHat(head);
   }

   public static MeshDefinition createDiagonalHat(CubeListBuilder head, CubeListBuilder diagonalParts, String partName) {
      MeshDefinition mesh = createHat(head);
      mesh.getRoot().getChild("head").addOrReplaceChild(partName, diagonalParts, PartPose.rotation((float) (Math.PI / 4), 0.0F, 0.0F));
      return mesh;
   }

   public static MeshDefinition createDrinkingHat() {
      CubeListBuilder head = CubeListBuilder.create();
      CubeListBuilder straws = CubeListBuilder.create();
      head.texOffs(32, 11);
      head.addBox(-4.0F, -6.0F, -8.0F, 8.0F, 1.0F, 4.0F);
      head.texOffs(32, 0);
      head.addBox(4.0F, -11.0F, -1.0F, 3.0F, 6.0F, 3.0F);
      head.texOffs(44, 0);
      head.addBox(-7.0F, -11.0F, -1.0F, 3.0F, 6.0F, 3.0F);
      head.texOffs(32, 9);
      head.addBox(-6.0F, -1.0F, -5.0F, 12.0F, 1.0F, 1.0F);
      straws.texOffs(0, 16);
      straws.addBox(5.0F, -4.0F, -3.0F, 1.0F, 1.0F, 8.0F);
      straws.texOffs(18, 16);
      straws.addBox(-6.0F, -4.0F, -3.0F, 1.0F, 1.0F, 8.0F);
      return createDiagonalHat(head, straws, "straws");
   }

   public static MeshDefinition createNightVisionGoggles() {
      CubeListBuilder head = CubeListBuilder.create();
      head.texOffs(0, 21);
      head.addBox(-4.0F, -6.0F, -4.95F, 8.0F, 4.0F, 1.0F);
      head.texOffs(0, 16);
      head.addBox(1.5F, -5.0F, -7.95F, 2.0F, 2.0F, 3.0F);
      head.texOffs(10, 16);
      head.addBox(-3.5F, -5.0F, -7.95F, 2.0F, 2.0F, 3.0F);
      return createHat(head);
   }

   public static MeshDefinition createSnorkel() {
      CubeListBuilder head = CubeListBuilder.create();
      CubeListBuilder tube = CubeListBuilder.create();
      head.texOffs(32, 0);
      head.addBox(-2.0F, -1.5F, -6.0F, 8.0F, 2.0F, 2.0F);
      tube.texOffs(0, 16);
      tube.addBox(4.01F, -5.0F, -3.0F, 2.0F, 2.0F, 12.0F);
      return createDiagonalHat(head, tube, "tube");
   }

   public static MeshDefinition createSuperstitiousHat() {
      CubeListBuilder head = CubeListBuilder.create();
      head.texOffs(0, 0);
      head.addBox(-4.0F, -16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      head.texOffs(0, 16);
      head.addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F);
      return createEmptyHat(head);
   }

   public static MeshDefinition createBrimmedHat(CubeListBuilder head) {
      head.texOffs(0, 16);
      head.addBox(-8.0F, -5.125F, -8.0F, 16.0F, 0.0F, 16.0F);
      return createHat(head);
   }

   public static MeshDefinition createWhoopeeCushion() {
      CubeListBuilder head = CubeListBuilder.create();
      head.texOffs(0, 0);
      head.addBox(-3.0F, -10.0F, -3.0F, 6.0F, 2.0F, 6.0F);
      head.texOffs(0, 8);
      head.addBox(-2.0F, -9.25F, 3.0F, 4.0F, 0.0F, 4.0F);
      return createEmptyHat(head);
   }

   public static MeshDefinition createCowboyHat() {
      CubeListBuilder head = CubeListBuilder.create();
      CubeListBuilder leftBrim = CubeListBuilder.create();
      CubeListBuilder rightBrim = CubeListBuilder.create();
      head.texOffs(-16, 16);
      head.addBox(-6.0F, -5.125F, -8.0F, 12.0F, 0.0F, 16.0F);
      leftBrim.texOffs(8, 16);
      leftBrim.addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 16.0F);
      rightBrim.texOffs(12, 16);
      rightBrim.addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 16.0F);
      MeshDefinition mesh = createHat(head);
      mesh.getRoot()
         .getChild("head")
         .addOrReplaceChild("left_brim", leftBrim, PartPose.offsetAndRotation(6.0F, -5.125F, -8.0F, 0.0F, 0.0F, (float) (-Math.PI / 4)));
      mesh.getRoot()
         .getChild("head")
         .addOrReplaceChild("right_brim", rightBrim, PartPose.offsetAndRotation(-7.41421F, -6.53921F, -8.0F, 0.0F, 0.0F, (float) (Math.PI / 4)));
      return mesh;
   }

   public static MeshDefinition createAnglersHat() {
      CubeListBuilder head = CubeListBuilder.create();
      CubeDeformation deformation = new CubeDeformation(0.0F, 0.51F, 0.2505F);
      head.texOffs(24, -4);
      head.addBox(4.51F, -13.635F, 0.2505F, 0.0F, 8.0F, 4.0F, deformation);
      return createBrimmedHat(head);
   }
}
