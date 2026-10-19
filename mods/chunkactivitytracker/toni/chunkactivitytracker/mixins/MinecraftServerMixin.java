package toni.chunkactivitytracker.mixins;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.chunkactivitytracker.data.ChunkActivityMap;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
   @Inject(method = "saveEverything(ZZZ)Z", at = @At("TAIL"))
   private void endSave(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
      if (ChunkActivityMap.instances != null) {
         ChunkActivityMap.instances.values().forEach(ChunkActivityMap::save);
      }
   }
}
