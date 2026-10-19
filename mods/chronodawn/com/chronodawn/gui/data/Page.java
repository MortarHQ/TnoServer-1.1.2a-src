package com.chronodawn.gui.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class Page {
   private final Page.PageType type;
   private final LocalizedText text;
   private final ResourceLocation recipe;
   private final ResourceLocation image;

   public Page(JsonObject json) {
      if (json.has("text")) {
         this.type = Page.PageType.TEXT;
         this.text = new LocalizedText(json.getAsJsonObject("text"));
         this.recipe = null;
         this.image = null;
      } else if (json.has("recipe")) {
         this.type = Page.PageType.RECIPE;
         this.text = null;
         this.recipe = ResourceLocation.parse(json.get("recipe").getAsString());
         this.image = null;
      } else if (json.has("image")) {
         this.type = Page.PageType.IMAGE;
         this.text = null;
         this.recipe = null;
         this.image = ResourceLocation.parse(json.get("image").getAsString());
      } else {
         this.type = Page.PageType.TEXT;
         this.text = new LocalizedText("");
         this.recipe = null;
         this.image = null;
      }
   }

   public Page.PageType getType() {
      return this.type;
   }

   public LocalizedText getText() {
      return this.text;
   }

   public ResourceLocation getRecipe() {
      return this.recipe;
   }

   public ResourceLocation getImage() {
      return this.image;
   }

   public enum PageType {
      TEXT,
      RECIPE,
      IMAGE;
   }
}
