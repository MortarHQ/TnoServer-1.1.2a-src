package terrablender.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.Climate.RTree;
import terrablender.api.Region;

public class RegionUtils {
   private static final List<Pair<ParameterPoint, ResourceKey<Biome>>> VANILLA_POINTS;
   private static Map<ResourceKey<Biome>, List<ParameterPoint>> biomeParameterPointCache = Maps.newHashMap();

   public static List<ParameterPoint> getVanillaParameterPoints(ResourceKey<Biome> biome) {
      if (biomeParameterPointCache.containsKey(biome)) {
         return biomeParameterPointCache.get(biome);
      }

      List<ParameterPoint> points = VANILLA_POINTS.stream()
         .filter(pair -> pair.getSecond() == biome)
         .map(pair -> (ParameterPoint)pair.getFirst())
         .collect(ImmutableList.toImmutableList());
      biomeParameterPointCache.put(biome, points);
      return points;
   }

   static {
      Builder<Pair<ParameterPoint, ResourceKey<Biome>>> builder = new Builder();
      new OverworldBiomeBuilder().addBiomes(pair -> builder.add(pair));
      VANILLA_POINTS = builder.build();
   }

   public record SearchTreeEntry<T>(Region region, RTree<T> tree) {
   }
}
