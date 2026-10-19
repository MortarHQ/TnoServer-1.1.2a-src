package com.teamresourceful.resourcefullib.client.sysinfo.defaults;

import com.teamresourceful.resourcefullib.client.sysinfo.SystemInfo;
import com.teamresourceful.resourcefullib.client.sysinfo.SystemInfoBuilder;
import java.util.function.Consumer;

public record LauncherInfo() implements Consumer<SystemInfoBuilder> {
   public static void register() {
      SystemInfo.addBuilder("Launcher", new LauncherInfo());
   }

   public void accept(SystemInfoBuilder builder) {
      builder.append("Name", System.getProperty("minecraft.launcher.brand", "Unknown"));
      builder.append("Version", System.getProperty("minecraft.launcher.version", "Unknown"));
      String modpack = System.getProperty("minecraft.modpack.name");
      if (modpack != null) {
         builder.append("Modpack", modpack);
      }
   }
}
