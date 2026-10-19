package net.darkhax.bookshelf.common.api.data.enchantment;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.function.ToIntBiFunction;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public enum EnchantmentLevel {
   HIGHEST((tag, enchantments) -> {
      int level = 0;

      for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
         if (((Holder)entry.getKey()).is(tag) && entry.getIntValue() > level) {
            level = entry.getIntValue();
         }
      }

      return level;
   }),
   LOWEST((tag, enchantments) -> {
      int level = Integer.MAX_VALUE;

      for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
         if (((Holder)entry.getKey()).is(tag) && entry.getIntValue() < level) {
            level = entry.getIntValue();
         }
      }

      return level;
   }),
   FIRST((tag, enchantments) -> {
      for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
         if (((Holder)entry.getKey()).is(tag)) {
            return entry.getIntValue();
         }
      }

      return 0;
   }),
   CUMULATIVE((tag, enchantments) -> {
      int level = 0;

      for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
         if (((Holder)entry.getKey()).is(tag)) {
            level += entry.getIntValue();
         }
      }

      return level;
   });

   private final ToIntBiFunction<TagKey<Enchantment>, ItemEnchantments> func;

   EnchantmentLevel(ToIntBiFunction<TagKey<Enchantment>, ItemEnchantments> func) {
      this.func = func;
   }

   public int get(TagKey<Enchantment> enchType, ItemStack stack) {
      return !stack.isEmpty() && stack.isEnchanted() ? this.func.applyAsInt(enchType, stack.getEnchantments()) : 0;
   }
}
