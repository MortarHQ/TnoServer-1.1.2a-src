package net.jadenxgamer.elysium_api.impl.core.surface_rules;

import java.util.ArrayList;
import java.util.List;
import net.jadenxgamer.elysium_api.impl.mixin.biome.NoiseGeneratorSettingsAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.neoforged.fml.ModList;

public class ElysiumSurfaceRulesManager {
   public static final List<RuleSource> OVERWORLD_SURFACE_RULES = new ArrayList<>();
   public static final List<RuleSource> NETHER_SURFACE_RULES = new ArrayList<>();
   public static final List<RuleSource> END_SURFACE_RULES = new ArrayList<>();

   public static RuleSource getForMergingRules(List<RuleSource> dimensionRules, RuleSource originalRules) {
      if (dimensionRules.isEmpty()) {
         return null;
      }

      List<RuleSource> combinedRules = new ArrayList<>(dimensionRules);
      combinedRules.add(originalRules);
      return SurfaceRules.sequence(combinedRules.toArray(RuleSource[]::new));
   }

   public static void handleSurfaceRules(ResourceKey<LevelStem> dimension, NoiseBasedChunkGenerator noiseGenerator) {
      if (!ModList.get().isLoaded("terrablender")) {
         RuleSource newRules = null;
         RuleSource originalRules = ((NoiseGeneratorSettings)noiseGenerator.settings.value()).surfaceRule();
         if (dimension.equals(LevelStem.OVERWORLD)) {
            newRules = getForMergingRules(OVERWORLD_SURFACE_RULES, originalRules);
         } else if (dimension.equals(LevelStem.NETHER)) {
            newRules = getForMergingRules(NETHER_SURFACE_RULES, originalRules);
         } else if (dimension.equals(LevelStem.END)) {
            newRules = getForMergingRules(END_SURFACE_RULES, originalRules);
         }

         if (newRules != null) {
            ((NoiseGeneratorSettingsAccessor)noiseGenerator.settings.value()).elysium_api$setSurfaceRule(newRules);
         }
      }
   }
}
