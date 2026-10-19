package com.sjyueluo.tensura_skill_books.util;

import java.util.Locale;
import net.minecraft.resources.ResourceLocation;

public final class SkillNameUtil {
   private SkillNameUtil() {
   }

   public static String itemNameForSkill(ResourceLocation skillId) {
      return "skill_" + sanitize(skillId.getNamespace()) + "_" + sanitize(skillId.getPath());
   }

   public static String prettyTitleFromPath(String path) {
      String[] parts = path.replace('-', '_').split("_");
      StringBuilder sb = new StringBuilder();

      for (String part : parts) {
         if (!part.isBlank()) {
            if (sb.length() > 0) {
               sb.append(' ');
            }

            sb.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
               sb.append(part.substring(1).toLowerCase(Locale.ROOT));
            }
         }
      }

      return sb.toString();
   }

   private static String sanitize(String s) {
      return s.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_\\-\\.]", "_");
   }
}
