package com.foxablazeultimate.compat.stextras;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

public final class STExtrasCompat {
   public static final String MOD_ID = "stextras";
   private static volatile boolean RESOLVED = false;
   private static volatile Method LOCK_SKILL;
   private static volatile Method UNLOCK_SKILL;
   private static volatile Method IS_SKILL_LOCKED;
   private static volatile Method GET_LOCKED_SKILLS;
   private static volatile Method GET_ADMIN_LOCKED_SKILLS;
   private static volatile Method ADD_ADMIN_LOCKED_SKILL;
   private static volatile Method REMOVE_ADMIN_LOCKED_SKILL;
   private static volatile Method SYNC_PLAYER_DATA;

   private STExtrasCompat() {
   }

   public static boolean isLoaded() {
      return ModList.get() != null && ModList.get().isLoaded("stextras");
   }

   public static boolean lockSkill(ServerPlayer player, ResourceLocation skillId) {
      if (!isLoaded()) {
         return false;
      }

      Method m = resolveLockSkill();
      if (m == null) {
         return false;
      }

      try {
         return m.invoke(null, player, skillId) instanceof Boolean b && b;
      } catch (ReflectiveOperationException e) {
         FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] 调用 STExtras.lockSkill 失败：{}", e.toString());
         return false;
      }
   }

   public static boolean isSkillLocked(ServerPlayer player, ResourceLocation skillId) {
      if (player != null && skillId != null && isLoaded()) {
         Method m = resolveIsSkillLocked();
         if (m == null) {
            return false;
         }

         try {
            return m.invoke(null, player, skillId) instanceof Boolean b && b;
         } catch (ReflectiveOperationException e) {
            FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Failed to query STExtras skill lock: {}", e.toString());
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean isRegularSkillLocked(ServerPlayer player, ResourceLocation skillId) {
      return containsReflectedSkill(player, skillId, false);
   }

   public static boolean isAdminSkillLocked(ServerPlayer player, ResourceLocation skillId) {
      return containsReflectedSkill(player, skillId, true);
   }

   public static boolean lockAdminSkill(ServerPlayer player, ResourceLocation skillId) {
      if (player != null && skillId != null && isLoaded()) {
         resolveMethods();
         if (ADD_ADMIN_LOCKED_SKILL == null) {
            return false;
         }

         try {
            return Boolean.TRUE.equals(ADD_ADMIN_LOCKED_SKILL.invoke(player, skillId));
         } catch (ReflectiveOperationException e) {
            FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Failed to add STExtras admin skill lock: {}", e.toString());
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean ensureRegularSkillLocked(ServerPlayer player, ResourceLocation skillId) {
      return isRegularSkillLocked(player, skillId) || lockSkill(player, skillId) || isRegularSkillLocked(player, skillId);
   }

   public static boolean ensureAdminSkillLocked(ServerPlayer player, ResourceLocation skillId) {
      return isAdminSkillLocked(player, skillId) || lockAdminSkill(player, skillId) || isAdminSkillLocked(player, skillId);
   }

   public static void syncPlayerData(ServerPlayer player) {
      if (player != null && isLoaded()) {
         resolveMethods();
         if (SYNC_PLAYER_DATA != null) {
            try {
               SYNC_PLAYER_DATA.invoke(null, player);
            } catch (ReflectiveOperationException e) {
               FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Failed to sync STExtras player data: {}", e.toString());
            }
         }
      }
   }

   public static void migrateSkillId(ServerPlayer player, ResourceLocation from, ResourceLocation to) {
      boolean regular = isRegularSkillLocked(player, from);
      boolean admin = isAdminSkillLocked(player, from);
      if (regular || admin) {
         boolean regularMigrated = !regular || ensureRegularSkillLocked(player, to);
         boolean adminMigrated = !admin || ensureAdminSkillLocked(player, to);
         if (regularMigrated && adminMigrated) {
            removeSkillLocks(player, from);
         }

         syncPlayerData(player);
      }
   }

   public static boolean removeSkillLocks(ServerPlayer player, ResourceLocation skillId) {
      if (player != null && skillId != null && isLoaded()) {
         resolveMethods();
         boolean changed = false;

         try {
            if (UNLOCK_SKILL != null) {
               changed |= Boolean.TRUE.equals(UNLOCK_SKILL.invoke(null, player, skillId));
            }

            if (REMOVE_ADMIN_LOCKED_SKILL != null) {
               changed |= Boolean.TRUE.equals(REMOVE_ADMIN_LOCKED_SKILL.invoke(player, skillId));
            }
         } catch (ReflectiveOperationException e) {
            FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Failed to clear STExtras skill locks for {}: {}", skillId, e.toString());
         }

         return changed;
      } else {
         return false;
      }
   }

   private static Method resolveLockSkill() {
      resolveMethods();
      return LOCK_SKILL;
   }

   private static Method resolveIsSkillLocked() {
      resolveMethods();
      return IS_SKILL_LOCKED;
   }

   private static boolean containsReflectedSkill(ServerPlayer player, ResourceLocation skillId, boolean admin) {
      if (player != null && skillId != null && isLoaded()) {
         resolveMethods();
         Method resolved = admin ? GET_ADMIN_LOCKED_SKILLS : GET_LOCKED_SKILLS;
         if (resolved == null) {
            return false;
         }

         try {
            return resolved.invoke(null, player) instanceof List<?> list && list.contains(skillId);
         } catch (ReflectiveOperationException e) {
            FoxAblazeUltimateMod.LOGGER.warn("[FoxAblazeUltimate] Failed to query STExtras lock category: {}", e.toString());
            return false;
         }
      } else {
         return false;
      }
   }

   private static void resolveMethods() {
      if (!RESOLVED) {
         synchronized (STExtrasCompat.class) {
            if (!RESOLVED) {
               try {
                  Class<?> player = Class.forName("org.crypticdev.stextras.storage.STExtarsStorage$Player");
                  LOCK_SKILL = player.getMethod("lockSkill", ServerPlayer.class, ResourceLocation.class);
                  UNLOCK_SKILL = player.getMethod("unlockSkill", ServerPlayer.class, ResourceLocation.class);
                  IS_SKILL_LOCKED = player.getMethod("isSkillLocked", ServerPlayer.class, ResourceLocation.class);
                  GET_LOCKED_SKILLS = player.getMethod("getLockedSkills", ServerPlayer.class);
                  GET_ADMIN_LOCKED_SKILLS = player.getMethod("getAdminLockedSkills", ServerPlayer.class);
               } catch (ReflectiveOperationException e) {
                  FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] STExtras regular skill-lock reflection unavailable: {}", e.toString());
               }

               try {
                  Class<?> playerStorage = Class.forName("org.crypticdev.stextras.storage.player.ISTExtrasPlayer");
                  ADD_ADMIN_LOCKED_SKILL = playerStorage.getMethod("addAdminLockedSkill", ResourceLocation.class);
                  REMOVE_ADMIN_LOCKED_SKILL = playerStorage.getMethod("removeAdminLockedSkill", ResourceLocation.class);
               } catch (ReflectiveOperationException e) {
                  FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] STExtras admin skill-lock reflection unavailable: {}", e.toString());
               }

               try {
                  Class<?> sync = Class.forName("org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync");
                  SYNC_PLAYER_DATA = sync.getMethod("sync", ServerPlayer.class);
               } catch (ReflectiveOperationException e) {
                  FoxAblazeUltimateMod.LOGGER.info("[FoxAblazeUltimate] STExtras player-data sync reflection unavailable: {}", e.toString());
               }

               RESOLVED = true;
            }
         }
      }
   }
}
