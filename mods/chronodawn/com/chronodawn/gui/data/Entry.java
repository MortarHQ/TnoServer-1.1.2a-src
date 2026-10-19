package com.chronodawn.gui.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class Entry {
   private final String id;
   private final String category;
   private final LocalizedText title;
   private final ResourceLocation icon;
   private final List<Page> pages;
   private final int sortnum;

   public Entry(String id, JsonObject json) {
      this.id = id;
      this.category = json.get("category").getAsString();
      this.title = new LocalizedText(json.getAsJsonObject("title"));
      this.icon = ResourceLocation.parse(json.get("icon").getAsString());
      this.sortnum = json.has("sortnum") ? json.get("sortnum").getAsInt() : 0;
      this.pages = new ArrayList<>();
      if (json.has("pages")) {
         for (JsonElement pageElement : json.getAsJsonArray("pages")) {
            this.pages.add(new Page(pageElement.getAsJsonObject()));
         }
      }
   }

   public String getId() {
      return this.id;
   }

   public String getCategory() {
      return this.category;
   }

   public LocalizedText getTitle() {
      return this.title;
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }

   public List<Page> getPages() {
      return this.pages;
   }

   public int getSortnum() {
      return this.sortnum;
   }
}
