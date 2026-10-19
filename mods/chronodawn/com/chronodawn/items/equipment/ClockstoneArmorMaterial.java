package com.chronodawn.items.equipment;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModItems;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ClockstoneArmorMaterial {
   public static final Holder<ArmorMaterial> CLOCKSTONE = register("clockstone", (EnumMap<Type, Integer>)Util.make(new EnumMap(Type.class), map -> {
      map.put(Type.BOOTS, 2);
      map.put(Type.LEGGINGS, 5);
      map.put(Type.CHESTPLATE, 6);
      map.put(Type.HELMET, 2);
      map.put(Type.BODY, 6);
   }), 14, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TIME_CRYSTAL.get()}), 1.0F, 0.0F);

   private static Holder<ArmorMaterial> register(
      String name,
      EnumMap<Type, Integer> defense,
      int enchantmentValue,
      Holder<SoundEvent> equipSound,
      Supplier<Ingredient> repairIngredient,
      float toughness,
      float knockbackResistance
   ) {
      EnumMap<Type, Integer> defenseMap = new EnumMap<>(Type.class);

      for (Type type : Type.values()) {
         defenseMap.put(type, defense.get(type));
      }

      return Registry.registerForHolder(
         BuiltInRegistries.ARMOR_MATERIAL,
         CompatResourceLocation.create("chronodawn", name),
         new ArmorMaterial(
            defenseMap,
            enchantmentValue,
            equipSound,
            repairIngredient,
            List.of(new Layer(CompatResourceLocation.create("chronodawn", name))),
            toughness,
            knockbackResistance
         )
      );
   }
}
