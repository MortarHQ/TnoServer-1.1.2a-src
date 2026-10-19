package com.nododiiiii.ponderer.ponder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.createmod.ponder.foundation.PonderScene;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import org.slf4j.Logger;

public final class NbtSceneFilter {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Map<String, CompoundTag> FILTERS = new ConcurrentHashMap<>();
   private static final Map<ResourceLocation, Set<String>> ALL_SCENES = new ConcurrentHashMap<>();
   static final ThreadLocal<ItemStack> CURRENT_STACK = new ThreadLocal<>();

   private NbtSceneFilter() {
   }

   public static void registerScene(ResourceLocation itemId, String sceneId) {
      ALL_SCENES.computeIfAbsent(itemId, k -> ConcurrentHashMap.newKeySet()).add(sceneId);
   }

   public static void registerFilter(String sceneId, CompoundTag nbtFilter) {
      FILTERS.put(sceneId, nbtFilter);
   }

   public static void clear() {
      FILTERS.clear();
      ALL_SCENES.clear();
   }

   public static void setCurrentStack(ItemStack stack) {
      CURRENT_STACK.set(stack);
   }

   @Nullable
   public static ItemStack getCurrentStack() {
      return CURRENT_STACK.get();
   }

   public static void clearCurrentStack() {
      CURRENT_STACK.remove();
   }

   public static boolean hasFilters(ResourceLocation itemId) {
      Set<String> sceneIds = ALL_SCENES.get(itemId);
      if (sceneIds != null && !sceneIds.isEmpty()) {
         for (String sid : sceneIds) {
            if (FILTERS.containsKey(sid)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean hasVisibleScenes(ItemStack stack, ResourceLocation itemId) {
      Set<String> sceneIds = ALL_SCENES.get(itemId);
      if (sceneIds != null && !sceneIds.isEmpty()) {
         for (String sid : sceneIds) {
            CompoundTag filter = FILTERS.get(sid);
            if (filter == null) {
               return true;
            }

            if (matchesNbt(stack, filter)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public static List<PonderScene> filter(ItemStack stack, List<PonderScene> scenes) {
      if (FILTERS.isEmpty()) {
         return scenes;
      }

      List<PonderScene> filtered = new ArrayList<>();
      boolean anyFiltered = false;

      for (PonderScene scene : scenes) {
         String sceneId = scene.getId().toString();
         CompoundTag filter = FILTERS.get(sceneId);
         if (filter == null) {
            filtered.add(scene);
         } else {
            anyFiltered = true;
            if (matchesNbt(stack, filter)) {
               filtered.add(scene);
            }
         }
      }

      if (!anyFiltered) {
         return scenes;
      } else {
         return filtered.isEmpty() ? scenes : filtered;
      }
   }

   public static boolean matchesNbt(ItemStack stack, CompoundTag filter) {
      if (filter != null && !filter.isEmpty()) {
         try {
            if (stack.is(Items.WRITTEN_BOOK)) {
               String requiredTitle = extractWrittenBookTitle(filter);
               if (requiredTitle != null && !requiredTitle.isBlank()) {
                  String actualTitle = extractWrittenBookTitle(stack);
                  return actualTitle != null && requiredTitle.equals(actualTitle);
               }
            }

            CompoundTag customTag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
            if (!customTag.isEmpty() && isSubset(filter, customTag)) {
               return true;
            }

            if (filter.contains("tag", 10)) {
               CompoundTag legacyTag = filter.getCompound("tag");
               if (!legacyTag.isEmpty() && isSubset(legacyTag, customTag)) {
                  return true;
               }
            }

            RegistryAccess registryAccess = getRegistryAccess();
            if (registryAccess == null) {
               return false;
            }

            if (stack.saveOptional(registryAccess) instanceof CompoundTag stackTag) {
               if (isSubsetIgnoringCount(filter, stackTag)) {
                  return true;
               } else {
                  return filter.contains("components", 10)
                        && stackTag.contains("components", 10)
                        && isSubset(filter.getCompound("components"), stackTag.getCompound("components"))
                     ? true
                     : filter.contains("tag", 10) && stackTag.contains("tag", 10) && isSubset(filter.getCompound("tag"), stackTag.getCompound("tag"));
               }
            } else {
               return false;
            }
         } catch (Exception e) {
            LOGGER.debug("NBT match check failed", e);
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   public static String extractWrittenBookTitleFromFilterSnbt(@Nullable String snbt) {
      CompoundTag parsed = parseNbt(snbt);
      return parsed == null ? null : extractWrittenBookTitle(parsed);
   }

   @Nullable
   private static String extractWrittenBookTitle(ItemStack stack) {
      CompoundTag customTag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      String fromCustom = extractWrittenBookTitle(customTag);
      if (fromCustom != null && !fromCustom.isBlank()) {
         return fromCustom;
      } else {
         RegistryAccess registryAccess = getRegistryAccess();
         if (registryAccess == null) {
            return null;
         } else {
            return stack.saveOptional(registryAccess) instanceof CompoundTag ct ? extractWrittenBookTitle(ct) : null;
         }
      }
   }

   @Nullable
   private static String extractWrittenBookTitle(@Nullable CompoundTag tag) {
      if (tag != null && !tag.isEmpty()) {
         if (tag.contains("title")) {
            String parsed = parseBookTitleTag(tag.get("title"));
            if (parsed != null && !parsed.isBlank()) {
               return parsed;
            }
         }

         if (tag.contains("tag", 10)) {
            String nested = extractWrittenBookTitle(tag.getCompound("tag"));
            if (nested != null && !nested.isBlank()) {
               return nested;
            }
         }

         if (tag.contains("components", 10)) {
            CompoundTag components = tag.getCompound("components");
            if (components.contains("minecraft:written_book_content", 10)) {
               CompoundTag content = components.getCompound("minecraft:written_book_content");
               if (content.contains("title")) {
                  String parsed = parseBookTitleTag(content.get("title"));
                  if (parsed != null && !parsed.isBlank()) {
                     return parsed;
                  }
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Nullable
   private static String parseBookTitleTag(@Nullable Tag titleTag) {
      if (titleTag == null) {
         return null;
      }

      if (titleTag instanceof CompoundTag compound) {
         if (compound.contains("raw", 8)) {
            return normalizeTitleString(compound.getString("raw"));
         } else if (compound.contains("text", 8)) {
            return normalizeTitleString(compound.getString("text"));
         } else {
            return compound.contains("translate", 8) ? normalizeTitleString(compound.getString("translate")) : normalizeTitleString(compound.toString());
         }
      } else {
         return normalizeTitleString(titleTag.getAsString());
      }
   }

   private static String normalizeTitleString(String raw) {
      if (raw == null) {
         return null;
      }

      String s = raw.trim();
      if (s.isEmpty()) {
         return s;
      }

      if (s.startsWith("{") || s.startsWith("[")) {
         try {
            JsonElement el = JsonParser.parseString(s);
            String fromJson = extractTextFromJsonComponent(el);
            if (fromJson != null && !fromJson.isBlank()) {
               return fromJson;
            }
         } catch (JsonSyntaxException var4) {
         }
      }

      return s;
   }

   @Nullable
   private static String extractTextFromJsonComponent(@Nullable JsonElement element) {
      if (element == null || element.isJsonNull()) {
         return null;
      }

      if (element.isJsonPrimitive()) {
         try {
            return element.getAsString();
         } catch (Exception ignored) {
            return null;
         }
      } else {
         if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("text") && obj.get("text").isJsonPrimitive()) {
               return obj.get("text").getAsString();
            }

            if (obj.has("raw") && obj.get("raw").isJsonPrimitive()) {
               return obj.get("raw").getAsString();
            }

            if (obj.has("translate") && obj.get("translate").isJsonPrimitive()) {
               return obj.get("translate").getAsString();
            }

            if (obj.has("extra") && obj.get("extra").isJsonArray()) {
               StringBuilder sb = new StringBuilder();

               for (JsonElement child : obj.getAsJsonArray("extra")) {
                  String part = extractTextFromJsonComponent(child);
                  if (part != null) {
                     sb.append(part);
                  }
               }

               String joined = sb.toString();
               return joined.isBlank() ? null : joined;
            }
         }

         if (element.isJsonArray()) {
            StringBuilder sb = new StringBuilder();

            for (JsonElement child : element.getAsJsonArray()) {
               String part = extractTextFromJsonComponent(child);
               if (part != null) {
                  sb.append(part);
               }
            }

            String joined = sb.toString();
            return joined.isBlank() ? null : joined;
         } else {
            return null;
         }
      }
   }

   private static boolean isSubset(CompoundTag subset, CompoundTag superset) {
      for (String key : subset.getAllKeys()) {
         Tag subVal = subset.get(key);
         Tag superVal = superset.get(key);
         if (superVal == null) {
            return false;
         }

         if (subVal instanceof CompoundTag subCompound && superVal instanceof CompoundTag superCompound) {
            if (!isSubset(subCompound, superCompound)) {
               return false;
            }
         } else if (!subVal.equals(superVal)) {
            return false;
         }
      }

      return true;
   }

   private static boolean isSubsetIgnoringCount(CompoundTag subset, CompoundTag superset) {
      for (String key : subset.getAllKeys()) {
         if (!"Count".equals(key) && !"count".equals(key)) {
            Tag subVal = subset.get(key);
            Tag superVal = superset.get(key);
            if (superVal == null) {
               return false;
            }

            if (subVal instanceof CompoundTag subCompound && superVal instanceof CompoundTag superCompound) {
               if (!isSubsetIgnoringCount(subCompound, superCompound)) {
                  return false;
               }
            } else if (!subVal.equals(superVal)) {
               return false;
            }
         }
      }

      return true;
   }

   @Nullable
   public static CompoundTag parseNbt(String snbt) {
      if (snbt != null && !snbt.isBlank()) {
         try {
            return TagParser.parseTag(snbt);
         } catch (Exception e) {
            LOGGER.warn("Failed to parse NBT filter: {}", snbt, e);
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   private static RegistryAccess getRegistryAccess() {
      Minecraft mc = Minecraft.getInstance();
      return mc.level != null ? mc.level.registryAccess() : null;
   }
}
