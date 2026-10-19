package me.wesley1808.servercore.mixin.optimizations.ticking.chunk.broadcast;

import me.wesley1808.servercore.common.interfaces.chunk.IServerChunkCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {
   @Shadow
   @Final
   private LevelHeightAccessor levelHeightAccessor;

   @Inject(
      method = "blockChanged(Lnet/minecraft/core/BlockPos;)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/server/level/ChunkHolder;hasChangedSections:Z", opcode = 181)
   )
   private void servercore$onBlockChanged(BlockPos blockPos, CallbackInfo ci) {
      this.servercore$requiresBroadcast();
   }

   @Inject(method = "sectionLightChanged(Lnet/minecraft/world/level/LightLayer;I)V", at = @At(value = "INVOKE", target = "Ljava/util/BitSet;set(I)V"))
   private void servercore$onLightChanged(LightLayer lightLayer, int i, CallbackInfo ci) {
      this.servercore$requiresBroadcast();
   }

   @Unique
   private void servercore$requiresBroadcast() {
      if (this.levelHeightAccessor instanceof ServerLevel serverLevel) {
         ((IServerChunkCache)serverLevel.getChunkSource()).servercore$requiresBroadcast((ChunkHolder)this);
      }
   }
}
