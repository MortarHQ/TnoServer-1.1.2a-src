package mezz.jei.common.config;

import java.util.function.Supplier;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.common.config.file.IConfigCategoryBuilder;
import mezz.jei.common.config.file.IConfigSchemaBuilder;
import mezz.jei.common.util.NavigationVisibility;

public class IngredientGridConfig implements IIngredientGridConfig {
   private static final int minNumRows = 1;
   private static final int defaultNumRows = 16;
   private static final int largestNumRows = 100;
   private static final int minNumColumns = 2;
   private static final int defaultNumColumns = 9;
   private static final int largestNumColumns = 100;
   private static final VerticalAlignment defaultVerticalAlignment = VerticalAlignment.TOP;
   private static final NavigationVisibility defaultButtonNavigationVisibility = NavigationVisibility.ENABLED;
   private static final boolean defaultDrawBackground = false;
   private final Supplier<Integer> maxRows;
   private final Supplier<Integer> maxColumns;
   private final Supplier<HorizontalAlignment> horizontalAlignment;
   private final Supplier<VerticalAlignment> verticalAlignment;
   private final Supplier<NavigationVisibility> buttonNavigationVisibility;
   private final Supplier<Boolean> drawBackground;

   public IngredientGridConfig(String categoryName, IConfigSchemaBuilder builder, HorizontalAlignment defaultHorizontalAlignment) {
      IConfigCategoryBuilder category = builder.addCategory(categoryName);
      this.maxRows = category.addInteger("maxRows", 16, 1, 100);
      this.maxColumns = category.addInteger("maxColumns", 9, 2, 100);
      this.horizontalAlignment = category.addEnum("horizontalAlignment", defaultHorizontalAlignment);
      this.verticalAlignment = category.addEnum("verticalAlignment", defaultVerticalAlignment);
      this.buttonNavigationVisibility = category.addEnum("buttonNavigationVisibility", defaultButtonNavigationVisibility);
      this.drawBackground = category.addBoolean("drawBackground", false);
   }

   @Override
   public int getMinColumns() {
      return 2;
   }

   @Override
   public int getMinRows() {
      return 1;
   }

   @Override
   public HorizontalAlignment getHorizontalAlignment() {
      return this.horizontalAlignment.get();
   }

   @Override
   public VerticalAlignment getVerticalAlignment() {
      return this.verticalAlignment.get();
   }

   @Override
   public boolean drawBackground() {
      return this.drawBackground.get();
   }

   @Override
   public int getMaxColumns() {
      return this.maxColumns.get();
   }

   @Override
   public int getMaxRows() {
      return this.maxRows.get();
   }

   @Override
   public NavigationVisibility getButtonNavigationVisibility() {
      return this.buttonNavigationVisibility.get();
   }
}
