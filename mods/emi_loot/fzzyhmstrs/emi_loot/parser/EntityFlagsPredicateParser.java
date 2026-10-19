package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.network.chat.Component;

public class EntityFlagsPredicateParser {
   public static Component parseEntityFlagsPredicate(EntityFlagsPredicate predicate) {
      return LText.translatable("emi_loot.entity_predicate.flag", parseEntityFlagsPredicateInternal(predicate));
   }

   private static Component parseEntityFlagsPredicateInternal(EntityFlagsPredicate predicate) {
      Optional<Boolean> isOnFire = predicate.isOnFire();
      if (isOnFire.isPresent()) {
         return isOnFire.get() ? LText.translatable("emi_loot.entity_predicate.fire_true") : LText.translatable("emi_loot.entity_predicate.fire_false");
      }

      Optional<Boolean> isSneaking = predicate.isCrouching();
      if (isSneaking.isPresent()) {
         return isSneaking.get() ? LText.translatable("emi_loot.entity_predicate.sneak_true") : LText.translatable("emi_loot.entity_predicate.sneak_false");
      }

      Optional<Boolean> isSprinting = predicate.isSprinting();
      if (isSprinting.isPresent()) {
         return isSprinting.get() ? LText.translatable("emi_loot.entity_predicate.sprint_true") : LText.translatable("emi_loot.entity_predicate.sprint_false");
      }

      Optional<Boolean> isSwimming = predicate.isSwimming();
      if (isSwimming.isPresent()) {
         return isSwimming.get() ? LText.translatable("emi_loot.entity_predicate.swim_true") : LText.translatable("emi_loot.entity_predicate.swim_false");
      }

      Optional<Boolean> isBaby = predicate.isBaby();
      if (isBaby.isPresent()) {
         return isBaby.get() ? LText.translatable("emi_loot.entity_predicate.baby_true") : LText.translatable("emi_loot.entity_predicate.baby_false");
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Empty or unparsable entity flags predicate in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
