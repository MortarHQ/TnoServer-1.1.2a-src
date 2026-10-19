package gardensofthedead.region;

import com.mojang.datafixers.util.Pair;
import gardensofthedead.GardensOfTheDead;
import gardensofthedead.registry.ModBiomes;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public interface GardensOfTheDeadRegion {
   int WEIGHT = 4;
   ResourceLocation ID = GardensOfTheDead.id("nether");

   default void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      this.add(mapper, Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), ModBiomes.SOULBLIGHT_FOREST);
      this.add(mapper, Climate.parameters(-0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), ModBiomes.WHISTLING_WOODS);
   }

   void add(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> var1, ParameterPoint var2, ResourceKey<Biome> var3);
}
