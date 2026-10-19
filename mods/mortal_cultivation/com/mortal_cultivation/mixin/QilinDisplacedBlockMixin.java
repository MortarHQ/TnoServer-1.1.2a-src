package com.mortal_cultivation.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mortal_cultivation.client.vfx.QilinClientEffects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderDispatcher.class)
public abstract class QilinDisplacedBlockMixin {
   @Inject(
      method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private void mortalCultivation$hideDisplacedSource(
      BlockState state,
      BlockPos pos,
      BlockAndTintGetter level,
      PoseStack pose,
      VertexConsumer consumer,
      boolean checkSides,
      RandomSource random,
      ModelData data,
      RenderType layer,
      CallbackInfo callback
   ) {
      if (QilinClientEffects.hides(pos, state)) {
         callback.cancel();
      }
   }
}
