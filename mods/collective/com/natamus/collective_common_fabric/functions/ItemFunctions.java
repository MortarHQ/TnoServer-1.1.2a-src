package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.config.CollectiveConfigHandler;
import com.natamus.collective_common_fabric.data.GlobalVariables;
import com.natamus.collective_common_fabric.fakeplayer.FakePlayer;
import com.natamus.collective_common_fabric.fakeplayer.FakePlayerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_173;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_181;
import net.minecraft.class_1838;
import net.minecraft.class_1893;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2520;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import net.minecraft.class_3468;
import net.minecraft.class_3965;
import net.minecraft.class_52;
import net.minecraft.class_5321;
import net.minecraft.class_7924;
import net.minecraft.class_8567;
import net.minecraft.class_8567.class_8568;
import net.minecraft.server.MinecraftServer;

public class ItemFunctions {
   public static void generateEntityDropsFromLootTable(class_1937 level) {
      MinecraftServer server = level.method_8503();
      if (server != null) {
         GlobalVariables.entitydrops = new HashMap<>();
         FakePlayer fakeplayer = FakePlayerFactory.getMinecraft((class_3218)level);
         class_243 vec = new class_243(0.0, 0.0, 0.0);
         class_1799 lootingsword = new class_1799(class_1802.field_8802, 1);
         lootingsword.method_7978(level.method_30349().method_30530(class_7924.field_41265).method_40290(class_1893.field_9110), 10);
         fakeplayer.method_5673(class_1304.field_6173, lootingsword);

         for (Entry<class_5321<class_1299<?>>, class_1299<?>> entry : level.method_30349().method_30530(class_7924.field_41266).method_29722()) {
            try {
               class_1299<?> type = entry.getValue();
               if (type != null) {
                  class_1297 entity = type.method_5883(level);
                  if (entity instanceof class_1309 le) {
                     class_5321<class_52> lootlocation = le.method_5864().method_16351();
                     class_52 loottable = server.method_58576().method_58295(lootlocation);
                     class_8567 lootParams = new class_8568((class_3218)level)
                        .method_51871(1000000.0F)
                        .method_51874(class_181.field_1226, entity)
                        .method_51874(class_181.field_24424, vec)
                        .method_51874(class_181.field_1230, fakeplayer)
                        .method_51874(class_181.field_1231, level.method_48963().method_48802(fakeplayer))
                        .method_51875(class_173.field_1173);
                     List<class_1792> alldrops = new ArrayList<>();

                     for (int n = 0; n < CollectiveConfigHandler.loopsAmountUsedToGetAllEntityDrops; n++) {
                        for (class_1799 newdrop : loottable.method_51878(lootParams)) {
                           class_1792 newitem = newdrop.method_7909();
                           if (!alldrops.contains(newitem) && !newitem.equals(class_1802.field_8162)) {
                              alldrops.add(newitem);
                           }
                        }
                     }

                     GlobalVariables.entitydrops.put(type, alldrops);
                  }
               }
            } catch (Exception var20) {
            }
         }
      }
   }

   public static void shrinkGiveOrDropItemStack(class_1657 player, class_1268 hand, class_1799 used, class_1799 give) {
      used.method_7934(1);
      if (used.method_7960()) {
         class_1792 giveitem = give.method_7909();
         int maxstacksize = give.method_7914();
         List<class_1799> inventory = player.method_31548().field_7547;
         boolean increased = false;

         for (class_1799 slot : inventory) {
            if (slot.method_7909().equals(giveitem)) {
               int slotcount = slot.method_7947();
               if (slotcount < maxstacksize) {
                  slot.method_7939(slotcount + 1);
                  increased = true;
                  break;
               }
            }
         }

         if (!increased) {
            player.method_6122(hand, give);
         }
      } else if (!player.method_31548().method_7394(give)) {
         player.method_7328(give, false);
      }
   }

   public static void giveOrDropItemStack(class_1657 player, class_1799 give) {
      if (!player.method_31548().method_7394(give)) {
         player.method_7328(give, false);
      }
   }

   public static void itemHurtBreakAndEvent(class_1799 itemStack, class_3222 player, class_1268 hand, int damage) {
      class_1937 level = player.method_37908();
      if (!level.field_9236) {
         if (!player.method_31549().field_7477 && itemStack.method_7963()) {
            itemStack.method_7956(damage, (class_3218)level, player, item -> {
               itemStack.method_7934(1);
               itemStack.method_7974(0);
               player.method_7259(class_3468.field_15383.method_14956(item));
            });
         }
      }
   }

   public static boolean isStoneTypeItem(class_1792 item) {
      return GlobalVariables.stoneblockitems.contains(item);
   }

   public static String itemToReadableString(class_1792 item, int amount) {
      String translationkey = item.method_7876();
      if (translationkey.contains("block.")) {
         return BlockFunctions.blockToReadableString(class_2248.method_9503(item), amount);
      }

      String[] itemspl = translationkey.replace("item.", "").split("\\.");
      String itemstring;
      if (itemspl.length > 1) {
         itemstring = itemspl[1];
      } else {
         itemstring = itemspl[0];
      }

      itemstring = itemstring.replace("_", " ");
      if (amount > 1) {
         itemstring = itemstring + "s";
      }

      return itemstring;
   }

   public static String itemToReadableString(class_1792 item) {
      return itemToReadableString(item, 1);
   }

   public static String getNBTStringFromItemStack(class_1937 level, class_1799 itemStack) {
      return getNBTStringFromItemStack(level, itemStack, true);
   }

   public static String getNBTStringFromItemStack(class_1937 level, class_1799 itemStack, boolean shouldSanitize) {
      class_2520 nbt = itemStack.method_57358(level.method_30349());
      String nbtstring = nbt.toString();
      if (shouldSanitize) {
         nbtstring = nbtstring.replace(" : ", ": ");
      }

      return nbtstring;
   }

   public static void setItemCategory(class_1792 item, class_1761 category) {
   }

   public static class_1838 getUseOnContext(class_1657 player, class_1268 interactionHand, class_3965 blockHitResult) {
      return getUseOnContext(player.method_37908(), player, interactionHand, player.method_5998(interactionHand), blockHitResult);
   }

   public static class_1838 getUseOnContext(class_1657 player, class_1268 interactionHand, class_2338 blockPos, class_2350 direction) {
      return getUseOnContext(player.method_37908(), player, interactionHand, player.method_5998(interactionHand), blockPos, direction);
   }

   public static class_1838 getUseOnContext(
      class_1937 level, @Nullable class_1657 player, class_1268 interactionHand, class_1799 itemStack, class_2338 blockPos, class_2350 direction
   ) {
      return getUseOnContext(level, player, interactionHand, itemStack, new class_3965(class_243.method_24953(blockPos), direction, blockPos, false));
   }

   public static class_1838 getUseOnContext(
      class_1937 level, @Nullable class_1657 player, class_1268 interactionHand, class_1799 itemStack, class_3965 blockHitResult
   ) {
      return new class_1838(level, player, interactionHand, itemStack, blockHitResult);
   }
}
