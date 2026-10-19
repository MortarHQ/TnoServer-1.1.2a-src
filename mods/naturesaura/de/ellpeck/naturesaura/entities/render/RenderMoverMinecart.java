package de.ellpeck.naturesaura.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.entities.EntityMoverMinecart;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.level.block.state.BlockState;

public class RenderMoverMinecart extends MinecartRenderer<EntityMoverMinecart> {
   private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/models/mover_cart.png");
   private final RenderMoverMinecart.ModelMoverMinecart model = new RenderMoverMinecart.ModelMoverMinecart();

   public RenderMoverMinecart(Context p_174300_) {
      super(p_174300_, ModelLayers.MINECART);
   }

   protected void renderMinecartContents(
      EntityMoverMinecart entityIn, float partialTicks, BlockState stateIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.0F, 1.375F, 0.0F);
      matrixStackIn.translate(0.0F, 0.0F, 1.0F);
      matrixStackIn.mulPose(Axis.XP.rotationDegrees(180.0F));
      this.model
         .renderToBuffer(
            matrixStackIn,
            bufferIn.getBuffer(this.model.renderType(RES)),
            packedLightIn,
            OverlayTexture.NO_OVERLAY,
            ARGB32.colorFromFloat(1.0F, 1.0F, 1.0F, 1.0F)
         );
      matrixStackIn.popPose();
   }

   private static class ModelMoverMinecart extends Model {
      private final ModelPart model;

      public ModelMoverMinecart() {
         super(RenderType::entityCutout);
         MeshDefinition mesh = new MeshDefinition();
         PartDefinition part = mesh.getRoot();
         part.addOrReplaceChild("main", new CubeListBuilder().addBox(0.0F, 0.0F, 0.0F, 16.0F, 24.0F, 16.0F), PartPose.ZERO);
         this.model = LayerDefinition.create(mesh, 64, 64).bakeRoot();
      }

      public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
         this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      }
   }
}
