package team.creative.creativecore.common.gui.manager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCoreGuiRegistry;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.control.inventory.GuiSlot;

public class GuiManagerItem extends GuiManager {
   private ItemStack hand = ItemStack.EMPTY;
   private boolean drag;
   private boolean rightClick;
   private List<GuiSlot> dragged;
   private List<Integer> stackSizes;
   private boolean handChanged;
   public SlotAccess handAccess = new SlotAccess() {
      public ItemStack get() {
         return GuiManagerItem.this.getHand();
      }

      public boolean set(ItemStack hand) {
         GuiManagerItem.this.setHand(hand);
         return true;
      }
   };

   public static int freeSpace(Slot slot, ItemStack hand) {
      if (slot.mayPlace(hand) && AbstractContainerMenu.canItemQuickReplace(slot, hand, true)) {
         int stackSize = slot.getMaxStackSize(hand);
         if (slot.hasItem()) {
            stackSize -= slot.getItem().getCount();
         }

         return stackSize;
      } else {
         return -1;
      }
   }

   public GuiManagerItem(GuiLayer layer) {
      super(layer);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void renderOverlay(GuiGraphics graphics, GuiLayer layer, int mouseX, int mouseY) {
      ItemStack stack = this.hand;
      int count = stack.getCount();
      if (this.drag) {
         int used = 0;
         int countPerSlot = Math.max(1, Mth.floor((float)this.hand.getCount() / this.stackSizes.size()));
         if (this.rightClick) {
            countPerSlot = 1;
         }

         for (int i = 0; i < this.stackSizes.size(); i++) {
            used += Math.min(countPerSlot, this.stackSizes.get(i));
         }

         int left = this.hand.getCount() - used;
         count = Math.max(0, left);
      }

      if (!stack.isEmpty() && (!this.drag || this.rightClick || this.dragged.size() > 1)) {
         PoseStack pose = graphics.pose();
         pose.pushPose();
         RenderSystem.disableScissor();
         pose.translate(mouseX - 8, mouseY - 8, 200.0F);
         GuiRenderHelper.drawItemStack(graphics, stack, 1.0F);
         graphics.renderItemDecorations(GuiRenderHelper.getFont(), stack, 0, 0, count == 1 ? null : count + "");
         pose.popPose();
      }
   }

   @Override
   public void tick() {
      if (this.handChanged) {
         this.handChanged = false;
         if (!this.layer.isClient()) {
            CreativeCoreGuiRegistry.HAND.send(this.layer, (CompoundTag)this.hand.saveOptional(this.layer.provider()));
         }
      }

      super.tick();
   }

   @Override
   public void closed() {
      if (!this.layer.isClient() && !this.hand.isEmpty() && !this.layer.getPlayer().addItem(this.hand)) {
         this.layer.getPlayer().drop(this.hand, false);
      }

      super.closed();
   }

   @Override
   public void mouseClickedOutside(double x, double y) {
      if (!this.hand.isEmpty()) {
         CreativeCoreGuiRegistry.DROP_HAND.send(this.layer, EndTag.INSTANCE);
      }
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      if (this.isDragged() && this.rightClick == (button == 1)) {
         this.endDrag();
      }
   }

   public void startDrag(GuiSlot slot, boolean rightClick, int stackSize) {
      this.drag = true;
      this.dragged = new ArrayList<>();
      this.dragged.add(slot);
      this.rightClick = rightClick;
      this.stackSizes = new ArrayList<>();
      this.stackSizes.add(stackSize);
      slot.draggedIndex = 0;
   }

   public void addToDrag(GuiSlot slot) {
      int stackSize = freeSpace(slot.slot, this.hand);
      if (stackSize > 0) {
         slot.draggedIndex = this.dragged.size();
         this.dragged.add(slot);
         this.stackSizes.add(stackSize);
      }
   }

   public void modifyDrag(GuiSlot slot) {
      this.stackSizes.set(slot.draggedIndex, freeSpace(slot.slot, this.hand));
   }

   public void abortDrag() {
      for (GuiSlot slot : this.dragged) {
         slot.draggedIndex = -1;
      }

      this.drag = false;
      this.rightClick = false;
      this.dragged = null;
      this.stackSizes = null;
   }

   public void endDrag() {
      CompoundTag nbt = new CompoundTag();
      nbt.putBoolean("rightClick", this.rightClick);
      HashMap<String, Integer> inventories = new HashMap<>();
      ListTag names = new ListTag();
      int[] ids = new int[this.dragged.size() * 2];
      int i = 0;

      for (GuiSlot slot : this.dragged) {
         String inventory = slot.inventory().name();
         Integer inventoryId = inventories.get(inventory);
         if (inventoryId == null) {
            inventories.put(inventory, inventoryId = inventories.size());
            names.add(StringTag.valueOf(inventory));
         }

         ids[i] = inventoryId;
         ids[i + 1] = slot.slot.getContainerSlot();
         i += 2;
      }

      nbt.putIntArray("ids", ids);
      nbt.put("names", names);
      CreativeCoreGuiRegistry.SPREAD.sendAndExecute(this.layer, nbt);
      if (this.isDragged()) {
         this.abortDrag();
      }
   }

   public boolean isDragged() {
      return this.drag;
   }

   public ItemStack getHand() {
      return this.hand;
   }

   public void setHandChanged() {
      this.handChanged = true;
   }

   public void setHand(ItemStack stack) {
      if (this.isDragged()) {
         this.abortDrag();
      }

      this.hand = stack;
      this.setHandChanged();
   }

   public int additionalDragCount(int index) {
      if (index >= this.hand.getCount()) {
         return 0;
      } else {
         return this.rightClick ? 1 : Math.max(1, Mth.floor((float)this.hand.getCount() / this.dragged.size()));
      }
   }
}
