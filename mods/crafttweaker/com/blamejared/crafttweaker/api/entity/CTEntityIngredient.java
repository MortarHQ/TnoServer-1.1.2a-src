package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.EntityIngredient")
@Document("vanilla/api/entity/EntityIngredient")
public abstract class CTEntityIngredient implements CommandStringDisplayable {
   CTEntityIngredient() {
   }

   @Override
   public abstract String getCommandString();

   public abstract boolean matches(EntityType<?> var1);

   public abstract boolean matches(EntityType<?> var1, int var2);

   public abstract boolean matches(TagKey<EntityType<?>> var1);

   public abstract boolean matches(TagKey<EntityType<?>> var1, int var2);

   public abstract <T> T mapTo(Function<EntityType<?>, T> var1, BiFunction<TagKey<EntityType<?>>, Integer, T> var2, Function<Stream<T>, T> var3);

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   public CTEntityIngredient asCompound(CTEntityIngredient other) {
      List<CTEntityIngredient> ingredients = new ArrayList<>();
      if (other instanceof CTEntityIngredient.CompoundEntityIngredient) {
         ingredients.addAll(((CTEntityIngredient.CompoundEntityIngredient)other).elements);
      } else {
         ingredients.add(other);
      }

      if (this instanceof CTEntityIngredient.CompoundEntityIngredient) {
         ((CTEntityIngredient.CompoundEntityIngredient)this).elements.addAll(ingredients);
         return this;
      } else {
         ingredients.add(this);
         return new CTEntityIngredient.CompoundEntityIngredient(ingredients);
      }
   }

   public static final class CompoundEntityIngredient extends CTEntityIngredient {
      final List<CTEntityIngredient> elements;

      public CompoundEntityIngredient(List<CTEntityIngredient> elements) {
         this.elements = elements;
      }

      @Override
      public String getCommandString() {
         return this.elements.stream().map(CTEntityIngredient::getCommandString).collect(Collectors.joining(" | "));
      }

      @Override
      public boolean matches(EntityType<?> type) {
         return this.elements.stream().anyMatch(ctEntityIngredient -> ctEntityIngredient.matches(type));
      }

      @Override
      public boolean matches(EntityType<?> type, int amount) {
         return this.elements.stream().anyMatch(ctEntityIngredient -> ctEntityIngredient.matches(type, amount));
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag) {
         return this.elements.stream().anyMatch(ctEntityIngredient -> ctEntityIngredient.matches(tag));
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag, int amount) {
         return this.elements.stream().anyMatch(ctEntityIngredient -> ctEntityIngredient.matches(tag, amount));
      }

      @Override
      public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         Stream<T> stream = this.elements.stream().map(element -> element.mapTo(typeMapper, tagMapper, compoundMapper));
         return compoundMapper.apply(stream);
      }
   }

   public static final class EntityTagWithAmountIngredient extends CTEntityIngredient {
      final Many<KnownTag<EntityType<?>>> tag;

      public EntityTagWithAmountIngredient(Many<KnownTag<EntityType<?>>> tag) {
         this.tag = tag;
      }

      @Override
      public boolean matches(EntityType<?> type) {
         return this.tag.getData().contains(type);
      }

      @Override
      public boolean matches(EntityType<?> type, int amount) {
         return this.tag.getData().contains(type) && this.tag.getAmount() <= amount;
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag) {
         return this.tag.getData().getTagKey().equals(tag);
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag, int amount) {
         return this.tag.getData().getTagKey().equals(tag) && this.tag.getAmount() <= amount;
      }

      @Override
      public String getCommandString() {
         return this.tag.getCommandString();
      }

      @Override
      public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         return tagMapper.apply(this.tag.getData().getTagKey(), this.tag.getAmount());
      }
   }

   public static final class EntityTypeIngredient extends CTEntityIngredient {
      final EntityType<?> entityType;

      public EntityTypeIngredient(EntityType<?> entityType) {
         this.entityType = entityType;
      }

      @Override
      public String getCommandString() {
         return ExpandEntityType.getCommandString(GenericUtil.uncheck(this.entityType));
      }

      @Override
      public boolean matches(EntityType<?> type) {
         return this.entityType == type;
      }

      @Override
      public boolean matches(EntityType<?> type, int amount) {
         return this.matches(type);
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag) {
         return this.entityType.is(tag);
      }

      @Override
      public boolean matches(TagKey<EntityType<?>> tag, int amount) {
         return this.matches(tag);
      }

      @Override
      public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         return typeMapper.apply(this.entityType);
      }
   }
}
