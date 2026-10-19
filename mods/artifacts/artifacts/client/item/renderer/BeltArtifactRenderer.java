package artifacts.client.item.renderer;

import artifacts.client.item.model.BeltModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BeltArtifactRenderer implements ArtifactRenderer {
   private final ResourceLocation texture;
   private final BeltModel model;

   public BeltArtifactRenderer(String name, BeltModel model) {
      this.texture = ArtifactRenderer.getTexturePath(name);
      this.model = model;
   }

   protected ResourceLocation getTexture() {
      return this.texture;
   }

   protected BeltModel getModel() {
      return this.model;
   }

   @Override
   public void render(
      ItemStack stack,
      LivingEntity entity,
      int slotIndex,
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      BeltModel model = this.getModel();
      model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
      model.setCharmPosition(slotIndex);
      ArtifactRenderer.followBodyRotations(entity, model);
      this.render(poseStack, multiBufferSource, light, stack.hasFoil());
   }

   protected void render(PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasFoil) {
      RenderType renderType = this.model.renderType(this.getTexture());
      VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
      this.model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, -1);
   }
}
