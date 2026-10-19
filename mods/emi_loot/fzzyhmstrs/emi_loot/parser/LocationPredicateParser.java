package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationPredicate.PositionPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class LocationPredicateParser {
   public static Component parseLocationPredicate(LocationPredicate predicate) {
      Optional<PositionPredicate> position = predicate.position();
      if (position.isPresent()) {
         Doubles x = position.get().x();
         if (!x.equals(Doubles.ANY)) {
            return NumberProcessors.processNumberRange(
               x,
               "emi_loot.location_predicate.x.exact",
               "emi_loot.location_predicate.x",
               "emi_loot.location_predicate.x.at_least",
               "emi_loot.location_predicate.x.at_most",
               "Unknown X coordinate"
            );
         }

         Doubles y = position.get().y();
         if (!y.equals(Doubles.ANY)) {
            return NumberProcessors.processNumberRange(
               y,
               "emi_loot.location_predicate.y.exact",
               "emi_loot.location_predicate.y",
               "emi_loot.location_predicate.y.at_least",
               "emi_loot.location_predicate.y.at_most",
               "Unknown Y coordinate"
            );
         }

         Doubles z = position.get().z();
         if (!z.equals(Doubles.ANY)) {
            return NumberProcessors.processNumberRange(
               z,
               "emi_loot.location_predicate.z.exact",
               "emi_loot.location_predicate.z",
               "emi_loot.location_predicate.z.at_least",
               "emi_loot.location_predicate.z.at_most",
               "Unknown Z coordinate"
            );
         }
      }

      Optional<ResourceKey<Level>> dim = predicate.dimension();
      if (dim.isPresent()) {
         return LText.translatable("emi_loot.location_predicate.dim", dim.get().location().toString());
      }

      Optional<HolderSet<Biome>> biome = predicate.biomes();
      if (biome.isPresent() && biome.get().unwrapKey().isPresent()) {
         return LText.translatable("emi_loot.location_predicate.biome.tag", ((TagKey)biome.get().unwrapKey().get()).location().toString());
      }

      if (biome.isPresent() && biome.get().size() > 0) {
         List<MutableComponent> list = biome.get()
            .stream()
            .map(b -> LText.translatable(b.unwrapKey().map(key -> Util.makeDescriptionId("biome", key.location())).orElse(b.getRegisteredName())))
            .toList();
         return LText.translatable("emi_loot.location_predicate.biome.list", ListProcessors.buildOrList(list));
      }

      Optional<HolderSet<Structure>> structure = predicate.structures();
      if (structure.isPresent() && structure.get().unwrapKey().isPresent()) {
         return LText.translatable("emi_loot.location_predicate.structure.tag", ((TagKey)structure.get().unwrapKey().get()).location().toString());
      }

      if (structure.isPresent() && structure.get().size() > 0) {
         List<MutableComponent> list = structure.get().stream().map(b -> {
            String id = b.unwrapKey().map(key -> Util.makeDescriptionId("structure", key.location())).orElse(b.getRegisteredName());
            return I18n.exists(id) ? LText.translatable(id) : LText.literal(b.getRegisteredName());
         }).toList();
         return LText.translatable("emi_loot.location_predicate.structure.list", ListProcessors.buildOrList(list));
      }

      Optional<Boolean> smokey = predicate.smokey();
      if (smokey.isPresent()) {
         return smokey.get() ? LText.translatable("emi_loot.location_predicate.smoke_true") : LText.translatable("emi_loot.location_predicate.smoke_false");
      }

      Optional<LightPredicate> light = predicate.light();
      if (light.isPresent()) {
         return LightPredicateParser.parseLightPredicate(light.get());
      }

      Optional<BlockPredicate> block = predicate.block();
      if (block.isPresent()) {
         return BlockPredicateParser.parseBlockPredicate(block.get());
      }

      Optional<FluidPredicate> fluid = predicate.fluid();
      if (fluid.isPresent()) {
         return FluidPredicateParser.parseFluidPredicate(fluid.get());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable location predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
