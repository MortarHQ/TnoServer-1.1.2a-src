package com.cerbon.bosses_of_mass_destruction.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class SimpleEntityRenderer<T extends Entity> extends EntityRenderer<T> {
   private final IRenderer<T> renderer;
   private final ITextureProvider<T> textureProvider;
   private final IRenderLight<T> brightness;

   public SimpleEntityRenderer(Context renderManager, IRenderer<T> renderer, ITextureProvider<T> textureProvider, IRenderLight<T> brightness) {
      super(renderManager);
      this.renderer = renderer;
      this.textureProvider = textureProvider;
      this.brightness = brightness;
   }

   public void render(@NotNull T entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
      this.renderer.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull T entity) {
      return this.textureProvider.getTexture(entity);
   }

   protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
      return this.brightness != null ? this.brightness.getBlockLight(entity, pos) : super.getBlockLightLevel(entity, pos);
   }
}
