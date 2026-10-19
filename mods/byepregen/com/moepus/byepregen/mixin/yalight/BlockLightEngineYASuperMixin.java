package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.yalight.YABlockLightEngine;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.lighting.BlockLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockLightEngine.class, remap = false)
public abstract class BlockLightEngineYASuperMixin {
   @Unique
   private static final MutableBlockPos byepregen$dummyMutablePos = new MutableBlockPos();

   @Redirect(
      method = "<init>(Lnet/minecraft/world/level/chunk/LightChunkGetter;Lnet/minecraft/world/level/lighting/BlockLightSectionStorage;)V",
      at = @At(value = "NEW", target = "()Lnet/minecraft/core/BlockPos$MutableBlockPos;")
   )
   private MutableBlockPos byepregen$skipMutablePos() {
      return this instanceof YABlockLightEngine ? byepregen$dummyMutablePos : new MutableBlockPos();
   }
}
