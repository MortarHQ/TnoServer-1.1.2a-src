package com.tensura_tno.compat.stextras;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;

public final class FoxBossQuestCompat {
   private static final String QUEST_ID = "stextras:race_tensura_tno_heavenly_fox_sovereign_0";
   private static Field questId;
   private static Method contextTarget;
   private static boolean warned;

   public static boolean matches(Object quest, Object context) {
      if (quest == null) {
         return true;
      }

      try {
         if (questId == null) {
            questId = quest.getClass().getField("id");
         }

         if (!"stextras:race_tensura_tno_heavenly_fox_sovereign_0".equals(String.valueOf(questId.get(quest)))) {
            return true;
         }

         if (contextTarget == null) {
            contextTarget = context.getClass().getDeclaredMethod("target");
            contextTarget.setAccessible(true);
         }

         return contextTarget.invoke(context) instanceof Mob mob && mob.getMaxHealth() >= 100.0F && (mob instanceof Enemy || mob.getTarget() != null);
      } catch (ReflectiveOperationException | LinkageError e) {
         if (!warned) {
            warned = true;
            TensuraTNOMod.LOGGER.warn("[TNO] Cannot validate the fox subordinate boss quest", e);
         }

         return false;
      }
   }

   private FoxBossQuestCompat() {
   }
}
