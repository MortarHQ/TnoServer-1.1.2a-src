package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

public class MCFluidStackMutable implements IFluidStack {
   private final FluidStack stack;

   public MCFluidStackMutable(FluidStack stack) {
      this.stack = stack;
   }

   @Override
   public boolean isEmpty() {
      return this.getInternal().isEmpty();
   }

   @Override
   public long getAmount() {
      return this.getInternal().getAmount();
   }

   @Override
   public IFluidStack setAmount(int amount) {
      this.getInternal().setAmount(amount);
      return this;
   }

   @Override
   public boolean isImmutable() {
      return false;
   }

   @Override
   public Fluid getFluid() {
      return this.getInternal().getFluid();
   }

   @Override
   public Codec<IFluidStack> codec() {
      return FluidStack.CODEC.xmap(IFluidStack::ofMutable, IFluidStack::getInternal);
   }

   @Override
   public PatchedDataComponentMap getComponents() {
      return this.getInternal().getComponents();
   }

   @Override
   public <T> IFluidStack with(DataComponentType<T> type, @ZenCodeType.Nullable T value) {
      this.getInternal().set(type, value);
      return this;
   }

   @Override
   public IFluidStack withJsonComponent(DataComponentType type, @ZenCodeType.Nullable IData value) {
      DataResult<Pair<DataComponentPatch, IData>> decoded = DataComponentPatch.CODEC.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
      Pair<DataComponentPatch, IData> pair = (Pair<DataComponentPatch, IData>)decoded.getOrThrow();
      this.getInternal().applyComponents((DataComponentPatch)pair.getFirst());
      return this;
   }

   @Override
   public IFluidStack withJsonComponents(IData value) {
      DataResult<Pair<DataComponentPatch, IData>> decoded = DataComponentPatch.CODEC.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
      Pair<DataComponentPatch, IData> pair = (Pair<DataComponentPatch, IData>)decoded.getOrThrow();
      this.getInternal().applyComponents((DataComponentPatch)pair.getFirst());
      return this;
   }

   @Override
   public <T> IFluidStack remove(DataComponentType<T> type) {
      this.getInternal().remove(type);
      return this;
   }

   @Override
   public <T, U> IFluidStack update(DataComponentType<T> type, T defaultValue, U data, BiFunction<T, U, T> operator) {
      this.getInternal().update(type, defaultValue, data, operator);
      return this;
   }

   @Override
   public <T> IFluidStack update(DataComponentType<T> type, T defaultValue, UnaryOperator<T> operator) {
      this.getInternal().update(type, defaultValue, operator);
      return this;
   }

   @Override
   public IFluidStack applyComponents(DataComponentMap map) {
      this.getInternal().applyComponents(map);
      return this;
   }

   @Override
   public IFluidStack applyComponents(DataComponentPatch patch) {
      this.getInternal().applyComponents(patch);
      return this;
   }

   public FluidStack getInternal() {
      return this.stack;
   }

   public FluidStack getImmutableInternal() {
      return this.stack.copy();
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FluidStack thatStack = ((MCFluidStackMutable)o).getInternal();
         FluidStack thisStack = this.getInternal();
         return FluidStack.matches(thisStack, thatStack);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.getInternal().getAmount() * 31 + FluidStack.hashFluidAndComponents(this.getInternal());
   }
}
