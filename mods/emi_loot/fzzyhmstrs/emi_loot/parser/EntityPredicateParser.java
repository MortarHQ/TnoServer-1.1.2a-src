package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.Optional;
import net.minecraft.advancements.critereon.DistancePredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.LocationWrapper;
import net.minecraft.network.chat.Component;

public class EntityPredicateParser {
   public static Component parseEntityPredicate(EntityPredicate predicate) {
      return LText.translatable("emi_loot.entity_predicate.base", parseEntityPredicateInternal(predicate));
   }

   private static Component parseEntityPredicateInternal(EntityPredicate predicate) {
      Optional<EntityTypePredicate> typePredicate = predicate.entityType();
      if (typePredicate.isPresent()) {
         return EntityTypePredicateParser.parseEntityTypePredicate(typePredicate.get());
      }

      Optional<DistancePredicate> distancePredicate = predicate.distanceToPlayer();
      if (distancePredicate.isPresent()) {
         return DistancePredicateParser.parseDistancePredicate(distancePredicate.get());
      }

      LocationWrapper positionPredicate = predicate.location();
      if (positionPredicate.located().isPresent()) {
         return LocationPredicateParser.parseLocationPredicate((LocationPredicate)positionPredicate.located().get());
      }

      if (positionPredicate.steppingOn().isPresent()) {
         return LocationPredicateParser.parseLocationPredicate((LocationPredicate)positionPredicate.steppingOn().get());
      }

      if (positionPredicate.affectsMovement().isPresent()) {
         return LocationPredicateParser.parseLocationPredicate((LocationPredicate)positionPredicate.affectsMovement().get());
      }

      Optional<MobEffectsPredicate> entityEffectPredicate = predicate.effects();
      if (entityEffectPredicate.isPresent()) {
         return EntityEffectPredicateParser.parseEntityEffectPredicate(entityEffectPredicate.get());
      }

      Optional<NbtPredicate> nbt = predicate.nbt();
      if (nbt.isPresent()) {
         return NbtPredicateParser.parseNbtPredicate(nbt.get());
      }

      Optional<EntityFlagsPredicate> entityFlagsPredicate = predicate.flags();
      if (entityFlagsPredicate.isPresent()) {
         return EntityFlagsPredicateParser.parseEntityFlagsPredicate(entityFlagsPredicate.get());
      }

      Optional<EntityEquipmentPredicate> entityEquipmentPredicate = predicate.equipment();
      if (entityEquipmentPredicate.isPresent()) {
         return EntityEquipmentPredicateParser.parseEntityEquipmentPredicate(entityEquipmentPredicate.get());
      }

      Optional<EntitySubPredicate> typeSpecificPredicate = predicate.subPredicate();
      if (typeSpecificPredicate.isPresent()) {
         return TypeSpecificPredicateParser.parseTypeSpecificPredicate(typeSpecificPredicate.get());
      }

      Optional<EntityPredicate> vehicle = predicate.vehicle();
      if (vehicle.isPresent()) {
         return parseEntityPredicate(vehicle.get());
      }

      Optional<EntityPredicate> passenger = predicate.passenger();
      if (passenger.isPresent()) {
         return parseEntityPredicate(passenger.get());
      }

      Optional<EntityPredicate> targetedEntity = predicate.targetedEntity();
      if (targetedEntity.isPresent()) {
         return parseEntityPredicate(targetedEntity.get());
      }

      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.warn("Entity predicate undefined in table: " + LootTableParser.currentTable);
      }

      return LText.translatable("emi_loot.predicate.invalid");
   }
}
