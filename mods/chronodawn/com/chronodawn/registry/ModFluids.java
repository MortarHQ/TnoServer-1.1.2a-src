package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.fluids.FluidFactory;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
   public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create("chronodawn", Registries.FLUID);
   public static final RegistrySupplier<FlowingFluid> DECORATIVE_WATER = FLUIDS.register("decorative_water", FluidFactory::createDecorativeWaterSource);
   public static final RegistrySupplier<FlowingFluid> DECORATIVE_WATER_FLOWING = FLUIDS.register(
      "decorative_water_flowing", FluidFactory::createDecorativeWaterFlowing
   );

   public static void register() {
      FLUIDS.register();
      ChronoDawn.LOGGER.debug("Registered ModFluids");
   }
}
