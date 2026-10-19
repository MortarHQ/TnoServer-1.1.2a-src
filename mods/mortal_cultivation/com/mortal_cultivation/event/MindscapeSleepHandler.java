package com.mortal_cultivation.event;

import com.mortal_cultivation.dimension.MindscapeDimension;
import com.mortal_cultivation.registry.race.CultivationRaces;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class MindscapeSleepHandler {
   private static final String TAG_MINDSCAPE_ORIGIN = "mindscape_origin";
   private static final String TAG_FORCE_MINDSCAPE = "mindscape_force";
   private static final int DELAY_TICKS = 50;
   private static final Map<UUID, Integer> pendingTeleports = new HashMap<>();
   private static final Set<UUID> checkedPlayers = new HashSet<>();

   @SubscribeEvent
   public static void onServerTick(Post event) {
      for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
         for (ServerPlayer player : List.copyOf(level.players())) {
            UUID uuid = player.getUUID();
            if (player.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL && !MindscapeChallengeManager.isInChallenge(uuid)) {
               returnToOrigin(player, false);
            } else if (player.isSleeping()) {
               if (!checkedPlayers.contains(uuid) && !pendingTeleports.containsKey(uuid)) {
                  checkedPlayers.add(uuid);
                  checkAndScheduleTeleport(player);
               }
            } else {
               checkedPlayers.remove(uuid);
            }
         }
      }

      if (!pendingTeleports.isEmpty()) {
         Iterator<Entry<UUID, Integer>> it = pendingTeleports.entrySet().iterator();

         while (it.hasNext()) {
            Entry<UUID, Integer> entry = it.next();
            UUID uuid = entry.getKey();
            int remaining = entry.getValue() - 1;
            ServerPlayer player = findPlayer(uuid);
            if (player == null) {
               it.remove();
            } else if (!player.isSleeping()) {
               it.remove();
            } else if (remaining <= 0) {
               it.remove();
               teleportToMindscape(player);
            } else {
               entry.setValue(remaining);
            }
         }
      }
   }

   private static void checkAndScheduleTeleport(ServerPlayer player) {
      CompoundTag persistentData = player.getPersistentData();
      boolean forced = persistentData.getBoolean("mindscape_force");
      if (!forced) {
         Races raceData = RaceAPI.getRaceFrom(player);
         boolean isWastelander = raceData.getRace().map(instance -> instance.getRace() == CultivationRaces.WASTELANDER_RACE.get()).orElse(false);
         if (!isWastelander) {
            return;
         }

         if (player.getRandom().nextFloat() > 0.25F) {
            return;
         }
      }

      pendingTeleports.put(player.getUUID(), 50);
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.level().dimension() == MindscapeDimension.MINDSCAPE_LEVEL) {
            returnToOrigin(player, false);
         }
      }
   }

   public static void teleportToMindscape(ServerPlayer player) {
      ServerLevel mindscapeLevel = player.server.getLevel(MindscapeDimension.MINDSCAPE_LEVEL);
      if (mindscapeLevel != null) {
         player.stopSleeping();
         CompoundTag persistentData = player.getPersistentData();
         CompoundTag origin = new CompoundTag();
         origin.putString("dimension", player.level().dimension().location().toString());
         origin.putDouble("x", player.getX());
         origin.putDouble("y", player.getY());
         origin.putDouble("z", player.getZ());
         origin.putFloat("yRot", player.getYRot());
         origin.putFloat("xRot", player.getXRot());
         origin.putInt("gamemode", player.gameMode.getGameModeForPlayer().getId());
         persistentData.put("mindscape_origin", origin);
         persistentData.remove("mindscape_force");
         generatePlatform(mindscapeLevel);
         player.teleportTo(mindscapeLevel, 0.5, 65.0, -5.5, 0.0F, 0.0F);
         player.setGameMode(GameType.ADVENTURE);
         player.sendSystemMessage(Component.translatable("mortal_cultivation.mindscape.enter").withStyle(ChatFormatting.GRAY));
         MindscapeChallengeManager.startChallenge(player, mindscapeLevel);
      }
   }

   public static void returnToOrigin(ServerPlayer player, boolean victory) {
      ServerLevel mindscapeLevel = player.server.getLevel(MindscapeDimension.MINDSCAPE_LEVEL);
      if (mindscapeLevel != null) {
         MindscapeChallengeManager.cleanupForPlayer(player.getUUID(), mindscapeLevel);
      }

      CompoundTag persistentData = player.getPersistentData();
      if (!persistentData.contains("mindscape_origin")) {
         ServerLevel overworld = player.server.overworld();
         BlockPos spawn = overworld.getSharedSpawnPos();
         player.teleportTo(overworld, spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, 0.0F, 0.0F);
         player.setGameMode(GameType.SURVIVAL);
      } else {
         CompoundTag origin = persistentData.getCompound("mindscape_origin");
         String dimId = origin.getString("dimension");
         double x = origin.getDouble("x");
         double y = origin.getDouble("y");
         double z = origin.getDouble("z");
         float yRot = origin.getFloat("yRot");
         float xRot = origin.getFloat("xRot");
         int gamemodeId = origin.getInt("gamemode");
         ServerLevel targetLevel = null;

         for (ServerLevel level : player.server.getAllLevels()) {
            if (level.dimension().location().toString().equals(dimId)) {
               targetLevel = level;
               break;
            }
         }

         if (targetLevel == null) {
            targetLevel = player.server.overworld();
         }

         player.teleportTo(targetLevel, x, y, z, yRot, xRot);
         GameType mode = GameType.byId(gamemodeId);
         player.setGameMode(mode);
         persistentData.remove("mindscape_origin");
         if (victory) {
            targetLevel.setDayTime(1000L);
            player.sendSystemMessage(Component.translatable("mortal_cultivation.mindscape.victory").withStyle(ChatFormatting.GOLD));
         } else {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.mindscape.defeat").withStyle(ChatFormatting.RED));
         }
      }
   }

   private static void generatePlatform(ServerLevel level) {
      for (int x = -10; x <= 9; x++) {
         for (int z = -10; z <= 9; z++) {
            BlockPos pos = new BlockPos(x, 64, z);
            if (level.getBlockState(pos).isAir() || !level.getBlockState(pos).is(Blocks.STONE)) {
               level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
            }
         }
      }
   }

   private static ServerPlayer findPlayer(UUID uuid) {
      for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
         ServerPlayer p = (ServerPlayer)level.getPlayerByUUID(uuid);
         if (p != null) {
            return p;
         }
      }

      return null;
   }

   public static void setForceFlag(ServerPlayer player) {
      player.getPersistentData().putBoolean("mindscape_force", true);
   }
}
