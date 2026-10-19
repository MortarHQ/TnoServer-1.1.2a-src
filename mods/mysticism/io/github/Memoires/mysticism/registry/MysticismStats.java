package io.github.Memoires.mysticism.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatType;

public class MysticismStats {
   private static final DeferredRegister<StatType<?>> STATS = DeferredRegister.create("mysticism", Registries.STAT_TYPE);
   private static final DeferredRegister<ResourceLocation> CUSTOM_STATS = DeferredRegister.create("mysticism", Registries.CUSTOM_STAT);
   public static final ResourceLocation LIGHTNING_STRIKES = makeCustomStat("lightning_strikes");

   private static <T> RegistrySupplier<StatType<T>> registerStatType(String string, Registry<T> registry) {
      Component component = Component.translatable("stat_type.mysticism." + string);
      return STATS.register(string, () -> new StatType(registry, component));
   }

   private static ResourceLocation makeCustomStat(String string) {
      ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("mysticism", string);
      CUSTOM_STATS.register(string, () -> resourceLocation);
      return resourceLocation;
   }

   public static void init() {
      STATS.register();
      CUSTOM_STATS.register();
   }
}
