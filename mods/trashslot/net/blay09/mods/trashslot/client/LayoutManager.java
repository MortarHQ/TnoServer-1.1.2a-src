package net.blay09.mods.trashslot.client;

import com.google.common.collect.Maps;
import java.util.Map;
import net.blay09.mods.trashslot.api.IGuiContainerLayout;
import net.blay09.mods.trashslot.client.gui.layout.SimpleGuiContainerLayout;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class LayoutManager {
   private static final Map<String, IGuiContainerLayout> layoutMap = Maps.newHashMap();

   public static IGuiContainerLayout getLayout(AbstractContainerScreen<?> gui) {
      IGuiContainerLayout layout = layoutMap.get(gui.getClass().getName());
      return layout == null ? SimpleGuiContainerLayout.DEFAULT : layout;
   }

   public static void registerLayout(Class<? extends AbstractContainerScreen<?>> clazz, IGuiContainerLayout layout) {
      layoutMap.put(clazz.getName(), layout);
   }
}
