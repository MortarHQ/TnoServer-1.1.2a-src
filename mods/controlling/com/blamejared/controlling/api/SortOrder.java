package com.blamejared.controlling.api;

import com.blamejared.controlling.ControllingConstants;
import com.blamejared.controlling.api.entries.IKeyEntry;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;
import net.minecraft.network.chat.Component;

public enum SortOrder {
   NONE("options.sortNone", entries -> {}),
   AZ("options.sortAZ", entries -> entries.sort(Comparator.comparing(o -> o.getKeyDesc().getString()))),
   ZA("options.sortZA", entries -> entries.sort(Comparator.comparing(o -> o.getKeyDesc().getString(), Comparator.reverseOrder()))),
   KEY_AZ(
      "options.sortKeyAZ",
      entries -> entries.sort(
         Comparator.<IKeyEntry, String>comparing(o -> o.getKey().getTranslatedKeyMessage().getString()).thenComparing(o -> o.getKeyDesc().getString())
      )
   ),
   KEY_ZA(
      "options.sortKeyZA",
      entries -> entries.sort(
         Comparator.<IKeyEntry, String>comparing(o -> o.getKey().getTranslatedKeyMessage().getString(), Comparator.reverseOrder())
            .thenComparing(o -> o.getKeyDesc().getString(), Comparator.reverseOrder())
      )
   );

   private final ISort sorter;
   private final Component display;

   SortOrder(String key, ISort sorter) {
      this.sorter = sorter;
      this.display = ControllingConstants.COMPONENT_OPTIONS_SORT.copy().append(": ").append(Component.translatable(key));
   }

   public SortOrder cycle() {
      return values()[(this.ordinal() + 1) % values().length];
   }

   public void sort(List<Entry> list) {
      list.removeIf(entry -> !(entry instanceof IKeyEntry));
      this.sorter.sort(list);
   }

   public Component getDisplay() {
      return this.display;
   }
}
