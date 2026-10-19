package com.chronodawn.gametest;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModBlockEntityId;
import com.chronodawn.registry.ModBlockId;
import com.chronodawn.registry.ModEntityId;
import com.chronodawn.registry.ModItemId;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;

public final class RegistryConsistencyTests {
   private RegistryConsistencyTests() {
   }

   public static <T> List<T> generateItemTests(RegistryConsistencyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (ModItemId itemId : ModItemId.availableForCurrent()) {
         String testName = "registry_item_" + itemId.id();
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            ResourceLocation rl = CompatResourceLocation.create("chronodawn", itemId.id());
            if (!BuiltInRegistries.ITEM.containsKey(rl)) {
               helper.fail("Item not registered: " + itemId.id() + " (enum: " + itemId.name() + ")");
            } else {
               helper.succeed();
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateBlockTests(RegistryConsistencyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (ModBlockId blockId : ModBlockId.availableForCurrent()) {
         String testName = "registry_block_" + blockId.id();
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            ResourceLocation rl = CompatResourceLocation.create("chronodawn", blockId.id());
            if (!BuiltInRegistries.BLOCK.containsKey(rl)) {
               helper.fail("Block not registered: " + blockId.id() + " (enum: " + blockId.name() + ")");
            } else {
               helper.succeed();
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateEntityTests(RegistryConsistencyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (ModEntityId entityId : ModEntityId.availableForCurrent()) {
         String testName = "registry_entity_" + entityId.id();
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            ResourceLocation rl = CompatResourceLocation.create("chronodawn", entityId.id());
            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
               helper.fail("Entity not registered: " + entityId.id() + " (enum: " + entityId.name() + ")");
            } else {
               helper.succeed();
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateBlockEntityTests(RegistryConsistencyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (ModBlockEntityId blockEntityId : ModBlockEntityId.availableForCurrent()) {
         String testName = "registry_block_entity_" + blockEntityId.id();
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            ResourceLocation rl = CompatResourceLocation.create("chronodawn", blockEntityId.id());
            if (!BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(rl)) {
               helper.fail("BlockEntity not registered: " + blockEntityId.id() + " (enum: " + blockEntityId.name() + ")");
            } else {
               helper.succeed();
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateSummaryTest(RegistryConsistencyTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();
      tests.add(
         factory.create(
            "registry_consistency_summary",
            helper -> helper.runAfterDelay(
               1L,
               () -> {
                  List<String> missingItems = new ArrayList<>();
                  List<String> missingBlocks = new ArrayList<>();
                  List<String> missingEntities = new ArrayList<>();
                  List<String> missingBlockEntities = new ArrayList<>();

                  for (ModItemId itemId : ModItemId.availableForCurrent()) {
                     ResourceLocation rl = CompatResourceLocation.create("chronodawn", itemId.id());
                     if (!BuiltInRegistries.ITEM.containsKey(rl)) {
                        missingItems.add(itemId.id());
                     }
                  }

                  for (ModBlockId blockId : ModBlockId.availableForCurrent()) {
                     ResourceLocation rl = CompatResourceLocation.create("chronodawn", blockId.id());
                     if (!BuiltInRegistries.BLOCK.containsKey(rl)) {
                        missingBlocks.add(blockId.id());
                     }
                  }

                  for (ModEntityId entityId : ModEntityId.availableForCurrent()) {
                     ResourceLocation rl = CompatResourceLocation.create("chronodawn", entityId.id());
                     if (!BuiltInRegistries.ENTITY_TYPE.containsKey(rl)) {
                        missingEntities.add(entityId.id());
                     }
                  }

                  for (ModBlockEntityId blockEntityId : ModBlockEntityId.availableForCurrent()) {
                     ResourceLocation rl = CompatResourceLocation.create("chronodawn", blockEntityId.id());
                     if (!BuiltInRegistries.BLOCK_ENTITY_TYPE.containsKey(rl)) {
                        missingBlockEntities.add(blockEntityId.id());
                     }
                  }

                  if (missingItems.isEmpty() && missingBlocks.isEmpty() && missingEntities.isEmpty() && missingBlockEntities.isEmpty()) {
                     ChronoDawn.LOGGER
                        .info(
                           "Registry consistency check passed: {} items, {} blocks, {} entities, {} block entities",
                           new Object[]{
                              ModItemId.availableForCurrent().size(),
                              ModBlockId.availableForCurrent().size(),
                              ModEntityId.availableForCurrent().size(),
                              ModBlockEntityId.availableForCurrent().size()
                           }
                        );
                     helper.succeed();
                  } else {
                     StringBuilder sb = new StringBuilder("Registry consistency check failed:\n");
                     if (!missingItems.isEmpty()) {
                        sb.append("Missing items (").append(missingItems.size()).append("): ").append(String.join(", ", missingItems)).append("\n");
                     }

                     if (!missingBlocks.isEmpty()) {
                        sb.append("Missing blocks (").append(missingBlocks.size()).append("): ").append(String.join(", ", missingBlocks)).append("\n");
                     }

                     if (!missingEntities.isEmpty()) {
                        sb.append("Missing entities (").append(missingEntities.size()).append("): ").append(String.join(", ", missingEntities)).append("\n");
                     }

                     if (!missingBlockEntities.isEmpty()) {
                        sb.append("Missing block entities (").append(missingBlockEntities.size()).append("): ").append(String.join(", ", missingBlockEntities));
                     }

                     helper.fail(sb.toString());
                  }
               }
            )
         )
      );
      return tests;
   }

   @FunctionalInterface
   public interface TestFactory<T> {
      T create(String var1, Consumer<GameTestHelper> var2);
   }
}
