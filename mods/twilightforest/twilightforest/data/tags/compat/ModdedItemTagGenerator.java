package twilightforest.data.tags.compat;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import twilightforest.block.HugeLilyPadBlock;
import twilightforest.block.WroughtIronFenceBlock;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;

public class ModdedItemTagGenerator extends ItemTagsProvider {
   public static final TagKey<Item> AC_FERNS = createTagFor("alexscaves", "ferns");
   public static final TagKey<Item> AC_FERROMAGNETIC_ITEMS = createTagFor("alexscaves", "ferromagnetic_items");
   public static final TagKey<Item> AC_RAW_MEATS = createTagFor("alexscaves", "raw_meats");
   public static final TagKey<Item> CURIOS_CHARM = createTagFor("curios", "charm");
   public static final TagKey<Item> CURIOS_HEAD = createTagFor("curios", "head");
   public static final TagKey<Item> CA_PLANTS = createTagFor("createaddition", "plants");
   public static final TagKey<Item> CA_PLANT_FOODS = createTagFor("createaddition", "plant_foods");
   public static final TagKey<Item> FA_MODIFIER_ETERNAL_INCOMPATIBLE = createTagFor("forbidden_arcanus", "modifier/eternal_incompatible");
   public static final TagKey<Item> FD_CABBAGE_ROLL_INGREDIENTS = createTagFor("farmersdelight", "cabbage_roll_ingredients");
   public static final TagKey<Item> RANDOMIUM_BLACKLIST = createTagFor("randomium", "blacklist");

   public ModdedItemTagGenerator(PackOutput output, CompletableFuture<Provider> future, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper helper) {
      super(output, future, provider, "twilightforest", helper);
   }

   protected void addTags(Provider provider) {
      this.tag(AC_FERNS).add(((Block)TFBlocks.FIDDLEHEAD.get()).asItem());
      this.tag(AC_FERROMAGNETIC_ITEMS)
         .addTag(ItemTagGenerator.STORAGE_BLOCKS_IRONWOOD)
         .addTag(ItemTagGenerator.STORAGE_BLOCKS_STEELEAF)
         .addTag(ItemTagGenerator.STORAGE_BLOCKS_KNIGHTMETAL)
         .add(new Item[]{((Block)TFBlocks.CANDELABRA.get()).asItem(), ((WroughtIronFenceBlock)TFBlocks.WROUGHT_IRON_FENCE.get()).asItem()})
         .add(
            new Item[]{
               (Item)TFItems.RAW_IRONWOOD.get(),
               (Item)TFItems.IRONWOOD_INGOT.get(),
               (Item)TFItems.STEELEAF_INGOT.get(),
               (Item)TFItems.ARMOR_SHARD.get(),
               (Item)TFItems.ARMOR_SHARD_CLUSTER.get(),
               (Item)TFItems.KNIGHTMETAL_INGOT.get(),
               (Item)TFItems.KNIGHTMETAL_RING.get(),
               (Item)TFItems.FIERY_INGOT.get(),
               (Item)TFItems.CHARM_OF_KEEPING_2.get(),
               (Item)TFItems.ORE_MAGNET.get(),
               (Item)TFItems.IRONWOOD_HELMET.get(),
               (Item)TFItems.IRONWOOD_CHESTPLATE.get(),
               (Item)TFItems.IRONWOOD_LEGGINGS.get(),
               (Item)TFItems.IRONWOOD_BOOTS.get(),
               (Item)TFItems.STEELEAF_HELMET.get(),
               (Item)TFItems.STEELEAF_CHESTPLATE.get(),
               (Item)TFItems.STEELEAF_LEGGINGS.get(),
               (Item)TFItems.STEELEAF_BOOTS.get(),
               (Item)TFItems.KNIGHTMETAL_HELMET.get(),
               (Item)TFItems.KNIGHTMETAL_CHESTPLATE.get(),
               (Item)TFItems.KNIGHTMETAL_LEGGINGS.get(),
               (Item)TFItems.KNIGHTMETAL_BOOTS.get(),
               (Item)TFItems.FIERY_HELMET.get(),
               (Item)TFItems.FIERY_CHESTPLATE.get(),
               (Item)TFItems.FIERY_LEGGINGS.get(),
               (Item)TFItems.FIERY_BOOTS.get(),
               (Item)TFItems.IRONWOOD_SWORD.get(),
               (Item)TFItems.IRONWOOD_PICKAXE.get(),
               (Item)TFItems.IRONWOOD_AXE.get(),
               (Item)TFItems.IRONWOOD_SHOVEL.get(),
               (Item)TFItems.IRONWOOD_HOE.get(),
               (Item)TFItems.STEELEAF_SWORD.get(),
               (Item)TFItems.STEELEAF_PICKAXE.get(),
               (Item)TFItems.STEELEAF_AXE.get(),
               (Item)TFItems.STEELEAF_SHOVEL.get(),
               (Item)TFItems.STEELEAF_HOE.get(),
               (Item)TFItems.KNIGHTMETAL_SWORD.get(),
               (Item)TFItems.KNIGHTMETAL_PICKAXE.get(),
               (Item)TFItems.KNIGHTMETAL_AXE.get(),
               (Item)TFItems.BLOCK_AND_CHAIN.get(),
               (Item)TFItems.KNIGHTMETAL_SHIELD.get(),
               (Item)TFItems.FIERY_SWORD.get(),
               (Item)TFItems.FIERY_PICKAXE.get(),
               (Item)TFItems.MAZEBREAKER_PICKAXE.get()
            }
         );
      this.tag(AC_RAW_MEATS).add(new Item[]{(Item)TFItems.RAW_VENISON.get(), (Item)TFItems.RAW_MEEF.get()});
      this.tag(CURIOS_CHARM)
         .add(
            new Item[]{
               (Item)TFItems.CHARM_OF_LIFE_1.get(),
               (Item)TFItems.CHARM_OF_LIFE_2.get(),
               (Item)TFItems.CHARM_OF_KEEPING_1.get(),
               (Item)TFItems.CHARM_OF_KEEPING_2.get(),
               (Item)TFItems.CHARM_OF_KEEPING_3.get()
            }
         );
      this.tag(CURIOS_HEAD)
         .add(
            new Item[]{
               (Item)TFItems.NAGA_TROPHY.get(),
               (Item)TFItems.LICH_TROPHY.get(),
               (Item)TFItems.MINOSHROOM_TROPHY.get(),
               (Item)TFItems.HYDRA_TROPHY.get(),
               (Item)TFItems.KNIGHT_PHANTOM_TROPHY.get(),
               (Item)TFItems.UR_GHAST_TROPHY.get(),
               (Item)TFItems.ALPHA_YETI_TROPHY.get(),
               (Item)TFItems.SNOW_QUEEN_TROPHY.get(),
               (Item)TFItems.QUEST_RAM_TROPHY.get(),
               ((Block)TFBlocks.CICADA.get()).asItem(),
               ((Block)TFBlocks.FIREFLY.get()).asItem(),
               ((Block)TFBlocks.MOONWORM.get()).asItem(),
               (Item)TFItems.CREEPER_SKULL_CANDLE.get(),
               (Item)TFItems.PIGLIN_SKULL_CANDLE.get(),
               (Item)TFItems.PLAYER_SKULL_CANDLE.get(),
               (Item)TFItems.SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.WITHER_SKELETON_SKULL_CANDLE.get(),
               (Item)TFItems.ZOMBIE_SKULL_CANDLE.get()
            }
         );
      this.tag(CA_PLANT_FOODS).add((Item)TFItems.TORCHBERRIES.get());
      this.tag(CA_PLANTS)
         .add(
            new Item[]{
               (Item)TFItems.LIVEROOT.get(),
               (Item)TFItems.MAGIC_BEANS.get(),
               ((Block)TFBlocks.HUGE_WATER_LILY.get()).asItem(),
               ((HugeLilyPadBlock)TFBlocks.HUGE_LILY_PAD.get()).asItem(),
               ((Block)TFBlocks.TROLLVIDR.get()).asItem(),
               ((Block)TFBlocks.UNRIPE_TROLLBER.get()).asItem(),
               ((Block)TFBlocks.TROLLBER.get()).asItem(),
               ((RotatedPillarBlock)TFBlocks.HUGE_STALK.get()).asItem(),
               ((Block)TFBlocks.THORN_ROSE.get()).asItem(),
               ((Block)TFBlocks.MAYAPPLE.get()).asItem(),
               ((Block)TFBlocks.CLOVER_PATCH.get()).asItem(),
               ((Block)TFBlocks.FIDDLEHEAD.get()).asItem(),
               ((Block)TFBlocks.MUSHGLOOM.get()).asItem(),
               ((Block)TFBlocks.TORCHBERRY_PLANT.get()).asItem(),
               ((Block)TFBlocks.ROOT_STRAND.get()).asItem(),
               ((Block)TFBlocks.FALLEN_LEAVES.get()).asItem(),
               ((Block)TFBlocks.HEDGE.get()).asItem(),
               ((Block)TFBlocks.ROOT_BLOCK.get()).asItem(),
               ((Block)TFBlocks.LIVEROOT_BLOCK.get()).asItem()
            }
         );
      this.tag(FA_MODIFIER_ETERNAL_INCOMPATIBLE).add((Item)TFItems.GLASS_SWORD.value());
      this.tag(FD_CABBAGE_ROLL_INGREDIENTS).add(new Item[]{(Item)TFItems.RAW_VENISON.get(), (Item)TFItems.RAW_MEEF.get()});
      this.tag(RANDOMIUM_BLACKLIST)
         .addTag(ItemTagGenerator.WIP)
         .add(
            new Item[]{
               (Item)TFItems.GLASS_SWORD.get(),
               ((Block)TFBlocks.TIME_LOG_CORE.get()).asItem(),
               ((Block)TFBlocks.TRANSFORMATION_LOG_CORE.get()).asItem(),
               ((Block)TFBlocks.MINING_LOG_CORE.get()).asItem(),
               ((Block)TFBlocks.SORTING_LOG_CORE.get()).asItem(),
               ((Block)TFBlocks.ANTIBUILDER.get()).asItem(),
               ((Block)TFBlocks.STRONGHOLD_SHIELD.get()).asItem(),
               ((Block)TFBlocks.LOCKED_VANISHING_BLOCK.get()).asItem(),
               ((Block)TFBlocks.BROWN_THORNS.get()).asItem(),
               ((Block)TFBlocks.GREEN_THORNS.get()).asItem(),
               ((Block)TFBlocks.BURNT_THORNS.get()).asItem(),
               ((Block)TFBlocks.PINK_FORCE_FIELD.get()).asItem(),
               ((Block)TFBlocks.ORANGE_FORCE_FIELD.get()).asItem(),
               ((Block)TFBlocks.GREEN_FORCE_FIELD.get()).asItem(),
               ((Block)TFBlocks.BLUE_FORCE_FIELD.get()).asItem(),
               ((Block)TFBlocks.VIOLET_FORCE_FIELD.get()).asItem(),
               ((Block)TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.NAGA_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.LICH_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.MINOSHROOM_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.HYDRA_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.UR_GHAST_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get()).asItem(),
               ((Block)TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get()).asItem()
            }
         );
   }

   private static TagKey<Item> createTagFor(String modid, String tagName) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modid, tagName));
   }
}
