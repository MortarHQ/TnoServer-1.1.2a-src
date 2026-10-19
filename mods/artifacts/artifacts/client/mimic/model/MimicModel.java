package artifacts.client.mimic.model;

import artifacts.Artifacts;
import artifacts.entity.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class MimicModel extends EntityModel<MimicEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Artifacts.id("mimic"), "mimic");
   protected final ModelPart bottom;
   protected final ModelPart lid;

   public MimicModel(ModelPart part) {
      this.bottom = part.getChild("bottom");
      this.lid = part.getChild("lid");
   }

   public void setupAnim(MimicEntity mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void prepareMobModel(MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks) {
      setChestRotations(mimic, partialTicks, this.lid, this.bottom);
   }

   protected static void setChestRotations(MimicEntity mimic, float partialTicks, ModelPart lid, ModelPart bottom) {
      if (mimic.ticksInAir > 0) {
         lid.xRot = Math.max(-60.0F, (mimic.ticksInAir - 1 + partialTicks) * -6.0F) * 0.0174533F;
         bottom.xRot = Math.min(30.0F, (mimic.ticksInAir - 1 + partialTicks) * 3.0F) * 0.0174533F;
      } else {
         lid.xRot = 0.0F;
         bottom.xRot = 0.0F;
      }
   }

   public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
      this.lid.render(matrixStack, buffer, packedLight, packedOverlay, color);
      this.bottom.render(matrixStack, buffer, packedLight, packedOverlay, color);
   }

   public static LayerDefinition createLayer() {
      MeshDefinition mesh = new MeshDefinition();
      mesh.getRoot()
         .addOrReplaceChild(
            "bottom",
            CubeListBuilder.create()
               .texOffs(0, 15)
               .addBox(-6.0F, -4.0F, -13.0F, 12.0F, 3.0F, 12.0F)
               .texOffs(36, 15)
               .addBox(-6.0F, -1.0F, -13.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.02F)),
            PartPose.offset(0.0F, 15.0F, 7.0F)
         );
      mesh.getRoot()
         .addOrReplaceChild(
            "lid",
            CubeListBuilder.create()
               .texOffs(0, 0)
               .addBox(-6.0F, 0.0F, -13.0F, 12.0F, 3.0F, 12.0F)
               .texOffs(24, 0)
               .addBox(-6.0F, 0.0F, -13.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.02F)),
            PartPose.offset(0.0F, 15.0F, 7.0F)
         );
      return LayerDefinition.create(mesh, 64, 32);
   }
}
