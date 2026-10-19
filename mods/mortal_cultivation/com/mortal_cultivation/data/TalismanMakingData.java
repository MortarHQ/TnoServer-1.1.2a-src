package com.mortal_cultivation.data;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class TalismanMakingData extends SavedData {
   private static final String DATA_NAME = "mortal_cultivation_talisman_making";
   private ItemStack tool = ItemStack.EMPTY;
   private ItemStack legacyBrush = ItemStack.EMPTY;
   private ItemStack legacyInk = ItemStack.EMPTY;

   public TalismanMakingData() {
   }

   public TalismanMakingData(CompoundTag tag, Provider provider) {
      if (tag.contains("tool")) {
         this.tool = ItemStack.parseOptional(provider, tag.getCompound("tool"));
      }

      if (tag.contains("brush")) {
         this.legacyBrush = ItemStack.parseOptional(provider, tag.getCompound("brush"));
      }

      if (tag.contains("ink")) {
         this.legacyInk = ItemStack.parseOptional(provider, tag.getCompound("ink"));
      }
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      if (!this.tool.isEmpty()) {
         tag.put("tool", this.tool.save(provider, new CompoundTag()));
      }

      if (!this.legacyBrush.isEmpty()) {
         tag.put("brush", this.legacyBrush.save(provider, new CompoundTag()));
      }

      if (!this.legacyInk.isEmpty()) {
         tag.put("ink", this.legacyInk.save(provider, new CompoundTag()));
      }

      return tag;
   }

   public ItemStack getTool() {
      return this.tool.copy();
   }

   public void setTool(ItemStack stack) {
      this.tool = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
      this.setDirty();
   }

   public List<ItemStack> takeLegacyTools() {
      List<ItemStack> stacks = new ArrayList<>(2);
      if (!this.legacyBrush.isEmpty()) {
         stacks.add(this.legacyBrush.copy());
         this.legacyBrush = ItemStack.EMPTY;
      }

      if (!this.legacyInk.isEmpty()) {
         stacks.add(this.legacyInk.copy());
         this.legacyInk = ItemStack.EMPTY;
      }

      if (!stacks.isEmpty()) {
         this.setDirty();
      }

      return stacks;
   }

   public static TalismanMakingData get(ServerLevel level) {
      DimensionDataStorage storage = level.getDataStorage();
      return (TalismanMakingData)storage.computeIfAbsent(
         new Factory(TalismanMakingData::new, TalismanMakingData::new, DataFixTypes.LEVEL), "mortal_cultivation_talisman_making"
      );
   }
}
