package com.tom.storagemod.util;

import com.tom.storagemod.inventory.TerminalItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;

public interface IAutoFillTerminal {
   List<IAutoFillTerminal.ISearchHandler> updateSearch = new ArrayList<>();

   void sendMessage(CompoundTag var1);

   List<TerminalItemStack> getStoredItems();

   boolean isSmartSearch();

   static boolean hasSync() {
      return !updateSearch.isEmpty();
   }

   static void sync(String searchString) {
      updateSearch.forEach(c -> c.setSearch(searchString));
   }

   static String getHandlerName() {
      return updateSearch.stream().map(IAutoFillTerminal.ISearchHandler::getName).collect(Collectors.joining(", "));
   }

   static String getHandlerNameOr(String text) {
      String t = getHandlerName();
      return t.isEmpty() ? text : t;
   }

   interface ISearchHandler {
      void setSearch(String var1);

      String getName();

      String getSearch();
   }
}
