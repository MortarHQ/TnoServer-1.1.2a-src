package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidPredicateParser {
   public static Component parseFluidPredicate(FluidPredicate predicate) {
      return LText.translatable("emi_loot.fluid_predicate.base", parseFluidPredicateInternal(predicate));
   }

   private static Component parseFluidPredicateInternal(FluidPredicate predicate) {
      Optional<HolderSet<Fluid>> fluid = predicate.fluids();
      if (fluid.isPresent() && fluid.get().unwrapKey().isPresent()) {
         return LText.translatable("emi_loot.fluid_predicate.tag", ((TagKey)fluid.get().unwrapKey().get()).location().toString());
      }

      if (fluid.isPresent() && fluid.get().size() > 0) {
         List<MutableComponent> list = fluid.get()
            .stream()
            .map(entry -> ((Fluid)entry.value()).defaultFluidState().createLegacyBlock().getBlock().getName())
            .toList();
         return LText.translatable("emi_loot.fluid_predicate.fluid", ListProcessors.buildOrList(list));
      }

      Optional<StatePropertiesPredicate> statePredicate = predicate.properties();
      if (statePredicate.isPresent()) {
         return StatePredicateParser.parseStatePredicate(statePredicate.get());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable fluid predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
