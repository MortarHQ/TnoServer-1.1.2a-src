package net.jadenxgamer.elysium_api.impl.core.datadriven.mosaic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public record MosaicBiomeEntry(
   EntryType type,
   ResourceLocation dimension,
   int climatePoint,
   Holder<Biome> biome,
   int weight,
   int keepWeight,
   Optional<MosaicBiomeEntry.SubBiomeType> subBiomeSettings
) {
   public static final Codec<MosaicBiomeEntry> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            EntryType.CODEC.optionalFieldOf("type", EntryType.DEFAULT).forGetter(MosaicBiomeEntry::type),
            ResourceLocation.CODEC.fieldOf("dimension").forGetter(MosaicBiomeEntry::dimension),
            Codec.INT.fieldOf("climate_point").forGetter(MosaicBiomeEntry::climatePoint),
            Biome.CODEC.fieldOf("biome").forGetter(MosaicBiomeEntry::biome),
            Codec.INT.fieldOf("weight").forGetter(MosaicBiomeEntry::weight),
            Codec.INT.optionalFieldOf("keep_weight", 80).forGetter(MosaicBiomeEntry::keepWeight),
            MosaicBiomeEntry.SubBiomeType.CODEC.optionalFieldOf("sub_biome_settings").forGetter(MosaicBiomeEntry::subBiomeSettings)
         )
         .apply(instance, MosaicBiomeEntry::new)
   );

   public record SubBiomeType(ResourceLocation replaceBiome, SubBiomeBehavior behavior) {
      public static final Codec<MosaicBiomeEntry.SubBiomeType> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("replace_biome").forGetter(MosaicBiomeEntry.SubBiomeType::replaceBiome),
               SubBiomeBehavior.CODEC.fieldOf("behavior").forGetter(MosaicBiomeEntry.SubBiomeType::behavior)
            )
            .apply(instance, MosaicBiomeEntry.SubBiomeType::new)
      );
   }
}
