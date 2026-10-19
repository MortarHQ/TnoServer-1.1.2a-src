package net.jadenxgamer.netherexp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockCrackTracker {
   private static final Map<Level, Map<BlockPos, BlockCrackTracker.CrackEntry>> LEVEL_DATA = new HashMap<>();

   public static void onBlockHit(Level level, BlockPos pos, BlockState state, double damageDealt) {
      if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
         if (!(damageDealt <= 0.0)) {
            float hardness = state.getDestroySpeed(serverLevel, pos);
            if (!(hardness < 0.0F) && hardness != Float.POSITIVE_INFINITY) {
               Map<BlockPos, BlockCrackTracker.CrackEntry> blockMap = LEVEL_DATA.computeIfAbsent(level, k -> new HashMap<>());
               BlockCrackTracker.CrackEntry entry = blockMap.get(pos);
               if (entry == null) {
                  Marker marker = new Marker(EntityType.MARKER, level);
                  marker.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                  level.addFreshEntity(marker);
                  entry = new BlockCrackTracker.CrackEntry(damageDealt, hardness, marker.getId(), 80);
                  blockMap.put(pos, entry);
               } else {
                  entry = new BlockCrackTracker.CrackEntry(entry.damage() + damageDealt, entry.neededHits(), entry.markerId(), 80);
                  blockMap.put(pos, entry);
               }

               if (entry.damage() >= entry.neededHits()) {
                  blockMap.remove(pos);
                  Block.dropResources(state, serverLevel, pos, null, null, ItemStack.EMPTY);
                  serverLevel.destroyBlock(pos, false);
                  sendCrackPacket(serverLevel, pos, entry.markerId(), -1);
                  removeMarker(level, entry.markerId());
               } else {
                  int stage = (int)(entry.damage() / entry.neededHits() * 9.0);
                  stage = Math.min(9, Math.max(0, stage));
                  sendCrackPacket(serverLevel, pos, entry.markerId(), stage);
               }
            }
         }
      }
   }

   public static void tick() {
      Iterator<Entry<Level, Map<BlockPos, BlockCrackTracker.CrackEntry>>> worldIt = LEVEL_DATA.entrySet().iterator();

      while (worldIt.hasNext()) {
         Entry<Level, Map<BlockPos, BlockCrackTracker.CrackEntry>> worldEntry = worldIt.next();
         Level level = worldEntry.getKey();
         Map<BlockPos, BlockCrackTracker.CrackEntry> blockMap = worldEntry.getValue();
         Iterator<Entry<BlockPos, BlockCrackTracker.CrackEntry>> entryIt = blockMap.entrySet().iterator();

         while (entryIt.hasNext()) {
            Entry<BlockPos, BlockCrackTracker.CrackEntry> entry = entryIt.next();
            BlockCrackTracker.CrackEntry crackEntry = entry.getValue();
            if (crackEntry.resetTicks() <= 0) {
               if (level instanceof ServerLevel serverLevel) {
                  sendCrackPacket(serverLevel, entry.getKey(), crackEntry.markerId(), -1);
                  removeMarker(level, crackEntry.markerId());
               }

               entryIt.remove();
            } else {
               BlockCrackTracker.CrackEntry updated = new BlockCrackTracker.CrackEntry(
                  crackEntry.damage(), crackEntry.neededHits(), crackEntry.markerId(), crackEntry.resetTicks() - 1
               );
               entry.setValue(updated);
            }
         }

         if (blockMap.isEmpty()) {
            worldIt.remove();
         }
      }
   }

   public static void onLevelUnload(Level level) {
      Map<BlockPos, BlockCrackTracker.CrackEntry> blockMap = LEVEL_DATA.remove(level);
      if (blockMap != null) {
         for (BlockCrackTracker.CrackEntry entry : blockMap.values()) {
            removeMarker(level, entry.markerId());
         }
      }
   }

   private static void sendCrackPacket(ServerLevel level, BlockPos pos, int markerId, int progress) {
      Entity marker = level.getEntity(markerId);
      if (marker != null && marker.isAlive()) {
         ClientboundBlockDestructionPacket packet = new ClientboundBlockDestructionPacket(markerId, pos, progress);

         for (ServerPlayer player : level.players()) {
            if (player.blockPosition().distSqr(pos) <= 4096.0) {
               player.connection.send(packet);
            }
         }
      }
   }

   private static void removeMarker(Level level, int markerId) {
      Entity marker = level.getEntity(markerId);
      if (marker != null && marker.isAlive()) {
         marker.discard();
      }
   }

   private record CrackEntry(double damage, double neededHits, int markerId, int resetTicks) {
   }
}
