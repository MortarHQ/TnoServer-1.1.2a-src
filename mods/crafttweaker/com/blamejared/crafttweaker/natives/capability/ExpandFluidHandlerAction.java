package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/IFluidHandlerAction")
@NativeTypeRegistration(value = FluidAction.class, zenCodeName = "crafttweaker.api.capability.IFluidHandlerAction")
@BracketEnum("neoforge:fluid_action")
public class ExpandFluidHandlerAction {
   @ZenCodeType.Getter("execute")
   public static boolean execute(FluidAction internal) {
      return internal.execute();
   }

   @ZenCodeType.Getter("simulate")
   public static boolean simulate(FluidAction internal) {
      return internal.simulate();
   }
}
