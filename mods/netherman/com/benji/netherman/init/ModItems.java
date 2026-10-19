package com.benji.netherman.init;

import com.benji.netherman.common.item.AltarCompassKeyItem;
import com.benji.netherman.common.item.AzazelArmorItem;
import com.benji.netherman.common.item.AzazelGuideBookItem;
import com.benji.netherman.common.item.AzazelScytheItem;
import com.benji.netherman.common.item.AzazelShieldItem;
import com.benji.netherman.common.item.AzazelSpearItem;
import com.benji.netherman.common.item.ChanceTotemItem;
import com.benji.netherman.common.item.CrimsonArrowItem;
import com.benji.netherman.common.item.CrimsonHoneyBottleItem;
import com.benji.netherman.common.item.FaithEssenceItem;
import com.benji.netherman.common.item.ManipulatorStickItem;
import com.benji.netherman.common.item.NoteItem;
import com.benji.netherman.common.item.QuotaItem;
import java.util.Collection;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class ModItems {
   private static final Supplier<Item> SIMPLE_SUPPLIER = () -> new Item(new Properties());
   public static final Items ITEMS = DeferredRegister.createItems("netherman");
   public static final DeferredItem<Item> ALTAR_COMPASS_KEY = register("altar_compass_key", () -> new AltarCompassKeyItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> QUEST_ICON_1 = registerSimple("locker_quest1");
   public static final DeferredItem<Item> QUEST_ICON_2 = registerSimple("locker_quest2");
   public static final DeferredItem<Item> QUEST_ICON_3 = registerSimple("locker_quest3");
   public static final DeferredItem<Item> MANIPULATOR_STICK = register("manipulator_stick", ManipulatorStickItem::new);
   public static final DeferredItem<Item> CHANCE_TOTEM = register("chance_totem", () -> new ChanceTotemItem(new Properties()));
   public static final DeferredItem<Item> NOTE = register("note", () -> new NoteItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> CRIMSON_ARROW = register("crimson_arrow", () -> new CrimsonArrowItem(new Properties()));
   public static final DeferredItem<Item> CRIMSON_HONEY_BOTTLE = register(
      "crimson_honey_bottle",
      () -> new CrimsonHoneyBottleItem(
         new Properties()
            .stacksTo(16)
            .craftRemainder(net.minecraft.world.item.Items.GLASS_BOTTLE)
            .food(new Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build())
      )
   );
   public static final DeferredItem<Item> MAZE_KEY = register("maze_key", () -> new Item(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> AZAZEL_GUIDE_BOOK = register("azazel_guide_book", () -> new AzazelGuideBookItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> GILDED_GOLEM_SPAWN_EGG = registerSpawnEgg("gilded_golem", ModEntities.GILDED_GOLEM);
   public static final DeferredItem<Item> GHASTLY_SPAWN_EGG = registerSpawnEgg("ghastly", ModEntities.GHASTLY);
   public static final DeferredItem<Item> GUARDIAN_SPAWN_EGG = registerSpawnEgg("guardian", ModEntities.GUARDIAN);
   public static final DeferredItem<Item> BELIEVER_SPAWN_EGG = registerSpawnEgg("believer", ModEntities.BELIEVER);
   public static final DeferredItem<Item> BELIEVER_VILLAGER_SPAWN_EGG = registerSpawnEgg("believer_villager", ModEntities.BELIEVER_VILLAGER);
   public static final DeferredItem<Item> BLACKSMITH_SPAWN_EGG = registerSpawnEgg("blacksmith", ModEntities.BLACKSMITH);
   public static final DeferredItem<Item> DOCTOR_SPAWN_EGG = registerSpawnEgg("doctor", ModEntities.DOCTOR);
   public static final DeferredItem<Item> MANIPULATOR_SPAWN_EGG = registerSpawnEgg("manipulator", ModEntities.MANIPULATOR);
   public static final DeferredItem<Item> STATUE_BOSSUNIT_SPAWN_EGG = registerSpawnEgg("statue_bossunit", ModEntities.STATUE_BOSSUNIT);
   public static final DeferredItem<Item> STATUE_SPAWN_EGG = registerSpawnEgg("statue_entity", ModEntities.STATUE);
   public static final DeferredItem<Item> TRADER_SPAWN_EGG = registerSpawnEgg("trader", ModEntities.TRADER);
   public static final DeferredItem<Item> AZAZEL_SPAWN_EGG = registerSpawnEgg("azazel", ModEntities.AZAZEL);
   public static final DeferredItem<Item> MUSIC_DISC_BOSS = registerMusicDisc("boss", ModJukeboxSongs.BOSS_SONG);
   public static final DeferredItem<Item> MUSIC_DISC_QUAR = registerMusicDisc("quar", ModJukeboxSongs.CAVE_AMBIENT);
   public static final DeferredItem<Item> MUSIC_DISC_SACRED = registerMusicDisc("sacred", ModJukeboxSongs.CITY_AMBIENT);
   public static final DeferredItem<Item> MUSIC_DISC_AZAZEL = registerMusicDisc("azazel", ModJukeboxSongs.CHURCH_AMBIENT);
   public static final DeferredItem<Item> MUSIC_DISC_MAZE = registerMusicDisc("maze", ModJukeboxSongs.MAZE_AMBIENT);
   public static final DeferredItem<Item> MUSIC_DISC_GOD = registerMusicDisc("god", ModJukeboxSongs.AZAZEL_SONG);
   public static final DeferredItem<Item> ANGEL_SPAWNER_ITEM = ITEMS.register(
      "angel_spawner", () -> new BlockItem((Block)ModBlocks.ANGEL_SPAWNER.get(), new Properties())
   );
   public static final DeferredItem<Item> FACE_SPAWNER_ITEM = ITEMS.register(
      "face_spawner", () -> new BlockItem((Block)ModBlocks.FACE_SPAWNER.get(), new Properties())
   );
   public static final DeferredItem<Item> VILLAGE_SPAWNER_ITEM = ITEMS.register(
      "village_spawner", () -> new BlockItem((Block)ModBlocks.VILLAGE_SPAWNER.get(), new Properties())
   );
   public static final DeferredItem<Item> KING_SPAWNER_ITEM = ITEMS.register(
      "king_spawner", () -> new BlockItem((Block)ModBlocks.KING_SPAWNER.get(), new Properties())
   );
   public static final DeferredItem<Item> THRONE_SPAWNER_ITEM = ITEMS.register(
      "throne_spawner", () -> new BlockItem((Block)ModBlocks.THRONE_SPAWNER.get(), new Properties())
   );
   public static final DeferredItem<Item> QUOTA = ITEMS.register("quota", () -> new QuotaItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> FAITH_PART = ITEMS.register("faith_part", () -> new Item(new Properties().stacksTo(27)));
   public static final DeferredItem<Item> FAITH_ESSENCE = ITEMS.register("faith_essence", () -> new FaithEssenceItem(new Properties().stacksTo(27)));
   public static final DeferredItem<Item> AZAZEL_SPEAR = ITEMS.register("azazel_spear", () -> new AzazelSpearItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> AZAZEL_SCYTHE = ITEMS.register("azazel_scythe", () -> new AzazelScytheItem(new Properties().stacksTo(1)));
   public static final DeferredItem<Item> AZAZEL_HELMET = ITEMS.register(
      "azazel_helmet",
      () -> new AzazelArmorItem(ModArmorMaterials.AZAZEL_MATERIAL, Type.HELMET, new Properties().stacksTo(1).durability(Type.HELMET.getDurability(37)))
   );
   public static final DeferredItem<Item> AZAZEL_CHESTPLATE = ITEMS.register(
      "azazel_chestplate",
      () -> new AzazelArmorItem(ModArmorMaterials.AZAZEL_MATERIAL, Type.CHESTPLATE, new Properties().stacksTo(1).durability(Type.CHESTPLATE.getDurability(37)))
   );
   public static final DeferredItem<Item> AZAZEL_LEGGINGS = ITEMS.register(
      "azazel_leggings",
      () -> new AzazelArmorItem(ModArmorMaterials.AZAZEL_MATERIAL, Type.LEGGINGS, new Properties().stacksTo(1).durability(Type.LEGGINGS.getDurability(37)))
   );
   public static final DeferredItem<Item> AZAZEL_BOOTS = ITEMS.register(
      "azazel_boots",
      () -> new AzazelArmorItem(ModArmorMaterials.AZAZEL_MATERIAL, Type.BOOTS, new Properties().stacksTo(1).durability(Type.BOOTS.getDurability(37)))
   );
   public static final DeferredItem<Item> AZAZEL_SHIELD = ITEMS.register("azazel_shield", () -> new AzazelShieldItem(new Properties().stacksTo(1)));

   private static DeferredItem<Item> registerSimple(String name, Properties itemProperties) {
      return register(name, () -> new Item(itemProperties));
   }

   private static DeferredItem<Item> registerSimple(String name) {
      return register(name, SIMPLE_SUPPLIER);
   }

   private static DeferredItem<Item> registerSpawnEgg(String id, Supplier<? extends EntityType<? extends Mob>> type) {
      return register(id + "_spawn_egg", () -> new DeferredSpawnEggItem(type, 16777215, 16777215, new Properties()));
   }

   private static DeferredItem<Item> registerMusicDisc(String id, ResourceKey<JukeboxSong> song) {
      return register("music_disc_" + id, () -> new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(song)));
   }

   private static <T extends Item> DeferredItem<T> register(String id, Supplier<T> pIProp) {
      return ITEMS.register(id.toLowerCase(), pIProp);
   }

   static void registerBlockItem(Holder<Block> blockHolder) {
      registerBlockItem(blockHolder, new Properties());
   }

   static void registerBlockItem(Holder<Block> blockHolder, Properties properties) {
      ITEMS.registerSimpleBlockItem(blockHolder, properties);
   }

   static DeferredItem<BlockItem> registerBlockItem(String name, Supplier<? extends Block> blockHolder, Properties properties) {
      return ITEMS.registerSimpleBlockItem(name, blockHolder, properties);
   }

   static void registerSpecialBlockItem(Holder<Block> blockHolder, Supplier<? extends BlockItem> sup) {
      String id = ((ResourceKey)blockHolder.unwrapKey().orElseThrow()).location().getPath();
      ITEMS.register(id, sup);
   }

   public static void init(IEventBus bus) {
      ITEMS.register(bus);
   }

   public static Collection<DeferredHolder<Item, ? extends Item>> getItems() {
      return ITEMS.getEntries();
   }
}
