package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.InvokerEntityModel;
import fuzs.illagerinvasion.client.render.entity.layers.InvokerGoldLayer;
import fuzs.illagerinvasion.client.render.entity.layers.InvokerShieldLayer;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class InvokerRender extends MobRenderer<Invoker, InvokerEntityModel<Invoker>> {
   private static final ResourceLocation INVOKER_LOCATION = IllagerInvasion.id("textures/entity/invoker.png");

   public InvokerRender(Context context) {
      super(context, new InvokerEntityModel(context.bakeLayer(ClientModRegistry.CAPED_ILLAGER)), 0.0F);
      this.addLayer(new InvokerGoldLayer(this));
      this.addLayer(new InvokerShieldLayer(this, context.getModelSet()));
      ((InvokerEntityModel)this.model).getHat().visible = true;
   }

   public void render(Invoker invoker, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
      float z = 0.076F;
      float q = Mth.sin(invoker.tickCount * z) * 1.5F * (float) (Math.PI / 180.0);
      matrixStack.translate(0.0, 0.3 + q, 0.0);
      matrixStack.scale(0.95F, 0.95F, 0.95F);
      super.render(invoker, f, g, matrixStack, vertexConsumerProvider, i);
   }

   public ResourceLocation getTextureLocation(Invoker entity) {
      return INVOKER_LOCATION;
   }
}
