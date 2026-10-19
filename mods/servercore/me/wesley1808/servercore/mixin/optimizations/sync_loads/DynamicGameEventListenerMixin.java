package me.wesley1808.servercore.mixin.optimizations.sync_loads;

import me.wesley1808.servercore.common.utils.ChunkManager;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DynamicGameEventListener.class)
public class DynamicGameEventListenerMixin {
   @Redirect(
      method = "ifChunkExists(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/SectionPos;Ljava/util/function/Consumer;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/LevelReader;getChunk(IILnet/minecraft/world/level/chunk/status/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/ChunkAccess;"
      )
   )
   private static ChunkAccess servercore$onlyUpdateIfLoaded(LevelReader level, int x, int z, ChunkStatus status, boolean bl) {
      return ChunkManager.getChunkNow(level, x, z);
   }
}
