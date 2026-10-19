package com.illusivesoulworks.polymorph.api.client;

import com.illusivesoulworks.polymorph.api.client.base.IRecipesWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

public abstract class PolymorphWidgets {
   private static PolymorphWidgets instance;

   public static PolymorphWidgets getInstance() {
      if (instance == null) {
         throw new RuntimeException("Missing PolymorphWidgets implementation!");
      } else {
         return instance;
      }
   }

   public abstract IRecipesWidget getCurrentWidget();

   public abstract IRecipesWidget getWidgetOrDefault(AbstractContainerScreen<?> var1);

   public abstract IRecipesWidget getWidget(AbstractContainerScreen<?> var1);

   public abstract void registerWidget(PolymorphWidgets.IRecipesWidgetFactory var1);

   public abstract Slot findResultSlot(AbstractContainerScreen<?> var1);

   public interface IRecipesWidgetFactory {
      IRecipesWidget createWidget(AbstractContainerScreen<?> var1);
   }
}
