package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor.ABGR32;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SimpleLivingGeoRenderer<T extends Entity & GeoAnimatable> extends GeoEntityRenderer<T> {
   private final IRenderLight<T> brightness;
   private final IBoneLight iBoneLight;
   private final IRenderer<T> renderer;
   private final IRendererWithModel renderWithModel;
   private final IOverlayOverride overlayOverride;
   private final boolean deathRotation;

   public SimpleLivingGeoRenderer(
      Context renderManager,
      GeoModel<T> model,
      IRenderLight<T> brightness,
      IBoneLight iBoneLight,
      IRenderer<T> renderer,
      IRendererWithModel renderWithModel,
      IOverlayOverride overlayOverride,
      boolean deathRotation
   ) {
      super(renderManager, model);
      this.brightness = brightness;
      this.iBoneLight = iBoneLight;
      this.renderer = renderer;
      this.renderWithModel = renderWithModel;
      this.overlayOverride = overlayOverride;
      this.deathRotation = deathRotation;
   }

   protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
      return this.brightness != null ? this.brightness.getBlockLight(entity, pos) : super.getBlockLightLevel(entity, pos);
   }

   public void renderRecursively(
      PoseStack poseStack,
      T animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      int packedLight1 = this.iBoneLight != null ? this.iBoneLight.getLightForBone(bone, packedLight) : packedLight;
      int red = ABGR32.red(colour);
      int green = ABGR32.green(colour);
      int blue = ABGR32.blue(colour);
      int alpha = ABGR32.alpha(colour);
      Vector4f color = new Vector4f(red, green, blue, alpha).mul(0.003921569F);
      Vector4f newColor = (this.iBoneLight != null ? this.iBoneLight.getColorForBone(bone, color) : color).mul(255.0F);
      super.renderRecursively(
         poseStack,
         animatable,
         bone,
         renderType,
         bufferSource,
         buffer,
         isReRender,
         partialTick,
         packedLight1,
         packedOverlay,
         ABGR32.color((int)newColor.w, (int)newColor.x, (int)newColor.y, (int)newColor.z)
      );
   }

   public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (this.renderer != null) {
         this.renderer.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }

      poseStack.pushPose();
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
   }

   public void actuallyRender(
      PoseStack poseStack,
      T animatable,
      BakedGeoModel model,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
      int packetOverlay = this.overlayOverride != null ? this.overlayOverride.getOverlay() : packedOverlay;
      if (this.renderWithModel != null) {
         this.renderWithModel
            .render(
               model,
               partialTick,
               poseStack,
               bufferSource,
               packedLight,
               packetOverlay,
               ABGR32.red(colour),
               ABGR32.green(colour),
               ABGR32.blue(colour),
               ABGR32.alpha(colour)
            );
      }
   }

   public int getPackedOverlay(T animatable, float u, float partialTick) {
      return this.overlayOverride != null ? this.overlayOverride.getOverlay() : super.getPackedOverlay(animatable, u, partialTick);
   }

   protected float getDeathMaxRotation(T animatable) {
      return this.deathRotation ? 90.0F : 0.0F;
   }
}
