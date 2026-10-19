package net.blay09.mods.waystones.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class WaystoneModel extends Model {
   private final ModelPart root;

   public WaystoneModel(ModelPart root) {
      super(it -> RenderType.cutout());
      this.root = root;
   }

   public static LayerDefinition createLayer(CubeDeformation cubeDeformation) {
      MeshDefinition meshDefinition = new MeshDefinition();
      PartDefinition partDefinition = meshDefinition.getRoot();
      partDefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(144, 0).addBox(-10.0F, -48.0F, -10.0F, 20.0F, 28.0F, 20.0F, cubeDeformation), PartPose.ZERO
      );
      return LayerDefinition.create(meshDefinition, 256, 256);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
