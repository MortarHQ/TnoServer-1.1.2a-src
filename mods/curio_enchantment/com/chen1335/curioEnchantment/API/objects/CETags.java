package com.chen1335.curioEnchantment.API.objects;

import com.chen1335.curioEnchantment.CurioEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public interface CETags {
   interface EnchantmentTags {
      TagKey<Enchantment> ENCHANTMENT = TagKey.create(Registries.ENCHANTMENT, CurioEnchantment.id("enchantment"));
      TagKey<Enchantment> COMMON_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, CurioEnchantment.id("common_enchantments"));
      TagKey<Enchantment> DEVOTED_EXCLUSIVE = TagKey.create(Registries.ENCHANTMENT, CurioEnchantment.id("devoted_exclusive"));
   }

   interface ItemTags {
      TagKey<Item> CURIOS_ENCHANTABLE = TagKey.create(Registries.ITEM, CurioEnchantment.id("curios_enchantable"));
      TagKey<Item> SPELLBOOK_ENCHANTABLE = TagKey.create(Registries.ITEM, CurioEnchantment.id("spellbook_enchantable"));
      TagKey<Item> HANDS_ENCHANTABLE = TagKey.create(Registries.ITEM, CurioEnchantment.id("hands_enchantable"));
   }
}
