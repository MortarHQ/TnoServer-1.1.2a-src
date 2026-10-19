package com.natamus.collective;

import com.natamus.collective.fabric.networking.FabricNetworkHandler;
import com.natamus.collective_common_fabric.CollectiveCommon;
import com.natamus.collective_common_fabric.check.RegisterMod;
import com.natamus.collective_common_fabric.cmds.CommandCollective;
import com.natamus.collective_common_fabric.config.GenerateJSONFiles;
import com.natamus.collective_common_fabric.events.CollectiveEvents;
import com.natamus.collective_common_fabric.implementations.networking.NetworkSetup;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartTick;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.Load;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.Before;

public class CollectiveFabric implements ModInitializer {
   public void onInitialize() {
      new NetworkSetup(new FabricNetworkHandler(Side.SERVER));
      setGlobalConstants();
      CollectiveCommon.init();
      ServerLifecycleEvents.SERVER_STARTING.register((ServerStarting)minecraftServer -> GenerateJSONFiles.initGeneration(minecraftServer));
      ServerWorldEvents.LOAD.register((Load)(server, level) -> CollectiveEvents.onWorldLoad(level));
      ServerTickEvents.START_WORLD_TICK.register((StartWorldTick)serverLevel -> CollectiveEvents.onWorldTick(serverLevel));
      ServerTickEvents.START_SERVER_TICK.register((StartTick)minecraftServer -> CollectiveEvents.onServerTick(minecraftServer));
      ServerEntityEvents.ENTITY_LOAD
         .register(
            (net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents.Load)(entity, serverLevel) -> CollectiveEvents.onEntityJoinLevel(serverLevel, entity)
         );
      PlayerBlockBreakEvents.BEFORE.register((Before)(world, player, pos, state, entity) -> CollectiveEvents.onBlockBreak(world, player, pos, state, entity));
      CommandRegistrationCallback.EVENT
         .register((CommandRegistrationCallback)(dispatcher, registryAccess, environment) -> CommandCollective.register(dispatcher));
      RegisterMod.register("Collective", "collective", "8.3", "[1.21.1]");
   }

   private static void setGlobalConstants() {
   }
}
