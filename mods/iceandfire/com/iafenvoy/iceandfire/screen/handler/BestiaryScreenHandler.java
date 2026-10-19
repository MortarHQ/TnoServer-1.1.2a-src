package com.iafenvoy.iceandfire.screen.handler;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.registry.IafItems;
import com.iafenvoy.iceandfire.registry.IafScreenHandlers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BestiaryScreenHandler extends AbstractContainerMenu {
   private ItemStack bookStack = ItemStack.EMPTY;

   public BestiaryScreenHandler(int syncId, Inventory playerInventory) {
      super((MenuType)IafScreenHandlers.BESTIARY_SCREEN.get(), syncId);
   }

   public BestiaryScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
      this(syncId, playerInventory);
      CompoundTag nbt = buf.readNbt();
      if (nbt != null) {
         this.bookStack = ItemStack.OPTIONAL_CODEC.parse(NbtOps.INSTANCE, nbt.get("data")).resultOrPartial(IceAndFire.LOGGER::error).orElse(ItemStack.EMPTY);
      }
   }

   public ItemStack quickMoveStack(Player player, int slot) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return player.getItemInHand(InteractionHand.MAIN_HAND).is((Item)IafItems.BESTIARY.get())
         || player.getItemInHand(InteractionHand.OFF_HAND).is((Item)IafItems.BESTIARY.get());
   }

   public ItemStack getBook() {
      return this.bookStack;
   }
}
