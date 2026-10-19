package com.tensura_guild.compat;

import com.tensura_guild.TensuraGuild;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

public final class GuildCompatHooks {
   private static final String FTBQUESTS_MOD_ID = "ftbquests";
   private static final String FTBQUESTS_TASK_TYPES = "com.tensura_guild.compat.ftbquests.GuildFtbQuestTaskTypes";
   private static final String FTBQUESTS_PROGRESS = "com.tensura_guild.compat.ftbquests.GuildFtbQuestProgress";
   private static boolean ftbQuestsInitialized;
   private static boolean ftbQuestsFailed;

   private GuildCompatHooks() {
   }

   public static void init() {
      if (ModList.get().isLoaded("ftbquests") && !ftbQuestsFailed) {
         try {
            Class.forName("com.tensura_guild.compat.ftbquests.GuildFtbQuestTaskTypes").getMethod("init").invoke(null);
            ftbQuestsInitialized = true;
         } catch (ReflectiveOperationException | LinkageError exception) {
            ftbQuestsFailed = true;
            TensuraGuild.LOGGER.error("Failed to initialize FTB Quests compatibility", unwrap(exception));
         }
      }
   }

   public static void onDwarfTaskCompleted(ServerPlayer player) {
      invokeProgress("onDwarfTaskCompleted", new Class[]{ServerPlayer.class}, player);
   }

   public static void onGuildTaskCompleted(ServerPlayer player, String rank) {
      invokeProgress("onGuildTaskCompleted", new Class[]{ServerPlayer.class, String.class}, player, rank);
   }

   public static void onJoinedGuild(ServerPlayer player) {
      invokeProgress("onJoinedGuild", new Class[]{ServerPlayer.class}, player);
   }

   public static void onAdventureExperienceChanged(ServerPlayer player) {
      invokeProgress("onAdventureExperienceChanged", new Class[]{ServerPlayer.class}, player);
   }

   private static void invokeProgress(String methodName, Class<?>[] parameterTypes, Object... args) {
      if (ftbQuestsInitialized && !ftbQuestsFailed && ModList.get().isLoaded("ftbquests")) {
         try {
            Class.forName("com.tensura_guild.compat.ftbquests.GuildFtbQuestProgress").getMethod(methodName, parameterTypes).invoke(null, args);
         } catch (ReflectiveOperationException | LinkageError exception) {
            ftbQuestsFailed = true;
            TensuraGuild.LOGGER.error("Failed to submit FTB Quests progress for {}", methodName, unwrap(exception));
         }
      }
   }

   private static Throwable unwrap(Throwable throwable) {
      return throwable instanceof InvocationTargetException invocationTargetException && invocationTargetException.getCause() != null
         ? invocationTargetException.getCause()
         : throwable;
   }
}
