package com.tensura_tno.registry;

import com.tensura_tno.item.EnchantmentStoneItem;
import com.tensura_tno.item.ForbiddenSummonTomeItem;
import com.tensura_tno.item.RequiredQuestResetScrollItem;
import com.tensura_tno.item.RimuruPlushItem;
import com.tensura_tno.item.TnoBowlFoodItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TensuraTNOItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, "tensura_tno");
   public static final DeferredHolder<Item, EnchantmentStoneItem> ENCHANTMENT_STONE = ITEMS.register(
      "enchantment_stone", () -> new EnchantmentStoneItem(new Properties().rarity(Rarity.EPIC).stacksTo(16))
   );
   public static final DeferredHolder<Item, ForbiddenSummonTomeItem> FORBIDDEN_SUMMON_TOME = ITEMS.register(
      "forbidden_summon_tome", ForbiddenSummonTomeItem::new
   );
   public static final DeferredHolder<Item, RequiredQuestResetScrollItem> REQUIRED_QUEST_RESET_SCROLL = ITEMS.register(
      "required_quest_reset_scroll", RequiredQuestResetScrollItem::new
   );
   public static final DeferredHolder<Item, TnoBowlFoodItem> STORM_DAEMON_HIPOKUTE_SOUP = ITEMS.register(
      "storm_daemon_hipokute_soup", () -> new TnoBowlFoodItem(TensuraTNOFoods.STORM_DAEMON_HIPOKUTE_SOUP)
   );
   public static final DeferredHolder<Item, TnoBowlFoodItem> MONSTER_MEAT_PLATTER = ITEMS.register(
      "monster_meat_platter", () -> new TnoBowlFoodItem(TensuraTNOFoods.MONSTER_MEAT_PLATTER)
   );
   public static final DeferredHolder<Item, Item> SLIME_SAUCE_BUN = ITEMS.register(
      "slime_sauce_bun", () -> new Item(new Properties().food(TensuraTNOFoods.SLIME_SAUCE_BUN))
   );
   public static final DeferredHolder<Item, Item> BLADE_TIGER_MINCED_MEAT = ITEMS.register(
      "blade_tiger_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.BLADE_TIGER_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> COOKED_BLADE_TIGER_MINCED_MEAT = ITEMS.register(
      "cooked_blade_tiger_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.COOKED_BLADE_TIGER_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> CHARYBDIS_MINCED_MEAT = ITEMS.register(
      "charybdis_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.CHARYBDIS_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> COOKED_CHARYBDIS_MINCED_MEAT = ITEMS.register(
      "cooked_charybdis_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.COOKED_CHARYBDIS_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> ARMORSAURUS_MINCED_MEAT = ITEMS.register(
      "armorsaurus_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.ARMORSAURUS_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> COOKED_ARMORSAURUS_MINCED_MEAT = ITEMS.register(
      "cooked_armorsaurus_minced_meat", () -> new Item(new Properties().food(TensuraTNOFoods.COOKED_ARMORSAURUS_MINCED_MEAT))
   );
   public static final DeferredHolder<Item, Item> KNIGHT_SPIDER_LEG_MEAT = ITEMS.register(
      "knight_spider_leg_meat", () -> new Item(new Properties().food(TensuraTNOFoods.KNIGHT_SPIDER_LEG_MEAT))
   );
   public static final DeferredHolder<Item, Item> COOKED_KNIGHT_SPIDER_LEG_MEAT = ITEMS.register(
      "cooked_knight_spider_leg_meat", () -> new Item(new Properties().food(TensuraTNOFoods.COOKED_KNIGHT_SPIDER_LEG_MEAT))
   );
   public static final DeferredHolder<Item, Item> CURED_MEAT = ITEMS.register("cured_meat", () -> new Item(new Properties().food(TensuraTNOFoods.CURED_MEAT)));
   public static final DeferredHolder<Item, Item> SLICED_CURED_MEAT = ITEMS.register(
      "sliced_cured_meat", () -> new Item(new Properties().food(TensuraTNOFoods.SLICED_CURED_MEAT))
   );
   public static final DeferredHolder<Item, BlockItem> TNO_STOVE = ITEMS.register(
      "tno_stove", () -> new BlockItem((Block)TensuraTNOBlocks.TNO_STOVE.get(), new Properties())
   );
   public static final DeferredHolder<Item, RimuruPlushItem> RIMURU_PLUSH = ITEMS.register(
      "rimuru_plush", () -> new RimuruPlushItem((Block)TensuraTNOBlocks.RIMURU_PLUSH.get(), new Properties())
   );
   public static final DeferredHolder<Item, RimuruPlushItem> LUMINOUS_PLUSH = ITEMS.register(
      "luminous_plush", () -> new RimuruPlushItem((Block)TensuraTNOBlocks.LUMINOUS_PLUSH.get(), new Properties())
   );

   private TensuraTNOItems() {
   }

   public static void register(IEventBus modBus) {
      ITEMS.register(modBus);
   }
}
