package com.chronodawn.events;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatAdvancementHelper;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.core.portal.PortalRegistry;
import com.chronodawn.core.portal.PortalState;
import com.chronodawn.core.portal.PortalStateMachine;
import com.chronodawn.data.ChronoDawnGlobalState;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModDimensions;
import com.chronodawn.registry.ModItems;
import com.chronodawn.worldgen.spawning.TimeKeeperVillagePlacer;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Server;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PlayerEventHandler {
   private static final Map<UUID, ResourceKey<Level>> playerDimensions = new ConcurrentHashMap<>();

   public static void register() {
      TickEvent.SERVER_POST.register((Server)server -> {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ResourceKey<Level> currentDimension = player.level().dimension();
            ResourceKey<Level> previousDimension = playerDimensions.get(player.getUUID());
            if (previousDimension != null && !previousDimension.equals(currentDimension) && currentDimension.equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
               onPlayerChangeDimension(player);
            }

            playerDimensions.put(player.getUUID(), currentDimension);
         }
      });
      PlayerEvent.PLAYER_JOIN.register(PlayerEventHandler::checkAndGrantTyrantDefeatAdvancement);
      ChronoDawn.LOGGER.debug("Registered PlayerEventHandler");
   }

   private static void checkAndGrantTyrantDefeatAdvancement(ServerPlayer player) {
      ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(player.server);
      if (globalState.isTyrantDefeated()) {
         ResourceLocation advancementId = CompatResourceLocation.create("chronodawn", "story/us3/time_tyrant_defeat");
         if (CompatAdvancementHelper.grantAdvancement(player.server, player, advancementId)) {
            ChronoDawn.LOGGER.debug("Auto-granted Time Tyrant defeat advancement to player {} based on world state", player.getName().getString());
         }
      }
   }

   private static void onPlayerChangeDimension(ServerPlayer player) {
      ServerLevel level = player.serverLevel();
      ResourceKey<Level> dimensionKey = level.dimension();
      if (dimensionKey.equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ChronoDawn.LOGGER.debug("Player {} entered ChronoDawn at position {}", player.getName().getString(), player.blockPosition());
         giveChronicleBook(player);
         TimeKeeperVillagePlacer.onPlayerEnterChronoDawn(player);
         ChronoDawnGlobalState globalState = ChronoDawnGlobalState.get(player.server);
         globalState.markChronoDawnEntered();
         if (!globalState.arePortalsUnstable()) {
            ChronoDawn.LOGGER.debug("Portals are stable, skipping deactivation for player {}", player.getName().getString());
         } else {
            BlockPos playerPos = player.blockPosition();
            PortalStateMachine portal = findNearbyPortal(level, playerPos);
            if (portal != null) {
               if (portal.getCurrentState() == PortalState.STABILIZED) {
                  ChronoDawn.LOGGER.debug("Portal {} is already stabilized, skipping deactivation", portal.getPortalId());
                  return;
               }

               if (portal.deactivate()) {
                  extinguishPortal(level, playerPos);
                  ChronoDawn.LOGGER.debug("Deactivated portal {} after player entry", portal.getPortalId());
               }
            } else {
               UUID portalId = UUID.randomUUID();
               portal = new PortalStateMachine(portalId, dimensionKey, playerPos);
               portal.setState(PortalState.DEACTIVATED);
               PortalRegistry.getInstance().registerPortal(portal);
               extinguishPortal(level, playerPos);
               ChronoDawn.LOGGER.debug("Registered and deactivated new portal {} at {}", portalId, playerPos);
            }
         }
      }
   }

   private static PortalStateMachine findNearbyPortal(Level level, BlockPos pos) {
      PortalRegistry registry = PortalRegistry.getInstance();

      for (int x = -5; x <= 5; x++) {
         for (int y = -5; y <= 5; y++) {
            for (int z = -5; z <= 5; z++) {
               BlockPos searchPos = pos.offset(x, y, z);
               PortalStateMachine portal = registry.getPortalAt(searchPos);
               if (portal != null) {
                  return portal;
               }
            }
         }
      }

      return null;
   }

   private static void extinguishPortal(ServerLevel level, BlockPos centerPos) {
      int removedBlocks = 0;
      boolean foundPortalBlock = false;
      ChronoDawn.LOGGER.debug("Searching for portal blocks near {} in dimension {}", centerPos, level.dimension().location());

      for (int x = -15; x <= 15; x++) {
         for (int y = -15; y <= 15; y++) {
            for (int z = -15; z <= 15; z++) {
               BlockPos searchPos = centerPos.offset(x, y, z);
               BlockState blockState = level.getBlockState(searchPos);
               if (blockState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                  foundPortalBlock = true;
                  level.removeBlock(searchPos, false);
                  removedBlocks++;
               }

               if (blockState.is(Blocks.NETHER_PORTAL)) {
                  foundPortalBlock = true;
                  level.removeBlock(searchPos, false);
                  removedBlocks++;
               }
            }
         }
      }

      if (!foundPortalBlock) {
         for (int x = -50; x <= 50; x++) {
            for (int y = -50; y <= 50; y++) {
               for (int z = -50; z <= 50; z++) {
                  BlockPos searchPos = centerPos.offset(x, y, z);
                  BlockState blockState = level.getBlockState(searchPos);
                  if (blockState.is((Block)ModBlocks.CHRONO_DAWN_PORTAL.get())) {
                     level.removeBlock(searchPos, false);
                     removedBlocks++;
                  }

                  if (blockState.is(Blocks.NETHER_PORTAL)) {
                     level.removeBlock(searchPos, false);
                     removedBlocks++;
                  }
               }
            }
         }
      }

      ChronoDawn.LOGGER.debug("Extinguished {} portal blocks near {}", removedBlocks, centerPos);
   }

   private static void giveChronicleBook(ServerPlayer player) {
      if (hasChronicleBook(player)) {
         ChronoDawn.LOGGER.debug("Player {} already has Chronicle book, skipping", player.getName().getString());
      } else {
         try {
            ItemStack bookStack = new ItemStack((ItemLike)ModItems.CHRONICLE_BOOK.get());
            if (!player.getInventory().add(bookStack)) {
               player.drop(bookStack, false);
               ChronoDawn.LOGGER.debug("Player {} inventory full, dropped Chronicle book at their position", player.getName().getString());
            } else {
               ChronoDawn.LOGGER.debug("Gave Chronicle book to player {}", player.getName().getString());
            }
         } catch (Exception e) {
            ChronoDawn.LOGGER.error("Failed to give Chronicle book to player {}: {}", new Object[]{player.getName().getString(), e.getMessage(), e});
         }
      }
   }

   private static boolean hasChronicleBook(ServerPlayer player) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (stack.is((Item)ModItems.CHRONICLE_BOOK.get())) {
            return true;
         }
      }

      return false;
   }

   private static void removeTimeHourglassFromInventory(ServerPlayer player) {
      int removedCount = 0;

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (stack.is((Item)ModItems.TIME_HOURGLASS.get())) {
            int count = stack.getCount();
            player.getInventory().removeItem(i, count);
            removedCount += count;
            ChronoDawn.LOGGER.debug("Removed {} Time Hourglass from slot {}", count, i);
         }
      }

      if (removedCount > 0) {
         ChronoDawn.LOGGER.debug("Removed total {} Time Hourglass items from player {}'s inventory", removedCount, player.getName().getString());
      }
   }
}
