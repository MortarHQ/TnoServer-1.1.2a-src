package com.chronodawn.gametest;

import com.chronodawn.gametest.boss.BossFightTestLogic;
import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public final class RegistryDrivenTestGenerator {
   public static final BlockPos TEST_POS = new BlockPos(1, 2, 1);
   private static final Map<String, String> ID_OVERRIDES = Map.of(
      "FRUIT_OF_TIME_BLOCK", "fruit_of_time", "CHRONO_DAWN_BOAT", "chronodawn_boat", "CHRONO_DAWN_CHEST_BOAT", "chronodawn_chest_boat"
   );

   private RegistryDrivenTestGenerator() {
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockPlacementTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistrySupplier<Block> blockSupplier : RegistryDrivenTestData.getPlaceableBlocks()) {
         String registryName = blockSupplier.getId().getPath();
         String testName = "block_placement_" + registryName;
         tests.add(new RegistryDrivenTestGenerator.NamedTest(testName, helper -> {
            helper.setBlock(TEST_POS, (Block)blockSupplier.get());
            helper.succeedWhenBlockPresent((Block)blockSupplier.get(), TEST_POS);
         }));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateEntitySpawnTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistrySupplier<EntityType<?>> entitySupplier : RegistryDrivenTestData.getSpawnableEntities()) {
         String registryName = entitySupplier.getId().getPath();
         String testName = "entity_spawn_" + registryName;
         tests.add(new RegistryDrivenTestGenerator.NamedTest(testName, helper -> {
            helper.spawn((EntityType)entitySupplier.get(), TEST_POS);
            helper.succeedWhenEntityPresent((EntityType)entitySupplier.get(), TEST_POS);
         }));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateToolDurabilityTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistryDrivenTestData.ToolSpec spec : RegistryDrivenTestData.getToolSpecs()) {
         String registryName = spec.item().getId().getPath();
         String testName = "tool_durability_" + registryName;
         tests.add(new RegistryDrivenTestGenerator.NamedTest(testName, helper -> {
            ItemStack stack = new ItemStack((ItemLike)spec.item().get());
            helper.runAfterDelay(1L, () -> {
               int actual = stack.getMaxDamage();
               if (actual == spec.expectedDurability()) {
                  helper.succeed();
               } else {
                  helper.fail(registryName + " durability was " + actual + ", expected " + spec.expectedDurability());
               }
            });
         }));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateArmorDefenseTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistryDrivenTestData.ArmorSpec spec : RegistryDrivenTestData.getArmorSpecs()) {
         String registryName = spec.item().getId().getPath();
         String testName = "armor_defense_" + registryName;
         tests.add(new RegistryDrivenTestGenerator.NamedTest(testName, helper -> helper.runAfterDelay(1L, () -> {
            if (spec.item().get() instanceof ArmorItem armorItem) {
               int actual = armorItem.getDefense();
               if (actual == spec.expectedDefense()) {
                  helper.succeed();
               } else {
                  helper.fail(registryName + " defense was " + actual + ", expected " + spec.expectedDefense());
               }
            } else {
               helper.fail(registryName + " is not an ArmorItem");
            }
         })));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateEntityAttributeTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistryDrivenTestData.EntityAttributeSpec spec : RegistryDrivenTestData.getEntityAttributeSpecs()) {
         String entityName = spec.entity().getId().getPath();
         String testName = "entity_attr_" + entityName + "_" + spec.attributeName();
         tests.add(new RegistryDrivenTestGenerator.NamedTest(testName, helper -> {
            Entity entity = helper.spawn((EntityType)spec.entity().get(), TEST_POS);
            helper.runAfterDelay(1L, () -> {
               if (entity instanceof LivingEntity living) {
                  double actual = getAttributeValue(living, spec.attributeName());
                  if (Math.abs(actual - spec.expectedValue()) < 0.1) {
                     helper.succeed();
                  } else {
                     helper.fail(entityName + " " + spec.attributeName() + " was " + actual + ", expected " + spec.expectedValue());
                  }
               } else {
                  helper.fail(entityName + " is not a LivingEntity");
               }
            });
         }));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBossFightTests() {
      return BossFightTestLogic.generateTests();
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateAdvancementLoadedTests() {
      return AdvancementTests.generateAdvancementLoadedTests(RegistryDrivenTestData.getAdvancementSpecs(), RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateAdvancementGrantTests() {
      return AdvancementTests.generateAdvancementGrantTests(RegistryDrivenTestData.getAdvancementSpecs(), RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateAdvancementParentTests() {
      return AdvancementTests.generateAdvancementParentTests(RegistryDrivenTestData.getAdvancementSpecs(), RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateAdvancementIsolationTests() {
      return AdvancementTests.generateAdvancementIsolationTests(RegistryDrivenTestData.getAdvancementSpecs(), RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateStructureTests() {
      return StructureTests.generateStructureTests(StructureTests.getStructureSpecs(), RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockProtectionTests() {
      return BlockProtectionTests.generateTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateFadedPlainsTests() {
      return FadedPlainsTests.generateTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateItemIdTests() {
      return generateRegistryIdTests(ModItems.class, "item_id_");
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockIdTests() {
      return generateRegistryIdTests(ModBlocks.class, "block_id_");
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateEntityIdTests() {
      return generateRegistryIdTests(ModEntities.class, "entity_id_");
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateSpawnEggEntityTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (Field field : ModItems.class.getDeclaredFields()) {
         if (isRegistrySupplierField(field)) {
            String fieldName = field.getName();
            if (fieldName.endsWith("_SPAWN_EGG")) {
               String expectedEntityId = fieldName.substring(0, fieldName.length() - "_SPAWN_EGG".length()).toLowerCase();
               tests.add(new RegistryDrivenTestGenerator.NamedTest("spawn_egg_entity_" + expectedEntityId, helper -> helper.runAfterDelay(1L, () -> {
                  ResourceLocation entityId = ResourceLocation.fromNamespaceAndPath("chronodawn", expectedEntityId);
                  if (BuiltInRegistries.ENTITY_TYPE.containsKey(entityId)) {
                     helper.succeed();
                  } else {
                     helper.fail("Spawn egg " + fieldName + " expects entity \"" + expectedEntityId + "\" but it is not registered");
                  }
               })));
            }
         }
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateFoodPropertyTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (Field field : ModItems.class.getDeclaredFields()) {
         if (isRegistrySupplierField(field)) {
            String fieldName = field.getName();
            String expectedId = fieldName.toLowerCase();
            tests.add(new RegistryDrivenTestGenerator.NamedTest("food_check_" + expectedId, helper -> helper.runAfterDelay(1L, () -> {
               try {
                  RegistrySupplier<Item> supplier = (RegistrySupplier<Item>)field.get(null);
                  ItemStack stack = new ItemStack((ItemLike)supplier.get());
                  FoodProperties food = (FoodProperties)stack.get(DataComponents.FOOD);
                  if (food == null) {
                     helper.succeed();
                     return;
                  }

                  if (food.nutrition() <= 0) {
                     helper.fail(fieldName + " is food but has nutrition=" + food.nutrition() + " (expected > 0)");
                  } else if (food.saturation() < 0.0F) {
                     helper.fail(fieldName + " is food but has saturation=" + food.saturation() + " (expected >= 0)");
                  } else {
                     helper.succeed();
                  }
               } catch (Exception e) {
                  helper.fail("Failed to check food for " + fieldName + ": " + e.getMessage());
               }
            })));
         }
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockItemConsistencyTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (Field field : ModBlocks.class.getDeclaredFields()) {
         if (isRegistrySupplierField(field)) {
            String fieldName = field.getName();
            String expectedId = fieldName.toLowerCase();
            tests.add(new RegistryDrivenTestGenerator.NamedTest("block_item_" + expectedId, helper -> helper.runAfterDelay(1L, () -> {
               try {
                  RegistrySupplier<?> supplier = (RegistrySupplier<?>)field.get(null);
                  ResourceLocation blockId = supplier.getId();
                  if (BuiltInRegistries.ITEM.containsKey(blockId)) {
                     helper.succeed();
                  } else {
                     boolean hasItemField = false;

                     for (Field itemField : ModItems.class.getDeclaredFields()) {
                        if (itemField.getName().equals(fieldName) && isRegistrySupplierField(itemField)) {
                           hasItemField = true;
                           break;
                        }
                     }

                     if (hasItemField) {
                        helper.fail(fieldName + " has a ModItems field but no item registered with ID \"" + blockId + "\"");
                     } else {
                        helper.succeed();
                     }
                  }
               } catch (Exception e) {
                  helper.fail("Failed to check block-item for " + fieldName + ": " + e.getMessage());
               }
            })));
         }
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateEquipmentStackSizeTests() {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (RegistryDrivenTestData.ToolSpec spec : RegistryDrivenTestData.getToolSpecs()) {
         String name = spec.item().getId().getPath();
         tests.add(new RegistryDrivenTestGenerator.NamedTest("stack_size_" + name, helper -> helper.runAfterDelay(1L, () -> {
            ItemStack stack = new ItemStack((ItemLike)spec.item().get());
            int maxStack = stack.getMaxStackSize();
            if (maxStack == 1) {
               helper.succeed();
            } else {
               helper.fail(name + " maxStackSize=" + maxStack + ", expected 1");
            }
         })));
      }

      for (RegistryDrivenTestData.ArmorSpec spec : RegistryDrivenTestData.getArmorSpecs()) {
         String name = spec.item().getId().getPath();
         tests.add(new RegistryDrivenTestGenerator.NamedTest("stack_size_" + name, helper -> helper.runAfterDelay(1L, () -> {
            ItemStack stack = new ItemStack((ItemLike)spec.item().get());
            int maxStack = stack.getMaxStackSize();
            if (maxStack == 1) {
               helper.succeed();
            } else {
               helper.fail(name + " maxStackSize=" + maxStack + ", expected 1");
            }
         })));
      }

      return tests;
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateItemRegistryConsistencyTests() {
      return RegistryConsistencyTests.generateItemTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockRegistryConsistencyTests() {
      return RegistryConsistencyTests.generateBlockTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateEntityRegistryConsistencyTests() {
      return RegistryConsistencyTests.generateEntityTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateBlockEntityRegistryConsistencyTests() {
      return RegistryConsistencyTests.generateBlockEntityTests(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateRegistryConsistencySummaryTest() {
      return RegistryConsistencyTests.generateSummaryTest(RegistryDrivenTestGenerator.NamedTest::new);
   }

   public static List<RegistryDrivenTestGenerator.NamedTest> generateAllTests() {
      List<RegistryDrivenTestGenerator.NamedTest> all = new ArrayList<>();
      all.addAll(generateBlockPlacementTests());
      all.addAll(generateEntitySpawnTests());
      all.addAll(generateToolDurabilityTests());
      all.addAll(generateArmorDefenseTests());
      all.addAll(generateEntityAttributeTests());
      all.addAll(generateItemIdTests());
      all.addAll(generateBlockIdTests());
      all.addAll(generateEntityIdTests());
      all.addAll(generateSpawnEggEntityTests());
      all.addAll(generateFoodPropertyTests());
      all.addAll(generateBlockItemConsistencyTests());
      all.addAll(generateEquipmentStackSizeTests());
      all.addAll(TranslationKeyTests.generate(ModItems.class, ID_OVERRIDES, RegistryDrivenTestGenerator.NamedTest::new));
      all.addAll(generateBossFightTests());
      all.addAll(MobBehaviorTests.generateSpawnEggUsageTests(RegistryDrivenTestData.getSpawnEggSpecs(), RegistryDrivenTestGenerator.NamedTest::new));
      all.addAll(MobBehaviorTests.generateMobCategoryTests(RegistryDrivenTestData.getMobCategorySpecs(), RegistryDrivenTestGenerator.NamedTest::new));
      all.addAll(MobBehaviorTests.generateMobPersistenceTests(RegistryDrivenTestData.getPersistentEntities(), RegistryDrivenTestGenerator.NamedTest::new));
      all.addAll(MobBehaviorTests.generateFlyingMobTests(RegistryDrivenTestData.getFlyingEntities(), RegistryDrivenTestGenerator.NamedTest::new));
      all.addAll(generateAdvancementLoadedTests());
      all.addAll(generateAdvancementGrantTests());
      all.addAll(generateAdvancementParentTests());
      all.addAll(generateAdvancementIsolationTests());
      all.addAll(generateStructureTests());
      all.addAll(generateBlockProtectionTests());
      all.addAll(generateFadedPlainsTests());
      all.addAll(generateItemRegistryConsistencyTests());
      all.addAll(generateBlockRegistryConsistencyTests());
      all.addAll(generateEntityRegistryConsistencyTests());
      all.addAll(generateBlockEntityRegistryConsistencyTests());
      all.addAll(generateRegistryConsistencySummaryTest());
      return all;
   }

   private static boolean isRegistrySupplierField(Field field) {
      return Modifier.isPublic(field.getModifiers())
         && Modifier.isStatic(field.getModifiers())
         && Modifier.isFinal(field.getModifiers())
         && RegistrySupplier.class.isAssignableFrom(field.getType());
   }

   private static List<RegistryDrivenTestGenerator.NamedTest> generateRegistryIdTests(Class<?> registryClass, String testPrefix) {
      List<RegistryDrivenTestGenerator.NamedTest> tests = new ArrayList<>();

      for (Field field : registryClass.getDeclaredFields()) {
         if (isRegistrySupplierField(field)) {
            String fieldName = field.getName();
            String expectedId = ID_OVERRIDES.getOrDefault(fieldName, fieldName.toLowerCase());
            tests.add(new RegistryDrivenTestGenerator.NamedTest(testPrefix + expectedId, helper -> helper.runAfterDelay(1L, () -> {
               try {
                  RegistrySupplier<?> supplier = (RegistrySupplier<?>)field.get(null);
                  String actualId = supplier.getId().getPath();
                  if (actualId.equals(expectedId)) {
                     helper.succeed();
                  } else {
                     helper.fail(fieldName + " has ID \"" + actualId + "\", expected \"" + expectedId + "\"");
                  }
               } catch (Exception e) {
                  helper.fail("Failed to check ID for " + fieldName + ": " + e.getMessage());
               }
            })));
         }
      }

      return tests;
   }

   private static double getAttributeValue(LivingEntity entity, String attributeName) {
      return switch (attributeName) {
         case "max_health" -> entity.getAttributeValue(Attributes.MAX_HEALTH);
         case "armor" -> entity.getAttributeValue(Attributes.ARMOR);
         case "attack_damage" -> entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
         case "knockback_resistance" -> entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
         default -> throw new IllegalArgumentException("Unknown attribute: " + attributeName);
      };
   }

   public record NamedTest(String name, Consumer<GameTestHelper> test, int timeoutTicks) {
      public NamedTest(String name, Consumer<GameTestHelper> test) {
         this(name, test, 100);
      }
   }
}
