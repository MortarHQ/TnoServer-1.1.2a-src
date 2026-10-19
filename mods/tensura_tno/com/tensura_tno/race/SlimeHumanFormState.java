package com.tensura_tno.race;

import com.tensura_tno.ability.skill.HumanFormSkill;
import com.tensura_tno.network.SlimeHumanFormPackets;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public final class SlimeHumanFormState {
   private static final String TAG_ROOT = "tensura_tno_slime_human_form";
   private static final String TAG_UNLOCKED = "Unlocked";
   private static final String TAG_CHECKED = "Checked";

   private SlimeHumanFormState() {
   }

   public static boolean isUnlocked(Player player) {
      return player == null ? false : getRoot(player).getBoolean("Unlocked");
   }

   public static boolean isChecked(Player player) {
      if (!isUnlocked(player)) {
         return false;
      }

      CompoundTag root = getRoot(player);
      return !root.contains("Checked", 1) || root.getBoolean("Checked");
   }

   public static void unlock(ServerPlayer player) {
      CompoundTag root = getOrCreateRoot(player);
      boolean wasUnlocked = root.getBoolean("Unlocked");
      root.putBoolean("Unlocked", true);
      if (!wasUnlocked || !root.contains("Checked", 1)) {
         root.putBoolean("Checked", true);
      }

      player.getPersistentData().put("tensura_tno_slime_human_form", root);
      applyCurrentSelection(player);
      SlimeHumanFormPackets.syncToClient(player);
   }

   public static void setChecked(ServerPlayer player, boolean checked) {
      if (isUnlocked(player) && SlimeRaceHelper.isSlimeRace(player)) {
         CompoundTag root = getOrCreateRoot(player);
         root.putBoolean("Checked", checked);
         player.getPersistentData().put("tensura_tno_slime_human_form", root);
         applyCurrentSelection(player);
         SlimeHumanFormPackets.syncToClient(player);
      } else {
         SlimeHumanFormPackets.syncToClient(player);
      }
   }

   public static void applyCurrentSelection(ServerPlayer player) {
      if (isUnlocked(player)) {
         if (isChecked(player) && SlimeRaceHelper.isSlimeRace(player)) {
            HumanFormSkill.applyHumanForm(player);
         } else {
            HumanFormSkill.removeHumanForm(player);
         }
      }
   }

   public static void onRaceChanged(ServerPlayer player, ManasRaceInstance newInstance) {
      HumanFormSkill.removeHumanForm(player);
      if (isUnlocked(player) && isChecked(player) && isSlimeRace(newInstance)) {
         MinecraftServer server = player.getServer();
         if (server != null) {
            server.execute(() -> {
               applyCurrentSelection(player);
               SlimeHumanFormPackets.syncToClient(player);
            });
            return;
         }

         applyCurrentSelection(player);
      }

      SlimeHumanFormPackets.syncToClient(player);
   }

   public static void onPlayerClone(Clone event) {
      if (event.isWasDeath()) {
         Player oldPlayer = event.getOriginal();
         Player newPlayer = event.getEntity();
         CompoundTag oldData = oldPlayer.getPersistentData().getCompound("tensura_tno_slime_human_form");
         if (!oldData.isEmpty()) {
            newPlayer.getPersistentData().put("tensura_tno_slime_human_form", oldData.copy());
         }
      }
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         applyCurrentSelection(player);
         SlimeHumanFormPackets.syncToClient(player);
      }
   }

   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         applyCurrentSelection(player);
         SlimeHumanFormPackets.syncToClient(player);
      }
   }

   private static boolean isSlimeRace(ManasRaceInstance instance) {
      if (instance != null && instance.getRace() != null) {
         ResourceLocation id = instance.getRace().getRegistryName();
         return id != null && SlimeRaceHelper.SLIME_RACE_IDS.contains(id);
      } else {
         return false;
      }
   }

   private static CompoundTag getRoot(Player player) {
      CompoundTag data = player.getPersistentData();
      return data.contains("tensura_tno_slime_human_form", 10) ? data.getCompound("tensura_tno_slime_human_form") : new CompoundTag();
   }

   private static CompoundTag getOrCreateRoot(Player player) {
      CompoundTag data = player.getPersistentData();
      CompoundTag root = data.contains("tensura_tno_slime_human_form", 10) ? data.getCompound("tensura_tno_slime_human_form") : new CompoundTag();
      data.put("tensura_tno_slime_human_form", root);
      return root;
   }
}
