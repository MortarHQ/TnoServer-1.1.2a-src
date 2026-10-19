package mezz.jei.common.config;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import mezz.jei.common.config.file.ConfigValue;
import mezz.jei.common.config.file.IConfigCategoryBuilder;
import mezz.jei.common.config.file.IConfigListener;
import mezz.jei.common.config.file.IConfigSchemaBuilder;
import mezz.jei.common.config.file.serializers.EnumSerializer;
import mezz.jei.common.config.file.serializers.ListSerializer;
import mezz.jei.common.platform.Services;
import org.jetbrains.annotations.Nullable;

public final class ClientConfig implements IClientConfig {
   @Nullable
   private static IClientConfig instance;
   private final Supplier<Boolean> centerSearchBarEnabled;
   private final Supplier<Integer> maxRecipeGuiHeight;
   private final Supplier<GiveMode> giveMode;
   private final Supplier<Boolean> cheatToHotbarUsingHotkeysEnabled;
   private final Supplier<Boolean> showHiddenIngredients;
   private final Supplier<Boolean> addBookmarksToFrontEnabled;
   private final Supplier<List<BookmarkTooltipFeature>> bookmarkTooltipFeatures;
   private final Supplier<Boolean> holdShiftToShowBookmarkTooltipFeaturesEnabled;
   private final Supplier<Boolean> dragToRearrangeBookmarksEnabled;
   private final ConfigValue<Boolean> lookupHistoryEnabled;
   private final ConfigValue<Integer> maxLookupHistoryRows;
   private final ConfigValue<Integer> maxLookupHistoryIngredients;
   private final ConfigValue<HistoryDisplaySide> lookupHistoryDisplaySide;
   private final ConfigValue<Boolean> ingredientsSummaryEnabled;
   private final Supplier<Boolean> lowMemorySlowSearchEnabled;
   private final Supplier<Boolean> catchRenderErrorsEnabled;
   private final Supplier<Boolean> lookupFluidContentsEnabled;
   private final Supplier<Boolean> lookupBlockTagsEnabled;
   private final Supplier<Boolean> showTagRecipesEnabled;
   private final Supplier<Boolean> showCreativeTabNamesEnabled;
   private final Supplier<Integer> dragDelayMs;
   private final Supplier<Integer> smoothScrollRate;
   private final Supplier<List<IngredientSortStage>> ingredientSorterStages;
   private final ConfigValue<List<RecipeSorterStage>> recipeSorterStages;
   private final Supplier<Boolean> tagContentTooltipEnabled;
   private final Supplier<Boolean> hideSingleTagContentTooltipEnabled;

   public ClientConfig(IConfigSchemaBuilder schema) {
      instance = this;
      boolean isDev = Services.PLATFORM.getModHelper().isInDev();
      IConfigCategoryBuilder appearance = schema.addCategory("appearance");
      this.centerSearchBarEnabled = appearance.addBoolean("centerSearch", false);
      this.maxRecipeGuiHeight = appearance.addInteger("recipeGuiHeight", 350, 175, Integer.MAX_VALUE);
      IConfigCategoryBuilder cheating = schema.addCategory("cheating");
      this.giveMode = cheating.addEnum("giveMode", GiveMode.defaultGiveMode);
      this.cheatToHotbarUsingHotkeysEnabled = cheating.addBoolean("cheatToHotbarUsingHotkeysEnabled", false);
      this.showHiddenIngredients = cheating.addBoolean("showHiddenIngredients", false);
      this.showTagRecipesEnabled = cheating.addBoolean("showTagRecipesEnabled", isDev);
      IConfigCategoryBuilder bookmarks = schema.addCategory("bookmarks");
      this.addBookmarksToFrontEnabled = bookmarks.addBoolean("addBookmarksToFrontEnabled", false);
      this.dragToRearrangeBookmarksEnabled = bookmarks.addBoolean("dragToRearrangeBookmarksEnabled", true);
      IConfigCategoryBuilder tooltips = schema.addCategory("tooltips");
      this.bookmarkTooltipFeatures = tooltips.addList(
         "bookmarkTooltipFeatures",
         BookmarkTooltipFeature.DEFAULT_BOOKMARK_TOOLTIP_FEATURES,
         new ListSerializer<>(new EnumSerializer<>(BookmarkTooltipFeature.class))
      );
      this.holdShiftToShowBookmarkTooltipFeaturesEnabled = tooltips.addBoolean("holdShiftToShowBookmarkTooltipFeatures", true);
      this.showCreativeTabNamesEnabled = tooltips.addBoolean("showCreativeTabNamesEnabled", false);
      this.tagContentTooltipEnabled = tooltips.addBoolean("tagContentTooltipEnabled", true);
      this.hideSingleTagContentTooltipEnabled = tooltips.addBoolean("hideSingleTagContentTooltipEnabled", true);
      this.ingredientsSummaryEnabled = tooltips.addBoolean("enableRecipesGuiIngredientsSummary", false);
      IConfigCategoryBuilder performance = schema.addCategory("performance");
      this.lowMemorySlowSearchEnabled = performance.addBoolean("lowMemorySlowSearchEnabled", false);
      IConfigCategoryBuilder lookups = schema.addCategory("lookups");
      this.lookupFluidContentsEnabled = lookups.addBoolean("lookupFluidContentsEnabled", false);
      this.lookupBlockTagsEnabled = lookups.addBoolean("lookupBlockTagsEnabled", true);
      IConfigCategoryBuilder lookupHistory = schema.addCategory("lookupHistory");
      this.lookupHistoryEnabled = lookupHistory.addBoolean("enabled", false);
      this.maxLookupHistoryRows = lookupHistory.addInteger("maxRows", 2, 1, 7);
      this.maxLookupHistoryIngredients = lookupHistory.addInteger("maxIngredients", 100, 10, 1000);
      this.lookupHistoryDisplaySide = lookupHistory.addEnum("displaySide", HistoryDisplaySide.LEFT);
      IConfigCategoryBuilder advanced = schema.addCategory("advanced");
      this.catchRenderErrorsEnabled = advanced.addBoolean("catchRenderErrorsEnabled", !isDev);
      IConfigCategoryBuilder input = schema.addCategory("input");
      this.dragDelayMs = input.addInteger("dragDelayInMilliseconds", 150, 0, 1000);
      this.smoothScrollRate = input.addInteger("smoothScrollRate", 9, 1, 50);
      IConfigCategoryBuilder sorting = schema.addCategory("sorting");
      this.ingredientSorterStages = sorting.addList(
         "ingredientSortStages", IngredientSortStage.defaultStages, new ListSerializer<>(new EnumSerializer<>(IngredientSortStage.class))
      );
      this.recipeSorterStages = sorting.addList(
         "recipeSorterStages", RecipeSorterStage.defaultStages, new ListSerializer<>(new EnumSerializer<>(RecipeSorterStage.class))
      );
   }

   @Deprecated
   public static IClientConfig getInstance() {
      Preconditions.checkNotNull(instance);
      return instance;
   }

   @Override
   public boolean isCenterSearchBarEnabled() {
      return this.centerSearchBarEnabled.get();
   }

   @Override
   public boolean isLowMemorySlowSearchEnabled() {
      return this.lowMemorySlowSearchEnabled.get();
   }

   @Override
   public boolean isCatchRenderErrorsEnabled() {
      return this.catchRenderErrorsEnabled.get();
   }

   @Override
   public boolean isCheatToHotbarUsingHotkeysEnabled() {
      return this.cheatToHotbarUsingHotkeysEnabled.get();
   }

   @Override
   public boolean isAddingBookmarksToFrontEnabled() {
      return this.addBookmarksToFrontEnabled.get();
   }

   @Override
   public boolean isLookupFluidContentsEnabled() {
      return this.lookupFluidContentsEnabled.get();
   }

   @Override
   public boolean isLookupBlockTagsEnabled() {
      return this.lookupBlockTagsEnabled.get();
   }

   @Override
   public GiveMode getGiveMode() {
      return this.giveMode.get();
   }

   @Override
   public boolean getShowHiddenIngredients() {
      return this.showHiddenIngredients.get();
   }

   @Override
   public List<BookmarkTooltipFeature> getBookmarkTooltipFeatures() {
      return this.bookmarkTooltipFeatures.get();
   }

   @Override
   public boolean isHoldShiftToShowBookmarkTooltipFeaturesEnabled() {
      return this.holdShiftToShowBookmarkTooltipFeaturesEnabled.get();
   }

   @Override
   public boolean isDragToRearrangeBookmarksEnabled() {
      return this.dragToRearrangeBookmarksEnabled.get();
   }

   @Override
   public boolean isLookupHistoryEnabled() {
      return this.lookupHistoryEnabled.get();
   }

   @Override
   public void setLookupHistoryEnabled(boolean enabled) {
      this.lookupHistoryEnabled.set(enabled);
   }

   @Override
   public void addLookupHistoryEnabledListener(IConfigListener<Boolean> listener) {
      this.lookupHistoryEnabled.addListener(listener);
   }

   @Override
   public int getMaxLookupHistoryRows() {
      return this.maxLookupHistoryRows.get();
   }

   @Override
   public int getMaxLookupHistoryIngredients() {
      return this.maxLookupHistoryIngredients.get();
   }

   @Override
   public HistoryDisplaySide getLookupHistoryDisplaySide() {
      return this.lookupHistoryDisplaySide.get();
   }

   @Override
   public void addLookupHistoryDisplaySideListener(IConfigListener<HistoryDisplaySide> listener) {
      this.lookupHistoryDisplaySide.addListener(listener);
   }

   @Override
   public boolean isIngredientsSummaryEnabled() {
      return this.ingredientsSummaryEnabled.get();
   }

   @Override
   public int getDragDelayMs() {
      return this.dragDelayMs.get();
   }

   @Override
   public int getSmoothScrollRate() {
      return this.smoothScrollRate.get();
   }

   @Override
   public int getMaxRecipeGuiHeight() {
      return this.maxRecipeGuiHeight.get();
   }

   @Override
   public List<IngredientSortStage> getIngredientSorterStages() {
      return this.ingredientSorterStages.get();
   }

   @Override
   public Set<RecipeSorterStage> getRecipeSorterStages() {
      return Set.copyOf(this.recipeSorterStages.getValue());
   }

   @Override
   public void enableRecipeSorterStage(RecipeSorterStage stage) {
      List<RecipeSorterStage> recipeSorterStages = this.recipeSorterStages.get();
      if (!recipeSorterStages.contains(stage)) {
         recipeSorterStages = new ArrayList<>(recipeSorterStages);
         recipeSorterStages.add(stage);
         this.recipeSorterStages.set(recipeSorterStages);
      }
   }

   @Override
   public void disableRecipeSorterStage(RecipeSorterStage stage) {
      List<RecipeSorterStage> recipeSorterStages = this.recipeSorterStages.get();
      if (recipeSorterStages.contains(stage)) {
         recipeSorterStages = new ArrayList<>(recipeSorterStages);
         recipeSorterStages.remove(stage);
         this.recipeSorterStages.set(recipeSorterStages);
      }
   }

   @Override
   public boolean isTagContentTooltipEnabled() {
      return this.tagContentTooltipEnabled.get();
   }

   @Override
   public boolean getHideSingleTagContentTooltipEnabled() {
      return this.hideSingleTagContentTooltipEnabled.get();
   }

   @Override
   public boolean isShowTagRecipesEnabled() {
      return this.showTagRecipesEnabled.get();
   }

   @Override
   public boolean isShowCreativeTabNamesEnabled() {
      return this.showCreativeTabNamesEnabled.get();
   }
}
