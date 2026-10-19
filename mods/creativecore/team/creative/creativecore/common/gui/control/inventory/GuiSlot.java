package team.creative.creativecore.common.gui.control.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.CreativeCoreGuiRegistry;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.IGuiParent;
import team.creative.creativecore.common.gui.manager.GuiManagerItem;

public class GuiSlot extends GuiSlotBase {
   public final Slot slot;
   public int draggedIndex = -1;
   private ItemStack lastSend = null;
   private boolean changed = false;

   public GuiSlot(Container container, int index) {
      this("", container, index);
   }

   public GuiSlot(String name, Container container, int index) {
      this(name + index, new Slot(container, index, 0, 0));
   }

   public GuiSlot(Slot slot) {
      this(slot.getContainerSlot() + "", slot);
   }

   public GuiSlot(String name, Slot slot) {
      super(name);
      this.slot = slot;
   }

   @Override
   public ItemStack getStack() {
      return this.slot.getItem();
   }

   @Override
   protected ItemStack getStackToRender() {
      if (this.draggedIndex != -1) {
         ItemStack stack = this.itemManager().getHand().copy();
         int toAdd = Math.min(this.itemManager().additionalDragCount(this.draggedIndex), this.slot.getMaxStackSize(stack) - this.slot.getItem().getCount());
         stack.setCount(toAdd + this.slot.getItem().getCount());
         return stack;
      } else {
         return this.getStack();
      }
   }

   public GuiManagerItem itemManager() {
      return this.getLayer().itemManager();
   }

   public IGuiInventory inventory() {
      IGuiParent parent;
      for (parent = this.getParent(); !(parent instanceof IGuiInventory); parent = ((GuiParent)parent).getParent()) {
         if (!(parent instanceof GuiParent)) {
            throw new RuntimeException("Slot needs inventory parent");
         }
      }

      return (IGuiInventory)parent;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (Minecraft.getInstance().options.keyDrop.matches(keyCode, scanCode) && this.isHovered()) {
         CreativeCoreGuiRegistry.DROP.sendAndExecute(this, ByteTag.valueOf(Screen.hasControlDown()));
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   @Override
   public boolean mouseScrolled(double x, double y, double delta) {
      if (!Screen.hasShiftDown()) {
         return false;
      }

      if (delta > 0.0) {
         CreativeCoreGuiRegistry.INSERT.sendAndExecute(this, IntTag.valueOf((int)delta));
      } else {
         CreativeCoreGuiRegistry.EXTRACT.sendAndExecute(this, IntTag.valueOf((int)(-delta)));
      }

      return true;
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (this.itemManager().isDragged()) {
         return true;
      }

      if (Screen.hasShiftDown()) {
         if (this.slot.mayPickup(this.getPlayer())) {
            CreativeCoreGuiRegistry.INSERT.sendAndExecute(this, IntTag.valueOf(this.slot.getMaxStackSize()));
         }

         return true;
      } else {
         ItemStack hand = this.itemManager().getHand();
         if (!hand.isEmpty() && button < 2) {
            int stackSize = GuiManagerItem.freeSpace(this.slot, hand);
            if (stackSize > 0) {
               this.itemManager().startDrag(this, button == 1, stackSize);
            }

            if (stackSize != -1) {
               return true;
            }
         }

         if (button == 2) {
            CreativeCoreGuiRegistry.DUPLICATE.sendAndExecute(this, EndTag.INSTANCE);
         } else if (this.slot.mayPickup(this.getPlayer()) && (hand.isEmpty() || this.slot.mayPlace(hand))) {
            CreativeCoreGuiRegistry.SWAP.sendAndExecute(this, ByteTag.valueOf(button == 1));
         }

         return true;
      }
   }

   @Override
   public void mouseMoved(double x, double y) {
      if (this.draggedIndex == -1 && this.itemManager().isDragged() && this.rect.inside(x + this.rect.getX(), y + this.rect.getY())) {
         this.itemManager().addToDrag(this);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.changed && (this.lastSend == null || !ItemStack.matches(this.slot.getItem(), this.lastSend))) {
         this.changed();
      }
   }

   public void onSendUpdate() {
      this.changed = false;
      this.lastSend = this.slot.getItem().copy();
   }

   public void changed() {
      this.changed = true;
      this.inventory().setChanged(this.slot.getContainerSlot());
      if (this.draggedIndex != -1) {
         this.itemManager().modifyDrag(this);
      }
   }

   public ItemStack insert(ItemStack stack) {
      boolean canStack = AbstractContainerMenu.canItemQuickReplace(this.slot, stack, true);
      int availableStackSize = this.slot.getMaxStackSize(stack);
      if (canStack && this.slot.mayPlace(stack)) {
         int neededStackSize = stack.getCount();
         if (this.slot.hasItem()) {
            availableStackSize -= this.slot.getItem().getCount();
         }

         int additionalStackSize = Math.min(availableStackSize, neededStackSize);
         if (additionalStackSize > 0) {
            ItemStack inSlot = stack.copy();
            inSlot.setCount(additionalStackSize);
            if (this.slot.hasItem()) {
               inSlot.grow(this.slot.getItem().getCount());
            }

            this.slot.set(inSlot);
            stack.shrink(additionalStackSize);
            this.changed();
         }
      }

      return stack;
   }
}
