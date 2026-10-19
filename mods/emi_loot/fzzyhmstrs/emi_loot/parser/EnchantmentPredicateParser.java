package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentPredicateParser {
   public static Component parseEnchantmentPredicates(List<EnchantmentPredicate> list) {
      List<MutableComponent> list2 = new ArrayList<>();

      for (EnchantmentPredicate predicate : list) {
         Optional<HolderSet<Enchantment>> enchant = predicate.enchantments();
         if (enchant.isPresent() && enchant.get().unwrapKey().isPresent()) {
            if (predicate.level() == Ints.ANY) {
               list2.add(LText.translatable("emi_loot.item_predicate.enchant.tag", ((TagKey)enchant.get().unwrapKey().get()).location().toString()));
            } else {
               Component numberRange = NumberProcessors.processNumberRange(
                  predicate.level(),
                  "emi_loot.item_predicate.enchant.levels_1",
                  "emi_loot.item_predicate.enchant.levels_2",
                  "emi_loot.item_predicate.enchant.levels_3",
                  "emi_loot.item_predicate.enchant.levels_4",
                  ""
               );
               Component e = LText.translatable("emi_loot.item_predicate.enchant.tag", ((TagKey)enchant.get().unwrapKey().get()).location().toString());
               list2.add(LText.translatable("emi_loot.item_predicate.enchant.levels", e, numberRange));
            }
         }

         if (enchant.isPresent() && enchant.get().size() > 0) {
            List<MutableComponent> list3 = enchant.get().stream().map(entry -> LText.enchant((Holder<Enchantment>)entry).plainCopy()).toList();
            if (predicate.level() == Ints.ANY) {
               list2.add(LText.translatable("emi_loot.item_predicate.enchant.list", ListProcessors.buildOrList(list3)));
            } else {
               Component numberRange = NumberProcessors.processNumberRange(
                  predicate.level(),
                  "emi_loot.item_predicate.enchant.levels_1",
                  "emi_loot.item_predicate.enchant.levels_2",
                  "emi_loot.item_predicate.enchant.levels_3",
                  "emi_loot.item_predicate.enchant.levels_4",
                  ""
               );
               Component e = LText.translatable("emi_loot.item_predicate.enchant.list", ListProcessors.buildOrList(list3));
               list2.add(LText.translatable("emi_loot.item_predicate.enchant.levels", e, numberRange));
            }
         }
      }

      if (!list2.isEmpty()) {
         return LText.translatable("emi_loot.item_predicate.enchant", ListProcessors.buildAndList(list2));
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable enchantment predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
