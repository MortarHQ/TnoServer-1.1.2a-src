package com.mortal_cultivation.registry.item;

import com.mortal_cultivation.item.AncientBookItem;
import com.mortal_cultivation.item.AncientScholarTableItem;
import com.mortal_cultivation.item.AncientSteleItem;
import com.mortal_cultivation.item.BreakAndRebuildManualItem;
import com.mortal_cultivation.item.CinnabarBloodInkItem;
import com.mortal_cultivation.item.CrystalOfCombustionItem;
import com.mortal_cultivation.item.DemonSpiritPillItem;
import com.mortal_cultivation.item.EssenceCharcoalItem;
import com.mortal_cultivation.item.FastingTalismanItem;
import com.mortal_cultivation.item.GiantSpiritPillItem;
import com.mortal_cultivation.item.HeavenCleansingElixirItem;
import com.mortal_cultivation.item.LightningWoodLingzhiItem;
import com.mortal_cultivation.item.MonsterGatheringTalismanItem;
import com.mortal_cultivation.item.MyriadSwordsReturnManualItem;
import com.mortal_cultivation.item.NineYinLotusLeafItem;
import com.mortal_cultivation.item.NourishingQiTalismanItem;
import com.mortal_cultivation.item.PuppetTalismanItem;
import com.mortal_cultivation.item.RuneTableItem;
import com.mortal_cultivation.item.RuyiJinguBangItem;
import com.mortal_cultivation.item.SizeTransformationManualItem;
import com.mortal_cultivation.item.SpiritCleansingPillItem;
import com.mortal_cultivation.item.SpiritCompassItem;
import com.mortal_cultivation.item.SpiritFruitItem;
import com.mortal_cultivation.item.SpiritGlassesItem;
import com.mortal_cultivation.item.StellarCorePillItem;
import com.mortal_cultivation.item.StrengthPillItem;
import com.mortal_cultivation.item.SwordFlightManualItem;
import com.mortal_cultivation.item.TalismanManualItem;
import com.mortal_cultivation.item.TenThousandSoulBannerItem;
import com.mortal_cultivation.item.TheSupremeAlchemyCanonItem;
import com.mortal_cultivation.item.ThunderTalismanItem;
import com.mortal_cultivation.item.ZhaoYingZhuItem;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.CultivationEntities;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class CultivationItems {
   public static final Items ITEMS = DeferredRegister.createItems("mortal_cultivation");
   public static final DeferredHolder<Item, SpiritFruitItem> SPIRIT_FRUIT = ITEMS.register(
      "spirit_fruit", () -> new SpiritFruitItem(new Properties().food(new Builder().nutrition(5).saturationModifier(0.3F).alwaysEdible().build()))
   );
   public static final DeferredHolder<Item, BlockItem> SPIRIT_LOG = ITEMS.register(
      "spirit_log", () -> new BlockItem((Block)BlockRegistry.SPIRIT_LOG.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> SPIRIT_LEAVES = ITEMS.register(
      "spirit_leaves", () -> new BlockItem((Block)BlockRegistry.SPIRIT_LEAVES.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> SPIRIT_FRUIT_LEAVES = ITEMS.register(
      "spirit_fruit_leaves", () -> new BlockItem((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> SPIRIT_SAPLING = ITEMS.register(
      "spirit_sapling", () -> new BlockItem((Block)BlockRegistry.SPIRIT_SAPLING.get(), new Properties())
   );
   public static final DeferredHolder<Item, DoubleHighBlockItem> SPIRIT_RECOVERY_HERB = ITEMS.register(
      "spirit_recovery_herb", () -> new DoubleHighBlockItem((Block)BlockRegistry.SPIRIT_RECOVERY_HERB.get(), new Properties())
   );
   public static final DeferredHolder<Item, Item> MILLENNIUM_GINSENG = ITEMS.register("millennium_ginseng", () -> new Item(new Properties()));
   public static final DeferredHolder<Item, DeferredSpawnEggItem> SPIRIT_MONKEY_SPAWN_EGG = ITEMS.register(
      "spirit_monkey_spawn_egg", () -> new DeferredSpawnEggItem(CultivationEntities.SPIRIT_MONKEY, 7491644, 14072199, new Properties())
   );
   public static final DeferredHolder<Item, DeferredSpawnEggItem> AZURE_MOSS_QILIN_SPAWN_EGG = ITEMS.register(
      "azure_moss_qilin_spawn_egg", () -> new DeferredSpawnEggItem(CultivationEntities.AZURE_MOSS_QILIN, 3562826, 11979137, new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> IMMORTAL_HERB = ITEMS.register(
      "immortal_herb", () -> new BlockItem((Block)BlockRegistry.IMMORTAL_HERB.get(), new Properties())
   );
   public static final DeferredHolder<Item, LightningWoodLingzhiItem> LIGHTNING_WOOD_LINGZHI = ITEMS.register(
      "lightning_wood_lingzhi", () -> new LightningWoodLingzhiItem((Block)BlockRegistry.LIGHTNING_WOOD_LINGZHI.get(), new Properties())
   );
   public static final DeferredHolder<Item, Item> NINE_YIN_LOTUS = ITEMS.register("nine_yin_lotus", () -> new Item(new Properties()));
   public static final DeferredHolder<Item, NineYinLotusLeafItem> NINE_YIN_LOTUS_LEAF = ITEMS.register(
      "nine_yin_lotus_leaf", () -> new NineYinLotusLeafItem((Block)BlockRegistry.NINE_YIN_LOTUS.get(), new Properties())
   );
   public static final DeferredHolder<Item, Item> TALISMAN_PAPER = ITEMS.register("talisman_paper", () -> new Item(new Properties().stacksTo(64)));
   public static final DeferredHolder<Item, Item> TAB_ICON = ITEMS.register("tab_icon", () -> new Item(new Properties()));
   public static final DeferredHolder<Item, Item> BRUSH = ITEMS.register(
      "brush", () -> new SwordItem(Tiers.WOOD, new Properties().durability(180).attributes(SwordItem.createAttributes(Tiers.WOOD, 2, -2.4F)))
   );
   public static final DeferredHolder<Item, Item> CINNABAR_BLOOD_INK = ITEMS.register(
      "localized_fantasy_term", () -> new CinnabarBloodInkItem(new Properties().stacksTo(1))
   );
   public static final DeferredHolder<Item, Item> BRUSH_AND_INK = ITEMS.register("brush_and_ink", () -> new Item(new Properties().durability(20)));
   public static final DeferredHolder<Item, Item> THUNDER_TALISMAN = ITEMS.register(
      "thunder_talisman", () -> new ThunderTalismanItem(new Properties().stacksTo(8))
   );
   public static final DeferredHolder<Item, Item> NOURISHING_QI_TALISMAN = ITEMS.register(
      "nourishing_qi_talisman", () -> new NourishingQiTalismanItem(new Properties().stacksTo(16))
   );
   public static final DeferredHolder<Item, Item> PUPPET_TALISMAN = ITEMS.register(
      "puppet_talisman", () -> new PuppetTalismanItem(new Properties().stacksTo(8))
   );
   public static final DeferredHolder<Item, Item> MONSTER_GATHERING_TALISMAN = ITEMS.register(
      "monster_gathering_talisman", () -> new MonsterGatheringTalismanItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, Item> FASTING_TALISMAN = ITEMS.register(
      "fasting_talisman", () -> new FastingTalismanItem(new Properties().stacksTo(1))
   );
   public static final DeferredHolder<Item, Item> FOUNDATION_BUILDING_PILL = ITEMS.register(
      "foundation_building_pill",
      () -> new Item(new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build()))
   );
   public static final DeferredHolder<Item, Item> STELLAR_CORE_PILL = ITEMS.register(
      "stellar_core_pill",
      () -> new StellarCorePillItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(2).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> GIANT_SPIRIT_PILL = ITEMS.register(
      "giant_spirit_pill",
      () -> new GiantSpiritPillItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> STRENGTH_PILL = ITEMS.register(
      "strength_pill",
      () -> new StrengthPillItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> DEMON_SPIRIT_PILL = ITEMS.register(
      "demon_spirit_pill",
      () -> new DemonSpiritPillItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(2).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> MYSTIC_VOID_ELIXIR = ITEMS.register(
      "mystic_void_elixir",
      () -> new Item(new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build()))
   );
   public static final DeferredHolder<Item, Item> VOID_REFINEMENT_PILL = ITEMS.register(
      "void_refinement_pill",
      () -> new Item(new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build()))
   );
   public static final DeferredHolder<Item, Item> HEAVEN_CLEANSING_ELIXIR = ITEMS.register(
      "heaven_cleansing_elixir",
      () -> new HeavenCleansingElixirItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> SPIRIT_CLEANSING_PILL = ITEMS.register(
      "spirit_cleansing_pill",
      () -> new SpiritCleansingPillItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(1).saturationModifier(0.5F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, Item> TORN_PAGE = ITEMS.register("torn_page", () -> new Item(new Properties().stacksTo(64)));
   public static final DeferredHolder<Item, AncientBookItem> ANCIENT_BOOK = ITEMS.register(
      "ancient_book", () -> new AncientBookItem(new Properties().stacksTo(1))
   );
   public static final DeferredHolder<Item, SpiritGlassesItem> SPIRIT_GLASSES = ITEMS.register("spirit_glasses", () -> new SpiritGlassesItem(new Properties()));
   public static final DeferredHolder<Item, SpiritCompassItem> SPIRIT_COMPASS = ITEMS.register("spirit_compass", () -> new SpiritCompassItem(new Properties()));
   public static final DeferredHolder<Item, ZhaoYingZhuItem> ZHAO_YING_ZHU = ITEMS.register(
      "zhao_ying_zhu", () -> new ZhaoYingZhuItem(new Properties().rarity(Rarity.RARE))
   );
   public static final DeferredHolder<Item, Item> ALCHEMY_FURNACE = ITEMS.register(
      "alchemy_furnace", () -> new BlockItem((Block)BlockRegistry.ALCHEMY_FURNACE.get(), new Properties())
   );
   public static final DeferredHolder<Item, AncientScholarTableItem> ANCIENT_SCHOLAR_TABLE = ITEMS.register(
      "ancient_scholar_table", () -> new AncientScholarTableItem((Block)BlockRegistry.ANCIENT_SCHOLAR_TABLE.get(), new Properties())
   );
   public static final DeferredHolder<Item, AncientSteleItem> ANCIENT_STELE = ITEMS.register(
      "ancient_stele", () -> new AncientSteleItem((Block)BlockRegistry.ANCIENT_STELE.get(), new Properties())
   );
   public static final DeferredHolder<Item, RuneTableItem> RUNE_TABLE = ITEMS.register(
      "rune_table", () -> new RuneTableItem((Block)BlockRegistry.RUNE_TABLE.get(), new Properties())
   );
   public static final DeferredHolder<Item, TenThousandSoulBannerItem> TEN_THOUSAND_SOUL_BANNER = ITEMS.register(
      "ten_thousand_soul_banner", () -> new TenThousandSoulBannerItem(new Properties().stacksTo(1).rarity(Rarity.RARE))
   );
   public static final DeferredHolder<Item, TheSupremeAlchemyCanonItem> THE_SUPREME_ALCHEMY_CANON = ITEMS.register(
      "the_supreme_alchemy_canon", () -> new TheSupremeAlchemyCanonItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, SwordFlightManualItem> SWORD_FLIGHT_MANUAL = ITEMS.register(
      "sword_flight_manual", () -> new SwordFlightManualItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, MyriadSwordsReturnManualItem> MYRIAD_SWORDS_RETURN_MANUAL = ITEMS.register(
      "myriad_swords_return_manual", () -> new MyriadSwordsReturnManualItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, SizeTransformationManualItem> SIZE_TRANSFORMATION_MANUAL = ITEMS.register(
      "size_transformation_manual", () -> new SizeTransformationManualItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, TalismanManualItem> TALISMAN_MANUAL = ITEMS.register(
      "talisman_manual", () -> new TalismanManualItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, EssenceCharcoalItem> MUD_ESSENCE_CHARCOAL = ITEMS.register(
      "mud_essence_charcoal", () -> new EssenceCharcoalItem(new Properties().stacksTo(64), 2700)
   );
   public static final DeferredHolder<Item, EssenceCharcoalItem> YELLOW_ESSENCE_CHARCOAL = ITEMS.register(
      "yellow_essence_charcoal", () -> new EssenceCharcoalItem(new Properties().stacksTo(64), 3900)
   );
   public static final DeferredHolder<Item, EssenceCharcoalItem> BLACK_ESSENCE_CHARCOAL = ITEMS.register(
      "black_essence_charcoal", () -> new EssenceCharcoalItem(new Properties().stacksTo(64), 5200)
   );
   public static final DeferredHolder<Item, EssenceCharcoalItem> VOID_ESSENCE_CHARCOAL = ITEMS.register(
      "void_essence_charcoal", () -> new EssenceCharcoalItem(new Properties().stacksTo(64), 7200)
   );
   public static final DeferredHolder<Item, EssenceCharcoalItem> PRIME_ESSENCE_CHARCOAL = ITEMS.register(
      "prime_essence_charcoal", () -> new EssenceCharcoalItem(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON), 10000)
   );
   public static final DeferredHolder<Item, BlockItem> SPIRIT_CHARCOAL_ORE = ITEMS.register(
      "spirit_charcoal_ore", () -> new BlockItem((Block)BlockRegistry.SPIRIT_CHARCOAL_ORE.get(), new Properties())
   );
   public static final DeferredHolder<Item, BlockItem> DEEPSLATE_SPIRIT_CHARCOAL_ORE = ITEMS.register(
      "deepslate_spirit_charcoal_ore", () -> new BlockItem((Block)BlockRegistry.DEEPSLATE_SPIRIT_CHARCOAL_ORE.get(), new Properties())
   );
   public static final DeferredHolder<Item, CrystalOfCombustionItem> CRYSTAL_OF_COMBUSTION = ITEMS.register(
      "crystal_of_combustion",
      () -> new CrystalOfCombustionItem(
         new Properties().stacksTo(16).rarity(Rarity.EPIC).food(new Builder().nutrition(0).saturationModifier(0.0F).fast().alwaysEdible().build())
      )
   );
   public static final DeferredHolder<Item, RuyiJinguBangItem> RUYI_JINGU_BANG = ITEMS.register(
      "ruyi_jingu_bang", () -> new RuyiJinguBangItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );
   public static final DeferredHolder<Item, BreakAndRebuildManualItem> BREAK_AND_REBUILD_MANUAL = ITEMS.register(
      "break_and_rebuild_manual", () -> new BreakAndRebuildManualItem(new Properties().stacksTo(1).rarity(Rarity.EPIC))
   );

   public static void register(IEventBus modEventBus) {
      ITEMS.register(modEventBus);
   }
}
