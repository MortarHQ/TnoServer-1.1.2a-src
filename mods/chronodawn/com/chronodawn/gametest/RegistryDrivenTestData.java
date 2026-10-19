package com.chronodawn.gametest;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModEntities;
import com.chronodawn.registry.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class RegistryDrivenTestData {
   private RegistryDrivenTestData() {
   }

   public static List<RegistrySupplier<Block>> getPlaceableBlocks() {
      return List.of(
         ModBlocks.CLOCKSTONE_ORE,
         ModBlocks.TIME_CRYSTAL_ORE,
         ModBlocks.CLOCKSTONE_BLOCK,
         ModBlocks.CLOCKSTONE_STAIRS,
         ModBlocks.CLOCKSTONE_SLAB,
         ModBlocks.CLOCKSTONE_WALL,
         ModBlocks.TEMPORAL_BRICKS,
         ModBlocks.TEMPORAL_BRICKS_STAIRS,
         ModBlocks.TEMPORAL_BRICKS_SLAB,
         ModBlocks.TEMPORAL_BRICKS_WALL,
         ModBlocks.CLOCKWORK_BLOCK,
         ModBlocks.TIME_CRYSTAL_BLOCK,
         ModBlocks.REVERSING_TIME_SANDSTONE,
         ModBlocks.TEMPORAL_MOSS,
         ModBlocks.FROZEN_TIME_ICE,
         ModBlocks.TIME_WHEAT_BALE,
         ModBlocks.CHRONO_MELON,
         ModBlocks.TIME_WOOD_LOG,
         ModBlocks.STRIPPED_TIME_WOOD_LOG,
         ModBlocks.TIME_WOOD,
         ModBlocks.STRIPPED_TIME_WOOD,
         ModBlocks.TIME_WOOD_LEAVES,
         ModBlocks.TIME_WOOD_PLANKS,
         ModBlocks.TIME_WOOD_STAIRS,
         ModBlocks.TIME_WOOD_SLAB,
         ModBlocks.TIME_WOOD_FENCE,
         ModBlocks.TIME_WOOD_DOOR,
         ModBlocks.TIME_WOOD_TRAPDOOR,
         ModBlocks.TIME_WOOD_FENCE_GATE,
         ModBlocks.TIME_WOOD_BUTTON,
         ModBlocks.TIME_WOOD_PRESSURE_PLATE,
         ModBlocks.DARK_TIME_WOOD_LOG,
         ModBlocks.STRIPPED_DARK_TIME_WOOD_LOG,
         ModBlocks.DARK_TIME_WOOD,
         ModBlocks.STRIPPED_DARK_TIME_WOOD,
         ModBlocks.DARK_TIME_WOOD_LEAVES,
         ModBlocks.DARK_TIME_WOOD_PLANKS,
         ModBlocks.DARK_TIME_WOOD_STAIRS,
         ModBlocks.DARK_TIME_WOOD_SLAB,
         ModBlocks.DARK_TIME_WOOD_FENCE,
         ModBlocks.DARK_TIME_WOOD_DOOR,
         ModBlocks.DARK_TIME_WOOD_TRAPDOOR,
         ModBlocks.DARK_TIME_WOOD_FENCE_GATE,
         ModBlocks.DARK_TIME_WOOD_BUTTON,
         ModBlocks.DARK_TIME_WOOD_PRESSURE_PLATE,
         ModBlocks.ANCIENT_TIME_WOOD_LOG,
         ModBlocks.STRIPPED_ANCIENT_TIME_WOOD_LOG,
         ModBlocks.ANCIENT_TIME_WOOD,
         ModBlocks.STRIPPED_ANCIENT_TIME_WOOD,
         ModBlocks.ANCIENT_TIME_WOOD_LEAVES,
         ModBlocks.ANCIENT_TIME_WOOD_PLANKS,
         ModBlocks.ANCIENT_TIME_WOOD_STAIRS,
         ModBlocks.ANCIENT_TIME_WOOD_SLAB,
         ModBlocks.ANCIENT_TIME_WOOD_FENCE,
         ModBlocks.ANCIENT_TIME_WOOD_DOOR,
         ModBlocks.ANCIENT_TIME_WOOD_TRAPDOOR,
         ModBlocks.ANCIENT_TIME_WOOD_FENCE_GATE,
         ModBlocks.ANCIENT_TIME_WOOD_BUTTON,
         ModBlocks.ANCIENT_TIME_WOOD_PRESSURE_PLATE,
         ModBlocks.TIMELESS_MUSHROOM,
         ModBlocks.PURPLE_TIME_BLOSSOM,
         ModBlocks.ORANGE_TIME_BLOSSOM,
         ModBlocks.PINK_TIME_BLOSSOM,
         ModBlocks.DAWN_BELL,
         ModBlocks.DUSK_BELL,
         ModBlocks.UNSTABLE_FUNGUS,
         ModBlocks.BOSS_ROOM_DOOR,
         ModBlocks.ENTROPY_CRYPT_TRAPDOOR,
         ModBlocks.TEMPORAL_PARTICLE_EMITTER,
         ModBlocks.DECORATIVE_WATER,
         ModBlocks.BOSS_ROOM_BOUNDARY_MARKER,
         ModBlocks.CLOCK_TOWER_TELEPORTER
      );
   }

   public static List<RegistrySupplier<EntityType<?>>> getSpawnableEntities() {
      return List.of(
         ModEntities.TIME_GUARDIAN,
         ModEntities.TIME_TYRANT,
         ModEntities.CHRONOS_WARDEN,
         ModEntities.CLOCKWORK_COLOSSUS,
         ModEntities.TEMPORAL_PHANTOM,
         ModEntities.ENTROPY_KEEPER,
         ModEntities.TEMPORAL_WRAITH,
         ModEntities.CLOCKWORK_SENTINEL,
         ModEntities.FLOQ,
         ModEntities.TIME_KEEPER,
         ModEntities.EPOCH_HUSK,
         ModEntities.FORGOTTEN_MINUTE,
         ModEntities.CHRONAL_LEECH,
         ModEntities.MOMENT_CREEPER,
         ModEntities.CHRONO_DAWN_BOAT,
         ModEntities.CHRONO_DAWN_CHEST_BOAT
      );
   }

   public static List<RegistryDrivenTestData.ToolSpec> getToolSpecs() {
      return List.of(
         new RegistryDrivenTestData.ToolSpec(ModItems.CLOCKSTONE_SWORD, 450),
         new RegistryDrivenTestData.ToolSpec(ModItems.CLOCKSTONE_PICKAXE, 450),
         new RegistryDrivenTestData.ToolSpec(ModItems.CLOCKSTONE_AXE, 450),
         new RegistryDrivenTestData.ToolSpec(ModItems.CLOCKSTONE_SHOVEL, 450),
         new RegistryDrivenTestData.ToolSpec(ModItems.CLOCKSTONE_HOE, 450),
         new RegistryDrivenTestData.ToolSpec(ModItems.ENHANCED_CLOCKSTONE_SWORD, 1200),
         new RegistryDrivenTestData.ToolSpec(ModItems.ENHANCED_CLOCKSTONE_PICKAXE, 1200),
         new RegistryDrivenTestData.ToolSpec(ModItems.ENHANCED_CLOCKSTONE_AXE, 1200),
         new RegistryDrivenTestData.ToolSpec(ModItems.ENHANCED_CLOCKSTONE_SHOVEL, 1200),
         new RegistryDrivenTestData.ToolSpec(ModItems.ENHANCED_CLOCKSTONE_HOE, 1200),
         new RegistryDrivenTestData.ToolSpec(ModItems.CHRONOBLADE, 2000),
         new RegistryDrivenTestData.ToolSpec(ModItems.SPATIALLY_LINKED_PICKAXE, 1561)
      );
   }

   public static List<RegistryDrivenTestData.ArmorSpec> getArmorSpecs() {
      return List.of(
         new RegistryDrivenTestData.ArmorSpec(ModItems.CLOCKSTONE_HELMET, EquipmentSlot.HEAD, 2),
         new RegistryDrivenTestData.ArmorSpec(ModItems.CLOCKSTONE_CHESTPLATE, EquipmentSlot.CHEST, 6),
         new RegistryDrivenTestData.ArmorSpec(ModItems.CLOCKSTONE_LEGGINGS, EquipmentSlot.LEGS, 5),
         new RegistryDrivenTestData.ArmorSpec(ModItems.CLOCKSTONE_BOOTS, EquipmentSlot.FEET, 2),
         new RegistryDrivenTestData.ArmorSpec(ModItems.ENHANCED_CLOCKSTONE_HELMET, EquipmentSlot.HEAD, 3),
         new RegistryDrivenTestData.ArmorSpec(ModItems.ENHANCED_CLOCKSTONE_CHESTPLATE, EquipmentSlot.CHEST, 7),
         new RegistryDrivenTestData.ArmorSpec(ModItems.ENHANCED_CLOCKSTONE_LEGGINGS, EquipmentSlot.LEGS, 6),
         new RegistryDrivenTestData.ArmorSpec(ModItems.ENHANCED_CLOCKSTONE_BOOTS, EquipmentSlot.FEET, 3)
      );
   }

   public static List<RegistryDrivenTestData.EntityAttributeSpec> getEntityAttributeSpecs() {
      return List.of(
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_GUARDIAN, "max_health", 200.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_GUARDIAN, "armor", 10.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_GUARDIAN, "attack_damage", 10.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_GUARDIAN, "knockback_resistance", 0.8),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_TYRANT, "max_health", 500.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_TYRANT, "armor", 15.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_TYRANT, "attack_damage", 18.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_TYRANT, "knockback_resistance", 1.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CHRONOS_WARDEN, "max_health", 180.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CHRONOS_WARDEN, "armor", 12.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CLOCKWORK_COLOSSUS, "max_health", 200.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CLOCKWORK_COLOSSUS, "knockback_resistance", 1.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TEMPORAL_PHANTOM, "max_health", 150.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TEMPORAL_PHANTOM, "attack_damage", 8.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.ENTROPY_KEEPER, "max_health", 160.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.ENTROPY_KEEPER, "armor", 6.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TEMPORAL_WRAITH, "max_health", 20.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TEMPORAL_WRAITH, "attack_damage", 4.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CLOCKWORK_SENTINEL, "max_health", 30.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CLOCKWORK_SENTINEL, "attack_damage", 6.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CLOCKWORK_SENTINEL, "armor", 5.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.FLOQ, "max_health", 16.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.FLOQ, "attack_damage", 3.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.TIME_KEEPER, "max_health", 20.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.EPOCH_HUSK, "max_health", 24.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.EPOCH_HUSK, "attack_damage", 5.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.FORGOTTEN_MINUTE, "max_health", 14.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.FORGOTTEN_MINUTE, "attack_damage", 4.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CHRONAL_LEECH, "max_health", 10.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.CHRONAL_LEECH, "attack_damage", 2.0),
         new RegistryDrivenTestData.EntityAttributeSpec(ModEntities.MOMENT_CREEPER, "max_health", 22.0)
      );
   }

   public static List<MobBehaviorTests.SpawnEggSpec> getSpawnEggSpecs() {
      return List.of(
         new MobBehaviorTests.SpawnEggSpec(ModItems.TEMPORAL_WRAITH_SPAWN_EGG, ModEntities.TEMPORAL_WRAITH),
         new MobBehaviorTests.SpawnEggSpec(ModItems.CLOCKWORK_SENTINEL_SPAWN_EGG, ModEntities.CLOCKWORK_SENTINEL),
         new MobBehaviorTests.SpawnEggSpec(ModItems.TIME_KEEPER_SPAWN_EGG, ModEntities.TIME_KEEPER),
         new MobBehaviorTests.SpawnEggSpec(ModItems.FLOQ_SPAWN_EGG, ModEntities.FLOQ),
         new MobBehaviorTests.SpawnEggSpec(ModItems.EPOCH_HUSK_SPAWN_EGG, ModEntities.EPOCH_HUSK),
         new MobBehaviorTests.SpawnEggSpec(ModItems.FORGOTTEN_MINUTE_SPAWN_EGG, ModEntities.FORGOTTEN_MINUTE),
         new MobBehaviorTests.SpawnEggSpec(ModItems.CHRONAL_LEECH_SPAWN_EGG, ModEntities.CHRONAL_LEECH),
         new MobBehaviorTests.SpawnEggSpec(ModItems.MOMENT_CREEPER_SPAWN_EGG, ModEntities.MOMENT_CREEPER),
         new MobBehaviorTests.SpawnEggSpec(ModItems.TIME_GUARDIAN_SPAWN_EGG, ModEntities.TIME_GUARDIAN),
         new MobBehaviorTests.SpawnEggSpec(ModItems.TIME_TYRANT_SPAWN_EGG, ModEntities.TIME_TYRANT),
         new MobBehaviorTests.SpawnEggSpec(ModItems.CHRONOS_WARDEN_SPAWN_EGG, ModEntities.CHRONOS_WARDEN),
         new MobBehaviorTests.SpawnEggSpec(ModItems.CLOCKWORK_COLOSSUS_SPAWN_EGG, ModEntities.CLOCKWORK_COLOSSUS),
         new MobBehaviorTests.SpawnEggSpec(ModItems.TEMPORAL_PHANTOM_SPAWN_EGG, ModEntities.TEMPORAL_PHANTOM),
         new MobBehaviorTests.SpawnEggSpec(ModItems.ENTROPY_KEEPER_SPAWN_EGG, ModEntities.ENTROPY_KEEPER)
      );
   }

   public static List<MobBehaviorTests.MobCategorySpec> getMobCategorySpecs() {
      return List.of(
         new MobBehaviorTests.MobCategorySpec(ModEntities.TIME_GUARDIAN, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.TIME_TYRANT, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CHRONOS_WARDEN, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CLOCKWORK_COLOSSUS, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.TEMPORAL_PHANTOM, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.ENTROPY_KEEPER, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.TEMPORAL_WRAITH, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CLOCKWORK_SENTINEL, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.FLOQ, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.EPOCH_HUSK, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.FORGOTTEN_MINUTE, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CHRONAL_LEECH, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.MOMENT_CREEPER, MobCategory.MONSTER),
         new MobBehaviorTests.MobCategorySpec(ModEntities.TIME_KEEPER, MobCategory.CREATURE),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CHRONO_DAWN_BOAT, MobCategory.MISC),
         new MobBehaviorTests.MobCategorySpec(ModEntities.CHRONO_DAWN_CHEST_BOAT, MobCategory.MISC)
      );
   }

   public static List<RegistrySupplier<EntityType<?>>> getPersistentEntities() {
      return List.of(ModEntities.TIME_KEEPER);
   }

   public static List<RegistrySupplier<EntityType<?>>> getFlyingEntities() {
      return List.of(ModEntities.FORGOTTEN_MINUTE);
   }

   public static List<AdvancementTests.AdvancementSpec> getAdvancementSpecs() {
      return List.of(
         new AdvancementTests.AdvancementSpec("root"),
         new AdvancementTests.AdvancementSpec("story/us1/portal_creation", "root"),
         new AdvancementTests.AdvancementSpec("story/us1/dimension_entry", "story/us1/portal_creation"),
         new AdvancementTests.AdvancementSpec("story/us2/time_guardian_defeat", "story/us1/dimension_entry"),
         new AdvancementTests.AdvancementSpec("story/us2/desert_clock_tower", "story/us1/dimension_entry"),
         new AdvancementTests.AdvancementSpec("story/us2/time_manipulation_tools", "story/us2/desert_clock_tower"),
         new AdvancementTests.AdvancementSpec("story/us2/chrono_aegis_obtained", "story/us2/time_guardian_defeat"),
         new AdvancementTests.AdvancementSpec("story/us3/master_clock_access", "story/us2/time_guardian_defeat"),
         new AdvancementTests.AdvancementSpec("story/us3/time_tyrant_defeat", "story/us3/master_clock_access"),
         new AdvancementTests.AdvancementSpec("story/us3/ultimate_artifacts", "story/us3/time_tyrant_defeat"),
         new AdvancementTests.AdvancementSpec("equipment/tier1_full_set", "story/us1/dimension_entry"),
         new AdvancementTests.AdvancementSpec("equipment/tier2_full_set", "story/us2/desert_clock_tower"),
         new AdvancementTests.AdvancementSpec("exploration/first_time_crystal_ore", "story/us1/dimension_entry"),
         new AdvancementTests.AdvancementSpec("mobs/chronos_warden_defeated", "story/us2/time_guardian_defeat"),
         new AdvancementTests.AdvancementSpec("mobs/clockwork_colossus_defeated", "story/us2/time_guardian_defeat"),
         new AdvancementTests.AdvancementSpec("mobs/temporal_phantom_defeated", "story/us2/time_guardian_defeat"),
         new AdvancementTests.AdvancementSpec("mobs/entropy_keeper_defeated", "story/us2/time_guardian_defeat")
      );
   }

   public record ArmorSpec(RegistrySupplier<Item> item, EquipmentSlot slot, int expectedDefense) {
   }

   public record EntityAttributeSpec(RegistrySupplier<? extends EntityType<?>> entity, String attributeName, double expectedValue) {
   }

   public record ToolSpec(RegistrySupplier<Item> item, int expectedDurability) {
   }
}
