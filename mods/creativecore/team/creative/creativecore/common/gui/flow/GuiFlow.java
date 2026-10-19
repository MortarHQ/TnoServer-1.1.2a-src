package team.creative.creativecore.common.gui.flow;

import java.util.List;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControlRect;
import team.creative.creativecore.common.gui.VAlign;

public abstract class GuiFlow {
   public static final GuiStackX STACK_X = new GuiStackX();
   public static final GuiFlowFitX FIT_X = new GuiFlowFitX();
   public static final GuiStackY STACK_Y = new GuiStackY();

   public static boolean areChildrenExpandableX(List<? extends GuiControlRect> controls) {
      for (GuiControlRect rect : controls) {
         if (rect.isExpandableX()) {
            return true;
         }
      }

      return false;
   }

   public static boolean areChildrenExpandableY(List<? extends GuiControlRect> controls) {
      for (GuiControlRect rect : controls) {
         if (rect.isExpandableY()) {
            return true;
         }
      }

      return false;
   }

   public abstract int minWidth(List<? extends GuiControlRect> var1, int var2, int var3);

   public abstract int preferredWidth(List<? extends GuiControlRect> var1, int var2, int var3);

   public abstract int minHeight(List<? extends GuiControlRect> var1, int var2, int var3, int var4);

   public abstract int preferredHeight(List<? extends GuiControlRect> var1, int var2, int var3, int var4);

   public abstract void flowX(List<? extends GuiControlRect> var1, int var2, Align var3, int var4, int var5, boolean var6);

   public abstract void flowY(List<? extends GuiControlRect> var1, int var2, VAlign var3, int var4, int var5, int var6, boolean var7);
}
