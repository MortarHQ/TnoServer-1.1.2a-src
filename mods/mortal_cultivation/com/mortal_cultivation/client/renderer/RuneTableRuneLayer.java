package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import com.mortal_cultivation.client.model.RuneTableRuneGeoModel;
import com.mortal_cultivation.item.SpiritGlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public final class RuneTableRuneLayer extends GeoRenderLayer<RuneTableBlockEntity> {
   private static final int STREAM_COUNT = 4;
   private static final float STREAM_CYCLE_TICKS = 100.0F;
   private static final float MODEL_CENTER_Y = 0.5625F;
   private final RuneTableRuneGeoModel runeModel = new RuneTableRuneGeoModel();
   private long animationPassId;

   public RuneTableRuneLayer(GeoRenderer<RuneTableBlockEntity> renderer) {
      super(renderer);
   }

   public GeoModel<RuneTableBlockEntity> getGeoModel() {
      return this.runeModel;
   }

   public void render(
      PoseStack poseStack,
      RuneTableBlockEntity table,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      Player player = Minecraft.getInstance().player;
      if (table.isAbsorbingStoredRuneOrb() && player != null && SpiritGlassesItem.isEquipped(player)) {
         float time = (float)(table.getLevel() == null ? 0L : table.getLevel().getGameTime()) + partialTick;
         float liftProgress = smoothStep(table.getLiftProgress(partialTick));
         BakedGeoModel runeBakedModel = this.getDefaultBakedModel(table);
         this.resetSharedRuneModel(table, partialTick);
         ResourceLocation texture = this.runeModel.getTextureResource(table, this.getRenderer());
         RenderType runeType = RenderType.entityTranslucentEmissive(texture, false);

         for (int index = 0; index < 4; index++) {
            float phase = Mth.frac((time + index * 25.0F) / 100.0F);
            float visibility = Mth.sin((float) Math.PI * phase);
            float scale = Mth.lerp(phase, 0.28F, 0.15F);
            float angle = time * 1.5F + index * 90.0F + phase * 180.0F;
            float radius = (1.0F - phase) * 0.06F;
            float angleRadians = angle * (float) (Math.PI / 180.0);
            float x = Mth.cos(angleRadians) * radius;
            float z = Mth.sin(angleRadians) * radius;
            float centerY = Mth.lerp(phase, 1.45F, 1.03F);
            int color = Color.ofRGBA(1.0F, 0.84F, 0.2F, visibility * liftProgress * 0.72F).argbInt();
            poseStack.pushPose();
            poseStack.translate(x, centerY - 0.5625F * scale, z);
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
            poseStack.scale(scale, scale, scale);
            this.getRenderer()
               .reRender(
                  runeBakedModel,
                  poseStack,
                  bufferSource,
                  table,
                  runeType,
                  bufferSource.getBuffer(runeType),
                  partialTick,
                  15728880,
                  OverlayTexture.NO_OVERLAY,
                  color
               );
            poseStack.popPose();
         }
      }
   }

   private void resetSharedRuneModel(RuneTableBlockEntity table, float partialTick) {
      AnimationState<RuneTableBlockEntity> animationState = new AnimationState(table, 0.0F, 0.0F, partialTick, false);
      long instanceId = ++this.animationPassId;
      animationState.setData(DataTickets.TICK, table.getTick(table));
      animationState.setData(DataTickets.BLOCK_ENTITY, table);
      this.runeModel.addAdditionalStateData(table, instanceId, animationState::setData);
      this.runeModel.handleAnimations(table, instanceId, animationState, partialTick);
   }

   private static float smoothStep(float progress) {
      float clamped = Mth.clamp(progress, 0.0F, 1.0F);
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }
}
