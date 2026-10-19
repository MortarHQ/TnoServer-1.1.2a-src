package team.creative.creativecore.common.gui.control.parent;

import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.VAlign;

public class GuiLeftRightBox extends GuiRow {
   private final GuiColumn left = (GuiColumn)new GuiColumn().setExpandableX();
   private final GuiColumn right;

   public GuiLeftRightBox() {
      super.addColumn(this.left);
      this.right = new GuiColumn();
      this.right.align = Align.RIGHT;
      super.addColumn(this.right);
      this.setExpandableX();
   }

   @Override
   public boolean isExpandableX() {
      return this.expandableX;
   }

   public GuiLeftRightBox addLeft(GuiControl control) {
      this.left.add(control);
      return this;
   }

   public GuiLeftRightBox addRight(GuiControl control) {
      this.right.add(control);
      return this;
   }

   public GuiLeftRightBox setVAlign(VAlign valign) {
      this.left.setVAlign(valign);
      this.right.setVAlign(valign);
      return this;
   }

   @Deprecated
   @Override
   public GuiRow addColumn(GuiColumn col) {
      throw new UnsupportedOperationException();
   }
}
