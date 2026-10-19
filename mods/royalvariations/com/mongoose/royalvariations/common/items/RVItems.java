package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.items.armor.RoyalKnightArmor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class RVItems {
   public static Items ITEMS = DeferredRegister.createItems("royalvariations");
   public static final DeferredItem<Item> SPIRITUAL_CROWN_SHARD = ITEMS.register("spiritual_crown_shard", RVItems.ItemBase::new);
   public static final DeferredItem<Item> SPIRITUAL_CROWN = ITEMS.register("spiritual_crown", SpiritualCrownItem::new);
   public static final DeferredItem<Item> ROYAL_BONE = ITEMS.register("royal_bone", RoyalBoneItem::new);
   public static final DeferredItem<Item> ROYAL_BONE_MEAL = ITEMS.register("royal_bone_meal", RoyalBoneMealItem::new);
   public static final DeferredItem<Item> ROYAL_GUNPOWDER = ITEMS.register("royal_gunpowder", RVItems.ItemBase::new);
   public static final DeferredItem<Item> ROYAL_BOMB = ITEMS.register("royal_bomb", RoyalBombItem::new);
   public static final DeferredItem<Item> ROYAL_ARROW = ITEMS.register("royal_arrow", RoyalArrowItem::new);
   public static final DeferredItem<Item> ROYAL_ENDER_PEARL = ITEMS.register("royal_ender_pearl", RoyalEnderPearlItem::new);
   public static final DeferredItem<Item> ROYAL_ARMOR_FRAGMENTS = ITEMS.register("royal_armor_fragments", RVItems.ItemBase::new);
   public static final DeferredItem<Item> ROYAL_UPGRADE = ITEMS.register("royal_upgrade_smithing_template", RoyalUpgrade::new);
   public static final DeferredItem<Item> ROYAL_STAFF = ITEMS.register("royal_staff", RoyalStaffItem::new);
   public static final DeferredItem<Item> ROYAL_BOW = ITEMS.register("royal_bow", RoyalBowItem::new);
   public static final DeferredItem<Item> EMPTY_ROYAL_TELELOCATOR = ITEMS.register("empty_royal_telelocator", () -> new Item(new Properties()));
   public static final DeferredItem<Item> ROYAL_TELELOCATOR = ITEMS.register("royal_telelocator", RoyalTelelocator::new);
   public static final DeferredItem<Item> SPECTRAL_GAUNTLET = ITEMS.register("spectral_gauntlet", GloveItem::new);
   public static final DeferredItem<Item> ROYAL_KNIGHT_HELMET = ITEMS.register("royal_knight_helmet", () -> new RoyalKnightArmor(Type.HELMET));
   public static final DeferredItem<Item> ROYAL_KNIGHT_CUIRASS = ITEMS.register("royal_knight_cuirass", () -> new RoyalKnightArmor(Type.CHESTPLATE));
   public static final DeferredItem<Item> ROYAL_KNIGHT_LEGGINGS = ITEMS.register("royal_knight_leggings", () -> new RoyalKnightArmor(Type.LEGGINGS));
   public static final DeferredItem<Item> ROYAL_KNIGHT_BOOTS = ITEMS.register("royal_knight_boots", () -> new RoyalKnightArmor(Type.BOOTS));
   public static final DeferredItem<DeferredSpawnEggItem> ROYAL_ZOMBIE_SPAWN_EGG = ITEMS.register(
      "royal_zombie_spawn_egg", () -> new DeferredSpawnEggItem(RVEntityType.ROYAL_ZOMBIE, 268435455, 268435455, new Properties())
   );
   public static final DeferredItem<DeferredSpawnEggItem> ROYAL_SKELETON_SPAWN_EGG = ITEMS.register(
      "royal_skeleton_spawn_egg", () -> new DeferredSpawnEggItem(RVEntityType.ROYAL_SKELETON, 268435455, 268435455, new Properties())
   );
   public static final DeferredItem<DeferredSpawnEggItem> ROYAL_CREEPER_SPAWN_EGG = ITEMS.register(
      "royal_creeper_spawn_egg", () -> new DeferredSpawnEggItem(RVEntityType.ROYAL_CREEPER, 268435455, 268435455, new Properties())
   );
   public static final DeferredItem<DeferredSpawnEggItem> ROYAL_ENDERMAN_SPAWN_EGG = ITEMS.register(
      "royal_enderman_spawn_egg", () -> new DeferredSpawnEggItem(RVEntityType.ROYAL_ENDERMAN, 268435455, 268435455, new Properties())
   );
   public static final DeferredItem<Item> ACHIEVEMENT_ICON = ITEMS.register("achievement_icon", RVItems.DummyItem::new);
   public static final DeferredItem<Item> BONY_KING_ICON = ITEMS.register("bony_king_icon", RVItems.DummyItem::new);
   public static final DeferredItem<Item> ROTTEN_KING_ICON = ITEMS.register("rotten_king_icon", RVItems.DummyItem::new);
   public static final DeferredItem<Item> EXPLOSIVE_KING_ICON = ITEMS.register("explosive_king_icon", RVItems.DummyItem::new);
   public static final DeferredItem<Item> TELEPORTING_KING_ICON = ITEMS.register("teleporting_king_icon", RVItems.DummyItem::new);
   public static final DeferredItem<Item> LEGACY_OF_KINGS_ICON = ITEMS.register("legacy_of_kings_icon", RVItems.DummyItem::new);

   public static void init(IEventBus modEventBus) {
      ITEMS.register(modEventBus);
   }

   public static boolean shouldSkipCreativeModTab(Item item) {
      return item instanceof RVItems.DummyItem;
   }

   public static class DummyItem extends Item {
      public DummyItem() {
         super(new Properties());
      }
   }

   public static class ItemBase extends Item {
      public ItemBase() {
         super(new Properties());
      }
   }
}
