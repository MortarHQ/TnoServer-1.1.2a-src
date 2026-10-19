package fzzyhmstrs.emi_loot.server;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ComplexLootPoolBuilder extends AbstractLootPoolBuilder {
   final HashMap<List<TextKey>, SimpleLootPoolBuilder> map = new HashMap<>();
   final List<LootTableParser.LootConditionResult> conditions;
   final List<LootTableParser.LootFunctionResult> functions;
   HashMap<List<TextKey>, SimpleLootPoolBuilder> builtMap = new HashMap<>();

   public ComplexLootPoolBuilder(float rollWeight, List<LootTableParser.LootConditionResult> conditions, List<LootTableParser.LootFunctionResult> functions) {
      super(rollWeight);
      this.conditions = conditions;
      this.functions = functions;
   }

   @Override
   public void addItem(LootTableParser.ItemEntryResult result) {
      List<TextKey> testKey = new LinkedList<>();
      testKey.addAll(result.functions());
      testKey.addAll(result.conditions());
      SimpleLootPoolBuilder builder = this.map.getOrDefault(testKey, new SimpleLootPoolBuilder(this.rollWeight));
      builder.addItem(result);
      this.map.put(testKey, builder);
   }

   @Override
   public void build() {
      if (this.map.isEmpty()) {
         this.isEmpty = true;
      } else {
         for (List<TextKey> key : this.map.keySet()) {
            SimpleLootPoolBuilder builder = this.map.getOrDefault(key, new SimpleLootPoolBuilder(this.rollWeight));
            builder.build();
            if (this.map.size() == 1 && builder.isEmpty) {
               this.isEmpty = true;
               return;
            }

            if (this.map.size() == 1 && this.conditions.isEmpty() && this.functions.isEmpty() && builder.isSimple && this.checkKey(key)) {
               this.simpleStack = builder.simpleStack;
               this.isSimple = true;
            }

            this.builtMap.put(key, builder);
         }
      }
   }

   private boolean checkKey(List<TextKey> keys) {
      if (keys.isEmpty()) {
         return true;
      } else {
         return keys.size() != 1 ? false : keys.get(0).index() == 0 || keys.get(0).index() == 150;
      }
   }

   @Override
   public List<LootTableParser.ItemEntryResult> revert() {
      List<LootTableParser.ItemEntryResult> list = new LinkedList<>();
      List<TextKey> topLevelKeys = new LinkedList<>();
      this.conditions.forEach(condition -> topLevelKeys.add(condition.text()));
      this.functions.forEach(function -> topLevelKeys.add(function.text()));
      this.map.forEach((keyList, builder) -> {
         List<LootTableParser.ItemEntryResult> builderList = builder.revert();
         builderList.forEach(builderEntry -> {
            builderEntry.functions().addAll(keyList);
            builderEntry.conditions().addAll(topLevelKeys);
         });
         list.addAll(builderList);
      });
      return list;
   }
}
