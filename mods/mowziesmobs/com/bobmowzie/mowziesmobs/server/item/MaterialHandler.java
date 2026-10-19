package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigurableArmorMaterial;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MaterialHandler {
   public static final DeferredRegister<ArmorMaterial> MM_ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "mowziesmobs");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SOL_VISAGE_MATERIAL = MM_ARMOR_MATERIALS.register(
      "sol_visage",
      () -> {
         ArmorMaterial material = new ArmorMaterial(
            (Map)Util.make(new EnumMap(Type.class), map -> map.put(Type.HELMET, ((ArmorMaterial)ArmorMaterials.GOLD.value()).getDefense(Type.HELMET))),
            ((ArmorMaterial)ArmorMaterials.GOLD.value()).enchantmentValue(),
            ((ArmorMaterial)ArmorMaterials.GOLD.value()).equipSound(),
            ((ArmorMaterial)ArmorMaterials.GOLD.value()).repairIngredient(),
            List.of(new Layer(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "sol_visage"))),
            ((ArmorMaterial)ArmorMaterials.GOLD.value()).toughness(),
            ((ArmorMaterial)ArmorMaterials.GOLD.value()).knockbackResistance()
         );
         ConfigurableArmorMaterial configurable = (ConfigurableArmorMaterial)material;
         configurable.mowziesmobs$setConfig(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig);
         return material;
      }
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> UMVUTHANA_MASK_MATERIAL = MM_ARMOR_MATERIALS.register(
      "umvuthana_mask",
      () -> {
         ArmorMaterial material = new ArmorMaterial(
            (Map)Util.make(new EnumMap(Type.class), map -> map.put(Type.HELMET, ((ArmorMaterial)ArmorMaterials.LEATHER.value()).getDefense(Type.HELMET))),
            ((ArmorMaterial)ArmorMaterials.LEATHER.value()).enchantmentValue(),
            ((ArmorMaterial)ArmorMaterials.LEATHER.value()).equipSound(),
            () -> Ingredient.of(new ItemLike[]{Items.AIR}),
            List.of(new Layer(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "umvuthana_mask"))),
            ((ArmorMaterial)ArmorMaterials.LEATHER.value()).toughness(),
            ((ArmorMaterial)ArmorMaterials.LEATHER.value()).knockbackResistance()
         );
         ConfigurableArmorMaterial configurable = (ConfigurableArmorMaterial)material;
         configurable.mowziesmobs$setConfig(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.UMVUTHANA_MASK.armorConfig);
         return material;
      }
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARMOR_WROUGHT_HELM = MM_ARMOR_MATERIALS.register(
      "wrought_helm",
      () -> {
         ArmorMaterial material = new ArmorMaterial(
            (Map)Util.make(new EnumMap(Type.class), map -> map.put(Type.HELMET, ((ArmorMaterial)ArmorMaterials.IRON.value()).getDefense(Type.HELMET))),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).enchantmentValue(),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).equipSound(),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).repairIngredient(),
            List.of(new Layer(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "wrought_helm"))),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).toughness(),
            0.1F
         );
         ConfigurableArmorMaterial configurable = (ConfigurableArmorMaterial)material;
         configurable.mowziesmobs$setConfig(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.WROUGHT_HELM.armorConfig);
         return material;
      }
   );
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GEOMANCER_ARMOR_MATERIAL = MM_ARMOR_MATERIALS.register(
      "geomancer_armor",
      () -> {
         ArmorMaterial material = new ArmorMaterial(
            (Map)Util.make(new EnumMap(Type.class), map -> {
               map.put(Type.BOOTS, 2);
               map.put(Type.LEGGINGS, 6);
               map.put(Type.CHESTPLATE, 7);
               map.put(Type.HELMET, 2);
            }),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).enchantmentValue(),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).equipSound(),
            () -> Ingredient.of(new ItemLike[]{(ItemLike)ItemHandler.BLUFF_ROD.value()}),
            List.of(new Layer(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "geomancer_armor"))),
            ((ArmorMaterial)ArmorMaterials.IRON.value()).toughness(),
            0.0F
         );
         ConfigurableArmorMaterial configurable = (ConfigurableArmorMaterial)material;
         configurable.mowziesmobs$setConfig(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.GEOMANCER_ARMOR.armorConfig);
         return material;
      }
   );
}
