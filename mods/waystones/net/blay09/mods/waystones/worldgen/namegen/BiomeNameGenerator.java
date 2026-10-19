package net.blay09.mods.waystones.worldgen.namegen;

import java.util.Optional;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class BiomeNameGenerator implements NameGenerator {
   @Override
   public Optional<Component> generateName(LevelAccessor level, Waystone waystone, RandomSource rand) {
      Holder<Biome> biome = level.getBiome(waystone.getPos());
      return Optional.of(
         (Component)biome.unwrapKey()
            .map(ResourceKey::location)
            .<String>map(ResourceLocation::toString)
            .map(it -> "biome." + it.replace(":", "."))
            .<MutableComponent>map(Component::translatable)
            .orElseGet(() -> Component.literal("Corrupted Lands"))
      );
   }
}
