package artifacts.client.mimic.model;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class MimicChestLayerModel extends EntityModel<MimicEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Artifacts.id("mimic_overlay"), "mimic_overlay");
   protected final ModelPart bottom;
   protected final ModelPart lid;

   public MimicChestLayerModel(ModelPart part) {
      this.bottom = part.getChild("bottom");
      this.lid = part.getChild("lid");
   }

   public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
      MimicModel.setChestRotations(mimic, partialTicks, this.lid, this.bottom);
   }

   public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      this.bottom.render(matrixStack, buffer, packedLight, packedOverlay, color);
      this.lid.render(matrixStack, buffer, packedLight, packedOverlay, color);
   }

   public static LayerDefinition createLayer() {
      MeshDefinition mesh = new MeshDefinition();
      mesh.getRoot()
         .addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, -9.0F, 0.0F, 14.0F, 10.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
      mesh.getRoot()
         .addOrReplaceChild(
            "lid",
            CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F).texOffs(0, 0).addBox(7.0F, -2.0F, 14.0F, 2.0F, 4.0F, 1.0F),
            PartPose.offset(0.0F, 9.0F, 1.0F)
         );
      return LayerDefinition.create(mesh, 64, 64);
   }
}
