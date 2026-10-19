package com.tensura_tno.ftb;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Method;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class STExtrasHelper {
   private static Method GET_RE;
   private static Method SET_RE;
   private static Method GET_SOUL_GRADE;
   private static Method SET_SOUL_GRADE;
   private static Method ACTIVE_QUESTS;
   private static Method COMPLETED_QUESTS;
   private static Method CAN_PRESTIGE;
   private static Method REQUEST_PRESTIGE_SCREEN_SYNC;
   private static Method GET_PLAYER_QUESTS;
   private static Method QUESTS_TO_NET;
   private static Method QUESTS_CLEAR_DIRTY;
   private static Method HAS_COMPLETED_RACE;
   private static Method SYNC_QUESTS_WITH_RACE_IDS;
   private static Method APPLY_REQUIRED_ON_CHARACTER_RESET;
   private static Object CAT_DAILY;
   private static Object CAT_WEEKLY;
   private static Object CAT_REQUIRED;
   private static boolean loaded = false;

   public static boolean isLoaded() {
      return loaded;
   }

   public static int getRE(ServerPlayer player) {
      if (!loaded) {
         return 0;
      }

      try {
         return (Integer)GET_RE.invoke(null, player);
      } catch (Exception e) {
         return 0;
      }
   }

   public static void setRE(ServerPlayer player, int value) {
      if (loaded) {
         try {
            SET_RE.invoke(null, player, value);
         } catch (Exception var3) {
         }
      }
   }

   public static int getSoulGrade(ServerPlayer player) {
      if (!loaded) {
         return 0;
      }

      try {
         return (Integer)GET_SOUL_GRADE.invoke(null, player);
      } catch (Exception e) {
         return 0;
      }
   }

   public static void setSoulGrade(ServerPlayer player, int value) {
      if (loaded) {
         try {
            SET_SOUL_GRADE.invoke(null, player, value);
         } catch (Exception var3) {
         }
      }
   }

   public static Set<ResourceLocation> getActiveQuests(ServerPlayer player, String category) {
      if (ACTIVE_QUESTS != null && player != null) {
         try {
            Object catEnum = resolveCategory(category);
            return catEnum == null ? null : (Set)ACTIVE_QUESTS.invoke(null, player, catEnum);
         } catch (Exception e) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static Set<ResourceLocation> getCompletedQuests(ServerPlayer player, String category) {
      if (COMPLETED_QUESTS != null && player != null) {
         try {
            Object catEnum = resolveCategory(category);
            return catEnum == null ? null : (Set)COMPLETED_QUESTS.invoke(null, player, catEnum);
         } catch (Exception e) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean canPrestige(ServerPlayer player) {
      if (CAN_PRESTIGE != null && player != null) {
         try {
            return (Boolean)CAN_PRESTIGE.invoke(null, player);
         } catch (Exception e) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void requestPrestigeScreenSync() {
      if (REQUEST_PRESTIGE_SCREEN_SYNC != null) {
         try {
            REQUEST_PRESTIGE_SCREEN_SYNC.invoke(null);
         } catch (Exception var1) {
         }
      }
   }

   public static boolean refreshRequiredQuests(ServerPlayer player, int prestige) {
      if (APPLY_REQUIRED_ON_CHARACTER_RESET != null && player != null) {
         try {
            APPLY_REQUIRED_ON_CHARACTER_RESET.invoke(null, player, prestige);
            syncQuestState(player);
            return true;
         } catch (Exception e) {
            TensuraTNOMod.LOGGER.warn("[TNO] Failed to refresh STExtras required quests: {}", e.getMessage());
            return false;
         }
      } else {
         return false;
      }
   }

   public static void syncQuestState(ServerPlayer player) {
      if (player != null && GET_PLAYER_QUESTS != null && QUESTS_TO_NET != null && SYNC_QUESTS_WITH_RACE_IDS != null) {
         try {
            Object store = GET_PLAYER_QUESTS.invoke(player);
            if (store == null) {
               return;
            }

            CompoundTag questData = (CompoundTag)QUESTS_TO_NET.invoke(store);
            SYNC_QUESTS_WITH_RACE_IDS.invoke(null, player, questData);
            if (QUESTS_CLEAR_DIRTY != null) {
               QUESTS_CLEAR_DIRTY.invoke(store);
            }
         } catch (Exception var3) {
         }
      }
   }

   public static boolean hasCompletedRace(ServerPlayer player, ResourceLocation raceId) {
      if (player != null && raceId != null && GET_PLAYER_QUESTS != null && HAS_COMPLETED_RACE != null) {
         try {
            Object store = GET_PLAYER_QUESTS.invoke(player);
            return store != null && (Boolean)HAS_COMPLETED_RACE.invoke(store, raceId);
         } catch (ReflectiveOperationException ignored) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static Object resolveCategory(String category) {
      if (category == null) {
         return null;
      }

      return switch (category) {
         case "DAILY" -> CAT_DAILY;
         case "WEEKLY" -> CAT_WEEKLY;
         case "REQUIRED" -> CAT_REQUIRED;
         default -> null;
      };
   }

   private STExtrasHelper() {
   }

   static {
      try {
         Class<?> playerStorage = Class.forName("org.crypticdev.stextras.storage.STExtarsStorage$Player");
         GET_RE = playerStorage.getMethod("getReincarnationEssence", ServerPlayer.class);
         SET_RE = playerStorage.getMethod("setReincarnationEssence", ServerPlayer.class, int.class);
         GET_SOUL_GRADE = playerStorage.getMethod("getSoulGrade", ServerPlayer.class);
         SET_SOUL_GRADE = playerStorage.getMethod("setSoulGrade", ServerPlayer.class, int.class);
         loaded = true;
      } catch (ReflectiveOperationException | LinkageError e) {
         TensuraTNOMod.LOGGER.warn("[TNO] STExtras player storage is unavailable: {}", e.getMessage());
      }

      try {
         Class<?> questsStorage = Class.forName("org.crypticdev.stextras.storage.STExtarsStorage$Quests");
         Class<?> categoryClass = Class.forName("org.crypticdev.stextras.quest.definition.QuestCategory");
         ACTIVE_QUESTS = questsStorage.getMethod("active", ServerPlayer.class, categoryClass);
         COMPLETED_QUESTS = questsStorage.getMethod("completed", ServerPlayer.class, categoryClass);
         Class<Enum> catEnum = (Class<Enum>)categoryClass;
         CAT_DAILY = Enum.valueOf(catEnum, "DAILY");
         CAT_WEEKLY = Enum.valueOf(catEnum, "WEEKLY");
         CAT_REQUIRED = Enum.valueOf(catEnum, "REQUIRED");
      } catch (ReflectiveOperationException | LinkageError e) {
         TensuraTNOMod.LOGGER.warn("[TNO] STExtras quest storage is unavailable: {}", e.getMessage());
      }

      try {
         Class<?> prestigeUtils = Class.forName("org.crypticdev.stextras.utils.PrestigeUtils");
         CAN_PRESTIGE = prestigeUtils.getMethod("canPrestige", ServerPlayer.class);
      } catch (ReflectiveOperationException | LinkageError var7) {
      }

      try {
         Class<?> questAccess = Class.forName("org.crypticdev.stextras.storage.quest.ISTExtrasQuests");
         Class<?> questStorage = Class.forName("org.crypticdev.stextras.storage.quest.STExtrasQuestStorage");
         GET_PLAYER_QUESTS = questAccess.getMethod("stextras$getQuests");
         QUESTS_TO_NET = questStorage.getMethod("toNet");
         QUESTS_CLEAR_DIRTY = questStorage.getMethod("clearDirty");
         HAS_COMPLETED_RACE = questStorage.getMethod("hasCompletedRace", ResourceLocation.class);
      } catch (ReflectiveOperationException | LinkageError var6) {
      }

      try {
         Class<?> openPrestigeScreenPacket = Class.forName("org.crypticdev.stextras.net.C2S.OpenPrestigeScreenPacket");
         REQUEST_PRESTIGE_SCREEN_SYNC = openPrestigeScreenPacket.getMethod("sendToServer");
      } catch (ReflectiveOperationException | LinkageError var5) {
      }

      try {
         Class<?> questSync = Class.forName("org.crypticdev.stextras.net.STExtrasQuestSync");
         SYNC_QUESTS_WITH_RACE_IDS = questSync.getMethod("sendWithRaceIds", ServerPlayer.class, CompoundTag.class);
      } catch (ReflectiveOperationException | LinkageError var4) {
      }

      try {
         Class<?> requiredSelector = Class.forName("org.crypticdev.stextras.quest.assignment.RequiredQuestSelector");
         APPLY_REQUIRED_ON_CHARACTER_RESET = requiredSelector.getMethod("applyRequiredOnCharacterReset", ServerPlayer.class, int.class);
      } catch (ReflectiveOperationException | LinkageError var3) {
      }
   }
}
