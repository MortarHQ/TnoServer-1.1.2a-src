package com.foxablazeultimate.ability.skill.ciel;

import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class CielState {
   private static final String ROOT_TAG = "foxablazeultimate";
   private static final String STATE_TAG = "ciel_state";
   private static final String AWAKENED = "awakened";
   private static final String NAMING_UNLOCKED = "naming_unlocked";
   private static final String INTEGRATED_CORES = "integrated_charybdis_cores";
   private static final String REFINED_ITEMS = "refined_item_count";
   private static final String LAST_COMBAT_TIME = "last_combat_time";
   private static final String EMERGENCY_TAKEOVER = "emergency_takeover";
   private static final String SKILL_BACKUP = "skill_backup";

   private CielState() {
   }

   public static boolean isAwakened(Player player) {
      return stateTag(player).getBoolean("awakened");
   }

   public static boolean peekAwakened(Player player) {
      return existingStateTag(player).getBoolean("awakened");
   }

   public static void setAwakened(Player player, boolean awakened) {
      CompoundTag state = stateTag(player);
      state.putBoolean("awakened", awakened);
      if (!awakened) {
         state.putBoolean("emergency_takeover", false);
         state.remove("skill_backup");
         resetAcquisitionProgress(state);
      }
   }

   public static void resetAcquisitionProgress(Player player) {
      resetAcquisitionProgress(stateTag(player));
   }

   public static boolean isNamingUnlocked(Player player) {
      return stateTag(player).getBoolean("naming_unlocked");
   }

   public static boolean peekNamingUnlocked(Player player) {
      return existingStateTag(player).getBoolean("naming_unlocked");
   }

   public static boolean unlockNaming(Player player) {
      CompoundTag state = stateTag(player);
      if (state.getBoolean("naming_unlocked")) {
         return false;
      }

      state.putBoolean("naming_unlocked", true);
      return true;
   }

   public static int getIntegratedCharybdisCores(Player player) {
      return Math.max(0, stateTag(player).getInt("integrated_charybdis_cores"));
   }

   public static int peekIntegratedCharybdisCores(Player player) {
      return Math.max(0, existingStateTag(player).getInt("integrated_charybdis_cores"));
   }

   public static int recordIntegratedCharybdisCores(Player player, int amount) {
      return addCapped(stateTag(player), "integrated_charybdis_cores", amount);
   }

   public static int getRefinedItemCount(Player player) {
      return Math.max(0, stateTag(player).getInt("refined_item_count"));
   }

   public static int peekRefinedItemCount(Player player) {
      return Math.max(0, existingStateTag(player).getInt("refined_item_count"));
   }

   public static int recordRefinedItems(Player player, int amount) {
      return addCapped(stateTag(player), "refined_item_count", amount);
   }

   public static void recordCombat(Player player) {
      if (!player.level().isClientSide()) {
         stateTag(player).putLong("last_combat_time", player.level().getGameTime());
      }
   }

   public static boolean isInCombat(Player player, int timeoutTicks) {
      CompoundTag state = stateTag(player);
      if (!state.contains("last_combat_time")) {
         return false;
      }

      long elapsed = player.level().getGameTime() - state.getLong("last_combat_time");
      return elapsed >= 0L && elapsed <= Math.max(0, timeoutTicks);
   }

   public static boolean peekInCombat(Player player, int timeoutTicks) {
      CompoundTag state = existingStateTag(player);
      if (!state.contains("last_combat_time")) {
         return false;
      }

      long elapsed = player.level().getGameTime() - state.getLong("last_combat_time");
      return elapsed >= 0L && elapsed <= Math.max(0, timeoutTicks);
   }

   public static boolean isEmergencyTakeover(Player player) {
      return isAwakened(player) && stateTag(player).getBoolean("emergency_takeover");
   }

   public static boolean toggleEmergencyTakeover(ServerPlayer player) {
      boolean enable = !isEmergencyTakeover(player);
      if (!enable || isAwakened(player) && hasPermanentCiel(player)) {
         setEmergencyTakeover(player, enable);
         return isEmergencyTakeover(player);
      } else {
         return false;
      }
   }

   public static void setEmergencyTakeover(Player player, boolean enabled) {
      stateTag(player).putBoolean("emergency_takeover", enabled && isAwakened(player));
   }

   public static void saveSkillBackup(Player player, ManasSkillInstance instance) {
      if (instance != null && !instance.isTemporarySkill() && !instance.isSubInstance()) {
         stateTag(player).put("skill_backup", instance.toNBT().copy());
      }
   }

   public static Optional<CompoundTag> getSkillBackup(Player player) {
      CompoundTag state = stateTag(player);
      return !state.contains("skill_backup", 10) ? Optional.empty() : Optional.of(state.getCompound("skill_backup").copy());
   }

   public static void copyToClone(Player original, Player clone) {
      CompoundTag originalRoot = original.getPersistentData();
      if (originalRoot.contains("foxablazeultimate", 10)) {
         CompoundTag originalMod = originalRoot.getCompound("foxablazeultimate");
         if (originalMod.contains("ciel_state", 10)) {
            modTag(clone).put("ciel_state", originalMod.getCompound("ciel_state").copy());
         }
      }
   }

   private static boolean hasPermanentCiel(Player player) {
      return SkillAPI.getSkillsFrom(player)
         .getSkill((ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
         .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
         .isPresent();
   }

   private static int addCapped(CompoundTag state, String key, int amount) {
      int current = Math.max(0, state.getInt(key));
      if (amount <= 0) {
         return current;
      }

      int next = (int)Math.min(2147483647L, (long)current + amount);
      state.putInt(key, next);
      return next;
   }

   private static void resetAcquisitionProgress(CompoundTag state) {
      state.remove("naming_unlocked");
      state.remove("integrated_charybdis_cores");
      state.remove("refined_item_count");
      state.remove("last_combat_time");
   }

   private static CompoundTag stateTag(Player player) {
      CompoundTag mod = modTag(player);
      if (!mod.contains("ciel_state", 10)) {
         mod.put("ciel_state", new CompoundTag());
      }

      return mod.getCompound("ciel_state");
   }

   private static CompoundTag existingStateTag(Player player) {
      CompoundTag root = player.getPersistentData();
      if (!root.contains("foxablazeultimate", 10)) {
         return new CompoundTag();
      }

      CompoundTag mod = root.getCompound("foxablazeultimate");
      return !mod.contains("ciel_state", 10) ? new CompoundTag() : mod.getCompound("ciel_state");
   }

   private static CompoundTag modTag(Player player) {
      CompoundTag root = player.getPersistentData();
      if (!root.contains("foxablazeultimate", 10)) {
         root.put("foxablazeultimate", new CompoundTag());
      }

      return root.getCompound("foxablazeultimate");
   }
}
