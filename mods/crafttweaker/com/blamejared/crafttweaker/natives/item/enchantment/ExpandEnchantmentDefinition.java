package com.blamejared.crafttweaker.natives.item.enchantment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment.Cost;
import net.minecraft.world.item.enchantment.Enchantment.EnchantmentDefinition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/enchantment/EnchantmentDefinition")
@NativeTypeRegistration(value = EnchantmentDefinition.class, zenCodeName = "crafttweaker.api.item.enchantment.EnchantmentDefinition")
public class ExpandEnchantmentDefinition {
   @ZenCodeType.Getter("supportedItems")
   public static Item[] supportedItems(EnchantmentDefinition internal) {
      return internal.supportedItems().stream().map(Holder::value).toArray(Item[]::new);
   }

   @ZenCodeType.Getter("maxCost")
   public static Cost maxCost(EnchantmentDefinition internal) {
      return internal.maxCost();
   }

   @ZenCodeType.Getter("weight")
   public static int weight(EnchantmentDefinition internal) {
      return internal.weight();
   }

   @ZenCodeType.Getter("maxLevel")
   public static int maxLevel(EnchantmentDefinition internal) {
      return internal.maxLevel();
   }

   @ZenCodeType.Getter("anvilCost")
   public static int anvilCost(EnchantmentDefinition internal) {
      return internal.anvilCost();
   }

   @ZenCodeType.Getter("slots")
   public static List<EquipmentSlotGroup> slots(EnchantmentDefinition internal) {
      return internal.slots();
   }

   @ZenCodeType.Getter("minCost")
   public static Cost minCost(EnchantmentDefinition internal) {
      return internal.minCost();
   }

   @ZenCodeType.Getter("primaryItems")
   public static Item[] primaryItems(EnchantmentDefinition internal) {
      return internal.primaryItems().isEmpty()
         ? new Item[0]
         : internal.primaryItems().map(holders -> holders.stream().map(Holder::value).toList()).orElseGet(Collections::emptyList).toArray(Item[]::new);
   }
}
