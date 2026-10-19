package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.compat.ModdedBlockTagGenerator;
import twilightforest.init.TFBlocks;

public class BlockTagGenerator extends ModdedBlockTagGenerator {
   public static final TagKey<Block> MAZESTONE = create("mazestone");
   public static final TagKey<Block> TOWERWOOD = create("towerwood");
   public static final TagKey<Block> CLOUDS = create("clouds");
   public static final TagKey<Block> DEADROCK = create("deadrock");
   public static final TagKey<Block> CASTLE_BLOCKS = create("castle_blocks");
   public static final TagKey<Block> TF_LOGS = create("logs");
   public static final TagKey<Block> TWILIGHT_OAK_LOGS = create("twilight_oak_logs");
   public static final TagKey<Block> CANOPY_LOGS = create("canopy_logs");
   public static final TagKey<Block> MANGROVE_LOGS = create("mangrove_logs");
   public static final TagKey<Block> DARKWOOD_LOGS = create("darkwood_logs");
   public static final TagKey<Block> TIME_LOGS = create("timewood_logs");
   public static final TagKey<Block> TRANSFORMATION_LOGS = create("transwood_logs");
   public static final TagKey<Block> MINING_LOGS = create("mining_logs");
   public static final TagKey<Block> SORTING_LOGS = create("sortwood_logs");
   public static final TagKey<Block> HOLLOW_LOGS = create("hollow_logs");
   public static final TagKey<Block> HOLLOW_LOGS_HORIZONTAL = create("hollow_logs_horizontal");
   public static final TagKey<Block> HOLLOW_LOGS_VERTICAL = create("hollow_logs_vertical");
   public static final TagKey<Block> HOLLOW_LOGS_CLIMBABLE = create("hollow_logs_climbable");
   public static final TagKey<Block> BANISTERS = create("banisters");
   public static final TagKey<Block> TF_CHESTS = create("chests");
   public static final TagKey<Block> PORTAL_EDGE = create("portal/edge");
   public static final TagKey<Block> PORTAL_POOL = create("portal/fluid");
   public static final TagKey<Block> PORTAL_DECO = create("portal/decoration");
   public static final TagKey<Block> GENERATED_PORTAL_DECO = create("portal/generated_decoration");
   public static final TagKey<Block> DARK_TOWER_ALLOWED_POTS = create("dark_tower_allowed_pots");
   public static final TagKey<Block> TROPHY_PEDESTAL_ACTIVATION_BLOCKS = create("trophy_pedestal_activation_blocks");
   public static final TagKey<Block> FIRE_JET_FUEL = create("fire_jet_fuel");
   public static final TagKey<Block> ICE_BOMB_REPLACEABLES = create("ice_bomb_replaceables");
   public static final TagKey<Block> MAZEBREAKER_ACCELERATED = create("mazebreaker_accelerated_mining");
   public static final TagKey<Block> COMMON_PROTECTIONS = create("common_protections");
   public static final TagKey<Block> ANNIHILATION_INCLUSIONS = create("annihilation_inclusions");
   public static final TagKey<Block> ANTIBUILDER_IGNORES = create("antibuilder_ignores");
   public static final TagKey<Block> CARMINITE_REACTOR_IMMUNE = create("carminite_reactor_immune");
   public static final TagKey<Block> CARMINITE_REACTOR_ORES = create("carminite_reactor_ores");
   public static final TagKey<Block> STRUCTURE_BANNED_INTERACTIONS = create("structure_banned_interactions");
   public static final TagKey<Block> PROGRESSION_ALLOW_BREAKING = create("progression_allow_breaking");
   public static final TagKey<Block> CANNOT_TROLL_CAVE_HOLLOW = create("cannot_troll_cave_hollow");
   public static final TagKey<Block> WORLDGEN_REPLACEABLES = create("worldgen_replaceables");
   public static final TagKey<Block> ROOT_TRACE_SKIP = create("tree_roots_skip");
   public static final TagKey<Block> SUPPORTS_STALAGMITES = create("supports_stalagmites");
   public static final TagKey<Block> CARVER_REPLACEABLES = create("carver_replaceables");
   public static final TagKey<Block> PLANTS_HANG_ON = create("plants_hang_on");
   public static final TagKey<Block> OREBERRY_BUSHES_SURVIVE = create("oreberry_bushes_survive");
   public static final TagKey<Block> TF_BERRY_BUSHES_SURVIVE = create("tf_berry_bushes_survive");
   public static final TagKey<Block> TF_BERRY_BUSHES_REPLACE = create("tf_berry_bushes_replace");
   public static final TagKey<Block> DARK_TOWER_BERRY_BUSHES_SURVIVE = create("dark_tower_berry_bushes_survive");
   public static final TagKey<Block> DARK_TOWER_BERRY_BUSHES_DIE = create("dark_tower_berry_bushes_die");
   public static final TagKey<Block> ORE_MAGNET_SAFE_REPLACE_BLOCK = create("ore_magnet/ore_safe_replace_block");
   public static final TagKey<Block> ORE_MAGNET_IGNORE = create("ore_magnet/ignored_ores");
   public static final TagKey<Block> ROOT_GROUND = makeCommonTag("ore_bearing_ground/root");
   public static final TagKey<Block> ROOT_ORES = makeCommonTag("ores_in_ground/root");
   public static final TagKey<Block> TIME_CORE_EXCLUDED = create("time_core_excluded");
   public static final TagKey<Block> MINING_CORE_EXCLUDED = create("mining_tree_excluded");
   public static final TagKey<Block> ORE_METER_TARGETABLE = create("ore_meter_targetable");
   public static final TagKey<Block> PENGUINS_SPAWNABLE_ON = create("penguins_spawnable_on");
   public static final TagKey<Block> GIANTS_SPAWNABLE_ON = create("giants_spawnable_on");
   public static final TagKey<Block> DRUID_PROJECTILE_REPLACEABLE = create("druid_projectile_replaceable");
   public static final TagKey<Block> STORAGE_BLOCKS_ARCTIC_FUR = makeCommonTag("storage_blocks/arctic_fur");
   public static final TagKey<Block> STORAGE_BLOCKS_CARMINITE = makeCommonTag("storage_blocks/carminite");
   public static final TagKey<Block> STORAGE_BLOCKS_FIERY = makeCommonTag("storage_blocks/fiery");
   public static final TagKey<Block> STORAGE_BLOCKS_IRONWOOD = makeCommonTag("storage_blocks/ironwood");
   public static final TagKey<Block> STORAGE_BLOCKS_KNIGHTMETAL = makeCommonTag("storage_blocks/knightmetal");
   public static final TagKey<Block> STORAGE_BLOCKS_STEELEAF = makeCommonTag("storage_blocks/steeleaf");
   public static final TagKey<Block> INCORRECT_FOR_IRONWOOD_TOOL = create("incorrect_for_ironwood_tool");
   public static final TagKey<Block> INCORRECT_FOR_FIERY_TOOL = create("incorrect_for_fiery_tool");
   public static final TagKey<Block> INCORRECT_FOR_STEELEAF_TOOL = create("incorrect_for_steeleaf_tool");
   public static final TagKey<Block> INCORRECT_FOR_KNIGHTMETAL_TOOL = create("incorrect_for_knightmetal_tool");
   public static final TagKey<Block> INCORRECT_FOR_GIANT_TOOL = create("incorrect_for_giant_tool");
   public static final TagKey<Block> INCORRECT_FOR_ICE_TOOL = create("incorrect_for_ice_tool");
   public static final TagKey<Block> INCORRECT_FOR_GLASS_TOOL = create("incorrect_for_glass_tool");
   public static final TagKey<Block> MINEABLE_WITH_BLOCK_AND_CHAIN = create("mineable_with_block_and_chain");
   public static final TagKey<Block> BLOCK_AND_CHAIN_NEVER_BREAKS = create("block_and_chain_never_breaks");
   public static final TagKey<Block> SMALL_LAKES_DONT_REPLACE = create("small_lakes_dont_replace");
   public static final TagKey<Block> DRYING_RACKS = create("drying_racks");

   public BlockTagGenerator(PackOutput output, CompletableFuture<Provider> future, ExistingFileHelper helper) {
      super(output, future, helper);
   }

   @Override
   protected void addTags(Provider provider) {
      super.addTags(provider);
      this.tag(TWILIGHT_OAK_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_LOG.get(),
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get(),
               (Block)TFBlocks.TWILIGHT_OAK_WOOD.get(),
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get()
            }
         );
      this.tag(CANOPY_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.CANOPY_LOG.get(),
               (Block)TFBlocks.STRIPPED_CANOPY_LOG.get(),
               (Block)TFBlocks.CANOPY_WOOD.get(),
               (Block)TFBlocks.STRIPPED_CANOPY_WOOD.get()
            }
         );
      this.tag(MANGROVE_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.MANGROVE_LOG.get(),
               (Block)TFBlocks.STRIPPED_MANGROVE_LOG.get(),
               (Block)TFBlocks.MANGROVE_WOOD.get(),
               (Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get()
            }
         );
      this.tag(DARKWOOD_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.DARK_LOG.get(),
               (Block)TFBlocks.STRIPPED_DARK_LOG.get(),
               (Block)TFBlocks.DARK_WOOD.get(),
               (Block)TFBlocks.STRIPPED_DARK_WOOD.get()
            }
         );
      this.tag(TIME_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.TIME_LOG.get(),
               (Block)TFBlocks.STRIPPED_TIME_LOG.get(),
               (Block)TFBlocks.TIME_WOOD.get(),
               (Block)TFBlocks.STRIPPED_TIME_WOOD.get()
            }
         );
      this.tag(TRANSFORMATION_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.TRANSFORMATION_LOG.get(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get(),
               (Block)TFBlocks.TRANSFORMATION_WOOD.get(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get()
            }
         );
      this.tag(MINING_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.MINING_LOG.get(),
               (Block)TFBlocks.STRIPPED_MINING_LOG.get(),
               (Block)TFBlocks.MINING_WOOD.get(),
               (Block)TFBlocks.STRIPPED_MINING_WOOD.get()
            }
         );
      this.tag(SORTING_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.SORTING_LOG.get(),
               (Block)TFBlocks.STRIPPED_SORTING_LOG.get(),
               (Block)TFBlocks.SORTING_WOOD.get(),
               (Block)TFBlocks.STRIPPED_SORTING_WOOD.get()
            }
         );
      this.tag(TF_LOGS)
         .addTags(new TagKey[]{TWILIGHT_OAK_LOGS, CANOPY_LOGS, MANGROVE_LOGS, DARKWOOD_LOGS, TIME_LOGS, TRANSFORMATION_LOGS, MINING_LOGS, SORTING_LOGS});
      this.tag(BlockTags.LOGS).addTag(TF_LOGS);
      this.tag(BlockTags.LOGS_THAT_BURN).addTag(TF_LOGS);
      this.tag(Blocks.STRIPPED_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.value(),
               (Block)TFBlocks.STRIPPED_CANOPY_LOG.value(),
               (Block)TFBlocks.STRIPPED_MANGROVE_LOG.value(),
               (Block)TFBlocks.STRIPPED_DARK_LOG.value(),
               (Block)TFBlocks.STRIPPED_TIME_LOG.value(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.value(),
               (Block)TFBlocks.STRIPPED_MINING_LOG.value(),
               (Block)TFBlocks.STRIPPED_SORTING_LOG.value()
            }
         );
      this.tag(Blocks.STRIPPED_WOODS)
         .add(
            new Block[]{
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.value(),
               (Block)TFBlocks.STRIPPED_CANOPY_WOOD.value(),
               (Block)TFBlocks.STRIPPED_MANGROVE_WOOD.value(),
               (Block)TFBlocks.STRIPPED_DARK_WOOD.value(),
               (Block)TFBlocks.STRIPPED_TIME_WOOD.value(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.value(),
               (Block)TFBlocks.STRIPPED_MINING_WOOD.value(),
               (Block)TFBlocks.STRIPPED_SORTING_WOOD.value()
            }
         );
      this.tag(BlockTags.SAPLINGS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_SAPLING.get(),
               (Block)TFBlocks.CANOPY_SAPLING.get(),
               (Block)TFBlocks.MANGROVE_SAPLING.get(),
               (Block)TFBlocks.DARKWOOD_SAPLING.get(),
               (Block)TFBlocks.TIME_SAPLING.get(),
               (Block)TFBlocks.TRANSFORMATION_SAPLING.get(),
               (Block)TFBlocks.MINING_SAPLING.get(),
               (Block)TFBlocks.SORTING_SAPLING.get(),
               (Block)TFBlocks.HOLLOW_OAK_SAPLING.get(),
               (Block)TFBlocks.RAINBOW_OAK_SAPLING.get()
            }
         );
      this.tag(BlockTags.LEAVES)
         .add(
            new Block[]{
               (Block)TFBlocks.RAINBOW_OAK_LEAVES.get(),
               (Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
               (Block)TFBlocks.CANOPY_LEAVES.get(),
               (Block)TFBlocks.MANGROVE_LEAVES.get(),
               (Block)TFBlocks.DARK_LEAVES.get(),
               (Block)TFBlocks.TIME_LEAVES.get(),
               (Block)TFBlocks.TRANSFORMATION_LEAVES.get(),
               (Block)TFBlocks.MINING_LEAVES.get(),
               (Block)TFBlocks.SORTING_LEAVES.get(),
               (Block)TFBlocks.THORN_LEAVES.get(),
               (Block)TFBlocks.BEANSTALK_LEAVES.get()
            }
         );
      this.tag(BlockTags.PLANKS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_PLANKS.get(),
               (Block)TFBlocks.CANOPY_PLANKS.get(),
               (Block)TFBlocks.MANGROVE_PLANKS.get(),
               (Block)TFBlocks.DARK_PLANKS.get(),
               (Block)TFBlocks.TIME_PLANKS.get(),
               (Block)TFBlocks.TRANSFORMATION_PLANKS.get(),
               (Block)TFBlocks.MINING_PLANKS.get(),
               (Block)TFBlocks.SORTING_PLANKS.get()
            }
         )
         .addTag(TOWERWOOD);
      this.tag(BlockTags.WOODEN_SLABS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_SLAB.get(),
               (Block)TFBlocks.CANOPY_SLAB.get(),
               (Block)TFBlocks.MANGROVE_SLAB.get(),
               (Block)TFBlocks.DARK_SLAB.get(),
               (Block)TFBlocks.TIME_SLAB.get(),
               (Block)TFBlocks.TRANSFORMATION_SLAB.get(),
               (Block)TFBlocks.MINING_SLAB.get(),
               (Block)TFBlocks.SORTING_SLAB.get()
            }
         );
      this.tag(BlockTags.SLABS).add((Block)TFBlocks.AURORA_SLAB.get());
      this.tag(BlockTags.WOODEN_STAIRS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_STAIRS.get(),
               (Block)TFBlocks.CANOPY_STAIRS.get(),
               (Block)TFBlocks.MANGROVE_STAIRS.get(),
               (Block)TFBlocks.DARK_STAIRS.get(),
               (Block)TFBlocks.TIME_STAIRS.get(),
               (Block)TFBlocks.TRANSFORMATION_STAIRS.get(),
               (Block)TFBlocks.MINING_STAIRS.get(),
               (Block)TFBlocks.SORTING_STAIRS.get()
            }
         );
      this.tag(BlockTags.STAIRS)
         .add(
            new Block[]{
               (Block)TFBlocks.CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get()
            }
         );
      this.tag(BlockTags.WOODEN_FENCES)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_FENCE.get(),
               (Block)TFBlocks.CANOPY_FENCE.get(),
               (Block)TFBlocks.MANGROVE_FENCE.get(),
               (Block)TFBlocks.DARK_FENCE.get(),
               (Block)TFBlocks.TIME_FENCE.get(),
               (Block)TFBlocks.TRANSFORMATION_FENCE.get(),
               (Block)TFBlocks.MINING_FENCE.get(),
               (Block)TFBlocks.SORTING_FENCE.get()
            }
         );
      this.tag(BlockTags.FENCE_GATES)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_GATE.get(),
               (Block)TFBlocks.CANOPY_GATE.get(),
               (Block)TFBlocks.MANGROVE_GATE.get(),
               (Block)TFBlocks.DARK_GATE.get(),
               (Block)TFBlocks.TIME_GATE.get(),
               (Block)TFBlocks.TRANSFORMATION_GATE.get(),
               (Block)TFBlocks.MINING_GATE.get(),
               (Block)TFBlocks.SORTING_GATE.get()
            }
         );
      this.tag(Blocks.FENCE_GATES_WOODEN)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_GATE.get(),
               (Block)TFBlocks.CANOPY_GATE.get(),
               (Block)TFBlocks.MANGROVE_GATE.get(),
               (Block)TFBlocks.DARK_GATE.get(),
               (Block)TFBlocks.TIME_GATE.get(),
               (Block)TFBlocks.TRANSFORMATION_GATE.get(),
               (Block)TFBlocks.MINING_GATE.get(),
               (Block)TFBlocks.SORTING_GATE.get()
            }
         );
      this.tag(BlockTags.WOODEN_BUTTONS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_BUTTON.get(),
               (Block)TFBlocks.CANOPY_BUTTON.get(),
               (Block)TFBlocks.MANGROVE_BUTTON.get(),
               (Block)TFBlocks.DARK_BUTTON.get(),
               (Block)TFBlocks.TIME_BUTTON.get(),
               (Block)TFBlocks.TRANSFORMATION_BUTTON.get(),
               (Block)TFBlocks.MINING_BUTTON.get(),
               (Block)TFBlocks.SORTING_BUTTON.get()
            }
         );
      this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_PLATE.get(),
               (Block)TFBlocks.CANOPY_PLATE.get(),
               (Block)TFBlocks.MANGROVE_PLATE.get(),
               (Block)TFBlocks.DARK_PLATE.get(),
               (Block)TFBlocks.TIME_PLATE.get(),
               (Block)TFBlocks.TRANSFORMATION_PLATE.get(),
               (Block)TFBlocks.MINING_PLATE.get(),
               (Block)TFBlocks.SORTING_PLATE.get()
            }
         );
      this.tag(BlockTags.WOODEN_TRAPDOORS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_TRAPDOOR.get(),
               (Block)TFBlocks.CANOPY_TRAPDOOR.get(),
               (Block)TFBlocks.MANGROVE_TRAPDOOR.get(),
               (Block)TFBlocks.DARK_TRAPDOOR.get(),
               (Block)TFBlocks.TIME_TRAPDOOR.get(),
               (Block)TFBlocks.TRANSFORMATION_TRAPDOOR.get(),
               (Block)TFBlocks.MINING_TRAPDOOR.get(),
               (Block)TFBlocks.SORTING_TRAPDOOR.get()
            }
         );
      this.tag(BlockTags.WOODEN_DOORS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_DOOR.get(),
               (Block)TFBlocks.CANOPY_DOOR.get(),
               (Block)TFBlocks.MANGROVE_DOOR.get(),
               (Block)TFBlocks.DARK_DOOR.get(),
               (Block)TFBlocks.TIME_DOOR.get(),
               (Block)TFBlocks.TRANSFORMATION_DOOR.get(),
               (Block)TFBlocks.MINING_DOOR.get(),
               (Block)TFBlocks.SORTING_DOOR.get()
            }
         );
      this.tag(Blocks.CHESTS_WOODEN)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_CHEST.get(),
               (Block)TFBlocks.CANOPY_CHEST.get(),
               (Block)TFBlocks.MANGROVE_CHEST.get(),
               (Block)TFBlocks.DARK_CHEST.get(),
               (Block)TFBlocks.TIME_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_CHEST.get(),
               (Block)TFBlocks.MINING_CHEST.get(),
               (Block)TFBlocks.SORTING_CHEST.get()
            }
         )
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
               (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
               (Block)TFBlocks.SORTING_TRAPPED_CHEST.get()
            }
         );
      this.tag(Blocks.CHESTS_TRAPPED)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
               (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
               (Block)TFBlocks.SORTING_TRAPPED_CHEST.get()
            }
         );
      this.tag(BlockTags.FLOWER_POTS)
         .add(
            new Block[]{
               (Block)TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get(),
               (Block)TFBlocks.POTTED_CANOPY_SAPLING.get(),
               (Block)TFBlocks.POTTED_MANGROVE_SAPLING.get(),
               (Block)TFBlocks.POTTED_DARKWOOD_SAPLING.get(),
               (Block)TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get(),
               (Block)TFBlocks.POTTED_HOLLOW_OAK_SAPLING.get(),
               (Block)TFBlocks.POTTED_TIME_SAPLING.get(),
               (Block)TFBlocks.POTTED_TRANSFORMATION_SAPLING.get(),
               (Block)TFBlocks.POTTED_MINING_SAPLING.get(),
               (Block)TFBlocks.POTTED_SORTING_SAPLING.get(),
               (Block)TFBlocks.POTTED_MAYAPPLE.get(),
               (Block)TFBlocks.POTTED_FIDDLEHEAD.get(),
               (Block)TFBlocks.POTTED_MUSHGLOOM.get(),
               (Block)TFBlocks.POTTED_THORN.get(),
               (Block)TFBlocks.POTTED_GREEN_THORN.get(),
               (Block)TFBlocks.POTTED_DEAD_THORN.get()
            }
         );
      this.tag(BlockTags.WALLS).add((Block)TFBlocks.WROUGHT_IRON_FENCE.get());
      this.tag(BANISTERS)
         .add(
            new Block[]{
               (Block)TFBlocks.OAK_BANISTER.get(),
               (Block)TFBlocks.SPRUCE_BANISTER.get(),
               (Block)TFBlocks.BIRCH_BANISTER.get(),
               (Block)TFBlocks.JUNGLE_BANISTER.get(),
               (Block)TFBlocks.ACACIA_BANISTER.get(),
               (Block)TFBlocks.DARK_OAK_BANISTER.get(),
               (Block)TFBlocks.CRIMSON_BANISTER.get(),
               (Block)TFBlocks.WARPED_BANISTER.get(),
               (Block)TFBlocks.VANGROVE_BANISTER.get(),
               (Block)TFBlocks.BAMBOO_BANISTER.get(),
               (Block)TFBlocks.CHERRY_BANISTER.get(),
               (Block)TFBlocks.TWILIGHT_OAK_BANISTER.get(),
               (Block)TFBlocks.CANOPY_BANISTER.get(),
               (Block)TFBlocks.MANGROVE_BANISTER.get(),
               (Block)TFBlocks.DARK_BANISTER.get(),
               (Block)TFBlocks.TIME_BANISTER.get(),
               (Block)TFBlocks.TRANSFORMATION_BANISTER.get(),
               (Block)TFBlocks.MINING_BANISTER.get(),
               (Block)TFBlocks.SORTING_BANISTER.get()
            }
         );
      this.tag(HOLLOW_LOGS_HORIZONTAL)
         .add(
            new Block[]{
               (Block)TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(),
               (Block)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get()
            }
         );
      this.tag(HOLLOW_LOGS_VERTICAL)
         .add(
            new Block[]{
               (Block)TFBlocks.HOLLOW_OAK_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_WARPED_STEM_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_VANGROVE_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_CHERRY_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_DARK_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_TIME_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_MINING_LOG_VERTICAL.get(),
               (Block)TFBlocks.HOLLOW_SORTING_LOG_VERTICAL.get()
            }
         );
      this.tag(HOLLOW_LOGS_CLIMBABLE)
         .add(
            new Block[]{
               (Block)TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get(),
               (Block)TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get()
            }
         );
      this.tag(HOLLOW_LOGS).addTags(new TagKey[]{HOLLOW_LOGS_HORIZONTAL, HOLLOW_LOGS_VERTICAL, HOLLOW_LOGS_CLIMBABLE});
      this.tag(BlockTags.STRIDER_WARM_BLOCKS).add((Block)TFBlocks.FIERY_BLOCK.get());
      this.tag(BlockTags.PORTALS).add((Block)TFBlocks.TWILIGHT_PORTAL.get());
      this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add((Block)TFBlocks.CANOPY_BOOKSHELF.get());
      this.tag(BlockTags.REPLACEABLE_BY_TREES)
         .add(
            new Block[]{
               (Block)TFBlocks.HARDENED_DARK_LEAVES.get(),
               (Block)TFBlocks.MAYAPPLE.get(),
               (Block)TFBlocks.FIDDLEHEAD.get(),
               (Block)TFBlocks.MOSS_PATCH.get(),
               (Block)TFBlocks.CLOVER_PATCH.get(),
               (Block)TFBlocks.MUSHGLOOM.get(),
               (Block)TFBlocks.FIREFLY.get(),
               (Block)TFBlocks.FALLEN_LEAVES.get(),
               (Block)TFBlocks.TORCHBERRY_PLANT.get(),
               (Block)TFBlocks.ROOT_STRAND.get(),
               (Block)TFBlocks.ROOT_BLOCK.get(),
               (Block)TFBlocks.RASPBERRY_BUSH.get(),
               (Block)TFBlocks.BLUEBERRY_BUSH.get(),
               (Block)TFBlocks.BLACKBERRY_BUSH.get(),
               (Block)TFBlocks.MALOBERRY_BUSH.get()
            }
         );
      this.tag(BlockTags.CLIMBABLE)
         .add(new Block[]{(Block)TFBlocks.IRON_LADDER.get(), (Block)TFBlocks.ROPE.get(), (Block)TFBlocks.ROOT_STRAND.get()})
         .addTag(HOLLOW_LOGS_CLIMBABLE);
      this.tag(BlockTags.STANDING_SIGNS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_SIGN.get(),
               (Block)TFBlocks.CANOPY_SIGN.get(),
               (Block)TFBlocks.MANGROVE_SIGN.get(),
               (Block)TFBlocks.DARK_SIGN.get(),
               (Block)TFBlocks.TIME_SIGN.get(),
               (Block)TFBlocks.TRANSFORMATION_SIGN.get(),
               (Block)TFBlocks.MINING_SIGN.get(),
               (Block)TFBlocks.SORTING_SIGN.get()
            }
         );
      this.tag(BlockTags.WALL_SIGNS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_WALL_SIGN.get(),
               (Block)TFBlocks.CANOPY_WALL_SIGN.get(),
               (Block)TFBlocks.MANGROVE_WALL_SIGN.get(),
               (Block)TFBlocks.DARK_WALL_SIGN.get(),
               (Block)TFBlocks.TIME_WALL_SIGN.get(),
               (Block)TFBlocks.TRANSFORMATION_WALL_SIGN.get(),
               (Block)TFBlocks.MINING_WALL_SIGN.get(),
               (Block)TFBlocks.SORTING_WALL_SIGN.get()
            }
         );
      this.tag(BlockTags.CEILING_HANGING_SIGNS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get(),
               (Block)TFBlocks.CANOPY_HANGING_SIGN.get(),
               (Block)TFBlocks.MANGROVE_HANGING_SIGN.get(),
               (Block)TFBlocks.DARK_HANGING_SIGN.get(),
               (Block)TFBlocks.TIME_HANGING_SIGN.get(),
               (Block)TFBlocks.TRANSFORMATION_HANGING_SIGN.get(),
               (Block)TFBlocks.MINING_HANGING_SIGN.get(),
               (Block)TFBlocks.SORTING_HANGING_SIGN.get()
            }
         );
      this.tag(BlockTags.WALL_HANGING_SIGNS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.CANOPY_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.MANGROVE_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.DARK_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.TIME_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.TRANSFORMATION_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.MINING_WALL_HANGING_SIGN.get(),
               (Block)TFBlocks.SORTING_WALL_HANGING_SIGN.get()
            }
         );
      this.tag(TOWERWOOD)
         .add(
            new Block[]{
               (Block)TFBlocks.TOWERWOOD.get(),
               (Block)TFBlocks.MOSSY_TOWERWOOD.get(),
               (Block)TFBlocks.CRACKED_TOWERWOOD.get(),
               (Block)TFBlocks.INFESTED_TOWERWOOD.get()
            }
         );
      this.tag(MAZESTONE)
         .add(
            new Block[]{
               (Block)TFBlocks.MAZESTONE.get(),
               (Block)TFBlocks.MAZESTONE_BRICK.get(),
               (Block)TFBlocks.CRACKED_MAZESTONE.get(),
               (Block)TFBlocks.MOSSY_MAZESTONE.get(),
               (Block)TFBlocks.CUT_MAZESTONE.get(),
               (Block)TFBlocks.DECORATIVE_MAZESTONE.get(),
               (Block)TFBlocks.MAZESTONE_MOSAIC.get(),
               (Block)TFBlocks.MAZESTONE_BORDER.get()
            }
         );
      this.tag(CASTLE_BLOCKS)
         .add(
            new Block[]{
               (Block)TFBlocks.CASTLE_BRICK.get(),
               (Block)TFBlocks.WORN_CASTLE_BRICK.get(),
               (Block)TFBlocks.CRACKED_CASTLE_BRICK.get(),
               (Block)TFBlocks.MOSSY_CASTLE_BRICK.get(),
               (Block)TFBlocks.CASTLE_ROOF_TILE.get(),
               (Block)TFBlocks.THICK_CASTLE_BRICK.get(),
               (Block)TFBlocks.BOLD_CASTLE_BRICK_TILE.get(),
               (Block)TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(),
               (Block)TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(),
               (Block)TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(),
               (Block)TFBlocks.CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.WORN_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get(),
               (Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.PINK_CASTLE_DOOR.get(),
               (Block)TFBlocks.YELLOW_CASTLE_DOOR.get(),
               (Block)TFBlocks.BLUE_CASTLE_DOOR.get(),
               (Block)TFBlocks.VIOLET_CASTLE_DOOR.get()
            }
         );
      this.tag(MAZEBREAKER_ACCELERATED).addTag(MAZESTONE).addTag(CASTLE_BLOCKS);
      this.tag(STORAGE_BLOCKS_ARCTIC_FUR).add((Block)TFBlocks.ARCTIC_FUR_BLOCK.get());
      this.tag(STORAGE_BLOCKS_CARMINITE).add((Block)TFBlocks.CARMINITE_BLOCK.get());
      this.tag(STORAGE_BLOCKS_FIERY).add((Block)TFBlocks.FIERY_BLOCK.get());
      this.tag(STORAGE_BLOCKS_IRONWOOD).add((Block)TFBlocks.IRONWOOD_BLOCK.get());
      this.tag(STORAGE_BLOCKS_KNIGHTMETAL).add((Block)TFBlocks.KNIGHTMETAL_BLOCK.get());
      this.tag(STORAGE_BLOCKS_STEELEAF).add((Block)TFBlocks.STEELEAF_BLOCK.get());
      this.tag(BlockTags.BEACON_BASE_BLOCKS)
         .addTags(new TagKey[]{STORAGE_BLOCKS_FIERY, STORAGE_BLOCKS_IRONWOOD, STORAGE_BLOCKS_KNIGHTMETAL, STORAGE_BLOCKS_STEELEAF});
      this.tag(Blocks.STORAGE_BLOCKS)
         .addTags(
            new TagKey[]{
               STORAGE_BLOCKS_ARCTIC_FUR,
               STORAGE_BLOCKS_CARMINITE,
               STORAGE_BLOCKS_FIERY,
               STORAGE_BLOCKS_IRONWOOD,
               STORAGE_BLOCKS_KNIGHTMETAL,
               STORAGE_BLOCKS_STEELEAF
            }
         );
      this.tag(BlockTags.DIRT).add((Block)TFBlocks.UBEROUS_SOIL.get());
      this.tag(PORTAL_EDGE)
         .add(new Block[]{net.minecraft.world.level.block.Blocks.FARMLAND, net.minecraft.world.level.block.Blocks.DIRT_PATH})
         .addTags(new TagKey[]{BlockTags.DIRT});
      this.tag(PORTAL_POOL).add(net.minecraft.world.level.block.Blocks.WATER);
      this.tag(PORTAL_DECO)
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.BAMBOO,
               net.minecraft.world.level.block.Blocks.SHORT_GRASS,
               net.minecraft.world.level.block.Blocks.TALL_GRASS,
               net.minecraft.world.level.block.Blocks.FERN,
               net.minecraft.world.level.block.Blocks.LARGE_FERN,
               net.minecraft.world.level.block.Blocks.DEAD_BUSH,
               net.minecraft.world.level.block.Blocks.SUGAR_CANE,
               net.minecraft.world.level.block.Blocks.CHORUS_PLANT,
               net.minecraft.world.level.block.Blocks.CHORUS_FLOWER,
               net.minecraft.world.level.block.Blocks.SWEET_BERRY_BUSH,
               net.minecraft.world.level.block.Blocks.NETHER_WART,
               net.minecraft.world.level.block.Blocks.COCOA,
               net.minecraft.world.level.block.Blocks.VINE,
               net.minecraft.world.level.block.Blocks.GLOW_LICHEN,
               net.minecraft.world.level.block.Blocks.RED_MUSHROOM,
               net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM,
               net.minecraft.world.level.block.Blocks.WARPED_FUNGUS,
               net.minecraft.world.level.block.Blocks.CRIMSON_FUNGUS,
               net.minecraft.world.level.block.Blocks.ATTACHED_MELON_STEM,
               net.minecraft.world.level.block.Blocks.ATTACHED_PUMPKIN_STEM,
               net.minecraft.world.level.block.Blocks.MOSS_CARPET,
               net.minecraft.world.level.block.Blocks.PINK_PETALS,
               net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF,
               net.minecraft.world.level.block.Blocks.BIG_DRIPLEAF_STEM,
               net.minecraft.world.level.block.Blocks.SMALL_DRIPLEAF,
               (Block)TFBlocks.FIDDLEHEAD.get(),
               (Block)TFBlocks.MOSS_PATCH.get(),
               (Block)TFBlocks.MAYAPPLE.get(),
               (Block)TFBlocks.CLOVER_PATCH.get(),
               (Block)TFBlocks.MUSHGLOOM.get(),
               (Block)TFBlocks.FALLEN_LEAVES.get(),
               (Block)TFBlocks.GIANT_LEAVES.get(),
               (Block)TFBlocks.STEELEAF_BLOCK.get(),
               (Block)TFBlocks.HARDENED_DARK_LEAVES.get()
            }
         )
         .addTags(new TagKey[]{BlockTags.FLOWERS, BlockTags.LEAVES, BlockTags.SAPLINGS, BlockTags.CROPS});
      this.tag(GENERATED_PORTAL_DECO)
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM,
               net.minecraft.world.level.block.Blocks.RED_MUSHROOM,
               net.minecraft.world.level.block.Blocks.SHORT_GRASS,
               net.minecraft.world.level.block.Blocks.FERN,
               net.minecraft.world.level.block.Blocks.BLUE_ORCHID,
               net.minecraft.world.level.block.Blocks.AZURE_BLUET,
               net.minecraft.world.level.block.Blocks.LILY_OF_THE_VALLEY,
               net.minecraft.world.level.block.Blocks.OXEYE_DAISY,
               net.minecraft.world.level.block.Blocks.ALLIUM,
               net.minecraft.world.level.block.Blocks.CORNFLOWER,
               net.minecraft.world.level.block.Blocks.WHITE_TULIP,
               net.minecraft.world.level.block.Blocks.PINK_TULIP,
               net.minecraft.world.level.block.Blocks.ORANGE_TULIP,
               net.minecraft.world.level.block.Blocks.RED_TULIP,
               (Block)TFBlocks.MUSHGLOOM.get(),
               (Block)TFBlocks.MAYAPPLE.get(),
               (Block)TFBlocks.FIDDLEHEAD.get()
            }
         );
      this.tag(DARK_TOWER_ALLOWED_POTS)
         .add(
            new Block[]{
               (Block)TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get(),
               (Block)TFBlocks.POTTED_CANOPY_SAPLING.get(),
               (Block)TFBlocks.POTTED_MANGROVE_SAPLING.get(),
               (Block)TFBlocks.POTTED_DARKWOOD_SAPLING.get(),
               (Block)TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get(),
               (Block)TFBlocks.POTTED_MAYAPPLE.get(),
               (Block)TFBlocks.POTTED_FIDDLEHEAD.get(),
               (Block)TFBlocks.POTTED_MUSHGLOOM.get()
            }
         )
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.FLOWER_POT,
               net.minecraft.world.level.block.Blocks.POTTED_POPPY,
               net.minecraft.world.level.block.Blocks.POTTED_BLUE_ORCHID,
               net.minecraft.world.level.block.Blocks.POTTED_ALLIUM,
               net.minecraft.world.level.block.Blocks.POTTED_AZURE_BLUET,
               net.minecraft.world.level.block.Blocks.POTTED_RED_TULIP,
               net.minecraft.world.level.block.Blocks.POTTED_ORANGE_TULIP,
               net.minecraft.world.level.block.Blocks.POTTED_WHITE_TULIP,
               net.minecraft.world.level.block.Blocks.POTTED_PINK_TULIP,
               net.minecraft.world.level.block.Blocks.POTTED_OXEYE_DAISY,
               net.minecraft.world.level.block.Blocks.POTTED_DANDELION,
               net.minecraft.world.level.block.Blocks.POTTED_OAK_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_SPRUCE_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_BIRCH_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_JUNGLE_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_ACACIA_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_DARK_OAK_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_RED_MUSHROOM,
               net.minecraft.world.level.block.Blocks.POTTED_BROWN_MUSHROOM,
               net.minecraft.world.level.block.Blocks.POTTED_DEAD_BUSH,
               net.minecraft.world.level.block.Blocks.POTTED_FERN,
               net.minecraft.world.level.block.Blocks.POTTED_CACTUS,
               net.minecraft.world.level.block.Blocks.POTTED_CORNFLOWER,
               net.minecraft.world.level.block.Blocks.POTTED_LILY_OF_THE_VALLEY,
               net.minecraft.world.level.block.Blocks.POTTED_WITHER_ROSE,
               net.minecraft.world.level.block.Blocks.POTTED_BAMBOO,
               net.minecraft.world.level.block.Blocks.POTTED_CRIMSON_FUNGUS,
               net.minecraft.world.level.block.Blocks.POTTED_WARPED_FUNGUS,
               net.minecraft.world.level.block.Blocks.POTTED_CRIMSON_ROOTS,
               net.minecraft.world.level.block.Blocks.POTTED_WARPED_ROOTS,
               net.minecraft.world.level.block.Blocks.POTTED_AZALEA,
               net.minecraft.world.level.block.Blocks.POTTED_FLOWERING_AZALEA,
               net.minecraft.world.level.block.Blocks.POTTED_MANGROVE_PROPAGULE,
               net.minecraft.world.level.block.Blocks.POTTED_CHERRY_SAPLING,
               net.minecraft.world.level.block.Blocks.POTTED_TORCHFLOWER
            }
         );
      this.tag(BlockTags.FROG_PREFER_JUMP_TO).add((Block)TFBlocks.HUGE_LILY_PAD.get());
      this.tag(TROPHY_PEDESTAL_ACTIVATION_BLOCKS)
         .add(
            new Block[]{
               (Block)TFBlocks.NAGA_TROPHY.get(),
               (Block)TFBlocks.LICH_TROPHY.get(),
               (Block)TFBlocks.MINOSHROOM_TROPHY.get(),
               (Block)TFBlocks.HYDRA_TROPHY.get(),
               (Block)TFBlocks.KNIGHT_PHANTOM_TROPHY.get(),
               (Block)TFBlocks.UR_GHAST_TROPHY.get(),
               (Block)TFBlocks.ALPHA_YETI_TROPHY.get(),
               (Block)TFBlocks.SNOW_QUEEN_TROPHY.get()
            }
         );
      this.tag(FIRE_JET_FUEL).add(net.minecraft.world.level.block.Blocks.LAVA);
      this.tag(ICE_BOMB_REPLACEABLES)
         .add(
            new Block[]{
               (Block)TFBlocks.MAYAPPLE.get(),
               (Block)TFBlocks.FIDDLEHEAD.get(),
               net.minecraft.world.level.block.Blocks.SHORT_GRASS,
               net.minecraft.world.level.block.Blocks.TALL_GRASS,
               net.minecraft.world.level.block.Blocks.FERN,
               net.minecraft.world.level.block.Blocks.LARGE_FERN
            }
         )
         .addTag(BlockTags.FLOWERS);
      this.tag(PLANTS_HANG_ON)
         .addTag(BlockTags.DIRT)
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.MOSS_BLOCK,
               (Block)TFBlocks.MANGROVE_ROOT.get(),
               (Block)TFBlocks.ROOT_BLOCK.get(),
               (Block)TFBlocks.LIVEROOT_BLOCK.get()
            }
         );
      this.tag(OREBERRY_BUSHES_SURVIVE)
         .addTags(new TagKey[]{Blocks.STONES})
         .addTags(new TagKey[]{BlockTags.STONE_BRICKS})
         .addTags(new TagKey[]{Blocks.ORES_IN_GROUND_STONE})
         .addTags(new TagKey[]{Blocks.ORES_IN_GROUND_DEEPSLATE})
         .addTags(new TagKey[]{Blocks.ORES_IN_GROUND_NETHERRACK})
         .addTags(new TagKey[]{Blocks.COBBLESTONES})
         .addTags(new TagKey[]{Blocks.NETHERRACKS})
         .add((Block)TFBlocks.GIANT_COBBLESTONE.get())
         .add(net.minecraft.world.level.block.Blocks.POLISHED_ANDESITE)
         .add(net.minecraft.world.level.block.Blocks.POLISHED_DIORITE)
         .add(net.minecraft.world.level.block.Blocks.POLISHED_GRANITE)
         .add(net.minecraft.world.level.block.Blocks.SMOOTH_STONE)
         .add(net.minecraft.world.level.block.Blocks.INFESTED_CHISELED_STONE_BRICKS)
         .add(net.minecraft.world.level.block.Blocks.INFESTED_CRACKED_STONE_BRICKS)
         .add(net.minecraft.world.level.block.Blocks.INFESTED_MOSSY_STONE_BRICKS)
         .add(net.minecraft.world.level.block.Blocks.INFESTED_STONE_BRICKS);
      this.tag(TF_BERRY_BUSHES_REPLACE)
         .addTags(new TagKey[]{BlockTags.REPLACEABLE})
         .addTags(new TagKey[]{BlockTags.FLOWERS})
         .add((Block)TFBlocks.MAYAPPLE.get());
      this.tag(TF_BERRY_BUSHES_SURVIVE).addTags(new TagKey[]{BlockTags.DIRT}).add(net.minecraft.world.level.block.Blocks.SNOW_BLOCK);
      this.tag(DARK_TOWER_BERRY_BUSHES_SURVIVE)
         .addTag(Blocks.NETHERRACKS)
         .addTag(Blocks.ORES_IN_GROUND_NETHERRACK)
         .add(net.minecraft.world.level.block.Blocks.BLACKSTONE)
         .add(net.minecraft.world.level.block.Blocks.SOUL_SAND)
         .add(net.minecraft.world.level.block.Blocks.SOUL_SOIL);
      this.tag(DARK_TOWER_BERRY_BUSHES_DIE).addTags(new TagKey[]{BlockTags.NYLIUM});
      this.tag(COMMON_PROTECTIONS)
         .add(
            new Block[]{
               (Block)TFBlocks.NAGA_BOSS_SPAWNER.get(),
               (Block)TFBlocks.LICH_BOSS_SPAWNER.get(),
               (Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get(),
               (Block)TFBlocks.HYDRA_BOSS_SPAWNER.get(),
               (Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get(),
               (Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get(),
               (Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get(),
               (Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get(),
               (Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get(),
               (Block)TFBlocks.STRONGHOLD_SHIELD.get(),
               (Block)TFBlocks.UNBREAKABLE_VANISHING_BLOCK.get(),
               (Block)TFBlocks.LOCKED_VANISHING_BLOCK.get(),
               (Block)TFBlocks.PINK_FORCE_FIELD.get(),
               (Block)TFBlocks.ORANGE_FORCE_FIELD.get(),
               (Block)TFBlocks.GREEN_FORCE_FIELD.get(),
               (Block)TFBlocks.BLUE_FORCE_FIELD.get(),
               (Block)TFBlocks.VIOLET_FORCE_FIELD.get(),
               (Block)TFBlocks.SKULL_CHEST.get(),
               (Block)TFBlocks.KEEPSAKE_CASKET.get(),
               (Block)TFBlocks.TROPHY_PEDESTAL.get()
            }
         )
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.BARRIER,
               net.minecraft.world.level.block.Blocks.BEDROCK,
               net.minecraft.world.level.block.Blocks.END_PORTAL,
               net.minecraft.world.level.block.Blocks.END_PORTAL_FRAME,
               net.minecraft.world.level.block.Blocks.END_GATEWAY,
               net.minecraft.world.level.block.Blocks.COMMAND_BLOCK,
               net.minecraft.world.level.block.Blocks.REPEATING_COMMAND_BLOCK,
               net.minecraft.world.level.block.Blocks.CHAIN_COMMAND_BLOCK,
               net.minecraft.world.level.block.Blocks.STRUCTURE_BLOCK,
               net.minecraft.world.level.block.Blocks.JIGSAW,
               net.minecraft.world.level.block.Blocks.MOVING_PISTON,
               net.minecraft.world.level.block.Blocks.LIGHT,
               net.minecraft.world.level.block.Blocks.REINFORCED_DEEPSLATE
            }
         );
      this.tag(BlockTags.DRAGON_IMMUNE)
         .addTag(COMMON_PROTECTIONS)
         .add(new Block[]{(Block)TFBlocks.GIANT_OBSIDIAN.get(), (Block)TFBlocks.FAKE_DIAMOND.get(), (Block)TFBlocks.FAKE_GOLD.get()});
      this.tag(BlockTags.WITHER_IMMUNE).addTag(COMMON_PROTECTIONS).add(new Block[]{(Block)TFBlocks.FAKE_DIAMOND.get(), (Block)TFBlocks.FAKE_GOLD.get()});
      this.tag(CARMINITE_REACTOR_IMMUNE).addTag(COMMON_PROTECTIONS);
      this.tag(CARMINITE_REACTOR_ORES)
         .add(new Block[]{net.minecraft.world.level.block.Blocks.NETHER_QUARTZ_ORE, net.minecraft.world.level.block.Blocks.NETHER_GOLD_ORE});
      this.tag(DEADROCK).add(new Block[]{(Block)TFBlocks.DEADROCK.get(), (Block)TFBlocks.CRACKED_DEADROCK.get(), (Block)TFBlocks.WEATHERED_DEADROCK.get()});
      this.tag(ANNIHILATION_INCLUSIONS)
         .add(net.minecraft.world.level.block.Blocks.NETHER_PORTAL)
         .addTag(DEADROCK)
         .add(
            new Block[]{
               (Block)TFBlocks.CASTLE_BRICK.get(),
               (Block)TFBlocks.THICK_CASTLE_BRICK.get(),
               (Block)TFBlocks.MOSSY_CASTLE_BRICK.get(),
               (Block)TFBlocks.CASTLE_ROOF_TILE.get(),
               (Block)TFBlocks.WORN_CASTLE_BRICK.get()
            }
         )
         .add(
            new Block[]{
               (Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(),
               (Block)TFBlocks.PINK_CASTLE_RUNE_BRICK.get()
            }
         )
         .add(
            new Block[]{
               (Block)TFBlocks.PINK_FORCE_FIELD.get(),
               (Block)TFBlocks.ORANGE_FORCE_FIELD.get(),
               (Block)TFBlocks.GREEN_FORCE_FIELD.get(),
               (Block)TFBlocks.BLUE_FORCE_FIELD.get(),
               (Block)TFBlocks.VIOLET_FORCE_FIELD.get()
            }
         )
         .add(new Block[]{(Block)TFBlocks.BROWN_THORNS.get(), (Block)TFBlocks.GREEN_THORNS.get()});
      this.tag(ANTIBUILDER_IGNORES)
         .add(
            new Block[]{
               net.minecraft.world.level.block.Blocks.REDSTONE_LAMP,
               net.minecraft.world.level.block.Blocks.TNT,
               net.minecraft.world.level.block.Blocks.WATER,
               (Block)TFBlocks.ANTIBUILDER.get(),
               (Block)TFBlocks.CARMINITE_BUILDER.get(),
               (Block)TFBlocks.BUILT_BLOCK.get(),
               (Block)TFBlocks.REACTOR_DEBRIS.get(),
               (Block)TFBlocks.CARMINITE_REACTOR.get(),
               (Block)TFBlocks.REAPPEARING_BLOCK.get(),
               (Block)TFBlocks.GHAST_TRAP.get(),
               (Block)TFBlocks.FAKE_DIAMOND.get(),
               (Block)TFBlocks.FAKE_GOLD.get()
            }
         )
         .addTag(COMMON_PROTECTIONS)
         .addOptional(ResourceLocation.parse("gravestone:gravestone"));
      this.tag(STRUCTURE_BANNED_INTERACTIONS)
         .add(net.minecraft.world.level.block.Blocks.LEVER)
         .add((Block)TFBlocks.ANTIBUILDER.get())
         .addTags(new TagKey[]{BlockTags.BUTTONS, Blocks.CHESTS});
      this.tag(PROGRESSION_ALLOW_BREAKING)
         .add((Block)TFBlocks.SKULL_CHEST.get())
         .add((Block)TFBlocks.KEEPSAKE_CASKET.get())
         .addOptional(ResourceLocation.fromNamespaceAndPath("gravestone", "gravestone"));
      this.tag(CANNOT_TROLL_CAVE_HOLLOW)
         .add(net.minecraft.world.level.block.Blocks.RED_MUSHROOM_BLOCK)
         .add(net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM_BLOCK)
         .add((Block)TFBlocks.HUGE_MUSHGLOOM.get());
      this.tag(ORE_MAGNET_SAFE_REPLACE_BLOCK)
         .addTags(
            new TagKey[]{
               BlockTags.DIRT,
               Blocks.GRAVELS,
               Blocks.SANDS,
               BlockTags.NYLIUM,
               BlockTags.BASE_STONE_OVERWORLD,
               BlockTags.BASE_STONE_NETHER,
               Blocks.END_STONES,
               BlockTags.DEEPSLATE_ORE_REPLACEABLES,
               BlockTags.STONE_ORE_REPLACEABLES,
               ROOT_GROUND
            }
         );
      this.tag(ORE_MAGNET_IGNORE).addTag(BlockTags.COAL_ORES);
      this.tag(MINING_CORE_EXCLUDED).addTag(BlockTags.COAL_ORES);
      this.tag(ROOT_GROUND).add((Block)TFBlocks.ROOT_BLOCK.get());
      this.tag(ROOT_ORES).add((Block)TFBlocks.LIVEROOT_BLOCK.get());
      this.tag(CLOUDS)
         .add(
            new Block[]{
               (Block)TFBlocks.FLUFFY_CLOUD.get(), (Block)TFBlocks.WISPY_CLOUD.get(), (Block)TFBlocks.RAINY_CLOUD.get(), (Block)TFBlocks.SNOWY_CLOUD.get()
            }
         );
      this.tag(TF_CHESTS)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_CHEST.get(),
               (Block)TFBlocks.CANOPY_CHEST.get(),
               (Block)TFBlocks.MANGROVE_CHEST.get(),
               (Block)TFBlocks.DARK_CHEST.get(),
               (Block)TFBlocks.TIME_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_CHEST.get(),
               (Block)TFBlocks.MINING_CHEST.get(),
               (Block)TFBlocks.SORTING_CHEST.get()
            }
         );
      this.tag(BlockTags.DAMPENS_VIBRATIONS).addTag(CLOUDS).add((Block)TFBlocks.ARCTIC_FUR_BLOCK.get());
      this.tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS).add((Block)TFBlocks.ARCTIC_FUR_BLOCK.get());
      this.tag(BlockTags.SMALL_DRIPLEAF_PLACEABLE).add((Block)TFBlocks.UBEROUS_SOIL.get());
      this.tag(BlockTags.FEATURES_CANNOT_REPLACE)
         .addTag(COMMON_PROTECTIONS)
         .add(new Block[]{(Block)TFBlocks.LIVEROOT_BLOCK.get(), (Block)TFBlocks.MANGROVE_ROOT.get(), (Block)TFBlocks.SINISTER_SPAWNER.get()});
      this.tag(WORLDGEN_REPLACEABLES).addTags(new TagKey[]{BlockTags.LUSH_GROUND_REPLACEABLE, BlockTags.REPLACEABLE_BY_TREES});
      this.tag(ROOT_TRACE_SKIP)
         .addTag(BlockTags.LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.ROOT_BLOCK.get(), (Block)TFBlocks.LIVEROOT_BLOCK.get(), (Block)TFBlocks.MANGROVE_ROOT.get(), (Block)TFBlocks.TIME_WOOD.get()
            }
         )
         .addTags(new TagKey[]{BlockTags.FEATURES_CANNOT_REPLACE});
      this.tag(DRUID_PROJECTILE_REPLACEABLE)
         .addTags(
            new TagKey[]{
               BlockTags.LEAVES,
               BlockTags.LOGS,
               BlockTags.PLANKS,
               BlockTags.OVERWORLD_CARVER_REPLACEABLES,
               BlockTags.NETHER_CARVER_REPLACEABLES,
               BlockTags.REPLACEABLE_BY_TREES,
               BlockTags.LUSH_GROUND_REPLACEABLE,
               BlockTags.SCULK_REPLACEABLE,
               Blocks.ORES
            }
         );
      this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add((Block)TFBlocks.TROLLSTEINN.get());
      this.tag(TIME_CORE_EXCLUDED).add(net.minecraft.world.level.block.Blocks.NETHER_PORTAL);
      this.tag(ORE_METER_TARGETABLE)
         .addTag(Blocks.ORES)
         .addTag(BlockTags.BASE_STONE_OVERWORLD)
         .addTag(BlockTags.BASE_STONE_NETHER)
         .addTag(BlockTags.DIRT)
         .addTag(Blocks.SANDS)
         .addTag(Blocks.SANDSTONE_BLOCKS)
         .addTag(BlockTags.TERRACOTTA)
         .addTag(Blocks.GRAVELS)
         .addTag(BlockTags.NYLIUM)
         .addTag(ROOT_ORES)
         .add(net.minecraft.world.level.block.Blocks.BUDDING_AMETHYST)
         .add(net.minecraft.world.level.block.Blocks.CALCITE)
         .add(net.minecraft.world.level.block.Blocks.SOUL_SAND)
         .add(net.minecraft.world.level.block.Blocks.SOUL_SOIL);
      this.tag(PENGUINS_SPAWNABLE_ON).addTag(BlockTags.ICE);
      this.tag(GIANTS_SPAWNABLE_ON).addTag(CLOUDS);
      this.tag(BlockTags.MINEABLE_WITH_AXE)
         .add(
            new Block[]{
               (Block)TFBlocks.HEDGE.get(),
               (Block)TFBlocks.ROOT_BLOCK.get(),
               (Block)TFBlocks.LIVEROOT_BLOCK.get(),
               (Block)TFBlocks.MANGROVE_ROOT.get(),
               (Block)TFBlocks.UNCRAFTING_TABLE.get(),
               (Block)TFBlocks.ENCASED_SMOKER.get(),
               (Block)TFBlocks.ENCASED_FIRE_JET.get(),
               (Block)TFBlocks.TIME_LOG_CORE.get(),
               (Block)TFBlocks.TRANSFORMATION_LOG_CORE.get(),
               (Block)TFBlocks.MINING_LOG_CORE.get(),
               (Block)TFBlocks.SORTING_LOG_CORE.get(),
               (Block)TFBlocks.REAPPEARING_BLOCK.get(),
               (Block)TFBlocks.VANISHING_BLOCK.get(),
               (Block)TFBlocks.ANTIBUILDER.get(),
               (Block)TFBlocks.CARMINITE_REACTOR.get(),
               (Block)TFBlocks.CARMINITE_BUILDER.get(),
               (Block)TFBlocks.GHAST_TRAP.get(),
               (Block)TFBlocks.HUGE_STALK.get(),
               (Block)TFBlocks.HUGE_MUSHGLOOM.get(),
               (Block)TFBlocks.HUGE_MUSHGLOOM_STEM.get(),
               (Block)TFBlocks.CINDER_LOG.get(),
               (Block)TFBlocks.CINDER_WOOD.get(),
               (Block)TFBlocks.IRONWOOD_BLOCK.get(),
               (Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get(),
               (Block)TFBlocks.CANOPY_BOOKSHELF.get(),
               (Block)TFBlocks.TWILIGHT_OAK_CHEST.get(),
               (Block)TFBlocks.CANOPY_CHEST.get(),
               (Block)TFBlocks.MANGROVE_CHEST.get(),
               (Block)TFBlocks.DARK_CHEST.get(),
               (Block)TFBlocks.TIME_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_CHEST.get(),
               (Block)TFBlocks.MINING_CHEST.get(),
               (Block)TFBlocks.SORTING_CHEST.get(),
               (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
               (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
               (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
               (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
               (Block)TFBlocks.SORTING_TRAPPED_CHEST.get(),
               (Block)TFBlocks.HUGE_LILY_PAD.get(),
               (Block)TFBlocks.ENCASED_TOWERWOOD.get()
            }
         )
         .addTags(new TagKey[]{BANISTERS, HOLLOW_LOGS, TOWERWOOD, DRYING_RACKS});
      this.tag(BlockTags.MINEABLE_WITH_HOE)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(),
               (Block)TFBlocks.CANOPY_LEAVES.get(),
               (Block)TFBlocks.MANGROVE_LEAVES.get(),
               (Block)TFBlocks.DARK_LEAVES.get(),
               (Block)TFBlocks.RAINBOW_OAK_LEAVES.get(),
               (Block)TFBlocks.TIME_LEAVES.get(),
               (Block)TFBlocks.TRANSFORMATION_LEAVES.get(),
               (Block)TFBlocks.MINING_LEAVES.get(),
               (Block)TFBlocks.SORTING_LEAVES.get(),
               (Block)TFBlocks.THORN_LEAVES.get(),
               (Block)TFBlocks.THORN_ROSE.get(),
               (Block)TFBlocks.BEANSTALK_LEAVES.get(),
               (Block)TFBlocks.STEELEAF_BLOCK.get(),
               (Block)TFBlocks.ARCTIC_FUR_BLOCK.get()
            }
         );
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(
            new Block[]{
               (Block)TFBlocks.NAGASTONE.get(),
               (Block)TFBlocks.NAGASTONE_HEAD.get(),
               (Block)TFBlocks.STRONGHOLD_SHIELD.get(),
               (Block)TFBlocks.TROPHY_PEDESTAL.get(),
               (Block)TFBlocks.AURORA_PILLAR.get(),
               (Block)TFBlocks.AURORA_SLAB.get(),
               (Block)TFBlocks.UNDERBRICK.get(),
               (Block)TFBlocks.MOSSY_UNDERBRICK.get(),
               (Block)TFBlocks.CRACKED_UNDERBRICK.get(),
               (Block)TFBlocks.UNDERBRICK_FLOOR.get(),
               (Block)TFBlocks.TROLLSTEINN.get(),
               (Block)TFBlocks.GIANT_LEAVES.get(),
               (Block)TFBlocks.GIANT_OBSIDIAN.get(),
               (Block)TFBlocks.GIANT_COBBLESTONE.get(),
               (Block)TFBlocks.GIANT_LOG.get(),
               (Block)TFBlocks.CINDER_FURNACE.get(),
               (Block)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(),
               (Block)TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get(),
               (Block)TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get(),
               (Block)TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get(),
               (Block)TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get(),
               (Block)TFBlocks.KNIGHTMETAL_BLOCK.get(),
               (Block)TFBlocks.IRONWOOD_BLOCK.get(),
               (Block)TFBlocks.FIERY_BLOCK.get(),
               (Block)TFBlocks.CARMINITE_BLOCK.get(),
               (Block)TFBlocks.SPIRAL_BRICKS.get(),
               (Block)TFBlocks.ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.IRON_LADDER.get(),
               (Block)TFBlocks.CANDELABRA.get(),
               (Block)TFBlocks.TWISTED_STONE.get(),
               (Block)TFBlocks.TWISTED_STONE_PILLAR.get(),
               (Block)TFBlocks.SKULL_CHEST.get(),
               (Block)TFBlocks.KEEPSAKE_CASKET.get(),
               (Block)TFBlocks.BOLD_STONE_PILLAR.get(),
               (Block)TFBlocks.TERRORCOTTA_CURVES.value(),
               (Block)TFBlocks.TERRORCOTTA_LINES.value(),
               (Block)TFBlocks.TERRORCOTTA_ARCS.value(),
               (Block)TFBlocks.SINISTER_SPAWNER.value()
            }
         )
         .addTags(new TagKey[]{MAZESTONE, CASTLE_BLOCKS, DEADROCK});
      this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
         .add(new Block[]{(Block)TFBlocks.SMOKER.get(), (Block)TFBlocks.FIRE_JET.get(), (Block)TFBlocks.UBEROUS_SOIL.get()});
      this.tag(Blocks.NEEDS_WOOD_TOOL)
         .add(
            new Block[]{
               (Block)TFBlocks.NAGASTONE.get(),
               (Block)TFBlocks.NAGASTONE_HEAD.get(),
               (Block)TFBlocks.ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.CRACKED_ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.MOSSY_ETCHED_NAGASTONE.get(),
               (Block)TFBlocks.NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_PILLAR.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get(),
               (Block)TFBlocks.NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get(),
               (Block)TFBlocks.SPIRAL_BRICKS.get(),
               (Block)TFBlocks.TWISTED_STONE.get(),
               (Block)TFBlocks.TWISTED_STONE_PILLAR.get(),
               (Block)TFBlocks.BOLD_STONE_PILLAR.get(),
               (Block)TFBlocks.TERRORCOTTA_CURVES.value(),
               (Block)TFBlocks.TERRORCOTTA_LINES.value(),
               (Block)TFBlocks.TERRORCOTTA_ARCS.value(),
               (Block)TFBlocks.AURORA_PILLAR.get(),
               (Block)TFBlocks.AURORA_SLAB.get(),
               (Block)TFBlocks.TROLLSTEINN.get()
            }
         );
      this.tag(BlockTags.NEEDS_STONE_TOOL)
         .add(
            new Block[]{
               (Block)TFBlocks.UNDERBRICK.get(),
               (Block)TFBlocks.CRACKED_UNDERBRICK.get(),
               (Block)TFBlocks.MOSSY_UNDERBRICK.get(),
               (Block)TFBlocks.UNDERBRICK_FLOOR.get(),
               (Block)TFBlocks.IRON_LADDER.get()
            }
         );
      this.tag(BlockTags.NEEDS_IRON_TOOL).add(new Block[]{(Block)TFBlocks.FIERY_BLOCK.get(), (Block)TFBlocks.KNIGHTMETAL_BLOCK.get()});
      this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add((Block)TFBlocks.AURORA_BLOCK.get()).addTags(new TagKey[]{CASTLE_BLOCKS, MAZESTONE, DEADROCK});
      this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add((Block)TFBlocks.UBEROUS_SOIL.get());
      this.tag(BlockTags.MOSS_REPLACEABLE)
         .add(new Block[]{(Block)TFBlocks.ROOT_BLOCK.get(), (Block)TFBlocks.LIVEROOT_BLOCK.get(), (Block)TFBlocks.TROLLSTEINN.get()});
      this.tag(BlockTags.INVALID_SPAWN_INSIDE).add((Block)TFBlocks.TWILIGHT_PORTAL.get());
      this.tag(Blocks.RELOCATION_NOT_SUPPORTED)
         .add(
            new Block[]{
               (Block)TFBlocks.TWILIGHT_PORTAL.get(),
               (Block)TFBlocks.STRONGHOLD_SHIELD.get(),
               (Block)TFBlocks.TIME_LOG_CORE.get(),
               (Block)TFBlocks.TRANSFORMATION_LOG_CORE.get(),
               (Block)TFBlocks.MINING_LOG_CORE.get(),
               (Block)TFBlocks.SORTING_LOG_CORE.get(),
               (Block)TFBlocks.ANTIBUILDER.get(),
               (Block)TFBlocks.BUILT_BLOCK.get(),
               (Block)TFBlocks.FAKE_DIAMOND.get(),
               (Block)TFBlocks.FAKE_GOLD.get(),
               (Block)TFBlocks.REACTOR_DEBRIS.get(),
               (Block)TFBlocks.LOCKED_VANISHING_BLOCK.get(),
               (Block)TFBlocks.VANISHING_BLOCK.get(),
               (Block)TFBlocks.UNBREAKABLE_VANISHING_BLOCK.get(),
               (Block)TFBlocks.REAPPEARING_BLOCK.get(),
               (Block)TFBlocks.BEANSTALK_GROWER.get(),
               (Block)TFBlocks.GIANT_COBBLESTONE.get(),
               (Block)TFBlocks.GIANT_LOG.get(),
               (Block)TFBlocks.GIANT_LEAVES.get(),
               (Block)TFBlocks.GIANT_OBSIDIAN.get(),
               (Block)TFBlocks.BROWN_THORNS.get(),
               (Block)TFBlocks.GREEN_THORNS.get(),
               (Block)TFBlocks.BURNT_THORNS.get(),
               (Block)TFBlocks.PINK_FORCE_FIELD.get(),
               (Block)TFBlocks.ORANGE_FORCE_FIELD.get(),
               (Block)TFBlocks.GREEN_FORCE_FIELD.get(),
               (Block)TFBlocks.BLUE_FORCE_FIELD.get(),
               (Block)TFBlocks.VIOLET_FORCE_FIELD.get(),
               (Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get(),
               (Block)TFBlocks.NAGA_BOSS_SPAWNER.get(),
               (Block)TFBlocks.LICH_BOSS_SPAWNER.get(),
               (Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get(),
               (Block)TFBlocks.HYDRA_BOSS_SPAWNER.get(),
               (Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get(),
               (Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get(),
               (Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get(),
               (Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get()
            }
         );
      this.tag(SUPPORTS_STALAGMITES).addTag(DEADROCK).add(net.minecraft.world.level.block.Blocks.PACKED_ICE);
      this.tag(CARVER_REPLACEABLES).addTag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(net.minecraft.world.level.block.Blocks.SNOW_BLOCK);
      this.tag(INCORRECT_FOR_IRONWOOD_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
      this.tag(INCORRECT_FOR_FIERY_TOOL).addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL);
      this.tag(INCORRECT_FOR_STEELEAF_TOOL).addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
      this.tag(INCORRECT_FOR_KNIGHTMETAL_TOOL).addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
      this.tag(INCORRECT_FOR_GIANT_TOOL).addTag(BlockTags.INCORRECT_FOR_STONE_TOOL);
      this.tag(INCORRECT_FOR_ICE_TOOL).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
      this.tag(INCORRECT_FOR_GLASS_TOOL).addTag(BlockTags.INCORRECT_FOR_WOODEN_TOOL);
      this.tag(Blocks.GLASS_BLOCKS).add((Block)TFBlocks.AURORALIZED_GLASS.get());
      this.tag(Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES).add((Block)TFBlocks.UNCRAFTING_TABLE.get());
      this.tag(Blocks.ROPES).add((Block)TFBlocks.ROPE.get());
      this.tag(MINEABLE_WITH_BLOCK_AND_CHAIN)
         .addTags(new TagKey[]{BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_AXE, BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.MINEABLE_WITH_HOE});
      this.tag(BLOCK_AND_CHAIN_NEVER_BREAKS)
         .addTags(new TagKey[]{MAZESTONE, CASTLE_BLOCKS, DEADROCK, BlockTags.WITHER_IMMUNE})
         .add(
            new Block[]{
               (Block)TFBlocks.TIME_LOG_CORE.get(),
               (Block)TFBlocks.TRANSFORMATION_LOG_CORE.get(),
               (Block)TFBlocks.MINING_LOG_CORE.get(),
               (Block)TFBlocks.SORTING_LOG_CORE.get()
            }
         )
         .add((Block)TFBlocks.GIANT_OBSIDIAN.get());
      this.tag(SMALL_LAKES_DONT_REPLACE)
         .addTags(new TagKey[]{BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.LOGS, BlockTags.LEAVES})
         .add(new Block[]{(Block)TFBlocks.ROOT_BLOCK.get(), (Block)TFBlocks.LIVEROOT_BLOCK.get(), net.minecraft.world.level.block.Blocks.MUSHROOM_STEM});
      this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add((Block)TFBlocks.HUGE_LILY_PAD.get());
      this.tag(BlockTags.SWORD_EFFICIENT).add((Block)TFBlocks.HUGE_LILY_PAD.get());
      this.tag(Blocks.BOOKSHELVES).add((Block)TFBlocks.CANOPY_BOOKSHELF.get());
      this.tag(BlockTags.WOOL_CARPETS).add((Block)TFBlocks.CORONATION_CARPET.get());
      this.tag(BlockTags.FIRE).add((Block)TFBlocks.OMINOUS_FIRE.get());
      this.tag(DRYING_RACKS)
         .add(
            new Block[]{
               (Block)TFBlocks.OAK_DRYING_RACK.get(),
               (Block)TFBlocks.SPRUCE_DRYING_RACK.get(),
               (Block)TFBlocks.BIRCH_DRYING_RACK.get(),
               (Block)TFBlocks.JUNGLE_DRYING_RACK.get(),
               (Block)TFBlocks.ACACIA_DRYING_RACK.get(),
               (Block)TFBlocks.DARK_OAK_DRYING_RACK.get(),
               (Block)TFBlocks.CRIMSON_DRYING_RACK.get(),
               (Block)TFBlocks.WARPED_DRYING_RACK.get(),
               (Block)TFBlocks.VANGROVE_DRYING_RACK.get(),
               (Block)TFBlocks.BAMBOO_DRYING_RACK.get(),
               (Block)TFBlocks.CHERRY_DRYING_RACK.get(),
               (Block)TFBlocks.TWILIGHT_OAK_DRYING_RACK.get(),
               (Block)TFBlocks.CANOPY_DRYING_RACK.get(),
               (Block)TFBlocks.MANGROVE_DRYING_RACK.get(),
               (Block)TFBlocks.DARK_DRYING_RACK.get(),
               (Block)TFBlocks.TIME_DRYING_RACK.get(),
               (Block)TFBlocks.TRANSFORMATION_DRYING_RACK.get(),
               (Block)TFBlocks.MINING_DRYING_RACK.get(),
               (Block)TFBlocks.SORTING_DRYING_RACK.get()
            }
         );
      this.tag(Blocks.STRIPPED_LOGS)
         .add(
            new Block[]{
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get(),
               (Block)TFBlocks.STRIPPED_CANOPY_LOG.get(),
               (Block)TFBlocks.STRIPPED_MANGROVE_LOG.get(),
               (Block)TFBlocks.STRIPPED_DARK_LOG.get(),
               (Block)TFBlocks.STRIPPED_TIME_LOG.get(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get(),
               (Block)TFBlocks.STRIPPED_MINING_LOG.get(),
               (Block)TFBlocks.STRIPPED_SORTING_LOG.get()
            }
         );
      this.tag(Blocks.STRIPPED_WOODS)
         .add(
            new Block[]{
               (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get(),
               (Block)TFBlocks.STRIPPED_CANOPY_WOOD.get(),
               (Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get(),
               (Block)TFBlocks.STRIPPED_DARK_WOOD.get(),
               (Block)TFBlocks.STRIPPED_TIME_WOOD.get(),
               (Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get(),
               (Block)TFBlocks.STRIPPED_MINING_WOOD.get(),
               (Block)TFBlocks.STRIPPED_SORTING_WOOD.get()
            }
         );
      this.tag(Blocks.STORAGE_BLOCKS_SLIME).add((Block)TFBlocks.MAZE_SLIME_BLOCK.get());
   }

   public static TagKey<Block> create(String tagName) {
      return BlockTags.create(TwilightForestMod.prefix(tagName));
   }

   public static TagKey<Block> makeCommonTag(String tagName) {
      return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", tagName));
   }

   public String getName() {
      return "Twilight Forest Block Tags";
   }
}
