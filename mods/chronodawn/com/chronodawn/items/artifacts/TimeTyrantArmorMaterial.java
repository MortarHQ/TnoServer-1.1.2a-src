package com.chronodawn.items.artifacts;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModItems;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class TimeTyrantArmorMaterial {
   private static final EnumMap<Type, Integer> DEFENSE_VALUES = (EnumMap<Type, Integer>)Util.make(new EnumMap(Type.class), map -> {
      map.put(Type.BOOTS, 3);
      map.put(Type.LEGGINGS, 6);
      map.put(Type.CHESTPLATE, 8);
      map.put(Type.HELMET, 3);
      map.put(Type.BODY, 8);
   });
   public static final Holder<ArmorMaterial> MATERIAL = Registry.registerForHolder(
      BuiltInRegistries.ARMOR_MATERIAL,
      CompatResourceLocation.create("chronodawn", "time_tyrant"),
      new ArmorMaterial(
         DEFENSE_VALUES,
         18,
         SoundEvents.ARMOR_EQUIP_NETHERITE,
         () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()}),
         List.of(new Layer(CompatResourceLocation.create("chronodawn", "time_tyrant"))),
         2.5F,
         0.1F
      )
   );
}
