package mcjty.incontrol.tools.cache;

import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record StructureCacheEntry(@Nonnull String structure, ResourceKey<Level> dimension, long chunkpos) {
}
