package team.creative.creativecore.common.gui.control.inventory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiInventoryGrid extends GuiParent implements IGuiInventory {
   public final Container container;
   protected boolean hasFixedSize;
   protected int fixedSize;
   protected boolean reverse = false;
   private int cols;
   private int rows;
   private int cachedCols;
   private int cachedRows;
   private boolean allChanged = false;
   private final BitSet changed = new BitSet();
   private List<Consumer<GuiSlot>> listeners;
   private final List<GuiSlot> slots = new ArrayList<>();

   public GuiInventoryGrid(String name, Container container) {
      this(name, container, (int)Math.ceil(Math.sqrt(container.getContainerSize())));
      this.hasFixedSize = false;
   }

   public GuiInventoryGrid(String name, Container container, int cols) {
      this(name, container, cols, (int)Math.ceil((double)container.getContainerSize() / cols));
   }

   public GuiInventoryGrid(String name, Container container, int cols, int rows) {
      this(name, container, cols, rows, (c, i) -> new Slot(c, i, 0, 0));
   }

   public GuiInventoryGrid(String name, Container container, int cols, int rows, BiFunction<Container, Integer, Slot> slotFactory) {
      super(name);
      this.hasFixedSize = true;
      this.cols = cols;
      this.rows = rows;
      this.container = container;
      this.fixedSize = Math.min(container.getContainerSize(), cols * rows);
      this.createInventoryGrid(slotFactory);
   }

   protected void createInventoryGrid(BiFunction<Container, Integer, Slot> slotFactory) {
      for (int i = 0; i < this.fixedSize; i++) {
         this.addSlot(new GuiSlot(slotFactory.apply(this.container, i)));
      }
   }

   public GuiInventoryGrid disableSlot(int index) {
      this.getSlot(index).enabled = false;
      return this;
   }

   public GuiInventoryGrid addListener(Consumer<GuiSlot> slot) {
      if (this.listeners == null) {
         this.listeners = new ArrayList<>();
      }

      this.listeners.add(slot);
      return this;
   }

   protected GuiControl addSlot(GuiSlot slot) {
      while (slot.slot.getContainerSlot() >= this.slots.size()) {
         this.slots.add(null);
      }

      this.slots.set(slot.slot.getContainerSlot(), slot);
      return super.add(slot);
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
         control.rect.setWidth(18, width);
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
         control.rect.setHeight(18, height);
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

   @Override
   public GuiSlot getSlot(int index) {
      return this.slots.get(index);
   }

   @Override
   public int inventorySize() {
      return this.hasFixedSize ? this.fixedSize : this.container.getContainerSize();
   }

   @Override
   public String name() {
      return this.name;
   }

   @Override
   public void tick() {
      if (this.allChanged) {
         this.container.setChanged();
         this.syncAll();
         this.allChanged = false;
         this.changed.clear();
      } else if (!this.changed.isEmpty()) {
         this.container.setChanged();
         this.sync(this.changed);
         this.changed.clear();
      }

      super.tick();
   }

   @Override
   public void setChanged() {
      this.allChanged = true;
      if (this.listeners != null) {
         for (Consumer<GuiSlot> listener : this.listeners) {
            listener.accept(null);
         }
      }
   }

   @Override
   public void setChanged(int slotIndex) {
      if (!this.allChanged) {
         this.changed.set(slotIndex);
         GuiSlot slot = this.getSlot(slotIndex);
         if (this.listeners != null) {
            for (Consumer<GuiSlot> listener : this.listeners) {
               listener.accept(slot);
            }
         }
      }
   }
}
