package mezz.jei.common.config;

import java.util.List;
import java.util.Set;
import mezz.jei.common.config.file.IConfigListener;

public interface IClientConfig {
   int minRecipeGuiHeight = 175;
   int defaultRecipeGuiHeight = 350;
   boolean defaultCenterSearchBar = false;

   boolean isCenterSearchBarEnabled();

   boolean isLowMemorySlowSearchEnabled();

   boolean isCatchRenderErrorsEnabled();

   boolean isCheatToHotbarUsingHotkeysEnabled();

   boolean isAddingBookmarksToFrontEnabled();

   boolean isLookupFluidContentsEnabled();

   boolean isLookupBlockTagsEnabled();

   GiveMode getGiveMode();

   boolean getShowHiddenIngredients();

   List<BookmarkTooltipFeature> getBookmarkTooltipFeatures();

   boolean isHoldShiftToShowBookmarkTooltipFeaturesEnabled();

   boolean isDragToRearrangeBookmarksEnabled();

   boolean isLookupHistoryEnabled();

   void setLookupHistoryEnabled(boolean var1);

   void addLookupHistoryEnabledListener(IConfigListener<Boolean> var1);

   int getMaxLookupHistoryRows();

   int getMaxLookupHistoryIngredients();

   HistoryDisplaySide getLookupHistoryDisplaySide();

   void addLookupHistoryDisplaySideListener(IConfigListener<HistoryDisplaySide> var1);

   boolean isIngredientsSummaryEnabled();

   int getDragDelayMs();

   int getSmoothScrollRate();

   int getMaxRecipeGuiHeight();

   List<IngredientSortStage> getIngredientSorterStages();

   Set<RecipeSorterStage> getRecipeSorterStages();

   void enableRecipeSorterStage(RecipeSorterStage var1);

   void disableRecipeSorterStage(RecipeSorterStage var1);

   boolean isTagContentTooltipEnabled();

   boolean getHideSingleTagContentTooltipEnabled();

   boolean isShowTagRecipesEnabled();

   boolean isShowCreativeTabNamesEnabled();
}
