package team.creative.creativecore.common.gui.control.collection;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiCheckBox;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.util.text.TextMapBuilder;
import team.creative.creativecore.common.util.type.itr.FilterIterator;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;

public class GuiCheckList<T> extends GuiScrollY {
   protected List<GuiCheckList<T>.GuiCheckListRow> rows = new ArrayList<>();
   protected int cachedWidth;
   protected int cachedHeight;
   public final boolean modifiable;
   public Predicate<T> canBeModified = x -> true;

   public GuiCheckList(String name, boolean modifiable, TextMapBuilder<T> map, Object2BooleanMap<T> selected) {
      super(name);
      this.modifiable = modifiable;
      if (map != null) {
         this.set(map, null);
      }
   }

   public void set(TextMapBuilder<T> map, Object2BooleanMap<T> selected) {
      this.rows.clear();
      this.clear();

      for (Entry<T, List<Component>> entry : map.entrySet()) {
         this.createControl(entry.getKey(), entry.getValue(), selected.getBoolean(entry.getKey()));
      }

      this.reflowInternal();
   }

   protected void createControl(T key, List<Component> components, boolean selected) {
      GuiCheckList<T>.GuiCheckListRow row = new GuiCheckList.GuiCheckListRow(key, components, selected);
      super.add(row);
      this.rows.add(row);
   }

   protected void removeControl(int index) {
      this.remove(this.rows.get(index));
      this.rows.remove(index);
   }

   protected GuiControl addCustomControl(GuiControl control) {
      return super.add(control);
   }

   protected GuiCheckList<T> addCustom(GuiControl control) {
      super.add(control);
      return this;
   }

   @Deprecated
   @Override
   public GuiParent add(GuiControl control) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public GuiParent addHover(GuiControl control) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void flowX(int width, int preferred) {
      this.cachedWidth = width;
      super.flowX(width, preferred);
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      this.cachedHeight = height;
      super.flowY(width, height, preferred);
   }

   public void reflowInternal() {
      if (this.hasGui()) {
         super.flowX(this.cachedWidth, this.preferredWidth(this.cachedWidth));
         super.flowY(this.cachedWidth, this.cachedHeight, this.preferredHeight(this.cachedWidth, this.cachedHeight));
      }
   }

   public void removeItem(int index) {
      this.removeControl(index);
      this.reflowInternal();
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   @Override
   public boolean isEmpty() {
      return this.rows.isEmpty();
   }

   @Override
   public int size() {
      return this.rows.size();
   }

   public T get(int index) {
      return this.rows.get(index).value;
   }

   public Iterable<T> selectedItems() {
      return new FunctionIterator<>(new FilterIterator(this.rows, x -> x.checkBox.value).iterator(), x -> x.value);
   }

   public Iterable<T> allItems() {
      return new FunctionIterator<>(this.rows, x -> x.value);
   }

   public boolean checked(int index) {
      return this.rows.get(index).checkBox.value;
   }

   public int indexOf(T value) {
      for (int i = 0; i < this.rows.size(); i++) {
         if (this.rows.get(i).value == value) {
            return i;
         }
      }

      return -1;
   }

   public class GuiCheckListRow extends GuiRow {
      public final T value;
      public final GuiButton removeButton;
      public final GuiCheckBox checkBox;

      public GuiCheckListRow(T value, List<Component> title, boolean selected) {
         this.value = value;
         GuiColumn content = (GuiColumn)new GuiColumn().setExpandableX();
         content.align = Align.LEFT;
         content.add(this.checkBox = new GuiCheckBox("box", selected).setTitle(title));
         this.addColumn(content);
         if (GuiCheckList.this.modifiable && GuiCheckList.this.canBeModified.test(value)) {
            GuiColumn remove = new GuiColumn(20);
            remove.align = Align.CENTER;
            this.removeButton = new GuiButton("x", x -> GuiCheckList.this.removeItem(GuiCheckList.this.indexOf(value)));
            this.removeButton.setDim(6, 8);
            this.removeButton.setAlign(Align.CENTER);
            this.removeButton.setTitle(Component.literal("x"));
            remove.add(this.removeButton);
            this.addColumn(remove);
         } else {
            this.removeButton = null;
         }
      }
   }
}
