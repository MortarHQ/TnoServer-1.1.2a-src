package net.witherspawnanimation;

import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TomlParser {
   private static final Pattern INVULN_PATTERN = Pattern.compile("(?mi)\\binvulnerabilityTime\\b\\s*=\\s*([+-]?[0-9][0-9_]*)");

   public static OptionalLong extractInvulnerabilityTime(String tomlContents) {
      if (tomlContents == null) {
         return OptionalLong.empty();
      }

      Matcher m = INVULN_PATTERN.matcher(tomlContents);
      if (!m.find()) {
         return OptionalLong.empty();
      }

      String rawNumber = m.group(1);
      String normalized = rawNumber.replace("_", "");

      try {
         long value = Long.parseLong(normalized);
         return OptionalLong.of(value);
      } catch (NumberFormatException e) {
         return OptionalLong.empty();
      }
   }
}
