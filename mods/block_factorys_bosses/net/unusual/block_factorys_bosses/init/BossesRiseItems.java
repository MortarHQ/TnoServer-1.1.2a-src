package net.unusual.block_factorys_bosses.init;

import java.util.function.UnaryOperator;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.item.AchievementIconsItem;
import net.unusual.block_factorys_bosses.item.AnchorEntityItem;
import net.unusual.block_factorys_bosses.item.CageEntityItem;
import net.unusual.block_factorys_bosses.item.CannonEntityItem;
import net.unusual.block_factorys_bosses.item.CrateEntityItem;
import net.unusual.block_factorys_bosses.item.DaggerItem;
import net.unusual.block_factorys_bosses.item.DragonBoneItem;
import net.unusual.block_factorys_bosses.item.DragonBonesItem;
import net.unusual.block_factorys_bosses.item.DragonGuardShieldItem;
import net.unusual.block_factorys_bosses.item.DragonShankItem;
import net.unusual.block_factorys_bosses.item.EnhancedShieldItem;
import net.unusual.block_factorys_bosses.item.IceGauntletItem;
import net.unusual.block_factorys_bosses.item.KnightItem;
import net.unusual.block_factorys_bosses.item.KnightSwordItem;
import net.unusual.block_factorys_bosses.item.KrakenTridentItem;
import net.unusual.block_factorys_bosses.item.LargeSwordItem;
import net.unusual.block_factorys_bosses.item.LootTableStickDragonItem;
import net.unusual.block_factorys_bosses.item.LootTableStickItem;
import net.unusual.block_factorys_bosses.item.LootTableStickRareItem;
import net.unusual.block_factorys_bosses.item.PirateSaberItem;
import net.unusual.block_factorys_bosses.item.PlaceholderItem;
import net.unusual.block_factorys_bosses.item.SandwormGauntletItem;
import net.unusual.block_factorys_bosses.item.TPStickItem;
import net.unusual.block_factorys_bosses.item.UnderworldArenaKeyItem;
import net.unusual.block_factorys_bosses.item.UndyingTentacleItem;
import net.unusual.block_factorys_bosses.item.WarriorSwordItem;
import net.unusual.block_factorys_bosses.procedures.AchievementIconsPropertyValueProviderProcedure;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseItems {
   public static final Items REGISTRY = DeferredRegister.createItems("block_factorys_bosses");
   public static final DeferredItem<Item> BIG_OAK_PLANKS = block(BossesRiseBlocks.BIG_OAK_PLANKS);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_BROKEN = block(BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_CRACKED = block(BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_STRAIGHT = block(BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_VARIATION = block(BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_WET = block(BossesRiseBlocks.BIG_OAK_PLANKS_WET);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_WET_BROKEN = block(BossesRiseBlocks.BIG_OAK_PLANKS_WET_BROKEN);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_WET_CRACKED = block(BossesRiseBlocks.BIG_OAK_PLANKS_WET_CRACKED);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_WET_STRAIGHT = block(BossesRiseBlocks.BIG_OAK_PLANKS_WET_STRAIGHT);
   public static final DeferredItem<Item> BIG_OAK_PLANKS_WET_VARIATION = block(BossesRiseBlocks.BIG_OAK_PLANKS_WET_VARIATION);
   public static final DeferredItem<Item> GUARDRAIL = block(BossesRiseBlocks.GUARDRAIL);
   public static final DeferredItem<Item> GUARDRAIL_DIAGONAL = block(BossesRiseBlocks.GUARDRAIL_DIAGONAL);
   public static final DeferredItem<Item> COIN_PILE = block(BossesRiseBlocks.COIN_PILE);
   public static final DeferredItem<Item> ROPE_ROLL = block(BossesRiseBlocks.ROPE_ROLL);
   public static final DeferredItem<Item> SHIP_STEERING_WHEEL = block(BossesRiseBlocks.SHIP_STEERING_WHEEL);
   public static final DeferredItem<Item> CANNONBALL = block(BossesRiseBlocks.CANNONBALL);
   public static final DeferredItem<Item> NET = block(BossesRiseBlocks.NET);
   public static final DeferredItem<Item> VASE = block(BossesRiseBlocks.VASE);
   public static final DeferredItem<Item> WALL_TORCH = block(BossesRiseBlocks.WALL_TORCH);
   public static final DeferredItem<Item> UNDERWOLD_WALL_TORCH = block(BossesRiseBlocks.UNDERWOLD_WALL_TORCH);
   public static final DeferredItem<Item> TALL_VASE = block(BossesRiseBlocks.TALL_VASE);
   public static final DeferredItem<Item> CANDLES = block(BossesRiseBlocks.CANDLES);
   public static final DeferredItem<Item> UNDERWOLD_CANDLES = block(BossesRiseBlocks.UNDERWOLD_CANDLES);
   public static final DeferredItem<Item> TALL_CANDLES = block(BossesRiseBlocks.TALL_CANDLES);
   public static final DeferredItem<Item> UNDERWORLD_TALL_CANDLES = block(BossesRiseBlocks.UNDERWORLD_TALL_CANDLES);
   public static final DeferredItem<Item> BONE_REMAINS = block(BossesRiseBlocks.BONE_REMAINS);
   public static final DeferredItem<Item> CORPSE = block(BossesRiseBlocks.CORPSE);
   public static final DeferredItem<Item> BONE_REMAINS_LEGS = block(BossesRiseBlocks.BONE_REMAINS_LEGS);
   public static final DeferredItem<Item> BONE_REMAINS_RIB_CAGE = block(BossesRiseBlocks.BONE_REMAINS_RIB_CAGE);
   public static final DeferredItem<Item> TALL_CANDLES_CROSS = block(BossesRiseBlocks.TALL_CANDLES_CROSS);
   public static final DeferredItem<Item> UNDER_WORLD_TALL_CANDLE_CROSS = block(BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS);
   public static final DeferredItem<Item> PILE_OF_BONES_SPAWN_EGG = REGISTRY.register(
      "pile_of_bones_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.PILE_OF_BONES, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> BIG_CHAIN = block(BossesRiseBlocks.BIG_CHAIN);
   public static final DeferredItem<Item> PRISON_DOOR = block(BossesRiseBlocks.PRISON_DOOR);
   public static final DeferredItem<Item> RUSTY_PRISON_DOOR = block(BossesRiseBlocks.RUSTY_PRISON_DOOR);
   public static final DeferredItem<Item> YETI_SPAWN_EGG = REGISTRY.register(
      "yeti_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.YETI, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> ICE_BLOCK_PARTICULES = block(BossesRiseBlocks.ICE_BLOCK_PARTICULES);
   public static final DeferredItem<CageEntityItem> CAGE_ITEM = REGISTRY.register(
      "cage_item", () -> new CageEntityItem(BossesRiseEntities.CAGE, new Properties())
   );
   public static final DeferredItem<CageEntityItem> CAGE_SKELLY_ITEM = REGISTRY.register(
      "cage_skelly_item", () -> new CageEntityItem(BossesRiseEntities.CAGE_SKELLY, new Properties())
   );
   public static final DeferredItem<CageEntityItem> BIG_CAGE_ITEM = REGISTRY.register(
      "big_cage_item", () -> new CageEntityItem(BossesRiseEntities.BIG_CAGE, new Properties())
   );
   public static final DeferredItem<CageEntityItem> BIG_CAGE_SKELLY_ITEM = REGISTRY.register(
      "big_cage_skelly_item", () -> new CageEntityItem(BossesRiseEntities.BIG_CAGE_SKELLY, new Properties())
   );
   public static final DeferredItem<CannonEntityItem> KRAKEN_CANNON_ITEM = REGISTRY.register(
      "kraken_cannon_item", () -> new CannonEntityItem(BossesRiseEntities.KRAKEN_CANNON, new Properties())
   );
   public static final DeferredItem<Item> DRAGON_GUARD_SHIELD = REGISTRY.register("dragon_guard_shield", DragonGuardShieldItem::new);
   public static final DeferredItem<Item> ENHANCED_SHIELD = REGISTRY.register("enhanced_shield", EnhancedShieldItem::new);
   public static final DeferredItem<Item> INFERNAL_DRAGON_SPAWN_EGG = REGISTRY.register(
      "infernal_dragon_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.INFERNAL_DRAGON, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> DRAGON_BANNER = block(BossesRiseBlocks.DRAGON_BANNER);
   public static final DeferredItem<Item> TP_STICK = REGISTRY.register("tp_stick", TPStickItem::new);
   public static final DeferredItem<Item> LOOT_TABLE_STICK = REGISTRY.register("loot_table_stick", LootTableStickItem::new);
   public static final DeferredItem<Item> LOOT_TABLE_STICK_RARE = REGISTRY.register("loot_table_stick_rare", LootTableStickRareItem::new);
   public static final DeferredItem<Item> DRAGON_BONES_CHESTPLATE = REGISTRY.register("dragon_bones_chestplate", DragonBonesItem.Chestplate::new);
   public static final DeferredItem<Item> DRAGON_BONES_LEGGINGS = REGISTRY.register("dragon_bones_leggings", DragonBonesItem.Leggings::new);
   public static final DeferredItem<Item> DRAGON_BONES_BOOTS = REGISTRY.register("dragon_bones_boots", DragonBonesItem.Boots::new);
   public static final DeferredItem<Item> DRAGON_SKULL = block(
      BossesRiseBlocks.DRAGON_SKULL, properties -> properties.rarity(Rarity.UNCOMMON).attributes(DragonBonesItem.createAttributes(EquipmentSlot.HEAD))
   );
   public static final DeferredItem<Item> ICE_GAUNTLET = REGISTRY.register("ice_gauntlet", IceGauntletItem::new);
   public static final DeferredItem<Item> PLACEHOLDER = REGISTRY.register("placeholder", PlaceholderItem::new);
   public static final DeferredItem<Item> KNIGHT_HELMET = REGISTRY.register("knight_helmet", KnightItem.Helmet::new);
   public static final DeferredItem<Item> KNIGHT_CHESTPLATE = REGISTRY.register("knight_chestplate", KnightItem.Chestplate::new);
   public static final DeferredItem<Item> KNIGHT_LEGGINGS = REGISTRY.register("knight_leggings", KnightItem.Leggings::new);
   public static final DeferredItem<Item> KNIGHT_BOOTS = REGISTRY.register("knight_boots", KnightItem.Boots::new);
   public static final DeferredItem<Item> LARGE_SWORD = REGISTRY.register("large_sword", LargeSwordItem::new);
   public static final DeferredItem<Item> ACHIEVEMENT_ICONS = REGISTRY.register("achievement_icons", AchievementIconsItem::new);
   public static final DeferredItem<Item> UNDERWORLD_KNIGHT_SPAWN_EGG = REGISTRY.register(
      "underworld_knight_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.UNDERWORLD_KNIGHT, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> BOSS_SPAWNER = block(BossesRiseBlocks.BOSS_SPAWNER);
   public static final DeferredItem<Item> KRAKEN_SPAWNER = block(BossesRiseBlocks.KRAKEN_SPAWNER);
   public static final DeferredItem<Item> WARRIOR_SWORD = REGISTRY.register("warrior_sword", WarriorSwordItem::new);
   public static final DeferredItem<Item> DAGGER = REGISTRY.register("dagger", DaggerItem::new);
   public static final DeferredItem<Item> DRAGON_BONE = REGISTRY.register("dragon_bone", DragonBoneItem::new);
   public static final DeferredItem<SwordItem> PIRATE_SABER = REGISTRY.register("pirate_saber", PirateSaberItem::new);
   public static final DeferredItem<Item> LOOT_TABLE_STICK_DRAGON = REGISTRY.register("loot_table_stick_dragon", LootTableStickDragonItem::new);
   public static final DeferredItem<Item> DRAGON_GUARD_SWORD_SPAWN_EGG = REGISTRY.register(
      "dragon_guard_sword_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.DRAGON_GUARD_SWORD, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> FLAMING_SKELETON_GUARD_SWORD_SPAWN_EGG = REGISTRY.register(
      "flaming_skeleton_guard_sword_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> FLAMING_SKELETON_GUARD_FIREBALL_SPAWN_EGG = REGISTRY.register(
      "flaming_skeleton_guard_fireball_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> SOUL_SKELETON_SPAWN_EGG = REGISTRY.register(
      "soul_skeleton_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.SOUL_SKELETON, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> SOUL_KNIGHT_WITHER_SKELETON_SPAWN_EGG = REGISTRY.register(
      "soul_knight_wither_skeleton_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.SOUL_KNIGHT_WITHER_SKELETON, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> KNIGHT_SWORD = REGISTRY.register("knight_sword", KnightSwordItem::new);
   public static final DeferredItem<Item> SANDWORM_SPAWN_EGG = REGISTRY.register(
      "sandworm_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.SANDWORM, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> SANDWORM_GAUNTLET = REGISTRY.register("sandworm_gauntlet", SandwormGauntletItem::new);
   public static final DeferredItem<Item> DRAGON_SHANK = REGISTRY.register("dragon_shank", DragonShankItem::new);
   public static final DeferredItem<Item> ANCIENT_TRIAL_KEY = REGISTRY.registerItem("ancient_trial_key", Item::new);
   public static final DeferredItem<Item> UNDERWORLD_ARENA_KEY = REGISTRY.registerItem("underworld_arena_key", UnderworldArenaKeyItem::new);
   public static final DeferredItem<BlockItem> UNDERWORLD_ARENA_DOOR = REGISTRY.registerSimpleBlockItem(
      "underworld_arena_door", BossesRiseBlocks.UNDERWORLD_ARENA_DOOR
   );
   public static final DeferredItem<Item> KRAKEN_SPAWN_EGG = REGISTRY.register(
      "kraken_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.KRAKEN, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> PIRATE_CAPTAIN_SPAWN_EGG = REGISTRY.register(
      "pirate_captain_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.PIRATE_CAPTAIN, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> PIRATE_ROOK_SPAWN_EGG = REGISTRY.register(
      "pirate_rook_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.PIRATE_ROOK, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> CROSSBOW_PIRATE_SPAWN_EGG = REGISTRY.register(
      "crossbow_pirate_spawn_egg", () -> new DeferredSpawnEggItem(BossesRiseEntities.CROSSBOW_PIRATE, -1, -1, new Properties())
   );
   public static final DeferredItem<Item> KRAKEN_TOOTH = REGISTRY.registerItem("kraken_tooth", Item::new);
   public static final DeferredItem<Item> KRAKEN_TRIDENT = REGISTRY.register(
      "kraken_trident",
      () -> new KrakenTridentItem(
         new Properties()
            .rarity(Rarity.EPIC)
            .durability(2500)
            .attributes(KrakenTridentItem.createAttributes())
            .component(DataComponents.TOOL, KrakenTridentItem.createToolProperties())
      )
   );
   public static final DeferredItem<Item> UNDYING_TENTACLE = REGISTRY.register("undying_tentacle", UndyingTentacleItem::new);
   public static final DeferredItem<Item> SHIP_LANTERN = block(BossesRiseBlocks.SHIP_LANTERN);
   public static final DeferredItem<Item> ANCHOR_ITEM = REGISTRY.register("anchor", () -> new AnchorEntityItem(BossesRiseEntities.ANCHOR, new Properties()));
   public static final DeferredItem<Item> PLANK = REGISTRY.register("plank", () -> new BlockItem((Block)BossesRiseBlocks.PLANK.get(), new Properties()));
   public static final DeferredItem<Item> CRATE = REGISTRY.register("crate", () -> new CrateEntityItem(BossesRiseEntities.CRATE_PILE, new Properties()));

   private static <T extends Block> DeferredItem<Item> block(DeferredHolder<Block, T> block) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Properties()));
   }

   private static <T extends Block> DeferredItem<Item> block(DeferredHolder<Block, T> block, UnaryOperator<Properties> properties) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), properties.apply(new Properties())));
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class ItemsClientSideHandler {
      @SubscribeEvent
      @OnlyIn(Dist.CLIENT)
      public static void clientLoad(FMLClientSetupEvent event) {
         event.enqueueWork(
            () -> {
               ItemProperties.register(
                  (Item)BossesRiseItems.DRAGON_GUARD_SHIELD.get(),
                  ResourceLocation.parse("minecraft:blocking"),
                  ItemProperties.getProperty(new ItemStack(net.minecraft.world.item.Items.SHIELD), ResourceLocation.parse("minecraft:blocking"))
               );
               ItemProperties.register(
                  (Item)BossesRiseItems.ENHANCED_SHIELD.get(),
                  ResourceLocation.parse("minecraft:blocking"),
                  ItemProperties.getProperty(new ItemStack(net.minecraft.world.item.Items.SHIELD), ResourceLocation.parse("minecraft:blocking"))
               );
               ItemProperties.register(
                  (Item)BossesRiseItems.ICE_GAUNTLET.get(),
                  ResourceLocation.parse("minecraft:blocking"),
                  ItemProperties.getProperty(new ItemStack(net.minecraft.world.item.Items.SHIELD), ResourceLocation.parse("minecraft:blocking"))
               );
               ItemProperties.register(
                  (Item)BossesRiseItems.ACHIEVEMENT_ICONS.get(),
                  BossesRise.prefix("achievement_icons_icon_index"),
                  (itemStackToRender, clientWorld, entity, itemEntityId) -> (float)AchievementIconsPropertyValueProviderProcedure.execute(itemStackToRender)
               );
            }
         );
      }
   }
}
