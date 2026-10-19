package terrablender.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class ModifiedVanillaOverworldBuilder {
   private Map<ResourceKey<Biome>, ResourceKey<Biome>> originalBiomeMappings = Maps.newHashMap();
   private Map<ParameterPoint, ResourceKey<Biome>> parameterBiomeMappings = Maps.newHashMap();
   private Map<ParameterPoint, ParameterPoint> parameterMappings = Maps.newHashMap();
   private List<ParameterPoint> parametersToRemove = Lists.newArrayList();
   private final OverworldBiomeBuilder biomeBuilder = new OverworldBiomeBuilder();

   public void replaceBiome(ResourceKey<Biome> original, ResourceKey<Biome> replacement) {
      this.originalBiomeMappings.put(original, replacement);
   }

   public void replaceBiome(ParameterPoint point, ResourceKey<Biome> biome) {
      this.parameterBiomeMappings.put(point, biome);
   }

   public void replaceParameter(ParameterPoint original, ParameterPoint replacement) {
      this.parameterMappings.put(original, replacement);
   }

   public void removeParameter(ParameterPoint parameter) {
      this.parametersToRemove.add(parameter);
   }

   public List<Pair<ParameterPoint, ResourceKey<Biome>>> build() {
      Builder<Pair<ParameterPoint, ResourceKey<Biome>>> builder = new Builder();
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper = pair -> {
         ParameterPoint parameters = (ParameterPoint)pair.getFirst();
         ResourceKey<Biome> biome = (ResourceKey<Biome>)pair.getSecond();
         if (!this.parametersToRemove.contains(parameters)) {
            if (this.originalBiomeMappings.containsKey(biome)) {
               biome = this.originalBiomeMappings.get(biome);
            } else if (this.parameterBiomeMappings.containsKey(parameters)) {
               biome = this.parameterBiomeMappings.get(parameters);
            }

            if (this.parameterMappings.containsKey(parameters)) {
               parameters = this.parameterMappings.get(parameters);
            }

            builder.add(Pair.of(parameters, biome));
         }
      };
      this.biomeBuilder.addBiomes(mapper);
      return builder.build();
   }
}
