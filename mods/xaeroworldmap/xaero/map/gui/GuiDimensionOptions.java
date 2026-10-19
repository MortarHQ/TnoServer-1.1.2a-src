package xaero.map.gui;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class GuiDimensionOptions {
   public int selected;
   public final ResourceKey<Level>[] values;

   public GuiDimensionOptions(int selected, ResourceKey<Level>[] values) {
      this.selected = selected;
      this.values = values;
   }
}
