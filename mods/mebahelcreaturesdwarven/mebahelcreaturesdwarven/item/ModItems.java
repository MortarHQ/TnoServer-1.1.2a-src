package mebahelcreaturesdwarven.item;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import mebahelcreaturesdwarven.item.weapon.DwarvenArrowItem;
import mebahelcreaturesdwarven.item.weapon.DwemerAxe;
import mebahelcreaturesdwarven.item.weapon.DwemerDagger;
import mebahelcreaturesdwarven.item.weapon.DwemerMace;
import mebahelcreaturesdwarven.item.weapon.DwemerSword;
import mebahelcreaturesdwarven.item.weapon.DwemerWarAxe;
import mebahelcreaturesdwarven.item.weapon.DwemerWarhammer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.class_1792;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7706;
import net.minecraft.class_7923;
import net.minecraft.class_1792.class_1793;

public class ModItems {
   public static final class_1792 DWARVEN_ARROW = registerItem("dwarven_arrow", new DwarvenArrowItem(new class_1793()));
   public static final class_1792 DWEMER_WAR_AXE = registerItem("weapon/dwemer/dwemer_war_axe", new DwemerWarAxe(ModToolMaterial.DWARVEN_2, new class_1793()));
   public static final class_1792 DWEMER_WARHAMMER = registerItem(
      "weapon/dwemer/dwemer_warhammer", new DwemerWarhammer(ModToolMaterial.DWARVEN_1, new class_1793())
   );
   public static final class_1792 DWEMER_DAGGER = registerItem("weapon/dwemer/dwemer_dagger", new DwemerDagger(ModToolMaterial.DWARVEN_1, new class_1793()));
   public static final class_1792 DWEMER_AXE = registerItem("weapon/dwemer/dwemer_axe", new DwemerAxe(ModToolMaterial.DWARVEN_1, new class_1793()));
   public static final class_1792 DWEMER_SWORD = registerItem("weapon/dwemer/dwemer_sword", new DwemerSword(ModToolMaterial.DWARVEN_1, new class_1793()));
   public static final class_1792 DWEMER_MACE = registerItem("weapon/dwemer/dwemer_mace", new DwemerMace(ModToolMaterial.DWARVEN_2, new class_1793()));
   public static final class_1792 DWEMER_METAL_SCRAP = registerItem("dwemer_metal_scrap", new class_1792(new class_1793()));
   public static final class_1792 DWEMER_METAL_INGOT = registerItem("dwemer_metal_ingot", new class_1792(new class_1793()));
   public static final class_1792 DWEMER_METAL_GEAR = registerItem("dwemer_metal_gear", new class_1792(new class_1793()));

   public static <I extends class_1792> I registerItem(String name, I item) {
      return (I)class_2378.method_10230(class_7923.field_41178, class_2960.method_60655("mebahelcreaturesdwarven", name), item);
   }

   public static void addItemToSpawnEggItemGroup(FabricItemGroupEntries entries) {
   }

   public static void addItemToIngredientItemGroup(FabricItemGroupEntries entries) {
      entries.method_45421(DWEMER_METAL_GEAR);
      entries.method_45421(DWEMER_METAL_SCRAP);
      entries.method_45421(DWEMER_METAL_INGOT);
   }

   public static void addItemToFoodItemGroup(FabricItemGroupEntries entries) {
   }

   public static void addItemToToolsAndUtilitiesGroup(FabricItemGroupEntries entries) {
   }

   public static void addItemToFunctionalItemGroup(FabricItemGroupEntries entries) {
   }

   public static void addItemToCombatItemGroup(FabricItemGroupEntries entries) {
      entries.method_45421(DWEMER_DAGGER);
      entries.method_45421(DWEMER_SWORD);
      entries.method_45421(DWEMER_MACE);
      entries.method_45421(DWEMER_WARHAMMER);
      entries.method_45421(DWEMER_AXE);
      entries.method_45421(DWEMER_WAR_AXE);
      entries.method_45421(DWARVEN_ARROW);
   }

   public static void registerModItems() {
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_40205).register(ModItems::addItemToSpawnEggItemGroup);
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_40202).register(ModItems::addItemToCombatItemGroup);
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_41062).register(ModItems::addItemToIngredientItemGroup);
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_41061).register(ModItems::addItemToFoodItemGroup);
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_40197).register(ModItems::addItemToFunctionalItemGroup);
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_41060).register(ModItems::addItemToToolsAndUtilitiesGroup);
      MebahelCreaturesDwarven.LOGGER.info("Registering items for mebahelcreaturesdwarven.");
   }
}
