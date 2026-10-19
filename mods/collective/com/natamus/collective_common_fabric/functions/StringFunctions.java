package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2558;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;
import net.minecraft.class_2558.class_2559;
import net.minecraft.server.MinecraftServer;

public class StringFunctions {
   public static void sendMessage(class_2168 source, String m, class_124 colour) {
      sendMessage(source, m, colour, false);
   }

   public static void sendMessage(class_1657 player, String m, class_124 colour) {
      sendMessage(player, m, colour, false);
   }

   public static void sendMessage(class_2168 source, String m, class_124 colour, boolean emptyline) {
      sendMessage(source, m, colour, emptyline, "");
   }

   public static void sendMessage(class_1657 player, String m, class_124 colour, boolean emptyline) {
      sendMessage(player, m, colour, emptyline, "");
   }

   public static void sendMessage(class_2168 source, String m, class_124 colour, String url) {
      sendMessage(source, m, colour, false, url);
   }

   public static void sendMessage(class_1657 player, String m, class_124 colour, String url) {
      sendMessage(player, m, colour, false, url);
   }

   public static void sendMessage(class_2168 source, String m, class_124 colour, boolean emptyline, String url) {
      if (!m.isEmpty()) {
         if (emptyline) {
            source.method_9226(() -> class_2561.method_43470(""), false);
         }

         class_5250 message = class_2561.method_43470(m);
         message.method_27692(colour);
         if (m.contains("http") || !url.isEmpty()) {
            if (url.isEmpty()) {
               for (String word : m.split(" ")) {
                  if (word.contains("http")) {
                     url = word;
                     break;
                  }
               }
            }

            if (!url.isEmpty()) {
               class_2583 clickstyle = message.method_10866().method_10958(new class_2558(class_2559.field_11749, url));
               message.method_27696(clickstyle);
            }
         }

         source.method_9226(() -> message, false);
      }
   }

   public static void sendMessage(class_1657 player, String m, class_124 colour, boolean emptyline, String url) {
      if (!m.isEmpty()) {
         if (emptyline) {
            player.method_43496(class_2561.method_43470(""));
         }

         class_5250 message = class_2561.method_43470(m);
         message.method_27692(colour);
         if (m.contains("http") || !url.isEmpty()) {
            if (url.isEmpty()) {
               for (String word : m.split(" ")) {
                  if (word.contains("http")) {
                     url = word;
                     break;
                  }
               }
            }

            if (!url.isEmpty()) {
               class_2583 clickstyle = message.method_10866().method_10958(new class_2558(class_2559.field_11749, url));
               message.method_27696(clickstyle);
            }
         }

         player.method_43496(message);
      }
   }

   public static void broadcastMessage(class_1937 world, String m, class_124 colour) {
      if (!m.isEmpty()) {
         class_5250 message = class_2561.method_43470(m);
         message.method_27692(colour);
         MinecraftServer server = world.method_8503();
         if (server != null) {
            for (class_1657 player : server.method_3760().method_14571()) {
               sendMessage(player, m, colour);
            }
         }
      }
   }

   public static void sendMessageToPlayersAround(class_1937 world, class_2338 p, int radius, String message, class_124 colour) {
      if (!message.isEmpty()) {
         for (class_1297 around : world.method_8335(
            null,
            new class_238(
               p.method_10263() - radius,
               p.method_10264() - radius,
               p.method_10260() - radius,
               p.method_10263() + radius,
               p.method_10264() + radius,
               p.method_10260() + radius
            )
         )) {
            if (around instanceof class_1657) {
               sendMessage((class_1657)around, message, colour);
            }
         }
      }
   }

   public static String capitalizeFirst(String string) {
      StringBuilder sb = new StringBuilder(string);

      for (int i = 0; i < sb.length(); i++) {
         if (i == 0 || sb.charAt(i - 1) == ' ') {
            sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
         }
      }

      return sb.toString();
   }

   public static String capitalizeEveryWord(String text) {
      if (text.isEmpty()) {
         return text;
      }

      char[] chars = text.toLowerCase().toCharArray();
      boolean found = false;

      for (int i = 0; i < chars.length; i++) {
         if (!found && Character.isLetter(chars[i])) {
            chars[i] = Character.toUpperCase(chars[i]);
            found = true;
         } else if (!Character.isDigit(chars[i]) && !Character.isLetter(chars[i])) {
            found = false;
         }
      }

      return String.valueOf(chars);
   }

   public static String escapeSpecialRegexChars(String str) {
      return Pattern.compile("[{}()\\[\\].+*?^$\\\\|]").matcher(str).replaceAll("\\\\$0");
   }

   public static String getRandomName(boolean useFemaleNames, boolean useMaleNames) {
      List<String> allnames;
      if (useFemaleNames && useMaleNames) {
         allnames = Stream.concat(GlobalVariables.femaleNames.stream(), GlobalVariables.maleNames.stream()).collect(Collectors.toList());
      } else if (useFemaleNames) {
         allnames = GlobalVariables.femaleNames;
      } else {
         if (!useMaleNames) {
            return "";
         }

         allnames = GlobalVariables.maleNames;
      }

      String name = allnames.get(GlobalVariables.random.nextInt(allnames.size())).toLowerCase();
      return capitalizeEveryWord(name);
   }

   public static String getPCLocalTime(boolean twentyfour, boolean showseconds) {
      LocalDateTime now = LocalDateTime.now();
      String time;
      if (showseconds) {
         if (twentyfour) {
            time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
         } else {
            time = now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")).replace("&nbsp;", "");
         }
      } else if (twentyfour) {
         time = now.format(DateTimeFormatter.ofPattern("HH:mm"));
      } else {
         time = now.format(DateTimeFormatter.ofPattern("hh:mm a")).replace("&nbsp;", "");
      }

      return time;
   }

   public static int sequenceCount(String text, String sequence) {
      Pattern pattern = Pattern.compile(sequence);
      Matcher matcher = pattern.matcher(text);
      int count = 0;

      while (matcher.find()) {
         count++;
      }

      return count;
   }

   public static String joinListWithCommaAnd(List<String> inputlist) {
      if (inputlist.isEmpty()) {
         return "";
      }

      if (inputlist.size() == 1) {
         return inputlist.getFirst();
      }

      List<String> list = new ArrayList<>(inputlist);
      String lastelement = list.getLast();
      list.removeLast();
      String initial = String.join(", ", list);
      return initial + " and " + lastelement;
   }
}
