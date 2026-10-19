package twilightforest.world.components.feature.templates;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFEntities;
import twilightforest.loot.TFLootTables;
import twilightforest.util.entities.EntityUtil;
import twilightforest.world.components.feature.config.SwizzleConfig;
import twilightforest.world.components.processors.CobbleVariants;
import twilightforest.world.components.processors.StoneBricksVariants;

public class DruidHutFeature extends TemplateFeature<SwizzleConfig> {
   public DruidHutFeature(Codec<SwizzleConfig> config) {
      super(config);
   }

   @Override
   protected StructureTemplate getTemplate(StructureTemplateManager templateManager, RandomSource random) {
      return templateManager.getOrCreate(((DruidHutFeature.HutType)Util.getRandom(DruidHutFeature.HutType.values(), random)).resourceLocation);
   }

   protected void modifySettings(StructurePlaceSettings settings, RandomSource random, SwizzleConfig config) {
      config.buildAddProcessors(settings, random);
   }

   protected void postPlacement(
      WorldGenLevel world,
      RandomSource random,
      StructureTemplateManager templateManager,
      Rotation rotation,
      Mirror mirror,
      StructurePlaceSettings placementSettings,
      BlockPos placementPos,
      SwizzleConfig config
   ) {
      if (random.nextBoolean()) {
         StructureTemplate template = templateManager.getOrCreate(
            DruidHutFeature.BasementType.values()[random.nextInt(DruidHutFeature.BasementType.size)].getBasement(random.nextBoolean())
         );
         if (template == null) {
            return;
         }

         placementPos = placementPos.below(12)
            .relative(rotation.rotate(mirror.mirror(Direction.NORTH)), 1)
            .relative(rotation.rotate(mirror.mirror(Direction.EAST)), 1);
         placementSettings.clearProcessors();
         config.buildAddProcessors(placementSettings, random);
         placementSettings.addProcessor(CobbleVariants.INSTANCE).addProcessor(StoneBricksVariants.INSTANCE);
         template.placeInWorld(world, placementPos, placementPos, placementSettings, random, 2);

         for (StructureBlockInfo info : template.filterBlocks(placementPos, placementSettings, Blocks.STRUCTURE_BLOCK)) {
            if (info.nbt() != null && StructureMode.valueOf(info.nbt().getString("mode")) == StructureMode.DATA) {
               this.processMarkers(info, world, rotation, mirror, random);
            }
         }
      }
   }

   @Override
   protected void processMarkers(StructureBlockInfo info, WorldGenLevel world, Rotation rotation, Mirror mirror, RandomSource random) {
      String s = info.nbt().getString("metadata");
      BlockPos blockPos = info.pos();
      if ("spawner".equals(s)) {
         if (world.removeBlock(blockPos, false)
            && world.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 18)
            && world.getBlockEntity(blockPos) instanceof SpawnerBlockEntity ms) {
            ms.setEntityId((EntityType)TFEntities.SKELETON_DRUID.get(), random);
         }
      } else if (s.startsWith("loot")) {
         world.removeBlock(blockPos, false);
         BlockState chest = s.endsWith("T") ? Blocks.TRAPPED_CHEST.defaultBlockState() : Blocks.CHEST.defaultBlockState();

         chest = switch (s.substring(5, 6)) {
            case "L" -> (BlockState)chest.setValue(ChestBlock.TYPE, mirror != Mirror.NONE ? ChestType.RIGHT : ChestType.LEFT);
            case "R" -> (BlockState)chest.setValue(ChestBlock.TYPE, mirror != Mirror.NONE ? ChestType.LEFT : ChestType.RIGHT);
            default -> (BlockState)chest.setValue(ChestBlock.TYPE, ChestType.SINGLE);
         };

         chest = switch (s.substring(4, 5)) {
            case "W" -> (BlockState)chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.WEST)));
            case "E" -> (BlockState)chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.EAST)));
            case "S" -> (BlockState)chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.SOUTH)));
            default -> (BlockState)chest.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(mirror.mirror(Direction.NORTH)));
         };
         TFLootTables.generateLootContainer(world, blockPos, chest, 18, s.endsWith("J") ? TFLootTables.HUT_JUNK : TFLootTables.BASEMENT);
      } else if (s.startsWith("barrel")) {
         world.removeBlock(blockPos, false);
         BlockState chest = Blocks.BARREL.defaultBlockState();

         chest = switch (s.substring(6, 7)) {
            case "D" -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.DOWN)));
            case "W" -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.WEST)));
            case "E" -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.EAST)));
            case "N" -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.NORTH)));
            case "S" -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.SOUTH)));
            default -> (BlockState)chest.setValue(BlockStateProperties.FACING, rotation.rotate(mirror.mirror(Direction.UP)));
         };
         TFLootTables.generateLootContainer(world, blockPos, chest, 18, TFLootTables.HUT_JUNK);
      } else if (s.startsWith("painting")) {
         world.removeBlock(blockPos, false);

         Direction direction = rotation.rotate(mirror.mirror(switch (s.substring(8, 9)) {
            case "W" -> Direction.WEST;
            case "E" -> Direction.EAST;
            case "S" -> Direction.SOUTH;
            default -> Direction.NORTH;
         }));
         String widthS = s.substring(9, 10);
         int paintingWidth = widthS.matches("\\d+") ? Integer.parseInt(widthS) : 1;
         boolean hasFlipped = mirror != Mirror.NONE;
         BlockPos hangPos = hasFlipped ? blockPos.relative(direction.getClockWise()) : blockPos;
         EntityUtil.tryHangPainting(
            world, hangPos, direction, EntityUtil.getPaintingOfSize(world, random, paintingWidth, paintingWidth != 2 && paintingWidth != 4 ? 1 : 2, true)
         );
      }
   }

   private enum BasementType {
      STUDY(TwilightForestMod.prefix("feature/druid_hut/basement_study"), TwilightForestMod.prefix("feature/druid_hut/basement_study_trap")),
      SHELVES(TwilightForestMod.prefix("feature/druid_hut/basement_shelves"), TwilightForestMod.prefix("feature/druid_hut/basement_shelves_trap")),
      GALLERY(TwilightForestMod.prefix("feature/druid_hut/basement_gallery"), TwilightForestMod.prefix("feature/druid_hut/basement_gallery_trap"));

      private final ResourceLocation resourceLocation;
      private final ResourceLocation resourceLocationTrap;
      private static int size;

      BasementType(ResourceLocation rl, ResourceLocation rlTrap) {
         this.resourceLocation = rl;
         this.resourceLocationTrap = rlTrap;
         increment();
      }

      private static void increment() {
         size++;
      }

      private ResourceLocation getBasement(boolean trapped) {
         return trapped ? this.resourceLocationTrap : this.resourceLocation;
      }
   }

   private enum HutType {
      REGULAR(TwilightForestMod.prefix("feature/druid_hut/druid_hut")),
      SIDEWAYS(TwilightForestMod.prefix("feature/druid_hut/druid_sideways")),
      DOUBLE_DECK(TwilightForestMod.prefix("feature/druid_hut/druid_doubledeck"));

      private final ResourceLocation resourceLocation;

      HutType(ResourceLocation rl) {
         this.resourceLocation = rl;
      }
   }
}
