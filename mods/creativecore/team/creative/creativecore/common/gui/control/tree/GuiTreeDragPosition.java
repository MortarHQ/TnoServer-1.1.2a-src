package team.creative.creativecore.common.gui.control.tree;

import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.common.gui.GuiControl;

public record GuiTreeDragPosition(GuiTreeDragPosition.ItemPosition position, GuiTreeItem item, @Nullable GuiControl above) {
   public void insert(GuiTreeItem item) {
      this.position.insert(item, this.item);
   }

   @Override
   public String toString() {
      return this.position.name() + "," + this.item.rect;
   }

   public enum ItemPosition {
      ABOVE {
         @Override
         public void insert(GuiTreeItem toAdd, GuiTreeItem reference) {
            GuiTreeItem parent = reference.getParentItem();
            parent.insertItem(parent.indexOf(reference), toAdd);
         }
      },
      IN {
         @Override
         public void insert(GuiTreeItem toAdd, GuiTreeItem reference) {
            reference.addItem(toAdd);
         }
      },
      BELOW {
         @Override
         public void insert(GuiTreeItem toAdd, GuiTreeItem reference) {
            GuiTreeItem parent = reference.getParentItem();
            int index = parent.indexOf(reference);
            if (index >= parent.itemsCount() - 1) {
               parent.addItem(toAdd);
            } else {
               parent.insertItem(index + 1, toAdd);
            }
         }
      };

      public abstract void insert(GuiTreeItem var1, GuiTreeItem var2);
   }
}
