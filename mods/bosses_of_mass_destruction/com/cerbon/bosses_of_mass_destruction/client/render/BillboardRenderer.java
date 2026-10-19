package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;

public class BillboardRenderer<T extends Entity> implements IRenderer<T> {
   private final EntityRenderDispatcher dispatcher;
   private final RenderType renderType;
   private final BillboardRenderer.ScaleFunction<T> scale;

   public BillboardRenderer(EntityRenderDispatcher dispatcher, RenderType renderLayer, BillboardRenderer.ScaleFunction<T> scale) {
      this.dispatcher = dispatcher;
      this.renderType = renderLayer;
      this.scale = scale;
   }

   @Override
   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float scaleValue = this.scale.apply(entity);
      poseStack.pushPose();
      poseStack.scale(scaleValue, scaleValue, scaleValue);
      VanillaCopies.renderBillboard(poseStack, buffer, light, this.dispatcher, this.renderType, new Quaternionf());
      poseStack.popPose();
   }

   public interface ScaleFunction<T> {
      float apply(T var1);
   }
}
