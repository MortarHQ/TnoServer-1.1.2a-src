package com.tensura_tno.mixin.client;

import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "net.unusual.block_factorys_bosses.entity.decoration.CageEntity", remap = false)
public class BossesCageArclightTickFixMixin {
   @Redirect(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/BlockAttachedEntity;tick()V"),
      remap = true,
      require = 0
   )
   private void tensuraTno$skipArclightHangingTick(BlockAttachedEntity entity) {
   }
}
