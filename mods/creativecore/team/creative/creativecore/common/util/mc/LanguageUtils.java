package team.creative.creativecore.common.util.mc;

import java.util.IllegalFormatException;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.locale.Language;
import team.creative.creativecore.CreativeCore;

public class LanguageUtils {
   public static boolean can(String name) {
      return I18n.exists(name);
   }

   public static String translate(String name) {
      return CreativeCore.loader().getOverallSide().isClient() ? I18n.get(name, new Object[0]) : Language.getInstance().getOrDefault(name);
   }

   public static String translateOr(String name, String defaultString) {
      String result = translate(name);
      return name.equals(result) ? defaultString : result;
   }

   public static String translate(String name, Object... args) {
      if (CreativeCore.loader().getOverallSide().isClient()) {
         return I18n.get(name, args);
      }

      try {
         return String.format(Language.getInstance().getOrDefault(name), args);
      } catch (IllegalFormatException illegalformatexception) {
         return "Format error: " + name;
      }
   }
}
