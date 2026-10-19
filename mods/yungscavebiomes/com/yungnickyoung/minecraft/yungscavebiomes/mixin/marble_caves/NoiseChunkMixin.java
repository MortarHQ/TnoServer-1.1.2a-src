package com.yungnickyoung.minecraft.yungscavebiomes.mixin.marble_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.world.NoiseSamplerBiomeHolder;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NoiseChunk.class)
public abstract class NoiseChunkMixin implements NoiseSamplerBiomeHolder {
}
