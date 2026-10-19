package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityGeneratorLimitRemover;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderGeneratorLimitRemover implements BlockEntityRenderer<BlockEntityGeneratorLimitRemover> {
   private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/models/generator_limit_remover_glint.png");
   private final RenderGeneratorLimitRemover.ModelLimitRemoverGlint model = new RenderGeneratorLimitRemover.ModelLimitRemoverGlint();

   public RenderGeneratorLimitRemover(Context context) {
   }

   public void render(
      BlockEntityGeneratorLimitRemover te, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn
   ) {
      BlockEntity above = te.getLevel().getBlockEntity(te.getBlockPos().above());
      if (above instanceof BlockEntityImpl && ((BlockEntityImpl)above).wantsLimitRemover()) {
         renderGlint(matrixStack, iRenderTypeBuffer, this.model, 0, 1, 0, combinedOverlayIn, RES, 1.0F);
         renderGlint(matrixStack, iRenderTypeBuffer, this.model, 0, 0, 0, combinedOverlayIn, RES, 1.0F);
      }
   }

   public static void renderGlint(
      PoseStack stack,
      MultiBufferSource buffer,
      RenderGeneratorLimitRemover.ModelLimitRemoverGlint model,
      int xOff,
      int yOff,
      int zOff,
      int combinedOverlayIn,
      ResourceLocation texture,
      float maxAlpha
   ) {
      stack.pushPose();
      int brightness = 15728880;
      float alpha = ((float)Math.sin(System.currentTimeMillis() / 800.0) + 1.0F) / 2.0F;
      stack.translate(-0.002F + xOff, 1.002F + yOff, 1.002F + zOff);
      stack.mulPose(Axis.XP.rotationDegrees(180.0F));
      stack.scale(1.004F, 1.004F, 1.004F);
      model.renderToBuffer(
         stack, buffer.getBuffer(model.renderType(texture)), brightness, combinedOverlayIn, ARGB32.colorFromFloat(alpha * maxAlpha, 1.0F, 1.0F, 1.0F)
      );
      stack.popPose();
   }

   public static class ModelLimitRemoverGlint extends Model {
      private final ModelPart model;

      public ModelLimitRemoverGlint() {
         super(RenderType::entityTranslucent);
         MeshDefinition mesh = new MeshDefinition();
         PartDefinition part = mesh.getRoot();
         part.addOrReplaceChild("main", new CubeListBuilder().addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
         this.model = LayerDefinition.create(mesh, 64, 64).bakeRoot();
      }

      public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
         this.model.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      }
   }
}
