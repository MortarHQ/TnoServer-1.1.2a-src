package team.creative.creativecore.common.gui.control.tree;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.simple.GuiButton;
import team.creative.creativecore.common.gui.control.simple.GuiButtonHoldSlim;
import team.creative.creativecore.common.gui.control.simple.GuiCheckBox;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.control.simple.GuiLabelHover;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.type.itr.FilterIterator;

public class GuiTreeItem extends GuiParent {
   public final GuiTree tree;
   private GuiTreeItem parentItem;
   private final List<GuiTreeItem> items = new ArrayList<>();
   private int level = 0;
   private boolean open = true;
   private boolean selected = false;
   private GuiCheckBox checkbox;
   private final GuiLabel label;
   private GuiButton button;
   private GuiTreeItem.ItemClickState state = null;
   protected boolean moving = false;

   public GuiTreeItem(String name, GuiTree tree) {
      super(name);
      this.tree = tree;
      if (tree.hasCheckboxes()) {
         this.add(this.getOrCreateCheckbox());
      }

      this.add(this.label = new GuiLabelHover("title"));
      this.flow = GuiFlow.STACK_X;
      this.spacing = 5;
   }

   public GuiTreeItem setTranslate(String translate) {
      this.label.setTranslate(translate);
      return this;
   }

   public GuiTreeItem setTitle(Component component) {
      this.label.setTitle(component);
      return this;
   }

   public GuiTreeItem setTitle(List<Component> components) {
      this.label.setTitle(components);
      return this;
   }

   public void resetCheckboxPartial() {
      if (this.checkbox != null) {
         this.checkbox.partial = false;
      }
   }

   protected GuiCheckBox getOrCreateCheckbox() {
      return this.checkbox != null ? this.checkbox : (this.checkbox = new GuiCheckBox("box", true).consumeChanged(x -> {
         if (this.parentItem != null && this.tree.hasCheckboxesPartial()) {
            this.parentItem.childCheckedChanged(x);
         }

         this.setChecked(x);
      }));
   }

   public boolean isMoving() {
      return this.moving;
   }

   public void setMoving(boolean moving) {
      this.moving = moving;
   }

   public boolean opened() {
      return this.open;
   }

   public void toggle() {
      if (this.button != null) {
         this.open = !this.open;
         this.button.setTitle(Component.literal(this.open ? "-" : "+"));
         this.tree.updateTree();
      }
   }

   public boolean isAtLeastPartiallyChecked() {
      return this.checkbox != null && (this.checkbox.value || this.checkbox.partial);
   }

   public boolean isChecked() {
      return this.checkbox != null && this.checkbox.value;
   }

   protected void setChecked(boolean value) {
      if (this.checkbox != null) {
         this.checkbox.value = value;
         this.checkbox.partial = false;
      }

      if (this.tree.hasCheckboxesPartial()) {
         for (GuiTreeItem item : this.items) {
            item.setChecked(value);
         }
      }
   }

   protected void childCheckedChanged(boolean value) {
      if (this.checkbox != null) {
         if (!this.checkbox.value) {
            if (value) {
               if (this.checkbox.partial) {
                  return;
               }

               this.checkbox.partial = true;
               if (this.parentItem != null) {
                  this.parentItem.childCheckedChanged(true);
               }
            } else {
               if (!this.checkbox.partial) {
                  return;
               }

               for (GuiTreeItem item : this.items) {
                  if (item.isAtLeastPartiallyChecked()) {
                     return;
                  }
               }

               this.checkbox.partial = false;
               if (this.parentItem != null) {
                  this.parentItem.childCheckedChanged(false);
               }
            }
         }
      }
   }

   protected void updateControls() {
      if (this.tree.hasCheckboxes() != (this.checkbox != null) || this.items.isEmpty() != (this.button == null)) {
         this.clear();
         if (this.items.isEmpty()) {
            this.button = null;
         } else {
            this.add(this.button = (GuiButton)new GuiButtonHoldSlim("expand", x -> this.toggle()).setTitle(Component.literal("-")));
         }

         if (this.tree.hasCheckboxes()) {
            this.add(this.getOrCreateCheckbox());
         } else if (this.checkbox != null) {
            this.checkbox = null;
         }

         this.add(this.label);
      }
   }

   public GuiTreeItem getParentItem() {
      return this.parentItem;
   }

   public void clearItems() {
      for (GuiTreeItem item : this.items) {
         item.parentItem = null;
         item.removed();
         this.updateControls();
      }

      this.items.clear();
   }

   public boolean removeItem(GuiTreeItem item) {
      if (this.items.remove(item)) {
         item.parentItem = null;
         item.removed();
         this.updateControls();
         return true;
      } else {
         return false;
      }
   }

   public void removed() {
   }

   public void insertItemAfter(GuiTreeItem before, GuiTreeItem item) {
      this.insertItemAfter(this.indexOf(before), item);
   }

   public void insertItemAfter(int index, GuiTreeItem item) {
      item.parentItem = this;
      item.updateLevel();
      this.items.add(index + 1, item);
      item.added();
      this.updateControls();
   }

   public void insertItem(GuiTreeItem before, GuiTreeItem item) {
      this.insertItem(this.indexOf(before), item);
   }

   public void insertItem(int index, GuiTreeItem item) {
      item.parentItem = this;
      item.updateLevel();
      this.items.add(index, item);
      item.added();
      this.updateControls();
   }

   public void addItem(GuiTreeItem item) {
      item.parentItem = this;
      item.updateLevel();
      this.items.add(item);
      item.added();
      this.updateControls();
   }

   public void added() {
   }

   protected void updateLevel() {
      this.level = this.parentItem.level + 1;

      for (GuiTreeItem item : this.items) {
         item.updateLevel();
      }
   }

   public int getLevel() {
      return this.level;
   }

   public Iterable<GuiTreeItem> items() {
      return this.items;
   }

   public Iterable<GuiTreeItem> itemsChecked() {
      return new FilterIterator<>(this.items, GuiTreeItem::isAtLeastPartiallyChecked);
   }

   public GuiTreeItem getItem(int index) {
      return this.items.get(index);
   }

   public int indexOf(GuiTreeItem item) {
      return this.items.indexOf(item);
   }

   public int itemsCount() {
      return this.items.size();
   }

   public boolean isChild(GuiTreeItem item) {
      for (GuiTreeItem child : this.items) {
         if (child == item || child.isChild(item)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void mouseMoved(double x, double y) {
      super.mouseMoved(x, y);
      if (this.state == GuiTreeItem.ItemClickState.CLICKED && !this.tree.isDragged() && !this.rect.inside(x, y)) {
         this.tree.startDrag(this);
         this.state = GuiTreeItem.ItemClickState.DRAGGED;
      }
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      super.mouseReleased(x, y, button);
      if (this.state == GuiTreeItem.ItemClickState.CLICKED) {
         this.tree.select(this);
         playSound(SoundEvents.UI_BUTTON_CLICK);
         this.state = null;
      } else if (this.state == GuiTreeItem.ItemClickState.DRAGGED) {
         this.state = null;
         if (this.tree.endDrag()) {
            playSound(SoundEvents.UI_BUTTON_CLICK, 0.1F, 2.0F);
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (super.mouseClicked(x, y, button)) {
         return true;
      }

      this.state = GuiTreeItem.ItemClickState.CLICKED;
      return true;
   }

   @Override
   public boolean mouseDoubleClicked(double x, double y, int button) {
      this.toggle();
      this.tree.select(this);
      playSound(SoundEvents.UI_BUTTON_CLICK);
      return true;
   }

   @Override
   public boolean testForDoubleClick(double x, double y, int button) {
      return button == 0;
   }

   protected void updateColor() {
      if (this.selected) {
         this.label.setDefaultColor(-256);
      } else {
         this.label.setDefaultColor(-1);
      }
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return this.state == GuiTreeItem.ItemClickState.DRAGGED ? ControlFormatting.OUTLINE : super.getControlFormatting();
   }

   public boolean selected() {
      return this.selected;
   }

   protected void select() {
      this.selected = true;
      this.updateColor();
   }

   protected void deselect() {
      this.selected = false;
      this.updateColor();
   }

   private enum ItemClickState {
      CLICKED,
      DRAGGED;
   }
}
