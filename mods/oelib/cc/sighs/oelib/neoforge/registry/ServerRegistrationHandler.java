package cc.sighs.oelib.neoforge.registry;

import cc.sighs.oelib.registry.RegisterSupplier;
import cc.sighs.oelib.registry.action.CommandRegisterAction;
import cc.sighs.oelib.registry.action.CreativeTabAppendStackAction;
import cc.sighs.oelib.registry.action.CreativeTabModifyAction;
import cc.sighs.oelib.registry.action.EntityAttributeAction;
import cc.sighs.oelib.registry.action.FuelAction;
import cc.sighs.oelib.registry.action.ListenAction;
import cc.sighs.oelib.registry.action.RegistrationAction;
import cc.sighs.oelib.registry.action.RegistryBatchAction;
import cc.sighs.oelib.registry.action.SpawnPlacementAction;
import cc.sighs.oelib.registry.action.TradeVillagerAction;
import cc.sighs.oelib.registry.action.TradeWandererAction;
import cc.sighs.oelib.registry.api.CreativeTabModifyCallback;
import cc.sighs.oelib.registry.api.CreativeTabOutput;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class ServerRegistrationHandler {
   private static final Map<ResourceKey<?>, List<ServerRegistrationHandler.ListenEntry<?>>> ENTRY_LISTENERS = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, List<Supplier<ItemStack>>> TAB_APPENDS = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, List<CreativeTabModifyCallback>> TAB_MODIFIERS = new ConcurrentHashMap<>();
   private static final Map<ResourceKey<?>, List<RegisterSupplier<?>>> REGISTERED_ENTRIES = new ConcurrentHashMap<>();
   private static final List<CommandRegisterAction> SERVER_COMMANDS = new CopyOnWriteArrayList<>();
   private static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<Builder>> ATTRIBUTES = new ConcurrentHashMap<>();
   private static final List<SpawnPlacementAction<?>> SPAWN_PLACEMENTS = new CopyOnWriteArrayList<>();
   private static final Map<VillagerProfession, Int2ObjectMap<List<ItemListing>>> VILLAGER_TRADES = new ConcurrentHashMap<>();
   private static final Int2ObjectMap<List<ItemListing>> WANDERER_TRADES_GENERIC = new Int2ObjectOpenHashMap();
   private static final Int2ObjectMap<List<ItemListing>> WANDERER_TRADES_RARE = new Int2ObjectOpenHashMap();
   private static final Map<ItemLike, Integer> FUEL_TIMES = new ConcurrentHashMap<>();

   private static void putFuelTime(int time, ItemLike item) {
      if (time >= 0) {
         FUEL_TIMES.put(item, time);
      } else {
         FUEL_TIMES.remove(item);
      }
   }

   public void perform(RegistrationAction action) {
      if (action instanceof RegistryBatchAction<?> batchRaw) {
         this.performRegistryBatch(batchRaw);
      } else if (action instanceof ListenAction<?> laRaw) {
         ListenAction<Object> la = (ListenAction<Object>)laRaw;
         ENTRY_LISTENERS.computeIfAbsent(la.registryKey(), k -> new CopyOnWriteArrayList<>())
            .add(new ServerRegistrationHandler.ListenEntry<>(la.supplier(), la.callback()));
      } else if (action instanceof CreativeTabAppendStackAction(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> item)) {
         TAB_APPENDS.computeIfAbsent(tab.location(), k -> new CopyOnWriteArrayList<>()).add(item);
      } else if (action instanceof CreativeTabModifyAction(ResourceKey<CreativeModeTab> tab, CreativeTabModifyCallback callback)) {
         TAB_MODIFIERS.computeIfAbsent(tab.location(), k -> new CopyOnWriteArrayList<>()).add(callback);
      } else if (action instanceof CommandRegisterAction cra) {
         if (!cra.clientOnly()) {
            SERVER_COMMANDS.add(cra);
         }
      } else if (action instanceof EntityAttributeAction(Supplier<? extends EntityType<? extends LivingEntity>> type, Supplier<Builder> attributes)) {
         ATTRIBUTES.put(type, attributes);
      } else if (action instanceof SpawnPlacementAction<?> spa) {
         SPAWN_PLACEMENTS.add(spa);
      } else if (action instanceof TradeVillagerAction(VillagerProfession profession, int level, ItemListing[] trades)) {
         Int2ObjectMap<List<ItemListing>> map = VILLAGER_TRADES.computeIfAbsent(profession, k -> new Int2ObjectOpenHashMap());
         List<ItemListing> list = (List<ItemListing>)map.computeIfAbsent(level, k -> new ArrayList());
         Collections.addAll(list, trades);
      } else if (action instanceof TradeWandererAction(boolean rare, ItemListing[] trades)) {
         Int2ObjectMap<List<ItemListing>> target = rare ? WANDERER_TRADES_RARE : WANDERER_TRADES_GENERIC;
         List<ItemListing> list = (List<ItemListing>)target.computeIfAbsent(rare ? 2 : 1, k -> new ArrayList());
         Collections.addAll(list, trades);
      } else {
         if (action instanceof FuelAction(int time, Supplier<? extends ItemLike>[] items)) {
            for (Supplier<? extends ItemLike> s : items) {
               if (s instanceof RegisterSupplier<?> rs) {
                  RegisterSupplier<? extends ItemLike> itemSupplier = (RegisterSupplier<? extends ItemLike>)rs;
                  itemSupplier.listen(item -> putFuelTime(time, item));
               } else {
                  putFuelTime(time, s.get());
               }
            }
         }
      }
   }

   private <T> void performRegistryBatch(RegistryBatchAction<T> batch) {
      ResourceKey<? extends Registry<T>> key = batch.registryKey();
      String modid = batch.modid();
      DeferredRegister<T> dr = DeferredRegister.create(key, modid);
      dr.register(ModLoadingContext.get().getActiveContainer().getEventBus());

      for (RegisterSupplier<? extends T> entry : batch.entries()) {
         Supplier<T> supplier = (Supplier<T>)entry.getCreator();
         DeferredHolder<T, T> ro = dr.register(entry.id().getPath(), supplier);
         entry.setGetter(ro);
      }

      REGISTERED_ENTRIES.computeIfAbsent(key, _k -> new CopyOnWriteArrayList<>()).addAll(batch.entries());
   }

   private record ListenEntry<T>(RegisterSupplier<? extends T> supplier, Consumer<? super T> callback) {
   }

   @EventBusSubscriber(modid = "oelib")
   public static class ModEventHooks {
      @SubscribeEvent(priority = EventPriority.LOWEST)
      public static void onNotifyRegistered(RegisterEvent event) {
         List<RegisterSupplier<?>> entries = ServerRegistrationHandler.REGISTERED_ENTRIES.remove(event.getRegistryKey());
         if (entries != null) {
            for (RegisterSupplier<?> e : entries) {
               Object obj = e.get();
               ((RegisterSupplier<Object>)e).bindInstance(obj);
            }
         }

         List<ServerRegistrationHandler.ListenEntry<?>> list = ServerRegistrationHandler.ENTRY_LISTENERS.remove(event.getRegistryKey());
         if (list != null) {
            for (ServerRegistrationHandler.ListenEntry<?> leRaw : list) {
               ServerRegistrationHandler.ListenEntry<Object> le = (ServerRegistrationHandler.ListenEntry<Object>)leRaw;
               le.callback.accept((Object)le.supplier.get());
            }
         }
      }

      @SubscribeEvent
      public static void onBuildCreative(BuildCreativeModeTabContentsEvent event) {
         ResourceLocation id = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(event.getTab());
         if (id != null) {
            List<Supplier<ItemStack>> items = ServerRegistrationHandler.TAB_APPENDS.get(id);
            if (items != null) {
               for (Supplier<ItemStack> s : items) {
                  event.accept(s.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
               }
            }

            List<CreativeTabModifyCallback> modifiers = ServerRegistrationHandler.TAB_MODIFIERS.get(id);
            if (modifiers != null) {
               var out = new CreativeTabOutput() {
                  @Override
                  public void acceptAfter(ItemStack after, ItemStack stack, TabVisibility v) {
                     event.insertAfter(after, stack, v);
                  }

                  @Override
                  public void acceptBefore(ItemStack before, ItemStack stack, TabVisibility v) {
                     event.insertBefore(before, stack, v);
                  }

                  @Override
                  public void accept(ItemStack stack, TabVisibility v) {
                     event.accept(stack, v);
                  }
               };

               for (CreativeTabModifyCallback m : modifiers) {
                  m.accept(event.getFlags(), out, event.hasPermissions());
               }
            }
         }
      }

      @SubscribeEvent
      public static void onEntityAttributes(EntityAttributeCreationEvent event) {
         for (Entry<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<Builder>> e : ServerRegistrationHandler.ATTRIBUTES.entrySet()) {
            event.put(e.getKey().get(), e.getValue().get().build());
         }
      }

      @SubscribeEvent
      public static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
         for (SpawnPlacementAction<?> spaRaw : ServerRegistrationHandler.SPAWN_PLACEMENTS) {
            SpawnPlacementAction<Mob> spa = (SpawnPlacementAction<Mob>)spaRaw;
            event.register(spa.type().get(), spa.placement(), spa.heightmap(), spa.predicate(), Operation.OR);
         }
      }

      @SubscribeEvent
      public static void onVillagerTrades(VillagerTradesEvent event) {
         Int2ObjectMap<List<ItemListing>> map = ServerRegistrationHandler.VILLAGER_TRADES.get(event.getType());
         if (map != null) {
            ObjectIterator var2 = map.int2ObjectEntrySet().iterator();

            while (var2.hasNext()) {
               it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<List<ItemListing>> entry = (it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<List<ItemListing>>)var2.next();
               ((List)event.getTrades().computeIfAbsent(entry.getIntKey(), k -> NonNullList.create())).addAll((Collection)entry.getValue());
            }
         }
      }

      @SubscribeEvent
      public static void onWandererTrades(WandererTradesEvent event) {
         List<ItemListing> generic = (List<ItemListing>)ServerRegistrationHandler.WANDERER_TRADES_GENERIC.get(1);
         List<ItemListing> rare = (List<ItemListing>)ServerRegistrationHandler.WANDERER_TRADES_RARE.get(2);
         if (generic != null) {
            event.getGenericTrades().addAll(generic);
         }

         if (rare != null) {
            event.getRareTrades().addAll(rare);
         }
      }

      @SubscribeEvent
      public static void onFurnaceFuel(FurnaceFuelBurnTimeEvent event) {
         ItemStack stack = event.getItemStack();
         if (!stack.isEmpty()) {
            int time = ServerRegistrationHandler.FUEL_TIMES.getOrDefault(stack.getItem(), Integer.MIN_VALUE);
            if (time != Integer.MIN_VALUE) {
               event.setBurnTime(time);
            }
         }
      }

      @SubscribeEvent
      public static void onRegisterCommands(RegisterCommandsEvent event) {
         for (CommandRegisterAction cra : ServerRegistrationHandler.SERVER_COMMANDS) {
            cra.registrar().register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
         }
      }
   }
}
