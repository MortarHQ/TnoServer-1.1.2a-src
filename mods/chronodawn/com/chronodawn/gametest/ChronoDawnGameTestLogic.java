package com.chronodawn.gametest;

import com.chronodawn.registry.ModItems;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;

public final class ChronoDawnGameTestLogic {
   public static final BlockPos TEST_POS = new BlockPos(1, 2, 1);
   public static final Consumer<GameTestHelper> TEST_MOCK_PLAYER_CAN_BE_CREATED = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         if (player != null) {
            helper.succeed();
         } else {
            helper.fail("Mock player was null");
         }
      } catch (Exception e) {
         helper.fail("Failed to create mock player: " + e.getMessage());
      }
   });
   public static final Consumer<GameTestHelper> TEST_PLAYER_CAN_EQUIP_CHESTPLATE = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ItemStack chestplate = new ItemStack((ItemLike)ModItems.CLOCKSTONE_CHESTPLATE.get());
         player.setItemSlot(EquipmentSlot.CHEST, chestplate);
         ItemStack equipped = player.getItemBySlot(EquipmentSlot.CHEST);
         if (!equipped.isEmpty() && equipped.getItem() == ModItems.CLOCKSTONE_CHESTPLATE.get()) {
            helper.succeed();
         } else {
            helper.fail("Chestplate was not properly equipped");
         }
      } catch (Exception e) {
         helper.fail("Failed to equip chestplate: " + e.getMessage());
      }
   });
   public static final Consumer<GameTestHelper> TEST_TIME_TYRANT_MAIL_CAN_BE_EQUIPPED = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ItemStack mail = new ItemStack((ItemLike)ModItems.TIME_TYRANT_MAIL.get());
         player.setItemSlot(EquipmentSlot.CHEST, mail);
         ItemStack equipped = player.getItemBySlot(EquipmentSlot.CHEST);
         if (!equipped.isEmpty() && equipped.getItem() == ModItems.TIME_TYRANT_MAIL.get()) {
            helper.succeed();
         } else {
            helper.fail("Time Tyrant's Mail was not properly equipped in chest slot");
         }
      } catch (Exception e) {
         helper.fail("Failed to equip Time Tyrant's Mail: " + e.getMessage());
      }
   });
   public static final Consumer<GameTestHelper> TEST_PLAYER_CAN_HOLD_CHRONOBLADE = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ItemStack chronoblade = new ItemStack((ItemLike)ModItems.CHRONOBLADE.get());
         player.setItemSlot(EquipmentSlot.MAINHAND, chronoblade);
         ItemStack equipped = player.getItemBySlot(EquipmentSlot.MAINHAND);
         if (!equipped.isEmpty() && equipped.getItem() == ModItems.CHRONOBLADE.get()) {
            helper.succeed();
         } else {
            helper.fail("Chronoblade was not properly equipped in main hand");
         }
      } catch (Exception e) {
         helper.fail("Failed to equip Chronoblade: " + e.getMessage());
      }
   });
   public static final Consumer<GameTestHelper> TEST_PLAYER_CAN_EQUIP_FULL_ARMOR_SET = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         player.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.CLOCKSTONE_HELMET.get()));
         player.setItemSlot(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModItems.CLOCKSTONE_CHESTPLATE.get()));
         player.setItemSlot(EquipmentSlot.LEGS, new ItemStack((ItemLike)ModItems.CLOCKSTONE_LEGGINGS.get()));
         player.setItemSlot(EquipmentSlot.FEET, new ItemStack((ItemLike)ModItems.CLOCKSTONE_BOOTS.get()));
         boolean helmetEquipped = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.CLOCKSTONE_HELMET.get();
         boolean chestplateEquipped = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.CLOCKSTONE_CHESTPLATE.get();
         boolean leggingsEquipped = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.CLOCKSTONE_LEGGINGS.get();
         boolean bootsEquipped = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.CLOCKSTONE_BOOTS.get();
         if (helmetEquipped && chestplateEquipped && leggingsEquipped && bootsEquipped) {
            helper.succeed();
         } else {
            helper.fail("Not all armor pieces were properly equipped");
         }
      } catch (Exception e) {
         helper.fail("Failed to equip full armor set: " + e.getMessage());
      }
   });
   public static final Consumer<GameTestHelper> TEST_PLAYER_INVENTORY_CAN_RECEIVE_ITEMS = helper -> helper.runAfterDelay(1L, () -> {
      try {
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ItemStack timeCrystal = new ItemStack((ItemLike)ModItems.TIME_CRYSTAL.get(), 10);
         boolean added = player.getInventory().add(timeCrystal);
         if (added) {
            boolean hasItems = player.getInventory().contains(new ItemStack((ItemLike)ModItems.TIME_CRYSTAL.get()));
            if (hasItems) {
               helper.succeed();
            } else {
               helper.fail("Items were added but not found in inventory");
            }
         } else {
            helper.fail("Failed to add items to player inventory");
         }
      } catch (Exception e) {
         helper.fail("Failed to manipulate player inventory: " + e.getMessage());
      }
   });

   private ChronoDawnGameTestLogic() {
   }
}
