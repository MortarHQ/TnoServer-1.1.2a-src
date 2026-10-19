package com.tensura_tno.food;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tensura_tno.TensuraTNOMod;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLPaths;
import org.jetbrains.annotations.Nullable;

public final class FoodEPManager {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final int DEFAULT_EP_PER_NUTRITION = 5;
   private static final Map<String, FoodEPConfig> CACHE = new ConcurrentHashMap<>();
   private static final Set<String> NO_CONFIG = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static Path configDir;

   private FoodEPManager() {
   }

   public static void init() {
      configDir = FMLPaths.CONFIGDIR.get().resolve("tno");

      try {
         Files.createDirectories(configDir);
      } catch (IOException e) {
         TensuraTNOMod.LOGGER.error("[FoodEP] Failed to create config/tno directory", e);
      }
   }

   public static String keyFor(Item item) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
      return id.getNamespace() + "_" + id.getPath().replace('/', '_');
   }

   private static Path fileFor(Item item) {
      return configDir.resolve(keyFor(item) + ".json");
   }

   @Nullable
   public static FoodEPConfig getConfig(Item item) {
      String key = keyFor(item);
      FoodEPConfig cached = CACHE.get(key);
      if (cached != null) {
         return cached;
      }

      if (NO_CONFIG.contains(key)) {
         return null;
      }

      Path file = fileFor(item);
      if (!Files.exists(file)) {
         NO_CONFIG.add(key);
         return null;
      }

      try (Reader r = Files.newBufferedReader(file)) {
         FoodEPConfig cfg = (FoodEPConfig)GSON.fromJson(r, FoodEPConfig.class);
         if (cfg != null) {
            CACHE.put(key, cfg);
         } else {
            NO_CONFIG.add(key);
         }

         return cfg;
      } catch (IOException | JsonSyntaxException e) {
         TensuraTNOMod.LOGGER.error("[FoodEP] Failed to read {}", file, e);
         NO_CONFIG.add(key);
         return null;
      }
   }

   public static void setConfig(Item item, @Nullable String ep, @Nullable String mp, @Nullable String ap) {
      FoodEPConfig cfg = new FoodEPConfig(ep, mp, ap);
      String key = keyFor(item);
      CACHE.put(key, cfg);
      if (configDir != null) {
         try (Writer w = Files.newBufferedWriter(fileFor(item))) {
            GSON.toJson(cfg, w);
         } catch (IOException e) {
            TensuraTNOMod.LOGGER.error("[FoodEP] Failed to save config for {}", key, e);
         }
      }
   }

   public static boolean hasCustomConfig(Item item) {
      return getConfig(item) != null;
   }

   public static void invalidate(Item item) {
      String key = keyFor(item);
      CACHE.remove(key);
      NO_CONFIG.remove(key);
   }

   public static double resolveEP(ItemStack stack, LivingEntity entity) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      if (cfg != null && cfg.getEp() != null) {
         double maxEP = EnergyHelper.getMaxEP(entity);
         return FoodEPConfig.resolve(cfg.getEp(), maxEP);
      } else {
         FoodProperties food = stack.getFoodProperties(entity);
         return food == null ? 0.0 : food.nutrition() * 5.0;
      }
   }

   public static double resolveMP(ItemStack stack, LivingEntity entity) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      if (cfg != null && cfg.getMp() != null) {
         double maxMP = EnergyHelper.getMaxMagicule(entity);
         return FoodEPConfig.resolve(cfg.getMp(), maxMP);
      } else {
         return 0.0;
      }
   }

   public static double resolveAP(ItemStack stack, LivingEntity entity) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      if (cfg != null && cfg.getAp() != null) {
         double maxAP = EnergyHelper.getMaxAura(entity);
         return FoodEPConfig.resolve(cfg.getAp(), maxAP);
      } else {
         return 0.0;
      }
   }

   @Nullable
   public static String getTooltipEP(ItemStack stack) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      if (cfg != null && cfg.getEp() != null) {
         return cfg.getEp();
      }

      FoodProperties food = stack.getFoodProperties(null);
      return food == null ? null : String.valueOf(food.nutrition() * 5);
   }

   @Nullable
   public static String getTooltipMP(ItemStack stack) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      return cfg != null ? cfg.getMp() : null;
   }

   @Nullable
   public static String getTooltipAP(ItemStack stack) {
      FoodEPConfig cfg = getConfig(stack.getItem());
      return cfg != null ? cfg.getAp() : null;
   }
}
