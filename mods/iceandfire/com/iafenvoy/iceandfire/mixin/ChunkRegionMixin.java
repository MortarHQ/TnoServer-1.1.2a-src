package com.iafenvoy.iceandfire.mixin;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldGenRegion.class)
public class ChunkRegionMixin {
   @Shadow
   private Supplier<String> currentlyGenerating;

   @Inject(
      method = "ensureCanWrite(Lnet/minecraft/core/BlockPos;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;logAndPauseIfInIde(Ljava/lang/String;)V"),
      cancellable = true
   )
   private void skipLog(BlockPos position, CallbackInfoReturnable<Boolean> callback) {
      if (this.currentlyGenerating != null && this.currentlyGenerating.get().contains("iceandfire")) {
         callback.setReturnValue(false);
      }
   }
}
