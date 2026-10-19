package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESShimmerLacewingVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public record ShimmerLacewingVariant(
   ResourceLocation texture, ResourceLocation textureFull, ResourceLocation glowTexture, ResourceLocation glowTextureFull, HolderSet<Biome> biomes
) {
   public static final Codec<ShimmerLacewingVariant> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(ShimmerLacewingVariant::texture),
            ResourceLocation.CODEC.fieldOf("glow_texture").forGetter(ShimmerLacewingVariant::glowTexture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(ShimmerLacewingVariant::biomes)
         )
         .apply(instance, ShimmerLacewingVariant::new)
   );

   public ShimmerLacewingVariant(ResourceLocation texture, ResourceLocation glowTexture, HolderSet<Biome> biomes) {
      this(texture, fullTextureId(texture), glowTexture, fullTextureId(glowTexture), biomes);
   }

   private static ResourceLocation fullTextureId(ResourceLocation location) {
      return location.withPath(string -> "textures/" + string + ".png");
   }

   public static Holder<ShimmerLacewingVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
      Registry<ShimmerLacewingVariant> registry = registryAccess.registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT);
      Optional<Reference<ShimmerLacewingVariant>> optional = registry.holders()
         .filter(reference -> ((ShimmerLacewingVariant)reference.value()).biomes().contains(holder))
         .findFirst()
         .or(() -> registry.getHolder(ESShimmerLacewingVariants.RIVER));
      return (Holder<ShimmerLacewingVariant>)optional.or(registry::getAny).orElseThrow();
   }
}
