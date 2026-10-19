package team.creative.creativecore.common.gui.control.tree;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.control.parent.GuiScrollXY;
import team.creative.creativecore.common.gui.control.simple.GuiTextfield;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.mc.ColorUtils;
import team.creative.creativecore.common.util.type.itr.NestedFunctionIterator;
import team.creative.creativecore.common.util.type.itr.TreeIterator;

public class GuiTree extends GuiScrollXY {
   private static final int levelSpacing = 10;
   private int lastWidth;
   private int lastHeight;
   private boolean searchbar = false;
   private boolean checkboxes = false;
   private boolean checkboxesPartial = false;
   protected boolean canDeselect = true;
   private final GuiTextfield search;
   private boolean visibleRoot = false;
   private final GuiTreeItem root;
   protected StyleDisplay line = new DisplayColor(-1);
   protected StyleDisplay dragLine = new DisplayColor(-16777216);
   protected StyleDisplay dragHover = new DisplayColor(ColorUtils.rgb(200, 150, 0));
   private int lineThickness;
   private int halfLineThickness;
   private GuiTreeItem selected = null;
   private GuiTreeItem dragged = null;
   private GuiTreeDragPosition lastDragPosition = null;

   public GuiTree(String name) {
      this(name, false);
   }

   public GuiTree(String name, boolean searchbar) {
      super(name, GuiFlow.STACK_Y);
      this.searchbar = searchbar;
      if (searchbar) {
         this.search = new GuiTextfield(name);
      } else {
         this.search = null;
      }

      this.root = new GuiTreeItem("root", this);
      this.spacing = 3;
      this.setLineThickness(1);
   }

   public GuiTree setCheckboxes(boolean checkboxes, boolean partial) {
      boolean changed = checkboxes != this.checkboxes || partial != this.checkboxesPartial;
      this.checkboxes = checkboxes;
      this.checkboxesPartial = partial;
      if (changed) {
         for (GuiTreeItem item : this.allItems()) {
            if (!partial) {
               item.resetCheckboxPartial();
            }

            item.updateControls();
         }
      }

      return this;
   }

   public GuiTree keepSelected() {
      this.canDeselect = false;
      return this;
   }

   public boolean hasCheckboxes() {
      return this.checkboxes;
   }

   public boolean hasCheckboxesPartial() {
      return this.checkboxesPartial;
   }

   public Iterable<GuiTreeItem> allItems() {
      return new TreeIterator<>(this.root, x -> x.items().iterator());
   }

   public Iterable<GuiTreeItem> itemsChecked() {
      return this.visibleRoot ? this.root.itemsChecked() : new NestedFunctionIterator<>(this.root.items(), GuiTreeItem::itemsChecked);
   }

   public GuiTreeItem selected() {
      return this.selected;
   }

   public GuiTreeItem getFirst() {
      if (this.visibleRoot) {
         return this.root;
      } else {
         return this.root.itemsCount() > 0 ? this.root.getItem(0) : null;
      }
   }

   public boolean selectFirst() {
      if (this.visibleRoot) {
         this.select(this.root);
      } else {
         if (this.root.itemsCount() <= 0) {
            this.select(null);
            return false;
         }

         this.select(this.root.getItem(0));
      }

      return true;
   }

   public void select(GuiTreeItem item) {
      if (this.selected != null) {
         this.selected.deselect();
      }

      GuiTreeItem old = this.selected;
      this.selected = item;
      if (item != null) {
         item.select();
      }

      this.raiseEvent(new GuiTree.GuiTreeSelectionChanged(this, old, item));
   }

   public void setLineThickness(int thickness) {
      this.lineThickness = thickness;
      this.halfLineThickness = thickness / 2;
   }

   public GuiTree setRootVisibility(boolean visible) {
      this.visibleRoot = visible;
      return this;
   }

   public GuiTreeItem root() {
      return this.root;
   }

   public void updateTree() {
      this.clear();
      if (this.searchbar) {
         this.add(this.search);
      }

      if (this.visibleRoot) {
         this.add(this.root);
      }

      if (!this.visibleRoot || this.root.opened()) {
         this.addItem(this.root);
      }

      this.reflowTree();
   }

   public void reflowTree() {
      if (this.lastWidth != 0) {
         this.flowX(this.lastWidth, this.preferredWidth(this.lastWidth));
         this.flowY(this.lastWidth, this.lastHeight, this.preferredHeight(this.lastWidth, this.lastHeight));
      }
   }

   private void addItem(GuiTreeItem item) {
      for (GuiTreeItem subItem : item.items()) {
         this.add(subItem);
         if (subItem.opened()) {
            this.addItem(subItem);
         }
      }
   }

   protected int offsetByLevel(int level) {
      return this.visibleRoot ? 10 * level : 10 * (level - 1);
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      int width = 0;

      for (GuiControl control : this.controls) {
         if (control instanceof GuiTreeItem item) {
            width = Math.max(width, this.offsetByLevel(item.getLevel()) + 1 + control.rect.getPreferredWidth(availableWidth));
         } else {
            width = Math.max(width, control.rect.getPreferredWidth(availableWidth));
         }
      }

      return width;
   }

   @Override
   public void flowX(int width, int preferred) {
      super.flowX(width, preferred);

      for (GuiControl control : this.controls) {
         if (control instanceof GuiTreeItem item) {
            control.rect.setX(this.offsetByLevel(item.getLevel()) + 1);
         }
      }

      this.updateWidth();
      this.lastWidth = width;
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      super.flowY(width, height, preferred);
      this.lastHeight = height;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(
      GuiGraphics graphics, ControlFormatting formatting, int borderWidth, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY
   ) {
      PoseStack pose = graphics.pose();
      if (this.isDragged()) {
         pose.pushPose();
         pose.translate(this.getContentOffset() + this.getOffsetX(), this.getContentOffset() + this.getOffsetY(), 0.0);
         this.lastDragPosition = this.calculatePosition(
            (int)(mouseX - realRect.minX - this.getContentOffset()), (int)(mouseY - realRect.minY - this.getContentOffset())
         );
         if (this.lastDragPosition != null) {
            if (this.lastDragPosition.position() == GuiTreeDragPosition.ItemPosition.IN) {
               this.dragHover.render(graphics, this.lastDragPosition.item().rect);
            } else {
               int thickness = 1;
               int width = this.lastDragPosition.item().rect.getWidth();
               int minY;
               if (this.lastDragPosition.position() == GuiTreeDragPosition.ItemPosition.ABOVE) {
                  minY = this.lastDragPosition.item().rect.getY() - thickness - 1;
                  if (this.lastDragPosition.above() != null) {
                     width = Math.max(width, this.lastDragPosition.above().rect.getWidth());
                  }
               } else {
                  minY = this.lastDragPosition.item().rect.getBottom() + 1;
               }

               if (this.lastDragPosition.above() != null) {
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() - thickness, minY - thickness, thickness, thickness * 3);
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() + width, minY - thickness, thickness, thickness * 3);
               } else if (this.lastDragPosition.position() == GuiTreeDragPosition.ItemPosition.ABOVE) {
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() - thickness, minY, thickness, thickness * 2);
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() + width, minY, thickness, thickness * 2);
               } else {
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() - thickness, minY - thickness, thickness, thickness * 2);
                  this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX() + width, minY - thickness, thickness, thickness * 2);
               }

               this.dragLine.render(graphics, this.lastDragPosition.item().rect.getX(), minY, width, thickness);
            }
         }

         pose.popPose();
      } else {
         this.lastDragPosition = null;
      }

      super.renderContent(graphics, formatting, borderWidth, controlRect, realRect, scale, mouseX, mouseY);
      pose.pushPose();
      pose.translate(this.getOffsetX(), this.getContentOffset() + this.getOffsetY(), 0.0);
      List<GuiTree.GuiTreeLine> lines = new ArrayList<>();
      int size = -1;
      Iterator var21 = this.controls.iterator();

      while (true) {
         int lineY;
         int level;
         label103:
         while (true) {
            if (!var21.hasNext()) {
               if (size >= 0) {
                  for (int i = size; i > -1; i--) {
                     if (!lines.get(i).invalid) {
                        lines.get(i).render(graphics, pose);
                        lines.get(i).invalid = false;
                     }
                  }
               }

               pose.popPose();
               return;
            }

            GuiControl control = (GuiControl)var21.next();
            if (control instanceof GuiTreeItem item) {
               lineY = (control.rect.getY() + control.rect.getBottom()) / 2 + this.halfLineThickness;
               level = item.getLevel() - (this.visibleRoot ? 1 : 2);
               if (level > size) {
                  if (level < 0) {
                     continue;
                  }

                  while (true) {
                     if (level <= size) {
                        break label103;
                     }

                     if (lines.size() > size + 1) {
                        lines.get(level).set(control.rect.getY() - 2, lineY);
                     } else {
                        lines.add(new GuiTree.GuiTreeLine(size + 1, control.rect.getY() - 2, lineY));
                     }

                     size++;
                  }
               }

               if (level >= 0) {
                  lines.get(level).y2 = lineY;
               }

               for (int i = size; i > level; i--) {
                  if (!lines.get(i).invalid) {
                     lines.get(i).render(graphics, pose);
                     lines.get(i).invalid = false;
                  }
               }

               size = level;
               break;
            }
         }

         if (level >= 0) {
            this.line.render(graphics, lines.get(level).x + this.lineThickness, lineY - this.lineThickness, 5.0, this.lineThickness);
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (super.mouseClicked(x, y, button)) {
         return true;
      }

      if (this.canDeselect) {
         this.select(null);
      }

      return true;
   }

   private GuiTreeDragPosition createPosition(GuiTreeDragPosition.ItemPosition position, GuiTreeItem item, GuiControl before) {
      return item == this.root && position != GuiTreeDragPosition.ItemPosition.IN
         ? null
         : new GuiTreeDragPosition(position, item, before != null && before instanceof GuiTreeItem item2 && item2.getLevel() == item.getLevel() ? before : null);
   }

   protected GuiTreeDragPosition calculatePosition(int mouseX, int mouseY) {
      GuiControl last = null;
      GuiControl before = null;

      for (GuiControl control : this.controls) {
         if (control != this.dragged) {
            if (control instanceof GuiTreeItem item) {
               if (control.rect.getY() > mouseY) {
                  return this.createPosition(GuiTreeDragPosition.ItemPosition.ABOVE, item, before);
               }

               if (control.rect.inside(mouseX, mouseY)) {
                  return this.createPosition(GuiTreeDragPosition.ItemPosition.IN, item, before);
               }

               last = control;
            }

            before = control;
         }
      }

      return last != null ? this.createPosition(GuiTreeDragPosition.ItemPosition.BELOW, (GuiTreeItem)last, null) : null;
   }

   public boolean performModication(GuiTreeItem item, GuiTreeDragPosition position) {
      if (item.isChild(position.item())) {
         return false;
      }

      try {
         item.setMoving(true);
         if (!item.getParentItem().removeItem(item)) {
            return false;
         }

         position.insert(item);
      } finally {
         item.setMoving(false);
      }

      this.updateTree();
      return true;
   }

   public boolean moveUp() {
      if (this.selected == null) {
         return false;
      }

      GuiTreeItem parent = this.selected.getParentItem();
      if (parent == null) {
         return false;
      }

      int index = parent.indexOf(this.selected);
      return index <= 0
         ? false
         : this.performModication(this.selected, new GuiTreeDragPosition(GuiTreeDragPosition.ItemPosition.ABOVE, parent.getItem(index - 1), null));
   }

   public boolean moveDown() {
      if (this.selected == null) {
         return false;
      }

      GuiTreeItem parent = this.selected.getParentItem();
      if (parent == null) {
         return false;
      }

      int index = parent.indexOf(this.selected);
      return index >= parent.itemsCount() - 1
         ? false
         : this.performModication(this.selected, new GuiTreeDragPosition(GuiTreeDragPosition.ItemPosition.BELOW, parent.getItem(index + 1), null));
   }

   public boolean isDragged() {
      return this.dragged != null;
   }

   public void startDrag(GuiTreeItem item) {
      this.dragged = item;
   }

   public boolean endDrag() {
      this.performModication(this.dragged, this.lastDragPosition);
      this.lastDragPosition = null;
      this.dragged = null;
      return true;
   }

   private class GuiTreeLine {
      public final int x;
      public int y;
      public int y2;
      public boolean invalid;

      public GuiTreeLine(int level, int minY, int maxY) {
         this.x = GuiTree.this.offsetByLevel(level + (GuiTree.this.visibleRoot ? 1 : 2)) - 5;
         this.set(minY, maxY);
      }

      public void set(int minY, int maxY) {
         this.y = minY;
         this.y2 = maxY;
         this.invalid = false;
      }

      public void render(GuiGraphics graphics, PoseStack pose) {
         GuiTree.this.line.render(graphics, this.x, this.y, GuiTree.this.lineThickness, this.y2 - this.y);
      }
   }

   public static class GuiTreeSelectionChanged extends GuiControlChangedEvent {
      public final GuiTreeItem previousSelected;
      public final GuiTreeItem selected;

      public GuiTreeSelectionChanged(GuiTree tree, GuiTreeItem previousSelected, GuiTreeItem selected) {
         super(tree);
         this.previousSelected = previousSelected;
         this.selected = selected;
      }
   }
}
