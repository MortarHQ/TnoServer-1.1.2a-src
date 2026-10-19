package nadiendev.constructionwand.wand.undo;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.network.PacketUndoBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UndoHistory {
   private final HashMap<UUID, UndoHistory.PlayerEntry> history = new HashMap<>();

   private UndoHistory.PlayerEntry getEntryFromPlayer(Player player) {
      return this.history.computeIfAbsent(player.getUUID(), k -> new UndoHistory.PlayerEntry());
   }

   public void add(Player player, Level world, List<ISnapshot> placeSnapshots) {
      LinkedList<UndoHistory.HistoryEntry> list = this.getEntryFromPlayer(player).entries;
      list.add(new UndoHistory.HistoryEntry(placeSnapshots, world));

      while (list.size() > ConfigServer.UNDO_HISTORY.get()) {
         list.removeFirst();
      }
   }

   public void removePlayer(Player player) {
      this.history.remove(player.getUUID());
   }

   public void updateClient(Player player, boolean ctrlDown) {
      Level world = player.level();
      if (!world.isClientSide) {
         UndoHistory.PlayerEntry playerEntry = this.getEntryFromPlayer(player);
         playerEntry.undoActive = ctrlDown;
         LinkedList<UndoHistory.HistoryEntry> historyEntries = playerEntry.entries;
         Set<BlockPos> positions;
         if (historyEntries.isEmpty()) {
            positions = Collections.emptySet();
         } else {
            UndoHistory.HistoryEntry entry = historyEntries.getLast();
            if (entry != null && entry.world.equals(world)) {
               positions = entry.getBlockPositions();
            } else {
               positions = Collections.emptySet();
            }
         }

         PacketUndoBlocks packet = new PacketUndoBlocks(positions);
         ModMessages.sendToPlayer(packet, (ServerPlayer)player);
      }
   }

   public boolean isUndoActive(Player player) {
      return this.getEntryFromPlayer(player).undoActive;
   }

   public boolean undoLast(Player player, Level world) {
      UndoHistory.PlayerEntry playerEntry = this.getEntryFromPlayer(player);
      LinkedList<UndoHistory.HistoryEntry> historyEntries = playerEntry.entries;
      if (historyEntries.isEmpty()) {
         return false;
      } else {
         UndoHistory.HistoryEntry entry = historyEntries.getLast();
         if (!entry.world.equals(world)) {
            return false;
         } else if (entry.undo(player)) {
            historyEntries.remove(entry);
            this.updateClient(player, playerEntry.undoActive);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean undo(Player player, Level world, BlockPos pos) {
      UndoHistory.PlayerEntry playerEntry = this.getEntryFromPlayer(player);
      if (!playerEntry.undoActive) {
         return false;
      } else {
         LinkedList<UndoHistory.HistoryEntry> historyEntries = playerEntry.entries;
         if (historyEntries.isEmpty()) {
            return false;
         } else {
            UndoHistory.HistoryEntry entry = historyEntries.getLast();
            if (!entry.world.equals(world) || !entry.withinRange(pos)) {
               return false;
            } else if (entry.undo(player)) {
               historyEntries.remove(entry);
               this.updateClient(player, true);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private static class HistoryEntry {
      public final List<ISnapshot> placeSnapshots;
      public final Level world;

      public HistoryEntry(List<ISnapshot> placeSnapshots, Level world) {
         this.placeSnapshots = placeSnapshots;
         this.world = world;
      }

      public Set<BlockPos> getBlockPositions() {
         return this.placeSnapshots.stream().map(ISnapshot::getPos).collect(Collectors.toSet());
      }

      public boolean withinRange(BlockPos pos) {
         Set<BlockPos> positions = this.getBlockPositions();
         if (positions.contains(pos)) {
            return true;
         }

         for (BlockPos p : positions) {
            if (pos.closerThan(p, 3.0)) {
               return true;
            }
         }

         return false;
      }

      public boolean undo(Player player) {
         for (ISnapshot snapshot : this.placeSnapshots) {
            if (!snapshot.canRestore(this.world, player)) {
               return false;
            }
         }

         for (ISnapshot snapshot : this.placeSnapshots) {
            if (snapshot.restore(this.world, player) && !player.isCreative()) {
               ItemStack stack = snapshot.getRequiredItems();
               if (!player.getInventory().add(stack)) {
                  player.drop(stack, false);
               }
            }
         }

         player.getInventory().setChanged();
         SoundEvent sound = SoundEvents.CHORUS_FRUIT_TELEPORT;
         this.world.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
         return true;
      }
   }

   private static class PlayerEntry {
      public final LinkedList<UndoHistory.HistoryEntry> entries = new LinkedList<>();
      public boolean undoActive = false;

      public PlayerEntry() {
      }
   }
}
