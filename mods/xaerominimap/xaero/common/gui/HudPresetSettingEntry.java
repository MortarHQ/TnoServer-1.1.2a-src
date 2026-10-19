package xaero.common.gui;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import xaero.hud.preset.HudPreset;
import xaero.hud.preset.HudPresetManager;
import xaero.lib.client.gui.ISettingEntry;

public class HudPresetSettingEntry implements ISettingEntry {
   private final HudPresetManager manager;
   private final HudPreset preset;
   private final String searchString;

   public HudPresetSettingEntry(HudPresetManager manager, HudPreset preset) {
      this.manager = manager;
      this.preset = preset;
      StringBuilder searchStringBuilder = new StringBuilder();
      searchStringBuilder.append(preset.getId()).append(" ");
      preset.getName().visit(s -> {
         searchStringBuilder.append(s);
         return Optional.empty();
      });
      this.searchString = searchStringBuilder.toString();
   }

   public String getStringForSearch() {
      return this.searchString;
   }

   public AbstractWidget createWidget(int x, int y, int w) {
      return Button.builder(this.preset.getName(), b -> {
         for (HudPreset preset : this.manager.getPresets()) {
            preset.cancel();
         }

         this.preset.apply();
         if (Minecraft.getInstance().screen instanceof GuiChoosePreset gui) {
            gui.goBack();
         }
      }).bounds(x, y, w, 20).build();
   }
}
