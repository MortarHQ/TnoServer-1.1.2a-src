package dev.ftbqlang.server;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class LanguagePolicy {
   public static final int MAX_LOCALE_LENGTH = 64;
   public static final int MAX_LANGUAGES = 512;
   private static final Pattern LOCALE = Pattern.compile("[a-zA-Z0-9_]{1,64}");

   private LanguagePolicy() {
   }

   public static String normalize(String locale) {
      return locale == null ? "" : locale.toLowerCase(Locale.ROOT);
   }

   public static boolean isValidLocale(String locale) {
      return locale != null && LOCALE.matcher(locale).matches();
   }

   public static String localeFromFilename(String filename) {
      if (!filename.endsWith(".snbt")) {
         return "";
      }

      String locale = filename.substring(0, filename.length() - 5);
      return isValidLocale(locale) ? normalize(locale) : "";
   }

   public static <T> void initializeMissingTables(Map<String, T> liveTables, Map<String, T> diskTables) {
      diskTables.forEach(liveTables::putIfAbsent);
   }

   public static LanguagePolicy.Decision decide(List<String> available, LanguagePolicy.Preference previous, String clientLocale, boolean bookOpened) {
      boolean prompt = bookOpened && available.size() > 1 && !previous.prompted();
      String selected = "";
      if (available.size() == 1) {
         selected = available.getFirst();
      } else if (!available.isEmpty()) {
         String saved = normalize(previous.selected());
         String client = normalize(clientLocale);
         selected = available.contains(saved) ? saved : (available.contains(client) ? client : (available.contains("en_us") ? "en_us" : available.getFirst()));
      }

      return new LanguagePolicy.Decision(selected, prompt, previous.prompted() || prompt);
   }

   public record Decision(String selected, boolean prompt, boolean prompted) {
   }

   public record Preference(String selected, boolean prompted) {
      public static final LanguagePolicy.Preference UNSET = new LanguagePolicy.Preference("", false);
   }
}
