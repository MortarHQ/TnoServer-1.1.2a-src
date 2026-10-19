package dev.ftb.mods.ftbfiltersystem.api.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public abstract class AbstractFilterScreen extends Screen {
   protected AbstractFilterScreen(Component component) {
      super(component);
   }

   public abstract void findAndSelect(SmartFilter var1);

   public abstract <T extends SmartFilter> void replaceFilter(T var1, T var2);

   public abstract void deleteSelectedFilter(boolean var1);
}
