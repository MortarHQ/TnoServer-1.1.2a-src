package team.creative.creativecore.common.gui.sync;

import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;
import team.creative.creativecore.common.gui.packet.ControlSyncPacket;

public class GuiSyncLocalLayer<T extends GuiLayer> extends GuiSyncControl<GuiLayer, CompoundTag> {
   private final Function<CompoundTag, T> creator;

   GuiSyncLocalLayer(GuiSyncHolder holder, String name, Function<CompoundTag, T> creator) {
      super(holder, name);
      this.creator = creator;
   }

   public void receive(GuiLayer layer, CompoundTag tag) {
      layer.getIntegratedParent().openLayer(this.creator.apply(tag));
   }

   public T open(CompoundTag tag) {
      T layer = this.creator.apply(tag);
      GuiControl control = ((GuiSyncHolder.GuiSyncHolderLayer)this.holder).parent;
      IGuiIntegratedParent parent = control.getIntegratedParent();
      parent.openLayer(layer);
      parent.send(new ControlSyncPacket(control, this, tag));
      return layer;
   }
}
