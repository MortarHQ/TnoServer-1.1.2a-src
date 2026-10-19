package dev.ftb.mods.ftbfiltersystem.api;

import dev.ftb.mods.ftbfiltersystem.api.client.FTBFilterSystemClientAPI;
import dev.ftb.mods.ftbfiltersystem.api.filter.DumpedFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTBFilterSystemAPI {
   private static final Logger LOGGER = LoggerFactory.getLogger(FTBFilterSystemAPI.class);
   public static final String MOD_ID = "ftbfiltersystem";
   private static FTBFilterSystemAPI.API instance;
   private static FTBFilterSystemClientAPI clientInstance;

   @NotNull
   public static FTBFilterSystemAPI.API api() {
      return Objects.requireNonNull(instance);
   }

   @NotNull
   public static FTBFilterSystemClientAPI clientApi() {
      return Objects.requireNonNull(clientInstance);
   }

   public static ResourceLocation rl(String path) {
      return ResourceLocation.fromNamespaceAndPath("ftbfiltersystem", path);
   }

   public static ResourceLocation modDefaultedRL(String str) {
      if (str.indexOf(":") > 0) {
         ResourceLocation result = ResourceLocation.tryParse(str);
         if (result == null) {
            LOGGER.warn("Invalid resource location: {}, falling back to ftbfiltersystem:", str);
            return rl(str);
         } else {
            return result;
         }
      } else {
         return rl(str);
      }
   }

   public static String modDefaultedString(ResourceLocation rl) {
      return rl.getNamespace().equals("ftbfiltersystem") ? rl.getPath() : rl.toString();
   }

   @Internal
   public static void _init(FTBFilterSystemAPI.API instance) {
      if (FTBFilterSystemAPI.instance != null) {
         throw new IllegalStateException("can't init more than once!");
      }

      FTBFilterSystemAPI.instance = instance;
   }

   @Internal
   public static void _initClient(FTBFilterSystemClientAPI instance) {
      if (clientInstance != null) {
         throw new IllegalStateException("can't init more than once!");
      }

      clientInstance = instance;
   }

   public interface API {
      FTBFilterSystemRegistry getRegistry();

      boolean isFilterItem(ItemStack var1);

      Item filterItem();

      boolean doesFilterMatch(ItemStack var1, ItemStack var2, Provider var3);

      List<DumpedFilter> dump(SmartFilter var1);

      Optional<SmartFilter> createDefaultFilter(@NotNull SmartFilter.Compound var1, ResourceLocation var2);

      SmartFilter parseFilter(String var1, Provider var2) throws FilterException;

      SmartFilter parseFilter(ItemStack var1, Provider var2) throws FilterException;

      List<SmartFilter> parseFilterList(@NotNull SmartFilter.Compound var1, String var2, Provider var3) throws FilterException;

      ItemStack makeTagFilter(TagKey<Item> var1);
   }
}
