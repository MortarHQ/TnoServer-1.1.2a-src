package net.regions_unexplored.client.gui;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.config.state.client.RUClientConfig;
import net.regions_unexplored.config.state.common.BiomeTarget;
import net.regions_unexplored.config.state.common.RUCommonConfig;

public interface ConfigListBuilder {
   StringWidget addCategory(String string, Font font);

   <T extends StringRepresentable> void addEnum(String string, Consumer<T> consumer, T stringRepresentable, T[] stringRepresentables, T stringRepresentable2);

   void addBoolean(String string, Consumer<Boolean> consumer, boolean bl, boolean bl2);

   void addSmallBoolean(String string, Consumer<Boolean> consumer, boolean bl, boolean bl2, boolean bl3);

   void addInteger(String string, double d, double e, double f, Consumer<Integer> consumer, double g, double h);

   void addDouble(String string, double d, double e, double f, Consumer<Double> consumer, double g, double h);

   void addEntry(AbstractWidget abstractWidget);

   default void build(Font font) {
      RUClientConfig client = RUConfigHandler.CLIENT;
      RUCommonConfig common = RUConfigHandler.COMMON;
      this.addCategory("particle_rates", font);
      this.addDouble("leaves", 0.0, 5.0, 0.1, value -> client.particleRates.leaves = value, client.particleRates.leaves, 1.0);
      this.addDouble("prismarite", 0.0, 5.0, 0.1, value -> client.particleRates.prismarite = value, client.particleRates.prismarite, 1.0);
      this.addCategory("eucalyptus_colors", font);
      this.addDouble("transition_size", 0.0, 200.0, 1.0, value -> client.eucalyptusColors.transitionSize = value, client.eucalyptusColors.transitionSize, 25.0);
      this.addDouble("saturation", 0.0, 1.0, 0.05, value -> client.eucalyptusColors.saturation = value, client.eucalyptusColors.saturation, 0.5);
      this.addDouble("brightness", 0.0, 1.0, 0.05, value -> client.eucalyptusColors.brightness = value, client.eucalyptusColors.brightness, 0.8);
      this.addCategory("misc", font);
      this.addEnum(
         "branch_mode",
         value -> common.misc.branchMode = value,
         common.misc.branchMode,
         RUCommonConfig.Misc.BranchMode.values(),
         RUCommonConfig.Misc.BranchMode.PLACE_BRANCHES
      );
      this.addBoolean("custom_dirts", value -> common.misc.customDirts = value, common.misc.customDirts, true);
      this.addBoolean("small_oak_trees", value -> common.misc.smallOakTrees = value, common.misc.smallOakTrees, false);
      this.addBoolean("painted_planks", value -> common.misc.paintedPlanks = value, common.misc.paintedPlanks, false);
      StringWidget biomePlacements = this.addCategory("biome_placements", font);
      biomePlacements.setTooltip(
         Tooltip.create(
            Component.literal(
                  "Note: Biome placement has more enhanced configurations such as biome weightings, but you need to open the config file to edit those."
               )
               .withStyle(ChatFormatting.GRAY)
         )
      );

      for (Entry<ResourceKey<Biome>, BiomeTarget> group : common.biomePlacements
         .placements
         .entrySet()
         .stream()
         .sorted(Comparator.comparing(entry -> entry.getKey().location().getPath()))
         .toList()) {
         BiomeTarget target = group.getValue();
         this.addSmallBoolean(group.getKey().location().toLanguageKey("biome"), target::setCanGenerate, target.canGenerate(), true, false);
      }

      StringWidget vanillaChanges = this.addCategory("vanilla_changes", font);

      for (Entry<String, Boolean> toggle : common.vanillaChanges.toggles.entrySet().stream().sorted(Entry.comparingByKey()).toList()) {
         String key = toggle.getKey();
         this.addSmallBoolean(key, bool -> common.vanillaChanges.toggles.put(key, bool), common.vanillaChanges.toggles.get(key), true, true);
      }
   }
}
