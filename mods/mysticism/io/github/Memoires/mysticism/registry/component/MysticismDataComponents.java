package io.github.Memoires.mysticism.registry.component;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public final class MysticismDataComponents {
   public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create("mysticism", Registries.DATA_COMPONENT_TYPE);
   public static final RegistrySupplier<DataComponentType<ResourceLocation>> ENHANCED_ENCHANTMENT = DATA_COMPONENTS.register(
      "enhanced_enchantment", () -> DataComponentType.builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build()
   );

   private MysticismDataComponents() {
   }

   public static void init() {
      DATA_COMPONENTS.register();
   }
}
