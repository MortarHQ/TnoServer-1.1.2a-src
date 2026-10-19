package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Armortier {
   public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "cataclysm");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> IGNITIUM = ARMOR_MATERIALS.register(
      "ignitium",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 6);
            map.put(Type.LEGGINGS, 9);
            map.put(Type.CHESTPLATE, 11);
            map.put(Type.HELMET, 6);
            map.put(Type.BODY, 15);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.IGNITIUM_INGOT.get()}),
         List.of(new Layer(ResourceLocation.fromNamespaceAndPath("cataclysm", "ignitium"))),
         4.0F,
         0.15F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CURSIUM = ARMOR_MATERIALS.register(
      "cursium",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 5);
            map.put(Type.LEGGINGS, 8);
            map.put(Type.CHESTPLATE, 10);
            map.put(Type.HELMET, 5);
            map.put(Type.BODY, 13);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.CURSIUM_INGOT.get()}),
         List.of(new Layer(ResourceLocation.fromNamespaceAndPath("cataclysm", "cursium"))),
         4.0F,
         0.05F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CRAB = ARMOR_MATERIALS.register(
      "crab",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 4);
            map.put(Type.LEGGINGS, 7);
            map.put(Type.CHESTPLATE, 9);
            map.put(Type.HELMET, 4);
            map.put(Type.BODY, 13);
         }),
         25,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.AMETHYST_CRAB_SHELL.get()}),
         List.of(new Layer(ResourceLocation.fromNamespaceAndPath("cataclysm", "crab"))),
         2.0F,
         0.1F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BONE_REPTILE = ARMOR_MATERIALS.register(
      "bone_reptile",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 4);
            map.put(Type.LEGGINGS, 7);
            map.put(Type.CHESTPLATE, 11);
            map.put(Type.HELMET, 6);
            map.put(Type.BODY, 13);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.ANCIENT_METAL_INGOT.get()}),
         List.of(new Layer(ResourceLocation.fromNamespaceAndPath("cataclysm", "bone_reptile"))),
         2.5F,
         0.25F
      )
   );
}
