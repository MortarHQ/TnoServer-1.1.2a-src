package twilightforest.compat.rei.categories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import twilightforest.compat.rei.displays.REICrumbleHornDisplay;
import twilightforest.init.TFItems;

public class REICrumbleHornCategory implements DisplayCategory<REICrumbleHornDisplay> {
   public static final CategoryIdentifier<REICrumbleHornDisplay> CRUMBLE_HORN = CategoryIdentifier.of("twilightforest", "crumble_horn");
   private final Renderer icon = EntryStacks.of(TFItems.CRUMBLE_HORN);
   private final Component localizedName = Component.translatable("gui.twilightforest.crumble_horn_jei");

   public CategoryIdentifier<? extends REICrumbleHornDisplay> getCategoryIdentifier() {
      return CRUMBLE_HORN;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public Renderer getIcon() {
      return this.icon;
   }

   public int getDisplayWidth(REICrumbleHornDisplay display) {
      return 124;
   }

   public int getDisplayHeight() {
      return 62;
   }

   public List<Widget> setupDisplay(REICrumbleHornDisplay display, Rectangle origin) {
      List<Widget> widgets = new ArrayList<>();
      widgets.add(Widgets.createRecipeBase(origin));
      Rectangle bounds = origin.getBounds();
      bounds.translate(4, 4);
      widgets.add(Widgets.createArrow(new Point(bounds.getX() + 46, bounds.getY() + 19)));
      widgets.add(Widgets.createSlot(new Point(bounds.getX() + 19, bounds.getY() + 19)).markInput().entries((Collection)display.getInputEntries().getFirst()));
      if (!display.isResultAir) {
         widgets.add(Widgets.createResultSlotBackground(new Point(bounds.getX() + 81, bounds.getY() + 19)));
         widgets.add(
            Widgets.createSlot(new Point(bounds.getX() + 81, bounds.getY() + 19))
               .markOutput()
               .disableBackground()
               .entries((Collection)display.getOutputEntries().getFirst())
         );
      } else {
         widgets.add(
            Widgets.createSlot(new Rectangle(new Point(bounds.getX() + 75, bounds.getY() + 12), new Dimension(32, 32)))
               .markOutput()
               .disableHighlight()
               .disableBackground()
               .entries((Collection)display.getOutputEntries().getFirst())
         );
      }

      return widgets;
   }
}
