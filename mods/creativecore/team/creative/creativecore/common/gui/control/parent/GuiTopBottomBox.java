package team.creative.creativecore.common.gui.control.parent;

import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.flow.GuiFlow;

public class GuiTopBottomBox extends GuiParent {
   public final GuiParent top;
   public final GuiParent bottom;

   public GuiTopBottomBox() {
      super(GuiFlow.STACK_Y);
      this.spacing = 0;
      this.setExpandableY();
      this.align = Align.STRETCH;
      this.valign = VAlign.STRETCH;
      this.top = (GuiParent)new GuiParent(GuiFlow.STACK_Y).setExpandableY();
      super.add(this.top);
      this.bottom = new GuiParent(GuiFlow.STACK_Y);
      super.add(this.bottom);
   }

   public GuiTopBottomBox addTop(GuiControl control) {
      this.top.add(control);
      return this;
   }

   public GuiTopBottomBox addBottom(GuiControl control) {
      this.bottom.add(control);
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
}
