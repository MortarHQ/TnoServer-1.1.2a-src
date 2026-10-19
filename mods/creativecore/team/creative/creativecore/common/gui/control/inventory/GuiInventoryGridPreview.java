package team.creative.creativecore.common.gui.control.inventory;

import net.minecraft.world.Container;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiInventoryGridPreview extends GuiParent {
   public final Container container;
   protected boolean hasFixedSize = false;
   private int fixedSize;
   protected boolean reverse = false;
   private int cols;
   private int rows;
   private int cachedCols;
   private int cachedRows;

   public GuiInventoryGridPreview(String name, Container container) {
      this(name, container, (int)Math.ceil(Math.sqrt(container.getContainerSize())));
      this.hasFixedSize = false;
   }

   public GuiInventoryGridPreview(String name, Container container, int cols) {
      this(name, container, cols, (int)Math.ceil((double)container.getContainerSize() / cols));
   }

   public GuiInventoryGridPreview(String name, Container container, int cols, int rows) {
      super(name);
      this.hasFixedSize = true;
      this.cols = cols;
      this.rows = rows;
      this.container = container;
      this.fixedSize = Math.min(container.getContainerSize(), cols * rows);

      for (int i = 0; i < this.fixedSize; i++) {
         super.add(new GuiSlotViewer(container.getItem(i)));
      }
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
   protected int minWidth(int availableWidth) {
      return this.hasFixedSize ? this.cols * 18 : 18;
   }

   @Override
   protected int minHeight(int width, int availableHeight) {
      return this.hasFixedSize ? this.rows * 18 : 18;
   }

   @Override
   public void flowX(int width, int preferred) {
      this.cachedCols = width / 18;
      if (this.hasFixedSize) {
         this.cachedCols = Math.min(this.cachedCols, this.cols);
      }

      int offset = (width - this.cachedCols * 18) / 2;
      int i = 0;

      for (GuiControl control : this.controls) {
         control.rect.setX(offset + i % this.cachedCols * 18);
         control.rect.setWidth(18, 18);
         control.rect.flowX();
         i++;
      }
   }

   @Override
   public void flowY(int witdh, int height, int preferred) {
      this.cachedRows = height / 18;
      if (this.hasFixedSize) {
         this.cachedRows = Math.min(this.cachedRows, this.rows);
      }

      int offset = (height - this.cachedRows * 18) / 2;
      int i = this.reverse ? this.controls.size() - 1 : 0;

      for (GuiControl control : this.controls) {
         int row = i / this.cachedCols;
         control.rect.setY(offset + row * 18);
         control.rect.setHeight(18, 18);
         control.rect.flowY();
         control.visible = row <= this.cachedRows;
         if (this.reverse) {
            i--;
         } else {
            i++;
         }
      }
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return this.cols * 18;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return (int)Math.ceil((double)this.container.getContainerSize() / this.cachedCols) * 18;
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }
}
