package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/IFluidHandler")
@NativeTypeRegistration(value = IFluidHandler.class, zenCodeName = "crafttweaker.api.capability.IFluidHandler")
public class ExpandIFluidHandler {
   @ZenCodeType.Getter("tanks")
   public static int getTanks(IFluidHandler internal) {
      return internal.getTanks();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
   public static IFluidStack getFluidInTank(IFluidHandler internal, int tank) {
      return IFluidStack.of(internal.getFluidInTank(tank));
   }

   @ZenCodeType.Method
   public static int getTankCapacity(IFluidHandler internal, int tank) {
      return internal.getTankCapacity(tank);
   }

   @ZenCodeType.Method
   public static boolean isFluidValid(IFluidHandler internal, int tank, IFluidStack stack) {
      return internal.isFluidValid(tank, stack.getInternal());
   }

   @ZenCodeType.Method
   public static int fill(IFluidHandler internal, IFluidStack resource, FluidAction action) {
      return internal.fill(resource.getInternal(), action);
   }

   @ZenCodeType.Method
   public static IFluidStack drain(IFluidHandler internal, IFluidStack resource, FluidAction action) {
      return IFluidStack.of(internal.drain(resource.getInternal(), action));
   }

   @ZenCodeType.Method
   public static IFluidStack drain(IFluidHandler internal, int maxDrain, FluidAction action) {
      return IFluidStack.of(internal.drain(maxDrain, action));
   }
}
