package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.createmod.catnip.theme.Color;

public final class ListSearchHelper {
   private ListSearchHelper() {
   }

   public static void applySearchFilter(@Nullable ConfigScreenList list, @Nullable HintableTextFieldWidget search, @Nullable String query, int entryHeight) {
      if (list != null && search != null) {
         String normalized = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
         if (normalized.isEmpty()) {
            list.setScrollAmount(0.0);
            search.setTextColor(((Color)UIRenderHelper.COLOR_TEXT.getFirst()).getRGB());
         } else {
            for (Entry entry : list.children()) {
               if (entry instanceof SearchableListEntry searchable && searchable.matchesQuery(normalized)) {
                  searchable.highlightEntry();
                  int index = list.children().indexOf(entry);
                  if (index >= 0) {
                     list.setScrollAmount((double)index * entryHeight);
                  }

                  search.setTextColor(((Color)UIRenderHelper.COLOR_TEXT.getFirst()).getRGB());
                  return;
               }
            }

            list.setScrollAmount(0.0);
            search.setTextColor(((Color)AbstractSimiWidget.COLOR_FAIL.getFirst()).getRGB());
         }
      }
   }
}
