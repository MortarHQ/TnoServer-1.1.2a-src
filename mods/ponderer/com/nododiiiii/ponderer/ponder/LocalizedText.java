package com.nododiiiii.ponderer.ponder;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public final class LocalizedText {
   private String plain;
   private Map<String, String> localized;

   private LocalizedText() {
   }

   public static LocalizedText of(String text) {
      LocalizedText lt = new LocalizedText();
      lt.plain = text != null ? text : "";
      return lt;
   }

   public static LocalizedText ofMap(Map<String, String> map) {
      LocalizedText lt = new LocalizedText();
      lt.localized = new LinkedHashMap<>(map);
      return lt;
   }

   public boolean isPlain() {
      return this.plain != null;
   }

   public Map<String, String> getAllTranslations() {
      if (this.localized != null) {
         return this.localized;
      }

      Map<String, String> m = new LinkedHashMap<>();
      m.put("_plain", this.plain != null ? this.plain : "");
      return m;
   }

   public String resolve() {
      if (this.plain != null) {
         return this.plain;
      }

      if (this.localized != null && !this.localized.isEmpty()) {
         String lang = getCurrentLanguage();
         String val = this.localized.get(lang);
         if (val != null) {
            return val;
         }

         val = this.localized.get("en_us");
         return val != null ? val : this.localized.values().iterator().next();
      } else {
         return "";
      }
   }

   public String resolve(String lang) {
      if (this.plain != null) {
         return this.plain;
      }

      if (this.localized != null && !this.localized.isEmpty()) {
         String val = this.localized.get(lang);
         if (val != null) {
            return val;
         }

         val = this.localized.get("en_us");
         return val != null ? val : this.localized.values().iterator().next();
      } else {
         return "";
      }
   }

   public String getForCurrentLang() {
      return this.resolve();
   }

   public void setForCurrentLang(String text) {
      this.setForLang(getCurrentLanguage(), text);
   }

   public void setForLang(String lang, String text) {
      if (this.plain != null) {
         if ("en_us".equals(lang)) {
            this.plain = text;
         } else {
            this.localized = new LinkedHashMap<>();
            if (!this.plain.isEmpty()) {
               this.localized.put("en_us", this.plain);
            }

            this.localized.put(lang, text);
            this.plain = null;
         }
      } else {
         if (this.localized == null) {
            this.localized = new LinkedHashMap<>();
         }

         this.localized.put(lang, text);
      }
   }

   public String getExact(String lang) {
      if (this.plain != null) {
         return this.plain;
      } else {
         return this.localized == null ? null : this.localized.get(lang);
      }
   }

   public boolean isEmpty() {
      if (this.plain != null) {
         return this.plain.isEmpty();
      } else {
         return this.localized != null && !this.localized.isEmpty() ? this.localized.values().stream().allMatch(v -> v == null || v.isEmpty()) : true;
      }
   }

   @Override
   public String toString() {
      return this.resolve();
   }

   private static String getCurrentLanguage() {
      try {
         return Minecraft.getInstance().getLanguageManager().getSelected();
      } catch (Exception e) {
         return "en_us";
      }
   }

   public static class GsonAdapter extends TypeAdapter<LocalizedText> {
      public void write(JsonWriter out, LocalizedText value) throws IOException {
         if (value == null) {
            out.nullValue();
         } else {
            if (value.plain != null) {
               out.value(value.plain);
            } else if (value.localized != null) {
               out.beginObject();

               for (Entry<String, String> e : value.localized.entrySet()) {
                  out.name(e.getKey()).value(e.getValue());
               }

               out.endObject();
            } else {
               out.value("");
            }
         }
      }

      public LocalizedText read(JsonReader in) throws IOException {
         JsonToken token = in.peek();
         if (token == JsonToken.NULL) {
            in.nextNull();
            return null;
         }

         if (token == JsonToken.STRING) {
            return LocalizedText.of(in.nextString());
         }

         if (token != JsonToken.BEGIN_OBJECT) {
            in.skipValue();
            return LocalizedText.of("");
         }

         Map<String, String> map = new LinkedHashMap<>();
         in.beginObject();

         while (in.hasNext()) {
            String key = in.nextName();
            JsonToken valueToken = in.peek();
            if (valueToken == JsonToken.STRING) {
               map.put(key, in.nextString());
            } else if ("localized".equals(key) && valueToken == JsonToken.BEGIN_OBJECT) {
               in.beginObject();

               while (in.hasNext()) {
                  String lang = in.nextName();
                  JsonToken nested = in.peek();
                  if (nested == JsonToken.STRING) {
                     map.put(lang, in.nextString());
                  } else {
                     in.skipValue();
                  }
               }

               in.endObject();
            } else {
               in.skipValue();
            }
         }

         in.endObject();
         return LocalizedText.ofMap(map);
      }
   }
}
