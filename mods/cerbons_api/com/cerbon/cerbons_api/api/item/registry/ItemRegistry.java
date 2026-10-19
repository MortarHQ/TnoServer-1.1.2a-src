package com.cerbon.cerbons_api.api.item.registry;

import com.cerbon.cerbons_api.api.item.enums.ToolType;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

public class ItemRegistry {
   private final ResourcefulRegistry<Item> itemRegistry;

   public ItemRegistry(String modId) {
      this.itemRegistry = ResourcefulRegistries.create(BuiltInRegistries.ITEM, modId);
   }

   public Map<Type, RegistryEntry<ArmorItem>> registerFullArmorSet(Holder<ArmorMaterial> material, int durabilityFactor) {
      return this.registerFullArmorSet(material, List.of(durabilityFactor, durabilityFactor, durabilityFactor, durabilityFactor));
   }

   public Map<Type, RegistryEntry<ArmorItem>> registerFullArmorSet(Holder<ArmorMaterial> material, List<Integer> durabilityFactors) {
      return this.registerFullArmorSet(material, properties -> properties, durabilityFactors);
   }

   public Map<Type, RegistryEntry<ArmorItem>> registerFullArmorSet(
      Holder<ArmorMaterial> material, UnaryOperator<Properties> itemProperties, List<Integer> durabilityFactors
   ) {
      Preconditions.checkArgument(
         durabilityFactors != null && durabilityFactors.size() == 4, "Expected durability array of length 4 (helmet, chest, legs, boots)"
      );
      return ImmutableMap.of(
         Type.HELMET,
         this.registerArmor(Type.HELMET, material, itemProperties, durabilityFactors.get(0)),
         Type.CHESTPLATE,
         this.registerArmor(Type.CHESTPLATE, material, itemProperties, durabilityFactors.get(1)),
         Type.LEGGINGS,
         this.registerArmor(Type.LEGGINGS, material, itemProperties, durabilityFactors.get(2)),
         Type.BOOTS,
         this.registerArmor(Type.BOOTS, material, itemProperties, durabilityFactors.get(3))
      );
   }

   public RegistryEntry<ArmorItem> registerArmor(Type armorType, Holder<ArmorMaterial> material, int durabilityFactor) {
      return this.registerArmor(armorType, material, properties -> properties, durabilityFactor);
   }

   public RegistryEntry<ArmorItem> registerArmor(Type armorType, Holder<ArmorMaterial> material, UnaryOperator<Properties> itemProperties, int durabilityFactor) {
      String materialName = material.unwrapKey().map(resourceKey -> resourceKey.location().getPath()).orElseThrow();
      return this.registerItem(
         () -> new ArmorItem(material, armorType, itemProperties.apply(new Properties().durability(armorType.getDurability(durabilityFactor)))),
         materialName + "_" + armorType.getSerializedName()
      );
   }

   public RegistryEntry<TieredItem> registerSimpleTool(ToolType toolType, Tier tier, float attackDamage, float attackSpeed, String id) {
      return this.registerSimpleTool(toolType, tier, properties -> properties, attackDamage, attackSpeed, id);
   }

   public RegistryEntry<TieredItem> registerSimpleTool(
      ToolType toolType, Tier tier, UnaryOperator<Properties> itemProperties, float attackDamage, float attackSpeed, String id
   ) {
      return switch (toolType) {
         case SWORD -> this.registerItem(
            () -> new SwordItem(tier, itemProperties.apply(new Properties().attributes(SwordItem.createAttributes(tier, (int)attackDamage, attackSpeed)))), id
         );
         case PICKAXE -> this.registerItem(
            () -> new PickaxeItem(tier, itemProperties.apply(new Properties().attributes(PickaxeItem.createAttributes(tier, attackDamage, attackSpeed)))), id
         );
         case AXE -> this.registerItem(
            () -> new AxeItem(tier, itemProperties.apply(new Properties().attributes(AxeItem.createAttributes(tier, attackDamage, attackSpeed)))), id
         );
         case SHOVEL -> this.registerItem(
            () -> new ShovelItem(tier, itemProperties.apply(new Properties().attributes(ShovelItem.createAttributes(tier, attackDamage, attackSpeed)))), id
         );
         case HOE -> this.registerItem(
            () -> new HoeItem(tier, itemProperties.apply(new Properties().attributes(HoeItem.createAttributes(tier, attackDamage, attackSpeed)))), id
         );
      };
   }

   public RegistryEntry<Item> registerFood(FoodProperties foodProperties, String id) {
      return this.registerFood(foodProperties, properties -> properties, id);
   }

   public RegistryEntry<Item> registerFood(FoodProperties foodProperties, UnaryOperator<Properties> itemProperties, String id) {
      return this.registerItem(itemProperties.apply(new Properties().food(foodProperties)), id);
   }

   public RegistryEntry<BlockItem> registerBlockItem(Supplier<Block> block, String id) {
      return this.registerBlockItem(block, new Properties(), id);
   }

   public RegistryEntry<BlockItem> registerBlockItem(Supplier<Block> block, Properties itemProperties, String id) {
      return this.registerBlockItem(id, () -> new BlockItem(block.get(), itemProperties));
   }

   public RegistryEntry<BlockItem> registerBlockItem(String id, Supplier<BlockItem> blockItem) {
      return this.registerItem(blockItem, id);
   }

   public RegistryEntry<Item> registerItem(String id) {
      return this.registerItem(new Properties(), id);
   }

   public RegistryEntry<Item> registerItem(Properties itemProperties, String id) {
      return this.registerItem(() -> new Item(itemProperties), id);
   }

   public <T extends Item> RegistryEntry<T> registerItem(Supplier<T> item, String id) {
      return this.itemRegistry.register(id, item);
   }

   public Collection<RegistryEntry<Item>> getEntries() {
      return this.itemRegistry.getEntries();
   }

   public Stream<RegistryEntry<Item>> stream() {
      return this.itemRegistry.stream();
   }

   public Stream<Item> boundStream() {
      return this.itemRegistry.boundStream();
   }

   public void register() {
      this.itemRegistry.register();
   }
}
