package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.data.BoundCargo;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

public class ExtrasDataComponents {
   public static final DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "tno_tensura_extras");
   public static final Supplier<DataComponentType<BoundCargo>> BOUND_CARGO = DATA_COMPONENTS.registerComponentType(
      "bound_cargo", builder -> builder.persistent(BoundCargo.CODEC).networkSynchronized(BoundCargo.STREAM_CODEC)
   );
}
