package com.almostreliable.lootjs;

import com.almostreliable.lootjs.core.filters.ItemFilterWrapper;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lootjs")
public class LootJS {
   public static final Logger LOG = LogManager.getLogger("LootJS");
   public static Consumer<String> DEBUG_ACTION = LOG::info;
   public static Provider LOOKUP_PROVIDER = new Provider() {
      public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
         return Stream.empty();
      }

      public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> arg) {
         return Optional.empty();
      }
   };

   public LootJS(IEventBus bus) {
      bus.addListener(this::onRegister);
      LootJSConditions.CONDITIONS.register(bus);
   }

   public static Provider lookup() {
      return LOOKUP_PROVIDER;
   }

   public static void storeLookup(RegistryAccess lookup) {
      LOOKUP_PROVIDER = new LookupProvider(lookup);
   }

   private void onRegister(RegisterEvent event) {
      if (event.getRegistry() == BuiltInRegistries.ITEM_SUB_PREDICATE_TYPE) {
         event.register(Registries.ITEM_SUB_PREDICATE_TYPE, ResourceLocation.fromNamespaceAndPath("lootjs", "item"), () -> ItemFilterWrapper.TYPE);
      }
   }
}
