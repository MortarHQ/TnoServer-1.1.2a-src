package com.tensura_tno.mixin.client;

import io.github.manasmods.tensura.block.LabyrinthPortal;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LabyrinthPortal.class, remap = false)
public abstract class LabyrinthPortalRenderMixin {
   @Inject(method = "getRenderShape", at = @At("HEAD"), cancellable = true)
   private void tensuraTno$showLabyrinthPortalModel(BlockState state, CallbackInfoReturnable<RenderShape> cir) {
      cir.setReturnValue(RenderShape.MODEL);
   }
}
