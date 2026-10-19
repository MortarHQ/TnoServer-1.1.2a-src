package team.creative.creativecore.common.gui.sync;

import net.minecraft.nbt.Tag;
import team.creative.creativecore.common.gui.GuiControl;

public abstract class GuiSyncControl<C extends GuiControl, T extends Tag> {
   public final GuiSyncHolder holder;
   public final String name;

   GuiSyncControl(GuiSyncHolder holder, String name) {
      this.holder = holder;
      this.name = name;
   }

   public abstract void receive(C var1, T var2);

   public String syncPath() {
      return this.holder.path() + this.name;
   }
}
