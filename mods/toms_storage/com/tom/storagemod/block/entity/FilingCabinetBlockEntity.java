package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.menu.FilingCabinetMenu;
import com.tom.storagemod.util.FilingCabinetContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FilingCabinetBlockEntity extends BlockEntity implements MenuProvider, Nameable, Clearable {
   private FilingCabinetContainer inv = new FilingCabinetContainer(512, this::setChanged, this::canInteractWith);
   private Component name;

   public FilingCabinetBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
      super(Content.filingCabinetBE.get(), p_155229_, p_155230_);
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      tag.put("inventory", this.inv.createTag(provider));
      if (this.name != null) {
         tag.putString("CustomName", Serializer.toJson(this.name, provider));
      }
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      this.inv.fromTag(tag.getList("inventory", 10), provider);
      if (tag.contains("CustomName", 8)) {
         this.name = parseCustomNameSafe(tag.getString("CustomName"), provider);
      }
   }

   public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
      return new FilingCabinetMenu(p_39954_, p_39955_, this.inv);
   }

   public Component getDefaultName() {
      return Component.translatable("menu.toms_storage.filing_cabinet");
   }

   public Container getInv() {
      return this.inv;
   }

   private boolean canInteractWith(Player player) {
      return this.level.getBlockEntity(this.worldPosition) != this
         ? false
         : player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) < 64.0;
   }

   public Component getName() {
      return this.name != null ? this.name : this.getDefaultName();
   }

   public Component getDisplayName() {
      return this.getName();
   }

   public Component getCustomName() {
      return this.name;
   }

   public void clearContent() {
      this.inv.clearContent();
   }
}
