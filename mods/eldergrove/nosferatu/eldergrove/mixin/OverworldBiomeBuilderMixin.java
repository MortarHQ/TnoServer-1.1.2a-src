package nosferatu.eldergrove.mixin;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import nosferatu.eldergrove.EldergroveBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
   @Inject(method = "addBiomes", at = @At("TAIL"))
   private void eldergrove$addEldergroveBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper, CallbackInfo ci) {
      addBiome(mapper, EldergroveBiomes.ELDERGROVE, 0.05F, 0.72F, 0.5F, 1.0F, 0.34F, 1.0F, -0.86F, 0.12F, -1.0F, 1.0F);
      addBiome(mapper, EldergroveBiomes.ELDERGROVE, 0.16F, 0.78F, 0.44F, 1.0F, 0.3F, 0.88F, -0.58F, 0.3F, -0.82F, 0.82F);
      addBiome(mapper, EldergroveBiomes.ELDERGROVE, 0.18F, 0.88F, 0.28F, 0.76F, 0.34F, 1.0F, -0.7F, 0.48F, -1.0F, 1.0F);
      addBiome(mapper, EldergroveBiomes.TAINTED_GROVE, -0.16F, 0.58F, 0.54F, 1.0F, 0.14F, 0.9F, -0.88F, 0.2F, -1.0F, -0.16F);
      addBiome(mapper, EldergroveBiomes.TAINTED_GROVE, 0.12F, 0.86F, 0.58F, 1.0F, 0.1F, 0.8F, -0.28F, 0.64F, -0.86F, -0.08F);
   }

   private static void addBiome(
      Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper,
      ResourceKey<Biome> biome,
      float minTemperature,
      float maxTemperature,
      float minHumidity,
      float maxHumidity,
      float minContinentalness,
      float maxContinentalness,
      float minErosion,
      float maxErosion,
      float minWeirdness,
      float maxWeirdness
   ) {
      mapper.accept(
         Pair.of(
            Climate.parameters(
               Parameter.span(minTemperature, maxTemperature),
               Parameter.span(minHumidity, maxHumidity),
               Parameter.span(minContinentalness, maxContinentalness),
               Parameter.span(minErosion, maxErosion),
               Parameter.point(0.0F),
               Parameter.span(minWeirdness, maxWeirdness),
               0.0F
            ),
            biome
         )
      );
   }
}
