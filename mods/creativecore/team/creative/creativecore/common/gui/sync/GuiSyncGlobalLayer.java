package team.creative.creativecore.common.gui.sync;

import java.util.function.BiFunction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;
import team.creative.creativecore.common.gui.packet.SyncPacket;

public class GuiSyncGlobalLayer<T extends GuiLayer> extends GuiSync<CompoundTag> {
   private final BiFunction<Provider, CompoundTag, T> creator;

   GuiSyncGlobalLayer(GuiSyncHolder holder, String name, BiFunction<Provider, CompoundTag, T> creator) {
      super(holder, name);
      this.creator = creator;
   }

   public void receive(IGuiIntegratedParent parent, CompoundTag tag) {
      parent.openLayer(this.creator.apply(parent.provider(), tag));
   }

   public T open(IGuiIntegratedParent parent, CompoundTag tag) {
      T layer = this.creator.apply(parent.provider(), tag);
      parent.openLayer(layer);
      parent.send(new SyncPacket(this, tag));
      return layer;
   }
}
