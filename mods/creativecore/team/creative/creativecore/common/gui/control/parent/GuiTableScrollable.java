package team.creative.creativecore.common.gui.control.parent;

import java.util.ArrayList;
import java.util.List;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiControlRect;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;

public class GuiTableScrollable extends GuiParent {
   protected GuiRow topRow = new GuiRow() {
      @Override
      public double getOffsetX() {
         return GuiTableScrollable.this.scrollableTable.getOffsetX();
      }
   };
   protected GuiParent bottom = new GuiParent();
   protected GuiParent firstCol = new GuiParent(GuiFlow.STACK_Y) {
      @Override
      public double getOffsetY() {
         return GuiTableScrollable.this.scrollableTable.getOffsetY();
      }
   };
   protected GuiScrollXY scrollableTable = new GuiScrollXY("", GuiFlow.STACK_Y) {
      @Override
      public void flowX(int width, int preferred) {
         this.updateWidth();
      }

      @Override
      public void flowY(int width, int height, int preferred) {
         this.updateHeight();
      }

      @Override
      public ControlFormatting getControlFormatting() {
         return ControlFormatting.TRANSPARENT;
      }
   };

   public GuiTableScrollable() {
      this("");
   }

   public GuiTableScrollable(String name) {
      super(name);
      this.scrollableTable.scrollbarThickness = 2;
      this.scrollableTable.scrollbarFace = ControlFormatting.ControlStyleFace.CLICKABLE_INACTIVE;
      this.scrollableTable.alternativeScrolling = true;
      this.flow = GuiFlow.STACK_Y;
      super.add(this.topRow);
      super.add(this.bottom);
      this.bottom.add(this.firstCol);
      this.bottom.add(this.scrollableTable);
      this.spacing = 4;
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

   public GuiTableScrollable setExpandable() {
      return (GuiTableScrollable)super.setExpandable();
   }

   public GuiRow getTopRow() {
      return this.topRow;
   }

   public GuiTableScrollable addRow(GuiRow row) {
      if (row.colCount() == 0) {
         return this;
      }

      this.firstCol.add(row.removeCol(0));
      this.scrollableTable.add(row);
      return this;
   }

   @Override
   public boolean isExpandableX() {
      return this.expandableX;
   }

   @Override
   public boolean isExpandableY() {
      return this.expandableY;
   }

   protected List<GuiTable.GuiTableGroup> createCols() {
      List<GuiTable.GuiTableGroup> cols = new ArrayList<>();
      int i = 0;

      for (GuiControl cell : this.topRow) {
         if (cols.size() <= i) {
            cols.add(new GuiTable.GuiTableGroup());
         }

         cols.get(i).controls.add(cell);
         i++;
      }

      GuiTable.GuiTableGroup first = cols.get(0);

      for (GuiControl control : this.firstCol) {
         first.controls.add(control);
      }

      for (GuiControl control : this.scrollableTable) {
         i = 1;

         for (GuiControl cell : (GuiRow)control) {
            if (cols.size() <= i) {
               cols.add(new GuiTable.GuiTableGroup());
            }

            cols.get(i).controls.add(cell);
            i++;
         }
      }

      return cols;
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return GuiFlow.STACK_X.preferredWidth(this.createCols(), this.spacing, availableWidth);
   }

   @Override
   public void flowX(int width, int preferred) {
      List<GuiTable.GuiTableGroup> cols = this.createCols();
      GuiFlow.STACK_X.flowX(cols, this.spacing, Align.LEFT, width, preferred, this.endlessX());
      GuiControlRect startCol = cols.get(0);
      int startX = startCol.getX();
      GuiControlRect lastCol = cols.get(cols.size() - 1);
      int combinedWidth = Math.min(width, lastCol.getWidth() + lastCol.getX() - startX);
      GuiControlRect bottomChild = this.bottom.rect;
      bottomChild.setX(0);
      bottomChild.setWidth(combinedWidth + startX, width);
      GuiControlRect firstColChild = this.firstCol.rect;
      firstColChild.setX(startX);
      firstColChild.setWidth(startCol.getWidth(), width);
      GuiControlRect topRowChild = this.topRow.rect;
      topRowChild.setX(startX);
      topRowChild.setWidth(combinedWidth, width);
      combinedWidth -= startCol.getWidth();
      GuiControlRect scrollableChild = this.scrollableTable.rect;
      scrollableChild.setX(startX + startCol.getWidth());
      scrollableChild.setWidth(combinedWidth, width);
      combinedWidth = lastCol.getWidth() + lastCol.getX() - startX - startCol.getWidth();
      startX += startCol.getWidth();

      for (GuiControl row : this.scrollableTable) {
         row.rect.setX(0);
         row.rect.setWidth(combinedWidth, width);

         for (GuiControl col : (GuiRow)row) {
            col.rect.setX(col.rect.getX() - startX);
         }
      }

      scrollableChild.flowX();
   }

   protected List<GuiControlRect> createRows() {
      List<GuiControlRect> rows = new ArrayList<>();
      rows.add(this.topRow.rect);
      int i = 1;

      for (GuiControl row : this.firstCol) {
         if (rows.size() <= i) {
            rows.add(new GuiTable.GuiTableGroup());
         }

         ((GuiTable.GuiTableGroup)rows.get(i)).controls.add(row);
         i++;
      }

      i = 1;

      for (GuiControl row : this.scrollableTable) {
         if (rows.size() <= i) {
            rows.add(new GuiTable.GuiTableGroup());
         }

         ((GuiTable.GuiTableGroup)rows.get(i)).controls.add(row);
         i++;
      }

      return rows;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return GuiFlow.STACK_Y.preferredHeight(this.createRows(), this.spacing, width, availableHeight);
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      List<GuiControlRect> rows = this.createRows();
      GuiFlow.STACK_Y.flowY(rows, this.spacing, VAlign.TOP, width, height, preferred, this.endlessY());
      int startY = rows.size() > 1 ? rows.get(1).getY() : rows.get(0).getHeight() + rows.get(0).getY();
      GuiControlRect lastRow = rows.get(rows.size() - 1);

      for (int j = 1; j < rows.size(); j++) {
         for (GuiControl child : ((GuiTable.GuiTableGroup)rows.get(j)).controls) {
            child.rect.setY(child.rect.getY() - startY);
         }
      }

      int combinedHeight = Math.min(height - startY, lastRow.getHeight() + lastRow.getY() - startY);
      GuiControlRect bottomChild = this.bottom.rect;
      bottomChild.setY(startY);
      bottomChild.setHeight(combinedHeight, height);
      GuiControlRect firstColChild = this.firstCol.rect;
      firstColChild.setY(0);
      firstColChild.setHeight(combinedHeight, height);
      GuiControlRect scrollableChild = this.scrollableTable.rect;
      scrollableChild.setY(0);
      scrollableChild.setHeight(combinedHeight, height);
      scrollableChild.flowY();
   }

   @Override
   protected boolean endlessX() {
      return true;
   }

   @Override
   protected boolean endlessY() {
      return true;
   }

   public void removeContentCol(int index) {
      this.topRow.removeCol(index + 1);

      for (GuiControl control : this.scrollableTable) {
         ((GuiRow)control).removeCol(index);
      }
   }

   public Iterable<GuiRow> contentRows() {
      return new FunctionIterator<>(this.scrollableTable, x -> (GuiRow)x);
   }
}
