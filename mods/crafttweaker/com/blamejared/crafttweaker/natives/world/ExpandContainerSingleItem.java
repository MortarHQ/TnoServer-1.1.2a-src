package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/ContainerSingleItem")
@NativeTypeRegistration(value = ContainerSingleItem.class, zenCodeName = "crafttweaker.api.world.ContainerSingleItem")
public class ExpandContainerSingleItem {
   @ZenCodeType.Method
   public static ItemStack getTheItem(ContainerSingleItem internal) {
      return internal.getTheItem();
   }

   @ZenCodeType.Method
   public static void setTheItem(ContainerSingleItem internal, ItemStack stack) {
      internal.setTheItem(stack);
   }

   @ZenCodeType.Method
   public static ItemStack splitTheItem(ContainerSingleItem internal, int amount) {
      return internal.splitTheItem(amount);
   }

   public static ItemStack removeTheItem(ContainerSingleItem internal) {
      return internal.removeTheItem();
   }
}
