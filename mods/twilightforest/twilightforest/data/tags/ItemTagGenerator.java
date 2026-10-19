package twilightforest.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.Tags.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.compat.ModdedItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;

public class ItemTagGenerator extends ModdedItemTagGenerator {
   public static final TagKey<Item> TWILIGHT_OAK_LOGS = create("twilight_oak_logs");
   public static final TagKey<Item> CANOPY_LOGS = create("canopy_logs");
   public static final TagKey<Item> MANGROVE_LOGS = create("mangrove_logs");
   public static final TagKey<Item> DARKWOOD_LOGS = create("darkwood_logs");
   public static final TagKey<Item> TIME_LOGS = create("timewood_logs");
   public static final TagKey<Item> TRANSFORMATION_LOGS = create("transwood_logs");
   public static final TagKey<Item> MINING_LOGS = create("mining_logs");
   public static final TagKey<Item> SORTING_LOGS = create("sortwood_logs");
   public static final TagKey<Item> TWILIGHT_LOGS = create("logs");
   public static final TagKey<Item> BANISTERS = create("banisters");
   public static final TagKey<Item> DRYING_RACKS = create("drying_racks");
   public static final TagKey<Item> PAPER = makeCommonTag("paper");
   public static final TagKey<Item> TOWERWOOD = create("towerwood");
   public static final TagKey<Item> FIERY_VIAL = create("fiery_vial");
   public static final TagKey<Item> ARCTIC_FUR = create("arctic_fur");
   public static final TagKey<Item> CARMINITE_GEMS = makeCommonTag("gems/carminite");
   public static final TagKey<Item> FIERY_INGOTS = makeCommonTag("ingots/fiery");
   public static final TagKey<Item> IRONWOOD_INGOTS = makeCommonTag("ingots/ironwood");
   public static final TagKey<Item> KNIGHTMETAL_INGOTS = makeCommonTag("ingots/knightmetal");
   public static final TagKey<Item> STEELEAF_INGOTS = makeCommonTag("ingots/steeleaf");
   public static final TagKey<Item> WROUGHT_IRON_INGOTS = makeCommonTag("ingots/wrought_iron");
   public static final TagKey<Item> COPPER_NUGGETS = makeCommonTag("nuggets/copper");
   public static final TagKey<Item> STORAGE_BLOCKS_ARCTIC_FUR = makeCommonTag("storage_blocks/arctic_fur");
   public static final TagKey<Item> STORAGE_BLOCKS_CARMINITE = makeCommonTag("storage_blocks/carminite");
   public static final TagKey<Item> STORAGE_BLOCKS_FIERY = makeCommonTag("storage_blocks/fiery");
   public static final TagKey<Item> STORAGE_BLOCKS_IRONWOOD = makeCommonTag("storage_blocks/ironwood");
   public static final TagKey<Item> STORAGE_BLOCKS_KNIGHTMETAL = makeCommonTag("storage_blocks/knightmetal");
   public static final TagKey<Item> STORAGE_BLOCKS_STEELEAF = makeCommonTag("storage_blocks/steeleaf");
   public static final TagKey<Item> RAW_MATERIALS_IRONWOOD = makeCommonTag("raw_materials/ironwood");
   public static final TagKey<Item> RAW_MATERIALS_KNIGHTMETAL = makeCommonTag("raw_materials/knightmetal");
   public static final TagKey<Item> PORTAL_ACTIVATOR = create("portal/activator");
   public static final TagKey<Item> WIP = create("wip");
   public static final TagKey<Item> KOBOLD_PACIFICATION_BREADS = create("kobold_pacification_breads");
   public static final TagKey<Item> BOAR_TEMPT_ITEMS = create("boar_tempt_items");
   public static final TagKey<Item> DEER_TEMPT_ITEMS = create("deer_tempt_items");
   public static final TagKey<Item> DWARF_RABBIT_TEMPT_ITEMS = create("dwarf_rabbit_tempt_items");
   public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = create("penguin_tempt_items");
   public static final TagKey<Item> RAVEN_TEMPT_ITEMS = create("raven_tempt_items");
   public static final TagKey<Item> SQUIRREL_TEMPT_ITEMS = create("squirrel_tempt_items");
   public static final TagKey<Item> TINY_BIRD_TEMPT_ITEMS = create("tiny_bird_tempt_items");
   public static final TagKey<Item> BANNED_UNCRAFTING_INGREDIENTS = create("banned_uncrafting_ingredients");
   public static final TagKey<Item> BANNED_UNCRAFTABLES = create("banned_uncraftables");
   public static final TagKey<Item> UNCRAFTING_IGNORES_COST = create("uncrafting_ignores_cost");
   public static final TagKey<Item> KEPT_ON_DEATH = create("kept_on_death");
   public static final TagKey<Item> BLOCK_AND_CHAIN_ENCHANTABLE = create("enchantable/block_and_chain");
   public static final TagKey<Item> TRAVELLERS_BELT_BLACKLISTED = create("travellers_belt_blacklisted");
   public static final TagKey<Item> TRAVELLERS_AGILE_RANGER_WHITELISTED = create("travellers_agile_ranger_whitelisted");
   public static final TagKey<Item> TRAVELLERS_AGILE_RANGER_BLACKLISTED = create("travellers_agile_ranger_blacklisted");
   public static final TagKey<Item> REPAIRS_IRONWOOD_TOOLS = create("repairs_ironwood_tools");
   public static final TagKey<Item> REPAIRS_STEELEAF_TOOLS = create("repairs_steeleaf_tools");
   public static final TagKey<Item> REPAIRS_KNIGHTMETAL_TOOLS = create("repairs_knightmetal_tools");
   public static final TagKey<Item> REPAIRS_FIERY_TOOLS = create("repairs_fiery_tools");
   public static final TagKey<Item> REPAIRS_GIANT_TOOLS = create("repairs_giant_tools");
   public static final TagKey<Item> REPAIRS_ICE_TOOLS = create("repairs_ice_tools");
   public static final TagKey<Item> SCEPTERS = create("scepters");
   public static final TagKey<Item> IMMUNE_TO_THORNS = create("immune_to_thorns");
   public static final TagKey<Item> FOODS_JERKY = makeCommonTag("foods/jerky");
   public static final TagKey<Item> NUGGETS_COPPER = makeCommonTag("nuggets/copper");
   public static final TagKey<Item> RENDER_LOWER_ON_DRYING_RACK = create("lower_on_drying_rack");
   public static final TagKey<Item> TROPHIES = create("trophies");
   public static final TagKey<Item> EMPERORS_CLOTH_APPLICABLE = create("emperors_cloth_applicable");

   public ItemTagGenerator(PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
      super(output, future, provider, helper);
   }

   @Override
   protected void addTags(Provider provider) {
      super.addTags(provider);
      this.copy(BlockTagGenerator.TWILIGHT_OAK_LOGS, TWILIGHT_OAK_LOGS);
      this.copy(BlockTagGenerator.CANOPY_LOGS, CANOPY_LOGS);
      this.copy(BlockTagGenerator.MANGROVE_LOGS, MANGROVE_LOGS);
      this.copy(BlockTagGenerator.DARKWOOD_LOGS, DARKWOOD_LOGS);
      this.copy(BlockTagGenerator.TIME_LOGS, TIME_LOGS);
      this.copy(BlockTagGenerator.TRANSFORMATION_LOGS, TRANSFORMATION_LOGS);
      this.copy(BlockTagGenerator.MINING_LOGS, MINING_LOGS);
      this.copy(BlockTagGenerator.SORTING_LOGS, SORTING_LOGS);
      this.copy(BlockTagGenerator.TF_LOGS, TWILIGHT_LOGS);
      this.tag(ItemTags.LOGS).addTag(TWILIGHT_LOGS);
      this.tag(ItemTags.LOGS_THAT_BURN)
         .addTag(TWILIGHT_OAK_LOGS)
         .addTag(CANOPY_LOGS)
         .addTag(MANGROVE_LOGS)
         .addTag(TIME_LOGS)
         .addTag(TRANSFORMATION_LOGS)
         .addTag(MINING_LOGS)
         .addTag(SORTING_LOGS);
      this.tag(Items.STRIPPED_LOGS)
         .add(
            new Item[]{
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_CANOPY_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_MANGROVE_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_DARK_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TIME_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TRANSFORMATION_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_MINING_LOG.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_SORTING_LOG.value()).asItem()
            }
         );
      this.tag(Items.STRIPPED_WOODS)
         .add(
            new Item[]{
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_CANOPY_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_MANGROVE_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_DARK_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TIME_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_MINING_WOOD.value()).asItem(),
               ((RotatedPillarBlock)TFBlocks.STRIPPED_SORTING_WOOD.value()).asItem()
            }
         );
      this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
      this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
      this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
      this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
      this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
      this.copy(Blocks.FENCE_GATES_WOODEN, Items.FENCE_GATES_WOODEN);
      this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
      this.copy(BlockTags.SLABS, ItemTags.SLABS);
      this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
      this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
      this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
      this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
      this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
      this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
      this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
      this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
      this.copy(Blocks.CHESTS_WOODEN, Items.CHESTS_WOODEN);
      this.copy(Blocks.CHESTS_TRAPPED, Items.CHESTS_TRAPPED);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_ARCTIC_FUR, STORAGE_BLOCKS_ARCTIC_FUR);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_CARMINITE, STORAGE_BLOCKS_CARMINITE);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_FIERY, STORAGE_BLOCKS_FIERY);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_IRONWOOD, STORAGE_BLOCKS_IRONWOOD);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL, STORAGE_BLOCKS_KNIGHTMETAL);
      this.copy(BlockTagGenerator.STORAGE_BLOCKS_STEELEAF, STORAGE_BLOCKS_STEELEAF);
      this.tag(Items.STORAGE_BLOCKS)
         .addTag(STORAGE_BLOCKS_FIERY)
         .addTag(STORAGE_BLOCKS_ARCTIC_FUR)
         .addTag(STORAGE_BLOCKS_CARMINITE)
         .addTag(STORAGE_BLOCKS_IRONWOOD)
         .addTag(STORAGE_BLOCKS_KNIGHTMETAL)
         .addTag(STORAGE_BLOCKS_STEELEAF);
      this.copy(BlockTagGenerator.TOWERWOOD, TOWERWOOD);
      this.copy(BlockTagGenerator.BANISTERS, BANISTERS);
      this.copy(BlockTagGenerator.DRYING_RACKS, DRYING_RACKS);
      this.tag(PAPER).add(net.minecraft.world.item.Items.PAPER);
      this.tag(Items.FEATHERS).add((Item)TFItems.RAVEN_FEATHER.get());
      this.tag(FIERY_VIAL).add(new Item[]{(Item)TFItems.FIERY_BLOOD.get(), (Item)TFItems.FIERY_TEARS.get()});
      this.tag(ARCTIC_FUR).add((Item)TFItems.ARCTIC_FUR.get());
      this.tag(CARMINITE_GEMS).add((Item)TFItems.CARMINITE.get());
      this.tag(FIERY_INGOTS).add((Item)TFItems.FIERY_INGOT.get());
      this.tag(IRONWOOD_INGOTS).add((Item)TFItems.IRONWOOD_INGOT.get());
      this.tag(KNIGHTMETAL_INGOTS).add((Item)TFItems.KNIGHTMETAL_INGOT.get());
      this.tag(STEELEAF_INGOTS).add((Item)TFItems.STEELEAF_INGOT.get());
      this.tag(WROUGHT_IRON_INGOTS).add((Item)TFItems.WROUGHT_IRON_BAR.get());
      this.tag(Items.GEMS).addTag(CARMINITE_GEMS);
      this.tag(Items.INGOTS).addTag(IRONWOOD_INGOTS).addTag(FIERY_INGOTS).addTag(KNIGHTMETAL_INGOTS).addTag(STEELEAF_INGOTS);
      this.tag(Items.NUGGETS).add(TFItems.COPPER_NUGGET.asItem());
      this.tag(COPPER_NUGGETS).add(TFItems.COPPER_NUGGET.asItem());
      this.tag(RAW_MATERIALS_IRONWOOD).add((Item)TFItems.RAW_IRONWOOD.get());
      this.tag(RAW_MATERIALS_KNIGHTMETAL).add((Item)TFItems.ARMOR_SHARD_CLUSTER.get());
      this.tag(Items.RAW_MATERIALS).addTag(RAW_MATERIALS_IRONWOOD).addTag(RAW_MATERIALS_KNIGHTMETAL);
      this.tag(PORTAL_ACTIVATOR).addTag(Items.GEMS_DIAMOND);
      this.tag(ItemTags.BOATS)
         .add(
            new Item[]{
               (Item)TFItems.TWILIGHT_OAK_BOAT.get(),
               (Item)TFItems.CANOPY_BOAT.get(),
               (Item)TFItems.MANGROVE_BOAT.get(),
               (Item)TFItems.DARK_BOAT.get(),
               (Item)TFItems.TIME_BOAT.get(),
               (Item)TFItems.TRANSFORMATION_BOAT.get(),
               (Item)TFItems.MINING_BOAT.get(),
               (Item)TFItems.SORTING_BOAT.get()
            }
         );
      this.tag(ItemTags.CHEST_BOATS)
         .add(
            new Item[]{
               (Item)TFItems.TWILIGHT_OAK_CHEST_BOAT.get(),
               (Item)TFItems.CANOPY_CHEST_BOAT.get(),
               (Item)TFItems.MANGROVE_CHEST_BOAT.get(),
               (Item)TFItems.DARK_CHEST_BOAT.get(),
               (Item)TFItems.TIME_CHEST_BOAT.get(),
               (Item)TFItems.TRANSFORMATION_CHEST_BOAT.get(),
               (Item)TFItems.MINING_CHEST_BOAT.get(),
               (Item)TFItems.SORTING_CHEST_BOAT.get()
            }
         );
      this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES)
         .add(
            new Item[]{
               (Item)TFItems.FIERY_HELMET.get(),
               (Item)TFItems.FIERY_CHESTPLATE.get(),
               (Item)TFItems.FIERY_LEGGINGS.get(),
               (Item)TFItems.FIERY_BOOTS.get(),
               (Item)TFItems.ARCTIC_HELMET.get(),
               (Item)TFItems.ARCTIC_CHESTPLATE.get(),
               (Item)TFItems.ARCTIC_LEGGINGS.get(),
               (Item)TFItems.ARCTIC_BOOTS.get(),
               (Item)TFItems.YETI_HELMET.get(),
               (Item)TFItems.YETI_CHESTPLATE.get(),
               (Item)TFItems.YETI_LEGGINGS.get(),
               (Item)TFItems.YETI_BOOTS.get(),
               (Item)TFItems.TRAVELLERS_VEST.get(),
               (Item)TFItems.TRAVELLERS_BOOTS.get()
            }
         );
      this.tag(WIP)
         .add(
            new Item[]{
               TFBlocks.AURORALIZED_GLASS.asItem(),
               (Item)TFItems.QUEST_RAM_BANNER_PATTERN.get(),
               TFBlocks.FINAL_BOSS_BOSS_SPAWNER.asItem(),
               (Item)TFItems.CUBE_TALISMAN.get(),
               (Item)TFItems.CUBE_OF_ANNIHILATION.get(),
               TFBlocks.CINDER_FURNACE.asItem(),
               TFBlocks.CINDER_LOG.asItem(),
               TFBlocks.CINDER_WOOD.asItem(),
               TFBlocks.SLIDER.asItem(),
               TFBlocks.BRAZIER.asItem()
            }
         );
      this.tag(KOBOLD_PACIFICATION_BREADS).add(net.minecraft.world.item.Items.BREAD);
      this.tag(BOAR_TEMPT_ITEMS).addTag(Items.CROPS_CARROT).addTag(Items.CROPS_POTATO).addTag(Items.CROPS_BEETROOT);
      this.tag(DEER_TEMPT_ITEMS).addTag(Items.CROPS_WHEAT).add(net.minecraft.world.item.Items.APPLE).add((Item)TFItems.SHIKA_SENBEI.get());
      this.tag(DWARF_RABBIT_TEMPT_ITEMS)
         .addTag(Items.CROPS_CARROT)
         .add(net.minecraft.world.item.Items.GOLDEN_CARROT)
         .add(net.minecraft.world.item.Items.DANDELION);
      this.tag(PENGUIN_TEMPT_ITEMS).addTag(ItemTags.FISHES);
      this.tag(RAVEN_TEMPT_ITEMS).addTag(Items.SEEDS);
      this.tag(SQUIRREL_TEMPT_ITEMS).addTag(Items.SEEDS);
      this.tag(TINY_BIRD_TEMPT_ITEMS).addTag(Items.SEEDS);
      this.tag(BANNED_UNCRAFTING_INGREDIENTS)
         .add(
            new Item[]{
               TFBlocks.INFESTED_TOWERWOOD.asItem(),
               TFBlocks.HOLLOW_OAK_SAPLING.asItem(),
               TFBlocks.TIME_SAPLING.asItem(),
               TFBlocks.TRANSFORMATION_SAPLING.asItem(),
               TFBlocks.MINING_SAPLING.asItem(),
               TFBlocks.SORTING_SAPLING.asItem(),
               (Item)TFItems.TRANSFORMATION_POWDER.get()
            }
         );
      this.tag(BANNED_UNCRAFTABLES);
      this.tag(UNCRAFTING_IGNORES_COST).addTag(Items.RODS_WOODEN);
      this.tag(KEPT_ON_DEATH).add(new Item[]{(Item)TFItems.TOWER_KEY.get(), (Item)TFItems.PHANTOM_HELMET.get(), (Item)TFItems.PHANTOM_CHESTPLATE.get()});
      this.tag(SCEPTERS)
         .add(
            new Item[]{
               (Item)TFItems.TWILIGHT_SCEPTER.get(),
               (Item)TFItems.LIFEDRAIN_SCEPTER.get(),
               (Item)TFItems.ZOMBIE_SCEPTER.get(),
               (Item)TFItems.FORTIFICATION_SCEPTER.get()
            }
         );
      this.tag(IMMUNE_TO_THORNS).add(new Item[]{TFBlocks.THORN_LEAVES.asItem(), TFBlocks.THORN_ROSE.asItem()});
      this.tag(ItemTags.PIGLIN_LOVED)
         .add(
            new Item[]{
               (Item)TFItems.GOLDEN_MINOTAUR_AXE.get(),
               (Item)TFItems.CHARM_OF_KEEPING_3.get(),
               (Item)TFItems.CHARM_OF_LIFE_2.get(),
               (Item)TFItems.LAMP_OF_CINDERS.get()
            }
         );
      this.tag(ItemTags.SKULLS)
         .add(
            new Item[]{
               (Item)TFItems.ZOMBIE_SKULL_CANDLE.get(),
               (Item)TFItems.SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.CREEPER_SKULL_CANDLE.get(),
               (Item)TFItems.PLAYER_SKULL_CANDLE.get(),
               (Item)TFItems.PIGLIN_SKULL_CANDLE.get()
            }
         );
      this.tag(ItemTags.NOTE_BLOCK_TOP_INSTRUMENTS)
         .add(
            new Item[]{
               (Item)TFItems.ZOMBIE_SKULL_CANDLE.get(),
               (Item)TFItems.SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.CREEPER_SKULL_CANDLE.get(),
               (Item)TFItems.PLAYER_SKULL_CANDLE.get(),
               (Item)TFItems.PIGLIN_SKULL_CANDLE.get()
            }
         );
      this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_HELMET.get(),
               (Item)TFItems.STEELEAF_HELMET.get(),
               (Item)TFItems.KNIGHTMETAL_HELMET.get(),
               (Item)TFItems.PHANTOM_HELMET.get(),
               (Item)TFItems.FIERY_HELMET.get(),
               (Item)TFItems.ARCTIC_HELMET.get(),
               (Item)TFItems.YETI_HELMET.get()
            }
         );
      this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
         .add(
            new Item[]{
               (Item)TFItems.NAGA_CHESTPLATE.get(),
               (Item)TFItems.IRONWOOD_CHESTPLATE.get(),
               (Item)TFItems.STEELEAF_CHESTPLATE.get(),
               (Item)TFItems.KNIGHTMETAL_CHESTPLATE.get(),
               (Item)TFItems.PHANTOM_CHESTPLATE.get(),
               (Item)TFItems.FIERY_CHESTPLATE.get(),
               (Item)TFItems.ARCTIC_CHESTPLATE.get(),
               (Item)TFItems.YETI_CHESTPLATE.get()
            }
         );
      this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
         .add(
            new Item[]{
               (Item)TFItems.NAGA_LEGGINGS.get(),
               (Item)TFItems.IRONWOOD_LEGGINGS.get(),
               (Item)TFItems.STEELEAF_LEGGINGS.get(),
               (Item)TFItems.KNIGHTMETAL_LEGGINGS.get(),
               (Item)TFItems.FIERY_LEGGINGS.get(),
               (Item)TFItems.ARCTIC_LEGGINGS.get(),
               (Item)TFItems.YETI_LEGGINGS.get()
            }
         );
      this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_BOOTS.get(),
               (Item)TFItems.STEELEAF_BOOTS.get(),
               (Item)TFItems.KNIGHTMETAL_BOOTS.get(),
               (Item)TFItems.FIERY_BOOTS.get(),
               (Item)TFItems.ARCTIC_BOOTS.get(),
               (Item)TFItems.YETI_BOOTS.get()
            }
         );
      this.tag(ItemTags.SWORDS)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_SWORD.get(),
               (Item)TFItems.STEELEAF_SWORD.get(),
               (Item)TFItems.KNIGHTMETAL_SWORD.get(),
               (Item)TFItems.FIERY_SWORD.get(),
               (Item)TFItems.GIANT_SWORD.get(),
               (Item)TFItems.ICE_SWORD.get(),
               (Item)TFItems.GLASS_SWORD.get()
            }
         );
      this.tag(ItemTags.PICKAXES)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_PICKAXE.get(),
               (Item)TFItems.STEELEAF_PICKAXE.get(),
               (Item)TFItems.KNIGHTMETAL_PICKAXE.get(),
               (Item)TFItems.MAZEBREAKER_PICKAXE.get(),
               (Item)TFItems.FIERY_PICKAXE.get(),
               (Item)TFItems.GIANT_PICKAXE.get()
            }
         );
      this.tag(ItemTags.AXES)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_AXE.get(),
               (Item)TFItems.STEELEAF_AXE.get(),
               (Item)TFItems.KNIGHTMETAL_AXE.get(),
               (Item)TFItems.GOLDEN_MINOTAUR_AXE.get(),
               (Item)TFItems.DIAMOND_MINOTAUR_AXE.get()
            }
         );
      this.tag(ItemTags.SHOVELS).add(new Item[]{(Item)TFItems.IRONWOOD_SHOVEL.get(), (Item)TFItems.STEELEAF_SHOVEL.get()});
      this.tag(ItemTags.HOES).add(new Item[]{(Item)TFItems.IRONWOOD_HOE.get(), (Item)TFItems.STEELEAF_HOE.get()});
      this.tag(Items.TOOLS_SHIELD).add((Item)TFItems.KNIGHTMETAL_SHIELD.get());
      this.tag(Items.TOOLS_BOW)
         .add(new Item[]{(Item)TFItems.TRIPLE_BOW.get(), (Item)TFItems.SEEKER_BOW.get(), (Item)TFItems.ICE_BOW.get(), (Item)TFItems.ENDER_BOW.get()});
      this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_PICKAXE.get(),
               (Item)TFItems.STEELEAF_PICKAXE.get(),
               (Item)TFItems.KNIGHTMETAL_PICKAXE.get(),
               (Item)TFItems.MAZEBREAKER_PICKAXE.get(),
               (Item)TFItems.FIERY_PICKAXE.get(),
               (Item)TFItems.GIANT_PICKAXE.get()
            }
         );
      this.tag(ItemTags.SMALL_FLOWERS).add(TFBlocks.THORN_ROSE.asItem());
      this.tag(ItemTags.TRIM_MATERIALS)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_INGOT.get(),
               (Item)TFItems.STEELEAF_INGOT.get(),
               (Item)TFItems.KNIGHTMETAL_INGOT.get(),
               (Item)TFItems.NAGA_SCALE.get(),
               (Item)TFItems.CARMINITE.get(),
               (Item)TFItems.FIERY_INGOT.get()
            }
         );
      this.tag(TRAVELLERS_AGILE_RANGER_WHITELISTED).add((Item)TFItems.MOONWORM_QUEEN.get());
      this.tag(REPAIRS_IRONWOOD_TOOLS).addTag(IRONWOOD_INGOTS);
      this.tag(REPAIRS_STEELEAF_TOOLS).addTag(STEELEAF_INGOTS);
      this.tag(REPAIRS_KNIGHTMETAL_TOOLS).addTag(KNIGHTMETAL_INGOTS);
      this.tag(REPAIRS_FIERY_TOOLS).addTag(FIERY_INGOTS);
      this.tag(REPAIRS_GIANT_TOOLS).add(TFBlocks.GIANT_COBBLESTONE.asItem());
      this.tag(REPAIRS_ICE_TOOLS)
         .add(
            new Item[]{
               net.minecraft.world.level.block.Blocks.ICE.asItem(),
               net.minecraft.world.level.block.Blocks.PACKED_ICE.asItem(),
               net.minecraft.world.level.block.Blocks.BLUE_ICE.asItem()
            }
         );
      this.tag(ItemTags.MEAT)
         .add(
            new Item[]{
               (Item)TFItems.RAW_VENISON.get(),
               (Item)TFItems.COOKED_VENISON.get(),
               (Item)TFItems.RAW_MEEF.get(),
               (Item)TFItems.COOKED_MEEF.get(),
               (Item)TFItems.MEEF_STROGANOFF.get(),
               (Item)TFItems.EXPERIMENT_115.get(),
               (Item)TFItems.HYDRA_CHOP.get(),
               (Item)TFItems.MONSTER_JERKY.get(),
               (Item)TFItems.BEEF_JERKY.get(),
               (Item)TFItems.PORK_JERKY.get(),
               (Item)TFItems.CHICKEN_JERKY.get(),
               (Item)TFItems.RABBIT_JERKY.get(),
               (Item)TFItems.MUTTON_JERKY.get(),
               (Item)TFItems.VENISON_JERKY.get(),
               (Item)TFItems.MEEF_JERKY.get(),
               (Item)TFItems.COD_JERKY.get(),
               (Item)TFItems.SALMON_JERKY.get(),
               (Item)TFItems.TROPICAL_FISH_JERKY.get(),
               (Item)TFItems.FUGU_JERKY.get()
            }
         );
      this.tag(ItemTags.BEACON_PAYMENT_ITEMS).addTags(new TagKey[]{IRONWOOD_INGOTS, STEELEAF_INGOTS, KNIGHTMETAL_INGOTS, FIERY_INGOTS});
      this.tag(ItemTags.TRIMMABLE_ARMOR)
         .remove((Item)TFItems.YETI_HELMET.get())
         .remove((Item)TFItems.TRAVELLERS_GOGGLES.get())
         .remove((Item)TFItems.TRAVELLERS_VEST.get())
         .remove((Item)TFItems.TRAVELLERS_GLOVES.get())
         .remove((Item)TFItems.TRAVELLERS_BELT.get())
         .remove((Item)TFItems.TRAVELLERS_WINGS.get())
         .remove((Item)TFItems.TRAVELLERS_BOOTS.get());
      this.tag(ItemTags.HEAD_ARMOR)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_HELMET.get(),
               (Item)TFItems.STEELEAF_HELMET.get(),
               (Item)TFItems.KNIGHTMETAL_HELMET.get(),
               (Item)TFItems.ARCTIC_HELMET.get(),
               (Item)TFItems.YETI_HELMET.get(),
               (Item)TFItems.FIERY_HELMET.get(),
               (Item)TFItems.PHANTOM_HELMET.get(),
               (Item)TFItems.TRAVELLERS_GOGGLES.get()
            }
         );
      this.tag(ItemTags.CHEST_ARMOR)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_CHESTPLATE.get(),
               (Item)TFItems.STEELEAF_CHESTPLATE.get(),
               (Item)TFItems.KNIGHTMETAL_CHESTPLATE.get(),
               (Item)TFItems.ARCTIC_CHESTPLATE.get(),
               (Item)TFItems.YETI_CHESTPLATE.get(),
               (Item)TFItems.FIERY_CHESTPLATE.get(),
               (Item)TFItems.PHANTOM_CHESTPLATE.get(),
               (Item)TFItems.NAGA_CHESTPLATE.get(),
               (Item)TFItems.TRAVELLERS_VEST.get(),
               (Item)TFItems.TRAVELLERS_GLOVES.get()
            }
         );
      this.tag(ItemTags.LEG_ARMOR)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_LEGGINGS.get(),
               (Item)TFItems.STEELEAF_LEGGINGS.get(),
               (Item)TFItems.KNIGHTMETAL_LEGGINGS.get(),
               (Item)TFItems.ARCTIC_LEGGINGS.get(),
               (Item)TFItems.YETI_LEGGINGS.get(),
               (Item)TFItems.FIERY_LEGGINGS.get(),
               (Item)TFItems.NAGA_LEGGINGS.get(),
               (Item)TFItems.TRAVELLERS_WINGS.get(),
               (Item)TFItems.TRAVELLERS_BELT.get()
            }
         );
      this.tag(ItemTags.FOOT_ARMOR)
         .add(
            new Item[]{
               (Item)TFItems.IRONWOOD_BOOTS.get(),
               (Item)TFItems.STEELEAF_BOOTS.get(),
               (Item)TFItems.KNIGHTMETAL_BOOTS.get(),
               (Item)TFItems.ARCTIC_BOOTS.get(),
               (Item)TFItems.YETI_BOOTS.get(),
               (Item)TFItems.FIERY_BOOTS.get(),
               (Item)TFItems.TRAVELLERS_BOOTS.get()
            }
         );
      this.tag(ItemTags.DYEABLE)
         .add(
            new Item[]{
               (Item)TFItems.ARCTIC_HELMET.get(), (Item)TFItems.ARCTIC_CHESTPLATE.get(), (Item)TFItems.ARCTIC_LEGGINGS.get(), (Item)TFItems.ARCTIC_BOOTS.get()
            }
         );
      this.tag(BLOCK_AND_CHAIN_ENCHANTABLE).add((Item)TFItems.BLOCK_AND_CHAIN.get());
      this.tag(ItemTags.BOW_ENCHANTABLE)
         .add(new Item[]{(Item)TFItems.TRIPLE_BOW.get(), (Item)TFItems.SEEKER_BOW.get(), (Item)TFItems.ICE_BOW.get(), (Item)TFItems.ENDER_BOW.get()});
      this.tag(ItemTags.MINING_ENCHANTABLE).add((Item)TFItems.BLOCK_AND_CHAIN.get());
      this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add((Item)TFItems.BLOCK_AND_CHAIN.get());
      this.tag(ItemTags.DURABILITY_ENCHANTABLE)
         .add(
            new Item[]{
               (Item)TFItems.TRIPLE_BOW.get(),
               (Item)TFItems.SEEKER_BOW.get(),
               (Item)TFItems.ICE_BOW.get(),
               (Item)TFItems.ENDER_BOW.get(),
               (Item)TFItems.BLOCK_AND_CHAIN.get(),
               (Item)TFItems.KNIGHTMETAL_SHIELD.get(),
               (Item)TFItems.ORE_MAGNET.get(),
               (Item)TFItems.PEACOCK_FEATHER_FAN.get(),
               (Item)TFItems.CRUMBLE_HORN.get()
            }
         );
      this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).remove((Item)TFItems.FIERY_SWORD.get(), new Item[]{(Item)TFItems.ICE_SWORD.get()});
      this.tag(ItemTags.VANISHING_ENCHANTABLE).remove((Item)TFItems.PHANTOM_HELMET.get(), new Item[]{(Item)TFItems.PHANTOM_CHESTPLATE.get()});
      this.tag(ItemTags.EQUIPPABLE_ENCHANTABLE).remove((Item)TFItems.PHANTOM_HELMET.get(), new Item[]{(Item)TFItems.PHANTOM_CHESTPLATE.get()});
      this.tag(ItemTags.BREAKS_DECORATED_POTS).add((Item)TFItems.BLOCK_AND_CHAIN.get());
      this.tag(Items.FOODS)
         .addTag(FOODS_JERKY)
         .add(
            new Item[]{
               (Item)TFItems.GELATINOUS_SLIME_DROP.get(),
               (Item)TFItems.GELATINOUS_MAZE_SLIME_DROP.get(),
               (Item)TFItems.BERRY_MEDLEY.get(),
               (Item)TFItems.MAZE_WAFER.get()
            }
         );
      this.tag(FOODS_JERKY)
         .add(
            new Item[]{
               (Item)TFItems.MONSTER_JERKY.get(),
               (Item)TFItems.BEEF_JERKY.get(),
               (Item)TFItems.PORK_JERKY.get(),
               (Item)TFItems.CHICKEN_JERKY.get(),
               (Item)TFItems.RABBIT_JERKY.get(),
               (Item)TFItems.MUTTON_JERKY.get(),
               (Item)TFItems.VENISON_JERKY.get(),
               (Item)TFItems.MEEF_JERKY.get(),
               (Item)TFItems.COD_JERKY.get(),
               (Item)TFItems.SALMON_JERKY.get(),
               (Item)TFItems.TROPICAL_FISH_JERKY.get(),
               (Item)TFItems.FUGU_JERKY.get()
            }
         );
      this.tag(Items.FOODS_BERRY)
         .add(
            new Item[]{
               (Item)TFItems.TORCHBERRIES.get(),
               (Item)TFItems.RASPBERRY.get(),
               (Item)TFItems.BLACKBERRY.get(),
               (Item)TFItems.BLUEBERRY.get(),
               (Item)TFItems.MALOBERRY.get(),
               (Item)TFItems.DUSKBERRY.get(),
               (Item)TFItems.SKYBERRY.get(),
               (Item)TFItems.BLIGHTBERRY.get(),
               (Item)TFItems.STINGBERRY.get()
            }
         );
      this.tag(Items.FOODS_RAW_MEAT).add(new Item[]{(Item)TFItems.RAW_VENISON.get(), (Item)TFItems.RAW_MEEF.get()});
      this.tag(Items.FOODS_COOKED_MEAT).add(new Item[]{(Item)TFItems.COOKED_VENISON.get(), (Item)TFItems.COOKED_MEEF.get(), (Item)TFItems.HYDRA_CHOP.get()});
      this.tag(Items.FOODS_SOUP).add(new Item[]{(Item)TFItems.MEEF_STROGANOFF.get(), (Item)TFItems.MOSS_SOUP.get()});
      this.tag(Items.FOODS_EDIBLE_WHEN_PLACED).add((Item)TFItems.EXPERIMENT_115.get());
      this.tag(Items.ROPES).add((Item)TFItems.ROPE.get());
      this.tag(Items.MUSHROOMS).add(TFBlocks.MUSHGLOOM.asItem());
      this.tag(Items.MUSIC_DISCS)
         .add(
            new Item[]{
               (Item)TFItems.MUSIC_DISC_RADIANCE.get(),
               (Item)TFItems.MUSIC_DISC_STEPS.get(),
               (Item)TFItems.MUSIC_DISC_SUPERSTITIOUS.get(),
               (Item)TFItems.MUSIC_DISC_HOME.get(),
               (Item)TFItems.MUSIC_DISC_WAYFARER.get(),
               (Item)TFItems.MUSIC_DISC_FINDINGS.get(),
               (Item)TFItems.MUSIC_DISC_MAKER.get(),
               (Item)TFItems.MUSIC_DISC_THREAD.get(),
               (Item)TFItems.MUSIC_DISC_MOTION.get()
            }
         );
      this.tag(NUGGETS_COPPER).add((Item)TFItems.COPPER_NUGGET.get());
      this.tag(Items.NUGGETS).addTag(NUGGETS_COPPER);
      this.tag(Items.SLIME_BALLS).add((Item)TFItems.MAZE_SLIME_BALL.get());
      this.tag(Items.STORAGE_BLOCKS_SLIME).add(TFBlocks.MAZE_SLIME_BLOCK.asItem());
      this.tag(RENDER_LOWER_ON_DRYING_RACK)
         .add(new Item[]{(Item)TFItems.GELATINOUS_SLIME_DROP.get(), (Item)TFItems.GELATINOUS_MAZE_SLIME_DROP.get()})
         .add(
            new Item[]{
               (Item)TFItems.ZOMBIE_SKULL_CANDLE.get(),
               (Item)TFItems.SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.CREEPER_SKULL_CANDLE.get(),
               (Item)TFItems.PLAYER_SKULL_CANDLE.get(),
               (Item)TFItems.PIGLIN_SKULL_CANDLE.get()
            }
         )
         .add(
            new Item[]{
               net.minecraft.world.item.Items.POINTED_DRIPSTONE,
               net.minecraft.world.item.Items.RECOVERY_COMPASS,
               net.minecraft.world.item.Items.CLOCK,
               net.minecraft.world.item.Items.SPYGLASS,
               net.minecraft.world.item.Items.TRIDENT
            }
         )
         .addTag(ItemTags.BANNERS)
         .addTag(Items.TOOLS)
         .remove(Items.TOOLS_SHIELD);
      this.tag(TROPHIES)
         .add(
            new Item[]{
               (Item)TFItems.NAGA_TROPHY.get(),
               (Item)TFItems.LICH_TROPHY.get(),
               (Item)TFItems.MINOSHROOM_TROPHY.get(),
               (Item)TFItems.HYDRA_TROPHY.get(),
               (Item)TFItems.KNIGHT_PHANTOM_TROPHY.get(),
               (Item)TFItems.UR_GHAST_TROPHY.get(),
               (Item)TFItems.ALPHA_YETI_TROPHY.get(),
               (Item)TFItems.SNOW_QUEEN_TROPHY.get()
            }
         );
      this.tag(EMPERORS_CLOTH_APPLICABLE).addTag(Items.ARMORS).add(net.minecraft.world.item.Items.ELYTRA);
   }

   public static TagKey<Item> create(String tagName) {
      return ItemTags.create(TwilightForestMod.prefix(tagName));
   }

   public static TagKey<Item> makeCommonTag(String tagName) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", tagName));
   }

   public String getName() {
      return "Twilight Forest Item Tags";
   }
}
