package com.tom.storagemod.menu;

import com.tom.storagemod.Content;
import com.tom.storagemod.components.ConfiguratorComponent;
import com.tom.storagemod.inventory.BlockFilter;
import com.tom.storagemod.inventory.PlatformInventoryAccess;
import com.tom.storagemod.item.IItemFilter;
import com.tom.storagemod.menu.slot.ItemFilterSlot;
import com.tom.storagemod.util.DataSlots;
import com.tom.storagemod.util.Priority;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InventoryConfiguratorMenu extends AbstractContainerMenu {
   private BlockPos pos;
   private BlockFilter filter;
   private InteractionHand hand;
   public Direction side = Direction.DOWN;
   public Priority priority = Priority.NORMAL;
   public boolean skip;
   public boolean keepLast;
   private boolean isClosed = false;

   public InventoryConfiguratorMenu(int wid, Inventory pinv) {
      this(wid, pinv, null, null, null);
   }

   public InventoryConfiguratorMenu(int wid, Inventory pinv, BlockPos te, BlockFilter f, InteractionHand hand) {
      super(Content.invConfigMenu.get(), wid);
      this.pos = te;
      this.filter = f;
      this.hand = hand;
      Container inv = f == null ? new SimpleContainer(1) : f.filter;
      this.addSlot(new ItemFilterSlot(inv, 0, 80, 32));

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(pinv, l, 8 + l * 18, 142));
      }

      this.addDataSlot(DataSlots.create(v -> this.priority = Priority.VALUES[v % Priority.VALUES.length], () -> f.getPriority().ordinal()));
      this.addDataSlot(DataSlots.create(v -> this.side = Direction.from3DDataValue(v), () -> f.getSide().ordinal()));
      this.addDataSlot(DataSlots.create(v -> this.skip = v != 0, () -> f.skip() ? 1 : 0));
      this.addDataSlot(DataSlots.create(v -> this.keepLast = v != 0, () -> f.isKeepLast() ? 1 : 0));
   }

   public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return (this.pos == null || player.distanceToSqr(this.pos.getX(), this.pos.getY(), this.pos.getZ()) < 256.0) && !this.isClosed;
   }

   public boolean clickMenuButton(Player player, int id) {
      int mode = id & 7;
      int arg = id >> 3;
      switch (mode) {
         case 0:
            this.filter.setPriority(Priority.VALUES[arg % Priority.VALUES.length]);
            break;
         case 1:
            ItemStack isx = player.getItemInHand(this.hand);
            if (isx.is(Content.invConfig.get())) {
               ConfiguratorComponent.Configurator c = new ConfiguratorComponent.Configurator(isx, player.level().getGameTime());
               c.startSelection(this.pos, this.filter.getConnectedBlocks());
               this.isClosed = true;
            }
            break;
         case 2:
            this.filter.getConnectedBlocks().clear();
            this.filter.getConnectedBlocks().add(this.filter.getMainPos());
            ItemStack is = player.getItemInHand(this.hand);
            if (is.is(Content.invConfig.get())) {
               ConfiguratorComponent.Configurator c = new ConfiguratorComponent.Configurator(is, player.level().getGameTime());
               c.setSelection(this.filter.getConnectedBlocks());
            }
            break;
         case 3:
            this.filter.setSide(Direction.from3DDataValue(arg));
            break;
         case 4:
            this.filter.setSkip(arg != 0);
            break;
         case 5:
            this.filter.setKeepLast(arg != 0);
            break;
         case 6:
            if (arg >= 0 && arg < this.slots.size()) {
               Slot s = (Slot)this.slots.get(arg);
               if (s instanceof ItemFilterSlot && s.getItem().getItem() instanceof IItemFilter f) {
                  f.openGui(s.getItem(), player, () -> s.getItem().getItem() == f, this.filter::markFilterDirty);
               }
            }
            break;
         case 7:
            this.isClosed = true;
            PlatformInventoryAccess.removeBlockFilterAt(player.level(), this.pos);
      }

      player.level().blockEntityChanged(this.pos);
      return true;
   }

   public void removed(Player player) {
      super.removed(player);
      if (!player.level().isClientSide) {
         player.level().blockEntityChanged(this.pos);
      }
   }
}
