package com.chronodawn.gui.data;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class Category {
   private final String id;
   private final LocalizedText title;
   private final LocalizedText description;
   private final ResourceLocation icon;
   private final int sortnum;
   private final List<Entry> entries;

   public Category(String id, JsonObject json) {
      this.id = id;
      this.title = new LocalizedText(json.getAsJsonObject("title"));
      this.description = json.has("description") ? new LocalizedText(json.getAsJsonObject("description")) : new LocalizedText("");
      this.icon = ResourceLocation.parse(json.get("icon").getAsString());
      this.sortnum = json.has("sortnum") ? json.get("sortnum").getAsInt() : 0;
      this.entries = new ArrayList<>();
   }

   public String getId() {
      return this.id;
   }

   public LocalizedText getTitle() {
      return this.title;
   }

   public LocalizedText getDescription() {
      return this.description;
   }

   public ResourceLocation getIcon() {
      return this.icon;
   }

   public int getSortnum() {
      return this.sortnum;
   }

   public List<Entry> getEntries() {
      return this.entries;
   }

   public void addEntry(Entry entry) {
      this.entries.add(entry);
      this.entries.sort((a, b) -> Integer.compare(a.getSortnum(), b.getSortnum()));
   }
}
