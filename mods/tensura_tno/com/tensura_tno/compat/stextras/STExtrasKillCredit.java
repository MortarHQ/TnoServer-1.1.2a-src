package com.tensura_tno.compat.stextras;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Method;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class STExtrasKillCredit {
   private static boolean initialized = false;
   private static boolean available = false;
   private static Method incrementMatchingMethod;
   private static Object killType;

   private STExtrasKillCredit() {
   }

   private static synchronized void init() {
      if (!initialized) {
         initialized = true;

         try {
            Class<?> listenerClass = Class.forName("org.crypticdev.stextras.handler.HandlePrestigeListeners");

            try {
               listenerClass.getDeclaredMethod("questProgressSource", LivingEntity.class);
               return;
            } catch (NoSuchMethodException var2) {
               Class<?> questObjTypeClass = Class.forName("org.crypticdev.stextras.quest.definition.QuestObjectiveType");
               killType = Enum.valueOf(questObjTypeClass, "KILL");
               incrementMatchingMethod = listenerClass.getDeclaredMethod(
                  "incrementMatching", ServerPlayer.class, questObjTypeClass, ResourceLocation.class, int.class, LivingEntity.class
               );
               incrementMatchingMethod.setAccessible(true);
               available = true;
               TensuraTNOMod.LOGGER.info("[TensuraTNO] STExtras kill credit bridge initialized");
            }
         } catch (Exception e) {
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] STExtras kill credit unavailable: {}", e.getMessage());
            available = false;
         }
      }
   }

   public static void creditKill(ServerPlayer owner, ResourceLocation entityId, LivingEntity killed) {
      init();
      if (available) {
         try {
            incrementMatchingMethod.invoke(null, owner, killType, entityId, 1, killed);
         } catch (Exception e) {
            TensuraTNOMod.LOGGER.error("[TensuraTNO] Failed to credit summon kill to STExtras: {}", e.getMessage());
         }
      }
   }
}
