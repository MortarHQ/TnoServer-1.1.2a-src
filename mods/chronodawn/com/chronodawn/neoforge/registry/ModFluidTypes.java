package com.chronodawn.neoforge.registry;

import com.chronodawn.ChronoDawn;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidType.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
   public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, "chronodawn");
   public static final Supplier<FluidType> DECORATIVE_WATER_TYPE = FLUID_TYPES.register(
      "decorative_water",
      () -> new FluidType(
         Properties.create()
            .descriptionId("block.chronodawn.decorative_water")
            .fallDistanceModifier(0.0F)
            .canExtinguish(true)
            .canConvertToSource(true)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .lightLevel(0)
            .density(1000)
            .temperature(300)
            .viscosity(1000)
            .pathType(PathType.WATER)
            .adjacentPathType(null)
      )
   );

   public static void register(IEventBus modEventBus) {
      FLUID_TYPES.register(modEventBus);
      ChronoDawn.LOGGER.debug("Registered ModFluidTypes for NeoForge");
   }
}
