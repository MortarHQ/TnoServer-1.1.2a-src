package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

abstract class ItemMatcherFactory {
   private final String typeName;

   public ItemMatcherFactory(String typeName) {
      this.typeName = typeName;
   }

   public boolean appliesTo(JsonElement jsonElement) {
      return jsonElement.isJsonObject() && GsonHelper.getAsString(jsonElement.getAsJsonObject(), "type").equals(this.typeName);
   }

   public Optional<Predicate<ItemStack>> getPredicate(JsonElement jsonElement) {
      return this.getPredicateFromObject(jsonElement.getAsJsonObject());
   }

   protected abstract Optional<Predicate<ItemStack>> getPredicateFromObject(JsonObject var1);
}
