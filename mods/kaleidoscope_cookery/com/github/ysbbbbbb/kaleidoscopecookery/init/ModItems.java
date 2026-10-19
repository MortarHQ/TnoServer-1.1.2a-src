package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.github.ysbbbbbb.kaleidoscopecookery.item.BambooTubeRiceBlockItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.BowlFoodOnlyItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.ChiliItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.EmptyCupItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.FlourItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.FoodWithEffectsItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.FruitBasketItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenKnifeItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.KitchenShovelItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.LiftBlockItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.OilItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.OilPotItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RawDoughItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RecipeItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.RiceItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.ScarecrowItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.SickleItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.SteamerItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.StockpotLidItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.StrawHatItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.TeapotItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.TransmutationLunchBagItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.WithTooltipsBlockItem;
import com.github.ysbbbbbb.kaleidoscopecookery.item.WithTooltipsItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class ModItems {
   public static final Items ITEMS = DeferredRegister.createItems("kaleidoscope_cookery");
   public static DeferredItem<Item> STOVE = ITEMS.register("stove", () -> new BlockItem((Block)ModBlocks.STOVE.get(), new Properties()));
   public static DeferredItem<Item> POT = ITEMS.register("pot", () -> new BlockItem((Block)ModBlocks.POT.get(), new Properties()));
   public static DeferredItem<Item> STOCKPOT = ITEMS.register("stockpot", () -> new BlockItem((Block)ModBlocks.STOCKPOT.get(), new Properties()));
   public static DeferredItem<Item> STOCKPOT_LID = ITEMS.register("stockpot_lid", StockpotLidItem::new);
   public static DeferredItem<Item> CHOPPING_BOARD = ITEMS.register(
      "chopping_board", () -> new BlockItem((Block)ModBlocks.CHOPPING_BOARD.get(), new Properties())
   );
   public static DeferredItem<Item> KITCHENWARE_RACKS = ITEMS.register(
      "kitchenware_racks", () -> new WithTooltipsBlockItem((Block)ModBlocks.KITCHENWARE_RACKS.get(), "kitchenware_racks")
   );
   public static DeferredItem<Item> SHAWARMA_SPIT = ITEMS.register("shawarma_spit", () -> new BlockItem((Block)ModBlocks.SHAWARMA_SPIT.get(), new Properties()));
   public static DeferredItem<Item> MILLSTONE = ITEMS.register("millstone", () -> new BlockItem((Block)ModBlocks.MILLSTONE.get(), new Properties()));
   public static DeferredItem<Item> STEAMER = ITEMS.register("steamer", SteamerItem::new);
   public static DeferredItem<Item> TEAPOT = ITEMS.register("teapot", TeapotItem::new);
   public static DeferredItem<Item> EMPTY_CUP = ITEMS.register("empty_cup", EmptyCupItem::new);
   public static DeferredItem<Item> TRASH_CAN = ITEMS.register("trash_can", () -> new BlockItem((Block)ModBlocks.TRASH_CAN.get(), new Properties()));
   public static DeferredItem<Item> OIL = ITEMS.register("oil", OilItem::new);
   public static DeferredItem<Item> OIL_POT = ITEMS.register("oil_pot", OilPotItem::new);
   public static DeferredItem<Item> OIL_BLOCK = ITEMS.register("oil_block", () -> new BlockItem((Block)ModBlocks.OIL_BLOCK.get(), new Properties()));
   public static DeferredItem<Item> ENAMEL_BASIN = ITEMS.register("enamel_basin", () -> new BlockItem((Block)ModBlocks.ENAMEL_BASIN.get(), new Properties()));
   public static DeferredItem<Item> CHILI_RISTRA = ITEMS.register("chili_ristra", () -> new BlockItem((Block)ModBlocks.CHILI_RISTRA.get(), new Properties()));
   public static DeferredItem<Item> STRUNG_MUSHROOMS = ITEMS.register(
      "strung_mushrooms", () -> new BlockItem((Block)ModBlocks.STRUNG_MUSHROOMS.get(), new Properties())
   );
   public static DeferredItem<Item> STRAW_BLOCK = ITEMS.register("straw_block", () -> new BlockItem((Block)ModBlocks.STRAW_BLOCK.get(), new Properties()));
   public static DeferredItem<Item> FRUIT_BASKET = ITEMS.register("fruit_basket", FruitBasketItem::new);
   public static DeferredItem<Item> SCARECROW = ITEMS.register("scarecrow", ScarecrowItem::new);
   public static DeferredItem<Item> RECIPE_ITEM = ITEMS.register("recipe_item", RecipeItem::new);
   public static DeferredItem<Item> TRANSMUTATION_LUNCH_BAG = ITEMS.register("transmutation_lunch_bag", TransmutationLunchBagItem::new);
   public static DeferredItem<Item> IRON_KITCHEN_KNIFE = ITEMS.register("iron_kitchen_knife", () -> new KitchenKnifeItem(Tiers.IRON));
   public static DeferredItem<Item> GOLD_KITCHEN_KNIFE = ITEMS.register("gold_kitchen_knife", () -> new KitchenKnifeItem(Tiers.GOLD));
   public static DeferredItem<Item> DIAMOND_KITCHEN_KNIFE = ITEMS.register("diamond_kitchen_knife", () -> new KitchenKnifeItem(Tiers.DIAMOND));
   public static DeferredItem<Item> NETHERITE_KITCHEN_KNIFE = ITEMS.register(
      "netherite_kitchen_knife", () -> new KitchenKnifeItem(Tiers.NETHERITE, new Properties().fireResistant())
   );
   public static DeferredItem<Item> KITCHEN_SHOVEL = ITEMS.register("kitchen_shovel", KitchenShovelItem::new);
   public static DeferredItem<Item> SICKLE = ITEMS.register("sickle", SickleItem::new);
   public static DeferredItem<Item> STRAW_HAT = ITEMS.register("straw_hat", () -> new StrawHatItem(false));
   public static DeferredItem<Item> STRAW_HAT_FLOWER = ITEMS.register("straw_hat_flower", () -> new StrawHatItem(true));
   public static final DeferredItem<Item> FARMER_CHEST_PLATE = ITEMS.register(
      "farmer_chest_plate", () -> new ArmorItem(ModArmorMaterials.FARMER, Type.CHESTPLATE, new Properties().stacksTo(1))
   );
   public static final DeferredItem<Item> FARMER_LEGGINGS = ITEMS.register(
      "farmer_leggings", () -> new ArmorItem(ModArmorMaterials.FARMER, Type.LEGGINGS, new Properties().stacksTo(1))
   );
   public static final DeferredItem<Item> FARMER_BOOTS = ITEMS.register(
      "farmer_boots", () -> new ArmorItem(ModArmorMaterials.FARMER, Type.BOOTS, new Properties().stacksTo(1))
   );
   public static DeferredItem<Item> TOMATO_SEED = ITEMS.register(
      "tomato_seed", () -> new ItemNameBlockItem((Block)ModBlocks.TOMATO_CROP.get(), new Properties())
   );
   public static DeferredItem<Item> CHILI_SEED = ITEMS.register("chili_seed", () -> new ItemNameBlockItem((Block)ModBlocks.CHILI_CROP.get(), new Properties()));
   public static DeferredItem<Item> LETTUCE_SEED = ITEMS.register(
      "lettuce_seed", () -> new ItemNameBlockItem((Block)ModBlocks.LETTUCE_CROP.get(), new Properties())
   );
   public static DeferredItem<Item> RICE_SEED = ITEMS.register("rice", RiceItem::new);
   public static DeferredItem<Item> WILD_RICE_SEED = ITEMS.register(
      "wild_rice", () -> new ItemNameBlockItem((Block)ModBlocks.RICE_CROP.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_OAK = ITEMS.register(
      "cook_stool_oak", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_OAK.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_SPRUCE = ITEMS.register(
      "cook_stool_spruce", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_SPRUCE.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_ACACIA = ITEMS.register(
      "cook_stool_acacia", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_ACACIA.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_BAMBOO = ITEMS.register(
      "cook_stool_bamboo", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_BAMBOO.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_BIRCH = ITEMS.register(
      "cook_stool_birch", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_BIRCH.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_CHERRY = ITEMS.register(
      "cook_stool_cherry", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_CHERRY.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_CRIMSON = ITEMS.register(
      "cook_stool_crimson", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_CRIMSON.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_DARK_OAK = ITEMS.register(
      "cook_stool_dark_oak", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_DARK_OAK.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_JUNGLE = ITEMS.register(
      "cook_stool_jungle", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_JUNGLE.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_MANGROVE = ITEMS.register(
      "cook_stool_mangrove", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_MANGROVE.get(), new Properties())
   );
   public static DeferredItem<Item> COOK_STOOL_WARPED = ITEMS.register(
      "cook_stool_warped", () -> new BlockItem((Block)ModBlocks.COOK_STOOL_WARPED.get(), new Properties())
   );
   public static DeferredItem<Item> CHAIR_OAK = ITEMS.register("chair_oak", () -> new BlockItem((Block)ModBlocks.CHAIR_OAK.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_SPRUCE = ITEMS.register("chair_spruce", () -> new BlockItem((Block)ModBlocks.CHAIR_SPRUCE.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_ACACIA = ITEMS.register("chair_acacia", () -> new BlockItem((Block)ModBlocks.CHAIR_ACACIA.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_BAMBOO = ITEMS.register("chair_bamboo", () -> new BlockItem((Block)ModBlocks.CHAIR_BAMBOO.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_BIRCH = ITEMS.register("chair_birch", () -> new BlockItem((Block)ModBlocks.CHAIR_BIRCH.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_CHERRY = ITEMS.register("chair_cherry", () -> new BlockItem((Block)ModBlocks.CHAIR_CHERRY.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_CRIMSON = ITEMS.register("chair_crimson", () -> new BlockItem((Block)ModBlocks.CHAIR_CRIMSON.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_DARK_OAK = ITEMS.register(
      "chair_dark_oak", () -> new BlockItem((Block)ModBlocks.CHAIR_DARK_OAK.get(), new Properties())
   );
   public static DeferredItem<Item> CHAIR_JUNGLE = ITEMS.register("chair_jungle", () -> new BlockItem((Block)ModBlocks.CHAIR_JUNGLE.get(), new Properties()));
   public static DeferredItem<Item> CHAIR_MANGROVE = ITEMS.register(
      "chair_mangrove", () -> new BlockItem((Block)ModBlocks.CHAIR_MANGROVE.get(), new Properties())
   );
   public static DeferredItem<Item> CHAIR_WARPED = ITEMS.register("chair_warped", () -> new BlockItem((Block)ModBlocks.CHAIR_WARPED.get(), new Properties()));
   public static DeferredItem<Item> TABLE_OAK = ITEMS.register("table_oak", () -> new BlockItem((Block)ModBlocks.TABLE_OAK.get(), new Properties()));
   public static DeferredItem<Item> TABLE_SPRUCE = ITEMS.register("table_spruce", () -> new BlockItem((Block)ModBlocks.TABLE_SPRUCE.get(), new Properties()));
   public static DeferredItem<Item> TABLE_ACACIA = ITEMS.register("table_acacia", () -> new BlockItem((Block)ModBlocks.TABLE_ACACIA.get(), new Properties()));
   public static DeferredItem<Item> TABLE_BAMBOO = ITEMS.register("table_bamboo", () -> new BlockItem((Block)ModBlocks.TABLE_BAMBOO.get(), new Properties()));
   public static DeferredItem<Item> TABLE_BIRCH = ITEMS.register("table_birch", () -> new BlockItem((Block)ModBlocks.TABLE_BIRCH.get(), new Properties()));
   public static DeferredItem<Item> TABLE_CHERRY = ITEMS.register("table_cherry", () -> new BlockItem((Block)ModBlocks.TABLE_CHERRY.get(), new Properties()));
   public static DeferredItem<Item> TABLE_CRIMSON = ITEMS.register("table_crimson", () -> new BlockItem((Block)ModBlocks.TABLE_CRIMSON.get(), new Properties()));
   public static DeferredItem<Item> TABLE_DARK_OAK = ITEMS.register(
      "table_dark_oak", () -> new BlockItem((Block)ModBlocks.TABLE_DARK_OAK.get(), new Properties())
   );
   public static DeferredItem<Item> TABLE_JUNGLE = ITEMS.register("table_jungle", () -> new BlockItem((Block)ModBlocks.TABLE_JUNGLE.get(), new Properties()));
   public static DeferredItem<Item> TABLE_MANGROVE = ITEMS.register(
      "table_mangrove", () -> new BlockItem((Block)ModBlocks.TABLE_MANGROVE.get(), new Properties())
   );
   public static DeferredItem<Item> TABLE_WARPED = ITEMS.register("table_warped", () -> new BlockItem((Block)ModBlocks.TABLE_WARPED.get(), new Properties()));
   public static DeferredItem<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Properties().food(ModFoods.TOMATO)));
   public static DeferredItem<Item> RED_CHILI = ITEMS.register("red_chili", () -> new ChiliItem(2));
   public static DeferredItem<Item> GREEN_CHILI = ITEMS.register("green_chili", () -> new ChiliItem(1));
   public static DeferredItem<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Properties().food(ModFoods.LETTUCE)));
   public static DeferredItem<Item> RICE_PANICLE = ITEMS.register("rice_panicle", () -> new Item(new Properties()));
   public static DeferredItem<Item> CATERPILLAR = ITEMS.register(
      "caterpillar", () -> new WithTooltipsItem(new Properties().food(ModFoods.CATERPILLAR), "caterpillar")
   );
   public static DeferredItem<Item> FLOUR = ITEMS.register("flour", FlourItem::new);
   public static DeferredItem<Item> RAW_DOUGH = ITEMS.register("raw_dough", RawDoughItem::new);
   public static DeferredItem<Item> RAW_NOODLES = ITEMS.register("raw_noodles", () -> new Item(new Properties()));
   public static DeferredItem<Item> STUFFED_DOUGH_FOOD = ITEMS.register("stuffed_dough_food", () -> new Item(new Properties()));
   public static DeferredItem<Item> RAW_ZONGZI = ITEMS.register("raw_zongzi", () -> new Item(new Properties()));
   public static DeferredItem<Item> RAW_BAMBOO_TUBE_RICE = ITEMS.register("raw_bamboo_tube_rice", () -> new Item(new Properties()));
   public static DeferredItem<Item> FRIED_EGG = ITEMS.register("fried_egg", () -> new Item(new Properties().food(ModFoods.FRIED_EGG)));
   public static DeferredItem<Item> DONKEY_BURGER = ITEMS.register("donkey_burger", () -> new FoodWithEffectsItem(ModFoods.DONKEY_BURGER));
   public static DeferredItem<Item> BAOZI = ITEMS.register("baozi", () -> new FoodWithEffectsItem(ModFoods.BAOZI));
   public static DeferredItem<Item> SHENGJIAN_MANTOU = ITEMS.register("shengjian_mantou", () -> new FoodWithEffectsItem(ModFoods.SHENGJIAN_MANTOU_ITEM));
   public static DeferredItem<Item> DUMPLING = ITEMS.register("dumpling", () -> new Item(new Properties().food(ModFoods.DUMPLING)));
   public static DeferredItem<Item> SAMSA = ITEMS.register("samsa", () -> new FoodWithEffectsItem(ModFoods.SAMSA));
   public static DeferredItem<Item> MANTOU = ITEMS.register("mantou", () -> new FoodWithEffectsItem(ModFoods.MANTOU));
   public static DeferredItem<Item> MEAT_PIE = ITEMS.register("meat_pie", () -> new FoodWithEffectsItem(ModFoods.MEAT_PIE));
   public static DeferredItem<Item> QINGTUAN = ITEMS.register("qingtuan", () -> new FoodWithEffectsItem(ModFoods.QINGTUAN));
   public static DeferredItem<Item> STICKY_CANDY = ITEMS.register("sticky_candy", () -> new FoodWithEffectsItem(ModFoods.STICKY_CANDY));
   public static DeferredItem<Item> STICKY_RICE_CAKE = ITEMS.register("sticky_rice_cake", () -> new FoodWithEffectsItem(ModFoods.STICKY_RICE_CAKE));
   public static DeferredItem<Item> ZONGZI = ITEMS.register("zongzi", () -> new FoodWithEffectsItem(ModFoods.ZONGZI));
   public static DeferredItem<Item> BAMBOO_TUBE_RICE = ITEMS.register(
      "bamboo_tube_rice", () -> new BambooTubeRiceBlockItem((Block)ModBlocks.BAMBOO_TUBE_RICE.get(), ModFoods.BAMBOO_TUBE_RICE)
   );
   public static DeferredItem<Item> COOKED_RICE = ITEMS.register(
      "cooked_rice", () -> new BowlFoodOnlyItem(ModFoods.COOKED_RICE, net.minecraft.world.item.Items.BOWL)
   );
   public static DeferredItem<Item> EGG_FRIED_RICE = ITEMS.register("egg_fried_rice", () -> new BowlFoodOnlyItem(ModFoods.EGG_FRIED_RICE));
   public static DeferredItem<Item> SCRAMBLE_EGG_WITH_TOMATOES = ITEMS.register(
      "scramble_egg_with_tomatoes", () -> new BowlFoodOnlyItem(ModFoods.SCRAMBLE_EGG_WITH_TOMATOES)
   );
   public static DeferredItem<Item> SCRAMBLE_EGG_WITH_TOMATOES_RICE_BOWL = ITEMS.register(
      "scramble_egg_with_tomatoes_rice_bowl", () -> new BowlFoodOnlyItem(ModFoods.SCRAMBLE_EGG_WITH_TOMATOES_RICE_BOWL)
   );
   public static DeferredItem<Item> BRAISED_BEEF = ITEMS.register("braised_beef", () -> new BowlFoodOnlyItem(ModFoods.BRAISED_BEEF));
   public static DeferredItem<Item> BRAISED_BEEF_RICE_BOWL = ITEMS.register(
      "braised_beef_rice_bowl", () -> new BowlFoodOnlyItem(ModFoods.BRAISED_BEEF_RICE_BOWL)
   );
   public static DeferredItem<Item> STIR_FRIED_PORK_WITH_PEPPERS = ITEMS.register(
      "stir_fried_pork_with_peppers", () -> new BowlFoodOnlyItem(ModFoods.STIR_FRIED_PORK_WITH_PEPPERS)
   );
   public static DeferredItem<Item> STIR_FRIED_PORK_WITH_PEPPERS_RICE_BOWL = ITEMS.register(
      "stir_fried_pork_with_peppers_rice_bowl", () -> new BowlFoodOnlyItem(ModFoods.STIR_FRIED_PORK_WITH_PEPPERS_RICE_BOWL)
   );
   public static DeferredItem<Item> SWEET_AND_SOUR_PORK = ITEMS.register("sweet_and_sour_pork", () -> new BowlFoodOnlyItem(ModFoods.SWEET_AND_SOUR_PORK));
   public static DeferredItem<Item> SWEET_AND_SOUR_PORK_RICE_BOWL = ITEMS.register(
      "sweet_and_sour_pork_rice_bowl", () -> new BowlFoodOnlyItem(ModFoods.SWEET_AND_SOUR_PORK_RICE_BOWL)
   );
   public static DeferredItem<Item> FISH_FLAVORED_SHREDDED_PORK = ITEMS.register(
      "fish_flavored_shredded_pork", () -> new BowlFoodOnlyItem(ModFoods.FISH_FLAVORED_SHREDDED_PORK)
   );
   public static DeferredItem<Item> FISH_FLAVORED_SHREDDED_PORK_RICE_BOWL = ITEMS.register(
      "fish_flavored_shredded_pork_rice_bowl", () -> new BowlFoodOnlyItem(ModFoods.FISH_FLAVORED_SHREDDED_PORK_RICE_BOWL)
   );
   public static DeferredItem<Item> PORK_BONE_SOUP = ITEMS.register("pork_bone_soup", () -> new BowlFoodOnlyItem(ModFoods.PORK_BONE_SOUP));
   public static DeferredItem<Item> SEAFOOD_MISO_SOUP = ITEMS.register("seafood_miso_soup", () -> new BowlFoodOnlyItem(ModFoods.SEAFOOD_MISO_SOUP));
   public static DeferredItem<Item> FEARSOME_THICK_SOUP = ITEMS.register("fearsome_thick_soup", () -> new BowlFoodOnlyItem(ModFoods.FEARSOME_THICK_SOUP));
   public static DeferredItem<Item> LAMB_AND_RADISH_SOUP = ITEMS.register("lamb_and_radish_soup", () -> new BowlFoodOnlyItem(ModFoods.LAMB_AND_RADISH_SOUP));
   public static DeferredItem<Item> BRAISED_BEEF_WITH_POTATOES = ITEMS.register(
      "braised_beef_with_potatoes", () -> new BowlFoodOnlyItem(ModFoods.BRAISED_BEEF_WITH_POTATOES)
   );
   public static DeferredItem<Item> WILD_MUSHROOM_RABBIT_SOUP = ITEMS.register(
      "wild_mushroom_rabbit_soup", () -> new BowlFoodOnlyItem(ModFoods.WILD_MUSHROOM_RABBIT_SOUP)
   );
   public static DeferredItem<Item> PUFFERFISH_SOUP = ITEMS.register("pufferfish_soup", () -> new BowlFoodOnlyItem(ModFoods.PUFFERFISH_SOUP));
   public static DeferredItem<Item> BORSCHT = ITEMS.register("borscht", () -> new BowlFoodOnlyItem(ModFoods.BORSCHT));
   public static DeferredItem<Item> BEEF_MEATBALL_SOUP = ITEMS.register("beef_meatball_soup", () -> new BowlFoodOnlyItem(ModFoods.BEEF_MEATBALL_SOUP));
   public static DeferredItem<Item> CHICKEN_AND_MUSHROOM_STEW = ITEMS.register(
      "chicken_and_mushroom_stew", () -> new BowlFoodOnlyItem(ModFoods.CHICKEN_AND_MUSHROOM_STEW)
   );
   public static DeferredItem<Item> LABA_CONGEE = ITEMS.register("laba_congee", () -> new BowlFoodOnlyItem(ModFoods.LABA_CONGEE));
   public static DeferredItem<Item> BEEF_NOODLE = ITEMS.register("beef_noodle", () -> new BowlFoodOnlyItem(ModFoods.BEEF_NOODLE));
   public static DeferredItem<Item> HUI_NOODLE = ITEMS.register("hui_noodle", () -> new BowlFoodOnlyItem(ModFoods.HUI_NOODLE));
   public static DeferredItem<Item> UDON_NOODLE = ITEMS.register("udon_noodle", () -> new BowlFoodOnlyItem(ModFoods.UDON_NOODLE));
   public static DeferredItem<Item> HOT_DRY_NOODLES = ITEMS.register("hot_dry_noodles", () -> new BowlFoodOnlyItem(ModFoods.HOT_DRY_NOODLES));
   public static DeferredItem<Item> SASHIMI = ITEMS.register("sashimi", () -> new Item(new Properties().food(ModFoods.SASHIMI)));
   public static DeferredItem<Item> RAW_LAMB_CHOPS = ITEMS.register("raw_lamb_chops", () -> new Item(new Properties().food(ModFoods.RAW_LAMB_CHOPS)));
   public static DeferredItem<Item> RAW_COW_OFFAL = ITEMS.register("raw_cow_offal", () -> new Item(new Properties().food(ModFoods.RAW_COW_OFFAL)));
   public static DeferredItem<Item> RAW_PORK_BELLY = ITEMS.register("raw_pork_belly", () -> new Item(new Properties().food(ModFoods.RAW_PORK_BELLY)));
   public static DeferredItem<Item> RAW_CUT_SMALL_MEATS = ITEMS.register(
      "raw_cut_small_meats", () -> new Item(new Properties().food(ModFoods.RAW_CUT_SMALL_MEATS))
   );
   public static DeferredItem<Item> RAW_MEATBALL = ITEMS.register("raw_meatball", () -> new Item(new Properties().food(ModFoods.RAW_MEATBALL)));
   public static DeferredItem<Item> COOKED_LAMB_CHOPS = ITEMS.register("cooked_lamb_chops", () -> new Item(new Properties().food(ModFoods.COOKED_LAMB_CHOPS)));
   public static DeferredItem<Item> COOKED_COW_OFFAL = ITEMS.register("cooked_cow_offal", () -> new Item(new Properties().food(ModFoods.COOKED_COW_OFFAL)));
   public static DeferredItem<Item> COOKED_PORK_BELLY = ITEMS.register("cooked_pork_belly", () -> new Item(new Properties().food(ModFoods.COOKED_PORK_BELLY)));
   public static DeferredItem<Item> COOKED_CUT_SMALL_MEATS = ITEMS.register(
      "cooked_cut_small_meats", () -> new Item(new Properties().food(ModFoods.COOKED_CUT_SMALL_MEATS))
   );
   public static DeferredItem<Item> COOKED_MEATBALL = ITEMS.register("cooked_meatball", () -> new Item(new Properties().food(ModFoods.COOKED_MEATBALL)));
   public static DeferredItem<Item> COLD_CUT_HAM_SLICES = ITEMS.register(
      "cold_cut_ham_slices", () -> new LiftBlockItem((Block)ModBlocks.COLD_CUT_HAM_SLICES.get(), "cold_cut_ham_slices")
   );
}
