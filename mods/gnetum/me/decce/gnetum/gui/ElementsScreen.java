package me.decce.gnetum.gui;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import me.decce.gnetum.CacheSetting;
import me.decce.gnetum.Gnetum;
import me.decce.gnetum.gui.widgets.IntSlider;
import me.decce.gnetum.gui.widgets.ToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.apache.commons.lang3.StringUtils;

public class ElementsScreen extends BaseScreen {
   private final Map<String, CacheSetting> rawMap;
   private Map<String, CacheSetting> map;
   private boolean vanilla;
   private int page;
   private int pageCount;
   private String searchText = "";
   private EditBox searchBox;
   private Button btnPrevPage;
   private Button btnNextPage;

   public ElementsScreen(Map<String, CacheSetting> map, boolean vanilla) {
      this.vanilla = vanilla;
      this.rawMap = map;
      this.updateFilter();
   }

   @Override
   public void init() {
      this.setPage(0);
   }

   @Override
   protected void rebuild() {
      super.rebuild();
      int max = Math.min(this.map.size(), this.page * 10 + 10);
      int wb = 130;
      int ws = 50;
      int extrawb = this.width - wb * 2 - ws * 2 - 60;
      extrawb = Math.max(0, Math.min(60, extrawb));
      wb += extrawb;
      int h = 20;
      int margin = 5;
      int xlb = this.width / 2 - 2 - ws - wb;
      int xls = this.width / 2 - 2 - ws;
      int xrb = this.width / 2 + 2;
      int xrs = this.width / 2 + 2 + wb;
      int y = this.height / 2 - h / 2 - margin - h - margin - h - 15;
      int i = 0;
      if (this.searchBox == null) {
         this.searchBox = new EditBox(Minecraft.getInstance().font, 0, 0, 0, 0, Component.literal(this.searchText));
         this.searchBox.setHint(Component.translatable("gnetum.config.searchHint"));
         this.searchBox.setResponder(s -> {
            if (!this.searchText.equals(s)) {
               this.searchText = s;
               this.updateFilter();
            }
         });
      }

      this.searchBox.setX(xlb + 2);
      this.searchBox.setY(y - h - margin);
      this.searchBox.setWidth(2 * wb + 2 * ws);
      this.searchBox.setHeight(h);
      this.addRenderableWidget(this.searchBox);
      boolean left = true;

      for (Entry<String, CacheSetting> entry : this.map.entrySet()) {
         if (i >= 10 * this.page && i < max) {
            ToggleButton btn = new ToggleButton(
               left ? xlb : xrb, y, wb, h, this.map.get(entry.getKey()).enabled, () -> this.beautifyString(entry.getKey()) + ": %s"
            );
            btn.setTooltip(
               () -> {
                  String line1 = btn.getMessage().getString() + "\n(" + entry.getKey() + ")";
                  String line2 = I18n.get(
                     "gnetum.config.tooltip." + entry.getValue().enabled.value.name().toLowerCase(Locale.ROOT),
                     new Object[]{entry.getValue().enabled.effectiveText()}
                  );
                  return line1 + "\n\n" + line2;
               }
            );
            this.addRenderableWidget(btn);
            IntSlider slider = new IntSlider(
               left ? xls : xrs,
               y,
               ws,
               h,
               () -> I18n.get("gnetum.config.pass", new Object[0]) + ": %s",
               1,
               Gnetum.config.numberOfPasses,
               entry.getValue().pass,
               true,
               pass -> entry.getValue().pass = pass
            );
            this.addRenderableWidget(slider);
            if (!left) {
               y += h + margin;
            }

            left = !left;
         }

         i++;
      }

      y = this.height / 2 + h / margin + h + margin + h + margin;
      int lineHeight = 9;
      int currentPage = this.map.isEmpty() ? 0 : this.page + 1;
      String pageString = currentPage + " / " + this.pageCount;
      int stringWidth = Minecraft.getInstance().font.width(pageString);
      StringWidget page = new StringWidget(
         this.width / 2 - stringWidth / 2, y + lineHeight / 2, stringWidth, lineHeight, Component.literal(pageString), Minecraft.getInstance().font
      );
      this.btnPrevPage = Button.builder(Component.literal("<"), btn -> this.setPage(this.page - 1)).pos(xlb, y).size(20, 20).build();
      this.btnNextPage = Button.builder(Component.literal(">"), btn -> this.setPage(this.page + 1)).pos(xrs + ws - 20, y).size(20, 20).build();
      this.btnPrevPage.active = this.page > 0;
      this.btnNextPage.active = this.page < this.pageCount - 1;
      this.addRenderableWidget(page);
      this.addRenderableWidget(this.btnPrevPage);
      this.addRenderableWidget(this.btnNextPage);
      super.addDoneButton();
      this.setFocused(this.searchBox);
   }

   @Override
   protected void renderTitle(GuiGraphics graphics) {
   }

   private void updateFilter() {
      LinkedHashMap<String, CacheSetting> sorted = this.rawMap
         .entrySet()
         .stream()
         .sorted((o1, o2) -> this.beautifyString(o1.getKey()).compareTo(this.beautifyString(o2.getKey())))
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
      this.map = Maps.filterEntries(sorted, entry -> !((CacheSetting)entry.getValue()).hidden && this.shouldDisplay((String)entry.getKey()));
      this.pageCount = this.map.size() % 10 == 0 ? this.map.size() / 10 : this.map.size() / 10 + 1;
      this.setPage(0);
   }

   private boolean shouldDisplay(String entry) {
      return StringUtils.containsIgnoreCase(entry, this.searchText) || StringUtils.containsIgnoreCase(this.beautifyString(entry), this.searchText);
   }

   private void setPage(int newPage) {
      this.page = newPage;
      this.rebuild();
   }

   @Override
   public void close() {
      super.close();
   }

   private String beautifyString(String string) {
      if (!this.vanilla) {
         if ("gnetum_unknown".equals(string)) {
            return I18n.get("gnetum.config.element.gnetum_unknown", new Object[0]);
         }

         IModFileInfo file = ModList.get().getModFileById(string);
         return file == null ? string : ((IModInfo)file.getMods().get(0)).getDisplayName();
      } else {
         String key1 = "gnetum.config.element." + string.replace(':', '.');
         if (I18n.exists(key1)) {
            return I18n.get(key1, new Object[0]);
         }

         String left = string.substring(0, string.indexOf(58));
         String right = string.substring(string.indexOf(58) + 1);
         IModFileInfo file = ModList.get().getModFileById(left);
         if (file != null) {
            left = "[" + ((IModInfo)file.getMods().get(0)).getDisplayName() + "]";
         } else {
            left = "[" + left + "]";
         }

         right = Character.toUpperCase(right.charAt(0)) + right.substring(1);

         int i;
         while ((i = right.indexOf(95)) != -1) {
            right = right.substring(0, i) + " " + Character.toUpperCase(right.charAt(i + 1)) + right.substring(i + 2);
         }

         return left + " " + right;
      }
   }
}
