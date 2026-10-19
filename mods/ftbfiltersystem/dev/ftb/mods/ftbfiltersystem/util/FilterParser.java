package dev.ftb.mods.ftbfiltersystem.util;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.filter.compound.RootFilter;
import dev.ftb.mods.ftbfiltersystem.registry.FilterRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FilterParser {
   @NotNull
   public static SmartFilter parse(String str, Provider registryAccess) throws FilterException {
      return FilterCache.INSTANCE.getOrCreateFilter(str, registryAccess);
   }

   @NotNull
   public static SmartFilter parseRaw(String str, Provider registryAccess) throws FilterException {
      RootFilter root = new RootFilter();
      root.getChildren().addAll(parseFilterList(root, str, registryAccess));
      return root;
   }

   public static List<SmartFilter> parseFilterList(SmartFilter.Compound parent, String str, Provider registryAccess) throws FilterException {
      List<SmartFilter> res = new ArrayList<>();
      str = str.trim();

      while (!str.isEmpty()) {
         int start = str.indexOf(40);
         if (start <= 0) {
            throw new FilterException("missing open parenthesis: " + str);
         }

         int matchingParen = findMatchingParenIdx(str, start);
         String type = str.substring(0, start).trim();
         String arg = unescape(str.substring(start + 1, matchingParen).trim());
         if (ResourceLocation.tryParse(type) == null) {
            throw new FilterException("invalid filter ID: " + type);
         }

         res.add(
            FilterRegistry.getInstance()
               .getDetails(FTBFilterSystemAPI.modDefaultedRL(type))
               .map(FilterRegistry.FilterDetails::factory)
               .orElseThrow(() -> new FilterException("unknown filter ID: " + type))
               .create(parent, arg, registryAccess)
         );
         str = str.substring(matchingParen + 1);
      }

      return res;
   }

   private static String unescape(String str) {
      StringBuilder res = new StringBuilder();

      for (int i = 0; i < str.length(); i++) {
         if (str.charAt(i) == '\\') {
            if (++i >= str.length()) {
               break;
            }
         }

         res.append(str.charAt(i));
      }

      return res.toString();
   }

   private static int findMatchingParenIdx(String str, int checkPos) throws FilterException {
      int depth = 0;

      while (checkPos < str.length()) {
         if (str.charAt(checkPos) == '\\') {
            checkPos++;
         } else if (str.charAt(checkPos) == '(') {
            depth++;
         } else if (str.charAt(checkPos) == ')') {
            if (--depth == 0) {
               return checkPos;
            }
         }

         checkPos++;
      }

      throw new FilterException("missing close parenthesis: " + str);
   }
}
