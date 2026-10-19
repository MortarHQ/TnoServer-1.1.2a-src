package org.anti_ad.mc.ipnext.event.autorefill;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public interface SpecificItemSlotMonitor {
   @NotNull
   Holder getEntry(@NotNull DefaultedRegistry var1, Object var2);

   boolean isIn(@NotNull Holder var1, @NotNull TagKey var2);
}
