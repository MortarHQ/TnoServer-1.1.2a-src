package org.crypticdev.stextras.quest.definition;

import java.util.Locale;
import java.util.Set;

public enum QuestSubordinateMode {
   NONE,
   HELP,
   ONLY;

   private static final Set<QuestObjectiveType> SUPPORTED_CREATORS = Set.of(
      QuestObjectiveType.KILL,
      QuestObjectiveType.SPIRITUAL_DAMAGE,
      QuestObjectiveType.CONSUME,
      QuestObjectiveType.MASTER,
      QuestObjectiveType.MASTER_TYPE,
      QuestObjectiveType.DAMAGE,
      QuestObjectiveType.EP_REACH,
      QuestObjectiveType.EQUIP_EVOLVE
   );

   public static QuestSubordinateMode fromString(String value) {
      if (value != null && !value.isBlank()) {
         try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException ignored) {
            return NONE;
         }
      } else {
         return NONE;
      }
   }

   public static boolean supports(QuestObjectiveType creator) {
      return creator != null && SUPPORTED_CREATORS.contains(creator);
   }
}
