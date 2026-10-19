package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.FluidIngredient")
@Document("vanilla/api/fluid/FluidIngredient")
public abstract class CTFluidIngredient implements CommandStringDisplayable {
   public static final Supplier<CTFluidIngredient> EMPTY = () -> new CTFluidIngredient.FluidStackIngredient(IFluidStack.empty());

   CTFluidIngredient() {
   }

   @Override
   public abstract String getCommandString();

   public abstract <T> T mapTo(Function<IFluidStack, T> var1, BiFunction<TagKey<Fluid>, Integer, T> var2, Function<Stream<T>, T> var3);

   @ZenCodeType.Method
   public abstract boolean matches(Fluid var1);

   @ZenCodeType.Method
   public abstract boolean matches(IFluidStack var1);

   public abstract boolean matches(TagKey<Fluid> var1);

   public abstract boolean matches(TagKey<Fluid> var1, int var2);

   protected abstract void dissolve();

   public abstract List<IFluidStack> getMatchingStacks();

   public boolean contains(CTFluidIngredient other) {
      return other.getMatchingStacks().stream().allMatch(this::matches);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   public CTFluidIngredient asCompound(CTFluidIngredient other) {
      List<CTFluidIngredient> ingredients = new ArrayList<>();
      if (other instanceof CTFluidIngredient.CompoundFluidIngredient) {
         ingredients.addAll(((CTFluidIngredient.CompoundFluidIngredient)other).elements);
      } else {
         ingredients.add(other);
      }

      if (this instanceof CTFluidIngredient.CompoundFluidIngredient) {
         ((CTFluidIngredient.CompoundFluidIngredient)this).elements.addAll(ingredients);
         return this;
      } else {
         ingredients.add(this);
         return new CTFluidIngredient.CompoundFluidIngredient(ingredients);
      }
   }

   public static final class CompoundFluidIngredient extends CTFluidIngredient {
      final List<CTFluidIngredient> elements;
      List<IFluidStack> matchingStacks;
      final List<CTFluidIngredient> elementsView;

      public CompoundFluidIngredient(List<CTFluidIngredient> elements) {
         this.elements = elements;
         this.elementsView = Collections.unmodifiableList(elements);
      }

      @Override
      public String getCommandString() {
         return this.elements.stream().map(CTFluidIngredient::getCommandString).collect(Collectors.joining(" | "));
      }

      @Override
      public <T> T mapTo(Function<IFluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         Stream<T> stream = this.elements.stream().map(element -> element.mapTo(fluidMapper, tagMapper, compoundMapper));
         return compoundMapper.apply(stream);
      }

      @Override
      public boolean matches(Fluid fluid) {
         return this.elements.stream().anyMatch(ingr -> ingr.matches(fluid));
      }

      @Override
      public boolean matches(IFluidStack fluidStack) {
         return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidStack));
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag) {
         return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidTag));
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag, int amount) {
         return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidTag, amount));
      }

      @Override
      protected void dissolve() {
         if (this.matchingStacks == null) {
            this.matchingStacks = this.elements.stream().map(CTFluidIngredient::getMatchingStacks).flatMap(Collection::stream).toList();
         }
      }

      @Override
      public List<IFluidStack> getMatchingStacks() {
         this.dissolve();
         return this.matchingStacks;
      }

      public List<CTFluidIngredient> getElements() {
         return this.elementsView;
      }
   }

   public static final class FluidStackIngredient extends CTFluidIngredient {
      final IFluidStack fluidStack;
      List<IFluidStack> fluidStacks;

      public FluidStackIngredient(IFluidStack fluidStack) {
         this.fluidStack = fluidStack;
      }

      @Override
      public boolean matches(Fluid fluid) {
         return this.fluidStack.getFluid() == fluid;
      }

      @Override
      public boolean matches(IFluidStack fluidStack) {
         return this.fluidStack.isFluidEqual(fluidStack) && this.fluidStack.getAmount() <= fluidStack.getAmount();
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag) {
         return this.fluidStack.getFluid().is(fluidTag);
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag, int amount) {
         return this.matches(fluidTag) && this.fluidStack.getAmount() <= amount;
      }

      @Override
      public String getCommandString() {
         return this.fluidStack.getCommandString();
      }

      @Override
      public <T> T mapTo(Function<IFluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         return fluidMapper.apply(this.fluidStack);
      }

      @Override
      protected void dissolve() {
         if (this.fluidStacks == null) {
            this.fluidStacks = List.of(this.fluidStack);
         }
      }

      @Override
      public List<IFluidStack> getMatchingStacks() {
         this.dissolve();
         return this.fluidStacks;
      }
   }

   public static final class FluidTagWithAmountIngredient extends CTFluidIngredient {
      final Many<KnownTag<Fluid>> tag;
      List<IFluidStack> matchingStacks;

      public FluidTagWithAmountIngredient(Many<KnownTag<Fluid>> tag) {
         this.tag = tag;
      }

      @Override
      public String getCommandString() {
         return this.tag.getCommandString();
      }

      @Override
      public <T> T mapTo(Function<IFluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
         return tagMapper.apply(this.tag.getData().getTagKey(), this.tag.getAmount());
      }

      @Override
      public boolean matches(Fluid fluid) {
         return this.tag.getData().contains(fluid);
      }

      @Override
      public boolean matches(IFluidStack fluidStack) {
         return this.tag.getData().contains(fluidStack.getFluid()) && this.tag.getAmount() <= fluidStack.getAmount();
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag) {
         return this.tag.getData().getTagKey().equals(fluidTag);
      }

      @Override
      public boolean matches(TagKey<Fluid> fluidTag, int amount) {
         return this.matches(fluidTag) && this.tag.getAmount() <= amount;
      }

      @Override
      protected void dissolve() {
         if (this.matchingStacks == null) {
            this.matchingStacks = this.tag.getData().elements().stream().map(fluid -> IFluidStack.of(fluid, this.tag.getAmount())).toList();
         }
      }

      @Override
      public List<IFluidStack> getMatchingStacks() {
         this.dissolve();
         return this.matchingStacks;
      }
   }
}
