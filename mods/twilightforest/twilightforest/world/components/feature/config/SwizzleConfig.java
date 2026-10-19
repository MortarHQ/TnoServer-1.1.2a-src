package twilightforest.world.components.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.jetbrains.annotations.NotNull;
import twilightforest.TFRegistries;
import twilightforest.data.tags.CustomTagGenerator;
import twilightforest.util.woods.WoodPalette;
import twilightforest.world.components.processors.StateTransfiguringProcessor;
import twilightforest.world.components.processors.WoodPaletteSwizzle;

public record SwizzleConfig(
   HolderSet<WoodPalette> targets, WeightedRandomList<Wrapper<HolderSet<WoodPalette>>> paletteChoices, List<ProcessorRule> preprocessingRules
) implements FeatureConfiguration {
   public static final Codec<SwizzleConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            RegistryCodecs.homogeneousList(TFRegistries.Keys.WOOD_PALETTES).fieldOf("target_palettes").forGetter(SwizzleConfig::targets),
            WeightedRandomList.codec(Wrapper.codec(RegistryCodecs.homogeneousList(TFRegistries.Keys.WOOD_PALETTES)))
               .fieldOf("palette_choices")
               .forGetter(SwizzleConfig::paletteChoices),
            ProcessorRule.CODEC.listOf().fieldOf("preprocessing_rules").orElseGet(Collections::emptyList).forGetter(SwizzleConfig::preprocessingRules)
         )
         .apply(instance, SwizzleConfig::new)
   );

   @NotNull
   public static WeightedRandomList<Wrapper<HolderSet<WoodPalette>>> buildRarityPalette(HolderGetter<WoodPalette> paletteHolders) {
      Wrapper<HolderSet<WoodPalette>> common = WeightedEntry.wrap(
         (HolderSet)paletteHolders.get(CustomTagGenerator.WoodPaletteTagGenerator.COMMON_PALETTES).get(), 8
      );
      Wrapper<HolderSet<WoodPalette>> uncommon = WeightedEntry.wrap(
         (HolderSet)paletteHolders.get(CustomTagGenerator.WoodPaletteTagGenerator.UNCOMMON_PALETTES).get(), 4
      );
      Wrapper<HolderSet<WoodPalette>> rare = WeightedEntry.wrap(
         (HolderSet)paletteHolders.get(CustomTagGenerator.WoodPaletteTagGenerator.RARE_PALETTES).get(), 3
      );
      Wrapper<HolderSet<WoodPalette>> treasure = WeightedEntry.wrap(
         (HolderSet)paletteHolders.get(CustomTagGenerator.WoodPaletteTagGenerator.TREASURE_PALETTES).get(), 1
      );
      return WeightedRandomList.create(new Wrapper[]{common, uncommon, rare, treasure});
   }

   public static SwizzleConfig generate(
      HolderGetter<WoodPalette> paletteHolders,
      TagKey<WoodPalette> swizzleMask,
      WeightedRandomList<Wrapper<HolderSet<WoodPalette>>> paletteChoices,
      ProcessorRule... postProcessingRules
   ) {
      return new SwizzleConfig(paletteHolders.getOrThrow(swizzleMask), paletteChoices, List.of(postProcessingRules));
   }

   public void buildAddProcessors(StructurePlaceSettings settings, RandomSource random) {
      if (!this.preprocessingRules().isEmpty()) {
         settings.addProcessor(new StateTransfiguringProcessor(this.preprocessingRules()));
      }

      for (Holder<WoodPalette> targetPalette : this.targets) {
         settings.addProcessor(
            new WoodPaletteSwizzle(
               targetPalette, (Holder<WoodPalette>)((HolderSet)((Wrapper)this.paletteChoices().getRandom(random).get()).data()).getRandomElement(random).get()
            )
         );
      }
   }
}
