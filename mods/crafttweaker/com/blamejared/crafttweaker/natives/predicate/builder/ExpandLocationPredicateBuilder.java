package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.LocationPredicate.Builder;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/LocationPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.LocationPredicateBuilder")
public final class ExpandLocationPredicateBuilder {
   @ZenCodeType.Method
   public static Builder x(Builder internal, Doubles x) {
      return internal.setX(x);
   }

   @ZenCodeType.Method
   public static Builder y(Builder internal, Doubles y) {
      return internal.setY(y);
   }

   @ZenCodeType.Method
   public static Builder z(Builder internal, Doubles z) {
      return internal.setZ(z);
   }

   @ZenCodeType.Method
   public static Builder biome(Builder internal, ResourceLocation biome) {
      Reference<Biome> holder = CraftTweakerAPI.getAccessibleElementsProvider()
         .registryAccess()
         .registryOrThrow(Registries.BIOME)
         .getHolderOrThrow(ResourceKey.create(Registries.BIOME, biome));
      return internal.setBiomes(HolderSet.direct(new Holder[]{holder}));
   }

   @ZenCodeType.Method
   public static Builder biome(Builder internal, String biome) {
      return biome(internal, ResourceLocation.parse(biome));
   }

   @ZenCodeType.Method
   public static Builder structure(Builder internal, ResourceLocation structure) {
      Reference<Structure> holder = CraftTweakerAPI.getAccessibleElementsProvider()
         .registryAccess()
         .registryOrThrow(Registries.STRUCTURE)
         .getHolderOrThrow(ResourceKey.create(Registries.STRUCTURE, structure));
      return internal.setStructures(HolderSet.direct(new Holder[]{holder}));
   }

   @ZenCodeType.Method
   public static Builder structure(Builder internal, String structure) {
      return structure(internal, ResourceLocation.parse(structure));
   }

   @ZenCodeType.Method
   public static Builder dimension(Builder internal, ResourceLocation dimension) {
      return internal.setDimension(ResourceKey.create(Registries.DIMENSION, dimension));
   }

   @ZenCodeType.Method
   public static Builder dimension(Builder internal, String dimension) {
      return dimension(internal, ResourceLocation.parse(dimension));
   }

   @ZenCodeType.Method
   public static Builder light(Builder internal, net.minecraft.advancements.critereon.LightPredicate.Builder predicate) {
      return internal.setLight(predicate);
   }

   @ZenCodeType.Method
   public static Builder block(Builder internal, net.minecraft.advancements.critereon.BlockPredicate.Builder predicate) {
      return internal.setBlock(predicate);
   }

   @ZenCodeType.Method
   public static Builder fluid(Builder internal, net.minecraft.advancements.critereon.FluidPredicate.Builder predicate) {
      return internal.setFluid(predicate);
   }

   @ZenCodeType.Method
   public static Builder smokey(Builder internal, @ZenCodeType.OptionalBoolean(true) Boolean smokey) {
      return internal.setSmokey(smokey);
   }

   @ZenCodeType.Method
   public static LocationPredicate build(Builder internal) {
      return internal.build();
   }
}
