package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;

public final class FriendsAndFoesArmorMaterials {
   public static final ResourcefulRegistry<ArmorMaterial> ARMOR_MATERIALS = ResourcefulRegistries.create(BuiltInRegistries.ARMOR_MATERIAL, "friendsandfoes");
   public static final HolderRegistryEntry<ArmorMaterial> WILDFIRE = ARMOR_MATERIALS.registerHolder(
      "wildfire_crown", () -> createArmorMaterial("wildfire_crown", (EnumMap<Type, Integer>)Util.make(new EnumMap(Type.class), enumMap -> {
         enumMap.put(Type.BOOTS, 3);
         enumMap.put(Type.LEGGINGS, 6);
         enumMap.put(Type.CHESTPLATE, 8);
         enumMap.put(Type.HELMET, 3);
         enumMap.put(Type.BODY, 11);
      }), 9, FriendsAndFoesSoundEvents.ITEM_EQUIP_WILDFIRE_CROWN.holder(), 1.0F, 0.0F, () -> Ingredient.of(FriendsAndFoesTags.REPAIRS_WILDFIRE_CROWN))
   );

   private static ArmorMaterial createArmorMaterial(
      String layerName,
      EnumMap<Type, Integer> enumMap,
      int enchantmentValue,
      Holder<SoundEvent> equipSound,
      float toughness,
      float knockback,
      Supplier<Ingredient> repairIngredient
   ) {
      EnumMap<Type, Integer> defenseMap = new EnumMap<>(Type.class);

      for (Type type : Type.values()) {
         defenseMap.put(type, enumMap.get(type));
      }

      List<Layer> layers = List.of(new Layer(FriendsAndFoes.makeID(layerName)));
      return new ArmorMaterial(defenseMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockback);
   }

   private FriendsAndFoesArmorMaterials() {
   }
}
