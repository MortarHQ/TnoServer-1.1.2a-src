package dev.ftb.mods.ftblibrary.api.client;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class FTBLibraryClientApi {
   private static FTBLibraryClientApi INSTANCE;
   public Set<String> screenSidebarBlacklist = new HashSet<>();

   public static FTBLibraryClientApi get() {
      if (INSTANCE == null) {
         INSTANCE = new FTBLibraryClientApi();
      }

      return INSTANCE;
   }

   private FTBLibraryClientApi() {
   }

   public void addSidebarScreenBlacklist(String... screenClass) {
      Collections.addAll(this.screenSidebarBlacklist, screenClass);
   }

   public boolean isSidebarScreenBlacklisted(@Nullable Screen screen) {
      return screen != null && this.screenSidebarBlacklist.contains(screen.getClass().getName());
   }

   public Set<String> getSidebarBlacklist() {
      return ImmutableSet.copyOf(this.screenSidebarBlacklist);
   }
}
