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

public class TemporalAmberArmorMaterial {
   public static final int BASE_DURABILITY = 35;
   public static final Holder<ArmorMaterial> TEMPORAL_AMBER = register("temporal_amber", (EnumMap<Type, Integer>)Util.make(new EnumMap(Type.class), map -> {
      map.put(Type.BOOTS, 3);
      map.put(Type.LEGGINGS, 6);
      map.put(Type.CHESTPLATE, 7);
      map.put(Type.HELMET, 3);
      map.put(Type.BODY, 7);
   }), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(new ItemLike[]{(ItemLike)ModItems.TEMPORAL_AMBER_DUST.get()}), 1.5F, 0.0F);

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
