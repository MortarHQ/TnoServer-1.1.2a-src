package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockPredicateParser {
   public static Component parseBlockPredicate(BlockPredicate predicate) {
      return LText.translatable("emi_loot.block_predicate.base", parseBlockPredicateInternal(predicate));
   }

   private static Component parseBlockPredicateInternal(BlockPredicate predicate) {
      Optional<HolderSet<Block>> blocks = predicate.blocks();
      if (blocks.isPresent() && blocks.get().unwrapKey().isPresent()) {
         return LText.translatable("emi_loot.block_predicate.tag", ((TagKey)blocks.get().unwrapKey().get()).location().toString());
      }

      if (blocks.isPresent() && blocks.get().size() > 0) {
         List<MutableComponent> list = blocks.get().stream().map(entry -> ((Block)entry.value()).getName()).toList();
         return LText.translatable("emi_loot.block_predicate.list_1", ListProcessors.buildOrList(list));
      }

      Optional<StatePropertiesPredicate> statePredicate = predicate.properties();
      if (statePredicate.isPresent()) {
         return StatePredicateParser.parseStatePredicate(statePredicate.get());
      }

      Optional<NbtPredicate> nbt = predicate.nbt();
      if (nbt.isPresent()) {
         return NbtPredicateParser.parseNbtPredicate(nbt.get());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable block predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
