package org.crypticdev.stextras.integration.lootr;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent.ServerState;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.crypticdev.stextras.Stextras;

public class LootrIntegration {
   private static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create("stextras", Registries.STRUCTURE_PROCESSOR);
   public static final RegistrySupplier<StructureProcessorType<CharybdisLootProcessor>> CHARYBDIS_LOOT_PROCESSOR = STRUCTURE_PROCESSORS.register(
      "charybdis_loot_processor", () -> new StructureProcessorType<CharybdisLootProcessor>() {
         public MapCodec<CharybdisLootProcessor> codec() {
            return CharybdisLootProcessor.CODEC;
         }
      }
   );

   public static void init() {
      Stextras.LOGGER.info("STExtras: Initializing Lootr Integration...");
      STRUCTURE_PROCESSORS.register();
      LifecycleEvent.SERVER_STARTING.register((ServerState)server -> injectProcessorIntoTensuraPool(server.registryAccess()));
   }

   private static void injectProcessorIntoTensuraPool(RegistryAccess registryAccess) {
      Stextras.LOGGER.info("STExtras: Attempting to inject processor into Tensura Pool...");

      try {
         Registry<StructureTemplatePool> templatePoolRegistry = registryAccess.registryOrThrow(Registries.TEMPLATE_POOL);
         ResourceLocation poolId = ResourceLocation.fromNamespaceAndPath("tensura", "charybdis_cave/rooms");
         StructureTemplatePool pool = (StructureTemplatePool)templatePoolRegistry.get(poolId);
         if (pool == null) {
            Stextras.LOGGER.error("STExtras: FAILED. Could not find pool: " + poolId);
            return;
         }

         StructureProcessorList myProcessorList = new StructureProcessorList(List.of(new CharybdisLootProcessor()));
         Holder<StructureProcessorList> myProcessorHolder = Holder.direct(myProcessorList);
         Field processorsField = null;

         for (Field f : SinglePoolElement.class.getDeclaredFields()) {
            if (Holder.class.isAssignableFrom(f.getType())) {
               f.setAccessible(true);
               processorsField = f;
               break;
            }
         }

         if (processorsField == null) {
            Stextras.LOGGER.error("STExtras: FAILED. Could not find 'processors' field.");
            return;
         }

         int count = 0;

         for (Field f : StructureTemplatePool.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (List.class.isAssignableFrom(f.getType()) && f.get(pool) instanceof List<?> list && !list.isEmpty()) {
               Object firstItem = list.get(0);
               if (firstItem instanceof Pair) {
                  for (Object item : list) {
                     if (item instanceof Pair<?, ?> pair && pair.getFirst() instanceof SinglePoolElement singleElement) {
                        processorsField.set(singleElement, myProcessorHolder);
                        count++;
                     }
                  }
               } else if (firstItem instanceof StructurePoolElement) {
                  for (Object item : list) {
                     if (item instanceof SinglePoolElement singleElement) {
                        processorsField.set(singleElement, myProcessorHolder);
                        count++;
                     }
                  }
               }
            }
         }

         Stextras.LOGGER.info("STExtras: Success! Injected Lootr Processor into " + count + " references.");
      } catch (Exception e) {
         Stextras.LOGGER.error("STExtras: Critical error injecting processor", e);
      }
   }
}
