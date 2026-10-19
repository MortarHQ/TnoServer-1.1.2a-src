package com.mortal_cultivation.mixin;

import com.mortal_cultivation.client.vfx.QilinChunkUploadGuard;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   targets = {
         "net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection$RebuildTask",
         "net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection$ResortTransparencyTask"
   }
)
public abstract class QilinCompileTaskContextMixin {
   @Inject(method = "doTask", at = @At("HEAD"), remap = false)
   private void mortalCultivation$beginCompile(SectionBufferBuilderPack buffers, CallbackInfoReturnable<CompletableFuture<?>> callback) {
      QilinChunkUploadGuard.enter(((QilinCompileTaskAccessor)this).mortalCultivation$getCancellationFlag());
   }

   @Inject(method = "doTask", at = @At("RETURN"), remap = false)
   private void mortalCultivation$endCompile(SectionBufferBuilderPack buffers, CallbackInfoReturnable<CompletableFuture<?>> callback) {
      QilinChunkUploadGuard.leave();
   }
}
