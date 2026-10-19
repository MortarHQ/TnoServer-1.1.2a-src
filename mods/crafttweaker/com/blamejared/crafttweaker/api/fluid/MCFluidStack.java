package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
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

public class MCFluidStack implements IFluidStack {
   private final FluidStack stack;

   public MCFluidStack(FluidStack fluidStack) {
      this.stack = fluidStack;
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
      FluidStack copy = this.getInternal().copy();
      copy.setAmount(amount);
      return IFluidStack.of(copy);
   }

   @Override
   public boolean isImmutable() {
      return true;
   }

   @Override
   public Fluid getFluid() {
      return this.getInternal().getFluid();
   }

   public FluidStack getInternal() {
      return this.stack;
   }

   public FluidStack getImmutableInternal() {
      return this.stack;
   }

   @Override
   public IData asIData() {
      IData data = IFluidStack.super.asIData();
      data.put("amount", new IntData(Math.toIntExact(this.getAmount())));
      return data;
   }

   @Override
   public Codec<IFluidStack> codec() {
      return FluidStack.CODEC.xmap(IFluidStack::of, IFluidStack::getInternal);
   }

   @Override
   public PatchedDataComponentMap getComponents() {
      return this.getInternal().getComponents();
   }

   @Override
   public <T> IFluidStack with(DataComponentType<T> type, @ZenCodeType.Nullable T value) {
      FluidStack copy = this.getInternal().copy();
      copy.set(type, value);
      return IFluidStack.of(copy);
   }

   @Override
   public IFluidStack withJsonComponent(DataComponentType type, @ZenCodeType.Nullable IData value) {
      FluidStack copy = this.getInternal().copy();
      if (value == null) {
         copy.remove(type);
      } else {
         Codec<?> codec = type.codecOrThrow();
         DataResult<? extends Pair<?, IData>> decode = codec.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
         copy.set(type, ((Pair)decode.getOrThrow()).getFirst());
      }

      return IFluidStack.of(copy);
   }

   @Override
   public IFluidStack withJsonComponents(IData value) {
      FluidStack copy = this.getInternal().copy();
      DataResult<Pair<DataComponentPatch, IData>> decoded = DataComponentPatch.CODEC.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
      Pair<DataComponentPatch, IData> pair = (Pair<DataComponentPatch, IData>)decoded.getOrThrow();
      copy.applyComponents((DataComponentPatch)pair.getFirst());
      return IFluidStack.of(copy);
   }

   @Override
   public <T> IFluidStack remove(DataComponentType<T> type) {
      FluidStack copy = this.getInternal().copy();
      copy.remove(type);
      return IFluidStack.of(copy);
   }

   @Override
   public <T, U> IFluidStack update(DataComponentType<T> type, T defaultValue, U data, BiFunction<T, U, T> operator) {
      FluidStack copy = this.getInternal().copy();
      copy.update(type, defaultValue, data, operator);
      return IFluidStack.of(copy);
   }

   @Override
   public <T> IFluidStack update(DataComponentType<T> type, T defaultValue, UnaryOperator<T> operator) {
      FluidStack copy = this.getInternal().copy();
      copy.update(type, defaultValue, operator);
      return IFluidStack.of(copy);
   }

   @Override
   public IFluidStack applyComponents(DataComponentMap map) {
      FluidStack copy = this.getInternal().copy();
      copy.applyComponents(map);
      return IFluidStack.of(copy);
   }

   @Override
   public IFluidStack applyComponents(DataComponentPatch patch) {
      FluidStack copy = this.getInternal().copy();
      copy.applyComponents(patch);
      return IFluidStack.of(copy);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FluidStack thatStack = ((MCFluidStack)o).getInternal();
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
