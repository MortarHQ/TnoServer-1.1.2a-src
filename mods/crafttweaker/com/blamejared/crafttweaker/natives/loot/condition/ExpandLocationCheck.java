package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/LocationCheckLootCondition")
@NativeTypeRegistration(value = LocationCheck.class, zenCodeName = "crafttweaker.api.loot.condition.LocationCheckLootCondition")
public final class ExpandLocationCheck {
   @ZenCodeType.StaticExpansionMethod
   public static Builder create(net.minecraft.advancements.critereon.LocationPredicate.Builder predicate) {
      return LocationCheck.checkLocation(predicate);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder create(net.minecraft.advancements.critereon.LocationPredicate.Builder predicate, BlockPos offset) {
      return LocationCheck.checkLocation(predicate, offset);
   }

   @ZenCodeType.StaticExpansionMethod
   public static Builder create(net.minecraft.advancements.critereon.LocationPredicate.Builder predicate, int xOffset, int yOffset, int zOffset) {
      return create(predicate, new BlockPos(xOffset, yOffset, zOffset));
   }
}
