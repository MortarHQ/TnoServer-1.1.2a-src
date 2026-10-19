package terrablender.api;

import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

public class ParameterUtils {
   public enum Continentalness {
      MUSHROOM_FIELDS(Parameter.span(-1.2F, -1.05F)),
      DEEP_OCEAN(Parameter.span(-1.05F, -0.455F)),
      OCEAN(Parameter.span(-0.455F, -0.19F)),
      COAST(Parameter.span(-0.19F, -0.11F)),
      NEAR_INLAND(Parameter.span(-0.11F, 0.03F)),
      MID_INLAND(Parameter.span(0.03F, 0.3F)),
      FAR_INLAND(Parameter.span(0.3F, 1.0F)),
      INLAND(Parameter.span(-0.11F, 0.55F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Continentalness(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Continentalness min, ParameterUtils.Continentalness max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }

   public enum Depth {
      SURFACE(Parameter.point(0.0F)),
      UNDERGROUND(Parameter.span(0.2F, 0.9F)),
      FLOOR(Parameter.point(1.0F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Depth(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Depth min, ParameterUtils.Depth max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }

   public enum Erosion {
      EROSION_0(Parameter.span(-1.0F, -0.78F)),
      EROSION_1(Parameter.span(-0.78F, -0.375F)),
      EROSION_2(Parameter.span(-0.375F, -0.2225F)),
      EROSION_3(Parameter.span(-0.2225F, 0.05F)),
      EROSION_4(Parameter.span(0.05F, 0.45F)),
      EROSION_5(Parameter.span(0.45F, 0.55F)),
      EROSION_6(Parameter.span(0.55F, 1.0F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Erosion(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Erosion min, ParameterUtils.Erosion max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }

   public enum Humidity {
      ARID(Parameter.span(-1.0F, -0.35F)),
      DRY(Parameter.span(-0.35F, -0.1F)),
      NEUTRAL(Parameter.span(-0.1F, 0.1F)),
      WET(Parameter.span(0.1F, 0.3F)),
      HUMID(Parameter.span(0.3F, 1.0F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Humidity(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Humidity min, ParameterUtils.Humidity max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }

   public static class ParameterPointListBuilder {
      private List<Parameter> temperatures = Lists.newArrayList();
      private List<Parameter> humidities = Lists.newArrayList();
      private List<Parameter> continentalnesses = Lists.newArrayList();
      private List<Parameter> erosions = Lists.newArrayList();
      private List<Parameter> depths = Lists.newArrayList();
      private List<Parameter> weirdnesses = Lists.newArrayList();
      private List<Long> offsets = Lists.newArrayList();

      public ParameterUtils.ParameterPointListBuilder temperature(Parameter... values) {
         this.temperatures.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder temperature(ParameterUtils.Temperature... values) {
         this.temperatures.addAll(Arrays.asList(values).stream().map(ParameterUtils.Temperature::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder humidity(Parameter... values) {
         this.humidities.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder humidity(ParameterUtils.Humidity... values) {
         this.humidities.addAll(Arrays.asList(values).stream().map(ParameterUtils.Humidity::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder continentalness(Parameter... values) {
         this.continentalnesses.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder continentalness(ParameterUtils.Continentalness... values) {
         this.continentalnesses.addAll(Arrays.asList(values).stream().map(ParameterUtils.Continentalness::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder erosion(Parameter... values) {
         this.erosions.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder erosion(ParameterUtils.Erosion... values) {
         this.erosions.addAll(Arrays.asList(values).stream().map(ParameterUtils.Erosion::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder depth(Parameter... values) {
         this.depths.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder depth(ParameterUtils.Depth... values) {
         this.depths.addAll(Arrays.asList(values).stream().map(ParameterUtils.Depth::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder weirdness(Parameter... values) {
         this.weirdnesses.addAll(Arrays.asList(values));
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder weirdness(ParameterUtils.Weirdness... values) {
         this.weirdnesses.addAll(Arrays.asList(values).stream().map(ParameterUtils.Weirdness::parameter).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder offset(Float... values) {
         this.offsets.addAll(Arrays.asList(values).stream().map(Climate::quantizeCoord).toList());
         return this;
      }

      public ParameterUtils.ParameterPointListBuilder offset(Long... values) {
         this.offsets.addAll(Arrays.asList(values));
         return this;
      }

      public List<ParameterPoint> build() {
         this.populateIfEmpty();
         Builder<ParameterPoint> builder = new Builder();
         this.temperatures
            .forEach(
               temperature -> this.humidities
                  .forEach(
                     humidity -> this.continentalnesses
                        .forEach(
                           continentalness -> this.erosions
                              .forEach(
                                 erosion -> this.depths
                                    .forEach(
                                       depth -> this.weirdnesses
                                          .forEach(
                                             weirdness -> this.offsets
                                                .forEach(
                                                   offset -> builder.add(
                                                      new ParameterPoint(temperature, humidity, continentalness, erosion, depth, weirdness, offset)
                                                   )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            );
         return builder.build();
      }

      private void populateIfEmpty() {
         if (this.temperatures.isEmpty()) {
            this.temperatures.add(ParameterUtils.Temperature.FULL_RANGE.parameter());
         }

         if (this.humidities.isEmpty()) {
            this.humidities.add(ParameterUtils.Humidity.FULL_RANGE.parameter());
         }

         if (this.continentalnesses.isEmpty()) {
            this.continentalnesses.add(ParameterUtils.Continentalness.FULL_RANGE.parameter());
         }

         if (this.erosions.isEmpty()) {
            this.erosions.add(ParameterUtils.Erosion.FULL_RANGE.parameter());
         }

         if (this.depths.isEmpty()) {
            this.depths.add(ParameterUtils.Depth.FULL_RANGE.parameter());
         }

         if (this.weirdnesses.isEmpty()) {
            this.weirdnesses.add(ParameterUtils.Weirdness.FULL_RANGE.parameter());
         }

         if (this.offsets.isEmpty()) {
            this.offsets.add(Climate.quantizeCoord(0.0F));
         }
      }
   }

   public enum Temperature {
      ICY(Parameter.span(-1.0F, -0.45F)),
      COOL(Parameter.span(-0.45F, -0.15F)),
      NEUTRAL(Parameter.span(-0.15F, 0.2F)),
      WARM(Parameter.span(0.2F, 0.55F)),
      HOT(Parameter.span(0.55F, 1.0F)),
      FROZEN(Parameter.span(-1.0F, -0.45F)),
      UNFROZEN(Parameter.span(-0.45F, 1.0F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Temperature(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Temperature min, ParameterUtils.Temperature max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }

   public enum Weirdness {
      MID_SLICE_NORMAL_ASCENDING(Parameter.span(-1.0F, -0.93333334F)),
      HIGH_SLICE_NORMAL_ASCENDING(Parameter.span(-0.93333334F, -0.7666667F)),
      PEAK_NORMAL(Parameter.span(-0.7666667F, -0.56666666F)),
      HIGH_SLICE_NORMAL_DESCENDING(Parameter.span(-0.56666666F, -0.4F)),
      MID_SLICE_NORMAL_DESCENDING(Parameter.span(-0.4F, -0.26666668F)),
      LOW_SLICE_NORMAL_DESCENDING(Parameter.span(-0.26666668F, -0.05F)),
      VALLEY(Parameter.span(-0.05F, 0.05F)),
      LOW_SLICE_VARIANT_ASCENDING(Parameter.span(0.05F, 0.26666668F)),
      MID_SLICE_VARIANT_ASCENDING(Parameter.span(0.26666668F, 0.4F)),
      HIGH_SLICE_VARIANT_ASCENDING(Parameter.span(0.4F, 0.56666666F)),
      PEAK_VARIANT(Parameter.span(0.56666666F, 0.7666667F)),
      HIGH_SLICE_VARIANT_DESCENDING(Parameter.span(0.7666667F, 0.93333334F)),
      MID_SLICE_VARIANT_DESCENDING(Parameter.span(0.93333334F, 1.0F)),
      FULL_RANGE(Parameter.span(-1.0F, 1.0F));

      private final Parameter parameter;

      Weirdness(Parameter parameter) {
         this.parameter = parameter;
      }

      public Parameter parameter() {
         return this.parameter;
      }

      public static Parameter span(ParameterUtils.Weirdness min, ParameterUtils.Weirdness max) {
         return Parameter.span(Climate.unquantizeCoord(min.parameter().min()), Climate.unquantizeCoord(max.parameter().max()));
      }
   }
}
