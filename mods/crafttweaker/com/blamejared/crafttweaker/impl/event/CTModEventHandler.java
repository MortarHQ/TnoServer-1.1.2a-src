package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTests;
import com.blamejared.crafttweaker.impl.network.packet.ClientBoundPackets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryDataLoader.RegistryData;
import net.minecraft.tags.TagManager.LoadResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "crafttweaker", bus = Bus.MOD)
public class CTModEventHandler {
   @SubscribeEvent
   public static void registerPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("crafttweaker").versioned(CraftTweakerConstants.NETWORK_VERSION_STRING);

      for (ClientBoundPackets msg : ClientBoundPackets.values()) {
         registrar.playToClient(msg.type(), msg.streamCodec(), (payload, context) -> context.enqueueWork(payload::handle));
      }
   }

   @SubscribeEvent
   public static void onRegisterGameTests(RegisterGameTestsEvent event) {
      event.register(CraftTweakerGameTests.class);
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void gatherData(GatherDataEvent event) {
      if (event.includeServer()) {
         List<LoadResult<?>> loadResults = new ArrayList<>();

         for (RegistryData<?> data : RegistryDataLoader.WORLDGEN_REGISTRIES) {
            loadResults.add(new LoadResult(data.key(), new HashMap()));
         }

         for (Registry<?> registry : BuiltInRegistries.REGISTRY) {
            loadResults.add(new LoadResult(registry.key(), new HashMap()));
         }

         CraftTweakerTagRegistry.INSTANCE.bind(loadResults);
      }
   }
}
