package com.mortal_cultivation.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import com.mortal_cultivation.client.model.AncientSteleRuneGeoModel;
import com.mortal_cultivation.item.SpiritGlassesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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

public final class AncientSteleRuneLayer extends GeoRenderLayer<AncientSteleBlockEntity> {
   private final AncientSteleRuneGeoModel runeModel = new AncientSteleRuneGeoModel();
   private long animationPassId;

   public AncientSteleRuneLayer(GeoRenderer<AncientSteleBlockEntity> renderer) {
      super(renderer);
   }

   public GeoModel<AncientSteleBlockEntity> getGeoModel() {
      return this.runeModel;
   }

   public void render(
      PoseStack poseStack,
      AncientSteleBlockEntity stele,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      Player player = Minecraft.getInstance().player;
      if (stele.shouldRenderRunes() && canSeeRunes(player)) {
         BakedGeoModel runeBakedModel = this.getDefaultBakedModel(stele);
         this.applyRuneAnimation(stele, partialTick);
         ResourceLocation texture = this.runeModel.getTextureResource(stele, this.getRenderer());
         RenderType runeRenderType = RenderType.entityTranslucentEmissive(texture, false);
         float alpha = getRuneAlpha(stele, partialTick);
         int color = Color.ofRGBA(1.0F, 0.84F, 0.2F, alpha).argbInt();
         this.getRenderer()
            .reRender(
               runeBakedModel,
               poseStack,
               bufferSource,
               stele,
               runeRenderType,
               bufferSource.getBuffer(runeRenderType),
               partialTick,
               15728880,
               packedOverlay,
               color
            );
      }
   }

   private void applyRuneAnimation(AncientSteleBlockEntity stele, float partialTick) {
      AnimationState<AncientSteleBlockEntity> animationState = new AnimationState(stele, 0.0F, 0.0F, partialTick, false);
      long instanceId = ++this.animationPassId;
      animationState.setData(DataTickets.TICK, stele.getTick(stele));
      animationState.setData(DataTickets.BLOCK_ENTITY, stele);
      this.runeModel.addAdditionalStateData(stele, instanceId, animationState::setData);
      this.runeModel.handleAnimations(stele, instanceId, animationState, partialTick);
   }

   private static float getRuneAlpha(AncientSteleBlockEntity stele, float partialTick) {
      long gameTime = stele.getLevel() == null ? 0L : stele.getLevel().getGameTime();
      long phaseOffset = Math.floorMod(stele.getBlockPos().asLong(), 80L);
      return 0.5F + 0.35F * Mth.sin((float)(((float)gameTime + partialTick + (float)phaseOffset) * 0.08));
   }

   private static boolean canSeeRunes(@Nullable Player player) {
      return player != null && SpiritGlassesItem.isEquipped(player);
   }
}
