package twilightforest.init;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.TwilightForestMod;

public class TFArmorMaterials {
   public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "twilightforest");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NAGA = ARMOR_MATERIALS.register(
      "naga_scale",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 7);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 8);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.NAGA_SCALE.get()}),
         List.of(new Layer(TwilightForestMod.prefix("naga_scale"))),
         0.5F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> IRONWOOD = ARMOR_MATERIALS.register(
      "ironwood",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 7);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 5);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.IRONWOOD_INGOT.get()}),
         List.of(new Layer(TwilightForestMod.prefix("ironwood"))),
         0.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FIERY = ARMOR_MATERIALS.register(
      "fiery",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 4);
            map.put(Type.LEGGINGS, 7);
            map.put(Type.CHESTPLATE, 9);
            map.put(Type.HELMET, 4);
            map.put(Type.BODY, 13);
         }),
         10,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.FIERY_INGOT.get()}),
         List.of(new Layer(TwilightForestMod.prefix("fiery"))),
         1.5F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TRAVELLERS = ARMOR_MATERIALS.register(
      "travellers",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 3);
            map.put(Type.CHESTPLATE, 4);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 4);
         }),
         0,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{TFItems.TANNED_LEATHER}),
         List.of(new Layer(TwilightForestMod.prefix("travellers"))),
         0.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STEELEAF = ARMOR_MATERIALS.register(
      "steeleaf",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 11);
         }),
         9,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.STEELEAF_INGOT.get()}),
         List.of(new Layer(TwilightForestMod.prefix("steeleaf"))),
         0.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KNIGHTMETAL = ARMOR_MATERIALS.register(
      "knightmetal",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 11);
         }),
         8,
         TFSounds.KNIGHTMETAL_EQUIP,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.KNIGHTMETAL_INGOT.get()}),
         List.of(new Layer(TwilightForestMod.prefix("knightmetal"))),
         1.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PHANTOM = ARMOR_MATERIALS.register(
      "phantom",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 10);
         }),
         8,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.KNIGHTMETAL_INGOT.get()}),
         List.of(new Layer(TwilightForestMod.prefix("phantom"))),
         2.5F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> YETI = ARMOR_MATERIALS.register(
      "yeti",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 7);
            map.put(Type.HELMET, 4);
            map.put(Type.BODY, 11);
         }),
         15,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.ALPHA_YETI_FUR.get()}),
         List.of(new Layer(TwilightForestMod.prefix("yeti"))),
         3.0F,
         0.0F
      )
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARCTIC = ARMOR_MATERIALS.register(
      "arctic",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 7);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 7);
         }),
         8,
         SoundEvents.ARMOR_EQUIP_GENERIC,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)TFItems.ARCTIC_FUR.get()}),
         List.of(new Layer(TwilightForestMod.prefix("arctic"), "_dyed", true), new Layer(TwilightForestMod.prefix("arctic"), "_overlay", false)),
         2.0F,
         0.0F
      )
   );
}
