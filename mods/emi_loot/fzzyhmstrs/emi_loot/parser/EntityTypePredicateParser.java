package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTypePredicateParser {
   public static Component parseEntityTypePredicate(EntityTypePredicate predicate) {
      HolderSet<EntityType<?>> registryEntryList = predicate.types();
      Optional<TagKey<EntityType<?>>> tag = registryEntryList.unwrapKey();
      if (tag.isPresent()) {
         return LText.translatable("emi_loot.entity_predicate.type_tag", tag.get().location().toString());
      }

      if (registryEntryList.size() == 1) {
         return LText.translatable("emi_loot.entity_predicate.type_single", ((EntityType)registryEntryList.get(0).value()).getDescription());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable entity type predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
