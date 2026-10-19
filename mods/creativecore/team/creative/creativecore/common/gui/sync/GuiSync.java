package team.creative.creativecore.common.gui.sync;

import net.minecraft.nbt.Tag;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;

public abstract class GuiSync<T extends Tag> {
   public final GuiSyncHolder holder;
   public final String name;

   GuiSync(GuiSyncHolder holder, String name) {
      this.holder = holder;
      this.name = name;
   }

   public abstract void receive(IGuiIntegratedParent var1, T var2);

   public String syncPath() {
      return this.holder.path() + this.name;
   }
}
