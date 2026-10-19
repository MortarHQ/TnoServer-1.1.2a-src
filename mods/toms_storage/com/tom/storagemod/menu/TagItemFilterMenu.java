package com.tom.storagemod.menu;

import com.tom.storagemod.Content;
import com.tom.storagemod.inventory.filter.TagFilter;
import com.tom.storagemod.menu.slot.PhantomSlot;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.util.DataSlots;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TagItemFilterMenu extends AbstractFilteredMenu {
   private TagFilter filter;
   public boolean allowList;
   private boolean synced;
   private BooleanSupplier isValid;
   private Runnable refresh;

   public TagItemFilterMenu(int wid, Inventory pinv, TagFilter filter, BooleanSupplier isValid, Runnable refresh) {
      this(wid, pinv);
      this.filter = filter;
      this.isValid = isValid;
      this.refresh = refresh;
   }

   public TagItemFilterMenu(int wid, Inventory pinv) {
      super(Content.tagItemFilterMenu.get(), wid, pinv);
      this.addSlot(new PhantomSlot(new SimpleContainer(1), 0, 8, 15));
      this.isValid = () -> true;

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(pinv, l, 8 + l * 18, 142));
      }

      this.addDataSlot(DataSlots.create(v -> this.allowList = v == 1, () -> this.filter.isAllowList() ? 1 : 0));
   }

   public boolean clickMenuButton(Player p_38875_, int btn) {
      boolean v = (btn & 1) != 0;
      btn >>= 1;
      if (btn == 0) {
         this.filter.setAllowList(v);
      }

      return false;
   }

   public boolean stillValid(Player playerIn) {
      return this.isValid.getAsBoolean();
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (!this.synced) {
         ListTag list = new ListTag();
         this.filter.getTags().forEach(t -> list.add(StringTag.valueOf(t.location().toString())));
         CompoundTag tag = new CompoundTag();
         tag.put("l", list);
         NetworkHandler.sendTo((ServerPlayer)this.pinv.player, tag);
         this.synced = true;
      }
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      Slot slot = (Slot)this.slots.get(index);
      if (slot != null && slot.hasItem() && index >= 1) {
         ItemStack is = slot.getItem().copy();
         is.setCount(1);
         Slot sl = (Slot)this.slots.get(0);
         if (ItemStack.isSameItemSameComponents(sl.getItem(), is)) {
            return ItemStack.EMPTY;
         }

         if (sl.getItem().isEmpty()) {
            sl.set(is);
            return ItemStack.EMPTY;
         }
      }

      return ItemStack.EMPTY;
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      if (this.filter != null) {
         this.filter.flush();
      }

      if (this.refresh != null) {
         this.refresh.run();
      }
   }

   @Override
   public void receive(CompoundTag tag) {
      super.receive(tag);
      if (tag.contains("l")) {
         ListTag list = tag.getList("l", 8);
         List<ResourceLocation> tags = new ArrayList<>();

         for (int i = 0; i < list.size(); i++) {
            ResourceLocation loc = ResourceLocation.tryParse(list.getString(i));
            if (loc != null) {
               tags.add(loc);
            }
         }

         this.filter.setTags(tags);
      }
   }
}
