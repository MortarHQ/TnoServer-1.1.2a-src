package com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot;

import com.nododiiiii.ponderer.neoforge.sticksnapshot.StickSnapshotFeature;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent.Close;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

public class ReplaySessionManager {
   private static final Map<UUID, ReplaySessionManager.ReplaySession> SESSIONS = new ConcurrentHashMap<>();

   private ReplaySessionManager() {
   }

   public static BlockPos getSandboxPos(ServerPlayer player) {
      long hi = player.getUUID().getMostSignificantBits();
      long lo = player.getUUID().getLeastSignificantBits();
      int hash = (int)(hi ^ lo ^ hi >>> 32 ^ lo >>> 32);
      int x = 2000000 + (hash & 16383) * 32;
      int z = 2000000 + (hash >>> 14 & 16383) * 32;
      int y = player.serverLevel().getMinBuildHeight() + 4;
      return new BlockPos(x, y, z);
   }

   public static void beginSession(ServerPlayer player, ServerLevel level, BlockPos pos, BlockState originalState, CompoundTag originalBeTag, int containerId) {
      SESSIONS.put(player.getUUID(), new ReplaySessionManager.ReplaySession(level.dimension(), pos.immutable(), originalState, originalBeTag, containerId));
   }

   public static void restoreSession(ServerPlayer player) {
      ReplaySessionManager.ReplaySession session = SESSIONS.remove(player.getUUID());
      if (session != null) {
         ServerLevel level = player.server.getLevel(session.dimension);
         if (level != null && level.hasChunkAt(session.pos)) {
            restoreBlock(level, session.pos, session.originalState, session.originalBeTag);
         }
      }
   }

   public static void restoreBlock(ServerLevel level, BlockPos pos, BlockState originalState, CompoundTag originalBeTag) {
      level.setBlock(pos, originalState, 0);
      if (originalBeTag != null) {
         BlockEntity restored = BlockEntity.loadStatic(pos, originalState, originalBeTag, level.registryAccess());
         if (restored != null) {
            level.setBlockEntity(restored);
         }
      } else {
         level.removeBlockEntity(pos);
      }
   }

   public static void disableReachabilityCheck(AbstractContainerMenu menu) {
      try {
         for (Field field : AbstractContainerMenu.class.getDeclaredFields()) {
            if (field.getType() == boolean.class && !Modifier.isStatic(field.getModifiers())) {
               field.setAccessible(true);
               field.setBoolean(menu, false);
            }
         }
      } catch (ReflectiveOperationException ex) {
         StickSnapshotFeature.LOGGER.warn("Failed to disable menu reachability check for {}", menu.getClass().getName(), ex);
      }
   }

   @SubscribeEvent
   public static void onContainerClose(Close event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         ReplaySessionManager.ReplaySession session = SESSIONS.get(serverPlayer.getUUID());
         if (session != null && event.getContainer().containerId == session.containerId) {
            restoreSession(serverPlayer);
         }
      }
   }

   @SubscribeEvent
   public static void onLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         restoreSession(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onDimensionChange(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         restoreSession(serverPlayer);
      }
   }

   private static class ReplaySession {
      private final ResourceKey<Level> dimension;
      private final BlockPos pos;
      private final BlockState originalState;
      private final CompoundTag originalBeTag;
      private final int containerId;

      private ReplaySession(ResourceKey<Level> dimension, BlockPos pos, BlockState originalState, CompoundTag originalBeTag, int containerId) {
         this.dimension = dimension;
         this.pos = pos;
         this.originalState = originalState;
         this.originalBeTag = originalBeTag;
         this.containerId = containerId;
      }
   }
}
