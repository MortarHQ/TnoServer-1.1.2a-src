package team.creative.creativecore.common.gui.control.parent;

import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class GuiRow extends GuiParent {
   public GuiRow() {
      super(GuiFlow.STACK_X);
      this.spacing = 0;
      this.setExpandableX();
      this.align = Align.STRETCH;
      this.valign = VAlign.STRETCH;
   }

   public GuiRow(GuiColumn... cols) {
      this();

      for (int i = 0; i < cols.length; i++) {
         this.addColumn(cols[i]);
      }
   }

   public GuiRow addColumn(GuiColumn col) {
      super.add(col);
      return this;
   }

   public GuiColumn removeCol(int index) {
      return (GuiColumn)this.controls.remove(index);
   }

   public GuiColumn getCol(int index) {
      return (GuiColumn)this.controls.get(index);
   }

   public int colCount() {
      return this.controls.size();
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
}
