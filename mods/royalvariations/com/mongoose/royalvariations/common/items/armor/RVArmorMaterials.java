package com.mongoose.royalvariations.common.items.armor;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.common.items.RVItems;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RVArmorMaterials {
   public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, "royalvariations");
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ROYAL_KNIGHT = ARMOR_MATERIALS.register(
      "royal_knight",
      () -> new ArmorMaterial(
         (Map)Util.make(new EnumMap(Type.class), map -> {
            map.put(Type.BOOTS, 3);
            map.put(Type.LEGGINGS, 6);
            map.put(Type.CHESTPLATE, 9);
            map.put(Type.HELMET, 4);
            map.put(Type.BODY, 11);
         }),
         24,
         SoundEvents.ARMOR_EQUIP_IRON,
         () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT, (ItemLike)RVItems.ROYAL_ARMOR_FRAGMENTS.get()}),
         List.of(new Layer(RoyalVariations.location("royal_knight"))),
         2.5F,
         0.0F
      )
   );

   public static void init(IEventBus modEventBus) {
      ARMOR_MATERIALS.register(modEventBus);
   }
}
