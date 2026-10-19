package com.chronodawn.gametest;

import com.chronodawn.compat.CompatAdvancementHelper;
import com.chronodawn.compat.CompatGameTestHelper;
import com.chronodawn.compat.CompatResourceLocation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class AdvancementTests {
   private AdvancementTests() {
   }

   public static <T> List<T> generateAdvancementLoadedTests(List<AdvancementTests.AdvancementSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (AdvancementTests.AdvancementSpec spec : specs) {
         String testName = "advancement_loaded_" + spec.id().replace('/', '_');
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            MinecraftServer server = helper.getLevel().getServer();
            ResourceLocation advId = CompatResourceLocation.create("chronodawn", spec.id());
            if (CompatAdvancementHelper.advancementExists(server, advId)) {
               helper.succeed();
            } else {
               helper.fail("Advancement not loaded: chronodawn:" + spec.id());
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateAdvancementGrantTests(List<AdvancementTests.AdvancementSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (AdvancementTests.AdvancementSpec spec : specs) {
         String testName = "advancement_grant_" + spec.id().replace('/', '_');
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            MinecraftServer server = helper.getLevel().getServer();
            ServerPlayer player = CompatGameTestHelper.makeMockServerPlayer(helper);
            if (player == null) {
               helper.succeed();
            } else {
               ResourceLocation advId = CompatResourceLocation.create("chronodawn", spec.id());
               if (!CompatAdvancementHelper.advancementExists(server, advId)) {
                  helper.fail("Advancement not found: chronodawn:" + spec.id());
               } else if (CompatAdvancementHelper.hasAdvancement(server, player, advId)) {
                  helper.fail("Player already has advancement before grant: " + spec.id());
               } else {
                  boolean granted = CompatAdvancementHelper.grantAdvancement(server, player, advId);
                  if (!granted) {
                     helper.fail("Failed to grant advancement: " + spec.id());
                  } else {
                     if (CompatAdvancementHelper.hasAdvancement(server, player, advId)) {
                        helper.succeed();
                     } else {
                        helper.fail("Advancement was granted but hasAdvancement returned false: " + spec.id());
                     }
                  }
               }
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateAdvancementParentTests(List<AdvancementTests.AdvancementSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (AdvancementTests.AdvancementSpec spec : specs) {
         if (spec.parentId() != null) {
            String testName = "advancement_parent_" + spec.id().replace('/', '_');
            tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
               MinecraftServer server = helper.getLevel().getServer();
               ResourceLocation advId = CompatResourceLocation.create("chronodawn", spec.id());
               ResourceLocation parentAdvId = CompatResourceLocation.create("chronodawn", spec.parentId());
               if (!CompatAdvancementHelper.advancementExists(server, advId)) {
                  helper.fail("Advancement not found: chronodawn:" + spec.id());
               } else {
                  if (CompatAdvancementHelper.advancementExists(server, parentAdvId)) {
                     helper.succeed();
                  } else {
                     helper.fail("Parent advancement not found: chronodawn:" + spec.parentId() + " (referenced by: chronodawn:" + spec.id() + ")");
                  }
               }
            })));
         }
      }

      return tests;
   }

   public static <T> List<T> generateAdvancementIsolationTests(List<AdvancementTests.AdvancementSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (AdvancementTests.AdvancementSpec spec : specs) {
         if (spec.parentId() != null) {
            String testName = "advancement_isolation_" + spec.id().replace('/', '_');
            tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
               MinecraftServer server = helper.getLevel().getServer();
               ServerPlayer player = CompatGameTestHelper.makeMockServerPlayer(helper);
               if (player == null) {
                  helper.succeed();
               } else {
                  ResourceLocation advId = CompatResourceLocation.create("chronodawn", spec.id());
                  ResourceLocation parentAdvId = CompatResourceLocation.create("chronodawn", spec.parentId());
                  if (!CompatAdvancementHelper.advancementExists(server, advId)) {
                     helper.fail("Advancement not found: chronodawn:" + spec.id());
                  } else if (!CompatAdvancementHelper.advancementExists(server, parentAdvId)) {
                     helper.fail("Parent advancement not found: chronodawn:" + spec.parentId());
                  } else {
                     CompatAdvancementHelper.grantAdvancement(server, player, advId);
                     if (CompatAdvancementHelper.hasAdvancement(server, player, parentAdvId)) {
                        helper.fail("Granting child advancement " + spec.id() + " should not grant parent " + spec.parentId());
                     } else {
                        helper.succeed();
                     }
                  }
               }
            })));
         }
      }

      return tests;
   }

   public record AdvancementSpec(String id, String parentId) {
      public AdvancementSpec(String id) {
         this(id, null);
      }
   }
}
