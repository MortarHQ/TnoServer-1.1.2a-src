package com.natamus.starterkit;

import com.natamus.collective.fabric.callbacks.CollectiveCommandEvents;
import com.natamus.collective.fabric.callbacks.CollectiveCommandEvents.On_Command_Parse;
import com.natamus.collective_common_fabric.check.RegisterMod;
import com.natamus.collective_common_fabric.check.ShouldLoadCheck;
import com.natamus.starterkit_common_fabric.ModCommon;
import com.natamus.starterkit_common_fabric.cmds.CommandStarterkit;
import com.natamus.starterkit_common_fabric.events.StarterServerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.Load;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting;

public class ModFabric implements ModInitializer {
   public void onInitialize() {
      if (ShouldLoadCheck.shouldLoad("starterkit")) {
         setGlobalConstants();
         ModCommon.init();
         this.loadEvents();
         RegisterMod.register("Starter Kit", "starterkit", "7.4", "[1.21.1]");
      }
   }

   private void loadEvents() {
      ServerLifecycleEvents.SERVER_STARTING.register((ServerStarting)minecraftServer -> StarterServerEvents.onServerStarting(minecraftServer));
      ServerEntityEvents.ENTITY_LOAD.register((Load)(entity, world) -> StarterServerEvents.onSpawn(world, entity));
      CollectiveCommandEvents.ON_COMMAND_PARSE.register((On_Command_Parse)(string, parse) -> StarterServerEvents.onCommand(string, parse));
      CommandRegistrationCallback.EVENT
         .register((CommandRegistrationCallback)(dispatcher, registryAccess, environment) -> CommandStarterkit.register(dispatcher));
   }

   private static void setGlobalConstants() {
   }
}
