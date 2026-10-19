package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.AccessLootContextParamSets;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/param/LootContextParamSets")
@NativeTypeRegistration(value = LootContextParamSets.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParamSets")
public final class ExpandLootContextParamSets {
   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet empty() {
      return LootContextParamSets.EMPTY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet chest() {
      return LootContextParamSets.CHEST;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet command() {
      return LootContextParamSets.COMMAND;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet selector() {
      return LootContextParamSets.SELECTOR;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet fishing() {
      return LootContextParamSets.FISHING;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet entity() {
      return LootContextParamSets.ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet gift() {
      return LootContextParamSets.GIFT;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet piglinBarter() {
      return LootContextParamSets.PIGLIN_BARTER;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet advancementReward() {
      return LootContextParamSets.ADVANCEMENT_REWARD;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet advancementEntity() {
      return LootContextParamSets.ADVANCEMENT_ENTITY;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet advancementLocation() {
      return LootContextParamSets.ADVANCEMENT_LOCATION;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet allParams() {
      return LootContextParamSets.ALL_PARAMS;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet block() {
      return LootContextParamSets.BLOCK;
   }

   @ZenCodeType.StaticExpansionMethod
   public static LootContextParamSet get(ResourceLocation name) {
      return (LootContextParamSet)Optional.ofNullable((LootContextParamSet)AccessLootContextParamSets.crafttweaker$getREGISTRY().get(name))
         .orElseThrow(() -> new IllegalArgumentException("No loot context param set registered under the name: " + name));
   }
}
