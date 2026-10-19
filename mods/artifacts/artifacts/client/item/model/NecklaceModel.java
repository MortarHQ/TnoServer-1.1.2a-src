package artifacts.client.item.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class NecklaceModel extends HumanoidModel<LivingEntity> {
   public NecklaceModel(ModelPart part) {
      super(part, RenderType::entityTranslucent);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.body);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay, int color) {
      poseStack.pushPose();
      if (this.young) {
         float babyBodyScale = 2.0F;
         float scale = 1.0F / babyBodyScale;
         poseStack.scale(scale, scale, scale);
         float bodyYOffset = 24.0F;
         poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
      }

      poseStack.scale(0.51F, 0.51F, 0.51F);
      this.bodyParts().forEach(modelPart -> modelPart.render(poseStack, buffer, light, overlay, color));
      poseStack.popPose();
   }

   public static MeshDefinition createNecklace(CubeListBuilder body) {
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot().addOrReplaceChild("body", body.texOffs(0, 0).addBox(-8.0F, -0.5F, -4.5F, 16.0F, 25.0F, 9.0F), PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createCenteredNecklace(CubeListBuilder body) {
      MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot().addOrReplaceChild("body", body.texOffs(0, 0).addBox(-8.5F, -0.5F, -4.5F, 17.0F, 25.0F, 9.0F), PartPose.ZERO);
      return mesh;
   }

   public static MeshDefinition createCharmOfSinking() {
      CubeListBuilder body = CubeListBuilder.create();
      body.texOffs(50, 0);
      body.addBox(-1.0F, 3.5F, -5.0F, 2.0F, 4.0F, 1.0F);
      return createNecklace(body);
   }

   public static MeshDefinition createCrossNecklace() {
      CubeListBuilder body = CubeListBuilder.create();
      body.texOffs(52, 0);
      body.addBox(-0.5F, 4.5F, -5.0F, 1.0F, 4.0F, 1.0F);
      body.texOffs(56, 0);
      body.addBox(-1.5F, 5.5F, -5.0F, 3.0F, 1.0F, 1.0F);
      return createCenteredNecklace(body);
   }

   public static MeshDefinition createPanicNecklace() {
      CubeListBuilder body = CubeListBuilder.create();
      body.texOffs(52, 0);
      body.addBox(-2.5F, 5.5F, -5.0F, 2.0F, 2.0F, 1.0F);
      body.texOffs(58, 0);
      body.addBox(0.5F, 5.5F, -5.0F, 2.0F, 2.0F, 1.0F);
      body.texOffs(52, 3);
      body.addBox(-1.5F, 6.5F, -5.0F, 3.0F, 2.0F, 1.0F);
      body.texOffs(60, 4);
      body.addBox(-0.5F, 8.5F, -5.0F, 1.0F, 1.0F, 1.0F);
      return createCenteredNecklace(body);
   }

   public static MeshDefinition createCharmOfShrinking() {
      CubeListBuilder body = CubeListBuilder.create();
      body.texOffs(52, 0);
      body.addBox(-1.5F, 4.5F, -5.0F, 3.0F, 2.0F, 1.0F);
      body.texOffs(52, 3);
      body.addBox(-0.5F, 6.5F, -5.0F, 1.0F, 1.0F, 1.0F);
      return createCenteredNecklace(body);
   }

   public static MeshDefinition createPendant() {
      CubeListBuilder body = CubeListBuilder.create();
      body.texOffs(50, 0);
      body.addBox(-1.0F, 4.5F, -5.0F, 2.0F, 2.0F, 1.0F);
      return createNecklace(body);
   }
}
