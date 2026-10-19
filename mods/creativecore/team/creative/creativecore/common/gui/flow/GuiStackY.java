package team.creative.creativecore.common.gui.flow;

import java.util.List;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControlRect;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.util.math.Maths;
import team.creative.creativecore.common.util.type.list.MarkIterator;
import team.creative.creativecore.common.util.type.list.MarkList;

public class GuiStackY extends GuiFlow {
   @Override
   public int minWidth(List<? extends GuiControlRect> controls, int spacing, int availableWidth) {
      int width = 0;

      for (GuiControlRect child : controls) {
         width = Math.max(width, child.getMinWidth(availableWidth));
      }

      return width;
   }

   @Override
   public int preferredWidth(List<? extends GuiControlRect> controls, int spacing, int availableWidth) {
      int width = 0;

      for (GuiControlRect child : controls) {
         width = Math.max(width, child.getPreferredWidth(availableWidth));
      }

      return width;
   }

   @Override
   public int minHeight(List<? extends GuiControlRect> controls, int spacing, int width, int availableHeight) {
      int height = -spacing;
      boolean has = false;

      for (GuiControlRect child : controls) {
         int min = child.getMinHeight(availableHeight);
         if (min != -1) {
            height += min;
            has = true;
         }

         height += spacing;
      }

      return has ? height : -1;
   }

   @Override
   public int preferredHeight(List<? extends GuiControlRect> controls, int spacing, int width, int availableHeight) {
      int height = -spacing;

      for (GuiControlRect child : controls) {
         height += child.getPreferredHeight(availableHeight) + spacing;
      }

      return height;
   }

   @Override
   public void flowX(List<? extends GuiControlRect> controls, int spacing, Align align, int width, int preferred, boolean endless) {
      int maxWidth = width;
      if (endless && preferred > width) {
         maxWidth = preferred;
      }

      boolean expandable = areChildrenExpandableX(controls);
      if (align == Align.LEFT && !expandable) {
         for (GuiControlRect child : controls) {
            child.setX(0);
            child.setWidth(Math.min(maxWidth, child.getPreferredWidth(width)), width);
            child.flowX();
         }
      } else if (align != Align.STRETCH && !expandable) {
         if (align == Align.RIGHT) {
            for (GuiControlRect child : controls) {
               child.setWidth(Math.min(maxWidth, child.getPreferredWidth(width)), width);
               child.setX(maxWidth - child.getWidth());
               child.flowX();
            }
         } else {
            for (GuiControlRect child : controls) {
               child.setWidth(Math.min(maxWidth, child.getPreferredWidth(width)), width);
               child.setX((int)Math.ceil(maxWidth / 2.0 - child.getWidth() / 2.0));
               child.flowX();
            }
         }
      } else {
         for (GuiControlRect child : controls) {
            if (!child.isExpandableX() && align != Align.STRETCH) {
               child.setWidth(Math.min(maxWidth, child.getPreferredWidth(width)), width);
            } else {
               child.setWidth(maxWidth, width);
            }

            child.setX(0);
            child.flowX();
         }
      }
   }

   @Override
   public void flowY(List<? extends GuiControlRect> controls, int spacing, VAlign valign, int width, int height, int preferred, boolean endless) {
      int available = height - spacing * (controls.size() - 1);
      MarkList<? extends GuiControlRect> list = new MarkList<>(controls);
      if (height >= preferred) {
         if (valign == VAlign.STRETCH && !areChildrenExpandableY(controls)) {
            for (GuiControlRect child : list) {
               int min = child.getMinHeight(height);
               if (min != -1) {
                  available -= min;
                  child.setHeight(min, height);
               } else {
                  child.setHeight(0, height);
               }
            }

            while (available > 0 && !list.isEmpty()) {
               int average = (int)Math.ceil((double)available / list.remaing());
               MarkIterator<? extends GuiControlRect> itr = list.iterator();

               while (itr.hasNext()) {
                  GuiControlRect child = itr.next();
                  int toAdd = Maths.min(average, available, child.getPreferredHeight(height) - child.getHeight());
                  if (toAdd <= 0) {
                     itr.mark();
                  } else {
                     available -= child.addHeight(toAdd, height);
                     if (child.isMaxHeight(height)) {
                        itr.mark();
                     }
                  }
               }
            }

            list.clear();

            while (available > 0 && !list.isEmpty()) {
               int average = (int)Math.ceil((double)available / list.remaing());
               MarkIterator<? extends GuiControlRect> itr = list.iterator();

               while (itr.hasNext()) {
                  GuiControlRect child = itr.next();
                  available -= child.addHeight(Math.min(average, available), height);
                  if (child.isMaxHeight(height)) {
                     itr.mark();
                  }
               }
            }
         } else {
            MarkIterator<? extends GuiControlRect> itr = list.iterator();

            while (itr.hasNext()) {
               GuiControlRect child = itr.next();
               child.setHeight(child.getPreferredHeight(height), height);
               if (!child.isExpandableY()) {
                  itr.mark();
               }

               available -= child.getHeight();
            }

            if (valign == VAlign.STRETCH || areChildrenExpandableY(controls)) {
               while (available > 0 && !list.isEmpty()) {
                  int average = (int)Math.ceil((double)available / list.remaing());
                  MarkIterator<? extends GuiControlRect> itrx = list.iterator();

                  while (itrx.hasNext()) {
                     GuiControlRect child = itrx.next();
                     available -= child.addHeight(Math.min(average, available), height);
                     if (child.isMaxHeight(height)) {
                        itrx.mark();
                     }
                  }
               }
            }
         }
      } else if (endless) {
         for (GuiControlRect child : controls) {
            child.setHeight(child.getPreferredHeight(height), height);
         }

         valign = VAlign.TOP;
      } else {
         for (GuiControlRect child : list) {
            int min = child.getMinHeight(height);
            if (min != -1) {
               available -= min;
               child.setHeight(min, height);
            } else {
               child.setHeight(0, height);
            }
         }

         while (available > 0 && !list.isEmpty()) {
            int average = (int)Math.ceil((double)available / list.remaing());
            MarkIterator<? extends GuiControlRect> itr = list.iterator();

            while (itr.hasNext()) {
               GuiControlRect child = itr.next();
               int prefer = child.getPreferredHeight(height);
               available -= child.addHeight(Math.min(average, Math.min(prefer - child.getHeight(), available)), height);
               if (child.isMaxHeight(height) || prefer <= child.getHeight()) {
                  itr.mark();
               }
            }
         }

         list.clear();

         while (available > 0 && !list.isEmpty()) {
            int average = (int)Math.ceil((double)available / list.remaing());
            MarkIterator<? extends GuiControlRect> itr = list.iterator();

            while (itr.hasNext()) {
               GuiControlRect child = itr.next();
               available -= child.addHeight(Math.min(average, available), height);
               if (child.isMaxHeight(height)) {
                  itr.mark();
               }
            }
         }
      }

      for (GuiControlRect child : controls) {
         child.flowY();
      }

      if (available > 0 && valign != VAlign.TOP) {
         if (valign == VAlign.BOTTOM) {
            int y = height;

            for (GuiControlRect child : controls) {
               y -= child.getHeight();
               child.setY(y);
               y -= spacing;
            }
         } else if (valign == VAlign.CENTER || valign == VAlign.STRETCH) {
            int y = available / 2;

            for (GuiControlRect child : controls) {
               child.setY(y);
               y += child.getHeight() + spacing;
            }
         }
      } else {
         int y = 0;

         for (GuiControlRect child : controls) {
            child.setY(y);
            y += child.getHeight() + spacing;
         }
      }
   }
}
