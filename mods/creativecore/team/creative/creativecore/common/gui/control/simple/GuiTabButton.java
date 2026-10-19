package team.creative.creativecore.common.gui.control.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.text.IComponentMap;
import team.creative.creativecore.common.util.type.list.Tuple;
import team.creative.creativecore.common.util.type.list.TupleList;

public class GuiTabButton<K> extends GuiParent {
   public static final ControlFormatting BUTTON_ACTIVE = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.CLICKABLE
   );
   public static final ControlFormatting BUTTON_INACTIVE = new ControlFormatting(
      ControlFormatting.ControlStyleBorder.SMALL, 2, ControlFormatting.ControlStyleFace.CLICKABLE_INACTIVE
   );
   protected TupleList<K, CompiledText> data;
   private List<GuiTabButton.GuiBorderlessButton> buttons;
   private int index;
   private GuiTabButton.GuiBorderlessButton<K> selected;

   public GuiTabButton(String name, IComponentMap states) {
      this(name, 0, states);
   }

   public GuiTabButton(String name, int index, IComponentMap map) {
      super(name, null);
      this.flow = GuiFlow.STACK_X;
      this.set(map);
      this.select(index);
   }

   public void set(IComponentMap<K> builder) {
      this.data = builder.build();
      this.buttons = new ArrayList<>();
      this.clear();
      int i = 0;

      for (Tuple<K, CompiledText> t : this.data) {
         int bIndex = i;
         GuiTabButton.GuiBorderlessButton b = new GuiTabButton.GuiBorderlessButton("b" + i, x -> this.select(bIndex), t.key);
         this.add(b.setText(t.value));
         this.buttons.add(b);
         i++;
      }

      this.select(this.index);
   }

   @Nullable
   public K selected() {
      return this.selected != null ? this.selected.value : null;
   }

   public K selected(K defaultValue) {
      K s = this.selected();
      return s != null ? s : defaultValue;
   }

   public void select(int index) {
      this.index = Mth.clamp(index, 0, this.data.size() - 1);
      if (this.selected != null) {
         this.selected.active = false;
      }

      if (!this.data.isEmpty()) {
         this.selected = this.buttons.get(index);
         this.selected.active = true;
      } else {
         this.selected = null;
      }

      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   public void select(K key) {
      this.select(this.indexOf(key));
   }

   public int indexOf(K key) {
      for (int i = 0; i < this.data.size(); i++) {
         if (Objects.equals(this.data.get(i).key, key)) {
            return i;
         }
      }

      return -1;
   }

   public void next() {
      int index = this.index + 1;
      if (index >= this.data.size()) {
         index = 0;
      }

      this.select(index);
   }

   public void previous() {
      int index = this.index - 1;
      if (index < 0) {
         index = this.data.size() - 1;
      }

      this.select(index);
   }

   public int index() {
      return this.index;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }

   public static class GuiBorderlessButton<K> extends GuiButton {
      public boolean active = false;
      private final K value;

      public GuiBorderlessButton(String name, Consumer<Integer> pressed, K value) {
         super(name, pressed);
         this.value = value;
      }

      @Override
      public ControlFormatting getControlFormatting() {
         return this.active ? GuiTabButton.BUTTON_ACTIVE : GuiTabButton.BUTTON_INACTIVE;
      }
   }
}
