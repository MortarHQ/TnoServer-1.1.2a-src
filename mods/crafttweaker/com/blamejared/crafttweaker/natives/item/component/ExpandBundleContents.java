package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.Optionull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.BundleContents.Mutable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/BundleContents")
@NativeTypeRegistration(value = BundleContents.class, zenCodeName = "crafttweaker.api.item.component.BundleContents")
public class ExpandBundleContents {
   @ZenCodeType.StaticExpansionMethod
   public static BundleContents of(List<IItemStack> items) {
      return new BundleContents(Lists.transform(items, IItemStack::getInternal));
   }

   @ZenCodeType.Getter("items")
   public static Iterable<ItemStack> items(BundleContents internal) {
      return Lists.newArrayList(internal.itemsCopy());
   }

   @ZenCodeType.Getter("isEmpty")
   public static boolean isEmpty(BundleContents internal) {
      return internal.isEmpty();
   }

   @ZenCodeType.Getter("size")
   public static int size(BundleContents internal) {
      return internal.size();
   }

   @ZenRegister
   @Document("vanilla/api/item/component/MutableBundleContents")
   @NativeTypeRegistration(value = Mutable.class, zenCodeName = "crafttweaker.api.item.component.MutableBundleContents")
   public static class ExpandBundleContentsMutable {
      @ZenCodeType.StaticExpansionMethod
      public static Mutable of(BundleContents contents) {
         return new Mutable(contents);
      }

      @ZenCodeType.Method
      public static Mutable clearItems(Mutable internal) {
         return internal.clearItems();
      }

      @ZenCodeType.Method
      @ZenCodeType.Caster(implicit = true)
      public static BundleContents toImmutable(Mutable internal) {
         return internal.toImmutable();
      }

      @ZenCodeType.Method
      public static int tryInsert(Mutable internal, ItemStack stack) {
         return internal.tryInsert(stack);
      }

      @ZenCodeType.Method
      public static @ZenCodeType.Nullable IItemStack removeOne(Mutable internal) {
         return (IItemStack)Optionull.map(internal.removeOne(), IItemStack::of);
      }
   }
}
