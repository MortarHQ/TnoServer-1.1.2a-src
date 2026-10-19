package com.mortal_cultivation.mixin;

import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.ByteBufferBuilder.Result;
import com.mortal_cultivation.client.vfx.QilinChunkUploadGuard;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionRenderDispatcher.class)
public abstract class QilinChunkUploadMixin {
   @Shadow(remap = false)
   @Final
   private Queue<Runnable> toUpload;
   @Shadow(remap = false)
   private volatile boolean closed;

   @Inject(method = "uploadSectionLayer", at = @At("HEAD"), cancellable = true, remap = false)
   private void mortalCultivation$guardMeshUpload(MeshData mesh, VertexBuffer buffer, CallbackInfoReturnable<CompletableFuture<Void>> callback) {
      AtomicBoolean cancelled = QilinChunkUploadGuard.current();
      if (cancelled != null) {
         if (this.closed) {
            mesh.close();
            callback.setReturnValue(CompletableFuture.completedFuture(null));
         } else {
            callback.setReturnValue(CompletableFuture.runAsync(() -> {
               if (!cancelled.get() && !this.closed && !buffer.isInvalid()) {
                  buffer.bind();
                  buffer.upload(mesh);
                  VertexBuffer.unbind();
               } else {
                  mesh.close();
               }
            }, this.toUpload::add));
         }
      }
   }

   @Inject(method = "uploadSectionIndexBuffer", at = @At("HEAD"), cancellable = true, remap = false)
   private void mortalCultivation$guardIndexUpload(Result indices, VertexBuffer buffer, CallbackInfoReturnable<CompletableFuture<Void>> callback) {
      AtomicBoolean cancelled = QilinChunkUploadGuard.current();
      if (cancelled != null) {
         if (this.closed) {
            indices.close();
            callback.setReturnValue(CompletableFuture.completedFuture(null));
         } else {
            callback.setReturnValue(CompletableFuture.runAsync(() -> {
               if (!cancelled.get() && !this.closed && !buffer.isInvalid()) {
                  buffer.bind();
                  buffer.uploadIndexBuffer(indices);
                  VertexBuffer.unbind();
               } else {
                  indices.close();
               }
            }, this.toUpload::add));
         }
      }
   }
}
