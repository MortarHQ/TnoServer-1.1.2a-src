package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.item.TotemItem;
import com.faboslav.friendsandfoes.common.mixin.SpawnEggItemAccessor;
import com.teamresourceful.resourcefullib.common.registry.ItemLikeResourcefulRegistry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;

public final class FriendsAndFoesItems {
   public static final ItemLikeResourcefulRegistry<Item> ITEMS = new ItemLikeResourcefulRegistry(BuiltInRegistries.ITEM, "friendsandfoes");
   public static final RegistryEntry<Item> COPPER_GOLEM_SPAWN_EGG = registerSpawnEgg(
      "copper_golem_spawn_egg", FriendsAndFoesEntityTypes.COPPER_GOLEM, -6664136, 268421528
   );
   public static final RegistryEntry<Item> CRAB_SPAWN_EGG = registerSpawnEgg("crab_spawn_egg", FriendsAndFoesEntityTypes.CRAB, -13422473, -92085);
   public static final RegistryEntry<Item> GLARE_SPAWN_EGG = registerSpawnEgg("glare_spawn_egg", FriendsAndFoesEntityTypes.GLARE, -9399763, -9809369);
   public static final RegistryEntry<Item> ICEOLOGER_SPAWN_EGG = registerSpawnEgg(
      "iceologer_spawn_egg", FriendsAndFoesEntityTypes.ICEOLOGER, -15255437, -7038053
   );
   public static final RegistryEntry<Item> ILLUSIONER_SPAWN_EGG = registerSpawnEgg(
      "illusioner_spawn_egg", FriendsAndFoesEntityTypes.ILLUSIONER, -10469796, -7827826
   );
   public static final RegistryEntry<Item> MAULER_SPAWN_EGG = registerSpawnEgg("mauler_spawn_egg", FriendsAndFoesEntityTypes.MAULER, -11317467, -8291527);
   public static final RegistryEntry<Item> MOOBLOOM_SPAWN_EGG = registerSpawnEgg("moobloom_spawn_egg", FriendsAndFoesEntityTypes.MOOBLOOM, -528959, -341504);
   public static final RegistryEntry<Item> RASCAL_SPAWN_EGG = registerSpawnEgg("rascal_spawn_egg", FriendsAndFoesEntityTypes.RASCAL, -16419990, -7712228);
   public static final RegistryEntry<Item> TUFF_GOLEM_SPAWN_EGG = registerSpawnEgg(
      "tuff_golem_spawn_egg", FriendsAndFoesEntityTypes.TUFF_GOLEM, -6249833, -10658478
   );
   public static final RegistryEntry<Item> WILDFIRE_SPAWN_EGG = registerSpawnEgg("wildfire_spawn_egg", FriendsAndFoesEntityTypes.WILDFIRE, -9686784, -10968);
   public static final RegistryEntry<Item> BUTTERCUP = registerItem(
      "buttercup", properties -> new BlockItem((Block)FriendsAndFoesBlocks.BUTTERCUP.get(), properties), () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> CRAB_CLAW = registerItem("crab_claw", Item::new, Properties::new);
   public static final RegistryEntry<Item> CRAB_EGG = registerItem(
      "crab_egg", properties -> new BlockItem((Block)FriendsAndFoesBlocks.CRAB_EGG.get(), properties), () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> ACACIA_BEEHIVE = registerBeehive("acacia_beehive", FriendsAndFoesBlocks.ACACIA_BEEHIVE);
   public static final RegistryEntry<Item> BAMBOO_BEEHIVE = registerBeehive("bamboo_beehive", FriendsAndFoesBlocks.BAMBOO_BEEHIVE);
   public static final RegistryEntry<Item> BIRCH_BEEHIVE = registerBeehive("birch_beehive", FriendsAndFoesBlocks.BIRCH_BEEHIVE);
   public static final RegistryEntry<Item> CHERRY_BEEHIVE = registerBeehive("cherry_beehive", FriendsAndFoesBlocks.CHERRY_BEEHIVE);
   public static final RegistryEntry<Item> CRIMSON_BEEHIVE = registerBeehive("crimson_beehive", FriendsAndFoesBlocks.CRIMSON_BEEHIVE);
   public static final RegistryEntry<Item> DARK_OAK_BEEHIVE = registerBeehive("dark_oak_beehive", FriendsAndFoesBlocks.DARK_OAK_BEEHIVE);
   public static final RegistryEntry<Item> JUNGLE_BEEHIVE = registerBeehive("jungle_beehive", FriendsAndFoesBlocks.JUNGLE_BEEHIVE);
   public static final RegistryEntry<Item> MANGROVE_BEEHIVE = registerBeehive("mangrove_beehive", FriendsAndFoesBlocks.MANGROVE_BEEHIVE);
   public static final RegistryEntry<Item> SPRUCE_BEEHIVE = registerBeehive("spruce_beehive", FriendsAndFoesBlocks.SPRUCE_BEEHIVE);
   public static final RegistryEntry<Item> WARPED_BEEHIVE = registerBeehive("warped_beehive", FriendsAndFoesBlocks.WARPED_BEEHIVE);
   public static final RegistryEntry<Item> COPPER_BUTTON = registerItem(
      "copper_button", properties -> new BlockItem((Block)FriendsAndFoesBlocks.COPPER_BUTTON.get(), properties), () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> EXPOSED_COPPER_BUTTON = registerItem(
      "exposed_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.EXPOSED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WEATHERED_COPPER_BUTTON = registerItem(
      "weathered_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WEATHERED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> OXIDIZED_COPPER_BUTTON = registerItem(
      "oxidized_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.OXIDIZED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_COPPER_BUTTON = registerItem(
      "waxed_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_EXPOSED_COPPER_BUTTON = registerItem(
      "waxed_exposed_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_WEATHERED_COPPER_BUTTON = registerItem(
      "waxed_weathered_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_OXIDIZED_COPPER_BUTTON = registerItem(
      "waxed_oxidized_copper_button",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> EXPOSED_LIGHTNING_ROD = registerItem(
      "exposed_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.EXPOSED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WEATHERED_LIGHTNING_ROD = registerItem(
      "weathered_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WEATHERED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> OXIDIZED_LIGHTNING_ROD = registerItem(
      "oxidized_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.OXIDIZED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_LIGHTNING_ROD = registerItem(
      "waxed_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_EXPOSED_LIGHTNING_ROD = registerItem(
      "waxed_exposed_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_WEATHERED_LIGHTNING_ROD = registerItem(
      "waxed_weathered_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WAXED_OXIDIZED_LIGHTNING_ROD = registerItem(
      "waxed_oxidized_lightning_rod",
      properties -> new BlockItem((Block)FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), properties),
      () -> new Properties().stacksTo(64)
   );
   public static final RegistryEntry<Item> WILDFIRE_CROWN = registerItem(
      "wildfire_crown",
      properties -> new ArmorItem(FriendsAndFoesArmorMaterials.WILDFIRE.holder(), Type.HELMET, properties),
      () -> new Properties().stacksTo(1).fireResistant().durability(Type.HELMET.getDurability(37))
   );
   public static final RegistryEntry<Item> WILDFIRE_CROWN_FRAGMENT = registerItem("wildfire_crown_fragment", Item::new, () -> new Properties().fireResistant());
   public static final RegistryEntry<Item> TOTEM_OF_FREEZING = registerItem(
      "totem_of_freezing", TotemItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
   );
   public static final RegistryEntry<Item> TOTEM_OF_ILLUSION = registerItem(
      "totem_of_illusion", TotemItem::new, () -> new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
   );
   public static final RegistryEntry<Item> MUSIC_DISC_AROUND_THE_CORNER = registerItem(
      "music_disc_around_the_corner",
      Item::new,
      () -> new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(FriendsAndFoesJukeboxSongs.AROUND_THE_CORNER)
   );

   private FriendsAndFoesItems() {
   }

   private static RegistryEntry<Item> registerBeehive(String id, RegistryEntry<Block> block) {
      Properties beehiveProperties = new Properties().stacksTo(64);
      return registerItem(id, properties -> new BlockItem((Block)block.get(), properties), () -> beehiveProperties);
   }

   private static RegistryEntry<Item> registerItem(String id, Function<Properties, Item> factory, Supplier<Properties> getter) {
      return ITEMS.register(id, () -> factory.apply(getter.get()));
   }

   private static RegistryEntry<Item> registerSpawnEgg(
      String id, Supplier<? extends EntityType<? extends Mob>> typeIn, int primaryColorIn, int secondaryColorIn
   ) {
      return ITEMS.register(id, () -> {
         SpawnEggItem spawnEgg = new SpawnEggItem(typeIn.get(), primaryColorIn, secondaryColorIn, new Properties().stacksTo(64));
         Map<EntityType<? extends Mob>, SpawnEggItem> spawnEggMap = SpawnEggItemAccessor.friendsandfoes$getSpawnEggs();
         spawnEggMap.put((EntityType<? extends Mob>)typeIn.get(), spawnEgg);
         return spawnEgg;
      });
   }

   public static void registerCompostableItems() {
      ComposterBlock.add(0.65F, (ItemLike)BUTTERCUP.get());
   }
}
