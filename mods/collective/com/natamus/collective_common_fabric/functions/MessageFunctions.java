package com.natamus.collective_common_fabric.functions;

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

public class MessageFunctions {
   public static void sendMessage(class_2168 source, class_5250 message) {
      sendMessage(source, message, false);
   }

   public static void sendMessage(class_1657 player, class_5250 message) {
      sendMessage(player, message, false);
   }

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

         sendMessage(source, message, emptyline);
      }
   }

   public static void sendMessage(class_2168 source, class_5250 message, boolean emptyline) {
      if (emptyline) {
         source.method_9226(() -> class_2561.method_43470(""), false);
      }

      source.method_9226(() -> message, false);
   }

   public static void sendMessage(class_1657 player, String m, class_124 colour, boolean emptyline, String url) {
      if (!m.isEmpty()) {
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

         sendMessage(player, message, emptyline);
      }
   }

   public static void sendMessage(class_1657 player, class_5250 message, boolean emptyline) {
      if (emptyline) {
         player.method_43496(class_2561.method_43470(""));
      }

      player.method_43496(message);
   }

   public static void broadcastMessage(class_1937 world, String m, class_124 colour) {
      if (!m.isEmpty()) {
         class_5250 message = class_2561.method_43470(m);
         message.method_27692(colour);
         broadcastMessage(world, message);
      }
   }

   public static void broadcastMessage(class_1937 world, class_5250 message) {
      MinecraftServer server = world.method_8503();
      if (server != null) {
         for (class_1657 player : server.method_3760().method_14571()) {
            sendMessage(player, message);
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

   public static void sendMessageToPlayersAround(class_1937 world, class_2338 p, int radius, class_5250 message) {
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
            sendMessage((class_1657)around, message);
         }
      }
   }
}
