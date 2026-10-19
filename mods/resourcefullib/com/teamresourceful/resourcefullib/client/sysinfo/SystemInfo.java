package com.teamresourceful.resourcefullib.client.sysinfo;

import com.mojang.datafixers.util.Pair;
import com.teamresourceful.resourcefullib.client.sysinfo.defaults.JavaInfo;
import com.teamresourceful.resourcefullib.client.sysinfo.defaults.LauncherInfo;
import com.teamresourceful.resourcefullib.client.sysinfo.defaults.MinecraftInfo;
import com.teamresourceful.resourcefullib.client.sysinfo.defaults.OperatingSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SystemInfo {
   private static final List<Supplier<SystemInfoBuilder>> BUILDERS = new ArrayList<>();

   public static void addBuilder(String category, Consumer<SystemInfoBuilder> builder) {
      BUILDERS.add(() -> {
         SystemInfoBuilder infoBuilder = new SystemInfoBuilder(category);
         builder.accept(infoBuilder);
         return infoBuilder;
      });
   }

   public static String buildForDiscord() {
      StringBuilder builder = new StringBuilder();
      builder.append("```md\n");

      for (Supplier<SystemInfoBuilder> supplier : BUILDERS) {
         SystemInfoBuilder infoBuilder = supplier.get();
         builder.append("# ").append(infoBuilder.category()).append("\n");

         for (Pair<String, String> info : infoBuilder.info()) {
            builder.append("[").append((String)info.getFirst()).append("]").append("[").append((String)info.getSecond()).append("]").append("\n");
         }

         builder.append("\n");
      }

      builder.append("```");
      return builder.toString();
   }

   static {
      OperatingSystem.register();
      JavaInfo.register();
      MinecraftInfo.register();
      LauncherInfo.register();
   }
}
