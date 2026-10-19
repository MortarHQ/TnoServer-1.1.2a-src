package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESEntVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public record EntVariant(Holder<Item> leaves, ResourceLocation texture, ResourceLocation textureFull, HolderSet<Biome> biomes) {
   public static final Codec<EntVariant> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("leaves").forGetter(EntVariant::leaves),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(EntVariant::texture),
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(EntVariant::biomes)
         )
         .apply(instance, EntVariant::new)
   );

   public EntVariant(Holder<Item> leaves, ResourceLocation texture, HolderSet<Biome> biomes) {
      this(leaves, texture, fullTextureId(texture), biomes);
   }

   private static ResourceLocation fullTextureId(ResourceLocation location) {
      return location.withPath(string -> "textures/" + string + ".png");
   }

   public static Holder<EntVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
      Registry<EntVariant> registry = registryAccess.registryOrThrow(ESRegistries.ENT_VARIANT);
      Optional<Reference<EntVariant>> optional = registry.holders()
         .filter(reference -> ((EntVariant)reference.value()).biomes().contains(holder))
         .findFirst()
         .or(() -> registry.getHolder(ESEntVariants.LUNAR));
      return (Holder<EntVariant>)optional.or(registry::getAny).orElseThrow();
   }
}
