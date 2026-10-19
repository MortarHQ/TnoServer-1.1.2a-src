package com.chronodawn.compat;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class ItemDataHandler121 implements ItemDataHandler {
   @Override
   public void setString(ItemStack stack, String key, String value) {
      if (stack != null && !stack.isEmpty()) {
         CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString(key, value));
      }
   }

   @Override
   public String getString(ItemStack stack, String key) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
         return customData != null ? customData.copyTag().getString(key) : "";
      } else {
         return "";
      }
   }

   @Override
   public void setInt(ItemStack stack, String key, int value) {
      if (stack != null && !stack.isEmpty()) {
         CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(key, value));
      }
   }

   @Override
   public int getInt(ItemStack stack, String key) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
         return customData != null ? customData.copyTag().getInt(key) : 0;
      } else {
         return 0;
      }
   }

   @Override
   public boolean contains(ItemStack stack, String key) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
         return customData != null ? customData.copyTag().contains(key) : false;
      } else {
         return false;
      }
   }

   @Override
   public CompoundTag getCustomData(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
         return customData != null ? customData.copyTag() : new CompoundTag();
      } else {
         return new CompoundTag();
      }
   }

   @Override
   public void updateCustomData(ItemStack stack, Consumer<CompoundTag> updater) {
      if (stack != null && !stack.isEmpty()) {
         CustomData.update(DataComponents.CUSTOM_DATA, stack, updater);
      } else {
         updater.accept(new CompoundTag());
      }
   }
}
