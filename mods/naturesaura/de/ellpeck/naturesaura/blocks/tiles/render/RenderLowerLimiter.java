package de.ellpeck.naturesaura.blocks.tiles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityLowerLimiter;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderLowerLimiter implements BlockEntityRenderer<BlockEntityLowerLimiter> {
   private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/models/lower_limiter_glint.png");
   private final RenderGeneratorLimitRemover.ModelLimitRemoverGlint model = new RenderGeneratorLimitRemover.ModelLimitRemoverGlint();

   public RenderLowerLimiter(Context context) {
   }

   public void render(
      BlockEntityLowerLimiter te, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn
   ) {
      for (Direction dir : Direction.values()) {
         BlockPos offset = te.getBlockPos().relative(dir);
         if (te.getLevel().getBlockEntity(offset) instanceof BlockEntityImpl impl && impl.allowsLowerLimiter()) {
            float alpha = te.getLevel().getBlockState(offset).isCollisionShapeFullBlock(te.getLevel(), offset) ? 1.0F : 0.25F;
            RenderGeneratorLimitRemover.renderGlint(
               matrixStack, iRenderTypeBuffer, this.model, dir.getStepX(), dir.getStepY(), dir.getStepZ(), combinedOverlayIn, RES, alpha
            );
            RenderGeneratorLimitRemover.renderGlint(matrixStack, iRenderTypeBuffer, this.model, 0, 0, 0, combinedOverlayIn, RES, 1.0F);
         }
      }
   }
}
