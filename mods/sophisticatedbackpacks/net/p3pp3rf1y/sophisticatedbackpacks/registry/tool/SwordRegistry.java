package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.fml.ModList;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.IRegistryDataLoader;

public class SwordRegistry {
   private static final Set<Item> SWORD_ITEMS = new HashSet<>();
   private static final Map<String, Set<Predicate<ItemStack>>> MOD_SWORD_MATCHERS = new HashMap<>();
   private static final Set<Predicate<ItemStack>> SWORD_MATCHERS = new HashSet<>();

   private SwordRegistry() {
   }

   public static boolean isSword(ItemStack stack) {
      if (SWORD_ITEMS.contains(stack.getItem())) {
         return true;
      }

      if (stack.getItem() instanceof SwordItem) {
         SWORD_ITEMS.add(stack.getItem());
         return true;
      }

      ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(stack.getItem());
      if (registryName == BuiltInRegistries.ITEM.getDefaultKey()) {
         return false;
      }

      for (Predicate<ItemStack> swordMatcher : SWORD_MATCHERS) {
         if (swordMatcher.test(stack)) {
            return true;
         }
      }

      String modId = registryName.getNamespace();
      if (!MOD_SWORD_MATCHERS.containsKey(modId)) {
         return false;
      }

      for (Predicate<ItemStack> matcher : MOD_SWORD_MATCHERS.get(modId)) {
         if (matcher.test(stack)) {
            return true;
         }
      }

      return false;
   }

   public static class SwordsLoader implements IRegistryDataLoader {
      @Override
      public String getName() {
         return "swords";
      }

      @Override
      public void parse(JsonObject json, @Nullable String modId) {
         for (JsonElement jsonElement : json.getAsJsonArray("swords")) {
            if (jsonElement.isJsonPrimitive()) {
               this.parseSword(jsonElement.getAsString());
            } else {
               this.parseSwordMatcher(modId, jsonElement);
            }
         }
      }

      private void parseSwordMatcher(@Nullable String modId, JsonElement jsonElement) {
         Matchers.getItemMatcher(jsonElement).ifPresent(swordMatcher -> {
            if (modId != null) {
               SwordRegistry.MOD_SWORD_MATCHERS.computeIfAbsent(modId, m -> new HashSet<>()).add((Predicate<ItemStack>)swordMatcher);
            } else {
               SwordRegistry.SWORD_MATCHERS.add((Predicate<ItemStack>)swordMatcher);
            }
         });
      }

      private void parseSword(String swordName) {
         BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(swordName)).ifPresentOrElse(SwordRegistry.SWORD_ITEMS::add, () -> {
            String modId = swordName.split(":")[0];
            if (!ModList.get().isLoaded(modId)) {
               SophisticatedBackpacks.LOGGER.debug("Mod {} isn't loaded skipping load of sword {}", modId, swordName);
            } else {
               SophisticatedBackpacks.LOGGER.warn("Mod {} is loaded and yet sword {} doesn't exist in registry, skipping its load", modId, swordName);
            }
         });
      }

      @Override
      public void clear() {
         SwordRegistry.SWORD_ITEMS.clear();
         SwordRegistry.SWORD_MATCHERS.clear();
         SwordRegistry.MOD_SWORD_MATCHERS.clear();
      }
   }
}
