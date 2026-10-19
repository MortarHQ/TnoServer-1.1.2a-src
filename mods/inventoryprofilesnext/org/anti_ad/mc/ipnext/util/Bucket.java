package org.anti_ad.mc.ipnext.util;

import java.util.Map;
import java.util.Set;
import org.anti_ad.mc.ipnext.item.rule.CountSink;
import org.anti_ad.mc.ipnext.item.rule.CountSource;
import org.jetbrains.annotations.NotNull;

public interface Bucket extends CountSource {
   int getSize();

   int count(@NotNull CountSink var1);

   boolean contains(@NotNull CountSink var1);

   boolean contains(@NotNull CountSink var1, int var2);

   boolean containsAll(@NotNull Bucket var1);

   @NotNull
   Set getElementSet();

   @NotNull
   Set getEntrySet();

   @NotNull
   Map getAsMap();

   boolean isEmpty();
}
