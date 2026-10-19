package xaero.map.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.lib.client.config.option.ui.ConfigOptionScreenEntry;
import xaero.lib.client.gui.CustomSettingEntry;
import xaero.lib.client.gui.GuiSettings;
import xaero.lib.client.gui.ISettingEntry;
import xaero.lib.common.config.channel.ConfigChannel;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.util.ConfigConstants;
import xaero.lib.common.gui.widget.TooltipInfo;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.file.export.PNGExportResult;

public class ExportScreen extends GuiSettings {
   private static final Component EXPORTING_MESSAGE = Component.translatable("gui.xaero_export_screen_exporting");
   private final MapProcessor mapProcessor;
   private PNGExportResult result;
   private int stage;
   private final MapTileSelection selection;
   public boolean fullExport;

   public ExportScreen(Screen backScreen, Screen escScreen, MapProcessor mapProcessor, MapTileSelection selection) {
      super(Component.translatable("gui.xaero_export_screen"), backScreen, escScreen);
      this.mapProcessor = mapProcessor;
      this.selection = selection;
      ISettingEntry fullExportEntry = new CustomSettingEntry(
         () -> false,
         Component.translatable("gui.xaero_export_option_full"),
         new TooltipInfo("gui.xaero_box_export_option_full"),
         false,
         () -> this.fullExport,
         0,
         1,
         i -> i == 1,
         v -> v ? ConfigConstants.ON : ConfigConstants.OFF,
         (o, n) -> this.fullExport = n,
         () -> true
      );
      this.entries = new ISettingEntry[]{
         fullExportEntry,
         this.primaryOptionEntry(WorldMapPrimaryClientConfigOptions.EXPORT_MULTIPLE_IMAGES),
         this.primaryOptionEntry(WorldMapPrimaryClientConfigOptions.NIGHT_EXPORT),
         this.primaryOptionEntry(WorldMapPrimaryClientConfigOptions.EXPORT_HIGHLIGHTS),
         this.primaryOptionEntry(WorldMapPrimaryClientConfigOptions.EXPORT_SCALE_DOWN_SQUARE)
      };
      this.canSearch = false;
      this.confirmButton = true;
      this.canSkipWorldRender = true;
   }

   public void init() {
      if (this.stage <= 0) {
         super.init();
      }
   }

   protected void confirm() {
      this.stage = 1;
      this.init(this.minecraft, this.width, this.height);
   }

   public void render(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      this.renderEscapeScreen(guiGraphics, par1, par2, par3);
      super.render(guiGraphics, par1, par2, par3);
      if (this.result != null) {
         guiGraphics.drawCenteredString(this.minecraft.font, this.result.getMessage(), this.width / 2, this.height / 7 + 29 + 96, -1);
      }

      if (this.stage > 0) {
         guiGraphics.drawCenteredString(this.minecraft.font, EXPORTING_MESSAGE, this.width / 2, this.height / 6 + 68, -1);
         if (this.stage == 1) {
            this.stage = 2;
            return;
         }
      }

      if (this.stage == 2) {
         if (this.mapProcessor.getMapSaveLoad().exportPNG(this, this.fullExport ? null : this.selection)) {
            this.stage = 3;
            this.result = null;

            for (GuiEventListener c : this.children()) {
               if (c instanceof Button) {
                  ((Button)c).active = false;
               }
            }
         } else {
            this.stage = 0;
            this.init(this.minecraft, this.width, this.height);
         }
      }
   }

   public <T> ConfigOptionScreenEntry<T> primaryOptionEntry(ConfigOption<T> option) {
      ConfigChannel channel = WorldMap.INSTANCE.getConfigs();
      return new ConfigOptionScreenEntry(
         option,
         () -> channel.getPrimaryClientConfigManager().getConfig(),
         () -> null,
         () -> channel.getPrimaryClientConfigManagerIO().save(),
         channel,
         true,
         false
      );
   }

   public void onExportDone(PNGExportResult result) {
      this.result = result;
      this.stage = 0;
   }

   public MapTileSelection getSelection() {
      return this.selection;
   }
}
