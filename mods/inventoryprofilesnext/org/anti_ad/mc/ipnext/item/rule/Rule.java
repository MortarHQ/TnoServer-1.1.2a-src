package org.anti_ad.mc.ipnext.item.rule;

import java.util.Comparator;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

public interface Rule extends Comparator {
   @NotNull
   ArgumentMap getArguments();

   int compare(@NotNull ItemType var1, @NotNull ItemType var2);
}
