package com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor;

import java.util.List;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NoiseChunk.class)
public interface NoiseChunkAccessor {
   @Invoker("cachedClimateSampler")
   Sampler callCachedClimateSampler(NoiseRouter var1, List<ParameterPoint> var2);
}
