package com.blamejared.crafttweaker.api.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.ILootModifier")
@Document("vanilla/api/loot/modifier/ILootModifier")
public interface ILootModifier {
   ILootModifier DEFAULT = (loot, context) -> loot;

   @ZenCodeType.Method
   List<IItemStack> modify(List<IItemStack> var1, LootContext var2);

   default ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
      return this.modify(loot.stream().map(IItemStack::of).collect(Collectors.toList()), context)
         .stream()
         .map(IItemStack::getImmutableInternal)
         .collect(Collectors.toCollection(ObjectArrayList::new));
   }
}
