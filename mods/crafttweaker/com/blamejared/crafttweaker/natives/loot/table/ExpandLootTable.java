package com.blamejared.crafttweaker.natives.loot.table;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.mixin.common.access.loot.AccessLootTable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootTable")
@NativeTypeRegistration(value = LootTable.class, zenCodeName = "crafttweaker.api.loot.LootTable")
public final class ExpandLootTable {
   @ZenCodeType.Method
   public static void getRandomItemsRaw(LootTable internal, LootContext context, Consumer<IItemStack> stackConsumer) {
      internal.getRandomItemsRaw(context, itemStack -> stackConsumer.accept(IItemStack.of(itemStack)));
   }

   @ZenCodeType.Method
   public static void getRandomItems(LootTable internal, LootContext context, Consumer<IItemStack> stackConsumer) {
      internal.getRandomItems(context, itemStack -> stackConsumer.accept(IItemStack.of(itemStack)));
   }

   @ZenCodeType.Method
   public static List<IItemStack> getRandomItems(LootTable internal, LootContext context) {
      return ((AccessLootTable)internal).crafttweaker$callGetRandomItems(context).stream().map(IItemStack::of).collect(Collectors.toList());
   }

   @ZenCodeType.Method
   public static List<IItemStack> getRandomItems(LootTable internal, LootParams params) {
      return internal.getRandomItems(params).stream().map(IItemStack::of).collect(Collectors.toList());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("paramSet")
   public static LootContextParamSet getParamSet(LootTable internal) {
      return internal.getParamSet();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("id")
   public static ResourceLocation getId(LootTable internal) {
      return CraftTweakerAPI.getAccessibleElementsProvider().registryAccess().registryOrThrow(Registries.LOOT_TABLE).getKey(internal);
   }

   @ZenCodeType.Method
   public static void fill(LootTable internal, Container container, LootParams params, @ZenCodeType.OptionalLong long seed) {
      internal.fill(container, params, seed);
   }
}
