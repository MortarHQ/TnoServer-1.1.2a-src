package com.github.ysbbbbbb.kaleidoscopecookery.init;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModArmorMaterials {
   public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, "kaleidoscope_cookery");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FARMER = ARMOR_MATERIALS.register(
      "cookery_farmer",
      () -> new ArmorMaterial(
         (Map)Util.make(Maps.newHashMap(), map -> {
            map.put(Type.HELMET, 1);
            map.put(Type.CHESTPLATE, 4);
            map.put(Type.LEGGINGS, 5);
            map.put(Type.BOOTS, 2);
         }),
         12,
         SoundEvents.ARMOR_EQUIP_LEATHER,
         () -> Ingredient.of(new ItemLike[]{Items.LEATHER}),
         List.of(new Layer(ResourceLocation.withDefaultNamespace("cookery_farmer"))),
         0.0F,
         0.0F
      )
   );
}
