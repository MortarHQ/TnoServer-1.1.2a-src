package com.blamejared.crafttweaker.api.fluid;

import com.google.common.base.Suppliers;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import net.minecraft.world.level.material.Fluids;

class FluidStackConstants {
   static final Supplier<IFluidStack> EMPTY = Suppliers.memoize(() -> IFluidStack.of(Fluids.EMPTY, 0L));
   static final BiPredicate<IFluidStack, IFluidStack> TAG_EQUALS = (first, second) -> true;
}
