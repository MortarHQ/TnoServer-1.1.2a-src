package com.natamus.collective_common_forge.functions;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective_common_forge.data.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class PlayerFunctions {
   public static boolean respawnPlayer(Level world, Player player) {
      if (!(player instanceof ServerPlayer)) {
         return false;
      }

      MinecraftServer server = world.getServer();
      ServerPlayer serverplayer = (ServerPlayer)player;
      if (serverplayer.wonGame) {
         serverplayer.wonGame = false;
         serverplayer = server.getPlayerList().respawn(serverplayer, true, RemovalReason.CHANGED_DIMENSION);
         CriteriaTriggers.CHANGED_DIMENSION.trigger(serverplayer, Level.END, Level.OVERWORLD);
      } else if (serverplayer.getHealth() <= 0.0F) {
         server.getPlayerList().respawn(serverplayer, false, RemovalReason.KILLED);
      }

      return true;
   }

   public static Player matchPlayer(Player player, String other) {
      return matchPlayer(player.level(), other);
   }

   public static Player matchPlayer(Level world, String other) {
      for (Player onlineplayer : world.players()) {
         if (onlineplayer.getName().getString().toLowerCase().equals(other)) {
            return onlineplayer;
         }
      }

      return null;
   }

   public static boolean isHoldingWater(Player player) {
      return player.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.WATER_BUCKET)
         || player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(Items.WATER_BUCKET);
   }

   public static boolean isJoiningWorldForTheFirstTime(Player player, String modid) {
      return isJoiningWorldForTheFirstTime(player, modid, false);
   }

   public static boolean isJoiningWorldForTheFirstTime(Player player, String modid, boolean mustHaveEmptyInventory) {
      return isJoiningWorldForTheFirstTime(player, modid, false, true);
   }

   public static boolean isJoiningWorldForTheFirstTime(Player player, String modid, boolean mustHaveEmptyInventory, boolean mustBeCloseToSpawn) {
      String firstjointag = "collective.firstJoin." + modid;
      String playerName = player.getName().getString();
      Set<String> tags = player.getTags();
      if (tags.contains(firstjointag)) {
         return false;
      }

      player.addTag(firstjointag);
      if (mustHaveEmptyInventory) {
         Inventory inv = player.getInventory();
         boolean isempty = true;

         for (int i = 0; i < 36; i++) {
            if (!inv.getItem(i).isEmpty()) {
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
         Level level = player.getCommandSenderWorld();
         ServerLevel serverLevel = (ServerLevel)level;
         ServerPlayer serverPlayer = (ServerPlayer)player;
         BlockPos spawnPos = serverPlayer.getRespawnPosition();
         if (spawnPos == null) {
            spawnPos = serverLevel.getSharedSpawnPos();
         }

         Constants.LOG.debug("[" + modid + "] Checking for first join of " + playerName + " with spawn position: " + spawnPos.toShortString());
         BlockPos playerPos = player.blockPosition();
         BlockPos checkPos = new BlockPos(playerPos.getX(), spawnPos.getY(), playerPos.getZ());
         int spawnRadius = ((IntegerValue)serverLevel.getGameRules().getRule(GameRules.RULE_SPAWN_RADIUS)).get();
         Constants.LOG.debug("[" + modid + "] Checking for first join of " + playerName + " with spawn radius: " + spawnRadius);
         return checkPos.closerThan(spawnPos, spawnRadius * 2);
      } else {
         return true;
      }
   }

   public static BlockPos getSpawnPoint(Level world, Player player) {
      Vec3 spawnvec = getSpawnVec(world, player);
      return BlockPos.containing(spawnvec.x, spawnvec.y, spawnvec.z);
   }

   public static Vec3 getSpawnVec(Level world, Player player) {
      ServerPlayer serverplayer = (ServerPlayer)player;
      ServerLevel ServerLevel = (ServerLevel)world;
      BlockPos respawnlocation = ServerLevel.getSharedSpawnPos();
      Vec3 respawnvec = new Vec3(respawnlocation.getX(), respawnlocation.getY(), respawnlocation.getZ());
      BlockPos bedpos = serverplayer.getRespawnPosition();
      if (bedpos != null) {
         DimensionTransition optionalbed = ((ServerPlayer)player).findRespawnPositionAndUseSpawnBlock(true, DimensionTransition.DO_NOTHING);
         Vec3 bedvec = optionalbed.pos();
         BlockPos bp = BlockPos.containing(bedvec);
         Iterator<BlockPos> it = BlockPos.betweenClosedStream(bp.getX() - 1, bp.getY() - 1, bp.getZ() - 1, bp.getX() + 1, bp.getY() + 1, bp.getZ() + 1)
            .iterator();

         while (it.hasNext()) {
            BlockPos np = it.next();
            BlockState state = world.getBlockState(np);
            Block block = state.getBlock();
            if (block instanceof BedBlock) {
               respawnvec = bedvec;
               break;
            }
         }
      }

      return respawnvec;
   }

   public static InteractionHand getOtherHand(InteractionHand hand) {
      return hand.equals(InteractionHand.MAIN_HAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
   }

   public static String getPlayerGearString(Player player) {
      Level level = player.level();
      StringBuilder skconfig = new StringBuilder();
      ItemStack offhand = player.getItemBySlot(EquipmentSlot.OFFHAND);
      if (!offhand.isEmpty()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, offhand);
         skconfig.append("'offhand' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("'offhand' : '',");
      }

      ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
      if (!head.isEmpty()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, head);
         skconfig.append("\n'head' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'head' : '',");
      }

      ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
      if (!chest.isEmpty()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, chest);
         skconfig.append("\n'chest' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'chest' : '',");
      }

      ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
      if (!legs.isEmpty()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, legs);
         skconfig.append("\n'legs' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'legs' : '',");
      }

      ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);
      if (!feet.isEmpty()) {
         String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, feet);
         skconfig.append("\n'feet' : '").append(nbtstring).append("',");
      } else {
         skconfig.append("\n'feet' : '',");
      }

      Inventory inv = player.getInventory();

      for (int i = 0; i < 36; i++) {
         ItemStack slot = inv.getItem(i);
         if (!slot.isEmpty()) {
            String nbtstring = ItemFunctions.getNBTStringFromItemStack(level, slot);
            skconfig.append("\n").append(i).append(" : ").append("'").append(nbtstring).append("',");
         } else {
            skconfig.append("\n").append(i).append(" : '',");
         }
      }

      return skconfig.toString();
   }

   public static String getPlayerGearStringFromHashMap(Level level, HashMap<String, ItemStack> gear) {
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

      List<ItemStack> emptyinventory = NonNullList.withSize(36, ItemStack.EMPTY);

      for (int i = 0; i < emptyinventory.size(); i++) {
         String itemstring = "";
         if (gear.containsKey(i + "")) {
            itemstring = ItemFunctions.getNBTStringFromItemStack(level, gear.get(i + ""));
         }

         gearstring.append("\n").append(i).append(" : '").append(itemstring).append("',");
      }

      return gearstring.toString();
   }

   public static void setPlayerGearFromString(Player player, String gearconfig) {
      Level level = player.level();
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
               ItemStack itemStack = null;

               try {
                  CompoundTag compoundTag = TagParser.parseTag(data);
                  Optional<ItemStack> optionalItemStack = ItemStack.parse(level.registryAccess(), compoundTag);
                  if (optionalItemStack.isPresent()) {
                     itemStack = optionalItemStack.get();
                  }
               } catch (CommandSyntaxException var18) {
               }

               if (itemStack == null) {
                  try {
                     data = data.replace("\",\"", "|||,|||").replace(" \"", " '").replace("\" ", "' ").replace("|||,|||", "\",\"");
                     CompoundTag compoundTag = TagParser.parseTag(data);
                     Optional<ItemStack> optionalItemStack = ItemStack.parse(level.registryAccess(), compoundTag);
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
                  player.getInventory().clearContent();
               }

               if (NumberFunctions.isNumeric(slotstring)) {
                  int slot = Integer.parseInt(slotstring);
                  player.getInventory().setItem(slot, itemStack);
               } else {
                  EquipmentSlot type;
                  switch (slotstring) {
                     case "offhand":
                        type = EquipmentSlot.OFFHAND;
                        break;
                     case "head":
                        type = EquipmentSlot.HEAD;
                        break;
                     case "chest":
                        type = EquipmentSlot.CHEST;
                        break;
                     case "legs":
                        type = EquipmentSlot.LEGS;
                        break;
                     case "feet":
                        type = EquipmentSlot.FEET;
                        break;
                     default:
                        continue;
                  }

                  player.setItemSlot(type, itemStack);
               }
            }
         }
      }
   }
}
