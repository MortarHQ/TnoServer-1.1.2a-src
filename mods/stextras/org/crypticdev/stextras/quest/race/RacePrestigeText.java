package org.crypticdev.stextras.quest.race;

import com.google.gson.JsonObject;

public class RacePrestigeText {
   public final String title;
   public final String subtitle;
   public final String chat;

   public RacePrestigeText(String title, String subtitle, String chat) {
      this.title = title == null ? "" : title;
      this.subtitle = subtitle == null ? "" : subtitle;
      this.chat = chat == null ? "" : chat;
   }

   public static RacePrestigeText fromJson(JsonObject object) {
      if (object == null) {
         return new RacePrestigeText("", "", "");
      }

      String title = object.has("title") ? object.get("title").getAsString() : "";
      String subtitle = object.has("subtitle") ? object.get("subtitle").getAsString() : "";
      String chat = object.has("chat") ? object.get("chat").getAsString() : "";
      return new RacePrestigeText(title, subtitle, chat);
   }
}
