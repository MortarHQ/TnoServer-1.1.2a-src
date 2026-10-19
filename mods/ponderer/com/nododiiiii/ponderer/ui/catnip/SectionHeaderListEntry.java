package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.function.Supplier;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class SectionHeaderListEntry extends LabeledEntry implements SearchableListEntry {
   private final Supplier<String> titleGetter;

   public SectionHeaderListEntry(String title) {
      this(() -> title);
   }

   public SectionHeaderListEntry(Supplier<String> titleGetter) {
      super("");
      this.titleGetter = titleGetter;
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.titleGetter.get().toLowerCase(Locale.ROOT).contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      String title = this.titleGetter.get();
      Font font = Minecraft.getInstance().font;
      int color = this.annotations.containsKey("highlight") ? -797589 : -3355529;
      boolean compact = height <= 24;
      graphics.drawString(font, title, x + 4, compact ? y + 8 : y + 11, color);
      int lineY = compact ? y + height - 3 : y + height - 10;
      graphics.fill(x + 4, lineY, x + width - 4, lineY + 1, 1090519039);
   }
}
