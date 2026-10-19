package team.creative.creativecore.common.gui.control.parent;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.GuiParent;

public class GuiTabsMapped<K> extends GuiTabs {
   private List<K> keys = new ArrayList<>();

   public GuiTabsMapped(String name) {
      super(name);
   }

   @Deprecated
   @Override
   public GuiParent createTab(Component component) {
      throw new UnsupportedOperationException();
   }

   public GuiParent createTab(K key, Component component) {
      this.keys.add(key);
      return super.createTab(component);
   }

   public K getSelected() {
      int index = this.index();
      return index < this.keys.size() ? this.keys.get(index) : null;
   }

   public K getSelected(K defaultValue) {
      int index = this.index();
      return index < this.keys.size() ? this.keys.get(index) : defaultValue;
   }

   public void select(K key) {
      int index = this.keys.indexOf(key);
      if (index != -1) {
         this.select(index);
      }
   }
}
