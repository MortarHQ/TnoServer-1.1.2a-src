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
import twilightforest.compat.rei.displays.REIOminousFireDisplay;
import twilightforest.init.TFItems;

public class REIOminousFireCategory implements DisplayCategory<REIOminousFireDisplay> {
   public static final CategoryIdentifier<REIOminousFireDisplay> OMINOUS_FIRE = CategoryIdentifier.of("twilightforest", "ominous_fire");
   private final Renderer icon = EntryStacks.of(TFItems.EXANIMATE_ESSENCE);
   private final Component localizedName = Component.translatable("gui.twilightforest.ominous_fire_jei");

   public CategoryIdentifier<? extends REIOminousFireDisplay> getCategoryIdentifier() {
      return OMINOUS_FIRE;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public Renderer getIcon() {
      return this.icon;
   }

   public int getDisplayHeight() {
      return 62;
   }

   public int getDisplayWidth(REIOminousFireDisplay display) {
      return 124;
   }

   public List<Widget> setupDisplay(REIOminousFireDisplay display, Rectangle origin) {
      List<Widget> widgets = new ArrayList<>();
      widgets.add(Widgets.createRecipeBase(origin));
      Rectangle bounds = origin.getBounds();
      bounds.translate(4, 4);
      widgets.add(Widgets.createArrow(new Point(bounds.getX() + 46, bounds.getY() + 19)));
      widgets.add(
         Widgets.createSlot(new Point(bounds.getX() + 48, bounds.getY() + 45))
            .disableBackground()
            .disableHighlight()
            .notInteractable()
            .entries((Collection)display.getInputEntries().get(2))
      );
      REITransformationPowderCategory.createEntitySlots(display, widgets, bounds);
      return widgets;
   }
}
