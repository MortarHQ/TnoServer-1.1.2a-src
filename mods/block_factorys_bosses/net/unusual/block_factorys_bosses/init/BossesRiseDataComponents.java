package net.unusual.block_factorys_bosses.init;

import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;
import net.unusual.block_factorys_bosses.item.component.OperationMode;

public class BossesRiseDataComponents {
   public static final DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "block_factorys_bosses");
   public static final Supplier<DataComponentType<OperationMode>> OPERATION_MODE = REGISTRAR.registerComponentType(
      "operation_mode", builder -> builder.networkSynchronized(OperationMode.STREAM_CODEC).cacheEncoding()
   );
}
