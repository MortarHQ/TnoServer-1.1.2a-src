package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.util.ESConventionalTags;
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

public class ESArmorMaterials {
   public static final RegistrationProvider<ArmorMaterial> ARMOR_MATERIALS = RegistrationProvider.get(Registries.ARMOR_MATERIAL, "eternal_starlight");
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> AETHERSENT = ARMOR_MATERIALS.register(
      "aethersent",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 7);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 11);
         }),
         35,
         ESSoundEvents.ARMOR_EQUIP_AETHERSENT.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.INGOTS_AETHERSENT),
         List.of(new Layer(EternalStarlight.id("aethersent"))),
         0.0F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> THERMAL_SPRINGSTONE = ARMOR_MATERIALS.register(
      "thermal_springstone",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 6);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 7);
         }),
         15,
         ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.INGOTS_THERMAL_SPRINGSTONE),
         List.of(new Layer(EternalStarlight.id("thermal_springstone"))),
         0.5F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> GLACITE = ARMOR_MATERIALS.register(
      "glacite",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 6);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 7);
         }),
         9,
         ESSoundEvents.ARMOR_EQUIP_GLACITE.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.GEMS_GLACITE),
         List.of(new Layer(EternalStarlight.id("glacite"))),
         0.5F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> STARLIT_DIAMOND = ARMOR_MATERIALS.register(
      "starlit_diamond",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 11);
         }),
         22,
         ESSoundEvents.ARMOR_EQUIP_STARLIT_DIAMOND.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.GEMS_STARLIT_DIAMOND),
         List.of(new Layer(EternalStarlight.id("starlit_diamond"))),
         3.0F,
         0.1F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> DEEPSILVER = ARMOR_MATERIALS.register(
      "deepsilver",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 6);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 5);
         }),
         25,
         ESSoundEvents.ARMOR_EQUIP_DEEPSILVER.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.INGOTS_DEEPSILVER),
         List.of(new Layer(EternalStarlight.id("deepsilver"))),
         0.5F,
         0.2F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> UNREALIUM = ARMOR_MATERIALS.register(
      "unrealium",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 8);
            map.put(Type.HELMET, 3);
            map.put(Type.BODY, 11);
         }),
         25,
         ESSoundEvents.ARMOR_EQUIP_UNREALIUM.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.INGOTS_UNREALIUM),
         List.of(new Layer(EternalStarlight.id("unrealium"))),
         0.5F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> AMARAMBER = ARMOR_MATERIALS.register(
      "amaramber",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 5);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 5);
         }),
         9,
         ESSoundEvents.ARMOR_EQUIP_AMARAMBER.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.INGOTS_AMARAMBER),
         List.of(new Layer(EternalStarlight.id("amaramber"))),
         0.0F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> ALCHEMIST = ARMOR_MATERIALS.register(
      "alchemist",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 2);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.CHESTPLATE, 6);
            map.put(Type.HELMET, 2);
            map.put(Type.BODY, 5);
         }),
         15,
         ESSoundEvents.ARMOR_EQUIP_ALCHEMIST.asHolder(),
         () -> Ingredient.of(ESConventionalTags.Items.GEMS_THIOQUARTZ),
         List.of(new Layer(EternalStarlight.id("alchemist"))),
         0.0F,
         0.0F
      )
   );
   public static final RegistryObject<ArmorMaterial, ArmorMaterial> AIR_SAC = ARMOR_MATERIALS.register(
      "air_sac",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 1);
            map.put(Type.LEGGINGS, 4);
            map.put(Type.CHESTPLATE, 4);
            map.put(Type.HELMET, 1);
            map.put(Type.BODY, 4);
         }),
         9,
         SoundEvents.ARMOR_EQUIP_LEATHER,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ESItems.ROOKFISH_AIR_SAC.get()}),
         List.of(new Layer(EternalStarlight.id("air_sac"))),
         0.0F,
         0.0F
      )
   );

   public static void loadClass() {
   }
}
