package team.creative.creativecore.common.gui.control.collection;

import java.util.function.Function;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.util.text.TextMapBuilder;

public class GuiComboBoxFlexible<K> extends GuiComboBox<K> {
   protected K forced;
   protected Function<K, Component> function;

   public GuiComboBoxFlexible(String name, TextMapBuilder<K> lines, Function<K, Component> function) {
      super(name, lines);
      this.function = function;
   }

   @Override
   protected void updateDisplay() {
      if (this.forced != null) {
         this.text = CompiledText.createAnySize();
         this.text.setText(this.function.apply(this.forced));
      } else {
         super.updateDisplay();
      }
   }

   public void forceSelect(K key) {
      int index = this.indexOf(key);
      if (index == -1) {
         this.forced = key;
         this.updateDisplay();
         this.raiseEvent(new GuiControlChangedEvent<>(this));
      } else {
         this.select(index);
      }
   }

   @Override
   public void select(int index) {
      this.forced = null;
      super.select(index);
   }

   @Override
   public K selected() {
      return this.forced != null ? this.forced : super.selected();
   }
}
