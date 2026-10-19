package terrablender.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class VanillaParameterOverlayBuilder {
   private Map<ParameterPoint, ResourceKey<Biome>> mappings = Maps.newHashMap();

   public void add(ParameterPoint point, ResourceKey<Biome> biome) {
      this.mappings.put(point, biome);
   }

   public List<Pair<ParameterPoint, ResourceKey<Biome>>> build() {
      Set<ParameterPoint> standalonePoints = Sets.newHashSet(this.mappings.keySet());
      SetMultimap<VanillaParameterOverlayBuilder.Adjacency, ParameterPoint> adjacentPoints = HashMultimap.create();
      this.permuteMappings((a, b) -> {
         if (!a.equals(b)) {
            for (VanillaParameterOverlayBuilder.Adjacency adjacencyx : VanillaParameterOverlayBuilder.Adjacency.values()) {
               if (adjacencyx.isAdjacent(a, b)) {
                  adjacentPoints.put(adjacencyx, a);
                  adjacentPoints.put(adjacencyx, b);
                  standalonePoints.remove(a);
                  standalonePoints.remove(b);
               }
            }
         }
      });
      Builder<Pair<ParameterPoint, ResourceKey<Biome>>> outBuilder = ImmutableList.builder();
      outBuilder.addAll(this.mappings.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList());
      if (!standalonePoints.isEmpty()) {
         outBuilder.addAll(
            standalonePoints.stream()
               .flatMap(point -> inversePoints(List.of(point)).stream().map(inversePoint -> Pair.of(inversePoint, Region.DEFERRED_PLACEHOLDER)))
               .toList()
         );
      }

      for (VanillaParameterOverlayBuilder.Adjacency adjacency : adjacentPoints.keySet()) {
         Set<ParameterPoint> values = adjacentPoints.get(adjacency);
         if (!values.isEmpty()) {
            outBuilder.addAll(inversePoints(List.copyOf(values)).stream().map(point -> Pair.of(point, Region.DEFERRED_PLACEHOLDER)).toList());
         }
      }

      return outBuilder.build();
   }

   private void permuteMappings(BiConsumer<ParameterPoint, ParameterPoint> consumer) {
      List<Entry<ParameterPoint, ResourceKey<Biome>>> entries = this.mappings.entrySet().stream().toList();
      if (entries.size() == 0) {
         throw new RuntimeException("Need at least one entry to permute!");
      }

      if (entries.size() == 1) {
         ParameterPoint point = entries.get(0).getKey();
         consumer.accept(point, point);
      } else {
         for (int i = 0; i < entries.size(); i++) {
            ParameterPoint pointA = entries.get(i).getKey();

            for (int j = i; j < entries.size(); j++) {
               consumer.accept(pointA, entries.get(j).getKey());
            }
         }
      }
   }

   private static List<ParameterPoint> inversePoints(List<ParameterPoint> values) {
      List<Parameter> temperatures = Lists.newArrayList();
      List<Parameter> humidities = Lists.newArrayList();
      List<Parameter> continentalnesses = Lists.newArrayList();
      List<Parameter> erosions = Lists.newArrayList();
      List<Parameter> depths = Lists.newArrayList();
      List<Parameter> weirdnesses = Lists.newArrayList();

      for (ParameterPoint point : values) {
         temperatures.add(point.temperature());
         humidities.add(point.humidity());
         continentalnesses.add(point.continentalness());
         erosions.add(point.erosion());
         depths.add(point.depth());
         weirdnesses.add(point.weirdness());
      }

      temperatures.sort(Comparator.comparing(Parameter::min));
      humidities.sort(Comparator.comparing(Parameter::min));
      continentalnesses.sort(Comparator.comparing(Parameter::min));
      erosions.sort(Comparator.comparing(Parameter::min));
      depths.sort(Comparator.comparing(Parameter::min));
      weirdnesses.sort(Comparator.comparing(Parameter::min));
      return new ParameterUtils.ParameterPointListBuilder()
         .temperature(inverseParameters(temperatures).toArray(Parameter[]::new))
         .humidity(inverseParameters(humidities).toArray(Parameter[]::new))
         .continentalness(inverseParameters(continentalnesses).toArray(Parameter[]::new))
         .erosion(inverseParameters(erosions).toArray(Parameter[]::new))
         .depth(inverseParameters(depths).toArray(Parameter[]::new))
         .weirdness(inverseParameters(weirdnesses).toArray(Parameter[]::new))
         .build();
   }

   private static List<Parameter> inverseParameters(List<Parameter> values) {
      if (values.isEmpty()) {
         return List.of(Parameter.span(-1.0F, 1.0F));
      }

      List<Parameter> out = Lists.newArrayList();
      float prevMax = -1.0F;

      for (Parameter value : values) {
         float min = Climate.unquantizeCoord(value.min());
         float max = Climate.unquantizeCoord(value.max());
         if (min - prevMax > 0.0F) {
            out.add(Parameter.span(prevMax, min));
         }

         prevMax = max;
      }

      if (prevMax < 1.0F) {
         out.add(Parameter.span(prevMax, 1.0F));
      }

      return out;
   }

   private enum Adjacency {
      TEMPERATURE(ParameterPoint::temperature),
      HUMIDITY(ParameterPoint::humidity),
      CONTINENTALNESS(ParameterPoint::continentalness),
      EROSION(ParameterPoint::erosion),
      DEPTH(ParameterPoint::depth),
      WEIRDNESS(ParameterPoint::weirdness);

      Function<ParameterPoint, Parameter> getter;

      Adjacency(Function<ParameterPoint, Parameter> getter) {
         this.getter = getter;
      }

      public Parameter getParameter(ParameterPoint point) {
         return this.getter.apply(point);
      }

      public boolean isAdjacent(ParameterPoint a, ParameterPoint b) {
         for (VanillaParameterOverlayBuilder.Adjacency adjacency : values()) {
            Parameter paramA = adjacency.getParameter(a);
            Parameter paramB = adjacency.getParameter(b);
            if (adjacency == this && paramA.equals(paramB)) {
               return false;
            }

            if (adjacency != this && !paramA.equals(paramB)) {
               return false;
            }
         }

         return true;
      }
   }
}
