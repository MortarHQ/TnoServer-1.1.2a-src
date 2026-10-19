package com.chronodawn.gametest;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModBlockId;
import com.chronodawn.registry.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class StructureTests {
   private StructureTests() {
   }

   public static List<StructureTests.StructureSpec> getStructureSpecs() {
      return List.of(
         new StructureTests.StructureSpec(
            "ancient_ruins",
            5,
            3,
            5,
            List.of(
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 2),
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_PARTICLE_EMITTER.id(), ModBlocks.TEMPORAL_PARTICLE_EMITTER, 20),
               new StructureTests.BlockRequirement(ModBlockId.CLOCKSTONE_ORE.id(), ModBlocks.CLOCKSTONE_ORE, 30)
            )
         ),
         new StructureTests.StructureSpec(
            "desert_clock_tower",
            21,
            48,
            21,
            List.of(
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 3),
               new StructureTests.BlockRequirement(ModBlockId.CLOCK_TOWER_TELEPORTER.id(), ModBlocks.CLOCK_TOWER_TELEPORTER, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "time_keeper_village",
            11,
            8,
            11,
            List.of(
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 2),
               new StructureTests.BlockRequirement("barrel", () -> Blocks.BARREL, 1),
               new StructureTests.BlockRequirement(ModBlockId.TIME_CRYSTAL_BLOCK.id(), ModBlocks.TIME_CRYSTAL_BLOCK, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "time_well",
            3,
            5,
            3,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_COBBLESTONE.id(), ModBlocks.TEMPORAL_COBBLESTONE, 8),
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_AMBER_ORE.id(), ModBlocks.TEMPORAL_AMBER_ORE, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "lost_adventurer_memorial",
            3,
            3,
            3,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.MOSSY_TEMPORAL_COBBLESTONE_SLAB.id(), ModBlocks.MOSSY_TEMPORAL_COBBLESTONE_SLAB, 2),
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_STONE_PRESSURE_PLATE.id(), ModBlocks.TEMPORAL_STONE_PRESSURE_PLATE, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "lost_adventurer_memorial_snowy",
            3,
            3,
            3,
            List.of(
               new StructureTests.BlockRequirement("snow", () -> Blocks.SNOW, 4),
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_STONE_PRESSURE_PLATE.id(), ModBlocks.TEMPORAL_STONE_PRESSURE_PLATE, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "time_cairn", 3, 3, 3, List.of(new StructureTests.BlockRequirement(ModBlockId.CLOCKWORK_DIAL.id(), ModBlocks.CLOCKWORK_DIAL, 1))
         ),
         new StructureTests.StructureSpec(
            "watchmaker_camp",
            5,
            2,
            5,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.TIME_WOOD_FENCE.id(), ModBlocks.TIME_WOOD_FENCE, 8),
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 1),
               new StructureTests.BlockRequirement("campfire", () -> Blocks.CAMPFIRE, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "old_sundial",
            3,
            3,
            3,
            List.of(
               new StructureTests.BlockRequirement("smooth_stone", () -> Blocks.SMOOTH_STONE, 9),
               new StructureTests.BlockRequirement("iron_bars", () -> Blocks.IRON_BARS, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "hourglass_monolith",
            3,
            5,
            3,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_SANDSTONE.id(), ModBlocks.TEMPORAL_SANDSTONE, 18),
               new StructureTests.BlockRequirement(ModBlockId.TEMPORAL_SANDSTONE_WALL.id(), ModBlocks.TEMPORAL_SANDSTONE_WALL, 16),
               new StructureTests.BlockRequirement("sand", () -> Blocks.SAND, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "upside_down_tree",
            4,
            6,
            4,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.TIME_WOOD_LOG.id(), ModBlocks.TIME_WOOD_LOG, 8),
               new StructureTests.BlockRequirement(ModBlockId.TIME_WOOD_LEAVES.id(), ModBlocks.TIME_WOOD_LEAVES, 7)
            )
         ),
         new StructureTests.StructureSpec(
            "forgotten_library",
            35,
            15,
            35,
            List.of(
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 7),
               new StructureTests.BlockRequirement("barrel", () -> Blocks.BARREL, 1),
               new StructureTests.BlockRequirement("enchanting_table", () -> Blocks.ENCHANTING_TABLE, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "master_clock_surface",
            15,
            10,
            15,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_DOOR.id(), ModBlocks.BOSS_ROOM_DOOR, 1),
               new StructureTests.BlockRequirement("dropper", () -> Blocks.DROPPER, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "master_clock_stairs",
            15,
            8,
            15,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2)
            )
         ),
         new StructureTests.StructureSpec(
            "master_clock_stairs_bottom",
            15,
            12,
            15,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2)
            )
         ),
         new StructureTests.StructureSpec(
            "master_clock_corridor",
            15,
            15,
            15,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2)
            )
         ),
         new StructureTests.StructureSpec(
            "master_clock_boss_room",
            35,
            20,
            35,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_DOOR.id(), ModBlocks.BOSS_ROOM_DOOR, 1),
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1)
            )
         ),
         new StructureTests.StructureSpec("guardian_vault_entrance", 7, 6, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1))),
         new StructureTests.StructureSpec(
            "guardian_vault_main",
            30,
            20,
            25,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_DOOR.id(), ModBlocks.BOSS_ROOM_DOOR, 1),
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 1),
               new StructureTests.BlockRequirement("barrel", () -> Blocks.BARREL, 9),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1)
            )
         ),
         new StructureTests.StructureSpec("entropy_crypt_entrance", 7, 6, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1))),
         new StructureTests.StructureSpec(
            "entropy_crypt_main",
            26,
            20,
            25,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement(ModBlockId.ENTROPY_CRYPT_TRAPDOOR.id(), ModBlocks.ENTROPY_CRYPT_TRAPDOOR, 1),
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 3),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1)
            )
         ),
         new StructureTests.StructureSpec("entropy_crypt_stairs", 5, 20, 5, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2))),
         new StructureTests.StructureSpec(
            "clockwork_depths_tower",
            11,
            15,
            11,
            List.of(
               new StructureTests.BlockRequirement("campfire", () -> Blocks.CAMPFIRE, 4), new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "clockwork_depths_gearshaft", 13, 21, 13, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2))
         ),
         new StructureTests.StructureSpec(
            "clockwork_depths_engine_room",
            30,
            17,
            30,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2),
               new StructureTests.BlockRequirement("crimson_wall_sign", () -> Blocks.CRIMSON_WALL_SIGN, 4)
            )
         ),
         new StructureTests.StructureSpec(
            "clockwork_depths_archive_vault",
            7,
            9,
            7,
            List.of(
               new StructureTests.BlockRequirement("chest", () -> Blocks.CHEST, 1),
               new StructureTests.BlockRequirement("barrel", () -> Blocks.BARREL, 4),
               new StructureTests.BlockRequirement("anvil", () -> Blocks.ANVIL, 1),
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1)
            )
         ),
         new StructureTests.StructureSpec("phantom_catacombs_entrance", 9, 7, 9, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1))),
         new StructureTests.StructureSpec(
            "phantom_catacombs_corridor", 5, 40, 5, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2))
         ),
         new StructureTests.StructureSpec(
            "phantom_catacombs_boss_room",
            21,
            9,
            21,
            List.of(
               new StructureTests.BlockRequirement(ModBlockId.BOSS_ROOM_BOUNDARY_MARKER.id(), ModBlocks.BOSS_ROOM_BOUNDARY_MARKER, 2),
               new StructureTests.BlockRequirement("amethyst_block", () -> Blocks.AMETHYST_BLOCK, 1)
            )
         ),
         new StructureTests.StructureSpec(
            "phantom_catacombs_room_dead_end",
            7,
            9,
            7,
            List.of(
               new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 1),
               new StructureTests.BlockRequirement("crying_obsidian", () -> Blocks.CRYING_OBSIDIAN, 1)
            )
         ),
         new StructureTests.StructureSpec("phantom_catacombs_room_1", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2))),
         new StructureTests.StructureSpec("phantom_catacombs_room_2", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 4))),
         new StructureTests.StructureSpec("phantom_catacombs_room_3", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 4))),
         new StructureTests.StructureSpec("phantom_catacombs_room_4", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 3))),
         new StructureTests.StructureSpec("phantom_catacombs_room_5", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 3))),
         new StructureTests.StructureSpec("phantom_catacombs_room_6", 7, 9, 7, List.of(new StructureTests.BlockRequirement("jigsaw", () -> Blocks.JIGSAW, 2))),
         new StructureTests.StructureSpec(
            "phantom_catacombs_room_7", 7, 9, 7, List.of(new StructureTests.BlockRequirement("crying_obsidian", () -> Blocks.CRYING_OBSIDIAN, 1))
         )
      );
   }

   public static <T> List<T> generateStructureTests(List<StructureTests.StructureSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      List<T> tests = new ArrayList<>();

      for (StructureTests.StructureSpec spec : specs) {
         ResourceLocation templateId = CompatResourceLocation.create("chronodawn", spec.id());
         tests.add(
            factory.create(
               "structure_load_" + spec.id(),
               helper -> helper.runAfterDelay(
                  1L,
                  () -> {
                     StructureTemplateManager templateManager = helper.getLevel().getStructureManager();
                     Optional<StructureTemplate> templateOpt = templateManager.get(templateId);
                     if (templateOpt.isEmpty()) {
                        helper.fail("Structure template '" + spec.id() + "' could not be loaded");
                     } else {
                        StructureTemplate template = templateOpt.get();
                        Vec3i size = template.getSize();
                        if (size.getX() >= spec.minWidth() && size.getY() >= spec.minHeight() && size.getZ() >= spec.minDepth()) {
                           helper.succeed();
                        } else {
                           helper.fail(
                              "Structure '"
                                 + spec.id()
                                 + "' size "
                                 + size.getX()
                                 + "x"
                                 + size.getY()
                                 + "x"
                                 + size.getZ()
                                 + " is smaller than minimum "
                                 + spec.minWidth()
                                 + "x"
                                 + spec.minHeight()
                                 + "x"
                                 + spec.minDepth()
                           );
                        }
                     }
                  }
               )
            )
         );

         for (StructureTests.BlockRequirement req : spec.blockRequirements()) {
            String testName = req.minCount() > 1
               ? "structure_contains_" + req.minCount() + "_" + req.name() + "_" + spec.id()
               : "structure_contains_" + req.name() + "_" + spec.id();
            tests.add(factory.create(testName, helper -> helper.runAfterDelay(1L, () -> {
               Block block = req.blockSupplier().get();
               StructureTemplateManager templateManager = helper.getLevel().getStructureManager();
               Optional<StructureTemplate> templateOpt = templateManager.get(templateId);
               if (templateOpt.isEmpty()) {
                  helper.fail("Structure template '" + spec.id() + "' could not be loaded");
               } else {
                  StructureTemplate template = templateOpt.get();
                  List<StructureBlockInfo> blocks = template.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(), block);
                  if (blocks.size() < req.minCount()) {
                     helper.fail("Structure '" + spec.id() + "' contains " + blocks.size() + " " + req.name() + ", expected at least " + req.minCount());
                  } else {
                     helper.succeed();
                  }
               }
            })));
         }
      }

      tests.add(generateStructureCoverageTest(specs, factory));
      return tests;
   }

   static <T> T generateStructureCoverageTest(List<StructureTests.StructureSpec> specs, MobBehaviorTests.TestFactory<T> factory) {
      return factory.create(
         "structure_coverage_all_nbt_have_specs",
         helper -> helper.runAfterDelay(
            1L,
            () -> {
               ResourceManager resourceManager = helper.getLevel().getServer().getResourceManager();
               Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                  "structure", idx -> idx.getNamespace().equals("chronodawn") && idx.getPath().endsWith(".nbt")
               );
               Set<String> testedIds = specs.stream().map(StructureTests.StructureSpec::id).collect(Collectors.toSet());
               Set<String> excludedIds = Set.of("empty_test");
               List<String> untestedIds = new ArrayList<>();

               for (ResourceLocation entry : resources.keySet()) {
                  String path = entry.getPath();
                  String id = path.substring("structure/".length(), path.length() - ".nbt".length());
                  if (!testedIds.contains(id) && !excludedIds.contains(id)) {
                     untestedIds.add(id);
                  }
               }

               if (!untestedIds.isEmpty()) {
                  helper.fail("Structure templates without test specs: " + String.join(", ", untestedIds));
               } else {
                  helper.succeed();
               }
            }
         )
      );
   }

   public record BlockRequirement(String name, Supplier<Block> blockSupplier, int minCount) {
   }

   public record StructureSpec(String id, int minWidth, int minHeight, int minDepth, List<StructureTests.BlockRequirement> blockRequirements) {
   }
}
