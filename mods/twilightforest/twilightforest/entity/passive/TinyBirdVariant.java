package twilightforest.entity.passive;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import twilightforest.TFRegistries;
import twilightforest.init.custom.TinyBirdVariants;

public record TinyBirdVariant(ResourceLocation texture, Optional<HolderSet<Biome>> spawnBiomes) {
   public static final Codec<TinyBirdVariant> DIRECT_CODEC = RecordCodecBuilder.create(
      p_332779_ -> p_332779_.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(TinyBirdVariant::texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(TinyBirdVariant::spawnBiomes)
         )
         .apply(p_332779_, TinyBirdVariant::new)
   );
   public static final Codec<Holder<TinyBirdVariant>> CODEC = RegistryFileCodec.create(TFRegistries.Keys.TINY_BIRD_VARIANT, DIRECT_CODEC);

   public TinyBirdVariant(ResourceLocation texture) {
      this(texture, Optional.empty());
   }

   public static Holder<TinyBirdVariant> getVariant(RegistryAccess access, Holder<Biome> currentBiome, RandomSource random) {
      Registry<TinyBirdVariant> registry = access.registryOrThrow(TFRegistries.Keys.TINY_BIRD_VARIANT);
      List<Reference<TinyBirdVariant>> validBirds = registry.holders()
         .filter(
            variant -> ((TinyBirdVariant)variant.value()).spawnBiomes().isEmpty()
               || ((TinyBirdVariant)variant.value()).spawnBiomes().get().contains(currentBiome)
         )
         .toList();
      return (Holder<TinyBirdVariant>)(validBirds.isEmpty()
         ? registry.getHolderOrThrow(TinyBirdVariants.RED)
         : (Holder)validBirds.get(random.nextInt(validBirds.size())));
   }
}
