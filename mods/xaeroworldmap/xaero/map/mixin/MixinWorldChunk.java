package xaero.map.mixin;

import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelChunk.class)
public class MixinWorldChunk {
   public boolean xaero_wm_chunkClean = false;
}
