package dev.ftb.mods.ftblibrary.api.sidebar;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface SidebarButton {
   ResourceLocation getId();

   void addVisibilityCondition(BooleanSupplier var1);

   void addOverlayRender(ButtonOverlayRender var1);

   void setTooltipOverride(Supplier<List<Component>> var1);
}
