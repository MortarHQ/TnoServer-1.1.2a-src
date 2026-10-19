package terrablender.mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import net.minecraft.world.level.levelgen.DensityFunction.SinglePointContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrablender.api.EndBiomeRegistry;
import terrablender.core.TerraBlender;
import terrablender.worldgen.IExtendedTheEndBiomeSource;
import terrablender.worldgen.noise.Area;
import terrablender.worldgen.noise.LayeredNoiseUtil;

@Mixin(TheEndBiomeSource.class)
public class MixinTheEndBiomeSource implements IExtendedTheEndBiomeSource {
   @Shadow
   @Final
   private Holder<Biome> end;
   @Unique
   private boolean tbInitialized = false;
   @Unique
   private Registry<Biome> biomeRegistry;
   @Unique
   private Set<Holder<Biome>> tbPossibleBiomes;
   @Unique
   private Area highlandsArea;
   @Unique
   private Area midlandsArea;
   @Unique
   private Area edgeArea;
   @Unique
   private Area islandsArea;

   @Override
   public void initializeForTerraBlender(RegistryAccess registryAccess, long seed) {
      this.biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
      List<Wrapper<ResourceKey<Biome>>> highlands = EndBiomeRegistry.getHighlandsBiomes();
      List<Wrapper<ResourceKey<Biome>>> midlands = EndBiomeRegistry.getMidlandsBiomes();
      List<Wrapper<ResourceKey<Biome>>> edge = EndBiomeRegistry.getEdgeBiomes();
      List<Wrapper<ResourceKey<Biome>>> islands = EndBiomeRegistry.getIslandBiomes();
      Builder<ResourceKey<Biome>> builder = ImmutableSet.builder();
      builder.addAll(highlands.stream().map(Wrapper::data).toList());
      builder.addAll(midlands.stream().map(Wrapper::data).toList());
      builder.addAll(edge.stream().map(Wrapper::data).toList());
      builder.addAll(islands.stream().map(Wrapper::data).toList());
      builder.add(Biomes.THE_END);
      Set<ResourceKey<Biome>> allBiomes = builder.build();
      allBiomes.forEach(key -> {
         if (!this.biomeRegistry.containsKey(key)) {
            throw new RuntimeException("Biome " + key + " has not been registered!");
         }
      });
      this.tbPossibleBiomes = allBiomes.stream().<Holder<Biome>>map(this.biomeRegistry::getHolderOrThrow).collect(Collectors.toSet());
      this.highlandsArea = LayeredNoiseUtil.biomeArea(registryAccess, seed, TerraBlender.CONFIG.endHighlandsBiomeSize, highlands);
      this.midlandsArea = LayeredNoiseUtil.biomeArea(registryAccess, seed, TerraBlender.CONFIG.endMidlandsBiomeSize, midlands);
      this.edgeArea = LayeredNoiseUtil.biomeArea(registryAccess, seed, TerraBlender.CONFIG.endEdgeBiomeSize, edge);
      this.islandsArea = LayeredNoiseUtil.biomeArea(registryAccess, seed, TerraBlender.CONFIG.endIslandBiomeSize, edge);
      this.tbInitialized = true;
   }

   @Inject(method = "collectPossibleBiomes", at = @At("RETURN"), cancellable = true)
   protected void onCollectPossibleBiomes(CallbackInfoReturnable<Stream<Holder<Biome>>> cir) {
      if (this.tbInitialized) {
         Builder<Holder<Biome>> builder = ImmutableSet.builder();
         builder.addAll(((Stream)cir.getReturnValue()).collect(Collectors.toSet()));
         builder.addAll(this.tbPossibleBiomes);
         cir.setReturnValue(builder.build().stream());
      }
   }

   @Inject(method = "getNoiseBiome", at = @At("HEAD"), cancellable = true)
   public void onGetNoiseBiome(int x, int y, int z, Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
      if (this.tbInitialized) {
         int blockX = QuartPos.toBlock(x);
         int blockY = QuartPos.toBlock(y);
         int blockZ = QuartPos.toBlock(z);
         int sectionX = SectionPos.blockToSectionCoord(blockX);
         int sectionZ = SectionPos.blockToSectionCoord(blockZ);
         if ((long)sectionX * sectionX + (long)sectionZ * sectionZ <= 4096L) {
            cir.setReturnValue(this.end);
         } else {
            double heightNoise = sampler.erosion().compute(new SinglePointContext(blockX, blockY, blockZ));
            if (heightNoise > 0.25) {
               cir.setReturnValue(this.getBiomeHolder(this.highlandsArea.get(x, z)));
            } else if (heightNoise >= -0.0625) {
               cir.setReturnValue(this.getBiomeHolder(this.midlandsArea.get(x, z)));
            } else {
               cir.setReturnValue(heightNoise < -0.21875 ? this.getBiomeHolder(this.islandsArea.get(x, z)) : this.getBiomeHolder(this.edgeArea.get(x, z)));
            }
         }
      }
   }

   @Unique
   private Holder<Biome> getBiomeHolder(int id) {
      return (Holder<Biome>)this.biomeRegistry.getHolder(id).orElseThrow();
   }
}
