package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.data.Constants;
import com.natamus.collective_common_fabric.data.GlobalVariables;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_181;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2333;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2533;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_8567.class_8568;

public class BlockFunctions {
   public static List<class_1799> getBlockDrops(class_1937 level, class_2680 blockState, boolean silkTouched) {
      return getBlockDrops(level, blockState, silkTouched ? Constants.silkPickaxeStack : Constants.normalPickaxeStack, null, new class_243(0.0, 0.0, 0.0));
   }

   public static List<class_1799> getBlockDrops(class_1937 level, class_2680 blockState, class_1799 itemStack, class_1657 player) {
      return getBlockDrops(level, blockState, itemStack, player, player.method_19538());
   }

   public static List<class_1799> getBlockDrops(class_1937 level, class_2680 blockState, class_1799 itemStack, class_1657 player, class_243 vec) {
      class_8568 lootParamsBuilder = new class_8568((class_3218)level)
         .method_51874(class_181.field_1224, blockState)
         .method_51874(class_181.field_1229, itemStack)
         .method_51874(class_181.field_24424, vec)
         .method_51877(class_181.field_1226, player);
      return blockState.method_26189(lootParamsBuilder);
   }

   public static Boolean isSpecificBlock(class_2248 specificblock, class_2248 tocheckblock) {
      return specificblock != null && tocheckblock != null ? specificblock.equals(tocheckblock) : false;
   }

   public static Boolean isSpecificBlock(class_2248 specificblock, class_1799 tocheckitemstack) {
      if (tocheckitemstack == null) {
         return false;
      }

      class_1792 tocheckitem = tocheckitemstack.method_7909();
      if (tocheckitem == null) {
         return false;
      }

      class_2248 tocheckblock = class_2248.method_9503(tocheckitem);
      return isSpecificBlock(specificblock, tocheckblock);
   }

   public static Boolean isSpecificBlock(class_2248 specificblock, class_1937 world, class_2338 pos) {
      class_2248 tocheckblock = world.method_8320(pos).method_26204();
      return isSpecificBlock(specificblock, tocheckblock);
   }

   public static void dropBlock(class_1937 world, class_2338 pos) {
      class_2680 blockstate = world.method_8320(pos);
      class_2586 tileentity = world.method_8321(pos);
      class_2248.method_9610(blockstate, world, pos, tileentity);
      world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
   }

   public static void dropBlock(class_1937 world, class_2338 pos, @Nullable class_1297 entity, class_1799 itemStack) {
      class_2680 blockstate = world.method_8320(pos);
      class_2586 tileentity = world.method_8321(pos);
      class_2248.method_9511(blockstate, world, pos, tileentity, entity, itemStack);
      world.method_8652(pos, class_2246.field_10124.method_9564(), 3);
   }

   public static Boolean isOneOfBlocks(List<class_2248> blocks, class_2248 tocheckblock) {
      if (blocks.size() < 1) {
         return false;
      }

      for (class_2248 specificblock : blocks) {
         if (isSpecificBlock(specificblock, tocheckblock)) {
            return true;
         }
      }

      return false;
   }

   public static Boolean isOneOfBlocks(List<class_2248> blocks, class_1799 tocheckitemstack) {
      if (tocheckitemstack == null) {
         return false;
      }

      class_1792 tocheckitem = tocheckitemstack.method_7909();
      if (tocheckitem == null) {
         return false;
      }

      class_2248 tocheckblock = class_2248.method_9503(tocheckitem);
      return isOneOfBlocks(blocks, tocheckblock);
   }

   public static Boolean isOneOfBlocks(List<class_2248> blocks, class_1937 world, class_2338 pos) {
      class_2248 tocheckblock = world.method_8320(pos).method_26204();
      return isOneOfBlocks(blocks, tocheckblock);
   }

   public static boolean isGrowBlock(class_2248 block) {
      return GlobalVariables.growblocks.contains(block);
   }

   public static boolean isStoneTypeBlock(class_2248 block) {
      return GlobalVariables.stoneblocks.contains(block);
   }

   public static Boolean isFilledPortalFrame(class_2680 blockstate) {
      class_2248 block = blockstate.method_26204();
      return !block.equals(class_2246.field_10398) ? false : (Boolean)blockstate.method_11654(class_2333.field_10958);
   }

   public static boolean canOpenByHand(class_2680 blockState) {
      return canOpenByHand(blockState, true);
   }

   public static boolean canOpenByHand(class_2680 blockState, boolean defaultReturn) {
      class_2248 block = blockState.method_26204();
      if (block instanceof class_2323 doorBlock) {
         return doorBlock.method_51169().comp_1471();
      } else {
         return block instanceof class_2533 trapDoorBlock ? trapDoorBlock.method_54766().comp_1471() : defaultReturn;
      }
   }

   public static String blockToReadableString(class_2248 block, int amount) {
      String[] blockspl = block.method_9539().replace("block.", "").split("\\.");
      String blockstring;
      if (blockspl.length > 1) {
         blockstring = blockspl[1];
      } else {
         blockstring = blockspl[0];
      }

      blockstring = blockstring.replace("_", " ");
      if (amount > 1) {
         blockstring = blockstring + "s";
      }

      return blockstring;
   }

   public static String blockToReadableString(class_2248 block) {
      return blockToReadableString(block, 1);
   }
}
