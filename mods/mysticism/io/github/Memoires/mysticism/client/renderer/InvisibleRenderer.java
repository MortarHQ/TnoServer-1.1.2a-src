package io.github.Memoires.mysticism.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class InvisibleRenderer<T extends Entity> extends EntityRenderer<T> {
   public InvisibleRenderer(Context context) {
      super(context);
   }

   public void render(T entity, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
   }

   public ResourceLocation getTextureLocation(T entity) {
      return null;
   }
}
