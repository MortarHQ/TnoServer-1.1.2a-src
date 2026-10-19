package net.darkhax.bookshelf.common.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.ref.WeakReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
   public static final String MOD_ID = "bookshelf";
   public static final String MOD_NAME = "Bookshelf";
   public static final Logger LOG = LoggerFactory.getLogger("Bookshelf");
   public static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();
   public static WeakReference<RecipeManager> SERVER_RECIPE_MANAGER;
   public static int SERVER_REVISION = 0;
   public static WeakReference<RecipeManager> CLIENT_RECIPE_MANAGER;
   public static int CLIENT_REVISION = 0;

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("bookshelf", path);
   }
}
