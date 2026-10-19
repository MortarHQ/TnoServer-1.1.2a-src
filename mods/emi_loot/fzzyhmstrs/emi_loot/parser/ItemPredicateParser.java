package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate.Type;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemPredicateParser {
   public static Component parseItemPredicate(ItemPredicate predicate) {
      List<MutableComponent> subTexts = new ArrayList<>();
      Optional<HolderSet<Item>> items = predicate.items();
      if (items.isPresent() && items.get().unwrapKey().isPresent()) {
         subTexts.add(LText.translatable("emi_loot.item_predicate.tag", ((TagKey)items.get().unwrapKey().get()).location().toString()));
      }

      if (items.isPresent() && items.get().size() > 0) {
         List<MutableComponent> list = items.get().stream().map(item -> (MutableComponent)((Item)item.value()).getDescription()).toList();
         subTexts.add(LText.translatable("emi_loot.item_predicate.items", ListProcessors.buildOrList(list)));
      }

      Ints count = predicate.count();
      if (count != Ints.ANY) {
         int finalMax = count.max().orElse(0);
         int finalMin = count.min().orElse(0);
         subTexts.add(LText.translatable("emi_loot.item_predicate.count", Integer.toString(finalMin), Integer.toString(finalMax)));
      }

      DataComponentPredicate componentPredicate = predicate.components();
      if (!componentPredicate.alwaysMatches()) {
         subTexts.add(LText.translatable("emi_loot.predicate.component"));
      }

      Map<Type<?>, ItemSubPredicate> subPredicates = predicate.subPredicates();

      for (Entry<Type<?>, ItemSubPredicate> entry : subPredicates.entrySet()) {
         subTexts.add(ItemSubPredicateParser.parseItemSubPredicate((Type<? extends ItemSubPredicate>)entry.getKey(), entry.getValue()).plainCopy());
      }

      if (subTexts.isEmpty()) {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Empty item predicate in table: " + LootTableParser.currentTable);
         }

         return LText.translatable("emi_loot.predicate.invalid");
      } else {
         return ListProcessors.buildAndList(subTexts);
      }
   }
}
