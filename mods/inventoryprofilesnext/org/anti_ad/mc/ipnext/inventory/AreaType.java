package org.anti_ad.mc.ipnext.inventory;

import java.util.List;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public interface AreaType {
   @NotNull
   AreaType$Companion Companion = AreaType$Companion.$$INSTANCE;

   @NotNull
   ItemArea getItemArea(@NotNull AbstractContainerMenu var1, @NotNull List var2);

   @NotNull
   AreaType plus(@NotNull AreaType var1);

   @NotNull
   AreaType minus(@NotNull AreaType var1);
}
