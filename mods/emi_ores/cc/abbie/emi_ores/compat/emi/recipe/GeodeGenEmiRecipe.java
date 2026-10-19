package cc.abbie.emi_ores.compat.emi.recipe;

import cc.abbie.emi_ores.EmiOres;
import cc.abbie.emi_ores.compat.emi.EmiOresRecipeCategories;
import cc.abbie.emi_ores.compat.emi.stack.BiomeEmiStack;
import cc.abbie.emi_ores.mixin.accessor.HeightRangePlacementAccessor;
import cc.abbie.emi_ores.mixin.accessor.NoiseProviderAccessor;
import cc.abbie.emi_ores.mixin.accessor.RarityFilterAccessor;
import cc.abbie.emi_ores.mixin.accessor.SimpleStateProviderAccessor;
import cc.abbie.emi_ores.mixin.accessor.WeightedStateProviderAccessor;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class GeodeGenEmiRecipe extends AbstractPlacedFeatureEmiRecipe {
   private final HeightProvider heightProvider;
   private final int rarityChance;
   private final EmiIngredient biomes;
   private final EmiIngredient alternateInner;
   private final EmiIngredient inner;
   private final EmiIngredient middle;
   private final EmiIngredient outer;
   private final EmiIngredient filling;
   private final EmiIngredient innerPlacements;
   private final ResourceLocation id;

   public GeodeGenEmiRecipe(PlacedFeature feature, ResourceLocation id) {
      this.id = id;
      HeightProvider heightProvider = null;
      int rarityChance = -1;
      List<Biome> biomes = List.of();

      for (PlacementModifier modifier : feature.placement()) {
         if (modifier instanceof HeightRangePlacement heightRangePlacement) {
            heightProvider = ((HeightRangePlacementAccessor)heightRangePlacement).getHeight();
         } else if (modifier instanceof RarityFilter rarityFilter) {
            rarityChance = ((RarityFilterAccessor)rarityFilter).getChance();
         } else if (modifier instanceof BiomeFilter) {
            biomes = getBiomes(id, feature);
         }
      }

      this.heightProvider = heightProvider;
      this.rarityChance = rarityChance;
      this.biomes = EmiIngredient.of(biomes.stream().map(BiomeEmiStack::of).collect(Collectors.toList()));
      GeodeConfiguration config = (GeodeConfiguration)((ConfiguredFeature)feature.feature().value()).config();
      GeodeBlockSettings blockSettings = config.geodeBlockSettings;
      GeodeLayerSettings layerSettings = config.geodeLayerSettings;
      this.alternateInner = ingredientForStateProvider(blockSettings.alternateInnerLayerProvider).setChance((float)config.useAlternateLayer0Chance);
      this.filling = ingredientForStateProvider(blockSettings.fillingProvider).setAmount((long)Math.ceil(layerSettings.filling));
      this.inner = ingredientForStateProvider(blockSettings.innerLayerProvider).setAmount((long)Math.ceil(layerSettings.innerLayer - layerSettings.filling));
      this.middle = ingredientForStateProvider(blockSettings.middleLayerProvider)
         .setAmount((long)Math.ceil(layerSettings.middleLayer - layerSettings.innerLayer));
      this.outer = ingredientForStateProvider(blockSettings.outerLayerProvider)
         .setAmount((long)Math.ceil(layerSettings.outerLayer - layerSettings.middleLayer));
      this.innerPlacements = EmiIngredient.of(
            blockSettings.innerPlacements.stream().map(BlockStateBase::getBlock).map(EmiStack::of).collect(Collectors.toList())
         )
         .setChance((float)config.usePotentialPlacementsChance);
   }

   private static EmiIngredient ingredientForStateProvider(BlockStateProvider provider) {
      if (provider instanceof SimpleStateProvider simple) {
         return EmiStack.of(((SimpleStateProviderAccessor)simple).getState().getBlock());
      } else if (provider instanceof WeightedStateProvider weighted) {
         return EmiIngredient.of(
            ((WeightedStateProviderAccessor)weighted)
               .getWeightedList()
               .unwrap()
               .stream()
               .map(Wrapper::data)
               .map(BlockStateBase::getBlock)
               .map(EmiStack::of)
               .toList()
         );
      } else {
         return (EmiIngredient)(provider instanceof NoiseProvider noise
            ? EmiIngredient.of(((NoiseProviderAccessor)noise).getStates().stream().map(BlockStateBase::getBlock).distinct().map(EmiStack::of).toList())
            : EmiStack.EMPTY);
      }
   }

   public EmiRecipeCategory getCategory() {
      return EmiOresRecipeCategories.GEODE;
   }

   public ResourceLocation getId() {
      return EmiOres.id("/geode/" + this.id.getNamespace() + "/" + this.id.getPath());
   }

   public List<EmiIngredient> getInputs() {
      return List.of(this.biomes);
   }

   public List<EmiStack> getOutputs() {
      return Stream.of(this.innerPlacements, this.filling, this.alternateInner, this.inner, this.middle, this.outer)
         .map(EmiIngredient::getEmiStacks)
         .flatMap(Collection::stream)
         .toList();
   }

   public int getDisplayWidth() {
      return 160;
   }

   public int getDisplayHeight() {
      return 90;
   }

   public boolean supportsRecipeTree() {
      return false;
   }

   public void addWidgets(WidgetHolder widgets) {
      widgets.addSlot(this.filling, 0, 18).recipeContext(this);
      widgets.addSlot(this.innerPlacements, 46, 18).recipeContext(this);
      widgets.addSlot(this.inner, 0, 36).recipeContext(this);
      widgets.addTexture(EmiTexture.EMPTY_ARROW, 20, 36);
      widgets.addSlot(this.alternateInner, 46, 36).recipeContext(this);
      widgets.addSlot(this.middle, 0, 54).recipeContext(this);
      widgets.addSlot(this.outer, 0, 72).recipeContext(this);
      addDistributionGraph(widgets, 64, 0, this.heightProvider);
      if (!this.biomes.isEmpty()) {
         widgets.addSlot(this.biomes, 96, 18);
      }

      Component veinFreq = getVeinFreqComponent(-1, -1, this.rarityChance);
      if (veinFreq != null) {
         widgets.addText(veinFreq, 160, 45, 0, false).horizontalAlign(Alignment.END).verticalAlign(Alignment.CENTER);
      }
   }
}
