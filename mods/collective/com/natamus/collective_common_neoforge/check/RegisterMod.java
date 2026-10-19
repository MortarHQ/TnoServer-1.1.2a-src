package com.natamus.collective_common_neoforge.check;

import com.natamus.collective_common_neoforge.functions.DataFunctions;
import com.natamus.collective_common_neoforge.functions.StringFunctions;
import com.natamus.collective_common_neoforge.functions.WorldFunctions;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RegisterMod {
   private static final CopyOnWriteArrayList<String> jarlist = new CopyOnWriteArrayList<>();
   private static final HashMap<String, String> jartoname = new HashMap<>();
   public static boolean shouldDoCheck = true;

   public static void register(String modname, String modid, String modVersion, String rawGameVersion) {
      String gameVersion = rawGameVersion.replaceAll("\\[", "").replaceAll("]", "");
      int dotCount = gameVersion.length() - gameVersion.replace(".", "").length();
      if (dotCount == 1) {
         gameVersion = gameVersion + ".0";
      }

      String jarname = modid + "-" + gameVersion + "-" + modVersion + ".jar";
      jarlist.add(jarname);
      jartoname.put(jarname, modname);
   }

   public static void initialProcess() {
      shouldDoCheck = false;
   }

   public static void joinWorldProcess(Level world, Player player) {
      if (world instanceof ServerLevel) {
         List<String> wrongmodnames = checkIfAllJarsExist();
         if (wrongmodnames.size() > 0 && processPreJoinWorldCheck(world)) {
            String s = "";
            if (wrongmodnames.size() > 1) {
               s = "s";
            }

            String projecturl = "https://curseforge.com/members/serilum/projects";
            StringFunctions.sendMessage(player, "Mod" + s + " from incorrect sources:", ChatFormatting.RED, projecturl);

            for (String wrongmodname : wrongmodnames) {
               StringFunctions.sendMessage(player, " " + wrongmodname, ChatFormatting.YELLOW, projecturl);
            }

            StringFunctions.sendMessage(
               player,
               "You are receiving this message because you are using some of Serilum's mods, but probably haven't downloaded them from the original source. Unofficial sources can contain malicious software, supply no income for developers and host outdated versions.",
               ChatFormatting.RED,
               projecturl
            );
            StringFunctions.sendMessage(
               player, "Serilum's mod downloads are only officially available at CurseForge and Modrinth.", ChatFormatting.DARK_GREEN, projecturl
            );
            StringFunctions.sendMessage(player, "  CF: https://curseforge.com/members/serilum/projects", ChatFormatting.YELLOW, projecturl);
            StringFunctions.sendMessage(player, "  MR: https://modrinth.com/user/Serilum", ChatFormatting.YELLOW, "https://modrinth.com/user/Serilum");
            StringFunctions.sendMessage(
               player, "You won't see this message again in this instance. Thank you for reading.", ChatFormatting.DARK_GREEN, projecturl
            );
            StringFunctions.sendMessage(player, "-Rick (Serilum)", ChatFormatting.YELLOW, projecturl);
            processPostJoinWorldCheck(world);
         }

         shouldDoCheck = false;
      }
   }

   private static boolean processPreJoinWorldCheck(Level world) {
      String checkfilepath = WorldFunctions.getWorldPath((ServerLevel)world)
         + File.separator
         + "config"
         + File.separator
         + "collective"
         + File.separator
         + "checked.txt";
      File checkfile = new File(checkfilepath);
      if (checkfile.exists()) {
         shouldDoCheck = false;
      } else if (!checkAlternative()) {
         shouldDoCheck = false;
      }

      return shouldDoCheck;
   }

   private static void processPostJoinWorldCheck(Level world) {
      shouldDoCheck = false;
      String worlddatapath = WorldFunctions.getWorldPath((ServerLevel)world) + File.separator + "config" + File.separator + "collective";
      File dir = new File(worlddatapath);
      if (dir.mkdirs()) {
         try {
            PrintWriter writer = new PrintWriter(worlddatapath + File.separator + "checked.txt", StandardCharsets.UTF_8);
            writer.println("# Please check out https://stopmodreposts.org/ for more information on why this feature exists.");
            writer.println("checked=true");
            writer.close();
         } catch (Exception var4) {
         }

         createRepostingCheckFile();
      }
   }

   public static void createRepostingCheckFile() {
      String alternativecheckpath = DataFunctions.getConfigDirectory() + File.separator + "collective";
      if (!new File(alternativecheckpath + File.separator + "checked.txt").isFile()) {
         File alternativedir = new File(alternativecheckpath);
         if (alternativedir.mkdirs()) {
            try {
               PrintWriter writer = new PrintWriter(alternativecheckpath + File.separator + "checked.txt", StandardCharsets.UTF_8);
               writer.println("# Please check out https://stopmodreposts.org/ for more information on why this feature exists.");
               writer.println("checked=true");
               writer.close();
            } catch (Exception var3) {
            }
         }
      }
   }

   private static List<String> checkIfAllJarsExist() {
      List<String> wrongmodnames = new ArrayList<>();
      boolean enabled = false;
      if (enabled) {
         List<String> installedmods = DataFunctions.getInstalledModJars();

         for (String jarname : jarlist) {
            if (!installedmods.contains(jarname) && jartoname.containsKey(jarname)) {
               wrongmodnames.add(jartoname.get(jarname));
            }
         }

         if (wrongmodnames.size() > 0) {
            Collections.sort(wrongmodnames);
         }
      }

      return wrongmodnames;
   }

   private static boolean checkAlternative() {
      String alternativecheckfilepath = DataFunctions.getConfigDirectory() + File.separator + "collective" + File.separator + "checked.txt";
      File alternativecheckfile = new File(alternativecheckfilepath);
      return !alternativecheckfile.exists();
   }
}
