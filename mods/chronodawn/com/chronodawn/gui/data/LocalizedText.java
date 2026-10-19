package com.chronodawn.gui.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class LocalizedText {
   private final Map<String, String> translations = new HashMap<>();
   private final String defaultText;

   public LocalizedText(JsonObject json) {
      for (java.util.Map.Entry<String, JsonElement> entry : json.entrySet()) {
         String lang = entry.getKey();
         String text = entry.getValue().getAsString();
         this.translations.put(lang, text);
      }

      this.defaultText = this.translations.getOrDefault("en_us", this.translations.values().isEmpty() ? "" : this.translations.values().iterator().next());
   }

   public LocalizedText(String text) {
      this.translations.put("en_us", text);
      this.defaultText = text;
   }

   public String get(String languageCode) {
      return this.translations.getOrDefault(languageCode, this.defaultText);
   }

   public String getDefault() {
      return this.defaultText;
   }
}
