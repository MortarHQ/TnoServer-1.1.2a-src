package twilightforest.compat.rei.categories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.rei.displays.REIDryingDisplay;
import twilightforest.init.TFBlocks;

public class REIDryingCategory implements DisplayCategory<REIDryingDisplay> {
   public static final CategoryIdentifier<REIDryingDisplay> DRYING = CategoryIdentifier.of("twilightforest", "drying");
   private final Renderer icon = EntryStacks.of(TFBlocks.SORTING_DRYING_RACK);
   private final Component localizedName = Component.translatable("gui.twilightforest.drying_jei");

   public CategoryIdentifier<? extends REIDryingDisplay> getCategoryIdentifier() {
      return DRYING;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public Renderer getIcon() {
      return this.icon;
   }

   public int getDisplayWidth(REIDryingDisplay display) {
      return 70;
   }

   public int getDisplayHeight() {
      return 38;
   }

   public List<Widget> setupDisplay(REIDryingDisplay display, Rectangle origin) {
      List<Widget> widgets = new ArrayList<>();
      widgets.add(Widgets.createRecipeBase(origin));
      Rectangle bounds = origin.getBounds();
      bounds.translate(4, 4);
      widgets.add(Widgets.createArrow(new Point(bounds.getX() + 19, bounds.getY() + 2)).animationDurationTicks(1200.0));
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() + 1, bounds.getY() + 3))
            .markInput()
            .disableBackground()
            .entries((Collection)display.getInputEntries().getFirst())
      );
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() + 45, bounds.getY() + 3))
            .markOutput()
            .disableBackground()
            .entries((Collection)display.getOutputEntries().getFirst())
      );
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() - 1, bounds.getY() + 21))
            .disableHighlight()
            .disableBackground()
            .notInteractable()
            .entries((Collection)display.getInputEntries().get(1))
      );
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() + 43, bounds.getY() + 21))
            .disableHighlight()
            .disableBackground()
            .notInteractable()
            .entries((Collection)display.getInputEntries().get(1))
      );
      Component text = RecipeViewerConstants.getDryingTime(display.getDryingTime());
      widgets.add(Widgets.createLabel(new Point(bounds.getX() + 32, bounds.getY() + 22), text));
      return widgets;
   }
}
