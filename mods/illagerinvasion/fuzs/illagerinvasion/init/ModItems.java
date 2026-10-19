package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.item.HatchetItem;
import fuzs.illagerinvasion.world.item.HornOfSightItem;
import fuzs.illagerinvasion.world.item.IllusionaryDustItem;
import fuzs.illagerinvasion.world.item.LostCandleItem;
import fuzs.illagerinvasion.world.item.MagicalFireChargeItem;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;

public class ModItems {
   public static final Reference<Item> IMBUING_TABLE_ITEM = ModRegistry.REGISTRIES.registerBlockItem(ModRegistry.IMBUING_TABLE_BLOCK);
   public static final Reference<Item> UNUSUAL_DUST_ITEM = ModRegistry.REGISTRIES.registerItem("unusual_dust", () -> new Item(new Properties()));
   public static final Reference<Item> MAGICAL_FIRE_CHARGE_ITEM = ModRegistry.REGISTRIES
      .registerItem("magical_fire_charge", () -> new MagicalFireChargeItem(new Properties()));
   public static final Reference<Item> ILLUSIONARY_DUST_ITEM = ModRegistry.REGISTRIES
      .registerItem("illusionary_dust", () -> new IllusionaryDustItem(new Properties()));
   public static final Reference<Item> LOST_CANDLE_ITEM = ModRegistry.REGISTRIES.registerItem("lost_candle", () -> new LostCandleItem(new Properties()));
   public static final Reference<Item> HORN_OF_SIGHT_ITEM = ModRegistry.REGISTRIES
      .registerItem(
         "horn_of_sight",
         () -> new HornOfSightItem(
            new Properties().stacksTo(1).component(DataComponents.INSTRUMENT, ModRegistry.REVEAL_INSTRUMENT), ModRegistry.HORN_OF_SIGHT_INSTRUMENT_TAG
         )
      );
   public static final Reference<Item> HALLOWED_GEM_ITEM = ModRegistry.REGISTRIES
      .registerItem("hallowed_gem", () -> new Item(new Properties().rarity(Rarity.UNCOMMON)));
   public static final Reference<Item> PRIMAL_ESSENCE_ITEM = ModRegistry.REGISTRIES
      .registerItem("primal_essence", () -> new Item(new Properties().rarity(Rarity.RARE)));
   public static final Reference<Item> PLATINUM_CHUNK_ITEM = ModRegistry.REGISTRIES.registerItem("platinum_chunk", () -> new Item(new Properties()));
   public static final Reference<Item> PLATINUM_SHEET_ITEM = ModRegistry.REGISTRIES.registerItem("platinum_sheet", () -> new Item(new Properties()));
   public static final Reference<Item> PLATINUM_INFUSED_HATCHET_ITEM = ModRegistry.REGISTRIES
      .registerItem(
         "platinum_infused_hatchet", () -> new HatchetItem(new Properties().rarity(Rarity.EPIC).durability(327).attributes(HatchetItem.createAttributes()))
      );
   public static final Reference<Item> PROVOKER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.PROVOKER_ENTITY_TYPE, 9541270, 9399876);
   public static final Reference<Item> BASHER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(ModEntityTypes.BASHER_ENTITY_TYPE, 9541270, 5985087);
   public static final Reference<Item> SORCERER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.SORCERER_ENTITY_TYPE, 9541270, 10899592);
   public static final Reference<Item> ARCHIVIST_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.ARCHIVIST_ENTITY_TYPE, 9541270, 13251893);
   public static final Reference<Item> INQUISITOR_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.INQUISITOR_ENTITY_TYPE, 9541270, 4934471);
   public static final Reference<Item> MARAUDER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.MARAUDER_ENTITY_TYPE, 9541270, 5441030);
   public static final Reference<Item> INVOKER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.INVOKER_ENTITY_TYPE, 9541270, 13552007);
   public static final Reference<Item> ALCHEMIST_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.ALCHEMIST_ENTITY_TYPE, 9541270, 7550099);
   public static final Reference<Item> FIRECALLER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.FIRECALLER_ENTITY_TYPE, 9541270, 14185784);
   public static final Reference<Item> NECROMANCER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.NECROMANCER_ENTITY_TYPE, 9541270, 9585210);
   public static final Reference<Item> SURRENDERED_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerSpawnEggItem(ModEntityTypes.SURRENDERED_ENTITY_TYPE, 11260369, 11858160);
   public static final Reference<Item> ILLUSIONER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES
      .registerItem("illusioner_spawn_egg", () -> new SpawnEggItem(EntityType.ILLUSIONER, 1267859, 9541270, new Properties()));

   public static void touch() {
   }
}
