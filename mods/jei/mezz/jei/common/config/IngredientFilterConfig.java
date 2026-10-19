package mezz.jei.common.config;

import java.util.function.Supplier;
import mezz.jei.common.config.file.IConfigCategoryBuilder;
import mezz.jei.common.config.file.IConfigSchemaBuilder;
import mezz.jei.core.search.SearchMode;

public class IngredientFilterConfig implements IIngredientFilterConfig {
   public final Supplier<SearchMode> modNameSearchMode;
   public final Supplier<SearchMode> tooltipSearchMode;
   public final Supplier<SearchMode> tagSearchMode;
   public final Supplier<SearchMode> colorSearchMode;
   public final Supplier<SearchMode> resourceLocationSearchMode;
   public final Supplier<SearchMode> creativeTabSearchMode;
   public final Supplier<Boolean> searchAdvancedTooltips;
   public final Supplier<Boolean> searchModIds;
   public final Supplier<Boolean> searchModAliases;
   public final Supplier<Boolean> searchShortModNames;
   public final Supplier<Boolean> searchIngredientAliases;

   public IngredientFilterConfig(IConfigSchemaBuilder builder) {
      IConfigCategoryBuilder search = builder.addCategory("search");
      this.modNameSearchMode = search.addEnum("modNameSearchMode", SearchMode.REQUIRE_PREFIX);
      this.tagSearchMode = search.addEnum("tagSearchMode", SearchMode.REQUIRE_PREFIX);
      this.tooltipSearchMode = search.addEnum("tooltipSearchMode", SearchMode.ENABLED);
      this.colorSearchMode = search.addEnum("colorSearchMode", SearchMode.DISABLED);
      this.resourceLocationSearchMode = search.addEnum("resourceLocationSearchMode", SearchMode.DISABLED);
      this.creativeTabSearchMode = search.addEnum("creativeTabSearchMode", SearchMode.DISABLED);
      this.searchAdvancedTooltips = search.addBoolean("searchAdvancedTooltips", false);
      this.searchModIds = search.addBoolean("searchModIds", true);
      this.searchModAliases = search.addBoolean("searchModAliases", true);
      this.searchShortModNames = search.addBoolean("searchShortModNames", false);
      this.searchIngredientAliases = search.addBoolean("searchIngredientAliases", true);
   }

   @Override
   public SearchMode getModNameSearchMode() {
      return this.modNameSearchMode.get();
   }

   @Override
   public SearchMode getTooltipSearchMode() {
      return this.tooltipSearchMode.get();
   }

   @Override
   public SearchMode getTagSearchMode() {
      return this.tagSearchMode.get();
   }

   @Override
   public SearchMode getColorSearchMode() {
      return this.colorSearchMode.get();
   }

   @Override
   public SearchMode getResourceLocationSearchMode() {
      return this.resourceLocationSearchMode.get();
   }

   @Override
   public SearchMode getCreativeTabSearchMode() {
      return this.creativeTabSearchMode.get();
   }

   @Override
   public boolean getSearchAdvancedTooltips() {
      return this.searchAdvancedTooltips.get();
   }

   @Override
   public boolean getSearchModIds() {
      return this.searchModIds.get();
   }

   @Override
   public boolean getSearchModAliases() {
      return this.searchModAliases.get();
   }

   @Override
   public boolean getSearchIngredientAliases() {
      return this.searchIngredientAliases.get();
   }

   @Override
   public boolean getSearchShortModNames() {
      return this.searchShortModNames.get();
   }
}
