package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.InvokerFangsModel;
import fuzs.illagerinvasion.world.entity.monster.InvokerFangs;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class InvokerFangsRenderer extends EntityRenderer<InvokerFangs> {
   private static final ResourceLocation INVOKER_FANGS_LOCATION = IllagerInvasion.id("textures/entity/invoker_fangs.png");
   private final InvokerFangsModel<InvokerFangs> model;

   public InvokerFangsRenderer(Context context) {
      super(context);
      this.model = new InvokerFangsModel<>(context.bakeLayer(ClientModRegistry.INVOKER_FANGS));
   }

   public void render(InvokerFangs invokerFangs, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
      float animationProgress = invokerFangs.getAnimationProgress(g);
      if (animationProgress != 0.0F) {
         float j = 2.0F;
         if (animationProgress > 0.9F) {
            j = (float)(j * ((1.0 - animationProgress) / 0.1F));
         }

         matrixStack.pushPose();
         matrixStack.scale(1.15F, 1.15F, 1.15F);
         matrixStack.mulPose(Axis.YP.rotationDegrees(90.0F - invokerFangs.getYRot()));
         matrixStack.scale(-j, -j, j);
         float k = 0.03125F;
         matrixStack.translate(0.0, -0.626F, 0.0);
         matrixStack.scale(0.5F, 0.5F, 0.5F);
         this.model.setupAnim(invokerFangs, animationProgress, 0.0F, 0.0F, invokerFangs.getYRot(), invokerFangs.getXRot());
         VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(this.getTextureLocation(invokerFangs)));
         this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
         matrixStack.popPose();
         super.render(invokerFangs, f, g, matrixStack, vertexConsumerProvider, i);
      }
   }

   public ResourceLocation getTextureLocation(InvokerFangs InvokerFangs) {
      return INVOKER_FANGS_LOCATION;
   }
}
