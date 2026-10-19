package org.popcraft.chunky.util;

import java.util.Optional;
import org.popcraft.chunky.Chunky;
import org.popcraft.chunky.iterator.PatternType;
import org.popcraft.chunky.platform.World;
import org.popcraft.chunky.shape.ShapeType;

public final class Input {
   private Input() {
   }

   public static Optional<World> tryWorld(Chunky chunky, String input) {
      return input != null && !input.isEmpty() ? chunky.getServer().getWorld(input) : Optional.empty();
   }

   public static Optional<String> tryPattern(String input) {
      if (input != null && !input.isEmpty()) {
         String inputLower = input.toLowerCase();
         return PatternType.ALL.contains(inputLower) ? Optional.of(inputLower) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   public static Optional<String> tryShape(String input) {
      if (input != null && !input.isEmpty()) {
         String inputLower = input.toLowerCase();
         return ShapeType.all().contains(inputLower) ? Optional.of(inputLower) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Boolean> tryBoolean(String input) {
      return input != null && !input.isEmpty() ? Optional.of(Boolean.parseBoolean(input)) : Optional.empty();
   }

   public static Optional<Integer> tryInteger(String input) {
      if (input != null && !input.isEmpty()) {
         try {
            return Optional.of(Integer.parseInt(input));
         } catch (NumberFormatException e) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Integer> tryIntegerSuffixed(String input) {
      if (input != null && !input.isEmpty()) {
         int last = input.length() - 1;
         return suffixValue(input.charAt(last)).map(suffixValue -> tryInteger(input.substring(0, last)).map(i -> i * suffixValue)).orElse(tryInteger(input));
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Double> tryDouble(String input) {
      if (input != null && !input.isEmpty()) {
         try {
            return Optional.of(Double.parseDouble(input));
         } catch (NumberFormatException e) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Double> tryDoubleSuffixed(String input) {
      if (input != null && !input.isEmpty()) {
         int last = input.length() - 1;
         return suffixValue(input.charAt(last))
            .map(suffixValue -> tryDouble(input.substring(0, last)).map(d -> d * suffixValue.intValue()))
            .orElse(tryDouble(input));
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Long> tryLong(String input) {
      if (input != null && !input.isEmpty()) {
         try {
            return Optional.of(Long.parseLong(input));
         } catch (NumberFormatException e) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public static Optional<Integer> trySign(String input) {
      if (input != null && !input.isEmpty()) {
         char sign = input.charAt(0);

         return switch (sign) {
            case '+' -> Optional.of(1);
            case '-' -> Optional.of(-1);
            default -> Optional.empty();
         };
      } else {
         return Optional.empty();
      }
   }

   public static boolean isPastWorldLimit(double value) {
      return Math.abs(value) > 3.0E7;
   }

   public static String checkLanguage(String language) {
      return Translator.isValidLanguage(language) ? language : "en";
   }

   private static Optional<Integer> suffixValue(char suffix) {
      return switch (Character.toLowerCase(suffix)) {
         case 'c' -> Optional.of(16);
         case 'k' -> Optional.of(1000);
         case 'r' -> Optional.of(512);
         default -> Optional.empty();
      };
   }
}
