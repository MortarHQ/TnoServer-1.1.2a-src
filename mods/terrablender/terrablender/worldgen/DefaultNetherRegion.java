package terrablender.worldgen;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class DefaultNetherRegion extends Region {
   public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "nether");

   public DefaultNetherRegion(int weight) {
      super(LOCATION, RegionType.NETHER, weight);
   }

   @Override
   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      this.addBiome(
         mapper,
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         0.0F,
         Biomes.NETHER_WASTES
      );
      this.addBiome(
         mapper,
         Parameter.point(0.0F),
         Parameter.point(-0.5F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         0.0F,
         Biomes.SOUL_SAND_VALLEY
      );
      this.addBiome(
         mapper,
         Parameter.point(0.4F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         0.0F,
         Biomes.CRIMSON_FOREST
      );
      this.addBiome(
         mapper,
         Parameter.point(0.0F),
         Parameter.point(0.5F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         0.375F,
         Biomes.WARPED_FOREST
      );
      this.addBiome(
         mapper,
         Parameter.point(-0.5F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         Parameter.point(0.0F),
         0.175F,
         Biomes.BASALT_DELTAS
      );
   }
}
