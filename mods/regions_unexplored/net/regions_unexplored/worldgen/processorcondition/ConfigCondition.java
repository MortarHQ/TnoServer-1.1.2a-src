package net.regions_unexplored.worldgen.processorcondition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.api.worldgen.processorcondition.ProcessorCondition;
import dev.worldgen.lithostitched.api.worldgen.processorcondition.ProcessorCondition.Data;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.regions_unexplored.config.RUConfigHandler;

public record ConfigCondition(String key) implements ProcessorCondition {
   public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(Codec.STRING.fieldOf("key").forGetter(ConfigCondition::key)).apply(i, ConfigCondition::new)
   );

   public boolean test(WorldGenLevel level, Data data, StructurePlaceSettings placeSettings, RandomSource random) {
      return RUConfigHandler.COMMON.test(this.key);
   }

   public MapCodec<? extends ProcessorCondition> codec() {
      return CODEC;
   }
}
