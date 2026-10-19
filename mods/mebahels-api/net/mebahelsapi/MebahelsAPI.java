package net.mebahelsapi;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.EndTick;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Unload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.mebahelsapi.network.ChestOpenPayload;
import net.mebahelsapi.network.UpdateChestPayload;
import net.mebahelsapi.processor.MyProcessors;
import net.mebahelsapi.processor.WaterRemovalScheduler;
import net.mebahelsapi.screenhandler.ModScreenHandlers;
import net.mebahelsapi.util.ModNetworking;
import net.minecraft.class_1937;
import net.minecraft.class_3218;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MebahelsAPI implements ModInitializer {
   public static final String MOD_ID = "mebahelsapi";
   public static final Logger LOGGER = LoggerFactory.getLogger("mebahelsapi");
   private static final WaterRemovalScheduler waterRemovalScheduler = new WaterRemovalScheduler();
   private static final Map<class_3218, EndTick> registeredListeners = new HashMap<>();

   public static WaterRemovalScheduler getWaterRemovalScheduler() {
      return waterRemovalScheduler;
   }

   public void onInitialize() {
      MyProcessors.init();
      PayloadTypeRegistry.playS2C().register(ChestOpenPayload.ID, ChestOpenPayload.CODEC);
      PayloadTypeRegistry.playC2S().register(UpdateChestPayload.ID, UpdateChestPayload.CODEC);
      ModScreenHandlers.registerScreenHandlers();
      ModNetworking.registerReceivers();
      ServerWorldEvents.LOAD.register((Load)(server, world) -> {
         waterRemovalScheduler.addWorld(world);
         EndTick listener = serverTick -> {
            if (serverTick.method_3847(class_1937.field_25179) == world) {
               waterRemovalScheduler.tick();
            }
         };
         ServerTickEvents.END_SERVER_TICK.register(listener);
      });
      ServerWorldEvents.UNLOAD.register((Unload)(server, world) -> {
         waterRemovalScheduler.removeWorld(world);
         registeredListeners.remove(world);
      });
   }
}
