package com.tensura_tno.client.localization;

import com.tensura_tno.compat.moreskills.MoreSkillsHardcodedLocalization;
import java.util.List;
import net.minecraft.locale.Language;

public final class MoreSkillsHardcodedClientLocalization {
   private MoreSkillsHardcodedClientLocalization() {
   }

   public static String localize(String original) {
      if (MoreSkillsHardcodedLocalization.hasCandidate(original) && MoreSkillsHardcodedLocalization.isCalledFromMoreSkills()) {
         Language language = Language.getInstance();
         String exactKey = MoreSkillsHardcodedLocalization.findExactKey(original);
         if (exactKey != null) {
            return language.getOrDefault(exactKey, original);
         }

         List<MoreSkillsHardcodedLocalization.Segment> segments = MoreSkillsHardcodedLocalization.split(original);
         if (segments == null) {
            return original;
         }

         StringBuilder result = new StringBuilder(original.length());

         for (MoreSkillsHardcodedLocalization.Segment segment : segments) {
            result.append(segment.translationKey() == null ? segment.text() : language.getOrDefault(segment.translationKey(), segment.text()));
         }

         return result.toString();
      } else {
         return original;
      }
   }
}
