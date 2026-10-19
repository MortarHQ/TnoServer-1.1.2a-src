package xaero.common.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.IXaeroMinimap;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.lib.client.gui.ISettingEntry;
import xaero.lib.client.gui.config.context.IEditConfigScreenContext;

public class GuiMinimapViewSettings extends GuiMinimapSettings {
   public GuiMinimapViewSettings(IXaeroMinimap modMain, Screen backScreen, Screen escScreen, IEditConfigScreenContext context) {
      super(Component.translatable("gui.xaero_minimap_view_settings"), backScreen, escScreen, context);
      this.entries = new ISettingEntry[]{
         this.optionEntry(MinimapProfiledConfigOptions.SIZE),
         this.optionEntry(MinimapProfiledConfigOptions.SHAPE),
         this.optionEntry(MinimapProfiledConfigOptions.NORTH_LOCKED),
         this.optionEntry(MinimapProfiledConfigOptions.LIGHTING),
         this.optionEntry(MinimapProfiledConfigOptions.ZOOM),
         this.optionEntry(MinimapProfiledConfigOptions.CAVE_ZOOM),
         this.optionEntry(MinimapProfiledConfigOptions.OPACITY),
         this.optionEntry(MinimapProfiledConfigOptions.FRAME),
         this.optionEntry(MinimapProfiledConfigOptions.ZOOM_ENLARGED),
         this.optionEntry(MinimapProfiledConfigOptions.FRAME_COLOR),
         this.optionEntry(MinimapProfiledConfigOptions.CENTERED_ENLARGED),
         this.optionEntry(MinimapProfiledConfigOptions.KEEP_ENLARGED_UNLOCKED),
         this.optionEntry(MinimapProfiledConfigOptions.TOGGLED_ENLARGED),
         this.optionEntry(MinimapProfiledConfigOptions.HIDE_UNDER_SCREEN),
         this.optionEntry(MinimapProfiledConfigOptions.HIDE_UNDER_F3),
         this.optionEntry(MinimapProfiledConfigOptions.BOSS_HEALTH_PUSH_BOX),
         this.optionEntry(MinimapProfiledConfigOptions.POTION_EFFECT_PUSH_BOX)
      };
   }
}
