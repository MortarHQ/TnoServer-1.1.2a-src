package com.chronodawn.gametest;

import com.chronodawn.items.DeferredSpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class MobBehaviorTests {
   private static final BlockPos TEST_POS = new BlockPos(1, 2, 1);

   private MobBehaviorTests() {
   }

   public static <T> List<T> generateSpawnEggUsageTests(List<MobBehaviorTests.SpawnEggSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (MobBehaviorTests.SpawnEggSpec spec : specs) {
         String entityName = spec.expectedEntity().getId().getPath();
         String testName = "spawn_egg_usage_" + entityName;
         tests.add(
            factory.create(
               testName,
               helper -> helper.runAfterDelay(
                  1L,
                  () -> {
                     Item eggItem = (Item)spec.spawnEgg().get();
                     if (eggItem instanceof DeferredSpawnEggItem spawnEgg) {
                        ItemStack eggStack = new ItemStack(eggItem);
                        EntityType<?> entityType = spawnEgg.getType(eggStack);
                        if (entityType == null) {
                           helper.fail(entityName + " spawn egg returned null entity type");
                        } else {
                           EntityType<?> expectedType = (EntityType<?>)spec.expectedEntity().get();
                           if (entityType != expectedType) {
                              helper.fail(
                                 entityName
                                    + " spawn egg returned wrong entity type: "
                                    + BuiltInRegistries.ENTITY_TYPE.getKey(entityType)
                                    + ", expected: "
                                    + BuiltInRegistries.ENTITY_TYPE.getKey(expectedType)
                              );
                           } else {
                              Entity entity = helper.spawn(entityType, TEST_POS);
                              if (entity != null) {
                                 helper.succeed();
                              } else {
                                 helper.fail(entityName + " could not be spawned from spawn egg entity type");
                              }
                           }
                        }
                     } else {
                        helper.fail(entityName + " spawn egg is not a DeferredSpawnEggItem");
                     }
                  }
               )
            )
         );
      }

      return tests;
   }

   public static <T> List<T> generateMobCategoryTests(List<MobBehaviorTests.MobCategorySpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (MobBehaviorTests.MobCategorySpec spec : specs) {
         String entityName = spec.entity().getId().getPath();
         String testName = "mob_category_" + entityName;
         tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
            EntityType<?> entityType = (EntityType<?>)spec.entity().get();
            MobCategory actualCategory = entityType.getCategory();
            if (actualCategory == spec.expectedCategory()) {
               helper.succeed();
            } else {
               helper.fail(entityName + " has category " + actualCategory.getName() + ", expected " + spec.expectedCategory().getName());
            }
         })));
      }

      return tests;
   }

   public static <T> List<T> generateMobPersistenceTests(List<RegistrySupplier<EntityType<?>>> entities, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (RegistrySupplier<EntityType<?>> entitySupplier : entities) {
         String entityName = entitySupplier.getId().getPath();
         tests.add(factory.create("mob_persistence_" + entityName, helper -> {
            Entity entity = helper.spawn((EntityType)entitySupplier.get(), TEST_POS);
            helper.runAfterDelay(1L, () -> {
               if (entity instanceof Mob mob) {
                  if (!mob.requiresCustomPersistence()) {
                     helper.fail(entityName + " should return true for requiresCustomPersistence()");
                  } else if (mob.removeWhenFarAway(100.0)) {
                     helper.fail(entityName + " should return false for removeWhenFarAway()");
                  } else {
                     helper.succeed();
                  }
               } else {
                  helper.fail(entityName + " is not a Mob");
               }
            });
         }));
      }

      return tests;
   }

   public static <T> List<T> generateFlyingMobTests(List<RegistrySupplier<EntityType<?>>> entities, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (RegistrySupplier<EntityType<?>> entitySupplier : entities) {
         String entityName = entitySupplier.getId().getPath();
         tests.add(factory.create("mob_flying_" + entityName, helper -> {
            Entity entity = helper.spawn((EntityType)entitySupplier.get(), TEST_POS);
            helper.runAfterDelay(1L, () -> {
               if (entity instanceof Mob mob) {
                  if (!(mob.getMoveControl() instanceof FlyingMoveControl)) {
                     helper.fail(entityName + " should use FlyingMoveControl but uses " + mob.getMoveControl().getClass().getSimpleName());
                  } else {
                     helper.succeed();
                  }
               } else {
                  helper.fail(entityName + " is not a Mob");
               }
            });
         }));
      }

      return tests;
   }

   public record MobCategorySpec(RegistrySupplier<? extends EntityType<?>> entity, MobCategory expectedCategory) {
   }

   public record SpawnEggSpec(RegistrySupplier<Item> spawnEgg, RegistrySupplier<? extends EntityType<?>> expectedEntity) {
   }

   @FunctionalInterface
   public interface TestFactory<T> {
      T create(String var1, Consumer<GameTestHelper> var2);
   }
}
