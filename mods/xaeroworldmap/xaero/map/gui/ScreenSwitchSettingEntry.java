package xaero.map.gui;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.ISettingEntry;
import xaero.lib.client.gui.widget.Tooltip;

public class ScreenSwitchSettingEntry implements ISettingEntry {
   private String name;
   private BiFunction<Screen, Screen, Screen> screenFactory;
   private Supplier<Tooltip> tooltipSupplier;
   private boolean active;
   private final boolean consideredAnExit;

   public ScreenSwitchSettingEntry(String name, BiFunction<Screen, Screen, Screen> screenFactoryFromCurrentAndEscape, Tooltip tooltip, boolean active) {
      this(name, screenFactoryFromCurrentAndEscape, tooltip, active, true);
   }

   public ScreenSwitchSettingEntry(
      String name, BiFunction<Screen, Screen, Screen> screenFactoryFromCurrentAndEscape, Tooltip tooltip, boolean active, boolean consideredAnExit
   ) {
      this.name = name;
      this.screenFactory = screenFactoryFromCurrentAndEscape;
      this.tooltipSupplier = () -> tooltip;
      this.active = active;
      this.consideredAnExit = consideredAnExit;
   }

   public String getStringForSearch() {
      Tooltip entryTooltip = this.tooltipSupplier == null ? null : this.tooltipSupplier.get();
      String tooltipFullCode = entryTooltip == null ? null : entryTooltip.getFullCode();
      return I18n.get(this.name, new Object[0])
         + " "
         + this.name.replace("gui.xaero", "")
         + (tooltipFullCode != null ? " " + tooltipFullCode.replace("gui.xaero", "") : "")
         + (entryTooltip != null ? " " + entryTooltip.getPlainText() : "");
   }

   public AbstractWidget createWidget(int x, int y, int w) {
      TooltipButton button = new TooltipButton(x, y, w, 20, Component.translatable(this.name), b -> {
         Minecraft mc = Minecraft.getInstance();
         Screen current = mc.screen;
         Screen currentEscScreen = current instanceof xaero.lib.client.gui.ScreenBase ? ((xaero.lib.client.gui.ScreenBase)current).escape : null;
         Screen targetScreen = this.screenFactory.apply(current, currentEscScreen);
         if (this.consideredAnExit && current instanceof xaero.lib.client.gui.ScreenBase) {
            ((xaero.lib.client.gui.ScreenBase)current).onExit(targetScreen);
         } else {
            mc.setScreen(targetScreen);
         }
      }, this.tooltipSupplier);
      button.active = this.active;
      return button;
   }

   public BiFunction<Screen, Screen, Screen> getScreenFactory() {
      return this.screenFactory;
   }
}
