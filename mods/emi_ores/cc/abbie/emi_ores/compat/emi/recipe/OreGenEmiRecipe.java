package cc.abbie.emi_ores.compat.emi.recipe;

import cc.abbie.emi_ores.EmiOres;
import cc.abbie.emi_ores.compat.emi.EmiOresRecipeCategories;
import cc.abbie.emi_ores.compat.emi.stack.BiomeEmiStack;
import cc.abbie.emi_ores.mixin.accessor.BlockMatchTestAccessor;
import cc.abbie.emi_ores.mixin.accessor.BlockStateMatchTestAccessor;
import cc.abbie.emi_ores.mixin.accessor.CountPlacementAccessor;
import cc.abbie.emi_ores.mixin.accessor.HeightRangePlacementAccessor;
import cc.abbie.emi_ores.mixin.accessor.RandomBlockMatchTestAccessor;
import cc.abbie.emi_ores.mixin.accessor.RandomBlockStateMatchTestAccessor;
import cc.abbie.emi_ores.mixin.accessor.RarityFilterAccessor;
import cc.abbie.emi_ores.mixin.accessor.TagMatchTestAccessor;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ScatteredOreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OreGenEmiRecipe extends AbstractPlacedFeatureEmiRecipe {
   private final ResourceLocation id;
   private final List<EmiIngredient> inputs;
   private final List<EmiStack> outputs;
   private final HeightProvider heightProvider;
   private final EmiIngredient biomes;
   private final float discardChanceOnAirExposure;
   private final int countMin;
   private final int countMax;
   private final int rarityChance;

   public OreGenEmiRecipe(PlacedFeature feature, ResourceLocation id) {
      this.id = id;
      List<EmiIngredient> inputs = new ArrayList<>();
      List<EmiStack> outputs = new ArrayList<>();
      OreConfiguration oreConfig = (OreConfiguration)((ConfiguredFeature)feature.feature().value()).config();
      this.discardChanceOnAirExposure = oreConfig.discardChanceOnAirExposure;
      oreConfig.targetStates
         .forEach(
            targetBlockState -> {
               RuleTest target = targetBlockState.target;
               if (target instanceof TagMatchTest tagMatchTest) {
                  TagKey<Block> tag = ((TagMatchTestAccessor)tagMatchTest).getTag();
                  List<EmiIngredient> l = BuiltInRegistries.BLOCK
                     .getOrCreateTag(tag)
                     .stream()
                     .map(Holder::value)
                     .<EmiIngredient>map(EmiStack::of)
                     .collect(Collectors.toList());
                  inputs.add(EmiIngredient.of(l));
               } else if (target instanceof BlockMatchTest blockMatchTest) {
                  Block block = ((BlockMatchTestAccessor)blockMatchTest).getBlock();
                  inputs.add(EmiStack.of(block));
               } else if (target instanceof BlockStateMatchTest blockStateMatchTest) {
                  BlockState state = ((BlockStateMatchTestAccessor)blockStateMatchTest).getBlockState();
                  inputs.add(EmiStack.of(state.getBlock()));
               } else if (target instanceof RandomBlockMatchTest randomBlockMatchTest) {
                  RandomBlockMatchTestAccessor accessor = (RandomBlockMatchTestAccessor)randomBlockMatchTest;
                  Block block = accessor.getBlock();
                  float probability = accessor.getProbability();
                  inputs.add(EmiStack.of(block).setChance(probability));
               } else if (target instanceof RandomBlockStateMatchTest randomBlockStateMatchTest) {
                  RandomBlockStateMatchTestAccessor accessor = (RandomBlockStateMatchTestAccessor)randomBlockStateMatchTest;
                  Block block = accessor.getBlockState().getBlock();
                  float probability = accessor.getProbability();
                  inputs.add(EmiStack.of(block).setChance(probability));
               } else {
                  inputs.add(EmiStack.EMPTY);
               }

               outputs.add(EmiStack.of(targetBlockState.state.getBlock()).setAmount(oreConfig.size));
            }
         );
      this.inputs = Collections.unmodifiableList(inputs);
      this.outputs = Collections.unmodifiableList(outputs);
      HeightProvider heightProvider = null;
      List<Biome> biomes = List.of();
      int countMin = -1;
      int countMax = -1;
      int rarityChance = -1;

      for (PlacementModifier modifier : feature.placement()) {
         if (modifier instanceof HeightRangePlacement heightRange) {
            heightProvider = ((HeightRangePlacementAccessor)heightRange).getHeight();
         } else if (modifier instanceof BiomeFilter) {
            biomes = getBiomes(id, feature);
         } else if (modifier instanceof CountPlacement countPlacement) {
            IntProvider countIntProvider = ((CountPlacementAccessor)countPlacement).getCount();
            countMin = countIntProvider.getMinValue();
            countMax = countIntProvider.getMaxValue();
         } else if (modifier instanceof RarityFilter rarityFilter) {
            rarityChance = ((RarityFilterAccessor)rarityFilter).getChance();
         }
      }

      if (((ConfiguredFeature)feature.feature().value()).feature() instanceof ScatteredOreFeature) {
         countMax = 1;
         countMin = 1;
      }

      this.countMin = countMin;
      this.countMax = countMax;
      this.rarityChance = rarityChance;
      this.heightProvider = heightProvider;
      this.biomes = EmiIngredient.of(biomes.stream().map(BiomeEmiStack::of).toList());
   }

   public EmiRecipeCategory getCategory() {
      return EmiOresRecipeCategories.OREGEN;
   }

   public ResourceLocation getId() {
      return EmiOres.id("/ore/" + this.id.getNamespace() + "/" + this.id.getPath());
   }

   public List<EmiIngredient> getInputs() {
      return Stream.concat(this.inputs.stream(), Stream.of(this.biomes)).toList();
   }

   public List<EmiStack> getOutputs() {
      return this.outputs;
   }

   public int getDisplayWidth() {
      return 160;
   }

   public int getDisplayHeight() {
      return Math.max(54, 18 + 18 * this.inputs.size());
   }

   public boolean supportsRecipeTree() {
      return false;
   }

   public void addWidgets(WidgetHolder widgets) {
      for (int i = 0; i < this.inputs.size(); i++) {
         widgets.addSlot(this.inputs.get(i), 0, 18 + i * 18);
         widgets.addTexture(EmiTexture.EMPTY_ARROW, 20, 18 + i * 18);
         widgets.addSlot((EmiIngredient)this.outputs.get(i), 46, 18 + i * 18).recipeContext(this);
      }

      addDistributionGraph(widgets, 64, 0, this.heightProvider);
      if (!this.biomes.isEmpty()) {
         widgets.addSlot(this.biomes, 96, 18);
      }

      if (this.discardChanceOnAirExposure > 0.0F) {
         widgets.addSlot(EmiStack.of(Items.BARRIER).setChance(this.discardChanceOnAirExposure), 142, 18)
            .appendTooltip(Component.translatable("emi_ores.discard_on_air_chance"));
      }

      Component veinFreq = getVeinFreqComponent(this.countMin, this.countMax, this.rarityChance);
      if (veinFreq != null) {
         widgets.addText(veinFreq, 160, 45, 0, false).horizontalAlign(Alignment.END).verticalAlign(Alignment.CENTER);
      }
   }
}
