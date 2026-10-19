package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.component.ComponentAccess;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.data.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.natives.component.ExpandDataComponentType;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.component.DataComponentPatch.SplitResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.IFluidStack")
@Document("vanilla/api/fluid/IFluidStack")
public interface IFluidStack extends CommandStringDisplayable, DataComponentHolder, ComponentAccess<IFluidStack> {
   @ZenCodeType.Method
   static IFluidStack empty() {
      return FluidStackConstants.EMPTY.get();
   }

   static <T> IFluidStack of(T convertable) {
      return Services.PLATFORM.createFluidStack(convertable);
   }

   static IFluidStack of(Fluid fluid, long amount) {
      return Services.PLATFORM.createFluidStack(fluid, amount, DataComponentPatch.EMPTY);
   }

   static IFluidStack of(Fluid fluid, long amount, DataComponentPatch components) {
      return Services.PLATFORM.createFluidStack(fluid, amount, components);
   }

   static <T> IFluidStack of(T convertable, boolean mutable) {
      return mutable ? ofMutable(convertable) : of(convertable);
   }

   static IFluidStack of(Fluid fluid, long amount, boolean mutable) {
      return mutable ? ofMutable(fluid, amount, DataComponentPatch.EMPTY) : of(fluid, amount, DataComponentPatch.EMPTY);
   }

   static IFluidStack of(Fluid fluid, long amount, DataComponentPatch components, boolean mutable) {
      return mutable ? ofMutable(fluid, amount, components) : of(fluid, amount, components);
   }

   static <T> IFluidStack ofMutable(T convertable) {
      return Services.PLATFORM.createFluidStackMutable(convertable);
   }

   static IFluidStack ofMutable(Fluid fluid, long amount) {
      return Services.PLATFORM.createFluidStackMutable(fluid, amount, DataComponentPatch.EMPTY);
   }

   static IFluidStack ofMutable(Fluid fluid, long amount, DataComponentPatch components) {
      return Services.PLATFORM.createFluidStackMutable(fluid, amount, components);
   }

   @ZenCodeType.Getter("registryName")
   default ResourceLocation getRegistryName() {
      return BuiltInRegistries.FLUID.getKey(this.getFluid());
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean matches(IFluidStack other) {
      return this.getAmount() >= other.getAmount() && this.isFluidEqual(other);
   }

   default boolean isFluidEqual(IFluidStack other) {
      return this.getFluid() == other.getFluid() && FluidStackConstants.TAG_EQUALS.test(this, other);
   }

   @ZenCodeType.Getter("empty")
   boolean isEmpty();

   @ZenCodeType.Getter("amount")
   long getAmount();

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
   IFluidStack setAmount(int var1);

   @ZenCodeType.Method
   default IFluidStack asMutable() {
      return !this.isImmutable() ? this : of(this.getInternal());
   }

   @ZenCodeType.Method
   default IFluidStack asImmutable() {
      return this.isImmutable() ? this : of(this.getImmutableInternal());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("isImmutable")
   boolean isImmutable();

   @ZenCodeType.Method
   default IFluidStack copy() {
      return this.isImmutable() ? of(this.getImmutableInternal()) : ofMutable(this.getImmutableInternal());
   }

   @ZenCodeType.Getter("fluid")
   @ZenCodeType.Caster(implicit = true)
   Fluid getFluid();

   @ZenCodeType.Caster(implicit = true)
   default CTFluidIngredient asFluidIngredient() {
      return new CTFluidIngredient.FluidStackIngredient(this);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   default CTFluidIngredient asList(CTFluidIngredient other) {
      List<CTFluidIngredient> elements = new ArrayList<>();
      elements.add(this.asFluidIngredient());
      elements.add(other);
      return new CTFluidIngredient.CompoundFluidIngredient(elements);
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   default IData asIData() {
      return (IData)this.codec()
         .encodeStart(IDataOps.INSTANCE.withRegistryAccess(), this)
         .getOrThrow(s -> new IllegalStateException("Error while encoding IFluidStack as IData: " + s));
   }

   Codec<IFluidStack> codec();

   @Override
   default String getCommandString() {
      Fluid fluid = this.getFluid();
      StringBuilder builder = new StringBuilder().append("<fluid:").append(BuiltInRegistries.FLUID.getKey(fluid)).append(">");
      SplitResult split = this.getComponents().asPatch().split();
      split.added()
         .filter(Predicate.not(DataComponentType::isTransient))
         .forEach(
            typedDataComponent -> builder.append(".withJsonComponent(")
               .append(ExpandDataComponentType.getCommandString(typedDataComponent.type()))
               .append(", ")
               .append(((IData)typedDataComponent.encodeValue(IDataOps.INSTANCE.withRegistryAccess()).getOrThrow()).accept(DataToStringVisitor.ESCAPE))
               .append(")")
         );
      split.removed().forEach(dataComponentType -> builder.append(".without(").append(ExpandDataComponentType.getCommandString(dataComponentType)).append(")"));
      if (!this.isEmpty() && this.getAmount() != 1L) {
         builder.append(" * ").append(this.getAmount());
      }

      return builder.toString();
   }

   PatchedDataComponentMap getComponents();

   @ZenCodeType.Method
   <T> IFluidStack with(DataComponentType<T> var1, @ZenCodeType.Nullable T var2);

   @ZenCodeType.Method
   default <T> IFluidStack without(DataComponentType<T> type) {
      return this.remove(type);
   }

   @ZenCodeType.Method
   IFluidStack withJsonComponent(DataComponentType var1, @ZenCodeType.Nullable IData var2);

   @ZenCodeType.Method
   IFluidStack withJsonComponents(IData var1);

   @ZenCodeType.Method
   <T> IFluidStack remove(DataComponentType<T> var1);

   @ZenCodeType.Method
   <T, U> IFluidStack update(DataComponentType<T> var1, T var2, U var3, BiFunction<T, U, T> var4);

   @ZenCodeType.Method
   <T> IFluidStack update(DataComponentType<T> var1, T var2, UnaryOperator<T> var3);

   @ZenCodeType.Method
   IFluidStack applyComponents(DataComponentMap var1);

   @ZenCodeType.Method
   IFluidStack applyComponents(DataComponentPatch var1);

   <T> T getInternal();

   <T> T getImmutableInternal();

   default <U> IFluidStack _without(DataComponentType<U> componentType) {
      return this.without(componentType);
   }

   default <U> IFluidStack _with(DataComponentType<U> componentType, @Nullable U value) {
      return this.with(componentType, value);
   }

   @Override
   default <U> U _get(DataComponentType<? extends U> componentType) {
      return (U)this.get(componentType);
   }

   @Override
   default <U> boolean _has(DataComponentType<U> componentType) {
      return this.has(componentType);
   }
}
