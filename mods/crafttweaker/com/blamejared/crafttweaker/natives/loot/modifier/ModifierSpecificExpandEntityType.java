package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemEntityPropertyCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemKilledByPlayerCondition;
import com.blamejared.crafttweaker.natives.predicate.ExpandEntityEquipmentPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandEntityPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandItemPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/loot/modifier/EntityLootModifiers")
@ZenCodeType.Expansion("crafttweaker.api.entity.EntityType<crafttweaker.api.entity.Entity>")
@ZenRegister
public class ModifierSpecificExpandEntityType {
   @ZenCodeType.Method
   public static void addLootModifier(EntityType<Entity> internal, String name, ILootModifier modifier) {
      LootManager.INSTANCE.getModifierManager().register(name, LootConditions.only(makeForType(internal)), modifier);
   }

   @ZenCodeType.Method
   public static void addPlayerOnlyLootModifier(EntityType<Entity> internal, String name, ILootModifier modifier) {
      LootManager.INSTANCE
         .getModifierManager()
         .register(name, LootConditions.allOf(makeForType(internal), ExpandLootItemKilledByPlayerCondition.create()), modifier);
   }

   @ZenCodeType.Method
   public static void addWeaponOnlyLootModifier(EntityType<Entity> internal, String name, IItemStack weapon, ILootModifier modifier) {
      addWeaponOnlyLootModifier(internal, name, weapon, false, modifier);
   }

   @ZenCodeType.Method
   public static void addWeaponOnlyLootModifier(EntityType<Entity> internal, String name, IItemStack weapon, boolean matchComponents, ILootModifier modifier) {
      LootManager.INSTANCE.getModifierManager().register(name, LootConditions.allOf(makeForType(internal), makeForWeapon(weapon, matchComponents)), modifier);
   }

   @ZenCodeType.Method
   public static void addWeaponAndPlayerOnlyLootModifier(EntityType<Entity> internal, String name, IItemStack weapon, ILootModifier modifier) {
      addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, false, modifier);
   }

   @ZenCodeType.Method
   public static void addWeaponAndPlayerOnlyLootModifier(
      EntityType<Entity> internal, String name, IItemStack weapon, boolean matchComponents, ILootModifier modifier
   ) {
      LootManager.INSTANCE
         .getModifierManager()
         .register(
            name, LootConditions.allOf(makeForType(internal), makeForWeapon(weapon, matchComponents), ExpandLootItemKilledByPlayerCondition.create()), modifier
         );
   }

   private static Builder makeForType(EntityType<Entity> type) {
      return ExpandLootItemEntityPropertyCondition.create(EntityTarget.THIS, ExpandEntityPredicate.create(type));
   }

   private static Builder makeForWeapon(IItemStack weapon, boolean matchComponents) {
      net.minecraft.advancements.critereon.ItemPredicate.Builder item = ExpandItemPredicate.create(weapon);
      if (matchComponents) {
         item.hasComponents(DataComponentPredicate.allOf(weapon.getComponents()));
      }

      return ExpandLootItemEntityPropertyCondition.create(
         EntityTarget.ATTACKER, ExpandEntityPredicate.create().equipment(ExpandEntityEquipmentPredicate.create().mainhand(item).build())
      );
   }
}
