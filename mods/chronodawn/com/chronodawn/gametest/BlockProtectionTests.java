package com.chronodawn.gametest;

import com.chronodawn.worldgen.protection.BlockProtectionHandler;
import com.chronodawn.worldgen.protection.PermanentProtectionHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public final class BlockProtectionTests {
   private BlockProtectionTests() {
   }

   public static <T> List<T> generateTests(MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();
      tests.add(factory.create("protection_area_registration", BlockProtectionTests::testAreaRegistration));
      tests.add(factory.create("protection_outside_area_not_protected", BlockProtectionTests::testOutsideAreaNotProtected));
      tests.add(factory.create("protection_boss_defeated_unprotects", BlockProtectionTests::testBossDefeatedUnprotects));
      tests.add(factory.create("protection_boss_defeated_at_unprotects", BlockProtectionTests::testBossDefeatedAtUnprotects));
      tests.add(factory.create("protection_permanent_not_unprotected", BlockProtectionTests::testPermanentNotUnprotected));
      tests.add(factory.create("protection_multiple_areas_independent", BlockProtectionTests::testMultipleAreasIndependent));
      tests.add(factory.create("protection_reset_clears_state", BlockProtectionTests::testResetClearsState));
      return tests;
   }

   private static void testAreaRegistration(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 5, center.getY() - 5, center.getZ() - 5, center.getX() + 5, center.getY() + 5, center.getZ() + 5);
         BlockProtectionHandler.registerProtectedArea(level, area, "test_area_1");
         if (BlockProtectionHandler.isProtected(level, center)) {
            helper.succeed();
         } else {
            helper.fail("Block at center should be protected after registration");
         }
      });
   }

   private static void testOutsideAreaNotProtected(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 2, center.getY() - 2, center.getZ() - 2, center.getX() + 2, center.getY() + 2, center.getZ() + 2);
         BlockProtectionHandler.registerProtectedArea(level, area, "test_area_2");
         BlockPos outside = helper.absolutePos(new BlockPos(15, 15, 15));
         if (!BlockProtectionHandler.isProtected(level, outside)) {
            helper.succeed();
         } else {
            helper.fail("Block outside protected area should not be protected");
         }
      });
   }

   private static void testBossDefeatedUnprotects(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 5, center.getY() - 5, center.getZ() - 5, center.getX() + 5, center.getY() + 5, center.getZ() + 5);
         Object uniqueId = "test_area_3";
         BlockProtectionHandler.registerProtectedArea(level, area, uniqueId);
         if (!BlockProtectionHandler.isProtected(level, center)) {
            helper.fail("Area should be protected before boss defeat");
         } else {
            BlockProtectionHandler.onBossDefeated(level, uniqueId);
            if (!BlockProtectionHandler.isProtected(level, center)) {
               helper.succeed();
            } else {
               helper.fail("Block should be unprotected after boss defeat");
            }
         }
      });
   }

   private static void testBossDefeatedAtUnprotects(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 5, center.getY() - 5, center.getZ() - 5, center.getX() + 5, center.getY() + 5, center.getZ() + 5);
         BlockProtectionHandler.registerProtectedArea(level, area, "test_area_4");
         if (!BlockProtectionHandler.isProtected(level, center)) {
            helper.fail("Area should be protected before boss defeat");
         } else {
            boolean found = BlockProtectionHandler.onBossDefeatedAt(level, center);
            if (!found) {
               helper.fail("onBossDefeatedAt() should find the protected area");
            } else {
               if (!BlockProtectionHandler.isProtected(level, center)) {
                  helper.succeed();
               } else {
                  helper.fail("Block should be unprotected after boss defeat at position");
               }
            }
         }
      });
   }

   private static void testPermanentNotUnprotected(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 5, center.getY() - 5, center.getZ() - 5, center.getX() + 5, center.getY() + 5, center.getZ() + 5);
         Object uniqueId = "test_permanent_area";
         PermanentProtectionHandler.registerProtectedArea(level, area, uniqueId);
         if (!PermanentProtectionHandler.isProtected(level, center)) {
            helper.fail("Permanent area should be protected after registration");
         } else {
            if (PermanentProtectionHandler.isProtected(level, center)) {
               helper.succeed();
            } else {
               helper.fail("Permanent protection should remain after any boss-related events");
            }
         }
      });
   }

   private static void testMultipleAreasIndependent(GameTestHelper helper) {
      helper.runAfterDelay(
         1L,
         () -> {
            BlockProtectionHandler.reset();
            PermanentProtectionHandler.reset();
            ServerLevel level = helper.getLevel();
            BlockPos center1 = helper.absolutePos(new BlockPos(3, 5, 3));
            BoundingBox area1 = new BoundingBox(
               center1.getX() - 2, center1.getY() - 2, center1.getZ() - 2, center1.getX() + 2, center1.getY() + 2, center1.getZ() + 2
            );
            BlockPos center2 = helper.absolutePos(new BlockPos(10, 5, 10));
            BoundingBox area2 = new BoundingBox(
               center2.getX() - 2, center2.getY() - 2, center2.getZ() - 2, center2.getX() + 2, center2.getY() + 2, center2.getZ() + 2
            );
            Object uniqueId1 = "test_multi_area_1";
            Object uniqueId2 = "test_multi_area_2";
            BlockProtectionHandler.registerProtectedArea(level, area1, uniqueId1);
            BlockProtectionHandler.registerProtectedArea(level, area2, uniqueId2);
            if (!BlockProtectionHandler.isProtected(level, center1)) {
               helper.fail("Area 1 should be protected");
            } else if (!BlockProtectionHandler.isProtected(level, center2)) {
               helper.fail("Area 2 should be protected");
            } else {
               BlockProtectionHandler.onBossDefeated(level, uniqueId1);
               if (BlockProtectionHandler.isProtected(level, center1)) {
                  helper.fail("Area 1 should be unprotected after boss defeat");
               } else if (!BlockProtectionHandler.isProtected(level, center2)) {
                  helper.fail("Area 2 should still be protected (independent)");
               } else {
                  helper.succeed();
               }
            }
         }
      );
   }

   private static void testResetClearsState(GameTestHelper helper) {
      helper.runAfterDelay(1L, () -> {
         BlockProtectionHandler.reset();
         PermanentProtectionHandler.reset();
         ServerLevel level = helper.getLevel();
         BlockPos center = helper.absolutePos(new BlockPos(5, 5, 5));
         BoundingBox area = new BoundingBox(center.getX() - 5, center.getY() - 5, center.getZ() - 5, center.getX() + 5, center.getY() + 5, center.getZ() + 5);
         BlockProtectionHandler.registerProtectedArea(level, area, "test_reset_1");
         PermanentProtectionHandler.registerProtectedArea(level, area, "test_reset_2");
         if (!BlockProtectionHandler.isProtected(level, center)) {
            helper.fail("Block protection should be active before reset");
         } else if (!PermanentProtectionHandler.isProtected(level, center)) {
            helper.fail("Permanent protection should be active before reset");
         } else {
            BlockProtectionHandler.reset();
            PermanentProtectionHandler.reset();
            if (BlockProtectionHandler.isProtected(level, center)) {
               helper.fail("Block protection should be cleared after reset");
            } else if (PermanentProtectionHandler.isProtected(level, center)) {
               helper.fail("Permanent protection should be cleared after reset");
            } else {
               helper.succeed();
            }
         }
      });
   }
}
