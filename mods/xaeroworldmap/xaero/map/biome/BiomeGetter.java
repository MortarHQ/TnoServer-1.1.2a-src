package xaero.map.biome;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class BiomeGetter {
   public final ResourceKey<Biome> PLACEHOLDER_BIOME;
   public final ResourceKey<Biome> UNKNOWN_BIOME = ResourceKey.create(Registries.BIOME, ResourceLocation.parse("xaeroworldmap:unknown_biome"));

   public BiomeGetter() {
      this.PLACEHOLDER_BIOME = ResourceKey.create(Registries.BIOME, ResourceLocation.parse("xaeroworldmap:placeholder_biome"));
   }

   public ResourceKey<Biome> getBiome(Level world, BlockPos pos, Registry<Biome> biomeRegistry) {
      Holder<Biome> biomeHolder = world.getBiome(pos);
      Biome biome = biomeHolder == null ? null : (Biome)biomeHolder.value();
      return biomeRegistry.getResourceKey(biome).orElse(this.UNKNOWN_BIOME);
   }
}
