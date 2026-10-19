package com.natamus.collective_common_fabric.functions;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective_common_fabric.data.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.class_1268;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_174;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1928;
import net.minecraft.class_1937;
import net.minecraft.class_2244;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2522;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_5454;
import net.minecraft.class_1297.class_5529;
import net.minecraft.class_1928.class_4312;
import net.minecraft.server.MinecraftServer;

public class PlayerFunctions {
   public static boolean respawnPlayer(class_1937 world, class_1657 player) {
      if (!(player instanceof class_3222)) {
         return false;
      }

      MinecraftServer server = world.method_8503();
      class_3222 serverplayer = (class_3222)player;
      if (serverplayer.field_13989) {
         serverplayer.field_13989 = false;
         serverplayer = server.method_3760().method_14556(serverplayer, true, class_5529.field_27002);
         class_174.field_1183.method_8794(serverplayer, class_1937.field_25181, class_1937.field_25179);
      } else if (serverplayer.method_6032() <= 0.0F) {
         server.method_3760().method_14556(serverplayer, false, class_5529.field_26998);
      }

      return true;
   }

   public static class_1657 matchPlayer(class_1657 player, String other) {
      return matchPlayer(player.method_37908(), other);
   }

   public static class_1657 matchPlayer(class_1937 world, String other) {
      for (class_1657 onlineplayer : world.method_18456()) {
         if (onlineplayer.method_5477().getString().toLowerCase().equals(other)) {
            return onlineplayer;
         }
      }

      return null;
   }

   public static boolean isHoldingWater(class_1657 player) {
      return player.method_5998(class_1268.field_5810).method_7909().equals(class_1802.field_8705)
         || player.method_5998(class_1268.field_5808).method_7909().equals(class_1802.field_8705);
   }

   public static boolean isJoiningWorldForTheFirstTime(class_1657 player, String modid) {
      return isJoiningWorldForTheFirstTime(player, modid, false);
   }

   public static boolean isJoiningWorldForTheFirstTime(class_1657 player, String modid, boolean mustHaveEmptyInventory) {
      return isJoiningWorldForTheFirstTime(player, modid, false, true);
   }

   public static boolean isJoiningWorldForTheFirstTime(class_1657 player, String modid, boolean mustHaveEmptyInventory, boolean mustBeCloseToSpawn) {
      String firstjointag = "collective.firstJoin." + modid;
      String playerName = player.method_5477().getString();
      Set<String> tags = player.method_5752();
      if (tags.contains(firstjointag)) {
         return false;
      }

      player.method_5780(firstjointag);
      if (mustHaveEmptyInventory) {
         class_1661 inv = player.method_31548();
         boolean isempty = true;

         for (int i = 0; i < 36; i++) {
            if (!inv.method_5438(i).method_7960()) {
               isempty = false;
               break;
            }
         }

         if (!isempty) {
            Constants.LOG.debug("[" + modid + "] Inventory of " + playerName + " is not empty, first join is false.");
            return false;
         }
      }

      if (mustBeCloseToSpawn) {
         class_1937 level = player.method_5770();
         class_3218 serverLevel = (class_3218)level;
         class_3222 serverPlayer = (class_3222)player;
         class_2338 spawnPos = serverPlayer.method_26280();
         if (spawnPos == null) {
            spawnPos = serverLevel.method_43126();
         }

         Constants.LOG.debug("[" + modid + "] Checking for first join of " + playerName + " with spawn position: " + spawnPos.method_23854());
         class_2338 playerPos = player.method_24515();
         class_2338 checkPos = new class_2338(playerPos.method_10263(), spawnPos.method_10264(), playerPos.method_10260());
         int spawnRadius = ((class_4312)serverLevel.method_8450().method_20746(class_1928.field_19403)).method_20763();
         Constants.LOG.debug("[" + modid + "] Checking for first join of " + playerName + " with spawn radius: " + spawnRadius);
         return checkPos.method_19771(spawnPos, spawnRadius * 2);
      } else {
         return true;
      }
   }

   public static class_2338 getSpawnPoint(class_1937 world, class_1657 player) {
      class_243 spawnvec = getSpawnVec(world, player);
      return class_2338.method_49637(spawnvec.field_1352, spawnvec.field_1351, spawnvec.field_1350);
   }

   public static class_243 getSpawnVec(class_1937 world, class_1657 player) {
      class_3222 serverplayer = (class_3222)player;
      class_3218 ServerLevel = (class_3218)world;
      class_2338 respawnlocation = ServerLevel.method_43126();
      class_243 respawnvec = new class_243(respawnlocation.method_10263(), respawnlocation.method_10264(), respawnlocation.method_10260());
      class_2338 bedpos = serverplayer.method_26280();
      if (bedpos != null) {
         class_5454 optionalbed = ((class_3222)player).method_60590(true, class_5454.field_52245);
         class_243 bedvec = optionalbed.comp_2821();
         class_2338 bp = class_2338.method_49638(bedvec);
         Iterator<class_2338> it = class_2338.method_17962(
               bp.method_10263() - 1, bp.method_10264() - 1, bp.method_10260() - 1, bp.method_10263() + 1, bp.method_10264() + 1, bp.method_10260() + 1
            )
            .iterator();

         while (it.hasNext()) {
            class_2338 np = it.next();
            class_2680 state = world.method_8320(np);
            class_2248 block = state.method_26204();
            if (block instanceof class_2244) {
               respawnvec = bedvec;
               break;
            }
         }
      }

      return respawnvec;
   }

   public static class_1268 getOtherHand(class_1268 hand) {
      return hand.equals(class_1268.field_5808) ? class_1268.field_5810 : class_1268.field_5808;
   }

   public static String getPlayerGearString(class_1657 player) {
      class_1937 level = player.method_37908();
      StringBuilder skconfig = new StringBuilder();
      class_1799 offhand = player.method_6118(class_1304.field_6171);
      if (!offhand.method_7960()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, offhand);
         skconfig.append("'offhand' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("'offhand' : '',");
      }

      class_1799 head = player.method_6118(class_1304.field_6169);
      if (!head.method_7960()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, head);
         skconfig.append("\n'head' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'head' : '',");
      }

      class_1799 chest = player.method_6118(class_1304.field_6174);
      if (!chest.method_7960()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, chest);
         skconfig.append("\n'chest' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'chest' : '',");
      }

      class_1799 legs = player.method_6118(class_1304.field_6172);
      if (!legs.method_7960()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, legs);
         skconfig.append("\n'legs' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'legs' : '',");
      }

      class_1799 feet = player.method_6118(class_1304.field_6166);
      if (!feet.method_7960()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, feet);
         skconfig.append("\n'feet' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'feet' : '',");
      }

      class_1661 inv = player.method_31548();

      for (int i = 0; i < 36; i++) {
         class_1799 slot = inv.method_5438(i);
         if (!slot.method_7960()) {
            String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, slot);
            skconfig.append("\n").append(i).append(" : ").append("'").append(nbtstring).append("',");
         } else {
            skconfig.append("\n").append(i).append(" : '',");
         }
      }

      return skconfig.toString();
   }

   public static String getPlayerGearStringFromHashMap(class_1937 level, HashMap<String, class_1799> gear) {
      StringBuilder gearstring = new StringBuilder();

      for (String specialslot : new ArrayList<>(Arrays.asList("offhand", "head", "chest", "legs", "feet"))) {
         String specialslotstring = "";
         if (gear.containsKey(specialslot)) {
            specialslotstring = ItemFunctions.getNBTStringFromItemStack(level, gear.get(specialslot));
         }

         if (!gearstring.toString().equals("")) {
            gearstring.append("\n");
         }

         gearstring.append("'").append(specialslot).append("'").append(" : ").append("'").append(specialslotstring).append("',");
      }

      List<class_1799> emptyinventory = class_2371.method_10213(36, class_1799.field_8037);

      for (int i = 0; i < emptyinventory.size(); i++) {
         String itemstring = "";
         if (gear.containsKey(i + "")) {
            itemstring = ItemFunctions.getNBTStringFromItemStack(level, gear.get(i + ""));
         }

         gearstring.append("\n").append(i).append(" : '").append(itemstring).append("',");
      }

      return gearstring.toString();
   }

   public static void setPlayerGearFromString(class_1657 player, String gearconfig) {
      class_1937 level = player.method_37908();
      String[] gearspl = gearconfig.split("',[\\r\\n]+");
      int newlinecount = gearspl.length;
      if (newlinecount < 40) {
         Constants.LOG.info("[Error] (Collective) setPlayerGearFromString: The gear config does not contain 40 lines and is invalid.");
      } else {
         boolean cleared = false;

         for (String line : gearspl) {
            line = line.trim();
            if (line.endsWith(",")) {
               line = line.substring(0, line.length() - 1);
            }

            if (!line.endsWith("'")) {
               line = line + "'";
            }

            String[] lspl = line.split(" : ");
            if (lspl.length != 2) {
               Constants.LOG.info("[Error] (Collective) setPlayerGearFromString: The line " + line + " is invalid.");
               return;
            }

            String slotstring = lspl[0].replace("'", "");
            String data = lspl[1];
            if (data.startsWith("'")) {
               data = data.substring(1);
            }

            if (data.endsWith("'")) {
               data = data.substring(0, data.length() - 1);
            }

            if (data.length() >= 2) {
               data = data.replaceAll("\r", "");
               class_1799 itemStack = null;

               try {
                  class_2487 compoundTag = class_2522.method_10718(data);
                  Optional<class_1799> optionalItemStack = class_1799.method_57360(level.method_30349(), compoundTag);
                  if (optionalItemStack.isPresent()) {
                     itemStack = optionalItemStack.get();
                  }
               } catch (CommandSyntaxException var18) {
               }

               if (itemStack == null) {
                  try {
                     data = data.replace("\",\"", "|||,|||").replace(" \"", " '").replace("\" ", "' ").replace("|||,|||", "\",\"");
                     class_2487 compoundTag = class_2522.method_10718(data);
                     Optional<class_1799> optionalItemStack = class_1799.method_57360(level.method_30349(), compoundTag);
                     if (optionalItemStack.isPresent()) {
                        itemStack = optionalItemStack.get();
                     }
                  } catch (CommandSyntaxException ex) {
                     ex.printStackTrace();
                  }
               }

               if (itemStack == null) {
                  Constants.LOG.info("[Error] (Collective) setPlayerGearFromString: Unable to get the correct itemstack data from data " + data);
                  return;
               }

               if (!cleared) {
                  cleared = true;
                  player.method_31548().method_5448();
               }

               if (NumberFunctions.isNumeric(slotstring)) {
                  int slot = Integer.parseInt(slotstring);
                  player.method_31548().method_5447(slot, itemStack);
               } else {
                  class_1304 type;
                  switch (slotstring) {
                     case "offhand":
                        type = class_1304.field_6171;
                        break;
                     case "head":
                        type = class_1304.field_6169;
                        break;
                     case "chest":
                        type = class_1304.field_6174;
                        break;
                     case "legs":
                        type = class_1304.field_6172;
                        break;
                     case "feet":
                        type = class_1304.field_6166;
                        break;
                     default:
                        continue;
                  }

                  player.method_5673(type, itemStack);
               }
            }
         }
      }
   }
}
