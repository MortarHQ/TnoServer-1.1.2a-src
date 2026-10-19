package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import com.iafenvoy.iceandfire.render.model.DeathWormModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DeathWormEntityRenderer extends MobRenderer<DeathWormEntity, DeathWormModel> {
   public static final ResourceLocation TEXTURE_RED = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/deathworm/deathworm_red.png");
   public static final ResourceLocation TEXTURE_WHITE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/deathworm/deathworm_white.png");
   public static final ResourceLocation TEXTURE_YELLOW = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/entity/deathworm/deathworm_yellow.png");

   public DeathWormEntityRenderer(Context context) {
      super(context, new DeathWormModel(), 0.0F);
   }

   protected void scale(DeathWormEntity entity, PoseStack matrixStackIn, float partialTickTime) {
      this.shadowRadius = entity.getAgeScale() / 3.0F;
      matrixStackIn.scale(entity.getAgeScale(), entity.getAgeScale(), entity.getAgeScale());
   }

   protected int getBlockLight(DeathWormEntity entityIn, BlockPos partialTicks) {
      return entityIn.isOnFire() ? 15 : entityIn.getWormBrightness(false);
   }

   protected int getSkyLight(DeathWormEntity entity, BlockPos pos) {
      return entity.getWormBrightness(true);
   }

   public ResourceLocation getTexture(DeathWormEntity entity) {
      return entity.getVariant() == 2 ? TEXTURE_WHITE : (entity.getVariant() == 1 ? TEXTURE_RED : TEXTURE_YELLOW);
   }
}
