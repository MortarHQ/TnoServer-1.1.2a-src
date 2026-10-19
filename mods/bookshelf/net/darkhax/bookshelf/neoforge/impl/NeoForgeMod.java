package net.darkhax.bookshelf.neoforge.impl;

import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.registry.register.RegisterIngredient;
import net.darkhax.bookshelf.common.api.registry.register.RegisterItemTab;
import net.darkhax.bookshelf.common.api.registry.register.RegisterVillagerTrades;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.bookshelf.common.impl.BookshelfMod;
import net.darkhax.bookshelf.neoforge.impl.data.NeoForgeIngredient;
import net.darkhax.bookshelf.neoforge.impl.network.NeoForgeNetworkHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

@Mod("bookshelf")
public class NeoForgeMod {
   private final CachedSupplier<RegisterVillagerTrades> villagerTrades = CachedSupplier.cache(() -> {
      RegisterVillagerTrades register = new RegisterVillagerTrades();
      Services.CONTENT_PROVIDERS.get().forEach(provider -> provider.registerTrades(register));
      return register;
   });

   public NeoForgeMod(IEventBus eventBus) {
      BookshelfMod.getInstance().init();
      NeoForge.EVENT_BUS.addListener(this::registerVillagerTrades);
      NeoForge.EVENT_BUS.addListener(this::registerWandererTrades);
      eventBus.addListener(this::onRegister);
      if (Services.NETWORK instanceof NeoForgeNetworkHandler handler) {
         eventBus.addListener(handler::registerPayloadHandlers);
      }

      if (Services.PLATFORM.isPhysicalClient()) {
         new NeoForgeModClient(eventBus);
      }
   }

   private void onRegister(RegisterEvent event) {
      if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) {
         event.register(Registries.CREATIVE_MODE_TAB, registerFunc -> {
            BiConsumer<ResourceLocation, CreativeModeTab> func = registerFunc::register;
            Services.CONTENT_PROVIDERS.get().forEach(provider -> provider.registerItemTabs(new RegisterItemTab(provider.contentNamespace(), func)));
         });
      } else if (event.getRegistryKey() == Keys.INGREDIENT_TYPES) {
         event.register(
            Keys.INGREDIENT_TYPES,
            registerFunc -> Services.CONTENT_PROVIDERS
               .get()
               .forEach(
                  provider -> {
                     RegisterIngredient registry = new RegisterIngredient(
                        provider.contentNamespace(), (id, codec, stream) -> registerFunc.register(id, NeoForgeIngredient.makeIngredientType(id, codec, stream))
                     );
                     provider.registerIngredientTypes(registry);
                  }
               )
         );
      }
   }

   private void registerVillagerTrades(VillagerTradesEvent event) {
      Multimap<Integer, ItemListing> newTrades = this.villagerTrades.get().getVillagerTrades().get(event.getType());
      if (newTrades != null && !newTrades.isEmpty()) {
         Int2ObjectMap<List<ItemListing>> tradeData = event.getTrades();

         for (Entry<Integer, ItemListing> entry : newTrades.entries()) {
            ((List)tradeData.computeIfAbsent(entry.getKey(), ArrayList::new)).add(entry.getValue());
         }
      }
   }

   private void registerWandererTrades(WandererTradesEvent event) {
      this.villagerTrades.get().getCommonWanderingTrades().forEach(event.getGenericTrades()::add);
      this.villagerTrades.get().getRareWanderingTrades().forEach(event.getRareTrades()::add);
   }
}
