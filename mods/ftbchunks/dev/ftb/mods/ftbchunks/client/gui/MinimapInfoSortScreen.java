package dev.ftb.mods.ftbchunks.client.gui;

import com.google.common.collect.UnmodifiableIterator;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import dev.ftb.mods.ftbchunks.api.client.minimap.TranslatedOption;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.ToggleableButton;
import dev.ftb.mods.ftblibrary.ui.SimpleButton.Callback;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractThreePanelScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.ClientTextComponentUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MinimapInfoSortScreen extends AbstractThreePanelScreen<MinimapInfoSortScreen.MinimapInfoSortEntry> {
   private List<ResourceLocation> infoSortList = new LinkedList<>();

   public MinimapInfoSortScreen() {
      this.setHeight(200);
      this.setWidth(200);
      ((List)FTBChunksClientConfig.MINIMAP_INFO_ORDER.get()).forEach(s -> this.infoSortList.add(ResourceLocation.parse(s)));
      this.showBottomPanel(false);
      this.setRenderBlur(false);
   }

   protected void doCancel() {
   }

   protected void doAccept() {
   }

   protected int getTopPanelHeight() {
      return 22;
   }

   protected MinimapInfoSortScreen.MinimapInfoSortEntry createMainPanel() {
      return new MinimapInfoSortScreen.MinimapInfoSortEntry(this);
   }

   protected Panel createTopPanel() {
      return new MinimapInfoSortScreen.CustomTopPanel();
   }

   private void saveConfig() {
      List<String> list = new LinkedList<>();
      this.infoSortList.forEach(resourceLocation -> list.add(resourceLocation.toString()));
      FTBChunksClientConfig.MINIMAP_INFO_ORDER.set(list);
      FTBChunksClientConfig.saveConfig();
      FTBChunksClient.INSTANCE.setupComponents();
   }

   private boolean isComponentDisabled(MinimapInfoComponent component) {
      return !FTBChunksAPI.clientApi().isMinimapComponentEnabled(component);
   }

   private void setComponentEnabled(MinimapInfoComponent component, boolean enabled) {
      FTBChunksAPI.clientApi().setMinimapComponentEnabled(component, enabled);
   }

   private boolean isTranslatedOptionEnabled(MinimapInfoComponent component, TranslatedOption option) {
      Map<String, String> stringStringMap = (Map<String, String>)FTBChunksClientConfig.MINIMAP_SETTINGS.get();
      if (!stringStringMap.containsKey(component.id().toString())) {
         stringStringMap.put(component.id().toString(), option.optionName());
      }

      return stringStringMap.get(component.id().toString()).equals(option.optionName());
   }

   private void setTranslatedOptionEnabled(MinimapInfoComponent component, TranslatedOption option) {
      Map<String, String> stringStringMap = (Map<String, String>)FTBChunksClientConfig.MINIMAP_SETTINGS.get();
      stringStringMap.put(component.id().toString(), option.optionName());
      FTBChunksClientConfig.MINIMAP_SETTINGS.set(stringStringMap);
      FTBChunksClientConfig.saveConfig();
   }

   protected class CustomTopPanel extends AbstractThreePanelScreen<MinimapInfoSortScreen.MinimapInfoSortEntry>.TopPanel {
      private final TextField titleLabel = new TextField(this);

      protected CustomTopPanel() {
         super(MinimapInfoSortScreen.this);
      }

      public void addWidgets() {
         this.titleLabel.setText(Component.translatable("ftbchunks.gui.sort_minimap_info"));
         this.titleLabel.addFlags(32);
         this.add(this.titleLabel);
      }

      public void alignWidgets() {
         this.titleLabel.setPosAndSize(4, 0, this.titleLabel.width, this.height);
      }
   }

   public class InfoEntry extends Panel {
      private final Button down;
      private final Button up;
      private final SimpleButton hideButton;
      private final TextField field;
      private final MinimapInfoComponent infoComponent;
      private final SimpleButton configButton;

      public InfoEntry(MinimapInfoComponent infoComponent, Panel panel) {
         super(panel);
         this.hideButton = new ToggleableButton(
            this,
            !MinimapInfoSortScreen.this.isComponentDisabled(infoComponent),
            Icons.VISIBILITY_SHOW,
            Icons.VISIBILITY_HIDE,
            (widget, newState) -> MinimapInfoSortScreen.this.setComponentEnabled(infoComponent, newState)
         );
         this.down = new MinimapInfoSortScreen.SortScreenButton(
            this, Component.translatable("ftbchunks.gui.move_up"), Icons.UP, (widget, button) -> this.move(false, isShiftKeyDown())
         );
         this.up = new MinimapInfoSortScreen.SortScreenButton(
            this, Component.translatable("ftbchunks.gui.move_down"), Icons.DOWN, (widget, button) -> this.move(true, isShiftKeyDown())
         );
         this.configButton = new SimpleButton(
            this,
            Component.translatable("gui.settings"),
            Icons.SETTINGS,
            (widget, button) -> {
               List<ContextMenuItem> items = new ArrayList<>();

               for (TranslatedOption translatedOption : infoComponent.getConfigComponents()) {
                  ContextMenuItem item = new ContextMenuItem(
                     Component.translatable(translatedOption.translationKey()),
                     MinimapInfoSortScreen.this.isTranslatedOptionEnabled(infoComponent, translatedOption) ? Icons.ACCEPT : Icons.REMOVE_GRAY,
                     button1 -> MinimapInfoSortScreen.this.setTranslatedOptionEnabled(infoComponent, translatedOption)
                  );
                  items.add(item);
               }

               MinimapInfoSortScreen.this.openContextMenu(items);
               this.playClickSound();
            }
         ) {
            public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
               super.drawIcon(graphics, theme, x, y, 12, 12);
            }
         };
         this.field = new TextField(this) {
            public void addMouseOverText(TooltipList list) {
               list.add(infoComponent.description());
            }
         };
         this.infoComponent = infoComponent;
      }

      public void addWidgets() {
         int listIndex = MinimapInfoSortScreen.this.infoSortList.indexOf(this.infoComponent.id());
         if (listIndex != 0) {
            this.add(this.down);
         }

         if (listIndex != MinimapInfoSortScreen.this.infoSortList.size() - 1) {
            this.add(this.up);
         }

         this.add(this.hideButton);
         this.add(this.field);
         if (!this.infoComponent.getConfigComponents().isEmpty()) {
            this.add(this.configButton);
         }
      }

      public void alignWidgets() {
         this.down.setPosAndSize(6, this.height / 6 + 1, 6, 8);
         this.up.setPosAndSize(6, this.height / 6 + 11, 6, 8);
         this.hideButton.setPosAndSize(this.width - 18, this.height / 6 + 2, 12, 12);
         this.field.setPosAndSize(16, 8, this.width - 37, this.height);
         this.field.setPosAndSize(16, 8, this.width - 37, this.height);
         if (!this.infoComponent.getConfigComponents().isEmpty()) {
            this.configButton.setPos(this.width - 18 - 14, this.height / 6 + 2);
         }

         this.field
            .setText(
               ClientTextComponentUtils.ellipsize(
                     MinimapInfoSortScreen.this.getTheme().getFont(), this.infoComponent.displayName(), this.hideButton.getPosX() - 14 - 5
                  )
                  .getString()
            );
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         theme.drawWidget(graphics, x, y, w, h, this.getWidgetType());
         if (this.isMouseOver()) {
            Color4I.WHITE.withAlpha(33).draw(graphics, x, y, w, h);
         }

         super.draw(graphics, theme, x, y, w, h);
      }

      private void move(boolean forward, boolean end) {
         List<ResourceLocation> list = new LinkedList<>(MinimapInfoSortScreen.this.infoSortList);
         if (end) {
            if (forward) {
               list.remove(this.infoComponent.id());
               list.add(this.infoComponent.id());
            } else {
               list.remove(this.infoComponent.id());
               list.addFirst(this.infoComponent.id());
            }
         } else {
            int index = list.indexOf(this.infoComponent.id());
            if (index == -1) {
               return;
            }

            if (forward) {
               if (index == list.size() - 1) {
                  return;
               }

               list.remove(index);
               list.add(index + 1, this.infoComponent.id());
            } else {
               if (index == 0) {
                  return;
               }

               list.remove(index);
               list.add(index - 1, this.infoComponent.id());
            }
         }

         MinimapInfoSortScreen.this.infoSortList = list;
         MinimapInfoSortScreen.this.saveConfig();
         this.parent.refreshWidgets();
      }
   }

   public class MinimapInfoSortEntry extends Panel {
      private final Map<ResourceLocation, MinimapInfoSortScreen.InfoEntry> entryMap = new HashMap<>();

      public MinimapInfoSortEntry(Panel panel) {
         super(panel);
         UnmodifiableIterator var3 = FTBChunksAPI.clientApi().getMinimapComponents().iterator();

         while (var3.hasNext()) {
            MinimapInfoComponent minimapComponent = (MinimapInfoComponent)var3.next();
            this.entryMap.put(minimapComponent.id(), MinimapInfoSortScreen.this.new InfoEntry(minimapComponent, this));
         }
      }

      public void addWidgets() {
         this.addAll(this.entryMap.values());
      }

      public void alignWidgets() {
         int height = 0;

         for (ResourceLocation id : MinimapInfoSortScreen.this.infoSortList) {
            MinimapInfoSortScreen.InfoEntry infoEntry = this.entryMap.get(id);
            if (infoEntry != null) {
               infoEntry.setPosAndSize(0, height, this.width, 24);
               infoEntry.alignWidgets();
               height += 24;
            }
         }
      }
   }

   public static class SortScreenButton extends SimpleButton {
      public SortScreenButton(Panel panel, Component text, Icon icon, Callback c) {
         super(panel, text, icon, c);
      }

      public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.drawIcon(graphics, theme, x - 1, y - 2, 6, 8);
      }
   }
}
