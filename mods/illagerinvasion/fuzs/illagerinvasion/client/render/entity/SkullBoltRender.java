package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SkullBoltRender extends EntityRenderer<SkullBolt> {
   private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/skullbolt.png");
   private final SkullModel model;

   public SkullBoltRender(Context context) {
      super(context);
      this.model = new SkullModel(context.bakeLayer(ModelLayers.WITHER_SKULL));
   }

   protected int getBlockLightLevel(SkullBolt skullbolt, BlockPos pos) {
      return 15;
   }

   public void render(SkullBolt skullbolt, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
      matrixStack.pushPose();
      matrixStack.scale(-1.0F, -1.0F, 1.0F);
      float h = Mth.rotLerp(skullbolt.yRotO, skullbolt.getYRot(), g);
      float j = Mth.lerp(g, skullbolt.xRotO, skullbolt.getXRot());
      VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(this.getTextureLocation(skullbolt)));
      this.model.setupAnim(0.0F, h, j);
      this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
      matrixStack.popPose();
      super.render(skullbolt, f, g, matrixStack, vertexConsumerProvider, i);
   }

   public ResourceLocation getTextureLocation(SkullBolt skullbolt) {
      return TEXTURE_LOCATION;
   }
}
