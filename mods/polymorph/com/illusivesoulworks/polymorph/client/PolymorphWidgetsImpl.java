package com.illusivesoulworks.polymorph.client;

import com.illusivesoulworks.polymorph.PolymorphConstants;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import com.illusivesoulworks.polymorph.api.client.base.IRecipesWidget;
import com.illusivesoulworks.polymorph.api.client.widgets.CrafterRecipesWidget;
import com.illusivesoulworks.polymorph.api.client.widgets.FurnaceRecipesWidget;
import com.illusivesoulworks.polymorph.api.client.widgets.PlayerRecipesWidget;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.CrafterMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmithingMenu;

public class PolymorphWidgetsImpl extends PolymorphWidgets {
   public static final PolymorphWidgets INSTANCE = new PolymorphWidgetsImpl();
   private final List<PolymorphWidgets.IRecipesWidgetFactory> widgetFactories = new CopyOnWriteArrayList<>();

   public static void setup() {
      INSTANCE.registerWidget(containerScreen -> {
         AbstractContainerMenu container = containerScreen.getMenu();

         return switch (container) {
            case SmithingMenu smithingMenu -> new PlayerRecipesWidget(containerScreen, (Slot)container.slots.get(3));
            case AbstractFurnaceMenu abstractFurnaceMenu -> new FurnaceRecipesWidget(containerScreen);
            case CrafterMenu crafterMenu -> new CrafterRecipesWidget(containerScreen);
            default -> null;
         };
      });
   }

   @Override
   public IRecipesWidget getCurrentWidget() {
      return RecipesWidget.get().orElse(null);
   }

   @Override
   public IRecipesWidget getWidgetOrDefault(AbstractContainerScreen<?> containerScreen) {
      IRecipesWidget widget = this.getWidget(containerScreen);
      if (widget == null) {
         Slot slot = this.findResultSlot(containerScreen);
         if (slot != null) {
            widget = new PlayerRecipesWidget(containerScreen, slot);
         }
      }

      return widget;
   }

   @Override
   public IRecipesWidget getWidget(AbstractContainerScreen<?> pContainerScreen) {
      for (PolymorphWidgets.IRecipesWidgetFactory factory : this.widgetFactories) {
         IRecipesWidget widget = factory.createWidget(pContainerScreen);
         if (widget != null) {
            return widget;
         }
      }

      return null;
   }

   @Override
   public void registerWidget(PolymorphWidgets.IRecipesWidgetFactory pFactory) {
      if (pFactory == null) {
         PolymorphConstants.LOG.error("Attempted to register a null IRecipesWidgetFactory");
      } else {
         this.widgetFactories.add(pFactory);
      }
   }

   @Override
   public Slot findResultSlot(AbstractContainerScreen<?> pContainerScreen) {
      AbstractContainerMenu container = pContainerScreen.getMenu();

      for (Slot slot : container.slots) {
         if (slot.container instanceof ResultContainer) {
            return slot;
         }
      }

      return null;
   }
}
