package team.creative.creativecore.common.gui.control.simple;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.text.IComponentMap;
import team.creative.creativecore.common.util.type.list.TupleList;

public class GuiStateButton<K> extends GuiButton {
   protected TupleList<K, CompiledText> data;
   private int index;
   private K selected;

   public GuiStateButton(String name, IComponentMap<K> map) {
      this(name, 0, map);
   }

   public GuiStateButton(String name, int index, IComponentMap<K> map) {
      super(name, null);
      this.pressed = button -> {
         if (button == 1) {
            this.previous();
         } else {
            this.next();
         }
      };
      this.index = index;
      this.set(map);
      this.select(index);
   }

   public GuiStateButton(String name, K value, IComponentMap<K> map) {
      this(name, map);
      this.select(value);
   }

   public void set(IComponentMap<K> builder) {
      this.data = builder.build();
      this.select(this.index);

      for (CompiledText text : this.data.values()) {
         text.setAlign(Align.CENTER);
      }

      this.updateDisplay();
   }

   @Override
   public void flowX(int width, int preferred) {
      for (CompiledText text : this.data.values()) {
         text.setDimension(width, Integer.MAX_VALUE);
      }
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      for (CompiledText text : this.data.values()) {
         text.setMaxHeight(height);
      }
   }

   @Override
   public int preferredWidth(int availableWidth) {
      int width = 0;

      for (CompiledText text : this.data.values()) {
         width = Math.max(width, text.getTotalWidth());
      }

      return width;
   }

   @Override
   public int preferredHeight(int width, int availableHeight) {
      int height = 0;

      for (CompiledText text : this.data.values()) {
         height = Math.max(height, text.getTotalHeight());
      }

      return height;
   }

   @Nullable
   public K selected() {
      return this.selected;
   }

   public K selected(K defaultValue) {
      K s = this.selected();
      return s != null ? s : defaultValue;
   }

   public void select(int index) {
      this.index = Mth.clamp(index, 0, this.data.size() - 1);
      if (!this.data.isEmpty()) {
         this.selected = this.data.get(index).key;
      } else {
         this.selected = null;
      }

      this.updateDisplay();
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

   protected void updateDisplay() {
      if (this.index >= 0 && this.index < this.data.size()) {
         this.text = (CompiledText)this.data.get(this.index).value;
      } else {
         this.text = CompiledText.EMPTY;
      }
   }

   @Override
   public void closed() {
   }

   @Override
   public void tick() {
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.CLICKABLE;
   }
}
