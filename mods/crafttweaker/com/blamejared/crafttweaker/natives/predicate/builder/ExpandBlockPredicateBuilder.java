package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.BlockPredicate.Builder;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/BlockPredicateBuilder")
@NativeTypeRegistration(value = Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.BlockPredicateBuilder")
public class ExpandBlockPredicateBuilder {
   @ZenCodeType.Method
   public static Builder blocks(Builder internal, Block... blocks) {
      return internal.of(blocks);
   }

   @ZenCodeType.Method
   public static Builder tag(Builder internal, KnownTag<Block> tag) {
      return internal.of(tag.getTagKey());
   }

   @ZenCodeType.Method
   public static Builder nbt(Builder internal, MapData tag) {
      return internal.hasNbt(tag.getInternal());
   }

   @ZenCodeType.Method
   public static Builder nbt(Builder internal, IData tag) {
      return nbt(internal, new MapData(tag.asMap()));
   }

   @ZenCodeType.Method
   public static Builder properties(Builder internal, net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder predicate) {
      return internal.setProperties(predicate);
   }

   @ZenCodeType.Method
   public static BlockPredicate build(Builder internal) {
      return internal.build();
   }
}
